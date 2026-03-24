package com.pcda.adg.reports.airandrailbkgcountreport.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.adg.reports.airandrailbkgcountreport.model.AirAndRailBkgTicketCountResponse;
import com.pcda.adg.reports.airandrailbkgcountreport.model.AirAndRailBookingDetails;
import com.pcda.adg.reports.airandrailbkgcountreport.model.AirRailTicketRequestModel;
import com.pcda.common.model.DODServices;
import com.pcda.common.services.MasterServices;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class AirAndRailBkgReportService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired 
	private MasterServices masterServices;

	public Map<String, String> getAllServiceMap() {
		Map<String, String> serviceMap = new HashMap<>();
		List<DODServices> serviceList = masterServices.getServicesByApprovalState("1");

			serviceList.forEach(service -> {
				serviceMap.put(service.getServiceId(), service.getServiceName());
			});

		DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, AirAndRailBkgReportService.class,
				"size serviceMap of getAllService [AirAndRailBkgReportService]:" + serviceMap.size());

		return serviceMap;

	}

	public List<AirAndRailBookingDetails> getAirRailTktBkgCount(AirRailTicketRequestModel requestModel) {
		List<AirAndRailBookingDetails> resultMap = new ArrayList<>();

	
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<AirRailTicketRequestModel> postEntity = new HttpEntity<>(requestModel, headers);
			
			String url = PcdaConstant.ADG_REPORTS_BASE_URL + "/adgMov/v1/getAdgMovAirRailTktBkgCountReport";
		
			ResponseEntity<AirAndRailBkgTicketCountResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, postEntity,
					new ParameterizedTypeReference<AirAndRailBkgTicketCountResponse>() {
					});

			AirAndRailBkgTicketCountResponse responseMap = responseEntity.getBody();
			if (responseMap != null && responseMap.getErrorCode() == 200 && null!=responseMap.getResponseList()) {
				DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, AirAndRailBkgReportService.class,
						"Air Rail Ticket Bkg Count Response List Size :: " + responseMap.getResponseList().size());
				resultMap.addAll(responseMap.getResponseList());
			}
			
		} catch (Exception e) {
			DODLog.printStackTrace(e, AirAndRailBkgReportService.class, LogConstant.ADG_REPORTS_LOG_FILE);

		}
		return resultMap;

	}

}
