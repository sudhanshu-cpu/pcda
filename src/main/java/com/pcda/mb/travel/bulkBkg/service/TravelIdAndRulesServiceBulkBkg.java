package com.pcda.mb.travel.bulkBkg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.travel.bulkBkg.model.TRRulesBulkBkgResponse;
import com.pcda.mb.travel.bulkBkg.model.TRRulesDataBulkBkg;
import com.pcda.mb.travel.journey.service.TravelIdAndRuleService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class TravelIdAndRulesServiceBulkBkg {
	
	@Autowired
	private RestTemplate template;

	public List<TRRulesDataBulkBkg> getTRforTravelGroup(String travelType, String enumCode, String serviceType,
			String serviceId, String categoryId, String requestType) {
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class, " travelType:: "+travelType+" serviceType:: "+serviceType+ " serviceId:: "+serviceId
				+" categoryId:: "+categoryId+ " enumCode:: "+enumCode+ " requestType:: "+requestType);
		
		List<TRRulesDataBulkBkg> trRulesDatas=new ArrayList<>();
		try {
		ResponseEntity<TRRulesBulkBkgResponse> response= template.getForEntity(
				PcdaConstant.REQUEST_BASE_URL+"/getAllTRforTravelGroup?travelType={travelType}&serviceType={serviceType}&serviceId={serviceId}&categoryId={categoryId}"
						+ "&requestType={requestType}&enumCode={enumCode}", 
						TRRulesBulkBkgResponse.class, travelType,Integer.parseInt(serviceType),serviceId,categoryId,requestType,Integer.parseInt(enumCode));
		
		
		TRRulesBulkBkgResponse rulesResponse=response.getBody();
		
		if(null!=rulesResponse && rulesResponse.getErrorCode()==200 && null!=rulesResponse.getResponseList()) {
			trRulesDatas.addAll(rulesResponse.getResponseList());
		}
		
		}catch(Exception e) {
			DODLog.printStackTrace(e, TravelIdAndRuleService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class, "############# trRulesDatas ######::"+trRulesDatas.size());
		return trRulesDatas;
	}

	public List<TRRulesDataBulkBkg> getTRforTravelTypeBulkBkg(String travelType, String serviceType, String serviceId,
			String categoryId, String requestType) {
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class, "travelType::"+travelType);
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class, "serviceType::"+serviceType);
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class, "serviceId::"+serviceId);
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class, "categoryId::"+categoryId);
		
		List<TRRulesDataBulkBkg> trRulesDatas=new ArrayList<>();
		try {
		
		ResponseEntity<TRRulesBulkBkgResponse> response= template.getForEntity(
				PcdaConstant.REQUEST_BASE_URL+"/getAllTRforTravelType?travelType={travelType}&serviceType={serviceType}&serviceId={serviceId}&categoryId={categoryId}&requestType={requestType}", 
				TRRulesBulkBkgResponse.class, travelType,Integer.parseInt(serviceType),serviceId,categoryId,requestType);
		
		TRRulesBulkBkgResponse rulesResponse=response.getBody();
		
		if(null!=rulesResponse && rulesResponse.getErrorCode()==200) {
			trRulesDatas.addAll(rulesResponse.getResponseList());
		}
		
		}catch(Exception e) {
			DODLog.printStackTrace(e, TravelIdAndRuleService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class, "###### trRulesDatas::"+trRulesDatas.size());
		return trRulesDatas;
		
	}
	
	

}
