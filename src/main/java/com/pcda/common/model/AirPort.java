package com.pcda.common.model;

import com.pcda.util.ApprovalState;
import com.pcda.util.DomIntType;
import com.pcda.util.Status;

import lombok.Data;

@Data
public class AirPort {    
	
	private String airPortId;
	private String airPortCode;
	private Status status;
	private String airPortName;
	private ApprovalState approvalState;
	private String remarks;
	private DomIntType airportType;   
	private String airPortCity;

	
	
	
	
}
