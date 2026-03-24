package com.pcda.mb.finalclaim.supplementaryclaim.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.User;
import com.pcda.common.services.AirportServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.StationServices;
import com.pcda.common.services.UserServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.finalclaim.claimrequest.model.ClaimRequestBean;
import com.pcda.mb.finalclaim.claimrequest.model.ClaimRequestResponse;
import com.pcda.mb.finalclaim.claimrequest.model.CounterPersonalInfoBean;
import com.pcda.mb.finalclaim.claimrequest.model.CounterPersonalInfoResponse;
import com.pcda.mb.finalclaim.claimrequest.model.GetCityBean;
import com.pcda.mb.finalclaim.claimrequest.model.GetCityResponse;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimCertifyDtlsBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimHotelDtlsBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimLeaveDtlsBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimRequestBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimRequestBeanResponse;
import com.pcda.mb.finalclaim.claimrequest.util.TADAClaimValidation;
import com.pcda.mb.finalclaim.supplementaryclaim.model.SupplementaryClaimReqResponse;
import com.pcda.mb.finalclaim.supplementaryclaim.model.SupplementaryClaimViewResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SupplementaryClaimService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UserServices userServices;

	@Autowired
	private StationServices stationServices;

	@Autowired
	private AirportServices airportServices;

	@Autowired
	private OfficesService officesService;

	public SupplementaryClaimReqResponse getSupplementaryClaimList(String groupId, String personalNo, String claimId) {
		DODLog.info(LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE, SupplementaryClaimService.class, "[getSupplementaryClaimList] Get Supplementary Claim Requests with personal no: "
				+ personalNo + " ;group Id: " + groupId + " ;claim Id: " + claimId);

		SupplementaryClaimReqResponse response =null;
		String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimSupplementary/supplementaryClaimListView";
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(uri);
		if (personalNo != null && !personalNo.isEmpty()) {
			urlBuilder.queryParam("personalNo", personalNo);
		}
		if (claimId != null && !claimId.isEmpty()) {
			urlBuilder.queryParam("claimId", claimId);
		}
		UriComponents builder = urlBuilder.queryParam("groupId", groupId).build();
		
try {
		ResponseEntity<SupplementaryClaimReqResponse> responseEntity = restTemplate.exchange(builder.toString(), HttpMethod.GET, null,
				SupplementaryClaimReqResponse.class);

		 response = responseEntity.getBody();
		
}catch(Exception e) {
	DODLog.printStackTrace(e, SupplementaryClaimService.class, LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE);
}
DODLog.info(LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE, SupplementaryClaimService.class, "[getSupplementaryClaimList] Supplementary Claim Request Response: " + response);
		return response;
	}

	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {
		DODLog.info(LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE, SupplementaryClaimService.class, "[getOfficeByUserId] Get user with user id: " + userId);
		return officesService.getOfficeByUserId(userId);
	}

	public SupplementaryClaimViewResponse getSupplementaryDetailsByClaimId(String claimId) {
		DODLog.info(LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE, SupplementaryClaimService.class, "[getSupplementaryDetailsByClaimId] Get Supplementary Details By Claim Id: " + claimId);
		SupplementaryClaimViewResponse response =null;
		String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimSupplementary/supplementaryDetailsByClaimID";
		UriComponents urlBuilder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("tadaClaimId", claimId).build();
try {
		ResponseEntity<SupplementaryClaimViewResponse> responseEntity = restTemplate.exchange(urlBuilder.toString(),
				HttpMethod.GET, null, SupplementaryClaimViewResponse.class);
		 response = responseEntity.getBody();
	}catch(Exception e) {
		DODLog.printStackTrace(e, SupplementaryClaimService.class, LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE);
	}
		DODLog.info(LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE, SupplementaryClaimService.class, "Supplementary Claim Response: " + response);
		return response;
	}

	public ViewClaimRequestBean getViewDataForClaim(String tadaclaimId) {
		ViewClaimRequestBean tadaClaimSettledModel = new ViewClaimRequestBean();

		try {
			String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRequest/ViewByClaimId";
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("tadaClaimId", tadaclaimId).build();

			ResponseEntity<ViewClaimRequestBeanResponse> responseEntity = restTemplate.exchange(builder.toString(), HttpMethod.GET, null, ViewClaimRequestBeanResponse.class);
			ViewClaimRequestBeanResponse tadaClaimSettledResponse = responseEntity.getBody();

			if (tadaClaimSettledResponse != null && tadaClaimSettledResponse.getErrorCode() == 200) {
				tadaClaimSettledModel = tadaClaimSettledResponse.getResponse();
	   
				if(tadaClaimSettledModel.getClaimLeaveDtls()!=null) {
					Set<ViewClaimLeaveDtlsBean> sortedHashSet = tadaClaimSettledModel.getClaimLeaveDtls().stream()
					        .sorted(Comparator.comparing(ViewClaimLeaveDtlsBean::getLeaveDate))
					        .collect(Collectors.toCollection(LinkedHashSet::new));
					
					tadaClaimSettledModel.setClaimLeaveDtls(sortedHashSet);
				}
				
				if(tadaClaimSettledModel.getClaimCertifyView()!=null) {
					Set<ViewClaimCertifyDtlsBean>  sortedHashSet= tadaClaimSettledModel.getClaimCertifyView().stream()
							.sorted(Comparator.comparing(ViewClaimCertifyDtlsBean :: getSeqNo))
							 .collect(Collectors.toCollection(LinkedHashSet::new));
					
					tadaClaimSettledModel.setClaimCertifyView(sortedHashSet);
							}
				
				if(tadaClaimSettledModel.getClaimHotelDtls()!=null) {
					Set<ViewClaimHotelDtlsBean> sortedHashSet = tadaClaimSettledModel.getClaimHotelDtls().stream()
							.sorted(Comparator.comparing(ViewClaimHotelDtlsBean :: getSeqNo))
							 .collect(Collectors.toCollection(LinkedHashSet::new));
					tadaClaimSettledModel.setClaimHotelDtls(sortedHashSet);					
				}
			}
			DODLog.info(LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE, SupplementaryClaimService.class, "[getViewDataForClaim] ## Ta Da Claim View Model: " + tadaClaimSettledModel);

		} catch (Exception e) {
			DODLog.printStackTrace(e, SupplementaryClaimService.class, LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE);
		}

		return tadaClaimSettledModel;
	}

	public CounterPersonalInfoBean getCounterPersonalInfo(String userAlias) {
		CounterPersonalInfoBean counterPersonalInfo = new CounterPersonalInfoBean();

		String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRequest/getCounterPersonalInfo";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("personalNumber", userAlias).build();
try {
		ResponseEntity<CounterPersonalInfoResponse> responseEntity = restTemplate.exchange(builder.toString(), HttpMethod.GET, null, CounterPersonalInfoResponse.class);
		CounterPersonalInfoResponse response = responseEntity.getBody();
		if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
			counterPersonalInfo = response.getResponse();
		}
		if((counterPersonalInfo.getFlag()==null || counterPersonalInfo.getFlag().equals("null"))&&
				(counterPersonalInfo.getMessage()==null || counterPersonalInfo.getMessage().equals("null"))) {
			counterPersonalInfo.setMessage("Personal Number you have entered is not correct");
		}
	} catch (Exception e) {
		DODLog.printStackTrace(e, SupplementaryClaimService.class, LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE);
	}
		
		DODLog.info(LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE, SupplementaryClaimService.class, "[getCounterPersonalInfo] ## Get Counter Personal Info: " + userAlias + " ; " + counterPersonalInfo);
		return counterPersonalInfo;
	}

	// Get Stations
	public List<String> getStation(String station) {
		List<String> stationList = stationServices.getStation(station);
		
		return stationList;
	}

	// Get Airport
	public List<String> getAirport(String airPortName) {
		List<String> airportList = airportServices.getAirport(airPortName);
		
		return airportList;
	}

	// Get City List
	public List<GetCityBean> getCityList(String city) {
	
		List<GetCityBean> cityList = new ArrayList<>();

		String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRequest/getCityList";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("city", city).build();

		ResponseEntity<GetCityResponse> responseEntity = restTemplate.exchange(builder.toString(), HttpMethod.GET, null, GetCityResponse.class);
		GetCityResponse response = responseEntity.getBody();
		if (response != null && response.getErrorCode() == 200 && response.getResponseList() != null) {
			cityList = response.getResponseList();
		}

		DODLog.info(LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE, SupplementaryClaimService.class, "[getCityList] Get City List::::: "+ cityList.size());
		return cityList;
	}

	public Map<Boolean, String> saveSupClaimData(HttpServletRequest request) {
		Map<Boolean, String> resultMap = new HashMap<>();
		String errorMessage = "Unable to save your claim request. Kindly contact DTS Helpline.";
		String travelTypeId = Optional.ofNullable(request.getParameter("travelTypeId")).orElse("");
		try {
			ClaimRequestBean saveTADAClaimDTO = null;
			  if(travelTypeId.equals("100002")){
				  saveTADAClaimDTO = new ClaimRequestBean(request);
			  }else if(travelTypeId.equals("100005") || travelTypeId.equals("100006") || travelTypeId.equals("100007") || travelTypeId.equals("100008")){
				  saveTADAClaimDTO = new ClaimRequestBean();
				  saveTADAClaimDTO.setLTCClaimDetails(request);
			  }else if(travelTypeId.equals("100001")){
				  saveTADAClaimDTO = new ClaimRequestBean();
				  saveTADAClaimDTO.setPTClaimDetails(request);
			  }

			

			  if (saveTADAClaimDTO != null) {
				  SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
				  LoginUser loginUser = sessionVisitor.getLoginUser();

				 

				  saveTADAClaimDTO.setTravelTypeId(travelTypeId);
				  saveTADAClaimDTO.setSuppClaimId(request.getParameter("suppClaimId"));
				  saveTADAClaimDTO.setLoginUserId(loginUser.getUserId());
				  saveTADAClaimDTO.setIrlaGrpID(Optional.ofNullable(request.getParameter("irlaGrpID")).orElse(""));
				  saveTADAClaimDTO.setPersonalNo(Optional.ofNullable(request.getParameter("personalNo")).orElse(""));
				  saveTADAClaimDTO.setUnitId(Optional.ofNullable(request.getParameter("unitId")).orElse(""));
				  DODLog.info(LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE, SupplementaryClaimService.class, "[saveSupClaimData] ## ClaimRequestBean: " + saveTADAClaimDTO);
				  String result="";
				  if(travelTypeId.equals("100002")){
					  result = TADAClaimValidation.validateClaimData(saveTADAClaimDTO);
				  }else if(travelTypeId.equals("100005") || travelTypeId.equals("100006") || travelTypeId.equals("100007") || travelTypeId.equals("100008")){
					  result = TADAClaimValidation.validateLTCClaimData(saveTADAClaimDTO);
				  }else if(travelTypeId.equals("100001")){
					  result = TADAClaimValidation.validatePTClaimData(saveTADAClaimDTO);
				  }

				  Optional<User> optionalUser = userServices.getUserByUserAlias(request.getParameter("personalNo"));
				  User user = new User();
				  if (optionalUser.isPresent()) {
					  user = optionalUser.get();
				  }
				  saveTADAClaimDTO.setUserId(user.getUserId());


				  if (result.equalsIgnoreCase("OK")) {
					  ClaimRequestResponse response = restTemplate.postForObject(PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimSupplementary/SupplementaryClaimRequest", saveTADAClaimDTO, ClaimRequestResponse.class) ;
					  DODLog.info(LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE, SupplementaryClaimService.class, "Supplementary Claim Request Save Response: " + response);
					  if (response != null && response.getErrorCode() == 200 && response.getResponse() != null && !response.getResponse().isBlank()) {
						  resultMap.put(true, response.getResponse());
					  } else if (response != null && response.getErrorCode() != 200 && !response.getErrorMessage().isEmpty()) {
						  resultMap.put(false, response.getErrorMessage());
					  } else {
							resultMap.put(false, errorMessage);
					  }
				  } else {
					  resultMap.put(false, result);
				  }
			  } else {
					resultMap.put(false, errorMessage);
			  }

		} catch (Exception e) {
			DODLog.printStackTrace(e, SupplementaryClaimService.class, LogConstant.SUPPLEMENTRY_CLAIM_LOG_FILE);
			resultMap.clear();
			resultMap.put(false, errorMessage);
		}
		
		  return resultMap;
	}

}
