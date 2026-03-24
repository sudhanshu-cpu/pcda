package com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model;


import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAckSearchParentSPModel {

	private String voucherNo;
	private String showHistory;
	private String creationTime;
	private String voucherStatus;
	private Double totalAmount;
	private Double balanceTotalAmount;
	private String balanceAvailable;
	private String settlementFound;
	private List<GetAckSearchChildSPModel> voucherAckPayment;

}
