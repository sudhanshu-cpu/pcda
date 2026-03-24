package com.pcda.mb.requestdashboard.offlinecancellationdashboard.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.requestdashboard.offlinecancellationdashboard.model.GetOfflineCanDataModel;
import com.pcda.mb.requestdashboard.offlinecancellationdashboard.model.GetOfflineCancelResponse;
import com.pcda.mb.requestdashboard.offlinecancellationdashboard.model.PostsendOffCancelModel;
import com.pcda.mb.requestdashboard.offlinecancellationdashboard.service.OfflineCancelDashboardService;
import com.pcda.mb.requestdashboard.viarequestlegrebooking.model.ViaRequestLegReBookingViewModel;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class OfflineCancelDashboardController {
	
	@Autowired
	private OfflineCancelDashboardService offlineCancelDashboardService;
	
	private String pageUrl="/MB/RequestDashbord/OfflineCancelDashboard/OfflineCancelDashboard";
	
	
	@GetMapping("/offlineCancelDashboard")
	public String offlineCancelDashboard(HttpServletRequest request,Model model) {
		
		SessionVisitor sessionVisitor=SessionVisitor.getInstance(request.getSession()); 
		LoginUser loginUser=sessionVisitor.getLoginUser();
		
		if(loginUser==null) {
			return "redirect:/login";
		}
		Optional<OfficeModel> officeModel=offlineCancelDashboardService.getGroupIdByUserId(loginUser.getUserId());
	String 	groupId="";
		if(officeModel.isPresent()) {
			groupId=officeModel.get().getGroupId();
		}
		model.addAttribute("groupId", groupId);
		 List<GetOfflineCanDataModel> getDataUserList=offlineCancelDashboardService.getDataUserList(groupId);
		 
		if(!getDataUserList.isEmpty()) {
		model.addAttribute("dataUserList", getDataUserList);
		}else {
			model.addAttribute("errorMessage","No Record Found !!");
		}
		
		return pageUrl;
	}

	// REQUEST ID POP-UP
	@PostMapping("/getOfflineCancelDashboardView")
	public String getViaRequestLegReBookingView(@RequestParam String requestId, Model model) {
		ViaRequestLegReBookingViewModel getViewOfflineCanclation = offlineCancelDashboardService.getViewOfflineCanclation(requestId);
		model.addAttribute("reqIdModel", getViewOfflineCanclation);
		return "/MB/RequestDashbord/OfflineCancelDashboard/offlineCancelationView";
	}
	
	//PERSONAL NO. POP-UP 
		@PostMapping("/userInformationOfflineCan")
		public String appChangeService1(@RequestParam String personalNo,@RequestParam String groupId, Model model, HttpServletRequest request) {

			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();

			if (loginUser == null) {
				return "redirect:/login";
			}
			model.addAttribute("userList", offlineCancelDashboardService.getViewOfflineCanclationPersonal(personalNo, groupId));
			return "/MB/RequestDashbord/OfflineCancelDashboard/offlineCancelaPersonalView";
		}
		
// send for cancellation
	@RequestMapping("/sendForCancelReq")	
	public String sendForCancelReq(PostsendOffCancelModel postCancelModel,Model model,HttpServletRequest request ) {
		SessionVisitor sessionVisitor=SessionVisitor.getInstance(request.getSession()); 
		LoginUser loginUser=sessionVisitor.getLoginUser();
		
		if(loginUser==null) {
			return "redirect:/login";
		}
         String reqId=Base64Coder.decodeString(postCancelModel.getRequestId());
		postCancelModel.setRequestId(reqId);
		 postCancelModel.setCancelReqType("0");
		GetOfflineCancelResponse response = offlineCancelDashboardService.sendReqForCancel(postCancelModel);
		 if(response!=null && response.getErrorCode()==200) {
			 return "redirect:offlineCancelDashboard";
		 }else {
			 model.addAttribute("errors",response==null ? "" : response.getErrorMessage());
		 }
		 return "/common/errorPage";
	}
}
