package com.pcda.adg.reports.bookingreport.model;

import lombok.Data;

@Data
public class AdgMovMonthlyRptBean {
	

	private String unitGroupId;
	private String name;
	private String transactionDate;	

	private long confirmedWarrantCount;	
      private long confirmedCvCount;
private long confirmedFormdCount;

private long confirmedTotalCount;	

private long wlWarrantCount;

		
	private long confirmedTotalAmount;		
	
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
	 private long  totalCvTransTillDateCount;
	 private long totalFdTransTillDateCount;	
	 private long totalWarrantAllUnitCount;
	 private long totalWarrantTransTillDateCount;


}
