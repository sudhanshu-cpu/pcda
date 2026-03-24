package com.pcda.mb.adduser.bulktransferin.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.UserStringResponse;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;
import com.pcda.mb.adduser.bulktransferin.model.BulkTransferInUserDetailsModel;
import com.pcda.mb.adduser.bulktransferin.model.PostBulkTransferInModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BulkTransferInService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	MasterServices masterServices;

	@Autowired
	private OfficesService officesService;

	public Optional<OfficeModel> getUnitByUserId(BigInteger userId) {
		return officesService.getOfficeByUserId(userId);

	}

	// -------------Save Unit Movement ---------------------------------//
	public UserStringResponse saveUnitMovement(PostBulkTransferInModel postBulkTransferInModel) {
		UserStringResponse bulkTransferInResponse = new UserStringResponse();
		try {
			 String url= PcdaConstant.TRANSFER_SERVICE_URL;
			
			ResponseEntity<UserStringResponse> response = restTemplate.postForEntity(url + "/saveBulkTransferIn",
					postBulkTransferInModel, UserStringResponse.class);
			bulkTransferInResponse = response.getBody();
		} catch (Exception e) {
			DODLog.printStackTrace(e, BulkTransferInService.class, LogConstant.TRANSFER_IN_LOG_FILE);

		}
		DODLog.info(LogConstant.TRANSFER_IN_LOG_FILE, BulkTransferInService.class, "bulkTransferInResponse:: " +bulkTransferInResponse);
		return bulkTransferInResponse;
	}

	public String validateFormField(PostBulkTransferInModel postBulkTransferInModel) {
		String message = "OK";

		if (postBulkTransferInModel.getGroupId() == null || postBulkTransferInModel.getGroupId().isEmpty()) {

			return "Group Id Can Not Be Empity";
		} else if (postBulkTransferInModel.getUnitRailDutyNrs() == null
				|| postBulkTransferInModel.getUnitRailDutyNrs().isEmpty()) {

			return "Duty Station NRS Can Not Be Empity";
		} else if (postBulkTransferInModel.getUnitMoveAuthority() == null
				|| postBulkTransferInModel.getUnitMoveAuthority().isEmpty()) {

			return "Authority Number Can Not Be Empity";
		} else if (postBulkTransferInModel.getUnitDutyStnNa() == null
				|| postBulkTransferInModel.getUnitDutyStnNa().isEmpty()) {

			return "Duty Station NAP Can Not Be Empity";
		} else if (postBulkTransferInModel.getUnitRelocationDate() == null
				|| postBulkTransferInModel.getUnitDutyStnNa().isEmpty()) {

			return "Authority Date Can Not Be Empity";
		} 
		
		if(postBulkTransferInModel.getUserDetalsModel() == null) {
			
			return "Kindly Add At Least One Personal Number";
		}
		if (postBulkTransferInModel.getUserDetalsModel() != null
				&& postBulkTransferInModel.getUserDetalsModel().isEmpty()) {
			for (BulkTransferInUserDetailsModel model : postBulkTransferInModel.getUserDetalsModel()) {

				if (model.getUserId() == null || model.getUserId().isEmpty()) {
					return "UserId Can Not Be Empty";
		}
			}
		}

		return message;
	}

	public void initialiseFormField(HttpServletRequest request,PostBulkTransferInModel postBulkTransferInModel) {
		
		postBulkTransferInModel.setUnitDutyStnNa(request.getParameter("unitDutyStnNa"));
		postBulkTransferInModel.setUnitRailDutyNrs(request.getParameter("unitRailDutyNrs"));
		postBulkTransferInModel.setUnitMoveAuthority(request.getParameter("unitMoveAuthority"));
		postBulkTransferInModel.setUnitRelocationDateStr(request.getParameter("unitRelocationDateStr"));
		
		String[] userIds= request.getParameterValues("userId");
		//String [] unitids=request.getParameterValues("unitId");
		
		List<BulkTransferInUserDetailsModel> userDetalsModel= new ArrayList<>();
		
		for(int i=0; i<userIds.length;i++) {
			BulkTransferInUserDetailsModel detailsBean= new BulkTransferInUserDetailsModel();
			detailsBean.setUserId(userIds[i]);
			//detailsBean.setUnitId(unitids[i]);
			userDetalsModel.add(detailsBean);
			
		}
		postBulkTransferInModel.setUserDetalsModel(userDetalsModel);
		
		DODLog.info(LogConstant.TRANSFER_IN_LOG_FILE, BulkTransferInService.class, "postBulkTransferInModel:: " +postBulkTransferInModel);
	}
}
