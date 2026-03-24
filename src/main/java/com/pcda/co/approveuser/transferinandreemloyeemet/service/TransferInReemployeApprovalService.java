package com.pcda.co.approveuser.transferinandreemloyeemet.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.co.approveuser.transferinandreemloyeemet.model.PostTranInReempApprovalModel;
import com.pcda.co.approveuser.transferinandreemloyeemet.model.TransInReEmpApprResponse;
import com.pcda.co.approveuser.transferinandreemloyeemet.model.TransferInReemployeApprovalModel;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class TransferInReemployeApprovalService {

    @Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private OfficesService officesService;
	
public Optional<OfficeModel> getOfficeByUserIdTranInRe(BigInteger userId) {
		
		return	officesService.getOfficeByUserId(userId);
			
		}
	

	/*public List<TransferInReemployeApprovalModel> getApprovalListTrnOut(String groupId) {

	List<TransferInReemployeApprovalModel> approvalTransferOutList = new ArrayList<>();
	try {
		ResponseEntity<TransInReEmpApprResponse> responseEntity = restTemplate.exchange(
						PcdaConstant.TRANSFER_SERVICE_URL + "/transferInAndReemployment/getTInAndReemploymentProfile/"
								+ groupId,
						HttpMethod.GET, null, new ParameterizedTypeReference<TransInReEmpApprResponse>() {
				});

		TransInReEmpApprResponse response = responseEntity.getBody();
		
				DODLog.info(LogConstant.TRANSFER_LOG_FILE, TransferInReemployeApprovalService.class,
						"Response Transfer-In Re-Employemt::" + response);
		
		if (response != null && response.getErrorCode() == 200) {
			approvalTransferOutList = response.getResponseList();
			
					approvalTransferOutList.forEach(model -> model
							.setProfileChangeAtrributeSplit(model.getProfileAttributeName().split("@")));
					
					List<String> formattedProfileAttr = approvalTransferOutList.stream()
							.map(TransferInReemployeApprovalModel::getProfileChangeAtrributeSplit)
							.filter(profileAttributeArray -> profileAttributeArray != null
									&& profileAttributeArray.length > 0)
							.flatMap(Arrays::stream).map(pair -> {
								String[] parts = pair.trim().split("=");
								if (parts.length == 2) {
									if (parts[0].trim().equalsIgnoreCase("TSOS_DATE")) {

										try {
											return "SOS Date = "
													+ CommonUtil.getChangeFormat(parts[1].trim(), "EEE MMM dd HH:mm:ss zzz yyyy" , "dd-MM-yyyy");
										} catch (Exception e) {
											DODLog.printStackTrace(e, TransferInReemployeApprovalService.class, LogConstant.TRANSFER_LOG_FILE);
											return pair;
										}

									} else {
										return pair;
									}
								}
								return pair;
							}).collect(Collectors.toList());

					
					formattedProfileAttr.forEach(e -> System.out.println(e.toString()));
					

					approvalTransferOutList.forEach(model -> model.setProfileChangeAttributeList(formattedProfileAttr));

		}

	} catch (Exception e) {
				DODLog.printStackTrace(e, TransferInReemployeApprovalService.class, LogConstant.TRANSFER_LOG_FILE);

	}

	return approvalTransferOutList;
		}*/
	
	
		public List<TransferInReemployeApprovalModel> getApprovalListTrnOut(String groupId) {
			DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT_APPROVAL, TransferInReemployeApprovalService.class," ## groupId ::" + groupId);
			
	List<TransferInReemployeApprovalModel> approvalTransferOutList = new ArrayList<>();
    
	try {
		ResponseEntity<TransInReEmpApprResponse> responseEntity = restTemplate.exchange(
            PcdaConstant.TRANSFER_SERVICE_URL + "/transferInAndReemployment/getTInAndReemploymentProfile/" + groupId,
            HttpMethod.GET, null, new ParameterizedTypeReference<TransInReEmpApprResponse>() {});

		TransInReEmpApprResponse response = responseEntity.getBody();

		if (response != null && response.getErrorCode() == 200 && null!=response.getResponseList()) {
		
			approvalTransferOutList = response.getResponseList();
            
            approvalTransferOutList.forEach(model ->  model.setProfileChangeAtrributeSplit(model.getProfileAttributeName().split("@")));
            
            approvalTransferOutList.forEach(model -> {
                List<String> formattedProfileAttr = formatProfileAttributes(model.getProfileChangeAtrributeSplit());
                model.setProfileChangeAttributeList(formattedProfileAttr);
            });
}
    } catch (Exception e) {
        DODLog.printStackTrace(e, TransferInReemployeApprovalService.class, LogConstant.TRANSFER_IN_REEMPLOYEMENT_APPROVAL);
    }
	DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT_APPROVAL, TransferInReemployeApprovalService.class," ##response  transferinre-em List ::"+groupId+" :: " + approvalTransferOutList.size());
    return approvalTransferOutList;
 }	

	
	
	// -------------- Formatting profile attributes -----------------------------//
		
	private List<String> formatProfileAttributes(String[] profileAttributeArray) {
		return Arrays.stream(profileAttributeArray).map(pair -> {
								String[] parts = pair.trim().split("=");
								if (parts.length == 2) {
									if (parts[0].trim().equalsIgnoreCase("TSOS_DATE")) {
										try {
						return "SOS Date = " + CommonUtil.getChangeFormat(parts[1].trim(),
								"EEE MMM dd HH:mm:ss zzz yyyy", "dd-MM-yyyy");
										} catch (Exception e) {
						DODLog.printStackTrace(e, TransferInReemployeApprovalService.class,
								LogConstant.TRANSFER_IN_REEMPLOYEMENT_APPROVAL);
											return pair;
										}
									} else {
										return pair;
									}
								}
								return pair;
							}).toList();

		}
// -------------------------------------------------------------------------------------------------------------------------- //	
	


  public void updateApprovalTraInRee(PostTranInReempApprovalModel model) {
	
	  DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT_APPROVAL, TransferInReemployeApprovalService.class,"## Model"+ model); 
	 try { 
		 
		 
		String url = PcdaConstant.TRANSFER_SERVICE_URL+"/transferInAndReemployment/";
		if (model.getEvent().equalsIgnoreCase("disapprove")) { 
			url = url + "disapprove";
			
		} else if (model.getEvent().equalsIgnoreCase("approve")) {
			
			url = url + "approve";
		}
		HttpEntity<PostTranInReempApprovalModel> requestUpdate = new HttpEntity<>(model);
		ResponseEntity<TransInReEmpApprResponse> response = restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, TransInReEmpApprResponse.class);

		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT_APPROVAL, TransferInReemployeApprovalService.class, " ##Response:::update:: Transfer IN Reemployee" + response);
	  } catch (Exception e) {
		DODLog.printStackTrace(e, TransferInReemployeApprovalService.class, LogConstant.TRANSFER_IN_REEMPLOYEMENT_APPROVAL);
	  }

	}





}
