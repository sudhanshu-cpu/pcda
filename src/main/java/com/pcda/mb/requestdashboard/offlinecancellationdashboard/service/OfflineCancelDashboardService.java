package com.pcda.mb.requestdashboard.offlinecancellationdashboard.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.mb.requestdashboard.offlinecancellationdashboard.model.GetOfflineCanDataModel;
import com.pcda.mb.requestdashboard.offlinecancellationdashboard.model.GetOfflineCancelResponse;
import com.pcda.mb.requestdashboard.offlinecancellationdashboard.model.GetPerOfflineCancelViewModel;
import com.pcda.mb.requestdashboard.offlinecancellationdashboard.model.OfflineCanPerViewResponse;
import com.pcda.mb.requestdashboard.offlinecancellationdashboard.model.OfflineCancelDashboardResponse;
import com.pcda.mb.requestdashboard.offlinecancellationdashboard.model.OfflineRequestBean;
import com.pcda.mb.requestdashboard.offlinecancellationdashboard.model.PostsendOffCancelModel;
import com.pcda.mb.requestdashboard.viarequestlegrebooking.model.ViaRequestLegReBookingViewModel;
import com.pcda.mb.requestdashboard.viarequestlegrebooking.model.ViaRequestLegReBookingViewResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class OfflineCancelDashboardService {

	@Autowired
	private OfficesService officesService;

	@Autowired
	private RestTemplate restTemplate;

	public Optional<OfficeModel> getGroupIdByUserId(BigInteger userId) {

		return officesService.getOfficeByUserId(userId);
	}

	public List<GetOfflineCanDataModel> getDataUserList(String groupId) {
		 DODLog.info(LogConstant.OFFLINE_CANCELLATION_DASHBOARD_LOG_FILE, OfflineCancelDashboardService.class, "###### getDataUserList from groupId ::"+groupId);  
		List<GetOfflineCanDataModel> offList = new ArrayList<>();
		GetOfflineCancelResponse restResponse;
try {
		restResponse = restTemplate.getForObject(PcdaConstant.OFFLINE_CAN_BASE_URL+"/getUniquePersonalNo?groupId=" + groupId,
				GetOfflineCancelResponse.class);

		if (restResponse != null && restResponse.getErrorCode() == 200 && null!=restResponse.getResponse()) {

			List<String> 	personalNoList = restResponse.getResponse();
			 DODLog.info(LogConstant.OFFLINE_CANCELLATION_DASHBOARD_LOG_FILE, OfflineCancelDashboardService.class, "###### OFFLINE PERSONA NO LIST  ::  ##"+personalNoList.size());  
		if (!personalNoList.isEmpty()) {
			for (int i = 0; i < personalNoList.size(); i++) {
                offList.add(getOfflineRequestBean(personalNoList.get(i)));
			}
               DODLog.info(LogConstant.OFFLINE_CANCELLATION_DASHBOARD_LOG_FILE, OfflineCancelDashboardService.class, "########OFFLINE CANCEL DATALIST :: ## "+offList.size());  
		}
			}

   }catch(Exception e){
	DODLog.printStackTrace(e,  OfflineCancelDashboardService.class, LogConstant.OFFLINE_CANCELLATION_DASHBOARD_LOG_FILE);
		}

		return offList;
	}
	
// GET REQUESTED DATA FROM PESONAL NO.
	public GetOfflineCanDataModel getOfflineRequestBean(String personalNo) {
		GetOfflineCanDataModel offModel=new GetOfflineCanDataModel();
		List<OfflineRequestBean> offlineRequestlist = new ArrayList<>();
try {
		OfflineCancelDashboardResponse offlineCancelDashboardResponse=restTemplate.getForObject(PcdaConstant.OFFLINE_CAN_BASE_URL+"/geDatabyPersonalNo?personalNo="+personalNo, OfflineCancelDashboardResponse.class);
		
		if(offlineCancelDashboardResponse!=null && offlineCancelDashboardResponse.getErrorCode()==200) {
			
			offlineRequestlist=offlineCancelDashboardResponse.getResponseList();
			offlineRequestlist.stream().forEach(e->e.setDateFormateJurny(CommonUtil.formatDate(e.getJournyDate(), "dd-MM-yyyy")));
			offModel.setPersonalNo(offlineRequestlist.get(0).getPersonalNo());
			offModel.setOffBeanList(offlineRequestlist);
			
		}
		  
  }catch(Exception e){
	DODLog.printStackTrace(e,  OfflineCancelDashboardService.class, LogConstant.OFFLINE_CANCELLATION_DASHBOARD_LOG_FILE);
		}
		return offModel;
	}

	
	public ViaRequestLegReBookingViewModel getViewOfflineCanclation(String requestId) {
		DODLog.info(LogConstant.OFFLINE_CANCELLATION_DASHBOARD_LOG_FILE, OfflineCancelDashboardService.class," OFFLINE CANCELLATION  REQ-ID ::" + requestId);
		ViaRequestLegReBookingViewModel viaRequestLegReBooking = null;
		try {
			String url = PcdaConstant.COMMON_REPORT_URL + "/getRailRequestReportBookingDetails?requestID=" + requestId;
			ViaRequestLegReBookingViewResponse viaRequestLegReBookingResponse = restTemplate.getForObject(url,
					ViaRequestLegReBookingViewResponse.class);

			if (viaRequestLegReBookingResponse != null && viaRequestLegReBookingResponse.getErrorCode() == 200) {

				viaRequestLegReBooking = viaRequestLegReBookingResponse.getResponse();
			}

			

		} catch (Exception e) {
			DODLog.printStackTrace(e, OfflineCancelDashboardService.class, LogConstant.OFFLINE_CANCELLATION_DASHBOARD_LOG_FILE);
		}

		return viaRequestLegReBooking;
	}

	public Optional<OfficeModel> getOfficeVia(BigInteger userId) {
		return officesService.getOfficeByUserId(userId);
	}
	
	//personal No view 
	public GetPerOfflineCancelViewModel getViewOfflineCanclationPersonal(String personalNo, String groupId) {
		DODLog.info(LogConstant.OFFLINE_CANCELLATION_DASHBOARD_LOG_FILE, OfflineCancelDashboardService.class, "ViewOfflineCanclationPersonal"
				+ " personalNo::"+personalNo+" | groupId::"+groupId);
		GetPerOfflineCancelViewModel mmprofileModel = null;
		try {
			OfflineCanPerViewResponse response = restTemplate.getForObject(
					 PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/getProfileViewData?personalNo=" + personalNo
							+ "&officeId=" + groupId,
							OfflineCanPerViewResponse.class);

			if (response == null) {
				response = new OfflineCanPerViewResponse();
			}
			mmprofileModel = response.getResponse();
			
			mmprofileModel.setUserAlias(mmprofileModel.getSignInID());

			
		} catch (Exception e) {
			DODLog.printStackTrace(e, OfflineCancelDashboardService.class, LogConstant.OFFLINE_CANCELLATION_DASHBOARD_LOG_FILE);
		}
		

		return mmprofileModel;
	}

	public GetOfflineCancelResponse sendReqForCancel(PostsendOffCancelModel postCancelModel) {
		 DODLog.info(LogConstant.OFFLINE_CANCELLATION_DASHBOARD_LOG_FILE, OfflineCancelDashboardService.class,
				 "SEND POST OFFLINE CANCEL MODEL  ::"+postCancelModel); 
		
		
		 
		 GetOfflineCancelResponse response = new GetOfflineCancelResponse();
		 try {
			String url =PcdaConstant.REQUEST_BASE_URL+"/cancelApprovedReq";
			
			response= restTemplate.postForObject(url, postCancelModel, GetOfflineCancelResponse.class);
		}catch (Exception e) {
			DODLog.printStackTrace(e, OfflineCancelDashboardService.class, LogConstant.OFFLINE_CANCELLATION_DASHBOARD_LOG_FILE);
		}
		 DODLog.info(LogConstant.OFFLINE_CANCELLATION_DASHBOARD_LOG_FILE, OfflineCancelDashboardService.class,
				 "SEND FOR OFFLINE CANCEL RESPONSE::"+response); 
		return response;
	}
	
	
}
