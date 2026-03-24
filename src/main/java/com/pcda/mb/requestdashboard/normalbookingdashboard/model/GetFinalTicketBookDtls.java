package com.pcda.mb.requestdashboard.normalbookingdashboard.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class GetFinalTicketBookDtls {
	private String requestId;
	private Integer seqNo;
	private String clientTxnId;
	private Double cateringCharge;
	private Double fuelAmount;
	private Double reservationCharge;
	private Double serviceTax;
	private Double tatkalFare;
	private Double totalConcession;
	private Double totalFare;
	private Double wpServiceCharge;
	private Double wpServiceTax;
	private Double totalCollectibleAmount;
	private Integer distance;
	private String trainName;
	private String trainNumber;
	private String arrivalTime;
	private Date boardingDate;
	private String boardingDateStr;
	private String boardingStn;
	private Date bookingDate;
	private String bookingDateStr;
	private String departureTime;
	private Date destArrvDate;
	private String destStn;
	private String fromStn;
	private String journeyClass;
	private Date journeyDate;
	private String journeyQuota;
	private Integer numberOfAdults;
	private Integer numberOfChilds;
	private Integer numberOfpassenger;
	private String pnrNumber;
	private Integer reasonIndex;
	private String reasonType;
	private String reservationId;
	private String bookingId;
	private String resvnUptoStn;
	private Integer timeTableFlag;
	
	private List<GetFinalTktPssngrDetailsModel> passDetails=new ArrayList<>();

}
