package net.vg4.valcana.model;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

/*
 * 
                {
                    "sender": {
                        "id": 774181692683344
                    },
                    "recipient": {
                        "id": 492081890991880
                    },
                    "timestamp": 1460629438476,
                    "message": {
                        "mid": "mid.1460629438465:cfe443d725b3b7b433",
                        "seq": 3,
                        "text": "!!!"
                    }
                }
 */

@Data
public class FBMessengerBotWebhookEntryMessaging {
	Map<String, BigDecimal> sender;
	Map<String, BigDecimal> recipient;
	BigDecimal timestamp;
	FBMessengerBotWebhookEntryMessagingMessage message;
}
