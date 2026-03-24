package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class ClusterTrainResponse {

	private ClusterStationSearchResponseBean response;
	private Integer errorCode;
	
}
