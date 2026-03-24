package com.pcda.adg.reports.bookingreport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pcda.adg.reports.bookingreport.model.AdgBookingReportInputModel;
import com.pcda.adg.reports.bookingreport.model.ExcelDataList;
import com.pcda.adg.reports.bookingreport.model.GetBookingRepoModel;
import com.pcda.adg.reports.bookingreport.service.BookingReportService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/adg")
public class BookingReportController {

	@Autowired
	private BookingReportService bookingReportService;

	private String path = "ADG/Reports/BookingReport/";

	@GetMapping("/bookingDetailsReport")
	public String getBookingDtls(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		model.addAttribute("fromModel", new AdgBookingReportInputModel());
		if (loginUser == null) {
			return "redirect:/login";
		}
		model.addAttribute("paoList", bookingReportService.getAllPao());
		model.addAttribute("reportType","");

		return path + "adgMovReport";

	}

	// Get Details For Booking Report
	@RequestMapping(value = "/bookingRepoDtls", method = { RequestMethod.GET, RequestMethod.POST })
	public String bookingReortdtls(AdgBookingReportInputModel adgBookingReportInputModel,Model model, HttpServletRequest request) {
		
		
		if(adgBookingReportInputModel.getReportType()==null) {
			model.addAttribute("errors", "DO Not Refresh");	
			return "common/errorPage";
		}
		DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, BookingReportController.class, "### FORM INPUT MODEL ########::" +adgBookingReportInputModel);
		model.addAttribute("fromModel", adgBookingReportInputModel);
		model.addAttribute("paoList", bookingReportService.getAllPao());
		List<GetBookingRepoModel> listModel = bookingReportService.getBookingDtls(adgBookingReportInputModel);
		
		if(listModel.isEmpty()) {
			model.addAttribute("error", "No Record Found");
			
		}else
		{
			for (GetBookingRepoModel list : listModel) {

				if (list.getAdgMovDailyRptBean() != null || list.getAdgMovMonthlyRptBean() != null
						|| list.getAdgMovSummaryRptBean() != null) {
					model.addAttribute("reportList", listModel);
				} else {
					model.addAttribute("error", "No Record Found");
		}
		}
		}
		

 ExcelDataList data = new ExcelDataList();
    data.setModelList(listModel);
         HttpSession session = request.getSession();
         session.setAttribute("DataList", data);
	
		return path + "adgMovReport";
	}
	

	// Export Excel
	@RequestMapping(value = "/bookingRepoExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public void bookingExcelRepo(Model model,HttpServletRequest request,HttpServletResponse response) {
		List<GetBookingRepoModel>  modelList = null;
		HttpSession session= request.getSession();
		Object data= session.getAttribute("DataList");
		
		if(null!=data && data instanceof ExcelDataList dataList) {
			
		 modelList = dataList.getModelList();
		}
			bookingReportService.createExcelSheet(modelList,response);
	}

	
	

}