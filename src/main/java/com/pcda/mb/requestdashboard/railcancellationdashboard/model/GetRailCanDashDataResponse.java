package com.pcda.mb.requestdashboard.railcancellationdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetRailCanDashDataResponse {

	private String errorMessage;
    private int errorCode;
    private List<GetRailCanDashDataModel> responseList;
    private GetRailCanDashDataModel response;

}
