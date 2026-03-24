package com.pcda.mb.reports.airrequestreport.model;


import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class PostAirReqRepModel {
	private String personalNo="";
	private String requestId="";
	
	private Date fromDate;
	private Date toDate;
	
	private String fromXdate="";
	private String toXdate="";

	private String bookingType="";
	private String requestMode="";
	private String traveType="";
	private String codeHead="";
	private String accountOffice="";
	private String bookingStatus="";
	

	private String groupID="";
	
	
}
