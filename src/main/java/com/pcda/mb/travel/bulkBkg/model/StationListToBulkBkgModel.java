package com.pcda.mb.travel.bulkBkg.model;

import lombok.Data;

@Data
public class StationListToBulkBkgModel {
	private int jrnyType;
	private String toStation;
	private String toStationType;
	private int airToStationTypeInt;
}
