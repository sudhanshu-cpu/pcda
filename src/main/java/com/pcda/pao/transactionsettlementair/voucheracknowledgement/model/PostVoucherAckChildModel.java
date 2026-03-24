package com.pcda.pao.transactionsettlementair.voucheracknowledgement.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostVoucherAckChildModel {

	private Integer paymentId;
	private String ackNo;
	private Date ackDate;
	private String filePath;
	
	
}
