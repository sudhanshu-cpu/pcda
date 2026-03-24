package com.pcda.pao.reports.airdemandreport.controller;

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
import com.pcda.pao.reports.airdemandreport.model.PaoAirDmdStatusReportResponse;
import com.pcda.pao.reports.airdemandreport.service.AirDemandService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pao")
public class AirDemandPaoController {
	
    @Autowired
    private OfficesService officesService;
	
    private String pageUrl = "/PAO/reports/airdemandreport/";
	
	@Autowired
	AirDemandService airDemandService;
	
	@GetMapping("/getAirDmdReport")
	public String getAirDemandReport(HttpServletRequest request,Model model) {
		String groupId="";
		SessionVisitor sessionVisitor=SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser=sessionVisitor.getLoginUser();
		if(loginUser==null) {
			return "login";
		}
		Optional<OfficeModel> office = officesService.getOfficeByUserId(loginUser.getUserId());
		if(office.isPresent()) {
			groupId= office.get().getGroupId();
		}
		PaoAirDmdStatusReportResponse airDmdStReportResp = airDemandService.getAirDmdStReport(groupId);
		if (airDmdStReportResp != null && airDmdStReportResp.getErrorCode() == 200
				&& airDmdStReportResp.getResponseList() != null && !airDmdStReportResp.getResponseList().isEmpty()) 
		{	
		
			model.addAttribute("airDmdStReportResp", airDmdStReportResp.getResponseList());
		} else {
			model.addAttribute("message",
					Optional.ofNullable(airDmdStReportResp).map(PaoAirDmdStatusReportResponse::getErrorMessage).orElse("No Record Found!!"));
		}
		return pageUrl+"/generateAirDemandRptPAO";
	}
	
	
}
