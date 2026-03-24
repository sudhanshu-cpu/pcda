package com.pcda.sao.airdemand.airdemandrequest.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.sao.airdemand.airdemandrequest.model.CheckSAORecordCountResponse;
import com.pcda.sao.airdemand.airdemandrequest.model.DemandDownloadFormResponse;
import com.pcda.sao.airdemand.airdemandrequest.model.DemandDownloadPostModel;
import com.pcda.sao.airdemand.airdemandrequest.model.DemandDownloadPostResponseModel;
import com.pcda.sao.airdemand.airdemandrequest.model.DemandDwnReqModel;
import com.pcda.sao.airdemand.airdemandrequest.model.DemandDwnResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AirDemandRequestService {

	@Autowired
	private RestTemplate restTemplate;
	
	// Method to Get All Demand Requests
	public DemandDwnReqModel getDemandDownloadRequest(Boolean flag, String serviceId, String groupId) {
		DemandDwnReqModel demandDownloadRequestModel = null;
		DODLog.info(LogConstant.AIR_TRANSCATION_LOG, AirDemandRequestService.class,"[getDemandDownloadRequest] ## flag::" + flag + " | serviceId::"+serviceId+" | groupId"+groupId);

		try {
			HttpEntity<DemandDwnReqModel> getDemandDownload = new HttpEntity<>(demandDownloadRequestModel);
			ResponseEntity<DemandDwnResponse> response = restTemplate
					.exchange(
							PcdaConstant.AIR_SERVICE_DEMAND + "/getDemandDwnReqs?flag=" + flag + "&serviceId=" + serviceId
									+ "&groupId=" + groupId,
							HttpMethod.GET, getDemandDownload, DemandDwnResponse.class);
			DemandDwnResponse demandDownloadResponse = response.getBody();
			if (demandDownloadResponse != null 
					&& demandDownloadResponse.getResponse() != null) 
			{
				demandDownloadRequestModel = demandDownloadResponse.getResponse();
			}
			DODLog.info(LogConstant.AIR_TRANSCATION_LOG, AirDemandRequestService.class,"[getDemandDownloadRequest] ## Demand Model::" + demandDownloadRequestModel);

		} catch (Exception e) {
			DODLog.printStackTrace(e, AirDemandRequestService.class, LogConstant.AIR_TRANSCATION_LOG);
		}
		return demandDownloadRequestModel;

	}
	 


	
	
	// Method to Return SAO-Record Count
	 public long getSAORecordCount(DemandDownloadPostModel downloadPostModel  ) {	 
		 long recordCount = 0;
		 String url= null;
		 		 try {
		 			 url= PcdaConstant.AIR_SERVICE_DEMAND + "/checkSAORecordsCount?fromDate={fromDate}&toDate={toDate}&flag={flag}&serviceId={serviceId}&groupId={groupId}";	 
		 			 //HttpEntity<DemandDownloadRequestModel> getSQORecordCount = new HttpEntity<>(demandDwnReqModel);
		 			 		 			 
		 			Map<String, String> params = new HashMap<>();
					params.put("fromDate", downloadPostModel.getFromDate());
					params.put("toDate", downloadPostModel.getToDate());
					params.put("flag", downloadPostModel.getFlag().toString());
					params.put("serviceId", downloadPostModel.getServiceId());
					params.put("groupId", downloadPostModel.getGroupId()); 			 
			 ResponseEntity<CheckSAORecordCountResponse>  response = restTemplate
					 .exchange(
							url, HttpMethod.GET, null, CheckSAORecordCountResponse.class,params);	 
			 CheckSAORecordCountResponse saoRecordCountResponse = response.getBody();
			 if(saoRecordCountResponse != null) {
				    
				 recordCount =saoRecordCountResponse.getResponse();	 
			 }			 
			 
		 }catch (Exception e) {
				DODLog.printStackTrace(e, AirDemandRequestService.class, LogConstant.AIR_TRANSCATION_LOG);
		 }	 
		 		 DODLog.info(LogConstant.AIR_TRANSCATION_LOG, AirDemandRequestService.class," recordCount ::" +recordCount );
		return recordCount; 
	 }
	
    //Method To Download Demand Form
	public String downloadDemandForms(String reqId, HttpServletResponse response1) throws IOException {
		String path = "";
		FileInputStream inputStream =null;
		try {
			ResponseEntity<DemandDownloadFormResponse> response = restTemplate
					.exchange(
							PcdaConstant.AIR_SERVICE_DEMAND + "/dwnSAOFilePath?reqId=" + reqId,
							HttpMethod.GET, null, DemandDownloadFormResponse.class);
			DODLog.info(LogConstant.AIR_TRANSCATION_LOG, DemandDownloadFormResponse.class,"  Request Id:" + reqId);
			DemandDownloadFormResponse downloadFormResponseBody = response.getBody();
			if(downloadFormResponseBody != null && downloadFormResponseBody.getErrorCode() == 200 && downloadFormResponseBody.getResponse() != null) {
				path=downloadFormResponseBody.getResponse();
			}
			String fileName=path.substring(path.lastIndexOf("/")+1);
			 inputStream = new FileInputStream(new File(path));
			  ServletOutputStream out = response1.getOutputStream();
			  response1.reset();
			  if(fileName.endsWith(".xls") || fileName.endsWith(".XLS") || fileName.endsWith(".xlsx") || fileName.endsWith(".XLSX")){
			      response1.setContentType("application/octet-stream");
			  }else if(fileName.endsWith(".txt") || fileName.endsWith(".TXT")){
				  response1.setContentType("text/plain");
			  }else if(fileName.endsWith(".pdf") || fileName.endsWith(".PDF")){
				  response1.setContentType("application/pdf");
			  }else{
				  response1.setContentType("application/octet-stream"); 
			  }
			  response1.setHeader("Content-Disposition", "attachment;filename="+fileName);
			  
			  byte[] buffer = new byte[1024];
				int bytesRead = -1;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					out.write(buffer, 0, bytesRead);
				}
			  inputStream.close();
			  out.flush();
			  out.close();				
			
		}catch(Exception e) {
			DODLog.printStackTrace(e, AirDemandRequestService.class, LogConstant.AIR_TRANSCATION_LOG);
			if(inputStream!=null) {
			inputStream.close();
			}
		}
		DODLog.info(LogConstant.AIR_TRANSCATION_LOG, DemandDownloadFormResponse.class,"  path :" + path);
		return path;
	}
	
	//METHOD SAVE DEMAND DOWNLOAD REQUEST
	public DemandDownloadPostResponseModel createDemandDownload(DemandDownloadPostModel demandDwnPostModel) {
		DemandDownloadPostResponseModel demandDwnPostModelResponse = null;
		
		try {
			HttpEntity<DemandDownloadPostModel> createDemandDwnRequest = new HttpEntity<>(demandDwnPostModel);
			ResponseEntity<DemandDownloadPostResponseModel> response = restTemplate.exchange(
					PcdaConstant.AIR_SERVICE_DEMAND + "/saveDemandDwn", HttpMethod.POST, createDemandDwnRequest,
					DemandDownloadPostResponseModel.class);
		 demandDwnPostModelResponse = response.getBody();
		 

		} catch (Exception e) {
			DODLog.printStackTrace(e, AirDemandRequestService.class, LogConstant.AIR_TRANSCATION_LOG);
		}
		DODLog.info(LogConstant.AIR_TRANSCATION_LOG, DemandDownloadFormResponse.class,"  demandDwnPostModelResponse :" + demandDwnPostModelResponse);
		return demandDwnPostModelResponse ;		
	}
	
	
	
	 
}
