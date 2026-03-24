package com.pcda.mb.adduser.travelerprofile.model;

import java.util.List;

import lombok.Data;

@Data
public class TravelerUserResponse {

	private String errorMessage;

	private int errorCode;

	private TravelerUser response;

	private List<TravelerUser> responseList;

}
