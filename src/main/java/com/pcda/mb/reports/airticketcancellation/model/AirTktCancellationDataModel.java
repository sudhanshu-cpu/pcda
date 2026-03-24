package com.pcda.mb.reports.airticketcancellation.model;

import lombok.Data;

@Data
public class AirTktCancellationDataModel {

		private String	bookingId;
		private String personalNo;
		private String	cancellationDate;
		private String	cancellationInvoiceStatus;
		private double	totalRefundAmt;
		private double	cancellationRefundAmt;
		private String fbNumber;
		private String fcNumber;
	
}
