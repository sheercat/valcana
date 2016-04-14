package net.vg4.valcana.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import net.vg4.valcana.model.LineBotRequest;
import net.vg4.valcana.model.LineBotResponse;
import net.vg4.valcana.model.LineBotResponseResult;

@Service
@val
@Slf4j
public class LineBotService {
	final String LINEBOTAPI_ENDPOINT = "https://trialbot-api.line.me/v1/events";
	final String LINE_CHANNEL_ID = System.getenv("LINE_CHANNEL_ID");
	final String LINE_CHANNEL_SECRET = System.getenv("LINE_CHANNEL_SECRET");
	final String LINE_CHANNEL_MID = System.getenv("LINE_CHANNEL_MID");

	public void sendToChannel(HttpServletRequest request) {
		try {
			val jb = new StringBuffer();
			request.getReader().lines().forEach(jb::append);
			log.info(jb.toString());

			val mapper = new ObjectMapper();
			val botResponse = mapper.readValue(jb.toString(), LineBotResponse.class);

			botResponse.getResult().stream().forEach(this::sendRequest);
		} catch (Exception e) {
			log.error(e.toString());
		}
	}

	void sendRequest(LineBotResponseResult botResponse) {
		try {
			val botContent = botResponse.getContent();
			val request = new LineBotRequest();
			request.setTo(Arrays.asList(botContent.getFrom()));
			request.setContent(botContent);
			val text = botContent.getText();
			Stream<String> stream = Arrays.stream(text.split(""));
			val post = new HttpPost(LINEBOTAPI_ENDPOINT);
			post.setHeader("Content-Type", "application/json; charset=UTF-8");
			post.setHeader("X-Line-ChannelID", LINE_CHANNEL_ID);
			post.setHeader("X-Line-ChannelSecret", LINE_CHANNEL_SECRET);
			post.setHeader("X-Line-Trusted-User-With-ACL", LINE_CHANNEL_MID);
			try (val httpclient = HttpClients.createDefault()) { // .custom()
				stream.forEach(e -> sendOneRequest(httpclient, post, request, e));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void sendOneRequest(CloseableHttpClient client, HttpPost post, LineBotRequest request, String oneString) {
		try {
			request.getContent().setText(oneString);
			val mapper = new ObjectMapper();
			val json = mapper.writeValueAsString(request);

			post.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
			val res = client.execute(post);

			Arrays.stream(res.getAllHeaders()).forEach(e -> log.info(e.toString()));
			try (val br = new BufferedReader(
					new InputStreamReader(res.getEntity().getContent(), StandardCharsets.UTF_8))) {
				br.lines().forEach(e -> log.info(e.toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.info("JSON:" + json);
			log.info("STATUSLINE:" + res.getStatusLine().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
