package com.pcda.co.approveuser.transferinapproval.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.co.approveuser.transferinapproval.model.PosttransferInApprovalModel;
import com.pcda.co.approveuser.transferinapproval.model.TransferInApprovalModel;
import com.pcda.co.approveuser.transferinapproval.model.TransferInApprovalResponse;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class TransferInApprovalService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private OfficesService officesService;
	
	@Autowired
	private RestTemplate template;
	
	
	public Optional<OfficeModel> getOfficeByUserIdTranIn(BigInteger userId) {
		
	return	officesService.getOfficeByUserId(userId);
		
	}
	
	public List<TransferInApprovalModel> getApprovalListTrnIn(String groupId) {
		DODLog.info(LogConstant.TRANSFER_IN_APPROVAL, TransferInApprovalService.class, " groupId ::"+groupId);
		
		List<TransferInApprovalModel> approvalTransferInList = new ArrayList<>();
		try {
			ResponseEntity<TransferInApprovalResponse> responseEntity = restTemplate.exchange(
					PcdaConstant.TRANSFER_SERVICE_URL + "/transferin/getTransferedInProfile/" + groupId, HttpMethod.GET, null,
					new ParameterizedTypeReference<TransferInApprovalResponse>() {
					});

			TransferInApprovalResponse response = responseEntity.getBody();
			
			

			if (response != null && response.getErrorCode() == 200 && null!=response.getResponseList()) {
				approvalTransferInList = response.getResponseList();
				approvalTransferInList.forEach(e->e.setProfileChangeAtrributeSplit(e.getProfileAttributeName().split("@")));
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, TransferInApprovalService.class, LogConstant.TRANSFER_IN_APPROVAL );

		}
		DODLog.info(LogConstant.TRANSFER_IN_APPROVAL, TransferInApprovalService.class, "## ResponseList of Approval transfer in::"+groupId+"::"+approvalTransferInList.size());
		return approvalTransferInList;
	}
	
	public TransferInApprovalResponse updateApprovalTranIn(PosttransferInApprovalModel model) {
		TransferInApprovalResponse trResponse = null;
		try { 
			String url = PcdaConstant.TRANSFER_SERVICE_URL + "/transferin/";
			if (model.getEvent().equalsIgnoreCase("disapprove")) {
				url = url + "disapprove";
			} else if (model.getEvent().equalsIgnoreCase("approve")) {
				url = url + "approve";
			}
			
			HttpEntity<PosttransferInApprovalModel> requestUpdate = new HttpEntity<>(model);
			ResponseEntity<TransferInApprovalResponse> response = template.exchange(url, HttpMethod.PUT, requestUpdate, TransferInApprovalResponse.class);
			    trResponse=response.getBody();
			DODLog.info(LogConstant.TRANSFER_IN_APPROVAL, TransferInApprovalService.class, " ## Response:::::" + response );
		} catch (Exception e) {
			DODLog.printStackTrace(e, TransferInApprovalService.class, LogConstant.TRANSFER_IN_APPROVAL);
		}
                 return trResponse;
 	}
	
	
	
}
