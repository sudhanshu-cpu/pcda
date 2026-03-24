package com.pcda.co.approveuser.unitmovementapproval.model;

import java.util.List;

import lombok.Data;

@Data
public class UnitUserResp {

	private List<String> responseList;
	private int errorCode;

}
