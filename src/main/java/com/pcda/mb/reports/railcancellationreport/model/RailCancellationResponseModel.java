package com.pcda.mb.reports.railcancellationreport.model;

import com.pcda.common.model.Category;
import lombok.Data;

import java.util.List;

@Data
public class RailCancellationResponseModel {

    private String errorMessage;
    private int errorCode;
    private String requestType;
    private String customMessage;
    private String response;
    private List<RailCancellationModel> responseList;


}
