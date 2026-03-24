package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class AirRequestQuestionData implements Comparable<AirRequestQuestionData> {

	private int seqNo;
	private String question;
	private String answer;
	
	@Override
	public int compareTo(AirRequestQuestionData obj) {
		return seqNo-obj.seqNo;
	}
}
