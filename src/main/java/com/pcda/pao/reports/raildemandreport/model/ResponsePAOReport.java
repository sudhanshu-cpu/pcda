package com.pcda.pao.reports.raildemandreport.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponsePAOReport {

	private String errorMessage;
	private int errorCode;
	private List<GeneratePAOBean> responseList;
	private GeneratePAOBean response;
	
}
