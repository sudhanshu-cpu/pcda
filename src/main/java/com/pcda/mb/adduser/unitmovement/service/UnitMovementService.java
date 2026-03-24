package com.pcda.mb.adduser.unitmovement.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.AirSearchResponse;
import com.pcda.common.model.Category;
import com.pcda.common.model.CategoryResponse;
import com.pcda.common.model.DODServices;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.RailSearchResponse;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;
import com.pcda.mb.adduser.unitmovement.model.PostUnitMovement;
import com.pcda.mb.adduser.unitmovement.model.UnitMovementModel;
import com.pcda.mb.adduser.unitmovement.model.UnitMovementResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class UnitMovementService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	MasterServices masterServices;

	@Autowired
	private OfficesService officesService;

	public Optional<OfficeModel> getUnitByUserId(BigInteger userId) {
		return officesService.getOfficeByUserId(userId);

	}
	
	// ------------------- Get Categories Based on Service id -----------------------//
	public List<Category> getCategories(String serviceId) {
		DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementService.class,
				"[getCategories]Get Categories with service id: " + serviceId);

		List<Category> categoryList = new ArrayList<>();
		try {
		ResponseEntity<CategoryResponse> responseEntity = restTemplate.exchange(
				PcdaConstant.MASTER_BASE_URL + "/category/getCategories/" + serviceId, HttpMethod.GET, null,
				new ParameterizedTypeReference<CategoryResponse>() {
				});
		CategoryResponse categoryResponse = responseEntity.getBody();

		if (null != categoryResponse && (categoryResponse.getErrorCode() == 200)
				&& null != categoryResponse.getResponseList()) {
			categoryList = categoryResponse.getResponseList();
		}
		}catch (Exception e) {
			DODLog.printStackTrace(e, UnitMovementService.class, LogConstant.UNIT_MOVEMENT_LOG_FILE);
		}
		return categoryList;
	}

// ----------------------- TO GET STATION CODE ------------------//

	public List<String> getStation(String station) {
	
		List<String> stations = new ArrayList<>();
		String notMatchStation="Station Name Not Exist";
		try {
		if (null != station && !station.isBlank()) {
			ResponseEntity<RailSearchResponse> serviceEntity = restTemplate.exchange(
					PcdaConstant.RAIL_BASE_URL + "/railStation/searchRailStation/" + station, HttpMethod.GET, null,
					new ParameterizedTypeReference<RailSearchResponse>() {
					});

			

			RailSearchResponse response = serviceEntity.getBody();
			if (null != response && response.getErrorCode() == 200 && null != response.getResponseList() && !response.getResponseList() .isEmpty() ) {
				stations.addAll(response.getResponseList());
				return stations;
			}else {
				stations.add(notMatchStation);
			}
		}
		}catch (Exception e) {
			DODLog.printStackTrace(e, UnitMovementService.class, LogConstant.UNIT_MOVEMENT_LOG_FILE);
		}
		DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementService.class,
				"[getStation] stations ::  " + stations.size());
		return stations;
	}

// ------------------------ TO GET AIRPORT CODE ----------------------//
	public List<String> getAirport(String airPortName) {
		
		List<String> airport = new ArrayList<>();
		String notMatchAirport="Airport Name Not Exist";
		try {
		if (null != airPortName && !airPortName.isBlank()) {
		ResponseEntity<AirSearchResponse> serviceEntity = restTemplate.exchange(
				PcdaConstant.AIR_BASE_URL + "/airport/searchAirport/" + airPortName, HttpMethod.GET, null,
				new ParameterizedTypeReference<AirSearchResponse>() {
				});



		AirSearchResponse airSearchResponse = serviceEntity.getBody();
		if (null != airSearchResponse && airSearchResponse.getErrorCode() == 200 && null != airSearchResponse.getResponseList() && !airSearchResponse.getResponseList() .isEmpty() ) {
			airport.addAll(airSearchResponse.getResponseList());
			return airport;
		}else {
			airport.add(notMatchAirport);
		}
		}
		}catch (Exception e) {
			DODLog.printStackTrace(e, UnitMovementService.class, LogConstant.UNIT_MOVEMENT_LOG_FILE);
		}
		DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementService.class,
				"[getAirport] airport ::  " + airport.size());
		return airport;
	}



	
	

	// -------- TO GET PERSONAL NO by GroupID, GroupId+PersonalNo, GroupId+PersonalNo+CategoryId, GroupId+CategoryId--------//
	
	public List<UnitMovementModel> getPersonalNoData(String groupId, String personalNo, String categoryId) {
		
		
		List<UnitMovementModel> personalNoData = new ArrayList<>();
		String url = "/getUnitTravelers?";
		try {
			if (!groupId.isEmpty() && personalNo.isBlank() && categoryId.isBlank()) {
				 
				url = url + "groupId=" + groupId;
				
				
			}else if (!groupId.isEmpty() && !personalNo.isEmpty() && categoryId.isBlank()) {
				
				url = url + "personalNo=" + personalNo + "&groupId=" + groupId;

			}else if (!groupId.isEmpty() && !personalNo.isEmpty() && !categoryId.isEmpty()) {
				
				url = url + "personalNo=" + personalNo + "&categoryId=" + categoryId + "&groupId=" + groupId;
				
			}else if (!groupId.isEmpty() && personalNo.isBlank() && !categoryId.isEmpty()) {
				
				url = url + "categoryId=" + categoryId + "&groupId=" + groupId;
			}

			ResponseEntity<UnitMovementResponse> unitMovementEntity = restTemplate.exchange(
					PcdaConstant.UNIT_MOVEMENT_URL + url, HttpMethod.GET, null,
					new ParameterizedTypeReference<UnitMovementResponse>() {
					});
			UnitMovementResponse unitMovementResponse = unitMovementEntity.getBody();

			if (null != unitMovementResponse && unitMovementResponse.getErrorCode() == 200 && unitMovementResponse.getResponseList() != null) {
				personalNoData.addAll(unitMovementResponse.getResponseList());
			}

			DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementService.class, "[getPersonalNoData] : personalNoData :: " + personalNoData.size());
			
		}catch (Exception e) {
			DODLog.printStackTrace(e, UnitMovementService.class, LogConstant.UNIT_MOVEMENT_LOG_FILE);
		}
		return personalNoData;

	}

	
	//-------------Save Unit Movement ---------------------------------//
	public UnitMovementResponse saveUnitMovement(PostUnitMovement postUnitMovement) {
		UnitMovementResponse unitMovementResponse=new UnitMovementResponse();
		try {
		ResponseEntity<UnitMovementResponse> response = restTemplate.postForEntity(PcdaConstant.UNIT_MOVEMENT_URL + "/save", postUnitMovement, UnitMovementResponse.class);
		 unitMovementResponse =response.getBody();	
		DODLog.info(LogConstant.UNIT_MOVEMENT_LOG_FILE, UnitMovementService.class, "UnitMovement Response::::: "+ unitMovementResponse);
		}catch (Exception e) {
			DODLog.printStackTrace(e, UnitMovementService.class, LogConstant.UNIT_MOVEMENT_LOG_FILE);
			
		}
		return unitMovementResponse;
	}
	
	//-------------- STRING TO DATE--------------//
		public Date formatString(String date) {
			Date date2;
			try {
				date2 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
			} catch (ParseException e) {
				DODLog.printStackTrace(e, UnitMovementService.class, 1);
				return null;
			}
			return date2;
		}
		

		
		// Get Service Based with Service id
		public DODServices getService(String serviceId) {
			DODServices dodServices = new DODServices();
			try {
				Optional<DODServices> opt =masterServices.getServiceByServiceId(serviceId);
				
				if(opt.isPresent()) {
					dodServices = opt.get();
				}
				
			} catch (Exception e) {
				DODLog.printStackTrace(e, UnitMovementService.class, LogConstant.UNIT_MOVEMENT_LOG_FILE);
				
			}
		
				return dodServices;
			}
		
		
		
}
