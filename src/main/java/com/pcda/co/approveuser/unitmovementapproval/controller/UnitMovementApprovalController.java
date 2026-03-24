package com.pcda.co.approveuser.unitmovementapproval.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.co.approveuser.unitmovementapproval.model.GetUnitMomentApprovalModel;
import com.pcda.co.approveuser.unitmovementapproval.model.PostUnitMomentApprovalModel;
import com.pcda.co.approveuser.unitmovementapproval.model.UnitMomentApprovalResponse;
import com.pcda.co.approveuser.unitmovementapproval.service.UnitMovementApprovalService;
import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class UnitMovementApprovalController {

	private String path = "CO/ApproveUser/UnitMovementApproval/";
	
	@Autowired UnitMovementApprovalService unitMovementApprovalService;
	//Get Approval Request
	@GetMapping("/approvalUnitMovementReq")
	public String appChangeService(Model model, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		Optional<OfficeModel> optionalOffice = unitMovementApprovalService.getOfficeByUserId(loginUser.getUserId());
		String officeId = "";

		if (optionalOffice.isPresent()) {
			OfficeModel officeModel = optionalOffice.get();
			officeId = officeModel.getGroupId();
		}
		List<GetUnitMomentApprovalModel> userList = unitMovementApprovalService.getAllUnitMovmentForApproval(officeId);
		model.addAttribute("userList", userList);
		
		return path + "unitmoveMentApproval";
	}
	//View unit Movement 
	@RequestMapping(value = "/unitViewMovement", method = { RequestMethod.GET, RequestMethod.POST })
	public String appChangeService1(Model model, @RequestParam String movementOId,   HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}

		model.addAttribute("userList", unitMovementApprovalService.getviewUserDetails(movementOId));
		return path + "unitMovementView";
	}
	
	
	@PostMapping("/approvalUnitMovement")
	public String approveUnitMovementEdit(@ModelAttribute PostUnitMomentApprovalModel postUnitMomentApprovalModel, HttpServletRequest request, RedirectAttributes redirect) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		UnitMomentApprovalResponse response;

		if (loginUser == null) {
			return "redirect:/login";
		}

		postUnitMomentApprovalModel.setLoginUserId(loginUser.getUserId());
		DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementApprovalController.class, "Update Approval for Unit Movement" + postUnitMomentApprovalModel);
		response=unitMovementApprovalService.approveUnitMovement(postUnitMomentApprovalModel);
		
		if (response!=null &&   response.getErrorCode()==200) {
		List<String> personalNo=	unitMovementApprovalService.getviewUserDetails(postUnitMomentApprovalModel.getMovementId());
		String perNo = String.join(", ", personalNo);
		if (postUnitMomentApprovalModel.getEvent().equalsIgnoreCase("approve")) {
			redirect.addFlashAttribute("success","The Unit Movement for the "+  perNo +" Personnel has been successfully approved");
			return "redirect:approvalUnitMovementReq";
		}}
		if (response!=null && response.getErrorCode()==200 && postUnitMomentApprovalModel.getEvent().equalsIgnoreCase("disapprove") ) {
			
			redirect.addFlashAttribute("success", "Request Rejected Successfully");
			return "redirect:approvalUnitMovementReq";
		
		}
		
		

		return "redirect:approvalUnitMovementReq";
	}
	

}
