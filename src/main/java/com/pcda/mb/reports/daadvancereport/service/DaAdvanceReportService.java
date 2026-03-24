package com.pcda.mb.reports.daadvancereport.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.mb.reports.daadvancereport.model.DaAdvanceInputModel;
import com.pcda.mb.reports.daadvancereport.model.DaAdvanceReportModelResponse;
import com.pcda.mb.reports.daadvancereport.model.GetDaAdvanceModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class DaAdvanceReportService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OfficesService officesService;

	// Get GroupId
	public Optional<OfficeModel> getOfficesByGroupId(BigInteger userId) {

		return officesService.getOfficeByUserId(userId);
	}

//Method to Get AdvanceReport
	public List<GetDaAdvanceModel> getAdvanceDetails(DaAdvanceInputModel advanceModel, String groupId) {
		List<GetDaAdvanceModel> advanceModels = new ArrayList<>();
		String url = null;
		

		try {
			

			url = PcdaConstant.COMMON_REPORT_URL + "/getDAAdvanceReport?unitOffice={unitOffice}";

			Map<String, Object> params = new HashMap<>();
			params.put("unitOffice", groupId);

			if (advanceModel.getRequestID() != null && !advanceModel.getRequestID().isEmpty()) {
				url = url + "&requestID={requestID}";
				params.put("requestID", advanceModel.getRequestID());
			}

			if (advanceModel.getPersonalNo() != null && !advanceModel.getPersonalNo().isEmpty()) {
				url = url + "&personalNo={personalNo}";
				params.put("personalNo", advanceModel.getPersonalNo());
			}
			if (advanceModel.getTravelMode() != null) {
				url = url + "&travelMode={travelMode}";
				params.put("travelMode", advanceModel.getTravelMode());
			}

			if (advanceModel.getAdvanceType() != null) {
				url = url + "&advanceType={advanceType}";
				params.put("advanceType", advanceModel.getAdvanceType());
			}
			
			DaAdvanceReportModelResponse response = restTemplate.getForObject(url, DaAdvanceReportModelResponse.class,
					params);

			if (response != null && response.getErrorCode() == 200) {
				advanceModels = response.getResponseList();
			}
		
		} catch (Exception e) {
			DODLog.printStackTrace(e, DaAdvanceReportService.class, LogConstant.DA_ADVANCE_REPORT);
		}
		DODLog.info(LogConstant.DA_ADVANCE_REPORT, DaAdvanceReportService.class, "[getAdvanceDetails] ## Response  advanceModels " + advanceModels );
		return advanceModels;
	}

}
