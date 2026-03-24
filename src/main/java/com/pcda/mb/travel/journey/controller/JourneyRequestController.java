package com.pcda.mb.travel.journey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.travel.journey.model.JourneyRequestDTO;
import com.pcda.mb.travel.journey.model.TravelRequestViewResponse;
import com.pcda.mb.travel.journey.service.JourneyRequestService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mb")
public class JourneyRequestController {
	
	@Autowired
	private JourneyRequestService  requestService; 
	
	private final String pagePath = "/MB/TravelRequest/journeyRequest/";

	@GetMapping("/createJourneyRequest")
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
		
		
		
		return pagePath + "createJourneyRequest";
	}
	
	@PostMapping("/saveJourneyRequest")
	public String saveJourneyRequest(HttpServletRequest request, Model model) {
		
		JourneyRequestDTO journeyRequestDTO = new JourneyRequestDTO();
		TravelRequestViewResponse viewResponse = requestService.postRequestData(request, journeyRequestDTO);
		if (viewResponse != null && viewResponse.getErrorCode() == 200 && viewResponse.getResponse() != null) {
			
			String requestType = request.getParameter("reqType");
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestController.class," requestType  ++ "+ requestType);
			
			
			model.addAttribute("requestType",requestType);
			model.addAttribute("request", viewResponse.getResponse());
			return pagePath + "saveJourneyRequestView";
		}else if(viewResponse != null && viewResponse.getErrorMessage() != null) {
			
			model.addAttribute("errorMessage", viewResponse.getErrorMessage());
		}else {
			
			model.addAttribute("errorMessage",null==viewResponse.getErrorMessage() ? "Due To Internal Error Request Is Not Being Saved.":viewResponse.getErrorMessage());
		}
			return pagePath + "errorPage";
		}

}
