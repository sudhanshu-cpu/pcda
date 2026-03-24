package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClaimJourneyDtlsBean {

	private String bookingId;
	private String requestId;
	private String fromStation;
	private String toStation;
	private Integer travelMode;
	private String journeyClass;
	private Integer journeyType;
	@NotNull(message = "DTS Journey Start Date should not be blank.")
	private Date journeyStartDate;
	@NotNull(message = "DTS Journey End Date should not be blank.")
	private Date journeyEndDate;
	private String bookingNo;
	private Double bookingAmount;
	private Double refundAmount;
	private String sector;
	@NotNull(message = "DTS Journey Performed should not be blank.")
	private Integer journeyPerformed = 0;
	private Integer jrnyCanType = 0;
	private Date jrnyCanSanDate;
	private String jrnyCanSanNo;
	private Double claimedAmount;
	private List<ClaimPassDtlsBean> claimPassDtls;

}
