package com.pcda.adg.reports.claimstatusreport.model;

import java.util.List;

import lombok.Data;


@Data
public class ClaimStatusResponse {
	private String errorMessage;
	private int errorCode;
	private ClaimStatusDataResBean response;

	private List<ClaimStatusDataResBean> responseList;
}
