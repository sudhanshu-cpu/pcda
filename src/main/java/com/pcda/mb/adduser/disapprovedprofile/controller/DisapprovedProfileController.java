package com.pcda.mb.adduser.disapprovedprofile.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.disapprovedprofile.model.DisProfileReqModel;
import com.pcda.mb.adduser.disapprovedprofile.model.DisapproveDTO;
import com.pcda.mb.adduser.disapprovedprofile.model.DisapprovedProfileResponse;
import com.pcda.mb.adduser.disapprovedprofile.model.EditDisapproveProfile;
import com.pcda.mb.adduser.disapprovedprofile.service.DisapprovedProfileService;
import com.pcda.mb.adduser.travelerprofile.service.TravelerProfileService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.ServiceType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mb")
public class DisapprovedProfileController {

	@Autowired
	private TravelerProfileService travelerProfileService;

	@Autowired
	private DisapprovedProfileService disapprovedProfileService;

	private String url = "/MB/AddUsers/DisapprovedProfile";

	private String relation = "RELATION_TYPE";
	private String marStatus = "MARITAL_STATUS";
	private String gender = "GENDER";

	@GetMapping("/disapprovedProfile")
	public String disapprovedProfile(HttpServletRequest request, Model model) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		Optional<OfficeModel> office = disapprovedProfileService.getOfficesByUserId(loginUser.getUserId());
		String groupId = "";
		String officeName="";
		if (office.isPresent()) {
			groupId = office.get().getGroupId();
			officeName=office.get().getName();
		}
		
		
		model.addAttribute("officeName", officeName);
		model.addAttribute("groupId", groupId);
		
		model.addAttribute("disapprovedProfile", disapprovedProfileService.getDisappUsers(groupId));
		return url + "/disapprovedProfile";
	}

	@RequestMapping(value = "/viewDisapproved", method = { RequestMethod.GET, RequestMethod.POST })
	public String viewDisapproved(@RequestParam String personalNo,@RequestParam String officeId, Model model, HttpServletRequest request) {

		DisapprovedProfileResponse response = disapprovedProfileService.getTraveler(personalNo, officeId);
		DODLog.info(LogConstant.DISAPPROVE_PROFILE_LOG, DisapprovedProfileController.class, "[viewDisapproved]:::::DisapprovedProfileResponse:::::: " + response);
		if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
			DisProfileReqModel profileReq = response.getResponse();

			if (Boolean.FALSE.equals(profileReq.getCivilianService())) {
				profileReq.setServiceType(ServiceType.ARMED_FORCES);
			} else {
				profileReq.setServiceType(ServiceType.CIVILIAN);
			}

			model.addAttribute("travelerReq", profileReq);
			return url + "/viewDisapproved";
		} else if (response != null){
			model.addAttribute("error", response.getErrorMessage());
		} else {
			model.addAttribute("error", "Unable to Get User");
		}

		return url + "/disapprovedProfile";
	}

	@PostMapping("/editDisapproved")
	public String editTraveler(@RequestParam String personalNo,@RequestParam String officeId, Model model, HttpServletRequest request, RedirectAttributes attributes) {
		
		DODLog.info(LogConstant.DISAPPROVE_PROFILE_LOG, DisapprovedProfileController.class, "[editTraveler]: INSIDE DISAPPROVED EDIT PNO.:: " + personalNo+" | officeId::"+officeId);
		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();

			if (loginUser == null) {
				return "redirect:/login";
			}

			DisapprovedProfileResponse reqResponse = disapprovedProfileService.getDisapprovedProfile(personalNo, officeId);
			DODLog.info(LogConstant.DISAPPROVE_PROFILE_LOG, DisapprovedProfileController.class, "[editTraveler]:::::INSIDE DISAPPROVED reqResponse.:::::: " + reqResponse);
			if (reqResponse != null && reqResponse.getErrorCode() == 200 && reqResponse.getResponse() != null) {
				DisProfileReqModel travelerReqModel = reqResponse.getResponse();
				if(travelerReqModel.getErrorMessage() != null && !travelerReqModel.getErrorMessage().isBlank()) {
					attributes.addFlashAttribute("error", travelerReqModel.getErrorMessage());
					return "redirect:disapprovedProfile";
				}


				if (Boolean.FALSE.equals(travelerReqModel.getCivilianService())) {
					travelerReqModel.setServiceType(ServiceType.ARMED_FORCES);
				} else {
					travelerReqModel.setServiceType(ServiceType.CIVILIAN);
				}

				model.addAttribute("travelerReq", travelerReqModel);

				model.addAttribute("loginUserId", loginUser.getUserId());

				model.addAttribute("enumRelation", travelerProfileService.getEnumAsString(relation));
				model.addAttribute("enumMaritalStatus", travelerProfileService.getEnumAsString(marStatus));
				model.addAttribute("enumGender", travelerProfileService.getEnumAsString(gender));

				model.addAttribute("familyDetailsNo", travelerReqModel.getFamilyDetails().size());

				return url + "/editDisappProfile";
			} else if (reqResponse != null){
				attributes.addFlashAttribute("error", reqResponse.getErrorMessage());
			} else {
				attributes.addFlashAttribute("error", "Unable to Get User");
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, DisapprovedProfileController.class, LogConstant.DISAPPROVE_PROFILE_LOG);
			attributes.addFlashAttribute("error", "Error Occurred");
		}
		return "redirect:disapprovedProfile";
	}

	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute @Valid DisapproveDTO travelerProfileDTO,BindingResult result , Model model, RedirectAttributes attributes,
			HttpServletRequest request) {
		String errors = "error";
		
		try {
			
			if (result.hasErrors()) {
				DODLog.error(LogConstant.DISAPPROVE_PROFILE_LOG, DisapprovedProfileController.class, "Error::::: " + result.getAllErrors());
				ObjectError objectError = result.getAllErrors().get(0);
				attributes.addFlashAttribute(errors, objectError.getDefaultMessage());
				return "redirect:disapprovedProfile";
			} else {
				EditDisapproveProfile editTravelerUser = disapprovedProfileService.initProfile(travelerProfileDTO,request);
				
				if (editTravelerUser == null) {
					
					attributes.addFlashAttribute(errors, "Error while initialize Profile");
				} else {
					
					Map.Entry<Boolean, String> entry = disapprovedProfileService.updateProfile(editTravelerUser).entrySet().iterator().next();
					if (Boolean.TRUE.equals(entry.getKey())) {
						
						attributes.addFlashAttribute(errors, "Profile Updated");
					} else {
						
						attributes.addFlashAttribute(errors, entry.getValue());
					}
				}
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, DisapprovedProfileController.class, LogConstant.DISAPPROVE_PROFILE_LOG);
			attributes.addFlashAttribute(errors, "Error Occurred");
		}
		return "redirect:disapprovedProfile";
	}

}
