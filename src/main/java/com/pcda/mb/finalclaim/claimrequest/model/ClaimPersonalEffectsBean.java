package com.pcda.mb.finalclaim.claimrequest.model;

import lombok.Data;

@Data
public class ClaimPersonalEffectsBean {

	private Integer type;
	private String particularName;
	private Integer particularWeight;
	private String particularConveyance;
	private Integer particularDistance;
	private Double particularClaimAmt;

}
