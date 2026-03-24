package com.pcda.mb.travel.journey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.travel.journey.model.TRRulesData;
import com.pcda.mb.travel.journey.model.TRRulesResponse;
import com.pcda.mb.travel.journey.model.TravelIdDetails;
import com.pcda.mb.travel.journey.model.TravelIdDetailsResponse;
import com.pcda.mb.travel.journey.model.TravelIdsData;
import com.pcda.mb.travel.journey.model.TravelIdsResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class TravelIdAndRuleService {
	
	@Autowired
	private RestTemplate template;

	public List<TRRulesData> getTRforTravelType(String travelType, String serviceType, String serviceId, String categoryId,
			String requestType) {
		
		
		
		List<TRRulesData> trRulesDatas=new ArrayList<>();
		try {
		
		ResponseEntity<TRRulesResponse> response= template.getForEntity(
				PcdaConstant.REQUEST_BASE_URL+"/getAllTRforTravelType?travelType={travelType}&serviceType={serviceType}&serviceId={serviceId}&categoryId={categoryId}&requestType={requestType}", 
				TRRulesResponse.class, travelType,Integer.parseInt(serviceType),serviceId,categoryId,requestType);
		
		TRRulesResponse rulesResponse=response.getBody();
		
		if(null!=rulesResponse && rulesResponse.getErrorCode()==200) {
			trRulesDatas.addAll(rulesResponse.getResponseList());
		}
		
		}catch(Exception e) {
			DODLog.printStackTrace(e, TravelIdAndRuleService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class, "###### trRulesDatas::"+trRulesDatas.size());
		return trRulesDatas;
		
	}

	public List<TRRulesData> getTRforTravelGroup(String travelType, String enumCode, String serviceType,
			String serviceId, String categoryId, String requestType) {
		
		
		
		List<TRRulesData> trRulesDatas=new ArrayList<>();
		try {
		ResponseEntity<TRRulesResponse> response= template.getForEntity(
				PcdaConstant.REQUEST_BASE_URL+"/getAllTRforTravelGroup?travelType={travelType}&serviceType={serviceType}&serviceId={serviceId}&categoryId={categoryId}"
						+ "&requestType={requestType}&enumCode={enumCode}", 
						TRRulesResponse.class, travelType,Integer.parseInt(serviceType),serviceId,categoryId,requestType,Integer.parseInt(enumCode));
		
		
		TRRulesResponse rulesResponse=response.getBody();
		
		if(null!=rulesResponse && rulesResponse.getErrorCode()==200 && null!=rulesResponse.getResponseList()) {
			trRulesDatas.addAll(rulesResponse.getResponseList());
		}
		
		}catch(Exception e) {
			DODLog.printStackTrace(e, TravelIdAndRuleService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class, "############# trRulesDatas ######::"+trRulesDatas.size());
		return trRulesDatas;
	}

	public List<TravelIdsData> getTravelIds(String personalNo, String travelTypeId) {
		
		
		List<TravelIdsData> travelIds=new ArrayList<>();
         try {
		ResponseEntity<TravelIdsResponse> response=	template.getForEntity(PcdaConstant.TRAVEL_ID_BASE_URL+"/getTravelIds/"+personalNo+"/"+travelTypeId, TravelIdsResponse.class);
		
		TravelIdsResponse rulesResponse=response.getBody();
		
		if(null!=rulesResponse && rulesResponse.getErrorCode()==200 && null!=rulesResponse.getResponseList()) {
			travelIds.addAll(rulesResponse.getResponseList());
			if(!travelIds.isEmpty()) {
				travelIds.forEach(e -> {
					e.setStartDateStr(CommonUtil.formatDate(e.getStartDate(), "dd/MM/yyyy"));
					e.setEndDateStr(CommonUtil.formatDate(e.getEndDate(), "dd/MM/yyyy"));
				});
			}
		}
		
		travelIds.stream().forEach(obj->obj.setTravelStatus(obj.getTripComplete().ordinal()));
         } catch (Exception e) {
        	 DODLog.printStackTrace(e, TravelIdAndRuleService.class, LogConstant.JOURNEY_REQUEST_LOG);
         }
         DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class, "travelIds::"+travelIds.size());
		return travelIds;
	}

	public TravelIdDetails getTravelIdData(String travelId) {
		
		
		TravelIdDetails travelIdDetails=null;
		try {
		ResponseEntity<TravelIdDetailsResponse> response=template.getForEntity(PcdaConstant.TRAVEL_ID_BASE_URL+"/getTravelRequestId?travelId="+travelId, TravelIdDetailsResponse.class);
		
		TravelIdDetailsResponse detailsResponse=response.getBody();
		
		if(null!=detailsResponse && detailsResponse.getErrorCode()==200) {
			travelIdDetails=detailsResponse.getResponse();
			travelIdDetails.setAuthorityDateStr(CommonUtil.formatDate(travelIdDetails.getAuthorityDate(), "dd/MM/yyyy"));
			travelIdDetails.setTravelStartDate(CommonUtil.formatDate(travelIdDetails.getStartDate(), "dd/MM/yyyy"));
			travelIdDetails.setTravelEndDate(CommonUtil.formatDate(travelIdDetails.getEndDate(), "dd/MM/yyyy"));
			
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, TravelIdAndRuleService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
		return travelIdDetails;
	}

	
}
