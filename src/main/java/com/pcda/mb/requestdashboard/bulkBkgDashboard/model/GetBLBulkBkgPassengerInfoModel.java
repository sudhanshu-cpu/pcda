package com.pcda.mb.requestdashboard.bulkBkgDashboard.model;

import lombok.Data;

@Data
public class GetBLBulkBkgPassengerInfoModel {
	
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String passCategory;
	private String mobileNo;
	private String emailId;
	private String operatorTxnId;
	private String airlinePnr;
	private String gdsPnr;
	private String bookingStatus;
	private int status;
	private String bookingClass;

}
