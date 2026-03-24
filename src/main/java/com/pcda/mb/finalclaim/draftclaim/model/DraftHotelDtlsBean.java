package com.pcda.mb.finalclaim.draftclaim.model;

import lombok.Data;

@Data
public class DraftHotelDtlsBean {

	private String stayLocation;  
    private String messLevel;  
    private String nacTaken;  
    private String hotelName; 
    private String hotelLocation; 
    private String gstNo; 
    private String checkInTime;  
    private String checkOutTime;  
    private Integer noOfDay;  
    private Double oneDayMaxHotelAmnt;
    private Double billAmount; 
    private Double gstAmount;
    private Double sanctionBillAmount;
    private String deductionReason; 
    private Double actualHotelAmnt;
    private Integer seqNo;

}
