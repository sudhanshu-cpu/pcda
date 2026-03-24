package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class OfficesResponse {

	private String errorMessage;
    private int errorCode;
    private Integer id;
    private OfficeModel response;
    private List<OfficeModel> responseList;

}
