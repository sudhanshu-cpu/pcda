package com.pcda.mb.adduser.disapprovedprofile.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.mb.adduser.disapprovedprofile.model.DisapResponse;
import com.pcda.mb.adduser.disapprovedprofile.model.DisapproveDTO;
import com.pcda.mb.adduser.disapprovedprofile.model.DisapprovedProfileModel;
import com.pcda.mb.adduser.disapprovedprofile.model.DisapprovedProfileResponse;
import com.pcda.mb.adduser.disapprovedprofile.model.EditDisapproveProfile;
import com.pcda.mb.adduser.disapprovedprofile.model.EditProfileFamilyDtls;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODDATAConstants;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class DisapprovedProfileService {

	@Autowired
	private OfficesService officesService;

	@Autowired
	private RestTemplate restTemplate;

	public Optional<OfficeModel> getOfficesByUserId(BigInteger userId) {

		return officesService.getOfficeByUserId(userId);
	}

	public DisapprovedProfileResponse getDisapprovedProfile(String personalNo, String officeId) {
		DisapprovedProfileResponse disapprovedProfileResponse=null;
		try {
		ResponseEntity<DisapprovedProfileResponse> responseEntity = restTemplate.exchange(
				PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/getProfileDetailsData?personalNo=" + personalNo +"&officeId="+ officeId, HttpMethod.GET, null,
				new ParameterizedTypeReference<>() {});

		disapprovedProfileResponse=responseEntity.getBody();
		
		}catch (Exception e) {
			
			DODLog.printStackTrace(e, DisapprovedProfileService.class, LogConstant.DISAPPROVE_PROFILE_LOG);
		}
		
		return disapprovedProfileResponse;
	}

	public DisapprovedProfileResponse getTraveler(String personalNo, String officeId) {
		
		DisapprovedProfileResponse response = null;
		try {
		DODLog.info(LogConstant.DISAPPROVE_PROFILE_LOG, DisapprovedProfileService.class, "Get user with personal no: " + personalNo + "; officeId:" + officeId);
		
		String uri = PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/getProfileViewData?personalNo="+personalNo+"&officeId="+officeId;
		
		response= restTemplate.getForObject(uri, DisapprovedProfileResponse.class);
	
		}catch(Exception e) {
		
		DODLog.printStackTrace(e, DisapprovedProfileService.class, LogConstant.DISAPPROVE_PROFILE_LOG);
		
	}
		return response;
	}

	public List<DisapprovedProfileModel> getDisappUsers(String officeId) {

		List<DisapprovedProfileModel> disapprovedProfileModel = new ArrayList<>();
		DODLog.info(LogConstant.DISAPPROVE_PROFILE_LOG, DisapprovedProfileService.class,
				"Disapproved Profile Date: " + "group id: " + officeId);
		try {

			ResponseEntity<DisapResponse> entity = restTemplate.exchange(
					PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/getDisapprovedProfile?officeId=" + officeId,
					HttpMethod.GET, null, new ParameterizedTypeReference<DisapResponse>() {
					});
			DisapResponse response = entity.getBody();

			if (null != response && response.getErrorCode() == 200 && response.getResponseList() != null) {
				disapprovedProfileModel = response.getResponseList();
			}
			

		} catch (Exception e) {
			DODLog.printStackTrace(e, DisapprovedProfileService.class, LogConstant.DISAPPROVE_PROFILE_LOG);
		}

		return disapprovedProfileModel;

	}

	public EditDisapproveProfile initProfile(DisapproveDTO travelerProfileDTO,HttpServletRequest request) {
		EditDisapproveProfile editTravelerUser = null;
		DODLog.info(LogConstant.DISAPPROVE_PROFILE_LOG, DisapprovedProfileService.class, "[initProfile] travelerProfileDTO: " + travelerProfileDTO);
		try {
			

			editTravelerUser = new EditDisapproveProfile();

			editTravelerUser.setLoginUserId(travelerProfileDTO.getLoginUserId());
			editTravelerUser.setUserAlias(travelerProfileDTO.getUserAlias());

			editTravelerUser.setFname(travelerProfileDTO.getFName());
			editTravelerUser.setMname(travelerProfileDTO.getMName());
			editTravelerUser.setLname(travelerProfileDTO.getLName());

			editTravelerUser.setEmailId(travelerProfileDTO.getEmail());
			editTravelerUser.setMobNo(travelerProfileDTO.getMobileNo());

			editTravelerUser.setPayAcOff(travelerProfileDTO.getPayAccOff());
			editTravelerUser.setAirAcOff(travelerProfileDTO.getAirAccOff());

			editTravelerUser.setErsPrintName(travelerProfileDTO.getErsPrntName());

			editTravelerUser.setGender(travelerProfileDTO.getGender());
			editTravelerUser.setMaritalStatus(travelerProfileDTO.getMaritalStatus());

			editTravelerUser.setHmeTownNrs(travelerProfileDTO.getHmeTwnStnNrs());
			editTravelerUser.setHmeTownPlace(travelerProfileDTO.getHmeTwnStnPlace());
			editTravelerUser.setDutyStnNrs(travelerProfileDTO.getDutyStnNrs());
			editTravelerUser.setSprPlace(travelerProfileDTO.getSprPlace());
			editTravelerUser.setSprNrs(travelerProfileDTO.getSprNrs());

			editTravelerUser.setRank(travelerProfileDTO.getRankId());
			editTravelerUser.setLevel(travelerProfileDTO.getLevel());

			editTravelerUser.setAccountNumber(travelerProfileDTO.getAccountNumber());
			editTravelerUser.setIfscCode(travelerProfileDTO.getIfscCode());

			editTravelerUser.setRetdCngReason(travelerProfileDTO.getRetirementChangeReason());
			editTravelerUser.setRetdCngAuthority(travelerProfileDTO.getRetirementChangeAuthority());

			editTravelerUser.setDateOfBirth(travelerProfileDTO.getDateOfBrth());
			editTravelerUser.setDateOfRetirement(travelerProfileDTO.getDateOfRetirement());
			editTravelerUser.setDateOfCom_join(travelerProfileDTO.getDateOfCom_join());

			editTravelerUser.setCdaAccNo(travelerProfileDTO.getCdaAccountNumber());

			editTravelerUser.setDutyStnNa(travelerProfileDTO.getDutyStnNa());
			editTravelerUser.setHmeTwnNa(travelerProfileDTO.getHmeTwnNa());
			editTravelerUser.setSprNa(travelerProfileDTO.getSprNa());

			editTravelerUser.setChangeAuthority(travelerProfileDTO.getChangeAuthority());

			editTravelerUser.setOdsNRS(travelerProfileDTO.getOdsNRS());
			editTravelerUser.setOdsNPA(travelerProfileDTO.getOdsNPA());
			editTravelerUser.setOdsSprNRS(travelerProfileDTO.getOdsSprNRS());
			editTravelerUser.setOdsSprNPA(travelerProfileDTO.getOdsSprNPA());

			editTravelerUser.setServiceId(travelerProfileDTO.getServiceId());
			editTravelerUser.setServiceName(travelerProfileDTO.getServiceName());
			editTravelerUser.setCategory(travelerProfileDTO.getCategoryId());
			editTravelerUser.setCategoryName(travelerProfileDTO.getCategoryName());

			List<EditProfileFamilyDtls> familyDetails = new ArrayList<>();
			StringBuilder childrenDateOfBirth = new StringBuilder();
              List<String> hostelNap=travelerProfileDTO.getChildHostelNAP();

			if (travelerProfileDTO.getFirstmemberName() != null) {
				AtomicInteger i=new AtomicInteger(0);
				IntStream.range(0, travelerProfileDTO.getFirstmemberName().size()).forEach(index -> {
					EditProfileFamilyDtls familyDtls = new EditProfileFamilyDtls();
					boolean checkStatus=true;
					int seq = 0;
					int noOffamilyMemDeleted = 0;
					String relation = "";
					String isDependant = "";

					if (travelerProfileDTO.getMemRelation() != null && travelerProfileDTO.getMemRelation().size() > index && 
							! travelerProfileDTO.getMemRelation().get(index).isBlank()) {
							 relation = travelerProfileDTO.getMemRelation().get(index);
					}

					if (travelerProfileDTO.getFirstmemberName() != null && travelerProfileDTO.getFirstmemberName().size() > index) {
						familyDtls.setFname(travelerProfileDTO.getFirstmemberName().get(index));
					}
					if (travelerProfileDTO.getMiddlememberName() != null && travelerProfileDTO.getMiddlememberName().size() > index) {
						familyDtls.setMname(travelerProfileDTO.getMiddlememberName().get(index));
					}
					if (travelerProfileDTO.getLastmemberName() != null && travelerProfileDTO.getLastmemberName().size() > index) {
						familyDtls.setLname(travelerProfileDTO.getLastmemberName().get(index));
					}

					String operationType = travelerProfileDTO.getOpType().get(index);
					if(operationType.equals("delete")){
						noOffamilyMemDeleted=noOffamilyMemDeleted+1;
					}

					String childDOB = "";
					Date memDob = stringToDate(travelerProfileDTO.getMemdob().get(index));
					Date compDate = stringToDate(DODDATAConstants.CHILD_DEPENDENT_DATE);
				
					if((!operationType.equals("delete")) && (relation.equals("2") || relation.equals("3") || relation.equals("4") || (relation.equals("5"))
							&& !isDependant.equals("0")) && memDob.after(compDate)){
						checkStatus=false;
						childDOB = travelerProfileDTO.getMemdob().get(index)+"::"+index+",";
						childrenDateOfBirth.append(childDOB);
					

						if (travelerProfileDTO.getChildHostelNRS() != null && !travelerProfileDTO.getChildHostelNRS().isEmpty() && travelerProfileDTO.getChildHostelNRS().get(i.get()) != null
								&& !travelerProfileDTO.getChildHostelNRS().get(i.get()).isEmpty()) {
							familyDtls.setChildHostelNRS(travelerProfileDTO.getChildHostelNRS().get(i.get()));
						}
						if (hostelNap != null && !hostelNap.isEmpty() && !hostelNap.get(i.get()).isEmpty()) {
							familyDtls.setChildHostelNAP(hostelNap.get(i.get()));
						}
						i.getAndIncrement();
					}

					if (!CommonUtil.isEmpty(travelerProfileDTO.getMemGender().get(index))) {
					familyDtls.setGender(travelerProfileDTO.getMemGender().get(index));
					}

					if (stringToDate(travelerProfileDTO.getMemdob().get(index)) != null) {
					familyDtls.setDob(travelerProfileDTO.getMemdob().get(index));
					}

					if (!relation.isBlank()) {
					familyDtls.setRelation(relation);
					}
					
					if (!CommonUtil.isEmpty(travelerProfileDTO.getMemMaritalStatus().get(index))) {
					familyDtls.setMaritalStatus(travelerProfileDTO.getMemMaritalStatus().get(index));
					}

					if (travelerProfileDTO.getDoIIPartNo() !=null && travelerProfileDTO.getDoIIPartNo().size() > index) {
					familyDtls.setDoIIPartNo(travelerProfileDTO.getDoIIPartNo().get(index));
					}
					if (travelerProfileDTO.getDoIIDate().size() > index && !travelerProfileDTO.getDoIIDate().get(index).isBlank() && stringToDate(travelerProfileDTO.getDoIIDate().get(index)) != null) {
					familyDtls.setDoIIDate(travelerProfileDTO.getDoIIDate().get(index));
					}

					if (travelerProfileDTO.getMemReson() != null && travelerProfileDTO.getMemReson().get(index) != null) {
					familyDtls.setReason(travelerProfileDTO.getMemReson().get(index));
					}
					familyDtls.setErsPrintName(travelerProfileDTO.getErsPrintName().get(index));
					familyDtls.setOpType(travelerProfileDTO.getOpType().get(index));
					familyDtls.setIsDependant(travelerProfileDTO.getIsDepen().get(index));
					familyDtls.setNoOffamilymemDeleted(noOffamilyMemDeleted);

					if(index<travelerProfileDTO.getSeqNo().size() && travelerProfileDTO.getSeqNo().get(index) != null && !travelerProfileDTO.getSeqNo().get(index).isBlank()) {  
						seq=Integer.parseInt(travelerProfileDTO.getSeqNo().get(index))-1;
					}
					else{
						seq=51;  
					}
					familyDtls.setSeqNo(seq);
					familyDtls.setMemSeqNo(Integer.parseInt(travelerProfileDTO.getMemSeqNo().get(index)));

					familyDtls.setCheckStatus(checkStatus);
					
					if(travelerProfileDTO.getRemark().size() > index) {
					familyDtls.setRemark(travelerProfileDTO.getRemark().get(index));
					}
					if (travelerProfileDTO.getRowNumber() != null && !travelerProfileDTO.getRowNumber().isBlank()) {
						familyDtls.setRowNum(Integer.parseInt(travelerProfileDTO.getRowNumber()));
					}

					if (travelerProfileDTO.getMstatus() != null && travelerProfileDTO.getMstatus().size() > index && travelerProfileDTO.getMstatus().get(index) != null) {
						familyDtls.setStatus(travelerProfileDTO.getMstatus().get(index));
					}

					familyDetails.add(familyDtls);
				});
			}

			editTravelerUser.setChildrenDateOfBirth(childrenDateOfBirth.toString());

			
			

			editTravelerUser.setFamilyDetails(familyDetails);

		} catch (Exception e) {
			
			DODLog.printStackTrace(e, DisapprovedProfileService.class, LogConstant.DISAPPROVE_PROFILE_LOG);
			return null;
		}
		DODLog.info(LogConstant.DISAPPROVE_PROFILE_LOG, DisapprovedProfileService.class, "Disapproved Profile User: " + editTravelerUser);
		return editTravelerUser;
	}

	private Date stringToDate(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			if (!dateString.isBlank()) {
				date = formatter.parse(dateString);
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, DisapprovedProfileService.class, LogConstant.DISAPPROVE_PROFILE_LOG);
			return null;
		}
		return date;
	}

	public Map<Boolean, String> updateProfile(EditDisapproveProfile travelerUser) { 
		Map<Boolean, String> resultMap = new HashMap<>();
		try {
			ResponseEntity<DisapprovedProfileResponse> response = restTemplate.postForEntity(PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/updateProfileToDb", travelerUser, DisapprovedProfileResponse.class);
			DisapprovedProfileResponse travelerUserResponse = response.getBody();
			DODLog.info(LogConstant.DISAPPROVE_PROFILE_LOG, DisapprovedProfileService.class, "update Profile Response::::: "+travelerUserResponse);
			if (travelerUserResponse != null) {
				
				if (travelerUserResponse.getErrorCode() == 200) {
					resultMap.put(true, "Profile updated");
					return resultMap;
				} else {
					resultMap.put(false, "Failes to update Profile");
					return resultMap;
				}
			} else {
				
				resultMap.put(false, "Unable To Update Profile");
			}
		} catch (Exception e) {
			
			DODLog.printStackTrace(e, DisapprovedProfileService.class, LogConstant.DISAPPROVE_PROFILE_LOG);
			resultMap.clear();
			resultMap.put(false, "Unable To Update Profile");
		}
		return resultMap;
	}

}
