package com.pcda.sao.airdemand.airdemandrequest.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class DemandDownloadPostModel {

	
	private Boolean flag ;
	private String serviceId;
	private String groupId;
	private BigInteger loginUserId;
	private String toDate;
	private String fromDate;
	

}
