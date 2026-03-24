package com.pcda.co.requestdashboard.claimreqapproval.model;


import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class ApprovalTaDaLocalCon {

	
    private String vehicleNo;   
    private Integer noOfDay;
    private Date dateOftravel;
    private Integer distance;
    private String billNo; 
    private Double billAmount;
    private Double sanctionBillAmount;
    private String deductionReason;
    private Double actualBillAmount;
    private Double oneDayMaxConAmnt;
    private Integer seqNo;    
}
