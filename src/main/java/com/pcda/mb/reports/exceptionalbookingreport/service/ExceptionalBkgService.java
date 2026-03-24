package com.pcda.mb.reports.exceptionalbookingreport.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.reports.exceptionalbookingreport.model.ExcepBkgDetailsResponseModel;
import com.pcda.mb.reports.exceptionalbookingreport.model.ExceptionalBkgReportPost;
import com.pcda.mb.reports.exceptionalbookingreport.model.ExceptionalRepostResponseModel;
import com.pcda.mb.reports.exceptionalbookingreport.model.GetExcepBkgDetailsModel;
import com.pcda.mb.reports.exceptionalbookingreport.model.GetExceptionalReportData;
import com.pcda.mb.reports.exceptionalbookingreport.model.GetUnitPrsnlExpReportData;
import com.pcda.mb.reports.exceptionalbookingreport.model.UnitPrsnlExcepBkgRptPost;
import com.pcda.mb.reports.exceptionalbookingreport.model.UnitPrsnlExpReportResponseModel;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ExceptionalBkgService {
	
	@Autowired
	private RestTemplate restTemplate;

	public List<GetExceptionalReportData> getExceptionalBkgReport(ExceptionalBkgReportPost exceptionalBkgReportPost) {
		DODLog.info(LogConstant.RAIL_REPORT, ExceptionalBkgService.class,
				"[exceptionalBkgReportPost] : exceptionalBkgReportPost " + exceptionalBkgReportPost);
		ExceptionalRepostResponseModel excepResponseData = new ExceptionalRepostResponseModel();
		String url = null;

		if (exceptionalBkgReportPost.getPersonalNo() != null && !exceptionalBkgReportPost.getPersonalNo().isBlank() ) {

			exceptionalBkgReportPost
					.setPersonalNo(CommonUtil.getDecryptText("Hidden Pass", exceptionalBkgReportPost.getPersonalNo()));
		}
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ExceptionalBkgReportPost> requestEntity = new HttpEntity<>(exceptionalBkgReportPost, headers);
			url = PcdaConstant.COMMON_REPORT_URL + "/getExceptionalBkgData";
			
			ResponseEntity<ExceptionalRepostResponseModel> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, new ParameterizedTypeReference<ExceptionalRepostResponseModel>() {
					});
			excepResponseData = responseEntity.getBody();

			if (excepResponseData != null && excepResponseData.getErrorCode() == 200
					 && !excepResponseData.getResponseList().isEmpty()) {
				return excepResponseData.getResponseList();

			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, ExceptionalBkgService.class, LogConstant.RAIL_REPORT);
		}
		return new ArrayList<>();

	}

	public GetExcepBkgDetailsModel getExpcepBkgDtlsById(String bookingId) {
		
		GetExcepBkgDetailsModel bookingdModel = null;

	
		String url = PcdaConstant.COMMON_REPORT_URL + "/getRailTicketBookingDetails?bookingId=" + bookingId;

		try {
			DODLog.info(LogConstant.RAIL_REPORT, ExceptionalBkgService.class,
					"[getrailTicketsBookindDtls] ## bookingId : " + bookingId);

			ExcepBkgDetailsResponseModel response = restTemplate.getForObject(url,
					ExcepBkgDetailsResponseModel.class);

			if (response != null && response.getErrorCode() == 200) {
				bookingdModel = response.getResponse();
				Collections.sort(bookingdModel.getTicketPassangerDetails());
	}

		} catch (Exception e) {
			DODLog.printStackTrace(e, ExceptionalBkgService.class, LogConstant.RAIL_REPORT);
		}
		

		return bookingdModel;
	}

	public List<GetUnitPrsnlExpReportData> getUnitPrsnlExceptionalBkgReport(
			UnitPrsnlExcepBkgRptPost exceptionalBkgReportPost) {
		DODLog.info(LogConstant.RAIL_REPORT, ExceptionalBkgService.class,
				"[getUnitPrsnlExceptionalBkgReport] : UnitPrsnlExcepBkgRptPost " + exceptionalBkgReportPost);
		UnitPrsnlExpReportResponseModel excepResponseData = new UnitPrsnlExpReportResponseModel();
		String url = null;

		if (exceptionalBkgReportPost.getPersonalNo() != null && !exceptionalBkgReportPost.getPersonalNo().isBlank() ) {

			exceptionalBkgReportPost
					.setPersonalNo(CommonUtil.getDecryptText("Hidden Pass", exceptionalBkgReportPost.getPersonalNo()));
		}
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<UnitPrsnlExcepBkgRptPost> requestEntity = new HttpEntity<>(exceptionalBkgReportPost, headers);
			url = PcdaConstant.COMMON_REPORT_URL + "/getUnitPrsnlExceptionalBkgData";
			
			ResponseEntity<UnitPrsnlExpReportResponseModel> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, new ParameterizedTypeReference<UnitPrsnlExpReportResponseModel>() {
					});
			excepResponseData = responseEntity.getBody();

			if (excepResponseData != null && excepResponseData.getErrorCode() == 200
					 && !excepResponseData.getResponseList().isEmpty()) {
				return excepResponseData.getResponseList();

			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, ExceptionalBkgService.class, LogConstant.RAIL_REPORT);
		}
		return new ArrayList<>();

	}

}
