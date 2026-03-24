package com.pcda.mb.travel.journey.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.PAOMappingModel;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.PAOMappingServices;
import com.pcda.login.model.LoginUser;
import com.pcda.mb.adduser.changeservice.service.ChangeServiceService;
import com.pcda.mb.travel.journey.model.AirRequestData;
import com.pcda.mb.travel.journey.model.AirRequestQuestionData;
import com.pcda.mb.travel.journey.model.AirRequestResponse;
import com.pcda.mb.travel.journey.model.RailRequestData;
import com.pcda.mb.travel.journey.model.RailRequestResponse;
import com.pcda.mb.travel.journey.model.TaggedRequestModel;
import com.pcda.mb.travel.journey.model.TaggedRequestResponse;
import com.pcda.util.DODDATAConstants;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TaggedRequestService {
	
	@Autowired
	private RestTemplate template;

	@Autowired
	private PAOMappingServices mappingServices;

	@Autowired
	private OfficesService officesService;

	@Async("pcdaAsyncExecutor")
	public CompletableFuture<TaggedRequestModel> getTaggedRequests(HttpServletRequest request) {
		TaggedRequestModel taggedRequestModel=null; 
		try {
		 String userAlias=Optional.ofNullable(request.getParameter("personalNo")).orElse("");
		 String travelType=Optional.ofNullable(request.getParameter("travelType")).orElse("");
		 String trRuleId=Optional.ofNullable(request.getParameter("TRRule")).orElse("");
		
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,TaggedRequestService.class,"getTaggedRequests:Parameter-UserAlias="+userAlias+"||TravelType="+travelType+"||Tr Rule Id="+trRuleId);
		
			ResponseEntity<TaggedRequestResponse> response= template.getForEntity(PcdaConstant.REQUEST_BASE_URL+"/getTaggedRequests?personalNo={userAlias}&travelType={travelType}&trRule={trRuleId}", TaggedRequestResponse.class, userAlias, travelType, trRuleId);
			
			
			TaggedRequestResponse booleanResponse=  response.getBody();
			if(null!=booleanResponse && booleanResponse.getErrorCode()==200) {
				taggedRequestModel=booleanResponse.getResponse();
				DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,TaggedRequestService.class,"getTaggedRequests: Response"+taggedRequestModel.toString());
			}
		}catch(Exception e){
			DODLog.printStackTrace(e, TaggedRequestService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
		return CompletableFuture.completedFuture(taggedRequestModel);
	}

	
	public AirRequestData getAirTaggedRequestDetails(HttpServletRequest request) {
		AirRequestData airRequestData=null;
		try {
		String requestId=Optional.ofNullable(request.getParameter("requestViewId")).orElse("");
		
		
		List<AirRequestQuestionData> airRequestQuestionData = new ArrayList<>();
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TaggedRequestService.class, "getAirTaggedRequestDetails::requestId: " + requestId);

		if(!requestId.isBlank()) {
			ResponseEntity<AirRequestResponse> responseEntity=template.getForEntity(PcdaConstant.REQUEST_BASE_URL + "/getAirJourneyRequestDetails/"+requestId, AirRequestResponse.class);
			
			AirRequestResponse ruleResponse=responseEntity.getBody();
			if (null != ruleResponse && ruleResponse.getErrorCode() == 200 && null != ruleResponse.getResponse()) {
				airRequestData= ruleResponse.getResponse();
				airRequestQuestionData=airRequestData.getQuestionData();
				if(airRequestQuestionData!=null) {
				Collections.sort(airRequestQuestionData);
				}
				airRequestData.setQuestionData(airRequestQuestionData);
				
			}else {
				airRequestData=new AirRequestData();
				if(null!=ruleResponse) {
				  airRequestData.setMessage(ruleResponse.getErrorMessage());
				}else {
					airRequestData.setMessage("Unable to get request data");
				}
			}
		}else {
			airRequestData=new AirRequestData();
			airRequestData.setMessage("Unable to get request data");
		}
		
		
		}catch(Exception e){
			DODLog.printStackTrace(e, TaggedRequestService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		return airRequestData;
	}

	
	public RailRequestData getRailTaggedRequestDetails(HttpServletRequest request) {
		RailRequestData railRequestData=null;
		try {
		String requestId=Optional.ofNullable(request.getParameter("requestID")).orElse("");
		String requestSeqNo=Optional.ofNullable(request.getParameter("requestSeqNo")).orElse("");
		
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TaggedRequestService.class, "getAirTaggedRequestDetails::requestId: " + requestId+" | requestSeqNo:"+requestSeqNo);

		if(!requestId.isBlank() && !requestSeqNo.isBlank()) {
		
		ResponseEntity<RailRequestResponse> responseEntity=template.getForEntity(PcdaConstant.REQUEST_BASE_URL+"/getRailJourneyRequestDetails/"+requestId+"/"+requestSeqNo, RailRequestResponse.class);
		
		RailRequestResponse ruleResponse=responseEntity.getBody();
		
        
			if (null != ruleResponse && ruleResponse.getErrorCode() == 200 && null != ruleResponse.getResponse()) {
				railRequestData= ruleResponse.getResponse();
			}else {
				railRequestData=new RailRequestData();
				if(null!=ruleResponse) {
					railRequestData.setMessage(ruleResponse.getErrorMessage());
				}else {
					railRequestData.setMessage("Unable to get request data");
				}
			}
		}else {
			railRequestData=new RailRequestData();
			railRequestData.setMessage("Unable to get request data");
		}
		
		
		}catch(Exception e){
			DODLog.printStackTrace(e, TaggedRequestService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		return railRequestData;
	}


	/* AJAX Getting Pay Account Office On PT - Travel   */
	public Map<String, List<PAOMappingModel>> getPayAccountOffice(String serviceId, String categoryId, LoginUser loginUser) {
		
		Map<String, String> paoOfficeMap = getPaoOfficesMap();
		List<PAOMappingModel> railPAOOfficeList = new ArrayList<>();
		List<PAOMappingModel> airPAOOfficeList = new ArrayList<>();

		if (serviceId.equals(DODDATAConstants.NAVY_SEVICE_ID)
				&& loginUser.getServiceId().equals(DODDATAConstants.NAVY_SEVICE_ID)) {

			OfficeModel officeModel = getOfficeByUserId(loginUser.getUserId());
			if (officeModel.getPaoGroupId() != null) {
				OfficeModel paoOfficeModel = getOfficeByGroupId(officeModel.getPaoGroupId());
				PAOMappingModel mappingModel = new PAOMappingModel();

				mappingModel.setName(paoOfficeModel.getName());
				mappingModel.setAcuntoficeId(paoOfficeModel.getGroupId());

				railPAOOfficeList.add(mappingModel);
			}
			if (officeModel.getPaoAirGroupId() != null) {
				OfficeModel paoAirOfficeModel = getOfficeByGroupId(officeModel.getPaoGroupId());
				PAOMappingModel mappingModel = new PAOMappingModel();

				mappingModel.setName(paoAirOfficeModel.getName());
				mappingModel.setAcuntoficeId(paoAirOfficeModel.getGroupId());

				airPAOOfficeList.add(mappingModel);
			}

		} else {
		List<PAOMappingModel> paoModelList = mappingServices.getPaoMappingServiceWithApproval("1");
			
		paoModelList = paoModelList.stream()
					.filter(pao -> pao.getServiceId().equals(serviceId) && pao.getCategoryId().equals(categoryId) && pao.getStatus().ordinal() == 1)
				.toList();

		paoModelList.forEach(e -> e.setName(setGroupName(e.getAcuntoficeId(), paoOfficeMap)));

			airPAOOfficeList = paoModelList.stream().filter(ob -> ob.getTravelType().ordinal()==1).toList();
			railPAOOfficeList = paoModelList.stream().filter(ob -> ob.getTravelType().ordinal()==0).toList();
		}

		return Map.of("0", railPAOOfficeList, "1", airPAOOfficeList);

	}


	// Get Map of PAO Office Id and Name
	public Map<String, String> getPaoOfficesMap() {
		List<OfficeModel> paoOfficesList = officesService.getOffices("PAO", "-1");
		Map<String, String> paoOfficeMap = new HashMap<>();
		try {
			paoOfficesList.forEach(e -> paoOfficeMap.put(e.getGroupId(), e.getName()));
			DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class,
					"[getPaoOfficesMap] PAO OFFICE MAP ::" + paoOfficeMap);
			return paoOfficeMap;
		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangeServiceService.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
		}
		return paoOfficeMap;
	}


	private String setGroupName(String acuntoficeId, Map<String, String> paoMap) {
		if (paoMap.containsKey(acuntoficeId)) {
			return paoMap.get(acuntoficeId);
		} else {
			return "";
		}
	}

	// Get office by user id
	public OfficeModel getOfficeByUserId(BigInteger userId) {
		OfficeModel officeModel = new OfficeModel();
		try {
			Optional<OfficeModel> opt = officesService.getOfficeByUserId(userId);
			if (opt.isPresent()) {
				officeModel = opt.get();
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangeServiceService.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
		}
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class,
				"[getOfficeByUserId] OFFICE MODEL ::" + officeModel.toString());
		return officeModel;
	}

	// Get office by group id
	public OfficeModel getOfficeByGroupId(String groupId) {
		OfficeModel officeModel = new OfficeModel();
		try {
			Optional<OfficeModel> opt = officesService.getOfficeByGroupId(groupId);
			if (opt.isPresent()) {
				officeModel = opt.get();
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangeServiceService.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
		}
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class, officeModel.toString());
		return officeModel;
	}



}
