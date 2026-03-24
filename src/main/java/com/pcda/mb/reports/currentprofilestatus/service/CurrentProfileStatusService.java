package com.pcda.mb.reports.currentprofilestatus.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.reports.currentprofilestatus.model.CurrentProfileStatusResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;


@Service
public class CurrentProfileStatusService {

	@Autowired
	RestTemplate restTemplate;
	public CurrentProfileStatusResponse getCurrentProfileStatus(String userAlias,String groupId) {
		DODLog.info(LogConstant.CURRENT_PROFILE_STATUS_LOG_FILE, CurrentProfileStatusService.class," ::[getCurrentProfileStatus] userAlias :: " + userAlias +" groupId "+groupId);
		CurrentProfileStatusResponse currentProfileStatusResponse = new CurrentProfileStatusResponse(); 
		try {
			String url = PcdaConstant.USER_BASE_URL+ "/profileStatusByUserAlias?userAlias="+userAlias+"&groupId="+groupId;
			currentProfileStatusResponse = restTemplate.getForObject(url, CurrentProfileStatusResponse.class);
			
		}catch(Exception e) {
			DODLog.printStackTrace(e, CurrentProfileStatusService.class, LogConstant.CURRENT_PROFILE_STATUS_LOG_FILE);
		}
		DODLog.info(LogConstant.CURRENT_PROFILE_STATUS_LOG_FILE, CurrentProfileStatusService.class," ::[getCurrentProfileStatus] currentProfileStatusResponse :: " + currentProfileStatusResponse);	
		return currentProfileStatusResponse;
	}

}
