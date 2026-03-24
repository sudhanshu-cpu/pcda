package com.pcda.mb.reports.railcancellationreport.model;

import lombok.Data;

@Data
public class PassengerCanDetailsModel {

    private Integer refundId;
    private String name  ;
    private String gender  ;
    private Integer age  ;
    private String pxnType ;
    private String isConcession;
    private Integer pxnNo;
    private String seat;
    private String berth;
    private String coach;
    private double ticketBaseFare;
    private double superfastCharge;
    private double otherCharge;
    private double safetyCharge;
    private double reservationCharge;
    private String ticketCurrentStatus;
    private String ticketBookingStatus;
    private String currentCancelStatus;
    private String isOnGovtInt;


}
