package com.pcda.co.requestdashboard.approvetdr.model;

import java.util.List;

import lombok.Data;

@Data
public class GroupIdDataListRespose {

	private String errorMessage;
    private int errorCode;
    private GetParentDataGrpIdModel response;
    private List<GetParentDataGrpIdModel> responseList;
	
}
