package com.pcda.mb.finalclaim.rejectedclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class RejectedLeave {

	private List<RejectedLeaveDtlsBean> claimLeaveDtls;	
    private Integer count;

}
