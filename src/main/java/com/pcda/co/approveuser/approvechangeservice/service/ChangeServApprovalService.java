package com.pcda.co.approveuser.approvechangeservice.service;

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

import com.pcda.co.approveuser.approvechangeservice.model.AppDisApproveChangeSerModel;
import com.pcda.co.approveuser.approvechangeservice.model.ChangServiceApprovelResponse;
import com.pcda.co.approveuser.approvechangeservice.model.GetChanSerApprovelModel;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.User;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.UserServices;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ChangeServApprovalService {
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private OfficesService officesService;
	
	@Autowired
	private UserServices userServices;
	
	
	public Optional<User> getUsers(BigInteger userId){
		
		return userServices.getUser(userId);
		
	}
	
	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {
		
	return	officesService.getOfficeByUserId(userId);
		
	}
	
	
	public List<GetChanSerApprovelModel> getChangeService(BigInteger loginUserId){
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServApprovalService.class," loginUserId #########  "+ loginUserId);
		
		List<GetChanSerApprovelModel> getChanSerApprovelModel=new ArrayList<>();
		
		try {
		ChangServiceApprovelResponse response  =   
				
				restTemplate.getForObject(PcdaConstant.CHANGE_SERVICE_URL + "/view/"+
						loginUserId, ChangServiceApprovelResponse.class);
		if(response!=null && response.getErrorCode()==200 && null!=response.getResponseList()) {
			getChanSerApprovelModel = response.getResponseList();
			DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServApprovalService.class,"CHANGE SERVICE APPROVAL DATALIST RESPONSE #########  "+ getChanSerApprovelModel.size());

	}
		
		}catch (Exception e) {
			DODLog.printStackTrace(e, ChangeServApprovalService.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
			
		}
		
		return getChanSerApprovelModel;
	}
	
	
	public void approveChangeSer(AppDisApproveChangeSerModel appDisApproveChangeSerModel) {
		
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServApprovalService.class," appDisApproveChangeSerModel #########  "+ appDisApproveChangeSerModel);
		
		try {
			ChangServiceApprovelResponse response=restTemplate.postForObject(PcdaConstant.CHANGE_SERVICE_URL+"/approve", appDisApproveChangeSerModel, ChangServiceApprovelResponse.class);
		
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServApprovalService.class," CHANGE SERVICE APPROVAL RESPONSE ####"+ response+"    " +"POST CHANGE SERVICE APPROVAL MODEL ####"+appDisApproveChangeSerModel);
		}catch (Exception e) {
			DODLog.printStackTrace(e, getClass(), LogConstant.CHANGE_SERVICE_LOG_FILE);
		}
		
	}
	
	
	
public void disApproveChangeSer(AppDisApproveChangeSerModel appDisApproveChangeSerModel) {
	DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServApprovalService.class," appDisApproveChangeSerModel #########  "+ appDisApproveChangeSerModel);
		try {
			ChangServiceApprovelResponse response =restTemplate.postForObject(PcdaConstant.CHANGE_SERVICE_URL+"/disApprove", appDisApproveChangeSerModel, ChangServiceApprovelResponse.class);
		
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServApprovalService.class, "CHANGE SERVICE DIS-APPROVAL RESPONSE"+response);
		}catch (Exception e) {
			DODLog.printStackTrace(e, getClass(), LogConstant.CHANGE_SERVICE_LOG_FILE);
		}
	}
	
	
	public GetChanSerApprovelModel getDetailsChangeSer(BigInteger loginUserId,String personalNo) {
		
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServApprovalService.class," loginUserId #########  "+ loginUserId);
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServApprovalService.class," personalNo #########  "+ personalNo);
		GetChanSerApprovelModel getChanSerApprovelModel=null;
		
		try {
		ResponseEntity<ChangServiceApprovelResponse> entity = restTemplate.exchange(
				PcdaConstant.CHANGE_SERVICE_URL + "/getData/"+loginUserId+"/"+personalNo, HttpMethod.GET, null,
				new ParameterizedTypeReference<ChangServiceApprovelResponse>() {
				});
		ChangServiceApprovelResponse response = entity.getBody();
		
		if (null != response && response.getErrorCode() == 200 && response.getResponseList() != null) {
			getChanSerApprovelModel=response.getResponse();
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, getClass(), LogConstant.CHANGE_SERVICE_LOG_FILE);
		}
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServApprovalService.class," getChanSerApprovelModel #########  "+ getChanSerApprovelModel);
		return getChanSerApprovelModel;
	}
	

}


