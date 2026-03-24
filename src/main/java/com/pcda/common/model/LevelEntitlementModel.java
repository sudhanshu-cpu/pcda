package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class LevelEntitlementModel {

	private Integer entId;

	private List<String> travelTypeIdList;

	private String serviceName;

	private String levelName;

	private String levelId;

	private String serviceId;

	private Integer retirementAge;

	private Integer higherRailEntitlement;

	private Integer higherAirEntitlement;

	private String description;

	private String remarks;

	private String travelTypeId;

	private String travelName;

	private String status;

	private Long loginUserId = 0L;

	private String railClass;

	private String airClass;

}
