package com.pcda.mb.requestdashboard.pnrstatus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.requestdashboard.pnrstatus.model.PnrEnquiryResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class TicketPNRStatusService {

	@Autowired
	private RestTemplate restTemplate;

	public PnrEnquiryResponse getPNRStatus(String pnrNo) {
		
		PnrEnquiryResponse response = restTemplate.postForObject(PcdaConstant.RAIL_BOOK_SERVICE + "/getPNRStatus/" + pnrNo, null, PnrEnquiryResponse.class);
		DODLog.info(LogConstant.PNR_STATUS_LOG, TicketPNRStatusService.class, "Response for pnr: " + response);
		return response;
	}

}
