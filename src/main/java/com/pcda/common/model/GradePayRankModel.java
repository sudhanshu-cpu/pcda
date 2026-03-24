package com.pcda.common.model;

import com.pcda.util.ApprovalState;
import com.pcda.util.CabinClass;
import com.pcda.util.ClassType;
import com.pcda.util.Status;

import lombok.Data;

@Data
public class GradePayRankModel {

	private String dodRankId;

	private String rankName;

	private String serviceId;

	private String serviceName;

	private String categoryId;

	private String category;

	private ClassType highestEntitledClass;

	private CabinClass highestAirEntitledClass;

	private Integer separateGradePayRankExists;

	private ApprovalState approvalState;

	private Status status;

	private Integer retirementAge;

	private String entityType;

	private String remarks;

	private Long loginUserId = 0L;

}
