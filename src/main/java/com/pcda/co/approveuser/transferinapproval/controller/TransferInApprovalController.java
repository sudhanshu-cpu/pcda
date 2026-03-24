package com.pcda.co.approveuser.transferinapproval.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.co.approveuser.transferinapproval.model.PosttransferInApprovalModel;
import com.pcda.co.approveuser.transferinapproval.model.TransferInApprovalModel;
import com.pcda.co.approveuser.transferinapproval.model.TransferInApprovalResponse;
import com.pcda.co.approveuser.transferinapproval.service.TransferInApprovalService;
import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class TransferInApprovalController {

	private String trInApprovalUrl = "CO/ApproveUser/TransferInApproval/";
	
	
	@Autowired
	private TransferInApprovalService transferInApprovalService;
	
	@GetMapping("/approvalTransferIn")
	public String appTransferIn(Model model, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		
		BigInteger loginUserId = loginUser.getUserId();
		Optional<OfficeModel> officeModel = transferInApprovalService.getOfficeByUserIdTranIn(loginUserId);
		
		String groupId="";
		String unitServiceId="";
		String loginVisitorUnitPaoId="";
		String loginVisitorUnitAirPaoId="";
		
		if(officeModel.isPresent())
		{
			groupId=officeModel.get().getGroupId();
			unitServiceId=officeModel.get().getServiceId();
			loginVisitorUnitAirPaoId=officeModel.get().getPaoAirGroupId();
			loginVisitorUnitPaoId=officeModel.get().getPaoGroupId();
		}
		
		model.addAttribute("loginVisitorUnitAirPaoId", loginVisitorUnitAirPaoId);
		model.addAttribute("loginVisitorUnitPaoId", loginVisitorUnitPaoId);
		model.addAttribute("unitServiceId", unitServiceId);
		model.addAttribute("groupId", groupId);
		List<TransferInApprovalModel> transeApprovalList=transferInApprovalService.getApprovalListTrnIn(groupId);
		
		if(! transeApprovalList.isEmpty() ) {
			
			model.addAttribute("approvalList", transeApprovalList);
		}else {
			model.addAttribute("errorMassage", "No Transfer Case Found For Your Unit");
			
		}
		return trInApprovalUrl + "transferInApproval";
	}
	
	
	@PostMapping("/approvalTransferIn")
	public String approvetransfeiIn(@ModelAttribute PosttransferInApprovalModel  model, HttpServletRequest request,RedirectAttributes redirect) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		model.setLoginUserId(loginUser.getUserId());
		DODLog.info(LogConstant.TRANSFER_IN_APPROVAL, TransferInApprovalController.class, "Update Approval for Transfer In" + model);
	
		
		TransferInApprovalResponse trResponse =transferInApprovalService.updateApprovalTranIn(model);
		
		if(trResponse!=null && trResponse.getErrorCode()==200) {
			redirect.addFlashAttribute("success", trResponse.getErrorMessage());
		return "redirect:approvalTransferIn";
		} else {
			  
			return "redirect:/mb/errorPageTransferIn";
		}
	}

}
