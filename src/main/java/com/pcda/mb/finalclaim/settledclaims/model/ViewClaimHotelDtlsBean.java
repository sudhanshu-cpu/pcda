package com.pcda.mb.finalclaim.settledclaims.model;


import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ViewClaimHotelDtlsBean {

	private String stayLocation;  
    private String messLevel;  
    private String nacTaken;  
    private String hotelName; 
    private String hotelLocation; 
    private String gstNo; 
    private Date checkInTime;  
    private Date checkOutTime;  
    private Integer noOfDay;  
    private double oneDayMaxHotelAmnt;
    private double billAmount; 
    private double gstAmount;
    private double sanctionBillAmount;
    private String deductionReason; 
    private double actualHotelAmnt;
   
    
    private Integer seqNo;
    
}
