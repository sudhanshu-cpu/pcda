package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PostConfirmBLAirBulkBkgModel {

	private String bulkBkgId;
	private String flightKey;
	private String flightNumber;
	private String carrier;
	private String isValidatedCase;
	private String validatedReason;
	private String sessionId;
	private List<AdultBulkBkg> adultList = new ArrayList<>();
	private BigInteger loginUserId;
	private int baggageWeight;
	private int leadIndex;
	private double totalFlightFare;

}
