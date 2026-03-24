package com.pcda.pao.transactionsettlementair.voucheracknowledgement.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostVoucherAckParentModel {

	private String voucherNo;
	private BigInteger loginUserId;
	
	List<PostVoucherAckChildModel> voucherAckDetails;
}
