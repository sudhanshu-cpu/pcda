package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostConfirmAttAirBook {

	private String requestId;
	private String flightKey;
	private String isValidatedCase;
	private String validatedReason;
	private String sessionId;
	private String leadMobile;
	private String leadEmail;
    private BigInteger loginUserId;
	private int baggageWeight;
	private int leadIndex;
	private double totalFlightFare;
	private List<FlightSearchOption> flightOption;
}
