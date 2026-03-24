package com.pcda.pao.transactionsettlementair.AirDemand.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AirDemandResponseData {

	private String toDateDisplay;
	private String toDate;
	private String message;
	private String fromDateDisplay;
	private String fromDate;
	private Integer recordsCount;
	private String functionalityAllow;
	private String isNavyPao;
	private List<AirDemandDownloadRequestData> downloadRequest=new ArrayList<>();
}
