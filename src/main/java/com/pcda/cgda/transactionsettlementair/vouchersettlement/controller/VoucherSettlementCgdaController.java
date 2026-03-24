package com.pcda.cgda.transactionsettlementair.vouchersettlement.controller;

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

import com.pcda.cgda.transactionsettlementair.vouchersettlement.model.AfterSaveCgdaResponse;
import com.pcda.cgda.transactionsettlementair.vouchersettlement.model.GetDataVoucherNoCgdaParentModel;
import com.pcda.cgda.transactionsettlementair.vouchersettlement.model.GetSaveResponseCgdaParentModel;
import com.pcda.cgda.transactionsettlementair.vouchersettlement.model.GetVoucherSetListDataCgdaModel;
import com.pcda.cgda.transactionsettlementair.vouchersettlement.model.PostFormFieldCgdaModel;
import com.pcda.cgda.transactionsettlementair.vouchersettlement.model.PostVoucherCgdaSaveModel;
import com.pcda.cgda.transactionsettlementair.vouchersettlement.service.VoucherSettlementCgdaService;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cgda")
public class VoucherSettlementCgdaController {


	
	@Autowired
	private OfficesService officeService;
	
@Autowired
private VoucherSettlementCgdaService settleService ;
	
	
	@GetMapping("/createVoucherSettlementCgda")
	public String voucherSettlement(Model model,HttpServletRequest request) {
		String groupId="";
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		
		 if (loginUser == null) {
			return "redirect:/login";
		}
		 PostFormFieldCgdaModel  postModel  = new PostFormFieldCgdaModel();
		Optional<OfficeModel> office =  officeService.getOfficeByUserId(loginUser.getUserId());
		if(office.isPresent()) {
			groupId=office.get().getGroupId();
			}
		model.addAttribute("accountMap", settleService.getAccountOfficeListMap("CGDA", groupId));
		model.addAttribute("searchModel", postModel);
		return "/CGDA/transactionsettlementair/voucherSettlement/voucherSettlementcgda";
		
	}
	
	@PostMapping("/voucherSetDataListCgda")
	public String getVouchSetDataList(Model model,PostFormFieldCgdaModel  fieldModel,HttpServletRequest request) {
		String groupId="";
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		Optional<OfficeModel> office =  officeService.getOfficeByUserId(loginUser.getUserId());
		if(office.isPresent()) {
		groupId=office.get().getGroupId();
		}
		model.addAttribute("accountMap", settleService.getAccountOfficeListMap("CGDA", groupId));
		model.addAttribute("searchModel", fieldModel);
		List<GetVoucherSetListDataCgdaModel> modelList = settleService.getDataList(fieldModel);
			
		if(modelList.isEmpty()) {
			model.addAttribute("noResult", "No Record Found !!");
		}
		else {
		 model.addAttribute("dataList", modelList);
		}
		return "/CGDA/transactionsettlementair/voucherSettlement/voucherSettlementcgda";
	}
	
	@PostMapping("/getDataFromVocherCgda")
	public String getDataFromVocherNo(Model model,@RequestParam String voucherNos) {
		GetDataVoucherNoCgdaParentModel parentModel =  settleService.getVocherNodata(voucherNos);
		
	
		model.addAttribute("vocherNoData", parentModel);
		return "/CGDA/transactionsettlementair/voucherSettlement/voucherNocgda";
	}
	
	@PostMapping("/saveSettlementCgda")
	public String saveSettlement(Model model,PostVoucherCgdaSaveModel saveModel,HttpServletRequest request,BindingResult result,RedirectAttributes attributes) {
		GetSaveResponseCgdaParentModel parentModel = null;
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		
		 if (loginUser == null) {
			return "redirect:/login";
		}
		 saveModel.setLoginUserId(loginUser.getUserId());
		if(result.hasErrors()) {
			DODLog.error(LogConstant.CGDA_VOUCHER_LOG_FILE, VoucherSettlementCgdaController.class,
					"Voucher Settlement Error: " + result.getAllErrors());
			ObjectError objectError = result.getAllErrors().get(0);
			attributes.addFlashAttribute("voucherNos",saveModel.getVoucherNo());
			attributes.addFlashAttribute("errors", objectError.getDefaultMessage());
			
			return "redirect:getDataFromVocherCgda";
		}else {
			
			AfterSaveCgdaResponse response=settleService.saveSettlement(saveModel);
			if( response!=null && response.getErrorCode()==200) {
				parentModel = 	response.getResponse();
			
			
			model.addAttribute("saveData", parentModel);
		   return "/CGDA/transactionsettlementair/voucherSettlement/vouchSetAfterSavecgda";
			}
			else
				return "/common/errorPage";	
		}
	}
	
}
