package com.pcda.co.approveuser.approvebulktransferin.controller;

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
import com.pcda.co.approveuser.approvebulktransferin.service.ApproveBulkTransferInService;
import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.bulktransferin.controller.BulkTransferInController;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class ApproveBulkTransferInController {

	private String path = "CO/ApproveUser/ApproveBulkTransferIn/";

	@Autowired
	private ApproveBulkTransferInService transferInService;

	@GetMapping("/approvalBulkTransferInReq")
	public String approvalBulkTransferIn(Model model, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		Optional<OfficeModel> optionalOffice = transferInService.getOfficeByUserId(loginUser.getUserId());
		String officeId = "";

		if (optionalOffice.isPresent()) {
			OfficeModel officeModel = optionalOffice.get();
			officeId = officeModel.getGroupId();
		}
		List<BulkTransferApprovalModel> bulkTransferProfile = transferInService.getAllApproval(officeId);
		model.addAttribute("approvalList", bulkTransferProfile);

		return path + "bulkTransferInApproval";
	}

	@PostMapping("/bulkTransferInView")
	public String bulkTransferInUser(Model model, @RequestParam String transferId, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("userList", transferInService.getviewUserDetails(transferId));
		return path + "bulkTransferInView";
	}

	@PostMapping("/approveBulkTransferIn")
	public String approveBulkTransferIn(@ModelAttribute PostBulkTransferInApprovalModel postApprovalModel,
			HttpServletRequest request, RedirectAttributes redirect) {

		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();
			if (loginUser == null) {
				return "redirect:/login";
			}
			postApprovalModel.setLoginUserId(loginUser.getUserId());
			Optional<OfficeModel> optionalOffice = transferInService.getOfficeByUserId(loginUser.getUserId());
			
			if (optionalOffice.isPresent()) {
				OfficeModel officeModel = optionalOffice.get();
				postApprovalModel.setGroupId(officeModel.getGroupId());
			}

			BulkTransferInApprovalResponse approvalResponse = transferInService.approveTransferIn(postApprovalModel);
			if (approvalResponse != null && approvalResponse.getErrorCode() == 200) {
				List<BulkTransferUserViewBean> personalNoList = transferInService.getviewUserDetails(postApprovalModel.getTransferId());
				List<String> pNo=new ArrayList<>();
				personalNoList.forEach(p-> pNo.add(p.getPersonalNo()));
				String perNo = String.join(", ", pNo);
				if (postApprovalModel.getEvent().equalsIgnoreCase("approve")) {
					redirect.addFlashAttribute("success","The Transfer In for the " + perNo + " Personnel has been successfully approved");
					return "redirect:approvalBulkTransferInReq";
				}
				if (postApprovalModel.getEvent().equalsIgnoreCase("disapprove")) {

					redirect.addFlashAttribute("success", "Request Rejected Successfully");
					return "redirect:approvalBulkTransferInReq";
				}
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, BulkTransferInController.class, LogConstant.TRANSFER_IN_APPROVAL);
			redirect.addFlashAttribute("errors", "Error while Approving TransferIn & Reemployment Request");
		}
		return "redirect:approvalBulkTransferInReq";

	}
}
