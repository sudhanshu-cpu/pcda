package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class GetConfirmResponseParent {

	private String irctcBookingUrl;
	private Map<String, String> requestParam=new HashMap<>();

}
