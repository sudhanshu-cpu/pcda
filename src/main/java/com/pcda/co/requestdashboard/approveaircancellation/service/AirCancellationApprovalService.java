package com.pcda.co.requestdashboard.approveaircancellation.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.co.requestdashboard.approveaircancellation.model.AirCancellationApproveResponse;
import com.pcda.co.requestdashboard.approveaircancellation.model.GetApproveAirCancellationModel;
import com.pcda.co.requestdashboard.approveaircancellation.model.PostAirCancellationApprovalModel;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.mb.travel.aircancellation.service.AirCancellationService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service

public class AirCancellationApprovalService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OfficesService officesService;

	// Get Air Cancellation
	public List<GetApproveAirCancellationModel> getAllAirCancellationForApproval(String groupId, String type) {
		
		DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, AirCancellationApprovalService.class, " groupId " + groupId +" type "+ type);

		List<GetApproveAirCancellationModel> getApproveAirCancellationModels = new ArrayList<>();

		String url = "/ticketForCancellation/";

		url = url + groupId + "/" + type;
		DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, AirCancellationApprovalService.class, "mapping Url" + url);

		try {

			ResponseEntity<AirCancellationApproveResponse> unitMovementEntity = restTemplate.exchange(
					PcdaConstant.AIR_SERVICE_BASE_URL + url, HttpMethod.GET, null,
					new ParameterizedTypeReference<AirCancellationApproveResponse>() {
					});
			AirCancellationApproveResponse response = unitMovementEntity.getBody();

			if (null != response && response.getErrorCode() == 200 && response.getResponseList() != null) {
				getApproveAirCancellationModels = response.getResponseList();
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, AirCancellationApprovalService.class, LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG);
		}
		DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, AirCancellationApprovalService.class, " getApproveAirCancellationModels " + getApproveAirCancellationModels);
		return getApproveAirCancellationModels;
	}

	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {
		return officesService.getOfficeByUserId(userId);
	}

	// Get Tickets Details
	public GetApproveAirCancellationModel getAirBookingContentByBookingId(String bookingId) {
		DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, AirCancellationApprovalService.class, " bookingId " + bookingId);
		GetApproveAirCancellationModel airBookingContent = null;

		try {

			ResponseEntity<AirCancellationApproveResponse> entity = restTemplate.exchange(
					PcdaConstant.AIR_SERVICE_BASE_URL + "/ticketDetailsCancellation/" + bookingId, HttpMethod.GET, null,
					new ParameterizedTypeReference<AirCancellationApproveResponse>() {
					});
			AirCancellationApproveResponse response = entity.getBody();

			if (null != response && response.getErrorCode() == 200 && response.getResponse() != null) {
				airBookingContent = response.getResponse();
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, AirCancellationService.class, LogConstant.AIR_TICKET_CANCELLATION_LOG);
		}

		DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, AirCancellationService.class," airBookingContent " + airBookingContent);

		return airBookingContent;
	}

	// Approve And Disapprove
	public AirCancellationApproveResponse airTicketCanReqestApprove(
			PostAirCancellationApprovalModel postAirCancellationApprovalModel, HttpServletRequest request) {

		DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, AirCancellationApprovalService.class,
				"######### Approval AirCancellation ::" + postAirCancellationApprovalModel);
		AirCancellationApproveResponse response = null;

		try {
			if (postAirCancellationApprovalModel.getIsRoundTrip().equalsIgnoreCase("NO")) {

			
			 int onwardCheckLength =Integer.parseInt(request.getParameter("totalPass"));
		    
			 DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, AirCancellationApprovalService.class,
						"####### totalPass ## " + onwardCheckLength);
							
			     setOnwardChkMap(onwardCheckLength,request,postAirCancellationApprovalModel);
				
					DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, AirCancellationApprovalService.class,
							" ######## Approval AirCancellation ::" + postAirCancellationApprovalModel);
				
					response = restTemplate.postForObject(
							PcdaConstant.AIR_SERVICE_BASE_URL + "/approveDisapproveCancellationRequest",
							postAirCancellationApprovalModel, AirCancellationApproveResponse.class);

					DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, AirCancellationApprovalService.class,
							"######### Onward Response ######## ::" + response);
					return response;
				
			}

			else if (postAirCancellationApprovalModel.getIsRoundTrip().equalsIgnoreCase("YES")) {

				int onwardCheck =Integer.parseInt(request.getParameter("onwardCheckLength"));
			     int returnCheck=Integer.parseInt(request.getParameter("returnCheckLength"));
			     if(onwardCheck!=0 ) {
					Map<String, Integer> onwardMap = new HashMap<>();
						for(int i=0;i<onwardCheck;i++) {
						String onwardCheckValue =	request.getParameter("passDetails"+i);
						
					   if(onwardCheckValue!=null) {
						onwardMap.put( onwardCheckValue,i);
					   }
					 }
						postAirCancellationApprovalModel.setOnwardCheck(onwardMap);
					}
						
						if(returnCheck!=0 ) {
					Map<String, Integer> retrurnMap = new HashMap<>();
					setReturnChkMap(returnCheck,retrurnMap,request);
					postAirCancellationApprovalModel.setReturnCheck(retrurnMap);
						}
						

					response = restTemplate.postForObject(
							PcdaConstant.AIR_SERVICE_BASE_URL + "/approveDisapproveCancellationRequest",
							postAirCancellationApprovalModel, AirCancellationApproveResponse.class);

					DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, AirCancellationApprovalService.class,
							"############  Onward And Return ###################::" + response);

					return response;
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, AirCancellationApprovalService.class, LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG);
		}

		return response;
	}

	
	private void setOnwardChkMap(int onwardCheckLength,HttpServletRequest request,PostAirCancellationApprovalModel postAirCancellationApprovalModel) {
		
		if(onwardCheckLength!=0 ) {
			Map<String, Integer> onwardMap = new HashMap<>();
				for(int i=0;i<=onwardCheckLength;i++) {
					
			   String onwardCheckValue =Optional.ofNullable(request.getParameter("passDetails"+i)).orElse("");
			   
			   if(!onwardCheckValue.isEmpty()) {
			   DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, AirCancellationApprovalService.class,
						"####### onwardCheckValue::" + onwardCheckValue);
			   
			   DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, AirCancellationApprovalService.class,
						"####### onwardIsOfficial::" + request.getParameter("onwardIsOfficial"+onwardCheckValue));
			   Integer isOffical =Integer.parseInt(request.getParameter("onwardIsOfficial"+onwardCheckValue));
			   
				if(isOffical!=null) {
			   onwardMap.put( onwardCheckValue,isOffical);
				}
			 }
			 }
			postAirCancellationApprovalModel.setOnwardCheck(onwardMap);
			}
		
		
	}
	
	
	
	
	private void setReturnChkMap(int returnCheck,Map<String, Integer> retrurnMap,HttpServletRequest request) {

		for(int i=0;i<returnCheck;i++) {
			 String returnCheckValue =	request.getParameter("retPassDetails"+i);
			 if(returnCheckValue!=null) {
				retrurnMap.put(returnCheckValue, i);
			
			}
			}
}
	
}
