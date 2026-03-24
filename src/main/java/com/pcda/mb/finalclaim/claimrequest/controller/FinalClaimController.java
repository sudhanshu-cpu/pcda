package com.pcda.mb.finalclaim.claimrequest.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.finalclaim.claimrequest.model.ClaimViewResponse;
import com.pcda.mb.finalclaim.claimrequest.model.CounterPersonalInfoBean;
import com.pcda.mb.finalclaim.claimrequest.model.FinalClaimReqSearchResponse;
import com.pcda.mb.finalclaim.claimrequest.model.GetCityBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimRequestBean;
import com.pcda.mb.finalclaim.claimrequest.service.FinalClaimService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class FinalClaimController {

	String path = "MB/FinalClaim/FinalClaimRequest/";
	String loginRediect = "redirect:/login";
	private String error = "errorMessage";
	private String redirectError = "redirect:finalclaimerror";

	@Autowired
	private FinalClaimService finalClaimService;

	@RequestMapping(value = "/finalclaimrequest", method = {RequestMethod.POST, RequestMethod.GET })
	public String finalclaimrequest(@RequestParam(defaultValue = "", required = false) String travelType, @RequestParam(defaultValue = "", required = false) String personalNo,
			Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return loginRediect;
		}
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, personalNo);
		model.addAttribute("travelTypeList", finalClaimService.getTravelType());

		if (!travelType.isBlank() && !decryptPersonalNo.isBlank()) {
			Optional<OfficeModel> optional = finalClaimService.getOfficeByUserId(loginUser.getUserId());
			OfficeModel office = new OfficeModel();
			if (optional.isPresent()) {
				office = optional.get();
			}
			FinalClaimReqSearchResponse response = finalClaimService.getClaimSearchData(decryptPersonalNo, office.getGroupId(), travelType);
			if (response != null && response.getErrorCode() == 200 && response.getResponseList() != null) {
				if (response.getResponseList().isEmpty()) {
					model.addAttribute(error, "No Records Found");
				} else {
					model.addAttribute("claimList", response.getResponseList());
				}
			} else if (response != null && response.getErrorMessage() != null) {
				model.addAttribute(error, response.getErrorMessage());
			} else {
				model.addAttribute(error, "Unable to get Data");
			}
		}

		model.addAttribute("travelType", travelType);
		model.addAttribute("personalNo", decryptPersonalNo);
		return path + "requestPoolForTADA";
	}

	@PostMapping("/requestClaimAction")
	public String requestClaimAction(Model model, @RequestParam String travelId, RedirectAttributes attributes) {
		try {
			DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimController.class, "[requestClaimAction] ## Request Claim traveller id: " + travelId);
			ClaimViewResponse response = finalClaimService.getClaimDetails(travelId);
			if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
				model.addAttribute("claimView", response.getResponse());
				String basicPayStr = response.getResponse().getTravelContent().getBasicPay();
				Integer basicPay = 0;
				if (!CommonUtil.isEmpty(basicPayStr)) {
					basicPay = Integer.parseInt(basicPayStr);
				}
				model.addAttribute("basicPay", basicPay);
				Boolean autofill = response.getResponse().getAutoFill() != null && response.getResponse().getAutoFill().equalsIgnoreCase("autoFill");
				
				if (Boolean.TRUE.equals(autofill)) {
					return path + "autoFillClaimForm";
				} else {
					return path + "requestConfirmationPoolForTADA";
				}
			}else if (response != null && response.getErrorMessage() != null) {
				
				attributes.addFlashAttribute(error, response.getErrorMessage());
			} else {
				attributes.addFlashAttribute(error, "Unable to get Claim Details.");
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, FinalClaimController.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
			attributes.addFlashAttribute(error, "Error Occurred");
		}
		return redirectError;
	}

	@PostMapping("/fileClaim")
	public String fileClaim(Model model, @RequestParam String travelId, RedirectAttributes attributes) {
		try {
			DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimController.class, "[fileClaim] ### Fiile Claim with traveller id: " + travelId);
			ClaimViewResponse response = finalClaimService.getClaimDetails(travelId);
			if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
				model.addAttribute("claimView", response.getResponse());
				String basicPayStr = response.getResponse().getTravelContent().getBasicPay();
				Integer basicPay = 0;
				if (!CommonUtil.isEmpty(basicPayStr)) {
					basicPay = Integer.parseInt(basicPayStr);
				}
				model.addAttribute("basicPay", basicPay);
				return path + "requestConfirmationPoolForTADA";
			} else if (response != null && response.getErrorMessage() != null) {
				attributes.addFlashAttribute(error, response.getErrorMessage());
			} else {
				attributes.addFlashAttribute(error, "Unable to get Claim Details.");
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, FinalClaimController.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
			attributes.addFlashAttribute(error, "Error Occurred");
		}
		return redirectError;
	}

	@PostMapping("/saveClaimRequest")
	public String saveClaimRequest(HttpServletRequest request, Model model) {
		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();

			if (loginUser == null) {
				return loginRediect;
			}

			
			
			
			Map.Entry<Boolean, String> entry = finalClaimService.saveClaimData(request).entrySet().iterator().next();
			if (Boolean.TRUE.equals(entry.getKey())) {
				String claimId = entry.getValue();
				ViewClaimRequestBean data = finalClaimService.getViewDataForClaim(claimId);
				if (data.getClaimLeaveDtls() != null) {
					data.getClaimLeaveDtls().forEach(e -> e.setLeaveDateStr(CommonUtil.formatDate(e.getLeaveDate(), "dd-MM-yyyy")));
				}
				if (data.getClaimHotelDtls() != null) {
					data.getClaimHotelDtls().forEach(e -> {
						e.setCheckInTimeStr(CommonUtil.formatDate(e.getCheckInTime(), "dd-MM-yyyy"));
						e.setCheckOutTimeStr(CommonUtil.formatDate(e.getCheckOutTime(), "dd-MM-yyyy"));
					});
				}
				model.addAttribute("taDaClaimDetails", data);
				return path + "tadaSaveConfirmation";
			} else {
				
				model.addAttribute(error, entry.getValue());
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, FinalClaimController.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
			model.addAttribute(error, "Unable to save your claim request. Kindly contact DTS Helpline.");
		}

		return path + "errorPage";
	}

	@PostMapping("/saveClaimAsDraft")
	public String saveClaimAsDraft(HttpServletRequest request, Model model) {
		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();

			if (loginUser == null) {
				return loginRediect;
			}

			
			Map.Entry<Boolean, String> entry = finalClaimService.saveClaimDataAsDraft(request).entrySet().iterator().next();
			if (Boolean.TRUE.equals(entry.getKey())) {
				String claimId = entry.getValue();
				
				model.addAttribute("claimId", claimId);
				return path + "saveConfirmationForDraft";
			} else {
				
				model.addAttribute(error, entry.getValue());
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, FinalClaimController.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
			model.addAttribute(error, "Unable to save your claim As Draft. Kindly contact DTS Helpline.");
		}

		return path + "errorPage";
	}

	// Ajax call to search Uesr
	@PostMapping("/counterPersonalInfo")
	public ResponseEntity<CounterPersonalInfoBean> getCounterPersonalInfo(@RequestParam String userAlias) {
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimController.class, "Counter Personal Info: " + userAlias);
		return ResponseEntity.ok(finalClaimService.getCounterPersonalInfo(userAlias));
	}

	// Ajax call to get Airport Code
	@PostMapping("/getNAPForClaim")
	public ResponseEntity<List<String>> searchAirport(@RequestParam String airportName) {
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimController.class, "Get Nearest Airport: " + airportName);
		return ResponseEntity.ok(finalClaimService.getAirport(airportName));
	}

	// Ajax call to search City
	@PostMapping("/getCityList")
	public ResponseEntity<List<GetCityBean>> getCityList(@RequestParam String city) {
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimController.class, "Search City: " + city);
		return ResponseEntity.ok(finalClaimService.getCityList(city));
	}

	// Ajax call to get Nearest Station
	@PostMapping("/getStationListForClaim")
	public ResponseEntity<List<String>> searchStation(@RequestParam String stationName) {
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimController.class, "Get Nearest Station: " + stationName);
		return ResponseEntity.ok(finalClaimService.getStation(stationName));
	}

	@GetMapping("/finalclaimerror")
	public String finalClaimError(HttpServletRequest request) {
		return path + "errorPage";
	}

}
