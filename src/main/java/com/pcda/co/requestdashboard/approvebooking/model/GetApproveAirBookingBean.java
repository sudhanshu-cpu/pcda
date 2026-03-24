package com.pcda.co.requestdashboard.approvebooking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetApproveAirBookingBean {

	private String travelMode;	
	private String requestId;
	private String bulkBookingId;
	private String encryptedReqId;
	private String requestMode;
	private String travelId;
	private String empCode;
	private String travelTypeId;	
	private String travelType;
	private String creationTime;
	private String bookingType;
	private Integer daAdvanceAvail;
	private String approvalStateStr;
	private Double daAdvanceAmount;
	private Double hotelAmount;
	private Double convyncAmount;
	private Double foodAmount;
	private Double ctgAmount;
	private Double goodsTransportAmount;
	private Double vehicleTransportAmnt;
    private String cabinClass;
    private String tripType;
    private String ltcSplMesg;
    private RequestDetailBean requestDeatils;
	
	

}
