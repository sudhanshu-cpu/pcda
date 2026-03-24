package com.pcda.mb.grievance.complaintHistory.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class GrievanceMainBean {
	 private int count;
     private Date earliestDate;
     private List<GrievanceHistoryView> grievanceViewBean;

	
}
