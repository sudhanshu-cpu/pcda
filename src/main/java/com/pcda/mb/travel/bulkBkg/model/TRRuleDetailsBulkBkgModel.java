package com.pcda.mb.travel.bulkBkg.model;

import java.util.List;
import java.util.Set;

import lombok.Data;
@Data
public class TRRuleDetailsBulkBkgModel {
	private String trRuleID;
	private String trRuleNo;
	private String travelGrp;
	private String notes;
	private String trRuleNews;
	private int travelReqStatus;
	private String trRuleTitle;
	private String trRuleDesc;
	private String remarks;
	private String status;
	private String journeyCountTR;
	private String journeyCountYear;
	private String attendedCount;
	private String travelType;
	private Set<String> categories;
	private List<String> locations;
	private List<TRJourneyBulkBkgModel> journeys;
	private List<EligibleMemberBulkBkgModel> eligbleMembers;
	private List<TRQuestionBulkBkgModel> questions;
}
