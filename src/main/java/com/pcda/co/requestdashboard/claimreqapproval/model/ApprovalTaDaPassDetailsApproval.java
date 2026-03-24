package com.pcda.co.requestdashboard.claimreqapproval.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApprovalTaDaPassDetailsApproval  {
	   	
	private String cancellationGrnd ;
    private String cancellationDate;
    private String cancellationRmrk;
    private String journeyPerformed;
    private Double bookingAmount;
    private Double refundAmount;
    private Double claimedAmount;
    private String ticketNo;
    private int age;
    private String relation;
    private String travellerName;
    private int passSeq;
	
}
