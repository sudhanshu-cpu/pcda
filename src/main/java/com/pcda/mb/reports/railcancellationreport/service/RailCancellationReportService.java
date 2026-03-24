package com.pcda.mb.reports.railcancellationreport.service;

import com.pcda.mb.reports.railcancellationreport.model.*;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import com.pcda.util.PcdaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RailCancellationReportService {

    @Autowired
    public RestTemplate restTemplate;


    public List<RailCancellationModel> getRailCancellationReport(RailCancellationReportInputModel railCancellationReportInputModel) {
        List<RailCancellationModel> railReportList = new ArrayList<>();
        DODLog.info(LogConstant.RAIL_REPORT, RailCancellationReportService.class, "[railCancellationReportInputModel] ## railCancellationReportInputModel" + railCancellationReportInputModel);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<RailCancellationReportInputModel> entity = new HttpEntity<>(railCancellationReportInputModel, headers);

            ResponseEntity<RailCancellationResponseModel> response = restTemplate.exchange(
                    PcdaConstant.COMMON_REPORT_URL+"/getRailCancellationReport",
                    HttpMethod.POST,
                    entity,
                    RailCancellationResponseModel.class);

            if (HttpStatus.OK.equals(response.getStatusCode())) {
                railReportList = Optional.ofNullable(response.getBody())
                        .map(RailCancellationResponseModel::getResponseList)
                        .orElse(Collections.emptyList());
            }

        } catch (Exception e) {
            DODLog.printStackTrace(e, RailCancellationReportService.class, LogConstant.RAIL_REPORT);
        }
        DODLog.info(LogConstant.RAIL_REPORT, RailCancellationReportService.class, "[getRailCancellationReport] ## railReportList :: " + railReportList.size());
        return railReportList;
    }


    // Get Rail Tickets Cancellation Details
    public RailCanDetailsModel getRailTktCancellationDetails(String bookingId) {
        RailCanDetailsModel cancellationDtls = null;
        DODLog.info(LogConstant.RAIL_REPORT, RailCancellationReportService.class, "[getrailTiceketsCancellationDtls] ## bookingId " + bookingId);

        try {
            DODLog.info(LogConstant.RAIL_REPORT, RailCancellationReportService.class,
                    "Get user with personal no: " + bookingId);
            RailCanDetailsResponseModel response = restTemplate.getForObject(PcdaConstant.COMMON_REPORT_URL
                            + "/getRailTicketCancellationViewDetail?bookingId=" + bookingId,
                    RailCanDetailsResponseModel.class);

            if (response != null && response.getErrorCode() == 200) {
                cancellationDtls = response.getResponse();
            }

        } catch (Exception e) {
            DODLog.printStackTrace(e, RailCancellationReportService.class, LogConstant.RAIL_REPORT);
        }
        
        return cancellationDtls;
    }




}
