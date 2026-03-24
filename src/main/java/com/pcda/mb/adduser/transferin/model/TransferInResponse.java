package com.pcda.mb.adduser.transferin.model;



import java.util.List;

import lombok.Data;

@Data
public class TransferInResponse {


	private String errorMessage;

	private int errorCode;

	private TransferInModel response;

	private List<TransferInModel> responseList;

}
