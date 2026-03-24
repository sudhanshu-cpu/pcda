package com.pcda.mb.finalclaim.rejectedclaim.model;

import java.util.Date;

import lombok.Data;

@Data
public class RejectedLeaveDtlsBean implements Comparable<RejectedLeaveDtlsBean> {

	private Date leaveDate;
	private String leaveDateStr;
	private Integer leaveFullHalf;
	private Integer seqNo;
	@Override
	public int compareTo(RejectedLeaveDtlsBean o) {
		
		return this.leaveDateStr.compareTo(o.getLeaveDateStr());
	}

}
