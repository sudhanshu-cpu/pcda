package com.pcda.common.model;

import lombok.Data;

@Data
public class Location {

	private String locationName;

	private String description;

	private String approvalState;

	private Long loginUserId = 0L;

	private String remarks;

	private String locationId;

	private String status;

}
