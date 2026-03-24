package com.pcda.mb.travel.railcancellation.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.travel.railcancellation.model.CancellationResponseModel;
import com.pcda.mb.travel.railcancellation.model.PostRailCancellation;
import com.pcda.mb.travel.railcancellation.model.RailCanBookingDtlsModel;
import com.pcda.mb.travel.railcancellation.model.RailCancellationModel;
import com.pcda.mb.travel.railcancellation.service.RailCancellationService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mb")
public class RailCancellationController {

	@Autowired
	private RailCancellationService railCancellationService;

	private String url = "/MB/TravelRequest/";

	@GetMapping("/railCancellation")
	public String getRailCancellation(Model model, HttpServletRequest request,@RequestParam(defaultValue = "",required = false) String personalNo, @RequestParam(defaultValue = "",required = false)String pnrNo) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());

		LoginUser loginUser = sessionVisitor.getLoginUser();
		
		if (loginUser == null) {
			return "redirect:/login";
		}
	
		Optional<OfficeModel> optionalOffice = railCancellationService.getUnitByUserId(loginUser.getUserId());
		OfficeModel officeModel = new OfficeModel();
		if (optionalOffice.isPresent()) {
			officeModel = optionalOffice.get();
		}
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, personalNo);
		model.addAttribute("loginUserId", loginUser.getUserId());
		model.addAttribute("userId", loginUser.getUserId());	
		model.addAttribute("groupId", officeModel.getGroupId());
		model.addAttribute("personalNo", decryptPersonalNo.trim());
		model.addAttribute("pnrNo", pnrNo);
		
		return url + "railCancellation/railCancellation";
	}
	@GetMapping("/cnfPageRailCancellation")
	public String confimPageIn(Model model) {
		
		return url + "railCancellation/conformationPage";
	}

	@GetMapping("/errorPageRailCancellation")
	public String errorPageRailCancellation(Model model) {
		return url + "railCancellation/errorPage";
	}
	
	//---------------------- Search Booking Details based on PNR, Personal No ----------------//
	
		@RequestMapping(value="/getBookingDtls",method= {RequestMethod.GET,RequestMethod.POST})
		public String searchBookingDtls(Model model,@RequestParam(required=false,defaultValue = "") String personalNo, 
				@RequestParam (required=false,defaultValue = "")String pnrNo, HttpServletRequest request) {
			
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());

			LoginUser loginUser = sessionVisitor.getLoginUser();
			if (loginUser == null) {
				return "redirect:/login";
			}
			String secret = "Hidden Pass";
			String decryptPersonalNo =  CommonUtil.getDecryptText(secret, personalNo);
			model.addAttribute("personalNo", decryptPersonalNo.trim());
			model.addAttribute("pnrNo", pnrNo.trim());
			Optional<OfficeModel> optionalOffice = railCancellationService.getUnitByUserId(loginUser.getUserId());
			
			String groupId="";
			if (optionalOffice.isPresent()) {
				groupId=optionalOffice.get().getGroupId();
			}
			
	List<RailCancellationModel> listModel = railCancellationService.getRailBookingDtls(groupId, decryptPersonalNo, pnrNo);
  
	
			
			if(listModel==null || listModel.isEmpty()) {
				model.addAttribute("error", "No Record Found!!");
			}else {
				
			model.addAttribute("railCancellation", listModel);
			}
			
			
			return url + "railCancellation/railCancellation";
		}

		// -------- TO GET BOOKING_DTLS WITH booking_id--------//
		
		@RequestMapping(value="/railTicketCancellation",method = {RequestMethod.GET,RequestMethod.POST})
		public String railTicketDetailsCancellation(Model model,@RequestParam String bookingId ) {
			
			RailCanBookingDtlsModel railModel = railCancellationService.getRailBookingContentByBookingId(bookingId);
			
			
			
			model.addAttribute("ticketDetails", railModel);
			model.addAttribute("isBooked", railCancellationService.isBooked(railCancellationService.getRailBookingContentByBookingId(bookingId)));
			return url+ "railCancellation/railBookingDtls";
			
		}
		
		//----------------- save RaiCancellation-------------
		
		@PostMapping("/saveRailCancellation")
		public String saveRailCancellation(@ModelAttribute @Valid PostRailCancellation postRailCancellation, BindingResult result,
				RedirectAttributes attributes, HttpServletRequest request) {
			
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();
			BigInteger userId = loginUser.getUserId();
			OfficeModel officeModel = railCancellationService.getOfficeByUserId(userId);
		String groupId = officeModel.getGroupId();
		
			postRailCancellation.setGroupId(groupId);
			postRailCancellation.setLoginUserId(userId);
			
			
			try {
				if (result.hasErrors()) {
					DODLog.error(LogConstant.RAIL_CANCELLATION_LOG_FILE, RailCancellationController.class,"RailCancellation Error: " + result.getAllErrors());
					ObjectError objectError = result.getAllErrors().get(0);
					attributes.addFlashAttribute("RailCancellation", postRailCancellation);
					attributes.addFlashAttribute("errors", objectError.getDefaultMessage());
					return "redirect:errorPageRailCancellation";
				} else {
					CancellationResponseModel responce=railCancellationService.saveRailCancellation(postRailCancellation,request);
					if (responce != null) {
						if (responce.getErrorCode() == 200) {
							attributes.addFlashAttribute("success", responce.getErrorMessage());
						
							return url + "railCancellation/conformationPage";
						} else {
							attributes.addFlashAttribute("errors", responce.getErrorMessage());
							return "redirect:errorPageRailCancellation";
						}
					}
					 return  url + "railCancellation/errorPage";
					
				} 

			} catch (Exception e) {
			DODLog.printStackTrace(e, RailCancellationController.class, LogConstant.TRANSFER_LOG_FILE);
				attributes.addFlashAttribute("errors", "Error while creating User");
				 return  url + "railCancellation/errorPage";
			}
		}
		
}
