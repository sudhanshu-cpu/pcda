package com.pcda.co.approveuser.approvetravelleredit.model;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ProfileAuditResponse {

	private Map<String, List<ProfileChangeDetails>> response;

	private int errorCode;

}
