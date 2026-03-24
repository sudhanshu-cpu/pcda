package com.pcda.mb.adduser.bulktransferinreemployment.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.DODServices;
import com.pcda.common.model.UserStringResponse;
import com.pcda.common.services.MasterServices;
import com.pcda.mb.adduser.bulktransferin.service.BulkTransferInService;
import com.pcda.mb.adduser.bulktransferinreemployment.model.BulkTransferInReEmpUserModel;
import com.pcda.mb.adduser.bulktransferinreemployment.model.PostBulkTransferInReEmpModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BulkTransferInAndreemploymentService {
	
	@Autowired
	private MasterServices masterServices;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public List<DODServices> getTransferInReEmpServices() {
		List<DODServices>	transferInReempServices = masterServices.getServicesByApprovalState("1");

		return transferInReempServices.stream()
				.filter(service -> service.getServiceId().equals("100014")
						&& service.getArmedForces().name().equalsIgnoreCase("YES"))
				.toList();

			}
				

	//-------------Save Unit Movement ---------------------------------//
	public UserStringResponse saveBulkTransferInReEmp(PostBulkTransferInReEmpModel postBulkTransferInModel) {
		UserStringResponse bulkTransferInResponse=new UserStringResponse();
		try {
			String url= PcdaConstant.TRANSFER_SERVICE_URL;
		
		ResponseEntity<UserStringResponse> response = restTemplate.postForEntity(url + "/submitBulkTransferInReEmp", postBulkTransferInModel, UserStringResponse.class);
		bulkTransferInResponse =response.getBody();	
		}catch (Exception e) {   
			DODLog.printStackTrace(e, BulkTransferInService.class, LogConstant.TRANSFER_IN_REEMPLOYEMENT);
			
		}
		return bulkTransferInResponse;
	}

	public void initialiseVariable(HttpServletRequest request,PostBulkTransferInReEmpModel mainBulkTransferInReEmpModel ) {
		
		mainBulkTransferInReEmpModel.setUnitRelocationDateStr(Optional.ofNullable(request.getParameter("unitRelocationDateStr")).orElse(null));
		mainBulkTransferInReEmpModel.setUnitRailDutyNrs(Optional.ofNullable(request.getParameter("unitRailDutyNrs")).orElse(null));
		mainBulkTransferInReEmpModel.setUnitDutyStnNa(Optional.ofNullable(request.getParameter("unitDutyStnNa")).orElse(null));
		mainBulkTransferInReEmpModel.setUnitMoveAuthority(Optional.ofNullable(request.getParameter("unitMoveAuthority")).orElse(null));
		mainBulkTransferInReEmpModel.setGroupId(Optional.ofNullable(request.getParameter("groupId")).orElse(null));
		mainBulkTransferInReEmpModel.setServiceId(Optional.ofNullable(request.getParameter("serviceId")).orElse(null));
		mainBulkTransferInReEmpModel.setCategoryId(Optional.ofNullable(request.getParameter("categoryId")).orElse(null));
		mainBulkTransferInReEmpModel.setRankId(Optional.ofNullable(request.getParameter("rankId")).orElse(null));
		mainBulkTransferInReEmpModel.setRailPayAcOff(Optional.ofNullable(request.getParameter("railPayAcOff")).orElse(null));
		mainBulkTransferInReEmpModel.setAirPayAcOff(Optional.ofNullable(request.getParameter("airPayAcOff")).orElse(null));
		mainBulkTransferInReEmpModel.setLevelId(Optional.ofNullable(request.getParameter("levelId")).orElse(null));
		
		
		
	String[] userIds = request.getParameterValues("userId");
	List<BulkTransferInReEmpUserModel> reEmpBulkTransferuserDetails= new ArrayList<>();
	
	for(int i=0; i<userIds.length; i++) {
		BulkTransferInReEmpUserModel userDetailsBean= new BulkTransferInReEmpUserModel();
		
		userDetailsBean.setUserId(new BigInteger(userIds[i]));
		userDetailsBean.setSosDate(Optional.ofNullable(request.getParameter("sosDate_"+userIds[i])).orElse(null));
		userDetailsBean.setDateOfRetirement(Optional.ofNullable(request.getParameter("dateOfRetirement_"+userIds[i])).orElse(null));
		reEmpBulkTransferuserDetails.add(userDetailsBean);
	}
		
	mainBulkTransferInReEmpModel.setUserDetailsModel(reEmpBulkTransferuserDetails);
		
		
		
		
		
	}

	public String validateBean(PostBulkTransferInReEmpModel postBulkTransferInModel) {
		
		if(postBulkTransferInModel.getUnitRelocationDate()==null || postBulkTransferInModel.getUnitRelocationDate().toString().isEmpty()) {
			return "The Transfer Authority Date Can Not Be Empty";
			
			
		}
		else if(postBulkTransferInModel.getUnitRailDutyNrs()==null || postBulkTransferInModel.getUnitRailDutyNrs().isEmpty()) {
			return "The Nearest Railway Station Can Not Be Empty";
			
		}
		else if(postBulkTransferInModel.getUnitDutyStnNa()==null || postBulkTransferInModel.getUnitDutyStnNa().isEmpty()) {
			return "The Nearest Airport Can Not Be Empty";
			
		}
		else if(postBulkTransferInModel.getUnitMoveAuthority()==null || postBulkTransferInModel.getUnitMoveAuthority().isEmpty()) {
			return "The Transfer Authority No Can Not Be Empty";
			
		}
		else if(postBulkTransferInModel.getGroupId()==null || postBulkTransferInModel.getGroupId().isEmpty()) {
			return	"The Group Id Can Not Be Empty";
			
		}
		else if(postBulkTransferInModel.getCategoryId()==null || postBulkTransferInModel.getCategoryId().isEmpty()) {
			return "The Category Name Can Not Be Empty";
			
		}
		else if(postBulkTransferInModel.getServiceId()==null || postBulkTransferInModel.getServiceId().isEmpty()) {
			return "The Service  Can Not Be Empty";
			
		}
		else if(postBulkTransferInModel.getRankId()==null || postBulkTransferInModel.getRankId().isEmpty()) {
			return "The Level Name Can Not Be Empty";
			
		}
		else if(postBulkTransferInModel.getRailPayAcOff()==null || postBulkTransferInModel.getRailPayAcOff().isEmpty()) {
			return "The Rail Accounting Office Can Not Be Empty";
			
		}
		else if(postBulkTransferInModel.getAirPayAcOff()==null || postBulkTransferInModel.getAirPayAcOff().isEmpty()) {
			return "The Air Accounting Office Can Not Be Empty";
			
		}
		else if(postBulkTransferInModel.getLevelId()==null || postBulkTransferInModel.getLevelId().isEmpty()) {
			return "The Level Name Can Not Be Empty";
			
		}
		
		for(BulkTransferInReEmpUserModel userDetails: postBulkTransferInModel.getUserDetailsModel()) {
		
			
			if(userDetails.getUserId()==null) {
				return "TheUser Id Can Not Be Empty"; 
				
				
			}
			else if(userDetails.getSosDate()==null || userDetails.getSosDate().isEmpty()) {
				return "The Token Of Strength Date Can not be Empty";
				
			}
			else if(userDetails.getDateOfRetirement()==null || userDetails.getDateOfRetirement().isEmpty()) {
				return "The Date Of Retirement Can not be Empty";
				
			}
			
		}
		
		
		return "OK";
	
		
	}


}
