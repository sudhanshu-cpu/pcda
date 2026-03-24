package com.pcda.pao.reports.airbookingreport.controller;

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
import com.pcda.mb.reports.airticketcancellation.model.AirTicketBookingDetailsResponseModel;
import com.pcda.pao.reports.airbookingreport.model.AirBookingModel;
import com.pcda.pao.reports.airbookingreport.model.AirBookingReportInputModel;
import com.pcda.pao.reports.airbookingreport.service.AirBookingService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pao")
public class AirBookingController {
	
	@Autowired
	private AirBookingService airBookingService;
	
	private String pageUrl =  "/PAO/reports/airbookingreport/";

	@GetMapping("/airBookingReport")
	public String airBookingReport(Model model, HttpServletRequest request) {
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if(loginUser == null) {
			return "redirect:login";
		}
		List<OfficeModel>  unitList = airBookingService.getAllUnit();
		List<TravelType> travelType = airBookingService.getAllTravelType(1);
        model.addAttribute("travelTypeList" ,travelType);
        model.addAttribute( "unitList", unitList);	
        
        model.addAttribute( "formModel", new AirBookingReportInputModel());	
		return pageUrl + "airBookingReport";
	}
	
	@PostMapping("/getAirBooking")
	public String getAirBookingReport(Model model , HttpServletRequest request, AirBookingReportInputModel airBookingReportInputModel ) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:login";
		}
		//comment when test
		Optional<OfficeModel> officeModel = airBookingService.getOfficesByGroupId(loginUser.getUserId());
		if(officeModel.isPresent()) {
		String  groupId = officeModel.get().getGroupId();		
		airBookingReportInputModel.setAccountOffice(groupId);
		}
		List<AirBookingModel> airBookingModel = airBookingService.getAirBookingReport(airBookingReportInputModel);
		
		if(airBookingModel == null || airBookingModel.isEmpty() ) {
			model.addAttribute("errorMessage", "No Record Found");
		}else {
			model.addAttribute("airBookingModel", airBookingModel);
		}
		
		List<OfficeModel>  unitList = airBookingService.getAllUnit();
		List<TravelType> travelType = airBookingService.getAllTravelType(1);
        model.addAttribute("travelTypeList" ,travelType);
        model.addAttribute( "unitList", unitList);	
        
        model.addAttribute( "formModel", airBookingReportInputModel);	
		
		
		return pageUrl + "airBookingReport";
	}
	
	    //AJAX Call for GET DATA BASED ON BOOKING ID
		 @PostMapping("/getBookingData")
	     public String getBookingData( @RequestParam String bookingId, HttpServletRequest request, Model model ) {
			
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();
			if (loginUser == null) {
				return "redirect:login";
			}
			
			AirTicketBookingDetailsResponseModel responseModel = airBookingService.getAirTicketBookingDetails(bookingId);
			model.addAttribute("response" ,responseModel);
			
			
			return pageUrl + "airBookingPopUp";
		}
	
	
}
