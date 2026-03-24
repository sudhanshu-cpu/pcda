package com.pcda.mb.adduser.changepersonalno.model;

import java.util.List;

import lombok.Data;

@Data
public class ChangePersonalNoPostResponse {

	private String errorMessage;
    private int errorCode;
    private List<PostChangePersonalNoModel> responseList;
    private PostChangePersonalNoModel response;
}
