package com.pcda.mb.reports.railirlareport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.reports.railirlareport.model.RailIrlaReportModel;
import com.pcda.mb.reports.railirlareport.service.RailIrlaReportService;
import com.pcda.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/reports")
public class RailIrlaReportController {

	private String path = "/MB/Reports/railirlareport/";
	
	
	@Autowired
	private RailIrlaReportService railIrlaReportService;
	
	@GetMapping("/railIrlaReport")
	public String railIrlaReport(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		
		if (loginUser == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("railReportModel", new RailIrlaReportModel());
		
		
		return  path + "railirlareport";
	}
	
	
	@PostMapping("/getTravelData")
	public String getTravelData(RailIrlaReportModel railReportModel ,Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		String encryptPno = railReportModel.getPersonalNo();
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, encryptPno);
		railReportModel.setPersonalNo(decryptPersonalNo);

		List<RailIrlaReportModel> list = railIrlaReportService.getTravelIdByData(railReportModel);
		if(list == null || list.isEmpty()) {
			model.addAttribute("errorMessage", "No Record Found");
		}else {
			model.addAttribute("railReportList", list);
		}	
		model.addAttribute("railReportModel", railReportModel);
		
		
			
		return  path + "railirlareport"; 
	}
	
	
	
	
	
}
