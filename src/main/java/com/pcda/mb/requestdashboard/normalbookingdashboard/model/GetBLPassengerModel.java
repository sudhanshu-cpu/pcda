package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class GetBLPassengerModel {

	private int count;
	private String firstName;
	private String email;
	private String mobileNo;
	private String lastName;
	private String dob;
	private int age;
}
