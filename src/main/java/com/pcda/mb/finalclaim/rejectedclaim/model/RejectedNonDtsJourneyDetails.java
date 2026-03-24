package com.pcda.mb.finalclaim.rejectedclaim.model;

import java.util.List;

import com.pcda.util.CommonUtil;

import lombok.Data;

@Data
public class RejectedNonDtsJourneyDetails implements Comparable<RejectedNonDtsJourneyDetails >{

	private List<RejectedPassDtlsBean> rejectedPassDtlsBeans;

	private Integer seqNo;
	private String fromPlace;
	private String toPlace;
	private String modeOfTravel;
	private String travelClass;
	private String journeyStartTime;
	private String journeyEndTime;
	private String distance;
	private String jrnyRefNo;
	private Double amount;
	private Double refundAmount;
	private Integer journeyType;
	private Integer journeyPerformed;
	private Integer cancellationGrnd = 0;
	private String cancellationDate;
	private String cancellationRmrk;
	private Double claimedAmount;
	private String sector;

	@Override
	public int compareTo(RejectedNonDtsJourneyDetails o) {
		return CommonUtil.formatString(this.journeyStartTime, "dd-MM-yyyy HH:mm").compareTo(CommonUtil.formatString(o.getJourneyStartTime(), "dd-MM-yyyy HH:mm"));
		
		
	}
}
