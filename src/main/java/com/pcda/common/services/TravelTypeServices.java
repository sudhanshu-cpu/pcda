package com.pcda.common.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.TravelType;
import com.pcda.common.model.TravelTypeResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;
import com.pcda.util.Status;

@Service
public class TravelTypeServices {

	@Autowired
	private RestTemplate template;
	
	public List<TravelType> getAllTravelType(Integer approvalType) {
		DODLog.info(LogConstant.COMMON_LOG, LevelServices.class, "Get Categories with Approval Type: " + approvalType);

		List<TravelType> travelTypes=new ArrayList<>();
		
		try {
		ResponseEntity<TravelTypeResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/traveltype/getAllTravelType/"+approvalType, HttpMethod.GET, null,
				new ParameterizedTypeReference<TravelTypeResponse>() {});
		
		TravelTypeResponse travelTypeResponse = responseEntity.getBody();

		if (null != travelTypeResponse && (travelTypeResponse.getErrorCode() == 200) && null != travelTypeResponse.getResponseList()) {
			travelTypes.addAll(travelTypeResponse.getResponseList());
			travelTypes = travelTypes.stream().filter(e->e.getStatus().equals(Status.ON_LINE)).toList();
		}
		} catch (RestClientException e) {
			DODLog.printStackTrace(e, TravelTypeServices.class, LogConstant.COMMON_LOG);
		}
		DODLog.info(LogConstant.COMMON_LOG, MasterServices.class, " travelTypes size " + travelTypes.size());
		return travelTypes;
	}
	
	public TravelType getTravelType(String travelType) {
		DODLog.info(LogConstant.COMMON_LOG, TravelTypeServices.class, "Get travel Type with travelType: " + travelType);

		TravelType type= null;
		
		try {
		ResponseEntity<TravelTypeResponse> responseEntity = template.getForEntity(PcdaConstant.MASTER_BASE_URL + "/traveltype/getTravelType/"+travelType, TravelTypeResponse.class);
		
		TravelTypeResponse travelTypeResponse = responseEntity.getBody();

		if (null != travelTypeResponse && (travelTypeResponse.getErrorCode() == 200) && null != travelTypeResponse.getResponse()) {
			type=travelTypeResponse.getResponse();
		}
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelTypeServices.class, LogConstant.COMMON_LOG);
		}

		return type;
	}
}
