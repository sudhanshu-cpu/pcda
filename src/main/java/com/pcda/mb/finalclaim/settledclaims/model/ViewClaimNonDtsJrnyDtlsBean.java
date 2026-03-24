package com.pcda.mb.finalclaim.settledclaims.model;


import java.util.Date;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ViewClaimNonDtsJrnyDtlsBean {

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
	private Set<ViewClaimPassDtlsBean> claimPassDtls;
	private String sector; 
	private Integer journeyType;
   
   /* 
	private String airlineType;
    private String otherAirlineType;
    private String authorityNo;
    private Date authorityDate;   
    private Double sanctionAmount;
    private String deductionReason;
    private Integer paymentMade;
    private String paymentTO;
   
    private Double claimedAmount;
   */
    
   
    
}
