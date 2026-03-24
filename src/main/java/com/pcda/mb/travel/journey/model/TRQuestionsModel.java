package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class TRQuestionsModel implements Comparable<TRQuestionsModel> {

	private String question;
	private String questionDesc;
	private String answer;
	private Integer sequanceNo;
	
	@Override
	  public int compareTo(TRQuestionsModel q) {
		return sequanceNo - q.sequanceNo;
	  }
	
	
}
