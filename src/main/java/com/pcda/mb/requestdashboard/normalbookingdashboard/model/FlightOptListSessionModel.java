package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class FlightOptListSessionModel implements Serializable{
	
	private static final long serialVersionUID = 3611737092771187853L;
	
	private List<FlightSearchOption> flightOption;
}
