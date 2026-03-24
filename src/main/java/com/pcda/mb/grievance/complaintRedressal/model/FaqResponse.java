package com.pcda.mb.grievance.complaintRedressal.model;

import java.util.List;

import lombok.Data;

@Data
public class FaqResponse {
	
	private List<GrievanceFAQViewBean> responseList;

	private int errorCode;

	private String errorMessage;
}
