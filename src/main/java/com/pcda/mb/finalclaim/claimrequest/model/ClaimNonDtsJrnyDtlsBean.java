package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ClaimNonDtsJrnyDtlsBean {

	private String nonDTSFromPlace;
	private String nonDTSToPlace;
	private String nonDTSModeOfTravel;
	private String nonDTSClassOfTravel;
	private Date nonDTSJryStartDate;
	private Date nonDTSJryEndDate;
	private Integer nonDTSDistanceKM;
	private String nonDTSTicketNo;
	private Double nonDTSJryAmount;
	private Double nonDTSJryRefundAmount;
	private Integer nonDTSJryPerformed;
	private Integer nonDTSJrnyCanType;
	private Date nonDTSJrnyCanSanDate;
	private String nonDTSJrnyCanSanNo;
	private String sector;
	private Integer journeyType;
	private Double claimedAmount;
	private List<ClaimPassDtlsBean> claimPassDtls;

}
