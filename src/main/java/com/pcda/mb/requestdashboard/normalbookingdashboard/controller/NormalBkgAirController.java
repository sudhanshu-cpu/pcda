package com.pcda.mb.requestdashboard.normalbookingdashboard.controller;

import java.io.ByteArrayOutputStream;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.FlightOptListSessionModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.FlightSearchOption;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetATTBookingResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetATTPassBookModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetAirBookReqIdParent;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetAirBookResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetAttBookInfoModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetAttConfirmBookingResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetAttFlightInfoModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetAttPassengerInfoModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetBLBookInfoModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetBLBookingResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetBLConfirmBookResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetBLFlightInfoModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetBLPassBokkModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetBLPassengerInfoModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetNormalCancelResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetNormalDashDataResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetNormalDashParentModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PosrAirBookATTModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostAirBookBlModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostConfimBLAirBookModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostConfirmAttAirBook;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostNormalCancelModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.service.NormalBkgDashService;
import com.pcda.mb.travel.emailticket.model.AirTicketPdfModel;
import com.pcda.mb.travel.emailticket.model.FlightInfo;
import com.pcda.mb.travel.emailticket.model.PassengerInfo;
import com.pcda.mb.travel.emailticket.service.AirEmailTicketService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mb")
public class NormalBkgAirController {
	
	
@Autowired
private OfficesService offService;	

@Autowired
private NormalBkgDashService normalService;

@Autowired

private AirEmailTicketService emailTicketService;

	
@GetMapping("/getNormalBkgDash")
public String getNormalBkgDash(Model model, HttpServletRequest request) {
	SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = visitor.getLoginUser();
	if (loginUser == null) {
		return "redirect:/login";
	}
	String groupId ="";
	Optional<OfficeModel> office =  offService.getOfficeByUserId(loginUser.getUserId());
	if(office.isPresent()) {
		groupId =office.get().getGroupId();
	}
	model.addAttribute("pNoList", normalService.getUniquePersonalNo(groupId));
	model.addAttribute("pno", "");
	model.addAttribute("req", "");
	return "/MB/RequestDashbord/normalbookingdashboard/normalbooking";
}

// data after search based on personal no and requestMode
@RequestMapping(value="/getNormalData",method = {RequestMethod.GET,RequestMethod.POST})
public String getNormalSearchData(Model model,@RequestParam(required=false,defaultValue = "ALL")String personalNo,
		@RequestParam(required=false,defaultValue = "ALL")String requestMode,HttpServletRequest request) {
	
	
	
	SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = visitor.getLoginUser();
	if (loginUser == null) {
		return "redirect:/login";
	}
	String groupId ="";
	Optional<OfficeModel> office =  offService.getOfficeByUserId(loginUser.getUserId());
	if(office.isPresent()) {

		groupId =office.get().getGroupId();
	}
	model.addAttribute("pNoList", normalService.getUniquePersonalNo(groupId));
	 
	model.addAttribute("pno", personalNo);
	model.addAttribute("req",requestMode);
	GetNormalDashDataResponse	response = normalService.getNormalData(personalNo, requestMode, groupId);
	if(response!=null && response.getErrorCode()==200 && !response.getResponseList().isEmpty()) {
		List<GetNormalDashParentModel> modelList = response.getResponseList();
		model.addAttribute("modelList",modelList);
	}else {
		model.addAttribute("noData", "No Result Found!!");
	}
	
	return "/MB/RequestDashbord/normalbookingdashboard/normalbooking";
}

//---Cancel ---------

//cancel button
@PostMapping("/RailCancelReasonPage")
public String getRailCancelReasonPage(Model model,@RequestParam(defaultValue = "") String railReqId,String sequenceNo,HttpServletRequest request ) {

	DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgAirController.class, "#### INSIDE  RailCancelReasonPage  #### railReqId "
	+railReqId+" ## sequenceNo"+sequenceNo);
	
	model.addAttribute("reqId", railReqId);
	model.addAttribute("sequenceNo", sequenceNo);
	model.addAttribute("cancelRequestType", "0");
	return "/MB/RequestDashbord/normalbookingdashboard/CancelReasonPage";
}

@PostMapping("/AirCancelReasonPage")
public String getAirCancelReasonPage(Model model,@RequestParam(defaultValue = "") String airReqId,String sequenceNo,HttpServletRequest request ) {
	
	DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgAirController.class, "#### INSIDE  AirCancelReasonPage  #### airReqId "
			+airReqId+" ## sequenceNo"+sequenceNo);
	
	model.addAttribute("reqId", airReqId);
	model.addAttribute("sequenceNo", 0);
	model.addAttribute("cancelRequestType", "1");
	return "/MB/RequestDashbord/normalbookingdashboard/CancelReasonPage";
}

@PostMapping("/cancelNormalRequest")
public String cancelNormalRequest(Model model ,PostNormalCancelModel postNormalCancelModel,HttpServletRequest request ) {
	postNormalCancelModel.setOperationType("MB");
	GetNormalCancelResponse response=	normalService.cancelNormalRequest(postNormalCancelModel);
	if(response!=null && response.getErrorCode()==200) {
		return "redirect:getNormalBkgDash";
	}else if(response!=null && response.getErrorCode()==400 ){
		model.addAttribute("errors", response.getErrorMessage());
		 return "/common/errorPage";
	}
	else{
		model.addAttribute("errors", "Request Not Cancelled");
		 return "/common/errorPage";
	}

}

//----------------------

// flight info after click on book button 
@PostMapping("/getAirBookData")
public String getAirBookData(Model model,@RequestParam String airRequestId,HttpServletRequest request) {
	
	airRequestId= Base64Coder.decodeString(airRequestId); 
GetAirBookResponse response = normalService.getAirBookData(airRequestId);

	if(response.getResponse()!=null && response.getErrorCode()==200) {
		GetAirBookReqIdParent	parentModel = response.getResponse();
	
	model.addAttribute("airRequestId", airRequestId);
	model.addAttribute("airBookParent", parentModel);
	
	 HttpSession session = request.getSession();
	 session.removeAttribute("flightOptionList");
	 FlightOptListSessionModel fltOptSesModel = new FlightOptListSessionModel();
	 if(null!=parentModel.getFlightOption()) {
	 fltOptSesModel.setFlightOption(parentModel.getFlightOption().stream().filter(FlightSearchOption ::getBookingAllow).toList());
	 }else {
		 fltOptSesModel.setFlightOption(new ArrayList<>());
	 }
	 session.setAttribute("flightOptionList",fltOptSesModel);
	
	return "/MB/RequestDashbord/normalbookingdashboard/normalAirBook";
	}else {
		model.addAttribute("errorMessage", response.getErrorMessage());
		 return "/MB/RequestDashbord/normalbookingdashboard/AirBookErrorPage";
	}
	
}





// traveller in and flight info for Att flight
@PostMapping("/bookATTFlight")
public String bookAttFlight(Model model ,PosrAirBookATTModel airBookATTModel,@RequestParam String isValidatedCase,@RequestParam String validatedReason) {
	
	
	GetATTBookingResponse response = normalService.bookATTFlight(airBookATTModel);
	if(response!=null && response.getErrorCode()==200) {
		
		GetATTPassBookModel attModel = response.getResponse(); 
	    model.addAttribute("attFareModel", attModel);
	    model.addAttribute("isValidatedCase", isValidatedCase);
	    model.addAttribute("validatedReason", validatedReason);
	    model.addAttribute("requestId", airBookATTModel.getRequestId());
	    return "/MB/RequestDashbord/normalbookingdashboard/ATTBooking";
	}else if(response!=null && response.getErrorCode()==400){
		 model.addAttribute("errorMessage",response.getErrorMessage());
		 return "/MB/RequestDashbord/normalbookingdashboard/AirBookErrorPage";
	}else {
		 return "/MB/RequestDashbord/normalbookingdashboard/AirBookErrorPage";
	}
	
}

//traveller in and flight info , validate fair for Bl flight
@PostMapping("/bookBLLFlight")
public String bookBlFlight(Model model ,PostAirBookBlModel postAirBookBlModel,@RequestParam String isValidatedCase,@RequestParam String validatedReason,
		HttpServletRequest request) {
	
	
	 FlightOptListSessionModel fltOptSesModel = new FlightOptListSessionModel();
		HttpSession session = request.getSession();
		 Object data=session.getAttribute("flightOptionList");
	   if(null!=data && data instanceof FlightOptListSessionModel flightOptModel) {
		   fltOptSesModel=flightOptModel;
	   }
	   List<FlightSearchOption> flightOptList = fltOptSesModel.getFlightOption();
	GetBLBookingResponse response = normalService.bookBLFlight(postAirBookBlModel,flightOptList);
	if(response!=null && response.getErrorCode()==200) {
		GetBLPassBokkModel blModel = response.getResponse(); 
	    model.addAttribute("blFareModel", blModel);
	    model.addAttribute("isValidatedCase", isValidatedCase);
	    model.addAttribute("validatedReason", validatedReason);
	    model.addAttribute("requestId", postAirBookBlModel.getRequestId());
	    return "/MB/RequestDashbord/normalbookingdashboard/BLBooking";
	}else if (response!=null && response.getErrorCode()==400){
		 model.addAttribute("errorMessage",response.getErrorMessage());
		 return "/MB/RequestDashbord/normalbookingdashboard/AirBookErrorPage";
	}else {
		 
		 return "/MB/RequestDashbord/normalbookingdashboard/AirBookErrorPage";
	}
	
}


// Att confirm
@PostMapping("/confirmAttAirBooking")
public String confirmATTBooking(PostConfirmAttAirBook attAirBookModel,Model model,HttpServletRequest request ) {
	

	SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
   
     
	LoginUser loginUser = visitor.getLoginUser();
	if (loginUser == null) {
		return "redirect:/login";
	}
	attAirBookModel.setLoginUserId(loginUser.getUserId());
	
	
	HttpSession session = request.getSession();
	 String sessionId = session.getId();
    attAirBookModel.setSessionId(sessionId);
    FlightOptListSessionModel fltOptSesModel = new FlightOptListSessionModel();
    Object data=session.getAttribute("flightOptionList");
    if(null!=data && data instanceof FlightOptListSessionModel flightOptModel) {
	      fltOptSesModel=flightOptModel;
    }
    List<FlightSearchOption> flightOptList = fltOptSesModel.getFlightOption();
    
	GetAttConfirmBookingResponse attResponse = normalService.confirmATTAirBook(attAirBookModel,flightOptList);
	 session.removeAttribute("flightOptionList");
	if(attResponse!=null && attResponse.getErrorCode()==200) {
	GetAttBookInfoModel infoModel = attResponse.getResponse();
	List<GetAttFlightInfoModel> flightInfoList = infoModel.getFlightInfo();
	List<GetAttPassengerInfoModel> attPassengerInfo = infoModel.getPassengerInfo();
	Collections.sort(attPassengerInfo);
	Collections.sort(flightInfoList);
	infoModel.setFlightInfo(flightInfoList);
	model.addAttribute("bookInfo", infoModel);
	model.addAttribute("flightInfoList",flightInfoList);
	model.addAttribute("passInfoList",attPassengerInfo);
	model.addAttribute("paxInvoiceList", infoModel.getPaxInvoiceInfo());
	

	return "/MB/RequestDashbord/normalbookingdashboard/AttAfterConfirmBooking";
	}
	if(attResponse!=null) { 
	 model.addAttribute("errorMessage", attResponse.getErrorMessage());
	}else {
		 model.addAttribute("errorMessage", "");
	}
	 return "/MB/RequestDashbord/normalbookingdashboard/AirBookErrorPage";
}



// BL confirm
@PostMapping("/confirmBLAirBooking")
public String confirmBLBooking(PostConfimBLAirBookModel blAirBookModel,Model model,HttpServletRequest request ) {
	
	
	SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
   
	LoginUser loginUser = visitor.getLoginUser();
	if (loginUser == null) {
		return "redirect:/login";
	}
	blAirBookModel.setLoginUserId(loginUser.getUserId());
	
	HttpSession session = request.getSession();
	FlightOptListSessionModel fltOptSesModel = new FlightOptListSessionModel();
   Object data=session.getAttribute("flightOptionList");
   if(null!=data && data instanceof FlightOptListSessionModel flightOptModel) {
	      fltOptSesModel=flightOptModel;
   }
   List<FlightSearchOption> flightOptList = fltOptSesModel.getFlightOption();
   
      String sessionId = session.getId();
	  blAirBookModel.setSessionId(sessionId);
	GetBLConfirmBookResponse blResponse = normalService.confirmBLAirBook(blAirBookModel,flightOptList);
	 session.removeAttribute("flightOptionList");
	
	if(blResponse!=null && blResponse.getErrorCode()==200) {
		GetBLBookInfoModel infoModel = blResponse.getResponse();
		List<GetBLFlightInfoModel> blFlightInfo = infoModel.getFlightInfo();
		List<GetBLPassengerInfoModel> blPassengerInfo = infoModel.getPassengerInfo();
		Collections.sort(blPassengerInfo);
		Collections.sort(blFlightInfo);
		infoModel.setFlightInfo(blFlightInfo);
		model.addAttribute("blBookInfo", infoModel);
		model.addAttribute("flightInfoList",infoModel.getFlightInfo());
		model.addAttribute("passInfoList", blPassengerInfo);
		model.addAttribute("paxInvoiceList", infoModel.getPaxInvoiceInfo());
		
		
		return "/MB/RequestDashbord/normalbookingdashboard/BLAfterConfirmBooking";
	}
	if(blResponse!=null) { 
		 model.addAttribute("errorMessage", blResponse.getErrorMessage());
		}else {
			 model.addAttribute("errorMessage", "");
		}
	 return "/MB/RequestDashbord/normalbookingdashboard/AirBookErrorPage";

	
}

// print pdf

@PostMapping("/airPrintTicketPdf")
@ResponseBody
public void getAirPdf( @RequestParam int serviceProvider,@RequestParam String optTxnId,HttpServletRequest request,HttpServletResponse response) {
	
	 AirTicketPdfModel airTicketPdfModel  = emailTicketService.getAirTicketPdf(optTxnId);
	
	
	 response.setContentType("application/pdf");
	 response.setHeader("Content-Disposition", "attachment; filename=Air_"+optTxnId+".pdf");
	 if(airTicketPdfModel!=null) {
	 List<FlightInfo> flightInfoList = airTicketPdfModel.getFlightInfo();
	 List<PassengerInfo> passengerInfo = airTicketPdfModel.getPassengerInfo();
	 Collections.sort(passengerInfo);
	 Collections.sort(flightInfoList);
	 airTicketPdfModel.setFlightInfo(flightInfoList);
	 airTicketPdfModel.setPassengerInfo(passengerInfo);
	 }
	 try {
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
	if(serviceProvider == 0) {
		
		 emailTicketService.airBLTicketPDF(airTicketPdfModel,baos, "", false);
		
	}
	else if(serviceProvider == 1){
		
		emailTicketService.createAirATTicketPDF(airTicketPdfModel,baos,"", false);
	}
	   
	ServletOutputStream out  = response.getOutputStream();
	byte[] bytes = baos.toByteArray();
	
	out.write(bytes);
	out.flush();
        response.flushBuffer();
		
		
	
	 } catch (Exception e) {
			DODLog.printStackTrace(e, NormalBkgAirController.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
		}
	
}

// open agreement
@GetMapping("/getGovtOrder")
public String getGovtOrder(HttpServletRequest request ){
return "/MB/RequestDashbord/normalbookingdashboard/GovtOrder";
}

// open terms and condition
@GetMapping("/getUserAgreement")
public String getUserAgreement(HttpServletRequest request ){
return "/MB/RequestDashbord/normalbookingdashboard/userAgreement";
}





}
