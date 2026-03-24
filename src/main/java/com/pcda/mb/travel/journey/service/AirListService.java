package com.pcda.mb.travel.journey.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcda.common.model.AirSearchResponse;
import com.pcda.common.services.AirportServices;
import com.pcda.common.services.MasterServices;
import com.pcda.mb.travel.journey.model.FlightSearchModel;
import com.pcda.mb.travel.journey.model.FlightSearchResponse;
import com.pcda.mb.travel.journey.model.RequestSearchBean;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AirListService {
	
	@Autowired
	private AirportServices airportServices; 
	
	@Autowired
	private RestTemplate template;

	@Async("pcdaAsyncExecutor")
	public CompletableFuture<List<String>> getAirportList(HttpServletRequest request) {
		List<String> stations= new ArrayList<>();
		try {
		String stationName=Optional.ofNullable(request.getParameter("stationName")).orElse("");
		String requestType=Optional.ofNullable(request.getParameter("reqType")).orElse("");
		
		
		
		 stations= airportServices.getAirport(stationName,requestType);
		if(stations.isEmpty()) {
			stations.add("Airport Not Exist");
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e,AirListService.class, LogConstant.JOURNEY_REQUEST_LOG);
		
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,AirListService.class,"[getAirportList] stations:"+stations.size());
		return CompletableFuture.completedFuture(stations);
	}

	
	public FlightSearchModel getAirSearchResult(HttpServletRequest request) {
	
		FlightSearchModel flightSearch = new FlightSearchModel();
		
		try{
	
		String journeyType = Optional.ofNullable(request.getParameter("journeyType")).orElse("");
		String travelTypeId = Optional.ofNullable(request.getParameter("travelTypeId")).orElse("");
		
		String trRule = Optional.ofNullable(request.getParameter("trRule")).orElse("");
		String airViaCount= Optional.ofNullable(request.getParameter("airViaCount")).orElse("0");
		
		
		String originCode = Optional.ofNullable(request.getParameter("originCode")).orElse("");
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, MasterServices.class, "  journeyType  " + journeyType+" travelTypeId : " + travelTypeId
				+ " trRule : " + trRule+" airViaCount " + airViaCount+" originCode " + originCode);

		RequestSearchBean searchBean = new RequestSearchBean();
		searchBean.setOriginCode(originCode);
		searchBean.setDtpickerDepart(Optional.ofNullable(request.getParameter("dtpickerDepart")).orElse(""));
		searchBean.setDestinationCode(Optional.ofNullable(request.getParameter("destinationCode")).orElse(""));
		searchBean.setNoOfAdult(Integer.parseInt(Optional.ofNullable(request.getParameter("noOfAdult")).orElse("0")));
		searchBean.setNoOfChild(Integer.parseInt(Optional.ofNullable(request.getParameter("noOfChild")).orElse("0")));
		searchBean.setNoOfInfant(Integer.parseInt(Optional.ofNullable(request.getParameter("noOfInfant")).orElse("0")));
		searchBean.setClassType(Integer.parseInt(Optional.ofNullable(request.getParameter("classType")).orElse("0")));
		searchBean.setJourneyType(journeyType);
		searchBean.setTravelTypeId(travelTypeId);
		searchBean.setTrRule(trRule);
		searchBean.setAirViaCount(Integer.parseInt(airViaCount));

		ResponseEntity<FlightSearchResponse> flightSearchResponse = template.postForEntity(PcdaConstant.AIR_BOOK_SERVICE + "/searchFlight",
				searchBean, FlightSearchResponse.class);

		
		FlightSearchResponse response = flightSearchResponse.getBody();

		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, AirListService.class, " getAirSearchResult response: " + response);
		if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
			flightSearch = response.getResponse();
			flightSearch.setJrnyType(journeyType);
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e,AirListService.class, LogConstant.JOURNEY_REQUEST_LOG);
			
		}

		return flightSearch;
	}

	@Async("pcdaAsyncExecutor")
	public CompletableFuture<List<String>> getMappedAirportList(HttpServletRequest request) {
		List<String> airport = new ArrayList<>();
		
		try {
		String railStn=Optional.ofNullable(request.getParameter("railStn")).orElse(""); 
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, AirListService.class, "getMappedAirportList:Get airport with railStn String: " + railStn);

		String uri = PcdaConstant.REQUEST_BASE_URL + "/mappedAirportList/"+railStn;
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).build();

		ResponseEntity<AirSearchResponse> serviceEntity = template.exchange(
				builder.toString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<AirSearchResponse>() {});

		AirSearchResponse airSearchResponse = serviceEntity.getBody();
		if (null != airSearchResponse && airSearchResponse.getErrorCode() == 200
				&& null != airSearchResponse.getResponseList()) {
			airport.addAll(airSearchResponse.getResponseList());
		}
		
		if(airport.isEmpty()) {
			airport.add("Airport Not Exist");
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e,AirListService.class, LogConstant.JOURNEY_REQUEST_LOG);
			
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, AirListService.class, " getMappedAirportList airport: " + airport.size());
		return CompletableFuture.completedFuture(airport);
		
		
	}
}
