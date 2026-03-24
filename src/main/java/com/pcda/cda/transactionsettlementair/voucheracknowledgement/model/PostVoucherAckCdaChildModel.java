package com.pcda.cda.transactionsettlementair.voucheracknowledgement.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostVoucherAckCdaChildModel {

	private Integer paymentId;
	private String ackNo;
	private Date ackDate;
	private String filePath;
	
	
}
