package com.pcda.mb.travel.bulkBkg.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;
@Data
public class PreventProcessDetailsBulkBkg {
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
