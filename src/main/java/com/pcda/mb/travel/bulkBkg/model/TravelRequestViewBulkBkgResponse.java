package com.pcda.mb.travel.bulkBkg.model;

import lombok.Data;

@Data
public class TravelRequestViewBulkBkgResponse {
	
	private String errorMessage;
	private Integer errorCode;
	private TravelRequestViewBulkBkgBean response;
}
