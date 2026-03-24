package com.pcda.co.requestdashboard.claimreqapproval.model;


import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ApprovalClaimHotelDtlsBean implements Comparable<ApprovalClaimHotelDtlsBean> {

	private String stayLocation;  
    private String messLevel;  
    private String nacTaken;  
    private String hotelName; 
    private String hotelLocation; 
    private String gstNo; 
    private Date checkInTime;  
    private Date checkOutTime;  
    private String checkInTimeStr;  
    private String checkOutTimeStr;
    private Integer noOfDay;  
    private Double oneDayMaxHotelAmnt;
    private Double billAmount; 
    private Double gstAmount;
    private Double sanctionBillAmount;
    private String deductionReason; 
    private Double actualHotelAmnt;
   
    
    private Integer seqNo;


	@Override
	public int compareTo(ApprovalClaimHotelDtlsBean o) {
		
		return o.getSeqNo()-seqNo;
	}
    
}
