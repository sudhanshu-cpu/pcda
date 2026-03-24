package com.pcda.mb.adduser.travelerprofile.controller;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.DODServices;
import com.pcda.common.model.GradePayRankModel;
import com.pcda.common.model.Location;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.PAOMappingModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.edittravelerprofile.model.TravelerReqModel;
import com.pcda.mb.adduser.edittravelerprofile.model.TravelerReqResponse;
import com.pcda.mb.adduser.travelerprofile.model.TravelerProfileDTO;
import com.pcda.mb.adduser.travelerprofile.model.TravelerProfileReqData;
import com.pcda.mb.adduser.travelerprofile.model.TravelerUser;
import com.pcda.mb.adduser.travelerprofile.service.ProfileValidation;
import com.pcda.mb.adduser.travelerprofile.service.TravelerProfileService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.ServiceType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mb")
public class TravelerProfileController {

	@Autowired
	private TravelerProfileService travelerProfileService;

	private String path = "MB/AddUsers/TravelerProfile/";

	private String relation = "RELATION_TYPE";
	private String marStatus = "MARITAL_STATUS";
	private String gender = "GENDER";

	@GetMapping("/addTravelerProfile")
	public String addTravelerProfile(RedirectAttributes attributes, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		String serviceId = "";

		if (loginUser == null) {
			return "redirect:/login";
		}

		if (!CommonUtil.isEmpty(loginUser.getUserServiceId())) {
			serviceId = loginUser.getUserServiceId();
		} else {
			serviceId = loginUser.getServiceId();
		}

		Optional<DODServices> optionalService = travelerProfileService.getService(serviceId);
		DODServices dodServices = new DODServices();
		if (optionalService.isPresent()) {
			dodServices = optionalService.get();
		}

		ServiceType serviceType;

		if (dodServices.getArmedForces().name().equalsIgnoreCase("YES")) {
			serviceType = ServiceType.ARMED_FORCES;
		} else {
			serviceType = ServiceType.CIVILIAN;
		}


		

		if (serviceType.equals(ServiceType.CIVILIAN)) {
			return "redirect:civillianTravelerProfile";
		} else {
			return "redirect:armyTravelerProfile";
		}
	}

	@GetMapping("/armyTravelerProfile")
	public String armyTravelerProfile(Model model, HttpServletRequest request) {
		
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}

		BigInteger userId = loginUser.getUserId();
		String serviceId = "";

		if (!CommonUtil.isEmpty(loginUser.getUserServiceId())) {
			serviceId = loginUser.getUserServiceId();
		} else {
			serviceId = loginUser.getServiceId();
		}

		Optional<OfficeModel> optionalOffice = travelerProfileService.getOfficeByUserId(userId);
		OfficeModel officeModel = new OfficeModel();
		if (optionalOffice.isPresent()) {
			officeModel = optionalOffice.get();
		}

		TravelerProfileReqData reqData = new TravelerProfileReqData();

		Optional<DODServices> optionalService = travelerProfileService.getService(serviceId);
		DODServices dodServices = new DODServices();
		if(optionalService.isPresent()) {
			dodServices = optionalService.get();
		}
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		reqData.setCurrentYear(currentYear);
		reqData.setPreviousYear(currentYear-1);
		
	

		Optional<Location> optionalLocation = travelerProfileService.getLocationById(officeModel.getLocationTypeId());
		Location location = new Location();
		if (optionalLocation.isPresent()) {
			location = optionalLocation.get();
		}

		reqData.setServiceId(dodServices.getServiceId());

		reqData.setServiceName(dodServices.getServiceName());

		List<DODServices> armedServices = travelerProfileService.getArmedTravelerServices(loginUser, officeModel, reqData);

		model.addAttribute("loginUserId", userId);

		model.addAttribute("travelerServiceList", armedServices);


		model.addAttribute("loginVisitorUnitId", officeModel.getGroupId());
		model.addAttribute("locationId", officeModel.getLocationTypeId());
		model.addAttribute("locationName", location.getLocationName());

		model.addAttribute("enumRelation", travelerProfileService.getEnumAsString(relation));
		model.addAttribute("enumMaritalStatus", travelerProfileService.getEnumAsString(marStatus));
		model.addAttribute("enumGender", travelerProfileService.getEnumAsString(gender));

		model.addAttribute("reqData", reqData);

		return path + "addTravelerProfileArmy";
	}

	@GetMapping("/civillianTravelerProfile")
	public String civillianTravelerProfile(Model model, HttpServletRequest request) {
		
		
		try {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}

		BigInteger userId = loginUser.getUserId();
		String serviceId = loginUser.getServiceId();

		Optional<OfficeModel> optionalOffice = travelerProfileService.getOfficeByUserId(userId);
		OfficeModel officeModel = new OfficeModel();
		if (optionalOffice.isPresent()) {
			officeModel = optionalOffice.get();
		}

		Optional<OfficeModel> optionalPaoOffice = travelerProfileService.getOfficeByGroupId(officeModel.getPaoGroupId());
		OfficeModel paoOfficeModel = new OfficeModel();
		if (optionalPaoOffice.isPresent()) {
			paoOfficeModel = optionalPaoOffice.get();
		}

		TravelerProfileReqData reqData = new TravelerProfileReqData();

		Optional<DODServices> optionalService = travelerProfileService.getService(serviceId);
		DODServices dodServices = new DODServices();
		if(optionalService.isPresent()) {
			dodServices = optionalService.get();
		}

		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);

		model.addAttribute("loginUserId", userId);

		model.addAttribute("visitorServiceId", loginUser.getServiceId());
		reqData.setServiceName(dodServices.getServiceName());

		model.addAttribute("locationId", officeModel.getLocationTypeId());
		model.addAttribute("loginVisitorUnitId", officeModel.getGroupId());
		model.addAttribute("loginVisitorPaoOfficeId", paoOfficeModel.getGroupId());
		model.addAttribute("loginVisitorPaoOfficeName", paoOfficeModel.getName());
		model.addAttribute("categoryList", travelerProfileService.getCategoriesBasedOnService(serviceId));

		model.addAttribute("enumRelation", travelerProfileService.getEnumAsString(relation));
		model.addAttribute("enumMaritalStatus", travelerProfileService.getEnumAsString(marStatus));
		model.addAttribute("enumGender", travelerProfileService.getEnumAsString(gender));

		reqData.setCurrentYear(currentYear);
		reqData.setCurrentSubBlockYear(travelerProfileService.getSubBlockYearOfYear(currentYear));
		reqData.setPreviousSubBlockYear(travelerProfileService.getSubBlockYearOfYear(currentYear - 2));

		model.addAttribute("reqData", reqData);
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelerProfileController.class, LogConstant.TRAVELER_PROFILE_LOG_FILE);
		}

		return path + "addTravelerProfileCivillian";
	}

	@PostMapping("/savecivillianTraveler")
	public String saveCivillianTraveler(@ModelAttribute @Valid TravelerProfileDTO travelerProfileDTO, BindingResult result, RedirectAttributes attributes, Model model,
			HttpServletRequest request) {
		String errors = "errors";

		try {
			DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileController.class, "[saveCivillianTraveler] post save civillian Model ::::::::::::::: " + travelerProfileDTO);
			if (result.hasErrors()) {
				DODLog.error(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileController.class, "Error::::: " + result.getAllErrors());
				ObjectError objectError = result.getAllErrors().get(0);
				attributes.addFlashAttribute(errors, objectError.getDefaultMessage());
			} else {
				travelerProfileDTO.setService(travelerProfileDTO.getLoginVisitorServiceId());
				travelerProfileDTO.setAlternateService(travelerProfileDTO.getLoginVisitorServiceId());
				travelerProfileDTO.setServiceType(ServiceType.CIVILIAN);
				TravelerUser travelerUser = travelerProfileService.initVariable(travelerProfileDTO);
				DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileController.class, "save civillian Traveler User Details: " + travelerUser);
				if (travelerUser == null) {
					
					attributes.addFlashAttribute(errors, "Error while creating Traveler Profile");
					
				} else {
					String validationMsg= ProfileValidation.validate(travelerUser);
					if (!validationMsg.equals("OK")){
						attributes.addFlashAttribute(errors,validationMsg);
						return "redirect:civillianTravelerProfile";
					
					}
					
					
					
					Map.Entry<Boolean, String> entry = travelerProfileService.saveTraveler(travelerUser).entrySet().iterator().next();
					if (Boolean.TRUE.equals(entry.getKey())) {
						

						Optional<OfficeModel> optionalOffice = travelerProfileService.getOfficeByUserId(travelerUser.getLoginUserId());
						OfficeModel officeModel = new OfficeModel();
						if (optionalOffice.isPresent()) {
							officeModel = optionalOffice.get();
						}

						TravelerReqResponse reqResponse = travelerProfileService.getTravelerProfile(travelerUser.getPersonalNumber(), officeModel.getGroupId());
						TravelerReqModel traveler = new TravelerReqModel();

						if (reqResponse != null && reqResponse.getErrorCode() == 200 && reqResponse.getResponse() != null) {
							traveler = reqResponse.getResponse();
						}

						model.addAttribute("traveller", traveler);
						return path + "saveConfirm";
					} else {
						
						attributes.addFlashAttribute(errors, entry.getValue());
					}
				}
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelerProfileController.class, LogConstant.TRAVELER_PROFILE_LOG_FILE);
		}

		return "redirect:civillianTravelerProfile";
	}

	@PostMapping("/saveArmyTraveler")
	public String saveArmyTraveler(@ModelAttribute @Valid TravelerProfileDTO travelerProfileDTO, BindingResult result, RedirectAttributes attributes, Model model,HttpServletRequest request) {
		String errors = "errors";

		try {
			DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileController.class, "Army Traveler Profile DTO::::::::::::::: " + travelerProfileDTO);
			if (result.hasErrors()) {
				DODLog.error(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileController.class, "Error::::: " + result.getAllErrors());
				ObjectError objectError = result.getAllErrors().get(0);
				attributes.addFlashAttribute(errors, objectError.getDefaultMessage());
				return "redirect:armyTravelerProfile";
			} else {
				travelerProfileDTO.setService(travelerProfileDTO.getLoginVisitorServiceId());
				travelerProfileDTO.setServiceType(ServiceType.ARMED_FORCES);
				TravelerUser travelerUser = travelerProfileService.initVariable(travelerProfileDTO);
				DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileController.class, "save Army Traveler User Details: " + travelerUser);
				if (travelerUser == null) {
					
					attributes.addFlashAttribute(errors, "Error while creating Traveler Profile");
				} else {
					
					Map.Entry<Boolean, String> entry = travelerProfileService.saveTraveler(travelerUser).entrySet().iterator().next();
					if (Boolean.TRUE.equals(entry.getKey())) {
						

						Optional<OfficeModel> optionalOffice = travelerProfileService.getOfficeByUserId(travelerUser.getLoginUserId());
						OfficeModel officeModel = new OfficeModel();
						if (optionalOffice.isPresent()) {
							officeModel = optionalOffice.get();
						}

						TravelerReqResponse reqResponse = travelerProfileService.getTravelerProfile(travelerUser.getPersonalNumber(), officeModel.getGroupId());
						TravelerReqModel traveler = new TravelerReqModel();

						if (reqResponse != null && reqResponse.getErrorCode() == 200 && reqResponse.getResponse() != null) {
							traveler = reqResponse.getResponse();
							
						}

						model.addAttribute("traveller", traveler);
						return path + "saveConfirm";
					} else {
						
						attributes.addFlashAttribute(errors, entry.getValue());
					}
				}
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelerProfileController.class, LogConstant.TRAVELER_PROFILE_LOG_FILE);
		}
		return "redirect:travelerError";
	}

	@GetMapping("/travelerError")
	public String travelerError() {
		return path+ "travelerError";
	}
	


	// Ajax Mapping to get Categories based on service
	@PostMapping("/travelerCategoryBasedOnService")
	public ResponseEntity<Map<String, String>> travelerCategoryBasedOnService(@RequestParam String serviceId) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileController.class,
				"Get Categories Based On Service Traveler Profile::"+serviceId);
		return ResponseEntity.ok(travelerProfileService.getCategoriesBasedOnService(serviceId));
	}

	// Ajax Mapping to get Level Map based on category
	@PostMapping("/getTravelerLevel")
	public ResponseEntity<Map<String, List<String>>> travelerGradePayBasedOnServiceCategory(
			@RequestParam String serviceId, @RequestParam String categoryId) {
	
		return ResponseEntity.ok(travelerProfileService.gradePayBasedOnServiceCategory(serviceId, categoryId));
	}

	// Ajax to Get Retirement Age
	@PostMapping("/getRetirementAge")
	public ResponseEntity<Integer> getRetirementAge(@RequestParam String serviceId, @RequestParam String levelId, @RequestParam String rankId) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileController.class,
				"Retirement Age Based On Service " + serviceId + " And Level " + levelId+"And Rank "+rankId);
		return ResponseEntity.ok(travelerProfileService.getRetirementAge(serviceId, levelId, rankId));
	}

	// Ajax Mapping to get Personal Number Prefix Map based on category
	@PostMapping("/getTravelerPersonalNoPrefix")
	public ResponseEntity<Map<String, String>> travelerPersonalNoPrefix(@RequestParam String serviceId,
			@RequestParam String categoryId) {
	
		return ResponseEntity.ok(travelerProfileService.getPersonalNoPrefixMap(serviceId, categoryId));
	}

	// Ajax Mapping to get Grade Pay On change of Level
	@PostMapping("/getTravelerGradePayRank")
	public ResponseEntity<String> getTravelerGradePayRank(@RequestParam String rankId) {
	
		Optional<GradePayRankModel> optional = travelerProfileService.getGradePayRank(rankId);
		GradePayRankModel gradePayRankModel = new GradePayRankModel();
		if (optional.isPresent()) {
			gradePayRankModel = optional.get();
		}
		return ResponseEntity.ok(gradePayRankModel.getRankName());
	}

	// Ajax call to get Nearest Station
	@PostMapping("/getNearestStation")
	public ResponseEntity<List<String>> searchStation(@RequestParam String station) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileController.class,
				"Get Nearest Station: " + station);
		return ResponseEntity.ok(travelerProfileService.getStation(station));
	}

	// Ajax call to get Airport Code
	@PostMapping("/getNearestAirport")
	public ResponseEntity<List<String>> searchAirport(@RequestParam String airPortName) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileController.class,
				"Get Nearest Airport: " + airPortName);
		return ResponseEntity.ok(travelerProfileService.getAirport(airPortName));
	}

	// Ajax To check existence of personal number
	@PostMapping("/checkPersonalNo")
	public ResponseEntity<Boolean> checkPersonalNo(@RequestParam String personalNo) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileController.class,
				"Check Duplicate: " + personalNo);
		return ResponseEntity.ok(travelerProfileService.checkDuplicate(personalNo));
	}

	// Ajax To Get PAO
	@PostMapping("/getPAO")
	public ResponseEntity<Map<String, List<PAOMappingModel>>> getPAO(@RequestParam String serviceId, @RequestParam String categoryId, HttpServletRequest request) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileController.class,
				"Get pao with service: " + serviceId + " , category: " + categoryId);
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		return ResponseEntity.ok(travelerProfileService.getPaoOffice(serviceId, categoryId, loginUser));
	}

	// Ajax To Check Duplicate CDA(O) Account Number
	@PostMapping("/checkDuplicateAccountNo")
	public ResponseEntity<String> checkDuplicateAccountNo(@RequestParam String cdaoAccountNo) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileController.class, "Check CDAO Account Number: " + cdaoAccountNo);
		return ResponseEntity.ok(travelerProfileService.checkDuplicateCDAOAccountNo(cdaoAccountNo));
	}

	// Ajax To Check Duplicate Navy Civilian Service Number
		@PostMapping("/checkDuplicateNavyCivilianServiceNo")
		public ResponseEntity<String> checkDupNavyCivilianServiceNo(@RequestParam String NavyCivilianPersonalNo) {
			DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileController.class, "Check Navy Civilian Service No: " + NavyCivilianPersonalNo);
			return ResponseEntity.ok(travelerProfileService.chkDupNavyCivilianServiceNo(NavyCivilianPersonalNo));
		}

}
