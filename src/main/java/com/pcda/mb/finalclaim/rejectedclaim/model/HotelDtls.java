package com.pcda.mb.finalclaim.rejectedclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class HotelDtls {

	private List<RejectedHotelDtlsBean> claimHotelDtls;
	private Integer count;

}
