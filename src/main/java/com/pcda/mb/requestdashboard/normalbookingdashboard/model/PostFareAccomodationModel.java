package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import lombok.Data;

@Data
public class PostFareAccomodationModel {

	private String originStnCode;
	private String destStnCode;
	private String frmStation;
	private String toStation;
	private String depDate;
	private String journeyclass;
	private String tatkal;
	private int child;
	private int adult;
	private int senior;
	private String trainNo;
	private String isCanWtTckt;
	private String trainType;
	private String foodChoiceFlag;
	private int infant;
	private int wsenior;
	private String reqId;
	private String clusterFlag;
	private String clusterMainLeg;
	private String srcStn;
	private String destStn;
	private String clusterPnrNo;
	private String clusterReservationId;

}
