package com.pcda.common.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcda.common.model.AirPort;
import com.pcda.common.model.AirSearchResponse;
import com.pcda.common.model.AirportResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class AirportServices {
	
	@Autowired
	private RestTemplate template;

	public List<String> getAirport(String airPortName) {
		

		List<String> airport = new ArrayList<>();
		String notMatchAirport="Airport Name Not Exist";
		try {
		if (null != airPortName && !airPortName.isBlank()) {
		ResponseEntity<AirSearchResponse> serviceEntity = template.exchange(
				PcdaConstant.AIR_BASE_URL + "/airport/searchAirport/" + airPortName, HttpMethod.GET, null,
				new ParameterizedTypeReference<AirSearchResponse>() {});

		AirSearchResponse airSearchResponse = serviceEntity.getBody();
		if (null != airSearchResponse && airSearchResponse.getErrorCode() == 200 && null != airSearchResponse.getResponseList() && !airSearchResponse.getResponseList() .isEmpty() ) {
			airport.addAll(airSearchResponse.getResponseList());
			return airport;
		}else {
			airport.add(notMatchAirport);
		}
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, AirportServices.class, LogConstant.COMMON_LOG);
		}
		DODLog.info(LogConstant.COMMON_LOG, AirportServices.class," airPortName :: "+ airPortName+ " airport size : " + airport.size());
		return airport;
	}
	
	public List<String> getAirport(String airPortName, String reqType) {
		DODLog.info(LogConstant.COMMON_LOG, AirportServices.class, " airPortName : " + airPortName +" reqType "+reqType);

		List<String> airport = new ArrayList<>();
		try {
		String uri = PcdaConstant.AIR_BASE_URL + "/airport/searchAirportWithReqType";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("airport", airPortName).queryParam("reqType", reqType).build();

		ResponseEntity<AirSearchResponse> serviceEntity = template.exchange(
				builder.toString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<AirSearchResponse>() {});

		AirSearchResponse airSearchResponse = serviceEntity.getBody();
		if (null != airSearchResponse && airSearchResponse.getErrorCode() == 200
				&& null != airSearchResponse.getResponseList()) {
			airport.addAll(airSearchResponse.getResponseList());
		}
	}catch(Exception e) {
		DODLog.printStackTrace(e, AirportServices.class, LogConstant.COMMON_LOG);
	}
		return airport;
	}
	
	public AirPort getAirportByCode(String airportCode) {
		DODLog.info(LogConstant.COMMON_LOG, AirportServices.class, "Get airport with airport Code: " + airportCode);

		AirPort airport=null;
		try {
		if(null==airportCode || airportCode.isBlank()) {
			return airport;
		}
		
		ResponseEntity<AirportResponse> serviceEntity = template.exchange(
				PcdaConstant.AIR_BASE_URL + "/airport/getAirportByCode/" + airportCode, HttpMethod.GET, null,
				new ParameterizedTypeReference<AirportResponse>() {});

		AirportResponse airportResponse = serviceEntity.getBody();
		if (null != airportResponse && airportResponse.getErrorCode() == 200
				&& null != airportResponse.getResponse()) {
			
			airport=airportResponse.getResponse();
		}
	}catch(Exception e) {
		DODLog.printStackTrace(e, AirportServices.class, LogConstant.COMMON_LOG);
	}
		
		return airport;
	}
}
