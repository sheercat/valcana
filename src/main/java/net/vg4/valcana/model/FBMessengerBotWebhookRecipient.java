package net.vg4.valcana.model;

import java.util.Map;

import lombok.Data;

@Data
public class FBMessengerBotWebhookRecipient {
	Map<String, String> recipient;
	Map<String, String> message;

}
