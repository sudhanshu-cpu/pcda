package com.pcda.mb.requestdashboard.exceptionalbookingdashboard.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class GetExcptnlAirRequestBean {
	private String requestForAir;
	private String airRequestId;
	private String travelId;
	private BigInteger userId;
	private String personalNo;
	private Double daAdvanceAmt;
	private Integer tripType;
	private String requestForBoth;
	private String encrptdRequestId;
	private String approvalStateStr;
	private String originStn;
	private String destStn;
	private String reqDeprtTime;
	private Date onwardJrnyDate;
	private Date returnJrnyDate;
	private String travelTypeId;
	private String bookingDataFound;
	private String onwardBooked;
	private String returnBooked;
	private String isOnwardBooked;
	private String isReturnBooked;
	private String requestType;
	private String journeyDateStr;
}
