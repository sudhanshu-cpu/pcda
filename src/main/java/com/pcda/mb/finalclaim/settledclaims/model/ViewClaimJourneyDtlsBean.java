package com.pcda.mb.finalclaim.settledclaims.model;


import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ViewClaimJourneyDtlsBean implements Comparable<ViewClaimJourneyDtlsBean> {

	
	    private Integer seqNo;
	    private String bkgFrom ;
	    private String fromStation;
	    private String toStation;
	    private String travelMode;
	    private String journeyClass;
	    private Date journeyStartDate;
	    private Date journeyEndDate;
	    private String distance;	
	    private String pnrOrAirTxnd;
	    private Double bookingAmount;
	    private Double refundAmount;
	    private String journeyPerformed;
	    private String serviceProvider;
	    private Double claimedAmount;
	    private String sector;	
	    private String journeyType;
	    private String cancellationGrnd ;
	    private Date cancellationDate;
	    private String cancellationRmrk;
	    private Double sanctionAmount;
	    private String deductionReason;
	    private String bookingId;
		
	    @Override
		
	    public int compareTo(ViewClaimJourneyDtlsBean jrnyDetails) {
			return this.journeyStartDate.compareTo(jrnyDetails.journeyStartDate);
		}
	
    
}
