package com.pcda.common.model;
import java.util.List;

import lombok.Data;

@Data
public class PersonalNoResponse {
	private String errorMessage;
    private int errorCode;
    private List<PersonalNumberModel> responseList;
    private PersonalNumberModel response;
}
