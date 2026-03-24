package com.pcda.mb.finalclaim.claimstatus.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.finalclaim.claimstatus.model.ClaimStatusDataModel;
import com.pcda.mb.finalclaim.claimstatus.model.ClaimStatusModel;
import com.pcda.mb.finalclaim.claimstatus.sarvice.ClaimStatusService;
import com.pcda.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class ClaimStatusController {

	@Autowired
	private OfficesService officesService;
	
	@Autowired
	private ClaimStatusService claimStatusService;
	
	
	@GetMapping("/claimStatus")
	public String claimStatus(Model model,HttpServletRequest request) {
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		BigInteger userid = loginUser.getUserId();
		Optional<OfficeModel> officeModel=officesService.getOfficeByUserId(userid);
		
		String groupId="";
		if(officeModel.isPresent()) {
			groupId=officeModel.get().getGroupId();
		}
		model.addAttribute("groupId", groupId);
		return "/MB/FinalClaim/claimStatus/claimStatus";
	}
	
	@PostMapping("/getClaimStatusData")
	public String getClaimStatus(ClaimStatusDataModel claimStatusDataModel,Model model) {
		
		String personalNo = claimStatusDataModel.getPersonalNo();
		String secret = "Hidden Pass";
		
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, personalNo);
		claimStatusDataModel.setPersonalNo(decryptPersonalNo);
		
		model.addAttribute("personalNo", claimStatusDataModel.getPersonalNo());
		model.addAttribute("claimId", claimStatusDataModel.getClaimId());
		model.addAttribute("groupId", claimStatusDataModel.getGroupId());
		
		List<ClaimStatusModel> airDataList=claimStatusService.getAllData(claimStatusDataModel);
		
		
		
		if(airDataList.isEmpty()) {
			
			model.addAttribute("error", "No Record Found");
		}
		else {
		model.addAttribute("claimStatusData", airDataList);
		}
		return "/MB/FinalClaim/claimStatus/claimStatus";
	}
	
	
}
