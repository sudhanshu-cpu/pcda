package com.pcda.mb.finalclaim.settledclaims.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.finalclaim.settledclaims.model.SettledClaimModel;
import com.pcda.mb.finalclaim.settledclaims.model.SettledModel;
import com.pcda.mb.finalclaim.settledclaims.model.ViewClaimRequestBean;
import com.pcda.mb.finalclaim.settledclaims.service.PrintClaimFromPdfService;
import com.pcda.mb.finalclaim.settledclaims.service.SettledClaimService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/mb")
public class SettledClaimController {
	
	@Autowired
	private OfficesService officesService;
	
	@Autowired
	private SettledClaimService settledClaimService;
	
	@Autowired
	private PrintClaimFromPdfService printClaimFromPdfService;
	
	String url="/MB/FinalClaim/settledClaim";
	
	@GetMapping("/settledClaim")
	public String settledClaim(Model model,HttpServletRequest request) {
		
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
		model.addAttribute("personalNo", "");
		model.addAttribute("claimId", "");
		return url+"/settledClaim";
	}
	
	@PostMapping("/getSettledClaimData")
	public String getClaimStatus(SettledModel settledModel,Model model) {
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, settledModel.getPersonalNo());
		settledModel.setPersonalNo(decryptPersonalNo);
		
		List<SettledClaimModel> settledClaimModels=settledClaimService.getAllDataSettled(settledModel);
		
		
		
		if(settledClaimModels.isEmpty()) {
			
			model.addAttribute("error", "No Record Found");
		}
		else {
			
			model.addAttribute("settledModelList", settledClaimModels);
		}
		model.addAttribute("groupId", settledModel.getGroupId());
		
		model.addAttribute("personalNo", settledModel.getPersonalNo());
		model.addAttribute("claimId", settledModel.getClaimId());
		
		return "/MB/FinalClaim/settledClaim/settledClaim";
	}
	
	
	@PostMapping("/getTADAPTClaimForm")
	public String getTADAPTClaimForm(@RequestParam String tadaclaimId,Model model) {
	
		ViewClaimRequestBean data=settledClaimService.getviewDataSettled(tadaclaimId);
		
	model.addAttribute("taDaClaimDetails", data);
	
 		return url+"/tadaClaimForm";
	}
	

	@PostMapping("/printClaimFormPdf")
	@ResponseBody
	public void getPrintClaimFormPdf(@RequestParam String claimId1, HttpServletRequest request,HttpServletResponse response) {
		DODLog.info(LogConstant.SETTLED_CLAIM_LOG_FILE, SettledClaimController.class,"[getClaimSettledPDF] ## claimId ##" + claimId1);
		try {
		printClaimFromPdfService.createClaimStatementsPDF(claimId1,response,request);
		}
		
		 catch (Exception e) {
			 DODLog.printStackTrace(e, SettledClaimController.class, LogConstant.SETTLED_CLAIM_LOG_FILE);
		}
	}
		
	@PostMapping("/viewClaimDetails")
	public String getviewClaimDetails(@RequestParam String tadaClaimId,Model model) {
		ViewClaimRequestBean data=settledClaimService.getviewDataSettled(tadaClaimId);
		
		if(data.getTravelTypeId().equals("100002")) {
			 
			model.addAttribute("taDaClaimDetails", data);
			return url+"/suplyViewTDClaim";
		}else if(data.getTravelTypeId().equals("100001")) {
			model.addAttribute("taDaClaimDetails", data);
			return url+"/suplyViewPTClaim";
		}
		model.addAttribute("taDaClaimDetails", data);
		return url+"/suplyViewLTCClaim";
	}
	
}
