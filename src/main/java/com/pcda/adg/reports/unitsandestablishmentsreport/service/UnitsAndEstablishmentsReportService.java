package com.pcda.adg.reports.unitsandestablishmentsreport.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.adg.reports.unitsandestablishmentsreport.model.UnitAndEstablishmentResponse;
import com.pcda.adg.reports.unitsandestablishmentsreport.model.UnitAndEstablishmentResponseModel;
import com.pcda.common.model.DODServices;
import com.pcda.common.services.MasterServices;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class UnitsAndEstablishmentsReportService {

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
			
		DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, UnitsAndEstablishmentsReportService.class,
				"size serviceMap of getAllService [UnitsAndEstablishmentsReportService]:" + serviceMap.size());

		return serviceMap;

	}
	
	public UnitAndEstablishmentResponse getUnitsAndEstablishmentsData(String serviceId) {
		DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, UnitsAndEstablishmentsReportService.class,
				" serviceId in  getUnitsAndEstablishmentsData():" + serviceId);
		UnitAndEstablishmentResponse response  = null;
		try {

			 String url = PcdaConstant.ADG_REPORTS_BASE_URL + "/adgMov/v1/getUnitsAndEstablishmentsReport?serviceId="+ serviceId;

			ResponseEntity<UnitAndEstablishmentResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					null, new ParameterizedTypeReference<UnitAndEstablishmentResponse>() {
					});

			 response = responseEntity.getBody();
			 DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, UnitsAndEstablishmentsReportService.class,
						" Response  getUnitsAndEstablishmentsData():" + response);
		} catch (Exception e) {
			DODLog.printStackTrace(e, UnitsAndEstablishmentsReportService.class, LogConstant.ADG_REPORTS_LOG_FILE);

		}
		return response;
	}

	public List<UnitAndEstablishmentResponseModel> getUnitsAndEstablishmentsDetails(String serviceId) {
		DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, UnitsAndEstablishmentsReportService.class,
				" serviceId in  getUnitsAndEstablishmentsDetails():" + serviceId);
		List<UnitAndEstablishmentResponseModel> result = new ArrayList<>();
		try {

			 String url = PcdaConstant.ADG_REPORTS_BASE_URL + "/adgMov/v1/getUnitsAndEstablishmentsDetails?serviceId="+ serviceId;

			ResponseEntity<UnitAndEstablishmentResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					null, new ParameterizedTypeReference<UnitAndEstablishmentResponse>() {
					});

			UnitAndEstablishmentResponse response = responseEntity.getBody();
			if (response != null && response.getErrorCode() == 200 && null!=response.getResponseList()) {
				result = response.getResponseList();
				DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, UnitsAndEstablishmentsReportService.class,
						" responselist size in  getUnitsAndEstablishmentsDetails():" + result.size());
			}
			
		} catch (Exception e) {
			DODLog.printStackTrace(e, UnitsAndEstablishmentsReportService.class, LogConstant.ADG_REPORTS_LOG_FILE);

		}
		return result;
	}
	
}
