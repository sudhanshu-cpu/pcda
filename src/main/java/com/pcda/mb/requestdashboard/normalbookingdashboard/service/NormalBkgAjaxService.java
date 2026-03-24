package com.pcda.mb.requestdashboard.normalbookingdashboard.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.adduser.edittravelerprofile.model.DependantDtls;
import com.pcda.mb.reports.airrequestreport.model.GetAirReqIdResponse;
import com.pcda.mb.reports.railrequestreport.model.GetReqIdResponse;
import com.pcda.mb.reports.userreports.model.GetTravllerProfileModel;
import com.pcda.mb.reports.userreports.model.TravellerProfileResponseModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetFairRuleResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetFareAccomodateResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetFareRuleBLResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetPinCodeResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetSingleTrainRouteResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostFareAccomodationModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class NormalBkgAjaxService {

	@Autowired
	private RestTemplate restTemplate;	
	
	
	
	// Rail req id view 

	public GetReqIdResponse getRailBookReqIdData(String requestId) {
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgAjaxService.class,"## [getRailBookReqIdData] requestId "+requestId);
	GetReqIdResponse response = new GetReqIdResponse();
	try {
		String url = PcdaConstant.COMMON_REPORT_URL+"/getRailRequestReportBookingDetails?requestID=";
		
		response = restTemplate.getForObject(url+requestId, GetReqIdResponse.class);
		
	}catch( Exception e) {
		
		DODLog.printStackTrace(e, NormalBkgAjaxService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
	}
	return response;
	}	
	
	
	// air requestId View
	public GetAirReqIdResponse getAirBookReqIdData(String requestId) {
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgAjaxService.class,"## [getAirBookReqIdData] requestId "+requestId);
		GetAirReqIdResponse response = new GetAirReqIdResponse();
		try {
			String url = PcdaConstant.COMMON_REPORT_URL+"/getAirRequestReportBookingDetails?requestID=";
			response = restTemplate.getForObject(url+requestId, GetAirReqIdResponse.class);
			
		}catch( Exception e) {
			
			DODLog.printStackTrace(e, NormalBkgAjaxService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
		}
		return response;
	}
	
	//personal No view 
		public TravellerProfileResponseModel getViewPersonal(String personalNo, String groupId) {
			DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgAjaxService.class,"## [getViewPersonal] personalNo "+personalNo+" groupId  "+ groupId);
			TravellerProfileResponseModel response = new TravellerProfileResponseModel() ;
			try {
				 response = restTemplate.getForObject(
						 PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/getProfileViewData?personalNo=" + personalNo
								+ "&officeId=" + groupId,
						TravellerProfileResponseModel.class);
				
				  
					if(response!=null && response.getErrorCode()==200) {
						GetTravllerProfileModel  personalModel =response.getResponse();
						List<DependantDtls>	familyDetails = personalModel.getFamilyDetails();
						Collections.sort(familyDetails);
						personalModel.setFamilyDetails(familyDetails);
						response.getResponse();
					}
				
			} catch (Exception e) {
				
				DODLog.printStackTrace(e, NormalBkgAjaxService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
			}
			DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgAjaxService.class, "[getViewPersonal] Get personal pop-up response::" + response);

			return response;
		}
	
		
		//AJAX ATT FAIR RULE 
		public String getATTFareRule(String flightKey) {
           DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgAjaxService.class,"[getATTFareRule] ## ATT FLIGHTKEY :: "+flightKey);
			String rule="";
			try {
				String url = PcdaConstant.AIR_BOOK_SERVICE+"/att/fareRule?flightKey="+flightKey;
				GetFairRuleResponse	response = restTemplate.postForObject(url,null, GetFairRuleResponse.class);
				
				if(response!=null) {
				rule = response.getResponse().getApiStatus().getResult().getRule();
				}
			}catch(Exception e) {
				
				DODLog.printStackTrace(e,  NormalBkgAjaxService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
			}
			return rule;
		}
		
		
		// BL Fair Rule
		public String getBLFareRule(String flightKey,String domInt) {
        DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgAjaxService.class,"##  [getBLFareRule] BL FLIGHTKEY:: "+flightKey+"DOMINT::"+domInt);
			String rule="";
			try {
				String url = PcdaConstant.AIR_BOOK_SERVICE+"/bl/fareRule?flightKey="+flightKey+"&domint="+domInt;
				GetFareRuleBLResponse	response = restTemplate.postForObject(url,null, GetFareRuleBLResponse.class);
				
				if(response!=null) {
				rule = response.getResponse().getResult();
				}
			}catch(Exception e) {
				
				DODLog.printStackTrace(e,  NormalBkgAjaxService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
			}
			return rule;
		}	
		
		//Rail trian route and other info related to train
		public GetSingleTrainRouteResponse getSingleTrainData(String journeyDate , String trainNo) {
			DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgAjaxService.class,"[getSingleTrainData] ## JOURNEY DATE :: "+journeyDate+"TRAIN NO ::"+trainNo);
			GetSingleTrainRouteResponse response = new GetSingleTrainRouteResponse();
			try {
				String url =PcdaConstant.RAIL_BOOK_SERVICE+"/irTrainRouteDetails?journeyDate="+journeyDate+"&trainNo="+trainNo;
				response = restTemplate.postForObject(url,null,GetSingleTrainRouteResponse.class);
			}catch( Exception e) {
				
				DODLog.printStackTrace(e, NormalBkgAjaxService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
			}
			DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgAjaxService.class,"SINGLE TRAIN INFO RESPONSE "+response);
			return response;
		}

		//Rail  fare and accomodation details
		public GetFareAccomodateResponse getFareAccmodationDtls(PostFareAccomodationModel postModel) {
			GetFareAccomodateResponse response = new GetFareAccomodateResponse();
			DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgAjaxService.class,"#####[getFareAccmodationDtls] POST FARE ACCOMODATION MODEL ####### :: "+postModel);
			try {
				String url = PcdaConstant.RAIL_BOOK_SERVICE+"/irAccomAndFareDetails";
				response = restTemplate.postForObject(url, postModel, GetFareAccomodateResponse.class);
			}catch( Exception e) {
				
				DODLog.printStackTrace(e, NormalBkgAjaxService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
			}
			DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgAjaxService.class,"######[getFareAccmodationDtls] FARE ACCOMODATION RESPONSE #### "+response);
			return response;
		}
		
		// Rail Pincode
		public GetPinCodeResponse getPinCodeDtsl(String pinCode) {
			GetPinCodeResponse response = new GetPinCodeResponse();
			try {
				String url=PcdaConstant.RAIL_BOOK_SERVICE+"/getPinCodeDetails/"+pinCode;
				response = restTemplate.postForObject(url, null,GetPinCodeResponse.class);
			}catch( Exception e) {
				
				DODLog.printStackTrace(e, NormalBkgAjaxService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
			}
			DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgAjaxService.class,"pinCode :: "+pinCode+"  [getPinCodeDtsl] PIN CODE RESPONSE "+response);
			return response;
		}
		
}
