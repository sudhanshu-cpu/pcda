package com.pcda.mb.reports.railcancellationreport.model;

import lombok.Data;

@Data
public class RailCancellationReportInputModel {

        private String bookingId = "";
        private String pnr = "";
        private String personalNumber = "";
        private String cancellationFromDate = "";
        private String cancellationToDate = "";
        private String groupId = "";


}
