package com.pcda.mb.travel.bulkBkg.model;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;
@Data
public class TravellerDetailsBulkBkgModel {
	
	private String message;
	private BigInteger userID;
	private String travlerName;
	private String serviceName;
	private String serviceId;
	private String categoryId;
	private String categoryName;
	private String levelId;
	private String levelName;
	private String railPAOId;
	private String airPAOId;
	private String office;
	private String airOffice;
	private String cdaoAccNo;
	
	private Map<String, String> travelType=new LinkedHashMap<>();

}
