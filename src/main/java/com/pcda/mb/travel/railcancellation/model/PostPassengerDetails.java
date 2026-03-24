package com.pcda.mb.travel.railcancellation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPassengerDetails {

	private String isOnGovtInt;

	private String isOfflineCancellation = "YES";
	private String cancellationString;
	
	private Integer passangerNo;
	private Integer isOfficial;
	private String passangerName;
	private Integer passangerAge;
	private String passangerGender;
	private String trnCoach;
	private String trnSeat;
	private String trnBerth;
	private String bookingStatus;
	private String currentStatus;
	private String cancelStatus;
	
	
	
}
