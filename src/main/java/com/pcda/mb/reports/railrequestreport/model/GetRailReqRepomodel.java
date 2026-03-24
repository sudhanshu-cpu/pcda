package com.pcda.mb.reports.railrequestreport.model;

import lombok.Data;

@Data
public class GetRailReqRepomodel {

	private String requestId ;
	private String personalNo;		
	private String travelTypeName ;	
	private String codeHead ;	
	private String accountOffice ;		
	private String tatkalRequestFlag ;	
	private String unitOffice ;		
	private String bookingType	;
	private String ticketType	;
	private String requestDate	;
	private String status;

}
