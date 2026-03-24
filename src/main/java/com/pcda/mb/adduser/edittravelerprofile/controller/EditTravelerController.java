package com.pcda.mb.adduser.edittravelerprofile.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.Category;
import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.edittravelerprofile.model.DependantDtls;
import com.pcda.mb.adduser.edittravelerprofile.model.EditTravelerDTO;
import com.pcda.mb.adduser.edittravelerprofile.model.EditTravelerUser;
import com.pcda.mb.adduser.edittravelerprofile.model.LevelInfo;
import com.pcda.mb.adduser.edittravelerprofile.model.TravelerReqModel;
import com.pcda.mb.adduser.edittravelerprofile.model.TravelerReqResponse;
import com.pcda.mb.adduser.edittravelerprofile.service.EditTravelerService;
import com.pcda.mb.adduser.travelerprofile.service.TravelerProfileService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.ServiceType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mb")
public class EditTravelerController {

	@Autowired
	private TravelerProfileService travelerProfileService;

	@Autowired
	private EditTravelerService editTravelerService;

	private String path = "MB/AddUsers/EditTravelerProfile/";

	private String travelerReq = "travelerReq";

	private String relation = "RELATION_TYPE";
	private String marStatus = "MARITAL_STATUS";
	private String gender = "GENDER";

	@RequestMapping(value = "/searchTraveler", method = {RequestMethod.GET, RequestMethod.POST})
	public String searchTraveler(Model model, HttpServletRequest request, @RequestParam(required = false, defaultValue = "") String personalNo) {
		String error = "error";
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, personalNo);
		if (!decryptPersonalNo.isBlank()) {
			Optional<OfficeModel> optionalOffice = editTravelerService.getOfficeByUserId(loginUser.getUserId());
			OfficeModel officeModel = null;

			if (optionalOffice.isPresent()) {
				officeModel = optionalOffice.get();
			}

			if (officeModel != null) {
				TravelerReqResponse reqResponse = editTravelerService.getTravelerByPersonalnoAndOfficeId(decryptPersonalNo, officeModel.getGroupId());
				if (reqResponse != null && reqResponse.getErrorCode() == 200 && reqResponse.getResponse() != null) {
					model.addAttribute(travelerReq, reqResponse.getResponse());
					model.addAttribute("personalNo", decryptPersonalNo);
				} else if (reqResponse != null && reqResponse.getErrorMessage() != null){
					model.addAttribute(error, reqResponse.getErrorMessage());
					model.addAttribute("personalNo", decryptPersonalNo);
				} else {
					model.addAttribute(error, "Unable to Get User");
					model.addAttribute("personalNo", decryptPersonalNo);
				}

			}
		}

		return path + "searchPersonalNo";
	}

	@PostMapping("/viewTraveler")
	public String viewTraveler(@RequestParam String personalNo, Model model, HttpServletRequest request, RedirectAttributes attributes) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, personalNo);
		Optional<OfficeModel> optionalOffice = editTravelerService.getOfficeByUserId(loginUser.getUserId());

		OfficeModel officeModel = null;
		String officeId = "";

		if (optionalOffice.isPresent()) {
			officeModel = optionalOffice.get();
			officeId = officeModel.getGroupId();
		}

		TravelerReqResponse reqResponse = editTravelerService.getTraveler(decryptPersonalNo, officeId);
		if (reqResponse != null && reqResponse.getErrorCode() == 200 && reqResponse.getResponse() != null) {
			TravelerReqModel travelerReqModel = reqResponse.getResponse();

			if(travelerReqModel.getErrorMessage() != null && !travelerReqModel.getErrorMessage().isBlank()) {
				attributes.addFlashAttribute("error", travelerReqModel.getErrorMessage());
				return "redirect:searchTraveler";
			}

			if (Boolean.FALSE.equals(travelerReqModel.getCivilianService())) {
				travelerReqModel.setServiceType(ServiceType.ARMED_FORCES);
			} else {
				travelerReqModel.setServiceType(ServiceType.CIVILIAN);
			}

			model.addAttribute(travelerReq, travelerReqModel);
			model.addAttribute("personalNo", decryptPersonalNo);
			return path + "viewTraveler";
		} else if (reqResponse != null){
			model.addAttribute("error", reqResponse.getErrorMessage());
		} else {
			model.addAttribute("error", "Unable to Get User");
		}

		return path + "searchPersonalNo";
	}

	@PostMapping("/editTraveler")
	public String editTraveler(@RequestParam String personalNo, Model model, HttpServletRequest request, RedirectAttributes attributes) {
		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();

			if (loginUser == null) {
				return "redirect:/login";
			}

			String secret = "Hidden Pass";
			String decryptPersonalNo =  CommonUtil.getDecryptText(secret, personalNo);
			Optional<OfficeModel> optionalOffice = editTravelerService.getOfficeByUserId(loginUser.getUserId());

			OfficeModel officeModel = null;
			String officeId = "";

			if (optionalOffice.isPresent()) {
				officeModel = optionalOffice.get();
				officeId = officeModel.getGroupId();
			}

			TravelerReqResponse reqResponse = editTravelerService.getTravelerDetails(decryptPersonalNo, officeId);
			if (reqResponse != null && reqResponse.getErrorCode() == 200 && reqResponse.getResponse() != null) {
				TravelerReqModel travelerReqModel = reqResponse.getResponse();

				if(travelerReqModel.getErrorMessage() != null && !travelerReqModel.getErrorMessage().isBlank()) {
					attributes.addFlashAttribute("error", travelerReqModel.getErrorMessage());
					return "redirect:searchTraveler";
				}

 
				  setServiceType(travelerReqModel);
 
				
				 if(travelerReqModel.getFamilyDetails() !=null && ! travelerReqModel.getFamilyDetails().isEmpty()) {
				
				List<DependantDtls>  dependentDetails = travelerReqModel.getFamilyDetails();
				 Collections.sort(dependentDetails);
				 travelerReqModel.setFamilyDetails(dependentDetails);
				 }
				 
				model.addAttribute(travelerReq, travelerReqModel);

				model.addAttribute("loginUserId", loginUser.getUserId());

				model.addAttribute("enumRelation", travelerProfileService.getEnumAsString(relation));
				model.addAttribute("enumMaritalStatus", travelerProfileService.getEnumAsString(marStatus));
				model.addAttribute("enumGender", travelerProfileService.getEnumAsString(gender));

				model.addAttribute("familyDetailsNo", travelerReqModel.getFamilyDetails().size());
				model.addAttribute("showcdao",
					travelerReqModel.getCategoryId().equals("100003") || travelerReqModel.getCategoryId().equals("100050") || travelerReqModel.getCategoryId().equals("100030"));

				return path + "editTraveler";
			} else if (reqResponse != null){
				model.addAttribute("error", reqResponse.getErrorMessage());
			} else {
				model.addAttribute("error", "Unable to Get User");
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, EditTravelerController.class, LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE);
			model.addAttribute("error", "Error Occurred");
		}
		return path + "searchPersonalNo";
	}

	
   public void setServiceType(TravelerReqModel travelerReqModel) {
	   
	   if (Boolean.FALSE.equals(travelerReqModel.getCivilianService())) {
			travelerReqModel.setServiceType(ServiceType.ARMED_FORCES);
		} else {
			travelerReqModel.setServiceType(ServiceType.CIVILIAN);
		}
	   
   }
 	
	@PostMapping("/updateTraveler")
	public String updateTraveler(@ModelAttribute @Valid EditTravelerDTO travelerProfileDTO, BindingResult result, RedirectAttributes attributes,HttpServletRequest request) {
		String errors = "errors";

		String encryptedUserAlias =	travelerProfileDTO.getUserAlias();
		
		
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, encryptedUserAlias);
		
		travelerProfileDTO.setUserAlias(decryptPersonalNo);
		
		try {
			DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerController.class, "[updateTraveler] Update Traveler Profile DTO::::::::::::::: " + travelerProfileDTO);
			if (result.hasErrors()) {
				DODLog.error(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerController.class, "[updateTraveler] Error::::: " + result.getAllErrors());
				ObjectError objectError = result.getAllErrors().get(0);
				attributes.addFlashAttribute(errors, objectError.getDefaultMessage());
				return "redirect:searchTraveler";
			} else {
				EditTravelerUser editTravelerUser = editTravelerService.initTraveler(travelerProfileDTO,request);
				if (editTravelerUser == null) {
					
					attributes.addFlashAttribute(errors, "Error while initialize Traveler Profile");
				} else {
					editTravelerUser.setDateOfComJoin(editTravelerUser.getDateOfCom_join());
					
					Map.Entry<Boolean, String> entry = editTravelerService.updateTraveler(editTravelerUser).entrySet().iterator().next();
					if (Boolean.TRUE.equals(entry.getKey())) {
						
						attributes.addFlashAttribute(errors, "PROFILE EDIT REQUEST HAS BEEN SENT TO CO FOR APPROVAL");
					} else {
						
						attributes.addFlashAttribute(errors, entry.getValue());
					}
				}
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, EditTravelerController.class, LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE);
			attributes.addFlashAttribute(errors, "Error Occurred");
		}
		return "redirect:searchTraveler";
	}

	// Ajax validate Block Of Personal Number
	@PostMapping("/validateBlockOfPersnlNo")
	public ResponseEntity<String> validateBlockOfPersnlNo(@RequestParam String paoCode, @RequestParam String persnlNo) {
		DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerController.class, "Validate Block Of Persnl No, PAO Code: " + paoCode + " persnl No: " + persnlNo);
		return ResponseEntity.ok(editTravelerService.validateBlockOfPNo(paoCode, persnlNo));
	}

	@PostMapping("/getCategoryIdOnTraveller")
	public ResponseEntity<List<Category>> getCategoryIdOnTraveller(@RequestParam String serviceId) {
		DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerController.class, "Get Category for DAD/DRDO: " + serviceId);
		return ResponseEntity.ok(editTravelerService.getCategoryIdOnTraveller(serviceId));
	}

	@PostMapping("/getLevelOnCategory")
	public ResponseEntity<List<LevelInfo>> getLevelOnCategory(@RequestParam String serviceId, @RequestParam String categoryId) {
		DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerController.class, "Get Level for DAD/DRDO serviceId: " + serviceId+" | categoryId:"+categoryId);
		return ResponseEntity.ok(editTravelerService.getLevelOnCategory(serviceId, categoryId));
	}

}
