package com.pcda.pao.reports.railbookingreport.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostRailBkgRepoFormModel {

	
	private String bookingId="";
	private String requestId="";
	
	private String pnrNo="";
	private String ticketNo="";
	
	private String fromDate="";
	private String toDate="";
	
	private String fromJourneyDate="";
	private String toJourneyDate="";
	
	private String traveType="";
	private String codeHead="";
	
	private Integer bookingStatus;
	private String personalNo="";
	
	private Integer tatkalFlag;
	private String accountOffice="";
	
	private Integer ticketType;
	private String unitOffice="";
	
	
		  
}
