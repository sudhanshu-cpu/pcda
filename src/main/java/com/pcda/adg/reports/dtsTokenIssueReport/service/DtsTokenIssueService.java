package com.pcda.adg.reports.dtsTokenIssueReport.service;

import com.pcda.adg.reports.dtsTokenIssueReport.model.DtsTokenCountModel;
import com.pcda.adg.reports.dtsTokenIssueReport.model.DtsTokenResponseModel;
import com.pcda.adg.reports.dtsTokenIssueReport.model.TokenReportDetailModel;
import com.pcda.adg.reports.dtsTokenIssueReport.model.TokenReportResponseModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DtsTokenIssueService {


    @Autowired
    private RestTemplate restTemplate;


    public List<DtsTokenCountModel> getDtsTokenIssuedReport(String fromIssueDate, String toIssueDate) {
        List<DtsTokenCountModel> dtsTokenCountList = new ArrayList<>();
        try {
            ResponseEntity<DtsTokenResponseModel> response =
                    restTemplate.getForEntity(PcdaConstant.ADG_REPORTS_BASE_URL + "/adgMov/v1/getTokenIssueReport?fromDate={fromIssueDate}&toDate={toIssueDate}",
                            DtsTokenResponseModel.class, fromIssueDate, toIssueDate);

            if (HttpStatus.OK.equals(response.getStatusCode())) {
                dtsTokenCountList = Optional.ofNullable(response.getBody())
                        .map(DtsTokenResponseModel::getResponseList)
                        .orElse(Collections.emptyList());
            }
        } catch (Exception e) {
            DODLog.printStackTrace(e, DtsTokenIssueService.class, LogConstant.ADG_REPORTS_LOG_FILE);
        }
        return dtsTokenCountList;
    }


    public Map<String, Map<String, List<String>>> getDtsTokenDetailedReport(String tokenIssueDate) {
        List<TokenReportDetailModel> dtsTokenDetailedList = new ArrayList<>();
        Map<String, Map<String, List<String>>> map = new HashMap<>();

        try {
            ResponseEntity<TokenReportResponseModel> response =
                    restTemplate.getForEntity(
                            PcdaConstant.ADG_REPORTS_BASE_URL + "/adgMov/v1/getDtsTokenReportDetails?tokenIssueDate={tokenIssueDate}",
                            TokenReportResponseModel.class, tokenIssueDate);

            if (HttpStatus.OK.equals(response.getStatusCode())) {
                dtsTokenDetailedList = Optional.ofNullable(response.getBody())
                        .map(TokenReportResponseModel::getResponseList)
                        .orElse(Collections.emptyList());
            }

            map = dtsTokenDetailedList.stream()
                    .collect(Collectors.groupingBy(TokenReportDetailModel::getUnitName,
                            Collectors.groupingBy(TokenReportDetailModel::getRoleId,
                                    Collectors.mapping(TokenReportDetailModel::getTokenName, Collectors.toList()))));

        } catch (Exception e) {
            DODLog.printStackTrace(e, DtsTokenIssueService.class, LogConstant.ADG_REPORTS_LOG_FILE);
        }
        return map;
    }


}
