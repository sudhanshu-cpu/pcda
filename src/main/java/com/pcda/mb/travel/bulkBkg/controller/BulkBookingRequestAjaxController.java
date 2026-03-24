package com.pcda.mb.travel.bulkBkg.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcda.common.model.CitySearchModel;
import com.pcda.mb.travel.bulkBkg.model.PartyVisitorBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.model.TRRulesDataBulkBkg;
import com.pcda.mb.travel.bulkBkg.model.TravellerDetailsBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.model.TravellerInfoBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.service.AirListBulkBkgService;
import com.pcda.mb.travel.bulkBkg.service.BulkBkgRequestAjaxService;
import com.pcda.mb.travel.bulkBkg.service.TravelInfoBulkBkgService;

import jakarta.servlet.http.HttpServletRequest;
@Controller
@RequestMapping("/mb")
public class BulkBookingRequestAjaxController {

	@Autowired
	private BulkBkgRequestAjaxService requestBulkAjaxService;
	@Autowired
	private TravelInfoBulkBkgService travelInfoBulkBkgService;
	@Autowired
	private AirListBulkBkgService  airListBulkBkgService;
	
	private final String path = "MB/TravelRequest/bulkBkgRequest/";
	
	@ResponseBody
	@PostMapping("/getTravellerDetailsBulkBkg")
	public TravellerDetailsBulkBkgModel getTravellerDetailsBulkBkg(HttpServletRequest request) {
		
		return requestBulkAjaxService.getTravellerDetailsBulkBkg(request);
	}
	
	
	@ResponseBody
	@GetMapping("/getAllTRforTravelGroupBulkBkg")
	public List<TRRulesDataBulkBkg> getAllTRforTravelGroupBulkBkg(HttpServletRequest request) {
		
		return requestBulkAjaxService.getAllTRforTravelGroupBulkBkg(request);
	}
	
	@PostMapping("/getTravelRuleDetailsBulkBkg")
	public String getTravelRuleDetailsBulkBkg(Model model, HttpServletRequest request) {
		model.addAttribute("ruleDtls", requestBulkAjaxService.getTravelRuleDetailsBulkBkg(request));
		
		return path + "trRulesDetailsBulkBkg";
	}
	
	@ResponseBody
	@GetMapping("/getTravelGroupBulkBkg")
	public Map<Integer, String> getTravelGroupBulkBkg(HttpServletRequest request) {
		
		return requestBulkAjaxService.getTravelGroupBulkBkg(request);
	}
	
	@ResponseBody
	@GetMapping("/getAllTRforTravelTypeBulkBkg")
	public List<TRRulesDataBulkBkg> getAllTRforTravelTypeBulkBkg(HttpServletRequest request) {
		
		return requestBulkAjaxService.getAllTRforTravelTypeBulkBkg(request);
	}
	
	@ResponseBody
	@GetMapping("/fillTravelInfoBulkBkg")
	public TravellerInfoBulkBkgModel fillTravelInfoBulkBkg(HttpServletRequest request) {
		
		return travelInfoBulkBkgService.getTravelInfoBulkBkg(request);
	}
	
	@GetMapping("/journyDtlsBulkBkgAjax")
	public String journyDtlsAjax(Model model, HttpServletRequest request) {
		
		model.addAttribute("jrnyType", Optional.ofNullable(request.getParameter("jrnyType")).orElse("")); 
		model.addAttribute("reqType", Optional.ofNullable(request.getParameter("reqType")).orElse("")); 
		model.addAttribute("serviceType", Optional.ofNullable(request.getParameter("serviceType")).orElse("")); 
		
		return path + "journeyDtlsBulkBkg";
	}
	
	@ResponseBody
	@GetMapping("/getBulkBkgPartyVisitor")
	public PartyVisitorBulkBkgModel getPartyVisitor(HttpServletRequest request) {
		
		return requestBulkAjaxService.getPartyVisitor(request);
	}
	
	
	@ResponseBody
	@GetMapping("/getAirportBulkBkgList")
	public List<String> getAirportList(HttpServletRequest request) {
		
		
		return airListBulkBkgService.getAirportList(request);
	}
	
	@ResponseBody
	@GetMapping("/getCityBulkBkgList")
	public List<CitySearchModel> getCityList(HttpServletRequest request) {
		
		
		return requestBulkAjaxService.getCityList(request);
	}
	
	
	@GetMapping("/airBulkBkgSrchRsltAjax")
	public String getAirSearchResult(Model model, HttpServletRequest request) {
		model.addAttribute("airBookParent", airListBulkBkgService.getAirSearchResult(request)); 
		
		return path + "airSearchBulkBkgResult";
	}
	
	@PostMapping("/jrnyBulkBkgATTFareRule")
	@ResponseBody
	public String getATTFairRule(@RequestParam String flightKey){
		
	return requestBulkAjaxService.getATTFareRule(flightKey);
	}
	
	
	// AJAX Call For BL Fair Rule
	@PostMapping("/jrnyBulkBkgBLFareRule")
	@ResponseBody
	public String getBLFairRule(@RequestParam String flightKey , @RequestParam String domInt  ) {

		
		return requestBulkAjaxService.getBLFareRule(flightKey, domInt);
	}
	
	
	@ResponseBody
	@GetMapping("/getTravelerBulkBkgJournayDate")
	public String getTravelerJournayDate(HttpServletRequest request) {
		
		
		return requestBulkAjaxService.getTravelerJournayDate(request);
	}
}
