package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class Trainbtwnstnslist {


	private String trainNo;
	private String trainName;
	private String originStnCode;
	private String originStnName;
	private String destStnCode;
	private String destStnName;
	private String depTimeSrc;
	private String arrTimeDest;
	private String daysOfRun;
	private String tempTrn;
	private List<String> trainType;
	private String trainJourneyClass;
	private List<String> runningDays;


}
