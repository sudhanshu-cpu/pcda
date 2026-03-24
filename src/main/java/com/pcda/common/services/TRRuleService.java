package com.pcda.common.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.TravelRule;
import com.pcda.common.model.TravelRuleResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class TRRuleService {
	
	@Autowired
	private RestTemplate template;

	public TravelRule getTRRuleDetails(String trRuleID) {
		

		
		TravelRule travelRule=null;
try {
		ResponseEntity<TravelRuleResponse> responseEntity=template.getForEntity(PcdaConstant.TR_RULE_URL + "/trRule/getTravelRule/"+trRuleID, TravelRuleResponse.class);
		
		TravelRuleResponse ruleResponse=responseEntity.getBody();
		

		if (null != ruleResponse && ruleResponse.getErrorCode() == 200 && null != ruleResponse.getResponse()) {
			travelRule= ruleResponse.getResponse();
		}
		}catch(Exception e) {
	DODLog.printStackTrace(e, TRRuleService.class, LogConstant.COMMON_LOG);
       }

		return travelRule;
	
	}
	
	

}
