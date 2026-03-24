package com.pcda.mb.reports.railrequestreport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.reports.railrequestreport.model.GetRailReqRepoResponse;
import com.pcda.mb.reports.railrequestreport.model.GetReqIdResponse;
import com.pcda.mb.reports.railrequestreport.model.PostRailReqModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class RailReqReportService {

@Autowired
private RestTemplate restTemplate;

public GetRailReqRepoResponse getRailReqReportData(PostRailReqModel railReqModel) {
	GetRailReqRepoResponse response = new GetRailReqRepoResponse();
	try {
		DODLog.info(LogConstant.RAIL_REPORT, RailReqReportService.class,"[getRailReqReportData] ## RAIL REQ-REPORT POST MODEL "+railReqModel);
		String url = PcdaConstant.COMMON_REPORT_URL+"/railRequestReport";
		response= restTemplate.postForObject(url, railReqModel, GetRailReqRepoResponse.class);
		
	}catch(Exception e) {
		DODLog.printStackTrace(e, RailReqReportService.class, LogConstant.RAIL_REPORT);
	}
	
	return response;
}
	
public GetReqIdResponse getRailReIdData(String requestId) {
	DODLog.info(LogConstant.RAIL_REPORT, RailReqReportService.class,"[getRailReIdData] ## requestId "+requestId);
	GetReqIdResponse response = new GetReqIdResponse();
	try {
		String url = PcdaConstant.COMMON_REPORT_URL+"/getRailRequestReportBookingDetails?requestID=";
		response = restTemplate.getForObject(url+requestId, GetReqIdResponse.class);
		
	}catch( Exception e) {
		DODLog.printStackTrace(e, RailReqReportService.class, LogConstant.RAIL_REPORT);
	}
	
	return response;
}
}
