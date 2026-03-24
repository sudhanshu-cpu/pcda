package com.pcda.common.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.pcda.common.model.CompleteUserResponse;
import com.pcda.common.model.User;
import com.pcda.common.model.UserResponse;
import com.pcda.common.model.VisitorModel;
import com.pcda.mb.adduser.transferout.model.EditUserModel;
import com.pcda.mb.adduser.transferout.model.EditUserResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class UserServices {

	@Autowired
	RestTemplate restTemplate;

	public Optional<User> getUserByUserAlias(String userAlias) {
		

		User mappedUser = null;
		try {

		UserResponse mappedUserResponse = restTemplate.getForObject(PcdaConstant.USER_BASE_URL + "/userAlias/" + userAlias, UserResponse.class);

		if (null != mappedUserResponse && mappedUserResponse.getErrorCode() == 200 && null != mappedUserResponse.getResponse()) {
			mappedUser = mappedUserResponse.getResponse();
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, UserServices.class, LogConstant.COMMON_LOG);
		}
		return Optional.ofNullable(mappedUser);
	}
	
	public Optional<User> getUser(BigInteger userId) {
		DODLog.info(LogConstant.COMMON_LOG, UserServices.class, "Get User By User Id :" + userId);

		User mappedUser = null;
try {
		UserResponse mappedUserResponse = restTemplate.getForObject(PcdaConstant.USER_BASE_URL + "/userId/" + userId, UserResponse.class);

		if (null != mappedUserResponse && mappedUserResponse.getErrorCode() == 200 && null != mappedUserResponse.getResponse()) {
			mappedUser = mappedUserResponse.getResponse();
		}
}catch(Exception e) {
	DODLog.printStackTrace(e, UserServices.class, LogConstant.COMMON_LOG);
}
		return Optional.ofNullable(mappedUser);
	}
	
	
	
	
	public List<User> getMappedMBUsersByUnitId(String groupId,String approvalType ) {
		List<User> mappedUsersList = new ArrayList<>();
		
		try {
		
		StringJoiner joiner = new StringJoiner("/").add(PcdaConstant.USER_MAP_URL).add("getMappedUser").add(groupId).add(approvalType);

		ResponseEntity<UserResponse> response = restTemplate.exchange(joiner.toString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<UserResponse>() {});
		
			UserResponse userResponse = response.getBody();
	
			if (userResponse != null && userResponse.getErrorCode() == 200 && !(userResponse.getResponseList().isEmpty())) {
				mappedUsersList = userResponse.getResponseList();
				DODLog.info(LogConstant.COMMON_LOG, UserServices.class, "Mapped User List with unit id: " + groupId + " : mappedUsersList size " + mappedUsersList.size());
			}
			
		}catch(Exception e) {
			DODLog.printStackTrace(e, UserServices.class, LogConstant.COMMON_LOG);
		}
			return mappedUsersList;
		
	}
	
	
	
	public Optional<VisitorModel> getCompleteUser(String userAlias) {
		DODLog.info(LogConstant.COMMON_LOG, UserServices.class, "Get User By Personal No :" + userAlias);

		VisitorModel visitorModel = null;
try {
		CompleteUserResponse userResponse = restTemplate.getForObject(PcdaConstant.USER_BASE_URL + "/completeUserByUserAlias/" + userAlias, CompleteUserResponse.class);

		if (null != userResponse && userResponse.getErrorCode() == 200 && null != userResponse.getResponse()) {
			visitorModel = userResponse.getResponse();
		}
}catch(Exception e) {
	DODLog.printStackTrace(e, UserServices.class, LogConstant.COMMON_LOG);
}
		return Optional.ofNullable(visitorModel);
	}

	public EditUserModel getUserByUserId(BigInteger userId) {
		DODLog.info(LogConstant.COMMON_LOG, UserServices.class, "Get User By User Id :" + userId);

		EditUserModel editUser = new EditUserModel();
		try {
			
		
		String url= PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/viewProfileAudit/" + userId;
	
	EditUserResponse response = restTemplate.getForObject(url, EditUserResponse.class);

		if (null != response && response.getErrorCode() == 200 && null != response.getResponse()) {
			editUser = response.getResponse();
			DODLog.info(LogConstant.COMMON_LOG, UserServices.class, "Edit User with user id: " + userId + " : editUser  " + editUser);
		}
		} catch (Exception e) {
			DODLog.printStackTrace(e, UserServices.class, LogConstant.COMMON_LOG);
		}

		return editUser;
	}
}
