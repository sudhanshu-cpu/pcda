package com.pcda.mb.adduser.transferout.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class PostTransferOutModel {

	private BigInteger userId;
	private BigInteger loginUserId;
	
	private String currentUnit;
	private String currentOfficeId;
	
	private String unitNameToTransfer;
	private String sosDateString;
	private Date sosDate;
	private String joiningDate;
	
	private String reason;
	
	private String nrsDutyStn;

	private String sprNrs;

	private String sprSfa;
	
	private String dutyStnNa;
	private String sprNa;
	private String payAccountOffice;

}
