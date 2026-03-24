package com.pcda.mb.requestdashboard.viarequestlegrebooking.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.mb.requestdashboard.viarequestlegrebooking.model.ViaJourneyDetailBean;
import com.pcda.mb.requestdashboard.viarequestlegrebooking.model.ViaJourneyDetailBeanResponse;
import com.pcda.mb.requestdashboard.viarequestlegrebooking.model.ViaRequestLegReBookingModel;
import com.pcda.mb.requestdashboard.viarequestlegrebooking.model.ViaRequestLegReBookingResponse;
import com.pcda.mb.requestdashboard.viarequestlegrebooking.model.ViaRequestLegReBookingViewModel;
import com.pcda.mb.requestdashboard.viarequestlegrebooking.model.ViaRequestLegReBookingViewResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ViaRequestLegReBookingService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OfficesService officesService;

	public List<ViaRequestLegReBookingModel> getViaRequestLegReBookingData(String groupId, String personalNo) {

		List<ViaRequestLegReBookingModel> viaRequestLegReBookingList = new ArrayList<>();
		try {
			String url = PcdaConstant.VIA_REQUEST_BASE_URL + "/getViaRequestForLegRebooking?groupId=" + groupId
					+ "&personalNo=" + personalNo;
			ViaRequestLegReBookingResponse viaRequestLegReBookingResponse = restTemplate.getForObject(url,
					ViaRequestLegReBookingResponse.class);

			if (viaRequestLegReBookingResponse != null && viaRequestLegReBookingResponse.getErrorCode() == 200 && null!=viaRequestLegReBookingResponse.getResponseList()) {
				viaRequestLegReBookingList = viaRequestLegReBookingResponse.getResponseList();
			}
			viaRequestLegReBookingList
					.forEach(e -> e.setJurneyDateFormat(CommonUtil.formatDate(e.getJourneyDate(), "dd-MM-yyyy")));
			
			
			DODLog.info(LogConstant.VIA_REQUEST_LEG_BOOKING_LOG, ViaRequestLegReBookingService.class,
					"Via Request Leg Re Booking:::" + viaRequestLegReBookingList.size());

		} catch (Exception e) {
			DODLog.printStackTrace(e, ViaRequestLegReBookingService.class, LogConstant.VIA_REQUEST_LEG_BOOKING_LOG);
		}

		return viaRequestLegReBookingList;
	}

	public ViaRequestLegReBookingViewModel getViaRequestLegReBookingViewModel(String requestId) {

		ViaRequestLegReBookingViewModel viaRequestLegReBooking = null;
		DODLog.info(LogConstant.VIA_REQUEST_LEG_BOOKING_LOG, ViaRequestLegReBookingService.class,
				"Via Request Leg Re Booking View requestId:::" + requestId);
		try {
			String url = PcdaConstant.COMMON_REPORT_URL + "/getRailRequestReportBookingDetails?requestID=" + requestId;
			ViaRequestLegReBookingViewResponse viaRequestLegReBookingResponse = restTemplate.getForObject(url,
					ViaRequestLegReBookingViewResponse.class);

			if (viaRequestLegReBookingResponse != null && viaRequestLegReBookingResponse.getErrorCode() == 200) {

				viaRequestLegReBooking = viaRequestLegReBookingResponse.getResponse();
			}


			if(viaRequestLegReBooking!=null && !viaRequestLegReBooking.getJourneyDetailList().isEmpty()) {
				
				Collections.sort(viaRequestLegReBooking.getJourneyDetailList());
				
			}
			
			

		} catch (Exception e) {
			DODLog.printStackTrace(e, ViaRequestLegReBookingService.class, LogConstant.VIA_REQUEST_LEG_BOOKING_LOG);
		}

		return viaRequestLegReBooking;
	}

	public Optional<OfficeModel> getOfficeVia(BigInteger userId) {
		return officesService.getOfficeByUserId(userId);
	}

	
	
	public List<ViaJourneyDetailBean> sendViaRequestLegReBooking(String requestId) {

		List<ViaJourneyDetailBean>  viaJourneyDetailModels= new ArrayList<>();
		try {
			String url = PcdaConstant.VIA_REQUEST_BASE_URL + "/getDataAfterSave?requestId=" + requestId;
			ViaJourneyDetailBeanResponse viaRequestLegReBookingResponse = restTemplate.getForObject(url,
					ViaJourneyDetailBeanResponse.class);

			if (viaRequestLegReBookingResponse != null && viaRequestLegReBookingResponse.getErrorCode() == 200) {

				viaJourneyDetailModels = viaRequestLegReBookingResponse.getResponseList();
			}

			DODLog.info(LogConstant.VIA_REQUEST_LEG_BOOKING_LOG, ViaRequestLegReBookingService.class,
					"Via Request Leg Re Booking  Data:::" + viaJourneyDetailModels);

		} catch (Exception e) {
			DODLog.printStackTrace(e, ViaRequestLegReBookingService.class, LogConstant.VIA_REQUEST_LEG_BOOKING_LOG);
		}

		return viaJourneyDetailModels;
	}
	
}
