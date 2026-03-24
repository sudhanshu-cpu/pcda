package com.pcda.mb.requestdashboard.offlinecancellationdashboard.model;

import lombok.Data;

@Data
public class PostsendOffCancelModel {


	private String requestId;
	private String reasonForDisapprove;
	private String cancelReqType;
	private int paxSeqNo;
	private String operationType;
}
