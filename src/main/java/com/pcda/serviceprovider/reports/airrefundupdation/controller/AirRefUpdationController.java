package com.pcda.serviceprovider.reports.airrefundupdation.controller;

import java.util.List;

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
import com.pcda.serviceprovider.reports.airbookingupdation.controller.AirBookUpdationController;
import com.pcda.serviceprovider.reports.airbookingupdation.model.BookingIdResponse;
import com.pcda.serviceprovider.reports.airrefundupdation.model.AirRefBkgIdResponse;
import com.pcda.serviceprovider.reports.airrefundupdation.model.GetAirRefBookChildModel;
import com.pcda.serviceprovider.reports.airrefundupdation.model.GetAirRefBookDataModel;
import com.pcda.serviceprovider.reports.airrefundupdation.model.GetAirRefParentModel;
import com.pcda.serviceprovider.reports.airrefundupdation.model.PostAirRefParentModel;
import com.pcda.serviceprovider.reports.airrefundupdation.service.AirRefUpdationService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("serpov")
public class AirRefUpdationController {

	@Autowired
	private AirRefUpdationService updationService;

	@GetMapping("/getAirRefundUpdation")
	public String getAirBookingUpdtion(Model model, @RequestParam(defaultValue = "") String bookingId) {

		model.addAttribute("bkgId", bookingId);
		
		return "/SERVICEPROVIDER/reports/airrefundupdation/airrefundupdation";
	}

	
	@PostMapping("/getBkgIdRefFormData")
	public String getDataFromBkgId(Model model, @RequestParam String bookingId,HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		int serviceProviderInt=loginUser.getServiceProvider();
		DODLog.info(LogConstant.AIR_BOOKING_UPDATION_LOG_FILE, AirBookUpdationController.class,
				"#####BookingID" + bookingId +" | serviceProviderInt "+serviceProviderInt);
		GetAirRefParentModel bkgIdModel = null;
		model.addAttribute("bkgId", bookingId);
		AirRefBkgIdResponse response = updationService.getDataFromBkgId(bookingId,serviceProviderInt);
		
		if (response != null && response.getErrorCode() == 200) {
			bkgIdModel = response.getResponse();
			
	List<GetAirRefBookChildModel> airReFundBookingChildList = bkgIdModel.getAirRefundBookChild();
	 GetAirRefBookDataModel airRefundBookData = bkgIdModel.getAirRefundBookData();
	 String bookingData = bkgIdModel.getBookingData();
	 String bookingOid = bkgIdModel.getBookingOid();
	 
	 
	 if(airRefundBookData != null &&
			 bookingData != null && bookingOid != null && !airReFundBookingChildList.isEmpty()) {
				model.addAttribute("dataRefModel", bkgIdModel);
		}else {
		 model.addAttribute("errorMessage", "No Record Found!!");
	 }
		}else {
			
			model.addAttribute("errorMessage", response.getErrorMessage());
		}
		return "/SERVICEPROVIDER/reports/airrefundupdation/airrefundupdation";
	}
	
	
	@PostMapping("saveAirRefUpdation")
	public String saveAirUpdation(Model model, PostAirRefParentModel airUpdationModel, HttpServletRequest request,
			BindingResult result) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		if (result.hasErrors()) {
			DODLog.error(LogConstant.AIR_REFUND_UPDATION_LOG_FILE, AirRefUpdationController.class,
					"ERROR IN SAVE MDOEL" + result.getAllErrors());
			return "/common/errorPage";
		} else {
			airUpdationModel.setLoginUserID(loginUser.getUserId());

			BookingIdResponse idResponse = updationService.saveAirUpdation(airUpdationModel, request);
			if (idResponse != null && idResponse.getErrorCode() == 200) {
				model.addAttribute("message", idResponse.getErrorMessage());
				return "/SERVICEPROVIDER/reports/airrefundupdation/airrefaftersave";
			} else {
				return "/common/errorPage";
			}
		}
	}
	
}
