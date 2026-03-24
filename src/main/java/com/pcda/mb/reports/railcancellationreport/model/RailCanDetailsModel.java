package com.pcda.mb.reports.railcancellationreport.model;

import com.pcda.mb.reports.railtravelreports.model.CancellationView;
import com.pcda.mb.reports.railtravelreports.model.TicketsPassangerDetails;
import lombok.Data;

import java.util.List;

@Data
public class RailCanDetailsModel {

    private String  requestId ;
    private String  bookingId ;
    private String  travelType ;
    private String  journeyClass ;
    private String  pnrNumber ;
    private String  ticketNumber ;
    private String  trainNumber ;
    private String  trainName ;
    private String  bookingDate ;
    private String  journeyDate ;
    private double  totalFare ;
    private String  tatkalBookingState;
    private String  tatkalApprovalState;
    private double  irctcServiceCharge;
    private String  fromStnCode;
    private String  toStnCode;
    private String  fromStnName;
    private String  toStnName;



    private List<CancellationViewDetails> cancellationView;



}
