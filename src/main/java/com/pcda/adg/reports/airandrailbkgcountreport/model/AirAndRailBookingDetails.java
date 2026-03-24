package com.pcda.adg.reports.airandrailbkgcountreport.model;

import lombok.Data;

@Data
public class AirAndRailBookingDetails {

	private String travelType;
	private Integer railCount=0;
	private Integer airCount=0;
}
