package com.pcda.mb.grievance.complaintHistory.model;

import java.util.Date;

import com.pcda.util.Department;
import com.pcda.util.GrievanceStatus;
import com.pcda.util.Priority;

import lombok.Data;

@Data
public class GrievanceHistoryView {
	private String grievanceId;
	private String unitName;
	private Department deptName;
	private String grievanceCategory;
	private Date creationTime;
	private Priority priorty;
	private GrievanceStatus grievanceStatus;
	private String grievance;
	private Integer status;
	private String filePath;
	private String mobileNumber;
	private Date closedOnDate;
	
}
