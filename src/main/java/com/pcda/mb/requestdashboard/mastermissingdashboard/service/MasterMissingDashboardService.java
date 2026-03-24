package com.pcda.mb.requestdashboard.mastermissingdashboard.service;

import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
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

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.PAOMappingModel;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.PAOMappingServices;
import com.pcda.login.model.LoginUser;
import com.pcda.mb.adduser.usermastermissdasboard.model.GetUserMasterMissProfileModel;
import com.pcda.mb.adduser.usermastermissdasboard.model.PostUserMasterMisResponse;
import com.pcda.mb.adduser.usermastermissdasboard.model.UserMasterMissProfileResponse;
import com.pcda.mb.adduser.usermastermissdasboard.service.UserMasterMissDasboardService;
import com.pcda.mb.requestdashboard.mastermissingdashboard.controller.MasterMissingDashBoardController;
import com.pcda.mb.requestdashboard.mastermissingdashboard.model.GetMasterMissingModel;
import com.pcda.mb.requestdashboard.mastermissingdashboard.model.GetMasterMissingProfileModel;
import com.pcda.mb.requestdashboard.mastermissingdashboard.model.MasterMissingProfileResponse;
import com.pcda.mb.requestdashboard.mastermissingdashboard.model.MasterMissingResponse;
import com.pcda.mb.requestdashboard.mastermissingdashboard.model.PostMasterMissingModel;
import com.pcda.mb.requestdashboard.mastermissingdashboard.model.PostSettleMasterMissEditModel;
import com.pcda.util.DODDATAConstants;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.validation.Valid;

@Service
public class MasterMissingDashboardService {
	
	
	@Autowired
	private OfficesService officesService;
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PAOMappingServices mappingServices;

	
	
	// Get GroupId
		public Optional<OfficeModel> getOfficesByGroupId(BigInteger userId) {

			return officesService.getOfficeByUserId(userId);
		}

		//Get MasterMissing Details
		
    public List<GetMasterMissingModel> getMasterMissingDetails(String groupId) {
	DODLog.info(LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE, MasterMissingDashboardService.class,
			"Get Master Miss Profile :: "+groupId);
            
	List<GetMasterMissingModel> modellist = new ArrayList<>();
	try {
		
		ResponseEntity<MasterMissingResponse> responseEntity = restTemplate.exchange(
				PcdaConstant.MASTER_MISSING_DASHBOARD_BASE_URL + "/getMasterMissingData?groupId=" + groupId, HttpMethod.GET, null,
				new ParameterizedTypeReference<MasterMissingResponse>() {
				});
		MasterMissingResponse response = responseEntity.getBody();

		if (null != response && response.getErrorCode() == 200 && null != response.getResponseList()) {
			modellist = response.getResponseList();
		}
	} catch (Exception e) {
		DODLog.printStackTrace(e, UserMasterMissDasboardService.class, LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE);
	}
	DODLog.info(LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE, MasterMissingDashboardService.class,
			" master miss profile" + modellist.size());
	return modellist;
	
    }
	// Get User Data For Settle
	
	public GetUserMasterMissProfileModel getviewMastermissingComment(String personalNo) {

		GetUserMasterMissProfileModel mmprofileModel = new GetUserMasterMissProfileModel();

		try {
			UserMasterMissProfileResponse response = restTemplate.getForObject(
					PcdaConstant.MASTER_MISSING_DASHBOARD_BASE_URL + "/getUserProfileDetailsByPno/" + personalNo,
					UserMasterMissProfileResponse.class);
			if (response != null && response .getErrorCode()==200) {
				mmprofileModel = response.getResponse();

			}

			DODLog.info(LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE,  UserMasterMissDasboardService.class, "Get User Profile Details::" + mmprofileModel);
		} catch (Exception e) {
			DODLog.printStackTrace(e, UserMasterMissDasboardService.class, LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE);
		}
		return mmprofileModel;
	}

	
	
	// Settle Master Missing
	public String saveSettleMasterMissingDash(PostMasterMissingModel postMasterMissingModel, BindingResult result) {

		String msg = "";
		DODLog.info(LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE, MasterMissingDashboardService.class,
				"@@@@@@Save Settle Master Missing Dashboard @@@ ::::" + postMasterMissingModel);
		try {
			if (result.hasErrors()) {
				DODLog.error(LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE, MasterMissingDashboardService.class,
						"Error Settle Master Missing Dashboard:::::" + result.getAllErrors());

			} else {
				
				PostUserMasterMisResponse response = restTemplate.postForObject(
						new URI(PcdaConstant.MASTER_MISSING_DASHBOARD_BASE_URL + "/settleMasterMissStatus"),
						postMasterMissingModel, PostUserMasterMisResponse.class);

				if (response != null && response.getErrorCode() == 200 && response.getErrorMessage() != null) {
					msg = response.getErrorMessage();

				}

			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, MasterMissingDashboardService.class,
					LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE);
		}
		return msg;
	}
    
 // Get User Profile Data
 	public GetMasterMissingProfileModel getviewProfileDetails(String personalNo) {
 		DODLog.info(LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE,  MasterMissingDashboardService.class, "@@@Get User Profile Details from personalNo : " + personalNo);
 		GetMasterMissingProfileModel mmprofileModel = new GetMasterMissingProfileModel();

 		try {
 			MasterMissingProfileResponse response = restTemplate.getForObject(
 					PcdaConstant.MASTER_MISSING_DASHBOARD_BASE_URL + "/getUserProfileDetailsByPno/" + personalNo,
 					MasterMissingProfileResponse.class);
 			if (response == null) {
 				response = new MasterMissingProfileResponse();

 			}
 			mmprofileModel=response.getResponse();
 		

 			
 		} catch (Exception e) {
 			DODLog.printStackTrace(e, MasterMissingDashboardService.class, LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE);
 		}
 		return mmprofileModel;
 	}
 	

	// Get office by user id
	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {
			return officesService.getOfficeByUserId(userId);
	}

	// Get office by group id
	public Optional<OfficeModel> getOfficeByGroupId(String groupId) {
			return officesService.getOfficeByGroupId(groupId);
	}
	// Get Map of PAO Office Id and Name
		public Map<String, String> getPaoOfficesMap() {
			List<OfficeModel> paoOfficesList = officesService.getOffices("PAO", "1");
			Map<String, String> paoOfficeMap = paoOfficesList.stream().collect(Collectors.toMap(OfficeModel::getGroupId, OfficeModel::getName));
			
			return paoOfficeMap;
		}
		
		// Set Group Name
		private String setGroupName(String acuntoficeId, Map<String, String> paoMap) {
			if (paoMap.containsKey(acuntoficeId)) {
				return paoMap.get(acuntoficeId);
			} else {
				return "";
			}
		}
 	
 	
 // Get PAO Offices List
 	public Map<String, List<PAOMappingModel>> getPaoOffice(String serviceId, String categoryId, LoginUser loginUser) {
 		DODLog.info(LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE, MasterMissingDashBoardController.class, "PAO List with service id: " + serviceId + " ; category id: " + categoryId);
 		Map<String, String> paoOfficeMap = getPaoOfficesMap();
 		List<PAOMappingModel> railPAOOfficeList = new ArrayList<>();
 		List<PAOMappingModel> airPAOOfficeList = new ArrayList<>();
 		List<PAOMappingModel> paoModelList = mappingServices.getPaoMappingServiceWithApproval("1");

 		if (serviceId.equals(DODDATAConstants.NAVY_SEVICE_ID) && loginUser.getServiceId().equals(DODDATAConstants.NAVY_SEVICE_ID)) {

 			Optional<OfficeModel> officeModel = getOfficeByUserId(loginUser.getUserId());
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

 		} else {
 			paoModelList = paoModelList.stream().filter(pao -> pao.getServiceId().equals(serviceId) && pao.getCategoryId().equals(categoryId)).toList();

 			paoModelList.forEach(e -> e.setName(setGroupName(e.getAcuntoficeId(), paoOfficeMap)));

 			airPAOOfficeList = paoModelList.stream().filter(ob -> ob.getTravelType().ordinal()==1).toList();
			railPAOOfficeList = paoModelList.stream().filter(ob -> ob.getTravelType().ordinal()==0).toList();
 		}

 		return Map.of("0", railPAOOfficeList, "1", airPAOOfficeList);
 	}
 	//Save After Pao Edit Missing
 	public PostSettleMasterMissEditModel updatePaoMasterMissingDashboard(@ModelAttribute @Valid PostSettleMasterMissEditModel editModel,
			BindingResult result) {
		try {
			DODLog.info(LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE, MasterMissingDashboardService.class, "PostSettleMasterMissEditModel :: " + editModel);
			if (result.hasErrors()) {
				DODLog.error(LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE, MasterMissingDashBoardController.class,
						"ERROR PAO AFTER EDIT UPDATE:: " + result.hasErrors());
			} else {
				String res =restTemplate.postForObject(PcdaConstant.MASTER_MISSING_DASHBOARD_BASE_URL + "/updatePaoProfile",
						editModel,String.class);
				DODLog.info(LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE, MasterMissingDashboardService.class, "@@@@Response@@@" + res);
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, MasterMissingDashboardService.class, LogConstant.MASTER_MISSING_DASHBOARD_LOG_FILE);
		}
		return editModel;
	}

}
