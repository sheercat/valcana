package net.vg4.valcana.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.vg4.valcana.model.LineBotRequest;
import net.vg4.valcana.model.LineBotResponse;
import net.vg4.valcana.model.LineBotResponseContent;
import net.vg4.valcana.model.LineBotResponseResult;

@Service("line")
@Slf4j
public class LineBotService implements BotService {
	final String LINEBOTAPI_ENDPOINT = System.getenv("LINEBOTAPI_ENDPOINT");
	final String LINE_CHANNEL_ID = System.getenv("LINE_CHANNEL_ID");
	final String LINE_CHANNEL_SECRET = System.getenv("LINE_CHANNEL_SECRET");
	final String LINE_CHANNEL_MID = System.getenv("LINE_CHANNEL_MID");

	@Override
	public String verify(HttpServletRequest request) {
		return "ok";
	}

	@Override
	public String send(HttpServletRequest request) {
		try {
			StringBuffer jb = new StringBuffer();
			request.getReader().lines().forEach(jb::append);
			log.info(jb.toString());

			ObjectMapper mapper = new ObjectMapper();
			LineBotResponse botResponse = mapper.readValue(jb.toString(), LineBotResponse.class);

			botResponse.getResult().stream().forEach(this::sendRequest);
		} catch (Exception e) {
			log.error(e.toString());
		}
		return "ok";
	}

	void sendRequest(LineBotResponseResult botResponse) {
		try {
			LineBotResponseContent botContent = botResponse.getContent();
			String text = botContent.getText();
			try (Stream<String> stream = Arrays.stream(text.split(""))) {
				try (CloseableHttpClient httpclient = HttpClients.createDefault()) { // .custom()
					stream.parallel().forEach(e -> sendOneRequest(httpclient, botContent.clone(), e));
				} catch (IOException ex) {
					throw ex;
				}
			} catch (IOException ex) {
				throw ex;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void sendOneRequest(CloseableHttpClient client, LineBotResponseContent botContent, String oneString) {
		try {
			HttpPost post = new HttpPost(LINEBOTAPI_ENDPOINT);
			post.setHeader("Content-Type", "application/json; charset=UTF-8");
			post.setHeader("X-Line-ChannelID", LINE_CHANNEL_ID);
			post.setHeader("X-Line-ChannelSecret", LINE_CHANNEL_SECRET);
			post.setHeader("X-Line-Trusted-User-With-ACL", LINE_CHANNEL_MID);

			LineBotRequest request = new LineBotRequest();
			request.setTo(Arrays.asList(botContent.getFrom()));
			request.setContent(botContent);
			request.getContent().setText(oneString);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(request);

			post.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
			try (CloseableHttpResponse res = client.execute(post)) {
				Arrays.stream(res.getAllHeaders()).forEach(e -> log.info(e.toString()));
				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(res.getEntity().getContent(), StandardCharsets.UTF_8))) {
					br.lines().forEach(e -> log.info(e.toString()));
				} catch (IOException ex) {
					throw ex;
				}
				log.info("JSON:" + json);
				log.info("STATUSLINE:" + res.getStatusLine().toString());
			} catch (IOException ex) {
				throw ex;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
