package com.pcda.mb.finalclaim.claimrequest.model;

import lombok.Data;

@Data
public class ViewClaimCertifyDtlsBean implements Comparable<ViewClaimCertifyDtlsBean> {

	private String certifyQuestion;

	private Integer seqNo;

	@Override
	public int compareTo(ViewClaimCertifyDtlsBean o) {
		
	 return this.seqNo - o.getSeqNo();
	}

}
