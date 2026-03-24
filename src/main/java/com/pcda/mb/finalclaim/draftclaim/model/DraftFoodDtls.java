package com.pcda.mb.finalclaim.draftclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class DraftFoodDtls {

	private List<DraftFoodDtlsBean> claimFoodDtls;
	private Integer count;

}
