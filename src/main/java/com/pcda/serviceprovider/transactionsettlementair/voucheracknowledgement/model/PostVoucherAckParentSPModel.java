package com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model;


import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostVoucherAckParentSPModel {

	private String voucherNo;
	private BigInteger loginUserId;
	
	List<PostVoucherAckChildSPModel> voucherAckDetails;
}
