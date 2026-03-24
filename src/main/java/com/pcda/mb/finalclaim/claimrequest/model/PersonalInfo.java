package com.pcda.mb.finalclaim.claimrequest.model;

import lombok.Data;

@Data
public class PersonalInfo {

	private String name;
	private String levelId;
	private String levelName;
	private String unitName;
	private String unitId;
	private String railPAOId;
	private String airPAOId;
	private String cDAOAC;
	private Integer isArmedForce;

}
