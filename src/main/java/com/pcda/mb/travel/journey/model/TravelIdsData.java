package com.pcda.mb.travel.journey.model;

import java.util.Date;

import com.pcda.util.YesOrNo;

import lombok.Data;

@Data
public class TravelIdsData {
	
	private String traveld;
	private Date startDate;
	private Date endDate;
	private String startDateStr;
	private String endDateStr;
	private YesOrNo tripComplete;
	private int travelStatus;
}
