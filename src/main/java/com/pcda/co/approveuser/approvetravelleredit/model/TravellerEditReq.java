package com.pcda.co.approveuser.approvetravelleredit.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class TravellerEditReq {

	private BigInteger userId;
	private String userAlias;
	private String name;
	private int sequence;

}
