package com.pcda.pao.vouchersettlement.model;

import java.util.List;

import lombok.Data;

@Data
public class GetSaveResponseParentModel {

	private String message;
	private String settlementDone;
	private String voucherNo;
	private String status;
	private Double amountPaid;
	private String paymentDate;
	private String ackStatus;
	private String paymentRemark;
	private List<GetSaveResponseChildModel> voucherPayment;

}
