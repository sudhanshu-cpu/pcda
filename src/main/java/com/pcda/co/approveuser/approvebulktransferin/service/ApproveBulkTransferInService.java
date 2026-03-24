package com.pcda.co.approveuser.approvebulktransferin.service;

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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.pcda.co.approveuser.approvebulktransferin.model.BulkTransferApprovalModel;
import com.pcda.co.approveuser.approvebulktransferin.model.BulkTransferInApprovalResponse;
import com.pcda.co.approveuser.approvebulktransferin.model.BulkTransferUserViewBean;
import com.pcda.co.approveuser.approvebulktransferin.model.BulkTransferUserViewResponse;
import com.pcda.co.approveuser.approvebulktransferin.model.PostBulkTransferInApprovalModel;
import com.pcda.co.approveuser.unitmovementapproval.service.UnitMovementApprovalService;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ApproveBulkTransferInService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private OfficesService officesService;

	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {
		return officesService.getOfficeByUserId(userId);
	}
	
	public List<BulkTransferApprovalModel> getAllApproval(String officeId) {


		List<BulkTransferApprovalModel> transferInApprovalModel = new ArrayList<>();
		try {
			
			String url=PcdaConstant.TRANSFER_SERVICE_URL +"/getBulkTransferProfile/" + officeId;
			ResponseEntity<BulkTransferInApprovalResponse> response = restTemplate.exchange(
					url, HttpMethod.GET, null,
					BulkTransferInApprovalResponse.class);
			BulkTransferInApprovalResponse bulkTransferInApprovalResponse = response.getBody();

			if (null != bulkTransferInApprovalResponse && bulkTransferInApprovalResponse.getErrorCode() == 200
					&& bulkTransferInApprovalResponse.getResponseList() != null) {
				transferInApprovalModel = bulkTransferInApprovalResponse.getResponseList();
				transferInApprovalModel.forEach(approvalModel->approvalModel.setTransferAuthorityDateFormat(formatDate(approvalModel.getTransferAuthorityDate(), "dd/MMM/yyyy")));	
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, ApproveBulkTransferInService.class, LogConstant.TRANSFER_IN_APPROVAL);
		}

		return transferInApprovalModel;
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
			DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementApprovalService.class,
					"Exception in Date obj is :: " + e);
		}
		return dateString;
	}

	public List<BulkTransferUserViewBean> getviewUserDetails(String transferId) {
		DODLog.info(LogConstant.TRANSFER_IN_APPROVAL, ApproveBulkTransferInService.class,"transferId:: "+ transferId);
		List<BulkTransferUserViewBean> bulkTransferInUserApproval = new ArrayList<>();
		String url=PcdaConstant.TRANSFER_SERVICE_URL +"/bulkTransferInView/"+ transferId;
		try {
			ResponseEntity<BulkTransferUserViewResponse> response = restTemplate.exchange(
					url, HttpMethod.GET, null,
					BulkTransferUserViewResponse.class);

			BulkTransferUserViewResponse userResponse = response.getBody();
			if (userResponse != null && userResponse.getErrorCode() == 200 && userResponse.getResponseList() != null) {
				bulkTransferInUserApproval = userResponse.getResponseList();
			}
			DODLog.info(LogConstant.TRANSFER_IN_APPROVAL, ApproveBulkTransferInService.class,"bulkTransferInUserApproval:: "+ bulkTransferInUserApproval.size());
		} catch (RestClientException e) {
			DODLog.printStackTrace(e, ApproveBulkTransferInService.class, LogConstant.TRANSFER_IN_APPROVAL);
		}

		return bulkTransferInUserApproval;
	}

	public BulkTransferInApprovalResponse approveTransferIn(PostBulkTransferInApprovalModel postApprovalModel) {
		BulkTransferInApprovalResponse bulkTransferInApprovalResponse = new BulkTransferInApprovalResponse();
		DODLog.info(LogConstant.TRANSFER_IN_APPROVAL, ApproveBulkTransferInService.class,"postApprovalModel :: "+ postApprovalModel);
		try {
			
			String url = PcdaConstant.TRANSFER_SERVICE_URL;
			if (postApprovalModel.getEvent().equalsIgnoreCase("approve")) {
				url = url + "/approveBulkTransferIn";
			} else if (postApprovalModel.getEvent().equalsIgnoreCase("disapprove")) {
				url = url + "/disApproveBulkTransferIn";
			}
			HttpEntity<PostBulkTransferInApprovalModel> httpRequest = new HttpEntity<>(postApprovalModel);
			ResponseEntity<BulkTransferInApprovalResponse> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,
					httpRequest, BulkTransferInApprovalResponse.class);
			
			bulkTransferInApprovalResponse= responseEntity.getBody();
				
		} catch (Exception e) {
			DODLog.printStackTrace(e, ApproveBulkTransferInService.class, LogConstant.TRANSFER_IN_APPROVAL);
		}
		return bulkTransferInApprovalResponse;

	}
}
