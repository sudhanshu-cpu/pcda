package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClaimLeaveDtlsBean {

	@NotNull(message = "Leave Date should not be blank.")
	private Date leaveDate;  
	@NotNull(message = "Full/Half should not be blank.")
    private Integer leaveFullHalf;
    private Integer seqNo;

}
