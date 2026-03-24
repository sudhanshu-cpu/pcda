package com.pcda.cgda.airtranscation.voucherdownload.model;

import java.util.Date;

import lombok.Data;

@Data
public class VoucherDownloadRequests {

	private String voucherReqId;
	private String fromDate;
	private String toDate;
	private Integer serviceProvider;
	private String serviceProviderStr;
	private String paoGroupId;
	private String paoName;
	private String serviceId ;  
	private String serviceName;
	private String travelType;
	private Integer isVoucherGen;
	private String isVoucherGenStr;
	private Integer isVoucherSettle;
	private String isVoucherSettleStr;
	private String utrNumber;
	private String utrDate;
	private Double voucherAmount;
	private String voucherNumber;
	private Long count;
	private String filePath;
	private String creationTime;
	private String reportFilePath;
	private String summaryFilePath;
	private Integer isDemandGenerated;
	private String isDemandGeneratedStr;
	private String cmpFilePath;
    private Date formatFromDate;
    
    private String requestFor;
}
