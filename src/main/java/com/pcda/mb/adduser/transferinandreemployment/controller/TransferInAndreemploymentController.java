package com.pcda.mb.adduser.transferinandreemployment.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.DODServices;
import com.pcda.common.model.GradePayRankModel;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.PAOMappingModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.transferinandreemployment.model.PostTransferInReemployement;
import com.pcda.mb.adduser.transferinandreemployment.model.TINAndReemployment;
import com.pcda.mb.adduser.transferinandreemployment.model.TINAndReqData;
import com.pcda.mb.adduser.transferinandreemployment.model.TransferInReemployeeResponse;
import com.pcda.mb.adduser.transferinandreemployment.service.TransferInReemploymentService;
import com.pcda.mb.adduser.travelerprofile.controller.TravelerProfileController;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mb")
public class TransferInAndreemploymentController {

	private String url = "MB/AddUsers/TransferInAndReemployment";

	@Autowired
	private TransferInReemploymentService transferInReemploymentService;

	@Autowired
	private OfficesService officesService;

	@GetMapping("/transferInReemployment")
	public String transferInReemployment(Model model, HttpServletRequest request) {

		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();

			if (loginUser == null) {
				return "redirect:/login";
			}
			Optional<OfficeModel> office = officesService.getOfficeByUserId(loginUser.getUserId());

			String locationId = "";
			String groupId = "";
			String name="";
			if (office.isPresent()) {
				locationId = office.get().getLocationTypeId();
				groupId = office.get().getGroupId();
				name=office.get().getName();
			}

			TINAndReqData reqData = new TINAndReqData();

			model.addAttribute("loginUserId", loginUser.getUserId());
			model.addAttribute("locationId", locationId);
			model.addAttribute("visitorUnitId", groupId);
			model.addAttribute("visitorServiceId", loginUser.getServiceId());
			model.addAttribute("serviceList", transferInReemploymentService.getTransferInReEmpServices());
			model.addAttribute("retirAgeOther", PcdaConstant.RETIR_AGE_OTHER);
			model.addAttribute("retirAgePBOR", PcdaConstant.RETIR_AGE_PBOR);
			model.addAttribute("reqData",
					transferInReemploymentService.getArmedTravelerServices(loginUser, office, reqData));
			model.addAttribute("name", name);
		} catch (Exception e) {
			DODLog.printStackTrace(e, TransferInAndreemploymentController.class, LogConstant.TRANSFER_IN_REEMPLOYEMENT);
		}

		return url + "/transferinAndReemployement";
	}

	@GetMapping("/getPrsonalNoDetailsTransferInRe")
	public ResponseEntity<TINAndReemployment> getPersonalNumberTransferInRe(@RequestParam String userAlias,
			HttpServletRequest request) {
		
		TINAndReemployment transferIn = transferInReemploymentService.getPersonalNumberTransferInRe(userAlias, request);
		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferInAndreemploymentController.class,
				"userAlias " + userAlias + "  ; result:::::::::: " + transferIn);
		return ResponseEntity.ok(transferIn);

	}

	@GetMapping("/confirmPagetransferInRee")
	public String confimPage(Model model) {
		return url + "/conformationPage";
	}

	@GetMapping("/errorPageTransferInRe")
	public String errorPageTransferOut(Model model) {
		return url + "/errorPage";
	}

	@PostMapping("/saveTransferInReemployement")
	public String saveTransferInReemployment(@ModelAttribute @Valid PostTransferInReemployement postTransferModel,
			BindingResult result, RedirectAttributes attributes, HttpServletRequest request) {
		
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loggedVisitor = sessionVisitor.getLoginUser();
	
		if (loggedVisitor == null) {
			return "redirect:/login";
		} 
		
		try {
			if (result.hasErrors()) {
				DODLog.error(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferInAndreemploymentController.class,
						"Transfer In Reemployement Error: " + result.getAllErrors());
				ObjectError objectError = result.getAllErrors().get(0);
				attributes.addFlashAttribute("transferOutModel", postTransferModel);
				attributes.addFlashAttribute("errors", objectError.getDefaultMessage());
				return "redirect:/mb/errorPageTransferInRe";
			} else {
				
				
				
				postTransferModel.setLoginUserId(loggedVisitor.getUserId());
				TransferInReemployeeResponse responce=transferInReemploymentService.saveTransferInReemploye(postTransferModel);
				

				
				if (responce.getErrorCode() == 200) {
					attributes.addFlashAttribute("success", responce.getErrorMessage());
					
					return "redirect:/mb/confirmPage";
				} else {
					attributes.addFlashAttribute("errors", responce.getErrorMessage());
					return "redirect:/mb/errorPageTransferOut";
				}
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, TransferInAndreemploymentController.class, LogConstant.TRANSFER_IN_REEMPLOYEMENT);
			attributes.addFlashAttribute("errors", "Error while creating User");
		}
		return "redirect:/mb/transferInReemployment";
	}

	// Ajax Mapping to get Categories based on service
	@PostMapping("/getTransferINReCateBasedOnService")
	public ResponseEntity<Map<String, String>> transferCategoryBasedOnService(@RequestParam String serviceId) {
		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferInAndreemploymentController.class,
				"Get Categories Based On Service Transfer In Reeemployement::" +serviceId);
		return ResponseEntity.ok(transferInReemploymentService.getCategoriesBasedOnService(serviceId));
	}


	// Ajax get service
	@GetMapping("/getServiceDetails")
	public ResponseEntity<List<DODServices>> getServiceDetailsTransfer() {
		
		return ResponseEntity.ok(transferInReemploymentService.getTransferInReEmpServices());
	}

	// Ajax Mapping to get Level Map based on category
	@PostMapping("/getTransferINReLevel")
	public ResponseEntity<Map<String, List<String>>> transferGradePayBasedOnServiceCategory(
			@RequestParam String serviceId, @RequestParam String categoryId) {
		
		return ResponseEntity.ok(transferInReemploymentService.gradePayBasedOnServiceCategory(serviceId, categoryId));
	}

	// Ajax To Get PAO
	@PostMapping("/getPAOTransfer")
	public ResponseEntity<Map<String, List<PAOMappingModel>>> getPAO(@RequestParam String serviceId,
			@RequestParam String categoryId) {
	
//		
		return ResponseEntity.ok(transferInReemploymentService.getPaoOfficeTra(serviceId, categoryId));
	}

	// Ajax Mapping to get Grade Pay On change of Level
	@PostMapping("/getTransferGradePayRank")
	public ResponseEntity<Optional<GradePayRankModel>> getTransGradePayRank(@RequestParam String rankId) {
		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TravelerProfileController.class,
				"Get Grade Pay Rank with id:" + rankId);
		Optional<GradePayRankModel> gradePayRankModel = transferInReemploymentService.getGradePayRank(rankId);
		return ResponseEntity.ok(gradePayRankModel);
	}

	// Ajax call to get Station Code
	@GetMapping("/getStationNRSTrans")
	@ResponseBody
	public List<String> searchStation(@RequestParam String station) {

		return transferInReemploymentService.getStations(station);
	}

	@GetMapping("/getSPRStationTrans")
	@ResponseBody
	public List<String> sprStation(@RequestParam String station) {

		return transferInReemploymentService.getStations(station);
	}

	@GetMapping("/getSPRNRSTrans")
	@ResponseBody
	public List<String> sprNRSStation(@RequestParam String station) {

		return transferInReemploymentService.getStations(station);
	}

	// Ajax call to get Airport Code
	@GetMapping("/getNADutyStationTrans")
	@ResponseBody
	public List<String> searchAirport(@RequestParam String airPortName) {

		return transferInReemploymentService.getAirports(airPortName);
	}

	@GetMapping("/getSPRAirportTrans")
	@ResponseBody
	public List<String> getSPRAirport(@RequestParam String airPortName) {

		return transferInReemploymentService.getAirports(airPortName);
	}

}
