package com.pcda.common.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.RailSearchResponse;
import com.pcda.common.model.RailStation;
import com.pcda.common.model.RailStationResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class StationServices {

	@Autowired
	private RestTemplate template;
	
	public List<String> getStation(String station) {
		

		List<String> stations = new ArrayList<>();
		String notMatchStation="Station Name Not Exist";
		
		try {
		if (null != station && !station.isBlank()) {
			ResponseEntity<RailSearchResponse> serviceEntity = template.exchange(
					PcdaConstant.RAIL_BASE_URL + "/railStation/searchRailStation/" + station, HttpMethod.GET, null,
					new ParameterizedTypeReference<RailSearchResponse>() {});

			RailSearchResponse response = serviceEntity.getBody();
			if (null != response && response.getErrorCode() == 200 && null != response.getResponseList() && !response.getResponseList() .isEmpty() ) {
				stations.addAll(response.getResponseList());
				return stations;
			}else {
				stations.add(notMatchStation);
			}
			
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, StationServices.class, LogConstant.COMMON_LOG);
		}
		DODLog.info(LogConstant.COMMON_LOG, MasterServices.class, " stations :: " + station+"stationList Size :: "+stations.size());
		return stations;
	}
	
	public RailStation getStationByCode(String stationCode) {
		DODLog.info(LogConstant.COMMON_LOG, StationServices.class, "Get Station with station Code: " + stationCode);

		RailStation station=null;
		try {
		if (null != stationCode && !stationCode.isBlank()) {
			ResponseEntity<RailStationResponse> serviceEntity = template.exchange(
					PcdaConstant.RAIL_BASE_URL + "/railStation/getStationByCode/" + stationCode, HttpMethod.GET, null,
					new ParameterizedTypeReference<RailStationResponse>() {});

			RailStationResponse response = serviceEntity.getBody();
			if (null != response && response.getErrorCode() == 200 && null != response.getResponse()) {
				station=response.getResponse();
			}
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, StationServices.class, LogConstant.COMMON_LOG);
		}
		return station;
	}
}
