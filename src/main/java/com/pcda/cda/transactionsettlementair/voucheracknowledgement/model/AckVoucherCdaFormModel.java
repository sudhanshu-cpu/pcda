package com.pcda.cda.transactionsettlementair.voucheracknowledgement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AckVoucherCdaFormModel {

	private String fromDate="";

	private String toDate="";

	private String fromTxnDate="";

	private String toTxnDate="";

	private String invoiceNo="";

	private String voucherNo="";

	private String personalNo="";

	private String travelType="";

	private String accountOffice="";

	private Integer statusType;

	private Integer serviceProvider;

}
