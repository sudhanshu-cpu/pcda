package com.pcda.co.approveuser.approvetravelleredit.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.co.approveuser.approvetravelleredit.controller.EditTravellerAppController;
import com.pcda.co.approveuser.approvetravelleredit.model.ApprovalEditModel;
import com.pcda.co.approveuser.approvetravelleredit.model.EditFamiltDtls;
import com.pcda.co.approveuser.approvetravelleredit.model.ProfileAuditResponse;
import com.pcda.co.approveuser.approvetravelleredit.model.ProfileChangeDetails;
import com.pcda.co.approveuser.approvetravelleredit.model.TravellerEditReq;
import com.pcda.co.approveuser.approvetravelleredit.model.TravellerEditReqResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class EditTravellerAppService {

	@Autowired
	RestTemplate restTemplate;

	public List<TravellerEditReq> getAllUserProfileForApproval(BigInteger loginUserId ) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_EDIT_APPROVAL_LOG_FILE, EditTravellerAppService.class," ###### loginUserId ##########"+ loginUserId);
		List<TravellerEditReq> userList = new ArrayList<>();
		ResponseEntity<TravellerEditReqResponse> response = restTemplate.exchange(PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/view/" + loginUserId, HttpMethod.GET,
				null, TravellerEditReqResponse.class);
		TravellerEditReqResponse travellerResponse = response.getBody();
		if (travellerResponse != null && travellerResponse.getErrorCode() == 200 && travellerResponse.getResponseList() != null) {
			userList.addAll(travellerResponse.getResponseList());
		}
		DODLog.info(LogConstant.TRAVELER_PROFILE_EDIT_APPROVAL_LOG_FILE, EditTravellerAppService.class," ###### loginUserId ##########"+ userList.size());
		return userList;
	}

	public Map<String, List<ProfileChangeDetails>> getViewUser(String userAlias) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_EDIT_APPROVAL_LOG_FILE, EditTravellerAppService.class," ###### userAlias ##########"+ userAlias);
		Map<String, List<ProfileChangeDetails>>  profileMap = new HashMap<>();
		try {
			ProfileAuditResponse response = restTemplate.getForObject(PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/userView?personalNo=" + userAlias, ProfileAuditResponse.class) ;
			if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
				profileMap = response.getResponse();
			}
			
		} catch (Exception e) {
			DODLog.printStackTrace(e, EditTravellerAppService.class, LogConstant.TRAVELER_PROFILE_EDIT_APPROVAL_LOG_FILE);
		}
		
		return profileMap;
	}

	public void updateEditProfileApp(ApprovalEditModel appEditModel) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_EDIT_APPROVAL_LOG_FILE, EditTravellerAppService.class," ###### appEditModel ##########"+ appEditModel);
		
		try {
			String path = PcdaConstant.EDIT_TRAVELLER_BASE_URL;
			if (appEditModel.getApprovalType() == 1) {
				StringJoiner url = new StringJoiner("/")
						.add("/setProfileStatusToApprove")
						.add(appEditModel.getUserId().toString())
						.add(String.valueOf(appEditModel.getSeqNo()))
						.add(appEditModel.getLoginUserId().toString());
				ResponseEntity<String> response = restTemplate.postForEntity(path + url.toString(), null, String.class);
				DODLog.info(LogConstant.TRAVELER_PROFILE_EDIT_APPROVAL_LOG_FILE, EditTravellerAppService.class, response.toString());
			}else if (appEditModel.getApprovalType() == 2) {
				path = path + "/setProfileStatusToDisapprove";
				ResponseEntity<String> response = restTemplate.postForEntity(path, appEditModel, String.class);
				DODLog.info(LogConstant.TRAVELER_PROFILE_EDIT_APPROVAL_LOG_FILE, EditTravellerAppService.class, response.toString());
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, EditTravellerAppService.class, LogConstant.TRAVELER_PROFILE_EDIT_APPROVAL_LOG_FILE);
		}
	}
	
	public void setFamilyDtlsfrmAttr(List<ProfileChangeDetails> familyDtlsList,List<EditFamiltDtls> fmDtlsList) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_EDIT_APPROVAL_LOG_FILE, EditTravellerAppService.class," ###### familyDtlsList ########## "+ familyDtlsList 
				+" ###### fmDtlsList ########## "+ fmDtlsList);
		
		
		if(familyDtlsList!=null && !familyDtlsList.isEmpty()) {
			StringBuilder builer = new StringBuilder();
			for(ProfileChangeDetails profile :familyDtlsList) {
				builer.append(profile.getProfileChangeAttribute());
				builer.append(profile.getProfileChangeValue());
			}
			
			
			String build =builer.toString();
			String[] noOfAddDepndnt =build.split("@");
			
			
			for(int i=1;i<noOfAddDepndnt.length;i++) {
				
				EditFamiltDtls fmDtls=  new EditFamiltDtls();
				 
				String dependentStr=noOfAddDepndnt[i];
				 
				
			   String[] attr = dependentStr.split("#");
				 
				for(int j=0; j<attr.length-1 ; j++) {
					
					String[] fieldArr = attr[j].split(":"); 
					if(fieldArr[0].trim().equals("Name")) {
						fmDtls.setName(fieldArr[1]);
					}
					if(fieldArr[0].trim().equals("MIDDLE_NAME")) { fmDtls.setMiddleName(fieldArr[1])  ;}
					if(fieldArr[0].trim().equals("LAST_NAME")) { fmDtls.setLastName(fieldArr[1])  ;}
					if(fieldArr[0].trim().equals("Gender")) { fmDtls.setGender(fieldArr[1])  ;}
					if(fieldArr[0].trim().equals("Relation")) { fmDtls.setRelation(fieldArr[1])  ;}
					if(fieldArr[0].trim().equals("Marital Status")) { fmDtls.setMaritalStatus(fieldArr[1])  ;}
					if(fieldArr[0].trim().equals("DOB")) { fmDtls.setDOB(fieldArr[1])  ;}
					
					
					if(fieldArr[0].trim().equals("DOII Date") || fieldArr[0].trim().equals("GX Date")|| fieldArr[0].trim().equals("POR Date")) { fmDtls.setDOIIDate(fieldArr[1]);}
					if(fieldArr[0].trim().equals("DOII Part No") || fieldArr[0].trim().equals("GX No")||fieldArr[0].trim().equals("POR No")) { fmDtls.setDOIIpartNo(fieldArr[1]);}
					if(fieldArr[0].trim().equals("Ers Print Name")) { fmDtls.setErsPrintName(fieldArr[1])  ;}
					if(fieldArr[0].trim().equals("REASON")) { fmDtls.setReason(fieldArr[1])  ;}
					if(fieldArr[0].trim().equals("HOSTEL_NRS")) { fmDtls.setHostelNrs(fieldArr[1]);}
					if(fieldArr[0].trim().equals("HOSTEL_NAP")) { fmDtls.setHostelNap(fieldArr[1]);}
				
				}
				DODLog.info(LogConstant.TRAVELER_PROFILE_EDIT_APPROVAL_LOG_FILE, EditTravellerAppController.class," ::fmDtls :: "+i+" " +fmDtls);
				fmDtlsList.add(fmDtls);
				 
			 }
			}
		
	}
	

}
