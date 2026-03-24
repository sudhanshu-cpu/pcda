package com.pcda.serviceprovider.reports.airbookingupdation.model;

import java.util.List;
import lombok.Data;
@Data
public class BookingIdResponse {


	private String errorMessage;
	private Integer errorCode;

	private List<GetParentFrmBkgIdModel> responseList;
	private GetParentFrmBkgIdModel response;
}
