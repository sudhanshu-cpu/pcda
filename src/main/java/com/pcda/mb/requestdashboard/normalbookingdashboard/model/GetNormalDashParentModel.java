package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class GetNormalDashParentModel {
	private String personalNo;	
	private GetNormalDashAirModel airRequestBean;
	private GetNormalDashRailModel railRequestBean; 

}
