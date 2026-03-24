package com.pcda.mb.travel.exceptionalcancellation.service;

import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.DODServices;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;

import com.pcda.mb.travel.exceptionalcancellation.model.ExpCanBookingResponse;
import com.pcda.mb.travel.exceptionalcancellation.model.ExpCanSearchResponse;
import com.pcda.mb.travel.exceptionalcancellation.model.GetExpCancelChildModel;
import com.pcda.mb.travel.exceptionalcancellation.model.GetExpCancelParentModel;
import com.pcda.mb.travel.exceptionalcancellation.model.GetSearchModel;
import com.pcda.mb.travel.exceptionalcancellation.model.PostExpCanChildModel;
import com.pcda.mb.travel.exceptionalcancellation.model.PostExpCancellationParentModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ExpCancelService {

	
@Autowired
private MasterServices masterServices;
 
@Autowired
private OfficesService officesService;

@Autowired
private RestTemplate restTemplate;
	

	// Get Service Based with Service id
	public DODServices getService(String serviceId) {
		DODServices dodServices = new DODServices();
		try {
			Optional<DODServices> opt = masterServices.getServiceByServiceId(serviceId);

			if (opt.isPresent()) {
				dodServices = opt.get();
			}
			DODLog.info(LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE, ExpCancelService.class,
					" ## SERVICES BASED ON SERVICE ID :: " + dodServices.toString());
			return dodServices;
		} catch (Exception e) {
			DODLog.printStackTrace(e, ExpCancelService.class, LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE);
			return dodServices;
		}
	}
	
	// Get office by user id
	public OfficeModel getOfficeByUserId(BigInteger userId) {
		OfficeModel officeModel = new OfficeModel();
		try {
			Optional<OfficeModel> opt =officesService.getOfficeByUserId(userId);
			if(opt.isPresent()) {
				officeModel=opt.get();	
			}
			 
		} catch (Exception e) {
			DODLog.printStackTrace(e, ExpCancelService.class, LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE);
		}
		DODLog.info(LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE, ExpCancelService.class, " ##[getOfficeByUserId] OFFICE MODEL ::" +officeModel.toString());
		return officeModel;
	}
	
	public List<GetSearchModel> getSearchData(String personalNumber,String groupId) {
		DODLog.info(LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE, ExpCancelService.class, "[getSearchData] ## personalNumber ::" +personalNumber +" #groupId" +groupId);
		
		List<GetSearchModel> model = new ArrayList<>(); 
		
		String url = PcdaConstant.EXCEPTIONAL_CANCELLATION_URL +"/getExceptionalBookingDetails?personalNo="+personalNumber + "&groupId=" +groupId ;
		try {
		ExpCanSearchResponse expCanSearchResponse = restTemplate.getForObject(url, ExpCanSearchResponse.class);
		if(expCanSearchResponse!=null && expCanSearchResponse.getErrorCode()==200 && null!=expCanSearchResponse.getResponseList()) {
		 model = expCanSearchResponse.getResponseList();
		 DODLog.info(LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE, ExpCancelService.class, "[getSearchData] ## expCanSearchResponse list ::" +model.size());
		 return model;
		}
		else {
			  new ExpCanSearchResponse();
		}
		
		}catch(Exception e) {
			DODLog.printStackTrace(e, ExpCancelService.class, LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE);
		}
		
		return model;
	}

// get ExcptionalCancelSingleData	
	
	public GetExpCancelParentModel getExpCanData(String bookingId) {
		
		DODLog.info(LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE, ExpCancelService.class, "[getExpCanData] ## bookingId ::" +bookingId);
		GetExpCancelParentModel model =  new GetExpCancelParentModel();
		
		try {
			
			
			ExpCanBookingResponse response = restTemplate.getForObject(
					new URI(PcdaConstant.EXCEPTIONAL_CANCELLATION_URL + "/getExceptionalTicketDetails?bookingId="+bookingId), ExpCanBookingResponse.class);
			 
			if(response!=null && response.getResponse()!=null && response.getErrorCode()==200) {
				model = response.getResponse();
			}
			
			List<GetExpCancelChildModel> list = model.getPassengerList();
			
			for(GetExpCancelChildModel s : list) {
				
				if(s.getPassangerSex()==0) {
				  s.setPSex("Male"); 
				}
				if(s.getPassangerSex()==1) {
					  s.setPSex("Female"); 
					}
				}
			model.setPassengerList(list);
			
			
		}catch(Exception e) {
			DODLog.printStackTrace(e, ExpCancelService.class, LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE);
		}
		
		return model;
	}

	// to check  at least single passenger is booked in list
public boolean isBooked(GetExpCancelParentModel model ) {
	
	boolean isvalid = false;
	List<GetExpCancelChildModel> arr = model.getPassengerList();
	
	for(GetExpCancelChildModel s : arr) {
		
	if(s.getCurrentCancelStatus().equals("Booked")) {
		isvalid = true;
	   return isvalid;
	}
	}
	DODLog.info(LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE, ExpCancelService.class, "[isBooked] ## isvalid ::" +isvalid);	
	return isvalid;
}
	
public ExpCanBookingResponse getSave(PostExpCancellationParentModel postModel,HttpServletRequest request) {
	 ExpCanBookingResponse response ;	
 List<PostExpCanChildModel> childList =  new ArrayList<>();
 
 int lengthArr = Integer.parseInt(request.getParameter("NoOfCheckBox"));
 DODLog.info(LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE, ExpCancelService.class,"[getSave] ## lengthArr::::: " + lengthArr);
 for(int i=1;i<=lengthArr;i++) {
 
    String check = request.getParameter("checkBox"+i);
    if(check!=null && check.equalsIgnoreCase("on")) {
    PostExpCanChildModel model = new PostExpCanChildModel();
    
    model.setIsOfficial(request.getParameter("isOfficial"+i));
    if(model.getIsOfficial().equals("")) {
    	model.setIsOfficial("0");
    }
    model.setIsOnGovtInt(request.getParameter("isOnGovtInt"+i));
    if(model.getIsOnGovtInt().equals("")) {
    	model.setIsOnGovtInt("0");
    }
    model.setPassangerNo(Integer.parseInt(request.getParameter("passangerNo"+i)));
    
    model.setPassangerAge(Integer.parseInt(request.getParameter("passangerAge"+i)));
    model.setPassangerGender(Integer.parseInt(request.getParameter("passangerGender"+i)));
    model.setPassangerName(request.getParameter("passangerName"+i));
    model.setTrnCoach(request.getParameter("trnCoach"+i));
    model.setTrnSeat(request.getParameter("trnSeat"+i));
    model.setTrnBerth(request.getParameter("trnBerth"+i));
    model.setBookingStatus(request.getParameter("bookingStatus"+i));
    model.setCurrentStatus(request.getParameter("currentStatus"+i));
    model.setCancelStatus(request.getParameter("cancelStatus"+i));
    
    childList.add(model);
    }
    
    }
    postModel.setPassengerList(childList);
    DODLog.info(LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE, ExpCancelService.class,
			"[getSave] ## SAVING EXCEPTIONAL CANCEL MODEL ::::: " + postModel);
 
 try {
	 
	 
  
  response  = restTemplate.postForObject(
		  PcdaConstant.RAIL_CANCELLATION_URL+"/updateForCanRequest", postModel,
		 ExpCanBookingResponse.class);
	 
  DODLog.info(LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE, ExpCancelService.class,"[getSave] ## SAVING response ::::: " + response);
 return response;
 }
 catch(Exception e) {
	 DODLog.printStackTrace(e, ExpCancelService.class, LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE);
 }
 return  new ExpCanBookingResponse();
}


}
