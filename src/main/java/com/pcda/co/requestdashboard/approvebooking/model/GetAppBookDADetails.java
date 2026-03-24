package com.pcda.co.requestdashboard.approvebooking.model;

import java.util.Date;

import lombok.Data;

@Data
public class GetAppBookDADetails {

	 private Double daAdvanceAmount;
	 private String destinationCity;
	 private String advanceType;			 
	 private Integer daNoOfDays;
	 private Date expectedReturnDate;	  
	 private String accomodation;	 
	 private Double hotelAmount;
	 private Double convyncAmount;
	 private Double foodAmount;
	 private Double ctgAmount;  
	 private Double miscAmount;
	 private Double totalAmount;


}
