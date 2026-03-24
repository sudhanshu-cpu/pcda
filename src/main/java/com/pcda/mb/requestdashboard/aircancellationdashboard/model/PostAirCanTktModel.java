package com.pcda.mb.requestdashboard.aircancellationdashboard.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PostAirCanTktModel {
	private BigInteger loginUserId;
	
	private  String bookingId;
	private  String intendedCanFlag;
	private  String intendedCanMsg;
	private  String serviceProvider;
	private int serPov;
	
}
