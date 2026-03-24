package com.pcda.mb.finalclaim.rejectedclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class ConyncDtls {

	private List<RejectedTaDaLocalCon> taDaLocalCon;
	private Integer isConveyanceDtls = 0;
	private Integer count = 0;

}
