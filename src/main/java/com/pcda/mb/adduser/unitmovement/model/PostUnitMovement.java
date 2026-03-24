package com.pcda.mb.adduser.unitmovement.model;


import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUnitMovement {
	
	
	private String movementId;
	private String groupIdForUserId;
	private String unitRelocationDateStr;
	private Date unitRelocationDate;
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
	private List<String> unitMoveUserId;
	



}
