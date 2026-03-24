package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetATTBulkBkgResponse {
	
	private String errorMessage;
	private int errorCode;
	private List<GetATTBulkBkgPassBookModel> responseList;
	private GetATTBulkBkgPassBookModel response;

}
