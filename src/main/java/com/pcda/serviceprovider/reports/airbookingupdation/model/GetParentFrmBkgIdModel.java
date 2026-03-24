package com.pcda.serviceprovider.reports.airbookingupdation.model;

import java.util.List;

import lombok.Data;

@Data

public class GetParentFrmBkgIdModel {

	private String bookingData;
	private int passCount;
	private String bookingOid;
	private int serviceProviderInt;
	
	private GetSecChildDataFrmBkgModel airTicketBookData;
	
	private List<GetChildFrmBkgIdModel> airTicketBookChild;

}
