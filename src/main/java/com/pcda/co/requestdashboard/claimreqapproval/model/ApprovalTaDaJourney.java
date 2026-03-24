package com.pcda.co.requestdashboard.claimreqapproval.model;


import java.util.List;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApprovalTaDaJourney  {

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
	    private Double bookingAmount;
	    private Double refundAmount;
	    private String journeyPerformed;
	    private String serviceProvider;
	    private Double claimedAmount;
	    private String sector;	
	    private String journeyType;
	    private String cancellationGrnd ;
	    private String cancellationDate;
	    private String cancellationRmrk;
	    private Double sanctionAmount;
	    private String deductionReason;
	    private String bookingId;		
	    private List<ApprovalTaDaPassDetailsApproval> taDaPassDetails;	
}
