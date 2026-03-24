package com.pcda.co.approveuser.unitmovementapproval.model;

import java.util.Date;

import lombok.Data;

@Data
public class GetUnitMomentApprovalModel {

	private String movementId;
	private Date relocationDate;
	private String relocationDateFormat;
	private String railStation;
	private String nearestAirport;
	private String movementAuthority;

}
