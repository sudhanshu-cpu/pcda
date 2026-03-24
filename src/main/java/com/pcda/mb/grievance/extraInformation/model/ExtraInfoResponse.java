package com.pcda.mb.grievance.extraInformation.model;

import lombok.Data;

@Data
public class ExtraInfoResponse {

	private String errorMessage;
    private int errorCode;
    private GrievanceMainBean response;
}
