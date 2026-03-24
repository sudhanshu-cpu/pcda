package com.pcda.cda.transactionsettlementair.vouchersettlement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.cda.transactionsettlementair.vouchersettlement.model.AfterSaveCdaResponse;
import com.pcda.cda.transactionsettlementair.vouchersettlement.model.GetDataVoucherNoCdaParentModel;
import com.pcda.cda.transactionsettlementair.vouchersettlement.model.GetSaveResponseCdaParentModel;
import com.pcda.cda.transactionsettlementair.vouchersettlement.model.GetVoucherSetListDataCdaModel;
import com.pcda.cda.transactionsettlementair.vouchersettlement.model.PostFormFieldCdaModel;
import com.pcda.cda.transactionsettlementair.vouchersettlement.model.PostVoucherCdaSaveModel;
import com.pcda.cda.transactionsettlementair.vouchersettlement.service.VoucherSettlementCdaService;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;



import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cda")
public class VoucherSettlementCdaController {


	
	@Autowired
	private OfficesService officeService;
	
@Autowired
private VoucherSettlementCdaService settleService ;
	
	
	@GetMapping("/createVoucherSettlementCda")
	public String voucherSettlement(Model model,HttpServletRequest request) {
		String groupId="";
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		
		 if (loginUser == null) {
			return "redirect:/login";
		}
		 PostFormFieldCdaModel  postModel  = new PostFormFieldCdaModel();
		Optional<OfficeModel> office =  officeService.getOfficeByUserId(loginUser.getUserId());
		if(office.isPresent()) {
			groupId=office.get().getGroupId();
			}
		model.addAttribute("accountMap", settleService.getAccountOfficeListMap("CDA", groupId));
		model.addAttribute("searchModel", postModel);
		return "/CDA/transactionsettlementair/voucherSettlement/voucherSettlementcda";
		
	}
	
	@PostMapping("/voucherSetDataListCda")
	public String getVouchSetDataList(Model model,PostFormFieldCdaModel  fieldModel,HttpServletRequest request) {
		String groupId="";
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		Optional<OfficeModel> office =  officeService.getOfficeByUserId(loginUser.getUserId());
		if(office.isPresent()) {
		groupId=office.get().getGroupId();
		}
		model.addAttribute("accountMap", settleService.getAccountOfficeListMap("CDA", groupId));
		model.addAttribute("searchModel", fieldModel);
		List<GetVoucherSetListDataCdaModel> modelList = settleService.getDataList(fieldModel);
	
		if(modelList.isEmpty()) {
			model.addAttribute("noResult", "No Record Found !!");
		}
		else {
		 model.addAttribute("dataList", modelList);
		}
		return "/CDA/transactionsettlementair/voucherSettlement/voucherSettlementcda";
	}
	
	@PostMapping("/getDataFromVocherCda")
	public String getDataFromVocherNo(Model model,@RequestParam String voucherNos) {
		GetDataVoucherNoCdaParentModel parentModel =  settleService.getVocherNodata(voucherNos);
		
		DODLog.info(LogConstant.CDA_VOUCHER_LOG_FILE, VoucherSettlementCdaController.class,
				"VOUCHER NO DATA :::::::"+parentModel);	
		model.addAttribute("vocherNoData", parentModel);
		return "/CDA/transactionsettlementair/voucherSettlement/voucherNocda";
	}
	
	@PostMapping("/saveSettlementCda")
	public String saveSettlement(Model model,PostVoucherCdaSaveModel saveModel,HttpServletRequest request,BindingResult result,RedirectAttributes attributes) {
		GetSaveResponseCdaParentModel parentModel = null;
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		
		 if (loginUser == null) {
			return "redirect:/login";
		}
		 saveModel.setLoginUserId(loginUser.getUserId());
		if(result.hasErrors()) {
			DODLog.error(LogConstant.CDA_VOUCHER_LOG_FILE, VoucherSettlementCdaController.class,
					"Voucher Settlement Error: " + result.getAllErrors());
			ObjectError objectError = result.getAllErrors().get(0);
			attributes.addFlashAttribute("voucherNos",saveModel.getVoucherNo());
			attributes.addFlashAttribute("errors", objectError.getDefaultMessage());
			
			return "redirect:getDataFromVocherCda";
		}else {
			
			AfterSaveCdaResponse response=settleService.saveSettlement(saveModel);
			if( response!=null && response.getErrorCode()==200) {
				parentModel = 	response.getResponse();
			
			
			model.addAttribute("saveData", parentModel);
		   return "/CDA/transactionsettlementair/voucherSettlement/vouchSetAfterSavecda";
			}
			else
				return "/common/errorPage";	
		}
	}
	
}
