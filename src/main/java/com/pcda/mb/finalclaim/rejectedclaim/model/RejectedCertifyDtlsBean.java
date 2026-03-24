package com.pcda.mb.finalclaim.rejectedclaim.model;

import lombok.Data;

@Data
public class RejectedCertifyDtlsBean implements Comparable<RejectedCertifyDtlsBean> {

	private String certifyQuestion;
	private Integer seqNo;
	@Override
	public int compareTo(RejectedCertifyDtlsBean o) {
		
		return this.seqNo - o.getSeqNo();
	}

}
