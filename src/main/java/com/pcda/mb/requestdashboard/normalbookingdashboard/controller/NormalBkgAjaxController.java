package com.pcda.mb.requestdashboard.normalbookingdashboard.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.reports.airrequestreport.model.GetAirReqIdResponse;
import com.pcda.mb.reports.airrequestreport.model.GetAirRqIdParentModel;
import com.pcda.mb.reports.railrequestreport.model.GetRailReqIdParentModel;
import com.pcda.mb.reports.railrequestreport.model.GetReqIdResponse;
import com.pcda.mb.reports.userreports.model.GetTravllerProfileModel;
import com.pcda.mb.reports.userreports.model.TravellerProfileResponseModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetFareAccomodateParent;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetFareAccomodateResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetPinCodeResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetSingleTrainRouteModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetSingleTrainRouteResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PinCodeDetailsBean;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostFareAccomodationModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.service.NormalBkgDashService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class NormalBkgAjaxController {

	@Autowired
	private OfficesService offService;	

	@Autowired
	private NormalBkgDashService normalService;
	
	

	//AJAX for AIR REQ-ID view
	@PostMapping("/getAirBookReqIdView")
	public String getAirBookReqIdView(@RequestParam String requestViewId,Model model , HttpServletRequest request) {
		GetAirReqIdResponse response = normalService.getAirBookReqIdData(requestViewId);
		if(response!=null && response.getErrorCode()==200&& response.getResponse()!=null) {
			GetAirRqIdParentModel parentModel = response.getResponse();
			Collections.sort(parentModel.getRequestQuestionList());
			
			model.addAttribute("DataModel", parentModel);
		}else {
			model.addAttribute("noData", "No Data Available!!");
		}
		return  "/MB/RequestDashbord/normalbookingdashboard/airrequestidview";
	}


	// AJAX Call For ATT Fair Rule 
	@PostMapping("/getATTFareRule")
	@ResponseBody
	public String getATTFairRule(@RequestParam String flightKey){
	return normalService.getATTFareRule(flightKey);
	}

	// AJAX for personal no View
	@PostMapping("/getNormalPersonalNoView")
	public String getNormalPersonalNoView(@RequestParam String personalNo, Model model, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}String groupId ="";
		Optional<OfficeModel> office =  offService.getOfficeByUserId(loginUser.getUserId());
		if(office.isPresent()) {

			groupId =office.get().getGroupId();
		}
		TravellerProfileResponseModel response = normalService.getViewPersonal(personalNo, groupId);
		GetTravllerProfileModel  personalModel = new GetTravllerProfileModel();
		if(response!=null && response.getErrorCode()==200) {
			personalModel=response.getResponse();
			personalModel.setUserAlias(personalModel.getSignInID());
		}
		model.addAttribute("userList",personalModel );
		return "/MB/RequestDashbord/normalbookingdashboard/normalPersonalNoPopUp";
	}


	// AJAX Call For BL Fair Rule
	@PostMapping("/getBLFareRule")
	@ResponseBody
	public String getBLFairRule(@RequestParam String flightKey , @RequestParam String domInt  ) {

		return normalService.getBLFareRule(flightKey, domInt);
	}
	
	
	// AJAX for RAIL REQ-ID VIEW
	@PostMapping ("/getRailBookReqIdView")
	public String getRailBookReqIdView(@RequestParam String requestViewId,Model model,HttpServletRequest request) {
		GetReqIdResponse response = normalService.getRailBookReqIdData(requestViewId);
		if(response!=null && response.getErrorCode()==200 && response.getResponse()!=null) {
			GetRailReqIdParentModel idParentModel = response.getResponse(); 
			Collections.sort(idParentModel.getRequestQuestionList());
			Collections.sort(idParentModel.getJourneyDetailList());
			Collections.sort(idParentModel.getPassengerDetailList());
			model.addAttribute("reqIdModel", idParentModel);
		}else {
			model.addAttribute("noData", "NO Data Available!!");
		}
		return "/MB/RequestDashbord/normalbookingdashboard/railbookrequestidview";
	}

	

	// AJAX FARE and ACCOMODATION details
	@PostMapping("/getFareAccomodationDtls")
	@ResponseBody
	public GetFareAccomodateParent getFareAccomodationDtls(Model model ,HttpServletRequest request,PostFareAccomodationModel postModel) {
		GetFareAccomodateParent parentModel=null;
		GetFareAccomodateResponse response = normalService.getFareAccmodationDtls(postModel);
		if(response!=null && response.getErrorCode()==200) {
		  parentModel = response.getResponse();
		}
		
		
		
		return parentModel;
	}




	//RAIL pop-up on train no for train route
	@PostMapping("/getSingleTrainRoute")
	public String getTrainRoute(Model model ,HttpServletRequest request,@RequestParam String journeyDate, @RequestParam String trainNo) {
		GetSingleTrainRouteResponse response =	normalService.getSingleTrainData(journeyDate,trainNo);
		if(response!=null && response.getErrorCode()==200 && !response.getResponseList().isEmpty()) {
			List<GetSingleTrainRouteModel> modelList = response.getResponseList();
			model.addAttribute("modelList", modelList);
		}
		return "/MB/RequestDashbord/normalbookingdashboard/TrainRouteViewPopUp";
	}

	//RAIL AJAX FOR PINCODE DETAILS
	@PostMapping("getPinCodeDtls")
	@ResponseBody
	public PinCodeDetailsBean getPinCodeDtls(@RequestParam String pinCode,HttpServletRequest request) {
		PinCodeDetailsBean pinBean = new PinCodeDetailsBean();
		GetPinCodeResponse response = normalService.getPinCodeDtsl(pinCode);
		if(response!=null && response.getErrorCode()==200) {
			pinBean=response.getResponse();
		}
		
		return pinBean;
	}

	
	
}
