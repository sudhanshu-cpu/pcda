package com.pcda.adg.reports.bookingreport.model;

import lombok.Data;

@Data
public class AdgBookingReportInputModel {

private String frmBookingDt;
private String toBookingDt;
private String reportType;

private String month;
private String year;
private String accountOffice;

}
