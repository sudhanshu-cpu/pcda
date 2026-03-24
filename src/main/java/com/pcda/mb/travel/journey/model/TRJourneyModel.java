package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class TRJourneyModel {

	private String fromStnOnward;
	private String toStnOnward;
	private String fromStnReturn;
	private String toStnReturn;
}
