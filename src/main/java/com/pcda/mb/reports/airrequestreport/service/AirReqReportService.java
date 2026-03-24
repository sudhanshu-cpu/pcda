package com.pcda.mb.reports.airrequestreport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.reports.airrequestreport.model.GetAirReqIdResponse;
import com.pcda.mb.reports.airrequestreport.model.GetAirReqRepResponse;
import com.pcda.mb.reports.airrequestreport.model.PostAirReqRepModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;
@Service
public class AirReqReportService {

	@Autowired
	private RestTemplate restTemplate;

	public GetAirReqRepResponse getAirReqReportData(PostAirReqRepModel airReqModel) {
		GetAirReqRepResponse response = new GetAirReqRepResponse();
		try {
			DODLog.info(LogConstant.AIR_REPORT, AirReqReportService.class,"[getAirReqReportData] ## AIR REQ-REPORT POST MODEL "+airReqModel);
			String url = PcdaConstant.COMMON_REPORT_URL+"/airRequestReport";
			response= restTemplate.postForObject(url, airReqModel, GetAirReqRepResponse.class);
			
		}catch(Exception e) {
			DODLog.printStackTrace(e, AirReqReportService.class, LogConstant.AIR_REPORT);
		}
		DODLog.info(LogConstant.AIR_REPORT, AirReqReportService.class,"[getAirReqReportData] ## AIR REQ-REPORT RESPONSE-LIST MODEL "+response);
		return response;
	}
	
	public GetAirReqIdResponse getAirReIdData(String requestId) {
		DODLog.info(LogConstant.AIR_REPORT, AirReqReportService.class,"[getAirReIdData] ## requestId "+requestId);
		GetAirReqIdResponse response = new GetAirReqIdResponse();
		try {
			String url = PcdaConstant.COMMON_REPORT_URL+"/getReportAirRequestBookingDetails?requestID=";
			response = restTemplate.getForObject(url+requestId, GetAirReqIdResponse.class);
			
		}catch( Exception e) {
			DODLog.printStackTrace(e, AirReqReportService.class, LogConstant.AIR_REPORT);
		}
		DODLog.info(LogConstant.AIR_REPORT, AirReqReportService.class,"[getAirReIdData] ## AIR REQ-ID RESPONSE Model "+response);
		return response;
	}
}
