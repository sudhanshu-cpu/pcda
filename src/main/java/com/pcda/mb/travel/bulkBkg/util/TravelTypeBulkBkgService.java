package com.pcda.mb.travel.bulkBkg.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.TravelType;
import com.pcda.common.model.TravelTypeResponse;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class TravelTypeBulkBkgService {
	
	@Autowired
	private RestTemplate template;
	
	public TravelType getTravelType(String travelType) {
		DODLog.info(LogConstant.COMMON_LOG, TravelTypeServices.class, "Get travel Type with travelType: " + travelType);

		TravelType type= null;
		
		try {
		ResponseEntity<TravelTypeResponse> responseEntity = template.getForEntity(PcdaConstant.MASTER_BASE_URL + "/traveltype/getTravelType/"+travelType, TravelTypeResponse.class);
		
		TravelTypeResponse travelTypeResponse = responseEntity.getBody();

		if (null != travelTypeResponse && (travelTypeResponse.getErrorCode() == 200) && null != travelTypeResponse.getResponse()) {
			type=travelTypeResponse.getResponse();
		}
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelTypeServices.class, LogConstant.COMMON_LOG);
		}

		return type;
	}

}
