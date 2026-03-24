package com.pcda.mb.adduser.transferin.model;

import java.math.BigInteger;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostTransferInModel {

	private BigInteger userId;
	private BigInteger loginUserId;
	@NotBlank(message = "Current Unit CanNot Blank")
	private String currentUnit;
	private String currentOfficeId;
	private String sosDateString;
	private Date sosDate;
	private String joiningDate;
	private String officename;
	private String reason;
	private String nrsDutyStn;
	private String sprNrs;
	private String sprSfa;
	private String dutyStnNa;
	private String sprNa;
	private String payAccountOffice;
	private Integer seqNo=0;

}
