package com.pcda.co.requestdashboard.approvebooking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDetailBean {
	private Integer sequanceNo;
	private String originStation;
	private String approvalState;
	private String destinationStation;
	private String journeyDate;
	private String originStationName;
	private String destinationStationName;

}
