package com.pcda.mb.travel.journey.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class PreventProcessDetails {

	private Integer processId;
	private String processName;
	private Integer processStatus;
	private BigInteger personalId;
	private String personalNumber;
	private BigInteger unitLoginId;
	private String groupId;
	private Date entryTime;
	private Date exitTime;
}
