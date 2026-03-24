package com.pcda.common.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.PersonalNoPrefix;
import com.pcda.common.model.PersonalNoPrefixResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class PersonalNoPrefixServices {

	@Autowired
	private RestTemplate template;
	
	public List<PersonalNoPrefix> getPersonalNoPrefixWithApprovalType(String approvalType) {
		DODLog.info(LogConstant.COMMON_LOG, MasterServices.class, "Get Personal Number Prefix with approval type: " + approvalType);

		List<PersonalNoPrefix> personalNoPrefixList = new ArrayList<>();
		try {
		ResponseEntity<PersonalNoPrefixResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/personalnoprefix/getAllPersonalNoPrefix/"+approvalType, HttpMethod.GET, null,
				new ParameterizedTypeReference<PersonalNoPrefixResponse>() {});

		PersonalNoPrefixResponse prefixResponse = responseEntity.getBody();

		if (null != prefixResponse && prefixResponse.getErrorCode() == 200 && null != prefixResponse.getResponseList()) {
			personalNoPrefixList = prefixResponse.getResponseList();
		}
		}catch (Exception e) {
			DODLog.printStackTrace(e, PersonalNoPrefixServices.class, LogConstant.COMMON_LOG);
		   }
		DODLog.info(LogConstant.COMMON_LOG, MasterServices.class, " personalNoPrefixList " + personalNoPrefixList.size());
		return personalNoPrefixList;
	}

}
