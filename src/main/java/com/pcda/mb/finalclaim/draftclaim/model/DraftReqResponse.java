package com.pcda.mb.finalclaim.draftclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class DraftReqResponse {

	private int errorCode;
	private String errorMessage;
	private List<DraftReqBean> responseList;

}
