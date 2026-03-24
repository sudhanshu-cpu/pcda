package com.pcda.pao.reports.userverification.model;

import java.util.Date;

import lombok.Data;

@Data
public class FileUploadDataModel {

	
	private String personalNo;
	private String creationDate;
	
	private String fullName;
	private String unitName;
	
	private String service;
	private String levelName;
	
	private String category;
	private String status;
	
	private Date creationDateType;
}
