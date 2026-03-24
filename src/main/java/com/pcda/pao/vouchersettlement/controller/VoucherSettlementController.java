package com.pcda.pao.vouchersettlement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pcda.pao.vouchersettlement.model.*;
import jakarta.servlet.http.HttpSession;
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

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.pao.vouchersettlement.service.VoucherSettlementService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pao")
public class VoucherSettlementController {

    @Autowired
    private OfficesService officeService;

	@Autowired
    private VoucherSettlementService settleService;

    public static final String SEARCH_MODEL = "searchModel";

	@GetMapping("/createVoucherSettlement")
    public String voucherSettlement(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		 if (loginUser == null) {
			return "redirect:/login";
		}
		Optional<OfficeModel> office =  officeService.getOfficeByUserId(loginUser.getUserId());
        String groupId = "";
        String name = "";
        if (office.isPresent()) {
            groupId = office.get().getGroupId();
            name = office.get().getName();
        }
        model.addAttribute("groupId", groupId);
        model.addAttribute("name", name);
        if (!model.containsAttribute(SEARCH_MODEL)) {
            model.addAttribute(SEARCH_MODEL, new PostFormFieldModel());
		    }
		return "/PAO/transactionsettlementair/voucherSettlement/voucherSettlement";
	}

	@PostMapping("/voucherSetDataList")
    public String getVouchSetDataList(PostFormFieldModel fieldModel, Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		Optional<OfficeModel> office =  officeService.getOfficeByUserId(loginUser.getUserId());
        String groupId = "";
        String name = "";
        if (office.isPresent()) {
            groupId = office.get().getGroupId();
            name = office.get().getName();
		    }

        model.addAttribute("groupId", groupId);
        model.addAttribute("name", name);
		List<GetVoucherSetListDataModel> modelList = settleService.getDataList(fieldModel);
        VouchSettleResponseModel vouchSettleResponseModel = new VouchSettleResponseModel();
		
        vouchSettleResponseModel.setDataModelList(modelList);
        vouchSettleResponseModel.setPostFormFieldModel(fieldModel);
        HttpSession session = request.getSession();
        session.setAttribute("voucherSession",fieldModel);
        model.addAttribute(SEARCH_MODEL, fieldModel);
        if (modelList.isEmpty()) {
			model.addAttribute("noResult", "No Record Found !!");
        } else {
		model.addAttribute("dataList", modelList);
		}
		return "/PAO/transactionsettlementair/voucherSettlement/voucherSettlement";
	}

    // VIEW VOUCHER DETAILS FOR PAO
    @PostMapping("/voucherDetails")
    public String getVoucherDetails(@RequestParam String voucherNumber, Model model, HttpServletRequest request) {

     

        SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
        LoginUser loginUser = sessionVisitor.getLoginUser();
        if (loginUser == null) {
            return "redirect:/login";
        }
        PostFormFieldModel postInputModel = new PostFormFieldModel();
		HttpSession session = request.getSession();
		Object voucherSession= session.getAttribute("voucherSession");
		if(voucherSession!=null && voucherSession instanceof PostFormFieldModel postModel){
			postInputModel = postModel;

		}
            DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementController.class,"UNDER VOUCHER DETAILS CONTROLLER voucherNumber :: " + voucherNumber 
            		+ " POST INPUT MODEL :::: "+postInputModel);
        model.addAttribute(SEARCH_MODEL ,postInputModel);
        session.removeAttribute("voucherSession");

        VoucherDetailsResponse response = settleService.getVoucherDetails(voucherNumber);
        if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
            VoucherParentModel getModel = response.getResponse();
            List<VoucherChildModel> modelList = getModel.getVoucherDetails();
            List<VoucherChildModel> pendingDataList = new ArrayList<>();
            List<VoucherChildModel> settleDataList = new ArrayList<>();
            double calPendingValue = 0;
            double calsettleAmount = 0;

            for (int i = 0; i < modelList.size(); i++) {
                if (modelList.get(i).getTxnPaymentStatus().equalsIgnoreCase("Pending")) {
                    pendingDataList.add(modelList.get(i));
                    calPendingValue = calPendingValue + modelList.get(i).getTxnOutstandingAmount();
                }
                if (modelList.get(i).getTxnPaymentStatus().equalsIgnoreCase("Settled")) {
                    settleDataList.add(modelList.get(i));
                    calsettleAmount = calsettleAmount + modelList.get(i).getTxnOutstandingAmount();
                }
            }
            getModel.setCalPendingAmount(calPendingValue);
            getModel.setCalSettleAmount(calsettleAmount);
            model.addAttribute("voucherModel", getModel);
            model.addAttribute("pending", pendingDataList);
            model.addAttribute("settled", settleDataList);
        } else {
            model.addAttribute("noData", "No Data Available !!");
        }
        return "/PAO/transactionsettlementair/voucherSettlement/voucherDetails";
    }


	@PostMapping("/getDataFromVocher")
    public String getDataFromVocherNo(Model model, @RequestParam String voucherNos) {
		DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementController.class,
                "get Data From VocherNo :::::::" + voucherNos);
		GetDataVoucherNoParentModel parentModel =  settleService.getVocherNodata(voucherNos);
		
		model.addAttribute("vocherNoData", parentModel);
		return "/PAO/transactionsettlementair/voucherSettlement/voucherNo";
	}

	@PostMapping("/saveSettlement")
    public String saveSettlement(Model model, PostVoucherSaveModel saveModel, HttpServletRequest request, BindingResult result, RedirectAttributes attributes) {
		GetSaveResponseParentModel parentModel = null;
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		 if (loginUser == null) {
			return "redirect:/login";
		}
		 saveModel.setLoginUserId(loginUser.getUserId());
        if (result.hasErrors()) {
			DODLog.error(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementController.class,
					"Voucher Settlement Error: " + result.getAllErrors());
			ObjectError objectError = result.getAllErrors().get(0);
            attributes.addFlashAttribute("voucherNos", saveModel.getVoucherNo());
			attributes.addFlashAttribute("errors", objectError.getDefaultMessage());

			return "redirect:getDataFromVocher";
        } else {

            AfterSaveResponse response = settleService.saveSettlement(saveModel);
            if (response != null && response.getErrorCode() == 200) {
				parentModel = 	response.getResponse();

			DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementController.class,
                        "VOUCHER RESPONSE MODEL :::" + parentModel);
			model.addAttribute("saveData", parentModel);
		   return "/PAO/transactionsettlementair/voucherSettlement/vouchSetAfterSave";
            } else
                return "/common/errorPage";
			}
		}

}
