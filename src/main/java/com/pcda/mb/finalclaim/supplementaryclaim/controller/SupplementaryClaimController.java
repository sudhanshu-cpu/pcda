package com.pcda.mb.finalclaim.supplementaryclaim.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.finalclaim.claimrequest.controller.FinalClaimController;
import com.pcda.mb.finalclaim.claimrequest.model.CounterPersonalInfoBean;
import com.pcda.mb.finalclaim.claimrequest.model.GetCityBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimHotelDtlsBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimRequestBean;
import com.pcda.mb.finalclaim.supplementaryclaim.model.SupplementaryClaimReqResponse;
import com.pcda.mb.finalclaim.supplementaryclaim.model.SupplementaryClaimViewResponse;
import com.pcda.mb.finalclaim.supplementaryclaim.service.SupplementaryClaimService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class SupplementaryClaimController {

	private String path = "MB/FinalClaim/SupplementaryClaim/";
	private String loginRediect = "redirect:/login";
	private String error = "errorMessage";

	@Autowired
	private SupplementaryClaimService supplementaryClaimService;

	@RequestMapping(value = "/supplementaryClaimReq", method = {RequestMethod.GET, RequestMethod.POST})
	public String supplementaryClaim(HttpServletRequest request, Model model, @RequestParam(required = false, defaultValue = "") String claimId,
			@RequestParam(required = false, defaultValue = "") String personalNo) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return loginRediect;
		}
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, personalNo);
		Boolean isError = false;

		if (!claimId.isBlank() || !decryptPersonalNo.isBlank()) {
			Optional<OfficeModel> optional = supplementaryClaimService.getOfficeByUserId(loginUser.getUserId());
			OfficeModel office = new OfficeModel();
			if (optional.isPresent()) {
				office = optional.get();
			}

			SupplementaryClaimReqResponse response = supplementaryClaimService.getSupplementaryClaimList(office.getGroupId(), decryptPersonalNo, claimId);
			if (response != null && response.getErrorCode() == 200 && response.getResponseList() != null) {
				if (response.getResponseList().isEmpty()) {
					model.addAttribute(error, "No Records Found");
					isError = true;
				} else {
					model.addAttribute("claimList", response.getResponseList());
				}
			} else if (response != null && response.getErrorMessage() != null) {
				model.addAttribute(error, response.getErrorMessage());
				isError = true;
			} else {
				model.addAttribute(error, "Unable to get Data");
				isError = true;
			}
		}

		model.addAttribute("isError", isError);
		model.addAttribute("personalNo", decryptPersonalNo);
		model.addAttribute("claimId", claimId);
		return path + "claimForSupplementary";
	}

	@PostMapping("/supplementaryClaim")
	public String supplementaryClaim(@RequestParam String tadaClaimId, HttpServletRequest request, Model model) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return loginRediect;
		}

		SupplementaryClaimViewResponse response = supplementaryClaimService.getSupplementaryDetailsByClaimId(tadaClaimId);
		if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
			model.addAttribute("claimView", response.getResponse());
			String basicPayStr = response.getResponse().getTravelContent().getBasicPay();
			Integer basicPay = 0;
			if (!CommonUtil.isEmpty(basicPayStr)) {
				basicPay = Integer.parseInt(basicPayStr);
			}
			model.addAttribute("basicPay", basicPay);
			return path + "supplementaryClaimForm";
		} else if(response != null && !response.getErrorMessage().isBlank()) {
			model.addAttribute(error, response.getErrorMessage());
		} else {
			model.addAttribute(error, "Unable to get Claim Details.Kindly contact DTS Helpline.");
		}

		return path + "errorPage";
	}

	@GetMapping("/viewClaimDetails")
	public String viewClaimDetails(@RequestParam String claimId, Model model) {
		ViewClaimRequestBean data = supplementaryClaimService.getViewDataForClaim(claimId);
		model.addAttribute("taDaClaimDetails", data);
		return path + "viewClaimDetails";
	}

	// Ajax call to search User
	@PostMapping("/supCounterPersonalInfo")
	public ResponseEntity<CounterPersonalInfoBean> getCounterPersonalInfo(@RequestParam String userAlias) {
		DODLog.info(LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE, SupplementaryClaimController.class, "Counter Personal Info: " + userAlias);
		return ResponseEntity.ok(supplementaryClaimService.getCounterPersonalInfo(userAlias));
	}

	// Ajax call to get Airport Code
	@PostMapping("/getNAPForSupClaim")
	public ResponseEntity<List<String>> searchAirport(@RequestParam String airportName) {
		DODLog.info(LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE, SupplementaryClaimController.class, "Get Nearest Airport: " + airportName);
		return ResponseEntity.ok(supplementaryClaimService.getAirport(airportName));
	}

	// Ajax call to get Nearest Station
	@PostMapping("/getStationListForSupClaim")
	public ResponseEntity<List<String>> searchStation(@RequestParam String stationName) {
		DODLog.info(LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE, SupplementaryClaimController.class, "Get Nearest Station: " + stationName);
		return ResponseEntity.ok(supplementaryClaimService.getStation(stationName));
	}

	// Ajax call to search City
	@PostMapping("/getCityListSup")
	public ResponseEntity<List<GetCityBean>> getCityListSup(@RequestParam String city) {
		DODLog.info(LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE, SupplementaryClaimController.class, "Search City: " + city);
		return ResponseEntity.ok(supplementaryClaimService.getCityList(city));
	}

	@PostMapping("/saveSupClaimRequest")
	public String saveSupClaimRequest(Model model, HttpServletRequest request) {
		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();

			if (loginUser == null) {
				return loginRediect;
			}

			
			Map.Entry<Boolean, String> entry = supplementaryClaimService.saveSupClaimData(request).entrySet().iterator().next();
			if (Boolean.TRUE.equals(entry.getKey())) {
				String claimId = entry.getValue();
				ViewClaimRequestBean data = supplementaryClaimService.getViewDataForClaim(claimId);
				if (data.getClaimHotelDtls() != null) {
					
					Set<ViewClaimHotelDtlsBean> setHoteldtls = data.getClaimHotelDtls();
					if(setHoteldtls!=null && !setHoteldtls.isEmpty()) {
						List<ViewClaimHotelDtlsBean> listHotelDtls = new ArrayList<>(setHoteldtls);
			            
			            Collections.sort(listHotelDtls);
			            
			            Set<ViewClaimHotelDtlsBean>  newHotelDtls = new LinkedHashSet<>(listHotelDtls);
			            data.setClaimHotelDtls(newHotelDtls);
			            
			            }
					
					data.getClaimHotelDtls().forEach(e -> {
						e.setCheckInTimeStr(CommonUtil.formatDate(e.getCheckInTime(), "dd-MM-yyyy hh:mm"));
						e.setCheckOutTimeStr(CommonUtil.formatDate(e.getCheckOutTime(), "dd-MM-yyyy hh:mm"));
					});
					
				}
				
				if (data.getClaimLeaveDtls() != null) {
					data.getClaimLeaveDtls().forEach(e -> e.setLeaveDateStr(CommonUtil.formatDate(e.getLeaveDate(), "dd-MM-yyyy")));
				}
				model.addAttribute("taDaClaimDetails", data);
				return path + "saveConfirmation";
			} else {
				
				model.addAttribute("errorMessage", entry.getValue());
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, SupplementaryClaimController.class, LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE);
			model.addAttribute("errorMessage", "Unable to save your claim request. Kindly contact DTS Helpline.");
		}

		return path + "errorPage";
	}

}
