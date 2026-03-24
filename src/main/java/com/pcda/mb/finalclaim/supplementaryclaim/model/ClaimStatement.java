package com.pcda.mb.finalclaim.supplementaryclaim.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class ClaimStatement implements Comparable<ClaimStatement> {

	private String value;
	private BigInteger id;	

	@Override
	public int compareTo(ClaimStatement o) {
		
		return this.id.compareTo(o.getId());
	}	

}
