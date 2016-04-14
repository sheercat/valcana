package net.vg4.valcana.model;

import java.util.List;

import lombok.Data;

@Data
public class LineBotRequest implements BotResponse {
    List<String> to;
    final int toChannel = 1383378250;
    final String eventType = "138311608800106203";
    LineBotResponseContent content;
}
