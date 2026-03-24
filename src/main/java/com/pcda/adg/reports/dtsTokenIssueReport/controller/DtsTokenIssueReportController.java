package com.pcda.adg.reports.dtsTokenIssueReport.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.adg.reports.dtsTokenIssueReport.model.DtsTokenCountModel;
import com.pcda.adg.reports.dtsTokenIssueReport.model.DtsTokenInputModel;
import com.pcda.adg.reports.dtsTokenIssueReport.service.DtsTokenIssueService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/adg")
public class DtsTokenIssueReportController {

    private final String pageURL = "/ADG/Reports/dtsTokenIssueReport/";

    @Autowired
    private DtsTokenIssueService dtsTokenIssueService;

    @GetMapping("/dtsTokenIssuedReport")
    public String dtsTokenIssueReport(Model model, HttpServletRequest request) {
        SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
        LoginUser loginUser = sessionVisitor.getLoginUser();
        if (loginUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("dtsTokenInputModel", new DtsTokenInputModel());

        return pageURL + "dtsTokenIssueReportPage";
    }


    @GetMapping("/getTokenReport")
    public String getTokenReport(Model model, HttpServletRequest request, DtsTokenInputModel dtsTokenInputModel) {
        SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
        LoginUser loginUser = sessionVisitor.getLoginUser();
        if (loginUser == null) {return "redirect:/login";}

        List<DtsTokenCountModel> tokenIssuedReportList = dtsTokenIssueService.
                getDtsTokenIssuedReport(dtsTokenInputModel.getFromDate(), dtsTokenInputModel.getToDate());

        if (tokenIssuedReportList == null && tokenIssuedReportList.isEmpty()) {
            model.addAttribute("error", "No Records Found");
        }else {
            model.addAttribute("dtsTokenReportList",tokenIssuedReportList);
        }
        model.addAttribute("dtsTokenInputModel", dtsTokenInputModel);

        return pageURL + "dtsTokenIssueReportPage";
    }


    /* AJAX TO SHOW TOKEN COUNT DETAILS REPORT */
    @GetMapping("/getDtsTokenReportDetails")
    public String getDtsTokenReportDtls(@RequestParam String tokenIssueDate, Model model, HttpServletRequest request){
        SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
        LoginUser loginUser = sessionVisitor.getLoginUser();
        if (loginUser == null) {return "redirect:/login";}

        Map<String, Map<String,List<String>>> reportMap = dtsTokenIssueService.getDtsTokenDetailedReport(tokenIssueDate);

        if (!reportMap.entrySet().isEmpty()) {
            model.addAttribute("tokenReportDetailMap",reportMap);
        }else {
            model.addAttribute("error", "No Records Found");
        }

        return pageURL + "tokenReportViewPage";
    }


}
