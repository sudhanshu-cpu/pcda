package com.pcda.mb.finalclaim.settledclaims.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.finalclaim.settledclaims.model.SettledClaimModel;
import com.pcda.mb.finalclaim.settledclaims.model.SettledClaimResponse;
import com.pcda.mb.finalclaim.settledclaims.model.SettledModel;
import com.pcda.mb.finalclaim.settledclaims.model.ViewClaimLeaveDtlsBean;
import com.pcda.mb.finalclaim.settledclaims.model.ViewClaimRequestBean;
import com.pcda.mb.finalclaim.settledclaims.model.ViewClaimRequestBeanResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class SettledClaimService {

	@Autowired	
	private RestTemplate restTemplate;
	
	public List<SettledClaimModel> getAllDataSettled(SettledModel settledModel) {
		DODLog.info(LogConstant.SETTLED_CLAIM_LOG_FILE, SettledClaimService.class,"[getAllDataSettled] ## settledModel "+settledModel);
		List<SettledClaimModel> settledClaimModels=new ArrayList<>();
		try {
		 String url = PcdaConstant.CLAIM_BASE_URL+
					"/claimSettled/viewTaDaApprovedClaimList?personalNo={personalNo}&claimId={claimId}&groupId={groupId}";
		 Map<String, String> params = new HashMap<>();
		 params.put("personalNo", settledModel.getPersonalNo());
		 params.put("claimId", settledModel.getClaimId());
		 params.put("groupId", settledModel.getGroupId());
		
		 
		 ResponseEntity<SettledClaimResponse> response=restTemplate.getForEntity(url, SettledClaimResponse.class, params);
		 
		 SettledClaimResponse settledClaimResponse= response.getBody();
			if(settledClaimResponse != null && settledClaimResponse.getErrorCode()== 200 && null!=settledClaimResponse.getResponseList()) {
				settledClaimModels=settledClaimResponse.getResponseList();
			}
		
			
		}catch (Exception e) {
			DODLog.printStackTrace(e, SettledClaimService.class, LogConstant.SETTLED_CLAIM_LOG_FILE);
		}
		DODLog.info(LogConstant.SETTLED_CLAIM_LOG_FILE, SettledClaimService.class,"[getAllDataSettled] ## "+settledClaimModels.size());
		return settledClaimModels;
		 
	}
	
	
public ViewClaimRequestBean getviewDataSettled(String tadaclaimId) {
	DODLog.info(LogConstant.SETTLED_CLAIM_LOG_FILE, SettledClaimService.class,"[getviewDataSettled]## tadaclaimId "+tadaclaimId);
	ViewClaimRequestBean tadaClaimSettledModel=new ViewClaimRequestBean();
	
		try {
		 String url = PcdaConstant.CLAIM_BASE_URL+
					"/claimRequest/ViewByClaimId?tadaClaimId=";
		
		 ViewClaimRequestBeanResponse tadaClaimSettledResponse=restTemplate.getForObject(url+tadaclaimId, ViewClaimRequestBeanResponse.class, tadaClaimSettledModel);
		 
			if(tadaClaimSettledResponse != null && tadaClaimSettledResponse.getErrorCode()== 200) {
				tadaClaimSettledModel=tadaClaimSettledResponse.getResponse();
				if(tadaClaimSettledModel.getClaimLeaveDtls()!=null) {
					
					Set<ViewClaimLeaveDtlsBean> sortedHashSet = tadaClaimSettledModel.getClaimLeaveDtls().stream()
					        .sorted(Comparator.comparing(ViewClaimLeaveDtlsBean::getLeaveDate))
					        .collect(Collectors.toCollection(LinkedHashSet::new));
					
					tadaClaimSettledModel.setClaimLeaveDtls(sortedHashSet);
				}
			}
		}catch (Exception e) {
			DODLog.printStackTrace(e, SettledClaimService.class, LogConstant.SETTLED_CLAIM_LOG_FILE);
		}
		DODLog.info(LogConstant.SETTLED_CLAIM_LOG_FILE, SettledClaimService.class,"[getviewDataSettled] ## tadaClaimSettledModel :: "+tadaClaimSettledModel);
		return tadaClaimSettledModel;
		 
	}
	
}
