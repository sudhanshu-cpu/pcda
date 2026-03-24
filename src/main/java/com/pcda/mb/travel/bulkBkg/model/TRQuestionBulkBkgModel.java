package com.pcda.mb.travel.bulkBkg.model;

import lombok.Data;

@Data
public class TRQuestionBulkBkgModel implements Comparable<TRQuestionBulkBkgModel> {
	private String  question;
	private String questionDesc;
	private String answer;
	private Integer sequanceNo;
	
	@Override
	  public int compareTo(TRQuestionBulkBkgModel q) {
		return sequanceNo - q.sequanceNo;
	  }
}
