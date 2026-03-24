package com.pcda.mb.reports.airtravelreport.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import com.pcda.mb.reports.airticketcancellation.model.TicketPassengerModel;
import com.pcda.mb.reports.airticketcancellation.model.TicketRefundDetails;
import com.pcda.mb.reports.airtravelreport.model.AirTravelBookingResponse;
import com.pcda.mb.reports.airtravelreport.model.AirTravelModel;
import com.pcda.mb.reports.airtravelreport.model.AirTravelReportInputModel;
import com.pcda.mb.reports.airtravelreport.service.AirTravelReportService;
import com.pcda.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/reports")
public class AirTravelReportController {
 	
	
	@Autowired
	private AirTravelReportService airTravelReportService;
	
	private String pageURL = "/MB/Reports/airtravelreport/";

	@GetMapping("/airTravelReport")
	public String airTravelReport(Model model, HttpServletRequest request) {
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if(loginUser == null) {
			return "redirect:/login";
		}
		
		List<OfficeModel>  unitList = airTravelReportService.getAllUnit();
		List<TravelType> travelType = airTravelReportService.getAllTravelType(1);
        model.addAttribute("travelTypeList" ,travelType);
        model.addAttribute( "unitList", unitList);	
        model.addAttribute( "formModel", new AirTravelReportInputModel());	
		return pageURL + "airtravelreport";
	}
	
	
	
	
	@PostMapping("/getAirTravelReport")
	public String getAirTravelReport(Model model , HttpServletRequest request, AirTravelReportInputModel airTravelReportInputModel ) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		String encryptPno = airTravelReportInputModel.getPersonalNo();
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, encryptPno);
		airTravelReportInputModel.setPersonalNo(decryptPersonalNo);
		
		
		Optional<OfficeModel> officeModel = airTravelReportService.getOfficesByGroupId(loginUser.getUserId());
		if(officeModel.isPresent()) {
		String  groupId = officeModel.get().getGroupId();		
		  airTravelReportInputModel.setUnitOffice(groupId);
		}
		List<AirTravelModel> airTravelModel = airTravelReportService.getAirTravelReport(airTravelReportInputModel);
		
		
		if(airTravelModel == null || airTravelModel.isEmpty() ) {
			model.addAttribute("errorMessage", "No Record Found");
		}else {
			model.addAttribute("airBookingModel", airTravelModel);
		}
		
		List<OfficeModel>  unitList = airTravelReportService.getAllUnit();
		List<TravelType> travelType = airTravelReportService.getAllTravelType(1);
        model.addAttribute("travelTypeList" ,travelType);
        model.addAttribute( "unitList", unitList);
        model.addAttribute( "formModel", airTravelReportInputModel);	
		
  		return pageURL + "airtravelreport";
  		
	}
	
	        //AJAX Call for GET DATA BASED ON BOOKING ID
		     @PostMapping("/getAirTravelData")
	         public String getTravelData( @RequestParam String bookingId, HttpServletRequest request, Model model ) {
			
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();
			if (loginUser == null) {
				return "redirect:/login";
			}
			
			AirTravelBookingResponse responseModel = airTravelReportService.getAirTicketBookingDtls(bookingId);
			if(responseModel.getTicketPassangerDetails()!=null && !responseModel.getTicketPassangerDetails().isEmpty()) {
			List<TicketPassengerModel> ticketPassangerDetails=responseModel.getTicketPassangerDetails();
			Collections.sort(ticketPassangerDetails);
			responseModel.setTicketPassangerDetails(ticketPassangerDetails);
			}
			if(responseModel.getTicketRefundDetails()!=null && !responseModel.getTicketRefundDetails().isEmpty()) {
			List<TicketRefundDetails> ticketRefundDetails=responseModel.getTicketRefundDetails();
			Collections.sort(ticketRefundDetails);
			responseModel.setTicketRefundDetails(ticketRefundDetails);
			}
			model.addAttribute("response" ,responseModel);
			return pageURL + "airTravelPopUp";
		}
}
