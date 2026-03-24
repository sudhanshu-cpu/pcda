package com.pcda.mb.travel.journey.model;

import lombok.Data;

@Data
public class StationListToModel {

	private int jrnyType;
	private String toStation;
	private String toStationType;
	private int airToStationTypeInt;
}
