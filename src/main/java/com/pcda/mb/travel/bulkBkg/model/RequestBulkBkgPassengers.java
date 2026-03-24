package com.pcda.mb.travel.bulkBkg.model;

import lombok.Data;

@Data
public class RequestBulkBkgPassengers {
	
	private Integer seqNo;
	private String relation;
	private String name;
	private String gender;
	private Integer age;
	private String personalNo;

}
