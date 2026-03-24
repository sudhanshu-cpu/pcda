package com.pcda.mb.reports.railtravelreports.controller;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.reports.railtravelreports.model.CancellationView;
import com.pcda.mb.reports.railtravelreports.model.GetRailCancelletionDetailsModel;
import com.pcda.mb.reports.railtravelreports.model.GetRailTravelReportModel;
import com.pcda.mb.reports.railtravelreports.model.PassengerTicketsCancellationModel;
import com.pcda.mb.reports.railtravelreports.model.RailTravelInputtModel;
import com.pcda.mb.reports.railtravelreports.model.RailTravelReportResponseModel;
import com.pcda.mb.reports.railtravelreports.service.RailTravelReportService;
import com.pcda.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/reports")
public class RailTravelReportsController {

	private String pageUrl = "/MB/Reports/RailTravelReport/";
	@Autowired
	private RailTravelReportService railTravelReportService;


	@GetMapping("/bookingReports")
	public String getrailBookingRepo(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}

		
		model.addAttribute("paoList", railTravelReportService.getAllPao());
		model.addAttribute("codeHead", railTravelReportService.getCodeHeadService());
	    model.addAttribute("travelList", railTravelReportService.getAllTravelType(1));
	    model.addAttribute("formModel",new RailTravelInputtModel());
		return pageUrl + "bookingreportrail";
	}

	@RequestMapping(value = "/bookinhDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public String getrailBookinDtls(RailTravelInputtModel inputModel, Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		String encryptPno = inputModel.getPersonalNo();
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, encryptPno);
		inputModel.setPersonalNo(decryptPersonalNo);
		
		if (loginUser == null) {
			return "redirect:/login";
		}

		Optional<OfficeModel> officeModel = railTravelReportService.getOfficesByGroupId(loginUser.getUserId());
		String groupId = "";

		if (officeModel.isPresent()) {

			groupId = officeModel.get().getGroupId();
			inputModel.setUnitOffice(groupId);

		}

		if(inputModel.getBookingId().isEmpty() && inputModel.getRequestId().isEmpty() && inputModel.getPnrNo().isEmpty() && inputModel.getTicketNo().isEmpty()
				&& inputModel.getFromDate().isEmpty() && inputModel.getToDate().isEmpty() && inputModel.getFromJourneyDate().isEmpty() && inputModel.getToJourneyDate().isEmpty()
				&& inputModel.getPersonalNo().isEmpty() ){
			
			
			Calendar calender =Calendar.getInstance();
			inputModel.setToDate(CommonUtil.formatDate(calender.getTime(), "dd-MM-yyyy"));
			calender.add(Calendar.DAY_OF_MONTH, -15);
			inputModel.setFromDate(CommonUtil.formatDate(calender.getTime(), "dd-MM-yyyy"));
		}
		
		RailTravelReportResponseModel response = railTravelReportService.getViewRailTravelRepo(inputModel);
		if (response!=null && response.getErrorCode()==200 && null!=response.getResponseList() && !response.getResponseList().isEmpty()) {
		 List<GetRailTravelReportModel> modelList = response.getResponseList();
			model.addAttribute("bookingRepoList", modelList);

		} else {
			model.addAttribute("errorMessage", "No Record Found");

		}
		model.addAttribute("paoList", railTravelReportService.getAllPao());
		model.addAttribute("codeHead", railTravelReportService.getCodeHeadService());
	    model.addAttribute("travelList", railTravelReportService.getAllTravelType(1));
		model.addAttribute("formModel",inputModel);
		return pageUrl + "bookingreportrail";
	}

//Pop_Up Controller for tickets booking dtls
	@RequestMapping(value = "/ticketsBookingDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public String railTravelRepo(@RequestParam String bookingId ,Model model, HttpServletRequest request) {

		
		model.addAttribute("userList", railTravelReportService.getrailTicketsBookindDtls(bookingId));

		return pageUrl + "railtickestbookingdtls";
	}

	// pou _up for Cancellation Details
	@RequestMapping(value = "/ticketsCancellationDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public String railTravelCancellatioRepo(@RequestParam String bookingId, Model model, HttpServletRequest request) {

		GetRailCancelletionDetailsModel model2 =railTravelReportService.getrailTiceketsCancellationDtls(bookingId);
		
		List<CancellationView> cancelletionDetailsModel=model2.getCancellationView();
		List<PassengerTicketsCancellationModel> paCancellationModels=new ArrayList<>();
		for(CancellationView list:cancelletionDetailsModel) {
			
			paCancellationModels.addAll(list.getPassengerDetails());
		}
				
		model.addAttribute("CancellationDtls", model2);
		
		model.addAttribute("paCancellationModels", paCancellationModels);

		return pageUrl + "cancellationDetails";
	}

}
