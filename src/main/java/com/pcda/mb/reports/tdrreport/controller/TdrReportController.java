package com.pcda.mb.reports.tdrreport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.TravelType;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.reports.tdrreport.model.TdrBookingPopUpModel;
import com.pcda.mb.reports.tdrreport.model.TdrReportInputModel;
import com.pcda.mb.reports.tdrreport.model.TdrReportModel;
import com.pcda.mb.reports.tdrreport.service.TdrReportService;
import com.pcda.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/reports")
public class TdrReportController {
	
	private String pageUrl = "/MB/Reports/tdrreport/";
	
	@Autowired
	private TdrReportService tdrReportService;
	
	
	
	@GetMapping("/tdrReport")
	public String getTdrReport(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		
		List<TravelType> travelType = tdrReportService.getAllTravelType(1);
		List<OfficeModel> pao = tdrReportService.getAllPao();
		
		model.addAttribute("paoList" , pao);
		model.addAttribute("travelTypeList" , travelType);
		model.addAttribute("formModel", new TdrReportInputModel());
		
		return pageUrl + "tdrreport";
	}
	
	
	@PostMapping("/getTdrReport")
	public String getReport( TdrReportInputModel tdrReportInputModel,  Model model ,HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}	
		
		String encryptPno = tdrReportInputModel.getPersonalNo();
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, encryptPno);
		tdrReportInputModel.setPersonalNo(decryptPersonalNo);

		
		List<TravelType> travelType = tdrReportService.getAllTravelType(1);
		List<OfficeModel> pao = tdrReportService.getAllPao();
		model.addAttribute("paoList" , pao);
		model.addAttribute("travelTypeList" , travelType);
		List<TdrReportModel>   tdrReportList =	tdrReportService.getTdrReport(tdrReportInputModel);	
		
		tdrReportList.forEach(obj -> obj.setCreationFomattedTime(
				CommonUtil.getChangeFormat(
						obj.getCreationTime(), 
						"yyyy-MM-dd", 
						"dd-MM-yyyy")));
		
		if(tdrReportList.isEmpty()) {
			model.addAttribute("listEmpty", "No Records Found For Selected Search Option");
		}else {		
		model.addAttribute("tdrReportList", tdrReportList);
		}
		model.addAttribute("formModel",tdrReportInputModel);
		return  pageUrl + "tdrreport";
	}
	
	
	
	// AJAX CALLING
	@PostMapping("/getTdrBookingDetails")
	public String getTdrBookingDtls(@RequestParam String bookingId  ,Model model ,HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}	
		
		TdrBookingPopUpModel bookingModel  = tdrReportService.getTdrBookingDetails(bookingId);
		model.addAttribute("bookingModel" ,bookingModel);
		
		return pageUrl + "tdrPopup";
	}
	
	
	
	
	
	
	
	
}
