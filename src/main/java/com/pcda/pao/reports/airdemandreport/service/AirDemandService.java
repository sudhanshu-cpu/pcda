package com.pcda.pao.reports.airdemandreport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.itextpdf.text.Font;
import com.pcda.pao.reports.airdemandreport.model.PaoAirDmdStatusReportResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class AirDemandService {

	@Autowired
	RestTemplate restTemplate;
	
	static Font headFont = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD);

	public PaoAirDmdStatusReportResponse getAirDmdStReport(String groupId) {
		PaoAirDmdStatusReportResponse paoResponse=new PaoAirDmdStatusReportResponse();
		try {
			String url = PcdaConstant.REPORT_SERVICE_URL_ADMIN + "/getPenSettAirDmdRpt?payAccountOffice=" + groupId;
			paoResponse = restTemplate.getForObject(url, PaoAirDmdStatusReportResponse.class);
		} catch (Exception e) {
			
			DODLog.printStackTrace(e, AirDemandService.class, LogConstant.COMMON_REPORT);
		}
		return paoResponse;

	}
	

	
}
