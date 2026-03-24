package com.pcda.common.model;

import com.pcda.util.RelationType;

import lombok.Data;

@Data
public class TREligibilityMembers {

	private Integer sequanceNo;

	private String member;

	private RelationType relationType;

	private Integer perMemberJrnyCount;

}
