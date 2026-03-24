package com.pcda.common.model;

import java.util.Date;

import lombok.Data;

@Data
public class FinancialYearModel {
	
	private Integer id;
	private String financialYearName;
	
	private Date startDate;

	private Date endDate;

	private Long loginUserId = 0L;
	
	private String status;
	
	private Date lastModTime;
	
	private String approvalState;
	
	private String remarks;
	private String financialYearDateFormat;
	
	private String startDatePost;
	
	private String endDatePost;

	
	private String startDatePostFormat;
	private String endDatePostFormat;
	


}
