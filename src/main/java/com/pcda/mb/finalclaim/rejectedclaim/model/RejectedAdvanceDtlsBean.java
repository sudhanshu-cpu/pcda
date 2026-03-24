package com.pcda.mb.finalclaim.rejectedclaim.model;

import lombok.Data;

@Data
public class RejectedAdvanceDtlsBean {

	private String daAdvanceDate;
	private String daAdvanceLuggage;
	private String daAdvanceConveyance;
	private String daAdvanceCtg;
	private Double daAdvanceAmount = 0.0;
	private Double paoAdvanceAmount = 0.0;
	private String paoAdvanceDate;
	private Double sanPaoAdvanceAmount = 0.0;
	private String paoDeductionReason;
	private Double otherAdvncAmnt = 0.0;
	private String otherAdvncRefId;
	private String otherAdvncDate;
	private Double sanOtherAdvncAmnt = 0.0;
	private String otherDeductionReason;
	private Double mroRefundAmount = 0.0;
	private String mroRefundRefId;
	private String mroRefundDate;
	private String mroReceived;
	private Double sanMroRefundAmount = 0.0;
	private String mroDeductionReason;
	private String authorityPersonal;
	private String authorityName;
	private String authorityLevel;
	private String claimSubmitDate;
	private String delayClaimPrfrd;
	private String delayClaimAuth;
	private String delayClaimDate;
	private String ctgPercentage;
	private Double ctgAmount = 0.0;
	private String deductionReason;
	private Double sanCtgAmount = 0.0;
	private String ctgDeductionReason;
	private Integer convPartOfLugg;
	private String convPartOfLuggName;
	private String conveyanceName;
	private Double paoAdvLugg = 0.0;
	private Double paoAdvConvyance = 0.0;
	private Double paoAdvCtg = 0.0;
	private String daTransferType;

}
