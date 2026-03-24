package com.pcda.mb.travel.bulkBkg.model;

import lombok.Data;

@Data
public class AirRequestBulkBkgRoute {
	
	private Integer seqNo;
	private String journeyMode;
	private String entitledClass;
	private String frmStation;
	private String toStation;
	private String journeyDate;
	private int startCount;

}
