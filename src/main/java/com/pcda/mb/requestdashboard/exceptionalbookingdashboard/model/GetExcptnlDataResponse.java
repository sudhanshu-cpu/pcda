package com.pcda.mb.requestdashboard.exceptionalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data

public class GetExcptnlDataResponse {
	private String errorMessage;
	private int errorCode;
	private List<GetExcptnlDataParentModel> responseList;
	private GetExcptnlDataParentModel response;
}
