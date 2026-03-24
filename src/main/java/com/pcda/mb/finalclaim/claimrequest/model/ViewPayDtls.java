package com.pcda.mb.finalclaim.claimrequest.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ViewPayDtls {

	private Integer PaymnetMade;
	private String paymnetTo;
	private Double amount;

}
