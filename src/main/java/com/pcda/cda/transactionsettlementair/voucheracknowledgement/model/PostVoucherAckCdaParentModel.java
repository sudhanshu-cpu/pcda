package com.pcda.cda.transactionsettlementair.voucheracknowledgement.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostVoucherAckCdaParentModel {

	private String voucherNo;
	private BigInteger loginUserId;
	
	List<PostVoucherAckCdaChildModel> voucherAckDetails;
}
