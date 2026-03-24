package com.pcda.cda.transactionsettlementair.vouchersettlement.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetSaveResponseCdaParentModel {

	private String message;
	private String settlementDone;
	private String voucherNo;
	private String status;
	private Double amountPaid;
	private String paymentDate;
	private String ackStatus;
	private String paymentRemark;
	private List<GetSaveResponseCdaChildModel> voucherPayment;

}
