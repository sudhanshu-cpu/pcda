package com.pcda.mb.reports.airticketcancellation.controller;

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
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.reports.airticketcancellation.model.AirTicketBookingDetailsResponseModel;
import com.pcda.mb.reports.airticketcancellation.model.AirTicketCancellationModel;
import com.pcda.mb.reports.airticketcancellation.model.AirTicketCancellationRequestData;
import com.pcda.mb.reports.airticketcancellation.model.AirTktCancellationDataModel;
import com.pcda.mb.reports.airticketcancellation.service.AirTicketCancellationService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/reports")
public class AirTicketCancellationController {

	@Autowired
	private AirTicketCancellationService airCancellationService;
	
	@Autowired
	private OfficesService officeService;

	
	private String pageURL = "/MB/Reports/airticketcancellationreport/";
	
	@GetMapping("/airCancellationReport")
	public String airCancellationReport(Model model, HttpServletRequest request,@RequestParam(defaultValue = "")String personalNo) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("personalNo", personalNo);
		return pageURL + "airticketreport";
	}
	
	
	@GetMapping("/airTktCancellationRptForm")
	public String airCancellationView(AirTicketCancellationRequestData requestData, Model model, HttpServletRequest request) {
	    try {
	        SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	        LoginUser loginUser = sessionVisitor.getLoginUser();
	        if (loginUser == null) {
	            return "redirect:/login";
	        } 
	        model.addAttribute("requestData",requestData);
	    } 
	    catch (Exception e) {
	        DODLog.printStackTrace(e, AirTicketCancellationController.class, LogConstant.AIR_REPORT);
	    }
	    return pageURL + "airCancellationData";
	}
	
	
	@PostMapping("/airCancellationTktData")
	public String airCancellationRpt(AirTicketCancellationRequestData requestData, Model model,
			HttpServletRequest request) {
		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();
			

			requestData.setPersonalNo(CommonUtil.getDecryptText("Hidden Pass", requestData.getPersonalNo()));
			

			Optional<OfficeModel> office = officeService.getOfficeByUserId(loginUser.getUserId());
		 
			if (office.isPresent()) {
				requestData.setGroupId(Optional.ofNullable(office.get().getGroupId()).orElse(""));
				
			
			}
			
			String msg = airCancellationService.validateFormBean(requestData);
			if (msg.equalsIgnoreCase("OK")) {
				DODLog.info(LogConstant.AIR_REPORT, AirTicketCancellationController.class, "requestData::::" + requestData);
				List<AirTktCancellationDataModel> cancelReport = airCancellationService.getAirTktCancellationData(requestData);

				if (cancelReport == null || cancelReport.isEmpty()) {
	                
					model.addAttribute("errorMsg", "No records found !!! ");
				}
				model.addAttribute("cancelRptDataList", cancelReport);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
				model.addAttribute("requestData",requestData);

			} else {
				model.addAttribute("validationMsg", msg);
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, AirTicketCancellationController.class, LogConstant.AIR_REPORT);
		}
		return pageURL + "airCancellationData";
	}
	
	 
	

	
	@PostMapping("/getCancellationList")
	public String getCancellationList(@RequestParam String personalNo ,HttpServletRequest request , Model model) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, personalNo);
		
		model.addAttribute("personalNo", decryptPersonalNo);
		if (loginUser == null) {
			return "redirect:/login";
		}	
		List<AirTicketCancellationModel> aircancellationList = airCancellationService.getAllBookedTicketForPersonalNo(decryptPersonalNo);
		
		if(aircancellationList.isEmpty() ) {
			
			model.addAttribute("noData", "No Ticket Found For Searched Personal No.");
			return pageURL + "airticketreport";
		}else {
			aircancellationList.stream().forEach(
					airCanList -> airCanList.setJourneyDateStr(CommonUtil.getChangeFormat(airCanList.getJourneyDate(), "yyyy-MM-dd", "dd-MM-yyyy")));
		 model.addAttribute( "aircancellationList" ,aircancellationList); 	
		}
		return pageURL + "airticketreport";
	}
	
	//AJAX Call for GET DATA BASED ON BOOKING ID
	@PostMapping("/getBookingData")
     public String getBookingData( @RequestParam String bookingId, HttpServletRequest request, Model model ) {
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		
		AirTicketBookingDetailsResponseModel responseModel = airCancellationService.getAirTicketBookingDetails(bookingId);
		model.addAttribute("response" ,responseModel);
		return pageURL + "airTicketPopup";
	}
	
	
	
	

}
