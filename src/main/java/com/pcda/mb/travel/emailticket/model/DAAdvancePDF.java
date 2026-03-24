package com.pcda.mb.travel.emailticket.model;

import lombok.Data;

@Data
public class DAAdvancePDF {

	private Double requestedDA;
	private Double paidDA;
	private String advanceSeq;
	private String dwnDate;
	
	private int journeyType;
	
	private String travelId;

}
