package com.pcda.common.model;

import lombok.Data;

@Data
public class Level {

	private String levelName;

	private String levelId;

	private String serviceId;

	private String description;

	private String serviceName;

	private Long loginUserId = 0L;

	private String remarks;

	private String status;

}
