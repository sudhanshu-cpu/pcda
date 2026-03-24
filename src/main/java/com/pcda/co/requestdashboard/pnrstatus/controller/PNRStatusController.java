package com.pcda.co.requestdashboard.pnrstatus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.co.requestdashboard.pnrstatus.model.PnrEnquiryResponse;
import com.pcda.co.requestdashboard.pnrstatus.model.PnrEnquiryResponseBean;
import com.pcda.co.requestdashboard.pnrstatus.service.PNRStatusService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class PNRStatusController {

	private String path = "CO/RequestDashBoard/PNRStatus/";

	@Autowired
	private PNRStatusService ticketPNRStatusService;

	@GetMapping("/pnrStatus")
	public String pnrStatus(HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {

			return "redirect:/login";
		}

		DODLog.info(LogConstant.PNR_STATUS_LOG, PNRStatusController.class, "Get PNR Status");
		return path + "pnrStatus";
	}

	@PostMapping("/getPnrStatus")
	public String getPnrStatus(@RequestParam String pnrNo, Model model) {
		DODLog.info(LogConstant.PNR_STATUS_LOG, PNRStatusController.class, "Get Status for pnr: " + pnrNo);
		Boolean isError = false;
		PnrEnquiryResponseBean bean = new PnrEnquiryResponseBean();
		PnrEnquiryResponse response = ticketPNRStatusService.getPNRStatus(pnrNo);
		if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
			bean = response.getResponse();
			if (!(bean.getErrorMessage() == null || bean.getErrorMessage().isBlank())) {
				isError = true;
			}
		} else {
			isError = true;
			bean.setErrorMessage("Unable To get PNR Status");
		}
		model.addAttribute("isError", isError);
		model.addAttribute("pnr", bean);
		return path + "pnrDetails";
	}

}
