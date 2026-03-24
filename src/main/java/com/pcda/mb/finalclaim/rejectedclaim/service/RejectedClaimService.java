package com.pcda.mb.finalclaim.rejectedclaim.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.User;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.UserServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.finalclaim.claimrequest.model.ClaimRequestResponse;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimRequestBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimRequestBeanResponse;
import com.pcda.mb.finalclaim.rejectedclaim.model.ClaimRejectReqView;
import com.pcda.mb.finalclaim.rejectedclaim.model.ClaimRejectReqViewResponse;
import com.pcda.mb.finalclaim.rejectedclaim.model.ConyncDtls;
import com.pcda.mb.finalclaim.rejectedclaim.model.FoodDtls;
import com.pcda.mb.finalclaim.rejectedclaim.model.HotelDtls;
import com.pcda.mb.finalclaim.rejectedclaim.model.NonDtsJrny;
import com.pcda.mb.finalclaim.rejectedclaim.model.RejectedClaimRequestBean;
import com.pcda.mb.finalclaim.rejectedclaim.model.RejectedClaimRequestResponse;
import com.pcda.mb.finalclaim.rejectedclaim.model.RejectedLeave;
import com.pcda.mb.finalclaim.rejectedclaim.model.RejectedPersonalEffectsDtlsBean;
import com.pcda.mb.finalclaim.rejectedclaim.model.UpdateClaimRequestBean;
import com.pcda.mb.finalclaim.rejectedclaim.util.TADAClaimValidation;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class RejectedClaimService {

	@Autowired
	private UserServices userServices;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OfficesService officesService;

	public List<ClaimRejectReqView> getRejectedClaimList(BigInteger userId) {
		DODLog.info(LogConstant.REJECTED_CLAIM_LOG_FILE, RejectedClaimService.class, "[getRejectedClaimList] ## userId : " + userId);
		List<ClaimRejectReqView> claimList = new ArrayList<>();
		String groupId = "";
		Optional<OfficeModel> optional = officesService.getOfficeByUserId(userId);
		if (optional.isPresent()) {
			groupId = optional.get().getGroupId();
		}

		
		
		String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRejected/rejectedClaimListView";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("groupId", groupId).build();
try {
		ResponseEntity<ClaimRejectReqViewResponse> responseEntity = restTemplate.exchange(builder.toString(), HttpMethod.GET, null, ClaimRejectReqViewResponse.class);
		ClaimRejectReqViewResponse reqViewResponse = responseEntity.getBody();
		if (reqViewResponse != null && reqViewResponse.getErrorCode() == 200 && reqViewResponse.getResponseList() != null) {
			claimList.addAll(reqViewResponse.getResponseList());
		}
      }catch (Exception e) {
			DODLog.printStackTrace(e, RejectedClaimService.class, LogConstant.REJECTED_CLAIM_LOG_FILE);
			
		}
		DODLog.info(LogConstant.REJECTED_CLAIM_LOG_FILE, RejectedClaimService.class, "[getRejectedClaimList] ## claimList: " + claimList.size());
		return claimList;
	}

	public RejectedClaimRequestResponse getClaimDtls(String tadaClaimId) {
		
		
		RejectedClaimRequestResponse requestResponse = null;

		String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRejected/rejectedDetailsByClaimID";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("tadaClaimId", tadaClaimId).build();
try {
		ResponseEntity<RejectedClaimRequestResponse> responseEntity = restTemplate.exchange(builder.toString(), HttpMethod.GET, null, RejectedClaimRequestResponse.class);
		requestResponse = responseEntity.getBody();
		
	}catch (Exception e) {
		DODLog.printStackTrace(e, RejectedClaimService.class, LogConstant.REJECTED_CLAIM_LOG_FILE);
		
	}
		
		return requestResponse;
	}

	public Map<Boolean, String> updateRejectedClaim(HttpServletRequest request) {
		Map<Boolean, String> resultMap = new HashMap<>();
		String errorMessage = "Unable to update your claim request. Kindly contact DTS Helpline.";
		String travelTypeId = Optional.ofNullable(request.getParameter("travelTypeId")).orElse("");
		try {
			UpdateClaimRequestBean saveTADAClaimDTO = null;
			  if(travelTypeId.equals("100002")){
				  saveTADAClaimDTO = new UpdateClaimRequestBean(request);
			  }else if(travelTypeId.equals("100005") || travelTypeId.equals("100006") || travelTypeId.equals("100007") || travelTypeId.equals("100008")){
				  saveTADAClaimDTO = new UpdateClaimRequestBean();
				  saveTADAClaimDTO.setLTCClaimDetails(request);
			  }else if(travelTypeId.equals("100001")){
				  saveTADAClaimDTO = new UpdateClaimRequestBean();
				  saveTADAClaimDTO.setPTClaimDetails(request);
			  }

			  if (saveTADAClaimDTO != null) {
				  SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
				  LoginUser loginUser = sessionVisitor.getLoginUser();

				 

				  saveTADAClaimDTO.setTravelTypeId(travelTypeId);
				  saveTADAClaimDTO.setLoginUserId(loginUser.getUserId());
				  saveTADAClaimDTO.setIrlaGrpID(Optional.ofNullable(request.getParameter("irlaGrpID")).orElse(""));
				  saveTADAClaimDTO.setPersonalNo(Optional.ofNullable(request.getParameter("personalNo")).orElse(""));
				  saveTADAClaimDTO.setUnitId(Optional.ofNullable(request.getParameter("unitId")).orElse(""));
				  saveTADAClaimDTO.setTadaClaimId(request.getParameter("tadaClaimId"));
				  DODLog.info(LogConstant.REJECTED_CLAIM_LOG_FILE, RejectedClaimService.class, "UpdateClaimRequestBean: " + saveTADAClaimDTO);
				  String result="";
				  if(travelTypeId.equals("100002")){
					  result = TADAClaimValidation.validateClaimData(saveTADAClaimDTO);
				  }else if(travelTypeId.equals("100005") || travelTypeId.equals("100006") || travelTypeId.equals("100007") || travelTypeId.equals("100008")){
					  result = TADAClaimValidation.validateLTCClaimData(saveTADAClaimDTO);
				  }else if(travelTypeId.equals("100001")){
					  result = TADAClaimValidation.validatePTClaimData(saveTADAClaimDTO);
				  }

				  Optional<User> optionalUser = userServices.getUserByUserAlias(request.getParameter("personalNo"));
				  User user = new User();
				  if (optionalUser.isPresent()) {
					  user = optionalUser.get();
				  }
				  saveTADAClaimDTO.setUserId(user.getUserId());

				 

				 

				  if (result.equalsIgnoreCase("OK")) {
					  ClaimRequestResponse response = restTemplate.postForObject(PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRejected/rejectedupdateClaim", saveTADAClaimDTO, ClaimRequestResponse.class) ;
					  DODLog.info(LogConstant.REJECTED_CLAIM_LOG_FILE, RejectedClaimService.class, "[updateRejectedClaim] ## Claim rejected  Update Response: " + response);
					  if (response != null && response.getErrorCode() == 200) {
						  resultMap.put(true, saveTADAClaimDTO.getTadaClaimId());
					  } else if (response != null && response.getErrorCode() != 200 && !response.getErrorMessage().isEmpty()) {
						  resultMap.put(false, response.getErrorMessage());
					  } else {
							resultMap.put(false, errorMessage);
					  }
				  } else {
					  resultMap.put(false, result);
				  }
			  } else {
					resultMap.put(false, errorMessage);
			  }

		} catch (Exception e) {
			DODLog.printStackTrace(e, RejectedClaimService.class, LogConstant.REJECTED_CLAIM_LOG_FILE);
			resultMap.clear();
			resultMap.put(false, errorMessage);
		}
		
		  return resultMap;
	}

	public ViewClaimRequestBean getViewDataForClaim(String tadaclaimId) {
		DODLog.info(LogConstant.REJECTED_CLAIM_LOG_FILE, RejectedClaimService.class, "[getViewDataForClaim] ## tadaclaimId : " + tadaclaimId);
		ViewClaimRequestBean tadaClaimSettledModel = new ViewClaimRequestBean();

		try {
			String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRequest/ViewByClaimId";
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("tadaClaimId", tadaclaimId).build();

			ResponseEntity<ViewClaimRequestBeanResponse> responseEntity = restTemplate.exchange(builder.toString(), HttpMethod.GET, null, ViewClaimRequestBeanResponse.class);
			ViewClaimRequestBeanResponse tadaClaimSettledResponse = responseEntity.getBody();
			
			if (tadaClaimSettledResponse != null && tadaClaimSettledResponse.getErrorCode() == 200) {
				tadaClaimSettledModel = tadaClaimSettledResponse.getResponse();
			}
			

		} catch (Exception e) {
			DODLog.printStackTrace(e, RejectedClaimService.class, LogConstant.REJECTED_CLAIM_LOG_FILE);
		}
	
		return tadaClaimSettledModel;
	}
	
	public void setLeaveDateStr(RejectedClaimRequestBean claimRequestBean) {
		
		if (claimRequestBean.getRejectedTaDaClaimDetails().getRejectedLeave() != null) {
			RejectedLeave rejLeave = claimRequestBean.getRejectedTaDaClaimDetails().getRejectedLeave();
			if (rejLeave.getClaimLeaveDtls() != null && !rejLeave.getClaimLeaveDtls().isEmpty()) {
				rejLeave.getClaimLeaveDtls().forEach(e ->  e.setLeaveDateStr(CommonUtil.formatDate(e.getLeaveDate(), "dd-MM-yyyy ")) );
			}
		}

		if (claimRequestBean.getRejectedTaDaClaimDetails().getConyncDtls() != null) {
			ConyncDtls conLeave = claimRequestBean.getRejectedTaDaClaimDetails().getConyncDtls();
			if (conLeave.getTaDaLocalCon() != null && !conLeave.getTaDaLocalCon().isEmpty()) {
				conLeave.getTaDaLocalCon().forEach(e ->  e.setDateOftravelStr(CommonUtil.formatDate(e.getDateOftravel(), "dd-MM-yyyy")) );
			}
		}

		if (claimRequestBean.getRejectedTaDaClaimDetails().getRejectedLeave() == null) {claimRequestBean.getRejectedTaDaClaimDetails().setRejectedLeave(new RejectedLeave());}
		if (claimRequestBean.getRejectedTaDaClaimDetails().getNonDtsJrny() == null) {claimRequestBean.getRejectedTaDaClaimDetails().setNonDtsJrny(new NonDtsJrny());}
		if (claimRequestBean.getRejectedTaDaClaimDetails().getHotelDtls() == null) {claimRequestBean.getRejectedTaDaClaimDetails().setHotelDtls(new HotelDtls());}
		if (claimRequestBean.getRejectedTaDaClaimDetails().getFoodDtls() == null) {claimRequestBean.getRejectedTaDaClaimDetails().setFoodDtls(new FoodDtls());}
		if (claimRequestBean.getRejectedTaDaClaimDetails().getConyncDtls() == null) {claimRequestBean.getRejectedTaDaClaimDetails().setConyncDtls(new ConyncDtls());}

	}
	
	public void setEffectMap(RejectedClaimRequestBean claimRequestBean,Map<String, List<RejectedPersonalEffectsDtlsBean>> personalEffectsMap) {
		
		if (claimRequestBean.getRejectedTaDaClaimDetails().getPersonalEffects() != null) {
			claimRequestBean.getRejectedTaDaClaimDetails().getPersonalEffects().forEach(e -> {
				List<RejectedPersonalEffectsDtlsBean> effects0DtlsBeans = new ArrayList<>();
				List<RejectedPersonalEffectsDtlsBean> effects1DtlsBeans = new ArrayList<>();
				if(e.getType() == 0) {
					if (effects0DtlsBeans.size() == 1) {
						e.setPersonalEffectsId("CartageOldResToBkgOff");
					} else if (effects0DtlsBeans.size() == 2) {
						e.setPersonalEffectsId("CartageBkgOffToNewRes");
					} else {
						e.setPersonalEffectsId("");
					}
					effects0DtlsBeans.add(e);
				} else {
					effects1DtlsBeans.add(e);
				}
				personalEffectsMap.put("0", effects0DtlsBeans);
				personalEffectsMap.put("1", effects1DtlsBeans);
			});
		}
		
	}

}
