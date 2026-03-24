package com.pcda.mb.reports.railcancellationreport.model;

import com.pcda.mb.reports.railtravelreports.model.PassengerTicketsCancellationModel;
import lombok.Data;

import java.util.List;

@Data
public class CancellationViewDetails {

    private Integer refundId;
    private String refundDate;
    private double refundamount;
    private double cancellationAmount;
    private String isOnGovInt;
    private String reconStatus;
    private String cancellationDate;
    private String transactionDate;
    private String cancellationMode;

    private List<PassengerCanDetailsModel> passengerDetails;

}
