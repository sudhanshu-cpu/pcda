package com.pcda.mb.requestdashboard.tdr.controller;

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

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.requestdashboard.tdr.model.GetParentTdrModel;
import com.pcda.mb.requestdashboard.tdr.model.GetTdrFinalProcessResponse;
import com.pcda.mb.requestdashboard.tdr.model.PostParenttdrModel;
import com.pcda.mb.requestdashboard.tdr.model.TDRViewModel;
import com.pcda.mb.requestdashboard.tdr.model.TdrViewResponse;
import com.pcda.mb.requestdashboard.tdr.service.TdrService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class TdrController {
	
@Autowired
private OfficesService officeService;
@Autowired
private TdrService tdrService; 

@GetMapping("/createTdr")
public String getTdrPage(Model model,HttpServletRequest request){
	
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();
	
	 if (loginUser == null) {
		return "redirect:/login";
	}

	model.addAttribute("pnrNo", "");
return "/MB/RequestDashbord/tdr/tdrpnr";	
}
	
@RequestMapping(value="/getPnrData",method= {RequestMethod.GET,RequestMethod.POST})
public String  getDataFrmPnr(Model model,HttpServletRequest request,@RequestParam(required = false)  String pnrNo) {
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();
	
	 if (loginUser == null) {
		return "redirect:/login";
	}
	Optional<OfficeModel> office =  officeService.getOfficeByUserId(loginUser.getUserId());
	
	model.addAttribute("pnrNo", pnrNo);
	String groupId="";
	if(office.isPresent()) {
	    groupId = office.get().getGroupId();
	}
			
	List<GetParentTdrModel> modelList=tdrService.getDataFrmPnr(pnrNo, groupId);

	if(modelList.isEmpty() && pnrNo!=null ) {
		model.addAttribute("eroor", "There is no content found in the list");
	}else {
	model.addAttribute("pnrData", modelList);
		
	}
	return "/MB/RequestDashbord/tdr/tdrpnr";
}

@PostMapping("/getDataFrmBkgId")
public String getDataFrmBkgId(Model model,@RequestParam String bookingId,HttpServletRequest request,RedirectAttributes redirectAttributes) {
	GetParentTdrModel parentModel = tdrService.getDataTktBkg(bookingId);
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();
	
	try {
	if(parentModel.getTdrFiledOrNot().equalsIgnoreCase("TdrNotFilled")){
		
		model.addAttribute("tktBkgData", parentModel);
		model.addAttribute("totalNoPassenger", parentModel.getPassengerList().size());
		model.addAttribute("tdrReasonList", tdrService.getTDRReason(loginUser.getAgentId(), parentModel.getTicketNo()));
		return "/MB/RequestDashbord/tdr/tdrbkgdata";
		}
	if(parentModel.getTdrFiledOrNot().equalsIgnoreCase("TdrFilled") && (parentModel.getIsTdrApproved().ordinal()==0 || parentModel.getIsTdrApproved().ordinal()==2)) {
		
		redirectAttributes.addAttribute("bookingId", bookingId);
		model.addAttribute("tktBkgData", parentModel);
		model.addAttribute("totalNoPassenger", parentModel.getPassengerList().size());
		model.addAttribute("tdrReasonList", tdrService.getTDRReason(loginUser.getAgentId(), parentModel.getTicketNo()));
		return "/MB/RequestDashbord/tdr/tdrEdit";
	}
	if(parentModel.getTdrFiledOrNot().equalsIgnoreCase("TdrFilled") &&parentModel.getIsTdrApproved().ordinal()==0 && parentModel.getIsTdrSuccess().ordinal()==1) {
		redirectAttributes.addAttribute("bookingId", bookingId);
		
		model.addAttribute("tktBkgData", parentModel);
		model.addAttribute("totalNoPassenger", parentModel.getPassengerList().size());
		model.addAttribute("tdrReasonList", tdrService.getTDRReason(loginUser.getAgentId(), parentModel.getTicketNo()));
		return "/MB/RequestDashbord/tdr/tdrEdit";
	}
	if(parentModel.getTdrFiledOrNot().equalsIgnoreCase("TdrFilled") &&parentModel.getIsTdrApproved().ordinal()==1 && parentModel.getIsTdrSuccess().ordinal()==1){
		
		model.addAttribute("tktBkgData", parentModel);
		model.addAttribute("totalNoPassenger", parentModel.getPassengerList().size());
		model.addAttribute("tdrReasonList", tdrService.getTDRReason(loginUser.getAgentId(), parentModel.getTicketNo()));
		return "/MB/RequestDashbord/tdr/tdrFinalProcess";
	}else {
		
		redirectAttributes.addAttribute("bookingId", bookingId);
		model.addAttribute("tktBkgViewData", parentModel);
		return "/MB/RequestDashbord/tdr/tdrView";
	}
	}catch (Exception e) {
		DODLog.printStackTrace(e, TdrController.class, LogConstant.TDR_LOG_FILE);
	}
	return "";
}

@PostMapping("/saveTdr")
public String saveTdr(Model model,PostParenttdrModel parentTdrModel ,HttpServletRequest request) {
	
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();
	
	 if (loginUser == null) {
		return "redirect:/login";
	}
	 parentTdrModel.setLoginUserId(loginUser.getUserId());
	 TdrViewResponse tdrViewResponse =	tdrService.saveTdr(parentTdrModel, request);
	if(tdrViewResponse!=null && tdrViewResponse.getErrorCode()==200) {
		 List<TDRViewModel> modelList =tdrViewResponse.getResponseList();
	model.addAttribute("viewData", modelList);
	return "/MB/RequestDashbord/tdr/tdrSave";
}else {
	model.addAttribute("errors", tdrViewResponse==null ? "" : tdrViewResponse.getErrorMessage());
	return "/common/errorPage";
}

}

	
@PostMapping("/tdrFinalProcessForm")
public String tdrFinalProcessForm(Model model ,@RequestParam String bookingId,HttpServletRequest request) {
	GetTdrFinalProcessResponse response =tdrService.sendTdrFinalProcess(bookingId);
	if(response!=null && response.getErrorCode()==200) {
		return "";
	}else {
		model.addAttribute("errors", response ==null ? "": response.getErrorMessage());
		return "/common/errorPage";
	}
	

}

}
