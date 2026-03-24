package com.pcda.serviceprovider.reports.airbookingupdation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.serviceprovider.reports.airbookingupdation.model.BookingIdResponse;
import com.pcda.serviceprovider.reports.airbookingupdation.model.GetParentFrmBkgIdModel;
import com.pcda.serviceprovider.reports.airbookingupdation.model.PostParentAirUpdationModel;
import com.pcda.serviceprovider.reports.airbookingupdation.service.AirBookUpdationService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("serpov")
public class AirBookUpdationController {

	@Autowired
	private AirBookUpdationService updationService;

	@GetMapping("/getAirBookingUpdation")
	public String getAirBookingUpdtion(Model model, @RequestParam(defaultValue = "") String bookingId) {

		model.addAttribute("bkgId", bookingId);
		
		return "/SERVICEPROVIDER/reports/airbookingupdation/airbookingupdation";
	}

	@PostMapping("/getBookingIdFormData")
	public String getDataFromBkgId(Model model, @RequestParam String bookingId,HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		int serviceProviderInt=loginUser.getServiceProvider();
		
		GetParentFrmBkgIdModel bkgIdModel = null;
		model.addAttribute("bkgId", bookingId);
		BookingIdResponse response = updationService.getDataFromBkgId(bookingId,serviceProviderInt);
		
		if (response != null && response.getErrorCode() == 200) {
			bkgIdModel = response.getResponse();
			if (bkgIdModel != null) {
				model.addAttribute("dataBkgModel", bkgIdModel);
				model.addAttribute("serviceProvider",bkgIdModel.getServiceProviderInt()==0?"BL":"ATT");
			} 
		} 
		if (response != null && response.getErrorCode() == 400) {
			String message = response.getErrorMessage();
		
			model.addAttribute("errorMessage", message);
		}
		return "/SERVICEPROVIDER/reports/airbookingupdation/airbookingupdation";
	}

	@PostMapping("saveAirUpdation")
	public String saveAirUpdation(Model model, PostParentAirUpdationModel airUpdationModel, HttpServletRequest request,
			BindingResult result) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		if (result.hasErrors()) {
			DODLog.error(LogConstant.AIR_BOOKING_UPDATION_LOG_FILE, AirBookUpdationController.class,
					"ERROR IN SAVE MDOEL" + result.getFieldError());
			return "/common/errorPage";
		} else {
			airUpdationModel.setLoginUserID(loginUser.getUserId());

			BookingIdResponse idResponse = updationService.saveAirUpdation(airUpdationModel, request);
			if (idResponse != null && idResponse.getErrorCode() == 200) {
				model.addAttribute("message", idResponse.getErrorMessage());
				return "/SERVICEPROVIDER/reports/airbookingupdation/airbkgaftersave";
			} else {
				return "/common/errorPage";
			}
		}
	}

}
