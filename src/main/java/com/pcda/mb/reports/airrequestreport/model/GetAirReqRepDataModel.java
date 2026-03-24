package com.pcda.mb.reports.airrequestreport.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAirReqRepDataModel {

	
	private String requestId ;
	private String personalNo;		
	private String travelTypeName ;	
	private String codeHead ;	
	private String accountOffice ;		
	private String requestMode;
	private String unitOffice ;		
	private String bookingType	;
	private String ticketType	;
	private String requestDate	;
	private String status;
	
	
}
