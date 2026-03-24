package com.pcda.mb.finalclaim.rejectedclaim.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimCertifyDtlsBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimHotelDtlsBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimLeaveDtlsBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimPersonalEffectsBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimRequestBean;
import com.pcda.mb.finalclaim.rejectedclaim.model.Recoverydetails;
import com.pcda.mb.finalclaim.rejectedclaim.model.RejectedCertifyDtlsBean;
import com.pcda.mb.finalclaim.rejectedclaim.model.RejectedClaimRequestBean;
import com.pcda.mb.finalclaim.rejectedclaim.model.RejectedClaimRequestResponse;
import com.pcda.mb.finalclaim.rejectedclaim.model.RejectedHotelDtlsBean;
import com.pcda.mb.finalclaim.rejectedclaim.model.RejectedJourneyDetails;
import com.pcda.mb.finalclaim.rejectedclaim.model.RejectedLeaveDtlsBean;
import com.pcda.mb.finalclaim.rejectedclaim.model.RejectedNonDtsJourneyDetails;
import com.pcda.mb.finalclaim.rejectedclaim.model.RejectedPersonalEffectsDtlsBean;
import com.pcda.mb.finalclaim.rejectedclaim.service.RejectedClaimService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class RejectedClaimController {

	private String error = "errorMessage";
	private String path = "MB/FinalClaim/RejectedClaim/";
	private String loginRediect = "redirect:/login";

	@Autowired
	private RejectedClaimService rejectedClaimService;

	@GetMapping("/rejectedClaimReq")
	public String rejectedClaimReq(HttpServletRequest request, Model model) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return loginRediect;
		}

		model.addAttribute("claimList", rejectedClaimService.getRejectedClaimList(loginUser.getUserId()));
		return path + "taDaRejectedClaimFilter";
	}

	@PostMapping("/rejectedClaimDetails")
	public String editRejectedClaimDetails(@RequestParam String selectedClaimId, Model model) {
		try {
			DODLog.info(LogConstant.REJECTED_CLAIM_LOG_FILE, RejectedClaimController.class, "Get Rejected Claim with id: " + selectedClaimId);
			RejectedClaimRequestResponse requestResponse = rejectedClaimService.getClaimDtls(selectedClaimId);
			if (requestResponse != null && requestResponse.getErrorCode() == 200 && requestResponse.getResponse() != null) {
				RejectedClaimRequestBean claimRequestBean = requestResponse.getResponse();

				rejectedClaimService.setLeaveDateStr(claimRequestBean);
				
				List<RejectedJourneyDetails> listRejectedTadaJrnyDtls = claimRequestBean.getRejectedTaDaClaimDetails().getTaDaJourneyDetails();
				if(listRejectedTadaJrnyDtls!=null && !listRejectedTadaJrnyDtls.isEmpty()) {
					Collections.sort(listRejectedTadaJrnyDtls);		
					}
				
				List<RejectedNonDtsJourneyDetails> listRejNonDtsJrnyDtls = claimRequestBean.getRejectedTaDaClaimDetails().getNonDtsJrny()
						.getNonDtstaDaJourneyDetails();
				
				if(listRejNonDtsJrnyDtls!=null && !listRejNonDtsJrnyDtls.isEmpty()) {
					Collections.sort(listRejNonDtsJrnyDtls);
				}
				
				
				List<RejectedHotelDtlsBean> hotelDtlsList = claimRequestBean.getRejectedTaDaClaimDetails().getHotelDtls().getClaimHotelDtls();
				if(hotelDtlsList!=null && !hotelDtlsList.isEmpty() ) {
					Collections.sort(hotelDtlsList);	
				}
				
				
			List<RejectedLeaveDtlsBean> leaveDtlsList = claimRequestBean.getRejectedTaDaClaimDetails().getRejectedLeave().getClaimLeaveDtls();
				if(  leaveDtlsList!=null && !leaveDtlsList.isEmpty()) {
					Collections.sort(leaveDtlsList);	
				}
				
				
				List<RejectedCertifyDtlsBean> listCertifyDtls = claimRequestBean.getRejectedTaDaClaimDetails().getCertifyDetails();
				if(  listCertifyDtls!=null && !listCertifyDtls.isEmpty()) {
					Collections.sort(listCertifyDtls);	
				}
				
				
				String irlaAmt="0.0";
				
				if(claimRequestBean.getRecoverydeatils()!=null) {
				claimRequestBean.getRecoverydeatils().setIrlaRecoveryAmnt(Optional.ofNullable(claimRequestBean.getRecoverydeatils().getIrlaRecoveryAmnt()).orElse(irlaAmt));
				}else {
					Recoverydetails recoveryDtls =  new Recoverydetails();
				    recoveryDtls.setIrlaRecoveryAmnt(irlaAmt);
					claimRequestBean.setRecoverydeatils(recoveryDtls);
				}
				
				String basicPayStr = claimRequestBean.getRejectedTaDaClaimDetails().getBasicPay();
				Integer basicPay = 0;
				if (!CommonUtil.isEmpty(basicPayStr)) {
					basicPay =Integer.parseInt(basicPayStr);
				}
				model.addAttribute("basicPay", basicPay);

				model.addAttribute("claimView", claimRequestBean);
				Map<String, List<String>> classOfTravelMap = new HashMap<>();
				claimRequestBean.getTransportModes().forEach(e -> classOfTravelMap.put(e.getTransportModeVal(), e.getTravelClass()));
				model.addAttribute("classOfTravelMap", classOfTravelMap);

				Map<String, List<RejectedPersonalEffectsDtlsBean>> personalEffectsMap = new HashMap<>();
				
				rejectedClaimService.setEffectMap(claimRequestBean, personalEffectsMap);
				
				model.addAttribute("personalEffectsMap", personalEffectsMap);
				return path + "editTADARejectedForm";
			}else if (requestResponse != null && requestResponse.getErrorMessage() != null) {
				model.addAttribute(error, requestResponse.getErrorMessage());
			} else {
				model.addAttribute(error, "Unable to get Claim Details.");
			}
		} catch (Exception e) {
			model.addAttribute(error, "Unable to get Claim Details.");
			DODLog.printStackTrace(e, RejectedClaimController.class, LogConstant.REJECTED_CLAIM_LOG_FILE);
		}
		return path + "errorPage";
	}

	@PostMapping("/updateClaimDetails")
	public String updateClaimDetails(HttpServletRequest request, Model model) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return loginRediect;
		}

		
		Map.Entry<Boolean, String> entry = rejectedClaimService.updateRejectedClaim(request).entrySet().iterator().next();
		if (Boolean.TRUE.equals(entry.getKey())) {
			String claimId = entry.getValue();
			ViewClaimRequestBean data = rejectedClaimService.getViewDataForClaim(claimId);

			Set<ViewClaimLeaveDtlsBean> setLeavedtls = data.getClaimLeaveDtls();
			if(setLeavedtls!=null && !setLeavedtls.isEmpty()) {
				List<ViewClaimLeaveDtlsBean> listLeavedtls = new ArrayList<>(setLeavedtls);
	            
	            Collections.sort(listLeavedtls);
	            
	            Set<ViewClaimLeaveDtlsBean>  newLeavedtls = new LinkedHashSet<>(listLeavedtls);
	            data.setClaimLeaveDtls(newLeavedtls);
	            
	            }
			
			Set<ViewClaimCertifyDtlsBean> setCertifyDtls = data.getClaimCertifyView();
			if(setCertifyDtls!=null && !setCertifyDtls.isEmpty()) {
				List<ViewClaimCertifyDtlsBean> listCertifyDtls = new ArrayList<>(setCertifyDtls);
	            
	            Collections.sort(listCertifyDtls);
	            
	            Set<ViewClaimCertifyDtlsBean>  newLeavedtls = new LinkedHashSet<>(listCertifyDtls);
	            data.setClaimCertifyView(newLeavedtls);
	            
	            }
			
			Set<ViewClaimHotelDtlsBean>  setHotelDtls =   data.getClaimHotelDtls();
			if(setHotelDtls!=null && !setHotelDtls.isEmpty()) {
				List<ViewClaimHotelDtlsBean> listHotelDtls = new ArrayList<>(setHotelDtls);
				Collections.sort(listHotelDtls);
				
				Set<ViewClaimHotelDtlsBean>  newHotelDtls = new LinkedHashSet<>(listHotelDtls);
	            data.setClaimHotelDtls(newHotelDtls);
			}
			
			
			
			
			if (data.getTravelTypeId().contentEquals("100001")) {
				Map<String, List<ViewClaimPersonalEffectsBean>> personalEffectsMap = new HashMap<>();
				List<ViewClaimPersonalEffectsBean> effects0DtlsBeans = new ArrayList<>();
				List<ViewClaimPersonalEffectsBean> effects1DtlsBeans = new ArrayList<>();
				if (data.getClaimPersonalEffectsBean() != null) {
					data.getClaimPersonalEffectsBean().forEach(e -> {
						if(e.getType() == 0) {
							effects0DtlsBeans.add(e);
						} else {
							effects1DtlsBeans.add(e);
						}
					});
				}
				personalEffectsMap.put("0", effects0DtlsBeans);
				personalEffectsMap.put("1", effects1DtlsBeans);
				DODLog.info(LogConstant.REJECTED_CLAIM_LOG_FILE, RejectedClaimService.class, "Rejected Ta Da Claim personalEffectsMap: " + personalEffectsMap);

				model.addAttribute("personalEffectsMap", personalEffectsMap);
			}

			model.addAttribute("taDaClaimDetails", data);
			return path + "updateTADACLaimConfirm";
		} else {
			
			model.addAttribute(error, entry.getValue());
		}

		return path + "errorPage";
	}

}
