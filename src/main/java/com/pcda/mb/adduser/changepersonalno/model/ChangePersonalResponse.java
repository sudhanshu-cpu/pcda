package com.pcda.mb.adduser.changepersonalno.model;

import java.util.List;

import lombok.Data;

@Data
public class ChangePersonalResponse {

	private String errorMessage;
    private int errorCode;
    private List<GetChangePersonalNo> responseList;
    private GetChangePersonalNo response;
}
