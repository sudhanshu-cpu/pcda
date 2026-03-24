package com.pcda.mb.grievance.extraInformation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.grievance.complaintHistory.model.GrievancePostBean;
import com.pcda.mb.grievance.extraInformation.model.ExtraInfoModel;
import com.pcda.mb.grievance.extraInformation.model.ExtraInfoResponse;
import com.pcda.mb.grievance.extraInformation.model.GrievanceMainBean;
import com.pcda.mb.grievance.extraInformation.model.PostUpdateGrievanceModel;
import com.pcda.mb.travel.journey.model.IntegerResponse;
import com.pcda.mb.travel.journey.model.StringResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ExtraInfoService {
	
	@Autowired
	private RestTemplate restTemplate;

	public List<ExtraInfoModel> getAllInformation(String groupId) {
		List<ExtraInfoModel> allDataList = new ArrayList<>();
		try {		
		ResponseEntity<ExtraInfoResponse> responseEntity = restTemplate.exchange(PcdaConstant.COMMON_GREIVANCE_URL + "/getGrExInfoReq?groupId=" +groupId, HttpMethod.GET, null,
				new ParameterizedTypeReference<ExtraInfoResponse>() {
				});
		ExtraInfoResponse response = responseEntity.getBody();
		
		if(null != response && (response.getErrorCode()==200) && null!= response.getResponse()) {
			allDataList = response.getResponse().getGrievanceViewBean();
		}

		DODLog.info(LogConstant.COMMON_LOG, ExtraInfoService.class, "ALL DATA LIST::::" + allDataList.size());
		} catch (Exception e) {
			DODLog.printStackTrace(e, ExtraInfoService.class, LogConstant.COMMON_LOG);
		}
		return allDataList;
	}

	//Edit Redressal for Extra information
	
	public List<ExtraInfoModel> getEditComplaintRedressal(GrievancePostBean bean){
		DODLog.info(LogConstant.COMMON_LOG, ExtraInfoService.class, "getEditComplaintRedressal::::" +bean);	
		List<ExtraInfoModel> data=new ArrayList<>();
			try {
				ExtraInfoResponse response = restTemplate.postForObject(
						PcdaConstant.COMMON_GREIVANCE_URL + "/getSpecificGrievance",bean, ExtraInfoResponse.class);
				if(response==null) {
					response= new ExtraInfoResponse();
				}
				 GrievanceMainBean mainBean = response.getResponse();
				 data= mainBean.getGrievanceViewBean();

				DODLog.info(LogConstant.COMMON_LOG, ExtraInfoService.class, "Complaint Redressal Edit Model::" + data);
			} catch (Exception e) {
				DODLog.printStackTrace(e,  ExtraInfoService.class, LogConstant.COMMON_LOG);
			}
			return data;
		}

	public String updateGrievance(PostUpdateGrievanceModel updateGrievanvceBean) {
		DODLog.info(LogConstant.COMMON_LOG, ExtraInfoService.class, "getEditComplaintRedressal::::" +updateGrievanvceBean);	
		String updatedResponse="";
		try {
			
			HttpEntity<PostUpdateGrievanceModel> entity= new HttpEntity<>(updateGrievanvceBean);
              ResponseEntity<StringResponse> entityResponse = restTemplate.exchange(PcdaConstant.COMMON_GREIVANCE_URL + "/update",HttpMethod.PUT, entity,StringResponse.class);
              StringResponse response = entityResponse.getBody();
			if(response!=null && response.getErrorCode()==200) {
				updatedResponse=response.getErrorMessage();
			}
			else {
				updatedResponse="Unable to update!";
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e,  ExtraInfoService.class, LogConstant.COMMON_LOG);
		}
		DODLog.info(LogConstant.COMMON_LOG, ExtraInfoService.class, "updatedResponse::::" +updatedResponse);	
		return updatedResponse;
		
	}

	//for Notification
	 public int getAllCountNotify(String groupId) {
		 Integer count = 0;
	        try {
	            ResponseEntity<IntegerResponse> response = restTemplate.exchange(PcdaConstant.COMMON_GREIVANCE_URL + "/getNotificationCount?groupId="+groupId,HttpMethod.GET,null,IntegerResponse.class);
	            if (response.getBody() != null) {
	                IntegerResponse body = response.getBody();
	              return  body.getResponse();
	              
	            }
	        } catch (Exception e) {
	        	DODLog.printStackTrace(e,  ExtraInfoService.class, LogConstant.COMMON_LOG);
	        }
	        return count;
	    }
		
	}




