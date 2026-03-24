package com.pcda.mb.travel.exceptionalcancellation.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.travel.exceptionalcancellation.model.ExpCanBookingResponse;
import com.pcda.mb.travel.exceptionalcancellation.model.GetExpCancelParentModel;
import com.pcda.mb.travel.exceptionalcancellation.model.GetSearchModel;
import com.pcda.mb.travel.exceptionalcancellation.model.PostExpCancellationParentModel;
import com.pcda.mb.travel.exceptionalcancellation.service.ExpCancelService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class ExpCancellationController {
	
	
@Autowired
private ExpCancelService service;

	private String path = "MB/TravelRequest/";
	
	
	@GetMapping("/cnfPageExpCan")
	public String confimPageIn(Model model) {
		return path + "ExceptionalCancellation/conformationPage";
	}

	@GetMapping("/errorPageExpCan")
	public String errorPageRailCancellation(Model model) {
		return path + "ExceptionalCancellation/errorPage";
	}
	

	@RequestMapping(value="/expCancelCreate",method= {RequestMethod.GET,RequestMethod.POST})
	public  String createExceptionalCan(Model model,HttpServletRequest request,@RequestParam(required = false, defaultValue = "") String personalNumber) {
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		BigInteger userId = loginUser.getUserId();
		OfficeModel officeModel = service.getOfficeByUserId(userId);
		String groupId = officeModel.getGroupId();
		String secret = "Hidden Pass";
		String personalNumberDecrypt = CommonUtil.getDecryptText(secret, personalNumber);
		
	
		model.addAttribute("personalNumber",personalNumberDecrypt);
		if(personalNumberDecrypt!=null && !personalNumberDecrypt.isBlank()) {
          List<GetSearchModel> listExpCanModel=service.getSearchData(personalNumberDecrypt, groupId);
           
		
		  if(!listExpCanModel.isEmpty()) {
				model.addAttribute("getModel",listExpCanModel);
		       
		        }else {
		        	model.addAttribute("noData","No Record Found !!");
		        }
		}
			
	return path +"ExceptionalCancellation/exceptionalCancellation";
	
}
	
	
	
	@PostMapping(value="/expCancelData")
	public  String getExceptionalData(Model model,@RequestParam  String bookingId,HttpServletRequest request) {
		
		GetExpCancelParentModel parentModel =  service.getExpCanData(bookingId);
	
		
	model.addAttribute("expCancelData", parentModel);
	model.addAttribute("isBooked", service.isBooked(parentModel));
	
	
	return path +"ExceptionalCancellation/exceptionalCancelData";
	
	
}
	@PostMapping("/saveExpCan")
	public String saveExpCanReq(@ModelAttribute PostExpCancellationParentModel postModel,BindingResult result,HttpServletRequest request,RedirectAttributes attributes) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		BigInteger userId = loginUser.getUserId();
		OfficeModel officeModel = service.getOfficeByUserId(userId);
	String groupId = officeModel.getGroupId();
		
		
		
		postModel.setGroupId(groupId);
		postModel.setLoginUserId(userId);
		
		try {
			if (result.hasErrors()) {
				DODLog.error(LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE, ExpCancellationController.class,
						"RailCancellation Error: " + result.getAllErrors());
				ObjectError objectError = result.getAllErrors().get(0);
				attributes.addFlashAttribute("expCan", objectError);
				attributes.addFlashAttribute("errors", objectError.getDefaultMessage());
				return  "redirect:expCancelCreate";
			}
		else {	
			 ExpCanBookingResponse response =	service.getSave(postModel,request);
			 
				if (response != null) {
					if (response.getErrorCode() == 200) {
						attributes.addFlashAttribute("success", response.getErrorMessage());
						return "redirect:cnfPageExpCan";
					} else {
						attributes.addFlashAttribute("errors", response.getErrorMessage());
						return "redirect:errorPageExpCan";
					}
				}
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, ExpCancellationController.class, LogConstant.EXCEPTIONAL_CANCELLATION_LOG_FILE);
		}
		return "redirect:expCancelCreate ";
	}
	
}
