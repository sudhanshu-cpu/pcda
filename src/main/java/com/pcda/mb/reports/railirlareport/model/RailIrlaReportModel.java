package com.pcda.mb.reports.railirlareport.model;

import lombok.Data;

@Data
public class RailIrlaReportModel {
	private String irlaId;	
	private String transaction;	
	private String  pnrNo;		
	private String transDate;
	private String personalNo;
	private Double craditAmount;
	private String downlodStatus;
	private String transactionType;
	private String accountOfiice;
	private String unitOffice;
	private String bookingDate;
	private String irlaReconStatus;
	private String downLoadDate;
	private String adjustmentYear;
}
