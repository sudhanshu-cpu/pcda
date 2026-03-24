package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.List;

import lombok.Data;

@Data
public class Travellers {

	private String name;
	private Integer age;
	private String relation;
	List<TravellerName> travellerName;

}
