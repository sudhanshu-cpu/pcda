package com.pcda.mb.travel.emailticket.model;

import java.util.List;

import lombok.Data;

@Data
public class DaAdvanceResponse {
	
	private String errorMessage;

	private Integer errorCode;

	private DAAdvancePDF response;

	private List<DAAdvancePDF> responseList;

}
