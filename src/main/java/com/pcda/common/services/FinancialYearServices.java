package com.pcda.common.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.FinancialYearModel;
import com.pcda.common.model.FinancialYearResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class FinancialYearServices {

	@Autowired
	private RestTemplate template;
	
	public  List<FinancialYearModel> getFinancialYearBrowse() {
		List<FinancialYearModel> financialBrowseList = new ArrayList<>();
		try {
		ResponseEntity<FinancialYearResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/FinancialYear/allFinancialYear/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<FinancialYearResponse>() {
				});
		FinancialYearResponse financialYearResponse=responseEntity.getBody();
		
		if(null != financialYearResponse && (financialYearResponse.getErrorCode()==200) && null!= financialYearResponse.getResponseList()) {
		financialBrowseList = financialYearResponse.getResponseList();
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, FinancialYearServices.class, LogConstant.COMMON_LOG);
		}
		DODLog.info(LogConstant.COMMON_LOG, CodeHeadServices.class, " financialBrowseList : " + financialBrowseList.size());
		return financialBrowseList;
	}
}
