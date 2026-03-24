package com.pcda.mb.adduser.unitmovement.model;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnitMovementFormData {
	private String unitRelocationDateStr;
	private String unitRailDutyNrs;
	private String unitDutyStnNa;
	private String unitMoveAuthority;
	private String unitTravelers;
	private String personalNo;
	private String groupId;
	
	private String serviceId;
	private String categoryName;
	private String categoryId;
	
	private BigInteger loginUserId = BigInteger.ZERO;
}
