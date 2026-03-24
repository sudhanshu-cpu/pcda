package com.pcda.mb.requestdashboard.tdr.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TDRViewModel {

	
	private Integer passangerSno;
	private String passangerName;
	private Integer passangerAge;
	private String passangerGender;
	private String trnCoach;
	private String trnSeat;
	private String trnBerth;
		
}
