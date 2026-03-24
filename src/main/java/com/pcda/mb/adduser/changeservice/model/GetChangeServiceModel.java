package com.pcda.mb.adduser.changeservice.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetChangeServiceModel {
	
	
	private String personalNumber;
	private String loginUserUnitId;
	private String loginUserUnitName;
	private String loginUserServiceId;
	private String loginUserServiceName;
	private Date personalDOB;
	private String userServiceId;
	private String unitServiceId;
	private String unitServiceName;
	private String categoryId;
	private String categoryName;
	private String rankId;
	private String rankName;
	private String personalName;
	private BigInteger  loginUserId =BigInteger.ZERO;
	private int approvalState;
	

}
