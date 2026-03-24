package com.pcda.adg.reports.bookingreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBookingRepoModel {



	private AdgMovDailyRptBean adgMovDailyRptBean;

	private AdgMovMonthlyRptBean adgMovMonthlyRptBean;

	private AdgMovBudgetRptBean adgMovSummaryRptBean;

	
	
	 private String userAction;
	 private String reportType;
	 private String frmDate;
	 private String toDate;
	 private String unitName;
	 private String accountOffice;
	 private String month;
	 private String year;
	 private String tillDate;
	 
	 private long totalConWrCount;
		
		private long totalConCvCount;

		private long totalConFormdCount;

		private long totalConCount;

		private long totalwlWrCount;

		private long totalwlCvCount;

		private long totalwlFdCount;

		private long totalwlToCount;

		private long totalcanWrCount;

		private long totalcanCvCount;

		private long totalcanFdCount;

		private long totalcanToCount;

		
		private long totaltoWrCount;

		private long totaltoCvCount;

		private long totaltoFdCount;

		private long totaltoToCount;

		private long totaltillWrCount;

		private long totaltillCvCount;

		private long totaltillFdCount;

		private long totaltillCount;
		
		
		private long totalWarantAllUnitCount;
		
		private long totalCvAllUnitCount;
		
		private long totalFdAllUnitCount;
		
		private long totalTransAllUnitCount;
		
		
		private long  totalWarnttranstilldateCount;
		
		private long totalCvtranstilldateCount;
		
		private long totalFdtranstilldateCount;
		
		private long totalTranstilldateCount;
	 
	

}
