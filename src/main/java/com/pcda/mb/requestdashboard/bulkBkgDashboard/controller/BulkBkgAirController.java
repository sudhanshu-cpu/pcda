package com.pcda.mb.requestdashboard.bulkBkgDashboard.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.GetATTBulkBkgConfirmBookingResponse;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.GetATTBulkBkgInfoModel;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.GetATTBulkBkgPassBookModel;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.GetATTBulkBkgResponse;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.GetBLBulkBkgBookInfoModel;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.GetBLBulkBkgConfirmBookResponse;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.GetBLBulkBkgPassbookModel;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.GetBLBulkBkgResponse;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.PostConfirmAttBulkBkgModel;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.PostConfirmBLAirBulkBkgModel;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.service.BulkBkgDashService;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.FlightOptListSessionModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.FlightSearchOption;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PosrAirBookATTModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostAirBookBlModel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mb")
public class BulkBkgAirController {

	@Autowired
	private BulkBkgDashService bulkBkgDashService;

	/* traveller in and flight info , validate fair for Bl flight */
	@PostMapping("/bulkBkgBLFLlight")
	public String bookBlFlight(Model model, PostAirBookBlModel postAirBookBlModel, @RequestParam String isValidatedCase,
			@RequestParam String validatedReason, HttpServletRequest request) {

		FlightOptListSessionModel fltOptSesModel = new FlightOptListSessionModel();
		HttpSession session = request.getSession();
		Object data = session.getAttribute("flightOptionList");
		if (null != data && data instanceof FlightOptListSessionModel flightOptModel) {
			fltOptSesModel = flightOptModel;
		}
		List<FlightSearchOption> flightOptList = fltOptSesModel.getFlightOption();
		GetBLBulkBkgResponse response = bulkBkgDashService.bookBLFlight(postAirBookBlModel, flightOptList);
		if (response != null && response.getErrorCode() == 200) {
			GetBLBulkBkgPassbookModel blModel = response.getResponse();
			model.addAttribute("blFareModel", blModel);
			model.addAttribute("isValidatedCase", isValidatedCase);
			model.addAttribute("validatedReason", validatedReason);
			model.addAttribute("requestId", postAirBookBlModel.getRequestId());
			return "/MB/RequestDashbord/bulkBkgDashboard/BLBulkBkg";
		} else if (response != null && response.getErrorCode() == 400) {
			model.addAttribute("errorMessage", response.getErrorMessage());
			return "/MB/RequestDashbord/normalbookingdashboard/AirBookErrorPage";
		} else {

			return "/MB/RequestDashbord/normalbookingdashboard/AirBookErrorPage";
		}

	}

	@PostMapping("/bulkBkgATTFlight")
	public String bookAttFlight(Model model, PosrAirBookATTModel airBookATTModel, @RequestParam String isValidatedCase,
			@RequestParam String validatedReason) {

		GetATTBulkBkgResponse response = bulkBkgDashService.bookATTFlight(airBookATTModel);
		if (response != null && response.getErrorCode() == 200) {

			GetATTBulkBkgPassBookModel attModel = response.getResponse();
			model.addAttribute("attFareModel", attModel);
			model.addAttribute("isValidatedCase", isValidatedCase);
			model.addAttribute("validatedReason", validatedReason);
			model.addAttribute("requestId", airBookATTModel.getRequestId());
			return "/MB/RequestDashbord/bulkBkgDashboard/ATTBulkBkg";
		} else if (response != null && response.getErrorCode() == 400) {
			model.addAttribute("errorMessage", response.getErrorMessage());
			return "/MB/RequestDashbord/normalbookingdashboard/AirBookErrorPage";
		} else {
			return "/MB/RequestDashbord/normalbookingdashboard/AirBookErrorPage";
		}

	}

	// Att confirm
	@PostMapping("/confirmAttAirBulkBkg")
	public String confirmATTBooking(PostConfirmAttBulkBkgModel attAirBookModel, Model model,
			HttpServletRequest request) {

		
		SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());

		LoginUser loginUser = visitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		attAirBookModel.setLoginUserId(loginUser.getUserId());

		HttpSession session = request.getSession();
		String sessionId = session.getId();
		attAirBookModel.setSessionId(sessionId);

		GetATTBulkBkgConfirmBookingResponse attResponse = bulkBkgDashService.confirmATTAirBook(attAirBookModel);
		if (attResponse != null && attResponse.getErrorCode() == 200) {
			GetATTBulkBkgInfoModel infoModel = attResponse.getResponse();			
			Collections.sort(infoModel.getFlightInfo());			
			model.addAttribute("bookInfo", infoModel);	

			return "/MB/RequestDashbord/bulkBkgDashboard/AttAfterConfirmBulkBkgBooking";
		}
		if (attResponse != null) {
			model.addAttribute("errorMessage", attResponse.getErrorMessage());
		} else {
			model.addAttribute("errorMessage", "");
		}
		return "/MB/RequestDashbord/normalbookingdashboard/AirBookErrorPage";
	}

	// BL confirm
	@PostMapping("/confirmBLAirBulkBkg")
	public String confirmBLBooking(PostConfirmBLAirBulkBkgModel blAirBookModel, Model model,
			HttpServletRequest request) {

	
		SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());

		LoginUser loginUser = visitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		blAirBookModel.setLoginUserId(loginUser.getUserId());
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		blAirBookModel.setSessionId(sessionId);
		GetBLBulkBkgConfirmBookResponse blResponse = bulkBkgDashService.confirmBLAirBook(blAirBookModel);
		if (blResponse != null && blResponse.getErrorCode() == 200) {
			GetBLBulkBkgBookInfoModel infoModel = blResponse.getResponse();
			Collections.sort(infoModel.getFlightInfo());
			model.addAttribute("blBookInfo", infoModel);
			
			return "/MB/RequestDashbord/bulkBkgDashboard/BLAfterConfirmBulkBkgBooking";
		}
		if (blResponse != null) {
			model.addAttribute("errorMessage", blResponse.getErrorMessage());
		} else {
			model.addAttribute("errorMessage", "");
		}
		return "/MB/RequestDashbord/normalbookingdashboard/AirBookErrorPage";

	}

}
