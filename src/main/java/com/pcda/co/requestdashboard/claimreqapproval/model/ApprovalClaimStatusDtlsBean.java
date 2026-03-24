package com.pcda.co.requestdashboard.claimreqapproval.model;



import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApprovalClaimStatusDtlsBean{

    private BigInteger userID; 
    private String approvalState;
	private String actionDate; 
	private String remark;   
	private Integer seqNo;
}
