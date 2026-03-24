package com.pcda.mb.adduser.edittravelerprofile.model;

import java.util.List;

import lombok.Data;

@Data
public class TravelerReqResponse {

	private String errorMessage;

	private Integer errorCode;

	private TravelerReqModel response;

	private List<TravelerReqModel> responseList;

}
