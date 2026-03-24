package com.pcda.co.approveuser.approvechangepersonalno.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class GetApproveChangePnoModel {

	private BigInteger userId;
	private long seq;
	private List<String> newAttributeList;
	private List<String> oldAttributeList;
	private String oldPersonalNo;
	private String newPersonalNo;

}
