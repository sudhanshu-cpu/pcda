package com.pcda.mb.finalclaim.draftclaim.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UpdateClaimNonDtsJrnyDtlsBean {

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
	private List<UpdateClaimPassDtlsBean> claimPassDtls;
	private String sector;
	private Integer journeyType;
	private Double nonDTSJryClaimedAmount;

}
