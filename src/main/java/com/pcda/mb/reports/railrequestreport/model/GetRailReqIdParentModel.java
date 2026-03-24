package com.pcda.mb.reports.railrequestreport.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetRailReqIdParentModel {

	
	private String personalNo;
	private String requestId ;
	private String travelType;
	private String ticketType;
	private String codeHead;
	private String accountOffice;
	private String bookedDate;
	private String trRuleNo;
	private String bookingStatus;
	private String bookingType;
	private String originCode;
	private String destinationCode;
	private String origin;
	private String destination;
	

	 List<GetJourneyDetailModel> journeyDetailList;
	 List<GetPassengerDtlsModel> passengerDetailList;
	 List<GetReqQuestionModel> requestQuestionList;
}
