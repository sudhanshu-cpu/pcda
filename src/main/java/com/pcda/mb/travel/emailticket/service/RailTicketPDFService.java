package com.pcda.mb.travel.emailticket.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pcda.common.model.Level;
import com.pcda.common.model.User;
import com.pcda.common.services.LevelServices;
import com.pcda.common.services.UserServices;
import com.pcda.mb.travel.emailticket.model.DAAdvancePDF;
import com.pcda.mb.travel.emailticket.model.DaAdvanceResponse;
import com.pcda.mb.travel.emailticket.model.RailTicketBookDtls;
import com.pcda.mb.travel.emailticket.model.RailTicketPdfModel;
import com.pcda.mb.travel.emailticket.model.RailTicketPdfresponse;
import com.pcda.mb.travel.emailticket.model.ViewTravelDtl;
import com.pcda.mb.travel.emailticket.model.ViewTravelDtlResponse;
import com.pcda.util.DODDATAConstants;
import com.pcda.util.DODLog;
import com.pcda.util.ERSConstants;
import com.pcda.util.Gender;
import com.pcda.util.IRCTCConstants;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class RailTicketPDFService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private LevelServices levelServices;

	
	public RailTicketPdfModel getRailTicketPdf(String bookingId) {
		DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class, " ## bookingId: " + bookingId);
		RailTicketPdfModel railTicketPdfModel = null;

		try {

			ResponseEntity<RailTicketPdfresponse> entity = restTemplate.exchange(
					PcdaConstant.RAIL_TICKET_BASE_URL + "/getTicketDtls/" + bookingId, HttpMethod.GET, null,
					new ParameterizedTypeReference<RailTicketPdfresponse>() {
					});
			RailTicketPdfresponse response = entity.getBody();

			if (null != response && response.getErrorCode() == 200 && response.getResponse() != null) {
				railTicketPdfModel = response.getResponse();
				Collections.sort(railTicketPdfModel.getTicketBookDtls());
			}
		

		} catch (Exception e) {
			DODLog.printStackTrace(e, RailTicketPDFService.class, LogConstant.EMAIL_PDF_LOG_FILE);
		}
		DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class, "Email PDF: " + railTicketPdfModel);
		return railTicketPdfModel;

	}

	public ViewTravelDtl getTravelDtls(String requestId, int requestSequanceNumber) {

		DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class,"## requestId: " + requestId+" ##requestSequanceNumber"+requestSequanceNumber);
		ViewTravelDtl viewTravelDtl = null;

		try {
			ResponseEntity<ViewTravelDtlResponse> entity = restTemplate.exchange(
					PcdaConstant.RAIL_TICKET_BASE_URL + "/viewTravelDtl?requestId=" + requestId
							+ "&requestSequanceNumber=" + requestSequanceNumber,
					HttpMethod.GET, null, new ParameterizedTypeReference<ViewTravelDtlResponse>() {
					});
			ViewTravelDtlResponse response = entity.getBody();

			if (null != response && response.getErrorCode() == 200 && response.getResponse() != null) {
				viewTravelDtl = response.getResponse();
			}
			

		} catch (Exception e) {
			DODLog.printStackTrace(e, RailTicketPDFService.class, LogConstant.EMAIL_PDF_LOG_FILE);
		}
		DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class,"## View Travel DTLS: " + viewTravelDtl);

		return viewTravelDtl;
	}


	public DAAdvancePDF airRequestDtls(String requestId ) {
		DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class,"## requestId: " + requestId);
		DAAdvancePDF daAdvancePDF= null;

		try {

			ResponseEntity<DaAdvanceResponse> entity = restTemplate.exchange(
					PcdaConstant.RAIL_TICKET_BASE_URL + "/getDAAdvanceContent/" + requestId, HttpMethod.GET, null,
					new ParameterizedTypeReference<DaAdvanceResponse>() {
					});
			DaAdvanceResponse response = entity.getBody();

			if (null != response && response.getErrorCode() == 200 && response.getResponse() != null) {
				daAdvancePDF = response.getResponse();
			}
			

		} catch (Exception e) {
			DODLog.printStackTrace(e, RailTicketPDFService.class, LogConstant.EMAIL_PDF_LOG_FILE);
		}
		DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class, "DA Advance : " + daAdvancePDF);

		return daAdvancePDF;

	}
	
	
	public User getVisitorModel(String personalNo ) {
		DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class, " personalNo : " + personalNo);
		 Optional<User>vistOptional=userServices.getUserByUserAlias(personalNo);
		 if(vistOptional.isPresent()) {
		 Optional<Level> level=levelServices.getLevel(vistOptional.get().getLevelId());
		 
		 String levelName="";
		 if(level.isPresent()) {
			 
			 levelName=level.get().getLevelName();
		 }
		 vistOptional.get().setLevelName(levelName);
		  return vistOptional.get();
		
		 }
		 
		 DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class, " vistOptional : " + vistOptional);
		 
		 return new User();

	}
	
	
	
	
	// DA Advance PDF
	public ByteArrayOutputStream createDAAdvancePDF(String requestId, HttpServletRequest request,String personalNo){
		
		Font fontbold_8px = FontFactory.getFont("", 8, Font.BOLD);
        Font fontbold_7px_Nml = FontFactory.getFont("", 7, Font.NORMAL);
        
        ByteArrayOutputStream baos=null;
        
try {
			
			User visitor = getVisitorModel(personalNo); 
	        
			DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class, "[createDAAdvancePDF] visitor serviceId : " + visitor.getUserServiceId());
			
			if(visitor.getUserServiceId().equals("100015") )
	{
		DAAdvancePDF daAdvanceContent= airRequestDtls(requestId);
		
		DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class, "[createDAAdvancePDF] daAdvanceContent : " + daAdvanceContent);
		
			 if(null!=daAdvanceContent && daAdvanceContent.getJourneyType()==0)
	{
				  
			double requestedDA=0.0d;
			double paidDA=0.0d;
			String advanceSeq="NA";
			String dwnDate="NA";
			String travelId=daAdvanceContent.getTravelId();
			
				requestedDA=daAdvanceContent.getRequestedDA();
				paidDA=daAdvanceContent.getPaidDA();
				advanceSeq=daAdvanceContent.getAdvanceSeq();
				dwnDate=daAdvanceContent.getDwnDate();

    baos=new ByteArrayOutputStream();
    
	PdfPCell cell;
	Document document = new Document(PageSize.A4, 0, 0, 50, 0);
	PdfWriter.getInstance(document, baos);
	document.open();
	
	PdfPTable bordertable = new PdfPTable(1);
	bordertable.getDefaultCell().setBorderWidth(1);
	
	PdfPTable maintable = new PdfPTable(1);
	maintable.getDefaultCell().setBorder(0);
	
	PdfPTable imageTable = new PdfPTable(2);
    imageTable.setWidthPercentage(95f);
	int[] imageTable_widths={50,50};
	imageTable.setWidths(imageTable_widths);
	
	
	
	String irctcLogoPath= DODDATAConstants.PCDA_LOGO_PATH; 
	irctcLogoPath=irctcLogoPath.replace("./", ""); 
	irctcLogoPath=irctcLogoPath.replace(".\\", ""); 
	irctcLogoPath=irctcLogoPath.replace("irctcLogo.jpg", ""); 
	
	Image addlogoOne=null;
	Image addlogoTwo=null;
	
	try {
		addlogoOne = Image.getInstance(irctcLogoPath+"/DTS_Header.png");
		addlogoTwo = Image.getInstance(irctcLogoPath+"/DTS_Header.png");
	} catch (Exception e) {
	DODLog.printStackTrace(e, RailTicketPDFService.class, LogConstant.EMAIL_PDF_LOG_FILE);
	}
	
	if(addlogoOne!=null) {
	addlogoOne.scaleToFit(50f, 50f);
	}
	if(addlogoTwo!=null) {
	addlogoTwo.scaleToFit(50f, 50f);
		}
	
	cell = new PdfPCell(addlogoOne);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	imageTable.addCell(cell);
	
	cell = new PdfPCell(addlogoTwo);
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	imageTable.addCell(cell);
	
	maintable.addCell(imageTable); 
	
	PdfPTable titleTable = new PdfPTable(1);
	titleTable.setWidthPercentage(95f);
	int titleTable_widths[]={100};
	titleTable.setWidths(titleTable_widths);
	
	
	setEmptyCell(titleTable,2,1,false);
	
	cell = new PdfPCell(new Paragraph("CGCDA (Delhi)", fontbold_8px));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	titleTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph("Payment Receipt Voucher", fontbold_8px));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	titleTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph("TY Duty Advance", fontbold_8px));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	titleTable.addCell(cell);
	
	maintable.addCell(titleTable);
	
	PdfPTable voucherTable = new PdfPTable(4);
	voucherTable.setWidthPercentage(95f);
	int voucherTable_widths[]={20, 30, 20, 30};
	voucherTable.setWidths(voucherTable_widths);
	
	setEmptyCell(voucherTable,2,4,false);
	
	cell = new PdfPCell(new Paragraph("Name",fontbold_8px));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph(visitor.getName(),fontbold_7px_Nml));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph("Level",fontbold_8px));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph(visitor.getLastName(),fontbold_7px_Nml));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph("Personel Number",fontbold_8px));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph(visitor.getUserAlias(),fontbold_7px_Nml));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph("Form ID",fontbold_8px));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph("DTS  "+travelId,fontbold_7px_Nml));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph("Claimed Amount",fontbold_8px));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph(String.valueOf(requestedDA),fontbold_7px_Nml));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph("Passed Amount",fontbold_8px));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph(String.valueOf(paidDA),fontbold_7px_Nml));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph("Voucher No",fontbold_8px));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph(advanceSeq,fontbold_7px_Nml));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph("Voucher Date",fontbold_8px));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	cell = new PdfPCell(new Paragraph(dwnDate,fontbold_7px_Nml));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	voucherTable.addCell(cell);
	
	setEmptyCell(voucherTable,1,4,false);
	
	maintable.addCell(voucherTable); 
	
	bordertable.addCell(maintable);
	
	document.add(bordertable);
	
	PdfPTable NoteTable = new PdfPTable(1);
	NoteTable.setWidthPercentage(95f);
	int NoteTable_widths[]={100};
	NoteTable.setWidths(NoteTable_widths);
	
	
	setEmptyCell(NoteTable,15,1,false);
	
	cell = new PdfPCell(new Paragraph("Note : This is system generated pay-slip does not required seal and signature.",fontbold_7px_Nml));
	cell.setBorder(Rectangle.NO_BORDER);
	cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	NoteTable.addCell(cell);
	
	document.add(NoteTable);
	
	document.close();
  }
	}
}
 catch (Exception e) {
	DODLog.printStackTrace(e, RailTicketPDFService.class,LogConstant.EMAIL_PDF_LOG_FILE);
}

return baos;
		
}


private void setEmptyCell(PdfPTable info, int row, int col, boolean isBorder) {
	for(int index=0;index<row;index++){
		for(int i=0;i<col;i++){
			PdfPCell cell = new PdfPCell(new Paragraph(" ",FontFactory.getFont("", 8, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			if(!isBorder){
				cell.setBorder(Rectangle.NO_BORDER);
			}
			info.addCell(cell);
		}
	}
	
}
	
	//Rail I Ticket PDF
	public boolean createITicketPDF(RailTicketPdfModel irTicketDetails, HttpServletResponse response, HttpServletRequest request) 
			throws DocumentException,IOException{
		
		Document document = new Document();

		String ticketStaus = null;
		boolean isWL = false;
		boolean isCGTicket = false;
		ServletOutputStream out = null;

		if (irTicketDetails != null) {
			try {
				if (irTicketDetails.getTicketUserAltId().equals(DODDATAConstants.COAST_GUARD_SEVICE_ID)) {
					isCGTicket = true;
				}
			} catch (Exception e) {
				DODLog.printStackTrace(e, RailTicketPDFService.class, LogConstant.EMAIL_PDF_LOG_FILE);
			}

			String irctcLogoPath = DODDATAConstants.PCDA_LOGO_PATH;

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			document.open();

			java.util.List<RailTicketBookDtls> bookingDetailFromListPdfPTable = irTicketDetails.getTicketBookDtls();
			for (int i = 0; i < bookingDetailFromListPdfPTable.size(); i++) {
				ticketStaus = bookingDetailFromListPdfPTable.get(i).getTktCurrentStatus();
				if (ticketStaus.indexOf("WL") != (-1)) {
					isWL = true;
					break;
				}
			}

			Chapter firstPage = new Chapter(0);

			PdfPTable frontPageContentTable = new PdfPTable(1);
			frontPageContentTable.setWidthPercentage(100f); 
			frontPageContentTable.getDefaultCell().setPadding(1);
			frontPageContentTable.setSpacingAfter(1);
			frontPageContentTable.getDefaultCell().setBorderWidth(0);
			int[] frontPageContentTableWidths = { 100 };
			frontPageContentTable.setWidths(frontPageContentTableWidths);

			PdfPTable blankPdfPTable = new PdfPTable(1);
			blankPdfPTable.setWidthPercentage(100f); 
			int[] blankPdfPTableWidths = { 95 };
			blankPdfPTable.setWidths(blankPdfPTableWidths);
			blankPdfPTable.setSpacingBefore(0);
			PdfPCell blankTitle = new PdfPCell(new Paragraph("\n", new Font(Font.FontFamily.TIMES_ROMAN, 8)));
			blankTitle.setColspan(0);
			blankTitle.setBorder(0);
			blankPdfPTable.addCell(blankTitle);

			PdfPTable ersHeaderLogoTable = new PdfPTable(1);
			ersHeaderLogoTable.setWidthPercentage(100f);
			int[] ersHeaderLogoTableWidth = { 95 };
			ersHeaderLogoTable.getDefaultCell().setBorder(0);
			ersHeaderLogoTable.setWidths(ersHeaderLogoTableWidth);

			if (isWL) {
				Image headerLogo = null;
				
				if (isCGTicket)
					headerLogo = Image.getInstance(irctcLogoPath+ "/ERS_Header_ITKT_WL_CG.jpg");
				else
					headerLogo = Image.getInstance(irctcLogoPath+ "/ERS_Header_ITKT_WL.jpg");

				PdfPCell imageCell = new PdfPCell(headerLogo);
				imageCell.setBorder(Rectangle.NO_BORDER);
				imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				ersHeaderLogoTable.addCell(imageCell);
			} else {
				Image headerLogo = null;
				if (isCGTicket)
					headerLogo = Image.getInstance(irctcLogoPath+ "/ERS_Header_ITKT_CNF_CG.jpg");
				else
					headerLogo = Image.getInstance(irctcLogoPath+ "/ERS_Header_ITKT_CNF.jpg");

				PdfPCell imageCell = new PdfPCell(headerLogo);
				imageCell.setBorder(Rectangle.NO_BORDER);
				imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				ersHeaderLogoTable.addCell(imageCell);
			}

			firstPage.add(ersHeaderLogoTable);

			String pnr = irTicketDetails.getPnrNo();
			String txnId = irTicketDetails.getTicketNo();

			String iTicketHeaderText = ERSConstants.iTicketParagraph;

			iTicketHeaderText = iTicketHeaderText.replace("PNRXXX", pnr);
			iTicketHeaderText = iTicketHeaderText.replace("TXNIDXXX", txnId);

			PdfPCell iTicketPara = new PdfPCell(
					new Paragraph(iTicketHeaderText, new Font(FontFamily.TIMES_ROMAN, 13, Font.BOLD)));
			iTicketPara.setBorder(0);
			iTicketPara.setHorizontalAlignment(Element.ALIGN_CENTER);
			frontPageContentTable.addCell(iTicketPara);

			PdfPCell hrLine = new PdfPCell(new Paragraph("\n"));
			hrLine.setBorder(2);
			hrLine.setHorizontalAlignment(Element.ALIGN_CENTER);
			frontPageContentTable.addCell(hrLine);

			PdfPCell receipt = new PdfPCell(
					new Paragraph("Receipt\n\n\n", new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
			receipt.setBorder(0);
			receipt.setHorizontalAlignment(Element.ALIGN_CENTER);
			frontPageContentTable.addCell(receipt);

			String receiptTextStr = "Received i-ticket against PNR No. " + pnr + " with Transaction ID " + txnId;
			PdfPCell receiptText = new PdfPCell(
					new Paragraph(receiptTextStr, new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
			receiptText.setBorder(0);
			receiptText.setHorizontalAlignment(Element.ALIGN_CENTER);
			frontPageContentTable.addCell(receiptText);

			PdfPTable ersFooterLogoPdfPTable = new PdfPTable(1);
			ersFooterLogoPdfPTable.setWidthPercentage(95f);
			int[] ersFooterLogoPdfPTableWidth = { 95 };
			ersFooterLogoPdfPTable.getDefaultCell().setBorder(0);
			ersFooterLogoPdfPTable.setWidths(ersFooterLogoPdfPTableWidth);

			Image footerLogo = Image.getInstance(irctcLogoPath + "/ERS_Footer_ITKT_CNF.jpg");
			footerLogo.scaleToFit(350f, 250f);
			PdfPCell imageCell = new PdfPCell(footerLogo);
			imageCell.setBorder(Rectangle.NO_BORDER);
			imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			ersFooterLogoPdfPTable.addCell(imageCell);
			firstPage.add(ersFooterLogoPdfPTable);
			
			
			PdfPCell signInfoText=new PdfPCell(new Paragraph("\n\n Name:\n Signature: \n Date: \n Id no:\n", new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
			signInfoText.setBorder(0);
			signInfoText.setHorizontalAlignment(Element.ALIGN_LEFT);
			frontPageContentTable.addCell(signInfoText);	
			
			PdfPCell instruction=new PdfPCell(new Paragraph("Instructions for i-ticket:", new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
			instruction.setBorder(0);
			instruction.setHorizontalAlignment(Element.ALIGN_LEFT);
			frontPageContentTable.addCell(instruction);
			
			PdfPCell bkgProcedure=new PdfPCell(new Paragraph("Booking Procedure:", new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
			bkgProcedure.setBorder(0);
			bkgProcedure.setHorizontalAlignment(Element.ALIGN_LEFT);
			frontPageContentTable.addCell(bkgProcedure);
			
			PdfPCell bkgPoint1=new PdfPCell(new Paragraph(ERSConstants.railPrintITktInsPoint1, new Font(FontFamily.TIMES_ROMAN, 8)));
			bkgPoint1.setBorder(0);
			bkgPoint1.setHorizontalAlignment(Element.ALIGN_LEFT);
			frontPageContentTable.addCell(bkgPoint1);
			
			PdfPCell bkgPoint2=new PdfPCell(new Paragraph(ERSConstants.railPrintITktInsPoint2, new Font(FontFamily.TIMES_ROMAN, 8)));
			bkgPoint2.setBorder(0);
			bkgPoint2.setHorizontalAlignment(Element.ALIGN_LEFT);
			frontPageContentTable.addCell(bkgPoint2);
			
			PdfPCell bkgPoint3=new PdfPCell( new Paragraph(ERSConstants.railPrintITktInsPoint3+"\n\n", new Font(FontFamily.TIMES_ROMAN, 8)));
			bkgPoint3.setBorder(0);
			bkgPoint3.setHorizontalAlignment(Element.ALIGN_LEFT);
			frontPageContentTable.addCell(bkgPoint3);
			
			PdfPCell cancelProcedure=new PdfPCell(new Paragraph("Cancellation Procedure:", new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
			cancelProcedure.setBorder(0);
			cancelProcedure.setHorizontalAlignment(Element.ALIGN_LEFT);
			frontPageContentTable.addCell(cancelProcedure);
			
			
			PdfPCell canPoint1=new PdfPCell(new Paragraph(ERSConstants.railPrintItktCanPt1, new Font(FontFamily.TIMES_ROMAN, 8)));
		
			canPoint1.setBorder(0);
			canPoint1.setHorizontalAlignment(Element.ALIGN_LEFT);
			frontPageContentTable.addCell(canPoint1);
			
			PdfPCell canPoint2=new PdfPCell(new Paragraph(ERSConstants.railPrintItktCanPt2, new Font(FontFamily.TIMES_ROMAN, 8)));
			canPoint2.setBorder(0);
			canPoint2.setHorizontalAlignment(Element.ALIGN_LEFT);
			frontPageContentTable.addCell(canPoint2);
			
			PdfPCell canPoint3=new PdfPCell();
		    Paragraph canPoint3Para= new Paragraph(ERSConstants.railPrintItktCanPt31, new Font(FontFamily.TIMES_ROMAN, 8,Font.BOLD));
			Paragraph canPoint31Para= new Paragraph(ERSConstants.railPrintItktCanPt32+"\n", new Font(FontFamily.TIMES_ROMAN, 8));

			canPoint3.addElement(canPoint3Para);
			canPoint3.addElement(canPoint31Para);
			
			canPoint3.setBorder(0);
			canPoint3.setHorizontalAlignment(Element.ALIGN_LEFT);
			frontPageContentTable.addCell(canPoint3);
			
			firstPage.add(frontPageContentTable);
	    	
			document.add(firstPage);
			

//			/*------- Second Page Of I ticket ------------*/
//
//			Chapter secondPage = new Chapter(0);
//
//			PdfPTable procedurePdfPTable = new PdfPTable(1);
//			procedurePdfPTable.setWidthPercentage(95f);
//			procedurePdfPTable.getDefaultCell().setBorder(0);
//			procedurePdfPTable.setWidths(new int[] { 95 });
//			Image procedureImage = Image.getInstance(irctcLogoPath + "/iTicketProcedure.jpeg");
//			PdfPCell procedureImageCell = new PdfPCell(procedureImage);
//			procedureImageCell.setBorder(Rectangle.NO_BORDER);
//			procedureImageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			procedurePdfPTable.addCell(procedureImageCell);
//
//			secondPage.add(procedurePdfPTable);
//			document.add(secondPage);
//
//			/*------- Third Page Of I ticket For Refund Rule ------------*/
//
//			try {
//				Chapter thirdPage = new Chapter(0);
//
//				PdfPTable procedurePdfPTabledata = new PdfPTable(1);
//				procedurePdfPTabledata.setWidthPercentage(95f);
//				procedurePdfPTabledata.getDefaultCell().setBorder(0);
//				procedurePdfPTabledata.setWidths(new int[] { 95 });
//				Image revisedImage = Image.getInstance(irctcLogoPath + "/RevisedRule.jpg");
//				PdfPCell procedureRefundCell = new PdfPCell(revisedImage);
//				procedureRefundCell.setBorder(Rectangle.NO_BORDER);
//				procedureRefundCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				procedurePdfPTabledata.addCell(procedureRefundCell);
//
//				thirdPage.add(procedurePdfPTabledata);
//				document.add(thirdPage);
//			} catch (Exception e) {
//				DODLog.printStackTrace(e, RailTicketPDFService.class, LogConstant.EMAIL_PDF_LOG_FILE);
//			}

			document.close();

			response.setContentLength(baos.size());
			response.setContentType("application/pdf");
			out = response.getOutputStream();
			baos.writeTo(out);
			out.flush();

			return true;
		}
		return false;
		

	}

	public boolean createETicketPDF(HttpServletResponse response, RailTicketPdfModel irTicketDetails ,
			ByteArrayOutputStream baos) throws IOException, DocumentException {
		
		Paragraph paraTitle = null;
		Document document = new Document();

		

		String currentCancelStatus = "";
		String travelTypeId = "";
		int tatkalFlag = 1;
		int isTatkalApproved = 1;
		int journeyType = 0;
		String viaRoute = null;
		String travelName = null;
		String bookingTypeViaRoute = null;
		String ticketStaus = null;
		boolean isWL = false;
		boolean isCGTicket = false;
		ServletOutputStream out = null;
		double halfAmount = 0.0;
		double fullAmount = 0.0;
		double deductibleAmount = 0.0;
		boolean irlaDeductFlag = false;
		double tatkalGst;

		if (irTicketDetails != null) {
			
			java.util.List<RailTicketBookDtls> bookingDetailFromListTable = irTicketDetails.getTicketBookDtls();
			
			try {
				if (irTicketDetails.getTicketUserAltId().equals(DODDATAConstants.COAST_GUARD_SEVICE_ID)) {
					isCGTicket = true;
				}
			} catch (Exception e) {
				DODLog.printStackTrace(e, RailTicketPDFService.class, LogConstant.EMAIL_PDF_LOG_FILE);
			}

			int nA = 0;
			int nC = 0;
			int nS = 0;
			int passengerType = 0;
			int bookingDtlsSize = bookingDetailFromListTable.size();
			for (int i = 0; i < bookingDtlsSize; i++) {
				passengerType = bookingDetailFromListTable.get(i).getPassengerType();

				if (passengerType == 0) {
					nC++;
				}
				if (passengerType == 1) {
					nA++;
				}
				if (passengerType == 2) {
					nS++;
				}

			}

			for (int i = 0; i < bookingDtlsSize; i++) {
				ticketStaus = bookingDetailFromListTable.get(i).getTktCurrentStatus();

				if (ticketStaus.indexOf("WL") != (-1)) {
					isWL = true;
					break;
				}
			}

			try {
				String requestId = irTicketDetails.getRequestId();
				int requestSequanceNumber = irTicketDetails.getRequestSeqNo();

				ViewTravelDtl viewTravelDtl = getTravelDtls(requestId, requestSequanceNumber);

				if (viewTravelDtl != null) {
					travelTypeId = viewTravelDtl.getTravelTypeId();
					tatkalFlag = viewTravelDtl.getTatkalFlag();
					isTatkalApproved = viewTravelDtl.getIsTatkalApproved();
					journeyType = viewTravelDtl.getJourneyType();
					viaRoute = viewTravelDtl.getViaRoute();
					travelName = viewTravelDtl.getTravelName();

					
				}
			} catch (Exception e) {
				DODLog.printStackTrace(e, RailTicketPDFService.class, LogConstant.EMAIL_PDF_LOG_FILE);

			}
			
			if(travelTypeId.equalsIgnoreCase("100003"))
			{
			for(int i=0;i < bookingDtlsSize;i++)
			{
				RailTicketBookDtls bookDtls=bookingDetailFromListTable.get(i);
					halfAmount=halfAmount+Optional.ofNullable(bookDtls.getTktBaseFare()).orElse(0.0) 
					+Optional.ofNullable(bookDtls.getDiffAmt()).orElse(0.0) - Optional.ofNullable(bookDtls.getConcesionAmt()).orElse(0.0);
				
					fullAmount=fullAmount+Optional.ofNullable(bookDtls.getReservCharge()).orElse(0.0)+Optional.ofNullable(bookDtls.getSuperFastChrg()).orElse(0.0)
					+Optional.ofNullable(bookDtls.getSafetyChrg()).orElse(0.0)+Optional.ofNullable(bookDtls.getOtherChrge()).orElse(0.0)+Optional.ofNullable(bookDtls.getServiceTax()).orElse(0.0)
					+Optional.ofNullable(bookDtls.getAtrTatkalFare()).orElse(0.0);
				}
				
				halfAmount=halfAmount*0.50;
				deductibleAmount=halfAmount+fullAmount;
				deductibleAmount=Math.round(deductibleAmount);
				irlaDeductFlag=true;
				DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class," CV deductible Amount is = "+deductibleAmount);
			}

			 if(travelTypeId.equalsIgnoreCase("100004"))
				{
					for(int i=0;i < bookingDtlsSize;i++)
					{
						RailTicketBookDtls bookDtls=bookingDetailFromListTable.get(i);
						
						halfAmount=halfAmount+Optional.ofNullable(bookDtls.getTktBaseFare()).orElse(0.0) 
								+Optional.ofNullable(bookDtls.getDiffAmt()).orElse(0.0) - Optional.ofNullable(bookDtls.getConcesionAmt()).orElse(0.0);
						
						fullAmount=fullAmount+Optional.ofNullable(bookDtls.getSuperFastChrg()).orElse(0.0)
								+Optional.ofNullable(bookDtls.getSafetyChrg()).orElse(0.0)+Optional.ofNullable(bookDtls.getOtherChrge()).orElse(0.0)+Optional.ofNullable(bookDtls.getServiceTax()).orElse(0.0)
								+Optional.ofNullable(bookDtls.getAtrTatkalFare()).orElse(0.0);	
						
					}
					
					halfAmount=halfAmount*0.60;
					deductibleAmount=halfAmount+fullAmount;
					deductibleAmount=Math.round(deductibleAmount);
					irlaDeductFlag=true;
					DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class, " FormD deductible Amount is = "+deductibleAmount);
				}
			 
			 if(travelTypeId.equalsIgnoreCase("100012"))
				{
					for(int i=0;i < bookingDtlsSize;i++)
					{
						RailTicketBookDtls bookDtls=bookingDetailFromListTable.get(i);
						
						halfAmount=halfAmount+Optional.ofNullable(bookDtls.getTktBaseFare()).orElse(0.0) +Optional.ofNullable(bookDtls.getReservCharge()).orElse(0.0)
								+Optional.ofNullable(bookDtls.getDiffAmt()).orElse(0.0) - Optional.ofNullable(bookDtls.getConcesionAmt()).orElse(0.0);
						
						fullAmount=fullAmount+Optional.ofNullable(bookDtls.getSuperFastChrg()).orElse(0.0)
								+Optional.ofNullable(bookDtls.getSafetyChrg()).orElse(0.0)+Optional.ofNullable(bookDtls.getOtherChrge()).orElse(0.0)+Optional.ofNullable(bookDtls.getServiceTax()).orElse(0.0)
								+Optional.ofNullable(bookDtls.getAtrTatkalFare()).orElse(0.0);	
						
					}
					
					halfAmount=halfAmount*0.50;
					deductibleAmount=halfAmount+fullAmount;
					deductibleAmount=Math.round(deductibleAmount);
					irlaDeductFlag=true;
					DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class, " Form G deductible Amount is = "+deductibleAmount);
				}
			 
			 if(!travelTypeId.equalsIgnoreCase("100003")&&!travelTypeId.equalsIgnoreCase("100004")&&!travelTypeId.equalsIgnoreCase("100012"))
				{
				 if(tatkalFlag == 0 && isTatkalApproved == 1)
					{
					 String journeyClass=irTicketDetails.getJrnyClass();
					 if(journeyClass!=null&&!journeyClass.equalsIgnoreCase("SL")&&!journeyClass.equalsIgnoreCase("2S")&&!journeyClass.equalsIgnoreCase(""))
						{
						 deductibleAmount=Optional.ofNullable(irTicketDetails.getAtrTatkalFare()).orElse(0.0);
						 tatkalGst=deductibleAmount*0.05;
						 deductibleAmount=deductibleAmount+tatkalGst;
						 irlaDeductFlag=true;
						}
				}
					deductibleAmount=Math.round(deductibleAmount);
					
					DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class, " TD,PT,Misc,Others,LTC travelTypeId ="+travelTypeId+" deductible Amount is = "+deductibleAmount);
			}

	
			
			String irctcLogoPath = DODDATAConstants.PCDA_LOGO_PATH;

			PdfWriter.getInstance(document, baos);
			document.open();

			/*-------------ERS Header Image  ---------------------*/
			Chapter firstPage = new Chapter(0);

			PdfPTable frontPageContentTable = new PdfPTable(1);
			frontPageContentTable.setWidthPercentage(100f); 
			frontPageContentTable.setPaddingTop(2f); 
			frontPageContentTable.getDefaultCell().setBorderWidth(0);
			int[] frontPageContentTableWidths = {100};
			frontPageContentTable.setWidths(frontPageContentTableWidths);

			PdfPTable blankTable = new PdfPTable(1);
			int[] blankTableWidths = {95};
			blankTable.setWidths(blankTableWidths);
			blankTable.getDefaultCell().setBorderWidth(0);
			PdfPCell blankTitle = new PdfPCell(new Paragraph("\n", new Font(FontFamily.TIMES_ROMAN, 8)));
			blankTitle.setColspan(0);
			blankTitle.setBorder(0);
			blankTable.addCell(blankTitle);

			PdfPTable ersHeaderLogoTable = new PdfPTable(1);
			ersHeaderLogoTable.setWidthPercentage(100f); 
			int[] ersHeaderLogoTableWidth = {95};
			ersHeaderLogoTable.getDefaultCell().setBorderWidth(0);
			ersHeaderLogoTable.setWidths(ersHeaderLogoTableWidth);

			if (isWL) {
				Image headerLogo = null;
				if (isCGTicket)
					headerLogo = Image.getInstance(irctcLogoPath+ "/ERS_Header_WL_CG.jpg");
				else
					headerLogo = Image.getInstance(irctcLogoPath+ "/ERS_Header_WL.jpg");

				PdfPCell imageCell = new PdfPCell(headerLogo);
				imageCell.setBorder(Rectangle.NO_BORDER);
				imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				ersHeaderLogoTable.addCell(imageCell);
			} else {
				Image headerLogo = null;
				if (isCGTicket)
					headerLogo = Image.getInstance(irctcLogoPath+ "/ERS_Header_CNF_CG.jpg");
				else
					headerLogo = Image.getInstance(irctcLogoPath+"/ERS_Header_CNF.jpg");

				PdfPCell imageCell = new PdfPCell(headerLogo);
				imageCell.setBorder(Rectangle.NO_BORDER);
				imageCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				ersHeaderLogoTable.addCell(imageCell);
			}

			firstPage.add(ersHeaderLogoTable);
			boolean isTatkalFlag = false;
			if (irTicketDetails.getQuota().equals("CK")
					|| irTicketDetails.getQuota().equals(IRCTCConstants.isQuotaTatkal)) {
				isTatkalFlag = true;
			}

			/*-------------ERS Header Image  ---------------------*/

			/*-------------ERS Header Titled For Both CNF and WL Tickets ---------------------*/

			Font normalTextFont = new Font(FontFamily.TIMES_ROMAN, 8);
			Font normalTextBoldFont = new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD);

			PdfPTable table0 = new PdfPTable(1);
			ersHeaderLogoTable.setWidthPercentage(100f); 
			int[] table0Width = {95};
			table0.setWidths(table0Width);

			if (isWL) {

				if (isTatkalFlag) {
					paraTitle = new Paragraph(ERSConstants.railPrintLineHeader01BoldTxt_T_WL, normalTextBoldFont);
					PdfPCell table0Cell = new PdfPCell(paraTitle);
					table0Cell.setBorder(Rectangle.NO_BORDER);
					table0Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table0.addCell(table0Cell);

					paraTitle = new Paragraph(ERSConstants.railPrintLineHeader02BoldTxt_T_WL, normalTextBoldFont);
					table0Cell = new PdfPCell(paraTitle);
					table0Cell.setBorder(Rectangle.NO_BORDER);
					table0Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table0.addCell(table0Cell);

					paraTitle = new Paragraph(ERSConstants.railPrintLineHeader02BoldTxt_WL, normalTextBoldFont);
					table0Cell = new PdfPCell(paraTitle);
					table0Cell.setBorder(Rectangle.NO_BORDER);
					table0Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table0.addCell(table0Cell);

					paraTitle = new Paragraph(ERSConstants.railPrintLineHeader03NormalTxt_WL + "\n", normalTextFont);
					table0Cell = new PdfPCell(paraTitle);
					table0Cell.setBorder(Rectangle.NO_BORDER);
					table0Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table0.addCell(table0Cell);

				} else {
					paraTitle = new Paragraph(ERSConstants.railPrintLineHeader, normalTextBoldFont);

					PdfPCell table0Cell = new PdfPCell(paraTitle);
					table0Cell.setBorder(Rectangle.NO_BORDER);
					table0Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table0.addCell(table0Cell);

					paraTitle = new Paragraph(ERSConstants.railPrintLineHeader02BoldTxt_WL, normalTextBoldFont);
					table0Cell = new PdfPCell(paraTitle);
					table0Cell.setBorder(Rectangle.NO_BORDER);
					table0Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table0.addCell(table0Cell);

					paraTitle = new Paragraph(ERSConstants.railPrintLineHeader03NormalTxt_WL, normalTextBoldFont);
					table0Cell = new PdfPCell(paraTitle);
					table0Cell.setBorder(Rectangle.NO_BORDER);
					table0Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table0.addCell(table0Cell);

				}
			} else {

				if (isTatkalFlag) {
					paraTitle = new Paragraph(ERSConstants.railPrintLineHeader01BoldTxt_T_CNF, normalTextBoldFont);
					PdfPCell table0Cell = new PdfPCell(paraTitle);
					table0Cell.setBorder(Rectangle.NO_BORDER);
					table0Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table0.addCell(table0Cell);

					paraTitle = new Paragraph(ERSConstants.railPrintLineHeader02BoldTxt_T_CNF, normalTextBoldFont);
					table0Cell = new PdfPCell(paraTitle);
					table0Cell.setBorder(Rectangle.NO_BORDER);
					table0Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table0.addCell(table0Cell);

					paraTitle = new Paragraph(ERSConstants.railPrintLineHeader02BoldTxt_WL, normalTextBoldFont);
					table0Cell = new PdfPCell(paraTitle);
					table0Cell.setBorder(Rectangle.NO_BORDER);
					table0Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table0.addCell(table0Cell);

					paraTitle = new Paragraph(ERSConstants.railPrintLineHeader03NormalTxt_WL + "\n", normalTextFont);
					table0Cell = new PdfPCell(paraTitle);
					table0Cell.setBorder(Rectangle.NO_BORDER);
					table0Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table0.addCell(table0Cell);
				} else {
					paraTitle = new Paragraph(ERSConstants.railPrintLineHeader, normalTextBoldFont);

					PdfPCell table0Cell = new PdfPCell(paraTitle);
					table0Cell.setBorder(Rectangle.NO_BORDER);
					table0Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table0.addCell(table0Cell);

					paraTitle = new Paragraph(ERSConstants.railPrintLineHeader02BoldTxt_WL, normalTextBoldFont);
					table0Cell = new PdfPCell(paraTitle);
					table0Cell.setBorder(Rectangle.NO_BORDER);
					table0Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table0.addCell(table0Cell);

					paraTitle = new Paragraph(ERSConstants.railPrintLineHeader03NormalTxt_WL+ "\n", normalTextBoldFont);
					table0Cell = new PdfPCell(paraTitle);
					table0Cell.setBorder(Rectangle.NO_BORDER);
					table0Cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table0.addCell(table0Cell);
				}
			}
			frontPageContentTable.addCell(table0);
			firstPage.add(frontPageContentTable);

			

			/*-----------------Tickets and Train Information Tables --------------------------*/

			PdfPTable ticketsTable = new PdfPTable(3);
			ticketsTable.setWidthPercentage(100f);
			int[] ticketsTableWidths = { 35, 50, 40 };
			ticketsTable.setWidths(ticketsTableWidths);

			PdfPCell ticketsTableCell00 = new PdfPCell(new Paragraph(" Travel_Type: " + travelName, normalTextFont));
			ticketsTable.addCell(ticketsTableCell00);
			PdfPCell ticketsTableCell01;
			if (journeyType == 0) {
				ticketsTableCell01 = new PdfPCell(new Paragraph(" Journey_Type: Onward", normalTextFont));
			} else {
				ticketsTableCell01 = new PdfPCell(new Paragraph(" Journey_Type: Return", normalTextFont));
			}
			ticketsTable.addCell(ticketsTableCell01);
			int ticketFlag = irTicketDetails.getIsTatKal();
			if (ticketFlag == 1) {
				if (viaRoute.equals("0")) {
					bookingTypeViaRoute = "Normal";
				} else if (viaRoute.equals("1")) {
					bookingTypeViaRoute = "Normal/Via Route";
				} else if (viaRoute.equals("2")) {
					bookingTypeViaRoute = "Normal/Relaxed Route";
				}
			} else if (ticketFlag == 0) {
				if (viaRoute.equals("0")) {
					bookingTypeViaRoute = "Tatkal";
				} else if (viaRoute.equals("1")) {
					bookingTypeViaRoute = "Tatkal/Via Route";
				} else if (viaRoute.equals("2")) {
					bookingTypeViaRoute = "Tatkal/Relaxed Route";
				}
			}

			PdfPCell ticketsTableCell02 = new PdfPCell(
					new Paragraph(" Booking_Type: " + bookingTypeViaRoute, normalTextFont));
			ticketsTable.addCell(ticketsTableCell02);



			PdfPCell ticketsTableCell11 = new PdfPCell(
					new Paragraph(" PNR No.: " + irTicketDetails.getPnrNo(), normalTextBoldFont));
			ticketsTable.addCell(ticketsTableCell11);

			PdfPCell ticketsTableCell21 = new PdfPCell(
					new Paragraph(" Transaction ID: " + irTicketDetails.getTicketNo(), normalTextBoldFont));
			ticketsTable.addCell(ticketsTableCell21);

			Paragraph trRule = new Paragraph(" Travel Rule: ", normalTextBoldFont);
			trRule.add(new Paragraph(irTicketDetails.getTrRule(), normalTextBoldFont));
			PdfPCell ticketsTableCell72 = new PdfPCell(trRule);
			ticketsTable.addCell(ticketsTableCell72);


			String quotaText = "";
			if (irTicketDetails.getQuota().equals("GN")) {
				quotaText = "General";
			}
			if (irTicketDetails.getQuota().equals("CK")
					|| irTicketDetails.getQuota().equals(IRCTCConstants.isQuotaTatkal)) {
				quotaText = "Tatkal";

			}
			if (irTicketDetails.getQuota().equals("LD")) {
				quotaText = "Ladies";
			}


			PdfPCell ticketsTableCell13 = new PdfPCell(new Paragraph(" Quota: " + quotaText, normalTextFont));
			ticketsTable.addCell(ticketsTableCell13);

			String trainNumberAndName = irTicketDetails.getTrainNo() + "/" + irTicketDetails.getTrainName();
			PdfPCell ticketsTableCell12 = new PdfPCell(
					new Paragraph(" Train No. & Name: " + trainNumberAndName, normalTextFont));
			ticketsTable.addCell(ticketsTableCell12);

			PdfPCell ticketsTableCell23 = new PdfPCell(
					new Paragraph(" Class Of Travel: " + irTicketDetails.getJrnyClass(), normalTextFont));
			ticketsTable.addCell(ticketsTableCell23);



			PdfPCell ticketsTableCell31 = new PdfPCell(
					new Paragraph(" From: " + irTicketDetails.getFromStation(), normalTextFont));
			ticketsTable.addCell(ticketsTableCell31);

			PdfPCell ticketsTableCell22 = new PdfPCell(
					new Paragraph(" Date & Time of Booking: " + irTicketDetails.getBookingDate(), normalTextFont));
			ticketsTable.addCell(ticketsTableCell22);

			PdfPCell ticketsTableCell33 = new PdfPCell(
					new Paragraph(" To: " + irTicketDetails.getToStn(), normalTextFont));
			ticketsTable.addCell(ticketsTableCell33);



			PdfPCell ticketsTableCell41 = new PdfPCell(
					new Paragraph(" Boarding: " + irTicketDetails.getBoardingPt(), normalTextFont));
			ticketsTable.addCell(ticketsTableCell41);

			String journeyDate = irTicketDetails.getBoardingDate();
			PdfPCell ticketsTableCell32 = new PdfPCell(
					new Paragraph(" Date of Journey: " + journeyDate, normalTextFont));
			ticketsTable.addCell(ticketsTableCell32);

			String schedularDeptText = irTicketDetails.getScheduleDepartre() + "*";
			PdfPCell ticketsTableCell43 = new PdfPCell(
					new Paragraph(" Scheduled Departure: " + journeyDate + " " + schedularDeptText, normalTextFont));
			ticketsTable.addCell(ticketsTableCell43);



			PdfPCell ticketsTableCell51 = new PdfPCell(
					new Paragraph(" Resv. Up to: " + irTicketDetails.getReservUpto(), normalTextFont));
			ticketsTable.addCell(ticketsTableCell51);

			String dateOfBoarding = "";
			if (irTicketDetails.getJrnyDate() != null) {
				dateOfBoarding = irTicketDetails.getJrnyDate();
			} else {
				dateOfBoarding = irTicketDetails.getBoardingDate();
			}
			PdfPCell ticketsTableCell42 = new PdfPCell(
					new Paragraph(" Date of Boarding: " + dateOfBoarding, normalTextFont));
			ticketsTable.addCell(ticketsTableCell42);

			PdfPCell ticketsTableCell53 = new PdfPCell(new Paragraph(
					"Adult :0" + nA + " " + "" + "Child :0" + nC + " " + "Senior :0" + nS, normalTextFont));
			ticketsTable.addCell(ticketsTableCell53);




			String mobilenumbe = irTicketDetails.getMobNo();
			PdfPCell ticketsTableCell61 = new PdfPCell(
					new Paragraph(" Passenger Mobile No: " + mobilenumbe, normalTextFont));
			ticketsTable.addCell(ticketsTableCell61);

			String arrivalTimeStr = getArrivalTimeString(irTicketDetails.getArrivalTime().trim());
			PdfPCell ticketsTableCell52 = new PdfPCell(
					new Paragraph(" Scheduled Arrival: " + arrivalTimeStr, normalTextFont));
			ticketsTable.addCell(ticketsTableCell52);

			String distanceStr = "";
			if (irTicketDetails.getDistance() > 999) {
				distanceStr = irTicketDetails.getDistance() + " KM";
			} else if (irTicketDetails.getDistance() > 99) {
				distanceStr = "0" + irTicketDetails.getDistance() + " KM";
			} else {
				distanceStr = "00" + irTicketDetails.getDistance() + " KM";
			}

			PdfPCell ticketsTableCell62 = new PdfPCell(new Paragraph(" Distance: " + distanceStr, normalTextFont));
			ticketsTable.addCell(ticketsTableCell62);


			Paragraph address = new Paragraph(" Passenger Address:-", normalTextBoldFont);
			address.add(new Paragraph(ERSConstants.passengerAddress, normalTextFont));
			PdfPCell ticketsTableCell71 = new PdfPCell(address);

			ticketsTableCell71.setColspan(3);
			ticketsTable.addCell(ticketsTableCell71);


			firstPage.add(ticketsTable);

			PdfPTable fareDetailTable = new PdfPTable(1);
			fareDetailTable.setWidthPercentage(100f);
			int[] titleTableasWidths = { 95 };
			fareDetailTable.setWidths(titleTableasWidths);
			PdfPCell fareDetailTitle = new PdfPCell(new Paragraph("FARE DETAILS:", normalTextBoldFont));
			fareDetailTitle.setColspan(0);
			fareDetailTitle.setBorder(0);
			fareDetailTable.addCell(fareDetailTitle);
			firstPage.add(fareDetailTable);

			/*----------------- FARE DETAILS TABLE START -----------------*/

			PdfPTable fareDtlsTable = new PdfPTable(4);

			fareDtlsTable.setWidthPercentage(100f);
			int[] fareDtlsTableWidths = { 7, 25, 18, 45 };
			fareDtlsTable.setWidths(fareDtlsTableWidths);

			Double serviceTax = irTicketDetails.getServiceTax();

			PdfPCell fareDtlsTable11 = new PdfPCell(new Paragraph(" 1", normalTextFont));
			fareDtlsTable.addCell(fareDtlsTable11);
			if (serviceTax > 0) {
				PdfPCell fareDtlsTable12 = new PdfPCell(new Paragraph(" Ticket Fare **", normalTextFont));
				fareDtlsTable.addCell(fareDtlsTable12);
			} else {
				PdfPCell fareDtlsTable12 = new PdfPCell(new Paragraph(" Ticket Fare", normalTextFont));
				fareDtlsTable.addCell(fareDtlsTable12);
			}

			PdfPCell fareDtlsTable13 = new PdfPCell(
					new Paragraph(" Rs. " + irTicketDetails.getBaseFare(), normalTextFont));
			fareDtlsTable13.setHorizontalAlignment(Element.ALIGN_RIGHT);
			fareDtlsTable.addCell(fareDtlsTable13);
			PdfPCell fareDtlsTable14 = new PdfPCell(
					new Paragraph(" " + convertDecimalToWords(irTicketDetails.getBaseFare() + ""), normalTextFont));
			fareDtlsTable.addCell(fareDtlsTable14);

			PdfPCell fareDtlsTable21 = new PdfPCell(new Paragraph(" 2", normalTextFont));
			fareDtlsTable.addCell(fareDtlsTable21);
			PdfPCell fareDtlsTable22 = new PdfPCell(new Paragraph(" IRCTCService Charges #", normalTextFont));
			fareDtlsTable.addCell(fareDtlsTable22);
			PdfPCell fareDtlsTable23 = new PdfPCell(
					new Paragraph(" Rs. " + irTicketDetails.getIrctcServCharg(), normalTextFont));
			fareDtlsTable23.setHorizontalAlignment(Element.ALIGN_RIGHT);
			fareDtlsTable.addCell(fareDtlsTable23);
			PdfPCell fareDtlsTable24 = new PdfPCell(new Paragraph(
					" " + convertDecimalToWords(irTicketDetails.getIrctcServCharg() + ""), normalTextFont));
			fareDtlsTable.addCell(fareDtlsTable24);

			PdfPCell fareDtlsTable41 = new PdfPCell(new Paragraph(" 3", normalTextFont));
			fareDtlsTable.addCell(fareDtlsTable41);
			PdfPCell fareDtlsTable42 = new PdfPCell(new Paragraph(" Total##", normalTextFont));
			fareDtlsTable.addCell(fareDtlsTable42);
			PdfPCell fareDtlsTable43 = new PdfPCell(
					new Paragraph(" Rs. " + irTicketDetails.getTotalFare(), normalTextFont));
			fareDtlsTable43.setHorizontalAlignment(Element.ALIGN_RIGHT);
			fareDtlsTable.addCell(fareDtlsTable43);
			PdfPCell fareDtlsTable44 = new PdfPCell(
					new Paragraph(" " + convertDecimalToWords(irTicketDetails.getTotalFare() + ""), normalTextFont));
			fareDtlsTable.addCell(fareDtlsTable44);

			firstPage.add(fareDtlsTable);

			if (serviceTax > 0) {
				PdfPTable titleTable = new PdfPTable(1);
				titleTable.setWidthPercentage(100f);
				int[] titleTableWidths = { 95 };
				titleTable.setWidths(titleTableWidths);
				PdfPCell title = new PdfPCell(
						new Paragraph("** Inclusive of GST - Rs/- " + serviceTax + " Only", normalTextFont));
				title.setColspan(0);
				title.setBorder(0);
				titleTable.addCell(title);
				firstPage.add(titleTable);

			}

			PdfPTable titleTable = new PdfPTable(1);
			titleTable.setWidthPercentage(100f);
			titleTable.setWidths(new int[] { 95 });
			PdfPCell lineTitle = new PdfPCell(
					new Paragraph("# Service Charges per e-ticket irrespective of number of passengers on the ticket.",
							normalTextFont));
			lineTitle.setColspan(0);
			lineTitle.setBorder(0);
			titleTable.addCell(lineTitle);
			firstPage.add(titleTable);

			/*----------------- FARE DETAILS TABLE END -----------------*/

			PdfPTable passTable = new PdfPTable(1);
			passTable.setWidthPercentage(100f);
			int[] titleTableWidths = { 95 };
			passTable.setWidths(titleTableWidths);
			PdfPCell title = new PdfPCell(new Paragraph("PASSENGER DETAILS:", normalTextBoldFont));
			title.setColspan(0);
			title.setBorder(0);
			passTable.addCell(title);
			firstPage.add(passTable);

			if (isTatkalFlag) {

				/*----------------- PASSANGER DETAILS TABLE START -----------------*/

				PdfPTable passangerDtlsTable = new PdfPTable(7);
				passangerDtlsTable.setWidthPercentage(100f);
				int[] passangerDtlsTableWidths = { 5, 15, 5, 10, 10, 30, 25 };
				passangerDtlsTable.setWidths(passangerDtlsTableWidths);

				PdfPCell passangerDtlsTable11 = new PdfPCell(
						new Paragraph("SNO.", new Font(FontFamily.TIMES_ROMAN, 9)));
				passangerDtlsTable.addCell(passangerDtlsTable11);
				PdfPCell passangerDtlsTable12 = new PdfPCell(
						new Paragraph("Name", new Font(FontFamily.TIMES_ROMAN, 9)));
				passangerDtlsTable.addCell(passangerDtlsTable12);
				PdfPCell passangerDtlsTable13 = new PdfPCell(
						new Paragraph("Age", new Font(FontFamily.TIMES_ROMAN, 9)));
				passangerDtlsTable.addCell(passangerDtlsTable13);
				PdfPCell passangerDtlsTable14 = new PdfPCell(
						new Paragraph("Sex", new Font(FontFamily.TIMES_ROMAN, 9)));
				passangerDtlsTable.addCell(passangerDtlsTable14);
				PdfPCell passangerDtlsTable15 = new PdfPCell(
						new Paragraph("Concession code ", new Font(FontFamily.TIMES_ROMAN, 9)));
				passangerDtlsTable.addCell(passangerDtlsTable15);
				PdfPCell passangerDtlsTable16 = new PdfPCell(new Paragraph(
						"Booking Status/Current Status/Coach No/Seat No", new Font(FontFamily.TIMES_ROMAN, 9)));
				passangerDtlsTable.addCell(passangerDtlsTable16);
				PdfPCell passangerDtlsTable17 = new PdfPCell(
						new Paragraph("ID card Type/ID card No.", new Font(FontFamily.TIMES_ROMAN, 9)));
				passangerDtlsTable.addCell(passangerDtlsTable17);

				int pxnNo = 1;
				String ticketCurrentStatus = "";
				String concessionCode = "";
				int gender = 0;
				int concessionCodeVal = 0;

				if (bookingDetailFromListTable != null && !bookingDetailFromListTable.isEmpty()) {
					for (int i = 0; i < bookingDetailFromListTable.size(); i++) {
						String masterPassCardType = "";
						String masterPassCardNumber = "";
						passangerDtlsTable.addCell(new Paragraph(" " + pxnNo + "", normalTextFont));
						passangerDtlsTable
								.addCell(new Paragraph(bookingDetailFromListTable.get(i).getName(), normalTextFont));
						passangerDtlsTable.addCell(
								new Paragraph(bookingDetailFromListTable.get(i).getAge() + "", normalTextFont));
						passangerDtlsTable.addCell(new Paragraph(
								bookingDetailFromListTable.get(i).getGender().toString(), normalTextFont));

						gender = bookingDetailFromListTable.get(i).getGender().ordinal();
						concessionCodeVal = bookingDetailFromListTable.get(i).getIsConcession();

						if (concessionCodeVal == 0) {
							if (gender == 1)
								concessionCode = "SRTZNW";
							else
								concessionCode = "SRTZN";
						}

						passangerDtlsTable.addCell(new Paragraph(concessionCode, normalTextFont));
						ticketCurrentStatus = bookingDetailFromListTable.get(i).getTktCurrentStatus();

						try {
							if (ticketCurrentStatus != null) {
								if (ticketCurrentStatus.equalsIgnoreCase("Can/Mod")
										|| ticketCurrentStatus.equalsIgnoreCase("Can")) {
									passangerDtlsTable.addCell(new Paragraph("/Cancelled", normalTextFont));
								} else {
									passangerDtlsTable
											.addCell(new Paragraph(
													bookingDetailFromListTable.get(i).getTktBookingStatus() + "/"
															+ bookingDetailFromListTable.get(i).getTktCurrentStatus()
															+ "/ " + bookingDetailFromListTable.get(i).getCoach() + "/ "
															+ bookingDetailFromListTable.get(i).getSeat() + "/ "
															+ bookingDetailFromListTable.get(i).getBerth(),
													normalTextFont));
								}
							}
						} catch (Exception e) {
							if (ticketCurrentStatus != null) {
								if (ticketCurrentStatus.equalsIgnoreCase("Can/Mod")
										|| ticketCurrentStatus.equalsIgnoreCase("Can")) {
									passangerDtlsTable.addCell(new Paragraph("/Cancelled", normalTextFont));
								} else {
									passangerDtlsTable
											.addCell(new Paragraph(
													"/" + bookingDetailFromListTable.get(i).getTktCurrentStatus() + "/ "
															+ bookingDetailFromListTable.get(i).getCoach() + "/ "
															+ bookingDetailFromListTable.get(i).getSeat() + "/ "
															+ bookingDetailFromListTable.get(i).getBerth(),
													normalTextFont));
								}
							}
						}

						if (bookingDetailFromListTable.get(i).getIdCardType() != null) {
							masterPassCardType = bookingDetailFromListTable.get(i).getIdCardType();
						}
						if (bookingDetailFromListTable.get(i).getIdCardNo() != null
								&& bookingDetailFromListTable.get(i).getIdCardNo().trim().length() > 3) {
							masterPassCardNumber = bookingDetailFromListTable.get(i).getIdCardNo();
						}

						if (masterPassCardType.length() > 3)
							passangerDtlsTable.addCell(
									new Paragraph(masterPassCardType + "/" + masterPassCardNumber, normalTextFont));
						else
							passangerDtlsTable.addCell(new Paragraph("", normalTextFont));

						pxnNo++;
					}
				}

				firstPage.add(passangerDtlsTable);

				/*----------------- PASSANGER DETAILS TABLE END -----------------*/

			} else {
				/*----------------- PASSANGER DETAILS TABLE START -----------------*/
				PdfPTable passangerDtlsTable;

				if (currentCancelStatus.equalsIgnoreCase("15")) {
					passangerDtlsTable = new PdfPTable(7);
					passangerDtlsTable.setWidthPercentage(100f);
					int[] passangerDtlsTableWidths = new int[] { 5, 25, 10, 10, 10, 35, 11 };
					passangerDtlsTable.setWidths(passangerDtlsTableWidths);
				} else {
					passangerDtlsTable = new PdfPTable(6);
					passangerDtlsTable.setWidthPercentage(100f);
					int[] passangerDtlsTableWidths = new int[] { 5, 25, 10, 10, 10, 35 };
					passangerDtlsTable.setWidths(passangerDtlsTableWidths);
				}

				PdfPCell passangerDtlsTable11 = new PdfPCell(
						new Paragraph("SNO.", new Font(FontFamily.TIMES_ROMAN, 9)));
				passangerDtlsTable.addCell(passangerDtlsTable11);
				PdfPCell passangerDtlsTable12 = new PdfPCell(
						new Paragraph("Name", new Font(FontFamily.TIMES_ROMAN, 9)));
				passangerDtlsTable.addCell(passangerDtlsTable12);
				PdfPCell passangerDtlsTable13 = new PdfPCell(
						new Paragraph("Age", new Font(FontFamily.TIMES_ROMAN, 9)));
				passangerDtlsTable.addCell(passangerDtlsTable13);
				PdfPCell passangerDtlsTable14 = new PdfPCell(
						new Paragraph("Sex", new Font(FontFamily.TIMES_ROMAN, 9)));
				passangerDtlsTable.addCell(passangerDtlsTable14);
				PdfPCell passangerDtlsTable15 = new PdfPCell(
						new Paragraph("Concession code ", new Font(FontFamily.TIMES_ROMAN, 9)));
				passangerDtlsTable.addCell(passangerDtlsTable15);
				PdfPCell passangerDtlsTable16 = new PdfPCell(new Paragraph(
						"Booking Status/Current Status/Coach No/Seat No", new Font(FontFamily.TIMES_ROMAN, 9)));
				passangerDtlsTable.addCell(passangerDtlsTable16);

				if (currentCancelStatus.equalsIgnoreCase("15")) {
					PdfPCell passangerDtlsTable17 = new PdfPCell(
							new Paragraph("Cancellation Date", new Font(FontFamily.TIMES_ROMAN, 9)));
					passangerDtlsTable.addCell(passangerDtlsTable17);
				}

				int pxnNo = 1;
				String ticketCurrentStatus = "";
				String concessionCode = "";
				int gender = 0;
				int concessionCodeVal = 0;

				if (bookingDetailFromListTable != null && !bookingDetailFromListTable.isEmpty()) {
					for (int i = 0; i < bookingDetailFromListTable.size(); i++) {
						passangerDtlsTable.addCell(new Paragraph(" " + pxnNo + "", normalTextFont));
						passangerDtlsTable
								.addCell(new Paragraph(bookingDetailFromListTable.get(i).getName(), normalTextFont));
						passangerDtlsTable.addCell(
								new Paragraph(bookingDetailFromListTable.get(i).getAge() + "", normalTextFont));
						passangerDtlsTable.addCell(new Paragraph(
								Optional.ofNullable(bookingDetailFromListTable.get(i).getGender()).orElse(Gender.MALE).getDisplayValue(), normalTextFont));

						gender = bookingDetailFromListTable.get(i).getGender().ordinal();
						concessionCodeVal = bookingDetailFromListTable.get(i).getIsConcession();

						if (concessionCodeVal == 0) {
							if (gender == 1)
								concessionCode = "SRTZNW";
							else
								concessionCode = "SRTZN";
						}

						passangerDtlsTable.addCell(new Paragraph(concessionCode, normalTextFont));

						ticketCurrentStatus = bookingDetailFromListTable.get(i).getTktCurrentStatus();
						try {
							if (ticketCurrentStatus != null) {
								if (ticketCurrentStatus.equalsIgnoreCase("Can/Mod")
										|| ticketCurrentStatus.equalsIgnoreCase("Can")) {
									passangerDtlsTable.addCell(new Paragraph("/Cancelled", normalTextFont));
								} else {
									passangerDtlsTable
											.addCell(new Paragraph(
													bookingDetailFromListTable.get(i).getTktBookingStatus() + "/"
															+ bookingDetailFromListTable.get(i).getTktCurrentStatus()
															+ "/ " + bookingDetailFromListTable.get(i).getCoach() + "/ "
															+ bookingDetailFromListTable.get(i).getSeat() + "/ "
															+ bookingDetailFromListTable.get(i).getBerth(),
													normalTextFont));
								}
							}
						} catch (Exception e) {
							if (ticketCurrentStatus != null) {
								if (ticketCurrentStatus.equalsIgnoreCase("Can/Mod")
										|| ticketCurrentStatus.equalsIgnoreCase("Can")) {
									passangerDtlsTable.addCell(new Paragraph("/Cancelled", normalTextFont));
								} else {
									passangerDtlsTable
											.addCell(new Paragraph(
													"/" + bookingDetailFromListTable.get(i).getTktCurrentStatus() + "/ "
															+ bookingDetailFromListTable.get(i).getCoach() + "/ "
															+ bookingDetailFromListTable.get(i).getSeat() + "/ "
															+ bookingDetailFromListTable.get(i).getBerth(),
													normalTextFont));
								}
							}
						}
						pxnNo++;

						if (currentCancelStatus.equalsIgnoreCase("15")) {
							passangerDtlsTable.addCell(
									new Paragraph(bookingDetailFromListTable.get(i).getCanceltnDate(), normalTextFont));
						}
					}
				}

				firstPage.add(passangerDtlsTable);

				/*----------------- PASSANGER DETAILS TABLE END -----------------*/
			}

			PdfPTable agentTitleTable = new PdfPTable(1);
			agentTitleTable.setWidthPercentage(100f);
			int[] agentTitleTableWidths = { 95 };
			agentTitleTable.setWidths(agentTitleTableWidths);
			PdfPCell agentTitle = new PdfPCell(new Paragraph("AGENT DETAILS:", normalTextBoldFont));
			agentTitle.setColspan(0);
			agentTitle.setBorder(0);
			agentTitleTable.addCell(agentTitle);
			firstPage.add(agentTitleTable);

			/*----------------- AGENT DETAILS TABLE START -----------------*/

			PdfPTable agentDtlsTable = new PdfPTable(4);
			agentDtlsTable.setWidthPercentage(100f);
			int[] agentDtlsTableWidths = { 25, 25, 25, 20 };
			agentDtlsTable.setWidths(agentDtlsTableWidths);

			PdfPCell agentDtlsTable01 = new PdfPCell(new Paragraph(ERSConstants.linePrincipalAgent, normalTextFont));
			agentDtlsTable01.setColspan(2);
			agentDtlsTable.addCell(agentDtlsTable01);

			PdfPCell agentDtlsTable02 = new PdfPCell(new Paragraph(ERSConstants.lineCorporateName, normalTextFont));
			agentDtlsTable02.setColspan(2);
			agentDtlsTable.addCell(agentDtlsTable02);

			PdfPCell agentDtlsTable11 = new PdfPCell(new Paragraph(ERSConstants.lineAgentName, normalTextFont));
			agentDtlsTable.addCell(agentDtlsTable11);
			PdfPCell agentDtlsTable12 = new PdfPCell(new Paragraph(ERSConstants.lineEmailId, normalTextFont));
			agentDtlsTable12.setColspan(2);
			agentDtlsTable.addCell(agentDtlsTable12);
			PdfPCell agentDtlsTable13 = new PdfPCell(new Paragraph(ERSConstants.linePhoneNo, normalTextFont));
			agentDtlsTable.addCell(agentDtlsTable13);

			PdfPCell agentDtlsTable21 = new PdfPCell(new Paragraph(ERSConstants.lineAddress, normalTextFont));
			agentDtlsTable21.setColspan(4);
			agentDtlsTable.addCell(agentDtlsTable21);

			firstPage.add(agentDtlsTable);

			/*----------------- AGENT DETAILS TABLE END -----------------*/

			/*----------------- DEDUCTIBLE AMOUNT DETAILS TABLE START -----------------*/

			if (irlaDeductFlag) {
				Font deductibleTextFont = new Font(FontFamily.TIMES_ROMAN, 8, 0, BaseColor.BLACK);
				PdfPTable deductibleDetailTable = new PdfPTable(1);
				deductibleDetailTable.setSpacingBefore(5f);
				deductibleDetailTable.setWidthPercentage(100f);
				int[] deductibleTableasWidths = { 95 };
				deductibleDetailTable.setWidths(deductibleTableasWidths);
				PdfPCell deductibleDetailTitle = new PdfPCell(new Paragraph("Note:", normalTextBoldFont));
				deductibleDetailTitle.setColspan(0);
				deductibleDetailTitle.setBorder(0);
				deductibleDetailTable.addCell(deductibleDetailTitle);
				firstPage.add(deductibleDetailTable);

				PdfPTable deductibleDtlsTable = new PdfPTable(2);
				deductibleDtlsTable.setWidthPercentage(100f);
				int[] deductibleDtlsTableWidths = { 45, 50 };
				deductibleDtlsTable.setWidths(deductibleDtlsTableWidths);

				PdfPCell deductibleDtlsTable11 = new PdfPCell(
						new Paragraph(" Amount Deductible from IRLA* ", deductibleTextFont));
				deductibleDtlsTable.addCell(deductibleDtlsTable11);

				PdfPCell deductibleDtlsTable12 = new PdfPCell(
						new Paragraph("Rs. " + deductibleAmount, normalTextFont));
				deductibleDtlsTable.addCell(deductibleDtlsTable12);

				PdfPCell deductibleDtlsTable21 = new PdfPCell(
						new Paragraph(ERSConstants.deductible_Amount1, deductibleTextFont));
				deductibleDtlsTable21.setColspan(2);
				deductibleDtlsTable.addCell(deductibleDtlsTable21);

				firstPage.add(deductibleDtlsTable);
			}

			/*----------------- DEDUCTIBLE AMOUNT DETAILS TABLE END -----------------*/

			/*----------------- IMPORTENT NOTICE BLOCK START -----------------*/

			PdfPTable impContentTable = new PdfPTable(1);
			impContentTable.setWidthPercentage(100f);
			impContentTable.getDefaultCell().setPadding(0);
			impContentTable.getDefaultCell().setSpaceCharRatio(0);
			impContentTable.getDefaultCell().setBorderWidth(0);
			int[] impContentTableWidths = { 95 };
			impContentTable.setWidths(impContentTableWidths);

			PdfPCell impMsgHeader = new PdfPCell(
			new Paragraph(ERSConstants.imp_Msg_Header, new Font(FontFamily.TIMES_ROMAN, 9, Font.BOLD)));
			impMsgHeader.setBorder(0);
			impContentTable.addCell(impMsgHeader);

			normalTextFont = new Font(FontFamily.TIMES_ROMAN, 7, Font.BOLD);

			PdfPCell impMsgCNFLine01 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line01, normalTextFont));
			impMsgCNFLine01.setBorder(0);
			impContentTable.addCell(impMsgCNFLine01);

			PdfPCell impMsgCNFLine02 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line02, normalTextFont));
			impMsgCNFLine02.setBorder(0);
			impContentTable.addCell(impMsgCNFLine02);

			PdfPCell impMsgCNFLine03 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line03, normalTextFont));
			impMsgCNFLine03.setBorder(0);
			impContentTable.addCell(impMsgCNFLine03);

			PdfPCell impMsgCNFLine04 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line04, normalTextFont));
			impMsgCNFLine04.setBorder(0);
			impContentTable.addCell(impMsgCNFLine04);

			PdfPCell impMsgCNFLine05 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line05, normalTextFont));
			impMsgCNFLine05.setBorder(0);
			impContentTable.addCell(impMsgCNFLine05);

			PdfPCell impMsgCNFLine06 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line06, normalTextFont));
			impMsgCNFLine06.setBorder(0);
			impContentTable.addCell(impMsgCNFLine06);

			PdfPCell impMsgCNFLine07 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line07, normalTextFont));
			impMsgCNFLine07.setBorder(0);
			impContentTable.addCell(impMsgCNFLine07);

			PdfPCell impMsgCNFLine08 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line08, normalTextFont));
			impMsgCNFLine08.setBorder(0);
			impContentTable.addCell(impMsgCNFLine08);

			PdfPCell impMsgCNFLine09 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line09, normalTextFont));
			impMsgCNFLine09.setBorder(0);
			impContentTable.addCell(impMsgCNFLine09);

			PdfPCell impMsgCNFLine10 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line10, normalTextFont));
			impMsgCNFLine10.setBorder(0);
			impContentTable.addCell(impMsgCNFLine10);

			PdfPCell impMsgCNFLine11 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line11, normalTextFont));
			impMsgCNFLine11.setBorder(0);
			impContentTable.addCell(impMsgCNFLine11);

			PdfPCell impMsgCNFLine12 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line12, normalTextFont));
			impMsgCNFLine12.setBorder(0);
			impContentTable.addCell(impMsgCNFLine12);

			PdfPCell impMsgCNFLine13 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line13, normalTextFont));
			impMsgCNFLine13.setBorder(0);
			impContentTable.addCell(impMsgCNFLine13);

			PdfPCell impMsgCNFLine14 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line14, normalTextFont));
			impMsgCNFLine14.setBorder(0);
			impContentTable.addCell(impMsgCNFLine14);

			PdfPCell impMsgCNFLine15 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line15, normalTextFont));
			impMsgCNFLine15.setBorder(0);
			impContentTable.addCell(impMsgCNFLine15);

			PdfPCell impMsgCNFLine16 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line16, normalTextFont));
			impMsgCNFLine16.setBorder(0);
			impContentTable.addCell(impMsgCNFLine16);

			PdfPCell impMsgCNFLine17 = new PdfPCell(new Paragraph("" + ERSConstants.imp_Msg_Line17, normalTextFont));
			impMsgCNFLine17.setBorder(0);
			impContentTable.addCell(impMsgCNFLine17);

			firstPage.add(impContentTable);

			/*----------------- IMPORTENT NOTICE BLOCK END -----------------*/

			/*--------------------------------------------------------------------------------*/
			/*--------------------------------ERS Back End Page Start ------------------------*/
			/*--------------------------------------------------------------------------------*/

			normalTextFont = new Font(FontFamily.TIMES_ROMAN, 6);
			Font normalTextBold = new Font(FontFamily.TIMES_ROMAN, 6, Font.BOLD);

			PdfPTable backPageContentTable = new PdfPTable(1);
			backPageContentTable.setWidthPercentage(100f);
			backPageContentTable.getDefaultCell().setPadding(2);
			backPageContentTable.getDefaultCell().setBorderWidth(95f);

			int[] backPageContentTableWidths = { 95 };
			backPageContentTable.setWidths(backPageContentTableWidths);

			PdfPCell spHeading = new PdfPCell(
			new Paragraph(ERSConstants.sp_Heading, new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD)));
			spHeading.setBorder(0);
			spHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
			backPageContentTable.addCell(spHeading);

			PdfPCell spBlock01Heading = new PdfPCell(new Paragraph(ERSConstants.sp_Block01_HeadingA, normalTextBold));
			spBlock01Heading.setBorder(0);
			backPageContentTable.addCell(spBlock01Heading);
			PdfPCell spBlock01Line01 = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block01_HeadingA01, normalTextFont));
			spBlock01Line01.setBorder(0);
			backPageContentTable.addCell(spBlock01Line01);

			PdfPCell spBlock01Line02 = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block01_HeadingA02, normalTextFont));
			spBlock01Line02.setBorder(0);
			backPageContentTable.addCell(spBlock01Line02);

			PdfPCell spBlock01Line03 = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block01_HeadingA03, normalTextFont));
			spBlock01Line03.setBorder(0);
			backPageContentTable.addCell(spBlock01Line03);

			PdfPCell spBlock02HeadingB = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block02_HeadingB, normalTextBold));
			spBlock02HeadingB.setBorder(0);
			backPageContentTable.addCell(spBlock02HeadingB);

			PdfPCell spBlock02Line02 = new PdfPCell(new Paragraph(ERSConstants.sp_Block02_HeadingB01, normalTextFont));
			spBlock02Line02.setBorder(0);
			backPageContentTable.addCell(spBlock02Line02);

			PdfPCell spBlock03Heading = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block02_HeadingB02, normalTextFont));
			spBlock03Heading.setBorder(0);
			backPageContentTable.addCell(spBlock03Heading);

			PdfPCell spBlock03HeadingC = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingC, normalTextBold));
			spBlock03HeadingC.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingC);

			PdfPCell spBlock03HeadingC01 = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingC01, normalTextFont));
			spBlock03HeadingC01.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingC01);

			PdfPCell spBlock03HeadingC02 = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingC02, normalTextFont));
			spBlock03HeadingC02.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingC02);

			PdfPCell spBlock03HeadingC03 = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingC03, normalTextFont));
			spBlock03HeadingC03.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingC03);

			PdfPCell spBlock03HeadingC04 = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingC04, normalTextFont));
			spBlock03HeadingC04.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingC04);

			PdfPCell spBlock03HeadingC05 = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingC05, normalTextFont));
			spBlock03HeadingC05.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingC05);

			PdfPCell spBlock03HeadingD = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingD, normalTextBold));
			spBlock03HeadingD.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingD);

			PdfPCell spBlock03HeadingD001 = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingD001, normalTextFont));
			spBlock03HeadingD001.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingD001);

			PdfPCell spBlock03HeadingD01 = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingD01, normalTextFont));
			spBlock03HeadingD01.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingD01);

			PdfPCell spBlock03HeadingD02 = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingD02, normalTextFont));
			spBlock03HeadingD02.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingD02);

			PdfPCell spBlock03HeadingD03 = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingD03, normalTextFont));
			spBlock03HeadingD03.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingD03);

			PdfPCell spBlock03HeadingD04 = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingD04, normalTextFont));
			spBlock03HeadingD04.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingD04);

			PdfPCell spBlock03HeadingD05 = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingD05, normalTextFont));
			spBlock03HeadingD05.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingD05);

			PdfPCell spBlock03HeadingE = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingE, normalTextFont));
			spBlock03HeadingE.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingE);

			PdfPCell spBlock03HeadingF = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingF, normalTextFont));
			spBlock03HeadingF.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingF);

			PdfPCell spBlock03HeadingG = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingG, normalTextFont));
			spBlock03HeadingG.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingG);

			PdfPCell spBlock03HeadingH = new PdfPCell(
					new Paragraph(ERSConstants.sp_Block03_HeadingH, normalTextFont));
			spBlock03HeadingH.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingH);

			Phrase phrase = new Phrase();
			Chunk chunk = new Chunk(ERSConstants.sp_Block03_HeadingI, normalTextBold);
			phrase.add(chunk);
			chunk = new Chunk(ERSConstants.sp_Block08_Line01b, normalTextFont);
			phrase.add(chunk);
			chunk = new Chunk(ERSConstants.sp_Block08_Line01c, normalTextFont);
			phrase.add(chunk);
			PdfPCell spBlock03HeadingI = new PdfPCell(new Paragraph(phrase));
			spBlock03HeadingI.setBorder(0);
			backPageContentTable.addCell(spBlock03HeadingI);

			firstPage.add(backPageContentTable);

			document.add(firstPage);

			/*--------------------------------------------------------------------------------*/
			/*--------------------------------ERS Back End Page End   ------------------------*/
			/*--------------------------------------------------------------------------------*/

			/*--------------------------------FINALLY BLOCK Start ------------------------*/
			document.close();
			response.setContentLength(baos.size());
			response.setContentType("application/pdf");
			out = response.getOutputStream();
			baos.writeTo(out);
			out.flush();
			return true;
		}
		return false;
	}

	/*--------------------------------FINALLY BLOCK END ------------------------*/

///*----------------------Methods To Convert Number To Words START---------------------*/
//
//String[] unitdo ={"", " One", " Two", " Three", " Four", " Five",
//         " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve",
//         " Thirteen", " Fourteen", " Fifteen",  " Sixteen", " Seventeen", 
//         " Eighteen", " Nineteen"};
//String[] tens =  {"", "Ten", " Twenty", " Thirty", " Forty", " Fifty",
//         " Sixty", " Seventy", " Eighty"," Ninety"};
//String[] digit = {"", " Hundred", " Thousand", " Lakh", " Crore"};
// int r;
// 
// 
// //Count the number of digits in the input number
// int numberCount(int num)
// {
//     int cnt=0;
//
//     while (num>0)
//     {
//       r = num%10;
//       cnt++;
//       num = num / 10;
//     }
//
//       return cnt;
// }
// 
// 
// //Function for Conversion of two digit
//
// String twonum(int numq)
// {
//      int numr, nq;
//      String ltr="";
//
//      nq = numq / 10;
//      numr = numq % 10;
//
//      if (numq>19)
//        {
//      ltr=ltr+tens[nq]+unitdo[numr];
//        }
//      else
//        {
//      ltr = ltr+unitdo[numq];
//        }
//
//      return ltr;
// }
//
// //Function for Conversion of three digit
//
// String threenum(int numq)
// {
//        int numr, nq;
//        String ltr = "";
//
//        nq = numq / 100;
//        numr = numq % 100;
//
//        if (numr == 0)
//         {
//         ltr = ltr + unitdo[nq]+digit[1];
//          }
//        else
//         {
//         ltr = ltr +unitdo[nq]+digit[1]+" and"+twonum(numr);
//         }
//        return ltr;
//
// }
// 
//public String getNumberInWords(String number){
//	
//	String words="";
//	 //Defining variables q is quotient, r is remainder
//
//    int len, q=0, r=0;
//    String ltr = " ";
//    String Str = "Rupees";
//    int num = Integer.parseInt(number);
//
//    if (num <= 0) System.out.println("Zero or Negative number not for conversion");
//
//    while (num>0)
//    {
//
//       len = numberCount(num);
//
//       //Take the length of the number and do letter conversion
//
//       switch (len)
//
//       {
//            case 8:
//                    q=num/10000000;
//                    r=num%10000000;
//                    ltr = twonum(q);
//                    Str = Str+ltr+digit[4];
//                    num = r;
//                    break;
//
//            case 7:
//            case 6:
//                    q=num/100000;
//                    r=num%100000;
//                    ltr = twonum(q);
//                    Str = Str+ltr+digit[3];
//                    num = r;
//                    break;
//
//            case 5:
//            case 4:
//
//                     q=num/1000;
//                     r=num%1000;
//                     ltr = twonum(q);
//                     Str= Str+ltr+digit[2];
//                     num = r;
//                     break;
//
//            case 3:
//
//
//                      if (len == 3)
//                          r = num;
//                      ltr = threenum(r);
//                      Str = Str + ltr;
//                      num = 0;
//                      break;
//
//            case 2:
//
//                     ltr = twonum(num);
//                     Str = Str + ltr;
//                     num=0;
//                     break;
//
//            case 1:
//                     Str = Str + unitdo[num];
//                     num=0;
//                     break;
//            default:
//
//                    num=0;
//                    System.out.println("Exceeding Crore....No conversion");
//                   
//
//       		}
//            if (num==0)
//            	words=Str+" Only";
//      }
//
//	
//	return words;
//	
//}
//
//
//
///*----------------------Methods To Convert Number To Words END---------------------*/
//
///*----------------------Methods To Convert Arrival Time For ERS START-----------------*/
	public String getArrivalTimeString(String arrivalTime) {
		String arrivalTimeStr = "";
		try {
			String arr[] = arrivalTime.split(" ");
			DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class, "arrivalTime=" + arrivalTime + "ArrayLength=" + arr.length);

			if (arr.length > 2) {
				Map<String,String> monthMap = new HashMap<>();
				monthMap.put("01", "Jan");
				monthMap.put("02", "Feb");
				monthMap.put("03", "Mar");
				monthMap.put("04", "Apr");
				monthMap.put("05", "May");
				monthMap.put("06", "Jun");
				monthMap.put("07", "Jul");
				monthMap.put("08", "Aug");
				monthMap.put("09", "Sep");
				monthMap.put("10", "Oct");
				monthMap.put("11", "Nov");
				monthMap.put("12", "Dec");
				String[] dateArr = arr[0].split("/");
				arrivalTimeStr = dateArr[0] + "-" + monthMap.get(dateArr[1]) + "-" + dateArr[2] + " " + arr[3];

			} else {
				arrivalTimeStr = arrivalTime;
			}

		} catch (Exception e) {

		}


		if (arrivalTimeStr == null || arrivalTimeStr.trim().length() == 0) {
			arrivalTimeStr = "N.A.";
		}
		DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, RailTicketPDFService.class, " ## arrivalTimeStr =" + arrivalTimeStr);
		return arrivalTimeStr;
	}

///*----------------------Methods To Convert Arrival Time For ERS END-------------------*/
//
//
///*----------------------Methods To Convert Decimal Number To Words START---------------------*/
//
//
	private String[] tensNames = { "", " Ten", " Twenty", " Thirty", " Forty", " Fifty", " Sixty", " Seventy",
			" Eighty", " Ninety" };
//
	private String[] numNames = { "", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight", " Nine",
			" Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen", " Eighteen",
			" Nineteen" };

//	  
//	  private  String convertLessThanOneThousand(int number) {
//		    String soFar;
//
//		    if (number % 100 < 20){
//		      soFar = numNames[number % 100];
//		      number /= 100;
//		    }
//		    else {
//		      soFar = numNames[number % 10];
//		      number /= 10;
//
//		      soFar = tensNames[number % 10] + soFar;
//		      number /= 10;
//		    }
//		    if (number == 0) return soFar;
//		    return numNames[number] + " Hundred" + soFar;
//		  }
//	  
//	  
//	  public  String convert(long number) 
//	  {
//		    // 0 to 999 999 999 999
//		    if (number == 0) { return "Zero"; }
//
//		    String snumber = Long.toString(number);
//
//		    // pad with "0"
//		    String mask = "000000000000";
//		    DecimalFormat df = new DecimalFormat(mask);
//		    snumber = df.format(number);
//
//		    // XXXnnnnnnnnn 
//		    int billions = Integer.parseInt(snumber.substring(0,3));
//		    // nnnXXXnnnnnn
//		    int millions  = Integer.parseInt(snumber.substring(3,6)); 
//		    // nnnnnnXXXnnn
//		    int hundredThousands = Integer.parseInt(snumber.substring(6,9)); 
//		    // nnnnnnnnnXXX
//		    int thousands = Integer.parseInt(snumber.substring(9,12));    
//
//		    String tradBillions;
//		    switch (billions) {
//		    case 0:
//		      tradBillions = "";
//		      break;
//		    case 1 :
//		      tradBillions = convertLessThanOneThousand(billions) 
//		      + " Billion ";
//		      break;
//		    default :
//		      tradBillions = convertLessThanOneThousand(billions) 
//		      + " Billion ";
//		    }
//		    String result =  tradBillions;
//
//		    String tradMillions;
//		    switch (millions) {
//		    case 0:
//		      tradMillions = "";
//		      break;
//		    case 1 :
//		      tradMillions = convertLessThanOneThousand(millions) 
//		      + " Million ";
//		      break;
//		    default :
//		      tradMillions = convertLessThanOneThousand(millions) 
//		      + " Million ";
//		    }
//		    result =  result + tradMillions;
//
//		    String tradHundredThousands;
//		    switch (hundredThousands) {
//		    case 0:
//		      tradHundredThousands = "";
//		      break;
//		    case 1 :
//		      tradHundredThousands = "One Thousand ";
//		      break;
//		    default :
//		      tradHundredThousands = convertLessThanOneThousand(hundredThousands) 
//		      + " Thousand ";
//		    }
//		    result =  result + tradHundredThousands;
//
//		    String tradThousand;
//		    tradThousand = convertLessThanOneThousand(thousands);
//		    result =  result + tradThousand;
//
//		    // remove extra spaces!
//		    return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
//		  }
//	  
	public String convertDecimalToWords(String phrase) {
		String text = "";
		BigDecimal bd = new BigDecimal(phrase);
		long rupees = bd.longValue();
		BigDecimal result = bd.subtract(bd.setScale(0, RoundingMode.FLOOR)).movePointRight(bd.scale());
		long paisa = result.longValue();

		if (paisa > 0) {
			
			text = "Rupees " + convertNumber(rupees) + " and " + convertNumber(paisa) + " Paisa Only ";
		} else {
			
			text = "Rupees " + convertNumber(rupees) + "Only ";
		}

		return text;
	}

//	  
//	  
	public String convertNumber(long number) {
		
		String convertedNumber = "";

		try {
			convertedNumber += convertOnesTwos((number / 10000000) % 100, " Crore");
			convertedNumber += convertOnesTwos(((number / 100000) % 100), " Lakh");
			convertedNumber += convertOnesTwos(((number / 1000) % 100), " Thousand");
			convertedNumber += convertOnesTwos(((number / 100) % 10), " Hundred");
			convertedNumber += convertOnesTwos((number % 100), " ");
		} catch (Exception e) {
			DODLog.printStackTrace(e, RailTicketPDFService.class, LogConstant.EMAIL_PDF_LOG_FILE);
		
		}

		return convertedNumber.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	}

//		
	public String convertOnesTwos(long n, String ch) {
		String convertedWord = "";
		int num = Math.toIntExact(n);
		if (n > 19) {
			convertedWord += tensNames[num / 10] + " " + numNames[num % 10];
		} else {
			convertedWord += numNames[num];
		}

		if (n > 0) {
			convertedWord += ch;
		}

		return convertedWord;
	}


}
