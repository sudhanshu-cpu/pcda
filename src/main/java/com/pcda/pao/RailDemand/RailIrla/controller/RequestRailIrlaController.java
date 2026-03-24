package com.pcda.pao.RailDemand.RailIrla.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
import com.pcda.pao.RailDemand.RailIrla.model.IRLAAdjustmentModel;
import com.pcda.pao.RailDemand.RailIrla.model.IRLAMasterMissingDataModel;
import com.pcda.pao.RailDemand.RailIrla.model.IRLAPostRequestData;
import com.pcda.pao.RailDemand.RailIrla.model.IRLAResponseData;
import com.pcda.pao.RailDemand.RailIrla.service.RequestRailIrlaService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pao")
public class RequestRailIrlaController {
	
	@Autowired
	private RequestRailIrlaService reqRailIrlaService;
	
	
	@RequestMapping(value = "/reqForRailIrla",method = {RequestMethod.GET,RequestMethod.POST})	
	public String reqForRailIrla(Model model,IRLAPostRequestData irlaPostRequestData,HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		 if (loginUser == null) {
			return "redirect:/login"; 
		}

		irlaPostRequestData.setLoginUserId(loginUser.getUserId());
		IRLAResponseData irlaResponseData = reqRailIrlaService.getReqRailIrla(irlaPostRequestData);
		 Map<String,Object> result = CommonUtil.getYearAndMonthData();
		
		
	    model.addAttribute("data", irlaResponseData);
	    model.addAttribute("yearList", result.get("year"));
	    model.addAttribute("monthMap", result.get("month"));
	    model.addAttribute("serviceMap",PcdaConstant.SERVICE_MAP);
		
	return "/PAO/RailDemand/RailIrla/reqForRailIrla";	
	}
	
	
	@ResponseBody
	@GetMapping("/getRailIrlaCount")
	public Integer getRailIrlaCount(Model model,HttpServletRequest request) {
		IRLAPostRequestData irlaPostRequestData = new IRLAPostRequestData();
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		 
		irlaPostRequestData.setFromDate(Optional.ofNullable(request.getParameter("fromDate")).orElse(""));
		irlaPostRequestData.setToDate(Optional.ofNullable(request.getParameter("toDate")).orElse(""));
		irlaPostRequestData.setServiceId(Optional.ofNullable(request.getParameter("serviceId")).orElse(""));
		irlaPostRequestData.setLoginUserId(loginUser.getUserId());
		
		return reqRailIrlaService.getReqRailIrlaCount(irlaPostRequestData);
	}
	
	@PostMapping("/irlaMasterMissingProcess")
	public String getMasterMissingData(Model model, HttpServletRequest request) {
		List<IRLAMasterMissingDataModel> irlaMasterMissingList = reqRailIrlaService
				.getIrlaMasterMissingData(request.getParameter("dwnIrlaReqIdMm"));

		model.addAttribute("personalNoInfo", irlaMasterMissingList);
		model.addAttribute("dwnIrlaReqIdMm", request.getParameter("dwnIrlaReqIdMm"));
		model.addAttribute("dwnRequestStatus", request.getParameter("dwnIrlaReqStatusMm"));

		return "/PAO/RailDemand/RailIrla/irlaMasterMissingProcessForm";
	}
	
	@PostMapping("/reqForIrlaAdjustment")
	public String railIrlaAdjustment(IRLAAdjustmentModel adjustmentModel, Model model,
			HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		adjustmentModel.setLoginUserId(loginUser.getUserId());
		String adjustmentMsg = reqRailIrlaService.railIrlaAdjustment(adjustmentModel);

		model.addAttribute("adjustmentMsg", adjustmentMsg);
		return "redirect:/pao/reqForRailIrla";

	}
	
	@PostMapping("/sentReqForIrlaMM")
	public String sentIrlaMasterMissing(Model model, @RequestParam List<String> dataForMM, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("message", reqRailIrlaService.sentIrlaMasterMissing(dataForMM, loginUser.getUserId()));

		return "/PAO/RailDemand/RailIrla/irlaMasterMissingProcessed";
	}
	
	@PostMapping("/sentNoReqForIrlaMM")
	public String sentNoIrlaMasterMissing(Model model, @RequestParam String dwnIrlaReqIdMm,
			HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("message",
				reqRailIrlaService.sentNoIrlaMasterMissing(dwnIrlaReqIdMm, loginUser.getUserId()));

		return "/PAO/RailDemand/RailIrla/irlaMasterMissingProcessed";
	}
	
	@GetMapping("/downloadIrlaReqData")
	public ResponseEntity<Object> downloadFile(@RequestParam String dwnReqId) throws IOException {
			String dwnFilePath="";

		dwnFilePath = reqRailIrlaService.getFilePathByDwnReqId(dwnReqId);
		File file = null;
		InputStreamResource resource = null;
		try {
			file = new File(dwnFilePath);
			resource = new InputStreamResource(new FileInputStream(file));
		} catch (Exception e) {
			DODLog.printStackTrace(e, RequestRailIrlaController.class, LogConstant.COMMON_LOG);
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
