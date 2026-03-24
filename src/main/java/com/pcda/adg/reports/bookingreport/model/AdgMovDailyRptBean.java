package com.pcda.adg.reports.bookingreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdgMovDailyRptBean {
	

	private String unitGroupId;
	private String name;
	private String transactionDate;
	private long confirmedWarrantCount;
	private long confirmedCvCount;	
	private long confirmedFormdCount;	
	private long confirmedTotalCount;	
	private long wlWarrantCount;
	private long wlCvCount;
	private long wlFormdCount;
	private long wlTotalCount;					
	private long cancelledWarrantCount;
	private long cancelledCvCount;
	private long cancelledFormdCount;
	private long cancelledTotalCount;
	private long totalTransWarrantCount;
	private long totalTransCvCount;
	private long totalTransFormdCount;
	private long totalTransCount;
	private long totalTransTillDateWarrantCount;
	 private long  totalTransTillDateCvCount;
	 private long  totalTransTillDateFormdCount;
	 private long  totalTransTillDateCount;	

}
