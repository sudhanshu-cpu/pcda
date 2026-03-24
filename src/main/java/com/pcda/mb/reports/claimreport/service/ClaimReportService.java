package com.pcda.mb.reports.claimreport.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.TravelType;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.mb.reports.claimreport.model.ClaimReportInputModel;
import com.pcda.mb.reports.claimreport.model.ClaimReportModel;
import com.pcda.mb.reports.claimreport.model.ClaimReportResponseModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ClaimReportService {

	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private TravelTypeServices travelTypeServices;
	
	@Autowired
	private OfficesService officeServices;
	
	
	     //Method to Get Travel ID
		public List<ClaimReportModel> getClaimReport(ClaimReportInputModel claimReportInputModel) {
			
			DODLog.info(LogConstant.CLAIM_REPORT, ClaimReportService.class,"[getClaimReport] ## claimReportInputModel : " + claimReportInputModel);
			List<ClaimReportModel> claimReportModelList=new ArrayList<>();
		
			
			try {		
				if( claimReportInputModel.getGroupId() != null) {
					String url =PcdaConstant.COMMON_REPORT_URL + "/getClaimReport?unitOffice={unitOffice}&claimId={claimId}&personalNo={personalNo}&travelType={travelType}&accountOffice={accountOffice}";
				
				Map<String, String> params = new HashMap<>();
				params.put("unitOffice",claimReportInputModel.getGroupId() );
				params.put("claimId", claimReportInputModel.getClaimId());
				params.put("personalNo",claimReportInputModel.getPersonalNo());
				params.put("travelType",  claimReportInputModel.getTravelType());
				params.put("accountOffice",claimReportInputModel.getAccountOffice());
				
				ClaimReportResponseModel responseList = restTemplate.getForObject(url, ClaimReportResponseModel.class, params);
				
				if(responseList!=null && responseList.getErrorCode()==200 && null!=responseList.getResponseList()) {
				claimReportModelList=responseList.getResponseList();
				}
				}
			} catch (Exception e) {
				DODLog.printStackTrace(e, ClaimReportService.class, LogConstant.CLAIM_REPORT);
			}
			DODLog.info(LogConstant.CLAIM_REPORT, ClaimReportService.class,"[getClaimReport] ## claimReportModelList : " + claimReportModelList.size());
			return claimReportModelList ;		
		}
	
		
		// Method to Get All TravelType
		public List<TravelType> getAllTravelType(Integer approvalType) {
			try {
			List<TravelType> travelTypeList = travelTypeServices.getAllTravelType(approvalType);
		
			return travelTypeList;
			} catch (Exception e) {
				DODLog.printStackTrace(e, ClaimReportService.class, LogConstant.CLAIM_REPORT);
				return Collections.emptyList();
			}
		}

				
		// Method that returns all PAO which is 1  [optimized method]
		public Map<String, String> getAllPao() {
		    try {
			List<OfficeModel> paoList = officeServices.getOffices("PAO", "1");

			if (!paoList.isEmpty()) {
		            Map<String, String> paoMap = paoList.stream()
		                    .collect(Collectors.toMap(OfficeModel::getGroupId, OfficeModel::getName));
		            
		            Map<String, String> sortedPAOMap = paoMap.entrySet().stream()
		                    .sorted(Map.Entry.comparingByValue())
		                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		            
		            DODLog.info(LogConstant.CLAIM_REPORT, ClaimReportService.class, "[getAllPao] ## paoList Size:" + paoList.size());
		            return sortedPAOMap;
		        } else {
		            return new HashMap<>();
			}
		    } catch (Exception e) {
		        DODLog.printStackTrace(e, ClaimReportService.class, LogConstant.CLAIM_REPORT);
			return new HashMap<>();
		}
		}	
		

		

		// Get GroupId
		public Optional<OfficeModel> getOfficesByGroupId(BigInteger userId) {

			return officeServices.getOfficeByUserId(userId);
		}

}
