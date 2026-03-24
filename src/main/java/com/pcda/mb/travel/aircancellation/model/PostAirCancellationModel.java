package com.pcda.mb.travel.aircancellation.model;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class PostAirCancellationModel {

	private String cancelReason;
	
	private String previousCanReason;
	
	private String isRoundTrip;
	
	private Integer totalPassanger;
	
	private String bookingId;
	
	private List<String> onwardCheckList;
	
	private List<String> returnCheckList;
	
	private Map<String, Integer>onwardCheck;
	
	private Map<String, Integer>returnCheck;
	
	
	
	
}

