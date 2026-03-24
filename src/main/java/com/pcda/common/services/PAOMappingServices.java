package com.pcda.common.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.PAOMapResponse;
import com.pcda.common.model.PAOMappingModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;
import com.pcda.util.Status;

@Service
public class PAOMappingServices {

	@Autowired
	private RestTemplate template;
	
	public List<PAOMappingModel> getPaoMappingServiceWithApproval(String approvalType) {
		DODLog.info(LogConstant.COMMON_LOG, MasterServices.class, " PAO MAPPING with approval type " + approvalType);

		List<PAOMappingModel> paoModelList = new ArrayList<>();
		try {
		ResponseEntity<PAOMapResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/paomapping/allPAOmap/" + approvalType, HttpMethod.GET, null,
				new ParameterizedTypeReference<PAOMapResponse>() {});
		PAOMapResponse paoMapResponse=responseEntity.getBody();

		if(null != paoMapResponse && (paoMapResponse.getErrorCode()==200) && null!= paoMapResponse.getResponseList()) {
			paoModelList =  paoMapResponse.getResponseList();
			paoModelList=paoModelList.stream().filter(e->e.getStatus().equals(Status.ON_LINE)).toList();
			
		}
		}catch (Exception e) {
			DODLog.printStackTrace(e, PAOMappingServices.class, LogConstant.COMMON_LOG);
		   }
		DODLog.info(LogConstant.COMMON_LOG, OfficesService.class, " paoModelList " + paoModelList.size());
		return paoModelList;
	}
}
