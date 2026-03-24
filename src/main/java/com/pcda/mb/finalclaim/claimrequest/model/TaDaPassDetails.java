package com.pcda.mb.finalclaim.claimrequest.model;

import lombok.Data;

@Data
public class TaDaPassDetails  {
	   	
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
