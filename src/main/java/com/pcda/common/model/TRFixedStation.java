package com.pcda.common.model;

import com.pcda.util.Status;

import lombok.Data;

@Data
public class TRFixedStation {
	
	private Integer sequanceNo;
	private String stationCode;
	private String stationName;
	private Integer stationType;
	private Status status;

}
