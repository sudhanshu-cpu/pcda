package com.pcda.adg.reports.claimstatusreport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.adg.reports.claimstatusreport.model.ClaimDataRequest;
import com.pcda.adg.reports.claimstatusreport.model.ClaimDataResponse;
import com.pcda.adg.reports.claimstatusreport.model.ClaimStatusDataResBean;
import com.pcda.adg.reports.claimstatusreport.model.ClaimStatusResponse;
import com.pcda.common.model.Category;
import com.pcda.common.services.CategoryServices;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ClaimStatusReportService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	 private CategoryServices categoryservices;

		public Map<String, String> getCategoryBasedOnService(String serviceId) {

			List<Category> serviceCategoryList = categoryservices.getCategories(serviceId);

			Map<String, String> serviceCategoryMap = serviceCategoryList.stream()
					.collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));

			return serviceCategoryMap;
		}

	// validation check
	public String validationFormBean(ClaimDataRequest requestData) {
		String msg = "OK";
		if ((requestData.getFromDate() == null || requestData.getFromDate().isEmpty())
				&& (requestData.getToDate() == null || requestData.getToDate().isEmpty())) {
			msg = "Please enter valid inputs";
		}
		return msg;
	}

//GET DATA for ALL
	public List<ClaimStatusDataResBean> getClaimStatusRptData(ClaimDataRequest requestData) {

		List<ClaimStatusDataResBean> claimDataResponse = new ArrayList<>();

		try {
			String url = PcdaConstant.ADG_REPORTS_BASE_URL + "/adgMov/v1/getClaimStatusReport";
			ResponseEntity<ClaimStatusResponse> postForEntity = restTemplate.postForEntity(url, requestData,
					ClaimStatusResponse.class);
			ClaimStatusResponse claimResponse = new ClaimStatusResponse();
			claimResponse = postForEntity.getBody();

			if (claimResponse != null && claimResponse.getErrorCode() == 200
					&& claimResponse.getResponseList() != null) {

				claimDataResponse = claimResponse.getResponseList();

			}
			DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, ClaimStatusReportService.class,
					"[getClaimStatusData] : ClaimStatusDataDetailsrESPONMSE::::: " + claimDataResponse);

		} catch (Exception e) {
			DODLog.printStackTrace(e, ClaimStatusReportService.class, LogConstant.ADG_REPORTS_LOG_FILE);
		}

		return claimDataResponse;
	}



	public ClaimDataResponse getviewClaimInformation(ClaimStatusDataResBean requestBean) {

		ClaimDataResponse response = new ClaimDataResponse();
		try {
			String url = PcdaConstant.ADG_REPORTS_BASE_URL + "/adgMov/v1/getClaimViewData";
			ResponseEntity<ClaimDataResponse> responseEntity = restTemplate.postForEntity(url, requestBean,
					ClaimDataResponse.class);
			response = responseEntity.getBody();
			DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, ClaimStatusReportService.class,
					"Claim View RESPONSE Model " + response);

		} catch (Exception e) {
			DODLog.printStackTrace(e, ClaimStatusReportService.class, LogConstant.ADG_REPORTS_LOG_FILE);
		}

		return response;
	}
}
