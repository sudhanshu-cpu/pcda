package com.pcda.adg.reports.dtsTokenIssueReport.model;

import lombok.Data;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.List;

@Data
public class TokenReportResponseModel {

    private String errorMessage;
    private Integer errorCode;
    private TokenReportDetailModel response;
    private List<TokenReportDetailModel> responseList;
}
