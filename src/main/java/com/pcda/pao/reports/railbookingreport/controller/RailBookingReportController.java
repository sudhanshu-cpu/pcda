package com.pcda.pao.reports.railbookingreport.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.CodeHeadServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.pao.reports.railbookingreport.model.GetBkgReportDtlsModel;
import com.pcda.pao.reports.railbookingreport.model.GetBkgReportDtslResponse;
import com.pcda.pao.reports.railbookingreport.model.GetRailCancelChildModel;
import com.pcda.pao.reports.railbookingreport.model.GetRailCancelParentModel;
import com.pcda.pao.reports.railbookingreport.model.GetRailCancelSubChildModel;
import com.pcda.pao.reports.railbookingreport.model.PostRailBkgRepoFormModel;
import com.pcda.pao.reports.railbookingreport.service.RailBookingReportService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pao")
public class RailBookingReportController {

	
	@Autowired
	private OfficesService officeService;

	@Autowired
	private TravelTypeServices travelServices;

	@Autowired
	private CodeHeadServices codeService;

	@Autowired
	private RailBookingReportService railBkgservice;

	@GetMapping("/railBookingReport")
	public String getRailBkgReport(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}

		model.addAttribute("unitList", officeService.getOffices("UNIT", "1"));
		model.addAttribute("codeHead", codeService.getCodeHeadByApproval("1"));

		model.addAttribute("travelList", travelServices.getAllTravelType(1));
		model.addAttribute("formModel",new PostRailBkgRepoFormModel());
		return "/PAO/reports/railbookingreport/railbookingreport";
	}

	@RequestMapping(value = "/getRailBkgReportData", method = {RequestMethod.GET, RequestMethod.POST })
	public String getrailBookinDtls(PostRailBkgRepoFormModel formModel, Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}

		Optional<OfficeModel> officeModel = officeService.getOfficeByUserId(loginUser.getUserId());
		String groupId = "";

		if (officeModel.isPresent()) {

			groupId = officeModel.get().getGroupId();
			formModel.setAccountOffice(groupId);

		}
	

		GetBkgReportDtslResponse response = railBkgservice.getRailBkgRepoModel(formModel);
		if (response != null && response.getErrorCode() == 200) {
			List<GetBkgReportDtlsModel> dtlsModelList = response.getResponseList();
			
			model.addAttribute("bookingRepoList", dtlsModelList);

		} else {
			model.addAttribute("errorMessage", "No Record Found");
		}
		model.addAttribute("unitList", officeService.getOffices("UNIT", "1"));
		model.addAttribute("codeHead", codeService.getCodeHeadByApproval("1"));

		model.addAttribute("travelList", travelServices.getAllTravelType(1));
		model.addAttribute("formModel",formModel);
		return "/PAO/reports/railbookingreport/railbookingreport";
	}

//Pop_Up Controller for tickets booking dtls
	@RequestMapping(value = "/tktBkgDtls", method = { RequestMethod.GET, RequestMethod.POST })
	public String railTravelRepo(@RequestParam String bookingId ,Model model, HttpServletRequest request) {
		DODLog.info(LogConstant.COMMON_REPORT, RailBookingReportController.class, "railTravelRepo bookingId ::"+bookingId);
		
		model.addAttribute("BkdDtlsModel", railBkgservice.getRailBkgDtls(bookingId));

		return "/PAO/reports/railbookingreport/railbookingpopview";
	}

	// pop _up for Cancellation Details
	@RequestMapping(value = "/tktCanDtls", method = { RequestMethod.GET, RequestMethod.POST })
	public String getRailBkgCanRepo(@RequestParam String bookingId, Model model, HttpServletRequest request) {

		GetRailCancelParentModel parentModel =railBkgservice.getRailTktCanDtls(bookingId);
		
		List<GetRailCancelChildModel> childModelList=parentModel.getCancellationView();
		List<GetRailCancelSubChildModel> subChildModelList=new ArrayList<>();
		for(GetRailCancelChildModel childModel:childModelList) {
			
			subChildModelList.addAll(childModel.getPassengerDetails());
		}
			model.addAttribute("parentModel", parentModel);	
			model.addAttribute("childModellist", childModelList);
			model.addAttribute("subChildModellist", subChildModelList);
	
		return "/PAO/reports/railbookingreport/railcancelDetailspopUp";
	}

}
