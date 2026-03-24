package com.pcda.co.approveuser.approvebulktransferinreemployment.model;

import java.util.List;

import com.pcda.co.approveuser.approvebulktransferin.model.BulkTransferUserViewBean;

import lombok.Data;
@Data
public class BulkTransferInReEmpUserViewResponse {
	
	private String errorMessage;
	private Integer errorCode;
	private BulkTransferInReEmpViewBean response;
	private List<BulkTransferInReEmpViewBean> responseList;

}
