package com.pcda.mb.finalclaim.draftclaim.model;

import java.util.Date;

import lombok.Data;

@Data
public class UpdateClaimAdvanceDtlsBean {

	private String ctgPercentage;
	private Double ctgAmount = 0.0;
	private Integer convPartOfLugg;
	private String conveyanceName;
	private Double paoAdvLugg = 0.0;
	private Double paoAdvConvyance = 0.0;
	private Double paoAdvCtg = 0.0;

	private Double advFromPAO = 0.0;
	private Date advFromPAODate;
	private Double advFromOther = 0.0;
	private String advFromOtherVoucherNo;
	private Date advFromOtherDate;
	private Double mroRefund = 0.0;
	private String mroNumber;
	private Date mroDate;
	private String counterPersonalNo;
	private String counterPersonalName;
	private String counterPersonalLevel;
	private Date claimPreferredDate;
	private String claimPreferredSanction;
	private String claimPreferredSanctionNo;
	private Date claimPreferredSanctionDate;

}
