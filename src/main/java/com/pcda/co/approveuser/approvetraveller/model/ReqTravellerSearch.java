package com.pcda.co.approveuser.approvetraveller.model;

import lombok.Data;

@Data
public class ReqTravellerSearch {

	private Long userId;
	private String userAlias;
	private String name;
	private String approvalState;

}
