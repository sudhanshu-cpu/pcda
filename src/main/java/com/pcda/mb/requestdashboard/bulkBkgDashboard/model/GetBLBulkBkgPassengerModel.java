package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import lombok.Data;

@Data
public class GetBLBulkBkgPassengerModel {
	
	private int count;
	private String firstName;
	private String email;
	private String mobileNo;
	private String lastName;
	private String dob;
	private int age;
	private String requestId;

}
