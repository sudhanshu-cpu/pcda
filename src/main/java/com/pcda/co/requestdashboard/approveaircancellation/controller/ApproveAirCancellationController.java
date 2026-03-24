package com.pcda.co.requestdashboard.approveaircancellation.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.co.requestdashboard.approveaircancellation.model.AirCancellationApproveResponse;
import com.pcda.co.requestdashboard.approveaircancellation.model.GetApproveAirCancellationModel;
import com.pcda.co.requestdashboard.approveaircancellation.model.PassangerDetail;
import com.pcda.co.requestdashboard.approveaircancellation.model.PostAirCancellationApprovalModel;
import com.pcda.co.requestdashboard.approveaircancellation.service.AirCancellationApprovalService;
import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class ApproveAirCancellationController {

	private String path = "CO/RequestDashBoard/ApproveAirCancellation/";

	@Autowired
	AirCancellationApprovalService airCancellationApprovalService;

	// Get DataList ForApprove Air Cancellation
	@RequestMapping(value="/approveAirCancellationReq",method= {RequestMethod.GET,RequestMethod.POST})
	public String approveAirCancellation(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		
		DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, ApproveAirCancellationController.class,
				"#########################  INSIDE APPROVE AIR CANCELLATION 1 #################################");

		if (loginUser == null) {
			return "redirect:/login";
		}
		
		DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, ApproveAirCancellationController.class,
				"#########################  INSIDE APPROVE AIR CANCELLATION 2 #################################"); 
		
		Optional<OfficeModel> offices = airCancellationApprovalService.getOfficeByUserId(loginUser.getUserId());
		String groupId = "";

		if (offices.isPresent()) {

			groupId = offices.get().getGroupId();
		}

		List<GetApproveAirCancellationModel> userList = airCancellationApprovalService
				.getAllAirCancellationForApproval(groupId, "CO");

		model.addAttribute("ticketsDtls", userList);

		DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, ApproveAirCancellationController.class,
				"#########################  INSIDE APPROVE AIR CANCELLATION 3 #################################");

		return path + "ticketsBookingDetails";
	}
	
	// Get Air Ticket Cancellation Details of Specific Booking Id
	@RequestMapping(value = "/airTicketCancellationDtls", method = { RequestMethod.GET, RequestMethod.POST })
	public String airTicketDetailsCancellation(Model model, @RequestParam String bookingId) {
		GetApproveAirCancellationModel  airModel =airCancellationApprovalService.getAirBookingContentByBookingId(bookingId);
		List<PassangerDetail> passList = airModel.getPassangerDetail();
		Collections.sort(passList);
		airModel.setPassangerDetail(passList);
		model.addAttribute("ticketDetails",airModel);
		model.addAttribute("toalPass",airModel.getPassangerDetail().size());
        model.addAttribute("bookingId", bookingId);   
		return path + "airTicketDetailsForCancellationDtls";
	}
	
	
	//Approve Air Cancellation By CO
	@PostMapping("/approveAirCancellation")
	public String approveAirCancellationReq( PostAirCancellationApprovalModel postAirCancellationApprovalModel,
			BindingResult result, HttpServletRequest request, Model model,
			 RedirectAttributes redirectAttributes) {
		AirCancellationApproveResponse response = null;

		String createPath = "redirect:airTicketCancellationDtls";
		try {
			if (result.hasErrors()) {
				redirectAttributes.addFlashAttribute("Errormessage", result.getAllErrors().get(0).getDefaultMessage());
				redirectAttributes.addFlashAttribute("bookingId", postAirCancellationApprovalModel.getBookingId());
				DODLog.info(LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG, ApproveAirCancellationController.class,
						"mapping" + postAirCancellationApprovalModel);
				return createPath;
			} else {
				response = airCancellationApprovalService.airTicketCanReqestApprove(postAirCancellationApprovalModel,request);

				if (response!=null && response.getErrorCode() == 200) {

					
					GetApproveAirCancellationModel  airModel =airCancellationApprovalService.getAirBookingContentByBookingId(postAirCancellationApprovalModel.getBookingId());
					if(postAirCancellationApprovalModel.getOperationMode().equals("approve")) {
					redirectAttributes.addFlashAttribute("message","Request for cancellation of bookingId "+airModel.getOperatorTxnId() +" has been successfully approved. Kindly ask your Master Booker to proceed further and cancel the ticket through Air Cancellation Dashboard.");
					}else if(postAirCancellationApprovalModel.getOperationMode().equals("disapprove")) {
						redirectAttributes.addFlashAttribute("message","Request for cancellation of bookingId "+airModel.getOperatorTxnId() +" has been successfully disapproved.Kindly ask your Master Booker to Create New Request.");
					}
					
					return "redirect:approveAirCancellationReq";
				} else {
					redirectAttributes.addFlashAttribute("message",response == null ? "" : response.getErrorMessage());
					redirectAttributes.addAttribute("bookingId", postAirCancellationApprovalModel.getBookingId());
					return createPath;
				}

			}

		} catch (Exception e) {

			DODLog.printStackTrace(e, ApproveAirCancellationController.class, LogConstant.AIR_TICKET_CANCEL_APPROVAL_LOG);
			redirectAttributes.addFlashAttribute("message", "Unable to proceed request .Kindly check the Booking Id Data");
			return "redirect:approveAirCancellationReq";
		}

	}
 
}
