package com.pcda.mb.travel.aircancellation.controller;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.travel.aircancellation.model.AirCancellationResponse;
import com.pcda.mb.travel.aircancellation.model.PostAirCancellationModel;
import com.pcda.mb.travel.aircancellation.service.AirCancellationService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class AirCancellationController {

	private String airCanUrl = "/MB/TravelRequest/airCancellation/";

	@Autowired
	private AirCancellationService airCancellationService;

	@GetMapping("/airCancellation")
	public String airCancellation(Model model, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());

		LoginUser loginUser = sessionVisitor.getLoginUser();
		Calendar toCal = Calendar.getInstance();
		Calendar fromCal = Calendar.getInstance();
		toCal.add(Calendar.DAY_OF_MONTH, 1);
		fromCal.add(Calendar.MONTH, -6);

		if (loginUser == null) {

			return "redirect:/login";
		}

		Optional<OfficeModel> offices = airCancellationService.getOfficesByUserId(loginUser.getUserId());

		String groupId = "";

		if (offices.isPresent()) {

			groupId = offices.get().getGroupId();
		}

		model.addAttribute("cancellationRe", airCancellationService.getAirCancellationDe(groupId, "MB"));

		return airCanUrl + "aircancellation";
	}

	@RequestMapping(value = "/airTicketCancellation", method = { RequestMethod.GET, RequestMethod.POST })
	public String airTicketDetailsCancellation(Model model, @RequestParam(required=false)String bookingId) {

		model.addAttribute("ticketDetails", airCancellationService.getAirBookingContentByBookingId(bookingId));

		return airCanUrl + "airTicketDetailsForCancellation";
	}

	@GetMapping("/confirmationPageCanAir")
	public String confimPageAirCan(Model model) {
		return airCanUrl + "confirmAirTicCanPage";
	}

	@PostMapping("/saveAirCancellationTicket")
	public String saveAirCancellationTicket( PostAirCancellationModel postAirCancellationModel,
			BindingResult result, HttpServletRequest request, Model model,
			final RedirectAttributes redirectAttributes) {
		AirCancellationResponse response = null;

		String createUrl = "redirect:airCancellation";
		try {
			if (result.hasErrors()) {
				redirectAttributes.addFlashAttribute("Errormessage", result.getAllErrors().get(0).getDefaultMessage());
				DODLog.error(LogConstant.AIR_TICKET_CANCELLATION_LOG, AirCancellationController.class,
						"error in saveAirCancellationTicket" + result.getAllErrors().get(0).getDefaultMessage());
				return createUrl;
			} else {
				response = airCancellationService.airTicketCanReqestSave(postAirCancellationModel,request);

				if (response!=null && response.getErrorCode() == 200) {

					redirectAttributes.addFlashAttribute("success","Cancellation request has been sent to CO for approval. Kindly get it approved and then cancel the ticket through Air Cancellation Dashboard under Request Dashboard menu");
					return "redirect:confirmationPageCanAir";
				} else {
					model.addAttribute("errors",response == null ? "" : response.getErrorMessage());
					return "/common/errorPage";
				}

			}

		} catch (Exception e) {

			DODLog.printStackTrace(e, AirCancellationController.class, LogConstant.AIR_TICKET_CANCELLATION_LOG);
			redirectAttributes.addFlashAttribute("message", "Error occurred");
			return createUrl;
		}

	}

}
