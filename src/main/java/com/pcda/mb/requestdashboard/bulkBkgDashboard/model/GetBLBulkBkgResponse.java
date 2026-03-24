package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import java.util.List;

import lombok.Data;
@Data
public class GetBLBulkBkgResponse {
	
	private String errorMessage;
	private int errorCode;
	private List<GetBLBulkBkgPassbookModel> responseList;
	private GetBLBulkBkgPassbookModel response;

}
