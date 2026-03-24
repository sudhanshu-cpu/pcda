package com.pcda.co.requestdashboard.approvebooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.co.requestdashboard.approvebooking.model.GetBookingDataResponse;
import com.pcda.co.requestdashboard.approvebooking.model.GetDADetailsResponse;
import com.pcda.co.requestdashboard.approvebooking.model.GetNormalBookAppResponse;
import com.pcda.co.requestdashboard.approvebooking.model.PostApproveBookModel;
import com.pcda.co.requestdashboard.approvebooking.model.PostDisAppNormalBook;
import com.pcda.mb.reports.airrequestreport.model.GetAirReqIdResponse;
import com.pcda.mb.reports.railrequestreport.model.GetReqIdResponse;
import com.pcda.mb.reports.userreports.model.TravellerProfileResponseModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ApproveBookingService {
	
@Autowired
private RestTemplate restTemplate;



	public GetBookingDataResponse getBookDataForApp(String personalNo, String requestMode, String groupId) {
	
		DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingService.class," personalNo " + personalNo +" requestMode "+requestMode+" groupId "+groupId);
		
		GetBookingDataResponse response = new GetBookingDataResponse();
		
		try {
			String url=PcdaConstant.REQUEST_BASE_URL+ "/co/getRequestBookingForApproval"+"?personalNo="+personalNo+"&requestMode="+requestMode+"&groupId="+groupId;
			response = restTemplate.getForObject(url,GetBookingDataResponse.class);
		}catch(Exception e) {
			DODLog.printStackTrace(e,  ApproveBookingService.class, LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE);
		}
		DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingService.class," response " + response);
		return response;
		
	}
	
	// req id view 

	public GetReqIdResponse getRailBookReqIdData(String requestId) {
		DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingService.class," rail requestId " + requestId);
		
	GetReqIdResponse response = new GetReqIdResponse();
	try {
		String url = PcdaConstant.COMMON_REPORT_URL+"/getRailRequestReportBookingDetails?requestID=";
		response = restTemplate.getForObject(url+requestId, GetReqIdResponse.class);
		
	}catch( Exception e) {
		DODLog.printStackTrace(e, ApproveBookingService.class, LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE);
	}
	DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingService.class,"RAIL REQ-ID RESPONSE Model "+response);
	return response;
	}

	// air requestId View
	public GetAirReqIdResponse getAirBookReqIdData(String requestId) {
		DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingService.class," air requestId " + requestId);
		GetAirReqIdResponse response = new GetAirReqIdResponse();
		try {
			String url = PcdaConstant.COMMON_REPORT_URL+"/getAirRequestReportBookingDetails?requestID=";
			response = restTemplate.getForObject(url+requestId, GetAirReqIdResponse.class);
			
		}catch( Exception e) {
			DODLog.printStackTrace(e, ApproveBookingService.class, LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE);
		}
		DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingService.class,"AIR REQ-ID RESPONSE Model "+response);
		return response;
	}

	//personal No view 
		public TravellerProfileResponseModel getViewPersonal(String personalNo) {
			DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingService.class," personalNo " + personalNo);
			TravellerProfileResponseModel response = new TravellerProfileResponseModel() ;
			try {
				 response = restTemplate.getForObject(
						 PcdaConstant.USER_BASE_URL + "/getUserDetailsByUserAlias/" + personalNo,
						TravellerProfileResponseModel.class);
				
			} catch (Exception e) {
				DODLog.printStackTrace(e, ApproveBookingService.class, LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE);
			}
			DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingService.class, "Get personal pop-up response::" + response);

			return response;
		}
// da advance details view
		public GetDADetailsResponse getAppBookDADetails(String daRequestId) {
			DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingService.class," daRequestId " + daRequestId);
			GetDADetailsResponse response= new GetDADetailsResponse();
			try {
				String url=PcdaConstant.REQUEST_BASE_URL+"/co/getDADetails?requestId="+daRequestId;
				response= restTemplate.getForObject(url, GetDADetailsResponse.class);
				
			}catch (Exception e) {
				DODLog.printStackTrace(e, ApproveBookingService.class, LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE);
			}
			DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingService.class, "Get da detials pop-up response::" + response);
			return response;
		}

		// approve
		public GetNormalBookAppResponse sendForApproval(PostApproveBookModel approveBookModel) {
			GetNormalBookAppResponse response = null;
			DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingService.class, "##### APPROVAL BOOKING MODEL #####  ::" + approveBookModel);
			try {
				String url=PcdaConstant.REQUEST_BASE_URL+"/co/approveBookingRequest";
				  response= restTemplate.postForObject(url, approveBookModel, GetNormalBookAppResponse.class);
			}catch (Exception e) {
				DODLog.printStackTrace(e, ApproveBookingService.class, LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE);
			}
			DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingService.class, "######### APPROVAL BOOKING RESPONSE ##### ::" + response);
			
			return response;
		}
 
		// disapprove
		public GetNormalBookAppResponse sendForDisApproval(PostDisAppNormalBook disAppNormalBook) {
			GetNormalBookAppResponse response = null;
			DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingService.class, "##### DISAPPROVAL BOOKING MODEL ####### ::" + disAppNormalBook);
			try {
				String url=PcdaConstant.REQUEST_BASE_URL+"/co/disApproveBookingRequest";
				 response= restTemplate.postForObject(url, disAppNormalBook, GetNormalBookAppResponse.class);
			}catch (Exception e) {
				DODLog.printStackTrace(e, ApproveBookingService.class, LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE);
			}
			DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingService.class, " ######## DISAPPROVAL BOOKING RESPONSE ########::" + response);
			return response;
		}


}
