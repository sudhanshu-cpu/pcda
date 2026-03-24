package com.pcda.mb.reports.railrequestreport.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GetReqQuestionModel implements Comparable<GetReqQuestionModel> {
	private Integer seqNo;
	private String question ;
	private String answer;
	
	@Override
	public int compareTo(GetReqQuestionModel o) {
		
		return seqNo-o.getSeqNo();
	}

}
