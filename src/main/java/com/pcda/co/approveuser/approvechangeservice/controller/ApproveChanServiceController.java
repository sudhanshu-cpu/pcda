package com.pcda.co.approveuser.approvechangeservice.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcda.co.approveuser.approvechangeservice.model.AppDisApproveChangeSerModel;
import com.pcda.co.approveuser.approvechangeservice.model.GetChanSerApprovelModel;
import com.pcda.co.approveuser.approvechangeservice.service.ChangeServApprovalService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class ApproveChanServiceController {

	private String appChanSerUrl = "CO/ApproveUser/ChangeServiceApproved/";

	@Autowired
	private ChangeServApprovalService changeServApprovalService;

	@GetMapping("/approvalChangeService")
	public String appChangeService(Model model, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}

		
		BigInteger loginUserId = loginUser.getUserId();
		
		
		List<GetChanSerApprovelModel>listGetChangeService=changeServApprovalService.getChangeService(loginUserId);
		
		 String loginUnitServiceId=loginUser.getServiceId();
			
			if(loginUnitServiceId==null || loginUnitServiceId.equals("") )
			{
				loginUnitServiceId=loginUser.getUserServiceId();
			}
			
			if(loginUnitServiceId.equals("100003") && listGetChangeService.isEmpty())
			{
				
				model.addAttribute("errorMassage", "No Record Found");
			
			}else if(loginUnitServiceId.equals("100003") &&! listGetChangeService.isEmpty()){
				model.addAttribute("loginUserId", loginUserId);
				 model.addAttribute("details", changeServApprovalService.getChangeService(loginUserId));
			}else {
				model.addAttribute("error", "Sorry , This Functionality Is Available For Unit Belongs To Navy Service");
				
			}

		return appChanSerUrl + "approveChangeService";
	}

	
	@PostMapping("/disApproveChgSer")
	public String serviceDisApprove(@ModelAttribute AppDisApproveChangeSerModel approvalModel,HttpServletRequest request) {
		
		changeServApprovalService.disApproveChangeSer(approvalModel);
		
		return "redirect:approvalChangeService";
	}
	
	@PostMapping("/approvalChangeService")
	public String approveServiceEdit(@ModelAttribute AppDisApproveChangeSerModel approvalModel,HttpServletRequest request) {
		
		changeServApprovalService.approveChangeSer(approvalModel);
		return "redirect:approvalChangeService";
	}
	
	
	
}
