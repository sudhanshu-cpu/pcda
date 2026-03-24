package com.pcda.sao.airdemand.airdemandrequest.model;

import lombok.Data;

@Data
public class SaveDemandModel {

	private Boolean flag;
	private String serviceId;
	private String groupId;
	private Integer loginUserId;
	
	private String toDate;
	private String fromDate;
	
	
	
}
