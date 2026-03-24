package com.pcda.mb.adduser.usermastermissdasboard.service;

import java.math.BigInteger;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.PAOMappingModel;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.PAOMappingServices;
import com.pcda.login.model.LoginUser;
import com.pcda.mb.adduser.travelerprofile.service.TravelerProfileService;
import com.pcda.mb.adduser.usermastermissdasboard.controller.UserMasterMissDasboardController;
import com.pcda.mb.adduser.usermastermissdasboard.model.GetUserMasterMissHistoryModel;
import com.pcda.mb.adduser.usermastermissdasboard.model.GetUserMasterMissHistoryResponse;
import com.pcda.mb.adduser.usermastermissdasboard.model.GetUserMasterMissProfileModel;
import com.pcda.mb.adduser.usermastermissdasboard.model.PostSettleUserMasterMissStatusModel;
import com.pcda.mb.adduser.usermastermissdasboard.model.PostUserMasterMisResponse;
import com.pcda.mb.adduser.usermastermissdasboard.model.UserMasterMissProfileResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODDATAConstants;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.validation.Valid;

@Service
public class UserMasterMissDasboardService {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OfficesService officesService;

	@Autowired
	private PAOMappingServices mappingServices;

	// Get GroupId
	public Optional<OfficeModel> getOfficesByGroupId(BigInteger userId) {

		return officesService.getOfficeByUserId(userId);
	}

	// Get MasterMiss Profile
	public List<GetUserMasterMissProfileModel> getMasterMissProfile(String groupId) {

		List<GetUserMasterMissProfileModel> projectList = new ArrayList<>();
		try {
			ResponseEntity<UserMasterMissProfileResponse> responseEntity = restTemplate.exchange(
					PcdaConstant.MASTER_MISED_DASHBOARD_URL + "/getMasterMissProfile/" + groupId, HttpMethod.GET, null,
					new ParameterizedTypeReference<UserMasterMissProfileResponse>() {
					});
			UserMasterMissProfileResponse response = responseEntity.getBody();

			if (null != response && response.getErrorCode() == 200 && null != response.getResponseList()) {
				projectList = response.getResponseList();
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, UserMasterMissDasboardService.class, LogConstant.MASTER_MISS_PROFILE_LOG);
		}
		DODLog.info(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardService.class,
				" master miss profile" + projectList.size());
		return projectList;
	}

	// Change Date Format
	public static Date getDate(String dateString, String desireFormat) {
		Date date = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(desireFormat);
			dateFormat.applyPattern(desireFormat);
			if (dateString != null && !dateString.equals(""))
				date = dateFormat.parse(dateString);
		} catch (Exception e) {
			DODLog.info(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardService.class, "Exception in Date obj is :: " + e);
		}
		DODLog.info(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardService.class, "Date obj is :: " + date);
		return date;
	}

	// Date to String
	public static String formatDate(Date date, String format) {

		String dateString = "";

		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			if (date != null) {
				dateString = dateFormat.format(date);
			}
		} catch (Exception e) {
			DODLog.info(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardService.class, "Exception in Date obj is :: " + e);
		}
		return dateString;
	}

	// Get User Profile Data
	public GetUserMasterMissProfileModel getviewUserDetails(String personalNo) {
		String dmyFormat = "dd-MM-yyyy";
		DODLog.info(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardService.class, "getviewUserDetails::" + personalNo);
		GetUserMasterMissProfileModel mmprofileModel = new GetUserMasterMissProfileModel();

		try {
			UserMasterMissProfileResponse response = restTemplate.getForObject(
					PcdaConstant.MASTER_MISED_DASHBOARD_URL + "/getUserProfileDetailsByPno/" + personalNo,
					UserMasterMissProfileResponse.class);
			if (response == null) {
				response = new UserMasterMissProfileResponse();

			}

			mmprofileModel = response.getResponse();
			
			mmprofileModel.setMobileNo(Base64Coder.decodeString(mmprofileModel.getMobileNo()));

			Date date = getDate(mmprofileModel.getDateOfBirth(), dmyFormat);
			String s1 = formatDate(date, dmyFormat);
			mmprofileModel.setDateOfBirth(s1);

			Date date2 = getDate(mmprofileModel.getDateOfRetirment(), dmyFormat);
			String s2 = formatDate(date2, dmyFormat);
			mmprofileModel.setDateOfRetirment(s2);

			Date date3 = getDate(mmprofileModel.getCommisioningDate(), dmyFormat);
			String s3 = formatDate(date3, dmyFormat);
			mmprofileModel.setCommisioningDate(s3);

			mmprofileModel.getFamilyDetails().forEach(e -> {
				e.setDobStr(CommonUtil.formatDate(e.getDob(), dmyFormat));
				e.setPartDateStr(CommonUtil.formatDate(e.getPartDate(), dmyFormat));
			});

			DODLog.info(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardService.class, "Get User Profile Details::" + mmprofileModel);
		} catch (Exception e) {
			DODLog.printStackTrace(e, UserMasterMissDasboardService.class,LogConstant.MASTER_MISS_PROFILE_LOG);
		}
		return mmprofileModel;
	}

	
	// Get Master Missing Commnent History
	public List<GetUserMasterMissHistoryModel> getViewMMCommentHistory(String personalNo) {
	

		List<GetUserMasterMissHistoryModel> projectList = new ArrayList<>();
		try {
			ResponseEntity<GetUserMasterMissHistoryResponse> responseEntity = restTemplate.exchange(
					PcdaConstant.MASTER_MISED_DASHBOARD_URL + "/getUserMasterMissHistory/" + personalNo, HttpMethod.GET,
					null, new ParameterizedTypeReference<GetUserMasterMissHistoryResponse>() {
					});
			GetUserMasterMissHistoryResponse response = responseEntity.getBody();

			if (null != response && response.getErrorCode() == 200 && null != response.getResponseList()) {
				projectList = response.getResponseList();
				
				projectList.stream().forEach(obj->obj.setComunctionDateStr(CommonUtil.getChangeFormat(obj.getComunctionDate(),"yyyy-mm-dd", "dd-MMM-yyyy")));
			
			}		
		} catch (Exception e) {
			DODLog.printStackTrace(e, UserMasterMissDasboardService.class, LogConstant.MASTER_MISS_PROFILE_LOG);
		}
		DODLog.info(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardService.class,
				" master miss comment history" + projectList.size());
		return projectList;
	}

	// Save Settle Master Miss User
	public String saveSettleMasterMissUser(PostSettleUserMasterMissStatusModel postSettleUserMasterMissmodel,
			BindingResult result) {

		String message = "";

		try {
			if (result.hasErrors()) {
				DODLog.error(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardService.class,
						"Error Settle Master Miss User:::::" + result.getAllErrors());

			} else {
				postSettleUserMasterMissmodel.setUserAlias(postSettleUserMasterMissmodel.getPersonalNo());
				PostUserMasterMisResponse postUserMasterMisResponse = restTemplate.postForObject(
						new URI(PcdaConstant.MASTER_MISED_DASHBOARD_URL + "/settleUserMasterMissStatus"),
						postSettleUserMasterMissmodel, PostUserMasterMisResponse.class);

				DODLog.info(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardService.class,
						"Save Settle Master Miss User Response::::" + postUserMasterMisResponse);
				if (postUserMasterMisResponse != null && postUserMasterMisResponse.getErrorMessage() != null
						&& postUserMasterMisResponse.getErrorCode() == 200) {
					message = postUserMasterMisResponse.getErrorMessage();
				}
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, UserMasterMissDasboardService.class, LogConstant.MASTER_MISS_PROFILE_LOG);
		}
		return message;
	}
	
	// Get Map of PAO Office Id and Name
	public Map<String, String> getPaoOfficesMap() {
		List<OfficeModel> paoOfficesList = officesService.getOffices("PAO", "1");
		Map<String, String> paoOfficeMap = paoOfficesList.stream().collect(Collectors.toMap(OfficeModel::getGroupId, OfficeModel::getName));
		DODLog.info(LogConstant.MASTER_MISS_PROFILE_LOG, TravelerProfileService.class, "Pao Map: " + paoOfficeMap);
		return paoOfficeMap;
	}

	// Get office by user id
	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {
			return officesService.getOfficeByUserId(userId);
	}

	// Get office by group id
	public Optional<OfficeModel> getOfficeByGroupId(String groupId) {
			return officesService.getOfficeByGroupId(groupId);
	}

	
	// Get PAO Offices List
	public Map<String, List<PAOMappingModel>> getPaoOffice(String serviceId, String categoryId, LoginUser loginUser) {
		DODLog.info(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardController.class, "PAO List with service id: " + serviceId + " ; category id: " + categoryId);
		Map<String, String> paoOfficeMap = getPaoOfficesMap();
		List<PAOMappingModel> railPAOOfficeList = new ArrayList<>();
		List<PAOMappingModel> airPAOOfficeList = new ArrayList<>();
		List<PAOMappingModel> paoModelList = mappingServices.getPaoMappingServiceWithApproval("1");

		if (serviceId.equals(DODDATAConstants.NAVY_SEVICE_ID) && loginUser.getServiceId().equals(DODDATAConstants.NAVY_SEVICE_ID)) {
		
			setOfficePaoList(loginUser.getUserId(),railPAOOfficeList,airPAOOfficeList);

		} else {
			paoModelList = paoModelList.stream().filter(pao -> pao.getServiceId().equals(serviceId) && pao.getCategoryId().equals(categoryId)).toList();

			paoModelList.forEach(e -> e.setName(setGroupName(e.getAcuntoficeId(), paoOfficeMap)));

			airPAOOfficeList = paoModelList.stream().filter(ob -> ob.getTravelType().ordinal()==1).toList();
			railPAOOfficeList = paoModelList.stream().filter(ob -> ob.getTravelType().ordinal()==0).toList();
		}

		return Map.of("0", railPAOOfficeList, "1", airPAOOfficeList);
	}
	
	
	
	public void setOfficePaoList(BigInteger userId,List<PAOMappingModel> railPAOOfficeList ,List<PAOMappingModel> airPAOOfficeList ) {
		
		Optional<OfficeModel> officeModel = getOfficeByUserId(userId);
			if (officeModel.isPresent()) {
				if (officeModel.get().getPaoGroupId() != null) {
					Optional<OfficeModel> paoOfficeModel = getOfficeByGroupId(officeModel.get().getPaoGroupId());
					PAOMappingModel mappingModel = new PAOMappingModel();
	               
					if(paoOfficeModel.isPresent()) {
					mappingModel.setName(paoOfficeModel.get().getName());
					mappingModel.setAcuntoficeId(paoOfficeModel.get().getGroupId());
					}

					railPAOOfficeList.add(mappingModel);
				}
				if (officeModel.get().getPaoAirGroupId() != null) {
					Optional<OfficeModel> paoAirOfficeModel = getOfficeByGroupId(officeModel.get().getPaoGroupId());
					PAOMappingModel mappingModel = new PAOMappingModel();

					if (paoAirOfficeModel.isPresent()) {
						mappingModel.setName(paoAirOfficeModel.get().getName());
						mappingModel.setAcuntoficeId(paoAirOfficeModel.get().getGroupId());
					}

					airPAOOfficeList.add(mappingModel);
				}
			}
	}
	
	// Set Group Name
	private String setGroupName(String acuntoficeId, Map<String, String> paoMap) {
		if (paoMap.containsKey(acuntoficeId)) {
			return paoMap.get(acuntoficeId);
		} else {
			return "";
		}
	}
	
	
	
	
	//update  Pao 
	
	public PostSettleUserMasterMissStatusModel updatePao(@ModelAttribute @Valid PostSettleUserMasterMissStatusModel postSettleUserMasterMissStatusModel,
			BindingResult result) {
		try {
			postSettleUserMasterMissStatusModel.setPersonalNo(postSettleUserMasterMissStatusModel.getUserAlias());
			DODLog.info(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardService.class, "PAO AFTER EDIT UPDATED::" + postSettleUserMasterMissStatusModel);
			if (result.hasErrors()) {
				DODLog.error(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardController.class,
						"ERROR PAO AFTER EDIT UPDATE:: " + result.getAllErrors().get(0).getDefaultMessage());
				
			} else {
				String res =restTemplate.postForObject(PcdaConstant.MASTER_MISED_DASHBOARD_URL + "/updatePaoProfile",
						postSettleUserMasterMissStatusModel,String.class);
				DODLog.info(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardService.class, "Response" + res);
				
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, UserMasterMissDasboardService.class, LogConstant.MASTER_MISS_PROFILE_LOG);
		}
		return postSettleUserMasterMissStatusModel;
	}
	


}
