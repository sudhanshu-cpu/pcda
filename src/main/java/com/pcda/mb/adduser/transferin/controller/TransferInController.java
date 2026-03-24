package com.pcda.mb.adduser.transferin.controller;

import java.util.List;
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

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.transferin.model.PostTransferInModel;
import com.pcda.mb.adduser.transferin.model.TransferIN;
import com.pcda.mb.adduser.transferin.model.TransferInResponse;
import com.pcda.mb.adduser.transferin.service.TransferInService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mb")
public class TransferInController {

	private String url = "MB/AddUsers/TransferIn";

	@Autowired
	private TransferInService transferInService;

	@Autowired
	private OfficesService officesService;


	@GetMapping("/transferIn")
	public String transferIn(Model model, HttpServletRequest request) {
		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();

			if (loginUser == null) {
				return "redirect:/login";
			}

			Optional<OfficeModel> office = officesService.getOfficeByUserId(loginUser.getUserId());
			
			String unitName = "";
			String unitCode = "";
			String locationId = "";
			if (office.isPresent()) {
				unitName = office.get().getName();
				unitCode = office.get().getGroupId();
				locationId = office.get().getLocationTypeId();
			}
			model.addAttribute("loginUserId", loginUser.getUserId());
			model.addAttribute("locationId", locationId);
			model.addAttribute("unitCode", unitCode);
			model.addAttribute("officename", unitName);

		} catch (Exception e) {
			DODLog.printStackTrace(e, TransferInController.class, LogConstant.TRANSFER_IN_LOG_FILE);
		}

		return url + "/transferin";

	}

	@GetMapping("/confirmationPage")
	public String confimPageIn(Model model) {
		return url + "/conformationPage";
	}

	@GetMapping("/errorPageTransferIn")
	public String errorPageTransferIn(Model model) {
		return url + "/errorPage";
	}

	@PostMapping("/saveTransferIn")
	public String saveTransferOut(@ModelAttribute @Valid PostTransferInModel postTransferInModel, BindingResult result,
			RedirectAttributes attributes, HttpServletRequest request) {

		try {
			if (result.hasErrors()) {
				
				DODLog.error(LogConstant.TRANSFER_IN_LOG_FILE, TransferInController.class,
						"Transfer In Error: " + result.getAllErrors());
				ObjectError objectError = result.getAllErrors().get(0);
				attributes.addFlashAttribute("transferInModel", postTransferInModel);
				attributes.addFlashAttribute("errors", objectError.getDefaultMessage());
				return "redirect:/mb/errorPageTransferIn";
			} else {
				DODLog.info(LogConstant.TRANSFER_IN_LOG_FILE, TransferInController.class,
						"[saveTransferOut] ## Saving Transfer BEFORE :::::: "+postTransferInModel);
				TransferInResponse responce=transferInService.saveTransferIn(postTransferInModel);
				
				if (responce != null && responce.getErrorCode() == 200 ) {
					
					attributes.addFlashAttribute("success", responce.getErrorMessage());
					
			
					return "redirect:/mb/confirmationPage";
				}
				 else {
						attributes.addFlashAttribute("errors", responce == null ? "" : responce.getErrorMessage());
						return "redirect:/mb/errorPageTransferIn";
					}
				}

		} catch (Exception e) {
			DODLog.printStackTrace(e, TransferInController.class, LogConstant.TRANSFER_IN_LOG_FILE);
			attributes.addFlashAttribute("errors", "Error while creating User");
		}
		return "redirect:/mb/transferIn";
	}

	@PostMapping("/getDetailBypersonalNo")
	public ResponseEntity<TransferIN> getDetailBypersonalNo(@RequestParam String userAlias,
			HttpServletRequest request) {
        TransferIN transferIN = transferInService.getDetailBypersonalNo(userAlias, request);
        DODLog.info(LogConstant.TRANSFER_IN_LOG_FILE, TransferInController.class,"  [getDetailBypersonalNo] response:: "+userAlias+"-->"+transferIN);
		return ResponseEntity.ok(transferIN);

	}

	// Ajax call to get Station Code
	@GetMapping("/getStationNRSIn")
	@ResponseBody
	public List<String> searchStationIn(@RequestParam String station) {

		return transferInService.getStations(station);
	}

	@GetMapping("/getSPRStationIn")
	@ResponseBody
	public List<String> sprStationIn(@RequestParam String station) {

		return transferInService.getStations(station);
	}

	@GetMapping("/getSPRNRSIn")
	@ResponseBody
	public List<String> sprNRSStationIn(@RequestParam String station) {

		return transferInService.getStations(station);
	}

	// Ajax call to get Airport Code
	@GetMapping("/getNADutyStationIn")
	@ResponseBody
	public List<String> searchAirportIn(@RequestParam String airPortName) {

		return transferInService.getAirports(airPortName);
	}

	@GetMapping("/getSPRAirportIn")
	@ResponseBody
	public List<String> getSPRAirportIn(@RequestParam String airPortName) {

		return transferInService.getAirports(airPortName);
	}

}
