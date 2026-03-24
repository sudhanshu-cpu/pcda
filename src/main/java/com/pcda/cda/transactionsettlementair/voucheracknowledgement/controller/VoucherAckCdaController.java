package com.pcda.cda.transactionsettlementair.voucheracknowledgement.controller;



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

import com.pcda.cda.transactionsettlementair.voucheracknowledgement.model.AckVoucherCdaFormModel;
import com.pcda.cda.transactionsettlementair.voucheracknowledgement.model.GetAckSearchCdaParentModel;
import com.pcda.cda.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckCdaParentModel;
import com.pcda.cda.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckCdaResponse;
import com.pcda.cda.transactionsettlementair.voucheracknowledgement.service.VoucherAckCdaService;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;

import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cda")
public class VoucherAckCdaController {

	@Autowired
	private TravelTypeServices travelType;

	@Autowired
	private VoucherAckCdaService ackService;

	@Autowired
	private OfficesService officeService;
	private String pageUrl = "/CDA/transactionsettlementair/voucheracknowledgement/voucheracksearchcda";

	@GetMapping("/searchVouchAckCda")
	public String searchVocherForm(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		AckVoucherCdaFormModel ackModel = new AckVoucherCdaFormModel();
		Optional<OfficeModel> office = officeService.getOfficeByUserId(loginUser.getUserId());
		String groupId="";
		if(office.isPresent()) {
	 groupId=office.get().getGroupId();
		}
		model.addAttribute("searchModel", ackModel);
		model.addAttribute("travelTypeList", travelType.getAllTravelType(1));
		model.addAttribute("accountMap", ackService.getAccountOfficeListMap("CDA", groupId));
		return pageUrl;
	}

	@PostMapping("/getAckSearchDataCda")
	public String getAckData(Model model, AckVoucherCdaFormModel ackModel, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		List<GetAckSearchCdaParentModel> modelList = ackService.getAckSearchData(ackModel);
		Optional<OfficeModel> office = officeService.getOfficeByUserId(loginUser.getUserId());
		
		String groupId="";
		if(office.isPresent()) {
	 groupId=office.get().getGroupId();
		}
			model.addAttribute("searchModel", ackModel);
			model.addAttribute("travelTypeList", travelType.getAllTravelType(1));
			model.addAttribute("accountMap", ackService.getAccountOfficeListMap("CDA", groupId));
		if (modelList.isEmpty()) {
			model.addAttribute("noResult", "No Result Found !!");
		} else {
		
			model.addAttribute("ackdataList", modelList);
		}
			return pageUrl;
	}
	

//save
	@PostMapping("/saveVoucherAckCda")
	public String getSaveAckVoucher(PostVoucherAckCdaParentModel parentModel, Model model,
			MultipartHttpServletRequest request, BindingResult result, RedirectAttributes attributes) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		parentModel.setLoginUserId(loginUser.getUserId());
		if (result.hasErrors()) {
			DODLog.error(LogConstant.CDA_VOUCHER_LOG_FILE, VoucherAckCdaController.class,
					"VOUCHER ACKNOWLEDGEMENT ERROR: " + result.getAllErrors());
			ObjectError objectError = result.getAllErrors().get(0);
			attributes.addFlashAttribute("errors", objectError.getDefaultMessage());

			return "redirect:searchVouchAckCda";
		} else {
			PostVoucherAckCdaResponse ackResponse = ackService.getSaveVoucherAck(parentModel, request);
			if (ackResponse != null) {
				attributes.addFlashAttribute("message", ackResponse.getErrorMessage());
				return "redirect:searchVouchAckCda";
			} else
				return "/common/errorPage";
		}

	}

//popUp Ajax for AckHistory

	@PostMapping("/voucherAckHistoryCda")
	public String getVoucherAckHistory(@RequestParam String voucherNo, Model model) {

		model.addAttribute("ackHistoryList", ackService.getVoucherAckHistory(voucherNo));

		return "/CDA/transactionsettlementair/voucheracknowledgement/voucherackpopupcda";
	}
}
