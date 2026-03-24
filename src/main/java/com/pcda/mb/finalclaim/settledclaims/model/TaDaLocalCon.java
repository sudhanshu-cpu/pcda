package com.pcda.mb.finalclaim.settledclaims.model;


import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class TaDaLocalCon {

	
    private String vehicleNo;   
    private Integer noOfDay;
    private Date dateOftravel;
    private Integer distance;
    private String billNo; 
    private double billAmount;
    private double sanctionBillAmount;
    private String deductionReason;
    private double actualBillAmount;
    private double oneDayMaxConAmnt;
    private Integer seqNo;    
}
