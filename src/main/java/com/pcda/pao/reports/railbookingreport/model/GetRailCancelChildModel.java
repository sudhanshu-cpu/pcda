package com.pcda.pao.reports.railbookingreport.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetRailCancelChildModel {
	private Integer refundId;
	private String refundDate;	
	private double refundamount;
	private double cancellationAmount;
	private String isOnGovInt;
	private String reconStatus;
	private String cancellationDate;	
	private String transactionDate;
	private String cancellationMode;
	
	private List<GetRailCancelSubChildModel> passengerDetails;
}
