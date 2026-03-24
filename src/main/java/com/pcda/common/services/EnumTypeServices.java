package com.pcda.common.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.EnumType;
import com.pcda.common.model.EnumTypeResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class EnumTypeServices {

	@Autowired
	private RestTemplate template;
	
	public List<EnumType> getEnumType(String enumName) {
		DODLog.info(LogConstant.COMMON_LOG, EnumTypeServices.class, "Get Enum list: " + enumName);

		List<EnumType> allEnum = new ArrayList<>();
		try {
		ResponseEntity<EnumTypeResponse> listResponse = template.exchange(PcdaConstant.MASTER_BASE_URL + "/enum/" + enumName, HttpMethod.GET, null, 
				new ParameterizedTypeReference<EnumTypeResponse>() {});
		EnumTypeResponse enumTypeResponse = listResponse.getBody();

		if (null != enumTypeResponse && enumTypeResponse.getErrorCode()==200 && enumTypeResponse.getResponseList() != null) {
			allEnum = enumTypeResponse.getResponseList();
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, EnumTypeServices.class, LogConstant.COMMON_LOG);
		}
		return allEnum;
	}
}
