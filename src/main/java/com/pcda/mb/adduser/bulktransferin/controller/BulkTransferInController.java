package com.pcda.mb.adduser.bulktransferin.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.UserStringResponse;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.bulktransferin.model.PostBulkTransferInModel;
import com.pcda.mb.adduser.bulktransferin.service.BulkTransferInService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class BulkTransferInController {

	@Autowired
	private BulkTransferInService bulkTransferInService;

	private String url = "/MB/AddUsers/BulkTransferIn";

	@GetMapping("/bulkTransferIn")
	public String getBulkTransferIn(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		String serviceId = "";

		if (loginUser == null) {
			return "redirect:/login";
		}
		if (loginUser.getUserServiceId() != null && !loginUser.getUserServiceId().equalsIgnoreCase("null")
				&& !loginUser.getUserServiceId().trim().equals("")) {
			serviceId = loginUser.getUserServiceId();
		} else {
			serviceId = loginUser.getServiceId();
		}
		model.addAttribute("bulkForm", new PostBulkTransferInModel());
			model.addAttribute("serviceId", serviceId);
			return url + "/bulkTransferIn";
		
	}

	@GetMapping("/cnfPageBulkTransferIn")
	public String confimPageIn(Model model) {
		return url + "/conformationPage";
	}

	@GetMapping("/errorPagebulkTransferIn")
	public String errorPageTransferIn(Model model) {
		return url + "/errorPage";
	}

	@PostMapping("/submitBulkTransferIn")
	public String saveUnitMovement(RedirectAttributes attributes, HttpServletRequest request) {
		try {

			PostBulkTransferInModel postBulkTransferInModel = new PostBulkTransferInModel();
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());

		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
			bulkTransferInService.initialiseFormField(request, postBulkTransferInModel);

		Optional<OfficeModel> optionalOffice = bulkTransferInService.getUnitByUserId(loginUser.getUserId());
		OfficeModel officeModel = new OfficeModel();
		if (optionalOffice.isPresent()) {
			officeModel = optionalOffice.get();
		}

			postBulkTransferInModel.setGroupId(officeModel.getGroupId());

				postBulkTransferInModel.setUnitRelocationDate(
						CommonUtil.formatString(postBulkTransferInModel.getUnitRelocationDateStr(), "dd-MM-yyyy"));
				postBulkTransferInModel.setLoginUserId(loginUser.getUserId());

				if (!bulkTransferInService.validateFormField(postBulkTransferInModel).equalsIgnoreCase("OK")) {
					attributes.addFlashAttribute("errors",
							bulkTransferInService.validateFormField(postBulkTransferInModel));
					return "redirect:/mb/errorPagebulkTransferIn";
				}

			UserStringResponse bulkTransferInResponse = bulkTransferInService
						.saveUnitMovement(postBulkTransferInModel);
				if (bulkTransferInResponse.getErrorCode() == 200) {

					attributes.addFlashAttribute("success", bulkTransferInResponse.getErrorMessage());
					return "redirect:/mb/cnfPageBulkTransferIn";
				} else {

					attributes.addFlashAttribute("errors", bulkTransferInResponse.getErrorMessage());
					return "redirect:/mb/errorPagebulkTransferIn";

			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, BulkTransferInController.class, LogConstant.TRANSFER_IN_LOG_FILE);
			attributes.addFlashAttribute("errors", "Error while creating Bulk Transfer In Request");
		}
		return "redirect:/mb/bulkTransferIn";
	}

}
