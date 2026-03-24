package com.pcda.mb.adduser.travelerprofile.model;

import lombok.Data;

@Data
public class TravelerProfileReqData {

	private String rankExistService;

	private String serviceName;

	private String serviceId;

	private int currentYear;
	private int previousYear;

	private String currentSubBlockYear;

	private String previousSubBlockYear;

}
