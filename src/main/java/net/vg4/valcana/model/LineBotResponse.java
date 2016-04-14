package net.vg4.valcana.model;

import java.util.List;

import lombok.Data;

/*
 {
    "result": [
        {
            "content": {
                "toType": 1,
                "createdTime": 1460375020236,
                "from": "u8e55a2e1a8c3300520e0f2fdadbbfc8b",
                "location": null,
                "id": "4157875544643",
                "to": [
                    "ud686b5aa306e8ff5c2c917f3f3a02ac2"
                ],
                "text": "わーいですよ",
                "contentMetadata": {
                    "AT_RECV_MODE": "2",
                    "EMTVER": "4"
                },
                "deliveredTime": 0,
                "contentType": 1,
                "seq": null
            },
            "createdTime": 1460375020262,
            "eventType": "138311609000106303",
            "from": "u206d25c2ea6bd87c17655609a1c37cb8",
            "fromChannel": 1341301815,
            "id": "WB1521-3348539942",
            "to": [
                "ud686b5aa306e8ff5c2c917f3f3a02ac2"
            ],
            "toChannel": 1461682721
        }
    ]
}
 */

@Data
public class LineBotResponse implements BotResponse {
	List<LineBotResponseResult> result;
}
