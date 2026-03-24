package com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model;



import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostOutStandVoucherGenCgdaModel {
	private String accountOffice;
	private BigInteger loginUserId;
	private int serviceProvider;
	private List<PostOutVouchGenCgdaChildModel> voucherData;
}
