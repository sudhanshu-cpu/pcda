package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import java.util.List;

import lombok.Data;
@Data
public class GetBLBulkBkgConfirmBookResponse {
	
	private String errorMessage;
	private int errorCode;
	private List<GetBLBulkBkgBookInfoModel> responseList;
	private GetBLBulkBkgBookInfoModel response;

}
