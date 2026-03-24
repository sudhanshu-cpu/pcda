package com.pcda.co.requestdashboard.approvetdr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.co.requestdashboard.approvetdr.model.GetAppDataFrmBkgIdResponse;
import com.pcda.co.requestdashboard.approvetdr.model.GetChildDataFrmGrpIdModel;
import com.pcda.co.requestdashboard.approvetdr.model.GetParentDataGrpIdModel;
import com.pcda.co.requestdashboard.approvetdr.model.GroupIdDataListRespose;
import com.pcda.co.requestdashboard.approvetdr.model.PostTdrAppDisAppModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;


@Service
public class TdrApproveService {
	
@Autowired
private RestTemplate restTemplate;
 

public GroupIdDataListRespose getDataListFrmGrpId(String groupId) {
	DODLog.info(LogConstant.TDR_LOG_FILE, TdrApproveService.class, "TDR APPROVAL GROUPI-ID:: "+ groupId);
	GroupIdDataListRespose dataListRespose = new GroupIdDataListRespose();
	try {
		String url = PcdaConstant.TDR_BASE_URL;
	dataListRespose= restTemplate.getForObject(url+"/getTDRDetailForApproval?groupId="+groupId, GroupIdDataListRespose.class);
	
	}catch(Exception e) {
		DODLog.printStackTrace(e, TdrApproveService.class, LogConstant.TDR_LOG_FILE);
	}
	DODLog.info(LogConstant.TDR_LOG_FILE, TdrApproveService.class, "TDR APPROVAL RESPONSE :: "+ dataListRespose);
	return dataListRespose;
}
	

public GetParentDataGrpIdModel getAppDataTktBkg(String bookingId) {
	GetParentDataGrpIdModel model = new GetParentDataGrpIdModel();
	try {
		DODLog.info(LogConstant.TDR_LOG_FILE, TdrApproveService.class, "######  BOOKING ID #######::"+bookingId);
		
		String url = PcdaConstant.TDR_BASE_URL+"/getBookingDtls?bookingId="+bookingId;
		GetAppDataFrmBkgIdResponse  response = restTemplate.getForObject(url, GetAppDataFrmBkgIdResponse.class);
		if(response !=null && response.getErrorCode()==200) {
			model=response.getResponse();
			DODLog.info(LogConstant.TDR_LOG_FILE, TdrApproveService.class, "#####RESPONSE MODEL FROM BKG ID  #####::"+model);
			
			List<GetChildDataFrmGrpIdModel> passengerList = new ArrayList<>();
			
			for(GetChildDataFrmGrpIdModel childModel : model.getPassengerList()) {
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
		DODLog.printStackTrace(e, TdrApproveService.class, LogConstant.TDR_LOG_FILE);
	}
	DODLog.info(LogConstant.TDR_LOG_FILE, TdrApproveService.class, "###### model #######::"+model);
	return model;
}

public GroupIdDataListRespose sendForApproval(PostTdrAppDisAppModel appDisAppModel) {
	GroupIdDataListRespose response = new GroupIdDataListRespose();
	DODLog.info(LogConstant.TDR_LOG_FILE, TdrApproveService.class, "###MODEL TDR APPROVAL ####### ::"+appDisAppModel);
	try {
		String url = PcdaConstant.TDR_BASE_URL+"/setTDRTravellerRequestToApprove";
		response=restTemplate.postForObject(url, appDisAppModel, GroupIdDataListRespose.class);
	}catch(Exception e) {
		DODLog.printStackTrace(e, TdrApproveService.class, LogConstant.TDR_LOG_FILE);
	}
	DODLog.info(LogConstant.TDR_LOG_FILE, TdrApproveService.class, "### response ####### ::"+response);
	return response;
}


public GroupIdDataListRespose sendForDisApproval(PostTdrAppDisAppModel appDisAppModel) {
	DODLog.info(LogConstant.TDR_LOG_FILE, TdrApproveService.class, "MODEL TDR DIS-APPROVAL ::"+appDisAppModel);
	GroupIdDataListRespose response = new GroupIdDataListRespose();
	try {
		String url = PcdaConstant.TDR_BASE_URL+"/setTravellerTDRRequestToDisapprove";
		response=restTemplate.postForObject(url, appDisAppModel, GroupIdDataListRespose.class);
	}catch(Exception e) {
		DODLog.printStackTrace(e, TdrApproveService.class, LogConstant.TDR_LOG_FILE);
	}
	DODLog.info(LogConstant.TDR_LOG_FILE, TdrApproveService.class, " response ::"+response);
	return response;
}
}
