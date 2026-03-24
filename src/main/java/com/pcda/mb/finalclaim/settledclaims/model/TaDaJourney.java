package com.pcda.mb.finalclaim.settledclaims.model;


import java.util.List;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaDaJourney  {

	    private Integer seqNo;
	    private Integer tableSeqNo;
	    private String bkgFrom ;
	    private String fromStation;
	    private String toStation;
	    private String travelMode;
	    private String journeyClass;
	    private String journeyDate;
	    private String journeyEndDate;
	    private String distance;	
	    private String pnrOrAirTxnd;
	    private double bookingAmount;
	    private double refundAmount;
	    private String journeyPerformed;
	    private String serviceProvider;
	    private double claimedAmount;
	    private String sector;	
	    private String journeyType;
	    private String cancellationGrnd ;
	    private String cancellationDate;
	    private String cancellationRmrk;
	    private double sanctionAmount;
	    private String deductionReason;
	    private String bookingId;		
	    private List<TaDaPassDetails> taDaPassDetails;	
}
