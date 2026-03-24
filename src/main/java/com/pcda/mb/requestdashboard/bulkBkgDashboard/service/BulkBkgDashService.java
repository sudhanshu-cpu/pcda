package com.pcda.mb.requestdashboard.bulkBkgDashboard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.GetATTBulkBkgConfirmBookingResponse;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.GetATTBulkBkgResponse;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.GetBLBulkBkgConfirmBookResponse;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.GetBLBulkBkgResponse;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.PostConfirmAttBulkBkgModel;
import com.pcda.mb.requestdashboard.bulkBkgDashboard.model.PostConfirmBLAirBulkBkgModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.FlightSearchOption;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PosrAirBookATTModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostAirBookBlModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class BulkBkgDashService {

	@Autowired
	private RestTemplate restTemplate;

	public GetBLBulkBkgResponse bookBLFlight(PostAirBookBlModel postAirBookBlModel,
			List<FlightSearchOption> flightOptList) {
		
		GetBLBulkBkgResponse response = new GetBLBulkBkgResponse();
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, BulkBkgDashService.class,
				"[bookBLFlight] ### POST BL BOOK MODEL ### " + postAirBookBlModel);

		try {
			Optional<FlightSearchOption> flightOpt = flightOptList.stream()
					.filter(e -> e.getFlightKey().equals(postAirBookBlModel.getFlightKey())).findFirst();

			if (flightOpt.isPresent()) {
				postAirBookBlModel.setFlightOption(flightOpt.get());
			}
			String url = PcdaConstant.AIR_BOOK_SERVICE + "/bl/validateBulkBkgFlightPrice";
			response = restTemplate.postForObject(url, postAirBookBlModel, GetBLBulkBkgResponse.class);
			DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, BulkBkgDashService.class,
					"[bookBLFlight]## Bl RESPONSE ## :: " + response);

		} catch (Exception e) {
			
			DODLog.printStackTrace(e, BulkBkgDashService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
		}
		return response;
	}

	// ATT BOOK
	public GetATTBulkBkgResponse bookATTFlight(PosrAirBookATTModel airBookATTModel) {

		GetATTBulkBkgResponse response = new GetATTBulkBkgResponse();
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, BulkBkgDashService.class,
				"POST ATT BULK BOOK MODEL:: " + airBookATTModel);

		try {
			String url = PcdaConstant.AIR_BOOK_SERVICE + "/att/validateATTBulkBkgFlightPrice";
			response = restTemplate.postForObject(url, airBookATTModel, GetATTBulkBkgResponse.class);
			DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, BulkBkgDashService.class,
					"ATT RESPONSE :: " + response);

		} catch (Exception e) {
			
			DODLog.printStackTrace(e, BulkBkgDashService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
		}
		return response;
	}

	// BL ConfirmBooking
	public GetBLBulkBkgConfirmBookResponse confirmBLAirBook(PostConfirmBLAirBulkBkgModel blAirBookModel) {

		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, BulkBkgDashService.class,
				"########## POST BL CONFIRM MODEL :: " + blAirBookModel);
		GetBLBulkBkgConfirmBookResponse blResponse = new GetBLBulkBkgConfirmBookResponse();

		try {
			String url = PcdaConstant.AIR_BOOK_SERVICE + "/bl/BulkBkgbook";
			blResponse = restTemplate.postForObject(url, blAirBookModel, GetBLBulkBkgConfirmBookResponse.class);
		} catch (Exception e) {
		
			DODLog.printStackTrace(e, BulkBkgDashService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
		}
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, BulkBkgDashService.class,
				"########## BL CONFIRM RESPONSE :: " + blResponse);
		return blResponse;
	}

	// ATT ConfirmBooking
	public GetATTBulkBkgConfirmBookingResponse confirmATTAirBook(PostConfirmAttBulkBkgModel attAirBookModel	) {

		GetATTBulkBkgConfirmBookingResponse attResponse = new GetATTBulkBkgConfirmBookingResponse();

		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, BulkBkgDashService.class,
				"####### POST ATT BULK CONFIRM MODEL :: " + attAirBookModel);
		try {
			String url = PcdaConstant.AIR_BOOK_SERVICE + "/att/BulkBkgbook";
			attResponse = restTemplate.postForObject(url, attAirBookModel, GetATTBulkBkgConfirmBookingResponse.class);
			DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, BulkBkgDashService.class,
					"##### ATT CONFIRM RESPONSE :: " + attResponse);
		} catch (Exception e) {
		
			DODLog.printStackTrace(e, BulkBkgDashService.class, LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE);
		}

		return attResponse;
	}

}
