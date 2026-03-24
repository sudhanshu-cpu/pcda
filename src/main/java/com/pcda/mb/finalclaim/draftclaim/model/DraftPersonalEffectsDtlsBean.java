package com.pcda.mb.finalclaim.draftclaim.model;

import lombok.Data;

@Data
public class DraftPersonalEffectsDtlsBean {

	private Integer type;
	private Integer seqNo;
	private String particulars;
	private Integer weight;
	private String modeOfConveyance;
	private Integer distance;
	private Double claimedAmt;
	private Double allowedAmount;
	private String personalEffectsId;

}
