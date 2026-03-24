package com.pcda.mb.finalclaim.draftclaim.model;

import java.util.List;

import lombok.Data;

@Data
public class DraftConyncDtls {

	private List<DraftTaDaLocalCon> taDaLocalCon;
	private Integer isConveyanceDtls;
	private Integer count;

}
