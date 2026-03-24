package com.pcda.co.approveuser.transferoutapproval.service;

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

import com.pcda.co.approveuser.transferinapproval.service.TransferInApprovalService;
import com.pcda.co.approveuser.transferoutapproval.model.PostAppDisAppTransferOutModel;
import com.pcda.co.approveuser.transferoutapproval.model.TransferOutApprovalModel;
import com.pcda.co.approveuser.transferoutapproval.model.TransferOutApprovalResponse;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class TransferOutApprovalService {

	
	@Autowired
	private OfficesService officesService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public Optional<OfficeModel> getOfficeByUserIdTranOut(BigInteger userId) {
		
		return	officesService.getOfficeByUserId(userId);
			
		}
		
		public List<TransferOutApprovalModel> getApprovalListTrnOut(String groupId) {
			DODLog.info(LogConstant.TRANSFER_OUT_APPROVAL, TransferOutApprovalService.class, "  groupId ::"+groupId);

			List<TransferOutApprovalModel> approvalTransferOutList = new ArrayList<>();
			try {
				ResponseEntity<TransferOutApprovalResponse> responseEntity = restTemplate.exchange(
						PcdaConstant.TRANSFER_SERVICE_URL + "/transferout/getTransferedOutProfile/" + groupId, HttpMethod.GET, null,
						new ParameterizedTypeReference<TransferOutApprovalResponse>() {
						});

				TransferOutApprovalResponse response = responseEntity.getBody();
				
				
				
				if (response != null && response.getErrorCode() == 200 && null!=response.getResponseList()) {
					approvalTransferOutList = response.getResponseList();
					approvalTransferOutList.forEach(e->e.setProfileChangeAtrributeSplit(e.getProfileAttributeName().split("@")));
				}

			} catch (Exception e) {
				DODLog.printStackTrace(e, TransferOutApprovalService.class, LogConstant.TRANSFER_OUT_APPROVAL );

			}
			DODLog.info(LogConstant.TRANSFER_OUT_APPROVAL, TransferOutApprovalService.class, "## Request size transferOut App ::"+approvalTransferOutList.size());
			return approvalTransferOutList;
		}
		public TransferOutApprovalResponse updateApprovalTraOut(PostAppDisAppTransferOutModel model) {
			TransferOutApprovalResponse toutResponse = null;
			
			try { 
				String url = PcdaConstant.TRANSFER_SERVICE_URL + "/transferout/";
				if (model.getEvent().equalsIgnoreCase("disapprove")) { 
	
					url = url + "disapprove";
				} else if (model.getEvent().equalsIgnoreCase("approve")) {
					url = url + "approve";
				}
				
				
				HttpEntity<PostAppDisAppTransferOutModel> requestUpdate = new HttpEntity<>(model);
				
				
				ResponseEntity<TransferOutApprovalResponse> response = restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, TransferOutApprovalResponse.class);
				 toutResponse=response.getBody();
		
				DODLog.info(LogConstant.TRANSFER_OUT_APPROVAL, TransferInApprovalService.class, " ##Response:::::update Transfer Out" + response);
			} catch (Exception e) {
				DODLog.printStackTrace(e, TransferInApprovalService.class, LogConstant.TRANSFER_OUT_APPROVAL);
			}
			return toutResponse;

	 	}
		
	
}
