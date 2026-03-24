package com.pcda.mb.reports.tdrreport.model;

import lombok.Data;

@Data
public class TdrReportModel {
private String bookingId;
private String requestId;
private String pnrNo;
private String bookingDate;
private String travelerUserId;
private String personalNo;
private String travelType;
private String groupId;
private String paoGroupId;
private String createdBy;
private String tdrReason;
private String tdrTxnId;
private String tdrErrorMsg;
private String approvalState;
private String isTdrSucess;
private String fromDate;
private String toDate;
private String creationTime;
	
//Format Time
private String creationFomattedTime;
	
	
}
