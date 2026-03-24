package com.pcda.adg.reports.airandrailbkgcountreport.model;

import java.util.List;

import lombok.Data;

@Data
public class AirAndRailBkgTicketCountResponse {
	private String errorMessage;
    private int errorCode;
    private AirAndRailBookingDetails response;
    private List<AirAndRailBookingDetails> responseList;
}
