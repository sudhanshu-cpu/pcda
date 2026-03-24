package com.pcda.mb.finalclaim.rejectedclaim.model;

import lombok.Data;

@Data
public class UpdateClaimPersonalEffectsBean {

	private Integer type;
	private String particularName;
	private Integer particularWeight;
	private String particularConveyance;
	private Integer particularDistance;
	private Double particularClaimAmt;	  

}
