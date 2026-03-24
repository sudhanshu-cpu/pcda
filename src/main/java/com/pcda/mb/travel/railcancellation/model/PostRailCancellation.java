package com.pcda.mb.travel.railcancellation.model;

import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRailCancellation {
	
	private List<PostPassengerDetails>passengerList;	
	private String cancellationString;
	private String bookingId;
	private String ticketNo;
	private String check;
	private String groupId;


	private String cancelReason  ;
	private String frmReq="";
	 private String dodBookingRefNo="";
	 private BigInteger	loginUserId	=BigInteger.ZERO;

	private List<String> modelString;

	private List<String> isOff;
	private List<String> isOnGovt;
	
	
	
	
}
