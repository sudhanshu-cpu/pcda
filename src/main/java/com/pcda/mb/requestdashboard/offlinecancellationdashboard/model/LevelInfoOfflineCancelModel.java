package com.pcda.mb.requestdashboard.offlinecancellationdashboard.model;

import lombok.Data;

@Data
public class LevelInfoOfflineCancelModel {

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
