package com.pcda.co.approveuser.approvebulktransferin.model;

import java.math.BigInteger;

import lombok.Data;
@Data
public class PostBulkTransferInApprovalModel {
	
	private String transferId;
	private String groupId;
	private String remark;
	private BigInteger loginUserId;
	private String event;
	private String loginUserServiceId;

}
