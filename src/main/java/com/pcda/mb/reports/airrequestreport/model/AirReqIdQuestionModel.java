package com.pcda.mb.reports.airrequestreport.model;

import lombok.Data;

@Data
public class AirReqIdQuestionModel implements Comparable<AirReqIdQuestionModel>{
	private Integer seqNo;
	private String question ;
	private String answer;
	
	@Override
	public int compareTo(AirReqIdQuestionModel o) {
	    return seqNo - o.getSeqNo();
	}
		

}
