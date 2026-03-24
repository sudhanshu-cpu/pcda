package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.Date;

import lombok.Data;

@Data
public class ViewClaimLeaveDtlsBean implements Comparable<ViewClaimLeaveDtlsBean> {

	private Date leaveDate;
	private String leaveDateStr;
	private String leaveFullHalf;
	private Integer seqNo;
	
	@Override
	public int compareTo(ViewClaimLeaveDtlsBean o) {
		
		return this.leaveDate.compareTo(o.getLeaveDate());
	}

}
