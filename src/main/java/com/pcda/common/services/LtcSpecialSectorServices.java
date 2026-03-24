package com.pcda.common.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.LtcSpecialSector;
import com.pcda.common.model.LtcSpecialSectorResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class LtcSpecialSectorServices {

	@Autowired
	private RestTemplate template;

	public Optional<LtcSpecialSector> getLtcSpecialSectorData(String sectorId) {
		
		DODLog.info(LogConstant.COMMON_LOG, LtcSpecialSectorServices.class, "get Ltc Special Sector Data sectorId: " + sectorId);
		
		LtcSpecialSector ltcSpecialSector=null;
try {
		if(null!=sectorId && !sectorId.isBlank()) { 
			ResponseEntity<LtcSpecialSectorResponse> response = template.exchange(
					PcdaConstant.AIR_BASE_URL + "/LtcSpecialSector/getLtcSpecialSectorData/" + sectorId, HttpMethod.GET, null,
					new ParameterizedTypeReference<LtcSpecialSectorResponse>() {});
	
			LtcSpecialSectorResponse  sectorResponse = response.getBody();
			
			if (null != sectorResponse && sectorResponse.getErrorCode() == 200 && null != sectorResponse.getResponse()) {
				ltcSpecialSector= sectorResponse.getResponse();
			}
		}
}catch (Exception e) {
	DODLog.printStackTrace(e, LtcSpecialSectorServices.class, LogConstant.COMMON_LOG);
   }
		return Optional.ofNullable(ltcSpecialSector);
	}
}
