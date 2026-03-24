package com.pcda.mb.finalclaim.claimstatus.sarvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.finalclaim.claimstatus.model.ClaimStatusDataModel;
import com.pcda.mb.finalclaim.claimstatus.model.ClaimStatusModel;
import com.pcda.mb.finalclaim.claimstatus.model.ClaimStatusResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ClaimStatusService {

	@Autowired	
	private RestTemplate restTemplate;
	
	public List<ClaimStatusModel> getAllData(ClaimStatusDataModel claimStatusDataModel) {
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, ClaimStatusService.class,"[getAllData] claimStatusDataModel ## "+claimStatusDataModel);
		List<ClaimStatusModel> airDataList=new ArrayList<>();
		try {
		 String url = PcdaConstant.CLAIM_BASE_URL+
					"/claimStatus/viewStatusOfClaim?personalNo={personalNo}&claimId={claimId}&groupId={groupId}";
		 Map<String, String> params = new HashMap<>();
		 params.put("personalNo", claimStatusDataModel.getPersonalNo());
		 params.put("claimId", claimStatusDataModel.getClaimId());
		 params.put("groupId", claimStatusDataModel.getGroupId());
		
		
		 ResponseEntity<ClaimStatusResponse> response=restTemplate.getForEntity(url, ClaimStatusResponse.class, params);
		 
		 ClaimStatusResponse claimStatusResponse= response.getBody();
			if(claimStatusResponse != null && claimStatusResponse.getErrorCode()== 200 && null!=claimStatusResponse.getResponseList()) {
				
				airDataList=claimStatusResponse.getResponseList();
			}
	
			
		}catch (Exception e) {
			DODLog.printStackTrace(e, ClaimStatusService.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
		}
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, ClaimStatusService.class,"[getAllData] ### Response DataList ###"+airDataList.size());	
		return airDataList;
		 
	}
	
}
