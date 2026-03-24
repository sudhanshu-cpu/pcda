package com.pcda.mb.requestdashboard.railcancellationdashboard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.requestdashboard.railcancellationdashboard.model.GetAbortCancelResponse;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.GetChildRailCanDashbkgData;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.GetRailCanDashBkgResponse;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.GetRailCanDashDataResponse;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.GetRailDashCancelResponse;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.PostRailCanDashModel;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.PostRailDashCancelModel;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class RailCanDashservice {

	
	@Autowired
	private RestTemplate restTemplate;
	
public GetRailCanDashDataResponse getRailCanDashData(PostRailCanDashModel dashModel) {
	GetRailCanDashDataResponse response = new GetRailCanDashDataResponse();
	
	DODLog.info(LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE, RailCanDashservice.class, "RAIL CAN DASH POST DATA MODEL  :: "+dashModel);
	try {
		String url =PcdaConstant.RAIL_CANCELLATION_URL+"/getRailCancltnAprrovedRequest?bookingId={bookingId}&requestId={requestId}&pnrNo={pnrNo}&personalNo={personalNo}&groupId={groupId}";
		
		Map<String ,String> param = new HashMap<>();
		param.put("bookingId", dashModel.getBookingId());
		param.put("requestId", dashModel.getRequestId());
		param.put("pnrNo", dashModel.getPnrNo());
		param.put("personalNo", dashModel.getPersonalNo());
		param.put("groupId", dashModel.getGroupId());
		
		response = restTemplate.getForObject(url, GetRailCanDashDataResponse.class, param);
		
		
	}catch(Exception e) {
		DODLog.printStackTrace(e,  RailCanDashservice.class, LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE);
	}
	
	
	return response;
}
	
public GetRailCanDashBkgResponse getBkgRailCanDashData(String bkgId){
	GetRailCanDashBkgResponse response = new GetRailCanDashBkgResponse();
	try {
		DODLog.info(LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE, RailCanDashservice.class, "[getBkgRailCanDashData] bkgId::"+bkgId);
		String url =PcdaConstant.RAIL_CANCELLATION_URL+"/getTicketDetails?bookingId="+bkgId;
		response=restTemplate.getForObject(url,GetRailCanDashBkgResponse.class);
		
	if(response!=null && response.getErrorCode()==200)
	   { 
	     response.getResponse().setJourneyDateStr(CommonUtil.formatDate(response.getResponse().getJourneyDate(), "dd-MM-yyyy"));
	     response.getResponse().setBookingDateStr(CommonUtil.formatDate(response.getResponse().getBookingDate(), "dd-MM-yyyy"));
	     List<GetChildRailCanDashbkgData> passengerList = response.getResponse().getPassengerList();
	     for(GetChildRailCanDashbkgData passData : passengerList) {
	    	 if(!passData.getCurrentStatus().equalsIgnoreCase("CAN")) {
	    		 response.setAllCan(false);
	    	 }
	    	 
	     }
	     }
	}catch(Exception e) {
		DODLog.printStackTrace(e,  RailCanDashservice.class, LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE);
	}

	return response;
}

//Abort Cancel

public GetAbortCancelResponse sendAbortCancel(String bookingIdAbort, String abortReason) {
	DODLog.info(LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE, RailCanDashservice.class, "ABORT CANCEL BKG-ID :: "+bookingIdAbort+" :: ABORT REASON ::"+abortReason);
	GetAbortCancelResponse response = new  GetAbortCancelResponse();
	try {
		String url =PcdaConstant.RAIL_CANCELLATION_URL+"/abortCancel?bookingId="+bookingIdAbort+"&abortReason="+abortReason;
		response=restTemplate.postForObject(url,null,GetAbortCancelResponse.class);
		DODLog.info(LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE, RailCanDashservice.class, "RAIL CAN DASH ABORT RESPONSE :: "+response);
	}catch(Exception e) {
		DODLog.printStackTrace(e,  RailCanDashservice.class, LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE);
	}
	return response;
}

// Make Cancel String
public String createCancelString(List<GetChildRailCanDashbkgData> passengerList) {
	int listSize = passengerList.size();

StringBuilder cancelString=new StringBuilder();

List<String> arr = new ArrayList<>(); 
try {
for(int j=0;j<6;j++) {
	arr.add("N");
}
for(int i=0;i<listSize;i++) {
	String forCan=passengerList.get(i).getCurrentCancelStatus();
	if(forCan.equals("For Cancellation")) {
	int number=passengerList.get(i).getPassangerNo();
	 number=number-1;
	 arr.remove(number);
	 arr.add(number,"Y");
	}
}
for(int i =0; i<arr.size(); i++) {
	cancelString.append(arr.get(i));
}

DODLog.info(LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE, RailCanDashservice.class, "RAIL CAN DASH CANCEL STRING :: "+cancelString);
}catch(Exception e) {
	DODLog.printStackTrace(e,  RailCanDashservice.class, LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE);
}
	return cancelString.toString();
}

// final cancel call
public GetRailDashCancelResponse sendRailDashTktCancel(PostRailDashCancelModel postCancelModel) {
	
	GetRailDashCancelResponse response = new GetRailDashCancelResponse();
	DODLog.info(LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE, RailCanDashservice.class, "RAIL CAN DASH FINAL CANCEL MODEL :: "+postCancelModel);
	try {
		String url=PcdaConstant.RAIL_BOOK_SERVICE+"/irTicketCancel";
		response=restTemplate.postForObject(url, postCancelModel,GetRailDashCancelResponse.class);
		DODLog.info(LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE, RailCanDashservice.class, "RAIL CAN DASH RESPONSE AFTER CAN REQ :: "+response);

	}catch(Exception e) {
		DODLog.printStackTrace(e,  RailCanDashservice.class, LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE);
	}
	
	return response;
}




}
