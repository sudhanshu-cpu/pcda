package com.pcda.common.services;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.GradePayMappingModel;
import com.pcda.common.model.GradePayMappingResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class GradePayServices {

	@Autowired
	private RestTemplate template;
	
	
	public List<GradePayMappingModel> getGradePayWithServiceAndCategory(String serviceId, String categoryId) {
		

		List<GradePayMappingModel> gradepayList = new ArrayList<>();
		try {
		
		StringJoiner joiner = new StringJoiner("/").add(PcdaConstant.MASTER_BASE_URL)
				.add("gradepaymapping/getGradePayByService")
				.add(serviceId).add(categoryId);
		ResponseEntity<GradePayMappingResponse> responseEntity = template.exchange(joiner.toString(),
				HttpMethod.GET, null, new ParameterizedTypeReference<GradePayMappingResponse>() {});
		GradePayMappingResponse gradePayMappingResponse = responseEntity.getBody();

		if (null != gradePayMappingResponse && gradePayMappingResponse.getErrorCode() == 200 && null != gradePayMappingResponse.getResponseList()) {
			gradepayList = gradePayMappingResponse.getResponseList();
		}
    }catch (Exception e) {
	DODLog.printStackTrace(e, GradePayServices.class, LogConstant.COMMON_LOG);
    }
		DODLog.info(LogConstant.COMMON_LOG, GradePayServices.class, " gradepayList : " + gradepayList.size());	
		return gradepayList;
	}
}
