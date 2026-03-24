package com.pcda.co.requestdashboard.approvebooking.model;

import java.math.BigInteger;

import lombok.Data;

@Data
public class PostApproveBookModel {

	
	
	private String daAdvanceAvail ;
	private String personalNo ;
	private String  requestId;
	private String  isTatkal;
	private String  travelMode;
	private String  event;
	private String reasonForDisapprove;
        private double modifiedDAAmount=0.0 ;
	private double  modifiedHotelAmount=0.0;
	private double  modifiedConveyanceAmount=0.0;
	private double modifiedFoodAmount=0.0 ;
	private double  modifiedCTGAmount=0.0;
	private double modifiedLuggAmount=0.0 ;
	private double modifiedVehicleAmount=0.0 ;
	private BigInteger loginUserId;
	private String searchPNo;
	private String requestMode;
	
	
	
	
	
	
	
	
	
	
}
