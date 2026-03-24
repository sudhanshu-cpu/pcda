package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClaimAdvanceDtlsBean {

	private String ctgPercentage;
	private Double ctgAmount = 0.0;
	private Integer convPartOfLugg = 0;
	private String conveyanceName;
	private Double paoAdvLugg = 0.0;
	private Double paoAdvConvyance = 0.0;
	private Double paoAdvCtg = 0.0;

	@NotNull(message = "Advance Drawn from PAO should not be null")
	private Double advFromPAO = 0.0;
	private Date advFromPAODate;
	@NotNull(message = "Advance Drawn from Other Sources should not be null")
	private Double advFromOther = 0.0;
	private String advFromOtherVoucherNo;
	private Date advFromOtherDate;
	@NotNull(message = "eMRO/MRO/Refund Amount should not be null")
	private Double mroRefund = 0.0;
	private String mroNumber;
	private Date mroDate;

	private String counterPersonalNo;
	private String counterPersonalName;
	private String counterPersonalLevel;
	@NotNull(message = "Date of Claim preferred by the Individual to the Office should not be blank.")
	private Date claimPreferredDate;
	@NotBlank(message = "If Date of Claim Preferred is more than 60 days from last date of Journey then whether time barred sanction taken is not selected")
	private String claimPreferredSanction;
	private String claimPreferredSanctionNo;
	private Date claimPreferredSanctionDate;

}
