package com.pcda.co.requestdashboard.approvebooking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PAOChangeBean {
	private String requestId;
	private String newRailPAOId;
	private String newRailPAO;
	private String newAirPAOId;
	private String newAirPAO;
	private String oldRailPAO;
	private String oldAirPAO;
	private String travelMode;

}
