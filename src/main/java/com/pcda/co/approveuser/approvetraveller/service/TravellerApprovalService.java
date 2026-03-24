package com.pcda.co.approveuser.approvetraveller.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.co.approveuser.approvetraveller.model.ReqTravellerResponse;
import com.pcda.co.approveuser.approvetraveller.model.ReqTravellerSearch;
import com.pcda.co.approveuser.approvetraveller.model.TravellerApprovalModel;
import com.pcda.co.approveuser.approvetraveller.model.TravellerApprovalReq;
import com.pcda.co.approveuser.approvetraveller.model.TravellerApprovalResponse;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class TravellerApprovalService {

	@Autowired
	private OfficesService officesService;

	@Autowired
	RestTemplate restTemplate;

	public List<ReqTravellerSearch> getAllTravlerForApproval(String officeId) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_APPROVAL_LOG_FILE, TravellerApprovalService.class, " officeId: " + officeId);
		List<ReqTravellerSearch> userList = new ArrayList<>();
		ResponseEntity<ReqTravellerResponse> response = restTemplate.exchange(PcdaConstant.TRAVELLER_URL + "/getTravellerForApproval?officeId=" + officeId, HttpMethod.GET,
				null, ReqTravellerResponse.class);
		ReqTravellerResponse travellerResponse = response.getBody();
		if (travellerResponse != null && travellerResponse.getErrorCode() == 200 && travellerResponse.getResponseList() != null) {
			userList.addAll(travellerResponse.getResponseList());
		}
		DODLog.info(LogConstant.TRAVELER_PROFILE_APPROVAL_LOG_FILE, TravellerApprovalService.class, " userList : " + userList.size());
		return userList;
	}

	public void updateTravellerApproval(TravellerApprovalModel travellerApprovalModel) {
		try {
			HttpEntity<TravellerApprovalModel> requestUpdate = new HttpEntity<>(travellerApprovalModel);

			ResponseEntity<TravellerApprovalResponse> response = restTemplate.exchange(PcdaConstant.TRAVELLER_URL + "/approveDisapproveTraveller", HttpMethod.PUT,
					requestUpdate, TravellerApprovalResponse.class);
			TravellerApprovalResponse travellerAppResponse = response.getBody();
			DODLog.info(LogConstant.TRAVELER_PROFILE_APPROVAL_LOG_FILE, TravellerApprovalService.class, "Traveller Approval Response: " + travellerAppResponse);

		} catch (Exception e) {
			DODLog.printStackTrace(e, TravellerApprovalService.class, LogConstant.TRAVELER_PROFILE_APPROVAL_LOG_FILE);
		}
	}

	public TravellerApprovalReq getUserByPersonalNo(String personalNo) {
		ReqTravellerResponse response = restTemplate.getForObject(PcdaConstant.TRAVELLER_URL + "/getTravellerDetails/" + personalNo, ReqTravellerResponse.class) ;
		TravellerApprovalReq user = new TravellerApprovalReq();
		if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
			user = response.getResponse();
		}

		DODLog.info(LogConstant.TRAVELER_PROFILE_APPROVAL_LOG_FILE, TravellerApprovalService.class, "Check personal number: " + personalNo + " ; " + user);
		return user;
	}

	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {
		return officesService.getOfficeByUserId(userId);
	}

}
