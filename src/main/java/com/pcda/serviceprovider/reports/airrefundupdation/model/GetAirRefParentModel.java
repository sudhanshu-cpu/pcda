package com.pcda.serviceprovider.reports.airrefundupdation.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetAirRefParentModel {
	private String bookingData;
	private int passCount;
	private String bookingOid;
	private int serviceProviderInt;
	
	private GetAirRefBookDataModel airRefundBookData;
	
	private List<GetAirRefBookChildModel> airRefundBookChild;
	
	
}
