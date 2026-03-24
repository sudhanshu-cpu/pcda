package com.pcda.co.approveuser.transferinandreemloyeemet.controller;

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

import com.pcda.co.approveuser.transferinandreemloyeemet.model.PostTranInReempApprovalModel;
import com.pcda.co.approveuser.transferinandreemloyeemet.model.TransferInReemployeApprovalModel;
import com.pcda.co.approveuser.transferinandreemloyeemet.service.TransferInReemployeApprovalService;
import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class TransferInReemplApprovalControoler {
	
	     private String trOutApprovalUrl = "CO/ApproveUser/TransferInReemployement/";
		
		 @Autowired
		 private TransferInReemployeApprovalService transferInReemployeApprovalService;
		 
		 @GetMapping("/approvalTranInReeemployement")
		 public String approvalTranInRee(Model model, HttpServletRequest request) {

			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();

			
			if (loginUser == null) {
				return "redirect:/login";
			}
			BigInteger loginUserId = loginUser.getUserId();
			Optional<OfficeModel> officeModel = transferInReemployeApprovalService.getOfficeByUserIdTranInRe(loginUserId);
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
			
			List<TransferInReemployeApprovalModel> transeApprovalList=transferInReemployeApprovalService.getApprovalListTrnOut(groupId);
			
			if(! transeApprovalList.isEmpty() ) {
				model.addAttribute("approvalList", transeApprovalList);
			}else {
				model.addAttribute("errorMassage", "No Transfer Case Found For Your Unit ");
			}
			return trOutApprovalUrl + "transferInAndReemployementApproval";
		}
	
		 
			@PostMapping("/approvalTranInRee")
			public String approveTransferInReeSave(@ModelAttribute PostTranInReempApprovalModel  transferModel, HttpServletRequest request) {
				SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
				LoginUser loginUser = sessionVisitor.getLoginUser();

				if (loginUser == null) {
					return "redirect:/login";
				}
				transferModel.setLoginUserId(loginUser.getUserId());
				DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT_APPROVAL, TransferInReemplApprovalControoler.class, "###### Update Approval for Transfer in reemployeement ## :: " + transferModel);
				transferInReemployeApprovalService.updateApprovalTraInRee(transferModel);
				return "redirect:approvalTranInReeemployement";
			}
			
		 
		 
		 
		 
		 
}
