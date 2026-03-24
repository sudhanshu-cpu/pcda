package com.pcda.common.model;

import java.util.List;

import lombok.Data;

@Data
public class LtcSpecialSectorResponse {

	private String errorMessage;
	private int errorCode;
	private List<LtcSpecialSector> responseList;
	private LtcSpecialSector response;
}
