package com.pcda.mb.requestdashboard.changeboardingstationdashboard.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PostBoardingStationModel {

	private String pnrNumber;
	private String bookingId;
	private String traninNumber;
	private String boardingDate;	
	private String boardingPoint;
	private String newBoardingPoint;
	private String groupId;
	private BigInteger loginUserId;

}
