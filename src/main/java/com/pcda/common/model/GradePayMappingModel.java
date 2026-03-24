package com.pcda.common.model;

import lombok.Data;

@Data
public class GradePayMappingModel {

	private Integer mapId;

	private String categoryId;

	private String dodRankId;

	private String levelId;

	private String serviceId;

	private String description;

	private String serviceName;

	private String levelName;

	private String categoryName;

	private String rankName;

	private String gradePay;

	private String status;

	private String approvalState;

	private String remarks;

	private Long loginUserId = 0L;

}
