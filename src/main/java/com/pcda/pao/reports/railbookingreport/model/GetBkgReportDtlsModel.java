package com.pcda.pao.reports.railbookingreport.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetBkgReportDtlsModel {
	private String bookingId ;
	private String pnrNumber;		
	private String accountOffice ;	
	private String personalNo ;	
	private String ticketType ;		
	private String tatkalApprovalStatus ;	
	private String tatkalBooking ;		
	private String bookingDate	;
	private double totalFare=0.0	;
	private Integer drtoPAO=0;
	private Integer crtoPAO=0;
	private String canView	;

}
