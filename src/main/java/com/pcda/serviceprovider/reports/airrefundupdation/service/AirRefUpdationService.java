package com.pcda.serviceprovider.reports.airrefundupdation.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.serviceprovider.reports.airbookingupdation.model.BookingIdResponse;
import com.pcda.serviceprovider.reports.airrefundupdation.model.AirRefBkgIdResponse;
import com.pcda.serviceprovider.reports.airrefundupdation.model.PosrAirRefChildModel;
import com.pcda.serviceprovider.reports.airrefundupdation.model.PostAirRefParentModel;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AirRefUpdationService {

	@Autowired
	private RestTemplate restTemplate;

	public AirRefBkgIdResponse getDataFromBkgId(String bookingId, Integer serviceProvider) {
		AirRefBkgIdResponse response= null;
		
		String url = PcdaConstant.AIR_BOOKING_UPDATION + "/getAirRefundData?bookingId=" + bookingId
				+ "&serviceProvider=" + serviceProvider;

		try {
			response = restTemplate.getForObject(url, AirRefBkgIdResponse.class);
				
			}
		
		 catch (Exception e) {
			DODLog.printStackTrace(e, AirRefUpdationService.class, LogConstant.AIR_REFUND_UPDATION_LOG_FILE);
		}
		DODLog.info(LogConstant.AIR_REFUND_UPDATION_LOG_FILE, AirRefUpdationService.class," RESPONSE REFUND  MODEL FROM BKG ID" + response);
		return response;
	}

	public BookingIdResponse saveAirUpdation(PostAirRefParentModel airRefUpdationModel, HttpServletRequest request) {
		List<PosrAirRefChildModel> modelList = new ArrayList<>();
		try {
		int passCount = airRefUpdationModel.getPassangerCount();
		for (int i = 1; i <= passCount; i++) {

			String checkbox = request.getParameter("checkbox" + i);
			if (null != checkbox && checkbox.equalsIgnoreCase("on")) {
				PosrAirRefChildModel model = new PosrAirRefChildModel();
				model.setPassangerNo(Integer.parseInt(request.getParameter("passangerNo" + i)));
				model.setPassangerName(request.getParameter("passangerName" + i));
				model.setNewCancellationTaxId(request.getParameter("cancellationTaxId" + i));
				model.setNewCreditNoteNo(request.getParameter("newCreditNoteNo" + i));
				String inDate = request.getParameter("newCreditNoteDate" + i);
				Date date = CommonUtil.formatString(inDate, "dd/MM/yyyy");
				model.setNewCreditNoteDate(date);
				model.setNewTotalRefund(Double.valueOf(request.getParameter("newTotalRefund" + i)));
				model.setNewBaseFare(Double.valueOf(request.getParameter("newBaseFare" + i)));
				model.setNewCanYq(Double.valueOf(request.getParameter("newCanYq" + i)));
				model.setNewPsfFee(Double.valueOf(request.getParameter("newPsfFee" + i)));
				model.setNewTax(Double.valueOf(request.getParameter("newTax" + i)));
				model.setNewCanAirline(Double.valueOf(request.getParameter("newCanAirline" + i)));
				model.setNewOtherTaxOnSegment(Double.valueOf(request.getParameter("newOtherTaxOnSegment" + i)));
				model.setNewOtherTax(Double.valueOf(request.getParameter("newOtherTax" + i)));
				model.setNewCgstTax(Double.valueOf(request.getParameter("newCgstTax" + i)));
				model.setNewSgstTax(Double.valueOf(request.getParameter("newSgstTax" + i)));
				model.setNewIgstTax(Double.valueOf(request.getParameter("newIgstTax" + i)));

				model.setNewCanSsrAmount(Double.valueOf(request.getParameter("newCanSsrAmount" + i)));
				model.setNewCanEducess(Double.valueOf(request.getParameter("newCanEducess" + i)));

				model.setNewCanHigherEducess(Double.valueOf(request.getParameter("newCanHigherEducess" + i)));

				model.setNewCanRxChargeAirline(Double.valueOf(request.getParameter("newCanRxChargeAirline" + i)));
				model.setNewCanRxPenaltyCharge(Double.valueOf(request.getParameter("newCanRxPenaltyCharge" + i)));
				model.setNewEducessOnAirLine(Double.valueOf(request.getParameter("newEducessOnAirLine" + i)));

				model.setNewHigherEducessOnAirLine(
						Double.valueOf(request.getParameter("newHigherEducessOnAirLine" + i)));
				model.setNewCanServiceTax(Double.valueOf(request.getParameter("newCanServiceTax" + i)));

				modelList.add(model);
			}
		}
		airRefUpdationModel.setRefundHistPassRows(modelList);
		DODLog.info(LogConstant.AIR_REFUND_UPDATION_LOG_FILE, AirRefUpdationService.class,
				"SAVE MODEL AIR REFUND UPDATION" + airRefUpdationModel);

		
			String url = PcdaConstant.AIR_BOOKING_UPDATION;
			BookingIdResponse idResponse = restTemplate.postForObject(url + "/saveAirRefundUpdation",
					airRefUpdationModel, BookingIdResponse.class);
			if (idResponse != null && idResponse.getErrorCode() == 200) {
				DODLog.info(LogConstant.AIR_REFUND_UPDATION_LOG_FILE, AirRefUpdationService.class," AIR REFUND Response :: "+idResponse);
				return idResponse;
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, AirRefUpdationService.class, LogConstant.AIR_REFUND_UPDATION_LOG_FILE);
		}
		
		return new BookingIdResponse();
	}

}
