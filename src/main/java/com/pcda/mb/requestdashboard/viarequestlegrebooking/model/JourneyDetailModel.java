package com.pcda.mb.requestdashboard.viarequestlegrebooking.model;

import lombok.Data;

@Data
public class JourneyDetailModel implements Comparable<JourneyDetailModel> {

	
	  private int seqNo;
	 private String travelerName;
	  private String fromStationCode;
	  private String toStationCode;	  
	  private String fromStationName;
	  private String toStationName;
	  private String boardingDate;
	  private String bookingStatus;	  
	  private String viaRelax;
	  private String journeyClass;
	  private String relation;
	@Override
	public int compareTo(JourneyDetailModel o) {
		
		return  seqNo - o.getSeqNo();
	}
}
