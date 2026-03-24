package com.pcda.mb.finalclaim.draftclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class DraftNonDtsJrny {

	private List<DraftNonDtsJourneyDetails> nonDtstaDaJourneyDetails;
	private Integer count;

}
