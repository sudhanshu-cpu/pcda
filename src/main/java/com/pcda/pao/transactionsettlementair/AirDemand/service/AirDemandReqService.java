package com.pcda.pao.transactionsettlementair.AirDemand.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.travel.journey.model.IntegerResponse;
import com.pcda.mb.travel.journey.model.StringResponse;
import com.pcda.pao.transactionsettlementair.AirDemand.model.AirDemandMasterMissingAdjustmentModel;
import com.pcda.pao.transactionsettlementair.AirDemand.model.AirDemandMasterMissingResponse;
import com.pcda.pao.transactionsettlementair.AirDemand.model.AirDemandResponseData;
import com.pcda.pao.transactionsettlementair.AirDemand.model.AirDmndPostRequestData;
import com.pcda.pao.transactionsettlementair.AirDemand.model.AirMMRequestModel;
import com.pcda.pao.transactionsettlementair.AirDemand.model.AirMasterMissingDataModel;
import com.pcda.pao.transactionsettlementair.AirDemand.model.ReqAirDmndResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class AirDemandReqService {

	@Autowired
	private RestTemplate restTemplate;

	public AirDemandResponseData getReqAirDmnd(AirDmndPostRequestData airPostRequestData) {

		DODLog.info(LogConstant.COMMON_LOG, AirDemandReqService.class,
				"[getReqAirDmnd] : airPostRequestData " + airPostRequestData);
		AirDemandResponseData airResponseData = new AirDemandResponseData();
		String url = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<AirDmndPostRequestData> requestEntity = new HttpEntity<>(airPostRequestData, headers);
			url = PcdaConstant.AIR_REQ_DEMAND_SERVICE + "/saveAirDemandRequest";
			ResponseEntity<ReqAirDmndResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, new ParameterizedTypeReference<ReqAirDmndResponse>() {
					});
			ReqAirDmndResponse airDmndResponse = responseEntity.getBody();

			if (airDmndResponse != null && airDmndResponse.getErrorCode() == 200
					&& airDmndResponse.getResponse() != null) {
				airResponseData = airDmndResponse.getResponse();

			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, AirDemandReqService.class, LogConstant.COMMON_LOG);
		}

		return airResponseData;
	}

	public Integer getReqAirDmndCount(AirDmndPostRequestData airPostRequestData) {
		DODLog.info(LogConstant.COMMON_LOG, AirDemandReqService.class,
				"[getReqAirDmndCount] : airPostRequestData " + airPostRequestData);
		Integer count=0;
		String url= null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON); 
			HttpEntity<AirDmndPostRequestData> requestEntity = new HttpEntity<>(airPostRequestData, headers);
			url=PcdaConstant.AIR_REQ_DEMAND_SERVICE + "/airDemandRecordsCount";	
			 ResponseEntity<IntegerResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
					 IntegerResponse.class);
			
			IntegerResponse	airCountResponse = responseEntity.getBody();
			
			if (airCountResponse != null && airCountResponse.getErrorCode() == 200) {
			  count = airCountResponse.getResponse();
			 
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, AirDemandReqService.class, LogConstant.COMMON_LOG);
		}
		
		return count;
		
	}

	public String getFilePathByDwnReqId(String dwnReqId) {
		DODLog.info(LogConstant.COMMON_LOG, AirDemandReqService.class,
				"[getFilePathByDwnReqId] : downloadReqId " + dwnReqId);
		String filePath="";
		String url=null;
		try {
			url=PcdaConstant.AIR_REQ_DEMAND_SERVICE + "/airDemandFilePath/{dwnReqId}" ;
			ResponseEntity<StringResponse> responseEntity = restTemplate.exchange(
					url, HttpMethod.POST, null,
					new ParameterizedTypeReference<StringResponse>() {},dwnReqId);

			StringResponse response = responseEntity.getBody();
			if (null != response && response.getErrorCode() == 200) {
				filePath=response.getResponse();
				DODLog.info(LogConstant.COMMON_LOG, AirDemandReqService.class,
						"[getFilePathByDwnReqId] : filePath---->> " + filePath);
			}
				
		}catch(Exception e) {
			DODLog.printStackTrace(e, AirDemandReqService.class, LogConstant.COMMON_LOG);
		}
		return filePath;
	}

	public List<AirMasterMissingDataModel> getAirMasterMissingData(String dwnAirReqId) {
		List<AirMasterMissingDataModel> airMasterMissingList = new ArrayList<>();
		DODLog.info(LogConstant.COMMON_LOG, AirDemandReqService.class,
				"[getAirMasterMissingData] : dwnAirReqId " + dwnAirReqId);

		String url = null;
		try {
			url = PcdaConstant.AIR_REQ_DEMAND_SERVICE + "/getAirDmndMasterMissingData/{dwnAirReqId}";
			ResponseEntity<AirDemandMasterMissingResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, null,
					new ParameterizedTypeReference<AirDemandMasterMissingResponse>() {
					}, dwnAirReqId);

			AirDemandMasterMissingResponse response = responseEntity.getBody();
			if (null != response && response.getErrorCode() == 200 && null!=response.getResponseList()) {
				airMasterMissingList = response.getResponseList();
				
			}
				DODLog.info(LogConstant.COMMON_LOG, AirDemandReqService.class,
						"[getAirMasterMissingData] : No. of data---->> " + airMasterMissingList.size());
		} catch (Exception e) {
			DODLog.printStackTrace(e, AirDemandReqService.class, LogConstant.COMMON_LOG);
		}

		return airMasterMissingList;
	}

	public String sentAirDmndMasterMissing(List<String> dataForMM, BigInteger loginUserId) {
		List<AirDemandMasterMissingAdjustmentModel> airMasterMissingPostList = new ArrayList<>();
		dataForMM.forEach(data -> {
			AirDemandMasterMissingAdjustmentModel bean = new AirDemandMasterMissingAdjustmentModel();
			String[] reqMMData = data.split("##");
			bean.setPersonalNo(reqMMData[0]);
			bean.setComments(reqMMData[1]);
			airMasterMissingPostList.add(bean);
		

		});
		AirMMRequestModel airMMRequestModel = new AirMMRequestModel();
		airMMRequestModel.setLoginUserId(loginUserId);
		airMMRequestModel.setDwnRequestId(dataForMM.stream().findFirst().map(s -> s.split("##"))
				.filter(arr -> arr.length > 1).map(arr -> arr[2]).orElse(""));
		airMMRequestModel.setAirRequestDataList(airMasterMissingPostList);
		String msg ="";
		DODLog.info(LogConstant.COMMON_LOG, AirDemandReqService.class,
				"[sentAirDmndMasterMissing] : airMMRequestModel---->> " + airMMRequestModel);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<AirMMRequestModel> airMMPostEntity = new HttpEntity<>(airMMRequestModel,
					headers);
			String url = PcdaConstant.AIR_REQ_DEMAND_SERVICE + "/saveAirMasterMissingData";
			ResponseEntity<StringResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					airMMPostEntity, new ParameterizedTypeReference<StringResponse>() {
					});
			StringResponse response = responseEntity.getBody();
			if(null!=response && response.getErrorCode()==200) {
				msg = response.getErrorMessage();
			}else {
				msg = "Unable to save Master Missing request, Please try after some time.";
			}
			DODLog.info(LogConstant.COMMON_LOG, AirDemandReqService.class,
					"[sentAirDmndMasterMissing] : Errormessage---->> " + response.getErrorMessage());
		} catch (Exception e) {
			DODLog.printStackTrace(e, AirDemandReqService.class, LogConstant.COMMON_LOG);
			msg = "Unable to save Master Missing request, Please try after some time.";
		}
		return msg;

	}

	public String sentNoAirDmndMasterMissing(String airDmndReqIdForMm, BigInteger loginUserId) {
		AirMMRequestModel airMMRequestModel = new AirMMRequestModel();
		airMMRequestModel.setDwnRequestId(airDmndReqIdForMm);
		airMMRequestModel.setLoginUserId(loginUserId);
		String msg ="";
		DODLog.info(LogConstant.COMMON_LOG, AirDemandReqService.class,
				"[sentNoAirDmndMasterMissing] : airMMRequestModel---->> " + airMMRequestModel);
		try {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AirMMRequestModel> airMMPostEntity = new HttpEntity<>(airMMRequestModel,headers);
	String	url = PcdaConstant.AIR_REQ_DEMAND_SERVICE +"/sentNoDataForAirMM";
		ResponseEntity<StringResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
				airMMPostEntity, new ParameterizedTypeReference<StringResponse>() {
				});
		StringResponse response  = responseEntity.getBody();
		if(null != response && response.getErrorCode()==200) {
			msg = response.getErrorMessage();
		}else {
			msg = "Unable to save Master Missing request, Please try after some time.";
		}
		DODLog.info(LogConstant.COMMON_LOG, AirDemandReqService.class,
				"[sentNoAirDmndMasterMissing] : Errormessage---->> " + response.getErrorMessage());
	} catch (Exception e) {
		DODLog.printStackTrace(e, AirDemandReqService.class, LogConstant.COMMON_LOG);
		msg = "Unable to save Master Missing request, Please try after some time.";
	}
	return msg;	
	}

	public String reqForDrDemandGenerate(String dwnRequestId, BigInteger loginUserId) {
		AirMMRequestModel airMMRequestModel = new AirMMRequestModel();
		airMMRequestModel.setDwnRequestId(dwnRequestId);
		airMMRequestModel.setLoginUserId(loginUserId);
		
         String msg ="";
         DODLog.info(LogConstant.COMMON_LOG, AirDemandReqService.class,
 				"[reqForDrDemandGenerate] : airMMRequestModel---->> " + airMMRequestModel);
		try {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AirMMRequestModel> airMMPostEntity = new HttpEntity<>(airMMRequestModel,headers);
	String	url = PcdaConstant.AIR_REQ_DEMAND_SERVICE +"/saveRequestForGenerateDRDemand";
		ResponseEntity<StringResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
				airMMPostEntity, new ParameterizedTypeReference<StringResponse>() {
				});
		StringResponse response  = responseEntity.getBody();
		if(null != response && response.getErrorCode()==200) {
			msg = response.getErrorMessage();
		}else {
			msg = "Unable to save Generate Demand request, Please try after some time.";
		}
		DODLog.info(LogConstant.COMMON_LOG, AirDemandReqService.class,
				"[reqForDrDemandGenerate] : Errormessage---->> " + response.getErrorMessage());
	} catch (Exception e) {
		DODLog.printStackTrace(e, AirDemandReqService.class, LogConstant.COMMON_LOG);
		msg = "Unable to save Generate Demand request, Please try after some time.";
	}
	return msg;	
	}
		
	}


	
	


