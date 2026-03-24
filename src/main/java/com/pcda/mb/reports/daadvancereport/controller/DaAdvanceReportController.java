package com.pcda.mb.reports.daadvancereport.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.reports.daadvancereport.model.DaAdvanceInputModel;
import com.pcda.mb.reports.daadvancereport.model.GetDaAdvanceModel;
import com.pcda.mb.reports.daadvancereport.service.DaAdvanceReportService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/reports")

public class DaAdvanceReportController {

	private String path = "/MB/Reports/DaAdvanceReport/";

	@Autowired
	private DaAdvanceReportService daAdvanceReportService;

	@GetMapping("/advanceReport")
	public String getdaAdvanceRepo(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		model.addAttribute("inputModel", new DaAdvanceInputModel());

		if (loginUser == null) {
			return "redirect:/login";
		}

		Optional<OfficeModel> officeModel = daAdvanceReportService.getOfficesByGroupId(loginUser.getUserId());

		
		if (officeModel.isPresent()) {

			String groupId = officeModel.get().getGroupId();
			String	name = officeModel.get().getName();

			model.addAttribute("groupId", groupId);
			model.addAttribute("name", name);

		}

		return path + "advancereport";
	}

	@PostMapping("/daAdvanceReport")

	public String getAdvanceRepo(DaAdvanceInputModel advanceInputModel, Model model, HttpServletRequest request,
			@RequestParam String groupId) {
		DODLog.info(LogConstant.DA_ADVANCE_REPORT, DaAdvanceReportController.class, "[getAdvanceDetails] ## advanceInputModel" + advanceInputModel + " groupId::"+groupId);
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
	
		String encryptPno = advanceInputModel.getPersonalNo();
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, encryptPno);
		advanceInputModel.setPersonalNo(decryptPersonalNo);
		
		model.addAttribute("inputModel", advanceInputModel);

		if (loginUser == null) {
			return "redirect:/login";
		}

		Optional<OfficeModel> officeModel = daAdvanceReportService.getOfficesByGroupId(loginUser.getUserId());
		
		if (officeModel.isPresent()) {
			 groupId = officeModel.get().getGroupId();
			 String name = officeModel.get().getName();
			model.addAttribute("groupId", groupId);
			model.addAttribute("name", name);
		}

	

		List<GetDaAdvanceModel> dataList = daAdvanceReportService.getAdvanceDetails(advanceInputModel, groupId);

		if (dataList.isEmpty()) {
			
			model.addAttribute("errorMessage", "No Record Found");
		} else {

			model.addAttribute("report", dataList);
		}

		
		return path + "advancereport";
	}
}
