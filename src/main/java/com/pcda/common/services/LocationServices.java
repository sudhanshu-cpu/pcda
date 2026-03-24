package com.pcda.common.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.Location;
import com.pcda.common.model.LocationResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class LocationServices {

	@Autowired
	private RestTemplate template;

	public Optional<Location> getLocationById(String locationId) {
		DODLog.info(LogConstant.COMMON_LOG, LocationServices.class, "Get Service with location id: " + locationId);

		Location location = null;
		try {
			LocationResponse locationResponse = template.getForObject(
					PcdaConstant.MASTER_BASE_URL + "/location/getLocation/" + locationId, LocationResponse.class);
		
			if (locationResponse != null && locationResponse.getErrorCode() == 200 && locationResponse.getResponse() != null) {
				location = locationResponse.getResponse();
			}

		} catch(Exception e) {
			
			DODLog.printStackTrace(e, LocationServices.class, LogConstant.COMMON_LOG);
		}
		return Optional.ofNullable(location);
	}

}
