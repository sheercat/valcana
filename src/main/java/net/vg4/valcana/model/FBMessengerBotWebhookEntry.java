package net.vg4.valcana.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/*
 * 
        {
            "id": 492081890991880,
            "time": 1460629438502,
            "messaging": [
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
            ]
        }
 */

@Data
public class FBMessengerBotWebhookEntry {
	BigDecimal id;
	BigDecimal time;
	List<FBMessengerBotWebhookEntryMessaging> messaging;
}
