package com.pcda.mb.reports.claimreport.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.TravelType;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.reports.claimreport.model.ClaimReportInputModel;
import com.pcda.mb.reports.claimreport.model.ClaimReportModel;
import com.pcda.mb.reports.claimreport.service.ClaimReportService;
import com.pcda.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/reports")
public class ClaimReportController {

private String path = "/MB/Reports/claimreport/";
	@Autowired
	private ClaimReportService claimReportService;
	
	@GetMapping("/claimReport")
	public String railIrlaReport(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		Optional<OfficeModel> officeModel = claimReportService.getOfficesByGroupId(loginUser.getUserId());
		String groupId = " ";
		String unitName = " ";
		if (officeModel.isPresent()) {
			groupId = officeModel.get().getGroupId();
			unitName = officeModel.get().getName();
		}
		Map<String, String> accountOffice = claimReportService.getAllPao();
		List<TravelType> travelType = claimReportService.getAllTravelType(1);

		model.addAttribute("accountOfficeMap", accountOffice);
		model.addAttribute("travelTypeList", travelType);
		model.addAttribute("groupID", groupId);
		model.addAttribute("name", unitName);
		model.addAttribute("claimReportInputModel", new ClaimReportInputModel());
		
		
		return path + "claimreport";
	}
	
	
	@PostMapping("/postclaimReport")
	public String getClaimReport(ClaimReportInputModel claimReportInputModel, Model model, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		
		String encryptPno = claimReportInputModel.getPersonalNo();
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, encryptPno);
		claimReportInputModel.setPersonalNo(decryptPersonalNo);
		
		List<ClaimReportModel> list = claimReportService.getClaimReport(claimReportInputModel);

		if (list.isEmpty()) {
			model.addAttribute("norecordfound", "No Record Found");
		} else {
			model.addAttribute("claimReportList", list);
		}
		Optional<OfficeModel> officeModel = claimReportService.getOfficesByGroupId(loginUser.getUserId());
		String groupId = " ";
		String unitName = " ";
		if (officeModel.isPresent()) {
			groupId = officeModel.get().getGroupId();
			unitName = officeModel.get().getName();
		}
		Map<String, String> accountOffice = claimReportService.getAllPao();
		List<TravelType> travelType = claimReportService.getAllTravelType(1);

		model.addAttribute("accountOfficeMap", accountOffice);
		model.addAttribute("travelTypeList", travelType);
		model.addAttribute("groupID", groupId);
		model.addAttribute("name", unitName);
		model.addAttribute("claimReportInputModel", claimReportInputModel);
		
		
		return path + "claimreport";
	}
	
	
}
