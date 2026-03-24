package com.pcda.mb.requestdashboard.pnrstatus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.requestdashboard.pnrstatus.model.PnrEnquiryResponse;
import com.pcda.mb.requestdashboard.pnrstatus.model.PnrEnquiryResponseBean;
import com.pcda.mb.requestdashboard.pnrstatus.service.TicketPNRStatusService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class TicketPNRStatusController {

	private String path = "MB/RequestDashbord/PNRStatus/";

	@Autowired
	private TicketPNRStatusService ticketPNRStatusService;

	@GetMapping("/pnrStatus")
	public String pnrStatus(HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {

			return "redirect:/login";
		}

		
		return path + "pnrStatus";
	}

	@RequestMapping(value="/getPnrStatus",method= {RequestMethod.GET,RequestMethod.POST})
	public String getPnrStatus(@RequestParam(required = false) String pnrNo, Model model) {
		DODLog.info(LogConstant.PNR_STATUS_LOG, TicketPNRStatusController.class, "Get Status for pnr: " + pnrNo);
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
