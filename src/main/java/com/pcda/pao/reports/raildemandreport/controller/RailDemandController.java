package com.pcda.pao.reports.raildemandreport.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.pao.reports.raildemandreport.model.ResponsePAOReport;
import com.pcda.pao.reports.raildemandreport.service.RailDemandService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pao")
public class RailDemandController {
	
	@Autowired
	private RailDemandService railDemandService;
	
    @Autowired
    private OfficesService officesService;
	
	private String pageUrl = "/PAO/reports/raildemandreport/";
	
	@GetMapping("/getRailDmdReport")	
	public String getRailDemandRpt(HttpServletRequest request,Model model) {
		String groupId = "";
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if(loginUser==null) {
			return "login";
		}
		Optional<OfficeModel> office = officesService.getOfficeByUserId(loginUser.getUserId());
		if (office.isPresent()) {
			groupId = office.get().getGroupId();

		}
		
			ResponsePAOReport railDmdStReportResp = railDemandService.getPAOReport(groupId);
	
		if (railDmdStReportResp != null && 
			railDmdStReportResp.getErrorCode() == 200 && 
			railDmdStReportResp.getResponseList() != null && 
			!railDmdStReportResp.getResponseList().isEmpty()) 
		{
		
			model.addAttribute("railDmdStReportResp", railDmdStReportResp.getResponseList());

	} 
		else {
		model.addAttribute("message",Optional.ofNullable(railDmdStReportResp).map(e -> e.getErrorMessage()).orElse("No Record Found!!"));
		   request.getSession().removeAttribute("railDmdStReportResp");
		}
		return pageUrl+"/generateRailDemandRptPAO";
}
	
	
}
