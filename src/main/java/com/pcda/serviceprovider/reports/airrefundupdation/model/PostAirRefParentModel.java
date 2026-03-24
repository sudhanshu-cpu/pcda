package com.pcda.serviceprovider.reports.airrefundupdation.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PostAirRefParentModel {

	private String airBookingId;
	private Integer serviceProviderInt;
	private Integer	passangerCount;	
	List<PosrAirRefChildModel>refundHistPassRows;	
	private BigInteger	loginUserID;
	
}
