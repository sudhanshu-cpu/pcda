package com.pcda.adg.reports.bookingreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdgMovBudgetRptBean {

	private String name;
	private long confirmedWarrantCount;
	private long confirmedWarrantAmount;
	private long confirmedCvCount;

	private long confirmedCvAmount;

	private long confirmedFormdCount;
	private long confirmedFormdAmount;
	private long confirmedTotalCount;
	private long confirmedTotalAmount;
	
	private long wlWarrantCount;
	private long wlWarrantAmount;
	private long wlCvCount;

	private long wlCvAmount;
	private long wlFormdCount;
	private long wlFormdAmount;
	private long wlTotalCount;
	

	private long wlTotalAmount;
	private long cancelledWarrantCount;
	private long cancelledWarrantAmount;
	private long cancelledCvCount;
	

   
	private long cancelledCvAmount;
	private long cancelledFormdCount;
	private long cancelledFormdAmount;
	private long cancelledTotalCount;

	private long cancelledTotalAmount;
	private long totalTransWarrantCount;
	private long totalTransWarrantAmount;
	private long totalTransCvCount;
	

	private long totalTransCvAmount;
	private long totalTransFormdCount;
	private long totalTransFormdAmount;
	private long totalTransCount;
	
	 
	

	private long totalTransAmount;

	private long totalTransTillDateWarrantCount;
	private long totalTransTillDateCvCount;
	private long totalTransTillDateFormdCount;
	private long totalTransTillDateCount;
}
