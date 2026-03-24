package com.pcda.mb.travel.emailticket.model;

import lombok.Data;

@Data
public class ViewTravelDtl {

	private String travelTypeId;

	private int tatkalFlag;

	private int isTatkalApproved;

	private int journeyType;

	private String viaRoute;

	private String travelName;
}
