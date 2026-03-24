package com.pcda.mb.finalclaim.rejectedclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class NonDtsJrny {

	private List<RejectedNonDtsJourneyDetails> nonDtstaDaJourneyDetails;
	private Integer count;

}
