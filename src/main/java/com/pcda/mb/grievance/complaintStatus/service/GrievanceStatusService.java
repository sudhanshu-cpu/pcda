package com.pcda.mb.grievance.complaintStatus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.grievance.complaintStatus.model.ComplaintStatusResponse;
import com.pcda.mb.grievance.complaintStatus.model.GrievanceViewBean;
import com.pcda.mb.travel.journey.model.StringResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class GrievanceStatusService {
	
	@Autowired
	private RestTemplate restTemplate;

	public GrievanceViewBean  getAllData(String compNo,String groupId) {
	    GrievanceViewBean grievanceStatus =null;
	    try {
	        String url = PcdaConstant.COMMON_GREIVANCE_URL + "/getGrStatusCompNo?compNo=" + compNo + "&groupId=" + groupId;
	        ResponseEntity<ComplaintStatusResponse> response = restTemplate.getForEntity(url, ComplaintStatusResponse.class);
	        ComplaintStatusResponse statusResponse = response.getBody();
	        
	        if (statusResponse != null && statusResponse.getErrorCode() == 200 && statusResponse.getResponse() != null) {
	            grievanceStatus = statusResponse.getResponse();
	        }
	        DODLog.info(LogConstant.COMMON_LOG, GrievanceStatusService.class," Grievance status response:::: " + grievanceStatus);
	    } catch (Exception e) {
	        DODLog.printStackTrace(e, GrievanceStatusService.class, LogConstant.COMMON_LOG);
	    }
	    return grievanceStatus;
	}

	
	public List<String> getSuggestionData(String complaintId) {
		try {
			String url=PcdaConstant.COMMON_GREIVANCE_URL +"/getSearchResult?searchString=" + complaintId;
			ResponseEntity<StringResponse> forEntity = restTemplate.getForEntity(url, StringResponse.class);
			StringResponse response = forEntity.getBody();
			return response.getResponseList();
			
			
		} catch (Exception e) {
			DODLog.printStackTrace(e, GrievanceStatusService.class, LogConstant.COMMON_LOG);
		}
		return null;
		
	}

}


