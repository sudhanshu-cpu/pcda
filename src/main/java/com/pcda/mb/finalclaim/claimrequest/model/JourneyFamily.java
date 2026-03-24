package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.List;

import lombok.Data;

@Data
public class JourneyFamily {

	private String sector;
	private List<FamilyTravellerDeatils> familyTravellerDeatils;

}
