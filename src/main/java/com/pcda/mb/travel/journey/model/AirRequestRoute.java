package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class AirRequestRoute {

	private Integer seqNo;
	private String journeyMode;
	private String entitledClass;
	private String frmStation;
	private String toStation;
	private String journeyDate;
	private int startCount;


}
