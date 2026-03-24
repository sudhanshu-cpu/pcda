package com.pcda.co.requestdashboard.claimreqapproval.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.co.requestdashboard.claimreqapproval.model.AppDisAppPostModel;
import com.pcda.co.requestdashboard.claimreqapproval.model.ApprovalClaimRequestBean;
import com.pcda.co.requestdashboard.claimreqapproval.model.ClaimApprovalReqModel;
import com.pcda.co.requestdashboard.claimreqapproval.service.ClaimApprovalService;
import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class ClaimApprovalController {

	private String pagePath = "/CO/RequestDashBoard/ApproveClaimRequest/";

	@Autowired
	private ClaimApprovalService claimApprService;

	@GetMapping("/finalClaimApproval")
	public String finalClaimApproval(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		Optional<OfficeModel> optionalOffice = claimApprService.getOfficeByUserId(loginUser.getUserId());
		String officeId = "";
		if (optionalOffice.isPresent()) {
			OfficeModel officeModel = optionalOffice.get();
			officeId = officeModel.getGroupId();
		}

		List<ClaimApprovalReqModel> claimList = claimApprService.viewAllTaDaForApproval(officeId);
		if (claimList.isEmpty()) {
			model.addAttribute("size", claimList.size());
			model.addAttribute("error", "No results found");
		} else {
			model.addAttribute("claimList", claimList);
			model.addAttribute("size", claimList.size());
		}

		return pagePath + "viewAllTaDaForApproval";
	}

	@PostMapping("/viewClaimRequest")
	public String viewClaimRequest(@RequestParam String tadaclaimId,Model model) {
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, ClaimApprovalController.class, "View Claim Request");

		ApprovalClaimRequestBean data=claimApprService.getApprovalClaimData(tadaclaimId);

		if (data.getClaimLeaveDtls() != null) {
			data.getClaimLeaveDtls().forEach(e -> e.setLeaveDateStr(CommonUtil.formatDate(e.getLeaveDate(), "dd-MM-yyyy")));
		}
		if (data.getClaimHotelDtls() != null) {
			data.getClaimHotelDtls().forEach(e -> {
				e.setCheckInTimeStr(CommonUtil.formatDate(e.getCheckInTime(), "dd-MM-yyyy hh:mm"));
				e.setCheckOutTimeStr(CommonUtil.formatDate(e.getCheckOutTime(), "dd-MM-yyyy hh:mm"));
			});
		}

		model.addAttribute("taDaClaimDetails", data);
		return pagePath + "tadaClaimForm";
	}


	@PostMapping("/approveDisApproveForm")
	public String appDisAppClaim(HttpServletRequest request,@ModelAttribute AppDisAppPostModel appDisAppPostModel) {
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}

		appDisAppPostModel.setLoginUserId(loginUser.getUserId());
		claimApprService.updateApprovalClaim(appDisAppPostModel);
		return "redirect:/co/finalClaimApproval";
	}

	@PostMapping("/viewClaimDetailsApprove")
	public String getviewClaimDetails(@RequestParam String tadaClaimId, Model model) {
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, ClaimApprovalController.class, "View Claim Request");
		ApprovalClaimRequestBean data = claimApprService.getApprovalClaimData(tadaClaimId);
		if (data.getClaimLeaveDtls() != null) {
			data.getClaimLeaveDtls()
					.forEach(e -> e.setLeaveDateStr(CommonUtil.formatDate(e.getLeaveDate(), "dd-MM-yyyy")));
		}
		if (data.getClaimHotelDtls() != null) {
			data.getClaimHotelDtls().forEach(e -> {
				e.setCheckInTimeStr(CommonUtil.formatDate(e.getCheckInTime(), "dd-MM-yyyy hh:mm"));
				e.setCheckOutTimeStr(CommonUtil.formatDate(e.getCheckOutTime(), "dd-MM-yyyy hh:mm"));
			});
		}

		model.addAttribute("taDaClaimDetails", data);
		if (data.getTravelTypeId().equals("100002")) {
			return pagePath + "suplyTDClaim";
		} else if (data.getTravelTypeId().equals("100001")) {
			return pagePath + "suplyPTClaim";
		} else {
			return pagePath + "suplyLTCClaim";
	}
	}

}
