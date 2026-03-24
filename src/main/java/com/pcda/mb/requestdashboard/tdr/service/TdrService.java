package com.pcda.mb.requestdashboard.tdr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.requestdashboard.tdr.model.GetParentTdrModel;
import com.pcda.mb.requestdashboard.tdr.model.GetTdrChildModel;
import com.pcda.mb.requestdashboard.tdr.model.GetTdrFinalProcessResponse;
import com.pcda.mb.requestdashboard.tdr.model.GetTdrPnrResponse;
import com.pcda.mb.requestdashboard.tdr.model.PostChildTdrModel;
import com.pcda.mb.requestdashboard.tdr.model.PostParenttdrModel;
import com.pcda.mb.requestdashboard.tdr.model.TDRReasonModel;
import com.pcda.mb.requestdashboard.tdr.model.TDRReasonResponse;
import com.pcda.mb.requestdashboard.tdr.model.TdrViewResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TdrService {

@Autowired
private RestTemplate restTemplate;
	
public List<GetParentTdrModel> getDataFrmPnr(String pnrNo,String groupId) {
	List<GetParentTdrModel> modelList = new ArrayList<>();
	try {
		String url = PcdaConstant.TDR_BASE_URL+"/getBookingDtlForTdr?pnrNo="+pnrNo+"&groupId="+groupId;
		GetTdrPnrResponse response = restTemplate.getForObject(url, GetTdrPnrResponse.class);
		if(response !=null && response.getErrorCode()==200 && null!=response.getResponseList()) {
			modelList=response.getResponseList();
			
			modelList.stream().forEach(obj->obj.setBoardingDateStr(CommonUtil.formatDate(obj.getBoardingDate(), "dd-MMM-yyyy")));
			
			
			
			
		}
		DODLog.info(LogConstant.TDR_LOG_FILE, TdrService.class, " ################ RESPONSE MODEL LIST #########"+modelList.size());
	}catch(Exception e) {
		DODLog.printStackTrace(e, TdrService.class, LogConstant.TDR_LOG_FILE);
		
	}
	return modelList;
}


public GetParentTdrModel getDataTktBkg(String bookingId) {
	GetParentTdrModel model = new GetParentTdrModel();
	try {
		DODLog.info(LogConstant.TDR_LOG_FILE, TdrService.class, "BOOKING ::"+bookingId);
		
		String url = PcdaConstant.TDR_BASE_URL+"/getBookingDtls?bookingId="+bookingId;
		GetTdrPnrResponse response = restTemplate.getForObject(url, GetTdrPnrResponse.class);
		if(response !=null && response.getErrorCode()==200) {
			model=response.getResponse();
			DODLog.info(LogConstant.TDR_LOG_FILE, TdrService.class, "RESPONSE MODEL FROM BKG ID  ::"+model);
			
			model.setBoardingDateStr(CommonUtil.formatDate(model.getBoardingDate(), "dd-MMM-yyyy"));
			model.setBookingDateStr(CommonUtil.formatDate(model.getBookingDate(), "dd-MMM-yyyy"));
			
			List<GetTdrChildModel> passengerList = new ArrayList<>();
			
			for(GetTdrChildModel childModel : model.getPassengerList()) {
				int pasSexNo=childModel.getPassangerSex();
				if(pasSexNo==1) {
					childModel.setPassSex("Female");
				}else if(pasSexNo==0) {
					childModel.setPassSex("Male");
				}
				passengerList.add(childModel);
			}
			model.setPassengerList(passengerList);
		}
	}catch(Exception e) {
		DODLog.printStackTrace(e, TdrService.class, LogConstant.TDR_LOG_FILE);
	}

	return model;
}

public TdrViewResponse  saveTdr(PostParenttdrModel parentTdrModel ,HttpServletRequest request) {
	
	TdrViewResponse response = new TdrViewResponse();
	List<PostChildTdrModel> childModel =  new ArrayList<>();
	int totalPass = parentTdrModel.getTotalNoOfPsnger();
	for(int i=1;i<=totalPass;i++) {
		if(null!=request.getParameter("chk_"+i)&& request.getParameter("chk_"+i).equalsIgnoreCase("on")){
			PostChildTdrModel model = new PostChildTdrModel();
			model.setPassangerNo(Integer.parseInt(request.getParameter("passangerNo_"+i)));
			model.setTdrReason(parentTdrModel.getTdrReason());
			model.setIsTdrFile(parentTdrModel.getIsTdrFile());
			childModel.add(model);
		}

	}
	parentTdrModel.setTdrFileList(childModel);
	DODLog.info(LogConstant.TDR_LOG_FILE, TdrService.class, "########## POST PARENT MODEL ##########"+parentTdrModel);
	try {
		String url = PcdaConstant.TDR_BASE_URL+"/updateForTdrFile";
		response = restTemplate.postForObject(url, parentTdrModel, TdrViewResponse.class);
	}catch(Exception e) {
		DODLog.printStackTrace(e, TdrService.class, LogConstant.TDR_LOG_FILE);
	}
	DODLog.info(LogConstant.TDR_LOG_FILE, TdrService.class, "#### R.D TDR RESPONSE ###### ::"+response);
	return response;
	
}

	public List<TDRReasonModel> getTDRReason(String agentId, String ticketNumber) {
		List<TDRReasonModel> tdrReasonList = new ArrayList<>();
		try {
			DODLog.info(LogConstant.TDR_LOG_FILE, TdrService.class, "TDR Reason  agentId : "+agentId +" | ticketNumber::"+ticketNumber);
			TDRReasonResponse response = restTemplate.postForObject(PcdaConstant.RAIL_BOOKING_URL +
					"/rail/v1/getTDRReason?agentId="+agentId+"&ticketNumber="+ticketNumber, null, TDRReasonResponse.class);
			if (response != null && response.getErrorCode() == 200 && null!=response.getResponseList() && !response.getResponseList().isEmpty()) {
				tdrReasonList = response.getResponseList();
			}
		}catch(Exception e) {
			DODLog.printStackTrace(e, TdrService.class, LogConstant.TDR_LOG_FILE);
		}
		DODLog.info(LogConstant.TDR_LOG_FILE, TdrService.class, "TDR Reason list :"+tdrReasonList.size());
		return tdrReasonList;
	}


	public GetTdrFinalProcessResponse sendTdrFinalProcess(String bookingId) {
		DODLog.info(LogConstant.TDR_LOG_FILE, TdrService.class, "########## FINAL TDR PROCESS BKG-ID   #######"+bookingId);
		GetTdrFinalProcessResponse response = new GetTdrFinalProcessResponse();
		try{
			String url= PcdaConstant.RAIL_BOOK_SERVICE +"/fileTdr/"+bookingId;
			response =restTemplate.postForObject(url, null, GetTdrFinalProcessResponse.class);
			DODLog.info(LogConstant.TDR_LOG_FILE, TdrService.class, "########## FINAL TDR PROCESS RESPONSE FROM  #######"+response);
		}catch(Exception e) {
			DODLog.printStackTrace(e, TdrService.class, LogConstant.TDR_LOG_FILE);
		}
		
		return response;
	}

}
