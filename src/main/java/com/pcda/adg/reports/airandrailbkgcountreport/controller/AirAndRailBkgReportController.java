package com.pcda.adg.reports.airandrailbkgcountreport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.adg.reports.airandrailbkgcountreport.model.AirAndRailBookingDetails;
import com.pcda.adg.reports.airandrailbkgcountreport.model.AirRailTicketRequestModel;
import com.pcda.adg.reports.airandrailbkgcountreport.service.AirAndRailBkgReportService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/adg")
public class AirAndRailBkgReportController {

	@Autowired
	private AirAndRailBkgReportService airAndRailBkgReportService;

	@GetMapping("/airAndRailBkgCountReportForm")
	public String getAirRailTktBkgCountForm(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("formModel", new AirRailTicketRequestModel());
		model.addAttribute("serviceMap", airAndRailBkgReportService.getAllServiceMap());
		return "ADG/Reports/airandrailbkgcountreport/airAndRailBkgReport";
	}

	@PostMapping("/airAndRailBkgCountReport")
	public String getAirRailTktBkgCountDtls(@ModelAttribute @Valid AirRailTicketRequestModel formModel,
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, AirAndRailBkgReportController.class,
				"AirRailTicketRequestModel validation" + formModel);
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("Errormessage", result.getAllErrors().get(0).getDefaultMessage());
			
			return "redirect:/adg/airAndRailBkgCountReportForm";
		}
		List<AirAndRailBookingDetails> resultList = airAndRailBkgReportService.getAirRailTktBkgCount(formModel);

		if(resultList.isEmpty()) {
			model.addAttribute("Errormessage", "No Record Found !!");
		}
		model.addAttribute("reportData", resultList);
		model.addAttribute("serviceMap", airAndRailBkgReportService.getAllServiceMap());
		model.addAttribute("formModel", formModel);

		return "ADG/Reports/airandrailbkgcountreport/airAndRailBkgReport";
	}
}
