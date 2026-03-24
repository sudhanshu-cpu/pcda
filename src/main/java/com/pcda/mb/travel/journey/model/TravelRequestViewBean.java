package com.pcda.mb.travel.journey.model;

import java.util.List;

import lombok.Data;

@Data
public class TravelRequestViewBean {

	private String travelId;
	private List<TravelRequestData> travelRequestList;

}
