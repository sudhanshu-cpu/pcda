
package com.pcda.mb.reports.userreports.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.Category;
import com.pcda.common.model.CategoryResponse;
import com.pcda.common.model.DODServices;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.ServiceResponse;
import com.pcda.common.model.VisitorModel;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.UserServices;
import com.pcda.mb.reports.userreports.model.GetTravllerProfileModel;
import com.pcda.mb.reports.userreports.model.GetUserReportModel;
import com.pcda.mb.reports.userreports.model.TravellerProfileResponseModel;
import com.pcda.mb.reports.userreports.model.UserInputModel;
import com.pcda.mb.reports.userreports.model.UserReportResponse;
import com.pcda.mb.reports.userreports.model.UserRoleModel;
import com.pcda.util.ApprovalState;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service

public class UserReportService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OfficesService officesService;
	
	@Autowired
	private UserServices userServices;

	// Get GroupId
	public Optional<OfficeModel> getOfficesByGroupId(BigInteger userId) {

		return officesService.getOfficeByUserId(userId);
	}

	// Get User Report Data

	public List<GetUserReportModel> getviewUserReport(UserInputModel userInputModel) {
		DODLog.info(LogConstant.USER_REPORT, UserReportService.class, "[getviewUserReport] @@@@userInputModel @@@"+userInputModel);
		
		if(userInputModel.getUserType().equals("1") || userInputModel.getUserType().equals("2")) {
			userInputModel.setType("1");	
		}else if(userInputModel.getUserType().equals("3")){
			userInputModel.setType("2");	
		}else {
			userInputModel.setType("0");
		}
		
		List<GetUserReportModel> projectList = new ArrayList<>();
		try {

			ResponseEntity<UserReportResponse> responseEntity = restTemplate.exchange(
					PcdaConstant.USER_MAP_URL + "/getMappedUser/" + userInputModel.getGroupId() + "/" + userInputModel.getType(), HttpMethod.GET,
					null, new ParameterizedTypeReference<UserReportResponse>() {
					});

			UserReportResponse response = responseEntity.getBody();

			if (null != response && response.getErrorCode() == 200 && null != response.getResponseList()) {
				projectList = response.getResponseList();
			}
			
			Predicate<GetUserReportModel> userServicePredicate = data -> {
				String userService = data.getUserServiceId();
				String inputtedUserService = userInputModel.getUserServiceId();
				return (inputtedUserService.equals("") || inputtedUserService.isEmpty()) || (userService !=null && userService.equals(inputtedUserService));
			};
			
			Predicate<GetUserReportModel> categoryPredicate = data -> {
				String categoryId = data.getCategoryId();
				String inputtedCategoryId  = userInputModel.getCategoryId();
				return (inputtedCategoryId.equals("") || inputtedCategoryId.isEmpty()) || (categoryId !=null && categoryId.equals(inputtedCategoryId));
			};
			
			if(userInputModel.getUserType().equalsIgnoreCase("1")) {
				projectList=projectList.stream().filter(obj->obj.getUserRole().get(0).getRoleId().equals(BigInteger.valueOf(10005))).toList();
			}
          	if(userInputModel.getUserType().equalsIgnoreCase("2")) {
				projectList=projectList.stream().filter(obj->obj.getUserRole().get(0).getRoleId().equals(BigInteger.valueOf(10004))).toList();
			}
			if(userInputModel.getApprovalStatus().equalsIgnoreCase("0")) {
				projectList=projectList.stream().filter(e->e.getApprovalState().equals(ApprovalState.APPROVED)).toList();
				
			}else if(userInputModel.getApprovalStatus().equalsIgnoreCase("3")) {
				
				projectList=projectList.stream().filter(e->e.getApprovalState().equals(ApprovalState.REQUESTED)).toList();
				
			}else if(userInputModel.getApprovalStatus().equalsIgnoreCase("1")) {
				
				projectList=projectList.stream().filter(e->e.getApprovalState().equals(ApprovalState.REJECTED)).toList();
			
			}
			
			Predicate<GetUserReportModel> finalPredicate = userServicePredicate
					                                       .and(categoryPredicate);
			 projectList = projectList.stream().filter(finalPredicate).toList();
			
			
		} catch (Exception e) {
			DODLog.printStackTrace(e, UserReportService.class, LogConstant.USER_REPORT);
		}

		DODLog.info(LogConstant.USER_REPORT, UserReportService.class, "[getviewUserReport] @@@@ projectList  @@@"+projectList);
		return projectList;
	}
	
	// get userInformation

	public GetTravllerProfileModel getviewUserInformationDtls(UserInputModel userInputModel) {

		GetTravllerProfileModel mmprofileModel = new GetTravllerProfileModel();
		DODLog.info(LogConstant.USER_REPORT, UserReportService.class,"[getviewUserInformationDtls] ## :: userInputModel :: "+userInputModel);
		
		try {
			

			TravellerProfileResponseModel response = restTemplate.getForObject(
					 PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/getProfileViewData?personalNo=" + userInputModel.getPersonalNo()
							+ "&officeId=" + userInputModel.getGroupId(),
					TravellerProfileResponseModel.class);

                if(response != null && response.getErrorCode()==200) {
				
			mmprofileModel = response.getResponse();
			mmprofileModel.setUserAlias(mmprofileModel.getSignInID());
		if(mmprofileModel.getLtcCurrentSubBlock()==null || mmprofileModel.getLtcCurrentSubBlock().isBlank()) {
			mmprofileModel.setLtcCurSubYesNo("NO");
		}else {mmprofileModel.setLtcCurSubYesNo("YES");}
		
		if(mmprofileModel.getLtcPreviousSubBlock()==null || mmprofileModel.getLtcPreviousSubBlock().isBlank()) {
			mmprofileModel.setLtcPrevCurSubYesNo("NO");
		}else {mmprofileModel.setLtcPrevCurSubYesNo("YES");}
			
		if(mmprofileModel.getLtcCurrentYear()==null || mmprofileModel.getLtcCurrentYear().isBlank()) {
			mmprofileModel.setLtcCurrYrYesNo("NO");
		}else {mmprofileModel.setLtcCurrYrYesNo("YES");}
		
			}
			
			
		} catch (Exception e) {
			DODLog.printStackTrace(e, UserReportService.class,LogConstant.USER_REPORT);
		}
		DODLog.info(LogConstant.USER_REPORT, UserReportService.class, "[getviewUserInformationDtls] @@@Get User Profile Details::@@" + mmprofileModel);
		
		

		return mmprofileModel;
	}
	
	
	
	public List<GetUserReportModel> userReportModel(UserInputModel userInputModel){
		
		DODLog.info(LogConstant.USER_REPORT, UserReportService.class, "[userReportModel] @@@Get User REPORT INPUT MODEL ::@@@" + userInputModel);
		
		Optional<VisitorModel> completUser=userServices.getCompleteUser(userInputModel.getPersonalNo());
		
		List<GetUserReportModel> projectList = new ArrayList<>();
		
		GetUserReportModel projectData=new GetUserReportModel();
		if(completUser.isPresent()) {
		projectData.setUserAlias(completUser.get().getUserAlias());
		projectData.setName(completUser.get().getName());
		projectData.setServiceId(completUser.get().getServiceId());
		projectData.setCategoryId(completUser.get().getCategoryId());
		projectData.setApprovalState(completUser.get().getApprovalState());
		projectData.setFirstName(completUser.get().getFirstName());
		projectData.setMiddleName(completUser.get().getMiddleName());
		projectData.setLastName(completUser.get().getLastName());
		projectData.setPersonalNumber(completUser.get().getUserAlias());
		UserRoleModel roleModel = new UserRoleModel(); 
		roleModel.setRoleId(completUser.get().getUserRole().get(0).getRoleId());
		projectData.getUserRole().add(roleModel);
		projectList.add(projectData);
		
		}
		if(userInputModel.getUserType().equals("1") || userInputModel.getUserType().equals("2")) {
			
			userInputModel.setType("1");	
		}else if(userInputModel.getUserType().equals("3")){
			
			userInputModel.setType("2");	
		}else {
			userInputModel.setType("0");
		}
	
		
		if(userInputModel.getUserType().equalsIgnoreCase("1")) {
			projectList=projectList.stream().filter(obj->obj.getUserRole().get(0).getRoleId().equals(BigInteger.valueOf(10005))).toList();
		}
      	if(userInputModel.getUserType().equalsIgnoreCase("2")) {
			projectList=projectList.stream().filter(obj->obj.getUserRole().get(0).getRoleId().equals(BigInteger.valueOf(10004))).toList();
		}
		if(userInputModel.getApprovalStatus().equalsIgnoreCase("0")) {
			projectList=projectList.stream().filter(e->e.getApprovalState().equals(ApprovalState.APPROVED)).toList();
			return projectList;
		}else if(userInputModel.getApprovalStatus().equalsIgnoreCase("3")) {
			
			projectList=projectList.stream().filter(e->e.getApprovalState().equals(ApprovalState.REQUESTED)).toList();
			return projectList;
		}else if(userInputModel.getApprovalStatus().equalsIgnoreCase("1")) {
			
			projectList=projectList.stream().filter(e->e.getApprovalState().equals(ApprovalState.REJECTED)).toList();
			return projectList;
		}else {
			return projectList;
		}
	
		
	}
	
	
	// User Service List
	public List<DODServices> getUserService() {
		List<DODServices> serviceList = new ArrayList<>();
		ResponseEntity<ServiceResponse> responseEntity = restTemplate.exchange(
				PcdaConstant.MASTER_BASE_URL + "/service/allServices/1", HttpMethod.GET, null,
				new ParameterizedTypeReference<com.pcda.common.model.ServiceResponse>() {
				});
		ServiceResponse serviceResponse = responseEntity.getBody();
		if (null != serviceResponse && serviceResponse.getErrorCode() == 200
				&& null != serviceResponse.getResponseList()) {
			serviceList = serviceResponse.getResponseList();
		}
		DODLog.info(LogConstant.USER_REPORT, UserReportService.class, "[getUserService] @@@@ serviceList  @@@" + serviceList.size());
		return serviceList;
	}

	// map of categoryId and categoryName w.r.t serviceId
	public Map<String, String> getCategoryBasedOnService(String serviceId) {
		DODLog.info(LogConstant.USER_REPORT, UserReportService.class,
				"@@@@SERVICE ID ::@@@ " +serviceId );
		List<Category> serviceCategoryList = new ArrayList<>();
		ResponseEntity<CategoryResponse> responseEntity = restTemplate.exchange(
				PcdaConstant.MASTER_BASE_URL + "/category/getCategories/" + serviceId, HttpMethod.GET, null,
				new ParameterizedTypeReference<CategoryResponse>() {
				});
		CategoryResponse categoryResponse = responseEntity.getBody();

		if (null != categoryResponse && (categoryResponse.getErrorCode() == 200)
				&& null != categoryResponse.getResponseList()) {
			serviceCategoryList = categoryResponse.getResponseList();
		}
		
		Map<String, String> serviceCategoryMap = serviceCategoryList.stream()
				.collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));

		

		return serviceCategoryMap;
	}
	public List<Category>getAllCategory() {
		List<Category> categoryList = new ArrayList<>();
		String url= PcdaConstant.MASTER_BASE_URL + "/category/allCategories/1";
		ResponseEntity<CategoryResponse> responseEntity = restTemplate.exchange(
			url	, HttpMethod.GET, null,
				new ParameterizedTypeReference<com.pcda.common.model.CategoryResponse>() {
				});
		CategoryResponse categoryResponse  = responseEntity.getBody();
		if (null != categoryResponse && categoryResponse.getErrorCode() == 200
				&& null != categoryResponse.getResponseList()) {
			categoryList = categoryResponse.getResponseList();
		}
		DODLog.info(LogConstant.USER_REPORT, UserReportService.class, "[getAllCategory]@@@@ categoryList @@@" + categoryList.size());
		return categoryList;
	}
	
	// Get Sub Block Of Year
				public String getSubBlockYearOfYear(int year) {
					String subBlockYearOfYear = "";

					if (year % 2 == 0) {
						subBlockYearOfYear = year + "-" + (year + 1);
					} else {
						subBlockYearOfYear = (year - 1) + "-" + year;
					}

					
					return subBlockYearOfYear;
				}
	
	
}
