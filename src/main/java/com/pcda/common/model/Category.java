package com.pcda.common.model;

import lombok.Data;

@Data
public class Category {

	private String categoryName;

	private String categoryDescription;

	private String serviceName;

	private String serviceId;

	private Integer loginUserId = 0;

	private String remark;

	private String status;

	private String approvalState;

	private String categoryId;

}
