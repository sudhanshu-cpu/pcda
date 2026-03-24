package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetAccmodationBean {

	private String errorMessage;
	 private List<GetAccmodationDataBean> accStatus;

}
