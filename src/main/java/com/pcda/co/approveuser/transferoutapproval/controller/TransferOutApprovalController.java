package com.pcda.co.approveuser.transferoutapproval.controller;

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

import com.pcda.co.approveuser.transferinapproval.model.TransferInApprovalModel;
import com.pcda.co.approveuser.transferoutapproval.model.PostAppDisAppTransferOutModel;
import com.pcda.co.approveuser.transferoutapproval.model.TransferOutApprovalModel;
import com.pcda.co.approveuser.transferoutapproval.model.TransferOutApprovalResponse;
import com.pcda.co.approveuser.transferoutapproval.service.TransferOutApprovalService;
import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class TransferOutApprovalController {
	
     private String trOutApprovalUrl = "CO/ApproveUser/TransferOutApproval/";
	
	@Autowired
	private TransferOutApprovalService transferOutApprovalService;
	
	 @GetMapping("/approvalTransferOut")
	 public String approvalTranOut(Model model, HttpServletRequest request, @ModelAttribute TransferInApprovalModel transferInApprovalModel ) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		BigInteger loginUserId = loginUser.getUserId();
		Optional<OfficeModel> officeModel = transferOutApprovalService.getOfficeByUserIdTranOut(loginUserId);
		String groupId="";
		if(officeModel.isPresent())
		{
			groupId=officeModel.get().getGroupId();
		}
		
		List<TransferOutApprovalModel> transeApprovalList=transferOutApprovalService.getApprovalListTrnOut(groupId);
		
		if(! transeApprovalList.isEmpty() ) {
			
			model.addAttribute("approvalList", transeApprovalList);
		}else {
			model.addAttribute("errorMassage", "No Transfer Case Found For Your Unit");
		}
		return trOutApprovalUrl + "transferOutApproval";
	}
	

	@PostMapping("/approvalTransferOut")
	public String approveTransferOut(@ModelAttribute PostAppDisAppTransferOutModel  model, HttpServletRequest request, RedirectAttributes attributes ) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	
		try {
//			String unitName = "";
//            String crationDate="";
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		model.setLoginUserId(loginUser.getUserId());
		DODLog.info(LogConstant.TRANSFER_OUT_APPROVAL, TransferOutApprovalController.class, "Update Approval for Transfer out" + model);
		TransferOutApprovalResponse toutResponse =	transferOutApprovalService.updateApprovalTraOut(model);

//		String profile= model.getOldUnit();
//		   String[] parts = profile.split("#");
//
//	        // Iterate through the parts to find UNIT and CREATED_DATE
//	        for (String part : parts) {
//	            if (part.startsWith("UNIT=")) {
//	                // Extract UNIT
//	            	unitName = part.substring("UNIT=".length());
//	            } else if (part.startsWith("CREATED_DATE=")) {
//	                // Extract CREATED_DATE
//	            	crationDate = part.substring("CREATED_DATE=".length());
//	            }
//	        }
       
		if(toutResponse!=null && toutResponse.getErrorCode()==200) {
			 attributes.addFlashAttribute("success",toutResponse.getErrorMessage());
			 return "redirect:approvalTransferOut";
		}else {
			return "redirect:/mb/errorPageTransferOut";
	            }
		
		
	
		} catch (Exception e) {
			DODLog.printStackTrace(e, TransferOutApprovalController.class, LogConstant.TRANSFER_OUT_APPROVAL);
			return "redirect:/mb/errorPageTransferOut";
		}
		
	}
	
	
}
