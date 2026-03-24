package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class GetBLPassengerInfoModel implements Comparable<GetBLPassengerInfoModel> {
	private String ticketNumber;
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String passCategory;
	private String mobileNo;
	private String emailId;
	private int leadPassanger;
	private String invoiceNo;
	private int passangerNo;
	@Override
	public int compareTo(GetBLPassengerInfoModel o) {
		
		return passangerNo - o.getPassangerNo();
	}

}
