package com.pcda.mb.reports.railirlareport.model;

import java.util.List;

import lombok.Data;

@Data
public class RailReportResponseModel {
   private String errorMessage;
   private Integer errorCode;
   private List<RailIrlaReportModel> responseList;

}
