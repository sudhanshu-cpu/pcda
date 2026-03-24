package com.pcda.mb.grievance.complaintHistory.controller;

import java.util.Comparator;
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
import com.pcda.mb.grievance.complaintHistory.model.ComplaintHistoryResponse;
import com.pcda.mb.grievance.complaintHistory.model.GrievanceHistoryView;
import com.pcda.mb.grievance.complaintHistory.model.GrievanceMainBean;
import com.pcda.mb.grievance.complaintHistory.model.GrievancePostBean;
import com.pcda.mb.grievance.complaintHistory.service.ComplaintHistoryService;
import com.pcda.mb.grievance.complaintStatus.model.GrievanceStatusViewBean;
import com.pcda.mb.grievance.complaintStatus.model.GrievanceViewBean;
import com.pcda.mb.grievance.complaintStatus.service.GrievanceStatusService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mb")

public class complaintHistoryController {

	@Autowired
	private ComplaintHistoryService complaintHistoryService;
	
	@Autowired
	private OfficesService officesService;
	
	@Autowired
	private GrievanceStatusService grievanceStatusService;

	private String pageURL = "/MB/Grievance/complaintHistory/";

	@GetMapping("/complaintHistory")
	public String GetComplaintHistory(GrievancePostBean grievancePostBean,Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		String groupId = "";
		Optional<OfficeModel> office = officesService.getOfficeByUserId(loginUser.getUserId());
		if (office.isPresent()) {
			groupId = office.get().getGroupId();
		}
		 grievancePostBean.setGroupId(groupId);
			ComplaintHistoryResponse response = complaintHistoryService.getComplaintHistory(grievancePostBean);
			if(response!=null && response.getErrorCode()==200 && response.getResponse()!=null) {
				
	        	GrievanceMainBean mainBean = response.getResponse();
	        	mainBean.setGrievanceViewBean(mainBean.getGrievanceViewBean().stream().sorted(Comparator.comparing(GrievanceHistoryView::getCreationTime).reversed()).toList());
	        	
	        	model.addAttribute("complaintHistoryList",mainBean.getGrievanceViewBean());
	        }
			else {
				model.addAttribute("message","No Record Found!!");
			}
			
		return pageURL + "dashboardStatus";
	}

	
	@PostMapping("/viewComplaintHistory")	
	public String getComplaintHistory(GrievancePostBean grievancePostBean, Model model,HttpServletRequest request) {
		
		 SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		    LoginUser loginUser = sessionVisitor.getLoginUser();
		    String groupId = "";
		    Optional<OfficeModel> office = officesService.getOfficeByUserId(loginUser.getUserId());
		    if (office.isPresent()) {
		        groupId = office.get().getGroupId();
		    }
		    grievancePostBean.setGroupId(groupId);
		ComplaintHistoryResponse response = complaintHistoryService.getComplaintHistory(grievancePostBean);
		
		if(response!=null && response.getErrorCode()==200 && response.getResponse()!=null) {
			
        	GrievanceMainBean mainBean = response.getResponse();
        	mainBean.setGrievanceViewBean(mainBean.getGrievanceViewBean().stream().sorted(Comparator.comparing(GrievanceHistoryView::getCreationTime).reversed()).toList());
        	
        	model.addAttribute("complaintHistoryList",mainBean.getGrievanceViewBean());
        }
		else if(response.getErrorMessage()!=null){
			model.addAttribute("message",response.getErrorMessage());
		}else {
			model.addAttribute("message","No Record Found!!");
		}
		model.addAttribute("personalNo",grievancePostBean.getPersonalNo());
		model.addAttribute("grievanceId",grievancePostBean.getGrievanceId());
		
		HttpSession session = request.getSession();
		session.setAttribute("personalNo", grievancePostBean.getPersonalNo());
		
		return pageURL + "dashboardStatus";
	}
	
	
	// POP-CONTROLLER
	@PostMapping("/grievanceStatusView")
	public String grievanceStatusView(@RequestParam("grievanceId") String grievanceId, Model model,HttpServletRequest request) {

	    SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	    LoginUser loginUser = sessionVisitor.getLoginUser();
	    String groupId = "";
	    Optional<OfficeModel> office = officesService.getOfficeByUserId(loginUser.getUserId());
	    if (office.isPresent()) {
	        groupId = office.get().getGroupId();
	    }
	    GrievanceViewBean allData = grievanceStatusService.getAllData(grievanceId, groupId);
	    List<GrievanceStatusViewBean> bean=allData.getGrievanceDetails();

		if(bean!=null && !bean.isEmpty()) {	
			model.addAttribute("beanList", bean);
		}
		else {
			model.addAttribute("error", "No Record Found!!!");
		}
		model.addAttribute("complaint", grievanceId);

	    return pageURL +"complaintStatusPopup"; 
	}




}
