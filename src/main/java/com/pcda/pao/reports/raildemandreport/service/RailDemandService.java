package com.pcda.pao.reports.raildemandreport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.itextpdf.text.Font;
import com.pcda.pao.reports.raildemandreport.model.ResponsePAOReport;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class RailDemandService {

	@Autowired
	private RestTemplate restTemplate;
	
	static Font headFont = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD);
	
	public ResponsePAOReport getPAOReport(String groupId) {
		ResponsePAOReport paoResponse=new ResponsePAOReport();
		try {
			String url = PcdaConstant.REPORT_SERVICE_URL_ADMIN + "/generatePaoData?payAccountOffice=" +groupId ;
			paoResponse = restTemplate.getForObject(url, ResponsePAOReport.class);
		} catch (Exception e) {		
			 DODLog.printStackTrace(e, RailDemandService.class, LogConstant.COMMON_REPORT);
	}
		return paoResponse;
		
}
	

	
}
