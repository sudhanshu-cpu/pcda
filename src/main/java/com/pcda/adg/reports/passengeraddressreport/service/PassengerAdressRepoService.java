package com.pcda.adg.reports.passengeraddressreport.service;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.adg.reports.passengeraddressreport.model.GetPassengerAddresModel;
import com.pcda.adg.reports.passengeraddressreport.model.PassengerAddreResponse;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service

public class PassengerAdressRepoService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OfficesService officesService;

	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {
		return officesService.getOfficeByUserId(userId);
	}

	// Get Details
	public GetPassengerAddresModel getPassengerDtlsByPnr(String pnrNo) {
		DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, PassengerAdressRepoService.class,"#### pnrNo ##" + pnrNo);
		GetPassengerAddresModel getPassengerAddresModel = null;

		try {

			ResponseEntity<PassengerAddreResponse> entity = restTemplate.exchange(
					PcdaConstant.ADG_REPORTS_BASE_URL + "/adgMov/v1/getAdgMovPassAddReport?pnrNo=" + pnrNo,
					HttpMethod.GET, null, new ParameterizedTypeReference<PassengerAddreResponse>() {
					});
			PassengerAddreResponse response = entity.getBody();

			if (null != response && response.getErrorCode() == 200 && response.getResponse() != null) {
				getPassengerAddresModel = response.getResponse();

				getPassengerAddresModel.setJourneyDateFormat(
						CommonUtil.formatDate(getPassengerAddresModel.getJourneyDate(), "dd-MM-yyyy"));
			}

		
		} catch (Exception e) {
			DODLog.printStackTrace(e, PassengerAdressRepoService.class, LogConstant.ADG_REPORTS_LOG_FILE);
		}
		DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, PassengerAdressRepoService.class,
				"#### getPassengerAddresModel ##" + getPassengerAddresModel);

		return getPassengerAddresModel;
	}

	

}
