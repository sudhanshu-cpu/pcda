package com.pcda.sao.airdemand.airdemandrequest.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.pcda.sao.airdemand.airdemandrequest.model.DemandDownloadPostModel;
import com.pcda.sao.airdemand.airdemandrequest.model.DemandDownloadPostResponseModel;
import com.pcda.sao.airdemand.airdemandrequest.model.DemandDwnReqModel;
import com.pcda.sao.airdemand.airdemandrequest.service.AirDemandRequestService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/sao")
public class AirDemandRequestController {

	
	private String path = "/SAO/airdemand/AirDemandRequest/";

	@Autowired
	OfficesService  officesService;
	
	@Autowired
	AirDemandRequestService airDemandRequestService; 
	
	@GetMapping("/createDemandRequest")
	public String getPage( Model model,HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		String serviceId  = loginUser.getServiceId();
		BigInteger userID   = loginUser.getUserId();
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String toDate = dateFormat.format(calendar.getTime());
		Optional<OfficeModel> office   = officesService.getOfficeByUserId(userID);
		OfficeModel officeModel = new OfficeModel();
		
		if (office.isPresent()) {
			officeModel = office.get();
		}
		Boolean flag = officeModel.getName().equalsIgnoreCase("UNIT");
		String groupId = "";
		if (Boolean.FALSE.equals(flag)) {
			groupId = officeModel.getGroupId();
		}
		
		DemandDwnReqModel  demandList= airDemandRequestService.getDemandDownloadRequest( flag, serviceId, groupId);
		model.addAttribute("toDate",toDate);
		model.addAttribute( "demandList", demandList);
		model.addAttribute("serviceId " ,serviceId);
		model.addAttribute("groupId" ,groupId );
         
		return path + "airDemand";
	}
	
	
	        //Get Mapping to download VoucherForms
			@GetMapping("/downloadDemandForm")
			public void downloadVoucherForms(@RequestParam String requestId, HttpServletResponse response) throws IOException{
				
				DODLog.info(LogConstant.AIR_TRANSCATION_LOG, AirDemandRequestController.class, "downloadVoucherForms() requestId"+ requestId);
				airDemandRequestService.downloadDemandForms(requestId, response);
				
			}
			
			
			// Controller Mapping to Create DemandRequest using POST API
			@PostMapping("/postDemandGeneration")
			public String postDemandGeneration(@ModelAttribute DemandDownloadPostModel saveDemandModel,
					HttpServletRequest request,   RedirectAttributes attributes) {
				SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
				LoginUser loginUser = sessionVisitor.getLoginUser();
				BigInteger loginUserId = loginUser.getUserId();

				String serviceId = loginUser.getServiceId();
				Optional<OfficeModel> office = officesService.getOfficeByUserId(loginUserId);
				OfficeModel officeModel = new OfficeModel();

				if (office.isPresent()) {
					officeModel = office.get();
				}
				Boolean flag = officeModel.getName().equalsIgnoreCase("UNIT");
				String groupId = "";
				if (Boolean.FALSE.equals(flag)) {
					groupId = officeModel.getGroupId();
				}

				saveDemandModel.setLoginUserId(loginUserId);
				saveDemandModel.setServiceId(serviceId);
				saveDemandModel.setGroupId(groupId);
				saveDemandModel.setFlag(flag);
				
				DODLog.info(LogConstant.AIR_TRANSCATION_LOG, AirDemandRequestController.class,
						"SaveDemandGeneration model"+saveDemandModel);
				DemandDownloadPostResponseModel response =airDemandRequestService.createDemandDownload(saveDemandModel);
		      	String errorMessage = response.getErrorMessage();
				attributes.addFlashAttribute("errorMessage", errorMessage);
				return "redirect:createDemandRequest";

			}
						
			
			//AJAX call to get SAO Records Count
			@PostMapping("/checkSAORecordCount")
			@ResponseBody
			public ResponseEntity<Long> countSAO(@ModelAttribute DemandDownloadPostModel demandDwnPostModel,
					HttpServletRequest request) {
				SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
				LoginUser loginUser = sessionVisitor.getLoginUser();
				BigInteger userID = loginUser.getUserId();

				String serviceId = loginUser.getServiceId();
				
				Optional<OfficeModel> office = officesService.getOfficeByUserId(userID);
				OfficeModel officeModel = new OfficeModel();

				if (office.isPresent()) {
					officeModel = office.get();
				}
				Boolean flag = officeModel.getName().equalsIgnoreCase("UNIT");
				String groupId = "";
				if (Boolean.FALSE.equals(flag)) {
					groupId = officeModel.getGroupId();
				}
				demandDwnPostModel.setGroupId(groupId);
				demandDwnPostModel.setServiceId(serviceId);
				demandDwnPostModel.setFlag(flag);
				demandDwnPostModel.setLoginUserId(userID);
				DODLog.info(LogConstant.AIR_TRANSCATION_LOG, AirDemandRequestController.class,
						"Ajax countSAO model "+demandDwnPostModel);
				Long count =airDemandRequestService.getSAORecordCount(demandDwnPostModel);	
				return ResponseEntity.ok(count);
			}
			
	
}
