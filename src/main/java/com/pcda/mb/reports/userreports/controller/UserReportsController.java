package com.pcda.mb.reports.userreports.controller;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcda.common.model.Category;
import com.pcda.common.model.DODServices;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.VisitorModel;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.UserServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.reports.userreports.model.GetUserReportModel;
import com.pcda.mb.reports.userreports.model.UserInputModel;
import com.pcda.mb.reports.userreports.service.UserReportService;
import com.pcda.util.CommonUtil;
import com.pcda.util.ServiceType;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/reports")
public class UserReportsController {

	private String pageURL = "/MB/Reports/UserReports/";

	@Autowired
	private UserReportService userReportService;
	
	@Autowired
	private MasterServices service;
	@Autowired
	private OfficesService officesService;
	@Autowired
	private UserServices userServices;
	
	public static final String USER_LIST = "userList";
	public static final String REDIRECT_LOGIN = "redirect:/login";
	public static final String USER_INPUT_MODEL = "fromModel";
			

	@GetMapping("/unitUserReport")
	public String getUserRepo(Model model, HttpServletRequest request,UserInputModel userInputModel) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		model.addAttribute(USER_INPUT_MODEL, new UserInputModel());
		model.addAttribute("userServiceList",userReportService.getUserService());
		if (loginUser == null) {
			return REDIRECT_LOGIN;
		}
		Optional<OfficeModel> officeModel = userReportService.getOfficesByGroupId(loginUser.getUserId());
		String groupId ="";
		if (officeModel.isPresent()) {
			groupId = officeModel.get().getGroupId();
			
		}
		 model.addAttribute("groupId", groupId);
		return pageURL + "unituserreport";
	}

	@RequestMapping(value = "/userReportDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public String userDtls(UserInputModel userInputModel, Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		
		String encryptPno = userInputModel.getPersonalNo();
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, encryptPno);
		userInputModel.setPersonalNo(decryptPersonalNo);
		
		
		model.addAttribute(USER_INPUT_MODEL, userInputModel);
		 Map<String,String> sortedPaoCategoryBasedOnServiceMap = null;
		 Map<String, String> sortedCategoryMap=null;
	

		if (loginUser == null) {
			return REDIRECT_LOGIN;
		}
		Optional<OfficeModel> officeModel = userReportService.getOfficesByGroupId(loginUser.getUserId());

		String name = "";
		if (officeModel.isPresent()) {
			name = officeModel.get().getName();
			model.addAttribute("name", name);
		}
			
		model.addAttribute("groupId", userInputModel.getGroupId());
		model.addAttribute("userServiceList",userReportService.getUserService());
		
		if(!userInputModel.getUserServiceId().equals("")){
			
			model.addAttribute("categoryByUserServiceMap",userReportService.getCategoryBasedOnService(userInputModel.getUserServiceId()));	
		}
		Optional<VisitorModel> completUser=userServices.getCompleteUser(userInputModel.getPersonalNo());
             BigInteger userId;
		
		if(completUser.isPresent()){
			userId= completUser.get().getUserId();
			Optional<OfficeModel> personalOffices = officesService.getOfficeByUserId(userId);
			String visitorOfficeId = "";
			
			if (personalOffices.isPresent()) {
				visitorOfficeId = personalOffices.get().getGroupId();
				
			}
			if(!userInputModel.getGroupId().equals(visitorOfficeId)) {
				
				model.addAttribute("errorMessage","User belongs to different Unit");
				return pageURL + "unituserreport";
			}
			
		}
		
		
		if (!userInputModel.getPersonalNo().isEmpty() && userInputModel.getPersonalNo()!=null) {
			
			List<Category> categoryList=userReportService.getAllCategory();
			 sortedCategoryMap=  categoryList.stream()
						.collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
			 
			
			List<DODServices> serviceList=userReportService.getUserService();
			 sortedPaoCategoryBasedOnServiceMap =  serviceList.stream()
						.collect(Collectors.toMap(DODServices::getServiceId, DODServices::getServiceName));
			
			List<GetUserReportModel> data = userReportService.userReportModel(userInputModel);
			 
			for (GetUserReportModel user : data) {
			    String serviceId = user.getServiceId();
			    if (sortedPaoCategoryBasedOnServiceMap.containsKey(serviceId)) {
			        String serviceName = sortedPaoCategoryBasedOnServiceMap.get(serviceId);
			        user.setServiceName(serviceName);
			    }
			}
			for (GetUserReportModel user : data) {
			    String categoryId = user.getCategoryId();
			    if (sortedCategoryMap.containsKey(categoryId)) {
			        String categoryName = sortedCategoryMap.get(categoryId);
			        user.setCategoryName(categoryName);
			    }
			}
			
			
			if (data.isEmpty()) {	
				model.addAttribute("errorMessage", "No Record Found");
			} else {
				model.addAttribute(USER_LIST, data);
			}

		} else {
			List<Category> categoryList=userReportService.getAllCategory();
			 sortedCategoryMap=  categoryList.stream()
						.collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
			 
			
			List<DODServices> serviceList=userReportService.getUserService();
			 sortedPaoCategoryBasedOnServiceMap =  serviceList.stream()
						.collect(Collectors.toMap(DODServices::getServiceId, DODServices::getServiceName));
			
			List<GetUserReportModel> userList = userReportService.getviewUserReport(userInputModel);
			for (GetUserReportModel user : userList) {
			    String serviceId = user.getUserServiceId();
			    if (sortedPaoCategoryBasedOnServiceMap.containsKey(serviceId)) {
			        String serviceName = sortedPaoCategoryBasedOnServiceMap.get(serviceId);
			        user.setServiceName(serviceName);
			    }
			}
			for (GetUserReportModel user : userList) {
			    String categoryId = user.getCategoryId();
			    if (sortedCategoryMap.containsKey(categoryId)) {
			        String categoryName = sortedCategoryMap.get(categoryId);
			        user.setCategoryName(categoryName);
			    }
			}
			if (userList.isEmpty()) {
				model.addAttribute("errorMessage", "No Record Found");
			} else {
				model.addAttribute(USER_LIST, userList);
			}
		}
		return pageURL + "unituserreport";
	}

	// POP-CONTROLLER
	@RequestMapping(value = "/userInformation", method = { RequestMethod.GET, RequestMethod.POST })
	public String appChangeService1(UserInputModel userInputModel, Model model, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return REDIRECT_LOGIN;
		}
		String encryptPno = userInputModel.getPersonalNo();
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, encryptPno);
		userInputModel.setPersonalNo(decryptPersonalNo);		

		String serviceId = "";
		if (loginUser.getUserServiceId() != null && !loginUser.getUserServiceId().equalsIgnoreCase("null")) {
			serviceId = loginUser.getUserServiceId();
		} else {
			serviceId = loginUser.getServiceId();
		}

		
		Optional<DODServices> dodServices = service.getServiceByServiceId(serviceId);
		String serviceType = "";

		if(dodServices.isPresent()) {
		 if (dodServices.get().getArmedForces().name().equalsIgnoreCase("YES")) {
			serviceType = ServiceType.ARMED_FORCES.getDisplayValue();
			
		} else {
			serviceType = ServiceType.CIVILIAN.getDisplayValue();
			
		}
		}
		model.addAttribute("serviceType", serviceType);
		
		model.addAttribute(USER_LIST, userReportService.getviewUserInformationDtls(userInputModel));
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		model.addAttribute("currentYear", currentYear);
		model.addAttribute("currentSubBlockYr", userReportService.getSubBlockYearOfYear(currentYear));
		model.addAttribute("previousSubBlockYear", userReportService.getSubBlockYearOfYear(currentYear-2));
		
		return pageURL + "userdatainformation";
	}
	
	// A J A X - Call Category Based On serviceId
	@GetMapping("/getCategorybasedOnService")
	@ResponseBody
	public Map<String, String> getCategorybasedOnService(@RequestParam String serviceId, Model model) {
		Map<String, String> map = new HashMap<>();

		if (serviceId != null && !serviceId.isEmpty() && !serviceId.isBlank()) {
			Map<String, String> categoryBasedOnService = userReportService.getCategoryBasedOnService(serviceId);
			
			model.addAttribute("categoryByUserServiceList", categoryBasedOnService);
			return categoryBasedOnService;
		} else {
			
			return map;
		}

		}
	

}
