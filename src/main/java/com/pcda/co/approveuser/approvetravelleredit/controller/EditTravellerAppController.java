package com.pcda.co.approveuser.approvetravelleredit.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.co.approveuser.approvetravelleredit.model.ApprovalEditModel;
import com.pcda.co.approveuser.approvetravelleredit.model.EditFamiltDtls;
import com.pcda.co.approveuser.approvetravelleredit.model.ProfileChangeDetails;
import com.pcda.co.approveuser.approvetravelleredit.model.TravellerEditReq;
import com.pcda.co.approveuser.approvetravelleredit.service.EditTravellerAppService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class EditTravellerAppController {

	private String path = "CO/ApproveUser/ApproveEditProfile/";

	@Autowired
	private EditTravellerAppService editTravellerAppService;

	@GetMapping("/editProfileApprReq")
	public String editProfileApprReq(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		Boolean isError = false;

		if (loginUser == null) {
			return "redirect:/login";
		}

		List<TravellerEditReq> userList = editTravellerAppService.getAllUserProfileForApproval(loginUser.getUserId());
		isError = userList == null || userList.isEmpty();
		if (Boolean.TRUE.equals(isError)) {
			model.addAttribute("errorMessage", "No results Found");
		}

		model.addAttribute("isError", isError);
		model.addAttribute("profileList", userList);

		return path + "editProfileApp";
	}

	@PostMapping("/viewEditUser")
	public String viewEditUser(@RequestParam String userAlias, Model model, HttpServletRequest request) {
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		
		 String loginUnitServiceId=loginUser.getServiceId();
	
		 
		Map<String, List<ProfileChangeDetails>>  profileMap = editTravellerAppService.getViewUser(userAlias);
		
		List<ProfileChangeDetails> familyDtlsList=profileMap.get("ProfileAuditDetailsAdd");
		List<EditFamiltDtls> fmDtlsList = new ArrayList<>();
		
		editTravellerAppService.setFamilyDtlsfrmAttr(familyDtlsList, fmDtlsList);
			 
		
		
		model.addAttribute("ProfileAuditDetails", profileMap.get("ProfileAuditDetails"));
		model.addAttribute("ProfileAuditDetailsEdit", profileMap.get("ProfileAuditDetailsEdit"));
        model.addAttribute("ProfileAuditDetailsAdd", fmDtlsList);
		model.addAttribute("ProfileAuditDetailsDelete", profileMap.get("ProfileAuditDetailsDelete"));
		model.addAttribute("ProfileAuditDetailsOnline", profileMap.get("ProfileAuditDetailsOnline"));
		model.addAttribute("UserServiceId",loginUnitServiceId);
		return path + "viewEditUser";
	}

	@PostMapping("/editProfileApp")
	public String editProfileApp(@ModelAttribute ApprovalEditModel appEditModel, RedirectAttributes attributes, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}

		appEditModel.setLoginUserId(loginUser.getUserId());
		
		editTravellerAppService.updateEditProfileApp(appEditModel);

		return "redirect:editProfileApprReq";
	}

}
