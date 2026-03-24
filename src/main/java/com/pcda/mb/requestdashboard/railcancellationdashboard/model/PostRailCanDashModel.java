package com.pcda.mb.requestdashboard.railcancellationdashboard.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostRailCanDashModel {

	private String bookingId="";
	private String requestId="";
	private String pnrNo="";
	private String personalNo="";
	private String groupId="";
}

