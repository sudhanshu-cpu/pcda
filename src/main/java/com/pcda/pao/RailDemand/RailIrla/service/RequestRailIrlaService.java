package com.pcda.pao.RailDemand.RailIrla.service;

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
import com.pcda.pao.RailDemand.RailIrla.model.IRLAAdjustmentModel;
import com.pcda.pao.RailDemand.RailIrla.model.IRLAMasterMissingAndAdjustmentResponse;
import com.pcda.pao.RailDemand.RailIrla.model.IRLAMasterMissingDataModel;
import com.pcda.pao.RailDemand.RailIrla.model.IRLAPostRequestData;
import com.pcda.pao.RailDemand.RailIrla.model.IRLAResponseData;
import com.pcda.pao.RailDemand.RailIrla.model.IrlaMMRequestModel;
import com.pcda.pao.RailDemand.RailIrla.model.IrlaMasterMissingAdjustmentModel;
import com.pcda.pao.RailDemand.RailIrla.model.ReqRailIRLAResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;


@Service
public class RequestRailIrlaService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public IRLAResponseData getReqRailIrla(IRLAPostRequestData irlaPostRequestData ){
		
		DODLog.info(LogConstant.COMMON_LOG, RequestRailIrlaService.class,
				"[getReqRailIrla] : irlaPostRequestData " + irlaPostRequestData);
		IRLAResponseData irlaResponseData = new IRLAResponseData();
		String url= null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON); 
			HttpEntity<IRLAPostRequestData> requestEntity = new HttpEntity<>(irlaPostRequestData, headers);
			  url=PcdaConstant.RAIL_REQ_IRLA_SERVICE + "/saveIRLARequest";
			ResponseEntity<ReqRailIRLAResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, new ParameterizedTypeReference<ReqRailIRLAResponse>() {
					});
			  ReqRailIRLAResponse irlaResponse = responseEntity.getBody();
			
			if (irlaResponse != null && irlaResponse.getErrorCode() == 200
					&& irlaResponse.getResponse() != null) {
				   irlaResponseData = irlaResponse.getResponse();
				   
			  }	
		} catch (Exception e) {
			DODLog.printStackTrace(e, RequestRailIrlaService.class, LogConstant.COMMON_LOG);
		}
		
		return irlaResponseData;
	}

	public Integer getReqRailIrlaCount(IRLAPostRequestData irlaPostRequestData) {

		DODLog.info(LogConstant.COMMON_LOG, RequestRailIrlaService.class,
				"[getReqRailIrlaCount] : irlaPostRequestData " + irlaPostRequestData);
		Integer count=0;
		String url= null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON); 
			HttpEntity<IRLAPostRequestData> requestEntity = new HttpEntity<>(irlaPostRequestData, headers);
			url=PcdaConstant.RAIL_REQ_IRLA_SERVICE + "/irlaRecordsCount";	
			 ResponseEntity<IntegerResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
					 IntegerResponse.class);
			
			IntegerResponse	irlaCountResponse = responseEntity.getBody();
			
			if (irlaCountResponse != null && irlaCountResponse.getErrorCode() == 200) {
			  count = irlaCountResponse.getResponse();
			 DODLog.info(LogConstant.COMMON_LOG, RequestRailIrlaService.class,
						"[getReqRailIrlaCount] : count result---->> " + count);
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, RequestRailIrlaService.class, LogConstant.COMMON_LOG);
		}
		
		return count;
		
	}

	public String getFilePathByDwnReqId(String dwnReqId) {
		DODLog.info(LogConstant.COMMON_LOG, RequestRailIrlaService.class,
				"[getFilePathByDwnReqId] : downloadReqId " + dwnReqId);
		String filePath="";
		String url=null;
		try {
			url=PcdaConstant.RAIL_REQ_IRLA_SERVICE + "/irlaFilePath/{dwnReqId}" ;
			ResponseEntity<StringResponse> responseEntity = restTemplate.exchange(
					url, HttpMethod.POST, null,
					new ParameterizedTypeReference<StringResponse>() {},dwnReqId);

			StringResponse response = responseEntity.getBody();
			if (null != response && response.getErrorCode() == 200) {
				filePath=response.getResponse();
				DODLog.info(LogConstant.COMMON_LOG, RequestRailIrlaService.class,
						"[getFilePathByDwnReqId] : filePath---->> " + filePath);
			}
				
		}catch(Exception e) {
			DODLog.printStackTrace(e, RequestRailIrlaService.class, LogConstant.COMMON_LOG);
		}
		return filePath;
	}

	public List<IRLAMasterMissingDataModel> getIrlaMasterMissingData(String dwnIrlaReqId) {
		List<IRLAMasterMissingDataModel> irlaMasterMissingList = new ArrayList<>();
		DODLog.info(LogConstant.COMMON_LOG, RequestRailIrlaService.class,
				"[getIrlaMasterMissingData] : dwnIrlaReqId " + dwnIrlaReqId);

		String url = null;
		try {
			url =  PcdaConstant.RAIL_REQ_IRLA_SERVICE+"/getIrlaMasterMissingData/{dwnIrlaReqId}";
			ResponseEntity<IRLAMasterMissingAndAdjustmentResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, null,
					new ParameterizedTypeReference<IRLAMasterMissingAndAdjustmentResponse>() {
					}, dwnIrlaReqId);

			IRLAMasterMissingAndAdjustmentResponse response = responseEntity.getBody();
			if (null != response && response.getErrorCode() == 200 && null!=response.getResponseList()) {
				irlaMasterMissingList = response.getResponseList();
				
			}
				DODLog.info(LogConstant.COMMON_LOG, RequestRailIrlaService.class,
						"[getIrlaMasterMissingData] : No. of data---->> " + irlaMasterMissingList.size());
		} catch (Exception e) {
			DODLog.printStackTrace(e, RequestRailIrlaService.class, LogConstant.COMMON_LOG);
		}

		return irlaMasterMissingList;
	}

	public String sentIrlaMasterMissing(List<String> dataForMM, BigInteger loginUserId) {
		IrlaMMRequestModel irlaMMRequestModel=new IrlaMMRequestModel();
		List<IrlaMasterMissingAdjustmentModel> irlaMasterMissingPostList = new ArrayList<>();
		dataForMM.forEach(data -> {
			IrlaMasterMissingAdjustmentModel bean = new IrlaMasterMissingAdjustmentModel();
			String[] reqMMData = data.split("##");
			bean.setPersonalNo(reqMMData[0]);
			bean.setComments(reqMMData[1]);
			irlaMasterMissingPostList.add(bean);

		});
		irlaMMRequestModel.setIrlaRequestDataList(irlaMasterMissingPostList);
		irlaMMRequestModel.setLoginUserId(loginUserId);
		irlaMMRequestModel.setDwnRequestId(dataForMM.stream().findFirst().map(s -> s.split("##"))
				.filter(arr -> arr.length > 1).map(arr -> arr[2]).orElse(""));
		String msg = "";
		try {
			DODLog.info(LogConstant.COMMON_LOG, RequestRailIrlaService.class,
					"[sentIrlaMasterMissing] : irlaMMRequestModel---->> " +irlaMMRequestModel);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<IrlaMMRequestModel> irlaMMPostEntity = new HttpEntity<>(irlaMMRequestModel, headers);
			String url = PcdaConstant.RAIL_REQ_IRLA_SERVICE+"/irlaSaveMasterMissing";
			ResponseEntity<StringResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					irlaMMPostEntity, new ParameterizedTypeReference<StringResponse>() {
					});
			StringResponse response = responseEntity.getBody();
			if (null != response && response.getErrorCode() == 200) {
				msg = response.getErrorMessage();
			} else {
				msg = "Unable to save Master Missing request, Please try after some time.";
			}
			DODLog.info(LogConstant.COMMON_LOG, RequestRailIrlaService.class,
					"[sentIrlaMasterMissing] : Errormessage---->> " + response.getErrorMessage());
		} catch (Exception e) {
			DODLog.printStackTrace(e, RequestRailIrlaService.class, LogConstant.COMMON_LOG);
			msg = "Unable to save Master Missing request, Please try after some time.";
		}
		return msg;

	}

	public String sentNoIrlaMasterMissing(String dwnIrlaReqIdMm, BigInteger loginUserId) {
		IrlaMMRequestModel irlaMMPostModel = new IrlaMMRequestModel();
		irlaMMPostModel.setDwnRequestId(dwnIrlaReqIdMm);
		irlaMMPostModel.setLoginUserId(loginUserId);
		String msg ="";
		DODLog.info(LogConstant.COMMON_LOG, RequestRailIrlaService.class,
				"[sentNoIrlaMasterMissing] : irlaMMPostModel---->> " + irlaMMPostModel);
		try {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<IrlaMMRequestModel> irlaMMPostEntity = new HttpEntity<>(irlaMMPostModel,headers);
	String	url =PcdaConstant.RAIL_REQ_IRLA_SERVICE + "/sentNoIrlaMasterMissingSave";
		ResponseEntity<StringResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
				irlaMMPostEntity, new ParameterizedTypeReference<StringResponse>() {
				});
		StringResponse response  = responseEntity.getBody();
		if(null != response && response.getErrorCode()==200) {
			msg = response.getErrorMessage();
		}else {
			msg = "Unable to save Master Missing request, Please try after some time.";
		}
		
	} catch (Exception e) {
		DODLog.printStackTrace(e, RequestRailIrlaService.class, LogConstant.COMMON_LOG);
		msg = "Unable to save Master Missing request, Please try after some time.";
	}
	return msg;	
	}

	public String railIrlaAdjustment(IRLAAdjustmentModel adjustmentModel) {
		String msg = "";
		try {
			DODLog.info(LogConstant.COMMON_LOG, RequestRailIrlaService.class,
					"[railIrlaAdjustment] : adjustmentModel---->> " + adjustmentModel);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<IRLAAdjustmentModel> irlaMMPostEntity = new HttpEntity<>(adjustmentModel, headers);
			String url = PcdaConstant.RAIL_REQ_IRLA_SERVICE +"/saveRequestForAdjustment";
			ResponseEntity<StringResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					irlaMMPostEntity, new ParameterizedTypeReference<StringResponse>() {
					});
			StringResponse response = responseEntity.getBody();
			if (null != response && response.getErrorCode() == 200) {
				msg = response.getErrorMessage();
			} else {
				msg = "Unable to save Adjustment request, Please try after some time.";
			}
			
		} catch (Exception e) {
			DODLog.printStackTrace(e, RequestRailIrlaService.class, LogConstant.COMMON_LOG);
			msg = "Unable to save Adjustment request, Please try after some time.";
	}
	return msg;
	}
}


