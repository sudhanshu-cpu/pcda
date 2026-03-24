package com.pcda.co.approveuser.approvebulktransferinreemployment.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BulkTransferInReEmpViewBean {
	
	private String transferId;
	private String unitRelocationDateStr;
	private Date transferAuthorityDate;
	private String railDutyNrs;
	private String dutyStnNap;
	private String transferAuthority;
	private String groupId;	
	private String categoryId;
	private String categoryName;
	private String serviceId;
	private String serviceName;
	private String rankId;
	private String rankName;
	private String railPao;
	private String airPao;
	private String levelId;
	private String level;
	private List<BulkTransferInReEmpViewDtlsBean> userDetailsModel;
	

}
