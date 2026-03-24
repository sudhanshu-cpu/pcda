package com.pcda.mb.adduser.transferout.model;

import java.util.List;

import lombok.Data;

@Data
public class TransferOutResponse {


	private String errorMessage;

	private int errorCode;

	private TransferOutModel response;

	private List<TransferOutModel> responseList;

}
