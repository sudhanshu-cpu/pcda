package com.pcda.mb.requestdashboard.exceptionalbookingdashboard.model;

import lombok.Data;

@Data
public class GetExcptnlDataParentModel {
	private String personalNo;
	GetExcptnlAirRequestBean airRequestBean;
	GetExcptnlRailRequestBean railRequestBean;

}
