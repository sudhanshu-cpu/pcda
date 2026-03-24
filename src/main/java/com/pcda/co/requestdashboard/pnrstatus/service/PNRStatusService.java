package com.pcda.co.requestdashboard.pnrstatus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.co.requestdashboard.pnrstatus.model.PnrEnquiryResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class PNRStatusService {

	@Autowired
	private RestTemplate restTemplate;

	public PnrEnquiryResponse getPNRStatus(String pnrNo) {
		DODLog.info(LogConstant.PNR_STATUS_LOG, PNRStatusService.class, "Get Status for pnr: " + pnrNo);
		PnrEnquiryResponse response = null;
		try {
		 response = restTemplate.postForObject(PcdaConstant.RAIL_BOOK_SERVICE + "/getPNRStatus/" + pnrNo, null, PnrEnquiryResponse.class);
		DODLog.info(LogConstant.PNR_STATUS_LOG, PNRStatusService.class, "Response for pnr: " + response);
		}catch(Exception e){
			DODLog.printStackTrace(e, PNRStatusService.class, LogConstant.PNR_STATUS_LOG);
		}
		DODLog.info(LogConstant.PNR_STATUS_LOG, PNRStatusService.class, " response: " +  response );
		return response;
	}

}
