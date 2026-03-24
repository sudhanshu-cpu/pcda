package com.pcda.pao.transactionsettlementair.voucheracknowledgement.controller;

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

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.pao.transactionsettlementair.voucheracknowledgement.model.AckVoucherFormModel;
import com.pcda.pao.transactionsettlementair.voucheracknowledgement.model.GetAckSearchParentModel;
import com.pcda.pao.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckParentModel;
import com.pcda.pao.transactionsettlementair.voucheracknowledgement.model.PostVoucherAckResponse;
import com.pcda.pao.transactionsettlementair.voucheracknowledgement.service.VoucherAckService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pao")
public class VoucherAckController {

	@Autowired
	private TravelTypeServices travelType;

	@Autowired
	private VoucherAckService ackService;

	@Autowired
	private OfficesService officeService;
	private String pageUrl = "/PAO/transactionsettlementair/voucheracknowledgement/voucheracksearch";

	@GetMapping("/searchVouchAck")
	public String searchVocherForm(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		Optional<OfficeModel> office = officeService.getOfficeByUserId(loginUser.getUserId());
		  String groupId="";
		   String name="";
		    if(office.isPresent()) {
			
			groupId=office.get().getGroupId();
			name=office.get().getName();
			
		    }
		    model.addAttribute("groupId", groupId);	
		    model.addAttribute("name", name);	
		
		model.addAttribute("searchModel", new AckVoucherFormModel());
		model.addAttribute("travelTypeList", travelType.getAllTravelType(1));
		return pageUrl;
	}

	@PostMapping("/getAckSearchData")
	public String getAckData(Model model, AckVoucherFormModel ackModel, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		
			Optional<OfficeModel> office = officeService.getOfficeByUserId(loginUser.getUserId());
			   String name="";
			   String groupId="";
			    if(office.isPresent()) {
			    	groupId=office.get().getGroupId();
				name=office.get().getName();
			    }
			    model.addAttribute("groupId", groupId);	
		    model.addAttribute("name", name);
	//	    model.addAttribute("office", office);
			model.addAttribute("searchModel", ackModel);
			model.addAttribute("travelTypeList", travelType.getAllTravelType(1));
		List<GetAckSearchParentModel> modelList = ackService.getAckSearchData(ackModel);
        if (modelList.isEmpty()) {
			model.addAttribute("noResult", "No Result Found !!");
			return pageUrl;
		}else {
			model.addAttribute("ackdataList", modelList);
			return pageUrl;
		}
	}

//save
	@PostMapping("/saveVoucherAck")
	public String getSaveAckVoucher(PostVoucherAckParentModel parentModel, Model model,
			MultipartHttpServletRequest request, BindingResult result, RedirectAttributes attributes) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		parentModel.setLoginUserId(loginUser.getUserId());
		if (result.hasErrors()) {
			DODLog.error(LogConstant.VOUCHER_ACKNOWLEDGEMENT_LOG_FILE, VoucherAckController.class,
					"VOUCHER ACKNOWLEDGEMENT ERROR: " + result.getAllErrors());
			ObjectError objectError = result.getAllErrors().get(0);
			attributes.addFlashAttribute("errors", objectError.getDefaultMessage());

			return "redirect:searchVouchAck";
		} else {
			PostVoucherAckResponse ackResponse = ackService.getSaveVoucherAck(parentModel, request);
			if (ackResponse != null) {
				attributes.addFlashAttribute("message", ackResponse.getErrorMessage());
				return "redirect:searchVouchAck";
			} else
				return "/common/errorPage";
		}

	}

//popUp Ajax for AckHistory

	@PostMapping("/voucherAckHistory")
	public String getVoucherAckHistory(@RequestParam String voucherNo, Model model) {

		model.addAttribute("ackHistoryList", ackService.getVoucherAckHistory(voucherNo));

		return "/PAO/transactionsettlementair/voucheracknowledgement/voucherackpopup";
	}
}
