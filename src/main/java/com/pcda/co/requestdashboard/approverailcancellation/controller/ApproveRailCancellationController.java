package com.pcda.co.requestdashboard.approverailcancellation.controller;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.co.requestdashboard.approverailcancellation.model.GetApproveCancelParentModel;
import com.pcda.co.requestdashboard.approverailcancellation.model.PostCanParentApproveModel;
import com.pcda.co.requestdashboard.approverailcancellation.model.RailCanApproveBookingResponse;
import com.pcda.co.requestdashboard.approverailcancellation.service.ApproveRailCancellationService;
import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class ApproveRailCancellationController{

	@Autowired
	private ApproveRailCancellationService service;
	
@GetMapping("/approveRailCancel")	
	public String getApproval(Model model,HttpServletRequest request,@RequestParam(required = false, defaultValue = "") String personalNumber) {
	
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();

	
	if (loginUser == null) {
		return "redirect:/login";
	}

	
	BigInteger loginUserId = loginUser.getUserId();
	Optional<OfficeModel> officeModel = service.getOfficeByUserId(loginUserId);
	if(officeModel.isPresent()) {
	model.addAttribute("approvalDataList", service.getRequestedApprovalData(officeModel.get().getGroupId()));
	return "/CO/RequestDashBoard/ApproveRailCancellation/ApproveRailCancellation";
	}
	return "/CO/RequestDashBoard/ApproveRailCancellation/errorPage";
}

@PostMapping("/railCancelApprovalData")

public String getTicketDetails(Model model,@RequestParam String bookingId) {
	
	GetApproveCancelParentModel parentModel =  service.getRailCanApprovalData(bookingId);

	DODLog.info(LogConstant.RAIL_CANCELLATION_LOG_FILE, ApproveRailCancellationController.class,
			" RAIL CANCEL APPROVAL  ::::: " + parentModel);
model.addAttribute("expCancelData", parentModel);

	
	return "/CO/RequestDashBoard/ApproveRailCancellation/ApproveRailCanDetails";
}


@PostMapping("/cancellationApproval")
public String sendForApproval(PostCanParentApproveModel approveModel,HttpServletRequest request,RedirectAttributes attributes) {
	String pnr=request.getParameter("pnrNo");
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();
	approveModel.setLoginUserID(loginUser.getUserId());
	RailCanApproveBookingResponse  response = service.sendForApproval(approveModel,request);
	attributes.addFlashAttribute("success", "Request for cancellation of PNR "
			+ pnr+ " has been successfully approved."
			+ " Kindly ask your Master Booker to proceed further and cancel the ticket through Rail Cancellation Dashboard.");
     return"redirect:approveRailCancel";	
}

@PostMapping("/cancellationDisApproval")
public String sendForDisApproval(PostCanParentApproveModel approveModel,HttpServletRequest request,RedirectAttributes attributes) {
	String pnr=request.getParameter("pnrNo");
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();
	approveModel.setLoginUserID(loginUser.getUserId());
	RailCanApproveBookingResponse  response = service.sendDisApproval(approveModel);
	attributes.addFlashAttribute("success", "Request for cancellation of PNR "
			+ pnr+ " has been successfully disapproved.");
     return"redirect:approveRailCancel";	
}


}
