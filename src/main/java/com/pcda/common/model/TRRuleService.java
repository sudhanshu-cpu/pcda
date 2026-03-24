package com.pcda.common.model;

import com.pcda.util.ServiceType;

import lombok.Data;

@Data
public class TRRuleService {

	private Integer sequanceNo;
	private String serviceId;
	private ServiceType serviceType;
}
