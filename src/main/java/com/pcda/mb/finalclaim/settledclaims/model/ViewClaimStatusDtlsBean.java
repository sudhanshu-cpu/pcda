package com.pcda.mb.finalclaim.settledclaims.model;



import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ViewClaimStatusDtlsBean{

    private BigInteger userID; 
    private String approvalState;
	private String actionDate; 
	private String remark;   
	private Integer seqNo;
}
