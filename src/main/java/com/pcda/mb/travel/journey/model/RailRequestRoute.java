package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class RailRequestRoute {

	private Integer seqNo;
	private String journeyMode;
	private String entitledClass;
	private String frmStation;
	private String toStation;
	private String journeyDate;

}
