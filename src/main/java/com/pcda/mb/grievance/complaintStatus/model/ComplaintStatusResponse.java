package com.pcda.mb.grievance.complaintStatus.model;

import java.util.List;

import lombok.Data;

@Data
public class ComplaintStatusResponse {
	private int errorCode;
	private String errorMessage;
	private List<GrievanceViewBean> responseList;
	private GrievanceViewBean response;
}
