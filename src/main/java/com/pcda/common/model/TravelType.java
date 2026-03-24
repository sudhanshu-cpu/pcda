package com.pcda.common.model;

import java.util.Date;

import com.pcda.util.ApprovalState;
import com.pcda.util.Status;

import lombok.Data;

@Data
public class TravelType {

	private String travelTypeId;
	private Date creationTime;
	private Status status;
	private Date lastModTime;
	private Long createdBy;
	private Long lastModBy;
	private Long approvedBy;
	private ApprovalState approvalState;
	private String travelName;
	private String description;
	private String remarks;

}
