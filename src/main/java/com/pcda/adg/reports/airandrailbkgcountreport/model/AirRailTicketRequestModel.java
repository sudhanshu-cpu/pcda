package com.pcda.adg.reports.airandrailbkgcountreport.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AirRailTicketRequestModel {
	
	@NotBlank (message = "From booking date - Cannot be blank")
	private String fromDate;
	
	@NotBlank(message = "To booking date - Cannot be blank")
	private String toDate;
	
	private String service;
	
	
}