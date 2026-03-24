package com.pcda.mb.reports.daadvancereport.model;

import lombok.Data;

@Data
public class DaAdvanceInputModel {

	private String unitOffice = "";
	private String requestID = "";
	private String personalNo = "";
	private Integer travelMode;
	private Integer advanceType;
	
}
