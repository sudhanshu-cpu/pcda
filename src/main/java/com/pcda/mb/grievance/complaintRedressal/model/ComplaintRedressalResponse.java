package com.pcda.mb.grievance.complaintRedressal.model;

import java.util.List;

import lombok.Data;

@Data
public class ComplaintRedressalResponse {
	private String errorMessage;
    private int errorCode;
    private List<String> responseList;
    private String response;
}
