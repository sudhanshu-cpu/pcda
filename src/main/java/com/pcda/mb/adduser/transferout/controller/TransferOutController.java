package com.pcda.mb.adduser.transferout.controller;

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
import com.pcda.mb.adduser.transferout.model.PostTransferOutModel;
import com.pcda.mb.adduser.transferout.model.TransferOut;
import com.pcda.mb.adduser.transferout.model.TransferOutResponse;
import com.pcda.mb.adduser.transferout.service.TransferOutServiceMB;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mb")
public class TransferOutController {

	private String url = "/MB/AddUsers/TransferOut";

	@Autowired
	private TransferOutServiceMB transferOutServiceMB;
	
	@Autowired
	private OfficesService userService;

	@GetMapping("/transferOutMB")
	public String transferOutServiceMB(Model model, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		List<OfficeModel> payAccountOffices = userService.getOffices("PAO", "1");

		Optional<OfficeModel> office = transferOutServiceMB.getOffices(loginUser.getUserId());
		String unitName = "";
		String unitCode = "";
		String locationId = "";
		if (office.isPresent()) {
			unitName = office.get().getName();
			unitCode = office.get().getGroupId();
			locationId = office.get().getLocationTypeId();
		}

		model.addAttribute("locationId", locationId);
		model.addAttribute("unitCode", unitCode);
		model.addAttribute("unitName", unitName);
		model.addAttribute("unitsList", transferOutServiceMB.getUnitList());
		model.addAttribute("joiningDate", loginUser.getJoiningDate());
		model.addAttribute("visitorServiceId", loginUser.getServiceId());
		model.addAttribute("userServiceId", loginUser.getUserId());
		model.addAttribute("PaoList", payAccountOffices);
		
	

		return url + "/transferout";
	}

	@GetMapping("/confirmPage")
	public String confimPage(Model model,HttpServletRequest request) {
		return url + "/conformationPage";
	}

	@GetMapping("/errorPageTransferOut")
	public String errorPageTransferOut(Model model,HttpServletRequest request) {
		return url + "/errorPage";
	}

	@PostMapping("/saveTransferOut")
	public String saveTransferOut(@ModelAttribute @Valid PostTransferOutModel postTransferOutModel,
			BindingResult result, RedirectAttributes attributes) {
		
		DODLog.info(LogConstant.TRANSFER_OUT_LOG_FILE, TransferOutController.class,	"<--[saveTransferOut] PostTransferOutModel " + postTransferOutModel);

		
		try {
			if (result.hasErrors()) {
				DODLog.error(LogConstant.TRANSFER_OUT_LOG_FILE, TransferOutController.class,"[saveTransferOut] Transfer Out Error: "+postTransferOutModel.getUserId()+"-->"  + result.getAllErrors());
				ObjectError objectError = result.getAllErrors().get(0);
				attributes.addFlashAttribute("transferOutModel", postTransferOutModel);
				attributes.addFlashAttribute("errors", objectError.getDefaultMessage());
				return "redirect:/mb/errorPageTransferOut";
			} else {
				
				TransferOutResponse transferOutResponce=transferOutServiceMB.saveTransferOut(postTransferOutModel);
				DODLog.info(LogConstant.TRANSFER_OUT_LOG_FILE, TransferOutController.class,	"[saveTransferOut] @@@ TransferOutResponse : "+postTransferOutModel.getUserId()+"-->"  + transferOutResponce);

				if (transferOutResponce != null && transferOutResponce.getErrorCode() == 200) {
						attributes.addFlashAttribute("success", transferOutResponce.getErrorMessage());
						return "redirect:confirmPage";
					} else {
						attributes.addFlashAttribute("errors", transferOutResponce != null ? transferOutResponce.getErrorMessage():"Error Occured");
						return "redirect:errorPageTransferOut";
				}
				
			}

		} catch (Exception e) {
			
			DODLog.printStackTrace(e, TransferOutController.class, LogConstant.TRANSFER_OUT_LOG_FILE);
			attributes.addFlashAttribute("errors", "Error while creating User");
		}
		return "redirect:transferOutMB";
	}

	@PostMapping("/getUnitByPersonalNo")
//	@ResponseBody
	public ResponseEntity<TransferOut> getUnitByPersonalNo(@RequestParam String userAlias, HttpServletRequest request) {
		if(!userAlias.trim().equals("")) {
	TransferOut	transferOut = transferOutServiceMB.getPersonalDetails(userAlias, request);
	DODLog.info(LogConstant.TRANSFER_OUT_LOG_FILE, TransferOutController.class,	"[getUnitByPersonalNo] @@@ Response TransferOut : "+transferOut);
		return ResponseEntity.ok(transferOut);
		}
	return ResponseEntity.ok(new TransferOut());

	}

	// Ajax call to get Station Code
	@GetMapping("/getStationNRS")
	@ResponseBody
	public List<String> searchStation(@RequestParam String station) {

		return transferOutServiceMB.getStations(station);
	}

	@GetMapping("/getSPRStation")
	@ResponseBody
	public List<String> sprStation(@RequestParam String station) {

		return transferOutServiceMB.getStations(station);
	}

	@GetMapping("/getSPRNRS")
	@ResponseBody
	public List<String> sprNRSStation(@RequestParam String station) {

		return transferOutServiceMB.getStations(station);
	}

	// Ajax call to get Airport Code
	@GetMapping("/getNADutyStation")
	@ResponseBody
	public List<String> searchAirport(@RequestParam String airPortName) {

		return transferOutServiceMB.getAirports(airPortName);
	}

	@GetMapping("/getSPRAirport")
	@ResponseBody
	public List<String> getSPRAirport(@RequestParam String airPortName) {

		return transferOutServiceMB.getAirports(airPortName);
	}

}
