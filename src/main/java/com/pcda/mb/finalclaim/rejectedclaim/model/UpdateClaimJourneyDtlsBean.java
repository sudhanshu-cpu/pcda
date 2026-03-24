package com.pcda.mb.finalclaim.rejectedclaim.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UpdateClaimJourneyDtlsBean {

	private String bookingId;
    private String requestId;
    private String fromStation;
    private String toStation;
    private Integer travelMode;
    private String journeyClass;
    private Integer journeyType;
    private Date journeyStartDate;
    private Date journeyEndDate;
    private String bookingNo;
    private Double bookingAmount;
    private Double refundAmount;
    private String sector;	
	private List<UpdateClaimPassDtlsBean> claimPassDtls;
	private Integer journeyPerformed;
    private Integer jrnyCanType =0;
    private Date jrnyCanSanDate;
    private String jrnyCanSanNo;
    private Double claimedAmount;

}
