package com.pcda.mb.reports.railirlareport.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.mb.reports.railirlareport.model.RailIrlaReportModel;
import com.pcda.mb.reports.railirlareport.model.RailReportResponseModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class RailIrlaReportService {

	@Autowired
	private RestTemplate restTemplate;
	
	//Method to Get Travel ID
	public List<RailIrlaReportModel> getTravelIdByData(RailIrlaReportModel railReportModel) {
		List<RailIrlaReportModel> railReportList=new ArrayList<>();
		
		DODLog.info(LogConstant.RAIL_IRLA_REPORT, RailIrlaReportService.class, "[getTravelIdByData] ## railReportModel" + railReportModel );
		String url = null;
		try {
			if(railReportModel.getPnrNo() != null && !railReportModel.getPnrNo().trim().equals("") && !railReportModel.getPnrNo().isEmpty()) {
				url = PcdaConstant.COMMON_REPORT_URL + "/getIRLAReport?pnrNumber="+railReportModel.getPnrNo();
			}else {
				url = PcdaConstant.COMMON_REPORT_URL + "/getIRLAReport?personalNo="+railReportModel.getPersonalNo();
			}
			ResponseEntity<RailReportResponseModel> response = restTemplate.exchange(
					     url, HttpMethod.GET,null,
					RailReportResponseModel.class);

			if (HttpStatus.OK.equals(response.getStatusCode())) {
				railReportList = Optional.ofNullable(response.getBody())
						.map(RailReportResponseModel::getResponseList)
						.orElse(Collections.emptyList());
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, RailIrlaReportService.class, LogConstant.RAIL_IRLA_REPORT);
		}
		DODLog.info(LogConstant.RAIL_IRLA_REPORT, RailIrlaReportService.class, "[getTravelIdByData] ## response  railReportModel1" + railReportList.size());
		return railReportList ;
	}
	
	
}
