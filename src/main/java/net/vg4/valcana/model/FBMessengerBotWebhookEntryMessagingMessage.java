package net.vg4.valcana.model;

import java.math.BigDecimal;

import lombok.Data;

/*
 * 
                    "message": {
                        "mid": "mid.1460629438465:cfe443d725b3b7b433",
                        "seq": 3,
                        "text": "!!!"
                    }
 */

@Data
public class FBMessengerBotWebhookEntryMessagingMessage {
	String mid;
	BigDecimal seq;
	String text;
}
