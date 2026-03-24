package com.pcda.mb.adduser.changeservice.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.DODServices;
import com.pcda.common.model.GradePayRankModel;
import com.pcda.common.model.PAOMappingModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.changeservice.model.ChangeServiceRequired;
import com.pcda.mb.adduser.changeservice.model.ChangeServiceResponse;
import com.pcda.mb.adduser.changeservice.model.ChangeServiceViewResponse;
import com.pcda.mb.adduser.changeservice.model.GetChangeServiceModel;
import com.pcda.mb.adduser.changeservice.model.GetChangeServiceViewModel;
import com.pcda.mb.adduser.changeservice.model.PostChangeServiceModel;
import com.pcda.mb.adduser.changeservice.service.ChangeServiceService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODDATAConstants;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.ServiceType;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class ChangeServiceController {

	
	@Autowired
	private ChangeServiceService service;
	
	private String path = "MB/AddUsers/ChangeService/";
	
	@RequestMapping(value="/changeService",method= {RequestMethod.GET,RequestMethod.POST})
	public  String createChangeService(Model model,HttpServletRequest request) {
		ChangeServiceRequired noRequired = new ChangeServiceRequired();
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		String serviceId = "";

		if (loginUser == null) {
			return "redirect:/login";
		}

		if (loginUser.getUserServiceId() != null && !loginUser.getUserServiceId().equalsIgnoreCase("null") && !loginUser.getUserServiceId().trim().equals("")) {
			serviceId = loginUser.getUserServiceId();
		} else {
			serviceId = loginUser.getServiceId();
		}

		
		DODServices dodServices = service.getService(serviceId);
		ServiceType serviceType;

		if (dodServices.getArmedForces().name().equalsIgnoreCase("YES")) {
			serviceType = ServiceType.ARMED_FORCES;
		} else {
			serviceType = ServiceType.CIVILIAN;
		}

 		noRequired.setServiceName(dodServices.getServiceName());

		

		if (serviceType.equals(ServiceType.CIVILIAN)) {
			
			
			return path+"usersCivilianErrorPage";
		} else {
			if(serviceId.equals(DODDATAConstants.NAVY_SEVICE_ID)) {
			
		     model.addAttribute("personalNo", "");
			 return "redirect:navyChangeService";
			}
			else {
				return path + "usersArmedErrorPage";
			}
		}
   
	}
	
	@RequestMapping(value="/navyChangeService",method= {RequestMethod.GET,RequestMethod.POST})
	public String armyTravelerProfile(Model model, HttpServletRequest request,@RequestParam(required = false, defaultValue = "") String personalNo) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
        
		if (loginUser == null) {
			return "redirect:/login";
		}
		
		BigInteger userId = loginUser.getUserId();
		String serviceId = "";

		if (loginUser.getUserServiceId() != null && !loginUser.getUserServiceId().equalsIgnoreCase("null")) {
			serviceId = loginUser.getUserServiceId();
		} else {
			serviceId = loginUser.getServiceId();
		}
		ChangeServiceRequired noRequired = new ChangeServiceRequired();

		DODServices dodServices = service.getService(serviceId);
		noRequired.setServiceName(dodServices.getServiceName());
		
		if(!personalNo.equalsIgnoreCase("")&& !personalNo.isEmpty()) {
			String serviceIdd = loginUser.getServiceId();
		    traveller(model,userId,personalNo,serviceIdd);
		}
		
		return path + "changeService";
	}
	
	public void traveller(Model model,BigInteger userId,String personalNo,String serviceId) {
		
  if(!personalNo.equalsIgnoreCase("")&& !personalNo.isEmpty()) {
	  model.addAttribute("personalNo", personalNo);
			  ChangeServiceResponse response = service.getSearchPnoData(userId, personalNo);
			 
			  
			  if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
				  GetChangeServiceModel getModel = response.getResponse();
				  if(getModel.getApprovalState()==1) {
					  model.addAttribute("getModel", getModel);
							  model.addAttribute("getCategories", service.getCategoryBasedOnService(serviceId)); 
				  }else {
					  model.addAttribute("error", "Personal No You Have  Is Not Approved");
				  }
				 
			  } else{
				  if (response != null && response.getErrorMessage() != null) {
					  model.addAttribute("error", response.getErrorMessage());
				  } else {
					  model.addAttribute("error", "Error Occurred");
				  }
			  }
		       
        }
		

	}
	
	

	


// AJAX Mapping to get Level
@PostMapping("/getChangeServiceLevel")
public ResponseEntity<Map<String,List<String>>> pnoGradePayBasedOnServiceCategory(
		@RequestParam String serviceId, @RequestParam String categoryId) {
	DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceController.class,
			"SERVICE ID AND CATEGORY ID FOR LEVEL BASED ON SERVICE AND CATEGORY :: "+serviceId+","+categoryId);
	return ResponseEntity.ok(service.gradePayBasedOnServiceCategory(serviceId, categoryId));
}

//AJAX Mapping to get Grade Pay On change of Level
	@PostMapping("/getServiceGradePayRank")
	public ResponseEntity<String> getChangePnoGradePayRank(@RequestParam String rankId) {
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceController.class,
				"RANK ID TO GET GRADEPAY RANK:" + rankId);
		GradePayRankModel gradePayRankModel = service.getGradePayRank(rankId);
		return ResponseEntity.ok(gradePayRankModel.getRankName());
	}

	// AJAX To Get PAO
	@PostMapping("/getPAOChangeService")
	public ResponseEntity<Map<String, List<PAOMappingModel>>> getPAO(@RequestParam String serviceId, @RequestParam String categoryId, HttpServletRequest request) {
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceController.class,
				"SERVICE ID AND CATEGORY ID FOR PAO BASED ON SERVICE AND CATEGORY :: " + serviceId+"," + categoryId);
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		return ResponseEntity.ok(service.getPaoOffice(serviceId, categoryId, loginUser));
	}

	

	// save change service
	@PostMapping("/saveChangeService")
	public String getChangeServiceSave(Model model,PostChangeServiceModel postChangeService, BindingResult result,HttpServletRequest request,RedirectAttributes attributes) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		
		if (loginUser == null) {
			return "redirect:/login";
		}
		
		postChangeService.setLoginUserId(loginUser.getUserId());
		
		String[] levelId=postChangeService.getLevelName().split(":");
		
		postChangeService.setLevelId(levelId[0]);
	
	
		try {
			if (result.hasErrors()) {
				DODLog.error(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceController.class,
						"CHANGE SERVICE MODEL ERROR :: " + result.getAllErrors());
				ObjectError objectError = result.getAllErrors().get(0);
			
				attributes.addFlashAttribute("errors", objectError.getDefaultMessage());
				return path+"changeServiceError";
				

			} else {
				DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceController.class,"[getChangeServiceSave]CHANGE SERVICE SAVE MODEL :: "+postChangeService);
				GetChangeServiceViewModel viewModel=null;
			ChangeServiceViewResponse response	= service.getSaveChangeService(postChangeService);
			 DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceController.class,"[getChangeServiceSave]CHANGE SERVICE RESPONSE :: "+response);
			if(response!=null && response.getErrorCode()==200) {
			 viewModel=response.getResponse();
				
			 viewModel.setCreationDateStr(CommonUtil.formatDate(viewModel.getCreationDate(), "dd-MMM-yyyy"));
			 model.addAttribute("view", viewModel);
			 
				return path+ "changeServiceView";

			}else if(response!=null && response.getErrorCode()!=200) {
				 model.addAttribute("errors", response.getErrorMessage());
				 return path+"changeServiceError";
			}else{
				model.addAttribute("errors","Unable to Change Service");
				return path+"changeServiceError";
			}
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangeServiceController.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
			
		}
		model.addAttribute("errors","Unable to Change Service");
		return path+"changeServiceError";

	}
	
}