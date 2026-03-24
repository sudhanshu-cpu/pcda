package com.pcda.co.requestdashboard.approvebooking.model;

import java.util.List;

import lombok.Data;

@Data
public class GetApproveRailBookingBean {
	private String travelMode;	
	private String requestId;
	private String encryptedReqId;
	private String travelId;
	private String empCode;
	private String travelTypeId;
	private String travelType;
	private String creationTime;
	private String bookingType;
	private String tatkalFlag;
	private Integer daAdvanceAvail;
	private Integer paoChanged;
	private String approvalStateStr;
	private String partyBooking;
	private Double daAdvanceAmount;
	private Double hotelAmount;
	private Double convyncAmount;
	private Double foodAmount;
	private Double ctgAmount;
	private Double goodsTransportAmount;
	private Double vehicleTransportAmnt;
	
	private List<PAOChangeBean> paoList;
	private List<RequestDetailBean> requestDeatils;


}
