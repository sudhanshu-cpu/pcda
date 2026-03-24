package com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostVoucherAckCgdaChildModel {

	private Integer paymentId;
	private String ackNo;
	private Date ackDate;
	private String filePath;
	
	
}
