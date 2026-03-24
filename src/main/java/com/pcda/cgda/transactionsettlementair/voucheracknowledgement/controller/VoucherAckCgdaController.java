package com.pcda.cgda.transactionsettlementair.voucheracknowledgement.controller;



import java.util.List;
import java.util.Optional;

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

import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model.AckVoucherCgdaFormModel;
import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model.GetAckSearchCgdaParentModel;
import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckCgdaParentModel;
import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckCgdaResponse;
import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.service.VoucherAckCgdaService;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;

import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cgda")
public class VoucherAckCgdaController {

	@Autowired
	private TravelTypeServices travelType;

	@Autowired
	private VoucherAckCgdaService ackService;

	@Autowired
	private OfficesService officeService;
	private String pathUrl = "/CGDA/transactionsettlementair/voucheracknowledgement/voucheracksearchcgda";

	@GetMapping("/searchVouchAckCgda")
	public String searchVocherForm(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		AckVoucherCgdaFormModel ackModel = new AckVoucherCgdaFormModel();
		Optional<OfficeModel> office = officeService.getOfficeByUserId(loginUser.getUserId());
		
		String groupId="";
		if(office.isPresent()) {
		 groupId=office.get().getGroupId();
		}
		
		model.addAttribute("searchModel", ackModel);
		model.addAttribute("travelTypeList", travelType.getAllTravelType(1));
		model.addAttribute("accountMap", ackService.getAccountOfficeListMap("CGDA", groupId));
		return pathUrl;
	}

	@PostMapping("/getAckSearchDataCgda")
	public String getAckData(Model model, AckVoucherCgdaFormModel ackModel, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		List<GetAckSearchCgdaParentModel> modelList = ackService.getAckSearchData(ackModel);
		Optional<OfficeModel> office = officeService.getOfficeByUserId(loginUser.getUserId());
		String groupId="";
		if(office.isPresent()) {
		 groupId=office.get().getGroupId();
		}
		model.addAttribute("searchModel", ackModel);
		model.addAttribute("travelTypeList", travelType.getAllTravelType(1));
		model.addAttribute("accountMap", ackService.getAccountOfficeListMap("CGDA", groupId));
		if (modelList.isEmpty()) {
			
			model.addAttribute("noResult", "No Result Found !!");
	
		} else {
			model.addAttribute("ackdataList", modelList);
			
		}
		return pathUrl;
	}

//save
	@PostMapping("/saveVoucherAckCgda")
	public String getSaveAckVoucher(PostVoucherAckCgdaParentModel parentModel, Model model,
			MultipartHttpServletRequest request, BindingResult result, RedirectAttributes attributes) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		parentModel.setLoginUserId(loginUser.getUserId());
		if (result.hasErrors()) {
			DODLog.error(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherAckCgdaController.class,
					"VOUCHER ACKNOWLEDGEMENT ERROR: " + result.getAllErrors());
			ObjectError objectError = result.getAllErrors().get(0);
			attributes.addFlashAttribute("errors", objectError.getDefaultMessage());

			return "redirect:searchVouchAckCgda";
		} else {
			PostVoucherAckCgdaResponse ackResponse = ackService.getSaveVoucherAck(parentModel, request);
			if (ackResponse != null) {
				attributes.addFlashAttribute("message", ackResponse.getErrorMessage());
				return "redirect:searchVouchAckCgda";
			} else
				return "/common/errorPage";
		}

	}

//popUp Ajax for AckHistory

	@PostMapping("/voucherAckHistoryCgda")
	public String getVoucherAckHistory(@RequestParam String voucherNo, Model model) {

		model.addAttribute("ackHistoryList", ackService.getVoucherAckHistory(voucherNo));

		return "/CGDA/transactionsettlementair/voucheracknowledgement/voucherackpopupcgda";
	}
}
