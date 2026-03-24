package com.pcda.co.requestdashboard.approvetdr.model;

import java.util.List;

import lombok.Data;

@Data
public class GetAppDataFrmBkgIdResponse {

	private String errorMessage;
    private int errorCode;
    private List<GetParentDataGrpIdModel> responseList;
    private GetParentDataGrpIdModel response;
}
