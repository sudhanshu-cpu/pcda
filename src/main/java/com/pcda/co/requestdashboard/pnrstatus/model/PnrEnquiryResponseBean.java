package com.pcda.co.requestdashboard.pnrstatus.model;

import java.util.List;

import lombok.Data;

@Data
public class PnrEnquiryResponseBean {

	private String trainNo;
	private String trainName;
	private String from;
	private String to;
	private String boardingPoint;
	private String reservationUpto;
	private String journeyDate;
	private String classCode;
	private String chartingStatus;
	private List<PnrEnquiryPassDetailsBean> passDetails;

	private String errorMessage;

}
