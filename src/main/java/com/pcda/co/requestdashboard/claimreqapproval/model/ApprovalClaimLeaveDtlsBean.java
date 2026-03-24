package com.pcda.co.requestdashboard.claimreqapproval.model;


import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ApprovalClaimLeaveDtlsBean implements Comparable<ApprovalClaimLeaveDtlsBean> {

	private Date leaveDate;  
	private String leaveDateStr;
    private String leaveFullHalf;
    private Integer seqNo;
	@Override
	public int compareTo(ApprovalClaimLeaveDtlsBean o) {
	    return this.leaveDate.compareTo(o.getLeaveDate());
	}
    
}
