package com.pcda.mb.reports.railcancellationreport.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.reports.railcancellationreport.model.CancellationViewDetails;
import com.pcda.mb.reports.railcancellationreport.model.PassengerCanDetailsModel;
import com.pcda.mb.reports.railcancellationreport.model.RailCanDetailsModel;
import com.pcda.mb.reports.railcancellationreport.model.RailCancellationModel;
import com.pcda.mb.reports.railcancellationreport.model.RailCancellationReportInputModel;
import com.pcda.mb.reports.railcancellationreport.service.RailCancellationReportService;
import com.pcda.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/reports")
public class RailCancellationReportController {

    @Autowired
    private OfficesService officesService;

    @Autowired
    private RailCancellationReportService railCancellationReportService;

    private final String pageURL = "/MB/Reports/RailCancellationReport/";

    @GetMapping("/railCancellationReport")
    public String railCancellationReport( Model model, HttpServletRequest request) {
        SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
        LoginUser loginUser = sessionVisitor.getLoginUser();
        if (loginUser == null) {
            return "redirect:/login";
        }
        Optional<OfficeModel> office =  officesService.getOfficeByUserId(loginUser.getUserId());
        office.ifPresent(officeModel -> model.addAttribute("groupId", officeModel.getGroupId()));

        model.addAttribute("railCanInputModel", new RailCancellationReportInputModel());

        return pageURL + "railCancellationReportPage";
    }


    @PostMapping("/getRailCancellationReport")
    public String getRailCanReport(Model model, HttpServletRequest request, RailCancellationReportInputModel railCancellationReportModel) {
        SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
        LoginUser loginUser = sessionVisitor.getLoginUser();
        if (loginUser == null) {
            return "redirect:/login";
        }
        Optional<OfficeModel> officeModelOptional = officesService.getOfficeByUserId(loginUser.getUserId());
        officeModelOptional.ifPresent(officeModel -> railCancellationReportModel.setGroupId(officeModel.getGroupId()));
        railCancellationReportModel.setPersonalNumber(CommonUtil.getDecryptText("Hidden Pass", railCancellationReportModel.getPersonalNumber()));
        List<RailCancellationModel> railCanReportList = railCancellationReportService.getRailCancellationReport(railCancellationReportModel);

        if (railCanReportList != null && !railCanReportList.isEmpty() ){
            model.addAttribute("railCancellationRefundList", railCanReportList);
        }else {
            model.addAttribute("noData", "No Record Found..");
        }
        model.addAttribute("railCanInputModel", railCancellationReportModel);

        return pageURL + "railCancellationReportPage";
    }


    //AJAX FOR RAIL CANCELLATION DETAILS
    @RequestMapping(value = "/getRailTktCancellationDetails", method = {RequestMethod.GET, RequestMethod.POST})
    public String getRailCancellationDetails(@RequestParam String bookingId, Model model, HttpServletRequest request) {

        RailCanDetailsModel railCanDetailsModel = railCancellationReportService.getRailTktCancellationDetails(bookingId);
        List<CancellationViewDetails> cancellationDetailsList = railCanDetailsModel.getCancellationView();
        List<PassengerCanDetailsModel> passengerCanDetailsList = new ArrayList<>();
        cancellationDetailsList.forEach(e -> {
            passengerCanDetailsList.addAll(e.getPassengerDetails());
        });

        model.addAttribute("CancellationDtls", railCanDetailsModel);
        model.addAttribute("paCancellationModels", passengerCanDetailsList);
        return pageURL + "railCancellationDtlsReportDetails";
    }


}
