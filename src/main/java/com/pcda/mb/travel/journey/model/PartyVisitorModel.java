package com.pcda.mb.travel.journey.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PartyVisitorModel {

	private String message;
	private boolean checkRtrmntAge;
	private BigInteger userID;
	private String service;
	private String serviceId;
	private String category;
	private String categoryId;
	private String name;
	private String dateOfBirth;
	private String ersPrintName;
	private String gender;
	private String rankName;
	private String rankId;
	private int highestEntitledClass;
}
