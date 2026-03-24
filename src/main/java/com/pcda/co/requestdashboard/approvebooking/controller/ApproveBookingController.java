package com.pcda.co.requestdashboard.approvebooking.controller;

import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.co.requestdashboard.approvebooking.model.GetAppBookDADetails;
import com.pcda.co.requestdashboard.approvebooking.model.GetApproveBookingBean;
import com.pcda.co.requestdashboard.approvebooking.model.GetBookingDataResponse;
import com.pcda.co.requestdashboard.approvebooking.model.GetDADetailsResponse;
import com.pcda.co.requestdashboard.approvebooking.model.GetNormalBookAppResponse;
import com.pcda.co.requestdashboard.approvebooking.model.PostApproveBookModel;
import com.pcda.co.requestdashboard.approvebooking.model.PostDisAppNormalBook;
import com.pcda.co.requestdashboard.approvebooking.service.ApproveBookingService;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.edittravelerprofile.model.DependantDtls;
import com.pcda.mb.reports.airrequestreport.model.AirReqIdQuestionModel;
import com.pcda.mb.reports.airrequestreport.model.GetAirReqIdResponse;
import com.pcda.mb.reports.airrequestreport.model.GetAirRqIdParentModel;
import com.pcda.mb.reports.railrequestreport.model.GetRailReqIdParentModel;
import com.pcda.mb.reports.railrequestreport.model.GetReqIdResponse;
import com.pcda.mb.reports.userreports.model.GetTravllerProfileModel;
import com.pcda.mb.reports.userreports.model.TravellerProfileResponseModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class ApproveBookingController {

	@Autowired
	private OfficesService offService;

	@Autowired
	private ApproveBookingService appBookService;

	@GetMapping("/getAppDisAppBooking")
	public String getAppDisAppBooking(Model model, HttpServletRequest request) {
		SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = visitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		String groupId = "";
		Optional<OfficeModel> office = offService.getOfficeByUserId(loginUser.getUserId());
		if (office.isPresent()) {
			groupId = office.get().getGroupId();
		}
		
		GetApproveBookingBean approveBeanModel = new GetApproveBookingBean();
		try {
		GetBookingDataResponse response=appBookService.getBookDataForApp("ALL","ALL",groupId);
		   if(response!=null && response.getResponse()!=null && response.getErrorCode()==200) {
			approveBeanModel = response.getResponse();
			DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingController.class,"##### APPROVE BOOKING DATA RESPONSE ##### :: "+approveBeanModel);
		
			model.addAttribute("approvalModel", approveBeanModel);
				
			if(approveBeanModel.getAirRequests().isEmpty() && approveBeanModel.getRailRequests().isEmpty()) {
				model.addAttribute("noData", "No Request For Approval");
				}
	         }
		else {
			model.addAttribute("noData", "No Request For Approval");
			}
			}catch (Exception e) {
				DODLog.printStackTrace(e, ApproveBookingService.class, LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE);
			}
		 
		model.addAttribute("pno", "");
		model.addAttribute("req", "");
		
		return "/CO/RequestDashBoard/approvebooking/ApproveBooking";
	}

	
@RequestMapping(value="/getBookDataForApp",method = {RequestMethod.GET,RequestMethod.POST})
public String getBookDataForApp(Model model,@RequestParam(required=false)String personalNo,@RequestParam(required=false) String requestMode,HttpServletRequest request) {
	SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = visitor.getLoginUser();
	if (loginUser == null) {
		return "redirect:/login";
	}
	String groupId = "";
	Optional<OfficeModel> office = offService.getOfficeByUserId(loginUser.getUserId());
	if (office.isPresent()) {
		groupId = office.get().getGroupId();
	}
	
	GetApproveBookingBean approveBeanModel = new GetApproveBookingBean();
	try {
		DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingController.class,"##### APPROVE BOOKING SEARCH REQUEST ##### :: "+"PNO ::"+personalNo+
				" :: REQ-MODE ::"+requestMode);
	GetBookingDataResponse response=appBookService.getBookDataForApp(personalNo,requestMode,groupId);
	   if(response!=null && response.getResponse()!=null && response.getErrorCode()==200) {
		approveBeanModel = response.getResponse();
		DODLog.info(LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE, ApproveBookingController.class,"##### APPROVE BOOKING SEARCH DATA RESPONSE ##### :: "+approveBeanModel);
		
	     	model.addAttribute("approvalModel", approveBeanModel);
	     	if(approveBeanModel.getAirRequests().isEmpty() && approveBeanModel.getRailRequests().isEmpty()) {
				model.addAttribute("noData", "No Request For Approval");
		}
	}
	else {
		model.addAttribute("noData", "No Request For Approval");
			}
	}catch (Exception e) {
		DODLog.printStackTrace(e, ApproveBookingService.class, LogConstant.APPROVE_BOOKING_DASHBOARD_LOG_FILE);
	}
		
	model.addAttribute("pno", personalNo);
	model.addAttribute("req", requestMode);
	
	return "/CO/RequestDashBoard/approvebooking/ApproveBooking";
}

//Approve normal Booking
@PostMapping("/AppDisAppNormalBook")
public String getAppDisAppNormalBook(Model model,HttpServletRequest request,PostApproveBookModel approveBookModel,RedirectAttributes redirect) {
	SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = visitor.getLoginUser();
	if (loginUser == null) {
		return "redirect:/login";
	}
	
	GetNormalBookAppResponse response = null ;
	approveBookModel.setLoginUserId(loginUser.getUserId());
	

	if(approveBookModel.getEvent().equals("approve")) {
		 response=	appBookService.sendForApproval(approveBookModel);
		 if(response !=null && response.getErrorCode()==200) {
			 redirect.addAttribute("personalNo", approveBookModel.getPersonalNo());
			 redirect.addAttribute("requestMode", approveBookModel.getRequestMode());
				redirect.addFlashAttribute("success", "Request Approved Successfully");
				return "redirect:getBookDataForApp";
			}
		
	}
if(approveBookModel.getEvent().equals("disapprove")) {
	PostDisAppNormalBook disAppNormalBook = new PostDisAppNormalBook(); 
	disAppNormalBook.setRequestId(approveBookModel.getRequestId());
	disAppNormalBook.setTravelMode(approveBookModel.getTravelMode());
	disAppNormalBook.setReasonForDisapprove(approveBookModel.getReasonForDisapprove());
	disAppNormalBook.setPersonalNo(approveBookModel.getPersonalNo());
	disAppNormalBook.setLoginUserId(loginUser.getUserId());
	
	 response=	appBookService.sendForDisApproval(disAppNormalBook);
     if(response !=null && response.getErrorCode()==200) {
		redirect.addAttribute("personalNo", approveBookModel.getSearchPNo());
		redirect.addAttribute("requestMode", approveBookModel.getRequestMode());
	redirect.addFlashAttribute("success", "Request Rejected Successfully");
	return "redirect:getBookDataForApp";
}
	}

	return "/common/errorPage";
}


//--ajax pop-up for rail req id view-
@PostMapping ("/getAppRailBookReqIdView")
public String getAppRailBookReqIdView(@RequestParam String requestViewId,Model model,HttpServletRequest request) {
	GetReqIdResponse response = appBookService.getRailBookReqIdData(requestViewId);
	if(response!=null && response.getErrorCode()==200 && response.getResponse()!=null) {
		GetRailReqIdParentModel idParentModel = response.getResponse(); 
		if(!idParentModel.getRequestQuestionList().isEmpty() && !idParentModel.getPassengerDetailList().isEmpty()){
	
			Collections.sort(idParentModel.getRequestQuestionList());
			Collections.sort(idParentModel.getPassengerDetailList());

		}

		model.addAttribute("reqIdModel", idParentModel);
	}else {
		model.addAttribute("noData", "No Data Available!!");
	}
	return "/CO/RequestDashBoard/approvebooking/AppRailbookreqidview";
}

//ajax for Air-req id view
@PostMapping("/getAppAirBookReqIdView")
public String getAppAirBookReqIdView(@RequestParam String requestViewId,Model model , HttpServletRequest request) {
	GetAirReqIdResponse response = appBookService.getAirBookReqIdData(requestViewId);
	if(response!=null && response.getErrorCode()==200&& response.getResponse()!=null) {
		GetAirRqIdParentModel parentModel = response.getResponse();
		if(!parentModel.getRequestQuestionList().isEmpty() && !parentModel.getPassengerDetailList().isEmpty()){
			
		List<AirReqIdQuestionModel> requestQuestionList= new ArrayList<>();
		requestQuestionList=parentModel.getRequestQuestionList();
		if(requestQuestionList!=null && !requestQuestionList.isEmpty()) {
		Collections.sort(requestQuestionList);
		Collections.sort(parentModel.getPassengerDetailList());
		parentModel.setRequestQuestionList(requestQuestionList);
		
		}
		}
		model.addAttribute("DataModel", parentModel);
	}else {
		model.addAttribute("noData", "No Data Available!!");
	}
	return  "/CO/RequestDashBoard/approvebooking/AppAirReqidview";
}

//AJAX for personal no View
@PostMapping("/getAppPersonalNoView")
public String geApptNormalPersonalNoView(@RequestParam String personalNo, Model model, HttpServletRequest request) {

	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();

	if (loginUser == null) {
		return "redirect:/login";
	}

	TravellerProfileResponseModel response = appBookService.getViewPersonal(personalNo);
	GetTravllerProfileModel  personalModel = new GetTravllerProfileModel();
	if(response!=null && response.getErrorCode()==200) {
		personalModel=response.getResponse();
		
		List<DependantDtls> familyDetails= new ArrayList<>();
				
		
			
		personalModel.getFamilyDetails().forEach(e->{
			DependantDtls depDtsl = e;
			depDtsl.setFirstName(Optional.ofNullable(e.getFirstName()).orElse(""));
			depDtsl.setMiddleName(Optional.ofNullable(e.getMiddleName()).orElse(""));
			depDtsl.setLastName(Optional.ofNullable(e.getLastName()).orElse(""));
     		familyDetails.add(depDtsl);
		});
		Collections.sort(familyDetails);
		personalModel.setFamilyDetails(familyDetails);
		
		 String naDutyStn = personalModel.getNaDutyStn();
		 String replace = naDutyStn.replaceAll("\\(null\\)|\\(\\)", "");
		 
		 String sprNrs = personalModel.getSprNrs();
		 String sprNrsReplace = sprNrs.replaceAll("\\(null\\)|\\(\\)", "");
		 
		 String homeTownNa = personalModel.getHomeTownNa();
		 String homeTownNaReplace = homeTownNa.replaceAll("\\(null\\)|\\(\\)", "");
		 
		 String sprNa = personalModel.getSprNa();
		 String sprNaReplace = sprNa.replaceAll("\\(null\\)|\\(\\)", "");
		 
		personalModel.setNaDutyStn(replace);
		personalModel.setSprNrs(sprNrsReplace);
		personalModel.setHomeTownNa(homeTownNaReplace);
		personalModel.setSprNa(sprNaReplace);
		personalModel.setUserAlias(personalModel.getSignInID());
	}
	model.addAttribute("userList",personalModel );
	return  "/CO/RequestDashBoard/approvebooking/AppBookPersonalNoPopUp";
}

// ajax da-details pop-up
@PostMapping("/getAppDADetails")
public String getAppDADetails(Model model,HttpServletRequest request,@RequestParam String daRequestId ) {
	GetAppBookDADetails dataModel =  new GetAppBookDADetails();
	GetDADetailsResponse response=	appBookService.getAppBookDADetails(daRequestId);
	if(response!=null && response.getErrorCode()==200) {
		dataModel=response.getResponse();
	}
	model.addAttribute("dataModel", dataModel);
	return  "/CO/RequestDashBoard/approvebooking/AppBookDADetails";
}



}
