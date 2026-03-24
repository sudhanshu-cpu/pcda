package com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostVoucherAckCgdaParentModel {

	private String voucherNo;
	private BigInteger loginUserId;
	
	List<PostVoucherAckCgdaChildModel> voucherAckDetails;
}
