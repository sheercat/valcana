package net.vg4.valcana.model;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

@Data
public class FBMessengerBotWebhookRecipient {
	Map<String, BigDecimal> recipient;
	Map<String, String> message;

}
