package com.pcda.co.approveuser.approvebulktransferinreemployment.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.co.approveuser.approvebulktransferin.model.BulkTransferApprovalModel;
import com.pcda.co.approveuser.approvebulktransferin.model.BulkTransferInApprovalResponse;
import com.pcda.co.approveuser.approvebulktransferin.model.BulkTransferUserViewBean;
import com.pcda.co.approveuser.approvebulktransferin.model.PostBulkTransferInApprovalModel;
import com.pcda.co.approveuser.approvebulktransferinreemployment.service.ApproveBulkTransferInReEmpService;
import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class ApproveBulkTransferInReEmpController {
	
	private String path = "CO/ApproveUser/ApproveBulkTransferInReemployment/";

	@Autowired
	private ApproveBulkTransferInReEmpService transferInReEmpService;

	@GetMapping("/approvalBulkTransferInReEmpReq")
	public String approvalBulkTransferIn(Model model, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		Optional<OfficeModel> optionalOffice = transferInReEmpService.getOfficeByUserId(loginUser.getUserId());
		String officeId = "";

		if (optionalOffice.isPresent()) {
			OfficeModel officeModel = optionalOffice.get();
			officeId = officeModel.getGroupId();
		}
		
		List<BulkTransferApprovalModel> bulkTransferProfile = transferInReEmpService.getAllReemploymentProfile(officeId);
		
		model.addAttribute("approvalList", bulkTransferProfile);

		return path + "bulkTransferInReemploymentapproval";
	}
	
	@PostMapping("/bulkTransferInViewReEmp")
	public String bulkTransferInUser(Model model, @RequestParam String transferId, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("userList", transferInReEmpService.getviewUserDetailsReEmployment(transferId));
		return path + "bulkTransferInReemploymentView";
	}

	@PostMapping("/approveBulkTransferInReEmp")
	public String approveBulkTransferInReEmp(@ModelAttribute PostBulkTransferInApprovalModel postApprovalModel,
			HttpServletRequest request, RedirectAttributes redirect) {

		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();
			
			if (loginUser == null) {
				return "redirect:/login";
			}
			postApprovalModel.setLoginUserServiceId(loginUser.getServiceId());
			BulkTransferInApprovalResponse approvalResponse;
			postApprovalModel.setLoginUserId(loginUser.getUserId());
			Optional<OfficeModel> optionalOffice = transferInReEmpService.getOfficeByUserId(loginUser.getUserId());
			
			if (optionalOffice.isPresent()) {
				OfficeModel officeModel = optionalOffice.get();
				postApprovalModel.setGroupId(officeModel.getGroupId());
				
			}

			approvalResponse = transferInReEmpService.approveTransferInReEmp(postApprovalModel);
			if (approvalResponse != null && approvalResponse.getErrorCode() == 200) {
				List<BulkTransferUserViewBean> personalNoList = transferInReEmpService.getviewReemploymentUserDetails(postApprovalModel.getTransferId());
				List<String> pNo=new ArrayList<>();
				personalNoList.forEach(p->pNo.add(p.getPersonalNo()));
				String perNo = String.join(", ", pNo);
				if (postApprovalModel.getEvent().equalsIgnoreCase("approve")) {
					redirect.addFlashAttribute("success","The Transfer In for the " + perNo + " Personnel has been successfully approved");
					return "redirect:approvalBulkTransferInReEmpReq";
				}
				if (postApprovalModel.getEvent().equalsIgnoreCase("disapprove")) {
					redirect.addFlashAttribute("success", "Request Rejected Successfully");
					return "redirect:approvalBulkTransferInReEmpReq";
				}
			}
		} catch (Exception e) {
			redirect.addFlashAttribute("errors", "Error while Approving TransferIn & Reemployment Request");
			
		}
		return "redirect:approvalBulkTransferInReEmpReq";

	}
	
	

}
