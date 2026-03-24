package com.pcda.common.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.LevelEntitlementModel;
import com.pcda.common.model.LevelEntitlementResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class LevelEntitlementServices {

	@Autowired
	private RestTemplate template;
	
	public List<LevelEntitlementModel> getAllLevelEntWithApproval(String approvalType) {
		DODLog.info(LogConstant.COMMON_LOG, LevelEntitlementServices.class, "[getAllLevelEntWithApproval] ## Get all level entitlement with approval type: " + approvalType);
		List<LevelEntitlementModel> levelEntitlementList = null;
		try {
		
		ResponseEntity<LevelEntitlementResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/levelEntitlement/getAllLevelEntitlement/" + approvalType, HttpMethod.GET, null,
				new ParameterizedTypeReference<LevelEntitlementResponse>() {});
		LevelEntitlementResponse levelEntitlementResponse = responseEntity.getBody();

		if (null != levelEntitlementResponse && (levelEntitlementResponse.getErrorCode() == 200) && null != levelEntitlementResponse.getResponseList()) {
			levelEntitlementList = levelEntitlementResponse.getResponseList();
			DODLog.info(LogConstant.COMMON_LOG, LevelEntitlementServices.class, " [getAllLevelEntWithApproval] ## levelEntitlementList : " + levelEntitlementList.size());
		}
		
        }catch(Exception e) {
        	DODLog.printStackTrace(e, LevelEntitlementServices.class, LogConstant.COMMON_LOG);
       }
		
		return levelEntitlementList;
	}
}
