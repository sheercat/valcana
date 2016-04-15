package net.vg4.valcana.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import net.vg4.valcana.service.FBMessengerBotService;

@RestController
@Slf4j
@val
public class FBMessengerBotController {
	@Autowired
	FBMessengerBotService botService;

	@RequestMapping(value = "/fbmessengerbot", method = RequestMethod.GET)
	String verify(HttpServletRequest request) throws RuntimeException {
		return botService.verify(request);
	}
	@RequestMapping(value = "/fbmessengerbot", method = RequestMethod.POST)
	String message(HttpServletRequest request) throws RuntimeException {
		return botService.sentToMessenger(request);
	}

}