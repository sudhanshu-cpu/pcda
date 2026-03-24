package com.pcda.mb.travel.journey.model;

import java.util.Date;

import com.pcda.util.YesOrNo;

import lombok.Data;

@Data
public class TravelIdDetails {

	private String personalNo;
	private String traveld;
	private Date startDate;
	private String travelStartDate;
	private String travelEndDate;
	private Date endDate;
	private YesOrNo claimGenerate;
	private String authorityNo;
	private Date authorityDate;
	private String authorityDateStr;
	private Long travelDaCounts;
}
