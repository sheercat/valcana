package net.vg4.valcana.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@val
@Slf4j
@Service
public class FBMessangerBotService {
	final String FBMESSAGEAPI_ENDPOINT = "https://trialbot-api.line.me/v1/events";

	public void sendToChannel(HttpServletRequest request) {
		try {
			val jb = new StringBuffer();
			request.getReader().lines().forEach(jb::append);
			log.info(jb.toString());

//			val mapper = new ObjectMapper();
//			val botResponse = mapper.readValue(jb.toString(), LineBotResponse.class);

//			botResponse.getResult().stream().forEach(this::sendRequest);
		} catch (Exception e) {
			log.error(e.toString());
		}
	}
}
