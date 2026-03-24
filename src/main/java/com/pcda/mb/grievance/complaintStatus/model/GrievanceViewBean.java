package com.pcda.mb.grievance.complaintStatus.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class GrievanceViewBean {
	private String grievanceId;
	private String unitName;
	private String deptName;
	private String grievanceCategory;
	private Date creationTime;
	private String priorty;
	private String grievanceStatus;
	private String grievance;
	private Integer status;
	private String filePath;
	private String mobileNumber;
	private Date closedOnDate;
	private String personalNo;
	private List<GrievanceStatusViewBean> grievanceDetails;
}
