package com.pcda.co.requestdashboard.claimreqapproval.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.co.requestdashboard.claimreqapproval.model.AppDisAppPostModel;
import com.pcda.co.requestdashboard.claimreqapproval.model.ApprovalClaimCertifyDtlsBean;
import com.pcda.co.requestdashboard.claimreqapproval.model.ApprovalClaimHotelDtlsBean;
import com.pcda.co.requestdashboard.claimreqapproval.model.ApprovalClaimLeaveDtlsBean;
import com.pcda.co.requestdashboard.claimreqapproval.model.ApprovalClaimRequestBean;
import com.pcda.co.requestdashboard.claimreqapproval.model.ApprovalClaimRequestBeanResponse;
import com.pcda.co.requestdashboard.claimreqapproval.model.ClaimApprovalReqModel;
import com.pcda.co.requestdashboard.claimreqapproval.model.ClaimApprovalReqResponse;
import com.pcda.co.requestdashboard.claimreqapproval.model.PostClaimAppDisModelResponse;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.mb.finalclaim.claimstatus.sarvice.ClaimStatusService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ClaimApprovalService {

	@Autowired
	private OfficesService officesService;

	@Autowired
	private RestTemplate restTemplate;

	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {
		return officesService.getOfficeByUserId(userId);
	}

	public List<ClaimApprovalReqModel> viewAllTaDaForApproval(String officeId) {
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, ClaimApprovalService.class, " officeId: " + officeId);
	
		List<ClaimApprovalReqModel> list = new ArrayList<>();
		try {
		ResponseEntity<ClaimApprovalReqResponse> response = restTemplate.exchange(PcdaConstant.FINAL_CLAIM_BASE_URL + "/approve/viewAllClaimForApproval?officeId=" + officeId, HttpMethod.GET,
				null, ClaimApprovalReqResponse.class);

		ClaimApprovalReqResponse claimApprReqResponse = response.getBody();
		if (claimApprReqResponse != null && claimApprReqResponse.getErrorCode() == 200 && claimApprReqResponse.getResponseList() != null) {
			list.addAll(claimApprReqResponse.getResponseList());
		}
	}catch (Exception e) {
		DODLog.printStackTrace(e, ClaimApprovalService.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
	}
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, ClaimApprovalService.class, " list : " + list);
		return list;
	}
	
	public ApprovalClaimRequestBean getApprovalClaimData(String tadaclaimId) {
		
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, ClaimApprovalService.class, "[getApprovalClaimData] tadaclaimId : " + tadaclaimId);
		ApprovalClaimRequestBean tadaClaimSettledModel=new ApprovalClaimRequestBean();
		
			try {
			 String url = PcdaConstant.CLAIM_BASE_URL+
						"/claimRequest/ViewByClaimId?tadaClaimId=";
			
			 ApprovalClaimRequestBeanResponse tadaClaimSettledResponse=restTemplate.getForObject(url+tadaclaimId, ApprovalClaimRequestBeanResponse.class, tadaClaimSettledModel);
			 
				if(tadaClaimSettledResponse != null && tadaClaimSettledResponse.getErrorCode()== 200) {
					tadaClaimSettledModel=tadaClaimSettledResponse.getResponse();
					if(tadaClaimSettledModel.getClaimLeaveDtls()!=null) {
						
					List<ApprovalClaimLeaveDtlsBean> sortList = new ArrayList<>(tadaClaimSettledModel.getClaimLeaveDtls());
					if(!sortList.isEmpty()) {
						
					Collections.sort(sortList, Comparator.comparing(ApprovalClaimLeaveDtlsBean::getLeaveDate));
					
					TreeSet<ApprovalClaimLeaveDtlsBean> treeSet = new TreeSet<>();
					treeSet.addAll(sortList);
				
					tadaClaimSettledModel.setClaimLeaveDtls(treeSet);
					}
					}
					if(tadaClaimSettledModel.getClaimHotelDtls()!=null) {
						
						Set<ApprovalClaimHotelDtlsBean> sortedHashSet = tadaClaimSettledModel.getClaimHotelDtls().stream()
						        .sorted(Comparator.comparing(ApprovalClaimHotelDtlsBean::getCheckInTime))
						        .collect(Collectors.toCollection(LinkedHashSet::new));
						
						tadaClaimSettledModel.setClaimHotelDtls(sortedHashSet);
					
					}
					
					if(tadaClaimSettledModel.getClaimCertifyView()!=null) {
						Set<ApprovalClaimCertifyDtlsBean> sortedHashSet = tadaClaimSettledModel.getClaimCertifyView().stream()
								.sorted(Comparator.comparing(ApprovalClaimCertifyDtlsBean :: getSeqNo))
								.collect(Collectors.toCollection(LinkedHashSet::new));
						
						tadaClaimSettledModel.setClaimCertifyView(sortedHashSet);
					}
					
				}
				
			}catch (Exception e) {
				DODLog.printStackTrace(e, ClaimStatusService.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
			}
			DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, ClaimStatusService.class,"tadaClaimSettledModel"+tadaClaimSettledModel);
				
			return tadaClaimSettledModel;
			 
		}
	
	
	public void updateApprovalClaim(AppDisAppPostModel appDisAppPostModel) {
		try { 
			String url = PcdaConstant.FINAL_CLAIM_BASE_URL +  "/approve/";
			if (appDisAppPostModel.getApproveDisApproveAction().equalsIgnoreCase("approve")) {
				url = url + "approve";
			} else if (appDisAppPostModel.getApproveDisApproveAction().equalsIgnoreCase("disapprove")) {
				url = url + "disapprove";
			}
	
			HttpEntity<AppDisAppPostModel> requestUpdate = new HttpEntity<>(appDisAppPostModel);
			ResponseEntity<PostClaimAppDisModelResponse> response = restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, PostClaimAppDisModelResponse.class);
	
			DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, ClaimStatusService.class,"tadaClaimSettledModel"+response);
		} catch (Exception e) {
			DODLog.printStackTrace(e, ClaimApprovalService.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
		}

	}
		
	
	
	
}
