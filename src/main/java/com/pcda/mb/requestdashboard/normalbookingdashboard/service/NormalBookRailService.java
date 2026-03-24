package com.pcda.mb.requestdashboard.normalbookingdashboard.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetConfirmIRSearchResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetFinalBookRailTktResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetPreConfirmParentModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetPreConfirmResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetRailPreSearchResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetRailSearchParentModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetSingleTrainRouteModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetSingleTrainRouteResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetTrainSearchResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostBookingPassangeDetails;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostFinalBookModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostIRPreConfirmModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostIRSearchConfirmModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostRailPreSearchModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostTrainSearchModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.Trainbtwnstnslist;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class NormalBookRailService {

	@Autowired
	private RestTemplate restTemplate;	
	
	@Autowired
	private NormalBkgAjaxService ajaxService;
	
	//----------- pre-search data------

	public GetRailPreSearchResponse getPreSearchData(PostRailPreSearchModel preSearchModel) {
		GetRailPreSearchResponse response = new GetRailPreSearchResponse();
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBookRailService.class,"##########[getPreSearchData] POST RAIL FIRST SEARCH MODEL ########## "+preSearchModel);
		
		
		try {
			String url=PcdaConstant.RAIL_BOOK_SERVICE+"/preSearch";
			response=restTemplate.postForObject(url, preSearchModel,GetRailPreSearchResponse.class);
		}catch( Exception e) {
			DODLog.printStackTrace(e, NormalBookRailService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
		}
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBookRailService.class,"######[[getPreSearchData]] FIRST SEARCH RESPONSE ######"+response);
		return response;
	}


	//-------- trains info after search data------
	public GetTrainSearchResponse getTrainsInfo(PostTrainSearchModel trainSearchModel) {
		GetTrainSearchResponse response = new GetTrainSearchResponse();
			if(trainSearchModel.getToStation()!= null ) {
	String[] arr = trainSearchModel.getToStation().split("\\(");
	   arr=arr[1].split("\\)");
	   trainSearchModel.setToStationCode(arr[0]);
		}
    if(trainSearchModel.getFrmStation()!= null ) {
	String[] frmArr = trainSearchModel.getFrmStation().split("\\(");
	frmArr=frmArr[1].split("\\)");
	   trainSearchModel.setFrmStationCode(frmArr[0]);
		}
    DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBookRailService.class,"######[getTrainsInfo]  POST SECOND RAIL SEARCH MODEL #####"+trainSearchModel);
		try {
			String url=PcdaConstant.RAIL_BOOK_SERVICE+"/searchTrain";
			response=restTemplate.postForObject(url, trainSearchModel,GetTrainSearchResponse.class);
		}catch( Exception e) {
			DODLog.printStackTrace(e, NormalBookRailService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
		}
		if(response!=null && response.getErrorCode()==200) {
			GetRailSearchParentModel parentModel = response.getResponse(); 
			for( Trainbtwnstnslist model : parentModel.getResponseBean()) {
				
				String[] daysArray = model.getDaysOfRun().split("");
				List<String> daysList = Arrays.asList(daysArray);
				model.setRunningDays(daysList);
				
				
			
			}
			
		}
		
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBookRailService.class,"#####[getTrainsInfo] SECOND SEARCH RESPONSE  ####### "+response);
		return response;
	}




	// pre-confirm data
	public GetPreConfirmResponse getPreConfirmData(PostIRPreConfirmModel preConfirmModel) {

		preConfirmModel.setNadult(preConfirmModel.getNoAdult());
		preConfirmModel.setNchild(preConfirmModel.getNoChild());
		preConfirmModel.setNinfant(preConfirmModel.getNoInfant());
		preConfirmModel.setNwsenior(preConfirmModel.getNoWSenior());
		preConfirmModel.setNsenior(preConfirmModel.getNoSenior());
		preConfirmModel.setJrnyDate(preConfirmModel.getJourneyDate());
		preConfirmModel.setJrnyClass(preConfirmModel.getJourneyClass().toString());
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBookRailService.class,"## [getPreConfirmData] POST RAIL PRE_CONFIRM MODEL "+preConfirmModel);
		GetPreConfirmResponse response = new GetPreConfirmResponse();
		try {
			String url =PcdaConstant.RAIL_BOOK_SERVICE+"/irPreConfirmDetails";
			response = restTemplate.postForObject(url, preConfirmModel, GetPreConfirmResponse.class);
			if(response!=null && response.getResponse()!=null) {
			GetPreConfirmParentModel parentModel = response.getResponse();
			 if(parentModel!=null) {
				Map<Integer,String> jrnyClassMap=parentModel.getSearchReqDtls().getJrnyClass();
				for(Map.Entry<Integer, String>  entry: jrnyClassMap.entrySet()) {
					String classValue = entry.getValue();
					String[] arr = classValue.split("\\(");
					arr=arr[1].split("\\)");
				if(arr[0].equals(parentModel.getItinaryReq().getJourneyClass())) {
					
					parentModel.getItinaryReq().setJrnyClass(classValue);
					break;
				}
				}
				
			
				Map<String,String> boardingMap = new LinkedHashMap<>();
				List<GetSingleTrainRouteModel> modelList= new ArrayList<>();
			 
				GetSingleTrainRouteResponse routeResponse=ajaxService.getSingleTrainData(parentModel.getItinaryReq().getJourneyDate(),parentModel.getItinaryReq().getTrainNo());
		
				
			 if(routeResponse!=null && routeResponse.getErrorCode()==200 && routeResponse.getResponseList()!=null && !routeResponse.getResponseList().isEmpty() ) {
					modelList = routeResponse.getResponseList();
					
					for(int i=0;i<modelList.size();i++) {
						if(modelList.get(i).getStationcode().trim().equalsIgnoreCase(parentModel.getItinaryReq().getSrcStnCode().trim())) {
							for(int j=i;j<modelList.size();j++) {
								if(modelList.get(j).getStationcode().trim().equalsIgnoreCase(parentModel.getItinaryReq().getDesStnCode().trim())) {
									boardingMap.put(modelList.get(j).getStationcode(),modelList.get(j).getStationname());
								break;
								}else {
									boardingMap.put(modelList.get(j).getStationcode(),modelList.get(j).getStationname());
								}
							}
						
						}
					}
					if(boardingMap!=null && !boardingMap.isEmpty()) {
					parentModel.getItinaryReq().setBoardingStationMap(boardingMap);
					}else {
						boardingMap.put(parentModel.getItinaryReq().getSrcStnCode(),parentModel.getItinaryReq().getSrcStnName());
						parentModel.getItinaryReq().setBoardingStationMap(boardingMap);	
				}
				}else {
					boardingMap.put(parentModel.getItinaryReq().getSrcStnCode(),parentModel.getItinaryReq().getSrcStnName());
					parentModel.getItinaryReq().setBoardingStationMap(boardingMap);
				}
			 
			 
			 
			 }
			 response.setResponse(parentModel);
			}
		
		}catch( Exception e) {
			DODLog.printStackTrace(e, NormalBookRailService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
		}
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBookRailService.class,"## [getPreConfirmData] PRE_CONFIRM RESPONSE "+response);
		return response;
	}


	// send for confirm ticket

	public GetConfirmIRSearchResponse confirmIRSearch(PostIRSearchConfirmModel irPostModel,HttpServletRequest request) {
		
		
		GetConfirmIRSearchResponse response = new GetConfirmIRSearchResponse();
		List<PostBookingPassangeDetails> passngrList = new ArrayList<>();
		int totalNoPassngr =Integer.parseInt(request.getParameter("totalNoOfPxn"));
		try {
		for(int i=1;i<=totalNoPassngr;i++) {
			
			PostBookingPassangeDetails postPassModel = new PostBookingPassangeDetails();
			String passName=request.getParameter("name_"+i);
			
			postPassModel.setName(passName);
			
			int passAge=Integer.parseInt(request.getParameter("age_"+i));
			
			postPassModel.setAge(passAge);
			
			int passGender=Integer.parseInt(request.getParameter("gender_"+i));
			
			postPassModel.setGender(passGender);
			
			String passPaxType=request.getParameter("pxnType_"+i);
		
			postPassModel.setPxnType(passPaxType);
			
			String passBerthPref=request.getParameter("berthPrefence_"+i);
			
			postPassModel.setBerthPrefence(passBerthPref);
			
			String passIDType=request.getParameter("idCardType_"+i);
			postPassModel.setIdCardType(passIDType);
			
			String passIDNo=request.getParameter("idCardNumber_"+i);
			postPassModel.setIdCardNumber(passIDNo);
			
			String passFoodpref=request.getParameter("foodPref_"+i);
			postPassModel.setFoodPref(passFoodpref);
			
			String passchildBerthFlag=Optional.ofNullable(request.getParameter("childBerthFlag_"+i)).orElse("false");
			
			postPassModel.setChildBerthFlag(Boolean.valueOf(passchildBerthFlag));
			
			passngrList.add(postPassModel);
			
			
		}
		irPostModel.setPxnsDtls(passngrList);
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBookRailService.class,"##[confirmIRSearch] POST RAIL CONFIRM MODEL "+irPostModel);
		String url=PcdaConstant.RAIL_BOOK_SERVICE+"/irConfirmDetails";
		response = restTemplate.postForObject(url, irPostModel, GetConfirmIRSearchResponse.class);
		
		} catch( Exception e) {
			DODLog.printStackTrace(e, NormalBookRailService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
		}
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBookRailService.class,"##[confirmIRSearch] RAIL CONFIRM RESPONSE "+response);
		
		return response;
	}
	// modify
	public Map<String, String> modifyHost(Map<String, String> requestParam,HttpServletRequest request) {
		try {
		if(requestParam.containsKey("wsReturnUrl")) {
		  String returnUrl=requestParam.get("wsReturnUrl");
		  returnUrl=returnUrl.replace("{Host}", request.getHeader("Host"));
	//	  returnUrl=returnUrl.replace("{Host}", request.getHeader("X-Forwarded-Host"));
		  requestParam.put("wsReturnUrl", returnUrl);
		  }
		}catch(Exception e) {
			DODLog.printStackTrace(e, NormalBookRailService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
		}
		return requestParam;
	}

// FINAL  RAIL BOOK 
	public GetFinalBookRailTktResponse getFinalTrainDtls(PostFinalBookModel finalBookModel) {
		GetFinalBookRailTktResponse response = new GetFinalBookRailTktResponse();
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBookRailService.class,
				"##[getFinalTrainDtls] POST FINAL BOOK MODEL ::" +finalBookModel);
		try {
              String url =PcdaConstant.RAIL_BOOK_SERVICE+"/bookRailTicket";			
			response= restTemplate.postForObject(url, finalBookModel,GetFinalBookRailTktResponse.class);
		}catch( Exception e) {
			DODLog.printStackTrace(e, NormalBookRailService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
		}
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBookRailService.class," ##[getFinalTrainDtls] RAIL FINAL BOOK RESPONSE "+response);
		
		return response;
	}
	
	
}
