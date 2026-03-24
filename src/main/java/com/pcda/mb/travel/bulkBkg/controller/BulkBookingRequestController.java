package com.pcda.mb.travel.bulkBkg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.travel.bulkBkg.model.JourneyRequestBulkBkgDTO;
import com.pcda.mb.travel.bulkBkg.model.TravelRequestViewBulkBkgResponse;
import com.pcda.mb.travel.bulkBkg.service.JourneyRequestBulkBkgService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mb")
public class BulkBookingRequestController {
	
	
		@Autowired
		private JourneyRequestBulkBkgService  requestService; 
		
		private final String pagePath = "/MB/TravelRequest/bulkBkgRequest/";

		@GetMapping("/createBulkBkgRequest")
		public String createJourneyRequest(Model model, HttpServletRequest request) {
			
			final String TRAVELLER_REQUEST="DOD_TRAVELLER_REQUEST_SAVE_ACTION";
			
			HttpSession session= request.getSession();
			
			if(null!=session.getAttribute("refresh"))
				session.removeAttribute("refresh");
			
			if(null!=session.getAttribute(TRAVELLER_REQUEST) && session.getAttribute(TRAVELLER_REQUEST).equals("true"))
			{
				session.removeAttribute(TRAVELLER_REQUEST);
			}
			
			SessionVisitor sessionVisitor=SessionVisitor.getInstance(session);
			LoginUser visitor= sessionVisitor.getLoginUser();
			
			model.addAttribute("journeyRequest", requestService.createJourneyRequest(visitor));
			
			
			
			return pagePath + "createBulkBkgRequest";
		
		
		}
		
		@PostMapping("/saveJourneyBulkBkgRequest")
		public String saveJourneyRequest(HttpServletRequest request, Model model) {
		
			JourneyRequestBulkBkgDTO journeyRequestDTO = new JourneyRequestBulkBkgDTO();
			TravelRequestViewBulkBkgResponse viewResponse = requestService.postRequestData(request, journeyRequestDTO);
			if (viewResponse != null && viewResponse.getErrorCode() == 200 && viewResponse.getResponse() != null) {
				
				String requestType = request.getParameter("reqType");
				
		
				model.addAttribute("requestType",requestType);
				model.addAttribute("request", viewResponse.getResponse());
				return pagePath + "saveJourneyRequestBulkBkgView";
			}else if(viewResponse != null && viewResponse.getErrorMessage() != null) {
				
				model.addAttribute("errorMessage", viewResponse.getErrorMessage());
			}else if(viewResponse != null) {
				
				model.addAttribute("errorMessage",null==viewResponse.getErrorMessage() ? "Due To Internal Error Request Is Not Being Saved.":viewResponse.getErrorMessage());
			}
				return pagePath + "errorPageBulkBkg";
			}
}
