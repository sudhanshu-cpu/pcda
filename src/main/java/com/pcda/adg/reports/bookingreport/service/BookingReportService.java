package com.pcda.adg.reports.bookingreport.service;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.adg.reports.bookingreport.model.AdgBookingReportInputModel;
import com.pcda.adg.reports.bookingreport.model.BookingReportResponse;
import com.pcda.adg.reports.bookingreport.model.GetBookingRepoModel;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletResponse;

@Service

public class BookingReportService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OfficesService officesService;
	

	public List<OfficeModel> getAllPao() {
		List<OfficeModel> paoList = officesService.getOffices("UNIT", "1");
		DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, BookingReportService.class,
				" Pao ListSize():" + paoList.size());
		return paoList;
	}

	//
	public List<GetBookingRepoModel> getBookingDtls(AdgBookingReportInputModel adgBkgReprtModel) {
		
		
		List<GetBookingRepoModel> modelList = new ArrayList<>();
		String url = "";

		String fromDate=CommonUtil.getChangeFormat(adgBkgReprtModel.getFrmBookingDt(),"dd/MM/yyyy", "yyyy-MM-dd");
		
		String tDate= CommonUtil.getChangeFormat(adgBkgReprtModel.getToBookingDt(),"dd/MM/yyyy", "yyyy-MM-dd");   

    if(adgBkgReprtModel.getReportType().equalsIgnoreCase("1")) {

			url=PcdaConstant.ADG_REPORTS_BASE_URL + "/adgMov/v1/getAdgMovReportDtls?frmDate=" + fromDate + "&accountOffice=" + adgBkgReprtModel.getAccountOffice() + "&reportType=" + adgBkgReprtModel.getReportType();
		}


     else if(adgBkgReprtModel.getReportType().equalsIgnoreCase("2"))
    	 {
			url = PcdaConstant.ADG_REPORTS_BASE_URL + "/adgMov/v1/getAdgMovReportDtls?accountOffice=" + adgBkgReprtModel.getAccountOffice()
					+ "&reportType=" + adgBkgReprtModel.getReportType() + "&month=" + adgBkgReprtModel.getMonth() + "&year=" + adgBkgReprtModel.getYear();

		} 
     else 
    	 if(adgBkgReprtModel.getReportType().equalsIgnoreCase("3") || adgBkgReprtModel.getReportType().equalsIgnoreCase("4"))
    	 {
			url = PcdaConstant.ADG_REPORTS_BASE_URL + "/adgMov/v1/getAdgMovReportDtls?frmDate=" + fromDate + "&toDate="
					+ tDate + "&accountOffice=" + adgBkgReprtModel.getAccountOffice() + "&reportType=" + adgBkgReprtModel.getReportType();
		}


		try {

			ResponseEntity<BookingReportResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<BookingReportResponse>() {
					});

			BookingReportResponse response = responseEntity.getBody();
			if (response != null && response.getErrorCode() == 200 && null!=response.getResponseList()) {
				modelList = response.getResponseList();
			}
			return modelList;
		} catch (Exception e) {
			DODLog.printStackTrace(e, BookingReportService.class, LogConstant.ADG_REPORTS_LOG_FILE);
		}
		DODLog.info(LogConstant.ADG_REPORTS_LOG_FILE, BookingReportService.class,
				" modelList Size : " + modelList.size());
		return modelList;
	}

	// for Excel

	public void createExcelSheet(List<GetBookingRepoModel> modelList, HttpServletResponse response) {

		List<GetBookingRepoModel> list = modelList;
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			
			XSSFSheet spreadsheet = workbook.createSheet("ADG_MOVE_REPORT");

			XSSFRow row = spreadsheet.createRow(0);

			row.createCell(0).setCellValue("TRANSACTION DATE");
			row.createCell(1).setCellValue("NAME");
			row.createCell(2).setCellValue("UNIT GROUP ID");
			row.createCell(3).setCellValue("CONFIRMED WARRANT COUNT");
			row.createCell(4).setCellValue("CONFIRMED CV COUNT");
			row.createCell(5).setCellValue("CONFIRMED FORMD COUNT");
			row.createCell(6).setCellValue("CONFIRMED TOTAL COUNT");
			row.createCell(7).setCellValue("WL WARRANT COUNT");
			row.createCell(8).setCellValue("WL CV COUNT");
			row.createCell(9).setCellValue("WL FORMD COUNT");
			row.createCell(10).setCellValue("WL TOTAL COUNT");
			row.createCell(11).setCellValue("CANCELLED WARRANT COUNT");
			row.createCell(12).setCellValue("CANCELLED CV COUNT");
			row.createCell(13).setCellValue("CANCELLED FORMD COUNT");
			row.createCell(14).setCellValue("CANCELLED TOTAL COUNT");
			row.createCell(15).setCellValue("TOTAL TRANS WARRANT COUNT");
			row.createCell(16).setCellValue("TOTAL TRANS CV COUNT");
			row.createCell(17).setCellValue("TOTAL TRANS FORMD COUNT");
			row.createCell(18).setCellValue("TOTAL TRANSACTION");

			for (GetBookingRepoModel model : list) {

				if(model.getAdgMovSummaryRptBean()!=null ) {
				
					setAdgMovSummaryRpt(spreadsheet,list.size(),model);
			    
				}
				if(model.getAdgMovDailyRptBean()!=null ) {
				
					setAdgMovDailyRpt(spreadsheet,list.size(),model);
					
				}
				
				if(model.getAdgMovMonthlyRptBean()!=null ) {
					
					setAdgMovMonthlyRpt(spreadsheet,list.size(),model);
				
				}
				
			}

		
			
			

			String fileName = "ADGMOV_Report.xlsx";
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "inline;filename=ADGMOV_Report.xlsx");

			FileOutputStream fileOut0 = new FileOutputStream(fileName);
			workbook.write(fileOut0);
			workbook.write(response.getOutputStream()); // Write workbook to response.
             response.getOutputStream().close();


		} catch (Exception e) {
			DODLog.printStackTrace(e, BookingReportService.class, LogConstant.ADG_REPORTS_LOG_FILE);
		}
	}
	
	
	private void setAdgMovSummaryRpt(XSSFSheet spreadsheet,int listSize,GetBookingRepoModel model) {
		
		for (int i = 1; i <= listSize; i++) {
			XSSFRow row = spreadsheet.createRow(i);
					row.createCell(0).setCellValue(model.getFrmDate()+" to "+model.getToDate());
					row.createCell(1).setCellValue(model.getAdgMovSummaryRptBean().getName());
					row.createCell(2).setCellValue(model.getAccountOffice());
				row.createCell(3).setCellValue(model.getAdgMovSummaryRptBean().getConfirmedWarrantCount());
					row.createCell(4).setCellValue(model.getAdgMovSummaryRptBean().getConfirmedCvCount());
					row.createCell(5).setCellValue(model.getAdgMovSummaryRptBean().getConfirmedFormdCount());
					row.createCell(6).setCellValue(model.getAdgMovSummaryRptBean().getConfirmedTotalCount());
					row.createCell(7).setCellValue(model.getAdgMovSummaryRptBean().getWlWarrantCount());
					row.createCell(8).setCellValue(model.getAdgMovSummaryRptBean().getWlCvCount());
					row.createCell(9).setCellValue(model.getAdgMovSummaryRptBean().getWlFormdCount());
					row.createCell(10).setCellValue(model.getAdgMovSummaryRptBean().getWlTotalCount());
					row.createCell(11).setCellValue(model.getAdgMovSummaryRptBean().getCancelledWarrantCount());
					row.createCell(12).setCellValue(model.getAdgMovSummaryRptBean().getCancelledCvCount());
					row.createCell(13).setCellValue(model.getAdgMovSummaryRptBean().getCancelledFormdCount());
					row.createCell(14).setCellValue(model.getAdgMovSummaryRptBean().getCancelledTotalCount());
					row.createCell(15).setCellValue(model.getAdgMovSummaryRptBean().getTotalTransWarrantCount());
					row.createCell(16).setCellValue(model.getAdgMovSummaryRptBean().getTotalTransCvCount());
					row.createCell(17).setCellValue(model.getAdgMovSummaryRptBean().getTotalTransFormdCount());
					row.createCell(18).setCellValue(model.getAdgMovSummaryRptBean().getTotalTransCount());

				}
			}
	
	private void setAdgMovDailyRpt(XSSFSheet spreadsheet,int listSize,GetBookingRepoModel model) {
		
		for (int i = 1; i <= listSize; i++) {
			XSSFRow	row = spreadsheet.createRow(i);
						row.createCell(0).setCellValue(model.getFrmDate()+" to "+model.getToDate());
						row.createCell(1).setCellValue(model.getAdgMovDailyRptBean().getName());
						row.createCell(2).setCellValue(model.getAccountOffice());
					row.createCell(3).setCellValue(model.getAdgMovDailyRptBean().getConfirmedWarrantCount());
						row.createCell(4).setCellValue(model.getAdgMovDailyRptBean().getConfirmedCvCount());
						row.createCell(5).setCellValue(model.getAdgMovDailyRptBean().getConfirmedFormdCount());
						row.createCell(6).setCellValue(model.getAdgMovDailyRptBean().getConfirmedTotalCount());
						row.createCell(7).setCellValue(model.getAdgMovDailyRptBean().getWlWarrantCount());
						row.createCell(8).setCellValue(model.getAdgMovDailyRptBean().getWlCvCount());
						row.createCell(9).setCellValue(model.getAdgMovDailyRptBean().getWlFormdCount());
						row.createCell(10).setCellValue(model.getAdgMovDailyRptBean().getWlTotalCount());
						row.createCell(11).setCellValue(model.getAdgMovDailyRptBean().getCancelledWarrantCount());
						row.createCell(12).setCellValue(model.getAdgMovDailyRptBean().getCancelledCvCount());
						row.createCell(13).setCellValue(model.getAdgMovDailyRptBean().getCancelledFormdCount());
						row.createCell(14).setCellValue(model.getAdgMovDailyRptBean().getCancelledTotalCount());
						row.createCell(15).setCellValue(model.getAdgMovDailyRptBean().getTotalTransWarrantCount());
						row.createCell(16).setCellValue(model.getAdgMovDailyRptBean().getTotalTransCvCount());
						row.createCell(17).setCellValue(model.getAdgMovDailyRptBean().getTotalTransFormdCount());
						row.createCell(18).setCellValue(model.getAdgMovDailyRptBean().getTotalTransCount());

					}
				}
				
	
	private void setAdgMovMonthlyRpt(XSSFSheet spreadsheet,int listSize,GetBookingRepoModel model) {
		
		for (int i = 1; i <= listSize; i++) {
			
			XSSFRow	row = spreadsheet.createRow(i);
						row.createCell(0).setCellValue(model.getFrmDate()+" to "+model.getToDate());
						row.createCell(1).setCellValue(model.getAdgMovMonthlyRptBean().getName());
						row.createCell(2).setCellValue(model.getAccountOffice());
					row.createCell(3).setCellValue(model.getAdgMovMonthlyRptBean().getConfirmedWarrantCount());
						row.createCell(4).setCellValue(model.getAdgMovMonthlyRptBean().getConfirmedCvCount());
						row.createCell(5).setCellValue(model.getAdgMovMonthlyRptBean().getConfirmedFormdCount());
						row.createCell(6).setCellValue(model.getAdgMovMonthlyRptBean().getConfirmedTotalCount());
						row.createCell(7).setCellValue(model.getAdgMovMonthlyRptBean().getWlWarrantCount());
						row.createCell(8).setCellValue(model.getAdgMovMonthlyRptBean().getWlCvCount());
						row.createCell(9).setCellValue(model.getAdgMovMonthlyRptBean().getWlFormdCount());
						row.createCell(10).setCellValue(model.getAdgMovMonthlyRptBean().getWlTotalCount());
						row.createCell(11).setCellValue(model.getAdgMovMonthlyRptBean().getCancelledWarrantCount());
						row.createCell(12).setCellValue(model.getAdgMovMonthlyRptBean().getCancelledCvCount());
						row.createCell(13).setCellValue(model.getAdgMovMonthlyRptBean().getCancelledFormdCount());
						row.createCell(14).setCellValue(model.getAdgMovMonthlyRptBean().getCancelledTotalCount());
						row.createCell(15).setCellValue(model.getAdgMovMonthlyRptBean().getTotalTransWarrantCount());
						row.createCell(16).setCellValue(model.getAdgMovMonthlyRptBean().getTotalTransCvCount());
						row.createCell(17).setCellValue(model.getAdgMovMonthlyRptBean().getTotalTransFormdCount());
						row.createCell(18).setCellValue(model.getAdgMovMonthlyRptBean().getTotalTransCount());

					}
				}
				

}
