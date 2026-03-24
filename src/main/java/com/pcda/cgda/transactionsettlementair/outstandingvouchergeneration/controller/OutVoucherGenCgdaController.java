package com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.controller;



import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model.AfterVoucherGenCgdaResponse;
import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model.GetOutVoucherGenCgdaDataModel;
import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model.OutVoucherGenCgdaReqModel;
import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model.PopUpParamCgdaModel;
import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model.PostOutStandVoucherGenCgdaModel;
import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model.VoucherTxnCgdaModel;
import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.service.OutVoucherCgdaService;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cgda")
public class OutVoucherGenCgdaController {

	

	@Autowired

	private MasterServices masterService;
	
	@Autowired
	private OfficesService officeService;
	
	@Autowired
	private TravelTypeServices travelType;
	@Autowired
	private OutVoucherCgdaService voucherService; 
	

	
@GetMapping("/outstandingVoucherGenCgda")	
public String outstandingGen(Model model,HttpServletRequest request) {
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();
	
	 if (loginUser == null) {
		return "redirect:/login";
	}
	Optional<OfficeModel> office =  officeService.getOfficeByUserId(loginUser.getUserId());
	
	 String groupId = "";
	 if(office.isPresent()) {
		 groupId = office.get().getGroupId();
	 }
	
	
	model.addAttribute("serviceList", masterService.getServicesByApprovalState("1"));
	model.addAttribute("unitList",officeService.getOffices("UNIT","1"));
	model.addAttribute("travelTypeList",travelType.getAllTravelType(1));
	model.addAttribute("accountMap",voucherService.getAccountOfficeListMap("CGDA", groupId));
	model.addAttribute("formModelData",new OutVoucherGenCgdaReqModel());
return "/CGDA/transactionsettlementair/outstandingvouchergeneration/outvouchergencgda";	
}

@PostMapping("/getVoucherDataCgda")
public String getVoucherdata(Model model , OutVoucherGenCgdaReqModel genReqModel,HttpServletRequest request ) {
	
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();
	
	 if (loginUser == null) {
		return "redirect:/login";
	}
	 
	 genReqModel.setLoginUserId(loginUser.getUserId());
	 
	Optional<OfficeModel> office =  officeService.getOfficeByUserId(loginUser.getUserId());
	
	 String groupId = "";
	 if(office.isPresent()) {
		 groupId = office.get().getGroupId();
	 }
	model.addAttribute("serviceList", masterService.getServicesByApprovalState("1"));
	model.addAttribute("unitList",officeService.getOffices("UNIT","1"));
	model.addAttribute("travelTypeList",travelType.getAllTravelType(1));
	model.addAttribute("accountMap",voucherService.getAccountOfficeListMap("CGDA", groupId));
	model.addAttribute("formModelData",genReqModel);

	List<GetOutVoucherGenCgdaDataModel>	 listModel = voucherService.getGenerateReportData(genReqModel);
	if(!listModel.isEmpty()) {
	VoucherTxnCgdaModel txnModel=new VoucherTxnCgdaModel();
	txnModel.setDataModels(listModel);
	if(txnModel.getDataModels().isEmpty()) {
		model.addAttribute("noResult", "No Record Found !!");
	}
	model.addAttribute("dataList", txnModel.getDataModels());
	
	model.addAttribute("listSize", txnModel.getDataModels().size());
	
	HttpSession session= request.getSession();
	
	session.setAttribute("voucherTxnData", txnModel);
	session.setAttribute("formData", genReqModel);
	}else {
		model.addAttribute("noData", "No Record Found !!");
	}
	
	return "/CGDA/transactionsettlementair/outstandingvouchergeneration/outvouchergencgda";	
}


// csv download 
@GetMapping("/getDownloadCgda")
@ResponseBody
public String getdataForDownload(HttpServletRequest request,HttpServletResponse response)  {
	
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();
	
	 if (loginUser == null) {
		return "redirect:/login";
	}
	
	HttpSession session= request.getSession();
	Object data= session.getAttribute("voucherTxnData");
	
	if(null!=data && data instanceof VoucherTxnCgdaModel txnModel) {
		
		List<GetOutVoucherGenCgdaDataModel> dataModels=txnModel.getDataModels();
		
		DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, OutVoucherGenCgdaController.class,
				"MODEL LIST :::::::"+dataModels);	
		response.setContentType("text/csv");
		Calendar cal=Calendar.getInstance();
		  cal.setTime(new Date());
		  String fileName="DueAmountReport_"+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH)+".csv";
		  response.setHeader("Content-Disposition", "attachment;filename="+fileName);
		try {
			voucherService.createCSVDownload(response.getWriter(), dataModels);
		}
		catch(Exception e) {
			DODLog.printStackTrace(e, OutVoucherGenCgdaController.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
		}
	}
	
	return "";
}




//ajax get grade pay based on category and serviceId
	@PostMapping("/popupViewCgda")
	public String getPopUpView(Model model ,HttpServletRequest request ) {

		HttpSession session= request.getSession();
		Object data= session.getAttribute("formData");
		
		OutVoucherGenCgdaReqModel reqModel = new OutVoucherGenCgdaReqModel();
		if(null!=data && data instanceof OutVoucherGenCgdaReqModel outModel) {
			
		 reqModel = outModel;
		
		}
		
		model.addAttribute("reqModel",reqModel);
		
		
		List<PopUpParamCgdaModel> modelList= voucherService.voucherGenerationSummaryDataInXML(request);
		 
		DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, OutVoucherGenCgdaController.class,
				"MODEL LIST FOR POP UP  :::::::"+modelList);	
		
		model.addAttribute("popupDataList",modelList);
		
	 return "/CGDA/transactionsettlementair/outstandingvouchergeneration/outvouchergenpopupcgda";	
	
	

	}
	
	// send Data For Save
@PostMapping("/generateOutStandingVoucherCgda")	
public String generateVoucher(@RequestParam List<String> txnsId,HttpServletRequest request,RedirectAttributes redirectAttributes,Model model) {
	
	try
	{
	HttpSession session= request.getSession();
	Object data= session.getAttribute("formData");
	
	OutVoucherGenCgdaReqModel reqModel = new OutVoucherGenCgdaReqModel();
	if(null!=data && data instanceof OutVoucherGenCgdaReqModel outModel) {
		
	 reqModel = outModel;
	
	}
	
	
	PostOutStandVoucherGenCgdaModel  postModel = voucherService.getPopUpModelList(txnsId,reqModel); 
	
	DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, OutVoucherGenCgdaController.class,
			"POST MODEL FROM POPUP  :::::::"+postModel);	
	
	if (postModel.getLoginUserId() == null) {
		return "redirect:/login";
	}
	
	AfterVoucherGenCgdaResponse response =   voucherService.sendDataForsave(postModel);
	
	DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE, OutVoucherGenCgdaController.class,
			"RESPONSE AFTER SAVE IN OUT VOUCHER GEN :::::::"+response);	
	
	if(response!=null && response.getErrorCode()==200) {
		model.addAttribute("responseData",response.getResponse());
	
	return "/CGDA/transactionsettlementair/outstandingvouchergeneration/outvouchergenviewcgda";
	}else {
		return "/common/errorPage";
	}
	}catch(Exception e) {
		DODLog.printStackTrace(e, OutVoucherGenCgdaController.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
	}
	
	return "/common/errorPage";
}


}
