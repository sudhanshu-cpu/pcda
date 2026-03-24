package com.pcda.mb.requestdashboard.aircancellationdashboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.requestdashboard.aircancellationdashboard.model.GetAirCanDashAbortResponse;
import com.pcda.mb.requestdashboard.aircancellationdashboard.model.GetAirCanDashBkgIdResponse;
import com.pcda.mb.requestdashboard.aircancellationdashboard.model.GetAirCanDashDataResponse;
import com.pcda.mb.requestdashboard.aircancellationdashboard.model.GetAirDashTktCancelResponse;
import com.pcda.mb.requestdashboard.aircancellationdashboard.model.PostAirCanTktModel;
import com.pcda.mb.requestdashboard.aircancellationdashboard.model.PostAirDashAbortCanModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class AirCancelDashBoardService {

	@Autowired
	private RestTemplate restTemplate;	
	
	// GET LIST OF DATA FOR AIR CAN DASH
	public GetAirCanDashDataResponse getAirCanDashData(String groupId) {
		DODLog.info(LogConstant.AIR_CANCELLATION_DASHBOARD_LOG_FILE, AirCancelDashBoardService.class, "[getAirCanDashData] ## AIR CAN DASH GROUP-ID ::"+groupId);
		GetAirCanDashDataResponse response = new GetAirCanDashDataResponse();
		try {
			String url=PcdaConstant.AIR_SERVICE_BASE_URL+"/getApprovedAirTicketForCancellatn/"+groupId;
			response = restTemplate.getForObject(url, GetAirCanDashDataResponse.class);
		}catch(Exception e) {
			DODLog.printStackTrace(e, AirCancelDashBoardService.class, LogConstant.AIR_CANCELLATION_DASHBOARD_LOG_FILE);
			
		}
		
		return response;
	}

	// GET  AIR CAN REQ DATA FROM BKG-ID
	public GetAirCanDashBkgIdResponse getAirDashDataFrmBkgId(String bookingId) {
		DODLog.info(LogConstant.AIR_CANCELLATION_DASHBOARD_LOG_FILE, AirCancelDashBoardService.class, "[getAirDashDataFrmBkgId] ##AIR CAN DASH BOOKING-ID ::"+bookingId);
		GetAirCanDashBkgIdResponse response = new GetAirCanDashBkgIdResponse();
		try {
			String url=PcdaConstant.AIR_SERVICE_BASE_URL+"/ticketDetailsCancellation/"+bookingId;
			response = restTemplate.getForObject(url, GetAirCanDashBkgIdResponse.class);
		}catch(Exception e) {
			DODLog.printStackTrace(e, AirCancelDashBoardService.class, LogConstant.AIR_CANCELLATION_DASHBOARD_LOG_FILE);
			
		}
		
		
		return response;
	}
	


	// ABORT AIR CAN DASH REQ
	public GetAirCanDashAbortResponse sendAbortCanRequest(PostAirDashAbortCanModel postModel) {
		DODLog.info(LogConstant.AIR_CANCELLATION_DASHBOARD_LOG_FILE, AirCancelDashBoardService.class, "[sendAbortCanRequest]AIR CAN DASH ABORT MODEL ::"+postModel);
		GetAirCanDashAbortResponse response = new GetAirCanDashAbortResponse();
		try {
			String url=PcdaConstant.AIR_SERVICE_BASE_URL+"/abortAirCancelRequest";
		
			response = restTemplate.postForObject(url,postModel ,GetAirCanDashAbortResponse.class);
		}catch(Exception e) {
			DODLog.printStackTrace(e, AirCancelDashBoardService.class, LogConstant.AIR_CANCELLATION_DASHBOARD_LOG_FILE);
			
		}
		DODLog.info(LogConstant.AIR_CANCELLATION_DASHBOARD_LOG_FILE, AirCancelDashBoardService.class, "[sendAbortCanRequest]AIR CAN DASH ABORT RESPONSE ::"+response);
		return response;
	}

	// AIR TKT CANCEL
	public GetAirDashTktCancelResponse sendAirDashTktCancel(PostAirCanTktModel postCanModel) {
		DODLog.info(LogConstant.AIR_CANCELLATION_DASHBOARD_LOG_FILE, AirCancelDashBoardService.class, "[sendAirDashTktCancel] AIR CAN DASH CANCEL MODEL ::"+postCanModel);
		GetAirDashTktCancelResponse response = new GetAirDashTktCancelResponse();
		
		if(postCanModel.getServiceProvider().equalsIgnoreCase("BL")) {
			postCanModel.setSerPov(0);
			
			
		}
		if(postCanModel.getServiceProvider().equalsIgnoreCase("ATT")) {
			postCanModel.setSerPov(1);
		}
		
		try {
			String url=PcdaConstant.AIR_BOOK_SERVICE+"/cancellation/"+postCanModel.getSerPov();
			
			response = restTemplate.postForObject(url,postCanModel ,GetAirDashTktCancelResponse.class);
		}catch(Exception e) {
			DODLog.printStackTrace(e, AirCancelDashBoardService.class, LogConstant.AIR_CANCELLATION_DASHBOARD_LOG_FILE);
			
		}
		DODLog.info(LogConstant.AIR_CANCELLATION_DASHBOARD_LOG_FILE, AirCancelDashBoardService.class, "[sendAirDashTktCancel] AIR CAN DASH CANCEL RESPONSE ::"+response);
		return response;
	}

}
