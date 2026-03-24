package com.pcda.co.approveuser.approvetraveller.controller;

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

import com.pcda.co.approveuser.approvetraveller.model.ReqTravellerSearch;
import com.pcda.co.approveuser.approvetraveller.model.TravellerApprovalModel;
import com.pcda.co.approveuser.approvetraveller.model.TravellerApprovalReq;
import com.pcda.co.approveuser.approvetraveller.service.TravellerApprovalService;
import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class TravellerApprovalController {

	private String path = "CO/ApproveUser/ApproveTraveller/";

	@Autowired
	private TravellerApprovalService travellerApprovalService;

	@GetMapping("/travellerApprovalReq")
	public String travellerApprovalReq(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		Boolean isError = false;

		if (loginUser == null) {
			return "redirect:/login";
		}

		Optional<OfficeModel> optionalOffice = travellerApprovalService.getOfficeByUserId(loginUser.getUserId());
		String officeId = "";

		if (optionalOffice.isPresent()) {
			OfficeModel officeModel = optionalOffice.get();
			officeId = officeModel.getGroupId();
		}

		List<ReqTravellerSearch> userList = travellerApprovalService.getAllTravlerForApproval(officeId);
		isError = userList == null || userList.isEmpty();
		if (Boolean.TRUE.equals(isError)) {
			model.addAttribute("error", "No results Found");
		}

		model.addAttribute("isError", isError);
		model.addAttribute("userList", userList);
		return path + "travellerApproval";
	}

	@PostMapping("/viewUserDetails")
	public String viewUserDetails(@RequestParam String personalNo, Model model) {
		TravellerApprovalReq user = travellerApprovalService.getUserByPersonalNo(personalNo);

		model.addAttribute("isDep", user.getDependents() != null && !user.getDependents().isEmpty());
		model.addAttribute("traveller", user);

		return path + "viewUserDetails";
	}

	@PostMapping("/approveTraveller")
	public String approveTraveller(@ModelAttribute TravellerApprovalModel travellerApprovalModel, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}

		travellerApprovalModel.setApprovedBy(loginUser.getUserId());
		if (travellerApprovalModel.getApprovalType() == 1) {
			travellerApprovalModel.setActionType("approve");
		} else {
			travellerApprovalModel.setActionType("disApprove");
		}
		DODLog.info(LogConstant.TRAVELER_PROFILE_APPROVAL_LOG_FILE, TravellerApprovalController.class, travellerApprovalModel.toString());

		travellerApprovalService.updateTravellerApproval(travellerApprovalModel);

		return "redirect:travellerApprovalReq";
	}

}
