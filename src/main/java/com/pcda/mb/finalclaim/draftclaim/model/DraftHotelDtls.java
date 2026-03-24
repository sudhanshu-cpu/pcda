package com.pcda.mb.finalclaim.draftclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class DraftHotelDtls {

	private List<DraftHotelDtlsBean> claimHotelDtls;	
    private Integer count;

}
