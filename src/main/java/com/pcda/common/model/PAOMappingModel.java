package com.pcda.common.model;

import java.util.Date;

import com.pcda.util.Status;
import com.pcda.util.TravelMode;

import lombok.Data;

@Data
public class PAOMappingModel {

	private Integer mappingId;
	private Date creationTime;
	private Status status;
	
    private String serviceName;
    private String categoryName;
	private Date lastModTime;
	private String name;
	private String approvalState;
	private String serviceId;
	private String categoryId;
	private TravelMode travelType;
	private String acuntoficeId;
	private String remarks;
	private String mixedmodPao;

}
