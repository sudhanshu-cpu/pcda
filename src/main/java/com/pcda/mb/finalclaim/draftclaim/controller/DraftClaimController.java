package com.pcda.mb.finalclaim.draftclaim.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.finalclaim.claimrequest.controller.FinalClaimController;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimPersonalEffectsBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimRequestBean;
import com.pcda.mb.finalclaim.draftclaim.model.DraftClaimRequestBean;
import com.pcda.mb.finalclaim.draftclaim.model.DraftClaimRequestResponse;
import com.pcda.mb.finalclaim.draftclaim.model.DraftPersonalEffectsDtlsBean;
import com.pcda.mb.finalclaim.draftclaim.model.Recoverydetails;
import com.pcda.mb.finalclaim.draftclaim.service.DraftClaimService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class DraftClaimController {

	private String errorMessage = "errorMessage";
	private String path = "MB/FinalClaim/DraftClaim/";
	private String loginRediect = "redirect:/login";

	@Autowired
	private DraftClaimService draftClaimService;

	@GetMapping("/tadaClaimDraftReq")
	public String tadaClaimDraft(HttpServletRequest request, Model model) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return loginRediect;
		}

		model.addAttribute("claimList", draftClaimService.viewAllDraftTaDaClaims(loginUser.getUserId()));
		return path + "viewAllDraftTADAClaim";
	}

	@PostMapping("/processDraftClaim")
	public String processDraftClaim(@RequestParam String tadaclaimId, Model model) {
		try {
			DraftClaimRequestResponse requestResponse = draftClaimService.getClaimById(tadaclaimId);
			if (requestResponse != null && requestResponse.getErrorCode() == 200 && requestResponse.getResponse() != null) {
				DraftClaimRequestBean claimRequestBean = requestResponse.getResponse();


				
		     	draftClaimService.setDateOfTravelStr(claimRequestBean);
		     	
                  String irlaAmt="0.0";
				
				if(claimRequestBean.getRecoverydeatils()!=null) {
				claimRequestBean.getRecoverydeatils().setIrlaRecoveryAmnt(Optional.ofNullable(claimRequestBean.getRecoverydeatils().getIrlaRecoveryAmnt()).orElse(irlaAmt));
				}else {
					Recoverydetails recoveryDtls =  new Recoverydetails();
				    recoveryDtls.setIrlaRecoveryAmnt(irlaAmt);
					claimRequestBean.setRecoverydeatils(recoveryDtls);
				}
				

				Map<String, List<String>> classOfTravelMap = new HashMap<>();
				claimRequestBean.getTransportModes().forEach(e -> classOfTravelMap.put(e.getTransportModeVal(), e.getTravelClass()));
				model.addAttribute("classOfTravelMap", classOfTravelMap);

				Map<String, List<DraftPersonalEffectsDtlsBean>> personalEffectsMap = new HashMap<>();
				
				draftClaimService.setEffectMap(claimRequestBean, personalEffectsMap);

				model.addAttribute("personalEffectsMap", personalEffectsMap);

				model.addAttribute("claimView", claimRequestBean);
				return path + "processDraftTADAClaim";
			} else if (requestResponse != null && requestResponse.getErrorMessage() != null) {
				model.addAttribute(errorMessage, requestResponse.getErrorMessage());
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, DraftClaimController.class, LogConstant.DRAFT_CLAIM_LOG_FILE);
		}
		model.addAttribute(errorMessage, "Error Occurred");
		return path + "errorPage";
	}

	

	
	@PostMapping("/updateDraftClaim")
	public String saveDraftClaim(HttpServletRequest request, Model model) {
		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();

			if (loginUser == null) {
				return loginRediect;
			}

			
			Map.Entry<Boolean, String> entry = draftClaimService.saveDraftClaim(request).entrySet().iterator().next();
			if (Boolean.TRUE.equals(entry.getKey())) {
				String claimId = entry.getValue();
				ViewClaimRequestBean data = draftClaimService.getViewDataForClaim(claimId);

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

					model.addAttribute("personalEffectsMap", personalEffectsMap);
				}

				draftClaimService.changeChkDateFrmt(data);
				
				
				
				

				model.addAttribute("taDaClaimDetails", data);
				return path + "processDraftTADAClaimView";
			} else {
			
				model.addAttribute("errorMessage", entry.getValue());
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, FinalClaimController.class, LogConstant.DRAFT_CLAIM_LOG_FILE);
			model.addAttribute("errorMessage", "Unable to update your claim. Kindly contact DTS Helpline.");
		}

		return path + "errorPage";
	}

	@PostMapping("/updateClaimAsDraft")
	public String updateDraftClaimAsDraft(HttpServletRequest request, Model model) {
		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();

			if (loginUser == null) {
				return loginRediect;
			}

		
			Map.Entry<Boolean, String> entry = draftClaimService.updateDraftClaimAsDraft(request).entrySet().iterator().next();
			if (Boolean.TRUE.equals(entry.getKey())) {
				String claimId = entry.getValue();
				ViewClaimRequestBean data = draftClaimService.getViewDataForClaim(claimId);

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

					model.addAttribute("personalEffectsMap", personalEffectsMap);
				}

				model.addAttribute("taDaClaimDetails", data);
				return path + "processDraftTADAClaimView";
			} else {
				
				model.addAttribute("errorMessage", entry.getValue());
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, FinalClaimController.class, LogConstant.DRAFT_CLAIM_LOG_FILE);
			model.addAttribute("errorMessage", "Unable to update your claim. Kindly contact DTS Helpline.");
		}

		return path + "errorPage";
	}

}
