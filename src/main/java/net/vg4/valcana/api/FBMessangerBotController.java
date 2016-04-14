package net.vg4.valcana.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import net.vg4.valcana.service.FBMessangerBotService;

@RestController
@Slf4j
@val
public class FBMessangerBotController {
	@Autowired
	FBMessangerBotService botService;

	@RequestMapping(value = "/fbmessangerbot")
	String index(HttpServletRequest request) throws RuntimeException {
		botService.sendToChannel(request);
		val reqestByString = ToStringBuilder.reflectionToString(request);
		log.info("!!!" + reqestByString);
		return "OK";
	}

}
