package com.pcda.cgda.airtranscation.voucherdownload.model;

import java.util.List;

import lombok.Data;

@Data
public class VoucherDownloadFormsResponse {

   private  String errorMessage;
   private int errorCode;
   private String request;
   private	String requestType;
   private	String customMessage;
   private	List<VoucherDownloadRequests> responseList;
   private	String response;
}
