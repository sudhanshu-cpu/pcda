package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class PostBookingPassangeDetails {
	private String name;
	private Integer age;
	private Integer gender;
	private String pxnType;
	private String berthPrefence;
	private String idCardType;
	private String idCardNumber;
	private String foodPref;
	private Boolean childBerthFlag;
}
