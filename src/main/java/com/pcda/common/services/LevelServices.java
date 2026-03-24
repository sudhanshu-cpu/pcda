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

import com.pcda.common.model.Level;
import com.pcda.common.model.LevelResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class LevelServices {

	@Autowired
	private RestTemplate template;
	
	public Optional<Level> getLevel(String levelId) {
		DODLog.info(LogConstant.COMMON_LOG, LevelServices.class, "Get Categories with Level id: " + levelId);

		Level level=null;
		try {
		ResponseEntity<LevelResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/level/getLevel/" + levelId, HttpMethod.GET, null,
				new ParameterizedTypeReference<LevelResponse>() {});
		
		LevelResponse levelResponse = responseEntity.getBody();

		if (null != levelResponse && (levelResponse.getErrorCode() == 200) && null != levelResponse.getResponse()) {
			level = levelResponse.getResponse();
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, LevelServices.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
		}
		return Optional.ofNullable(level);
	}
	
	public List<Level> getLevelByServiceId(String serviceId) {
		DODLog.info(LogConstant.COMMON_LOG, LevelServices.class, "Get Level with service id: " + serviceId);

		List<Level> levelList = new ArrayList<>();
		try {
		ResponseEntity<LevelResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/level/getLevels/" + serviceId, HttpMethod.GET, null,
				new ParameterizedTypeReference<LevelResponse>() {});
		LevelResponse levelResponse = responseEntity.getBody();

		if (null != levelResponse && (levelResponse.getErrorCode() == 200) && null != levelResponse.getResponseList()) {
			levelList = levelResponse.getResponseList();
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, LevelServices.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
		}
		DODLog.info(LogConstant.COMMON_LOG, LevelServices.class, " levelList : " + levelList.size() );
		return levelList;
	}
	
	public List<Level> getLevelByApprovalType(String approvalType) {
		DODLog.info(LogConstant.COMMON_LOG, LevelServices.class, "Get Level with approval type: " + approvalType);

		List<Level> levelList = new ArrayList<>();
		try {
		ResponseEntity<LevelResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/level/getAllLevels/" + approvalType, HttpMethod.GET, null,
				new ParameterizedTypeReference<LevelResponse>() {});
		LevelResponse levelResponse = responseEntity.getBody();

		if (null != levelResponse && (levelResponse.getErrorCode() == 200) && null != levelResponse.getResponseList()) {
			levelList = levelResponse.getResponseList();
			levelList=levelList.stream().filter(e->e.getStatus().equals("ON_LINE")).toList();
		}
	}catch(Exception e) {
		DODLog.printStackTrace(e, LevelServices.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
	}
		DODLog.info(LogConstant.COMMON_LOG, LevelServices.class, " levelList : " + levelList.size() );
		return levelList;
	}
}
