package com.pcda.pao.reports.airbookingreport.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.TravelType;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.mb.reports.airticketcancellation.model.AirTicketBookingDetailsResponse;
import com.pcda.mb.reports.airticketcancellation.model.AirTicketBookingDetailsResponseModel;
import com.pcda.mb.reports.airticketcancellation.service.AirTicketCancellationService;
import com.pcda.pao.reports.airbookingreport.model.AirBookingModel;
import com.pcda.pao.reports.airbookingreport.model.AirBookingReportInputModel;
import com.pcda.pao.reports.airbookingreport.model.AirBookingResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class AirBookingService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private TravelTypeServices travelTypeServices;
	
	@Autowired
	private OfficesService officeServices;
	
	//Method to get the airBooking ReportList using Rest API
	public List<AirBookingModel> getAirBookingReport(AirBookingReportInputModel airBookingInputModel )
	{
		DODLog.info(LogConstant.AIR_TRANSCATION_LOG, TravelTypeServices.class, "[getAirBookingReport] : airBookingInputModel " + airBookingInputModel);
		List<AirBookingModel> airBookingModel = new ArrayList<>();
		String url= null;
		try {
			  url=PcdaConstant.COMMON_REPORT_URL + "/airTravelReport";
			  AirBookingResponse response = restTemplate.postForObject(url, airBookingInputModel, AirBookingResponse.class);
			  
			  if(response != null && response.getErrorCode()==200 && null!=response.getResponse()) {
				 airBookingModel = response.getResponse();
				
			  }	
				 DODLog.info(LogConstant.AIR_TRANSCATION_LOG, TravelTypeServices.class, "[getAirBookingReport] : airBookingModel " + airBookingModel.size());
		}catch(Exception e)
		{
			DODLog.printStackTrace(e, AirBookingService.class, LogConstant.AIR_TRANSCATION_LOG);
		}
		
		return airBookingModel;
	}
	
	
	//Get Travel Type
	public List<TravelType> getAllTravelType(Integer approvalType) {

		List<TravelType> travelTypeList = travelTypeServices.getAllTravelType(approvalType);
		DODLog.info(LogConstant.AIR_TRANSCATION_LOG, TravelTypeServices.class,
				"Get TravelType with approval type: " + travelTypeList);
		return travelTypeList;
	}
	
	
	// Get GroupId
			public Optional<OfficeModel> getOfficesByGroupId(BigInteger userId) {

				return officeServices.getOfficeByUserId(userId);
			}
			
			//Get all unit
			public List<OfficeModel> getAllUnit() {
				List<OfficeModel> unitList = officeServices.getOffices("UNIT", "1");
				DODLog.info(LogConstant.AIR_TRANSCATION_LOG, OfficesService.class,
						"Get Unit with approval type:" + unitList);
				return unitList;
			}

			//POP UP DATA LIST
			public AirTicketBookingDetailsResponseModel getAirTicketBookingDetails(String bookingId ){
				DODLog.info(LogConstant.AIR_TRANSCATION_LOG, TravelTypeServices.class, "[getAirTicketBookingDetails] : bookingId " + bookingId);
				AirTicketBookingDetailsResponseModel airTicketBookingDlsList = null;
				String url = null;
				try {
					if (bookingId  != null && !bookingId .isEmpty()) {
						url = PcdaConstant.COMMON_REPORT_URL + "/getAirTicketBookingDetails?bookingId=" + bookingId;
				    AirTicketBookingDetailsResponse response = restTemplate.getForObject(url,AirTicketBookingDetailsResponse.class);
					if(response != null ) {
					airTicketBookingDlsList = response.getResponse();
					}
					}
				} catch (Exception e) {
					DODLog.printStackTrace(e, AirTicketCancellationService.class, LogConstant.AIR_TRANSCATION_LOG);
				}	
				DODLog.info(LogConstant.AIR_TRANSCATION_LOG, TravelTypeServices.class, "[getAirTicketBookingDetails] : airTicketBookingDlsList " + airTicketBookingDlsList);
			  return airTicketBookingDlsList;	
			}
	
}
