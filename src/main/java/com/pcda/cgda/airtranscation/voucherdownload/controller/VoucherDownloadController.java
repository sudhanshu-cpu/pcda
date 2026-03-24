package com.pcda.cgda.airtranscation.voucherdownload.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

import com.pcda.cgda.airtranscation.voucherdownload.model.GenerateDemandPostModel;
import com.pcda.cgda.airtranscation.voucherdownload.model.GetCountAmountModel;
import com.pcda.cgda.airtranscation.voucherdownload.model.VocuherPostResponseModel;
import com.pcda.cgda.airtranscation.voucherdownload.model.VoucherPostModel;
import com.pcda.cgda.airtranscation.voucherdownload.model.VoucherReqModel;
import com.pcda.cgda.airtranscation.voucherdownload.model.VoucherSettlePostModel;
import com.pcda.cgda.airtranscation.voucherdownload.service.VoucherDownloadService;
import com.pcda.common.model.DODServices;
import com.pcda.common.model.EnumType;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.TravelType;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/cgda")
public class VoucherDownloadController {
	
	@Autowired
	private VoucherDownloadService voucherDownloadService;

	private String pageURL = "/CGDA/airtranscation/VoucherDownload/";


	@GetMapping("/voucher")
	public String voucherDownload(@RequestParam(required=false, defaultValue = "1") int pageNumber,
								  @RequestParam(required=false, defaultValue = "10") int pageSize,
								  @RequestParam(required = false) String serviceProviderType,
								  Model model, HttpServletRequest request) {
		try {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}

		Boolean isError = false;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String toDate = dateFormat.format(calendar.getTime());

		Calendar calendarNew = Calendar.getInstance();
		DateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
		String currentDate = dateFormat1.format(calendarNew.getTime());
		VoucherReqModel voucherReqModel = null;

		List<DODServices> armedServices = voucherDownloadService.getAllServices("1");
		List<TravelType> travelType = voucherDownloadService.getAllTravelType(1);
		List<OfficeModel> pao = voucherDownloadService.getAllPao();
		List<EnumType> enumType = voucherDownloadService.getVendorList("AIR_SERVICE_PROVIDER_TYPE");

			voucherReqModel = voucherDownloadService.getAllVoucher(pageNumber,pageSize,serviceProviderType);

			isError = voucherReqModel == null;

		if (Boolean.TRUE.equals(isError)) {
			model.addAttribute("error", "No results Found");
		}
		model.addAttribute("travelerServiceList", armedServices);
		model.addAttribute("travelTypeList", travelType);
		model.addAttribute("paoList", pao);
		model.addAttribute("toDate", toDate);
		model.addAttribute("currentDate", currentDate);
		model.addAttribute("vendorEnumType", enumType);
            if(voucherReqModel != null) {
			model.addAttribute("voucherList", voucherReqModel);
			 }else {
				model.addAttribute("errorMessage","No Records Found");
			}
			model.addAttribute("currentPage", pageNumber);
			model.addAttribute("pageSize", pageSize);
			if(serviceProviderType != null){
				model.addAttribute("serviceProvider",serviceProviderType);
			}
         DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherDownloadController.class, currentDate);
		}catch (Exception e){
			DODLog.printStackTrace(e, VoucherDownloadController.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
		}
		return pageURL  + "VoucherDownload";
	}


		@PostMapping("/saveVoucher")
		public String saveVoucher(VoucherPostModel voucherPostModel, Model model, HttpServletRequest request,RedirectAttributes attribute) {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();
			if (loginUser == null) {
				return "redirect:/login";
			}
			BigInteger loginUserId = loginUser.getUserId();
			voucherPostModel.setLoginUserId(loginUserId);
			VocuherPostResponseModel voucherPostModelResponse=voucherDownloadService.createVoucher(voucherPostModel);
			if (voucherPostModelResponse != null && voucherPostModelResponse.getErrorCode() == 200
					&& voucherPostModelResponse.getResponse() != null) {
				attribute.addFlashAttribute("message", "voucher created successfully.");
			    return "redirect:voucher";
			}else {
				attribute.addFlashAttribute("message",voucherPostModelResponse==null ?"":voucherPostModelResponse.getErrorMessage());
				return "redirect:voucher";
			}
			
			
		}
     
		//AJAX call to get Records
		@PostMapping("/checkCount")
		@ResponseBody
		public ResponseEntity<GetCountAmountModel> countVoucher(@ModelAttribute VoucherPostModel data,HttpServletRequest request) {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();
			BigInteger loginUserId = loginUser.getUserId();
			data.setLoginUserId(loginUserId);
			return ResponseEntity.ok(voucherDownloadService.getRecordCount(data));
		}
		
		//Get Mapping to download VoucherForms
		@GetMapping("/getForms")
		public ResponseEntity<Object> downloadVoucherForms(@RequestParam String dwnReqId, @RequestParam String requestFor, HttpServletResponse response1) throws IOException {
	         DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherDownloadController.class,"dwnReqId :: "+ dwnReqId +" | requestFor:: "+requestFor);
			String path = voucherDownloadService.downloadVoucherForms(dwnReqId, requestFor);	
			File file = new File(path);
			InputStreamResource resource=null;
			try {
		       resource = new InputStreamResource(new FileInputStream(file));
			}catch(Exception e) {
				DODLog.printStackTrace(e, VoucherDownloadController.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
			}
			 HttpHeaders headers = new HttpHeaders();
			 headers.add("Content-Disposition", String.format("attachement; filename=\"%s\"", file.getName()));
			 headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			 headers.add("Pragma", "no-cahce");
			 headers.add("Expires", "0");

			 String contentType = "";
			 if(file.getName().toLowerCase().endsWith(".xls") || file.getName().toLowerCase().endsWith(".xlsx")){
				 contentType = "application/octet-stream";
			  }else if(file.getName().toLowerCase().endsWith(".txt")){
				  contentType = "text/plain";
			  }else if(file.getName().toLowerCase().endsWith(".pdf")){
				  contentType = "application/pdf";
			  }else if(file.getName().toLowerCase().endsWith(".csv")){
				  contentType = "text/csv";
			  }else{
				  contentType = "application/octet-stream"; 
			  }

			 return ResponseEntity.ok().headers(headers).contentLength(file.length())
					 .contentType(MediaType.parseMediaType(contentType)).body(resource);
			
		}
		
		//Post Mapping for the VoucherSettlement
		@PostMapping("/postVoucherSettle")
		public String postSettleVoucher(@ModelAttribute VoucherSettlePostModel  voucherSettlePostModel  ,HttpServletRequest request, Model model) {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();
			BigInteger loginUserId = loginUser.getUserId();
			voucherSettlePostModel.setLoginUserId(loginUserId);
			
			
		
	         DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherDownloadController.class, " Post Voucher Settle :: "+voucherSettlePostModel);
	         model.addAttribute("errorMessage", voucherDownloadService.settleVoucher(voucherSettlePostModel)) ;
			
			return pageURL  + "Success";
	
		}
		
		
		//PostMapping for submit Demand
		@PostMapping("/postDemandGeneration")
		public String generateDemand( @RequestParam String dwnRequestId , @ModelAttribute GenerateDemandPostModel generateDemandPostModel ,HttpServletRequest request,Model model   ) {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();
			if (loginUser == null) {
				return "redirect:/login";
			}
			DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherDownloadController.class, " generateDemandPostModel:: "+generateDemandPostModel + " | dwnRequestId"+dwnRequestId);
			model.addAttribute("errorMessage",voucherDownloadService.demandGen(generateDemandPostModel, dwnRequestId) );
			
		
			return pageURL  + "Success";
		}
		
		
		
}
