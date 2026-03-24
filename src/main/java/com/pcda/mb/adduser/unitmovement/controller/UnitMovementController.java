package com.pcda.mb.adduser.unitmovement.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.DODServices;
import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.unitmovement.model.PostUnitMovement;
import com.pcda.mb.adduser.unitmovement.model.UnitMovementFormData;
import com.pcda.mb.adduser.unitmovement.model.UnitMovementModel;
import com.pcda.mb.adduser.unitmovement.model.UnitMovementResponse;
import com.pcda.mb.adduser.unitmovement.service.UnitMovementService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.ServiceType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mb")
public class UnitMovementController {

	@Autowired
	private UnitMovementService unitMovementService;

	private String url = "/MB/AddUsers/UnitMovement";

	@GetMapping("/unitMovement")
	public String getUnitMovement(Model model,HttpServletRequest request) {
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

		DODServices dodServices = unitMovementService.getService(serviceId);
		ServiceType serviceType;

		if (dodServices.getArmedForces().name().equalsIgnoreCase("YES")) {
			serviceType = ServiceType.ARMED_FORCES;
		} else {
			serviceType = ServiceType.CIVILIAN;
		}

		if (serviceType.equals(ServiceType.CIVILIAN)) {
			model.addAttribute("errors", " Not Available for Civilian");
			return "MB/AddUsers/UnitMovement/userUnitMovementErrorPage";
		} else {
		model.addAttribute("serviceId", loginUser.getServiceId());
		return url + "/unitMovement";
	}
	}

	@GetMapping("/cnfPageUnitMov")
	public String confimPageIn(Model model) {
		return url + "/conformationPage";
	}

	@GetMapping("/errorPageUnitMov")
	public String errorPageTransferIn(Model model) {
		return url + "/errorPage";
	}

	@PostMapping("/createUnitMovement")
	public String saveUnitMovement(Model model,PostUnitMovement postUnitMovement,
			HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());

		LoginUser loginUser = sessionVisitor.getLoginUser();
		postUnitMovement.setLoginUserId(loginUser.getUserId());

		Optional<OfficeModel> optionalOffice = unitMovementService.getUnitByUserId(loginUser.getUserId());
		OfficeModel officeModel = new OfficeModel();
		if (optionalOffice.isPresent()) {
			officeModel = optionalOffice.get();
		}
		model.addAttribute("personalNo", "");
		model.addAttribute("groupId", officeModel.getGroupId());
		model.addAttribute("model", postUnitMovement);
		model.addAttribute("categoryList", unitMovementService.getCategories(postUnitMovement.getServiceId()));
		return url + "/saveUnitMovement";
	}
	
//---------------------- Search PersonalNoData based on category----------------//
	
	@PostMapping("/getPersonalNo")
	public String searchPersonalNo(Model model, @RequestParam String serviceId, UnitMovementFormData movementForm,
			HttpServletRequest request) {
		DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementController.class,
				"[searchPersonalNo] serviceId::" +serviceId+" ## movementForm model :" + movementForm);

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());

		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		movementForm.setLoginUserId(loginUser.getUserId());

		Optional<OfficeModel> optionalOffice = unitMovementService.getUnitByUserId(loginUser.getUserId());
		OfficeModel officeModel = new OfficeModel();
		if (optionalOffice.isPresent()) {
			officeModel = optionalOffice.get();
		}
  
		List<UnitMovementModel> personalNoData =unitMovementService.getPersonalNoData(movementForm.getGroupId(),
				movementForm.getPersonalNo(), movementForm.getCategoryId());
		
		model.addAttribute("groupId", officeModel.getGroupId());
		model.addAttribute("serviceId", serviceId);
		model.addAttribute("model", movementForm);
		model.addAttribute("personalNo", movementForm.getPersonalNo());
		model.addAttribute("categoryId", movementForm.getCategoryId());
		model.addAttribute("categoryList", unitMovementService.getCategories(serviceId));
		
		if(personalNoData.isEmpty()) {
			
			model.addAttribute("noRecord", "No Record Found");
		}else {
		model.addAttribute("personalNoData", personalNoData);
		}
		return url + "/saveUnitMovement";
	}

	
	//-------------------- save Unit Movement----------------//
	
	@PostMapping("/submitUnit")
	public String saveUnitMovement(@ModelAttribute @Valid PostUnitMovement postUnitMovement, BindingResult result,
			RedirectAttributes attributes, HttpServletRequest request) {
		String errors = "errors";

		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());

			LoginUser loginUser = sessionVisitor.getLoginUser();
			if (loginUser == null) {
				return "redirect:/login";
			}

			Optional<OfficeModel> optionalOffice = unitMovementService.getUnitByUserId(loginUser.getUserId());
			OfficeModel officeModel = new OfficeModel();
			if (optionalOffice.isPresent()) {
				officeModel = optionalOffice.get();
			}

			
			if (result.hasErrors()) {
				DODLog.error(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementController.class,
						"[saveUnitMovement] Error::::: " + result.getAllErrors());
				ObjectError objectError = result.getAllErrors().get(0);
				attributes.addFlashAttribute(errors, objectError.getDefaultMessage());
			} else {
				
				postUnitMovement.setServiceId(postUnitMovement.getServiceId());
				postUnitMovement.setGroupIdForUserId(officeModel.getGroupId());
				postUnitMovement.setUnitRelocationDate(CommonUtil.formatString(postUnitMovement.getUnitRelocationDateStr(), "dd-MM-yyyy"));
				postUnitMovement.setLoginUserId(loginUser.getUserId());
				DODLog.error(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementController.class,
						"[saveUnitMovement] Saving Unit Movement ::::" + postUnitMovement);
				UnitMovementResponse unitMovementResponse = unitMovementService.saveUnitMovement(postUnitMovement);
				if (unitMovementResponse.getErrorCode() == 200) {
					
					attributes.addFlashAttribute("success", unitMovementResponse.getErrorMessage());
					return "redirect:/mb/cnfPageUnitMov";
				} else {
					
					attributes.addFlashAttribute("errors", unitMovementResponse.getErrorMessage());
					return "redirect:/mb/errorPageUnitMov";
				}
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, UnitMovementController.class, LogConstant.UNIT_MOVEMENT_LOG_FILE);
			attributes.addFlashAttribute("errors", "Error while creating Unit Movement");
		}
		return "redirect:/mb/unitMovement";
	}

	
	
	// Ajax call to get Station Code
	@GetMapping("/getStation")
	@ResponseBody
	public List<String> searchStation(@RequestParam String station) {
		DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementController.class,
				"Search Station Airport:" + station);
		return unitMovementService.getStation(station);
	}

	// Ajax call to get Airport Code
	@GetMapping("/getAirport")
	@ResponseBody
	public List<String> searchAirport(@RequestParam String airPortName) {
		DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementController.class, "Search  Airport:" + airPortName);
		return unitMovementService.getAirport(airPortName);
	}

}
