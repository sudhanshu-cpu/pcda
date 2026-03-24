package com.pcda.mb.requestdashboard.exceptionalbookingdashboard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.reports.userreports.model.TravellerProfileResponseModel;
import com.pcda.mb.requestdashboard.exceptionalbookingdashboard.model.GetExcepUniquePnoResponse;
import com.pcda.mb.requestdashboard.exceptionalbookingdashboard.model.GetExcptnlDataParentModel;
import com.pcda.mb.requestdashboard.exceptionalbookingdashboard.model.GetExcptnlDataResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ExceptionalBkgDashService {

	@Autowired
	private RestTemplate restTemplate;
	
	public List<String> getUniquePersonalNo(String groupId){
		DODLog.info(LogConstant.EXCEPTIONAL_BOOKING_DASHBOARD_LOG_FILE, ExceptionalBkgDashService.class, "EXCEPTIONAL DASH groupId :: "+groupId);
		List<String> uniquePnoList =  new ArrayList<>();
		try {
			String url = PcdaConstant.REQUEST_BASE_URL+"/getExcptnlUniquePersonalNo?groupId="+groupId;
			GetExcepUniquePnoResponse response = restTemplate.getForObject(url, GetExcepUniquePnoResponse.class);
				if(response!=null && response.getErrorCode()==200 && !response.getResponse().isEmpty()) {
					uniquePnoList = response.getResponse();
					
				}
		}catch(Exception e) {
			DODLog.printStackTrace(e,  ExceptionalBkgDashService.class, LogConstant.EXCEPTIONAL_BOOKING_DASHBOARD_LOG_FILE);
		}
		return uniquePnoList;
	}
	
	
	//search data from personal no and request mode
	public GetExcptnlDataResponse getExceptionalData(String personalNo , String requestMode,String groupId) {
		DODLog.info(LogConstant.EXCEPTIONAL_BOOKING_DASHBOARD_LOG_FILE, ExceptionalBkgDashService.class, "######  EXCEPTIONAL PERSONAL NO :: "+personalNo+" REQUEST MODE : "
	+requestMode+"GROUP-ID : "+groupId);
		
		GetExcptnlDataResponse  response = new GetExcptnlDataResponse();
		List<GetExcptnlDataParentModel> newModelList = new ArrayList<>();
		try {
			String url = PcdaConstant.REQUEST_BASE_URL+"/getExcptnlRequesDatabyPersonalNo?personalNo="+personalNo+"&requestMode="+requestMode+"&groupId="+groupId;
			response = restTemplate.getForObject(url, GetExcptnlDataResponse.class);
		   if(response!=null && response.getErrorCode()==200 && null!=response.getResponseList() && !response.getResponseList().isEmpty()) {
				List<GetExcptnlDataParentModel> modelList = response.getResponseList();
				DODLog.info(LogConstant.EXCEPTIONAL_BOOKING_DASHBOARD_LOG_FILE, ExceptionalBkgDashService.class, "EXCEPTIONAL DASH DATA LIST :: "+modelList.size());
				
				for(GetExcptnlDataParentModel model : modelList) {
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
				
			}
		}catch(Exception e) {
			DODLog.printStackTrace(e,  ExceptionalBkgDashService.class, LogConstant.EXCEPTIONAL_BOOKING_DASHBOARD_LOG_FILE);
		}
		
		
		return response;
	}
	
	
	
	//personal No view 
			public TravellerProfileResponseModel getViewPersonal(String personalNo) {
				DODLog.info(LogConstant.EXCEPTIONAL_BOOKING_DASHBOARD_LOG_FILE, ExceptionalBkgDashService.class, "#########  EXCEPTIONAL DASH PNO ## :: "+personalNo);
				TravellerProfileResponseModel response = new TravellerProfileResponseModel() ;
				try {
					 response = restTemplate.getForObject(PcdaConstant.USER_BASE_URL + "/getUserDetailsByUserAlias/" + personalNo, TravellerProfileResponseModel.class);
	
				} catch (Exception e) {
					DODLog.printStackTrace(e, ExceptionalBkgDashService.class,LogConstant.EXCEPTIONAL_BOOKING_DASHBOARD_LOG_FILE);
				}
				

				return response;
			}
	
	
}
