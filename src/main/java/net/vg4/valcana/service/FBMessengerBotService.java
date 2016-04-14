package net.vg4.valcana.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

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
			
			if (params.get("hub.mode").equals("subscribe") && params.get("hub.verify_token").equals(FBMESSENGERBOT_VERIFY_TOKEN)) {
				return String.join("", params.get("hub.challenge"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	public String sentToMessenger(HttpServletRequest request) {
		try {
			val params = request.getParameterMap();
			// debug
			val mapStream = params.entrySet().stream();
			mapStream.forEach(e -> log.info(String.format("%s:%s", e.getKey(), String.join("", e.getValue()))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}
}
