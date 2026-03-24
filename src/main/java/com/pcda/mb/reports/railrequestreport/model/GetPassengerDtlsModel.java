package com.pcda.mb.reports.railrequestreport.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetPassengerDtlsModel implements Comparable<GetPassengerDtlsModel> {

	private String name;
	private Integer age ;
	private String gender;
	private String relation;
	private String passengertype;
	private Integer passNo;
	@Override
	public int compareTo(GetPassengerDtlsModel o) {
		return passNo - o.getPassNo();
	}
	
	

}
