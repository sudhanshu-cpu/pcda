package com.pcda.mb.requestdashboard.tdr.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostParenttdrModel {

	private BigInteger loginUserId;
	private String bookingId;
	private String tdrReason;
	private int totalNoOfPsnger;
	
	private String isTdrFile;
	private int isTdrOfficial;
	private int isTdrGuardSlip;
	private String isFulTdrFiled="YES";
	
	private double tdrGuardSlipAmount;
	private String tdrGuardSlipNo="";
	private String tdrGuardSlipDate="";
	private String tdrReasonIndex;
	
	private List<PostChildTdrModel> tdrFileList;

}

