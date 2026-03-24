package com.pcda.mb.adduser.transferinandreemployment.model;

import java.util.List;

import lombok.Data;

@Data
public class TransferInReemployeeResponse {

	
	
	private String errorMessage;

	private int errorCode;

	private TransferInAndreemploymentModel response;

	private List<TransferInAndreemploymentModel> responseList;
}
