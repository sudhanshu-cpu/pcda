package com.pcda.mb.adduser.bulktransferin.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostBulkTransferInModel {

	private String unitRelocationDateStr;
	private Date unitRelocationDate;
	private String unitRailDutyNrs;
	private String unitDutyStnNa;
	private String unitMoveAuthority;
	private String groupId;	
	private BigInteger loginUserId = BigInteger.ZERO;
	private List<BulkTransferInUserDetailsModel> userDetalsModel;
	

}
