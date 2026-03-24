package com.pcda.mb.travel.railcancellation.model;

import java.util.List;

import lombok.Data;

@Data
public class RailCanBookingDtlsResponse {
	private String errorMessage;

	private int errorCode;

	private RailCanBookingDtlsModel response;

	private List<RailCanBookingDtlsModel> responseList;

	
}
