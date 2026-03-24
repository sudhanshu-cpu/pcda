package com.pcda.adg.reports.unitsandestablishmentsreport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.adg.reports.unitsandestablishmentsreport.model.UnitAndEstablishmentResponse;
import com.pcda.adg.reports.unitsandestablishmentsreport.model.UnitAndEstablishmentResponseModel;
import com.pcda.adg.reports.unitsandestablishmentsreport.service.UnitsAndEstablishmentsReportService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/adg")
public class UnitsAndEstablishmentsReportController {
  
	@Autowired
	private UnitsAndEstablishmentsReportService unitsAndEstablishmentsReportService;
	
	@GetMapping("/unitsAndEstablishmentsReportForm")
	public String getUnitsAndEstablishmentsReportForm(Model model , HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("serviceMap", unitsAndEstablishmentsReportService.getAllServiceMap());
		
		return "ADG/Reports/unitsandestablishmentsreport/unitsAndEstablishmentsReportForm";
	}
	
	@PostMapping("/getUnitsAndEstablishmentsReportData")
	public String getUnitsAndEstablishmentsReportData(Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		UnitAndEstablishmentResponseModel result = null;
		if (request.getParameter("serviceId") == null || request.getParameter("serviceId").isBlank()) {
			redirectAttributes.addFlashAttribute("validationErrorMsg", "Service should be selected !!");
			return "redirect:/adg/unitsAndEstablishmentsReportForm";
		}
		model.addAttribute("serviceId", request.getParameter("serviceId"));
		model.addAttribute("serviceMap", unitsAndEstablishmentsReportService.getAllServiceMap());
		UnitAndEstablishmentResponse response = unitsAndEstablishmentsReportService
				.getUnitsAndEstablishmentsData(request.getParameter("serviceId"));
		if (response != null && response.getErrorCode() == 200) {
			result = response.getResponse();
			model.addAttribute("unitData", result);

		} else {
			model.addAttribute("Errormessage",
					response.getErrorMessage() != null ? response.getErrorMessage() : "Unable to get your report, please try again !!");
		}

		return "ADG/Reports/unitsandestablishmentsreport/unitsAndEstablishmentsReportForm";
	}
	
	@PostMapping("/getUnitsAndEstablishmentsReportDetails")
	public String getUnitsAndEstablishmentsReportDetails(Model model, HttpServletRequest request) {

		model.addAttribute("unitDetails",
				unitsAndEstablishmentsReportService.getUnitsAndEstablishmentsDetails(request.getParameter("service")));

		return "ADG/Reports/unitsandestablishmentsreport/unitsAndEstablishmentsReportDetails";
	}
}
