package com.pcda.mb.reports.railrequestreport.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetRailReqRepoResponse {
private String errorMessage;
private int errorCode;
private GetRailReqRepomodel response;

private List<GetRailReqRepomodel> responseList;
	
}
