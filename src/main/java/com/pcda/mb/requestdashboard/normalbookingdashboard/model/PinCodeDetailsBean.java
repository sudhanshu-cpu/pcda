package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class PinCodeDetailsBean {

	private String stateName="";
	private Set<String> postOffice=new HashSet<>();
	private Set<String> district=new HashSet<>();

}
