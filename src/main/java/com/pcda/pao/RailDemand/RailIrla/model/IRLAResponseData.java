package com.pcda.pao.RailDemand.RailIrla.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class IRLAResponseData {
	
	private String toDateDisplay;
	private String toDate;
	private String message;
	private String fromDateDisplay;
	private String fromDate;
	private Integer recordsCount;
	private String functionalityAllow;
	private String isNavyPao;
	private List<IRLADownloadRequestData> downloadRequest=new ArrayList<>();
}
