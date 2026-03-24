package com.pcda.co.approveuser.approvechangepersonalno.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pcda.co.approveuser.approvechangepersonalno.model.PostApproveModel;
import com.pcda.co.approveuser.approvechangepersonalno.service.ApproveChangePnoService;
import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class ApproveChangePnoController {

@Autowired
private ApproveChangePnoService approveService; 
	
	
	@GetMapping("/changePnoApp")

	public String changePnoPage(Model model , HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		BigInteger userId = loginUser.getUserId();
		OfficeModel officeModel = approveService.getOfficeByUserId(userId);
		model.addAttribute("approveData", approveService.getApproveChangePnoData(officeModel.getGroupId()));
		return "/CO/ApproveUser/ApproveChangePersonalNo/appproveChangePersonalNo";
	}
	
	
	@PostMapping("/approveChangePno")

	public String approveChangePno(PostApproveModel approveModel , HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		approveModel.setLoginUserId(loginUser.getUserId());
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ApproveChangePnoController.class,
				"CHANGE PERSONAL NO APPROVAL MODEL :: " + approveModel);
		approveService.sendApprove(approveModel);
		
		return "redirect:changePnoApp";
	}
		
	@RequestMapping(value="/disApproveChangePno",method= {RequestMethod.GET,RequestMethod.POST})
    public String disApproveChangePno(PostApproveModel disApproveModel , HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		disApproveModel.setLoginUserId(loginUser.getUserId());
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ApproveChangePnoController.class,
				"CHANGE PERSONAL NO DISAPPROVAL MODEL :: " + disApproveModel);
		approveService.sendDisapprove(disApproveModel);
		
		return "redirect:changePnoApp";
	}
	
}
