package com.pcda.pao.RailDemand.RailIrla.model;

import lombok.Data;

@Data
public class ReqRailIRLAResponse {
	private String errorMessage;
	private Integer errorCode;
	private IRLAResponseData response;
}
