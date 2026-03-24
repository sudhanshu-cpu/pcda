package com.pcda.common.model;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pcda.util.AllowedJourneyType;
import com.pcda.util.ApprovalState;
import com.pcda.util.ServiceType;
import com.pcda.util.Status;
import com.pcda.util.TravelGroup;
import com.pcda.util.YesOrNo;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TravelRule {

	private String trRuleId;
	private String rulePurpose;
	private Status status;
	private ApprovalState approvalState;
	private String trRuleNumber;
	private String remarks;
	private TravelGroup travelGroup;
	private String trRuleTitle;
	private String trRuleDesc;
	private Integer jrnyCountAllowedTR;
	private Integer jrnyCountCalenderYear;
	private Integer selfJrnyCount;
	private Integer dependentJrnyCount;
	private Integer attendentJrnyCount;
	private YesOrNo isAttendendAllowed;
	private String movAuthorityDetail;
	private String notes;
	private AllowedJourneyType jrnyAllowed;
	private YesOrNo higherClassAllowed;
	private YesOrNo partyDepHigherClassAllowed;
	private ServiceType serviceType;
	private YesOrNo higherClassAllowedAir;
	private YesOrNo ltc80FareAllowed;
	private YesOrNo isFixedStnAllowed;
	private YesOrNo isPeriodAllowed;
	private Date periodFromDate;
	private Date periodToDate;
	private String periodFromDateFormat;
	private String periodToDateFormat;
	private String ruleRelatedNews;
	private YesOrNo isRequestAllowed;
	private YesOrNo privateAirlineAllowed;
	private YesOrNo airlineAllowedAll;
	private YesOrNo daAllowed;
	private YesOrNo ctgAllowed;
	private Integer daAdvanceDay;
	private YesOrNo hotelAllowanceAllow;
	private YesOrNo conveyanceAllowanceAllow;
	private YesOrNo foodAllowanceAllow;
	private YesOrNo miscAllowanceAllow;
	private YesOrNo vehicleAllowanceAllow;
	private YesOrNo jrnyBasedTR;
	private Date maxJrnyDate;
	private String maxJrnyDateFormat;
	private YesOrNo intlBookingAllowed;

	private Set<TREligibilityCategory> eligibilityCategories;
	private Set<TREligibilityLocation> eligibilityLocations;
	private Set<TREligibilityMembers> eligibilityMembers;
	private Set<TRQuestion> questions;
	private Set<TRJourney> journeys;
	private Set<TRRuleService> ruleServices;
	private Set<TRScheme> schemes;
	private Set<TRTravelMode> modes;
	private Set<TRRequisite> requisites;
	private Set<TRFixedStation> fixedStations;

}
