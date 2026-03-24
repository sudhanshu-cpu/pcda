package com.pcda.mb.requestdashboard.railcancellationdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetRailCanDashBkgResponse {

	private String errorMessage;
	private int errorCode;
	private List<GetParentRailCanDashBkgData> responseList;
	private GetParentRailCanDashBkgData response;
	private Boolean allCan=true;

}
