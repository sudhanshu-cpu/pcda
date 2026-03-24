package com.pcda.mb.reports.currentprofilestatus.controller;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.reports.currentprofilestatus.model.CurrentProfileStatusModel;
import com.pcda.mb.reports.currentprofilestatus.model.CurrentProfileStatusResponse;
import com.pcda.mb.reports.currentprofilestatus.service.CurrentProfileStatusService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/reports")
public class CurrentProfileStatusController {

	private String url = "MB/Reports/CurrentProfileStatus";

	@Autowired
	private CurrentProfileStatusService currentProfileStatusService;
	
	@Autowired
	private OfficesService officeService;

	@RequestMapping(value ="/browseProfileStatus", method = {RequestMethod.GET, RequestMethod.POST })
	public String getCurrentProfileStatus(Model model, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "") String userAlias ) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		
	
		Optional<OfficeModel> officeModel = officeService.getOfficeByUserId(loginUser.getUserId());
		String groupId = " ";
		
		if (officeModel.isPresent()) {
			groupId = officeModel.get().getGroupId();
			
		}
		
		try {
			
			if (!userAlias.isEmpty() && !userAlias.trim().equals("")) {
				
				String secret = "Hidden Pass";
				String decryptUserAlias =  CommonUtil.getDecryptText(secret, userAlias);
				
				model.addAttribute("userAlias", decryptUserAlias);
				
				
				
				CurrentProfileStatusResponse profileStatusResponse =  currentProfileStatusService.getCurrentProfileStatus(decryptUserAlias,groupId);
				if (null != profileStatusResponse && profileStatusResponse.getErrorCode() == 200
						&& null != profileStatusResponse.getResponse()) {
					CurrentProfileStatusModel	statusModel = profileStatusResponse.getResponse();
					statusModel.setRemark(Optional.ofNullable(statusModel.getRemark()).orElse(""));
					
				
					
					model.addAttribute("profileModel", statusModel);
					return url + "/browseProfileStatus";
				} else {
					model.addAttribute("noData", profileStatusResponse==null ? "": profileStatusResponse.getErrorMessage());

				}

			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, CurrentProfileStatusController.class, LogConstant.CURRENT_PROFILE_STATUS_LOG_FILE);
		}

		return url + "/browseProfileStatus";
	}

}
