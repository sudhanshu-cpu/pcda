package com.pcda.mb.requestdashboard.pnrstatus.model;

import lombok.Data;

@Data
public class PnrEnquiryResponse {

	private Integer errorCode;
	private String errorMessage;
	private PnrEnquiryResponseBean response;

}
