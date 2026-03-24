package com.pcda.mb.adduser.edittravelerprofile.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelInfo {

	private String levelId;
	private String levelName;
	private String rankId;
	private String rankName;
	private Integer retireAge;

	@Override
	public String toString() {
		return "{\"levelId\":\"" + levelId + "\", \"levelName\":\"" + levelName + "\", \"rankId\":\"" + rankId
				+ "\", \"rankName\":\"" + rankName + "\", \"retireAge\":" + retireAge + "}";
	}

}
