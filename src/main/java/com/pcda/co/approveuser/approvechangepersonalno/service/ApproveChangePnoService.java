package com.pcda.co.approveuser.approvechangepersonalno.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.co.approveuser.approvechangepersonalno.model.ApprovechangePnoResponse;
import com.pcda.co.approveuser.approvechangepersonalno.model.GetApproveChangePnoModel;
import com.pcda.co.approveuser.approvechangepersonalno.model.PostApproveModel;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.mb.adduser.changepersonalno.controller.ChangePersonalNoController;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ApproveChangePnoService {
	
@Autowired
private RestTemplate restTemplate;


@Autowired
private OfficesService officesService;

// Get office by user id
public OfficeModel getOfficeByUserId(BigInteger userId) {
	OfficeModel officeModel = new OfficeModel();
	try {
		Optional<OfficeModel> opt =officesService.getOfficeByUserId(userId);
		if(opt.isPresent()) {
			officeModel=opt.get();	
		}
		 
	} catch (Exception e) {
		DODLog.printStackTrace(e, ApproveChangePnoService.class, LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
	}
	DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ApproveChangePnoService.class, "OFFICE MODEL ::" +officeModel.toString());
	return officeModel;
}

public List<GetApproveChangePnoModel> getApproveChangePnoData(String groupId) {
	DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ApproveChangePnoService.class," groupId :: " + groupId);	
	List<GetApproveChangePnoModel> modelList = new ArrayList<>();

	try {
			
			ResponseEntity<ApprovechangePnoResponse> responseEntity = restTemplate.exchange(
					PcdaConstant.CHANGE_PERSONAL_NO_URL + "/view/"+groupId, HttpMethod.GET, null,
					new ParameterizedTypeReference<ApprovechangePnoResponse>() {
					});

			ApprovechangePnoResponse response = responseEntity.getBody();
	if(response!=null && response.getErrorCode()==200 && null!=response.getResponseList()) {
		modelList = response.getResponseList();
     }
	
	}catch(Exception e) {
		 DODLog.printStackTrace(e, ApproveChangePnoService.class,LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
	}
	DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ApproveChangePnoService.class," modelList :: " + modelList.size());	
	return modelList;
}

public void sendApprove(PostApproveModel approveModel) {
	
	 try {
			DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ApproveChangePnoService.class,
					" ## CHANGE PERSONAL NO APPROVAL SERVICE MODEL :: " + approveModel);
		 restTemplate.postForObject(PcdaConstant.CHANGE_PERSONAL_NO_URL + "/approve",approveModel,ApprovechangePnoResponse.class);
	 }
	 catch(Exception e) {
		 DODLog.printStackTrace(e, ApproveChangePnoService.class,LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
		 
	 }
	
}
	
public void sendDisapprove(PostApproveModel disApproveModel) {
	
	 try {
			DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoController.class,
					"## CHANGE PERSONAL NO DISAPPROVAL SERVICE MODEL :: " + disApproveModel);
		 restTemplate.postForObject(PcdaConstant.CHANGE_PERSONAL_NO_URL + "/disApprove",disApproveModel,ApprovechangePnoResponse.class);
	 }
	 catch(Exception e) {
		 DODLog.printStackTrace(e, ApproveChangePnoService.class,LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
		 
	 }
	
}
}
