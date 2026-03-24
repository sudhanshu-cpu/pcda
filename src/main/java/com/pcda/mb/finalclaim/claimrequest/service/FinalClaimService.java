package com.pcda.mb.finalclaim.claimrequest.service;

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
import com.pcda.common.model.TravelType;
import com.pcda.common.model.User;
import com.pcda.common.services.AirportServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.StationServices;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.common.services.UserServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.finalclaim.claimrequest.model.ClaimRequestBean;
import com.pcda.mb.finalclaim.claimrequest.model.ClaimRequestResponse;
import com.pcda.mb.finalclaim.claimrequest.model.ClaimViewResponse;
import com.pcda.mb.finalclaim.claimrequest.model.CounterPersonalInfoBean;
import com.pcda.mb.finalclaim.claimrequest.model.CounterPersonalInfoResponse;
import com.pcda.mb.finalclaim.claimrequest.model.FinalClaimReqSearchResponse;
import com.pcda.mb.finalclaim.claimrequest.model.GetCityBean;
import com.pcda.mb.finalclaim.claimrequest.model.GetCityResponse;
import com.pcda.mb.finalclaim.claimrequest.model.Recoverydetails;
import com.pcda.mb.finalclaim.claimrequest.model.TravelContent;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimCertifyDtlsBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimHotelDtlsBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimLeaveDtlsBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimRequestBean;
import com.pcda.mb.finalclaim.claimrequest.model.ViewClaimRequestBeanResponse;
import com.pcda.mb.finalclaim.claimrequest.util.TADAClaimValidation;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class FinalClaimService {

	@Autowired
	private UserServices userServices;

	@Autowired
	private AirportServices airportServices;

	@Autowired
	private OfficesService officesService;

	@Autowired
	private StationServices stationServices;

	@Autowired
	private TravelTypeServices travelTypeServices;

	@Autowired
	private RestTemplate template;

	public List<TravelType> getTravelType() {
		List<TravelType> travelTypes = new ArrayList<>();
		try {
			travelTypes = travelTypeServices.getAllTravelType(1);
		} catch (Exception e) {
			DODLog.printStackTrace(e, FinalClaimService.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
		}
		return travelTypes;
	}

	public FinalClaimReqSearchResponse getClaimSearchData(String personalNo, String groupId, String travelTypeId) {
		
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimService.class, "[getClaimSearchData]## Claim Requests Pno. : "
				+ personalNo + " ;group Id: " + groupId + " ;travel Type Id: " + travelTypeId);
		FinalClaimReqSearchResponse response = null;

		String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRequest/getTravelIdData";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("personalNo", personalNo).queryParam("groupId", groupId)
				.queryParam("travelTypeId", travelTypeId).build();
		try {

		ResponseEntity<FinalClaimReqSearchResponse> responseEntity = template.exchange(builder.toString(), HttpMethod.GET, null,
				FinalClaimReqSearchResponse.class);
		
		response =responseEntity.getBody();
		}catch(Exception e) {
			DODLog.printStackTrace(e, FinalClaimService.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
		}
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimService.class, "[getOfficeByUserId] ## response ## " + response);
		return response;
	}

	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimService.class, "[getOfficeByUserId] ## Get user with user id: " + userId);
		return officesService.getOfficeByUserId(userId);
	}

	public ClaimViewResponse getClaimDetails(String travelId) {
		
		
		
		ClaimViewResponse response = null;
try {
		String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRequest/getClaimDetailsbyTravelId";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("travelId", travelId).build();

		ResponseEntity<ClaimViewResponse> responseEntity = template.exchange(builder.toString(),
				HttpMethod.GET, null, ClaimViewResponse.class);
		response = responseEntity.getBody();
		if(response!=null &&  response.getResponse()!=null && response.getErrorCode() == 200 ) {
	
		TravelContent travContent=response.getResponse().getTravelContent();
		travContent.setDtsCoveyanceAdvAmt(Optional.ofNullable(travContent.getDtsCoveyanceAdvAmt()).orElse("0.0"));
		travContent.setDtsLuggageAdvAmt(Optional.ofNullable(travContent.getDtsLuggageAdvAmt()).orElse("0.0"));
		travContent.setDtsCTGAdvAmt(Optional.ofNullable(travContent.getDtsCTGAdvAmt()).orElse("0.0"));
		travContent.setAdvanceAmount(Optional.ofNullable(travContent.getAdvanceAmount()).orElse(0.0));
		
		response.getResponse().setTravelContent(travContent);
		
		if( response.getResponse().getRecoverydeatils()!=null) {
			Recoverydetails recoverydetails = response.getResponse().getRecoverydeatils();
			recoverydetails.setIrlaRecoveryAmnt(Optional.ofNullable(recoverydetails.getIrlaRecoveryAmnt()).orElse("0.0"));
			response.getResponse().setRecoverydeatils(recoverydetails);
		}else {
			Recoverydetails recoverydetails =new Recoverydetails();
			recoverydetails.setIrlaRecoveryAmnt("0.0");
			response.getResponse().setRecoverydeatils(recoverydetails);
		}
	
		
		
			
		
		}
}catch(Exception e) {
	DODLog.printStackTrace(e, FinalClaimService.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
}
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimService.class, "[getClaimDetails] ::: Response ::::" + response);
		return response;
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
		String notMatchCity="City Name Not Exist";
		if (null != city && !city.isBlank()) {
			try {
		String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRequest/getCityList";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("city", city).build();

		ResponseEntity<GetCityResponse> responseEntity = template.exchange(builder.toString(), HttpMethod.GET, null, GetCityResponse.class);
		GetCityResponse response = responseEntity.getBody();
		if (null != response && response.getErrorCode() == 200 && null != response.getResponseList() && !response.getResponseList() .isEmpty() ) {
			cityList.addAll(response.getResponseList());
			return cityList;
		}else {
			GetCityBean getCityBean=new GetCityBean();
			getCityBean.setCityName(notMatchCity);
			cityList.add(getCityBean);
		}
			}catch(Exception e) {
				DODLog.printStackTrace(e, FinalClaimService.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
			}
		}
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimService.class, "Get City List::::: " + cityList.size());
		return cityList;
	}

	public CounterPersonalInfoBean getCounterPersonalInfo(String userAlias) {
		
		
		CounterPersonalInfoBean counterPersonalInfo = new CounterPersonalInfoBean();
try {
		String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRequest/getCounterPersonalInfo";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("personalNumber", userAlias).build();

		ResponseEntity<CounterPersonalInfoResponse> responseEntity = template.exchange(builder.toString(), HttpMethod.GET, null, CounterPersonalInfoResponse.class);
		CounterPersonalInfoResponse response = responseEntity.getBody();
		if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
			counterPersonalInfo = response.getResponse();
		}
		if((counterPersonalInfo.getFlag()==null || counterPersonalInfo.getFlag().equals("null"))&&
				(counterPersonalInfo.getMessage()==null || counterPersonalInfo.getMessage().equals("null"))) {
			counterPersonalInfo.setMessage("Personal Number you have entered is not correct");
		}
}catch(Exception e) {
	DODLog.printStackTrace(e, FinalClaimService.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
}
		DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimService.class, "Get Counter Personal Info::::: " + userAlias + " ; " + counterPersonalInfo);
		return counterPersonalInfo;
	}

	public Map<Boolean, String> saveClaimData(HttpServletRequest request) {
		Map<Boolean, String> resultMap = new HashMap<>();
		String errorMessage = "Unable to save your claim request. Kindly contact DTS Helpline.";
		String travelTypeId = Optional.ofNullable(request.getParameter("travelTypeId")).orElse("");
		try {
			
			
			ClaimRequestBean saveTADAClaimDTO =	saveTADAClaimDTO(request,travelTypeId);


			  

			  if (saveTADAClaimDTO != null) {
				  SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
				  LoginUser loginUser = sessionVisitor.getLoginUser();

				 

				  saveTADAClaimDTO.setTravelTypeId(travelTypeId);
				  saveTADAClaimDTO.setLoginUserId(loginUser.getUserId());
				  saveTADAClaimDTO.setIrlaGrpID(Optional.ofNullable(request.getParameter("irlaGrpID")).orElse(""));
				  saveTADAClaimDTO.setPersonalNo(Optional.ofNullable(request.getParameter("personalNo")).orElse(""));
				  saveTADAClaimDTO.setUnitId(Optional.ofNullable(request.getParameter("unitId")).orElse(""));
				  DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimService.class, "[saveClaimData] ## ClaimRequestBean: " + saveTADAClaimDTO);
			      String result=setResultString(saveTADAClaimDTO,travelTypeId);

				  Optional<User> optionalUser = userServices.getUserByUserAlias(request.getParameter("personalNo"));
				  User user = new User();
				  if (optionalUser.isPresent()) {
					  user = optionalUser.get();
				  }
				  saveTADAClaimDTO.setUserId(user.getUserId());

				  DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimService.class, "result" + result);

				  if (result.equalsIgnoreCase("OK")) {
					  ClaimRequestResponse response = template.postForObject(PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRequest/SaveClaimRequest", saveTADAClaimDTO, ClaimRequestResponse.class) ;
					  DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimService.class, "Claim Request Save Response: " + response);
					  if (response != null && response.getErrorCode() == 200 && !response.getResponse().isBlank()) {
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
			DODLog.printStackTrace(e, FinalClaimService.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
			resultMap.clear();
			resultMap.put(false, errorMessage);
		}
		
		  return resultMap;
	}
  
	
	public ClaimRequestBean saveTADAClaimDTO(HttpServletRequest request, String travelTypeId) {
		
		ClaimRequestBean saveTADAClaimDTO=null;
		try {
		  if(travelTypeId.equals("100002")){
			  saveTADAClaimDTO = new ClaimRequestBean(request);
		  }else if(travelTypeId.equals("100005") || travelTypeId.equals("100006") || travelTypeId.equals("100007") || travelTypeId.equals("100008")){
			  saveTADAClaimDTO = new ClaimRequestBean();
			  saveTADAClaimDTO.setLTCClaimDetails(request);
		  }else if(travelTypeId.equals("100001")){
			  saveTADAClaimDTO = new ClaimRequestBean();
			  saveTADAClaimDTO.setPTClaimDetails(request);
		  }
		}catch (Exception e) {
			DODLog.printStackTrace(e, FinalClaimService.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
			
		}
		  return saveTADAClaimDTO;
	}
	
	public String setResultString(ClaimRequestBean saveTADAClaimDTO,String travelTypeId) {
		  
	    String result="";
	    try {
		  if(travelTypeId.equals("100002")){
			  result = TADAClaimValidation.validateClaimData(saveTADAClaimDTO);
		  }else if(travelTypeId.equals("100005") || travelTypeId.equals("100006") || travelTypeId.equals("100007") || travelTypeId.equals("100008")){
			  result = TADAClaimValidation.validateLTCClaimData(saveTADAClaimDTO);
		  }else if(travelTypeId.equals("100001")){
			  result = TADAClaimValidation.validatePTClaimData(saveTADAClaimDTO);
		  }
	    }catch (Exception e) {
				DODLog.printStackTrace(e, FinalClaimService.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
		  
			}
		  return result;
	}
	
	public Map<Boolean, String> saveClaimDataAsDraft(HttpServletRequest request) {
		Map<Boolean, String> resultMap = new HashMap<>();
		String errorMessage = "Unable to save your claim as draft. Kindly contact DTS Helpline.";
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

				DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimService.class, "[saveClaimDataAsDraft] ## Claim Draft Request Bean: " + saveTADAClaimDTO);

				if (saveTADAClaimDTO != null) {
					SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
					LoginUser loginUser = sessionVisitor.getLoginUser();

					

				  saveTADAClaimDTO.setTravelTypeId(travelTypeId);
				  saveTADAClaimDTO.setLoginUserId(loginUser.getUserId());
				  saveTADAClaimDTO.setIrlaGrpID(Optional.ofNullable(request.getParameter("irlaGrpID")).orElse(""));
				  saveTADAClaimDTO.setPersonalNo(Optional.ofNullable(request.getParameter("personalNo")).orElse(""));
				  saveTADAClaimDTO.setUnitId(Optional.ofNullable(request.getParameter("unitId")).orElse(""));

				  Optional<User> optionalUser = userServices.getUserByUserAlias(request.getParameter("personalNo"));
				  User user = new User();
				  if (optionalUser.isPresent()) {
					  user = optionalUser.get();
				  }
				  saveTADAClaimDTO.setUserId(user.getUserId());

				  ClaimRequestResponse response = template.postForObject(PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRequest/SaveClaimAsDraft", saveTADAClaimDTO, ClaimRequestResponse.class) ;
				  DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE, FinalClaimService.class, "Claim Draft Save Response: " + response);

				  if (response != null && response.getErrorCode() == 200 && !response.getResponse().isBlank()) {
					  resultMap.put(true, response.getResponse());
				  } else if (response != null && response.getErrorCode() != 200 && !response.getErrorMessage().isEmpty()) {
					  resultMap.put(false, response.getErrorMessage());
				  } else {
						resultMap.put(false, errorMessage);
				  }
				} else {
					resultMap.put(false, errorMessage);
				}

		} catch (Exception e) {
			DODLog.printStackTrace(e, FinalClaimService.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
			resultMap.clear();
			resultMap.put(false, errorMessage);
		}
	
		  return resultMap;
	}

	public ViewClaimRequestBean getViewDataForClaim(String tadaclaimId) {
		ViewClaimRequestBean tadaClaimSettledModel = new ViewClaimRequestBean();
         DODLog.info(LogConstant.CLAIM_REQUEST_LOG_FILE,FinalClaimService.class,"[getViewDataForClaim] ## TA-DA CLAIM ID ::::: "+tadaclaimId);
		try {
			String uri = PcdaConstant.FINAL_CLAIM_BASE_URL + "/claimRequest/ViewByClaimId";
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("tadaClaimId", tadaclaimId).build();

			ResponseEntity<ViewClaimRequestBeanResponse> responseEntity = template.exchange(builder.toString(), HttpMethod.GET, null, ViewClaimRequestBeanResponse.class);
			ViewClaimRequestBeanResponse tadaClaimSettledResponse = responseEntity.getBody();

			if (tadaClaimSettledResponse != null && tadaClaimSettledResponse.getErrorCode() == 200) {
				tadaClaimSettledModel = tadaClaimSettledResponse.getResponse();
				
				if(tadaClaimSettledModel.getClaimLeaveDtls()!=null) {
					Set<ViewClaimLeaveDtlsBean> sortedHashSet = tadaClaimSettledModel.getClaimLeaveDtls().stream()
					        .sorted(Comparator.comparing(ViewClaimLeaveDtlsBean::getLeaveDate))
					        .collect(Collectors.toCollection(LinkedHashSet::new));
					
					tadaClaimSettledModel.setClaimLeaveDtls(sortedHashSet);
				}
				if(tadaClaimSettledModel.getClaimHotelDtls()!=null) {
				
					Set<ViewClaimHotelDtlsBean> sortedHashSet = tadaClaimSettledModel.getClaimHotelDtls().stream()
					        .sorted(Comparator.comparing(ViewClaimHotelDtlsBean::getCheckInTime))
					        .collect(Collectors.toCollection(LinkedHashSet::new));
					
					tadaClaimSettledModel.setClaimHotelDtls(sortedHashSet);
				
				}
				
				if(tadaClaimSettledModel.getClaimCertifyView()!=null) {
					Set<ViewClaimCertifyDtlsBean> sortedCertifyDtls =	tadaClaimSettledModel.getClaimCertifyView().stream()
							.sorted(Comparator.comparing(ViewClaimCertifyDtlsBean :: getSeqNo))
							.collect(Collectors.toCollection(LinkedHashSet::new));
				
					tadaClaimSettledModel.setClaimCertifyView(sortedCertifyDtls);
				}
			}
			

		} catch (Exception e) {
			DODLog.printStackTrace(e, FinalClaimService.class, LogConstant.CLAIM_REQUEST_LOG_FILE);
		}
		
		return tadaClaimSettledModel;
	}

}
