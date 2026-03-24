package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class FamilyDetailModel {

	private List<YearWiseModel> yearWise;
	private String name;
	private String relationShip;
	private int relationCode;
	private String dob;
	private String gender;
	private int genderCode;
	private int depSeqNo;
	private String ersPrintFamilyName;
	private String maritalStatus;
	private int maritalStatusCode;
	private String dOIIDate;
	private String dOIIPartNo;
	
	private String journeyCheck;
	private String reason;
	private String yearWiseFormDAndG;
	
	
}
