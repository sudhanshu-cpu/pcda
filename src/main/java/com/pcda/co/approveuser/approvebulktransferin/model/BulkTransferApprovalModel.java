package com.pcda.co.approveuser.approvebulktransferin.model;

import java.util.Date;

import lombok.Data;
@Data
public class BulkTransferApprovalModel {
	
	private String transferId;
	private Date transferAuthorityDate;
	private String transferAuthorityDateFormat;
	private String railDutyNrs;
	private String dutyStnNap;
	private String transferAuthority;

}
