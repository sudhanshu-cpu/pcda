package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class PersonalNoPrefixResponse {

	private String errorMessage;

	private int errorCode;

	private List<PersonalNoPrefix> responseList;

	private PersonalNoPrefix response;

}
