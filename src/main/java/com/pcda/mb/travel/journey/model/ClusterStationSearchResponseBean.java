package com.pcda.mb.travel.journey.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClusterStationSearchResponseBean {

	@JsonProperty("clusterStationList")
    private List<Clusterstationlist> clusterstationlist;
	
	private String errorMessage;
    private String errorCode;
}
