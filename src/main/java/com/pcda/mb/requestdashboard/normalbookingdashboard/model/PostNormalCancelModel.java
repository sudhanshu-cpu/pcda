package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class PostNormalCancelModel {

	 private String  requestId;
	 private String  cancelReqType;
	 private String  reasonForDisapprove;
	 private int paxSeqNo;
	 private String operationType;
	
	
}
