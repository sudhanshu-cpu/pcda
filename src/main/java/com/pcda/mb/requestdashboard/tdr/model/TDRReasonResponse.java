package com.pcda.mb.requestdashboard.tdr.model;

import java.util.List;

import lombok.Data;

@Data
public class TDRReasonResponse {

	private int errorCode;
	private List<TDRReasonModel> responseList;

}
