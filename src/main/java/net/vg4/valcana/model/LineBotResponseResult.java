package net.vg4.valcana.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class LineBotResponseResult {
	LineBotResponseContent content;
	BigDecimal createdTime;
	String eventType;
	String from;
	BigDecimal fromChannel;
	String id;
	List<String> to;
	BigDecimal toChannel;

}
