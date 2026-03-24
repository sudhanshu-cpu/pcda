package com.pcda.mb.travel.railcancellation.model;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RailCancellationForm {
	private String ticketNo;
	private String bookingId;
	private String isExpCan;
	private String travelType;
	private String personalNo;
	private String groupId= "UN108544";
	private String pnrNo;
	private BigInteger loginUserId = BigInteger.ZERO;
}
