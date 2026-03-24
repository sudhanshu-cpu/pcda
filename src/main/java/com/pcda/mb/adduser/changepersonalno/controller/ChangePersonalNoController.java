package com.pcda.mb.adduser.changepersonalno.controller;


import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.DODServices;
import com.pcda.common.model.GradePayRankModel;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.PAOMappingModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.changepersonalno.model.ChangePNoRequired;
import com.pcda.mb.adduser.changepersonalno.model.ChangePersonalNoPostResponse;
import com.pcda.mb.adduser.changepersonalno.model.GetChangePersonalNo;
import com.pcda.mb.adduser.changepersonalno.model.PostChangePersonalNoModel;
import com.pcda.mb.adduser.changepersonalno.service.ChangePersonalNoService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.ServiceType;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/mb")
public class ChangePersonalNoController {
@Autowired
	private ChangePersonalNoService changePersonalNoService;

	private String pagePath = "MB/AddUsers/ChangePersonalNo/";

	@GetMapping("/changePersonalNo")
	public  String createChangePersonalNo(Model model,HttpServletRequest request) {
		ChangePNoRequired noRequired = new ChangePNoRequired();
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		String serviceId = "";

		if (loginUser == null) {
			return "redirect:/login";
		}

		if (loginUser.getUserServiceId() != null && !loginUser.getUserServiceId().equalsIgnoreCase("null") && !loginUser.getUserServiceId().trim().equals("")) {
			serviceId = loginUser.getUserServiceId();
		} else {
			serviceId = loginUser.getServiceId();
		}

		
		DODServices dodServices = changePersonalNoService.getService(serviceId);
		ServiceType serviceType;
		
		
		
		if (dodServices.getArmedForces().name().equalsIgnoreCase("YES")) {
			serviceType = ServiceType.ARMED_FORCES;
		} else {
			serviceType = ServiceType.CIVILIAN;
		}

		noRequired.setServiceName(dodServices.getServiceName());

		

		if (serviceType.equals(ServiceType.CIVILIAN)) {
			model.addAttribute("errors", " Not Available for Civilian");
			return "MB/AddUsers/ChangeService/usersCivilianErrorPage";
		} else {
			 return "redirect:armyTravlerPno";
		}
   
	}

	@GetMapping("/armyTravlerPno")
	public String armyTravelerProfile(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		BigInteger userId = loginUser.getUserId();
		String serviceId = "";

		if (loginUser.getUserServiceId() != null &&!loginUser.getUserServiceId().trim().isEmpty()) {
			serviceId = loginUser.getUserServiceId();
		} else {
			serviceId = loginUser.getServiceId();
		}
		ChangePNoRequired changePNoRequired = new ChangePNoRequired();
		OfficeModel officeModel = changePersonalNoService.getOfficeByUserId(userId);
		DODServices dodServices = changePersonalNoService.getService(serviceId);
		changePNoRequired.setServiceName(dodServices.getServiceName());

		List<DODServices> armedServices = changePersonalNoService.getArmedTravelerServices(loginUser, officeModel ,changePNoRequired);

		model.addAttribute("changeServiceList", armedServices);
		model.addAttribute("visitorServiceName", changePersonalNoService.getService(serviceId).getServiceName());
		model.addAttribute("visitorServiceId", serviceId);
		model.addAttribute("loginVisitorUnitId",officeModel.getGroupId());
		model.addAttribute("locationId", officeModel.getLocationTypeId());
		model.addAttribute("locationName", changePersonalNoService.getLocationById(officeModel.getLocationTypeId()));

		model.addAttribute("enumRelation", changePersonalNoService.getEnumAsString("RELATION_TYPE"));
		model.addAttribute("enumMaritalStatus", changePersonalNoService.getEnumAsString("MARITAL_STATUS"));
		model.addAttribute("enumGender", changePersonalNoService.getEnumAsString("GENDER"));

		model.addAttribute("rankExistService", changePNoRequired.getRankExistService()); 

		return pagePath + "changePersonalNo";
	}

	
	
	@PostMapping("/saveChangePno")
	public String getChangePersonalSave(PostChangePersonalNoModel postChangePNo, BindingResult result,HttpServletRequest request,RedirectAttributes attributes,Model model) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
	
		postChangePNo.setLoginUserId(loginUser.getUserId());
		
		postChangePNo.setUserAlias(postChangePNo.getOldPersonalNo());
		String[] levelarr=postChangePNo.getLevelId().split(":");
		postChangePNo.setLevelId(levelarr[0]);
		try {
			if (result.hasErrors()) {
				DODLog.error(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoController.class,
						"[getChangePersonalSave] CHANGE PERONAL NO MODEL ERROR :: " + result.getAllErrors());
				ObjectError objectError = result.getAllErrors().get(0);
			
				model.addAttribute("errors", objectError.getDefaultMessage());
				return pagePath+"changePnoError";
				

			} else {
				DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoController.class,
						"[getChangePersonalSave] CHANGE PERSONAL NO SAVE MODEL :: "+postChangePNo);
				
				
				
				ChangePersonalNoPostResponse changePersonalNoPostResponse=changePersonalNoService.getSaveChangePno(postChangePNo); 
				
				if(changePersonalNoPostResponse!=null && changePersonalNoPostResponse.getErrorCode()==200) {
				return pagePath+ "changePersonalSuccessPage";
				
				}else {
					
					model.addAttribute("errors",changePersonalNoPostResponse==null ? "":changePersonalNoPostResponse.getErrorMessage());
					return pagePath+"changePnoError";
				}

			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangePersonalNoController.class, LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
			
		}
		
		return  "redirect:changePersonalNo";

	}

	// AJAX Mapping to get Categories based on service
	@PostMapping("/ChangePersonalCategoryBasedOnService")
	public ResponseEntity<Map<String, String>> pnoCategoryBasedOnService(@RequestParam String serviceId) {
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoController.class,
				"[pnoCategoryBasedOnService] SERVICE ID FOR CATEGORY BASED SEVICE IN CHANGE PERSONAL NO:: " +serviceId);
		return ResponseEntity.ok(changePersonalNoService.getCategoriesBasedOnService(serviceId));
	}

	// AJAX Mapping to get Level
	@PostMapping("/getChangePersonalLevel")
	public ResponseEntity<Map<String, List<String>>> pnoGradePayBasedOnServiceCategory(
			@RequestParam String serviceId, @RequestParam String categoryId) {
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoController.class,
				"[pnoGradePayBasedOnServiceCategory] SERVICE ID AND CATEGORY ID FOR LEVEL BASED ON SERVICE AND CATEGORY :: "+serviceId+","+categoryId);
		return ResponseEntity.ok(changePersonalNoService.gradePayBasedOnServiceCategory(serviceId, categoryId));
	}


	// AJAX Mapping to get Personal Number Prefix Map based on category AND SERVICE 
	@PostMapping("/getChangePersonalPersonalNoPrefix")
	public ResponseEntity<Map<String, String>> pnoPersonalNoPrefix(@RequestParam String serviceId,
			@RequestParam String categoryId) {
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoController.class,
				"[pnoPersonalNoPrefix] SERVICE ID AND CATEGORY ID FOR PERRSONAL NO PREFIX BASED ON SERVICE AND CATEGORY :: "+serviceId+","+categoryId);
		return ResponseEntity.ok(changePersonalNoService.getPersonalNoPrefixMap(serviceId, categoryId));
	}

	// AJAX Mapping to get Grade Pay On change of Level
	@PostMapping("/getChangePersonalGradePayRank")
	public ResponseEntity<String> getChangePnoGradePayRank(@RequestParam String rankId) {
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoController.class,
				"[getChangePnoGradePayRank] RANK ID TO GET GRADEPAY RANK:" + rankId);
		GradePayRankModel gradePayRankModel = changePersonalNoService.getGradePayRank(rankId);
		return ResponseEntity.ok(gradePayRankModel.getRankName());
	}


	// AJAX To check existence of personal number
	@PostMapping("/getCheckPersonalNoPno")
	public ResponseEntity<Boolean> checkPersonalNo(@RequestParam String personalNo) {
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoController.class,
				"[checkPersonalNo] PERSONAL NO TO CHECK DUPLICACY :: " + personalNo);
		return ResponseEntity.ok(changePersonalNoService.checkDuplicate(personalNo));
	}

	// AJAX To Get PAO
	@PostMapping("/getPAOPno")
	public ResponseEntity<Map<String, List<PAOMappingModel>>> getPAO(@RequestParam String serviceId, @RequestParam String categoryId, HttpServletRequest request) {
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoController.class,
				"[getPAO] SERVICE ID AND CATEGORY ID FOR PAO BASED ON SERVICE AND CATEGORY :: " + serviceId+"," + categoryId);
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		return ResponseEntity.ok(changePersonalNoService.getPaoOffice(serviceId, categoryId, loginUser));
	}

	// ajax for data
	
	@PostMapping("/getDataPno")
	public ResponseEntity<List<GetChangePersonalNo>> getserverData(@RequestParam String dob, @RequestParam String enrollmentDate, HttpServletRequest request){
	
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoController.class,
				"[getserverData] DOB AND ENROLLMENT DATE FOR DATA :: " + dob + "," + enrollmentDate);
	
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		BigInteger userId=	loginUser.getUserId();
		return ResponseEntity.ok(changePersonalNoService.getData(dob, enrollmentDate,userId));
		
	}
	
	

}
