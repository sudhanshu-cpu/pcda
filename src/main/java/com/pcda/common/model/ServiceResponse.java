package com.pcda.common.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceResponse {

	private String errorMessage;

	private int errorCode;

	private List<DODServices> responseList;

	private DODServices response;

}
