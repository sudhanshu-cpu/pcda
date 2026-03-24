package com.pcda.co.approveuser.approvebulktransferin.model;

import java.util.List;

import lombok.Data;

@Data
public class BulkTransferUserViewResponse {
	
	private String errorMessage;
	private Integer errorCode;
	private BulkTransferUserViewBean response;
	private List<BulkTransferUserViewBean> responseList;

}
