package com.pcda.mb.reports.airrequestreport.model;

import lombok.Data;

@Data
public class AirReqIPassengerModel implements Comparable<AirReqIPassengerModel> {

	private String name;
	private Integer age ;
	private String gender;
	private String relation;
	private String passengertype;
	private String passportNo;
	private String passportExpiryDate;
	private Integer passNo;
	@Override
	public int compareTo(AirReqIPassengerModel o) {
		
		return passNo - o.getPassNo();
	}

}
