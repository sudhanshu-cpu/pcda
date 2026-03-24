package com.pcda.mb.reports.railrequestreport.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetJourneyDetailModel implements Comparable<GetJourneyDetailModel>{

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
		public int compareTo(GetJourneyDetailModel o) {
			
			return  seqNo - o.getSeqNo();
		}
}
