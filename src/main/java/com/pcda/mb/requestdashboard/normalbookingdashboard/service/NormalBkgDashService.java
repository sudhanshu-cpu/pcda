package com.pcda.mb.requestdashboard.normalbookingdashboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.reports.airrequestreport.model.GetAirReqIdResponse;
import com.pcda.mb.reports.railrequestreport.model.GetReqIdResponse;
import com.pcda.mb.reports.userreports.model.TravellerProfileResponseModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.FlightSearchOption;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetATTBookingResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetAirBookResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetAttConfirmBookingResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetBLBookingResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetBLConfirmBookResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetConfirmIRSearchResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetFareAccomodateResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetFinalBookRailTktResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetNormalCancelResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetNormalDashDataResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetNormalDashParentModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetPinCodeResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetPreConfirmResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetRailPreSearchResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetSessionDtlsResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetSingleTrainRouteResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetTrainSearchResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetUniquePNoResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PosrAirBookATTModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostAirBookBlModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostConfimBLAirBookModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostConfirmAttAirBook;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostFareAccomodationModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostFinalBookModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostIRPreConfirmModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostIRSearchConfirmModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostNormalCancelModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostRailPreSearchModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostTrainSearchModel;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class NormalBkgDashService {

@Autowired
private RestTemplate restTemplate;

@Autowired
private NormalBkgAjaxService ajaxService;

@Autowired
private NormalBookRailService railService;

	
public List<String> getUniquePersonalNo(String groupId){
	List<String> uniquePnoList =  new ArrayList<>();
	try {
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class, "[getUniquePersonalNo] groupId :: "+groupId);
		String url = PcdaConstant.REQUEST_BASE_URL+"/getUniquePersonalNo?groupId="+groupId;
	GetUniquePNoResponse response = restTemplate.getForObject(url, GetUniquePNoResponse.class);
			if(response!=null && response.getErrorCode()==200 && !response.getResponse().isEmpty()) {
				uniquePnoList = response.getResponse();
				
			}
	}catch(Exception e) {
		
		DODLog.printStackTrace(e,  NormalBkgDashService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
	}
	return uniquePnoList;
}

//search data from personal no and request mode
public GetNormalDashDataResponse getNormalData(String personalNo , String requestMode,String groupId) {
	
	DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class, "#### [getNormalData] personalNo #### "+personalNo
			+"##requestMode"+requestMode+"##groupId"+groupId);
	
	GetNormalDashDataResponse  response = new GetNormalDashDataResponse();
	List<GetNormalDashParentModel> newModelList = new ArrayList<>();
	try {
		String url = PcdaConstant.REQUEST_BASE_URL+"/getRequesDatabyPersonalNo?personalNo="+personalNo+"&requestMode="+requestMode+"&groupId="+groupId;
		response = restTemplate.getForObject(url, GetNormalDashDataResponse.class);
	   if(response!=null && response.getErrorCode()==200 && null!=response.getResponseList()&& !response.getResponseList().isEmpty()) {
			List<GetNormalDashParentModel> modelList = response.getResponseList();
			
			
			for(GetNormalDashParentModel model : modelList) {
				if(model.getRailRequestBean()!=null) {
					model.setPersonalNo(model.getRailRequestBean().getPersonalNo());
					String jouneyDate=CommonUtil.formatDate(model.getRailRequestBean().getJournyDate(),"dd-MM-yyyy");
					model.getRailRequestBean().setJourneyDateStr(jouneyDate);
					
				}
				if(model.getAirRequestBean()!=null) {
					model.setPersonalNo(model.getAirRequestBean().getPersonalNo());
                  String jouneyDate=CommonUtil.formatDate(model.getAirRequestBean().getOnwardJrnyDate(),"dd-MM-yyyy");
					model.getAirRequestBean().setJourneyDateStr(jouneyDate);
				}
				newModelList.add(model);
			}
			response.setResponseList(newModelList);
			DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class, "NEW NORAML DASH DATA LIST :: "+newModelList.size());
		}
	}catch(Exception e) {
		
		DODLog.printStackTrace(e,  NormalBkgDashService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
	}
	
	
	return response;
}

public GetAirBookResponse getAirBookData(String requestId) {
	GetAirBookResponse response = new GetAirBookResponse();
	DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class, "####[getAirBookData] INSIDE  getAirBookData  #### "+requestId);
	try {
		String url=PcdaConstant.AIR_BOOK_SERVICE+"/searchFlight/"+requestId;

		response = restTemplate.postForObject(url,null,GetAirBookResponse.class);
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class, " [getAirBookData] ## NORMAL AIR BOOK RESPONSE :: "+response);
	}catch(Exception e) {
		
		DODLog.printStackTrace(e,  NormalBkgDashService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
	}
	
	
	return response;
}

// ATT BOOK
public GetATTBookingResponse bookATTFlight(PosrAirBookATTModel airBookATTModel) {
	
	
	GetATTBookingResponse response = new GetATTBookingResponse();
	DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class, "POST ATT BOOK MODEL:: "+airBookATTModel);
	
	try{
		String url=PcdaConstant.AIR_BOOK_SERVICE+"/att/validateFlightPrice";
		response=restTemplate.postForObject(url, airBookATTModel, GetATTBookingResponse.class);
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class, "ATT RESPONSE :: "+response);
		
	}catch(Exception e) {
		
			DODLog.printStackTrace(e,  NormalBkgDashService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
		}
	return response;
	}

// BL Book 

public GetBLBookingResponse bookBLFlight(PostAirBookBlModel postAirBookBlModel,List<FlightSearchOption> flightOptList) {
	GetBLBookingResponse response = new GetBLBookingResponse();
	DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class, "[bookBLFlight] ### POST BL BOOK MODEL ### "+postAirBookBlModel);
	
	try{
		Optional<FlightSearchOption> flightOpt= flightOptList.stream().filter(e->e.getFlightKey().equals(postAirBookBlModel.getFlightKey())).findFirst();
		
		if(flightOpt.isPresent()) {
	
		postAirBookBlModel.setFlightOption(flightOpt.get());
		}
		String url=PcdaConstant.AIR_BOOK_SERVICE+"/bl/validateFlightPrice";
		
	
		response=restTemplate.postForObject(url, postAirBookBlModel, GetBLBookingResponse.class);
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class, "[bookBLFlight]## Bl RESPONSE ## :: "+response);
		
	}catch(Exception e) {
		
			DODLog.printStackTrace(e,  NormalBkgDashService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
		}
	return response;
	}

//ATT ConfirmBooking
public GetAttConfirmBookingResponse confirmATTAirBook(PostConfirmAttAirBook attAirBookModel,List<FlightSearchOption> flightOptList) {
	

	
	GetAttConfirmBookingResponse attResponse = new GetAttConfirmBookingResponse();
	
	flightOptList=flightOptList.stream().filter(FlightSearchOption ::getBookingAllow).toList();
	attAirBookModel.setFlightOption(flightOptList);
	
	DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class, "####### POST ATT CONFIRM MODEL :: "+attAirBookModel);
	try {
		String url=PcdaConstant.AIR_BOOK_SERVICE+"/att/book";
		attResponse=restTemplate.postForObject(url, attAirBookModel, GetAttConfirmBookingResponse.class);
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class, "##### ATT CONFIRM RESPONSE :: "+attResponse);
	}catch(Exception e) {
		
		DODLog.printStackTrace(e,  NormalBkgDashService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
	}
	
	return attResponse;
}



// BL ConfirmBooking
public GetBLConfirmBookResponse confirmBLAirBook(PostConfimBLAirBookModel blAirBookModel,List<FlightSearchOption> flightOptList ) {
	

	DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class, "########## POST BL CONFIRM MODEL :: "+blAirBookModel);
	GetBLConfirmBookResponse blResponse = new GetBLConfirmBookResponse();
	
	flightOptList=flightOptList.stream().filter(FlightSearchOption ::getBookingAllow).toList();
	blAirBookModel.setFlightOption(flightOptList);
	
	
	try {
		String url=PcdaConstant.AIR_BOOK_SERVICE+"/bl/book";
		blResponse=restTemplate.postForObject(url, blAirBookModel, GetBLConfirmBookResponse.class);
	}catch(Exception e) {
		
		DODLog.printStackTrace(e,  NormalBkgDashService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
	}
	DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class, "########## BL CONFIRM RESPONSE :: "+blResponse);
	return blResponse;
}



//---------------------AJAX-------------------------------

// ATT fair rule
public String getATTFareRule(String flightKey) {
 return ajaxService.getATTFareRule(flightKey);
}

// BL fair Rule
public String getBLFareRule(String flightKey,String domInt) {
  return ajaxService.getBLFareRule(flightKey, domInt);
}

//RAIL  request-id view 

public GetReqIdResponse getRailBookReqIdData(String requestId) {
    return ajaxService.getRailBookReqIdData(requestId);
}

// AIR request-Id View
public GetAirReqIdResponse getAirBookReqIdData(String requestId) {
	return ajaxService.getAirBookReqIdData(requestId);
}

//PersonalNo view 
	public TravellerProfileResponseModel getViewPersonal(String personalNo, String groupId) {
      return ajaxService.getViewPersonal(personalNo, groupId);
	}
			
// Train route and other info related to train
	public GetSingleTrainRouteResponse getSingleTrainData(String journeyDate , String trainNo) {
		return ajaxService.getSingleTrainData(journeyDate, trainNo);
		}

// FARE and ACCOMODATION details
	public GetFareAccomodateResponse getFareAccmodationDtls(PostFareAccomodationModel postModel) {
		return ajaxService.getFareAccmodationDtls(postModel);
	}

// PINCODE 
	public GetPinCodeResponse getPinCodeDtsl(String pinCode) {
		return ajaxService.getPinCodeDtsl(pinCode);
	}
	
//--------------------AJAX END--------------------------------------------
	
	
public GetNormalCancelResponse cancelNormalRequest(PostNormalCancelModel postNormalCancelModel) {
	GetNormalCancelResponse response = new GetNormalCancelResponse();
	DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class,"###########[cancelNormalRequest] POST NORMAL CANCEL MODEL ######## "+postNormalCancelModel);
	try {
		String url= PcdaConstant.REQUEST_BASE_URL+"/cancelApprovedReq";
		response=restTemplate.postForObject(url, postNormalCancelModel,GetNormalCancelResponse.class);
	}catch( Exception e) {
		DODLog.printStackTrace(e, NormalBkgDashService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
	}
	
	
	DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgDashService.class,"###########[cancelNormalRequest] NORMAL CANCEL RESPONSE ######## "+response);
	return response;
}

//-------------


//----------- pre-search data------

public GetRailPreSearchResponse getPreSearchData(PostRailPreSearchModel preSearchModel) {
	return railService.getPreSearchData(preSearchModel);
}


//-------- trains info after search data------
public GetTrainSearchResponse getTrainsInfo(PostTrainSearchModel trainSearchModel) {
	return railService.getTrainsInfo(trainSearchModel);
}




// pre-confirm data
public GetPreConfirmResponse getPreConfirmData(PostIRPreConfirmModel preConfirmModel) {
  return railService.getPreConfirmData(preConfirmModel);
}


// send for confirm ticket

public GetConfirmIRSearchResponse confirmIRSearch(PostIRSearchConfirmModel irPostModel,HttpServletRequest request) {
	return railService.confirmIRSearch(irPostModel, request);
}
// modify
public Map<String, String> modifyHost(Map<String, String> requestParam,HttpServletRequest request) {

	return railService.modifyHost(requestParam, request);
}

public GetFinalBookRailTktResponse getFinalTrainDtls(PostFinalBookModel finalBookModel) {
	return railService.getFinalTrainDtls(finalBookModel);
}

public String getSessionDtls(String clientTxnId) {
	
	GetSessionDtlsResponse response = new GetSessionDtlsResponse();
	String sessionId="";
	try {
		String url=PcdaConstant.RAIL_BOOK_SERVICE+"/getSessionDetails/"+clientTxnId;
		response=restTemplate.postForObject(url, null, GetSessionDtlsResponse.class);
		if(response!=null && response.getErrorCode()==200 && !response.getResponse().isEmpty()) {
		sessionId= response.getResponse();
		}
	}catch( Exception e) {
		DODLog.printStackTrace(e, NormalBkgDashService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
	}
	return sessionId;
}

}
