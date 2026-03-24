package com.pcda.mb.finalclaim.draftclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class DraftLeave {

	private List<DraftLeaveDtlsBean> claimLeaveDtls;
	private Integer count;

}
