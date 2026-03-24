package com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model;


import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostVoucherAckChildSPModel {

	private Integer paymentId;
	private String ackNo;
	private Date ackDate;
	private String filePath;
	
	
}
