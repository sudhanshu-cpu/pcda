package com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class PostOutStandVoucherGenModel {
	private String accountOffice;
	private BigInteger loginUserId;
	private int serviceProvider;
	private List<PostOutVouchGenChildModel> voucherData;
}
