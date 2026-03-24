package com.pcda.common.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.CodeHead;
import com.pcda.common.model.CodeHeadResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class CodeHeadServices {

	@Autowired
	private RestTemplate template;
	
	public List<CodeHead> getCodeHeadByApproval(String approvalType) {
		DODLog.info(LogConstant.COMMON_LOG, CodeHeadServices.class, 
				"Get code head with approval type: " + approvalType);
		List<CodeHead> codeHeadList = null;
		try {
		ResponseEntity<CodeHeadResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/codehead/allCodehead/"+approvalType, HttpMethod.GET, null,
				new ParameterizedTypeReference<CodeHeadResponse>() {
				});
		CodeHeadResponse codeHeadResponse = responseEntity.getBody();

		if (null != codeHeadResponse && codeHeadResponse.getErrorCode() == 200
				&& null != codeHeadResponse.getResponseList()) {
			codeHeadList = codeHeadResponse.getResponseList();
			DODLog.info(LogConstant.COMMON_LOG, CodeHeadServices.class, " codeHeadList: " + codeHeadList.size());
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, CodeHeadServices.class, LogConstant.COMMON_LOG);
		}
		
		
		return codeHeadList;
	}
	
	public Optional<CodeHead> getCodeHeadByService(String serviceId,String categoryId ,String travelType) {
		
		DODLog.info(LogConstant.COMMON_LOG, CodeHeadServices.class, 
				"Get code head with serviceId: " + serviceId+" | categoryId:"+categoryId+" | travelType:"+travelType);
		
		CodeHead codeHead = null;
		try {
		ResponseEntity<CodeHeadResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/codehead/getCodeheadByService/"+serviceId+"/"+categoryId+"/"+travelType, HttpMethod.GET, null,
				new ParameterizedTypeReference<CodeHeadResponse>() {
				});
		CodeHeadResponse codeHeadResponse = responseEntity.getBody();

		if (null != codeHeadResponse && codeHeadResponse.getErrorCode() == 200 && null != codeHeadResponse.getResponse()) {
			codeHead = codeHeadResponse.getResponse();
			
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, CodeHeadServices.class, LogConstant.COMMON_LOG);
		}
		return Optional.ofNullable(codeHead);
	}
	
}
