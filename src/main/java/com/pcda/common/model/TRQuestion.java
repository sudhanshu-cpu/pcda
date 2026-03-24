package com.pcda.common.model;

import com.pcda.util.YesOrNo;

import lombok.Data;

@Data
public class TRQuestion implements Comparable<TRQuestion> {

	private Integer sequanceNo;

	private String question;

	private String description;

	private YesOrNo answer;

	@Override
	  public int compareTo(TRQuestion q) {
		return sequanceNo - q.sequanceNo;
	  }
}
