package com.pcda.adg.reports.passengeraddressreport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.adg.reports.passengeraddressreport.model.GetPassengerAddresModel;
import com.pcda.adg.reports.passengeraddressreport.service.PassengerAdressRepoService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/adg")
public class PassengerAdressRepoController {

	private String pageUrl= "/ADG/Reports/PassengerAddressReport/";

	@Autowired
	private PassengerAdressRepoService passengerAdressRepoService;
	

	@GetMapping("/passengerAddresReports")
	public String repoPassAdress(Model model,@RequestParam(required=false,defaultValue = "") String pnrNo, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("pnr",pnrNo);

		return pageUrl + "passengerAddressReport";
	}


	@RequestMapping(value = "/passengerAddresDtls", method = { RequestMethod.GET, RequestMethod.POST })
	public String passAdressRepo(@RequestParam(required=false,defaultValue = "") String pnrNo, Model model) {
		GetPassengerAddresModel passengerData = passengerAdressRepoService.getPassengerDtlsByPnr(pnrNo);

		model.addAttribute("pnr", pnrNo);
		if (passengerData == null) {
			model.addAttribute("errorMessage", "No Record Found");
		} else {
			model.addAttribute("passengerDtls", passengerData);

		}

		return pageUrl + "passengerAddressReport";
	}

}
