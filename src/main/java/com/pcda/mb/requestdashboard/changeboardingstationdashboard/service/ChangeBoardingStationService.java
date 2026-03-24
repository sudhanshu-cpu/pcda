package com.pcda.mb.requestdashboard.changeboardingstationdashboard.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.mb.requestdashboard.changeboardingstationdashboard.model.BoardingStationDetailsModel;
import com.pcda.mb.requestdashboard.changeboardingstationdashboard.model.BoardingStationDetailsResponse;
import com.pcda.mb.requestdashboard.changeboardingstationdashboard.model.ChangeBoardingStationResponse;
import com.pcda.mb.requestdashboard.changeboardingstationdashboard.model.ChangeBoardingStationResponseModel;
import com.pcda.mb.requestdashboard.changeboardingstationdashboard.model.PostBoardingStationModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetSingleTrainRouteModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetSingleTrainRouteResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.service.NormalBkgAjaxService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ChangeBoardingStationService {
	
	private OfficesService officesService;
	
	private RestTemplate restTemplate;
	
	private NormalBkgAjaxService ajaxService;
	

	public ChangeBoardingStationService(OfficesService officesService, RestTemplate restTemplate,NormalBkgAjaxService ajaxService) {
	
		this.officesService = officesService;
		this.restTemplate=restTemplate;
		this.ajaxService=ajaxService;
	}


	public Optional<OfficeModel> getUnitByUserId(BigInteger userId) {
		
		return officesService.getOfficeByUserId(userId);
	}


	public BoardingStationDetailsResponse getRailBookingDtls(String bookingId, String pnrNo,String groupId) {
		
		BoardingStationDetailsResponse railCancellationResponse=null;
		DODLog.info(LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE, ChangeBoardingStationService.class, "getRailBookingDtls bookingId:: "+bookingId
				+" | pnrNo :: "+pnrNo +" | groupId :: "+groupId);
		try {
			
   			Map<String, String> param= new HashMap<>();
			param.put("bookingId", bookingId);
			param.put("pnrNo", pnrNo);
			param.put("groupId", groupId);
			
			String baseUrl= PcdaConstant.RAIL_TICKET_BASE_URL +"/getBoardingDetails?";
			
			
			
			String url= param.entrySet().stream().filter(reqParam->!reqParam.getValue().isEmpty())
					.map(reqParam->reqParam.getKey()+"=" +reqParam.getValue())
					.collect(Collectors.joining("&", baseUrl, ""));
			
			ResponseEntity<BoardingStationDetailsResponse> unitMovementEntity = restTemplate.getForEntity(url, BoardingStationDetailsResponse.class);
		     railCancellationResponse = unitMovementEntity.getBody(); 
			DODLog.info(LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE, ChangeBoardingStationService.class, "railCancellationResponse:: "+railCancellationResponse);


			
		} catch (Exception e) {
			
			DODLog.printStackTrace(e, ChangeBoardingStationService.class, LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE);

		}
		
		return railCancellationResponse;
	}


	public void getAllBoardingStation(BoardingStationDetailsModel listModel) {

		Map<String, String> boardingMap = new LinkedHashMap<>();
	 
		listModel.setJourneyDate(CommonUtil.getChangeFormat(listModel.getJourneyDate(), "yyyy-MM-dd", "dd-MM-yyyy"));
		GetSingleTrainRouteResponse routeResponse = ajaxService.getSingleTrainData(listModel.getJourneyDate(), listModel.getTraninNumber());

		if (routeResponse != null && routeResponse.getErrorCode() == 200 && routeResponse.getResponseList() != null && !routeResponse.getResponseList().isEmpty()) {
		  
			List<GetSingleTrainRouteModel> modelList = routeResponse.getResponseList();
		    int fromStationIndex = IntStream.range(0, modelList.size())
		            .filter(i -> modelList.get(i).getStationcode().trim().equalsIgnoreCase(listModel.getFromStation().trim()))
		            .findFirst()
		            .orElse(-1);

		    int toStationIndex = IntStream.range(0, modelList.size())   
		            .filter(i -> modelList.get(i).getStationcode().trim().equalsIgnoreCase(listModel.getToStation().trim()))
		            .findFirst()
		            .orElse(-1);

		   
		    if (fromStationIndex != -1 && toStationIndex != -1){  
		        
		        int startIndex = Math.min(fromStationIndex, toStationIndex);
		        int endIndex = Math.max(fromStationIndex, toStationIndex);
		        
		        modelList.subList(startIndex, endIndex + 1).stream()
		                .forEach(stationModel -> boardingMap.put(stationModel.getStationcode(), stationModel.getStationname()));
		    } else {
		      
		        boardingMap.put(listModel.getFromStation(), listModel.getFromStation());
		    }

		    listModel.setBoardingStationMap(boardingMap);
		} else {
		   
		    boardingMap.put(listModel.getFromStation(), listModel.getFromStation());
		    listModel.setBoardingStationMap(boardingMap);
		}
	}


	public String validateBean(PostBoardingStationModel postBoardingStationModel) {
		String msg = "OK";

		if (postBoardingStationModel.getBoardingPoint() == null
				&& postBoardingStationModel.getBoardingPoint().isEmpty()) {

			return "Boarding Point Can not Be Empty";
		}
		if (postBoardingStationModel.getBoardingDate() == null
				&& postBoardingStationModel.getBoardingDate().isEmpty()) {

			return "Boarding Date Can not Be Empty";
		}
		if (postBoardingStationModel.getNewBoardingPoint() == null
				&& postBoardingStationModel.getNewBoardingPoint().isEmpty()) {

			return "New Boarding Point Can not Be Empty";
		}
		if (postBoardingStationModel.getTraninNumber() == null
				&& postBoardingStationModel.getTraninNumber().isEmpty()) {

			return "Train Number Can not Be Empty";
		}
		return msg;

	}


	public ChangeBoardingStationResponseModel saveBoardingStatioinReq(PostBoardingStationModel postBoardingStationModel) {
		DODLog.info(LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE, ChangeBoardingStationService.class, "postBoardingStationModel:: "+postBoardingStationModel);
		ChangeBoardingStationResponseModel responseModel = null;
		try {
			String url=PcdaConstant.RAIL_BOOK_SERVICE +"/saveBoardingChangeStation";			
			ResponseEntity<ChangeBoardingStationResponse> response=restTemplate.postForEntity(url, postBoardingStationModel, ChangeBoardingStationResponse.class);

			ChangeBoardingStationResponse boardingStationResponse=response.getBody();
			if(boardingStationResponse!=null && boardingStationResponse.getErrorCode()==200 && boardingStationResponse.getResponse()!=null) {
				responseModel=boardingStationResponse.getResponse();
				
			}
		} catch (RestClientException e) {
			DODLog.printStackTrace(e, ChangeBoardingStationService.class,LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE);
		}
		return responseModel; 
		
	}
	
	
	

}
