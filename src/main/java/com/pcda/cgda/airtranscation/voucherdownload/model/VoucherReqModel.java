package com.pcda.cgda.airtranscation.voucherdownload.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class VoucherReqModel {

	private List<VoucherDownloadRequests> voucherRequestList;
	private Long recordsCount=0L;
	private Date startDate;
	private String startDateFormat;
}
