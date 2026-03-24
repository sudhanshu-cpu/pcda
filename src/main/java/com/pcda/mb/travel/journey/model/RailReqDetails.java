package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class RailReqDetails {

	private String fromStation;
	private String toStation;
	private String journeyDate;
	private String journeyType;
	private String jrnyClass;
	private String viaRelRoute;
	
	
}
