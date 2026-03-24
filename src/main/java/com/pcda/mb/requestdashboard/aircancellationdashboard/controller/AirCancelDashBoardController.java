package com.pcda.mb.requestdashboard.aircancellationdashboard.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.requestdashboard.aircancellationdashboard.model.GetAirCanDashAbortResponse;
import com.pcda.mb.requestdashboard.aircancellationdashboard.model.GetAirCanDashBkgIdResponse;
import com.pcda.mb.requestdashboard.aircancellationdashboard.model.GetAirCanDashDataResponse;
import com.pcda.mb.requestdashboard.aircancellationdashboard.model.GetAirCanDashParentModel;
import com.pcda.mb.requestdashboard.aircancellationdashboard.model.GetAirDashBkgIdParentModel;
import com.pcda.mb.requestdashboard.aircancellationdashboard.model.GetAirDashPassangerDetail;
import com.pcda.mb.requestdashboard.aircancellationdashboard.model.GetAirDashTktCancelResponse;
import com.pcda.mb.requestdashboard.aircancellationdashboard.model.PostAirCanTktModel;
import com.pcda.mb.requestdashboard.aircancellationdashboard.model.PostAirDashAbortCanModel;
import com.pcda.mb.requestdashboard.aircancellationdashboard.service.AirCancelDashBoardService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class AirCancelDashBoardController {

@Autowired
private AirCancelDashBoardService airCanService;
@Autowired
private OfficesService officesService;

// GET ALL REQUERST FOR CANCELLATION
@GetMapping("/getAirCanDashPage")
public String getAirCanDashPage(Model model,HttpServletRequest request) {
	SessionVisitor sessionVisitor=SessionVisitor.getInstance(request.getSession()); 
	LoginUser loginUser=sessionVisitor.getLoginUser();
	
	if(loginUser==null) {
		return "redirect:/login";
	}
	Optional<OfficeModel> officeModel=officesService.getOfficeByUserId(loginUser.getUserId());
     String groupId="";
	if(officeModel.isPresent()) {
		groupId=officeModel.get().getGroupId();
	}
	
	GetAirCanDashDataResponse response = airCanService.getAirCanDashData(groupId);
	if(response!=null && response.getErrorCode()==200) {
		List<GetAirCanDashParentModel> modelList = response.getResponseList();
		model.addAttribute("modelList", modelList);
	}
	
	return "/MB/RequestDashbord/aircancellationdashboard/AirCancellationDashboard";
}

// GET TKT DTLS FROM BKG-ID
@PostMapping("/getAirDashDataFrmBkgId")
public String getAirDashDataFrmBkgId(Model model,@RequestParam String bookingId,HttpServletRequest request) {
	GetAirCanDashBkgIdResponse response =  airCanService.getAirDashDataFrmBkgId(bookingId);
	if(response!=null && response.getErrorCode()==200) {
		GetAirDashBkgIdParentModel parentModel = response.getResponse();
		List<GetAirDashPassangerDetail> passDtls = new ArrayList<>();
		passDtls=parentModel.getPassangerDetail();
		Collections.sort(passDtls);
		parentModel.setPassangerDetail(passDtls);
		
		model.addAttribute("ModelData", parentModel);
		return "/MB/RequestDashbord/aircancellationdashboard/AirCanDashDataFrmBkgId";
	}else {
		return "common/errorPage";
	}
	
	
}

// CANCEL AIR CAN DASK TKT

@PostMapping("/sendAirDashTktCancel")
public String sendAirDashTktCancel(Model model,PostAirCanTktModel postCanModel,HttpServletRequest request ) {
	SessionVisitor sessionVisitor=SessionVisitor.getInstance(request.getSession()); 
	LoginUser loginUser=sessionVisitor.getLoginUser();
	
	if(loginUser==null) {
		return "redirect:/login";
	}
	postCanModel.setLoginUserId(loginUser.getUserId());
	GetAirDashTktCancelResponse response = airCanService.sendAirDashTktCancel(postCanModel);
	if(response!=null) {
		model.addAttribute("message", response.getResponse());
	}
	return "/MB/RequestDashbord/aircancellationdashboard/AirCancellationSuccess";
}

// ABORT CANCELLATION

@PostMapping("/abortAirDashCancel")
public String sendReqForAbort(PostAirDashAbortCanModel postModel,HttpServletRequest request){
	SessionVisitor sessionVisitor=SessionVisitor.getInstance(request.getSession()); 
	LoginUser loginUser=sessionVisitor.getLoginUser();
	
	if(loginUser==null) {
		return "redirect:/login";
	}
	postModel.setLoginUserId(loginUser.getUserId());
	postModel.setBookingId(postModel.getBookingIdAbort());
	GetAirCanDashAbortResponse response = airCanService.sendAbortCanRequest(postModel);
	if(response!=null && response.getErrorCode()==200) {
		return "redirect:getAirCanDashPage";
	}
	else {
		return "common/errorPage";
	}
}


}
