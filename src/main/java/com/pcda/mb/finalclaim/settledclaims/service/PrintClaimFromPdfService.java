package com.pcda.mb.finalclaim.settledclaims.service;

import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pcda.mb.finalclaim.settledclaims.model.TaDaJourney;
import com.pcda.mb.finalclaim.settledclaims.model.TaDaLocalCon;
import com.pcda.mb.finalclaim.settledclaims.model.TaDaPassDetails;
import com.pcda.mb.finalclaim.settledclaims.model.ViewClaimAdvanceDtlsBean;
import com.pcda.mb.finalclaim.settledclaims.model.ViewClaimCertifyDtlsBean;
import com.pcda.mb.finalclaim.settledclaims.model.ViewClaimConyncDtlsBean;
import com.pcda.mb.finalclaim.settledclaims.model.ViewClaimFoodDtlsBean;
import com.pcda.mb.finalclaim.settledclaims.model.ViewClaimHotelDtlsBean;
import com.pcda.mb.finalclaim.settledclaims.model.ViewClaimLeaveDtlsBean;
import com.pcda.mb.finalclaim.settledclaims.model.ViewClaimPersonalEffectsBean;
import com.pcda.mb.finalclaim.settledclaims.model.ViewClaimRequestBean;
import com.pcda.mb.finalclaim.settledclaims.model.ViewClaimRequestBeanResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class PrintClaimFromPdfService {

	@Autowired
	private RestTemplate restTemplate;

	public ViewClaimRequestBean getClaimSettledPDF(String tadaclaimId) {
		
		ViewClaimRequestBean tadaClaimSettledModel = new ViewClaimRequestBean();

		try {
			String url = PcdaConstant.CLAIM_BASE_URL + "/claimRequest/ViewByClaimId?tadaClaimId=";

			ViewClaimRequestBeanResponse tadaClaimSettledResponse = restTemplate.getForObject(url + tadaclaimId,
					ViewClaimRequestBeanResponse.class, tadaClaimSettledModel);

			if (tadaClaimSettledResponse != null && tadaClaimSettledResponse.getErrorCode() == 200) {
				tadaClaimSettledModel = tadaClaimSettledResponse.getResponse();
			if(tadaClaimSettledModel.getClaimLeaveDtls()!=null) {	
				Set<ViewClaimLeaveDtlsBean> sortedHashSet = tadaClaimSettledModel.getClaimLeaveDtls().stream()
				        .sorted(Comparator.comparing(ViewClaimLeaveDtlsBean::getLeaveDate))
				        .collect(Collectors.toCollection(LinkedHashSet::new));
				
				tadaClaimSettledModel.setClaimLeaveDtls(sortedHashSet);
			}

			}
			

			DODLog.info(LogConstant.SETTLED_CLAIM_LOG_FILE, PrintClaimFromPdfService.class, "[getClaimSettledPDF] ## tadaClaimSettledModel" + tadaClaimSettledModel);

		} catch (Exception e) {
			DODLog.printStackTrace(e, PrintClaimFromPdfService.class, LogConstant.SETTLED_CLAIM_LOG_FILE);
		}

		return tadaClaimSettledModel;

	}

	public boolean createClaimStatementsPDF(String claimId1, HttpServletResponse response, HttpServletRequest request)
			throws Exception {

		try {

			ViewClaimRequestBean content = getClaimSettledPDF(claimId1);
			DODLog.info(LogConstant.SETTLED_CLAIM_LOG_FILE, PrintClaimFromPdfService.class, "[createClaimStatementsPDF] ## View Claim Response::" + content);
			String claimId = claimId1;
			Document document = new Document(PageSize.A4);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			document.open();
			if (claimId != null && !claimId.equals("")) {
				if(content != null) {
					String travelTypeId = content.getTravelTypeId();

					if (travelTypeId.equals("100002")) {
						createTDClaimStatementsPDF(content, document, baos);
					} else if (travelTypeId.equals("100005") || travelTypeId.equals("100006")
							|| travelTypeId.equals("100007") || travelTypeId.equals("100008")) {
						createLTCClaimStatementsPDF(content, document,baos);
					} else if (travelTypeId.equals("100001")) {
						createPTClaimStatementsPDF(content, document,baos);
					}
				}
			}
			
			response.setContentLength(baos.size());
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename=ClaimForm_" + claimId + ".pdf");
			ServletOutputStream out = response.getOutputStream();
			byte[] bytes = baos.toByteArray();
			out.write(bytes);
			out.flush();
			response.flushBuffer();
		} catch (Exception e) {
			DODLog.printStackTrace(e, PrintClaimFromPdfService.class, LogConstant.SETTLED_CLAIM_LOG_FILE);

		}
		return true;
	}

	// ====================================================================================================================================//

	public void createTDClaimStatementsPDF(ViewClaimRequestBean content, Document document, ByteArrayOutputStream baos)
			throws Exception {

		Font fontbold = FontFactory.getFont("", 14, Font.BOLD);
		Font fontbold_8px = FontFactory.getFont("", 8, Font.BOLD);
		Font fontbold_8px_Nml = FontFactory.getFont("", 8, Font.NORMAL);
		BaseColor color =   new BaseColor(226,226,226);
		BaseColor bgColor = BaseColor.GREEN;

		String personalVisitor = content.getPersonalNo();

		 PdfWriter writer=PdfWriter.getInstance(document, baos);
     	document.open();
		 
		 
		PdfPTable claimtable = new PdfPTable(1);
		claimtable.getDefaultCell().setBorder(0);
		claimtable.setWidthPercentage(100f);

		PdfPCell cell = new PdfPCell(new Paragraph("CLAIM FOR MOVE ON TEMPORARY DUTY (TOUR)", fontbold));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		claimtable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Claim ID: " + content.getTadaClaimId(), fontbold_8px));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

		claimtable.addCell(cell);

		setEmptyCell(claimtable, 1, 2, false);

		PdfPTable personalInfoTable = new PdfPTable(4);
		personalInfoTable.getDefaultCell().setBorder(0);
		int personalInfotable_widths[] = { 25, 25, 25, 25 };
		personalInfoTable.setWidths(personalInfotable_widths);

		cell = new PdfPCell(new Paragraph("Name, rank and unit of claimant", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getName() + " (" + content.getLevelName() + ")", fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		setEmptyCell(personalInfoTable, 1, 2, true);

		cell = new PdfPCell(new Paragraph("Orders for move", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getMoveSanctionNo() + "\n dated " + content.getMovSanctionDate(),
				fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Authority (Rule in TR/SR)", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getTrRuleName(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Station from which Journey Commenced", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getJrnyStrtdFrom(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Date/Time of start", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getJrnyStrtdTime(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Personal/PAN No.", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getPersonalNo(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Travel ID", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getTravelID(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		claimtable.addCell(personalInfoTable);

		setEmptyCell(claimtable, 1, 1, false);

		cell = new PdfPCell(new Paragraph(
				"Details of journey by Road, Rail, Air, Steamer, etc. and DA for journey/halt", fontbold_8px));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		claimtable.addCell(cell);

		setEmptyCell(claimtable, 1, 1, false);

		if (content.getTaDaJourneyDetails() == null) {
		}

		if (content.getTaDaJourneyDetails() != null) {
			List<TaDaJourney> jrnyDtls = content.getTaDaJourneyDetails().getTaDaJourney();

			if (null != jrnyDtls && jrnyDtls.size() > 0) {

				PdfPTable jrnyDtlsTable = new PdfPTable(10);
				jrnyDtlsTable.getDefaultCell().setBorder(0);
				int[] jrnyDtlsTable_widths = { 10, 9, 11, 9, 10, 10, 10, 10, 10, 10};
				jrnyDtlsTable.setWidths(jrnyDtlsTable_widths);

				cell = new PdfPCell(new Paragraph("Departure Place", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				jrnyDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Departure Date & Time", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				jrnyDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Arrival Place", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				jrnyDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Arrival Date & Time", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				jrnyDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Distance by Road in Km", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				jrnyDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Conveyance Mode & Class", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				jrnyDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Bill/PNR/Ticket No.", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				jrnyDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Booking Amount", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				jrnyDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Journey Performed?", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				jrnyDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Refund Amount", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				jrnyDtlsTable.addCell(cell);

				

				for (TaDaJourney detailsPDF : jrnyDtls) {

					cell = new PdfPCell(new Paragraph(detailsPDF.getFromStation(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getJourneyDate(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getToStation(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getJourneyEndDate(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getDistance(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getTravelMode() + "\n" + detailsPDF.getTravelMode(),
							fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getPnrOrAirTxnd(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(String.valueOf(detailsPDF.getBookingAmount()), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getJourneyPerformed(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(String.valueOf(detailsPDF.getRefundAmount()), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDtlsTable.addCell(cell);

					

				}
				claimtable.addCell(jrnyDtlsTable);
			}
		}
		Set<ViewClaimLeaveDtlsBean> leaveDetailsTableData = content.getClaimLeaveDtls();

		

		if (null != leaveDetailsTableData && !leaveDetailsTableData.isEmpty()) {
			PdfPTable leaveDtlsTable = new PdfPTable(2);
			leaveDtlsTable.getDefaultCell().setBorder(0);
			int[] leaveDtlsTable_widths = { 50, 50};
			leaveDtlsTable.setWidths(leaveDtlsTable_widths);

			cell = new PdfPCell(new Paragraph("Leave Period", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(BaseColor.GRAY);
			cell.setColspan(2);
			leaveDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Leave Date", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			leaveDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Full/Half", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			leaveDtlsTable.addCell(cell);

			for (ViewClaimLeaveDtlsBean viewClaimLeaveDtlsBean : leaveDetailsTableData) {

				cell = new PdfPCell(new Paragraph(
						CommonUtil.formatDate(viewClaimLeaveDtlsBean.getLeaveDate(), "dd/MM/yyyy"), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				leaveDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(viewClaimLeaveDtlsBean.getLeaveFullHalf(), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				leaveDtlsTable.addCell(cell);
			}

			claimtable.addCell(leaveDtlsTable);
		}

		Set<ViewClaimHotelDtlsBean> hotelDetailsTableData = content.getClaimHotelDtls();
		
		if (null != hotelDetailsTableData && !hotelDetailsTableData.isEmpty()) {

			PdfPTable hotelDtlsTable = new PdfPTable(8);
			hotelDtlsTable.getDefaultCell().setBorder(0);
			int[] hotelDtlsTable_widths = { 12, 13, 12, 13, 13, 12, 12, 12};
			hotelDtlsTable.setWidths(hotelDtlsTable_widths);

			cell = new PdfPCell(new Paragraph("Hotel Bill Details", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(BaseColor.GRAY);
			cell.setColspan(8);
			hotelDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Hotel/Mess", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			hotelDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Hotel/MESS Name & Location", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			hotelDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("GST No.", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			hotelDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Check In Date & Time", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			hotelDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Check Out Date & Time", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			hotelDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("No of Days", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			hotelDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Hotel Bill (Without GST)", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			hotelDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("GST Amount", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			hotelDtlsTable.addCell(cell);

			

			

				for (ViewClaimHotelDtlsBean viewClaimHotelDtlsBean : hotelDetailsTableData) {

					cell = new PdfPCell(new Paragraph(viewClaimHotelDtlsBean.getStayLocation(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hotelDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(viewClaimHotelDtlsBean.getHotelName() + " ("
							+ viewClaimHotelDtlsBean.getHotelLocation() + ")", fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hotelDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(viewClaimHotelDtlsBean.getGstNo(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hotelDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(
							CommonUtil.formatDate(viewClaimHotelDtlsBean.getCheckInTime(), "dd/MM/yyyy HH:mm"),
							fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hotelDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(
							CommonUtil.formatDate(viewClaimHotelDtlsBean.getCheckOutTime(), "dd/MM/yyyy HH:mm"),
							fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hotelDtlsTable.addCell(cell);

					cell = new PdfPCell(
							new Paragraph(String.valueOf(viewClaimHotelDtlsBean.getNoOfDay()), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hotelDtlsTable.addCell(cell);

					cell = new PdfPCell(
							new Paragraph(String.valueOf(viewClaimHotelDtlsBean.getBillAmount()), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hotelDtlsTable.addCell(cell);

					cell = new PdfPCell(
							new Paragraph(String.valueOf(viewClaimHotelDtlsBean.getGstAmount()), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					hotelDtlsTable.addCell(cell);

					

				}
				claimtable.addCell(hotelDtlsTable);
			
		}

			Set<ViewClaimFoodDtlsBean> foodDetailsTableData = content.getClaimFoodDtls();
			
			if (null != foodDetailsTableData && !foodDetailsTableData.isEmpty()) {

				PdfPTable foodDtlsTable = new PdfPTable(2);
				foodDtlsTable.getDefaultCell().setBorder(0);
				int[] foodDtlsTable_widths = { 50, 50};
				foodDtlsTable.setWidths(foodDtlsTable_widths);

				cell = new PdfPCell(new Paragraph("Food Bill Details", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(BaseColor.GRAY);
				cell.setColspan(2);
				foodDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("No of Days", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				foodDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Amount", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				foodDtlsTable.addCell(cell);

				

				for (ViewClaimFoodDtlsBean viewClaimFoodDtlsBean : foodDetailsTableData) {

					cell = new PdfPCell(
							new Paragraph(String.valueOf(viewClaimFoodDtlsBean.getNoOfDay()), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					foodDtlsTable.addCell(cell);

					cell = new PdfPCell(
							new Paragraph(String.valueOf(viewClaimFoodDtlsBean.getBillAmount()), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					foodDtlsTable.addCell(cell);

					

				}

				claimtable.addCell(foodDtlsTable);
			}

			ViewClaimConyncDtlsBean localConDetailsTableData = content.getClaimConyncDtls();
			
			if (null != localConDetailsTableData) {

				if (localConDetailsTableData.getIsConveyanceDtls() == 0) {

					PdfPTable localDtlsTable = new PdfPTable(5);
					localDtlsTable.getDefaultCell().setBorder(0);
					int[] localDtlsTable_widths = { 20, 20, 20, 20, 20};
					localDtlsTable.setWidths(localDtlsTable_widths);

					cell = new PdfPCell(new Paragraph("Taxi Charges within TD City", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBackgroundColor(BaseColor.GRAY);
					cell.setColspan(5);
					localDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Vehicle No", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(color);
					localDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Date of Travel", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(color);
					localDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Distance", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(color);
					localDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Bill No", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(color);
					localDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Amount", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(color);
					localDtlsTable.addCell(cell);

					

					for (TaDaLocalCon taDaLocalCon : localConDetailsTableData.getTaDaLocalCon()) {

						cell = new PdfPCell(new Paragraph(taDaLocalCon.getVehicleNo(), fontbold_8px_Nml));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						localDtlsTable.addCell(cell);

						cell = new PdfPCell(new Paragraph(
								CommonUtil.formatDate(taDaLocalCon.getDateOftravel(), "dd/MM/yyyy"), fontbold_8px_Nml));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						localDtlsTable.addCell(cell);

						cell = new PdfPCell(
								new Paragraph(String.valueOf(taDaLocalCon.getDistance()), fontbold_8px_Nml));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						localDtlsTable.addCell(cell);

						cell = new PdfPCell(new Paragraph(taDaLocalCon.getBillNo(), fontbold_8px_Nml));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						localDtlsTable.addCell(cell);

						cell = new PdfPCell(
								new Paragraph(String.valueOf(taDaLocalCon.getBillAmount()), fontbold_8px_Nml));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						localDtlsTable.addCell(cell);

					

					}

					claimtable.addCell(localDtlsTable);

				} else {

					PdfPTable localDtlsTable = new PdfPTable(2);
					localDtlsTable.getDefaultCell().setBorder(0);
					int[] localDtlsTable_widths = { 50, 50};
					localDtlsTable.setWidths(localDtlsTable_widths);

					cell = new PdfPCell(new Paragraph("Taxi Charges within TD City", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBackgroundColor(BaseColor.GRAY);
					cell.setColspan(2);
					localDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Number of Days", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(color);
					localDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Amount", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(color);
					localDtlsTable.addCell(cell);

					
					for (TaDaLocalCon taDaLocalCon : localConDetailsTableData.getTaDaLocalCon()) {
						cell = new PdfPCell(new Paragraph(String.valueOf(taDaLocalCon.getNoOfDay()), fontbold_8px_Nml));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						localDtlsTable.addCell(cell);

						cell = new PdfPCell(
								new Paragraph(String.valueOf(taDaLocalCon.getBillAmount()), fontbold_8px_Nml));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						localDtlsTable.addCell(cell);

						

					}

					claimtable.addCell(localDtlsTable);
				}
			}

			ViewClaimAdvanceDtlsBean advanceDetailsTableData = content.getClaimAdvanceDtls();
			

			if (null != advanceDetailsTableData) {

				PdfPTable advDtlsTable = new PdfPTable(7);
				advDtlsTable.getDefaultCell().setBorder(0);
				int[] advDtlsTable_widths = { 14, 14, 14, 14, 14, 14, 14};
				advDtlsTable.setWidths(advDtlsTable_widths);

				cell = new PdfPCell(new Paragraph("Advances Drawn", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(BaseColor.GRAY);
				cell.setColspan(7);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Advance Drawn from DTS", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Advance Drawn from PAO", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Date of Advance Drawn from PAO", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Advance Drawn from Other Sources", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Voucher No for Advance Drawn from Other Sources", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Date of Advance Drawn from Other Sources", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				

				cell = new PdfPCell(
						new Paragraph(String.valueOf(advanceDetailsTableData.getDaAdvanceAmount()), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(
						new Paragraph(String.valueOf(advanceDetailsTableData.getPaoAdvanceAmount()), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getPaoAdvanceDate(), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("", fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(
						new Paragraph(String.valueOf(advanceDetailsTableData.getOtherAdvncAmnt()), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getOtherAdvncRefId(), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getOtherAdvncDate(), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);


				claimtable.addCell(advDtlsTable);


				PdfPTable refDtlsTable = new PdfPTable(3);
				refDtlsTable.getDefaultCell().setBorder(0);
				int[] refDtlsTable_widths = { 33, 33, 33};
				refDtlsTable.setWidths(refDtlsTable_widths);

				cell = new PdfPCell(new Paragraph("Advances Refunded", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(BaseColor.GRAY);
				cell.setColspan(3);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("eMRO/MRO/ Refund Amount", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("eMRO/MRO/Refund No", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("eMRO/MRO Submission Date/Refund Date", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				refDtlsTable.addCell(cell);



				cell = new PdfPCell(
						new Paragraph(String.valueOf(advanceDetailsTableData.getMroRefundAmount()), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getMroRefundRefId(), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getMroRefundDate(), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				refDtlsTable.addCell(cell);

				setEmptyCell(refDtlsTable, 1, 3, false);

				cell = new PdfPCell(new Paragraph("Total Amount of Claim(Submitted)", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				cell.setColspan(2);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(String.valueOf(content.getTotalSpentAmount()), fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				refDtlsTable.addCell(cell);


				cell = new PdfPCell(new Paragraph("less: DA Advance Taken(User)", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				cell.setColspan(2);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(String.valueOf(content.getTotalAdvanceAmount()), fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("add: e-MRO Refunds(User)", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				cell.setColspan(2);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(String.valueOf(content.getTotalRefundAmount()), fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Payable/Recoverable Amount", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				cell.setColspan(2);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(String.valueOf(content.getTotalClaimedAmount()), fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				refDtlsTable.addCell(cell);

				claimtable.addCell(refDtlsTable);


			}

			PdfPTable certifyDtlsTable = new PdfPTable(1);
			certifyDtlsTable.getDefaultCell().setBorder(0);

			setEmptyCell(certifyDtlsTable, 1, 1, false);

			cell = new PdfPCell(new Paragraph("Certified that:", fontbold_8px));
			cell.setBorder(PdfPCell.NO_BORDER);
			certifyDtlsTable.addCell(cell);

			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			com.itextpdf.text.List list = new com.itextpdf.text.List();

			Set<ViewClaimCertifyDtlsBean> certifyDetailsTableData = content.getClaimCertifyView();
			
			if (null != certifyDetailsTableData && !certifyDetailsTableData.isEmpty()) {
				for (ViewClaimCertifyDtlsBean veClaimCertifyDtlsBean : certifyDetailsTableData) {

					list.add(new ListItem(
							new Chunk(String.valueOf(veClaimCertifyDtlsBean.getCertifyQuestion()), fontbold_8px_Nml)));

				}
			}

			cell.addElement(list);
			certifyDtlsTable.addCell(cell);

			claimtable.addCell(certifyDtlsTable);

			cell = new PdfPCell(new Paragraph("Countersigned as correct claim by", fontbold_8px));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingTop(30);
			claimtable.addCell(cell);

			setEmptyCell(claimtable, 2, 1, false);

			cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getAuthorityName(), fontbold_8px));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			claimtable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getName(), fontbold_8px));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingTop(20);
			claimtable.addCell(cell);

			cell = new PdfPCell(
					new Paragraph("Signature and designation of the Officer countersigning the claim", fontbold_8px));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingTop(20);
			claimtable.addCell(cell);

			document.add(claimtable);
			document.close();

		}
	

/////////////////////////////////////////////////

	public void createLTCClaimStatementsPDF(ViewClaimRequestBean content, Document document,ByteArrayOutputStream baos) throws Exception {

		Font fontbold = FontFactory.getFont("", 14, Font.BOLD);
		Font fontbold_8px = FontFactory.getFont("", 8, Font.BOLD);
		Font fontbold_8px_Nml = FontFactory.getFont("", 8, Font.NORMAL);
		BaseColor color = new BaseColor(226,226,226);
	//	BaseColor bgColor = BaseColor.GREEN;
		
		 PdfWriter writer=PdfWriter.getInstance(document, baos);
      	document.open();
		
		try {

			PdfPTable claimtable = new PdfPTable(1);
			claimtable.getDefaultCell().setBorder(0);
			claimtable.setWidthPercentage(100f);

			PdfPCell cell = new PdfPCell(new Paragraph("CLAIM FOR MOVE ON LEAVE TRAVEL CONCESSION (LTC)", fontbold));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);

			claimtable.addCell(cell);

			setEmptyCell(claimtable, 1, 1, false);

			cell = new PdfPCell(new Paragraph("Claim ID: " + content.getTadaClaimId(), fontbold_8px));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

			claimtable.addCell(cell);
			setEmptyCell(claimtable, 1, 1, false);

			PdfPTable personalInfoTable = new PdfPTable(8);
			personalInfoTable.getDefaultCell().setBorder(0);
			int[] personalInfotable_widths = { 13, 13, 13, 13, 12, 12, 12, 12 };
			personalInfoTable.setWidths(personalInfotable_widths);

			cell = new PdfPCell(new Paragraph("Personal Details", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(BaseColor.GRAY);
			cell.setColspan(8);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Name of Claimant", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getName(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Designation", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getDesignatin(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Level", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getLevelName(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Office / Unit", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);


			cell = new PdfPCell(new Paragraph("Move Sanction No", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getMoveSanctionNo(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Move Sanction Date", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getMovSanctionDate(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Authority (Rule in TR/SR)", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getTrRuleName(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Personal/Staff No", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getPersonalNo(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Leave Details", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("From", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getLeaveFrom(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("To", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getLeaveTo(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Date/Time of start", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getJrnyStrtdTime(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Home Town", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getHometown(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Declared Leave Station", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getLeaveStn(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("LTC Block Year", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getLtcBlockYr(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("LTC Calendar Year", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getLtcCalYr(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Travel ID", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			personalInfoTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getTravelID(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(7);
			personalInfoTable.addCell(cell);

			claimtable.addCell(personalInfoTable);

			setEmptyCell(claimtable, 1, 1, false);

			PdfPTable jrnyDetailsTable = new PdfPTable(8);
			jrnyDetailsTable.getDefaultCell().setBorder(0);
			int[] jrnyDetailsTable_widths = { 13, 13, 13, 13, 12, 12, 12, 12 };
			
			jrnyDetailsTable.setWidths(jrnyDetailsTable_widths);

			cell = new PdfPCell(new Paragraph("Journey Details", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(BaseColor.GRAY);
			cell.setColspan(8);
			jrnyDetailsTable.addCell(cell);

			claimtable.addCell(jrnyDetailsTable);

			
			if (content.getTaDaJourneyDetails() != null) {
				List<TaDaJourney> jrnyDtls = content.getTaDaJourneyDetails().getTaDaJourney();

				for (TaDaJourney detailsPDF : jrnyDtls) {

					jrnyDetailsTable = new PdfPTable(8);
					jrnyDetailsTable.getDefaultCell().setBorder(0);
					jrnyDetailsTable.setWidths(jrnyDetailsTable_widths);

					cell = new PdfPCell(new Paragraph("Journey Type:" + detailsPDF.getJourneyType(), fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setBackgroundColor(color);
					cell.setColspan(8);
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Mode of Travel", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getTravelMode(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Departure Place", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getFromStation(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Arrival Place", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getToStation(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Class of Accomodation", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getTravelMode(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Departure Time", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getJourneyDate(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Arrival Time", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					if (null != detailsPDF.getJourneyDate()) {
						cell = new PdfPCell(new Paragraph(detailsPDF.getJourneyEndDate(), fontbold_8px_Nml));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
							cell.setBackgroundColor(new BaseColor(144,238,144));
						}
						jrnyDetailsTable.addCell(cell);
					} else {
						cell = new PdfPCell(new Paragraph("NA", fontbold_8px_Nml));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
							cell.setBackgroundColor(new BaseColor(144,238,144));
						}
						jrnyDetailsTable.addCell(cell);
					}

					cell = new PdfPCell(new Paragraph("Sector", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getSector(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Bill/PNR/ Ticket No.", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getPnrOrAirTxnd(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Distance(KM)", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(detailsPDF.getDistance(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Total Fare", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(String.valueOf(detailsPDF.getBookingAmount()), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Total Refund", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(String.valueOf(detailsPDF.getRefundAmount()), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Total Claimed Amount", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(String.valueOf(detailsPDF.getClaimedAmount()), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);



					PdfPTable jrnyPassTable = new PdfPTable(8);
					jrnyPassTable.getDefaultCell().setBorder(0);
					int[] jrnyPassTable_widths = { 13, 13, 13, 13, 12, 12, 12, 12 };
					
					jrnyPassTable.setWidths(jrnyPassTable_widths);

					cell = new PdfPCell(new Paragraph("Traveller Name", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Relation", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Age", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Ticket No", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Journey Performed?", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Booking Amount", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Refund Amount", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Claimed Amount", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					List<TaDaPassDetails> claimPassDetailsTable = detailsPDF.getTaDaPassDetails();
					if (null != claimPassDetailsTable && !claimPassDetailsTable.isEmpty()) {
						for (TaDaPassDetails claimPassDetailsTableData : claimPassDetailsTable) {
							cell = new PdfPCell(
									new Paragraph(claimPassDetailsTableData.getTravellerName(), fontbold_8px_Nml));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							jrnyPassTable.addCell(cell);

							cell = new PdfPCell(
									new Paragraph(claimPassDetailsTableData.getRelation(), fontbold_8px_Nml));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							jrnyPassTable.addCell(cell);

							cell = new PdfPCell(new Paragraph(String.valueOf(claimPassDetailsTableData.getAge()),
									fontbold_8px_Nml));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							jrnyPassTable.addCell(cell);

							cell = new PdfPCell(
									new Paragraph(claimPassDetailsTableData.getTicketNo(), fontbold_8px_Nml));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							jrnyPassTable.addCell(cell);

							cell = new PdfPCell(
									new Paragraph(claimPassDetailsTableData.getJourneyPerformed(), fontbold_8px_Nml));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							jrnyPassTable.addCell(cell);

							cell = new PdfPCell(new Paragraph(
									String.valueOf(claimPassDetailsTableData.getBookingAmount()), fontbold_8px_Nml));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							jrnyPassTable.addCell(cell);

							cell = new PdfPCell(new Paragraph(
									String.valueOf(claimPassDetailsTableData.getRefundAmount()), fontbold_8px_Nml));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							jrnyPassTable.addCell(cell);

							cell = new PdfPCell(new Paragraph(
									String.valueOf(claimPassDetailsTableData.getClaimedAmount()), fontbold_8px_Nml));
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							jrnyPassTable.addCell(cell);
						}
					}
					cell = new PdfPCell(jrnyPassTable);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setBorderWidth(2);
					cell.setBorderColor(new BaseColor(0,58,112));
					cell.setColspan(8);
					if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
						cell.setBackgroundColor(new BaseColor(144,238,144));
					}
					jrnyDetailsTable.addCell(cell);

					setEmptyCell(jrnyDetailsTable, 1, 8, false);

					claimtable.addCell(jrnyDetailsTable);
				}
			}

			setEmptyCell(claimtable, 1, 1, false);

			ViewClaimAdvanceDtlsBean advanceDetailsTableData = content.getClaimAdvanceDtls();
			
			if (null != advanceDetailsTableData) {

				PdfPTable advDtlsTable = new PdfPTable(7);
				advDtlsTable.getDefaultCell().setBorder(0);
				int[] advDtlsTable_widths = { 14, 14, 14, 14, 14, 14, 14 };
				advDtlsTable.setWidths(advDtlsTable_widths);

				cell = new PdfPCell(new Paragraph("Advances Drawn", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(BaseColor.GRAY);
				cell.setColspan(7);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Advance Drawn from PAO", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Date of Advance Drawn from PAO", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(2);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Advance Drawn from Other Sources", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Voucher No for Advance Drawn from Other Sources", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Date of Advance Drawn from Other Sources", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(
						new Paragraph(String.valueOf(advanceDetailsTableData.getPaoAdvanceAmount()), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getPaoAdvanceDate()!=null?advanceDetailsTableData.getPaoAdvanceDate():"", fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("", fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(2);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(
						new Paragraph(String.valueOf(advanceDetailsTableData.getOtherAdvncAmnt()), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getOtherAdvncRefId()!=null?advanceDetailsTableData.getOtherAdvncRefId():"", fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				advDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getOtherAdvncDate()!=null?advanceDetailsTableData.getOtherAdvncDate():"", fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				advDtlsTable.addCell(cell);

				claimtable.addCell(advDtlsTable);


				PdfPTable refDtlsTable = new PdfPTable(3);
				refDtlsTable.getDefaultCell().setBorder(0);
				int[] refDtlsTable_widths = { 33, 33, 33};
				refDtlsTable.setWidths(refDtlsTable_widths);

				cell = new PdfPCell(new Paragraph("Advances Refunded", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(BaseColor.GRAY);
				cell.setColspan(3);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("eMRO/MRO/ Refund Amount", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("eMRO/MRO/Refund No", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("eMRO/MRO Submission Date/Refund Date", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(
						new Paragraph(String.valueOf(advanceDetailsTableData.getMroRefundAmount()), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getMroRefundRefId()!=null?advanceDetailsTableData.getMroRefundRefId():"", fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getMroRefundDate()!=null?advanceDetailsTableData.getMroRefundDate():"", fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				refDtlsTable.addCell(cell);



				setEmptyCell(refDtlsTable, 1, 3, false);



				cell = new PdfPCell(new Paragraph("Total Amount of Claim(Submitted)", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				cell.setColspan(2);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(String.valueOf(content.getTotalSpentAmount()), fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				refDtlsTable.addCell(cell);


				cell = new PdfPCell(new Paragraph("less: DA Advance Taken(User)", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				cell.setColspan(2);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(String.valueOf(content.getTotalAdvanceAmount()), fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("add: e-MRO Refunds(User)", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				cell.setColspan(2);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(String.valueOf(content.getTotalRefundAmount()), fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph("Payable/Recoverable Amount", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				cell.setColspan(2);
				refDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(String.valueOf(content.getTotalClaimedAmount()), fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBackgroundColor(color);
				refDtlsTable.addCell(cell);

				claimtable.addCell(refDtlsTable);

			}

			PdfPTable certifyDtlsTable = new PdfPTable(1);
			certifyDtlsTable.getDefaultCell().setBorder(0);

			setEmptyCell(certifyDtlsTable, 1, 1, false);

			cell = new PdfPCell(new Paragraph("Certified that:", fontbold_8px));
			cell.setBorder(PdfPCell.NO_BORDER);
			certifyDtlsTable.addCell(cell);

			cell = new PdfPCell();
			cell.setBorder(PdfPCell.NO_BORDER);
			com.itextpdf.text.List list = new com.itextpdf.text.List();

			Set<ViewClaimCertifyDtlsBean> certifyDetailsTableData = content.getClaimCertifyView();
		

			if (null != certifyDetailsTableData && !certifyDetailsTableData.isEmpty()) {
				for (ViewClaimCertifyDtlsBean viewClaimCertifyDtlsBean : certifyDetailsTableData) {
					list.add(new ListItem(new Chunk(viewClaimCertifyDtlsBean.getCertifyQuestion(), fontbold_8px_Nml)));
				}
			}

			cell.addElement(list);
			certifyDtlsTable.addCell(cell);

			claimtable.addCell(certifyDtlsTable);

			cell = new PdfPCell(new Paragraph("Countersigned as correct claim by", fontbold_8px));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingTop(30);
			claimtable.addCell(cell);

			setEmptyCell(claimtable, 2, 1, false);

			cell = new PdfPCell(new Paragraph(advanceDetailsTableData!=null ? advanceDetailsTableData.getAuthorityName():"", fontbold_8px));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			claimtable.addCell(cell);

			cell = new PdfPCell(new Paragraph(content.getName(), fontbold_8px));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingTop(20);
			claimtable.addCell(cell);

			cell = new PdfPCell(
					new Paragraph("Signature and designation of the Officer countersigning the claim", fontbold_8px));
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingTop(20);
			claimtable.addCell(cell);

			document.add(claimtable);
			document.close();

		}

		catch (Exception e) {
			DODLog.printStackTrace(e, PrintClaimFromPdfService.class, LogConstant.SETTLED_CLAIM_LOG_FILE);
		}

	}

	public void createPTClaimStatementsPDF(ViewClaimRequestBean content, Document document,ByteArrayOutputStream baos) throws Exception {

		Font fontbold = FontFactory.getFont("", 14, Font.BOLD);
		Font fontbold_8px = FontFactory.getFont("", 8, Font.BOLD);
		Font fontbold_8px_Nml = FontFactory.getFont("", 8, Font.NORMAL);
		BaseColor color = new BaseColor(226,226,226);
	//	BaseColor bgColor = BaseColor.GREEN;

		PdfPTable claimtable = new PdfPTable(1);
		claimtable.getDefaultCell().setBorder(0);
		claimtable.setWidthPercentage(100f);
		
		 PdfWriter writer=PdfWriter.getInstance(document, baos);
		  	document.open();

		PdfPCell cell = new PdfPCell(
				new Paragraph("TRAVELLING ALLOWANCE CLAIM FOR MOVES ON PERMANENT TRANSFER", fontbold));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		claimtable.addCell(cell);

		setEmptyCell(claimtable, 1, 1, false);

		cell = new PdfPCell(new Paragraph("Claim ID: " + content.getTadaClaimId(), fontbold_8px));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

		claimtable.addCell(cell);

		setEmptyCell(claimtable, 1, 1, false);

		PdfPTable personalInfoTable = new PdfPTable(8);
		personalInfoTable.getDefaultCell().setBorder(0);
		int[] personalInfotable_widths = { 13, 13, 13, 13, 12, 12, 12, 12 };
		personalInfoTable.setWidths(personalInfotable_widths);

		cell = new PdfPCell(new Paragraph("Details in respect of the Claimant", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBackgroundColor(BaseColor.GRAY);
		cell.setColspan(8);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Name", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getName(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Corps/Office", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getUnitName(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Personal/Staff No", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getPersonalNo(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Pay Account No", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getPayAccntNo(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Pay Level", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getLevelName(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Basic Pay", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getBasicPay(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Transferred from", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getTrnsfrFrom(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Transferred To", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getTrnsfrTo(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Departure Place", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getJrnyStrtdFrom(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Departure Date", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getJrnyStrtdTime(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Arrival Place", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getJrnyStrtdTo(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Arrival Date", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getJrnyFnshTime(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Rule in TR/SR", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getTrRuleName(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Move Sanction No", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getMoveSanctionNo(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Move Sanction Date", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getMovSanctionDate(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Move Issuing Authority", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getMoveIssungAuth(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph("Travel ID", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		personalInfoTable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getTravelStartDate(), fontbold_8px_Nml));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(7);
		personalInfoTable.addCell(cell);

		claimtable.addCell(personalInfoTable);

		setEmptyCell(claimtable, 1, 1, false);

		PdfPTable jrnyDetailsTable = new PdfPTable(8);
		jrnyDetailsTable.getDefaultCell().setBorder(0);
		int[] jrnyDetailsTable_widths = { 13, 13, 13, 13, 12, 12, 12, 12 };
		
		jrnyDetailsTable.setWidths(jrnyDetailsTable_widths);

		cell = new PdfPCell(new Paragraph("Journey Details", fontbold_8px));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBackgroundColor(BaseColor.GRAY);
		cell.setColspan(8);
		jrnyDetailsTable.addCell(cell);

		claimtable.addCell(jrnyDetailsTable);

		
		if (content.getTaDaJourneyDetails() == null) {
		}

		if (content.getTaDaJourneyDetails() != null) {

		List<TaDaJourney> jrnyDtls = content.getTaDaJourneyDetails().getTaDaJourney();

		for (TaDaJourney detailsPDF : jrnyDtls) {

			jrnyDetailsTable = new PdfPTable(8);
			jrnyDetailsTable.getDefaultCell().setBorder(0);
			jrnyDetailsTable.setWidths(jrnyDetailsTable_widths);

			cell = new PdfPCell(new Paragraph("Journey Type:" + detailsPDF.getJourneyType(), fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBackgroundColor(color);
			cell.setColspan(8);
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Mode of Travel", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(detailsPDF.getTravelMode(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Departure Place", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(detailsPDF.getFromStation(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Arrival Place", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(detailsPDF.getToStation(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Class of Accomodation", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(detailsPDF.getTravelMode(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Departure Time", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(detailsPDF.getJourneyDate(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Arrival Time", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			if (null != detailsPDF.getJourneyEndDate()) {
				cell = new PdfPCell(new Paragraph(detailsPDF.getJourneyEndDate(), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
					cell.setBackgroundColor(new BaseColor(144,238,144));
				}
				jrnyDetailsTable.addCell(cell);
			} else {
				cell = new PdfPCell(new Paragraph("NA", fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
					cell.setBackgroundColor(new BaseColor(144,238,144));
				}
				jrnyDetailsTable.addCell(cell);
			}

			cell = new PdfPCell(new Paragraph("Sector", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(detailsPDF.getSector(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Bill/PNR/ Ticket No.", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(detailsPDF.getPnrOrAirTxnd(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Distance(KM)", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(detailsPDF.getDistance(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Total Fare", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(String.valueOf(detailsPDF.getBookingAmount()), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Total Refund", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(String.valueOf(detailsPDF.getRefundAmount()), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Total Claimed Amount", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(String.valueOf(detailsPDF.getClaimedAmount()), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}
			jrnyDetailsTable.addCell(cell);



			PdfPTable jrnyPassTable = new PdfPTable(8);
			jrnyPassTable.getDefaultCell().setBorder(0);
			int[] jrnyPassTable_widths = { 13, 13, 13, 13, 12, 12, 12, 12 };
			
			jrnyPassTable.setWidths(jrnyPassTable_widths);

			cell = new PdfPCell(new Paragraph("Traveller Name", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			jrnyPassTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Relation", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			jrnyPassTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Age", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			jrnyPassTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Ticket No", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			jrnyPassTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Journey Performed?", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			jrnyPassTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Booking Amount", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			jrnyPassTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Refund Amount", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			jrnyPassTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Claimed Amount", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			jrnyPassTable.addCell(cell);

			List<TaDaPassDetails> claimPassDetailsTable = detailsPDF.getTaDaPassDetails();
			if (null != claimPassDetailsTable && !claimPassDetailsTable.isEmpty()) {
				for (TaDaPassDetails claimPassDetailsTableData : claimPassDetailsTable) {
					cell = new PdfPCell(new Paragraph(claimPassDetailsTableData.getTravellerName(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(claimPassDetailsTableData.getRelation(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					cell = new PdfPCell(
							new Paragraph(String.valueOf(claimPassDetailsTableData.getAge()), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(claimPassDetailsTableData.getTicketNo(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					cell = new PdfPCell(
							new Paragraph(claimPassDetailsTableData.getJourneyPerformed(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(String.valueOf(claimPassDetailsTableData.getBookingAmount()),
							fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(String.valueOf(claimPassDetailsTableData.getRefundAmount()),
							fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(String.valueOf(claimPassDetailsTableData.getClaimedAmount()),
							fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					jrnyPassTable.addCell(cell);
				}
			}

			cell = new PdfPCell(jrnyPassTable);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(2);
			cell.setBorderColor(new BaseColor(0,58,112));
			cell.setColspan(8);
			if (detailsPDF.getBkgFrom().equalsIgnoreCase("DTS")) {
				cell.setBackgroundColor(new BaseColor(144,238,144));
			}

			jrnyDetailsTable.addCell(cell);

			setEmptyCell(jrnyDetailsTable, 1, 8, false);

			claimtable.addCell(jrnyDetailsTable);
		}
		}

		setEmptyCell(claimtable, 1, 1, false);

		ViewClaimAdvanceDtlsBean advanceDetailsTableData = content.getClaimAdvanceDtls();
		
		if (null != advanceDetailsTableData) {

			PdfPTable ctgDtlsTable = new PdfPTable(5);
			ctgDtlsTable.getDefaultCell().setBorder(0);
			int[] ctgDtlsTable_widths = { 25, 15, 20, 20, 20 };
			ctgDtlsTable.setWidths(ctgDtlsTable_widths);

			cell = new PdfPCell(new Paragraph("CTG & Personal Effects", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(BaseColor.GRAY);
			cell.setColspan(5);
			ctgDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			cell.setColspan(3);
			ctgDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Claimed Amount", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			cell.setColspan(2);
			ctgDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Composite Transfer and Packing Grant (CTG)", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			ctgDtlsTable.addCell(cell);

			cell = new PdfPCell(
					new Paragraph(String.valueOf(advanceDetailsTableData.getCtgAmount()), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(2);
			ctgDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Particulars", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			ctgDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Weight (in Kg)", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			ctgDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Mode of Conveyance", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			ctgDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Distance (in Kms)", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			ctgDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Claimed Amount", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			ctgDtlsTable.addCell(cell);


			Set<ViewClaimPersonalEffectsBean> personalEffTableData = content.getClaimPersonalEffectsBean();
			
			if (null != personalEffTableData && !personalEffTableData.isEmpty()) {
				for (ViewClaimPersonalEffectsBean viewClaimPersonalEffectsBean : personalEffTableData) {
					cell = new PdfPCell(new Paragraph(viewClaimPersonalEffectsBean.getParticulars(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					ctgDtlsTable.addCell(cell);

					cell = new PdfPCell(
							new Paragraph(String.valueOf(viewClaimPersonalEffectsBean.getWeight()), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					ctgDtlsTable.addCell(cell);

					cell = new PdfPCell(
							new Paragraph(viewClaimPersonalEffectsBean.getModeOfConveyance(), fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					ctgDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(String.valueOf(viewClaimPersonalEffectsBean.getDistance()),
							fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					ctgDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph(String.valueOf(viewClaimPersonalEffectsBean.getClaimedAmt()),
							fontbold_8px_Nml));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					ctgDtlsTable.addCell(cell);

					
				}
			}

			cell = new PdfPCell(new Paragraph("Is Conveyance part of Luggage?", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			cell.setColspan(2);
			ctgDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getConvPartOfLuggName(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(3);
			ctgDtlsTable.addCell(cell);

			if (advanceDetailsTableData.getConvPartOfLugg() == 1) {

				cell = new PdfPCell(new Paragraph("Transportation of Conveyance", fontbold_8px));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(color);
				cell.setColspan(2);
				ctgDtlsTable.addCell(cell);

				cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getConveyanceName(), fontbold_8px_Nml));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(3);
				ctgDtlsTable.addCell(cell);

				personalEffTableData = content.getClaimPersonalEffectsBean();
				if (null != personalEffTableData && !personalEffTableData.isEmpty()) {
					cell = new PdfPCell(new Paragraph("Weight (in Kg)", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(color);
					ctgDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Mode of Conveyance", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(color);
					ctgDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Distance (in Kms)", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(color);
					ctgDtlsTable.addCell(cell);

					cell = new PdfPCell(new Paragraph("Claimed Amount", fontbold_8px));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(color);
					cell.setColspan(2);
					ctgDtlsTable.addCell(cell);

					

					for (ViewClaimPersonalEffectsBean viewClaimPersonalEffectsBean : personalEffTableData) {

						cell = new PdfPCell(new Paragraph(String.valueOf(viewClaimPersonalEffectsBean.getWeight()),
								fontbold_8px_Nml));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						ctgDtlsTable.addCell(cell);

						cell = new PdfPCell(
								new Paragraph(viewClaimPersonalEffectsBean.getModeOfConveyance(), fontbold_8px_Nml));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						ctgDtlsTable.addCell(cell);

						cell = new PdfPCell(new Paragraph(String.valueOf(viewClaimPersonalEffectsBean.getDistance()),
								fontbold_8px_Nml));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						ctgDtlsTable.addCell(cell);

						cell = new PdfPCell(new Paragraph(String.valueOf(viewClaimPersonalEffectsBean.getClaimedAmt()),
								fontbold_8px_Nml));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setColspan(2);
						ctgDtlsTable.addCell(cell);

					}
				}
			}

			claimtable.addCell(ctgDtlsTable);

			setEmptyCell(claimtable, 1, 1, false);

			PdfPTable advDtlsTable = new PdfPTable(6);
			advDtlsTable.getDefaultCell().setBorder(0);
			int[] advDtlsTable_widths = { 16, 16, 16, 16, 16, 16};
			advDtlsTable.setWidths(advDtlsTable_widths);

			cell = new PdfPCell(new Paragraph("Advances Drawn", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(BaseColor.GRAY);
			cell.setColspan(6);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Advance Drawn From", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Date of Advance", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Luggage including Cartage if any", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Coveyance", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("CTG", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("Total", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("DTS", fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("", fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(String.valueOf(0), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(String.valueOf(0), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(String.valueOf(0), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(
					new Paragraph(String.valueOf(advanceDetailsTableData.getDaAdvanceAmount()), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			advDtlsTable.addCell(cell);

			

			cell = new PdfPCell(new Paragraph("PAO", fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getPaoAdvanceDate(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(
					new Paragraph(String.valueOf(advanceDetailsTableData.getPaoAdvLugg()), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(
					new Paragraph(String.valueOf(advanceDetailsTableData.getPaoAdvConvyance()), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(
					new Paragraph(String.valueOf(advanceDetailsTableData.getPaoAdvCtg()), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			advDtlsTable.addCell(cell);

			cell = new PdfPCell(
					new Paragraph(String.valueOf(advanceDetailsTableData.getPaoAdvanceAmount()), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			advDtlsTable.addCell(cell);

			claimtable.addCell(advDtlsTable);

			PdfPTable refDtlsTable = new PdfPTable(3);
			refDtlsTable.getDefaultCell().setBorder(0);
			int[] refDtlsTable_widths = { 33, 33, 33};
			refDtlsTable.setWidths(refDtlsTable_widths);

			cell = new PdfPCell(new Paragraph("Advances Refunded", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(BaseColor.GRAY);
			cell.setColspan(3);
			refDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("eMRO/MRO/ Refund Amount", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			refDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("eMRO/MRO/Refund No", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			refDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph("eMRO/MRO Submission Date/Refund Date", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(color);
			refDtlsTable.addCell(cell);


			cell = new PdfPCell(
					new Paragraph(String.valueOf(advanceDetailsTableData.getMroRefundAmount()), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			refDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getMroRefundRefId(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			refDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getMroRefundDate(), fontbold_8px_Nml));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			refDtlsTable.addCell(cell);


			cell = new PdfPCell(new Paragraph("Total Amount of Claim(Submitted)", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(color);
			cell.setColspan(2);
			refDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(String.valueOf(content.getTotalSpentAmount()), fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(color);
			refDtlsTable.addCell(cell);



			cell = new PdfPCell(new Paragraph("less: DA Advance Taken(User)", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(color);
			cell.setColspan(2);
			refDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(String.valueOf(content.getTotalAdvanceAmount()), fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(color);
			refDtlsTable.addCell(cell);



			cell = new PdfPCell(new Paragraph("add: e-MRO Refunds(User)", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(color);
			cell.setColspan(2);
			refDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(String.valueOf(content.getTotalRefundAmount()), fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(color);
			refDtlsTable.addCell(cell);



			cell = new PdfPCell(new Paragraph("Payable/Recoverable Amount", fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(color);
			cell.setColspan(2);
			refDtlsTable.addCell(cell);

			cell = new PdfPCell(new Paragraph(String.valueOf(content.getTotalClaimedAmount()), fontbold_8px));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBackgroundColor(color);
			refDtlsTable.addCell(cell);

			claimtable.addCell(refDtlsTable);

		}

		PdfPTable certifyDtlsTable = new PdfPTable(1);
		certifyDtlsTable.getDefaultCell().setBorder(0);

		setEmptyCell(certifyDtlsTable, 1, 1, false);

		cell = new PdfPCell(new Paragraph("Certified that:", fontbold_8px));
		cell.setBorder(PdfPCell.NO_BORDER);
		certifyDtlsTable.addCell(cell);

		cell = new PdfPCell();
		cell.setBorder(PdfPCell.NO_BORDER);

		com.itextpdf.text.List list = new com.itextpdf.text.List();
		Set<ViewClaimCertifyDtlsBean> certifyDetailsTableData = content.getClaimCertifyView();
		

		if (null != certifyDetailsTableData && !certifyDetailsTableData.isEmpty()) {
			for (ViewClaimCertifyDtlsBean viewClaimCertifyDtlsBean : certifyDetailsTableData) {
				list.add(new ListItem(new Chunk(viewClaimCertifyDtlsBean.getCertifyQuestion(), fontbold_8px_Nml)));
			}
		}

		cell.addElement(list);
		certifyDtlsTable.addCell(cell);

		claimtable.addCell(certifyDtlsTable);

		cell = new PdfPCell(new Paragraph("Countersigned as correct claim by", fontbold_8px));
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(30);
		claimtable.addCell(cell);

		setEmptyCell(claimtable, 2, 1, false);

		cell = new PdfPCell(new Paragraph(advanceDetailsTableData.getAuthorityName(), fontbold_8px));
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		claimtable.addCell(cell);

		cell = new PdfPCell(new Paragraph(content.getName(), fontbold_8px));
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setPaddingTop(20);
		claimtable.addCell(cell);

		cell = new PdfPCell(
				new Paragraph("Signature and designation of the Officer countersigning the claim", fontbold_8px));
		cell.setBorder(PdfPCell.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingTop(20);
		claimtable.addCell(cell);


		document.add(claimtable);
		document.close();

	}

	// ===============================================================================================//

	private void setEmptyCell(PdfPTable info, int row, int col, boolean isBorder) {
		for (int index = 0; index < row; index++) {
			for (int i = 0; i < col; i++) {
				PdfPCell cell = new PdfPCell(new Paragraph(" ", FontFactory.getFont("", 8, Font.BOLD)));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				if (!isBorder) {
					cell.setBorder(Rectangle.NO_BORDER);
				}
				info.addCell(cell);
			}

		}

	}





}
