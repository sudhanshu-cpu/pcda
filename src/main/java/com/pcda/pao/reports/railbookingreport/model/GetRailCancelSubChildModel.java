package com.pcda.pao.reports.railbookingreport.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetRailCancelSubChildModel {
	private Integer refundId;
	private String name  ;
	private String gender  ;
	private Integer age  ;
	private String pxnType ;
	private String isConcession;
	private Integer pxnNo;
	private String seat;
	private String berth;	
	private String coach;
	private double ticketBaseFare;
	private double superfastCharge;
	private double otherCharge;
	private double safetyCharge;
	private double reservationCharge;
	private String ticketCurrentStatus;
	private String ticketBookingStatus;
	private String currentCancelStatus;
	private String isOnGovtInt;
}
