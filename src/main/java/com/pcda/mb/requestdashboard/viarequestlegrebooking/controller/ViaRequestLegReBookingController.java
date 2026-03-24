package com.pcda.mb.requestdashboard.viarequestlegrebooking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.requestdashboard.viarequestlegrebooking.model.ViaRequestLegReBookingModel;
import com.pcda.mb.requestdashboard.viarequestlegrebooking.model.ViaRequestLegReBookingViewModel;
import com.pcda.mb.requestdashboard.viarequestlegrebooking.service.ViaRequestLegReBookingService;
import com.pcda.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class ViaRequestLegReBookingController {

	@Autowired
	private ViaRequestLegReBookingService viaRequestLegReBookingService;

	@GetMapping("/viaRequestLegReBooking")
	public String viaRequestLegReBooking(Model model, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}

		Optional<OfficeModel> officeModel = viaRequestLegReBookingService.getOfficeVia(loginUser.getUserId());
		String groupId = "";

		if (officeModel.isPresent()) {
			 groupId=officeModel.get().getGroupId();
		}
		model.addAttribute("personalNo", "");
		model.addAttribute("groupId", groupId);
		return "/MB/RequestDashbord/ViaRequestLegReBooking/viaRequestLegReBooking";
	}

	@RequestMapping(value="/getViaRequestLegReBooking",method= {RequestMethod.GET,RequestMethod.POST})
	public String getViaRequestLegReBooking(@RequestParam(required=false,defaultValue = "") String personalNo, HttpServletRequest request,
			Model model) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, personalNo);
		
		if (loginUser == null) {
			return "redirect:/login";
		}

		Optional<OfficeModel> officeModel = viaRequestLegReBookingService.getOfficeVia(loginUser.getUserId());
		String groupId = "";

		if (officeModel.isPresent()) {
			 groupId=officeModel.get().getGroupId();
		}

		List<ViaRequestLegReBookingModel> viaRequestLegReBookingList = viaRequestLegReBookingService
				.getViaRequestLegReBookingData(groupId, decryptPersonalNo);
		if (viaRequestLegReBookingList.isEmpty()) {
			model.addAttribute("error", "No Request of Via Leg Re-Booking Found For Your Unit");
		} else {
			model.addAttribute("viaRequestLegReBookingList", viaRequestLegReBookingList);
		}
		model.addAttribute("personalNo", decryptPersonalNo);
		
		
		return "/MB/RequestDashbord/ViaRequestLegReBooking/viaRequestLegReBooking";
	}

	@PostMapping("/getViaRequestLegReBookingView")
	public String getViaRequestLegReBookingView(@RequestParam String requestId, Model model) {
		ViaRequestLegReBookingViewModel viaRequestLegReBookingView = viaRequestLegReBookingService
				.getViaRequestLegReBookingViewModel(requestId);
		model.addAttribute("reqIdModel", viaRequestLegReBookingView);
		return "/MB/RequestDashbord/ViaRequestLegReBooking/viaRequestLegReBookingView";
	}

	
//	@PostMapping("/sendViaRequestLegReBooking")
//	public String sendViaRequestLegReBooking(@RequestParam String requestId, Model model) {
//		List<ViaJourneyDetailModel> viaJourneyDetailList = viaRequestLegReBookingService.sendViaRequestLegReBooking(requestId);
//		model.addAttribute("viaJourneyDetailList", viaJourneyDetailList);
//		return "/MB/RequestDashbord/ViaRequestLegReBooking/viaRequestLegReBookingView";
//	}

	
}
