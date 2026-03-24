package com.pcda.serviceprovider.reports.airbookingupdation.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostParentAirUpdationModel {

	private String newOperatorTxnId;
	private String bookingId;
	private Integer serviceProviderInt;
	private Integer passCount;
	List<PostChildAirUpdationModel> passRows;
	private BigInteger loginUserID;
}
