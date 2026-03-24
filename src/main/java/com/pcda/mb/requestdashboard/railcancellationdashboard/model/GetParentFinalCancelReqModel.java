package com.pcda.mb.requestdashboard.railcancellationdashboard.model;

import java.util.List;

import lombok.Data;

@Data
public class GetParentFinalCancelReqModel {

	private String bookingId="";
	private String canString="";
	private String refundId="";
	private double refundAmount;
	private double cashDeducted;
	private String cancelledDate="";
	private double serviceTax;
	private double amountCollected;
	private String cancellationId="";
	private String pnrNo="";
	private String agentCanTxnId="";
	private String tktCanType;
	private List<GetCanPassengerDetailBean> passengerDetail;

}
