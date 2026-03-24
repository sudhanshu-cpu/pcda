package com.pcda.mb.adduser.edittravelerprofile.service;

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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcda.common.model.Category;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.CategoryServices;
import com.pcda.common.services.OfficesService;
import com.pcda.mb.adduser.edittravelerprofile.model.EditCategoryResponse;
import com.pcda.mb.adduser.edittravelerprofile.model.EditFamilyDtls;
import com.pcda.mb.adduser.edittravelerprofile.model.EditTravelerDTO;
import com.pcda.mb.adduser.edittravelerprofile.model.EditTravelerUser;
import com.pcda.mb.adduser.edittravelerprofile.model.LevelInfo;
import com.pcda.mb.adduser.edittravelerprofile.model.TravelerReqResponse;
import com.pcda.mb.adduser.edittravelerprofile.model.ValidateBlockOfPnoResponse;
import com.pcda.mb.adduser.travelerprofile.model.TravelerUserResponse;
import com.pcda.util.DODDATAConstants;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EditTravelerService {

	@Autowired
	private RestTemplate template;

	@Autowired
	private OfficesService officesService;

	@Autowired
	private CategoryServices categoryServices;

	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {
		return officesService.getOfficeByUserId(userId);
	}

	public TravelerReqResponse getTravelerDetails(String personalNo, String officeId) {
		DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerService.class, "[getTravelerDetails] Get user with personal no: " + personalNo + "; officeId:" + officeId);
try {
		String uri = PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/getProfileDetailsData";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("personalNo", personalNo).queryParam("officeId", officeId).build();

		ResponseEntity<TravelerReqResponse> responseEntity = template.exchange(
				builder.toString(), HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
		DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerService.class, " [getTravelerDetails]::  get Edit  traveller response body"+responseEntity.getBody() );

		return responseEntity.getBody();
}catch(Exception e) {
	DODLog.printStackTrace(e, EditTravelerService.class, LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE);
}
return null;
	}

	public TravelerReqResponse getTraveler(String personalNo, String officeId) {
		DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerService.class, "[getTraveler]##  Get user with personal no: " + personalNo + "; officeId:" + officeId);

		try
		{
		String uri = PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/getProfileViewData";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("personalNo", personalNo).queryParam("officeId", officeId).build();

		ResponseEntity<TravelerReqResponse> responseEntity = template.exchange(
				builder.toString(), HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
 
		DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerService.class, " [getTraveler] ##  get Edit  traveller response body ::"+responseEntity.getBody() );

		return responseEntity.getBody();
		
		}catch(Exception e) {
			DODLog.printStackTrace(e, EditTravelerService.class, LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE);
		}
		return null;
	}

	
	public TravelerReqResponse getTravelerByPersonalnoAndOfficeId(String personalNo, String officeId) {
		TravelerReqResponse response = null;
		try {
		DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerService.class, "[getTravelerByPersonalnoAndOfficeId] ## Search user with personal no: " + personalNo + "; officeId:" + officeId);

		String uri = PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/getSearchData";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("personalNo", personalNo).queryParam("officeId", officeId).build();

		ResponseEntity<TravelerReqResponse> responseEntity = template.exchange(
				builder.toString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<>() {});
			response = responseEntity.getBody();
		} catch (Exception e) {
			DODLog.printStackTrace(e, EditTravelerService.class, LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE);
		}
		DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerService.class, "[getTravelerByPersonalnoAndOfficeId]  ## response " + response );
		return response;
	}


	public Map<Boolean, String> updateTraveler(EditTravelerUser travelerUser) { 
		Map<Boolean, String> resultMap = new HashMap<>();
		
		try {
			ResponseEntity<TravelerUserResponse> response = template.postForEntity(PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/updateProfileToDb", travelerUser, TravelerUserResponse.class);
			TravelerUserResponse travelerUserResponse = response.getBody();
			DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerService.class, "[updateTraveler]  ## Traveler User Response::::: "+ travelerUserResponse);
			if (travelerUserResponse != null) {
				
				if (travelerUserResponse.getErrorCode() == 200) {
					resultMap.put(true, "Traveler updated");
					return resultMap;
				} else {
					resultMap.put(false, "Fails to update Traveler");
					return resultMap;
				}

			} else {
				
				resultMap.put(false, "Unable To Update Traveler");
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, EditTravelerService.class, LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE);
			resultMap.clear();
			resultMap.put(false, "Unable To Update Traveler");
		}
		return resultMap;
	}

	public EditTravelerUser initTraveler(EditTravelerDTO travelerProfileDTO,HttpServletRequest request) {
		EditTravelerUser editTravelerUser = null;
	
		try {
			

			editTravelerUser = new EditTravelerUser();

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

			List<EditFamilyDtls> familyDetails = new ArrayList<>();
			StringBuilder childrenDateOfBirth = new StringBuilder();
//			String[] hostelNap = request.getParameterValues("childHostelNAP"); 
			List<String> hostelNap=travelerProfileDTO.getChildHostelNAP();
			
			if (travelerProfileDTO.getFirstmemberName() != null) {
				AtomicInteger i=new AtomicInteger(0);
				IntStream.range(0, travelerProfileDTO.getFirstmemberName().size()).forEach(index -> {
					EditFamilyDtls familyDtls = new EditFamilyDtls();
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
						
						DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerService.class, "#### i ##: " + i);
                         
						if (travelerProfileDTO.getChildHostelNRS() != null && !travelerProfileDTO.getChildHostelNRS().isEmpty() && travelerProfileDTO.getChildHostelNRS().get(i.get()) != null
								&& !travelerProfileDTO.getChildHostelNRS().get(i.get()).isEmpty()) {
							familyDtls.setChildHostelNRS(travelerProfileDTO.getChildHostelNRS().get(i.get()));
						}
					
						if (hostelNap != null && !hostelNap.isEmpty()&& hostelNap.get(i.get()) != null && !hostelNap.get(i.get()).isEmpty()) {
							
							familyDtls.setChildHostelNAP(hostelNap.get(i.get()));
						}
						
						i.getAndIncrement();
					}

					familyDtls.setGender(travelerProfileDTO.getMemGender().get(index));

					familyDtls.setDob(travelerProfileDTO.getMemdob().get(index));

					familyDtls.setRelation(relation);
					familyDtls.setMaritalStatus(travelerProfileDTO.getMemMaritalStatus().get(index));
					
					if (travelerProfileDTO.getDoIIPartNo().size()> index) {
					familyDtls.setDoIIPartNo(travelerProfileDTO.getDoIIPartNo().get(index));
					}
					if (travelerProfileDTO.getDoIIDate().size()> index) {
					familyDtls.setDoIIDate(travelerProfileDTO.getDoIIDate().get(index));
					}
					if (travelerProfileDTO.getMemReson().size()> index) {
					familyDtls.setReason(travelerProfileDTO.getMemReson().get(index));
					}

					familyDtls.setErsPrintName(travelerProfileDTO.getErsPrintName().get(index));
					familyDtls.setOpType(travelerProfileDTO.getOpType().get(index));
					
					if(travelerProfileDTO.getIsDepen().size() > index  && travelerProfileDTO.getIsDepen().get(index)!=null &&  !travelerProfileDTO.getIsDepen().get(index).isEmpty()) {
					familyDtls.setIsDependant(travelerProfileDTO.getIsDepen().get(index));
						}else {
							familyDtls.setIsDependant("1");
					}
					
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
			DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerService.class, "########### Traveler User:######### " + editTravelerUser);
		} catch (Exception e) {
			DODLog.printStackTrace(e, EditTravelerService.class, LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE);
			return null;
		}

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
			DODLog.printStackTrace(e, EditTravelerService.class, LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE);
			return null;
		}
		return date;
	}

	public String validateBlockOfPNo(String paoCode, String persnlNo) {
		
		
	
		
        String check = "false";
		try {
		ResponseEntity<ValidateBlockOfPnoResponse> response = template.exchange(PcdaConstant.TRAVELLER_AJAX_URL + "/validateBlockOfPersnlNo/" + paoCode + "/" + persnlNo,
				HttpMethod.GET, null, ValidateBlockOfPnoResponse.class);
		ValidateBlockOfPnoResponse pnoResponse = response.getBody();
		DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerService.class, "Validate Block Of Personal number Response: " + pnoResponse);
		if (pnoResponse != null && pnoResponse.getErrorCode() == 200 && pnoResponse.getResponse() != null) {
			check = pnoResponse.getResponse();
		}
		} catch (RestClientException e) {
			DODLog.printStackTrace(e, EditTravelerService.class, LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE);
		}
        return check;
	}

	public List<Category> getCategoryIdOnTraveller(String serviceId) {
		List<Category> categoryList = categoryServices.getCategories(serviceId);
		
		return categoryList;
	}

	public List<LevelInfo> getLevelOnCategory(String serviceId, String categoryId) {
		
		List<LevelInfo> responseList=new ArrayList<>();
		
		try {
			String urlTemplate = UriComponentsBuilder.fromHttpUrl(PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/getLevelDataOnEdit")
					                            .queryParam("serviceId", serviceId)
					                            .queryParam("categoryId", categoryId)
					                            .build().toString();
			        
			
			ResponseEntity<EditCategoryResponse> response = template.exchange(urlTemplate,
					HttpMethod.GET, null, EditCategoryResponse.class);
			EditCategoryResponse catResponse = response.getBody();
			
			
			if (catResponse != null && catResponse.getErrorCode() == 200 && catResponse.getResponseList() != null) {
				responseList.addAll(catResponse.getResponseList());
			}
		} catch (RestClientException e) {
			DODLog.printStackTrace(e, EditTravelerService.class, LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE);
		}
		DODLog.info(LogConstant.EDIT_TRAVELER_PROFILE_LOG_FILE, EditTravelerService.class, "get Level On Category ResponseList: " + responseList.size());
		return responseList;
	}

}
