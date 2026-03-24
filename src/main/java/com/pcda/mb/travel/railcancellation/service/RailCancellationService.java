package com.pcda.mb.travel.railcancellation.service;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;

import com.pcda.mb.travel.railcancellation.model.CancellationResponseModel;
import com.pcda.mb.travel.railcancellation.model.PassengerRailCanDetailsBean;
import com.pcda.mb.travel.railcancellation.model.PostPassengerDetails;
import com.pcda.mb.travel.railcancellation.model.PostRailCancellation;
import com.pcda.mb.travel.railcancellation.model.RailCanBookingDtlsModel;
import com.pcda.mb.travel.railcancellation.model.RailCanBookingDtlsResponse;
import com.pcda.mb.travel.railcancellation.model.RailCancellationModel;
import com.pcda.mb.travel.railcancellation.model.RailCancellationResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;


@Service
public class RailCancellationService {
	
	@Autowired
	private RestTemplate restTemplate;

	
	@Autowired
	private OfficesService officesService;
	
	
	public Optional<OfficeModel> getUnitByUserId(BigInteger userId) {
		return officesService.getOfficeByUserId(userId);
		
	}
	
	
	// Get office by user id
	public OfficeModel getOfficeByUserId(BigInteger userId) {
		OfficeModel officeModel = new OfficeModel();
		try {
			Optional<OfficeModel> opt =officesService.getOfficeByUserId(userId);
			if(opt.isPresent()) {
				officeModel=opt.get();	
			}
			 
		} catch (Exception e) {
			DODLog.printStackTrace(e, RailCancellationService.class, LogConstant.RAIL_CANCELLATION_LOG_FILE);
		}
		DODLog.info(LogConstant.RAIL_CANCELLATION_LOG_FILE, RailCancellationService.class, "OFFICE MODEL ::" +officeModel.toString());
		return officeModel;
	}
	
	// -------- TO GET PERSONAL NO by GroupID, GroupId+PersonalNo, GroupId+PersonalNo+CategoryId, GroupId+CategoryId--------//
	
		public List<RailCancellationModel> getRailBookingDtls(String groupId, String personalNo, String pnrNo) {
			DODLog.info(LogConstant.RAIL_CANCELLATION_LOG_FILE, RailCancellationService.class, "get BookingTicketDtls data with PNR_No: " + pnrNo + " group id: " + groupId + " personal no: " + personalNo );
			List<RailCancellationModel> railCancellationList = new ArrayList<>();
			try {
							
				Map<String,String> params = new HashMap<>();
				params.put("pnrNo", pnrNo);
				params.put("personalNo", personalNo);
				params.put("groupId", groupId);
					
				String baseUrl = PcdaConstant.RAIL_CANCELLATION_URL + "/getBookingDetails?";
				String url = params.entrySet().stream()
						.filter(entry -> !entry.getValue().isEmpty())
					    .map(entry -> entry.getKey() + "=" + entry.getValue())
					    .collect(Collectors.joining("&", baseUrl, ""));
				ResponseEntity<RailCancellationResponse> unitMovementEntity = restTemplate.exchange(
						url, HttpMethod.GET, null,
						new ParameterizedTypeReference<RailCancellationResponse>() {
						});
				RailCancellationResponse railCancellationResponse = unitMovementEntity.getBody();

				if (null != railCancellationResponse && railCancellationResponse.getErrorCode() == 200 && railCancellationResponse.getResponseList() != null) {
					railCancellationList.addAll(railCancellationResponse.getResponseList());
				}
				
			}catch (Exception e) {
				DODLog.printStackTrace(e, RailCancellationService.class, LogConstant.RAIL_CANCELLATION_LOG_FILE);
			}
			DODLog.info(LogConstant.RAIL_CANCELLATION_LOG_FILE, RailCancellationService.class,  "BookingTicketDtls data" + railCancellationList.size());
			return railCancellationList;
		}
	
		// -------- TO GET BOOKING_DTLS WITH booking_id--------//
		
		public RailCanBookingDtlsModel getRailBookingContentByBookingId(String bookingId) {
			DODLog.info(LogConstant.RAIL_CANCELLATION_LOG_FILE, RailCancellationService.class,  " bookingId " + bookingId);

			RailCanBookingDtlsModel railBookingContent =null;
			
			RailCanBookingDtlsResponse response=null;
			int i=0;
			try {
				
				 response = restTemplate.getForObject(PcdaConstant.RAIL_CANCELLATION_URL+"/getTicketDetails?bookingId="+bookingId, RailCanBookingDtlsResponse.class);
				if (null != response && response.getErrorCode() == 200 && response.getResponse() != null) {
					railBookingContent = response.getResponse();
					railBookingContent.setJourneyDateStr(CommonUtil.formatDate(railBookingContent.getJourneyDate(), "dd-MMM-yyyy"));
					railBookingContent.setBookingDateStr(CommonUtil.formatDate(railBookingContent.getBookingDate(), "dd-MMM-yyyy"));
					
					for(PassengerRailCanDetailsBean passModel : railBookingContent.getPassengerList()) {
						if(passModel.getCurrentCancelStatus().equalsIgnoreCase("Booked") && passModel.getPassangerAge()>12) {
							i=i+1;
						}
					}
					
					railBookingContent.setBookAdultCount(i);
				}

				
			} catch (Exception e) {
				DODLog.printStackTrace(e, RailCancellationService.class, LogConstant.RAIL_CANCELLATION_LOG_FILE);
			}
			return railBookingContent;
		}
		 
		// to check if a single passenger is booked in list
		public boolean isBooked(RailCanBookingDtlsModel model ) {
			
			boolean isvalid = false;
			List<PassengerRailCanDetailsBean> arr = model.getPassengerList();
			
			for(PassengerRailCanDetailsBean s : arr) {
				
			if(s.getCurrentCancelStatus().equals("Booked")) {
				isvalid = true;
			   return isvalid;
			}
			}
			DODLog.info(LogConstant.RAIL_CANCELLATION_LOG_FILE, RailCancellationService.class, "#### isvalid ########" + isvalid);
			return isvalid;
		}

		
		public CancellationResponseModel saveRailCancellation(PostRailCancellation postModel,HttpServletRequest request) {
			
			CancellationResponseModel railCancellationSaveResponse=null;
			 List<PostPassengerDetails> childList =  new ArrayList<>();
			
			   String arr = request.getParameter("checkBoxArr");
			   String[] chckArr=arr.split(",");
			
			// int lengthArr = Integer.parseInt(request.getParameter("NoOfCheckBox"));
			 try {
			 for(int j=0;j<chckArr.length;j++) {
				 int i=Integer.parseInt(chckArr[j]);
//			    String check = request.getParameter("checkBox"+i);
//			    if(check!=null && check.equalsIgnoreCase("on")) {
			    	PostPassengerDetails model = new PostPassengerDetails();
			    	int isOff=Integer.parseInt(request.getParameter("isOfficial"+i));
				    
			    model.setIsOfficial(isOff);
			    model.setIsOnGovtInt(request.getParameter("isOnGovtInt"+i));
			    model.setPassangerNo(Integer.parseInt(request.getParameter("passangerNo"+i)));
			    model.setPassangerAge(Integer.parseInt(request.getParameter("passangerAge"+i)));
			   model.setPassangerGender(request.getParameter("passangerGender"+i));
			    model.setPassangerName(request.getParameter("passangerName"+i));
			    model.setTrnCoach(request.getParameter("trnCoach"+i));
			    model.setTrnSeat(request.getParameter("trnSeat"+i));
			    model.setTrnBerth(request.getParameter("trnBerth"+i));
			    model.setBookingStatus(request.getParameter("bookingStatus"+i));
			    model.setCurrentStatus(request.getParameter("currentStatus"+i));
			    model.setCancelStatus(request.getParameter("cancelStatus"+i));
			    
			    childList.add(model);
				    }
				    
			//	 }
			    postModel.setPassengerList(childList);
			    DODLog.info(LogConstant.RAIL_CANCELLATION_LOG_FILE, RailCancellationService.class,
						"SAVING RAIL CANCELATION MODEL ::::: " + postModel);
			
				
					
				ResponseEntity<CancellationResponseModel> response = restTemplate.postForEntity(
							PcdaConstant.RAIL_CANCELLATION_URL+"/updateForCanRequest", postModel,
							CancellationResponseModel.class);
					
				railCancellationSaveResponse = response.getBody();
				

				}catch (Exception e) {
				DODLog.printStackTrace(e, RailCancellationService.class, LogConstant.RAIL_CANCELLATION_LOG_FILE);
			}
			 DODLog.info(LogConstant.RAIL_CANCELLATION_LOG_FILE, RailCancellationService.class, "mapping" + railCancellationSaveResponse);

				return railCancellationSaveResponse;
		}
		
	
}










