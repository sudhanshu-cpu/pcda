package com.pcda.mb.travel.exceptionalcancellation.model;

import lombok.Data;

@Data
public class PostExpCanChildModel {

	private Integer passangerNo;
	private String passangerName;

	private Integer passangerAge;
	private Integer passangerGender;

	private String trnCoach;
	private String trnSeat;
	private String trnBerth;

	private String bookingStatus;
	private String currentStatus;

	private String cancelStatus;

	private String isOfficial;
	private String isOnGovtInt;

	private String isOfflineCancellation = "YES";
	private String cancellationString;

}
