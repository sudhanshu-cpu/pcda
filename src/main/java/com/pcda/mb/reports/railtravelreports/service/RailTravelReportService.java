package com.pcda.mb.reports.railtravelreports.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.CodeHead;
import com.pcda.common.model.EnumType;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.TravelType;
import com.pcda.common.services.CodeHeadServices;
import com.pcda.common.services.EnumTypeServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.PAOMappingServices;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.mb.reports.railtravelreports.model.BookingCancellationDetailsResponse;
import com.pcda.mb.reports.railtravelreports.model.BookingDetailsResponseModel;
import com.pcda.mb.reports.railtravelreports.model.GetBookingDetailsModel;
import com.pcda.mb.reports.railtravelreports.model.GetRailCancelletionDetailsModel;
import com.pcda.mb.reports.railtravelreports.model.RailTravelInputtModel;
import com.pcda.mb.reports.railtravelreports.model.RailTravelReportResponseModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class RailTravelReportService {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OfficesService officesService;

	@Autowired
	private CodeHeadServices codeHeadServices;
	@Autowired
	private TravelTypeServices travelTypeServices;

	@Autowired
	private EnumTypeServices enumTypeServices;

	// Get GroupId
	public Optional<OfficeModel> getOfficesByGroupId(BigInteger userId) {

		return officesService.getOfficeByUserId(userId);
	}

	public List<OfficeModel> getAllPao() {
		List<OfficeModel> paoList = officesService.getOffices("PAO", "1");
	
		return paoList;
	}

//	GET ALL TRAVEL TYPE
	public List<TravelType> getAllTravelType(Integer approvalType) {
		List<TravelType> travelTypeList = travelTypeServices.getAllTravelType(approvalType);
		
		return travelTypeList;
	}

	// Method that returns All Vendors
	public List<EnumType> getTatkalFlag(String enumType) {
		DODLog.info(LogConstant.RAIL_REPORT, PAOMappingServices.class,
				"[RailTravelReportService] ## enumType :" + enumType);
		return enumTypeServices.getEnumType(enumType);
		}

	// Code Head
	public List<CodeHead> getCodeHeadService() {
		List<CodeHead> codeHeadList = codeHeadServices.getCodeHeadByApproval("1");
		if (null == codeHeadList) {
			codeHeadList = new ArrayList<>();
		}

		Set<String> set = new HashSet<>();
		List<CodeHead> uniqueCodeHeads = codeHeadList.stream().filter(p -> set.add(p.getRailCodehead())).toList();

		
		return uniqueCodeHeads;
	}

	// Get Rail travel Report

	public RailTravelReportResponseModel getViewRailTravelRepo(RailTravelInputtModel railTravelInputtModel) {

		RailTravelReportResponseModel response = new RailTravelReportResponseModel();
		DODLog.info(LogConstant.RAIL_REPORT, RailTravelReportService.class,
				"[getViewRailTravelRepo] ## BOOKING FORM INPUT  MODEL ######### ::" + railTravelInputtModel);

		try {

			railTravelInputtModel.setBookingId(railTravelInputtModel.getBookingId().trim());
			railTravelInputtModel.setRequestId(railTravelInputtModel.getRequestId().trim());
			railTravelInputtModel.setPnrNo(railTravelInputtModel.getPnrNo().trim());
			railTravelInputtModel.setTicketNo(railTravelInputtModel.getTicketNo().trim());
			railTravelInputtModel.setFromDate(railTravelInputtModel.getFromDate().trim());
			railTravelInputtModel.setToDate(railTravelInputtModel.getToDate().trim());
			railTravelInputtModel.setFromJourneyDate(railTravelInputtModel.getFromJourneyDate().trim());
			railTravelInputtModel.setToJourneyDate(railTravelInputtModel.getToJourneyDate().trim());
			railTravelInputtModel.setPersonalNo(railTravelInputtModel.getPersonalNo().trim());
			
			String url = PcdaConstant.COMMON_REPORT_URL + "/railTravelReport";
			response = restTemplate.postForObject(url, railTravelInputtModel, RailTravelReportResponseModel.class);

		} catch (Exception e) {
		
			DODLog.printStackTrace(e, RailTravelReportService.class, LogConstant.RAIL_REPORT);
		}
		
		return response;

		}

	// Get Rail Tickets Details
	public GetBookingDetailsModel getrailTicketsBookindDtls(String bookingId) {
		GetBookingDetailsModel bookingdModel = null;

		DODLog.info(LogConstant.RAIL_REPORT, RailTravelReportService.class, "Get Rail TiCkets From BookingId :: "+bookingId);
		String url = PcdaConstant.COMMON_REPORT_URL + "/getRailTicketBookingDetails?bookingId=" + bookingId;

		try {
			DODLog.info(LogConstant.RAIL_REPORT, RailTravelReportService.class,
					"[getrailTicketsBookindDtls] ## bookingId : " + bookingId);

			BookingDetailsResponseModel response = restTemplate.getForObject(url,
					BookingDetailsResponseModel.class);

			if (response != null && response.getErrorCode() == 200) {
				bookingdModel = response.getResponse();
				Collections.sort(bookingdModel.getTicketPassangerDetails());
	}

		} catch (Exception e) {
			DODLog.printStackTrace(e, RailTravelReportService.class, LogConstant.RAIL_REPORT);
		}
	

		return bookingdModel;
	}

	// Get Rail tickets Cancellation Details
	public GetRailCancelletionDetailsModel getrailTiceketsCancellationDtls(String bookingId) {
		GetRailCancelletionDetailsModel cancellationDtls = null;
		DODLog.info(LogConstant.RAIL_REPORT, RailTravelReportService.class, "[getrailTiceketsCancellationDtls] ## bookingId " +bookingId);


		try {
			
			BookingCancellationDetailsResponse response = restTemplate.getForObject(PcdaConstant.COMMON_REPORT_URL
					+ "/getRailTicketCancellationViewDetail?bookingId=" + bookingId,
					BookingCancellationDetailsResponse.class);

			if (response != null && response.getErrorCode() == 200) {
				cancellationDtls = response.getResponse();
			}
			

		} catch (Exception e) {
			DODLog.printStackTrace(e, RailTravelReportService.class, LogConstant.RAIL_REPORT);
	}
		
		return cancellationDtls;
}

}
