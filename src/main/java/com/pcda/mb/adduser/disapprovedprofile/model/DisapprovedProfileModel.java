package com.pcda.mb.adduser.disapprovedprofile.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class DisapprovedProfileModel {

	
	private String personalNo;
	
	private String name;
	
	private BigInteger userId;
	
	private String signInId;
	
	private String office;
	private String role="TRAVELLER";
	private String remark;
	
	
	
	
}
