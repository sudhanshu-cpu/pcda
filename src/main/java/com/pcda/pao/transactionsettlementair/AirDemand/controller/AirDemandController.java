package com.pcda.pao.transactionsettlementair.AirDemand.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.pao.transactionsettlementair.AirDemand.model.AirDemandResponseData;
import com.pcda.pao.transactionsettlementair.AirDemand.model.AirDmndPostRequestData;
import com.pcda.pao.transactionsettlementair.AirDemand.model.AirMasterMissingDataModel;
import com.pcda.pao.transactionsettlementair.AirDemand.service.AirDemandReqService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pao")
public class AirDemandController {

	@Autowired
	private AirDemandReqService airDemandReqService ;

	@RequestMapping(value = "/reqForAirDemandDwnl",method = {RequestMethod.GET,RequestMethod.POST})	
	public String reqForRailIrla(Model model,AirDmndPostRequestData airPostRequestData,HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		 if (loginUser == null) {
			return "redirect:/login";
		}
		
			
		 airPostRequestData.setLoginUserId(loginUser.getUserId());
		AirDemandResponseData ResponseData = airDemandReqService.getReqAirDmnd(airPostRequestData); 
	    model.addAttribute("data", ResponseData);
	    model.addAttribute("serviceMap",PcdaConstant.SERVICE_MAP);
		
	return "/PAO/transactionsettlementair/AirDemand/reqForAirDmnd";	
	}
	
	
	@ResponseBody
	@GetMapping("/getAirDemandCount")
	public Integer getRailIrlaCount(Model model,HttpServletRequest request) {
		AirDmndPostRequestData airPostRequestData = new AirDmndPostRequestData();
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		 
		airPostRequestData.setFromDate(Optional.ofNullable(request.getParameter("fromDate")).orElse(""));
		airPostRequestData.setToDate(Optional.ofNullable(request.getParameter("toDate")).orElse(""));
		airPostRequestData.setLoginUserId(loginUser.getUserId());
		airPostRequestData.setServiceId(Optional.ofNullable(request.getParameter("serviceId")).orElse(""));
		
		return airDemandReqService.getReqAirDmndCount(airPostRequestData);
	}
	
	@PostMapping("/airDemandMasterMissingProcess")
	public String getAirMasterMissingData(Model model, HttpServletRequest request) {
		List<AirMasterMissingDataModel> airMasterMissingList = airDemandReqService
				.getAirMasterMissingData(request.getParameter("dwnAirReqIdMm"));

		model.addAttribute("personalNoInfo", airMasterMissingList);
		model.addAttribute("dwnAirReqIdMm", request.getParameter("dwnAirReqIdMm"));
		model.addAttribute("dwnAirRequestStatus", request.getParameter("dwnAirReqStatusMm"));

		return  "/PAO/transactionsettlementair/AirDemand/airMasterMissingProcessForm";
	}
	
	@PostMapping("/sentReqForAirDemandMM")
	public String sentAirMasterMissingData(Model model, @RequestParam List<String> dataForMM, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("message", airDemandReqService.sentAirDmndMasterMissing(dataForMM, loginUser.getUserId()));

		return "/PAO/transactionsettlementair/AirDemand/airMasterMissingProcessed";
	}
	
	@PostMapping("/sentNoReqForAirDemandMM")
	public String sentNoAirMasterMissingData(Model model, @RequestParam String airDmndReqIdForMm,
			HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("message",
				airDemandReqService.sentNoAirDmndMasterMissing(airDmndReqIdForMm, loginUser.getUserId()));

		return "/PAO/transactionsettlementair/AirDemand/airMasterMissingProcessed";
	}
	@PostMapping("/reqForDrAirDemandGenerate")
	public String reqForDrAirDemandGenerate(Model model, @RequestParam String dwnRequestId,
			HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("message",airDemandReqService.reqForDrDemandGenerate(dwnRequestId, loginUser.getUserId()));

		return "redirect:/pao/reqForAirDemandDwnl";
	}
	

	 
	@GetMapping("/downloadAirDmndReqData")
	public ResponseEntity<Object> downloadFile(@RequestParam String dwnReqId) throws IOException {
		String dwnFilePath="";
		
		dwnFilePath = airDemandReqService.getFilePathByDwnReqId(dwnReqId);
		File file = null;
		InputStreamResource resource = null;
		try {
			file = new File(dwnFilePath);
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (Exception e) {
			DODLog.printStackTrace(e, AirDemandController.class, LogConstant.COMMON_LOG);
		}
		
		if(null==file) {
			return null;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachement; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cahce");
		headers.add("Expires", "0");

		String contentType = "";
		if (file.getName().toLowerCase().endsWith(".xls")) {
			contentType = "application/octet-stream";
		}else if (file.getName().toLowerCase().endsWith(".xlsx")) {
			contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		} else if (file.getName().toLowerCase().endsWith(".txt")) {
			contentType = "text/plain";
		} else if (file.getName().toLowerCase().endsWith(".pdf")) {
			contentType = "application/pdf";
		} else if (file.getName().toLowerCase().endsWith(".csv")) {
			contentType = "text/csv";
		} else {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType(contentType)).body(resource);
	}
	
}
