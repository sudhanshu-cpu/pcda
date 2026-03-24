package com.pcda.pao.reports.userverification.model;

import lombok.Data;

@Data
public class DownloadUserReportModel {

	private String personalNo;
	private String creationDate;
	
	private String name;
	private String unitName;
	
	private String serviceName;
	private String levelName;
	
   private String categoryName;
   private String paoVarificationStatusStr;
}



