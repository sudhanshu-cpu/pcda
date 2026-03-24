package com.pcda.co.requestdashboard.approverailcancellation.model;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCanChildApproveModel {


	private Integer passengerNo;
	private Integer isOnGovtInt;
	private String cancelStatus;
}
