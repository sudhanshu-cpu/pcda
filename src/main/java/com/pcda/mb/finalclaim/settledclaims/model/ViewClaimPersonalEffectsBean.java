package com.pcda.mb.finalclaim.settledclaims.model;


import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor

public class ViewClaimPersonalEffectsBean{

	private int type;
	private Integer seqNo;
	private String particulars;
	private int weight;
	private String modeOfConveyance;
	private int distance;
	private Double claimedAmt;	
	private Double allowedAmount; 
    private String deductionReason;
    private double sanClaimedAmount;

  
    
}
