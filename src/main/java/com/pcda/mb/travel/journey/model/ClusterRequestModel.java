package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class ClusterRequestModel {

	private String journeyDay;
	private String journeyMonth;
	private String journeyYear;
	private String sourceStnCode;
	private String trainNo;
}
