package com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.model;



import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostOutStandVoucherGenCdaModel {
	private String accountOffice;
	private BigInteger loginUserId;
	private int serviceProvider;
	private List<PostOutVouchGenCdaChildModel> voucherData;
}
