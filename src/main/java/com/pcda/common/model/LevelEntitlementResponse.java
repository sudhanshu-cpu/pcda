package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class LevelEntitlementResponse {

	private String errorMessage;
    private int errorCode;
    private List<LevelEntitlementModel> responseList;
    private LevelEntitlementModel response;

}
