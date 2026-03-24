package com.pcda.mb.finalclaim.draftclaim.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimRequestBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimRequestBeanResponse;
import com.pcda.mb.finalclaim.draftclaim.model.DraftClaimRequestBean;
import com.pcda.mb.finalclaim.draftclaim.model.DraftClaimRequestResponse;
import com.pcda.mb.finalclaim.draftclaim.model.DraftClaimUpdateBean;
import com.pcda.mb.finalclaim.draftclaim.model.DraftClaimUpdateResponse;
import com.pcda.mb.finalclaim.draftclaim.model.DraftConyncDtls;
import com.pcda.mb.finalclaim.draftclaim.model.DraftFoodDtls;
import com.pcda.mb.finalclaim.draftclaim.model.DraftHotelDtls;
import com.pcda.mb.finalclaim.draftclaim.model.DraftLeave;
import com.pcda.mb.finalclaim.draftclaim.model.DraftNonDtsJrny;
import com.pcda.mb.finalclaim.draftclaim.model.DraftPersonalEffectsDtlsBean;
import com.pcda.mb.finalclaim.draftclaim.model.DraftReqBean;
import com.pcda.mb.finalclaim.draftclaim.model.DraftReqResponse;
import com.pcda.mb.finalclaim.draftclaim.util.TADAClaimValidation;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class DraftClaimService {

	@Autowired
	private UserServices userServices;

	@Autowired
	private OfficesService officesService;

	@Autowired
	private RestTemplate restTemplate;

	public List<DraftReqBean> viewAllDraftTaDaClaims(BigInteger userId) {
		DODLog.info(LogConstant.DRAFT_CLAIM_LOG_FILE, DraftClaimService.class, "[viewAllDraftTaDaClaims]## userId: " + userId);
		String groupId = "";
		Optional<OfficeModel> optional = officesService.getOfficeByUserId(userId);
		if (optional.isPresent()) {
			groupId = optional.get().getGroupId();
		}

		List<DraftReqBean> claimList = new ArrayList<>();
		

		String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimDraft/viewAllDraftTaDaClaims";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("groupId", groupId).build();
try {
		ResponseEntity<DraftReqResponse> responseEntity = restTemplate.exchange(builder.toString(), HttpMethod.GET, null, DraftReqResponse.class);
		DraftReqResponse draftReqResponse = responseEntity.getBody();
		
		if (draftReqResponse != null && draftReqResponse.getErrorCode() == 200 && draftReqResponse.getResponseList() != null) {
			claimList.addAll(draftReqResponse.getResponseList());
		}
		
}catch(Exception e) {
	DODLog.printStackTrace(e, DraftClaimService.class, LogConstant.DRAFT_CLAIM_LOG_FILE);
}
DODLog.info(LogConstant.DRAFT_CLAIM_LOG_FILE, DraftClaimService.class, "[viewAllDraftTaDaClaims] ## claimList size ##: " + claimList.size());
		return claimList;
	}

	public DraftClaimRequestResponse getClaimById(String claimId) {
		DODLog.info(LogConstant.DRAFT_CLAIM_LOG_FILE, DraftClaimService.class, "[getClaimById]## claimId: " + claimId);
		
		DraftClaimRequestResponse requestResponse = null;

		String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimDraft/draftDetailsByClaimID";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("tadaClaimId", claimId).build();
try {
		ResponseEntity<DraftClaimRequestResponse> responseEntity = restTemplate.exchange(builder.toString(), HttpMethod.GET, null, DraftClaimRequestResponse.class);
		requestResponse = responseEntity.getBody();
		
}catch(Exception e) {
	DODLog.printStackTrace(e, DraftClaimService.class, LogConstant.DRAFT_CLAIM_LOG_FILE);
}
DODLog.info(LogConstant.DRAFT_CLAIM_LOG_FILE, DraftClaimService.class, "[getClaimById] ## Draft Claim Request Response view: " + requestResponse);
		return requestResponse;
	}

	public Map<Boolean, String> updateDraftClaimAsDraft(HttpServletRequest request) {
		Map<Boolean, String> resultMap = new HashMap<>();
		String errorMessage = "Unable to save your claim as draft. Kindly contact DTS Helpline.";
		String travelTypeId = Optional.ofNullable(request.getParameter("travelTypeId")).orElse("");
		try {
			DraftClaimUpdateBean saveTADAClaimDTO = null;
				if(travelTypeId.equals("100002")){
					saveTADAClaimDTO = new DraftClaimUpdateBean(request);
				}else if(travelTypeId.equals("100005") || travelTypeId.equals("100006") || travelTypeId.equals("100007") || travelTypeId.equals("100008")){
					saveTADAClaimDTO = new DraftClaimUpdateBean();
				    saveTADAClaimDTO.setLTCClaimDetails(request);
				}else if(travelTypeId.equals("100001")){
					saveTADAClaimDTO = new DraftClaimUpdateBean();
				    saveTADAClaimDTO.setPTClaimDetails(request);
				}

				DODLog.info(LogConstant.DRAFT_CLAIM_LOG_FILE, DraftClaimService.class, "[updateDraftClaimAsDraft] ## Claim Draft Request Bean: " + saveTADAClaimDTO);

				if (saveTADAClaimDTO != null) {
					SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
					LoginUser loginUser = sessionVisitor.getLoginUser();

					

				  saveTADAClaimDTO.setTravelTypeId(travelTypeId);
				  saveTADAClaimDTO.setLoginUserId(loginUser.getUserId());
				  saveTADAClaimDTO.setIrlaGrpID(Optional.ofNullable(request.getParameter("irlaGrpID")).orElse(""));
				  saveTADAClaimDTO.setPersonalNo(Optional.ofNullable(request.getParameter("personalNo")).orElse(""));
				  saveTADAClaimDTO.setUnitId(Optional.ofNullable(request.getParameter("unitId")).orElse(""));
				  saveTADAClaimDTO.setTadaClaimId(request.getParameter("tadaClaimId"));

				  Optional<User> optionalUser = userServices.getUserByUserAlias(request.getParameter("personalNo"));
				  User user = new User();
				  if (optionalUser.isPresent()) {
					  user = optionalUser.get();
				  }
				  saveTADAClaimDTO.setUserId(user.getUserId());

				  DraftClaimUpdateResponse response = restTemplate.postForObject(PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimDraft/draftToDraftUpdateClaim", saveTADAClaimDTO, DraftClaimUpdateResponse.class) ;
				  DODLog.info(LogConstant.DRAFT_CLAIM_LOG_FILE, DraftClaimService.class, "Claim Draft Save Response: " + response);

				  if (response != null && response.getErrorCode() == 200) {
					  resultMap.put(true, saveTADAClaimDTO.getTadaClaimId());
				  } else if (response != null && response.getErrorCode() != 200 && !response.getErrorMessage().isEmpty()) {
					  resultMap.put(false, response.getErrorMessage());
				  } else {
						resultMap.put(false, errorMessage);
				  }
				} else {
					resultMap.put(false, errorMessage);
				}

		} catch (Exception e) {
			DODLog.printStackTrace(e, DraftClaimService.class, LogConstant.DRAFT_CLAIM_LOG_FILE);
			resultMap.clear();
			resultMap.put(false, errorMessage);
		}
		
		  return resultMap;
	}

	public Map<Boolean, String> saveDraftClaim(HttpServletRequest request) {
		Map<Boolean, String> resultMap = new HashMap<>();
		String errorMessage = "Unable to save your claim request. Kindly contact DTS Helpline.";
		String travelTypeId = Optional.ofNullable(request.getParameter("travelTypeId")).orElse("");
		try {
			DraftClaimUpdateBean saveTADAClaimDTO = null;
			if(travelTypeId.equals("100002")){
				saveTADAClaimDTO = new DraftClaimUpdateBean(request);
			}else if(travelTypeId.equals("100005") || travelTypeId.equals("100006") || travelTypeId.equals("100007") || travelTypeId.equals("100008")){
				saveTADAClaimDTO = new DraftClaimUpdateBean();
			    saveTADAClaimDTO.setLTCClaimDetails(request);
			}else if(travelTypeId.equals("100001")){
				saveTADAClaimDTO = new DraftClaimUpdateBean();
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
				  DODLog.info(LogConstant.DRAFT_CLAIM_LOG_FILE, DraftClaimService.class, "[saveDraftClaim] ## draft save Bean: " + saveTADAClaimDTO);
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
					  DraftClaimUpdateResponse response = restTemplate.postForObject(PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimDraft/draftToSaveUpdateClaim", saveTADAClaimDTO, DraftClaimUpdateResponse.class) ;
					  DODLog.info(LogConstant.DRAFT_CLAIM_LOG_FILE, DraftClaimService.class, "Claim Request Save Response: " + response);
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
			DODLog.printStackTrace(e, DraftClaimService.class, LogConstant.DRAFT_CLAIM_LOG_FILE);
			resultMap.clear();
			resultMap.put(false, errorMessage);
		}
		
		  return resultMap;
	}

	public ViewClaimRequestBean getViewDataForClaim(String tadaclaimId) {
		ViewClaimRequestBean tadaClaimSettledModel = new ViewClaimRequestBean();
		DODLog.info(LogConstant.DRAFT_CLAIM_LOG_FILE, DraftClaimService.class, "[getViewDataForClaim] TaDa Claim View tadaclaimId : " + tadaclaimId);
		try {
			String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRequest/ViewByClaimId";
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("tadaClaimId", tadaclaimId).build();

			ResponseEntity<ViewClaimRequestBeanResponse> responseEntity = restTemplate.exchange(builder.toString(), HttpMethod.GET, null, ViewClaimRequestBeanResponse.class);
			ViewClaimRequestBeanResponse tadaClaimSettledResponse = responseEntity.getBody();
			
			if (tadaClaimSettledResponse != null && tadaClaimSettledResponse.getErrorCode() == 200) {
				
				tadaClaimSettledModel = tadaClaimSettledResponse.getResponse();
			}
			

		} catch (Exception e) {
			DODLog.printStackTrace(e, DraftClaimService.class, LogConstant.DRAFT_CLAIM_LOG_FILE);
		}
		

		return tadaClaimSettledModel;
	}

	
	
	public void setDateOfTravelStr(DraftClaimRequestBean claimRequestBean) {
		
		
		if (claimRequestBean.getDraftTaDaClaimDetails().getConyncDtls() != null) {
			DraftConyncDtls conLeave = claimRequestBean.getDraftTaDaClaimDetails().getConyncDtls();
			if (conLeave.getTaDaLocalCon() != null && !conLeave.getTaDaLocalCon().isEmpty()) {
				conLeave.getTaDaLocalCon().forEach(e ->  e.setDateOftravelStr(CommonUtil.formatDate(e.getDateOftravel(), "dd-MM-yyyy")) );
			}
		}
		
		if (claimRequestBean.getDraftTaDaClaimDetails().getDraftLeave() == null) {claimRequestBean.getDraftTaDaClaimDetails().setDraftLeave(new DraftLeave());}
		if (claimRequestBean.getDraftTaDaClaimDetails().getNonDtsJrny() == null) {claimRequestBean.getDraftTaDaClaimDetails().setNonDtsJrny(new DraftNonDtsJrny());}
		if (claimRequestBean.getDraftTaDaClaimDetails().getHotelDtls() == null) {claimRequestBean.getDraftTaDaClaimDetails().setHotelDtls(new DraftHotelDtls());}
		if (claimRequestBean.getDraftTaDaClaimDetails().getFoodDtls() == null) {claimRequestBean.getDraftTaDaClaimDetails().setFoodDtls(new DraftFoodDtls());}
		if (claimRequestBean.getDraftTaDaClaimDetails().getConyncDtls() == null) {claimRequestBean.getDraftTaDaClaimDetails().setConyncDtls(new DraftConyncDtls());}
	}
	
	
  public void setEffectMap(DraftClaimRequestBean claimRequestBean,Map<String, List<DraftPersonalEffectsDtlsBean>> personalEffectsMap) {
	if (claimRequestBean.getDraftTaDaClaimDetails().getPersonalEffects() != null) {
		claimRequestBean.getDraftTaDaClaimDetails().getPersonalEffects().forEach(e -> {
			List<DraftPersonalEffectsDtlsBean> effects0DtlsBeans = new ArrayList<>();
			List<DraftPersonalEffectsDtlsBean> effects1DtlsBeans = new ArrayList<>();
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
  
  public void changeChkDateFrmt(ViewClaimRequestBean data) {
	  
	  if(data.getClaimLeaveDtls()!=null && !data.getClaimLeaveDtls().isEmpty()) {
			data.getClaimLeaveDtls().forEach(e -> e.setLeaveDateStr(CommonUtil.formatDate(e.getLeaveDate(), "dd-MM-yyyy")));
			if (data.getClaimHotelDtls() != null) {
				data.getClaimHotelDtls().forEach(e -> {
					e.setCheckInTimeStr(CommonUtil.formatDate(e.getCheckInTime(), "dd-MM-yyyy"));
					e.setCheckOutTimeStr(CommonUtil.formatDate(e.getCheckOutTime(), "dd-MM-yyyy"));
				});
			}
}
	  
	    if(data.getClaimHotelDtls()==null) {data.setClaimHotelDtls(new HashSet<>());}
		if(data.getClaimLeaveDtls()==null) {data.setClaimLeaveDtls(new HashSet<>());}
		if(data.getClaimFoodDtls()==null) {data.setClaimFoodDtls(new HashSet<>());}
	  
  }
  
	
}
