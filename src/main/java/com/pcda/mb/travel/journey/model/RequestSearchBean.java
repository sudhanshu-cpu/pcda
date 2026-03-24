package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class RequestSearchBean {

	private String originCode;
	private String dtpickerDepart;
	private String destinationCode;
	private int noOfAdult;
	private int noOfChild;
	private int noOfInfant;
	private int classType;
	private String journeyType;
	private String travelTypeId;
	private String trRule;
	private int airViaCount;

}
