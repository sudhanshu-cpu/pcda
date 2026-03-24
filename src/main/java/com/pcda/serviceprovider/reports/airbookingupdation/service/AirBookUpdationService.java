package com.pcda.serviceprovider.reports.airbookingupdation.service;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.serviceprovider.reports.airbookingupdation.model.BookingIdResponse;
import com.pcda.serviceprovider.reports.airbookingupdation.model.PostChildAirUpdationModel;
import com.pcda.serviceprovider.reports.airbookingupdation.model.PostParentAirUpdationModel;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;



@Service
public class AirBookUpdationService {

	@Autowired 
	private RestTemplate restTemplate;
	
public BookingIdResponse getDataFromBkgId(String bookingId,Integer serviceProvider){
	DODLog.info(LogConstant.AIR_BOOKING_UPDATION_LOG_FILE, AirBookUpdationService.class," BKG ID "+bookingId +" serviceProvider "+serviceProvider);	
	
	BookingIdResponse response=new BookingIdResponse();
 try {
	String url = PcdaConstant.AIR_BOOKING_UPDATION + "/getAirTicketData?bookingId="+bookingId+"&serviceProvider="+serviceProvider;
    response=restTemplate.getForObject(url, BookingIdResponse.class);
	}
	
	catch(Exception e) {
		DODLog.printStackTrace(e, AirBookUpdationService.class, LogConstant.AIR_BOOKING_UPDATION_LOG_FILE);
	}
 DODLog.info(LogConstant.AIR_BOOKING_UPDATION_LOG_FILE, AirBookUpdationService.class,"RESPONSE MODEL MODEL FROM BKG ID"+response);	
	return response;
}


public BookingIdResponse saveAirUpdation(PostParentAirUpdationModel airUpdationModel,HttpServletRequest request) {
	List<PostChildAirUpdationModel> modelList = new ArrayList<>();
	
	try {
	int passCount = airUpdationModel.getPassCount();
	for(int i=1;i<=passCount;i++) {
		
		String checkbox = request.getParameter("checkbox"+i);
		if(null != checkbox   && checkbox.equalsIgnoreCase("on")){
			PostChildAirUpdationModel model = new PostChildAirUpdationModel();
			model.setPassangerNo(Integer.parseInt(request.getParameter("passangerNo"+i)));
			model.setPassangerName(request.getParameter("passangerName"+i));
			model.setNewOnwardPaxId(request.getParameter("newOnwardPaxId"+i));
			model.setNewInvoiceNo(request.getParameter("newInvoiceNo"+i));
			String inDate = request.getParameter("newInvoiceDate"+i);
			Date date = CommonUtil.formatString(inDate, "dd/MM/yyyy");
			model.setNewInvoiceDate(date);
			model.setNewTotalInvoice(Double.valueOf(request.getParameter("newTotalInvoice"+i)));
			model.setNewBaseFare(Double.valueOf(request.getParameter("newBaseFare"+i)));
			model.setNewTax(Double.valueOf(request.getParameter("newTax"+i)));
			model.setNewFuelCharge(Double.valueOf(request.getParameter("newFuelCharge"+i)));
			model.setNewPsfFee(Double.valueOf(request.getParameter("newPsfFee"+i)));
			model.setNewOtherTax(Double.valueOf(request.getParameter("newOtherTax"+i)));
			model.setNewCgstTax(Double.valueOf(request.getParameter("newCgstTax"+i)));
			model.setNewSgstTax(Double.valueOf(request.getParameter("newSgstTax"+i)));
			model.setNewIgstTax(Double.valueOf(request.getParameter("newIgstTax"+i)));
			model.setNewGst(Double.valueOf(request.getParameter("newGst"+i)));
			modelList.add(model);
		}
	}
	airUpdationModel.setPassRows(modelList);
	DODLog.info(LogConstant.AIR_BOOKING_UPDATION_LOG_FILE, AirBookUpdationService.class,"SAVE MODEL AIR BOOKING UPDATION"+airUpdationModel);	
	
	
		String url = PcdaConstant.AIR_BOOKING_UPDATION;
		BookingIdResponse idResponse = restTemplate.postForObject(url+"/saveAirBookingUpdation", airUpdationModel,BookingIdResponse.class);
		if(idResponse!=null && idResponse.getErrorCode()==200) {
			return idResponse;
		}
	}catch(Exception e ) {
		DODLog.printStackTrace(e, AirBookUpdationService.class, LogConstant.AIR_BOOKING_UPDATION_LOG_FILE);
	}
	
	
	return new BookingIdResponse();
}
	
}
