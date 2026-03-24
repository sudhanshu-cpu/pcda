package com.pcda.co.requestdashboard.claimreqapproval.model;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ApprovalClaimFoodDtlsBean {

	
    private Integer rationCertiIssue;
    private Integer noOfDay;   
    private Double billAmount;
    private Double sanctionbillAmount;
    private String deductionReason;
    private Double actualBillAmount;
    private Double oneDayMaxFoodAmnt;
    private Double oneDayRationMoney;
    
    private Integer seqNo;
    
    
}
