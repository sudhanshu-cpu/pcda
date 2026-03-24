package com.pcda.adg.reports.dtsTokenIssueReport.model;

import com.pcda.adg.reports.passengeraddressreport.model.GetPassengerAddresModel;
import lombok.Data;

import java.util.List;

@Data
public class DtsTokenResponseModel {

        private String errorMessage;
        private Integer errorCode;
        private DtsTokenCountModel response;
        private List<DtsTokenCountModel> responseList;

}
