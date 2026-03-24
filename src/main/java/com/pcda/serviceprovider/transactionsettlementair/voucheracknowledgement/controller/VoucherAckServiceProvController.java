package com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.services.TravelTypeServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model.AckVoucherFormSPModel;
import com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model.GetAckSearchParentSPModel;
import com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckParentSPModel;
import com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckSPResponse;
import com.pcda.serviceprovider.transactionsettlementair.voucheracknowledgement.service.VoucherAckSPService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/serpov")
public class VoucherAckServiceProvController {

	@Autowired
	private TravelTypeServices travelType;

	@Autowired
	private VoucherAckSPService ackService;


	
	private String pageUrl = "/SERVICEPROVIDER/transactionsettlementair/voucheracknowledgement/voucheracksearchsp";

	@GetMapping("/searchVouchAckSP")
	public String searchVocherForm(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
        String serviceProvider = loginUser.getServiceProvider().toString();
		AckVoucherFormSPModel ackModel = new AckVoucherFormSPModel();
		
		model.addAttribute("accountMap", ackService.getApprovedPao("PAO","1"));
		model.addAttribute("searchModel", ackModel);
		model.addAttribute("travelTypeList", travelType.getAllTravelType(1));
		model.addAttribute("serpov",serviceProvider);
		return pageUrl;
	}

	@PostMapping("/getAckSearchDataSP")
	public String getAckData(Model model, AckVoucherFormSPModel ackModel, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		String serviceProvider = loginUser.getServiceProvider().toString();
		List<GetAckSearchParentSPModel> modelList = ackService.getAckSearchData(ackModel);
		model.addAttribute("accountMap", ackService.getApprovedPao("PAO","1"));
		model.addAttribute("searchModel", ackModel);
		model.addAttribute("travelTypeList", travelType.getAllTravelType(1));
		model.addAttribute("serpov",serviceProvider);
		if (modelList.isEmpty()) {
		
			model.addAttribute("noResult", "No Result Found !!");
			return pageUrl;
		} else {
			
		
			model.addAttribute("ackdataList", modelList);

			return pageUrl;
		}
	}

//save
	@PostMapping("/saveVoucherAckSP")
	public String getSaveAckVoucher(PostVoucherAckParentSPModel parentModel, Model model,
			MultipartHttpServletRequest request, BindingResult result, RedirectAttributes attributes) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		parentModel.setLoginUserId(loginUser.getUserId());
		if (result.hasErrors()) {
			DODLog.error(LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE, VoucherAckServiceProvController.class,
					"VOUCHER ACKNOWLEDGEMENT ERROR: " +  result.getAllErrors().get(0).getDefaultMessage());
			ObjectError objectError = result.getAllErrors().get(0);
			attributes.addFlashAttribute("errors", objectError.getDefaultMessage());

			return "redirect:searchVouchAckSP";
		} else {
			PostVoucherAckSPResponse ackResponse = ackService.getSaveVoucherAck(parentModel, request);
			if (ackResponse != null) {
				attributes.addFlashAttribute("message", ackResponse.getErrorMessage());
				return "redirect:searchVouchAckSP";
			} else
				return "/common/errorPage";
		}

	}

//popUp Ajax for AckHistory

	@PostMapping("/voucherAckHistorySP")
	public String getVoucherAckHistory(@RequestParam String voucherNo, Model model) {

		model.addAttribute("ackHistoryList", ackService.getVoucherAckHistory(voucherNo));

		return "/SERVICEPROVIDER/transactionsettlementair/voucheracknowledgement/voucherackpopupsp";
	}
}
