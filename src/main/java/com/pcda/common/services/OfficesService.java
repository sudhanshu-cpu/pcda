package com.pcda.common.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.OfficesResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;
import com.pcda.util.Status;

@Service
public class OfficesService {

	@Autowired
	private RestTemplate template;

	public List<OfficeModel> getOffices(String officeType, String approvalType) {
		DODLog.info(LogConstant.COMMON_LOG, OfficesService.class, "Get " + officeType + " with approval type " + approvalType);

		List<OfficeModel> officesList = null;
		try {
		StringJoiner joiner = new StringJoiner("/").add(PcdaConstant.OFFICE_BASE_URL).add("allOffices").add(officeType).add(approvalType);

		ResponseEntity<OfficesResponse> responseEntity = template.exchange(joiner.toString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<OfficesResponse>() {});
		OfficesResponse officesResponse = responseEntity.getBody();

		if (null != officesResponse && (officesResponse.getErrorCode() == 200) && null != officesResponse.getResponseList()) {
			officesList = officesResponse.getResponseList();
			officesList=officesList.stream().filter(e->e.getStatus().equals(Status.ON_LINE)).toList();
			DODLog.info(LogConstant.COMMON_LOG, OfficesService.class, " officesList " + officesList.size());
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, OfficesService.class, LogConstant.COMMON_LOG);
		}
		
		return officesList;
	}

	// Get Office By user id
	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {
		DODLog.info(LogConstant.COMMON_LOG, OfficesService.class, "Get office by user id: " + userId);

		OfficeModel officeModel = null;
		try {
		
		OfficesResponse officesResponse = template.getForObject(PcdaConstant.OFFICE_BASE_URL + "/getOfficeByUserId/" + userId, OfficesResponse.class);

		if (null != officesResponse && (officesResponse.getErrorCode() == 200) && null != officesResponse.getResponse()) {
			officeModel = officesResponse.getResponse();
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, OfficesService.class, LogConstant.COMMON_LOG);
		}
		return Optional.ofNullable(officeModel);
	}

	// Get Office By group id
	public Optional<OfficeModel> getOfficeByGroupId(String groupId) {
		DODLog.info(LogConstant.COMMON_LOG, OfficesService.class, "Get office by group id: " + groupId);

		OfficeModel officeModel = null;
		try {
		OfficesResponse officesResponse = template.getForObject(PcdaConstant.OFFICE_BASE_URL + "/getOffice/" + groupId, OfficesResponse.class);

		if (null != officesResponse && (officesResponse.getErrorCode() == 200) && null != officesResponse.getResponse()) {
			officeModel = officesResponse.getResponse();
		}
	}catch(Exception e) {
		DODLog.printStackTrace(e, OfficesService.class, LogConstant.COMMON_LOG);
	}
		return Optional.ofNullable(officeModel);
		
	}

}
	
