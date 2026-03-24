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

import com.pcda.common.model.CitySearchModel;
import com.pcda.common.model.CitySearchResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class CityServices {
	
	@Autowired
	private RestTemplate template;

	public List<CitySearchModel> getCitySearch(String cityName) {
		DODLog.info(LogConstant.COMMON_LOG, CityServices.class, "Get cityName with search String: " + cityName);

		List<CitySearchModel> city = new ArrayList<>();
		try {
		String uri = PcdaConstant.MASTER_BASE_URL + "/citymaster/searchCity";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("cityName", cityName).build();

		ResponseEntity<CitySearchResponse> serviceEntity = template.exchange(
				builder.toString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<CitySearchResponse>() {});

		CitySearchResponse citySearchResponse = serviceEntity.getBody();
		if (null != citySearchResponse && citySearchResponse.getErrorCode() == 200
				&& null != citySearchResponse.getResponseList()) {
			
			city.addAll(citySearchResponse.getResponseList());
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, CityServices.class, LogConstant.COMMON_LOG);
		}
		DODLog.info(LogConstant.COMMON_LOG, AirportServices.class, " city : " + city.size());
		return city;
	}
}
