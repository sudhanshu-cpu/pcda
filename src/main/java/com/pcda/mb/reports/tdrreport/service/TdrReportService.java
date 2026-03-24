package com.pcda.mb.reports.tdrreport.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.TravelType;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.mb.reports.tdrreport.model.TdrBookingDetailsResponseModel;
import com.pcda.mb.reports.tdrreport.model.TdrBookingPopUpModel;
import com.pcda.mb.reports.tdrreport.model.TdrReportInputModel;
import com.pcda.mb.reports.tdrreport.model.TdrReportModel;
import com.pcda.mb.reports.tdrreport.model.TdrReportResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class TdrReportService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private TravelTypeServices travelTypeServices;

	@Autowired
	private OfficesService paoServices;

	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	public static final String DD_MM_YYYY = "dd-MM-yyyy";
	
	// Method To get TDR-Report List
	public List<TdrReportModel> getTdrReport(TdrReportInputModel tdrReportInputModel) {
		DODLog.info(LogConstant.TDR_REPORT, TdrReportService.class, "[getTdrReport] ## tdrReportInputModel:" + tdrReportInputModel);
		List<TdrReportModel> listTdr = new ArrayList<>();
		String url = null;
		try {
			if (tdrReportInputModel.getTdrStatus() != null && tdrReportInputModel.getTravelType() != null
					&& tdrReportInputModel.getAccountOfice() != null) {
				url = PcdaConstant.COMMON_REPORT_URL
						+ "/getTdrReport?tdrStatus={tdrStatus}&accountOfice={accountOffice}&travelType={travelType}";
			}

			Map<String, String> params = new HashMap<>();
			params.put("tdrStatus", tdrReportInputModel.getTdrStatus());
			params.put("accountOffice", tdrReportInputModel.getAccountOfice());
			params.put("travelType", tdrReportInputModel.getTravelType());

			if (tdrReportInputModel.getBookingId() != null && !tdrReportInputModel.getBookingId().isEmpty()) {
				url = url + "&bookingId={bookingId}";
				params.put("bookingId", tdrReportInputModel.getBookingId());
			}

			if (tdrReportInputModel.getRequestId() != null && !tdrReportInputModel.getRequestId().isEmpty()) {
				url = url + "&requestId={requestId}";
				params.put("requestId", tdrReportInputModel.getRequestId());
			}

			if (tdrReportInputModel.getPnrNo() != null && !tdrReportInputModel.getPnrNo().isEmpty()) {
				url = url + "&pnrNo={pnrNo}";
				params.put("pnrNo", tdrReportInputModel.getPnrNo());
			}

			if (tdrReportInputModel.getPersonalNo() != null && !tdrReportInputModel.getPersonalNo().isEmpty()) {
				url = url + "&personalNo={personalNo}";
				params.put("personalNo", tdrReportInputModel.getPersonalNo());
			}

			if (tdrReportInputModel.getFromDate() != null && !tdrReportInputModel.getFromDate().isEmpty()) {
				url = url + "&fromDat={fromDate}";
				params.put("fromDate", CommonUtil.getChangeFormat(tdrReportInputModel.getFromDate(), "dd-MM-yyyy", "yyyy-MM-dd"));
			}

			if (tdrReportInputModel.getToDate() != null && !tdrReportInputModel.getToDate().isEmpty()) {
				url = url + "&toDat={toDate}";
				params.put("toDate",CommonUtil.getChangeFormat(tdrReportInputModel.getToDate(), "dd-MM-yyyy", "yyyy-MM-dd"));
			}

			TdrReportResponse response = restTemplate.getForObject(url, TdrReportResponse.class, params);
			if(response!=null && response.getErrorCode()==200 && null!=response.getResponseList()) {
			listTdr = response.getResponseList();
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, TdrReportService.class, LogConstant.TDR_REPORT);
		}
		DODLog.info(LogConstant.TDR_REPORT, TdrReportService.class, "[getTdrReport] ## listTdr:" + listTdr.size());
		return listTdr;
	}

	// GET ALL TRAVEL TYPE
	public List<TravelType> getAllTravelType(Integer approvalType) {
		List<TravelType> travelTypeList = travelTypeServices.getAllTravelType(approvalType);
		
		return travelTypeList;
	}

	// Method that returns all PAO which is 1
	public List<OfficeModel> getAllPao() {
		List<OfficeModel> paoList = paoServices.getOffices("PAO", "1");
		
		return paoList;
	}

	// Method to consume AJAX API CALL
	public TdrBookingPopUpModel getTdrBookingDetails(String bookingId) {
		DODLog.info(LogConstant.TDR_REPORT, TdrReportService.class, "[getTdrBookingDetails] ## bookingId:" + bookingId);
		TdrBookingPopUpModel tdrBookingPopUpModel = null;
		String url = "";
		try {
			if (bookingId != null) {
				url = PcdaConstant.COMMON_REPORT_URL + "/getTdrBookingIdDetails?bookingId=" + bookingId;
			}
			TdrBookingDetailsResponseModel response = restTemplate.getForObject(url,
					TdrBookingDetailsResponseModel.class);
			if (response != null) {
				tdrBookingPopUpModel = response.getResponse();

				tdrBookingPopUpModel.setBookingFormattedDate(CommonUtil.getChangeFormat(
						tdrBookingPopUpModel.getBookingDate(), DATE_FORMAT, DD_MM_YYYY));
				tdrBookingPopUpModel.setBoardingFormattedDate(CommonUtil.getChangeFormat(
						tdrBookingPopUpModel.getBoardingDate(), DATE_FORMAT, DD_MM_YYYY));
				tdrBookingPopUpModel.setTdrFormattedDate(CommonUtil.getChangeFormat(tdrBookingPopUpModel.getTdrDate(),
						DATE_FORMAT, DD_MM_YYYY));

			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, TdrReportService.class, LogConstant.TDR_REPORT);
		}
		
		return tdrBookingPopUpModel;

	}

}
