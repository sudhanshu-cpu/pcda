package com.pcda.common.model;

import com.pcda.util.Status;
import com.pcda.util.YesOrNo;

import lombok.Data;

@Data
public class RailStation{

	private int railID;    
	private Status status;	
	private String railStationName;
	private String railStationCode;
	private YesOrNo clusteredStns;  			               	
	
}
