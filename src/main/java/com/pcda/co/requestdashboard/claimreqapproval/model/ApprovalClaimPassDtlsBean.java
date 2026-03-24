package com.pcda.co.requestdashboard.claimreqapproval.model;


import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor

public class ApprovalClaimPassDtlsBean{

	
 
   // private Integer jrnySeqNo;   
   // private Integer dtsNonDtsType;   
   // private Integer passSeq;      
   	private String travellerName; 	
 	private String relation;	
 	private Integer age;	
 	private String ticketNo;	
 	private Double bookingAmount;
 	private Double refundAmount;
 	private Double claimedAmount;
    private Integer jrnyPerfmd;	
 	private Integer cancellationGrnd;
 	private Date cancellationDate;
    private String cancellationRmrk;
    private Integer currentStatus;
  
    
}
