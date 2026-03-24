package com.pcda.mb.reports.airticketcancellation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.reports.airticketcancellation.model.AirTicketBookingDetailsResponse;
import com.pcda.mb.reports.airticketcancellation.model.AirTicketBookingDetailsResponseModel;
import com.pcda.mb.reports.airticketcancellation.model.AirTicketCancellationModel;
import com.pcda.mb.reports.airticketcancellation.model.AirTicketCancellationRequestData;
import com.pcda.mb.reports.airticketcancellation.model.AirTicketCancellationResponse;
import com.pcda.mb.reports.airticketcancellation.model.AirTktCancellationDataModel;
import com.pcda.mb.reports.airticketcancellation.model.AirTktCancellationReportResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;
@Service
public class AirTicketCancellationService {

	@Autowired
	private RestTemplate restTemplate;
	
	
	
	//Get All Booked Ticket For Personal Number
	public List<AirTicketCancellationModel> getAllBookedTicketForPersonalNo(String personalNo) {
		 DODLog.info(LogConstant.AIR_REPORT,  AirTicketCancellationService.class, "[getAllBookedTicketForPersonalNo]######## personalNo######"+personalNo);
		List<AirTicketCancellationModel> ticketList = new ArrayList<>();
		
		try {
			if (personalNo != null && !personalNo.isEmpty() && !personalNo.trim().equals("")) {
				String	url = PcdaConstant.COMMON_REPORT_URL + "/getAllBookedTicketForPersonalNo?personalNo=" + personalNo;
			

			AirTicketCancellationResponse response = restTemplate.getForObject(url,
					AirTicketCancellationResponse.class);
			if(response!=null && response.getErrorCode()==200 && null!=response.getResponseList()) {
			List<AirTicketCancellationModel> airTicketCancellationModelList = response.getResponseList();
		for(AirTicketCancellationModel model : airTicketCancellationModelList ) {
	     	String[] arr =	model.getJourneyDate().split(" ");
		      model.setJourneyDateStr(arr[0]);
		       ticketList.add(model);
		}
			}
			}
			
   DODLog.info(LogConstant.AIR_REPORT,  AirTicketCancellationService.class,"[getAllBookedTicketForPersonalNo] #### AIR TICKET LIST ###"+ticketList.size());
		} catch (Exception e) {
			DODLog.printStackTrace(e, AirTicketCancellationService.class, LogConstant.AIR_REPORT);
		}
		return ticketList;
		
	}
	
	
	//Pop Data List
	public AirTicketBookingDetailsResponseModel getAirTicketBookingDetails(String bookingId ){
		 DODLog.info(LogConstant.AIR_REPORT,  AirTicketCancellationService.class, "[getAirTicketBookingDetails]######## bookingId ###"+bookingId);
		AirTicketBookingDetailsResponseModel airTicketBookingDlsList = null;
		
		try {
			if (bookingId  != null && !bookingId .isEmpty()) {
				String	url = PcdaConstant.COMMON_REPORT_URL + "/getAirTicketBookingDetails?bookingId=" + bookingId;
			

			AirTicketBookingDetailsResponse response = restTemplate.getForObject(url,
					AirTicketBookingDetailsResponse.class);
			if(response!=null && response.getErrorCode()==200) {
			airTicketBookingDlsList = response.getResponse();
			}
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, AirTicketCancellationService.class, LogConstant.AIR_REPORT);
		}	
		DODLog.info(LogConstant.AIR_REPORT,  AirTicketCancellationService.class, "[getAirTicketBookingDetails]######## airTicketBookingDlsList ###"+airTicketBookingDlsList);
	  return airTicketBookingDlsList;	
	}

	
//for air ticket cancellation service

public List<AirTktCancellationDataModel> getAirTktCancellationData(AirTicketCancellationRequestData requestData) {
		
		
		AirTktCancellationReportResponse airTktCancellationResData= new AirTktCancellationReportResponse();
		 List<AirTktCancellationDataModel> cancelReport=new ArrayList<>();
			String url = null;
			
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			
			HttpEntity<AirTicketCancellationRequestData> requestEntity = new HttpEntity<>(requestData, headers);
			url = PcdaConstant.COMMON_REPORT_URL + "/getAirTicketCancellationReport";
			
			ResponseEntity<AirTktCancellationReportResponse> responseEntity =restTemplate.postForEntity(url, requestEntity, AirTktCancellationReportResponse.class);
			airTktCancellationResData = responseEntity.getBody();

			if (airTktCancellationResData != null && airTktCancellationResData.getErrorCode() == 200
					 && !airTktCancellationResData.getResponseList().isEmpty()) {
				
				cancelReport =airTktCancellationResData.getResponseList();

			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, AirTicketCancellationService.class, LogConstant.AIR_REPORT);
		}
			return cancelReport;
	}

//validation check 

	public String validateFormBean(AirTicketCancellationRequestData requestData) {
		String msg="OK";
		if ((requestData.getBookingId() == null || requestData.getBookingId().isEmpty())
				&& (requestData.getPersonalNo()==null || requestData.getPersonalNo().isEmpty())
				&& (requestData.getFcNumber() == null || requestData.getFcNumber().isEmpty())
				&& (requestData.getFbNumber() == null || requestData.getFbNumber().isEmpty())
				&&( requestData.getFromDate() == null || requestData.getFromDate().isEmpty())
				&& (requestData.getToDate() == null || requestData.getToDate().isEmpty())
				) {
	
			return "Please provide valid inputs";

		}
		return msg;
		
	
	}
	
	
	
	
}
