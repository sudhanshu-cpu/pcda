package com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.controller;



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

import com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.model.AfterVoucherGenCdaResponse;
import com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.model.GetOutVoucherGenCdaDataModel;
import com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.model.OutVoucherGenCdaReqModel;
import com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.model.PopUpParamCdaModel;
import com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.model.PostOutStandVoucherGenCdaModel;
import com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.model.VoucherTxnCdaModel;
import com.pcda.cda.transactionsettlementair.outstandingvouchergeneration.service.OutVoucherCdaService;
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
@RequestMapping("/cda")
public class OutVoucherGenCdaController {

	

	@Autowired

	private MasterServices masterService;
	
	@Autowired
	private OfficesService officeService;
	
	@Autowired
	private TravelTypeServices travelType;
	@Autowired
	private OutVoucherCdaService voucherService; 
	

	
@GetMapping("/outstandingVoucherGenCda")	
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
	model.addAttribute("accountMap",voucherService.getAccountOfficeListMap("CDA", groupId));
	model.addAttribute("formModelData",new OutVoucherGenCdaReqModel());
return "/CDA/transactionsettlementair/outstandingvouchergeneration/outvouchergencda";	
}

@PostMapping("/getVoucherDataCda")
public String getVoucherdata(Model model , OutVoucherGenCdaReqModel genReqModel,HttpServletRequest request ) {
	
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
	model.addAttribute("accountMap",voucherService.getAccountOfficeListMap("CDA", groupId));
	model.addAttribute("formModelData",genReqModel);

	List<GetOutVoucherGenCdaDataModel> listModel = voucherService.getGenerateReportData(genReqModel);
	if(!listModel.isEmpty()) {
	VoucherTxnCdaModel txnModel=new VoucherTxnCdaModel();
	txnModel.setDataModels(voucherService.getGenerateReportData(genReqModel));
	
	model.addAttribute("dataList", txnModel.getDataModels());
	
	model.addAttribute("listSize", txnModel.getDataModels().size());
	
	HttpSession session= request.getSession();
	
	session.setAttribute("voucherTxnData", txnModel);
	session.setAttribute("formData", genReqModel);
	}else {
		model.addAttribute("noData", "No Record Found !!");
	}
	
	return "/CDA/transactionsettlementair/outstandingvouchergeneration/outvouchergencda";	
}


// csv download 
@GetMapping("/getDownloadCda")
@ResponseBody
public String getdataForDownload(HttpServletRequest request,HttpServletResponse response)  {
	
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();
	
	 if (loginUser == null) {
		return "redirect:/login";
	}
	
	HttpSession session= request.getSession();
	Object data= session.getAttribute("voucherTxnData");
	
	if(null!=data && data instanceof VoucherTxnCdaModel txnModel) {
		
		List<GetOutVoucherGenCdaDataModel> dataModels=txnModel.getDataModels();
		
		DODLog.info(LogConstant.CDA_VOUCHER_LOG_FILE, OutVoucherGenCdaController.class,
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
			DODLog.printStackTrace(e, OutVoucherGenCdaController.class, LogConstant.CDA_VOUCHER_LOG_FILE);
		}
	}
	
	return "";
}




//ajax get grade pay based on category and serviceId
	@PostMapping("/popupViewCda")
	public String getPopUpView(Model model ,HttpServletRequest request ) {

		HttpSession session= request.getSession();
		Object data= session.getAttribute("formData");
		
		OutVoucherGenCdaReqModel reqModel = new OutVoucherGenCdaReqModel();
		if(null!=data && data instanceof OutVoucherGenCdaReqModel outModel) {
			
		 reqModel = outModel;
		
		}
		
		model.addAttribute("reqModel",reqModel);
		
		
		List<PopUpParamCdaModel> modelList= voucherService.voucherGenerationSummaryDataInXML(request);
		 
		model.addAttribute("popupDataList",modelList);
		
	 return "/CDA/transactionsettlementair/outstandingvouchergeneration/outvouchergenpopupcda";	
	
	

	}
	
	// send Data For Save
@PostMapping("/generateOutStandingVoucherCda")	
public String generateVoucher(@RequestParam List<String> txnsId,HttpServletRequest request,RedirectAttributes redirectAttributes,Model model) {
	
	try
	{
	HttpSession session= request.getSession();
	Object data= session.getAttribute("formData");
	
	OutVoucherGenCdaReqModel reqModel = new OutVoucherGenCdaReqModel();
	if(null!=data && data instanceof OutVoucherGenCdaReqModel outModel) {
		
	 reqModel = outModel;
	
	}
	
	
	PostOutStandVoucherGenCdaModel  postModel = voucherService.getPopUpModelList(txnsId,reqModel); 
	
	
	if (postModel.getLoginUserId() == null) {
		return "redirect:/login";
	}
	
	AfterVoucherGenCdaResponse response =   voucherService.sendDataForsave(postModel);
	
	DODLog.info(LogConstant.CDA_VOUCHER_LOG_FILE, OutVoucherGenCdaController.class,
			"RESPONSE AFTER SAVE IN OUT VOUCHER GEN :::::::"+response);	
	
	if(response!=null && response.getErrorCode()==200) {
		model.addAttribute("responseData",response.getResponse());
	
	return "/CDA/transactionsettlementair/outstandingvouchergeneration/outvouchergenviewcda";
	}else {
		return "/common/errorPage";
	}
	}catch(Exception e) {
		DODLog.printStackTrace(e, OutVoucherGenCdaController.class, LogConstant.CDA_VOUCHER_LOG_FILE);
	}
	
	return "/common/errorPage";
}


}
