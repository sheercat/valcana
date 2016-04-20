package net.vg4.valcana.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class LineBotResponseContent implements Cloneable {
	String toType;
	BigDecimal createdTime;
	String from;
	String location;
	String id;
	List<String> to;
	String text;
	Map<String, String> contentMetadata;
	BigDecimal deliveredTime;
	BigDecimal contentType;
	String seq;

	@Override
	public LineBotResponseContent clone() {
		try {
			return (LineBotResponseContent) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
