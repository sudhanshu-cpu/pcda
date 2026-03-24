package com.pcda.pao.reports.userverification.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.opencsv.CSVReader;
import com.pcda.mb.travel.journey.model.StringResponse;
import com.pcda.pao.reports.userverification.model.MissDesVirifiedResponse;
import com.pcda.pao.reports.userverification.model.PaoReportBean;
import com.pcda.pao.reports.userverification.model.PaoReportResponse;
import com.pcda.pao.reports.userverification.model.PersonalNoVeryfyBean;
import com.pcda.pao.reports.userverification.model.PostParentModel;
import com.pcda.pao.reports.userverification.model.PostVerifyMissDeModel;
import com.pcda.pao.reports.userverification.model.UserVarificationFormDataModel;
import com.pcda.pao.reports.userverification.model.UserVarificationHistoryResponse;
import com.pcda.pao.reports.userverification.model.UserVerificationHistoryModel;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserVerificationService {

	@Autowired
	private RestTemplate restTemplate;
	
	public List<PaoReportBean> getUserVarificationReport(UserVarificationFormDataModel userVarificationFormDataModel) {
		DODLog.info(LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE, UserVerificationService.class," userVarificationFormDataModel "+userVarificationFormDataModel);
		List<PaoReportBean> paoReportBeans=new ArrayList<>();
		try {
		 String url = PcdaConstant.COMMON_REPORT_URL+
					"/getPAOUserVerification";
		 
		 UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		 builder.queryParam("accountOffice", userVarificationFormDataModel.getAccountOffice());
		 if(!userVarificationFormDataModel.getPersonal().equals("")) {
			 builder.queryParam("personalNo", userVarificationFormDataModel.getPersonal());
		 }
		 else if(!userVarificationFormDataModel.getFrmBookingDt().equals("") && !userVarificationFormDataModel.getToBookingDt().equals("")) {
			 builder.queryParam("toDate", CommonUtil.getChangeFormat(userVarificationFormDataModel.getToBookingDt(), "dd-MM-yyyy", "yyyy-MM-dd"));
			 builder.queryParam("frmDate",CommonUtil.getChangeFormat(userVarificationFormDataModel.getFrmBookingDt(), "dd-MM-yyyy", "yyyy-MM-dd"));
			 
		 }
		 PaoReportResponse response=restTemplate.getForObject(builder.build().toString() , PaoReportResponse.class);
	
			if(response != null && response.getErrorCode()== 200) {
				paoReportBeans=response.getResponse();
			}
			paoReportBeans.stream().forEach(e->e.setCreationDateFormate(CommonUtil.formatDate(e.getCreationDate(), "dd-MM-yyyy")));
		DODLog.info(LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE, UserVerificationService.class,"PAO_USER_VERIFICATION_REPORT_DATA=="+paoReportBeans);
		}catch (Exception e) {
			DODLog.printStackTrace(e, UserVerificationService.class, LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE);
		}
			
		return paoReportBeans;
	}
	
	public MissDesVirifiedResponse updateMissVerified(PostParentModel model, HttpServletRequest request) {
		MissDesVirifiedResponse response = null;
	   List<PostVerifyMissDeModel> modelList = new ArrayList<>();
	   try {
		int check= model.getChekArr();
	    for(int i=0; i<check; i++) {
	    	String chcekBox = request.getParameter("usr_checkbox"+i);
	    	String userIdString=request.getParameter("userId_"+i);
	    	BigInteger userId = new BigInteger(userIdString);
	    	 String remark=request.getParameter("comment_"+i);
	    	 String profileStatus=request.getParameter("profileStatus_"+i);
	    	 int status=Integer.parseInt(profileStatus);
	    	if(chcekBox != null && chcekBox.equalsIgnoreCase("on")) {
	    		PostVerifyMissDeModel childModel = new PostVerifyMissDeModel();
	    		childModel.setUserId(userId);
	    		childModel.setComment(remark);
	    		childModel.setOldPaoVerificationStatus(status);
	    		childModel.setUserAction(model.getUserAction());
	    		childModel.setLoginUser(model.getLoginUser());
	    		modelList.add(childModel);
	    	}
	    }
		DODLog.info(LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE, UserVerificationService.class,"UPDATE_VERIFIED_MODEL=="+modelList);
	   
	    response = restTemplate.postForObject(PcdaConstant.PAO_USER_REPORT_BASE_URL + "/updateUserPAOVerificationStatus", modelList,
				MissDesVirifiedResponse.class);
		
		} catch (Exception e) {
			DODLog.printStackTrace(e, UserVerificationService.class, LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE);
		}
	   DODLog.info(LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE, UserVerificationService.class,"UPDATE_VERIFIED_RESPONSE=="+response);
		return response;
	}
	
	
	// Get Master Missing Commnent History
		public List<UserVerificationHistoryModel> getUserVerificationHistory(String personalNo) {
			
			DODLog.info(LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE, UserVerificationService.class,
					"[getUserVerificationHistory] personalNo " +personalNo);

			List<UserVerificationHistoryModel> projectList = new ArrayList<>();
			try {
				ResponseEntity<UserVarificationHistoryResponse> responseEntity = restTemplate.exchange(
						PcdaConstant.MASTER_MISED_DASHBOARD_URL + "/getUserMasterMissHistory/" + personalNo, HttpMethod.GET,
						null, new ParameterizedTypeReference<UserVarificationHistoryResponse>() {
						});
				UserVarificationHistoryResponse response = responseEntity.getBody();

				if (null != response && response.getErrorCode() == 200 && null != response.getResponseList()) {
					projectList = response.getResponseList();

					projectList.stream().forEach(obj->obj.setComunctionDateStr(CommonUtil.formatDate(obj.getComunctionDate(), "dd-MMM-yyyy")));
					
				}		
			} catch (Exception e) {
				DODLog.printStackTrace(e, UserVerificationService.class, LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE);
			}
			DODLog.info(LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE, UserVerificationService.class,
					" master miss comment history" + projectList);
			return projectList;
		}
	
	public void writePAOUserReportCSVFile(ServletOutputStream out,List<PaoReportBean> userSessionList)  {
		
			for(PaoReportBean e : userSessionList) {
				StringBuilder buffer = new StringBuilder(); 	
					buffer.append(e.getPersonalNo());
					buffer.append(",");
					buffer.append(CommonUtil.formatDate(e.getCreationDate(), "dd-MM-yyyy"));
					buffer.append(",");
					buffer.append(e.getFullName());
					buffer.append(",");
					buffer.append(e.getUnitName());
					buffer.append(",");
					buffer.append(e.getServiceName());
					buffer.append(",");
					buffer.append(e.getLevelName());
					buffer.append(",");
					buffer.append(e.getCategoryName());
					buffer.append(",");
					buffer.append(e.getProfileStatusStr());
					buffer.append(",");
					buffer.append(0);
					buffer.append("\n");
						try {
							out.write(buffer.toString().getBytes());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				}
}
	
	public void addPAOUserReportCSVHeader(ServletOutputStream out) throws IOException {
		StringBuilder buffer=new StringBuilder();
		buffer.append("Personal No.");
		buffer.append(",");
		buffer.append("Creation Date");
		buffer.append(",");
		buffer.append("Full Name");
		buffer.append(",");
		buffer.append("Unit Name");
		buffer.append(",");
		buffer.append("Service");
		buffer.append(",");
		buffer.append("Level Name");
		buffer.append(",");
		buffer.append("Category");
		buffer.append(",");
		buffer.append("Status");
		buffer.append(",");
		buffer.append("Verify Status");
		buffer.append("\n");
		out.write(buffer.toString().getBytes());
		
		
	} 
	
	public List<List<String>> getBookingData(InputStream inputStream) {
		CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
		List<List<String>> dataList = new LinkedList<>();

		try {
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				List<String> bookingDataList = new LinkedList<>();
				for (String token : nextLine) {
					bookingDataList.addAll(Arrays.asList(token));
				}
				DODLog.info(LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE, UserVerificationService.class, " PAO_USER_VERIFICATION_REPORT_LOG_FILE " + bookingDataList);
				dataList.add(bookingDataList);
			}
			reader.close();
			
		}catch (Exception e) {
			DODLog.printStackTrace(e, UserVerificationService.class, LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE);
			
				}
		DODLog.info(LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE, UserVerificationService.class, " dataList size " + dataList.size());
 return dataList;
	}

	public String verfyUplaodPersonalNoData(String[] personalNoList) {
		String msg = "";
		PersonalNoVeryfyBean personalNoVeryfyBean = new PersonalNoVeryfyBean();
		personalNoVeryfyBean.setPersonalNo(Arrays.asList(personalNoList));
        String url=PcdaConstant.MASTER_MISED_DASHBOARD_URL + "/veryfyUplaodedPersonalData";
        
		ResponseEntity<StringResponse> responseEntity = restTemplate.postForEntity(
				url, personalNoVeryfyBean,
				StringResponse.class);
		StringResponse response = responseEntity.getBody();
		DODLog.info(LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE, UserVerificationService.class, " Response :: " + response);
		if (response != null && response.getErrorCode() == 200 && response.getErrorMessage() != null) {
			msg = response.getErrorMessage();
		}
		return msg;

	}
	
}
