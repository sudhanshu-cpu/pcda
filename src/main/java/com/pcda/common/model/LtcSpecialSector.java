package com.pcda.common.model;


import java.util.Set;

import com.pcda.util.ApprovalState;
import com.pcda.util.Status;

import lombok.Data;

@Data
public class LtcSpecialSector{

	private String sectorId;
	private Status status;	
	private String sectorName;
	private ApprovalState approvalState;
	private String remarks;
	private String travelTypeId;
	private String travelType;
	private Set<LtcSpecialSectorDtls> ltcSpecialSectorDtls;
	
}
