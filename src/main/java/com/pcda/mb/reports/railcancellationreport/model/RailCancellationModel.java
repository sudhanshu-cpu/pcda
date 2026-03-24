package com.pcda.mb.reports.railcancellationreport.model;
import lombok.Data;

@Data
public class RailCancellationModel {

    private String pnr;
    private String refundId;
    private String bookingId;
    private Double totalFare;
    private String refundDate;
    private Double cancellationAmount;
    private Double refundAmount;
    private String currentBookingStatus;
    private String personalNumber;
}
