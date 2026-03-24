package com.pcda.mb.finalclaim.claimrequest.model;

import java.util.List;

import lombok.Data;

@Data
public class ViewClaimConyncDtlsBean {

	private List<TaDaLocalCon> taDaLocalCon;
	private int isConveyanceDtls = 0;

}
