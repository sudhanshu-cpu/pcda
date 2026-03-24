package com.pcda.mb.requestdashboard.exceptionalbookingdashboard.controller;

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
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.reports.userreports.model.GetTravllerProfileModel;
import com.pcda.mb.reports.userreports.model.TravellerProfileResponseModel;
import com.pcda.mb.requestdashboard.exceptionalbookingdashboard.model.GetExcptnlDataParentModel;
import com.pcda.mb.requestdashboard.exceptionalbookingdashboard.model.GetExcptnlDataResponse;
import com.pcda.mb.requestdashboard.exceptionalbookingdashboard.service.ExceptionalBkgDashService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class ExceptionalBkgDashController {

	@Autowired
	private OfficesService offService;	
	
	@Autowired
	private ExceptionalBkgDashService excepService;
	
@GetMapping("getExceptionalBkgPage")	
public String getExceptionalBkgPage(Model model,HttpServletRequest request) {
	
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
	model.addAttribute("pNoList", excepService.getUniquePersonalNo(groupId));
	model.addAttribute("pno", "");
	model.addAttribute("req", "");
	
	return "/MB/RequestDashbord/ExceptionalBookingDashBoard/ExceptionalBookData";
}

//data after search based on personal no and requestMode
@RequestMapping(value="/getExceptionalBkgData",method= {RequestMethod.GET,RequestMethod.POST})
public String getNormalSearchData(Model model,@RequestParam(required=false,defaultValue = "All") String personalNo,
		@RequestParam(required=false,defaultValue = "All") String requestMode,HttpServletRequest request) {
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
	model.addAttribute("pNoList", excepService.getUniquePersonalNo(groupId));
	 
	model.addAttribute("pno", personalNo);
	model.addAttribute("req",requestMode);
	GetExcptnlDataResponse	response = excepService.getExceptionalData(personalNo, requestMode, groupId);
	if(response!=null && response.getErrorCode()==200 && !response.getResponseList().isEmpty()) {
		List<GetExcptnlDataParentModel> modelList = response.getResponseList();
		model.addAttribute("modelList",modelList);
	}else {
		model.addAttribute("noData", "No Result Found!!");
	}
	
	return "/MB/RequestDashbord/ExceptionalBookingDashBoard/ExceptionalBookData";
}


@PostMapping("/getExceptionalPersonalNoView")
public String getExceptionalPersonalNoView(@RequestParam String personalNo,Model model, HttpServletRequest request) {

	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();

	if (loginUser == null) {
		return "redirect:/login";
	}
	GetTravllerProfileModel trvellerModel = new GetTravllerProfileModel();
	TravellerProfileResponseModel response =  excepService.getViewPersonal(personalNo);
	
	if(response!=null && response.getErrorCode()==200) {
		trvellerModel=response.getResponse();
		trvellerModel.setUserAlias(trvellerModel.getSignInID());
	}
	

	model.addAttribute("userList",trvellerModel);
	
	return "/MB/RequestDashbord/ExceptionalBookingDashBoard/ExceptionalPersonalPopUp";
}

}
