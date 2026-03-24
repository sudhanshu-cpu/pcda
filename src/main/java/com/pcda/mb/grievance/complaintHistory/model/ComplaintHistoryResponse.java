package com.pcda.mb.grievance.complaintHistory.model;

import lombok.Data;

@Data
public class ComplaintHistoryResponse {

	private GrievanceMainBean response;
	private int errorCode;

	private String errorMessage;
}
