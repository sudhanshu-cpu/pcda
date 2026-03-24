package com.pcda.mb.grievance.complaintStatus.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.grievance.complaintStatus.model.GrievanceStatusViewBean;
import com.pcda.mb.grievance.complaintStatus.model.GrievanceViewBean;
import com.pcda.mb.grievance.complaintStatus.service.GrievanceStatusService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/mb")

public class complaintStatusController {

	private String pageURL = "/MB/Grievance/complaintStatus/";
	
	@Autowired
	private GrievanceStatusService grievanceStatusService;
	
	@Autowired
	private OfficesService officesService;
	
	@GetMapping("/complaintStatus")
	public String GetComplaintStatus(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		return pageURL + "complaintStatus";
	}
	
	
	
	@GetMapping("/getComplaintStatus")
	public String getComplaintStatus(@RequestParam (required = false)String complaint,@RequestParam (required = false) Boolean flag, Model model, RedirectAttributes attributes, HttpServletRequest request) {
		
		String groupId="";
		SessionVisitor sessionVisitor=SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser=sessionVisitor.getLoginUser();
		Optional<OfficeModel> office =  officesService.getOfficeByUserId(loginUser.getUserId());
		if(office.isPresent()) {
			 groupId=office.get().getGroupId();
		
		}		
		GrievanceViewBean allData = grievanceStatusService.getAllData(complaint,groupId);
		List<GrievanceStatusViewBean> bean=allData.getGrievanceDetails();
		DODLog.info(LogConstant.COMMON_LOG, complaintStatusController.class, "bean Data ::: " + bean);	
		if(bean!=null && !bean.isEmpty()) {	
			model.addAttribute("beanList", bean);
		}
		else {
			model.addAttribute("error", "No Record Found!!!");
		}
		model.addAttribute("complaint", complaint);
		model.addAttribute("flag",flag);
		
		HttpSession session = request.getSession();
		String personalNo = (String) session.getAttribute("personalNo");  
	    model.addAttribute("personalNo", personalNo);                     
		return pageURL + "complaintStatus";
	}
	
	//get the suggetion search result
	@GetMapping("/getSuggestionData")
	public ResponseEntity<List<String>> getSuggestion(@RequestParam String complaintId,Model model) {
		DODLog.info(LogConstant.COMMON_LOG, complaintStatusController.class, "complaintId suggestion ::: " + complaintId);
		return ResponseEntity.ok(grievanceStatusService.getSuggestionData(complaintId));
	
	}
	

	
	
	
}
