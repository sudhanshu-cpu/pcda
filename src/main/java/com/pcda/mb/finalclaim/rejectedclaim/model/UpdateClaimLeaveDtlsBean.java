package com.pcda.mb.finalclaim.rejectedclaim.model;

import java.util.Date;

import lombok.Data;

@Data
public class UpdateClaimLeaveDtlsBean {

	private Date leaveDate;  
    private Integer leaveFullHalf;
    private Integer seqNo;

}
