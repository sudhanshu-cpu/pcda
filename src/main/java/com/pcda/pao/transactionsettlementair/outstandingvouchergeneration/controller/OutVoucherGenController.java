package com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.controller;

import java.util.ArrayList;
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

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.model.AfterVoucherGenResponse;
import com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.model.GenerateVoucherResponse;
import com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.model.GetOutVoucherGenDataModel;
import com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.model.OutVoucherGenReqModel;
import com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.model.PopUpParamModel;
import com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.model.PostOutStandVoucherGenModel;
import com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.model.VoucherTxnModel;
import com.pcda.pao.transactionsettlementair.outstandingvouchergeneration.service.OutVoucherService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pao")
public class OutVoucherGenController {

	@Autowired
	private MasterServices masterService;
	
	@Autowired
	private OfficesService officeService;
	
	@Autowired
	private TravelTypeServices travelType;
	
	@Autowired
	private OutVoucherService voucherService; 
	

	
@GetMapping("/outstandingVoucherGen")	
public String outstandingGen(Model model,HttpServletRequest request) {
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();
	
	 if (loginUser == null) {
		return "redirect:/login";
	}
	Optional<OfficeModel> office =  officeService.getOfficeByUserId(loginUser.getUserId());
	
	
    String groupId="";
   String name="";
    if(office.isPresent()) {
	
	groupId=office.get().getGroupId();
	name=office.get().getName();
	
    }
	
    model.addAttribute("groupId", groupId);	
    model.addAttribute("name", name);	
	model.addAttribute("formModelData",new OutVoucherGenReqModel());
	model.addAttribute("serviceList", masterService.getServicesByApprovalState("1"));
	model.addAttribute("unitList",officeService.getOffices("UNIT","1"));
	model.addAttribute("travelTypeList",travelType.getAllTravelType(1));
	
return "/PAO/transactionsettlementair/outstandingvouchergeneration/outvouchergen";	
}

@PostMapping("/getVoucherData")
public String getVoucherdata(Model model , OutVoucherGenReqModel genReqModel,HttpServletRequest request ) {
	
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();
	
	 if (loginUser == null) {
		return "redirect:/login";
	}
	 
	 genReqModel.setLoginUserId(loginUser.getUserId());
	 
	Optional<OfficeModel> office =  officeService.getOfficeByUserId(loginUser.getUserId());
	
	  String groupId="";
	   String name="";
	    if(office.isPresent()) {
		
		groupId=office.get().getGroupId();
		name=office.get().getName();
		
	    }
	    model.addAttribute("groupId", groupId);	
	    model.addAttribute("name", name);	
		
	model.addAttribute("serviceList", masterService.getServicesByApprovalState("1"));
	model.addAttribute("unitList",officeService.getOffices("UNIT","1"));
	model.addAttribute("travelTypeList",travelType.getAllTravelType(1));
	
	model.addAttribute("formModelData",genReqModel);
	List<GetOutVoucherGenDataModel> listModel = new ArrayList<>();
	GenerateVoucherResponse response =voucherService.getGenerateReportData(genReqModel);
	if(response!=null && response.getErrorCode()==200 && null!=response.getResponseList()) {
		listModel =  response.getResponseList();
		  
	 }
	if(!listModel.isEmpty()) {
	
	VoucherTxnModel txnModel=new VoucherTxnModel();
	txnModel.setDataModels(listModel);
	
	model.addAttribute("dataList", txnModel.getDataModels());
	model.addAttribute("listSize", txnModel.getDataModels().size());
	HttpSession session= request.getSession();
	
	session.setAttribute("voucherTxnData", txnModel);
	session.setAttribute("formData", genReqModel);
	}else {
		model.addAttribute("noData",(response != null && response.getErrorMessage()!=null) ? response.getErrorMessage(): "No Record Found !!");
	}
	
	return "/PAO/transactionsettlementair/outstandingvouchergeneration/outvouchergen";	
}


// csv download 
@GetMapping("/getDownload")
@ResponseBody
public String getdataForDownload(HttpServletRequest request,HttpServletResponse response)  {
	
	SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = sessionVisitor.getLoginUser();
	
	 if (loginUser == null) {
		return "redirect:/login";
	}
	
	HttpSession session= request.getSession();
	Object data= session.getAttribute("voucherTxnData");
	
	if(null!=data && data instanceof VoucherTxnModel txnModel) {
		
		List<GetOutVoucherGenDataModel> dataModels=txnModel.getDataModels();
		
		DODLog.info(LogConstant.OUTSTANDING_VOUCHER_GENEATION_LOG_FILE, OutVoucherGenController.class,
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
			DODLog.printStackTrace(e, OutVoucherGenController.class, LogConstant.OUTSTANDING_VOUCHER_GENEATION_LOG_FILE);
		}
	}
	
	return "";
}




//ajax get grade pay based on category and serviceId
	@PostMapping("/popupView")
	public String getPopUpView(Model model ,HttpServletRequest request ) {

		HttpSession session= request.getSession();
		Object data= session.getAttribute("formData");
		
		OutVoucherGenReqModel reqModel = new OutVoucherGenReqModel();
		if(null!=data && data instanceof OutVoucherGenReqModel outModel) {
			
		 reqModel = outModel;
		
		}
		
		model.addAttribute("reqModel",reqModel);
		
		
		List<PopUpParamModel> modelList= voucherService.voucherGenerationSummaryDataInXML(request);
		 
		
		model.addAttribute("popupDataList",modelList);
		
	 return "/PAO/transactionsettlementair/outstandingvouchergeneration/outvouchergenpopup";	
	
	

	}
	
	// send Data For Save
@PostMapping("/generateOutStandingVoucher")	
public String generateVoucher(@RequestParam List<String> txnsId,HttpServletRequest request,RedirectAttributes redirectAttributes,Model model) {
	
	try
	{
	HttpSession session= request.getSession();
	Object data= session.getAttribute("formData");
	
	OutVoucherGenReqModel reqModel = new OutVoucherGenReqModel();
	if(null!=data && data instanceof OutVoucherGenReqModel outModel) {
		
	 reqModel = outModel;
	
	}
	
	
	PostOutStandVoucherGenModel  postModel = voucherService.getPopUpModelList(txnsId,reqModel); 
	
		
	
	if (postModel.getLoginUserId() == null) {
		return "redirect:/login";
	}
	
	AfterVoucherGenResponse response =   voucherService.sendDataForsave(postModel);
	
	
	
	if(response!=null && response.getErrorCode()==200) {
		model.addAttribute("responseData",response.getResponse());
	
	return "/PAO/transactionsettlementair/outstandingvouchergeneration/outvouchergenview";
	}else {
		return "/common/errorPage";
	}
	}catch(Exception e) {
		DODLog.printStackTrace(e, OutVoucherGenController.class, LogConstant.OUTSTANDING_VOUCHER_GENEATION_LOG_FILE);
	}
	
	return "/common/errorPage";
}


}
