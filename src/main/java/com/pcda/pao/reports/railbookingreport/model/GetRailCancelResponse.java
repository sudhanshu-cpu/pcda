package com.pcda.pao.reports.railbookingreport.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetRailCancelResponse {
	private int errorCode;
	private String errorMessage;
	private GetRailCancelParentModel response;
	private List<GetRailCancelParentModel>responseList;
}
