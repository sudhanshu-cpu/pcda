package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.List;

import lombok.Data;

@Data
public class EachRecord {

	List<JourneyDetails> journeyDetails;

	private String ruleName;
	private String reqAuthorityNumber;
	private String authorityDate;
	private String ltcBlockYear;

}
