package com.pcda.mb.requestdashboard.mastermissingdashboard.model;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMasterMissingModel {
	
	private BigInteger userId;
	private String personalNo;
	private String name;
	private String categoryId;
	private String categoryName;
	private String serviceId;
	private String serviceName;
	private String accountOfficeNam;
	private String levelId;
	private String levelName;
	private String groupId;
	

}
