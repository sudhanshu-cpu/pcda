package com.pcda.mb.finalclaim.settledclaims.model;


import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ViewClaimLeaveDtlsBean implements Comparable<ViewClaimLeaveDtlsBean>{

	private Date leaveDate;  
    private String leaveFullHalf;
    private Integer seqNo;

    
    @Override
	public int compareTo(ViewClaimLeaveDtlsBean o) {
		
		return this.leaveDate.compareTo(getLeaveDate());
	}
    
}
