package com.pcda.mb.reports.airtravelreport.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.TravelType;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.mb.reports.airticketcancellation.model.TicketPassengerModel;
import com.pcda.mb.reports.airtravelreport.model.AirTicketDlsResponse;
import com.pcda.mb.reports.airtravelreport.model.AirTravelBookingResponse;
import com.pcda.mb.reports.airtravelreport.model.AirTravelModel;
import com.pcda.mb.reports.airtravelreport.model.AirTravelReportInputModel;
import com.pcda.mb.reports.airtravelreport.model.AirTravelResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class AirTravelReportService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private TravelTypeServices travelTypeServices;

	@Autowired
	private OfficesService officeServices;

	// Method to get the airBooking ReportList using Rest API
	public List<AirTravelModel> getAirTravelReport(AirTravelReportInputModel airTravelReportInputModel) {
		DODLog.info(LogConstant.AIR_REPORT, AirTravelReportService.class,"[getAirTravelReport] ## airTravelReportInputModel ## " + airTravelReportInputModel);
		
		List<AirTravelModel> airTravelModel = new ArrayList<>();
		
		try {
			String	url = PcdaConstant.COMMON_REPORT_URL + "/airTravelReport";
			AirTravelResponse response = restTemplate.postForObject(url, airTravelReportInputModel,
					AirTravelResponse.class);

			if (response != null && response.getErrorCode() == 200 && null!=response.getResponseList()) {
				airTravelModel = response.getResponseList();
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, AirTravelReportService.class, LogConstant.AIR_REPORT);
		}
		
		DODLog.info(LogConstant.AIR_REPORT, AirTravelReportService.class,"[getAirTravelReport] ## response  airTravelModel ## " + airTravelModel.size());
		return airTravelModel;
	}

	// Get Travel Type
	public List<TravelType> getAllTravelType(Integer approvalType) {
		List<TravelType> travelTypeList = travelTypeServices.getAllTravelType(approvalType);
		
	
		return travelTypeList;
	}

	// Get GroupId
	public Optional<OfficeModel> getOfficesByGroupId(BigInteger userId) {
		return officeServices.getOfficeByUserId(userId);
	}

	// Get all unit
	public List<OfficeModel> getAllUnit() {
		List<OfficeModel> unitList = officeServices.getOffices("UNIT", "1");
		
		return unitList;
	}

	// POP UP DATA LIST
	public AirTravelBookingResponse getAirTicketBookingDtls(String bookingId) {
		
		DODLog.info(LogConstant.AIR_REPORT, AirTravelReportService.class,"[getAirTicketBookingDtls] ## bookingId ## " + bookingId);
		AirTravelBookingResponse airTicketBookingDls = null;
	
		try {
			if (bookingId != null && !bookingId.isEmpty()) {
				String	url = PcdaConstant.COMMON_REPORT_URL + "/getAirTicketBookingDetails?bookingId=" + bookingId;
			AirTicketDlsResponse response = restTemplate.getForObject(url, AirTicketDlsResponse.class);
			if (response != null && response.getErrorCode()==200) {
				airTicketBookingDls = response.getResponse();
				List<TicketPassengerModel> passangerDetails= airTicketBookingDls.getTicketPassangerDetails();
				if(passangerDetails!=null && !passangerDetails.isEmpty()) {
				Collections.sort(passangerDetails);
				airTicketBookingDls.setTicketPassangerDetails(passangerDetails);
			}
			}
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, AirTravelReportService.class, LogConstant.AIR_REPORT);
		}
		
		return airTicketBookingDls;
	}

}
