package com.pcda.adg.reports.claimstatusreport.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcda.adg.reports.claimstatusreport.model.ClaimDataRequest;
import com.pcda.adg.reports.claimstatusreport.model.ClaimDataResponse;
import com.pcda.adg.reports.claimstatusreport.model.ClaimInformationResponse;
import com.pcda.adg.reports.claimstatusreport.model.ClaimStatusDataResBean;
import com.pcda.adg.reports.claimstatusreport.service.ClaimStatusReportService;
import com.pcda.common.services.MasterServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/adg")
public class ClaimStatusReportController {
	
	@Autowired
	private MasterServices masterServices;
	
	
	@Autowired
	private ClaimStatusReportService claimStatusReportservice;
	
	private String path = "ADG/Reports/ClaimReport/";
	

	@GetMapping("/claimStatusReport")
	public String getClaimDtls(Model model, HttpServletRequest request) {
		
		  try {
		        SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		        LoginUser loginUser = sessionVisitor.getLoginUser();
		        model.addAttribute("userServiceList",masterServices.getServicesByApprovalState("1"));
		        if (loginUser == null) {
		            return "redirect:/login";
		        }

				 model.addAttribute("requestData", new ClaimDataRequest());
		    } 
		    catch (Exception e) {	
		    	 DODLog.printStackTrace(e, ClaimStatusReportController.class, LogConstant.ADG_REPORTS_LOG_FILE);
		    }
		    return path + "claimStatusReport";
			
	}
	
	
	
	// A J A X - Call Category Based On serviceId
		@GetMapping("/getCategorybasedOnServiceID")
		@ResponseBody
		public Map<String, String> getCategorybasedOnService(@RequestParam String serviceId, Model model) {
			Map<String, String> map = new HashMap<>();

			if (serviceId != null && !serviceId.isEmpty() && !serviceId.isBlank()) {
				Map<String, String> categoryBasedOnService = claimStatusReportservice.getCategoryBasedOnService(serviceId);
				DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, ClaimStatusReportController.class,
						"@@@@Category Service Map ::@@@" + categoryBasedOnService);
				model.addAttribute("categoryByUserServiceList", categoryBasedOnService);
				
				return categoryBasedOnService;
			} else {
				DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, ClaimStatusReportController.class, "@@@@ServiceID::@@@" + serviceId);
				return map;
			}
		}

		
		@PostMapping("/claimStatusData")
		public String claimStatusData(ClaimDataRequest requestData,Model model, HttpServletRequest request) {
			try {
				SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
				LoginUser loginUser = visitor.getLoginUser();
				if (loginUser == null) {
					return "redirect:/login";
				}
				
				requestData.setFromDate(CommonUtil.getChangeFormat(requestData.getFromDateStr(),"dd-MM-yyyy", "yyyy-MM-dd"));
				requestData.setToDate(CommonUtil.getChangeFormat(requestData.getToDateStr(),"dd-MM-yyyy", "yyyy-MM-dd"));
	
				String msg=claimStatusReportservice.validationFormBean(requestData);
				if(msg.equalsIgnoreCase("OK")) {
					
					DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, ClaimStatusReportController.class, "claim request data:::::" + requestData);
					List<ClaimStatusDataResBean> response = claimStatusReportservice.getClaimStatusRptData(requestData);
					if(response==null || response.isEmpty()) {
						model.addAttribute("errorMsg", " No records found!!");
					}
					model.addAttribute("claimRptDataList", response);
				}else {
					
					model.addAttribute("errorMsg", msg);
				}
				model.addAttribute("requestData",requestData);
				model.addAttribute("userServiceList",masterServices.getServicesByApprovalState("1"));
				model.addAttribute("categoryByUserServiceList", claimStatusReportservice.getCategoryBasedOnService(requestData.getUserServiceId()));
			} catch (Exception e) {
				DODLog.printStackTrace(e, ClaimStatusReportController.class, LogConstant.ADG_REPORTS_LOG_FILE);
			}
			return path + "claimStatusReport";
					
		}
		
		
		
		
		//POPUP CONTROLLER
		@PostMapping("/claimInformation") 
		public String viewClaimInformation(ClaimStatusDataResBean requestBean, HttpServletRequest request,Model model) {
			
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();
			if (loginUser == null) {
				return "redirect:/login";
			}
			DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, ClaimStatusReportController.class, "view Claim Information :: " + requestBean);
			ClaimDataResponse response= claimStatusReportservice.getviewClaimInformation(requestBean);
			if(response!=null && response.getErrorCode()==200 && response.getResponseList()!=null){
				List<ClaimInformationResponse> claimInfo= response.getResponseList();
				model.addAttribute("claimInfo", claimInfo);
			}
			else {
				model.addAttribute("noData","No Data Available!!!");
			}
			return path + "viewClaim";
			
		}
}


		
