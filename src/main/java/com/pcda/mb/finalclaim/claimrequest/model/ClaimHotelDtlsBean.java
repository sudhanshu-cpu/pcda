package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.Date;

import lombok.Data;

@Data
public class ClaimHotelDtlsBean {

	private String hotelOrMess;
	private String messLevel;
	private String hotelNACTaken;
	private String hotelName;
	private String hotelCity;
	private String hotelGSTNo;
	private Date checkInDate;
	private Date checkOutDate;
	private Integer hotelNoDays;
	private Double hotelAmount;
	private Double hotelGSTAmount;
	private Double actualHotelAmount;
	private Double hotelDailyAmount;

	private Integer seqNo;

}
