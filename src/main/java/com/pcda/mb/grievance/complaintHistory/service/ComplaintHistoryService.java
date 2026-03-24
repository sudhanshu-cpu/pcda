package com.pcda.mb.grievance.complaintHistory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.grievance.complaintHistory.model.ComplaintHistoryResponse;
import com.pcda.mb.grievance.complaintHistory.model.GrievancePostBean;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ComplaintHistoryService {
	
	@Autowired
	private RestTemplate restTemplate;

	public ComplaintHistoryResponse getComplaintHistory(GrievancePostBean grievancePostBean) {
		ComplaintHistoryResponse response=null;
		try {
			String url = PcdaConstant.COMMON_GREIVANCE_URL +"/getSpecificGrievance";
	        ResponseEntity<ComplaintHistoryResponse> entity = restTemplate.postForEntity(url, grievancePostBean,ComplaintHistoryResponse.class);
	         response=  entity.getBody();
	       
	        DODLog.info(LogConstant.COMMON_LOG, ComplaintHistoryService.class," History status response:::: " + response);
		} catch (Exception e) {
			DODLog.printStackTrace(e, ComplaintHistoryService.class, LogConstant.COMMON_LOG);
		}
		return response;
		 
		
	}

}
