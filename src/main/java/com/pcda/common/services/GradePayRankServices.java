package com.pcda.common.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.GradePayRankModel;
import com.pcda.common.model.GradePayRankResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class GradePayRankServices {
	
	@Autowired
	private RestTemplate template;

	public Optional<GradePayRankModel> getGradePayWithDODRankId(String dodRankId) {
		
		GradePayRankModel gradePayRankModel = new GradePayRankModel();
		try {
		GradePayRankResponse gradePayRankResponse = template.getForObject(
				PcdaConstant.MASTER_BASE_URL + "/gradepayrank/getGradePay/" + dodRankId,
				GradePayRankResponse.class);
		if (null != gradePayRankResponse && gradePayRankResponse.getErrorCode() == 200 && null != gradePayRankResponse.getResponse()) {
			gradePayRankModel = gradePayRankResponse.getResponse();
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, GradePayRankServices.class, LogConstant.COMMON_LOG);
		}
		return Optional.ofNullable(gradePayRankModel);
	}

	// Get all Rank with approval type
	public List<GradePayRankModel> getAllGradePayRankWithApproval(String approvalType) {
		DODLog.info(LogConstant.COMMON_LOG, GradePayRankServices.class, "Get All Grade Pay Rank with approval: " + approvalType);

		List<GradePayRankModel> gradePayRankList = null;
		ResponseEntity<GradePayRankResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/gradepayrank/getAllGradePay/" + approvalType,
				HttpMethod.GET, null, new ParameterizedTypeReference<GradePayRankResponse>() {});
		GradePayRankResponse gradePayRankResponse = responseEntity.getBody();

		if (null != gradePayRankResponse && gradePayRankResponse.getErrorCode() == 200 && null != gradePayRankResponse.getResponseList()) {
			gradePayRankList = new ArrayList<>();
			gradePayRankList.addAll(gradePayRankResponse.getResponseList());
			DODLog.info(LogConstant.COMMON_LOG, GradePayRankServices.class, " gradePayRankList : " + gradePayRankList.size());
		}
		
		return gradePayRankList;
	}

}
