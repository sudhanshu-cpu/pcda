package com.pcda.mb.adduser.bulktransferinreemployment.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.UserStringResponse;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.bulktransferin.controller.BulkTransferInController;
import com.pcda.mb.adduser.bulktransferin.model.PostBulkTransferInModel;
import com.pcda.mb.adduser.bulktransferinreemployment.model.PostBulkTransferInReEmpModel;
import com.pcda.mb.adduser.bulktransferinreemployment.service.BulkTransferInAndreemploymentService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mb")
public class BulkTransferInAndreemploymentController {
	
	@Autowired
	private BulkTransferInAndreemploymentService reemploymentService;
	
	@Autowired
	private OfficesService officesService;
	
	private String url = "/MB/AddUsers/BulkTransferInReemployment";
	
	
	@GetMapping("/bulkTransferInReemployment")
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
		Optional<OfficeModel> office = officesService.getOfficeByUserId(loginUser.getUserId());
		String name="";
		if (office.isPresent()) {
			name=office.get().getName();
		}

		model.addAttribute("loginUserId", loginUser.getUserId());
		model.addAttribute("visitorServiceId", loginUser.getServiceId());
		model.addAttribute("bulkForm",new PostBulkTransferInModel());
		model.addAttribute("serviceList", reemploymentService.getTransferInReEmpServices());
		model.addAttribute("retirAgeOther", PcdaConstant.RETIR_AGE_OTHER);
		model.addAttribute("retirAgePBOR", PcdaConstant.RETIR_AGE_PBOR);
		model.addAttribute("name", name);
		model.addAttribute("serviceId", serviceId);
			return url + "/bulkTransferInReemployment";
		
	}
	
	@PostMapping("/createBulkTransferInReEmp")
	public String createBulkTransferInRemp(@ModelAttribute @Valid PostBulkTransferInReEmpModel postBulkTransferInModel, Model model,
			HttpServletRequest request, BindingResult result, RedirectAttributes attributes) {
		
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		
		if (result.hasErrors()) {
			DODLog.error(LogConstant.TRANSFER_IN_LOG_FILE, BulkTransferInAndreemploymentController.class,
					"[saveUnitMovement] Error::::: " + result.getAllErrors());
			
			return url + "/bulkTransferInReemployment";
		}

		postBulkTransferInModel.setLoginUserId(loginUser.getUserId());
		DODLog.info(LogConstant.TRANSFER_IN_LOG_FILE, BulkTransferInAndreemploymentController.class,
				"PostBulkTransferInReEmpModel:: " + postBulkTransferInModel);

		Optional<OfficeModel> optionalOffice = officesService.getOfficeByUserId(loginUser.getUserId());
		OfficeModel officeModel = new OfficeModel();
		if (optionalOffice.isPresent()) {
			officeModel = optionalOffice.get();
		}
		model.addAttribute("serviceList", reemploymentService.getTransferInReEmpServices());
		model.addAttribute("groupId", officeModel.getGroupId());
		model.addAttribute("model", postBulkTransferInModel);
		
		return url + "/saveBulkTransferInReemployment";
		
	}
	
		
	
	@PostMapping("/submitBulkTransferInReEmp")
	public String saveBulkTransferInReEmp(RedirectAttributes attributes, HttpServletRequest request) {
		

		try {
			PostBulkTransferInReEmpModel postBulkTransferInModel= new PostBulkTransferInReEmpModel();
			reemploymentService.initialiseVariable(request,postBulkTransferInModel);
			
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());

			LoginUser loginUser = sessionVisitor.getLoginUser();
			if (loginUser == null) {
				return "redirect:/login";
			}

				postBulkTransferInModel.setUnitRelocationDate(
						CommonUtil.formatString(postBulkTransferInModel.getUnitRelocationDateStr(), "dd-MM-yyyy"));
				postBulkTransferInModel.setLoginUserId(loginUser.getUserId());
				
			     if(!reemploymentService.validateBean(postBulkTransferInModel).equalsIgnoreCase("OK")) {
			    	 attributes.addFlashAttribute("errors",reemploymentService.validateBean(postBulkTransferInModel));
						return "redirect:/mb/errorPagebulkTransferIn";
			     }
				DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, BulkTransferInAndreemploymentController.class,
						"[BULK TRANSFER IN REEMPLOYMENT] Saving Bulk Transfer In Reemployment ::::" + postBulkTransferInModel);
				UserStringResponse bulkTransferInResponse = reemploymentService
						.saveBulkTransferInReEmp(postBulkTransferInModel);
				if (bulkTransferInResponse.getErrorCode() == 200) {
					
					attributes.addFlashAttribute("success", bulkTransferInResponse.getErrorMessage());
					return "redirect:/mb/cnfPageBulkTransferIn";
				} else {
					
					attributes.addFlashAttribute("errors", bulkTransferInResponse.getErrorMessage());
					return "redirect:/mb/errorPagebulkTransferIn";
				}
			
		} catch (Exception e) {
			DODLog.printStackTrace(e, BulkTransferInController.class, LogConstant.TRANSFER_IN_LOG_FILE);
			attributes.addFlashAttribute("errors", "Error while creating TransferIn & Reemployment");
		}
		return "redirect:/mb/bulkTransferInReemployment";
	}



}
