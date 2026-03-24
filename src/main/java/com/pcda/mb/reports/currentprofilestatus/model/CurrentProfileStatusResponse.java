package com.pcda.mb.reports.currentprofilestatus.model;



import java.util.List;

import lombok.Data;
@Data
public class CurrentProfileStatusResponse {
	private String errorMessage;
    private int errorCode;
    private List<CurrentProfileStatusModel> responseList;
    private CurrentProfileStatusModel response;

}
