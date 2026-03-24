package com.pcda.co.requestdashboard.approverailcancellation.service;



import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.co.requestdashboard.approverailcancellation.model.GetApprovalDataModel;
import com.pcda.co.requestdashboard.approverailcancellation.model.GetApproveCancelChildModel;
import com.pcda.co.requestdashboard.approverailcancellation.model.GetApproveCancelParentModel;
import com.pcda.co.requestdashboard.approverailcancellation.model.PostCanChildApproveModel;
import com.pcda.co.requestdashboard.approverailcancellation.model.PostCanParentApproveModel;
import com.pcda.co.requestdashboard.approverailcancellation.model.RailCanApprovalResponse;
import com.pcda.co.requestdashboard.approverailcancellation.model.RailCanApproveBookingResponse;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service

public class ApproveRailCancellationService {

	@Autowired
	private OfficesService officesService;

	@Autowired
	private RestTemplate restTemplate;

	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {

		return officesService.getOfficeByUserId(userId);

	}

	public List<GetApprovalDataModel> getRequestedApprovalData(String groupId) {
		DODLog.info(LogConstant.RAIL_CANCEL_APPROVAL_LOG, ApproveRailCancellationService.class," groupId" + groupId);
		
		List<GetApprovalDataModel> modelList = new ArrayList<>();

		String url = PcdaConstant.RAIL_CANCELLATION_URL + "/getBookingDetailsForApproval?groupId=" + groupId;
		try {
			RailCanApprovalResponse response = restTemplate.getForObject(url, RailCanApprovalResponse.class);
			if (response != null && response.getErrorCode() == 200) {
				modelList = response.getResponseList();
				
				modelList.stream().forEach(obj->obj.setBookingDateStr(CommonUtil.formatDate(obj.getBookingDate(), "dd-MMM-yyyy")));
				modelList.stream().forEach(obj->obj.setJourneyDateStr(CommonUtil.formatDate(obj.getJourneyDate(), "dd-MMM-yyyy")));
				
			} else {
				new RailCanApprovalResponse();
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, ApproveRailCancellationService.class, LogConstant.RAIL_CANCEL_APPROVAL_LOG);
		}
		DODLog.info(LogConstant.RAIL_CANCEL_APPROVAL_LOG, ApproveRailCancellationService.class,"RAIL CANCELLATION APROVAL MODEL LIST" + modelList);
		return modelList;
	}

	// get ExcptionalCancelSingleData

	public GetApproveCancelParentModel getRailCanApprovalData(String bookingId) {
		DODLog.info(LogConstant.RAIL_CANCEL_APPROVAL_LOG, ApproveRailCancellationService.class," bookingId " + bookingId);
		GetApproveCancelParentModel parentModel = new GetApproveCancelParentModel();

		try {

			RailCanApproveBookingResponse response = restTemplate.getForObject(
					new URI(PcdaConstant.RAIL_CANCELLATION_URL + "/getTicketDetails?bookingId=" + bookingId),
					RailCanApproveBookingResponse.class);

			if (response != null && response.getResponse() != null && response.getErrorCode() == 200) {
				parentModel = response.getResponse();
			}
			parentModel.setBoardingDateStr(CommonUtil.formatDate(parentModel.getBoardingDate(), "dd-MMM-yyyy"));
			parentModel.setBookingDateStr(CommonUtil.formatDate(parentModel.getBookingDate(), "dd-MMM-yyyy"));
			parentModel.setJourneyDateStr(CommonUtil.formatDate(parentModel.getJourneyDate(), "dd-MMM-yyyy"));
			
			List<GetApproveCancelChildModel> list = parentModel.getPassengerList();

			for (GetApproveCancelChildModel s : list) {

				if (s.getPassangerSex() == 0) {
					s.setPSex("Male");
				}
				if (s.getPassangerSex() == 1) {
					s.setPSex("Female");
				}
			}
			parentModel.setPassengerList(list);
		} catch (Exception e) {
			DODLog.printStackTrace(e, ApproveRailCancellationService.class, LogConstant.RAIL_CANCEL_APPROVAL_LOG);
		}
		DODLog.info(LogConstant.RAIL_CANCEL_APPROVAL_LOG, ApproveRailCancellationService.class,
				"####### RAIL CAN PARENT TABLE DATA ####### " + parentModel);
		return parentModel;
	}

	public RailCanApproveBookingResponse sendForApproval(PostCanParentApproveModel approveModel,
			HttpServletRequest request) {

		RailCanApproveBookingResponse response;
		try {
		int ttlPxn = approveModel.getTotalPessnager();

		ArrayList<PostCanChildApproveModel> cancelPsngrList = new ArrayList<>();

		for (int i = 0; i <= ttlPxn; i++) {

			if (null != request.getParameter("isOnGovtInt_" + i)
					&& !request.getParameter("isOnGovtInt_" + i).equals("")) {
				String cancelSatus=request.getParameter("cancelStatus_"+i);
				if(cancelSatus.equalsIgnoreCase("For Cancellation")) {
				PostCanChildApproveModel childModel = new PostCanChildApproveModel();

				childModel.setPassengerNo(Integer.parseInt(request.getParameter("passangerNo" + i)));
				childModel.setIsOnGovtInt(Integer.parseInt(request.getParameter("isOnGovtInt_" + i)));
				childModel.setCancelStatus(request.getParameter("cancelStatus_" + i));
				cancelPsngrList.add(childModel);

			}
		}
		}
		approveModel.setCancelPsngrList(cancelPsngrList);

		

			DODLog.info(LogConstant.RAIL_CANCEL_APPROVAL_LOG, ApproveRailCancellationService.class,
					"########## RAIL CAN APPROAL MODEL ########" + approveModel);
			
			
			response = restTemplate.postForObject(PcdaConstant.RAIL_CANCELLATION_URL+"/approveCancellation",
					approveModel, RailCanApproveBookingResponse.class);
			
			if (response != null && response.getErrorCode() == 200) {
				return response;
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, ApproveRailCancellationService.class, LogConstant.RAIL_CANCEL_APPROVAL_LOG);
		}
		return new RailCanApproveBookingResponse();

	}

	// for disapproval
	public RailCanApproveBookingResponse sendDisApproval(PostCanParentApproveModel disApproveModel) {
		RailCanApproveBookingResponse response;
		try {
		
			DODLog.info(LogConstant.RAIL_CANCEL_APPROVAL_LOG, ApproveRailCancellationService.class,
					" ## RAIL CAN DISAPPROVE" + disApproveModel);
			response = restTemplate.postForObject(PcdaConstant.RAIL_CANCELLATION_URL + "/disApproveCancellation",
					disApproveModel, RailCanApproveBookingResponse.class);
			if (response != null && response.getErrorCode() == 200) {
				return response;
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, ApproveRailCancellationService.class, LogConstant.RAIL_CANCEL_APPROVAL_LOG);
		}
		return new RailCanApproveBookingResponse();
	}

}
