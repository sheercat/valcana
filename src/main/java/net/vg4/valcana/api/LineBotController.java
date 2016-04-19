package net.vg4.valcana.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.vg4.valcana.service.BotService;

@RestController
@Slf4j
public class LineBotController {
	@Autowired
	@Qualifier("line")
	BotService botService;

	@RequestMapping(value = "/linebot")
	String index(HttpServletRequest request) throws RuntimeException {
		botService.send(request);
		String reqestByString = ToStringBuilder.reflectionToString(request);
		log.info("!!!" + reqestByString);
		return "OK";
	}

}
