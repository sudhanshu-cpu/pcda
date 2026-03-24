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

import com.pcda.common.model.RailSearchResponse;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.StationServices;
import com.pcda.mb.travel.journey.model.ClusterRequestModel;
import com.pcda.mb.travel.journey.model.ClusterStationSearchResponseBean;
import com.pcda.mb.travel.journey.model.ClusterTrainResponse;
import com.pcda.mb.travel.journey.model.TrainSearchResponse;
import com.pcda.mb.travel.journey.model.TrainSearchResponseBean;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TrainListService {

	@Autowired
	private StationServices stationServices; 

	@Autowired
	private RestTemplate template;

	@Async("pcdaAsyncExecutor")
	public CompletableFuture<TrainSearchResponseBean> getTrainListDetails(HttpServletRequest request) {

		TrainSearchResponseBean responseBean = new TrainSearchResponseBean();
		
		try {
		 String frmStation=Optional.ofNullable(request.getParameter("frmStation")).orElse("");
		 String toStation=Optional.ofNullable(request.getParameter("toStation")).orElse("");
		 String depDate=Optional.ofNullable(request.getParameter("dep_date")).orElse("");
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,TrainListService.class,"[getTrainsOnRoute]frmStation:"+frmStation+" | toStation:"+toStation+" | depDate"+depDate);
		
		 
		 //CRIS Call pending
			String uri = PcdaConstant.RAIL_BOOKING_URL + "rail/v1/searchTrainOnRequest";
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("fromStn", frmStation).queryParam("toStn", toStation).queryParam("depDate", depDate).build();

		TrainSearchResponse trainSearchResponse = template.postForObject(builder.toString(), null, TrainSearchResponse.class);
		if (trainSearchResponse != null && trainSearchResponse.getErrorCode() == 200) {
			responseBean = trainSearchResponse.getResponse();

			if(responseBean.getErrorMessage()==null) {
				responseBean.setErrorMessage("unable to fetch the details from IRCTC");
				return CompletableFuture.completedFuture(responseBean);
			}
		
			if (!responseBean.getErrorMessage().isEmpty() && (responseBean.getErrorMessage().contains("404") || responseBean.getErrorMessage().contains("502"))) {
				responseBean.setErrorMessage("Requested Service not found. Please try after some time");
			}
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, TrainListService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}

		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,TrainListService.class,"[getTrainsOnRoute]responseBean " +responseBean);
		return CompletableFuture.completedFuture(responseBean);
	}

	@Async("pcdaAsyncExecutor")
	public CompletableFuture<List<String>> getStationList(HttpServletRequest request) {
		
		String stationName=Optional.ofNullable(request.getParameter("stationName")).orElse("");
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,TrainListService.class,"[getStationList] stationName:"+stationName);
		
		List<String> stations= stationServices.getStation(stationName);
		if(stations.isEmpty()) {
			stations.add("Station Name Not Exist");
		}
		
		return CompletableFuture.completedFuture(stations);
	}

	
	public TrainSearchResponseBean searchTrainList(HttpServletRequest request) {
		TrainSearchResponseBean responseBean = new TrainSearchResponseBean();

		try {
		 String frmStation=Optional.ofNullable(request.getParameter("frmStation")).orElse("");
		 String toStation=Optional.ofNullable(request.getParameter("toStation")).orElse("");
		 String depDate=Optional.ofNullable(request.getParameter("dep_date")).orElse("");
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,TrainListService.class,"[searchTrainList]frmStation:"+frmStation+" | toStation:"+toStation+" | depDate"+depDate);
		 
		
		 
		 //CRIS Call pending
			String uri = PcdaConstant.RAIL_BOOKING_URL + "rail/v1/searchTrainOnRequest";
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("fromStn", frmStation).queryParam("toStn", toStation).queryParam("depDate", depDate).build();

		TrainSearchResponse trainSearchResponse = template.postForObject(builder.toString(), null, TrainSearchResponse.class);
		if (trainSearchResponse != null && trainSearchResponse.getErrorCode() == 200) {
			responseBean = trainSearchResponse.getResponse();
			if (responseBean.getTrainbtwnstnslist() != null && !responseBean.getTrainbtwnstnslist().isEmpty()) {
			responseBean.getTrainbtwnstnslist().forEach(e -> {
				String runsOn = "";
				if(e.getRunningmon().equalsIgnoreCase("Y")) {runsOn += "M";} else {runsOn += "-";}
				if(e.getRunningtue().equalsIgnoreCase("Y")) {runsOn += "T";} else {runsOn += "-";}
				if(e.getRunningwed().equalsIgnoreCase("Y")) {runsOn += "W";} else {runsOn += "-";}
				if(e.getRunningthu().equalsIgnoreCase("Y")) {runsOn += "TH";} else {runsOn += "-";}
				if(e.getRunningfri().equalsIgnoreCase("Y")) {runsOn += "F";} else {runsOn += "-";}
				if(e.getRunningsat().equalsIgnoreCase("Y")) {runsOn += "S";} else {runsOn += "-";}
				if(e.getRunningsun().equalsIgnoreCase("Y")) {runsOn += "SU";} else {runsOn += "-";}
				e.setRunsOn(runsOn);
			});
		}
			if (responseBean.getErrorMessage() != null && responseBean.getErrorMessage().contains("404")) {
				responseBean.setErrorMessage("Requested Service not found. Please try after some time");
			}
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, TrainListService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,TrainListService.class,"[searchTrainList] responseBean : "+responseBean);
		return responseBean;
	}

	@Async("pcdaAsyncExecutor")
	public CompletableFuture<ClusterStationSearchResponseBean> clusterStationSearch(HttpServletRequest request) {
		
	
		ClusterStationSearchResponseBean responseBean=null;
	 	try {
		 String sourceStnCode=Optional.ofNullable(request.getParameter("srcStation")).orElse("");
		 String journeyDate=Optional.ofNullable(request.getParameter("journeyDate")).orElse("");
		 String[] journeyDateArr=null;
		 if ( !"".equals(journeyDate)) {
			 journeyDateArr= journeyDate.split("-");
			
		 }
		 String journeyDateDay="";
		 String journeyDateMonth="";
		 String journeyDateYear="";
		 if(null!=journeyDateArr && journeyDateArr.length==3){
			 journeyDateDay=journeyDateArr[0];
			 journeyDateMonth=journeyDateArr[1];
			 journeyDateYear=journeyDateArr[2];
		 }
		 String trainNo=request.getParameter("trainNo");
		
		 ClusterRequestModel clusterRequestModel=new ClusterRequestModel();
		 clusterRequestModel.setJourneyDay(journeyDateDay);
		 clusterRequestModel.setJourneyMonth(journeyDateMonth);
		 clusterRequestModel.setJourneyYear(journeyDateYear);
		 clusterRequestModel.setTrainNo(trainNo);
		 clusterRequestModel.setSourceStnCode(sourceStnCode);
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,TrainListService.class,"[clusterStationSearch]clusterRequestModel-->"+clusterRequestModel);
		 ClusterTrainResponse clusterTrainResponse= template.postForObject(PcdaConstant.RAIL_BOOKING_URL + "rail/v1/getClusterStation", clusterRequestModel, ClusterTrainResponse.class);
		 
		 
		 
		 if(null!=clusterTrainResponse && clusterTrainResponse.getErrorCode()==200) {
			 
			 responseBean=clusterTrainResponse.getResponse();
		 }
	 	}catch(Exception e) {
			DODLog.printStackTrace(e, TrainListService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		 
		return CompletableFuture.completedFuture(responseBean);
	}

	@Async("pcdaAsyncExecutor")
	public CompletableFuture<List<String>> getMappedStationList(HttpServletRequest request) {
		List<String> stations = new ArrayList<>();
		try {
		String railStn=Optional.ofNullable(request.getParameter("airportCode")).orElse(""); 
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, MasterServices.class, "getMappedAirportList:Get airport with railStn String: " + railStn);

		
		
		String uri = PcdaConstant.REQUEST_BASE_URL + "/mappedStationList/"+railStn;
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).build();

		ResponseEntity<RailSearchResponse> serviceEntity = template.exchange(
				builder.toString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<RailSearchResponse>() {});

		RailSearchResponse railSearchResponse = serviceEntity.getBody();
		if (null != railSearchResponse && railSearchResponse.getErrorCode() == 200
				&& null != railSearchResponse.getResponseList()) {
			stations.addAll(railSearchResponse.getResponseList());
		}
		
		if(stations.isEmpty()) {
			stations.add("Station Name Not Exist");
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, TrainListService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, MasterServices.class, "getMappedAirportList: stations: " + stations.size());
		return CompletableFuture.completedFuture(stations);
		
		
	}

	

}
