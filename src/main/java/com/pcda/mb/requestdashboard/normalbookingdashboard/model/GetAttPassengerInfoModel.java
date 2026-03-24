package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class GetAttPassengerInfoModel implements Comparable<GetAttPassengerInfoModel> {
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
	public int compareTo(GetAttPassengerInfoModel o) {

		return  passangerNo-o.getPassangerNo();
	}
	
	

}
