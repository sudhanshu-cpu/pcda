package com.pcda.mb.finalclaim.rejectedclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class FoodDtls {

	private List<RejectedFoodDtlsBean> claimFoodDtls;	
    private Integer count;

}
