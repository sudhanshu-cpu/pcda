package com.pcda.pao.reports.railbookingreport.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.pao.reports.railbookingreport.model.GetBkgIdPopParentModel;
import com.pcda.pao.reports.railbookingreport.model.GetBkgIdPopResponse;
import com.pcda.pao.reports.railbookingreport.model.GetBkgReportDtslResponse;
import com.pcda.pao.reports.railbookingreport.model.GetRailCancelParentModel;
import com.pcda.pao.reports.railbookingreport.model.GetRailCancelResponse;
import com.pcda.pao.reports.railbookingreport.model.PostRailBkgRepoFormModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class RailBookingReportService {
	@Autowired
	private RestTemplate restTemplate;

	



	// Get Rail travel Report

	public GetBkgReportDtslResponse getRailBkgRepoModel(PostRailBkgRepoFormModel formModel) {

		GetBkgReportDtslResponse response = new GetBkgReportDtslResponse();

		DODLog.info(LogConstant.COMMON_REPORT, RailBookingReportService.class, "RAIL BOOKING REPORT FORM MODEL ::"+formModel);

		
		
		try {
			
			String	url = PcdaConstant.COMMON_REPORT_URL + "/railTravelReport";
			 response = restTemplate.postForObject(url, formModel,
					 GetBkgReportDtslResponse.class);
			 DODLog.info(LogConstant.COMMON_REPORT, RailBookingReportService.class, "RAIL BOOKING REPORT RESPONSE ::"+response);

		} catch (Exception e) {
			
			DODLog.printStackTrace(e, RailBookingReportService.class, LogConstant.COMMON_REPORT);
		}

		return response;

	}

	//AJAx for popup view details
	public GetBkgIdPopParentModel getRailBkgDtls(String bookingId) {
		GetBkgIdPopParentModel bookingdModel = new GetBkgIdPopParentModel();

			try {
			String url=PcdaConstant.COMMON_REPORT_URL + "/getRailTicketBookingDetails?bookingId=" + bookingId;
			

			GetBkgIdPopResponse response = restTemplate.getForObject(url,GetBkgIdPopResponse.class);

			if (response != null && response.getErrorCode() == 200) {
				bookingdModel = response.getResponse();
				DODLog.info(LogConstant.COMMON_REPORT, RailBookingReportService.class, "RAIL BOOKING REPORT PARENT ::" + bookingdModel);
			}

	 } catch (Exception e) {
			DODLog.printStackTrace(e, RailBookingReportService.class, LogConstant.COMMON_REPORT);
		}
	

		return bookingdModel;
	}

	// Get Rail tickets Cancellation Details
	public GetRailCancelParentModel getRailTktCanDtls(String bookingId) {
		GetRailCancelParentModel cancellationDtls = new  GetRailCancelParentModel();
		
		DODLog.info(LogConstant.COMMON_REPORT, RailBookingReportService.class, "RAIL BOOKING CAN BOOKING ID ::"+bookingId);

		String url = PcdaConstant.COMMON_REPORT_URL+ "/getRailTicketCancellationViewDetail?bookingId=" + bookingId;

		try {
			
			GetRailCancelResponse response = restTemplate.getForObject(url ,GetRailCancelResponse.class);

			if (response != null && response.getErrorCode() == 200) {
				cancellationDtls = response.getResponse();
				DODLog.info(LogConstant.COMMON_REPORT, RailBookingReportService.class, "RAIL BOOKING CANCEL MODEL::"+cancellationDtls);
			}
		

		} catch (Exception e) {
			DODLog.printStackTrace(e, RailBookingReportService.class, LogConstant.COMMON_REPORT);
		}
		
		return cancellationDtls;
	}

}
