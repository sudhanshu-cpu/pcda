package com.pcda.mb.adduser.bulktransferinreemployment.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PostBulkTransferInReEmpModel {
	
	private BigInteger loginUserId;
	private String unitRelocationDateStr;
	private Date unitRelocationDate;
	private String unitRailDutyNrs;
	private String unitDutyStnNa;
	private String unitMoveAuthority;
	private String groupId;	
	private String categoryId;
	private Integer retireAgePbor;
	private Integer retireAgeOther;
	private String serviceId;
	private String rankId;
	private String railPayAcOff;
	private String airPayAcOff;
	private String levelId;
	private String remark;
	private List<BulkTransferInReEmpUserModel> userDetailsModel;
}
