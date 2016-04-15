package net.vg4.valcana.service;

import javax.servlet.http.HttpServletRequest;

public interface BotService {

	String send(HttpServletRequest request);

	String verify(HttpServletRequest request);

}
