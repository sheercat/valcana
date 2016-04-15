package net.vg4.valcana.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import net.vg4.valcana.model.FBMessengerBotWebhook;
import net.vg4.valcana.model.FBMessengerBotWebhookEntryMessaging;
import net.vg4.valcana.model.FBMessengerBotWebhookRecipient;

@val
@Slf4j
@Service
public class FBMessengerBotService {
	final String FBMESSENGERBOT_VERIFY_TOKEN = System.getenv("FBMESSENGERBOT_VERIFY_TOKEN");
	final String FBMESSENGERBOT_ACCESS_TOKEN = System.getenv("FBMESSENGERBOT_ACCESS_TOKEN");
	final String FBMESSENGERBOT_ENDPOINT = "https://graph.facebook.com/v2.6/me/messages";

	public String verify(HttpServletRequest request) {
		try {
			val paramMap = request.getParameterMap();

			// debug
			val mapStream = paramMap.entrySet().stream();
			mapStream.forEach(e -> log.info(String.format("%s:%s", e.getKey(), String.join("", e.getValue()))));

			if (paramMap.get("hub.mode")[0].equals("subscribe")
					&& paramMap.get("hub.verify_token")[0].equals(FBMESSENGERBOT_VERIFY_TOKEN)) {
				return String.join("", paramMap.get("hub.challenge"));
			}
		} catch (UnrecognizedPropertyException ex) {
			;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "failed";
	}

	public String sentToMessenger(HttpServletRequest request) {
		try {
			val jb = new StringBuffer();
			request.getReader().lines().forEach(jb::append);
			log.info(jb.toString());

			val mapper = new ObjectMapper();
			val botResponse = mapper.readValue(jb.toString(), FBMessengerBotWebhook.class);
			log.error("!!!" + ToStringBuilder.reflectionToString(botResponse));
			botResponse.getEntry().forEach(e -> e.getMessaging().stream().forEach(this::sendMessage));
		} catch (UnrecognizedPropertyException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}

	void sendMessage(FBMessengerBotWebhookEntryMessaging messaging) {
		try {
			val message = messaging.getMessage();
			val text = message.getText();
			log.info(text);
			Stream<String> stream = Arrays.stream(text.split(""));
			val builder = new URIBuilder(FBMESSENGERBOT_ENDPOINT);
			builder.setParameter("access_token", FBMESSENGERBOT_ACCESS_TOKEN);
			val recipient = new FBMessengerBotWebhookRecipient();
			recipient.setRecipient(messaging.getSender());
			val post = new HttpPost(builder.build());
			post.setHeader("Content-Type", "application/json; charset=UTF-8");
			try (val httpclient = HttpClients.createDefault()) {
				stream.forEach(e -> sendOneRequest(httpclient, post, recipient, e));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	void sendOneRequest(CloseableHttpClient client, HttpPost post, FBMessengerBotWebhookRecipient recipient,
			String oneString) {
		try {
			Map<String, String> message = new HashMap<>();
			message.put("text", oneString);
			recipient.setMessage(message);

			val mapper = new ObjectMapper();
			val json = mapper.writeValueAsString(recipient);
			log.info(json);

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
