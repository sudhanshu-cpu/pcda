package com.pcda.co.approveuser.unitmovementapproval.service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.co.approveuser.unitmovementapproval.model.GetUnitMomentApprovalModel;
import com.pcda.co.approveuser.unitmovementapproval.model.PostUnitMomentApprovalModel;
import com.pcda.co.approveuser.unitmovementapproval.model.UnitMomentApprovalResponse;
import com.pcda.co.approveuser.unitmovementapproval.model.UnitUserResp;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class UnitMovementApprovalService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OfficesService officesService;

	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {
		return officesService.getOfficeByUserId(userId);
	}

//Get All Request Unit Movement
	public List<GetUnitMomentApprovalModel> getAllUnitMovmentForApproval(String officeId) {
		
		DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementApprovalService.class," ## officeId" + officeId);
		
		List<GetUnitMomentApprovalModel> getUnitMomentApprovalModel = new ArrayList<>();
		try {
			ResponseEntity<UnitMomentApprovalResponse> response = restTemplate.exchange(
					PcdaConstant.UNIT_MOVEMENT_URL + "/getAllUnitMovement/" + officeId, HttpMethod.GET, null,
					UnitMomentApprovalResponse.class);
			UnitMomentApprovalResponse unitMovementrResponse = response.getBody();

			if (null != unitMovementrResponse && unitMovementrResponse.getErrorCode() == 200
					&& unitMovementrResponse.getResponseList() != null) {
				getUnitMomentApprovalModel = unitMovementrResponse.getResponseList();
                getUnitMomentApprovalModel.forEach(e ->e.setRelocationDateFormat(formatDate(e.getRelocationDate(), "dd/MMM/yyyy")));
			}

			
		} catch (Exception e) {
			DODLog.printStackTrace(e, UnitMovementApprovalService.class, LogConstant.UNIT_MOVEMENT_LOG_FILE);
		}
		DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementApprovalService.class,"## Unit Movement Approval Response " + getUnitMomentApprovalModel.size());
		return getUnitMomentApprovalModel;
	}

	// Date to String
	public static String formatDate(Date date, String format) {

		String dateString = "";

		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			if (date != null) {
				dateString = dateFormat.format(date);
			}
		} catch (Exception e) {
			DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementApprovalService.class, "Exception in Date obj is :: " + e);
		}
		return dateString;
	}

	// Get Movement Details

	public List<String> getviewUserDetails(String movementId) {
		DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementApprovalService.class, " movementId :: " + movementId);
		
		List<String> getUnitMomentApproval = new ArrayList<>();
		try {
			ResponseEntity<UnitUserResp> response = restTemplate.exchange(
					PcdaConstant.UNIT_MOVEMENT_URL + "/getUnitUser/" + movementId, HttpMethod.GET, null,
					UnitUserResp.class);
			UnitUserResp unitMovementrResponse = response.getBody();

			if (null != unitMovementrResponse && unitMovementrResponse.getErrorCode() == 200
					&& unitMovementrResponse.getResponseList() != null) {
				getUnitMomentApproval = unitMovementrResponse.getResponseList();

			}
			DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementApprovalService.class,
					" Unit Movement Details Response" + getUnitMomentApproval.size());
		} catch (Exception e) {
			DODLog.printStackTrace(e, UnitMovementApprovalService.class, LogConstant.UNIT_MOVEMENT_LOG_FILE);
		}

		return getUnitMomentApproval;
	}

//Approval Unit Movement
	public UnitMomentApprovalResponse approveUnitMovement(PostUnitMomentApprovalModel postUnitMomentApprovalModel) {
		
		UnitMomentApprovalResponse response = null;
		try {
			String url = PcdaConstant.UNIT_MOVEMENT_URL;
			if (postUnitMomentApprovalModel.getEvent().equalsIgnoreCase("approve")) {
				url = url + "/approveUnitMovement";
			} else if (postUnitMomentApprovalModel.getEvent().equalsIgnoreCase("disapprove")) {
				url = url + "/disapproveUnitMovement";
			}

			HttpEntity<PostUnitMomentApprovalModel> requestUpdate = new HttpEntity<>(postUnitMomentApprovalModel);
			ResponseEntity<UnitMomentApprovalResponse> responseEnt  = restTemplate.exchange(url, HttpMethod.PUT,
					requestUpdate, UnitMomentApprovalResponse.class);
		    	
			    response=responseEnt.getBody();
		} catch (Exception e) {
			DODLog.printStackTrace(e, UnitMovementApprovalService.class, LogConstant.UNIT_MOVEMENT_LOG_FILE);
		}
		DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementApprovalService.class," ## Response Approve Unit Movment:::::" + response);
		return response;

	}

}
