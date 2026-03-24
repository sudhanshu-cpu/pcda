package com.pcda.mb.requestdashboard.railcancellationdashboard.controller;

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
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.GetAbortCancelResponse;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.GetParentFinalCancelReqModel;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.GetParentRailCanDashBkgData;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.GetRailCanDashBkgResponse;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.GetRailCanDashDataModel;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.GetRailCanDashDataResponse;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.GetRailDashCancelResponse;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.PostPreCancelRailDashModel;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.PostRailCanDashModel;
import com.pcda.mb.requestdashboard.railcancellationdashboard.model.PostRailDashCancelModel;
import com.pcda.mb.requestdashboard.railcancellationdashboard.service.RailCanDashservice;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class RailCanDashController {
	
@Autowired
private OfficesService offService;

@Autowired
private RailCanDashservice dashservice;  


@GetMapping("/getRailCanDashBoard")	
public String getRailCanDash(Model model,HttpServletRequest request) {
	SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = visitor.getLoginUser();
	if (loginUser == null) {
		return "redirect:/login";
	}
	PostRailCanDashModel dashModel= new PostRailCanDashModel();
	Optional<OfficeModel> office =  offService.getOfficeByUserId(loginUser.getUserId());
	if(office.isPresent()) {
		dashModel.setGroupId(office.get().getGroupId());
	}GetRailCanDashDataResponse response = dashservice.getRailCanDashData(dashModel);
	if(response!=null && response.getErrorCode()==200 && null!=response.getResponseList() && !response.getResponseList().isEmpty()) {
		List<GetRailCanDashDataModel> modelList = response.getResponseList();
		DODLog.info(LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE, RailCanDashController.class, "RAIL CAN DASH RESPONSE MODELLIST :: "+modelList.size());
		model.addAttribute("modelList", modelList);
	}else {
		model.addAttribute("noData", "NO Record Found!!");
	}
	
	model.addAttribute("formModel", new PostRailCanDashModel());
	return "/MB/RequestDashbord/railcandashboard/requestdashboard";
}

// FILTER SEARCH DATA
@PostMapping("/getRailCanDashData")
public String getRailCanDsh(PostRailCanDashModel dashModel, Model model,HttpServletRequest request) {
	SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = visitor.getLoginUser();
	if (loginUser == null) {
		return "redirect:/login";
	}
	
	String prsnlNo =  dashModel.getPersonalNo();
	String secret = "Hidden Pass";
	String decryptPersonalNo =  CommonUtil.getDecryptText(secret, prsnlNo);
	dashModel.setPersonalNo(decryptPersonalNo);
	
	Optional<OfficeModel> office =  offService.getOfficeByUserId(loginUser.getUserId());
	if(office.isPresent()) {
		dashModel.setGroupId(office.get().getGroupId());
		
	}
	
	GetRailCanDashDataResponse response = dashservice.getRailCanDashData(dashModel);
	if(response!=null && response.getErrorCode()==200 && null!=response.getResponseList() && !response.getResponseList().isEmpty()) {
		List<GetRailCanDashDataModel> modelList = response.getResponseList();
		DODLog.info(LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE, RailCanDashController.class, "RAIL CAN DASH RESPONSE MODELLIST :: "+modelList.size());
		model.addAttribute("modelList", modelList);
	}else {
		model.addAttribute("noData", "NO Record Found!!");
	}
	model.addAttribute("formModel", dashModel);
	return "/MB/RequestDashbord/railcandashboard/requestdashboard";
}

// GET DATA FROM BKG-ID
@RequestMapping(value="/getRailBkgIdData",method= {RequestMethod.GET,RequestMethod.POST})
public String getrailCanDashBkgData(Model model,HttpServletRequest request,@RequestParam(required=false,defaultValue = "") String bkgId) {
	SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = visitor.getLoginUser();
	if (loginUser == null) {
		return "redirect:/login";
	}
	
	GetRailCanDashBkgResponse response = dashservice.getBkgRailCanDashData(bkgId);
	if(response!=null && response.getErrorCode()==200 && response.getResponse()!=null) {
		GetParentRailCanDashBkgData bkgModel = response.getResponse();
		model.addAttribute("bkgModel", bkgModel);
		model.addAttribute("allCan", response.getAllCan());
		
	}else {
		model.addAttribute("errors", response==null?"": response.getErrorMessage());
		return "/common/errorPage";
	}
	
return "/MB/RequestDashbord/railcandashboard/requestdashboardbkgview";
}

//ABORT CANCEL
@PostMapping("abortRailCanDash")
public String abortRainCandash(Model model,@RequestParam String bookingIdAbort,@RequestParam String abortReason,HttpServletRequest request) {
	SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = visitor.getLoginUser();
	if (loginUser == null) {
		return "redirect:/login";
	}
	GetAbortCancelResponse response = dashservice.sendAbortCancel(bookingIdAbort,abortReason);
	if(response!=null && response.getErrorCode()==200) {
		return "redirect:getRailCanDashBoard";
	}else {
		model.addAttribute("errors", response==null?"": response.getErrorMessage());
		return "/common/errorPage";
	}
	
}

//PRE-CANCEL TKT
@PostMapping("/preCancelRailDashPage")
public String preCancelRailDashPage(Model model,PostPreCancelRailDashModel cancelDashModel,HttpServletRequest request) {
	SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = visitor.getLoginUser();
	if (loginUser == null) {
		return "redirect:/login";
	}
	
	GetRailCanDashBkgResponse response = dashservice.getBkgRailCanDashData(cancelDashModel.getBookingId());
	if(response!=null && response.getErrorCode()==200 && response.getResponse()!=null) {
		GetParentRailCanDashBkgData bkgModel = response.getResponse();
	
		String cancelString = dashservice.createCancelString(bkgModel.getPassengerList());
		cancelDashModel.setCancelString(cancelString);
		model.addAttribute("bkgModel", bkgModel);
		model.addAttribute("allCan", response.getAllCan());
	
	}else {
		model.addAttribute("noData", "NO CANCELLATION DATA FOUND!!");
	}
	model.addAttribute("canDashModel", cancelDashModel);
	return "/MB/RequestDashbord/railcandashboard/preRailCancelDashPage";
}

// CANCEL TKT
@PostMapping("/sendRailDashTktCancel")
public String sendRailDashTktCancel(Model model,PostRailDashCancelModel postCancelModel,HttpServletRequest request) {
	SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = visitor.getLoginUser();
	if (loginUser == null) {
		return "redirect:/login";
	}
	postCancelModel.setLoginUserId(loginUser.getUserId());
	GetRailDashCancelResponse response= dashservice.sendRailDashTktCancel(postCancelModel);
	if(response!=null && response.getErrorCode()==200) {
		GetParentFinalCancelReqModel finalModel = response.getResponse();
		model.addAttribute("finalModel", finalModel);
		return "/MB/RequestDashbord/railcandashboard/RailAfterFinalCancelView";
	}else {
		model.addAttribute("errors", response == null ? "":response.getErrorMessage()); 
		return "/common/errorPage";
	}
	
	
}

}
