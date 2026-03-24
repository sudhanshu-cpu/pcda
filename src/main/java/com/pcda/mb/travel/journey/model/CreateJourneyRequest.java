package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class CreateJourneyRequest {

	private String groupId;
	private String unitServiceId;
	private String intTravelAllow;
	private int serviceType;
}
