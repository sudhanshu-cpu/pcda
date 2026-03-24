package com.pcda.common.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.DODServices;
import com.pcda.common.model.ServiceResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class MasterServices {

	@Autowired
	private RestTemplate template;

	// Services

	// Get All services with Approval State
	public List<DODServices> getServicesByApprovalState(String approvalType) {
		

		List<DODServices> serviceList = new ArrayList<>();
		try {
		ResponseEntity<ServiceResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/service/allServices/" + approvalType, HttpMethod.GET, null,
				new ParameterizedTypeReference<ServiceResponse>() {});
		ServiceResponse apiResponse = responseEntity.getBody();

		if (null != apiResponse && apiResponse.getErrorCode() == 200 && null != apiResponse.getResponseList()) {
			serviceList = apiResponse.getResponseList();
			serviceList=serviceList.stream().filter(e->e.getStatus().equals("ON_LINE")).toList();
			
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, MasterServices.class, LogConstant.COMMON_LOG);
		}
		DODLog.info(LogConstant.COMMON_LOG, MasterServices.class, "Online service List: " + serviceList.size());
		return serviceList;
	}

	
	public Optional<DODServices> getServiceByServiceId(String serviceId) {
		

		DODServices dodServices = null;
		try {
			ServiceResponse serviceResponse = template.getForObject(
					PcdaConstant.MASTER_BASE_URL + "/service/getService/" + serviceId, ServiceResponse.class);
	
			if (serviceResponse != null && serviceResponse.getErrorCode() == 200 && serviceResponse.getResponse() != null) {
				dodServices = serviceResponse.getResponse();
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, MasterServices.class, LogConstant.COMMON_LOG);
		}

		return Optional.ofNullable(dodServices);
	}


}
