package com.pcda.mb.reports.exceptionalbookingreport.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.reports.exceptionalbookingreport.model.ExceptionalBkgReportPost;
import com.pcda.mb.reports.exceptionalbookingreport.model.GetExceptionalReportData;
import com.pcda.mb.reports.exceptionalbookingreport.model.GetUnitPrsnlExpReportData;
import com.pcda.mb.reports.exceptionalbookingreport.model.UnitPrsnlExcepBkgRptPost;
import com.pcda.mb.reports.exceptionalbookingreport.service.ExceptionalBkgService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/reports")
public class ExceptionalBkgController {

	@Autowired
	private ExceptionalBkgService exceptionalBkgService;

	@Autowired
	private OfficesService officeService;

	@GetMapping(value = "/exceptionalBkgReport")
	public String getExpcepBkg(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());

		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("formModel", new ExceptionalBkgReportPost());
		return "/MB/Reports/exceptionalbookingreport/exceptionalBkgReport";
	}

	@PostMapping(value = "/exceptionalBkgReportData")
	public String getExpcepBkgData(ExceptionalBkgReportPost exceptionalBkgReportPost, Model model,
			HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		Optional<OfficeModel> office = officeService.getOfficeByUserId(loginUser.getUserId());
		String groupId = "";
		if (office.isPresent()) {
			groupId = office.get().getGroupId();
		}
		
		if (!exceptionalBkgReportPost.getPersonalNo().equalsIgnoreCase("")
				|| (!exceptionalBkgReportPost.getFromDate().equalsIgnoreCase("")
						&& !exceptionalBkgReportPost.getToDate().equalsIgnoreCase(""))) {

			exceptionalBkgReportPost.setGroupId(groupId);
			List<GetExceptionalReportData> expReportList =exceptionalBkgService.getExceptionalBkgReport(exceptionalBkgReportPost);
			if(expReportList!=null && !expReportList.isEmpty()) {
				model.addAttribute("bookingRepoList", expReportList);
			}else {
				model.addAttribute("errorMessage", "No Record Found!!");
			}
		} else {

			model.addAttribute("errorMessage", "Please Provide Valid Input !!");
		}
		
		model.addAttribute("formModel", exceptionalBkgReportPost);
		return "/MB/Reports/exceptionalbookingreport/exceptionalBkgReport";
	}
	
	
	@GetMapping(value = "/unitPersnalExpBkgReport")
	public String getUnitPrsnlExpcepBkg(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());

		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("formModel", new UnitPrsnlExcepBkgRptPost());
		return "/MB/Reports/exceptionalbookingreport/unitPrsnlExceptionalBkgReport";
	}

	@PostMapping(value = "/unitPrsnlExcepnlBkgReportData")
	public String getUnitprsnlExpcepBkgData(UnitPrsnlExcepBkgRptPost unitPrsnlExpBkgRptPost, Model model,
			HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		Optional<OfficeModel> office = officeService.getOfficeByUserId(loginUser.getUserId());
		String groupId = "";
		if (office.isPresent()) {
			groupId = office.get().getGroupId();
		}
		
		if (!unitPrsnlExpBkgRptPost.getPersonalNo().equalsIgnoreCase("")
				|| (!unitPrsnlExpBkgRptPost.getFromDate().equalsIgnoreCase("")
						&& !unitPrsnlExpBkgRptPost.getToDate().equalsIgnoreCase(""))) {

			unitPrsnlExpBkgRptPost.setGroupId(groupId);
			List<GetUnitPrsnlExpReportData> expReportList =exceptionalBkgService.getUnitPrsnlExceptionalBkgReport(unitPrsnlExpBkgRptPost);
			if(expReportList!=null && !expReportList.isEmpty()) {
				model.addAttribute("bookingRepoList", expReportList);
			}else {
				model.addAttribute("errorMessage", "No Record Found!!");
			}
		} else {

			model.addAttribute("errorMessage", "Please Provide Valid Input !!");
		}
		
		model.addAttribute("formModel", unitPrsnlExpBkgRptPost);
		return "/MB/Reports/exceptionalbookingreport/unitPrsnlExceptionalBkgReport";
	}

	// Pop_Up Controller for tickets booking dtls
	@RequestMapping(value = "/expTicketsBkgDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public String getExpcepBkgDtlsById(@RequestParam String bookingId, Model model, HttpServletRequest request) {

		model.addAttribute("userList", exceptionalBkgService.getExpcepBkgDtlsById(bookingId));

		return "/MB/Reports/exceptionalbookingreport/exptickestbookingdtls";
	}

}
