package com.pcda.mb.travel.aircancellation.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.mb.travel.aircancellation.model.AirCancellationModel;
import com.pcda.mb.travel.aircancellation.model.AirCancellationResponse;
import com.pcda.mb.travel.aircancellation.model.PassangerDetail;
import com.pcda.mb.travel.aircancellation.model.PostAirCancellationModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AirCancellationService {

	@Autowired
	private OfficesService officesService;

	@Autowired
	private RestTemplate restTemplate;

	public Optional<OfficeModel> getOfficesByUserId(BigInteger userId) {

		return officesService.getOfficeByUserId(userId);
	}

	public List<AirCancellationModel> getAirCancellationDe(String groupId, String type) {
		DODLog.info(LogConstant.AIR_TICKET_CANCELLATION_LOG, AirCancellationService.class,"[getAirCancellationDe] ## groupId " + groupId +":: type:: "+type);
		List<AirCancellationModel> airCancellationModels = new ArrayList<>();

		String url = "/ticketForCancellation/";

		url = url + groupId + "/" + type;
	

		try {

			ResponseEntity<AirCancellationResponse> unitMovementEntity = restTemplate.exchange(
					PcdaConstant.AIR_SERVICE_BASE_URL + url, HttpMethod.GET, null,
					new ParameterizedTypeReference<AirCancellationResponse>() {
					});
			AirCancellationResponse response = unitMovementEntity.getBody();

			if (null != response && response.getErrorCode() == 200 && response.getResponseList() != null) {
				airCancellationModels = response.getResponseList();
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, AirCancellationService.class, LogConstant.AIR_TICKET_CANCELLATION_LOG);
		}
		DODLog.info(LogConstant.AIR_TICKET_CANCELLATION_LOG, AirCancellationService.class,"[getAirCancellationDe] ## airCancellationModels " +airCancellationModels.size());
		return airCancellationModels;

	}

	public AirCancellationModel getAirBookingContentByBookingId(String bookingId) {

		DODLog.info(LogConstant.AIR_TICKET_CANCELLATION_LOG, AirCancellationService.class,"[getAirBookingContentByBookingId] ## bookingId " + bookingId);
		AirCancellationModel airBookingContent = null;

		try {

			ResponseEntity<AirCancellationResponse> entity = restTemplate.exchange(
					PcdaConstant.AIR_SERVICE_BASE_URL + "/ticketDetailsCancellation/" + bookingId, HttpMethod.GET, null,
					new ParameterizedTypeReference<AirCancellationResponse>() {
					});
			AirCancellationResponse response = entity.getBody();

			if (null != response && response.getErrorCode() == 200 && response.getResponse() != null) {
				airBookingContent = response.getResponse();
				
				List<PassangerDetail> passangerDetail = airBookingContent.getPassangerDetail();
				Collections.sort(passangerDetail);
				airBookingContent.setPassangerDetail(passangerDetail);
			}

			
		} catch (Exception e) {
			DODLog.printStackTrace(e, AirCancellationService.class, LogConstant.AIR_TICKET_CANCELLATION_LOG);
		}
		
		return airBookingContent;
	}

	public AirCancellationResponse airTicketCanReqestSave(PostAirCancellationModel postAirCancellationModel,
			HttpServletRequest request) {

		
		AirCancellationResponse response = null;

		try {
		if (postAirCancellationModel.getIsRoundTrip().equalsIgnoreCase("NO")) {

			if (postAirCancellationModel.getOnwardCheckList() != null
					&& !postAirCancellationModel.getOnwardCheckList().isEmpty()) {

				Map<String, Integer> onwardMap = new HashMap<>();
				postAirCancellationModel.getOnwardCheckList().stream().forEach(e -> {

					String isOfficial = Optional.ofNullable(request.getParameter("onwardIsOfficial" + e)).orElse("1");
					onwardMap.put(e, Integer.parseInt(isOfficial));
				});

				

				postAirCancellationModel.setOnwardCheck(onwardMap);

				DODLog.info(LogConstant.AIR_TICKET_CANCELLATION_LOG, AirCancellationService.class,
						"######## Save model AirCancellation ######### ::" + postAirCancellationModel);
				
				response = restTemplate.postForObject(PcdaConstant.AIR_SERVICE_BASE_URL + "/saveCancellationRequest",
						postAirCancellationModel, AirCancellationResponse.class);

				DODLog.info(LogConstant.AIR_TICKET_CANCELLATION_LOG, AirCancellationService.class,
						"### roundtrip NO response ::" + response);

			}
		}

		else if (postAirCancellationModel.getIsRoundTrip().equalsIgnoreCase("YES")
				&& postAirCancellationModel.getReturnCheckList() != null && !postAirCancellationModel.getOnwardCheckList().isEmpty()) {

				Map<String, Integer> onwardMap = new HashMap<>();

				Map<String, Integer> retrurnMap = new HashMap<>();
				postAirCancellationModel.getReturnCheckList().stream().forEach(e -> {

					String returnIsOfficialValue = Optional.ofNullable(request.getParameter("returnIsOfficial" + e))
							.orElse("1");
					onwardMap.put(e, Integer.parseInt(returnIsOfficialValue));
				});

				postAirCancellationModel.getOnwardCheckList().stream().forEach(e -> {

					String isOfficial = Optional.ofNullable(request.getParameter("onwardIsOfficial" + e)).orElse("1");
					onwardMap.put(e, Integer.parseInt(isOfficial));
				});

				

				postAirCancellationModel.setOnwardCheck(onwardMap);
				postAirCancellationModel.setReturnCheck(retrurnMap);
				DODLog.info(LogConstant.AIR_TICKET_CANCELLATION_LOG, AirCancellationService.class,
						"######## Save AirCancellation roundtrip yes ######### ::" + postAirCancellationModel);
				response = restTemplate.postForObject(PcdaConstant.AIR_SERVICE_BASE_URL + "/saveCancellationRequest",
						postAirCancellationModel, AirCancellationResponse.class);

				DODLog.info(LogConstant.AIR_TICKET_CANCELLATION_LOG, AirCancellationService.class,
						"### roundtrip YES response ::" + response);

			}
		
		
		}catch (Exception e) {
			DODLog.printStackTrace(e, AirCancellationService.class, LogConstant.AIR_TICKET_CANCELLATION_LOG);
		}

		return response;
	}

}
