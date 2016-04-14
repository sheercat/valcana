package net.vg4.valcana.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import net.vg4.valcana.model.FBMessengerBotWebhook;

@val
@Slf4j
@Service
public class FBMessengerBotService {
	final String FBMESSENGERBOT_VERIFY_TOKEN = System.getenv("FBMESSENGERBOT_VERIFY_TOKEN");
	final String FBMESSENGERBOT_ACCESS_TOKEN = System.getenv("FBMESSENGERBOT_ACCESS_TOKEN");

	public String verify(HttpServletRequest request) {
		try {
			val params = request.getParameterMap();

			// debug
			val mapStream = params.entrySet().stream();
			mapStream.forEach(e -> log.info(String.format("%s:%s", e.getKey(), String.join("", e.getValue()))));
			
			if (params.get("hub.mode")[0].equals("subscribe") && params.get("hub.verify_token")[0].equals(FBMESSENGERBOT_VERIFY_TOKEN)) {
				return String.join("", params.get("hub.challenge"));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}
}
