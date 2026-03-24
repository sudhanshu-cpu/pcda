package com.pcda.mb.finalclaim.rejectedclaim.model;

import com.pcda.util.CommonUtil;

import lombok.Data;

@Data
public class RejectedHotelDtlsBean implements Comparable<RejectedHotelDtlsBean>{

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

	@Override
	public int compareTo(RejectedHotelDtlsBean o) {
		return CommonUtil.formatString(this.checkInTime, "dd-MM-yyyy HH:mm").compareTo(CommonUtil.formatString(o.getCheckInTime(), "dd-MM-yyyy HH:mm"));
		
	}

}
