package com.pcda.mb.requestdashboard.changeboardingstationdashboard.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeBoardingStationResponseModel {
	
	private String successMsg;
	private String flag;
	private String pnrNumber;
	private String newBoardingPoint;
	private String oldBoardingPoint;
	private Date newBoardingDate;
	private String newBoardingDateStr;
	

}
