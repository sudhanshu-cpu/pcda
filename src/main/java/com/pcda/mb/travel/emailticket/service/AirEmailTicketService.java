package com.pcda.mb.travel.emailticket.service;

import java.io.ByteArrayOutputStream;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pcda.mb.reports.airrequestreport.model.GetAirReqIdResponse;
import com.pcda.mb.reports.airrequestreport.model.GetAirRqIdParentModel;
import com.pcda.mb.reports.airrequestreport.service.AirReqReportService;
import com.pcda.mb.travel.emailticket.model.AirTicketPdfModel;
import com.pcda.mb.travel.emailticket.model.EmailPdfTicketResponse;
import com.pcda.mb.travel.emailticket.model.FlightInfo;
import com.pcda.mb.travel.emailticket.model.InvoiceInfoModel;
import com.pcda.mb.travel.emailticket.model.PassengerInfo;
import com.pcda.mb.travel.emailticket.model.PaxCanInvoiceInfo;
import com.pcda.util.DODDATAConstants;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class AirEmailTicketService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AirReqReportService repService;

	public AirTicketPdfModel getAirTicketPdf(String operatorTxnId) {

		DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, AirEmailTicketService.class,
				"[getAirTicketPdf] ### operatorTxnId ###: " + operatorTxnId);
		AirTicketPdfModel airTicketPdfModel = null;

		try {

			ResponseEntity<EmailPdfTicketResponse> entity = restTemplate.exchange(
					PcdaConstant.EMAIL_BASE_URL + "/bookingDetails/" + operatorTxnId, HttpMethod.GET, null,
					new ParameterizedTypeReference<EmailPdfTicketResponse>() {
					});
			EmailPdfTicketResponse response = entity.getBody();

			if (null != response && response.getErrorCode() == 200 && response.getResponse() != null) {
				airTicketPdfModel = response.getResponse();
			}
			DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, AirEmailTicketService.class,
					"### Email PDF ###: " + airTicketPdfModel);

		} catch (Exception e) {
			DODLog.printStackTrace(e, AirEmailTicketService.class, LogConstant.EMAIL_PDF_LOG_FILE);
		}

		return airTicketPdfModel;

	}

	public Integer isTravelTypeDomInt(String tktRequestId) {
		DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, AirEmailTicketService.class,
				"######### tktRequestId ##########: " + tktRequestId);
		Integer domInteger = 0;
		try {
		GetAirReqIdResponse response = repService.getAirReIdData(tktRequestId);
		if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
			GetAirRqIdParentModel parentModel = response.getResponse();
			domInteger = parentModel.getTravelTypeDomInt();
			DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, AirEmailTicketService.class,
					"######### AIR TRAVEL TYPE DOMINT ##########: " + domInteger);
		}
		}
		catch (Exception e) {
			DODLog.printStackTrace(e, AirEmailTicketService.class, LogConstant.EMAIL_PDF_LOG_FILE);
		}
		return domInteger;
	}

	public void createAirATTicketPDF(AirTicketPdfModel airTicketDetailsXml, ByteArrayOutputStream baos,
			String personalNumber, boolean flag) {
		
	
		Document document = null;

		String iataCode = DODDATAConstants.DOD_ATT_IATA_CODE_NUMBER;
		if (airTicketDetailsXml != null) {
			try {

				document = new Document(PageSize.A4, 10, 10, 10, 10);

				  PdfPTable flightDetailTable = new PdfPTable(5);
				BaseColor flightColor = new BaseColor(255, 255, 255);
				  PdfPTable maintable = new PdfPTable(1);

				Font commonBoldFont8 = FontFactory.getFont("", 8, Font.BOLD);
				Font commonBoldFont10 = FontFactory.getFont("", 10, Font.BOLD);
				Font commonBoldFont12 = FontFactory.getFont("", 12, Font.BOLD);
				Font commonNormalFont7 = FontFactory.getFont("", 7);

				BaseColor redColor = new BaseColor(255, 0, 0);

				Font commonBoldFont7Red = FontFactory.getFont("", 7, Font.BOLD, redColor);
				Font commonBoldFont7 = FontFactory.getFont("", 7, Font.BOLD);
				Font commonBoldFont6 = FontFactory.getFont("", 6, Font.BOLD);
				/* Block to Find the Travel Type DomIntiger */

				
				  String tktRequestId=airTicketDetailsXml.getRequestId(); 
				  AtomicInteger domInt=new AtomicInteger(0); 
				  int domIntiger =  Optional.ofNullable(isTravelTypeDomInt(tktRequestId)).orElse(0);  
				  domInt.set(domIntiger);

				/* Block to Find the Travel Type DomIntiger */

				if (document != null) {
					PdfWriter writer = PdfWriter.getInstance(document, baos);
					if (flag) {
						writer.setEncryption(personalNumber.getBytes(), personalNumber.getBytes(),
								PdfWriter.ALLOW_PRINTING, PdfWriter.ALLOW_COPY);

	        	}
	        	document.open();
					String irctcLogoPath = DODDATAConstants.PCDA_LOGO_PATH;

				 PdfPTable imageTable = new PdfPTable(1);
					Image addlogo = null;
					try {

						addlogo = Image.getInstance(irctcLogoPath + "/DTS_Header.png");

					} catch (Exception e) {
						DODLog.printStackTrace(e, AirEmailTicketService.class, LogConstant.EMAIL_PDF_LOG_FILE);
					}

					addlogo.scaleToFit(455f, 6700f);

					PdfPCell cell0 = new PdfPCell(addlogo);
					cell0.setBorder(Rectangle.NO_BORDER);
					cell0.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
					cell0.setVerticalAlignment(Rectangle.ALIGN_CENTER);
					imageTable.addCell(cell0);

					maintable.addCell(imageTable);

				 	PdfPTable claimTable1 = new PdfPTable(1);
				    claimTable1.getDefaultCell().setBorder(Rectangle.NO_BORDER);

					PdfPCell claimCell1 = new PdfPCell(new Paragraph(
							"ALL TRAVELERS WHO HAVE BOOKED THEIR TICKET THROUGH DEFENCE TRAVEL SYSTEM MUST SUBMIT THEIR FINAL SETTLEMENT CLAIM TO THEIR RESPECTIVE PAO OFFICES WITHIN STIPULATED TIME w.r.t.AIR TRAVEL EVEN IF THE TICKET/JOURNEY HAS BEEN CANCELLED.",
							commonBoldFont12));
					claimCell1.setBorder(Rectangle.NO_BORDER);
					claimCell1.setBackgroundColor(flightColor);
					claimCell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
					claimCell1.setPadding(3.0f);
					claimTable1.addCell(claimCell1);
				    maintable.addCell(claimTable1);

				 	PdfPTable headerTable = new PdfPTable(1);

			        Font fontbold = FontFactory.getFont("", 14, Font.BOLD);
			        headerTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
					PdfPCell cell = null;
					if (airTicketDetailsXml.getShowLTCLabel().equals("YES")) {
						cell = new PdfPCell(new Paragraph("LTC Air Ticket", fontbold));
					} else {
						cell = new PdfPCell(new Paragraph("Air Ticket", fontbold));
			        }

			        cell.setBorder(Rectangle.NO_BORDER);
			        cell.setBackgroundColor(flightColor);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPadding(8.0f);
				    headerTable.addCell(cell);

				    maintable.addCell(headerTable);

				    PdfPTable journeyDetailTable = new PdfPTable(6);
				    journeyDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

					float[] journeyColumnWidths = new float[] { 19f, 20f, 19f, 22f, 16f, 12f };

					PdfPCell journeycell0 = new PdfPCell(new Paragraph("Booking Id: ", commonBoldFont8));
					journeycell0.setBorder(Rectangle.NO_BORDER);
					journeycell0.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell0.setBackgroundColor(flightColor);
					journeycell0.setPadding(8.0f);

					journeyDetailTable.addCell(journeycell0);

					PdfPCell journeycell00 = new PdfPCell(
							new Paragraph(airTicketDetailsXml.getBookingId(), commonNormalFont7));
					journeycell00.setBorder(Rectangle.NO_BORDER);
					journeycell00.setBackgroundColor(flightColor);
					journeycell00.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell00.setPadding(8.0f);

					journeyDetailTable.addCell(journeycell00);

					PdfPCell journeycell1 = new PdfPCell(new Paragraph("Booking Date: ", commonBoldFont8));
					journeycell1.setBorder(Rectangle.NO_BORDER);
					journeycell1.setBackgroundColor(flightColor);
					journeycell1.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell1.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell1);

					PdfPCell journeycell10 = new PdfPCell(
							new Paragraph(airTicketDetailsXml.getBookingDate(), commonNormalFont7));
					journeycell10.setBorder(Rectangle.NO_BORDER);
					journeycell10.setBackgroundColor(flightColor);
					journeycell10.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell10.setPadding(8.0f);

					journeyDetailTable.addCell(journeycell10);

					PdfPCell journeycell2 = new PdfPCell(new Paragraph("Status: ", commonBoldFont8));

					journeycell2.setBorder(Rectangle.NO_BORDER);
					journeycell2.setBackgroundColor(flightColor);
					journeycell2.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell2.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell2);

					PdfPCell journeycell20 = null;

					if (airTicketDetailsXml.getBookingStatus().equalsIgnoreCase("Booked")) {
						journeycell20 = new PdfPCell(new Paragraph("Confirmed", commonNormalFont7));
					} else {
						journeycell20 = new PdfPCell(
								new Paragraph(airTicketDetailsXml.getBookingStatus(), commonNormalFont7));
				    }

					journeycell20.setBorder(Rectangle.NO_BORDER);
					journeycell20.setBackgroundColor(flightColor);
					journeycell20.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell20.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell20);

					PdfPCell journeycell021 = new PdfPCell(new Paragraph("Order No.: ", commonBoldFont8));
					journeycell021.setBorder(Rectangle.NO_BORDER);
					journeycell021.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell021.setBackgroundColor(flightColor);
					journeycell021.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell021);

					PdfPCell journeycell022 = new PdfPCell(
							new Paragraph(airTicketDetailsXml.getOperatorTxnId(), commonNormalFont7));
					journeycell022.setBorder(Rectangle.NO_BORDER);
					journeycell022.setBackgroundColor(flightColor);
					journeycell022.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell022.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell022);

					PdfPCell journeycell023 = new PdfPCell(new Paragraph("Agent Name: ", commonBoldFont8));
					journeycell023.setBorder(Rectangle.NO_BORDER);
					journeycell023.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell023.setBackgroundColor(flightColor);
					journeycell023.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell023);

					PdfPCell journeycell024 = new PdfPCell(new Paragraph("Ashok Travels & Tours", commonNormalFont7));
					journeycell024.setBorder(Rectangle.NO_BORDER);
					journeycell024.setBackgroundColor(flightColor);
					journeycell024.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell024.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell024);

					PdfPCell journeycell025 = new PdfPCell(new Paragraph("IATA Code:", commonBoldFont8));
					journeycell025.setBorder(Rectangle.NO_BORDER);
					journeycell025.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell025.setBackgroundColor(flightColor);
					journeycell025.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell025);

					PdfPCell journeycell026 = new PdfPCell(new Paragraph(iataCode, commonNormalFont7));
					journeycell026.setBorder(Rectangle.NO_BORDER);
					journeycell026.setBackgroundColor(flightColor);
					journeycell026.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell026.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell026);

					journeycell0 = new PdfPCell(new Paragraph("Trip Type: ", commonBoldFont8));
					journeycell0.setBorder(Rectangle.NO_BORDER);
					journeycell0.setBackgroundColor(flightColor);
					journeycell0.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell0.setPadding(8.0f);

					journeyDetailTable.addCell(journeycell0);

					if (airTicketDetailsXml.getBookedSegment() == 0) {

						if (airTicketDetailsXml.getJournyType().equalsIgnoreCase("Return")) {
							journeycell00 = new PdfPCell(new Paragraph("Special Round Trip", commonNormalFont7));

						} else {
							journeycell00 = new PdfPCell(new Paragraph("Oneway Trip", commonNormalFont7));
				    }

					} else {

						if (airTicketDetailsXml.getBookedSegment() == 1) {
							journeycell00 = new PdfPCell(new Paragraph("Onward Trip", commonNormalFont7));
						} else {
							journeycell00 = new PdfPCell(new Paragraph("Return Trip", commonNormalFont7));
					    }
				    	}

					journeycell00.setColspan(1);
					journeycell00.setBackgroundColor(flightColor);
					journeycell00.setBorder(Rectangle.NO_BORDER);
					journeycell00.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell00.setPadding(8.0f);

					journeyDetailTable.addCell(journeycell00);

					journeycell1 = new PdfPCell(new Paragraph("Booking Sector: ", commonBoldFont8));
					journeycell1.setBorder(Rectangle.NO_BORDER);
					journeycell1.setBackgroundColor(flightColor);
					journeycell1.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell1.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell1);

					if (airTicketDetailsXml.getJournyType().equalsIgnoreCase("Return")) {
						journeycell10 = new PdfPCell(new Paragraph(airTicketDetailsXml.getSourceSectorCity() + " ("
								+ airTicketDetailsXml.getSourceSectorCode() + ") - "
								+ airTicketDetailsXml.getDestinationSectorCity() + " ("
								+ airTicketDetailsXml.getDestinationSectorCode() + ") - "
								+ airTicketDetailsXml.getSourceSectorCity() + " ("
								+ airTicketDetailsXml.getSourceSectorCode() + ")", commonNormalFont7));
					} else {
						journeycell10 = new PdfPCell(new Paragraph(airTicketDetailsXml.getSourceSectorCity() + " ("
								+ airTicketDetailsXml.getSourceSectorCode() + ") - "
								+ airTicketDetailsXml.getDestinationSectorCity() + " ("
								+ airTicketDetailsXml.getDestinationSectorCode() + ")", commonNormalFont7));
				    }

					journeycell10.setColspan(3);
					journeycell10.setBackgroundColor(flightColor);
					journeycell10.setBorder(Rectangle.NO_BORDER);
					journeycell10.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell10.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell10);


					PdfPCell journeycell30 = new PdfPCell(new Paragraph("Travel Rule:", commonBoldFont8));
					journeycell30.setColspan(1);
					journeycell30.setBorder(Rectangle.NO_BORDER);
					journeycell30.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell30.setBackgroundColor(flightColor);
					journeycell30.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell30);

					PdfPCell journeycell31 = new PdfPCell(new Paragraph(airTicketDetailsXml.getTrRule(), commonNormalFont7));
					journeycell31.setColspan(5);
					journeycell31.setBorder(Rectangle.NO_BORDER);
					journeycell31.setBackgroundColor(flightColor);
					journeycell31.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell31.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell31);

					    journeyDetailTable.setWidths(journeyColumnWidths);

					    maintable.addCell(journeyDetailTable);

					    flightDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

					float[] flightColumnWidths = new float[] { 2f, 13f, 25f, 30f, 25f };
					    flightDetailTable.setWidths(flightColumnWidths);

					String airLineGST = airTicketDetailsXml.getAirGSTNo();

					    String airLinePNR = "";
					    String gdsPNR = "";
					String isRefundable = "";

					if (airTicketDetailsXml.getJournyType().equalsIgnoreCase("Return")) {
					    	java.util.List<FlightInfo> onwardFlightList = airTicketDetailsXml.getFlightInfo();

					    	java.util.List<FlightInfo> returnFlightList = airTicketDetailsXml.getFlightInfo();

						
						{
							PdfPCell flightcell0 = new PdfPCell(
									new Paragraph("Flight Information (Onward)", commonBoldFont10));
							flightcell0.setBorder(Rectangle.NO_BORDER);
							flightcell0.setColspan(3);
							flightcell0.setBackgroundColor(flightColor);
							flightcell0.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell0.setPadding(8.0f);
							flightDetailTable.addCell(flightcell0);

							PdfPCell flightcell01 = new PdfPCell(
									new Paragraph("Stops : " + (onwardFlightList.size() - 1), commonBoldFont10));
							flightcell01.setBorder(Rectangle.NO_BORDER);
							flightcell01.setBackgroundColor(flightColor);
							flightcell01.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell01.setPadding(8.0f);
							flightDetailTable.addCell(flightcell01);

							PdfPCell flightcell02 = new PdfPCell(new Paragraph("", commonBoldFont6));
							flightcell02.setBorder(Rectangle.NO_BORDER);
							flightcell02.setBackgroundColor(flightColor);
							flightcell02.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell02.setPadding(8.0f);
							flightDetailTable.addCell(flightcell02);

							PdfPCell flightcell = new PdfPCell(new Paragraph("", commonBoldFont6));
						    flightcell.setBorder(Rectangle.NO_BORDER);
						    flightcell.setBackgroundColor(flightColor);
							flightcell.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell.setPadding(3.0f);
						    flightDetailTable.addCell(flightcell);

							PdfPCell flightcell1 = new PdfPCell(new Paragraph("Carrier", commonBoldFont7));
							flightcell1.setBorder(Rectangle.NO_BORDER);
							flightcell1.setBackgroundColor(flightColor);
							flightcell1.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell1.setPadding(3.0f);
							flightDetailTable.addCell(flightcell1);

							PdfPCell flightcell2 = new PdfPCell(new Paragraph("Departure", commonBoldFont7));
							flightcell2.setBorder(Rectangle.NO_BORDER);
							flightcell2.setBackgroundColor(flightColor);
							flightcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell2.setPadding(3.0f);
							flightDetailTable.addCell(flightcell2);

							PdfPCell flightcell3 = new PdfPCell(new Paragraph("Arrival", commonBoldFont7));
							flightcell3.setBorder(Rectangle.NO_BORDER);
							flightcell3.setBackgroundColor(flightColor);
							flightcell3.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell3.setPadding(3.0f);
							flightDetailTable.addCell(flightcell3);

							PdfPCell flightcell4 = new PdfPCell(new Paragraph("Other Information", commonBoldFont7));
							flightcell4.setBorder(Rectangle.NO_BORDER);
							flightcell4.setBackgroundColor(flightColor);
							flightcell4.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell4.setPadding(3.0f);
							flightDetailTable.addCell(flightcell4);

							for (int v = 0; v < onwardFlightList.size(); v++) {

								if (v == 0) {
									airLinePNR = onwardFlightList.get(v).getAirlinePnr();
									gdsPNR = onwardFlightList.get(v).getGdsPnr();
									isRefundable = onwardFlightList.get(v).getRefundable();

					    		}

								PdfPCell flightcell00 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell00.setBorder(Rectangle.NO_BORDER);
								flightcell00.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell00.setPadding(3.0f);
								flightcell00.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell00);

								PdfPCell flightcell10 = new PdfPCell(
										new Paragraph(onwardFlightList.get(v).getOperatingAirline(), commonBoldFont8));
								flightcell10.setBorder(Rectangle.NO_BORDER);
								flightcell10.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell10.setPadding(3.0f);
								flightcell10.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell10);

								PdfPCell flightcell11 = new PdfPCell(new Paragraph(onwardFlightList.get(v).getSource()
										+ " (" + onwardFlightList.get(v).getSourceCode() + ")", commonBoldFont8));
								flightcell11.setBorder(Rectangle.NO_BORDER);
								flightcell11.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell11.setPadding(3.0f);
								flightcell11.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell11);

								PdfPCell flightcell21 = new PdfPCell(
										new Paragraph(
												onwardFlightList.get(v).getDestination() + " ("
														+ onwardFlightList.get(v).getDestinationCode() + ")",
												commonBoldFont8));
								flightcell21.setBorder(Rectangle.NO_BORDER);
								flightcell21.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell21.setPadding(3.0f);
								flightcell21.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell21);

								PdfPCell flightcell31 = new PdfPCell(new Paragraph(
										"Check-in: " + onwardFlightList.get(v).getBaggageAllowance() + "  Cabin: 7Kg ",
										commonNormalFont7));
								flightcell31.setBorder(Rectangle.NO_BORDER);
								flightcell31.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell31.setPadding(3.0f);
								flightcell31.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell31);

								PdfPCell flightcell011 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell011.setBorder(Rectangle.NO_BORDER);
								flightcell011.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell011.setPadding(3.0f);
								flightcell011.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell011);

								PdfPCell flightcell12 = new PdfPCell(new Paragraph(onwardFlightList.get(v).getAirline()
										+ " - " + onwardFlightList.get(v).getFlightNo(), commonNormalFont7));
								flightcell12.setBorder(Rectangle.NO_BORDER);
								flightcell12.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell12.setPadding(3.0f);
								flightcell12.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell12);

								PdfPCell flightcell22 = new PdfPCell(new Paragraph(
										onwardFlightList.get(v).getSegmentOriginAirport(), commonNormalFont7));

								flightcell22.setBorder(Rectangle.NO_BORDER);
								flightcell22.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell22.setPadding(3.0f);
								flightcell22.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell22);

								PdfPCell flightcell32 = new PdfPCell(new Paragraph(
										onwardFlightList.get(v).getSegmentDestinationAirport(), commonNormalFont7));
								flightcell32.setBorder(Rectangle.NO_BORDER);
								flightcell32.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell32.setPadding(3.0f);
								flightcell32.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell32);

								PdfPCell flightcell42 = new PdfPCell(new Paragraph(
										"Cabin Class: " + onwardFlightList.get(v).getCabinClass(), commonNormalFont7));
								flightcell42.setBorder(Rectangle.NO_BORDER);
								flightcell42.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell42.setPadding(3.0f);
								flightcell42.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell42);

								PdfPCell flightcell022 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell022.setBorder(Rectangle.NO_BORDER);
								flightcell022.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell022.setPadding(3.0f);
								flightcell022.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell022);

								PdfPCell flightcell13 = new PdfPCell(new Paragraph("", commonNormalFont7));
								flightcell13.setBorder(Rectangle.NO_BORDER);
								flightcell13.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell13.setPadding(3.0f);
								flightcell13.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell13);

								PdfPCell flightcell23 = new PdfPCell(
										new Paragraph(
												onwardFlightList.get(v).getScheduledDepartureDate() + ""
														+ onwardFlightList.get(v).getScheduledDeparture(),
												commonNormalFont7));
								flightcell23.setBorder(Rectangle.NO_BORDER);
								flightcell23.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell23.setPadding(3.0f);
								flightcell23.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell23);

								PdfPCell flightcell33 = new PdfPCell(
										new Paragraph(
												onwardFlightList.get(v).getScheduledArrivalDate() + " "
														+ onwardFlightList.get(v).getScheduledArrival(),
												commonNormalFont7));
								flightcell33.setBorder(Rectangle.NO_BORDER);
								flightcell33.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell33.setPadding(3.0f);
								flightcell33.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell33);

								PdfPCell flightcell43 = new PdfPCell(
										new Paragraph("Booking Class: " + onwardFlightList.get(v).getBookingClass(),
												commonNormalFont7));
								flightcell43.setBorder(Rectangle.NO_BORDER);
								flightcell43.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell43.setPadding(3.0f);
								flightcell43.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell43);

								PdfPCell flightcell03 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell03.setBorder(Rectangle.NO_BORDER);
								flightcell03.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell03.setPadding(3.0f);
								flightcell03.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell03);

								PdfPCell flightcell14 = new PdfPCell(new Paragraph("", commonNormalFont7));
								flightcell14.setBorder(Rectangle.NO_BORDER);
								flightcell14.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell14.setPadding(3.0f);
								flightcell14.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell14);

								PdfPCell flightcell24 = new PdfPCell(new Paragraph(
										"Terminal: " + onwardFlightList.get(v).getDepartTerminal(), commonNormalFont7));
								flightcell24.setBorder(Rectangle.NO_BORDER);
								flightcell24.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell24.setPadding(3.0f);
								flightcell24.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell24);

								PdfPCell flightcell34 = new PdfPCell(new Paragraph(
										"Terminal: " + onwardFlightList.get(v).getArrivTerminal(), commonNormalFont7));
								flightcell34.setBorder(Rectangle.NO_BORDER);
								flightcell34.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell34.setPadding(3.0f);
								flightcell34.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell34);

								PdfPCell flightcell44 = new PdfPCell(new Paragraph(
										"Duration: " + onwardFlightList.get(v).getSectorDuration(), commonNormalFont7));
								flightcell44.setBorder(Rectangle.NO_BORDER);
								flightcell44.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell44.setPadding(3.0f);
								flightcell44.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell44);

							} // End of onwardFlightList loop

						} // End of onward block

						{ // Starts Return Block

							PdfPCell flightcell0 = new PdfPCell(
									new Paragraph("Flight Information (Return)", commonBoldFont10));
							flightcell0.setBorder(Rectangle.NO_BORDER);
							flightcell0.setColspan(3);
							flightcell0.setBackgroundColor(flightColor);
							flightcell0.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell0.setPadding(8.0f);
							flightDetailTable.addCell(flightcell0);

							// Start: Stops display
							PdfPCell flightcell01 = new PdfPCell(
									new Paragraph("Stops : " + (returnFlightList.size() - 1), commonBoldFont10));
							flightcell01.setBorder(Rectangle.NO_BORDER);
							flightcell01.setBackgroundColor(flightColor);
							flightcell01.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell01.setPadding(8.0f);
							flightDetailTable.addCell(flightcell01);

							PdfPCell flightcell02 = new PdfPCell(new Paragraph("", commonBoldFont6));
							flightcell02.setBorder(Rectangle.NO_BORDER);
							flightcell02.setBackgroundColor(flightColor);
							flightcell02.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell02.setPadding(8.0f);
							flightDetailTable.addCell(flightcell02);
							// End: Stops display

							PdfPCell flightcell = new PdfPCell(new Paragraph("", commonBoldFont6));
								    flightcell.setBorder(Rectangle.NO_BORDER);
								    flightcell.setBackgroundColor(flightColor);
							flightcell.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell.setPadding(3.0f);
								    flightDetailTable.addCell(flightcell);

							PdfPCell flightcell1 = new PdfPCell(new Paragraph("Carrier", commonBoldFont7));
							flightcell1.setBorder(Rectangle.NO_BORDER);
							flightcell1.setBackgroundColor(flightColor);
							flightcell1.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell1.setPadding(3.0f);
							flightDetailTable.addCell(flightcell1);

							PdfPCell flightcell2 = new PdfPCell(new Paragraph("Departure", commonBoldFont7));
							flightcell2.setBorder(Rectangle.NO_BORDER);
							flightcell2.setBackgroundColor(flightColor);
							flightcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell2.setPadding(3.0f);
							flightDetailTable.addCell(flightcell2);

							PdfPCell flightcell3 = new PdfPCell(new Paragraph("Arrival", commonBoldFont7));
							flightcell3.setBorder(Rectangle.NO_BORDER);
							flightcell3.setBackgroundColor(flightColor);
							flightcell3.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell3.setPadding(3.0f);
							flightDetailTable.addCell(flightcell3);

							PdfPCell flightcell4 = new PdfPCell(new Paragraph("Other Information", commonBoldFont7));
							flightcell4.setBorder(Rectangle.NO_BORDER);
							flightcell4.setBackgroundColor(flightColor);
							flightcell4.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell4.setPadding(3.0f);
							flightDetailTable.addCell(flightcell4);

							for (int v = 0; v < returnFlightList.size(); v++) {

								PdfPCell flightcell00 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell00.setBorder(Rectangle.NO_BORDER);
								flightcell00.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell00.setPadding(3.0f);
								flightcell00.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell00);

								PdfPCell flightcell10 = new PdfPCell(
										new Paragraph(returnFlightList.get(v).getOperatingAirline(), commonBoldFont8));
								flightcell10.setBorder(Rectangle.NO_BORDER);
								flightcell10.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell10.setPadding(3.0f);
								flightcell10.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell10);

								PdfPCell flightcell11 = new PdfPCell(new Paragraph(returnFlightList.get(v).getSource()
										+ " (" + returnFlightList.get(v).getSourceCode() + ")", commonBoldFont8));
								flightcell11.setBorder(Rectangle.NO_BORDER);
								flightcell11.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell11.setPadding(3.0f);
								flightcell11.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell11);

								PdfPCell flightcell21 = new PdfPCell(
										new Paragraph(
												returnFlightList.get(v).getDestination() + " ("
														+ returnFlightList.get(v).getDestinationCode() + ")",
												commonBoldFont8));
								flightcell21.setBorder(Rectangle.NO_BORDER);
								flightcell21.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell21.setPadding(3.0f);
								flightcell21.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell21);

								PdfPCell flightcell31 = new PdfPCell(new Paragraph(
										"Check-in: " + returnFlightList.get(v).getBaggageAllowance() + "  Cabin: 7Kg ",
										commonNormalFont7));
								flightcell31.setBorder(Rectangle.NO_BORDER);
								flightcell31.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell31.setPadding(3.0f);
								flightcell31.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell31);

								PdfPCell flightcell011 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell011.setBorder(Rectangle.NO_BORDER);
								flightcell011.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell011.setPadding(3.0f);
								flightcell011.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell011);

								PdfPCell flightcell12 = new PdfPCell(new Paragraph(returnFlightList.get(v).getAirline()
										+ " - " + returnFlightList.get(v).getFlightNo(), commonNormalFont7));
								flightcell12.setBorder(Rectangle.NO_BORDER);
								flightcell12.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell12.setPadding(3.0f);
								flightcell12.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell12);

								PdfPCell flightcell22 = new PdfPCell(new Paragraph(
										returnFlightList.get(v).getSegmentOriginAirport(), commonNormalFont7));
								flightcell22.setBorder(Rectangle.NO_BORDER);
								flightcell22.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell22.setPadding(3.0f);
								flightcell22.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell22);

								PdfPCell flightcell32 = new PdfPCell(new Paragraph(
										returnFlightList.get(v).getSegmentDestinationAirport(), commonNormalFont7));
								flightcell32.setBorder(Rectangle.NO_BORDER);
								flightcell32.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell32.setPadding(3.0f);
								flightcell32.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell32);

								PdfPCell flightcell42 = new PdfPCell(new Paragraph(
										"Cabin Class: " + returnFlightList.get(v).getCabinClass(), commonNormalFont7));
								flightcell42.setBorder(Rectangle.NO_BORDER);
								flightcell42.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell42.setPadding(3.0f);
								flightcell42.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell42);

								PdfPCell flightcell023 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell023.setBorder(Rectangle.NO_BORDER);
								flightcell023.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell023.setPadding(3.0f);
								flightcell023.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell023);

								PdfPCell flightcell13 = new PdfPCell(new Paragraph("", commonNormalFont7));
								flightcell13.setBorder(Rectangle.NO_BORDER);
								flightcell13.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell13.setPadding(3.0f);
								flightcell13.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell13);

								PdfPCell flightcell23 = new PdfPCell(
										new Paragraph(
												returnFlightList.get(v).getScheduledDepartureDate() + " "
														+ returnFlightList.get(v).getScheduledDeparture(),
												commonNormalFont7));
								flightcell23.setBorder(Rectangle.NO_BORDER);
								flightcell23.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell23.setPadding(3.0f);
								flightcell23.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell23);

								PdfPCell flightcell33 = new PdfPCell(
										new Paragraph(
												returnFlightList.get(v).getScheduledArrivalDate() + " "
														+ returnFlightList.get(v).getScheduledArrival(),
												commonNormalFont7));
								flightcell33.setBorder(Rectangle.NO_BORDER);
								flightcell33.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell33.setPadding(3.0f);
								flightcell33.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell33);

								PdfPCell flightcell43 = new PdfPCell(
										new Paragraph("Booking Class: " + returnFlightList.get(v).getBookingClass(),
												commonNormalFont7));
								flightcell43.setBorder(Rectangle.NO_BORDER);
								flightcell43.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell43.setPadding(3.0f);
								flightcell43.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell43);

								PdfPCell flightcell03 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell03.setBorder(Rectangle.NO_BORDER);
								flightcell03.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell03.setPadding(3.0f);
								flightcell03.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell03);

								PdfPCell flightcell14 = new PdfPCell(new Paragraph("", commonNormalFont7));
								flightcell14.setBorder(Rectangle.NO_BORDER);
								flightcell14.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell14.setPadding(3.0f);
								flightcell14.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell14);

								PdfPCell flightcell24 = new PdfPCell(new Paragraph(
										"Terminal: " + returnFlightList.get(v).getDepartTerminal(), commonNormalFont7));
								flightcell24.setBorder(Rectangle.NO_BORDER);
								flightcell24.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell24.setPadding(3.0f);
								flightcell24.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell24);

								PdfPCell flightcell34 = new PdfPCell(new Paragraph(
										"Terminal: " + returnFlightList.get(v).getArrivTerminal(), commonNormalFont7));
								flightcell34.setBorder(Rectangle.NO_BORDER);
								flightcell34.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell34.setPadding(3.0f);
								flightcell34.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell34);

								PdfPCell flightcell44 = new PdfPCell(new Paragraph(
										"Duration: " + returnFlightList.get(v).getSectorDuration(), commonNormalFont7));
								flightcell44.setBorder(Rectangle.NO_BORDER);
								flightcell44.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell44.setPadding(3.0f);
								flightcell44.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell44);
							} // End of returnFlightList loop

						} // End of Return block

					} // End of Return

	        else {
	        	java.util.List<FlightInfo> onwardFlightList = airTicketDetailsXml.getFlightInfo();
						PdfPCell flightcell0 = new PdfPCell(new Paragraph("Flight Information", commonBoldFont10));
						flightcell0.setBorder(Rectangle.NO_BORDER);
						flightcell0.setColspan(3);
						flightcell0.setBackgroundColor(flightColor);
						flightcell0.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell0.setPadding(8.0f);
						flightDetailTable.addCell(flightcell0);

						// Start: Stops display
						PdfPCell flightcell01 = new PdfPCell(
								new Paragraph("Stops : " + (onwardFlightList.size() - 1), commonBoldFont10));
						flightcell01.setBorder(Rectangle.NO_BORDER);
						flightcell01.setBackgroundColor(flightColor);
						flightcell01.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell01.setPadding(8.0f);
						flightDetailTable.addCell(flightcell01);

						PdfPCell flightcell02 = new PdfPCell(new Paragraph("", commonBoldFont6));
						flightcell02.setBorder(Rectangle.NO_BORDER);
						flightcell02.setBackgroundColor(flightColor);
						flightcell02.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell02.setPadding(8.0f);
						flightDetailTable.addCell(flightcell02);
						// End: Stops display

						PdfPCell flightcell = new PdfPCell(new Paragraph("", commonBoldFont6));
			    flightcell.setBorder(Rectangle.NO_BORDER);
			    flightcell.setBackgroundColor(flightColor);
						flightcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell.setPadding(3.0f);
			    flightDetailTable.addCell(flightcell);

						PdfPCell flightcell1 = new PdfPCell(new Paragraph("Carrier", commonBoldFont7));
						flightcell1.setBorder(Rectangle.NO_BORDER);
						flightcell1.setBackgroundColor(flightColor);
						flightcell1.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell1.setPadding(3.0f);
						flightDetailTable.addCell(flightcell1);

						PdfPCell flightcell2 = new PdfPCell(new Paragraph("Departure", commonBoldFont7));
						flightcell2.setBorder(Rectangle.NO_BORDER);
						flightcell2.setBackgroundColor(flightColor);
						flightcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell2.setPadding(3.0f);
						flightDetailTable.addCell(flightcell2);

						PdfPCell flightcell3 = new PdfPCell(new Paragraph("Arrival", commonBoldFont7));
						flightcell3.setBorder(Rectangle.NO_BORDER);
						flightcell3.setBackgroundColor(flightColor);
						flightcell3.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell3.setPadding(3.0f);
						flightDetailTable.addCell(flightcell3);

						PdfPCell flightcell4 = new PdfPCell(new Paragraph("Other Information", commonBoldFont7));
						flightcell4.setBorder(Rectangle.NO_BORDER);
						flightcell4.setBackgroundColor(flightColor);
						flightcell4.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell4.setPadding(3.0f);
						flightDetailTable.addCell(flightcell4);

						for (int v = 0; v < onwardFlightList.size(); v++) {

							if (v == 0) {
		    			airLinePNR = onwardFlightList.get(v).getAirlinePnr();
		    			gdsPNR = onwardFlightList.get(v).getGdsPnr();
								isRefundable = onwardFlightList.get(v).getRefundable();
		    		}
							PdfPCell flightcell00 = new PdfPCell(new Paragraph("", commonBoldFont6));
							flightcell00.setBorder(Rectangle.NO_BORDER);
							flightcell00.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell00.setPadding(3.0f);
							flightcell00.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell00);

							PdfPCell flightcell10 = new PdfPCell(
									new Paragraph(onwardFlightList.get(v).getOperatingAirline(), commonBoldFont8));
							flightcell10.setBorder(Rectangle.NO_BORDER);
							flightcell10.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell10.setPadding(3.0f);
							flightcell10.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell10);

							PdfPCell flightcell11 = new PdfPCell(new Paragraph(onwardFlightList.get(v).getSource()
									+ " (" + onwardFlightList.get(v).getSourceCode() + ")", commonBoldFont8));
							flightcell11.setBorder(Rectangle.NO_BORDER);
							flightcell11.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell11.setPadding(3.0f);
							flightcell11.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell11);

							PdfPCell flightcell21 = new PdfPCell(new Paragraph(onwardFlightList.get(v).getDestination()
									+ " (" + onwardFlightList.get(v).getDestinationCode() + ")", commonBoldFont8));
							flightcell21.setBorder(Rectangle.NO_BORDER);
							flightcell21.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell21.setPadding(3.0f);
							flightcell21.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell21);

							PdfPCell flightcell31 = new PdfPCell(new Paragraph(
									"Check-in: " + onwardFlightList.get(v).getBaggageAllowance() + "  Cabin: 7Kg ",
									commonNormalFont7));
							flightcell31.setBorder(Rectangle.NO_BORDER);
							flightcell31.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell31.setPadding(3.0f);
							flightcell31.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell31);

							PdfPCell flightcell015 = new PdfPCell(new Paragraph("", commonBoldFont6));
							flightcell015.setBorder(Rectangle.NO_BORDER);
							flightcell015.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell015.setPadding(3.0f);
							flightcell015.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell015);

							PdfPCell flightcell12 = new PdfPCell(new Paragraph(onwardFlightList.get(v).getAirline()
									+ " - " + onwardFlightList.get(v).getFlightNo(), commonNormalFont7));
							flightcell12.setBorder(Rectangle.NO_BORDER);
							flightcell12.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell12.setPadding(3.0f);
							flightcell12.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell12);

							PdfPCell flightcell22 = new PdfPCell(new Paragraph(
									onwardFlightList.get(v).getSegmentOriginAirport(), commonNormalFont7));
							flightcell22.setBorder(Rectangle.NO_BORDER);
							flightcell22.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell22.setPadding(3.0f);
							flightcell22.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell22);

							PdfPCell flightcell32 = new PdfPCell(new Paragraph(
									onwardFlightList.get(v).getSegmentDestinationAirport(), commonNormalFont7));
							flightcell32.setBorder(Rectangle.NO_BORDER);
							flightcell32.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell32.setPadding(3.0f);
							flightcell32.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell32);

							PdfPCell flightcell42 = new PdfPCell(new Paragraph(
									"Cabin Class: " + onwardFlightList.get(v).getCabinClass(), commonNormalFont7));
							flightcell42.setBorder(Rectangle.NO_BORDER);
							flightcell42.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell42.setPadding(3.0f);
							flightcell42.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell42);

							PdfPCell flightcell023 = new PdfPCell(new Paragraph("", commonBoldFont6));
							flightcell023.setBorder(Rectangle.NO_BORDER);
							flightcell023.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell023.setPadding(3.0f);
							flightcell023.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell023);

							PdfPCell flightcell13 = new PdfPCell(new Paragraph("", commonNormalFont7));
							flightcell13.setBorder(Rectangle.NO_BORDER);
							flightcell13.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell13.setPadding(3.0f);
							flightcell13.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell13);

							PdfPCell flightcell23 = new PdfPCell(
									new Paragraph(
											onwardFlightList.get(v).getScheduledDepartureDate() + " "
													+ onwardFlightList.get(v).getScheduledDeparture(),
											commonNormalFont7));
							flightcell23.setBorder(Rectangle.NO_BORDER);
							flightcell23.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell23.setPadding(3.0f);
							flightcell23.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell23);

							PdfPCell flightcell33 = new PdfPCell(
									new Paragraph(
											onwardFlightList.get(v).getScheduledArrivalDate() + " "
													+ onwardFlightList.get(v).getScheduledArrival(),
											commonNormalFont7));
							flightcell33.setBorder(Rectangle.NO_BORDER);
							flightcell33.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell33.setPadding(3.0f);
							flightcell33.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell33);

							PdfPCell flightcell43 = new PdfPCell(new Paragraph(
									"Booking Class: " + onwardFlightList.get(v).getBookingClass(), commonNormalFont7));
							flightcell43.setBorder(Rectangle.NO_BORDER);
							flightcell43.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell43.setPadding(3.0f);
							flightcell43.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell43);

							PdfPCell flightcell03 = new PdfPCell(new Paragraph("", commonBoldFont6));
							flightcell03.setBorder(Rectangle.NO_BORDER);
							flightcell03.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell03.setPadding(3.0f);
							flightcell03.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell03);

							PdfPCell flightcell14 = new PdfPCell(new Paragraph("", commonNormalFont7));
							flightcell14.setBorder(Rectangle.NO_BORDER);
							flightcell14.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell14.setPadding(3.0f);
							flightcell14.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell14);

							PdfPCell flightcell24 = new PdfPCell(new Paragraph(
									"Terminal: " + onwardFlightList.get(v).getDepartTerminal(), commonNormalFont7));
							flightcell24.setBorder(Rectangle.NO_BORDER);
							flightcell24.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell24.setPadding(3.0f);
							flightcell24.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell24);

							PdfPCell flightcell34 = new PdfPCell(new Paragraph(
									"Terminal: " + onwardFlightList.get(v).getArrivTerminal(), commonNormalFont7));
							flightcell34.setBorder(Rectangle.NO_BORDER);
							flightcell34.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell34.setPadding(3.0f);
							flightcell34.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell34);

							PdfPCell flightcell44 = new PdfPCell(new Paragraph(
									"Duration: " + onwardFlightList.get(v).getSectorDuration(), commonNormalFont7));
							flightcell44.setBorder(Rectangle.NO_BORDER);
							flightcell44.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell44.setPadding(3.0f);
							flightcell44.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell44);
						} // End of onwardFlightList loop
		    }

	        maintable.addCell(flightDetailTable);

		    PdfPTable passengerContactDetailTable = new PdfPTable(4);

		    passengerContactDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

					float[] passengerContactColumnWidths = new float[] { 25f, 14f, 25f, 13f };

		    passengerContactDetailTable.setWidths(passengerContactColumnWidths);

		java.util.List<PassengerInfo> passengerContactElementList = airTicketDetailsXml.getPassengerInfo();

					PdfPCell paxCell001 = new PdfPCell(new Paragraph("Passenger Contact Details", commonBoldFont10));
					paxCell001.setBorder(Rectangle.NO_BORDER);
					paxCell001.setColspan(4);
					paxCell001.setBackgroundColor(flightColor);
					paxCell001.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell001.setPadding(8.0f);
					passengerContactDetailTable.addCell(paxCell001);

					PdfPCell paxCell01 = new PdfPCell(new Paragraph("Name", commonBoldFont7));
					paxCell01.setBorder(Rectangle.NO_BORDER);
					paxCell01.setBackgroundColor(flightColor);
					paxCell01.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell01.setPadding(5.0f);
					passengerContactDetailTable.addCell(paxCell01);

					PdfPCell paxCell02 = new PdfPCell(new Paragraph("Mobile Number", commonBoldFont7));
					paxCell02.setBorder(Rectangle.NO_BORDER);
					paxCell02.setBackgroundColor(flightColor);
					paxCell02.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell02.setPadding(5.0f);
					passengerContactDetailTable.addCell(paxCell02);

					PdfPCell paxCell03 = new PdfPCell(new Paragraph("Email", commonBoldFont7));
					paxCell03.setBorder(Rectangle.NO_BORDER);
					paxCell03.setBackgroundColor(flightColor);
					paxCell03.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell03.setPadding(5.0f);
					passengerContactDetailTable.addCell(paxCell03);

					PdfPCell paxCell04 = new PdfPCell(new Paragraph("Endorsements", commonBoldFont7));
					paxCell04.setBorder(Rectangle.NO_BORDER);
					paxCell04.setBackgroundColor(flightColor);
					paxCell04.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell04.setPadding(5.0f);
					passengerContactDetailTable.addCell(paxCell04);

					for (int v = 0; v < passengerContactElementList.size(); v++) {

						if (passengerContactElementList.get(v).getLeadPassanger() == 0) {
							PdfPCell paxCell11 = new PdfPCell(
									new Paragraph(
											passengerContactElementList.get(v).getTitle() + ". "
													+ passengerContactElementList.get(v).getFirstName() + " "
													+ passengerContactElementList.get(v).getMiddleName() + " "
													+ passengerContactElementList.get(v).getLastName(),
											commonBoldFont8));
							paxCell11.setBorder(Rectangle.NO_BORDER);
							paxCell11.setHorizontalAlignment(Element.ALIGN_LEFT);
							paxCell11.setPadding(5.0f);
							paxCell11.setBackgroundColor(flightColor);
							passengerContactDetailTable.addCell(paxCell11);

							PdfPCell paxCell12 = new PdfPCell(
									new Paragraph(passengerContactElementList.get(v).getMobileNo(), commonBoldFont8));
							paxCell12.setBorder(Rectangle.NO_BORDER);
							paxCell12.setHorizontalAlignment(Element.ALIGN_LEFT);
							paxCell12.setPadding(5.0f);
							paxCell12.setBackgroundColor(flightColor);
							passengerContactDetailTable.addCell(paxCell12);

							PdfPCell paxCell13 = new PdfPCell(
									new Paragraph(passengerContactElementList.get(v).getEmailId(), commonBoldFont8));
							paxCell13.setBorder(Rectangle.NO_BORDER);
							paxCell13.setHorizontalAlignment(Element.ALIGN_LEFT);
							paxCell13.setPadding(5.0f);
							paxCell13.setBackgroundColor(flightColor);
							passengerContactDetailTable.addCell(paxCell13);

							PdfPCell paxCell14 = new PdfPCell(new Paragraph("-", commonBoldFont8));
							paxCell14.setBorder(Rectangle.NO_BORDER);
							paxCell14.setHorizontalAlignment(Element.ALIGN_LEFT);
							paxCell14.setPadding(5.0f);
							paxCell14.setBackgroundColor(flightColor);
							passengerContactDetailTable.addCell(paxCell14);

		    	}
					} // End of Passenger loop

		    maintable.addCell(passengerContactDetailTable);

					// End: Passenger contact detail

		    PdfPTable passengerDetailTable = new PdfPTable(5);

		    passengerDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

					float[] passengerColumnWidths = new float[] { 18f, 14f, 17f, 10f, 13f };

		    passengerDetailTable.setWidths(passengerColumnWidths);

		    java.util.List<PassengerInfo> passengerElementList = airTicketDetailsXml.getPassengerInfo();

					PdfPCell paxCell0 = new PdfPCell(new Paragraph("Traveller Details", commonBoldFont10));
					paxCell0.setBorder(Rectangle.NO_BORDER);
					paxCell0.setColspan(7);
					paxCell0.setBackgroundColor(flightColor);
					paxCell0.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell0.setPadding(8.0f);
					passengerDetailTable.addCell(paxCell0);

					PdfPCell paxCell = new PdfPCell(new Paragraph("Pax Name", commonBoldFont7));
		    paxCell.setBorder(Rectangle.NO_BORDER);
		    paxCell.setBackgroundColor(flightColor);
					paxCell.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell.setPadding(5.0f);
		    passengerDetailTable.addCell(paxCell);

					PdfPCell paxCell1 = new PdfPCell(new Paragraph("Traveller Type", commonBoldFont7));
					paxCell1.setBorder(Rectangle.NO_BORDER);
					paxCell1.setBackgroundColor(flightColor);
					paxCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell1.setPadding(5.0f);
					passengerDetailTable.addCell(paxCell1);

					PdfPCell paxCell4 = new PdfPCell(new Paragraph("Ticket Number", commonBoldFont7));
					paxCell4.setBorder(Rectangle.NO_BORDER);
					paxCell4.setBackgroundColor(flightColor);
					paxCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell4.setPadding(5.0f);
					passengerDetailTable.addCell(paxCell4);

					PdfPCell paxCell5 = new PdfPCell(new Paragraph("Airline PNR", commonBoldFont7));
					paxCell5.setBorder(Rectangle.NO_BORDER);
					paxCell5.setBackgroundColor(flightColor);
					paxCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell5.setPadding(5.0f);
					passengerDetailTable.addCell(paxCell5);

					PdfPCell paxCell6 = new PdfPCell(new Paragraph("GDS PNR", commonBoldFont7));
					paxCell6.setBorder(Rectangle.NO_BORDER);
					paxCell6.setBackgroundColor(flightColor);
					paxCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell6.setPadding(5.0f);
					passengerDetailTable.addCell(paxCell6);

					for (int v = 0; v < passengerElementList.size(); v++) {

						PdfPCell paxCell00 = new PdfPCell(new Paragraph(passengerElementList.get(v).getTitle() + ". "
								+ passengerElementList.get(v).getFirstName() + " "
								+ passengerElementList.get(v).getMiddleName() + " "
								+ passengerElementList.get(v).getLastName(), commonBoldFont8));
						paxCell00.setBorder(Rectangle.NO_BORDER);
						paxCell00.setHorizontalAlignment(Element.ALIGN_LEFT);
						paxCell00.setPadding(5.0f);
						paxCell00.setBackgroundColor(flightColor);
						passengerDetailTable.addCell(paxCell00);

						PdfPCell paxCell10 = new PdfPCell(
								new Paragraph(passengerElementList.get(v).getPassCategory(), commonBoldFont8));
						paxCell10.setBorder(Rectangle.NO_BORDER);
						paxCell10.setHorizontalAlignment(Element.ALIGN_LEFT);
						paxCell10.setPadding(5.0f);
						paxCell10.setBackgroundColor(flightColor);
						passengerDetailTable.addCell(paxCell10);

						PdfPCell paxCell40 = new PdfPCell(
								new Paragraph(passengerElementList.get(v).getTicketNumber(), commonBoldFont8));
						paxCell40.setBorder(Rectangle.NO_BORDER);
						paxCell40.setHorizontalAlignment(Element.ALIGN_LEFT);
						paxCell40.setPadding(5.0f);
						paxCell40.setBackgroundColor(flightColor);
						passengerDetailTable.addCell(paxCell40);

						PdfPCell paxCell50 = new PdfPCell(new Paragraph(airLinePNR, commonBoldFont8));
						paxCell50.setBorder(Rectangle.NO_BORDER);
						paxCell50.setHorizontalAlignment(Element.ALIGN_LEFT);
						paxCell50.setPadding(5.0f);
						paxCell50.setBackgroundColor(flightColor);
						passengerDetailTable.addCell(paxCell50);

						PdfPCell paxCell60 = new PdfPCell(new Paragraph(gdsPNR, commonBoldFont8));
						paxCell60.setBorder(Rectangle.NO_BORDER);
						paxCell60.setHorizontalAlignment(Element.ALIGN_LEFT);
						paxCell60.setPadding(5.0f);
						paxCell60.setBackgroundColor(flightColor);
						passengerDetailTable.addCell(paxCell60);

					} // End of Passenger loop

		    maintable.addCell(passengerDetailTable);

		    PdfPTable fareDetailTable = new PdfPTable(8);

		    fareDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

					float[] fareColumnWidths = new float[] { 10f, 30f, 9f, 8f, 8f, 10f, 10f, 15f };

		    fareDetailTable.setWidths(fareColumnWidths);

					float fuelCharge = 0;
					float totalInvoice = 0;
					float gstTax = 0;
					float otherTax = 0;
		    InvoiceInfoModel invoiceElement = airTicketDetailsXml.getInvoiceInfoModel();

					try {

						fuelCharge = invoiceElement.getFuleCharge();
						gstTax = invoiceElement.getGstTax();
						otherTax = invoiceElement.getOtherTax();
						totalInvoice = invoiceElement.getTotalInvoice();

					} catch (NullPointerException npe) {

						DODLog.printStackTrace(npe, AirEmailTicketService.class, LogConstant.EMAIL_PDF_LOG_FILE);
		    }

					PdfPCell fareCell0 = new PdfPCell(new Paragraph("Billing Details", commonBoldFont10));
					fareCell0.setBorder(Rectangle.NO_BORDER);
					fareCell0.setColspan(8);
					fareCell0.setBackgroundColor(flightColor);
					fareCell0.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell0.setPadding(5.0f);
					fareDetailTable.addCell(fareCell0);

					PdfPCell fareCell1 = new PdfPCell(new Paragraph("Base Fare :", commonNormalFont7));
					fareCell1.setColspan(7);
					fareCell1.setBorder(Rectangle.NO_BORDER);
					fareCell1.setBackgroundColor(flightColor);
					fareCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell1.setPadding(5.0f);
					fareDetailTable.addCell(fareCell1);

					PdfPCell fareCell2 = new PdfPCell(
							new Paragraph(invoiceElement.getBaseFare() + "", commonNormalFont7));
					fareCell2.setBorder(Rectangle.NO_BORDER);
					fareCell2.setBackgroundColor(flightColor);
					fareCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell2.setPadding(5.0f);
					fareDetailTable.addCell(fareCell2);

					PdfPCell fareCell5 = new PdfPCell(new Paragraph("Fuel Charges : ", commonNormalFont7));
					fareCell5.setColspan(7);
					fareCell5.setBorder(Rectangle.NO_BORDER);
					fareCell5.setBackgroundColor(flightColor);
					fareCell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell5.setPadding(5.0f);
					fareDetailTable.addCell(fareCell5);

					PdfPCell fareCell6 = new PdfPCell(new Paragraph(fuelCharge + "", commonNormalFont7));
					fareCell6.setBorder(Rectangle.NO_BORDER);
					fareCell6.setBackgroundColor(flightColor);
					fareCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell6.setPadding(5.0f);
					fareDetailTable.addCell(fareCell6);

					PdfPCell fareCell13 = new PdfPCell(new Paragraph("GST : ", commonNormalFont7));
					fareCell13.setColspan(7);
					fareCell13.setBorder(Rectangle.NO_BORDER);
					fareCell13.setBackgroundColor(flightColor);
					fareCell13.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell13.setPadding(5.0f);
					fareDetailTable.addCell(fareCell13);

					PdfPCell fareCell14 = new PdfPCell(new Paragraph(gstTax + "", commonNormalFont7));
					fareCell14.setBorder(Rectangle.NO_BORDER);
					fareCell14.setBackgroundColor(flightColor);
					fareCell14.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell14.setPadding(5.0f);
					fareDetailTable.addCell(fareCell14);

					PdfPCell fareCell15 = new PdfPCell(new Paragraph("Tax : ", commonNormalFont7));
					fareCell15.setColspan(7);
					fareCell15.setBorder(Rectangle.NO_BORDER);
					fareCell15.setBackgroundColor(flightColor);
					fareCell15.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell15.setPadding(5.0f);
					fareDetailTable.addCell(fareCell15);

					PdfPCell fareCell16 = new PdfPCell(new Paragraph(invoiceElement.getTax() + "", commonNormalFont7));
					fareCell16.setBorder(Rectangle.NO_BORDER);
					fareCell16.setBackgroundColor(flightColor);
					fareCell16.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell16.setPadding(5.0f);
					fareDetailTable.addCell(fareCell16);

					PdfPCell fareCell17 = new PdfPCell(new Paragraph("Other Tax : ", commonNormalFont7));
					fareCell17.setColspan(7);
					fareCell17.setBorder(Rectangle.NO_BORDER);
					fareCell17.setBackgroundColor(flightColor);
					fareCell17.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell17.setPadding(5.0f);
					fareDetailTable.addCell(fareCell17);

					PdfPCell fareCell18 = new PdfPCell(new Paragraph(otherTax + "", commonNormalFont7));
					fareCell18.setBorder(Rectangle.NO_BORDER);
					fareCell18.setBackgroundColor(flightColor);
					fareCell18.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell18.setPadding(5.0f);
					fareDetailTable.addCell(fareCell18);

					PdfPCell fareCell19 = new PdfPCell(new Paragraph("Grand Total : ", commonBoldFont7Red));
					fareCell19.setColspan(7);
					fareCell19.setBorder(Rectangle.NO_BORDER);
					fareCell19.setBackgroundColor(flightColor);
					fareCell19.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell19.setPadding(5.0f);
					fareDetailTable.addCell(fareCell19);

					PdfPCell fareCell20 = new PdfPCell(new Paragraph(totalInvoice + "", commonBoldFont7Red));
					fareCell20.setBorder(Rectangle.NO_BORDER);
					fareCell20.setBackgroundColor(flightColor);
					fareCell20.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell20.setPadding(5.0f);
					fareDetailTable.addCell(fareCell20);

					PdfPCell fareCell21 = new PdfPCell(new Paragraph("GST No. :", commonNormalFont7));
					fareCell21.setColspan(1);
					fareCell21.setBorder(Rectangle.NO_BORDER);
					fareCell21.setBackgroundColor(flightColor);
					fareCell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell21.setPadding(5.0f);
					fareDetailTable.addCell(fareCell21);

					PdfPCell fareCell22 = new PdfPCell(new Paragraph(airLineGST, commonBoldFont7Red));
					fareCell22.setColspan(4);
					fareCell22.setBorder(Rectangle.NO_BORDER);
					fareCell22.setBackgroundColor(flightColor);
					fareCell22.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell22.setPadding(5.0f);
					fareDetailTable.addCell(fareCell22);

					PdfPCell fareCell23 = new PdfPCell(new Paragraph("Fare Type :", commonNormalFont7));
					fareCell23.setColspan(2);
					fareCell23.setBorder(Rectangle.NO_BORDER);
					fareCell23.setBackgroundColor(flightColor);
					fareCell23.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell23.setPadding(5.0f);
					fareDetailTable.addCell(fareCell23);

					PdfPCell fareCell24 = new PdfPCell(new Paragraph(isRefundable + " *", commonBoldFont7Red));
					fareCell24.setColspan(1);
					fareCell24.setBorder(Rectangle.NO_BORDER);
					fareCell24.setBackgroundColor(flightColor);
					fareCell24.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell24.setPadding(5.0f);
					fareDetailTable.addCell(fareCell24);

		    maintable.addCell(fareDetailTable);

					// Start: Passenger Refund Details

					java.util.List<PaxCanInvoiceInfo> passengerRefundElementList = airTicketDetailsXml
							.getPaxCanInvoiceInfo();

					if (null != passengerRefundElementList && !passengerRefundElementList.isEmpty()) {

			    PdfPTable passengerRefundDetailTable = new PdfPTable(8);

			    passengerRefundDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
						float[] passengerRefundColumnWidths = new float[] { 13f, 13f, 13f, 13f, 13f, 13f, 13f, 13f };
			    passengerRefundDetailTable.setWidths(passengerRefundColumnWidths);

						PdfPCell refCell001 = new PdfPCell(new Paragraph("Refund Details", commonBoldFont10));
						refCell001.setBorder(Rectangle.NO_BORDER);
						refCell001.setColspan(9);
						refCell001.setBackgroundColor(flightColor);
						refCell001.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell001.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell001);

						PdfPCell refCell01 = new PdfPCell(new Paragraph("Traveler Name", commonBoldFont7));
						refCell01.setBorder(Rectangle.NO_BORDER);
						refCell01.setBackgroundColor(flightColor);
						refCell01.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell01.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell01);

						PdfPCell refCell02 = new PdfPCell(new Paragraph("Pax ID", commonBoldFont7));
						refCell02.setBorder(Rectangle.NO_BORDER);
						refCell02.setBackgroundColor(flightColor);
						refCell02.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell02.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell02);

						PdfPCell refCell03 = new PdfPCell(new Paragraph("Cancelled Txn ID", commonBoldFont7));
						refCell03.setBorder(Rectangle.NO_BORDER);
						refCell03.setBackgroundColor(flightColor);
						refCell03.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell03.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell03);

						PdfPCell refCell04 = new PdfPCell(new Paragraph("Refund Status", commonBoldFont7));
						refCell04.setBorder(Rectangle.NO_BORDER);
						refCell04.setBackgroundColor(flightColor);
						refCell04.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell04.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell04);

						PdfPCell refCell05 = new PdfPCell(new Paragraph("Refund Invoice ID", commonBoldFont7));
						refCell05.setBorder(Rectangle.NO_BORDER);
						refCell05.setBackgroundColor(flightColor);
						refCell05.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell05.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell05);

						PdfPCell refCell06 = new PdfPCell(new Paragraph("Refunded Date", commonBoldFont7));
						refCell06.setBorder(Rectangle.NO_BORDER);
						refCell06.setBackgroundColor(flightColor);
						refCell06.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell06.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell06);

						PdfPCell refCell07 = new PdfPCell(new Paragraph("Refunded Amount", commonBoldFont7));
						refCell07.setBorder(Rectangle.NO_BORDER);
						refCell07.setBackgroundColor(flightColor);
						refCell07.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell07.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell07);

						PdfPCell refCell08 = new PdfPCell(new Paragraph("Cancellation Date", commonBoldFont7));
						refCell08.setBorder(Rectangle.NO_BORDER);
						refCell08.setBackgroundColor(flightColor);
						refCell08.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell08.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell08);

						for (int v = 0; v < passengerRefundElementList.size(); v++) {

							PdfPCell refCell10 = new PdfPCell(new Paragraph(
									passengerRefundElementList.get(v).getPassangerFullName(), commonNormalFont7));
							refCell10.setBorder(Rectangle.NO_BORDER);
							refCell10.setBackgroundColor(flightColor);
							refCell10.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell10.setPadding(5.0f);
							passengerRefundDetailTable.addCell(refCell10);

							PdfPCell refCell101 = new PdfPCell(new Paragraph(
									passengerRefundElementList.get(v).getOnwardPaxId(), commonNormalFont7));
							refCell101.setBorder(Rectangle.NO_BORDER);
							refCell101.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell101.setPadding(5.0f);
							refCell101.setBackgroundColor(flightColor);
							passengerRefundDetailTable.addCell(refCell101);

							PdfPCell refCell102 = new PdfPCell(new Paragraph(
									passengerRefundElementList.get(v).getCancellationTaxId(), commonNormalFont7));
							refCell102.setBorder(Rectangle.NO_BORDER);
							refCell102.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell102.setPadding(5.0f);
							refCell102.setBackgroundColor(flightColor);
							passengerRefundDetailTable.addCell(refCell102);

							PdfPCell refCell103 = new PdfPCell(new Paragraph(
									passengerRefundElementList.get(v).getInvoiceStatus(), "", commonNormalFont7));
							refCell103.setBorder(Rectangle.NO_BORDER);
							refCell103.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell103.setPadding(5.0f);
							refCell103.setBackgroundColor(flightColor);
							passengerRefundDetailTable.addCell(refCell103);

							PdfPCell refCell104 = new PdfPCell(new Paragraph(
									passengerRefundElementList.get(v).getCreditNoteNo(), commonNormalFont7));
							refCell104.setBorder(Rectangle.NO_BORDER);
							refCell104.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell104.setPadding(5.0f);
							refCell104.setBackgroundColor(flightColor);
							passengerRefundDetailTable.addCell(refCell104);

							PdfPCell refCell105 = new PdfPCell(new Paragraph(
									passengerRefundElementList.get(v).getCreditNoteDate(), commonNormalFont7));
							refCell105.setBorder(Rectangle.NO_BORDER);
							refCell105.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell105.setPadding(5.0f);
							refCell105.setBackgroundColor(flightColor);
							passengerRefundDetailTable.addCell(refCell105);

							PdfPCell refCell106 = new PdfPCell(new Paragraph(String.valueOf(passengerRefundElementList.get(v).getCalTotalRefund()), commonNormalFont7));
							refCell106.setBorder(Rectangle.NO_BORDER);
							refCell106.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell106.setPadding(5.0f);
							refCell106.setBackgroundColor(flightColor);
							passengerRefundDetailTable.addCell(refCell106);

							PdfPCell refCell107 = new PdfPCell(new Paragraph(
									passengerRefundElementList.get(v).getCancellationDate(), commonNormalFont7));
							refCell107.setBorder(Rectangle.NO_BORDER);
							refCell107.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell107.setPadding(5.0f);
							refCell107.setBackgroundColor(flightColor);
							passengerRefundDetailTable.addCell(refCell107);

						}
			    maintable.addCell(passengerRefundDetailTable);

			 }
	    }

				// End: Passenger Refund detail

		    PdfPTable blankTable = new PdfPTable(1);
		    blankTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

				PdfPCell blankTableCell0 = new PdfPCell(new Paragraph("DTS Air Helpline", commonBoldFont7));
				blankTableCell0.setBorder(Rectangle.NO_BORDER);
				blankTableCell0.setBackgroundColor(flightColor);
				blankTableCell0.setHorizontalAlignment(Element.ALIGN_LEFT);
				blankTableCell0.setPadding(3.0f);
				blankTable.addCell(blankTableCell0);

				PdfPCell blankTableCell01 = new PdfPCell(new Paragraph(
						"For Queries (Timings 09:30 AM to 06:00 PM) Monday to Saturday", commonNormalFont7));
				blankTableCell01.setBorder(Rectangle.NO_BORDER);
				blankTableCell01.setBackgroundColor(flightColor);
				blankTableCell01.setHorizontalAlignment(Element.ALIGN_LEFT);
				blankTableCell01.setPadding(3.0f);
				blankTable.addCell(blankTableCell01);

				PdfPCell blankTableCell02 = new PdfPCell(new Paragraph(
						"Call us on: 011-26700300 or E-mail: air.helpdesk@hub.nic.in", commonNormalFont7));
				blankTableCell02.setBorder(Rectangle.NO_BORDER);
				blankTableCell02.setBackgroundColor(flightColor);
				blankTableCell02.setHorizontalAlignment(Element.ALIGN_LEFT);
				blankTableCell02.setPadding(3.0f);
				blankTable.addCell(blankTableCell02);

				PdfPCell blankTableCell1 = new PdfPCell(
						new Paragraph("Ashok Travels & Tours Helpline: (Timing: 24x7)", commonBoldFont7));
				blankTableCell1.setBorder(Rectangle.NO_BORDER);
				blankTableCell1.setBackgroundColor(flightColor);
				blankTableCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				blankTableCell1.setPadding(3.0f);
				blankTable.addCell(blankTableCell1);

				PdfPCell blankTableCell11 = new PdfPCell(
						new Paragraph("Call on: 7304542848 and 7738687401 and 011-26700300", commonNormalFont7));
				blankTableCell11.setBorder(Rectangle.NO_BORDER);
				blankTableCell11.setBackgroundColor(flightColor);
				blankTableCell11.setHorizontalAlignment(Element.ALIGN_LEFT);
				blankTableCell11.setPadding(3.0f);
				blankTable.addCell(blankTableCell11);
		    maintable.addCell(blankTable);

				PdfPCell blankTableCell12 = new PdfPCell(
						new Paragraph("* (after deduction of applicable cancellation charges)", commonBoldFont7Red));
				blankTableCell12.setBorder(Rectangle.NO_BORDER);
				blankTableCell12.setBackgroundColor(flightColor);
				blankTableCell12.setHorizontalAlignment(Element.ALIGN_LEFT);
				blankTableCell12.setPadding(3.0f);
				maintable.addCell(blankTableCell12);

		    PdfPTable rulesInforTable = new PdfPTable(1);
		    rulesInforTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

				PdfPCell ruleHeaderCell0 = new PdfPCell(new Paragraph("Rules and Conditions", commonBoldFont10));
				ruleHeaderCell0.setBorder(Rectangle.NO_BORDER);
				ruleHeaderCell0.setBackgroundColor(flightColor);
				ruleHeaderCell0.setHorizontalAlignment(Element.ALIGN_LEFT);
				ruleHeaderCell0.setPadding(5.0f);
				rulesInforTable.addCell(ruleHeaderCell0);

		    PdfPCell listCell = new PdfPCell();
		    listCell.setBorder(Rectangle.NO_BORDER);

				List orderedList = new List(List.ORDERED, 20f);
				orderedList.add(new ListItem(
						"DTS Air Helpline number 011-26700300 is available from 9:30 to 6:00 (Mon – Sat). You may also email your concern at air.helpdesk@hub.nic.in",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"Report at the designated Airport terminal as per the recommended reporting time of the Airline. Airline check-in counters closing time varies from airline to airline. Certain Airports have enhanced reporting time due to high security checks.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"Flight Timings are subject to change without prior notice. Please re-check with the Airline prior to departure to avoid any inconvenience.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"For queries such as Departure/Arrival status of Flights, Flight Delays, Terminal Information, Baggage Information, Cancellation Status etc, users may call the respective service provider Customer Care or the Airline’s helpline/counter keeping in view the information printed on the ticket. Users may provide the Ticket Number & Airline/GDS PNR for identification.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"Users should cancel tickets Online through DTS unless it is not feasible to do so. Online cancellation updates the required information in DTS immediately for any further action such as rebooking/refunds.However if the status is not updated within 10 minutes you may kindly call the helpline number to get it updated. Ticket for the same day and sector can only be booked after the status is updated as cancelled.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"In case sufficient time is not remaining for online cancellation of Air Ticket, user may call the Customer Care of Service Provider. Tickets can be cancelled by contacting the Customer Care of Service Providers and not through the Airline Website. Service Provider numbers are as follows:",
						commonNormalFont7));

            List sublist = new List(false, false, 10);
				sublist.setListSymbol(new Chunk("", commonBoldFont6));
				sublist.add(new ListItem("Balmer Lawrie & Co. Ltd.:   0124-4603500/ 0124-6282500", commonBoldFont8));
				sublist.add(new ListItem("Ashoka Travels and Tours:   7304542848 and 7738687401 and 011-26700300",
						commonBoldFont8));
            orderedList.add(sublist);

				orderedList.add(new ListItem(
						"In case user is not able to contact service provider he may cancel the ticket directly through airlines also on the following airlinenumbers:",
						commonNormalFont7));

            List custlist = new List(true, false, 10);
				custlist.setListSymbol(new Chunk("", commonBoldFont6));
				custlist.add(new ListItem("Air India       :   011-69329333",commonBoldFont8));
	            custlist.add(new ListItem("Akasa       :   09606112131",commonBoldFont8));
	            custlist.add(new ListItem("Indigo          :   0124-6173838 / 0124-4973838",commonBoldFont8));
	            custlist.add(new ListItem("Star Air          :   022-50799555",commonBoldFont8));
	            custlist.add(new ListItem("Spice Jet     :   0124-4983410",commonBoldFont8));
	            custlist.add(new ListItem("Alliance Air     : 044 4255 4255/044 3511 3511",commonBoldFont8));
	            custlist.add(new ListItem("Air India Exp     : 080 4666 2222",commonBoldFont8));
            orderedList.add(custlist);

				List custlist1 = new List(false, false, 10);
				custlist1.setListSymbol(new Chunk("", commonBoldFont6));
				custlist1.add(
						new ListItem("*Users may also confirm the above numbers from respective websites of Airlines.",
								commonBoldFont8));
				orderedList.add(custlist1);

				orderedList.add(new ListItem(
						"In case of Offline cancellation DTS Air helpline may be intimated via e-mail to update the status of the cancelled Ticket in DTS for processing of refunds and records kept your end for future references.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"Different Cancellation Timings/Policies are applicable based on the Booking Class (S, T, H, etc) of the Ticket. Users may contact the Airlines or the Service Providers for information regarding cancellation policies. Information regarding Booking Class is available on the final Booking Page and the DTS Air Ticket Printouts.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"If a passenger fails to cancel the travel booking and do not report for travel on time, the Airline will consider such passenger as No Show and refund of fare is not applicable in such cases. The cost is to be borne by the passenger.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"All refunds will be automatically made to Defence Travel System upon cancellation of Air Ticket. Refunds will be processed as per the cancellation policies of the airlines.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"Seat/Meal requests may/may not be realized depending on the participating airline.",
						commonNormalFont7));

				/* CODE BLOCK TO ADD INTERNATIONAL TRAVEL CONDITION */
				 if(domInt.get()==1){
				 
				  orderedList.add(new
				  ListItem("Passport: Valid passport with minimum expiry date as per the country of travel(06 Months)."
				  ,commonNormalFont7)); orderedList.add(new
				  ListItem("Vaccine Requirements: All fully vaccinated must carry certificate, partially vaccinated, and non-vaccinated Indians can travel but required RTPCR report as per country requirement. Travelers must check the recently updated policy before traveling (African Nations).Some countries may require proof of vaccination against certain diseases, such as yellow fever or cholera."
				  ,commonNormalFont7)); orderedList.add(new
				  ListItem("Visiting Guidelines: Please check all the detailed guidelines for both your chosen destinations/countries and airlines."
				  ,commonNormalFont7)); orderedList.add(new
				  ListItem("Fare Rules: Fare will be applicable as per airlines policies, must check the fare refund rule before booking, in case of non-refundable tickets – no refund will be applicable."
				  ,commonNormalFont7)); orderedList.add(new
				  ListItem("Visa Requirements: All travellers must present hard copies of their foreign visa (soft copies won’t be accepted) at the immigration counters during departure. Defence Travel System, Balmer Lawrie, Ashok Travels & Tours and Corporate Client hold no liability with respect to visa information. To get further details on visa/transit and passport requirements please contact embassy before booking your travel."
				  ,commonNormalFont7)); orderedList.add(new
				  ListItem("Additional Services post booking: Once International flight booking is done on the portal then there will not be any additional services are to be purchased offline or online through Airline portal on direct payment basis. "
				  ,commonNormalFont7)); orderedList.add(new
				  ListItem("Name: Please ensure that the spelling of your name and other details match with your travel document/ govt.ID as these cannot be changed later. Incorrect details will lead to cancellation penalties or including reissued charges / Airline can deny boarding."
				  ,commonNormalFont7)); orderedList.add(new
				  ListItem("Check the airline's baggage allowance: Each airline has different baggage allowances for checked and carry-on luggage. Make sure you know how much luggage you can bring with you before you book your flight."
				  ,commonNormalFont7)); orderedList.add(new
				  ListItem("Check the airline's website for the latest updates: The airline's website is a good source of information about the latest travel rules and regulations. You should check the website before you book your flight to make sure you are up-todate on the latest information."
				  ,commonNormalFont7)); orderedList.add(new
				  ListItem("Please note that travellers are solely responsible for ensuring their eligibility to enter the destination or transit countries. We accept no liability in this regard. Please check the travel rules of all regulatory websites before booking and commencing travel. "
				 ,commonNormalFont7)); } 
				/* END CODE BLOCK TO ADD INTERNATIONAL TRAVEL CONDITION*/
				 
            listCell.addElement(orderedList);

            rulesInforTable.addCell(listCell);

				PdfPCell mandatoryCell = new PdfPCell(new Paragraph(
						"Mandatory: All travelers have to ensure that they must carry a copy of this e-ticket accompanied with a photo identification (ID) card as per requirement of security at the airport. ID Cards may include Driving License, Passport,PAN Card,Voter ID Card, Aadhaar Card or any other ID issued by Government of India. Birth Certificate is a mandatory proof forinfant traveler.",
						commonBoldFont8));
            mandatoryCell.setBorder(Rectangle.NO_BORDER);

            rulesInforTable.addCell(mandatoryCell);

		    maintable.addCell(rulesInforTable);

		    document.add(maintable);

		    document.close();

 		    }

	   catch (Exception e) {
			DODLog.printStackTrace(e, AirEmailTicketService.class, LogConstant.EMAIL_PDF_LOG_FILE);
		}

		}
	}

	public void airBLTicketPDF(AirTicketPdfModel airTicketDetailsXml, ByteArrayOutputStream baos, String personalNumber,
			boolean flag) {
		
		Document document = null;
		String iataCode = DODDATAConstants.DOD_ATT_IATA_CODE_NUMBER;
		if (airTicketDetailsXml != null) {
			try {

				document = new Document(PageSize.A4, 10, 10, 10, 10);

				  PdfPTable flightDetailTable = new PdfPTable(5);
				BaseColor flightColor = new BaseColor(255, 255, 255);
				  PdfPTable maintable = new PdfPTable(1);

				Font commonBoldFont8 = FontFactory.getFont("", 8, Font.BOLD);
				Font commonBoldFont10 = FontFactory.getFont("", 10, Font.BOLD);
				Font commonBoldFont12 = FontFactory.getFont("", 12, Font.BOLD);
				Font commonNormalFont7 = FontFactory.getFont("", 7);

				BaseColor redColor = new BaseColor(255, 0, 0);
			

				Font commonBoldFont7Red = FontFactory.getFont("", 7, Font.BOLD, redColor);
				Font commonBoldFont7 = FontFactory.getFont("", 7, Font.BOLD);
				Font commonBoldFont6 = FontFactory.getFont("", 6, Font.BOLD);
				/* Block to Find the Travel Type DomIntiger */

				String tktRequestId = airTicketDetailsXml.getRequestId();
				AtomicInteger domInt = new AtomicInteger(0);
				int domIntiger = Optional.ofNullable(isTravelTypeDomInt(tktRequestId)).orElse(0); 
				domInt.set(domIntiger);

				/* Block to Find the Travel Type DomIntiger */

				if (document != null) {
					PdfWriter writer = PdfWriter.getInstance(document, baos);
					if (flag) {
						writer.setEncryption(personalNumber.getBytes(), personalNumber.getBytes(),
								PdfWriter.ALLOW_PRINTING, PdfWriter.ALLOW_COPY);
	        	}
	        	document.open();

					String irctcLogoPath = DODDATAConstants.PCDA_LOGO_PATH;

				 PdfPTable imageTable = new PdfPTable(1);
					Image addlogo = null;
					try {
						addlogo = Image.getInstance(irctcLogoPath + "/DTS_Header.png");
					} catch (Exception e) {
						DODLog.printStackTrace(e, AirEmailTicketService.class, LogConstant.EMAIL_PDF_LOG_FILE);
					}

					addlogo.scaleToFit(455f, 6700f);

					PdfPCell cell0 = new PdfPCell(addlogo);
					cell0.setBorder(Rectangle.NO_BORDER);
					cell0.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
					cell0.setVerticalAlignment(Rectangle.ALIGN_CENTER);
					imageTable.addCell(cell0);

					maintable.addCell(imageTable);

				 	PdfPTable claimTable1 = new PdfPTable(1);
				    claimTable1.getDefaultCell().setBorder(Rectangle.NO_BORDER);

					PdfPCell claimCell1 = new PdfPCell(new Paragraph(
							"ALL TRAVELERS WHO HAVE BOOKED THEIR TICKET THROUGH DEFENCE TRAVEL SYSTEM MUST SUBMIT THEIR FINAL SETTLEMENT CLAIM TO THEIR RESPECTIVE PAO OFFICES WITHIN STIPULATED TIME w.r.t.AIR TRAVEL EVEN IF THE TICKET/JOURNEY HAS BEEN CANCELLED.",
							commonBoldFont12));
					claimCell1.setBorder(Rectangle.NO_BORDER);
					claimCell1.setBackgroundColor(flightColor);
					claimCell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
					claimCell1.setPadding(3.0f);
					claimTable1.addCell(claimCell1);
				    maintable.addCell(claimTable1);

				 	PdfPTable headerTable = new PdfPTable(1);

			        Font fontbold = FontFactory.getFont("", 14, Font.BOLD);
			        headerTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
					PdfPCell cell = null;
					if (airTicketDetailsXml.getShowLTCLabel().equals("YES")) {
						cell = new PdfPCell(new Paragraph("LTC Air Ticket", fontbold));
					} else {
						cell = new PdfPCell(new Paragraph("Air Ticket", fontbold));
			        }

			        cell.setBorder(Rectangle.NO_BORDER);
			        cell.setBackgroundColor(flightColor);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPadding(8.0f);
				    headerTable.addCell(cell);

				    maintable.addCell(headerTable);

				    PdfPTable journeyDetailTable = new PdfPTable(6);
				    journeyDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

					float[] journeyColumnWidths = new float[] { 22f, 20f, 19f, 22f, 16f, 12f };

					PdfPCell journeycell0 = new PdfPCell(new Paragraph("Booking Id: ", commonBoldFont8));
					journeycell0.setBorder(Rectangle.NO_BORDER);
					journeycell0.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell0.setBackgroundColor(flightColor);
					journeycell0.setPadding(8.0f);

					journeyDetailTable.addCell(journeycell0);

					PdfPCell journeycell00 = new PdfPCell(
							new Paragraph(airTicketDetailsXml.getBookingId(), commonNormalFont7));
					journeycell00.setBorder(Rectangle.NO_BORDER);
					journeycell00.setBackgroundColor(flightColor);
					journeycell00.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell00.setPadding(8.0f);

					journeyDetailTable.addCell(journeycell00);

					PdfPCell journeycell1 = new PdfPCell(new Paragraph("Booking Date: ", commonBoldFont8));
					journeycell1.setBorder(Rectangle.NO_BORDER);
					journeycell1.setBackgroundColor(flightColor);
					journeycell1.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell1.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell1);

					PdfPCell journeycell10 = new PdfPCell(
							new Paragraph(airTicketDetailsXml.getBookingDate(), commonNormalFont7));
					journeycell10.setBorder(Rectangle.NO_BORDER);
					journeycell10.setBackgroundColor(flightColor);
					journeycell10.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell10.setPadding(8.0f);

					journeyDetailTable.addCell(journeycell10);

					PdfPCell journeycell2 = new PdfPCell(new Paragraph("Status: ", commonBoldFont8));

					journeycell2.setBorder(Rectangle.NO_BORDER);
					journeycell2.setBackgroundColor(flightColor);
					journeycell2.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell2.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell2);

					PdfPCell journeycell20 = null;

					if (airTicketDetailsXml.getBookingStatus().equalsIgnoreCase("Booked")) {
						journeycell20 = new PdfPCell(new Paragraph("Confirmed", commonNormalFont7));
					} else {
						journeycell20 = new PdfPCell(
								new Paragraph(airTicketDetailsXml.getBookingStatus(), commonNormalFont7));
				    }

					journeycell20.setBorder(Rectangle.NO_BORDER);
					journeycell20.setBackgroundColor(flightColor);
					journeycell20.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell20.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell20);

					PdfPCell journeycell021 = new PdfPCell(new Paragraph("BL Reference No.: ", commonBoldFont8));
					journeycell021.setBorder(Rectangle.NO_BORDER);
					journeycell021.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell021.setBackgroundColor(flightColor);
					journeycell021.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell021);

					PdfPCell journeycell022 = new PdfPCell(
							new Paragraph(airTicketDetailsXml.getOperatorTxnId(), commonNormalFont7));
					journeycell022.setBorder(Rectangle.NO_BORDER);
					journeycell022.setBackgroundColor(flightColor);
					journeycell022.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell022.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell022);

					PdfPCell journeycell023 = new PdfPCell(new Paragraph("Agent Name: ", commonBoldFont8));
					journeycell023.setBorder(Rectangle.NO_BORDER);
					journeycell023.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell023.setBackgroundColor(flightColor);
					journeycell023.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell023);

					PdfPCell journeycell024 = new PdfPCell(new Paragraph("Balmer Lawrie", commonNormalFont7));
					journeycell024.setBorder(Rectangle.NO_BORDER);
					journeycell024.setBackgroundColor(flightColor);
					journeycell024.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell024.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell024);

					PdfPCell journeycell025 = new PdfPCell(new Paragraph("IATA Code", commonBoldFont8));
					journeycell025.setBorder(Rectangle.NO_BORDER);
					journeycell025.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell025.setBackgroundColor(flightColor);
					journeycell025.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell025);

					PdfPCell journeycell026 = new PdfPCell(new Paragraph(iataCode, commonNormalFont7));
					journeycell026.setBorder(Rectangle.NO_BORDER);
					journeycell026.setBackgroundColor(flightColor);
					journeycell026.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell026.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell026);

					journeycell0 = new PdfPCell(new Paragraph("Trip Type: ", commonBoldFont8));
					journeycell0.setBorder(Rectangle.NO_BORDER);
					journeycell0.setBackgroundColor(flightColor);
					journeycell0.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell0.setPadding(8.0f);

					journeyDetailTable.addCell(journeycell0);

					if (airTicketDetailsXml.getBookedSegment() == 0) {

						if (airTicketDetailsXml.getJournyType().equalsIgnoreCase("Return")) {
							journeycell00 = new PdfPCell(new Paragraph("Special Round Trip", commonNormalFont7));

						} else {
							journeycell00 = new PdfPCell(new Paragraph("Oneway Trip", commonNormalFont7));
				    }

					} else {

						if (airTicketDetailsXml.getBookedSegment() == 1) {
							journeycell00 = new PdfPCell(new Paragraph("Onward Trip", commonNormalFont7));
						} else {
							journeycell00 = new PdfPCell(new Paragraph("Return Trip", commonNormalFont7));
				    }
					    	}

					journeycell00.setColspan(1);
					journeycell00.setBackgroundColor(flightColor);
					journeycell00.setBorder(Rectangle.NO_BORDER);
					journeycell00.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell00.setPadding(8.0f);

					journeyDetailTable.addCell(journeycell00);

					journeycell1 = new PdfPCell(new Paragraph("Booking Sector: ", commonBoldFont8));
					journeycell1.setBorder(Rectangle.NO_BORDER);
					journeycell1.setBackgroundColor(flightColor);
					journeycell1.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell1.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell1);

					if (airTicketDetailsXml.getJournyType().equalsIgnoreCase("Return")) {
						journeycell10 = new PdfPCell(new Paragraph(airTicketDetailsXml.getSourceSectorCity() + " ("
								+ airTicketDetailsXml.getSourceSectorCode() + ") - "
								+ airTicketDetailsXml.getDestinationSectorCity() + " ("
								+ airTicketDetailsXml.getDestinationSectorCode() + ") - "
								+ airTicketDetailsXml.getSourceSectorCity() + " ("
								+ airTicketDetailsXml.getSourceSectorCode() + ")", commonNormalFont7));
					} else {
						journeycell10 = new PdfPCell(new Paragraph(airTicketDetailsXml.getSourceSectorCity() + " ("
								+ airTicketDetailsXml.getSourceSectorCode() + ") - "
								+ airTicketDetailsXml.getDestinationSectorCity() + " ("
								+ airTicketDetailsXml.getDestinationSectorCode() + ")", commonNormalFont7));
					    }

					journeycell10.setColspan(3);
					journeycell10.setBackgroundColor(flightColor);
					journeycell10.setBorder(Rectangle.NO_BORDER);
					journeycell10.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell10.setPadding(8.0f);

					journeyDetailTable.addCell(journeycell10);


					PdfPCell journeycell30 = new PdfPCell(new Paragraph("Travel Rule:", commonBoldFont8));
					journeycell30.setColspan(1);
					journeycell30.setBorder(Rectangle.NO_BORDER);
					journeycell30.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell30.setBackgroundColor(flightColor);
					journeycell30.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell30);

					PdfPCell journeycell31 = new PdfPCell(new Paragraph(airTicketDetailsXml.getTrRule(), commonNormalFont7));
					journeycell31.setColspan(5);
					journeycell31.setBorder(Rectangle.NO_BORDER);
					journeycell31.setBackgroundColor(flightColor);
					journeycell31.setHorizontalAlignment(Element.ALIGN_LEFT);
					journeycell31.setPadding(8.0f);
					journeyDetailTable.addCell(journeycell31);

						    journeyDetailTable.setWidths(journeyColumnWidths);

						    maintable.addCell(journeyDetailTable);

					    flightDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

					float[] flightColumnWidths = new float[] { 2f, 13f, 25f, 30f, 25f };
					    flightDetailTable.setWidths(flightColumnWidths);

					String airLineGST = airTicketDetailsXml.getAirGSTNo();

					    String airLinePNR = "";
					    String gdsPNR = "";
					String isRefundable = "";

					if (airTicketDetailsXml.getJournyType().equalsIgnoreCase("Return")) {
					    	java.util.List<FlightInfo> onwardFlightList = airTicketDetailsXml.getFlightInfo();

					    	java.util.List<FlightInfo> returnFlightList = airTicketDetailsXml.getFlightInfo();

						
						{
							PdfPCell flightcell0 = new PdfPCell(
									new Paragraph("Flight Information (Onward)", commonBoldFont10));
							flightcell0.setBorder(Rectangle.NO_BORDER);
							flightcell0.setColspan(3);
							flightcell0.setBackgroundColor(flightColor);
							flightcell0.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell0.setPadding(8.0f);
							flightDetailTable.addCell(flightcell0);

							PdfPCell flightcell01 = new PdfPCell(
									new Paragraph("Stops : " + (onwardFlightList.size() - 1), commonBoldFont10));
							flightcell01.setBorder(Rectangle.NO_BORDER);
							flightcell01.setBackgroundColor(flightColor);
							flightcell01.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell01.setPadding(8.0f);
							flightDetailTable.addCell(flightcell01);

							PdfPCell flightcell02 = new PdfPCell(new Paragraph("", commonBoldFont6));
							flightcell02.setBorder(Rectangle.NO_BORDER);
							flightcell02.setBackgroundColor(flightColor);
							flightcell02.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell02.setPadding(8.0f);
							flightDetailTable.addCell(flightcell02);

							PdfPCell flightcell = new PdfPCell(new Paragraph("", commonBoldFont6));
						    flightcell.setBorder(Rectangle.NO_BORDER);
						    flightcell.setBackgroundColor(flightColor);
							flightcell.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell.setPadding(3.0f);
						    flightDetailTable.addCell(flightcell);

							PdfPCell flightcell1 = new PdfPCell(new Paragraph("Carrier", commonBoldFont7));
							flightcell1.setBorder(Rectangle.NO_BORDER);
							flightcell1.setBackgroundColor(flightColor);
							flightcell1.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell1.setPadding(3.0f);
							flightDetailTable.addCell(flightcell1);

							PdfPCell flightcell2 = new PdfPCell(new Paragraph("Departure", commonBoldFont7));
							flightcell2.setBorder(Rectangle.NO_BORDER);
							flightcell2.setBackgroundColor(flightColor);
							flightcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell2.setPadding(3.0f);
							flightDetailTable.addCell(flightcell2);

							PdfPCell flightcell3 = new PdfPCell(new Paragraph("Arrival", commonBoldFont7));
							flightcell3.setBorder(Rectangle.NO_BORDER);
							flightcell3.setBackgroundColor(flightColor);
							flightcell3.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell3.setPadding(3.0f);
							flightDetailTable.addCell(flightcell3);

							PdfPCell flightcell4 = new PdfPCell(new Paragraph("Other Information", commonBoldFont7));
							flightcell4.setBorder(Rectangle.NO_BORDER);
							flightcell4.setBackgroundColor(flightColor);
							flightcell4.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell4.setPadding(3.0f);
							flightDetailTable.addCell(flightcell4);

							for (int v = 0; v < onwardFlightList.size(); v++) {

								if (v == 0) {
									airLinePNR = onwardFlightList.get(v).getAirlinePnr();
									gdsPNR = onwardFlightList.get(v).getGdsPnr();
									isRefundable = onwardFlightList.get(v).getRefundable();

								}

								PdfPCell flightcell00 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell00.setBorder(Rectangle.NO_BORDER);
								flightcell00.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell00.setPadding(3.0f);
								flightcell00.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell00);

								PdfPCell flightcell10 = new PdfPCell(
										new Paragraph(onwardFlightList.get(v).getOperatingAirline(), commonBoldFont8));
								flightcell10.setBorder(Rectangle.NO_BORDER);
								flightcell10.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell10.setPadding(3.0f);
								flightcell10.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell10);

								PdfPCell flightcell11 = new PdfPCell(new Paragraph(onwardFlightList.get(v).getSource()
										+ " (" + onwardFlightList.get(v).getSourceCode() + ")", commonBoldFont8));
								flightcell11.setBorder(Rectangle.NO_BORDER);
								flightcell11.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell11.setPadding(3.0f);
								flightcell11.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell11);

								PdfPCell flightcell21 = new PdfPCell(
										new Paragraph(
												onwardFlightList.get(v).getDestination() + " ("
														+ onwardFlightList.get(v).getDestinationCode() + ")",
												commonBoldFont8));
								flightcell21.setBorder(Rectangle.NO_BORDER);
								flightcell21.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell21.setPadding(3.0f);
								flightcell21.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell21);

								PdfPCell flightcell31 = new PdfPCell(new Paragraph(
										"Check-in: " + onwardFlightList.get(v).getBaggageAllowance() + "  Cabin: 7Kg ",
										commonNormalFont7));
								flightcell31.setBorder(Rectangle.NO_BORDER);
								flightcell31.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell31.setPadding(3.0f);
								flightcell31.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell31);

								PdfPCell flightcell011 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell011.setBorder(Rectangle.NO_BORDER);
								flightcell011.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell011.setPadding(3.0f);
								flightcell011.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell011);

								PdfPCell flightcell12 = new PdfPCell(new Paragraph(onwardFlightList.get(v).getAirline()
										+ " - " + onwardFlightList.get(v).getFlightNo(), commonNormalFont7));
								flightcell12.setBorder(Rectangle.NO_BORDER);
								flightcell12.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell12.setPadding(3.0f);
								flightcell12.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell12);

								PdfPCell flightcell22 = new PdfPCell(new Paragraph(
										onwardFlightList.get(v).getSegmentOriginAirport(), commonNormalFont7));

								flightcell22.setBorder(Rectangle.NO_BORDER);
								flightcell22.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell22.setPadding(3.0f);
								flightcell22.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell22);

								PdfPCell flightcell32 = new PdfPCell(new Paragraph(
										onwardFlightList.get(v).getSegmentDestinationAirport(), commonNormalFont7));
								flightcell32.setBorder(Rectangle.NO_BORDER);
								flightcell32.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell32.setPadding(3.0f);
								flightcell32.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell32);

								PdfPCell flightcell42 = new PdfPCell(new Paragraph(
										"Cabin Class: " + onwardFlightList.get(v).getCabinClass(), commonNormalFont7));
								flightcell42.setBorder(Rectangle.NO_BORDER);
								flightcell42.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell42.setPadding(3.0f);
								flightcell42.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell42);

								PdfPCell flightcell022 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell022.setBorder(Rectangle.NO_BORDER);
								flightcell022.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell022.setPadding(3.0f);
								flightcell022.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell022);

								PdfPCell flightcell13 = new PdfPCell(new Paragraph("", commonNormalFont7));
								flightcell13.setBorder(Rectangle.NO_BORDER);
								flightcell13.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell13.setPadding(3.0f);
								flightcell13.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell13);

								PdfPCell flightcell23 = new PdfPCell(
										new Paragraph(
												onwardFlightList.get(v).getScheduledDepartureDate() + ""
														+ onwardFlightList.get(v).getScheduledDeparture(),
												commonNormalFont7));
								flightcell23.setBorder(Rectangle.NO_BORDER);
								flightcell23.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell23.setPadding(3.0f);
								flightcell23.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell23);

								PdfPCell flightcell33 = new PdfPCell(
										new Paragraph(
												onwardFlightList.get(v).getScheduledArrivalDate() + " "
														+ onwardFlightList.get(v).getScheduledArrival(),
												commonNormalFont7));
								flightcell33.setBorder(Rectangle.NO_BORDER);
								flightcell33.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell33.setPadding(3.0f);
								flightcell33.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell33);

								PdfPCell flightcell43 = new PdfPCell(
										new Paragraph("Booking Class: " + onwardFlightList.get(v).getBookingClass(),
												commonNormalFont7));
								flightcell43.setBorder(Rectangle.NO_BORDER);
								flightcell43.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell43.setPadding(3.0f);
								flightcell43.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell43);

								PdfPCell flightcell03 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell03.setBorder(Rectangle.NO_BORDER);
								flightcell03.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell03.setPadding(3.0f);
								flightcell03.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell03);

								PdfPCell flightcell14 = new PdfPCell(new Paragraph("", commonNormalFont7));
								flightcell14.setBorder(Rectangle.NO_BORDER);
								flightcell14.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell14.setPadding(3.0f);
								flightcell14.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell14);

								PdfPCell flightcell24 = new PdfPCell(new Paragraph(
										"Terminal: " + onwardFlightList.get(v).getDepartTerminal(), commonNormalFont7));
								flightcell24.setBorder(Rectangle.NO_BORDER);
								flightcell24.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell24.setPadding(3.0f);
								flightcell24.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell24);

								PdfPCell flightcell34 = new PdfPCell(new Paragraph(
										"Terminal: " + onwardFlightList.get(v).getArrivTerminal(), commonNormalFont7));
								flightcell34.setBorder(Rectangle.NO_BORDER);
								flightcell34.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell34.setPadding(3.0f);
								flightcell34.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell34);

								PdfPCell flightcell44 = new PdfPCell(new Paragraph(
										"Duration: " + onwardFlightList.get(v).getSectorDuration(), commonNormalFont7));
								flightcell44.setBorder(Rectangle.NO_BORDER);
								flightcell44.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell44.setPadding(3.0f);
								flightcell44.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell44);

							} // End of onwardFlightList loop

						} // End of onward block

						    {
							// Starts Return Block

							PdfPCell flightcell0 = new PdfPCell(
									new Paragraph("Flight Information (Return)", commonBoldFont10));
							flightcell0.setBorder(Rectangle.NO_BORDER);
							flightcell0.setColspan(3);
							flightcell0.setBackgroundColor(flightColor);
							flightcell0.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell0.setPadding(8.0f);
							flightDetailTable.addCell(flightcell0);

							// Start: Stops display
							PdfPCell flightcell01 = new PdfPCell(
									new Paragraph("Stops : " + (returnFlightList.size() - 1), commonBoldFont10));
							flightcell01.setBorder(Rectangle.NO_BORDER);
							flightcell01.setBackgroundColor(flightColor);
							flightcell01.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell01.setPadding(8.0f);
							flightDetailTable.addCell(flightcell01);

							PdfPCell flightcell02 = new PdfPCell(new Paragraph("", commonBoldFont6));
							flightcell02.setBorder(Rectangle.NO_BORDER);
							flightcell02.setBackgroundColor(flightColor);
							flightcell02.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell02.setPadding(8.0f);
							flightDetailTable.addCell(flightcell02);
							// End: Stops display

							PdfPCell flightcell = new PdfPCell(new Paragraph("", commonBoldFont6));
								    flightcell.setBorder(Rectangle.NO_BORDER);
								    flightcell.setBackgroundColor(flightColor);
							flightcell.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell.setPadding(3.0f);
								    flightDetailTable.addCell(flightcell);

							PdfPCell flightcell1 = new PdfPCell(new Paragraph("Carrier", commonBoldFont7));
							flightcell1.setBorder(Rectangle.NO_BORDER);
							flightcell1.setBackgroundColor(flightColor);
							flightcell1.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell1.setPadding(3.0f);
							flightDetailTable.addCell(flightcell1);

							PdfPCell flightcell2 = new PdfPCell(new Paragraph("Departure", commonBoldFont7));
							flightcell2.setBorder(Rectangle.NO_BORDER);
							flightcell2.setBackgroundColor(flightColor);
							flightcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell2.setPadding(3.0f);
							flightDetailTable.addCell(flightcell2);

							PdfPCell flightcell3 = new PdfPCell(new Paragraph("Arrival", commonBoldFont7));
							flightcell3.setBorder(Rectangle.NO_BORDER);
							flightcell3.setBackgroundColor(flightColor);
							flightcell3.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell3.setPadding(3.0f);
							flightDetailTable.addCell(flightcell3);

							PdfPCell flightcell4 = new PdfPCell(new Paragraph("Other Information", commonBoldFont7));
							flightcell4.setBorder(Rectangle.NO_BORDER);
							flightcell4.setBackgroundColor(flightColor);
							flightcell4.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell4.setPadding(3.0f);
							flightDetailTable.addCell(flightcell4);

							for (int v = 0; v < returnFlightList.size(); v++) {

								PdfPCell flightcell00 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell00.setBorder(Rectangle.NO_BORDER);
								flightcell00.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell00.setPadding(3.0f);
								flightcell00.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell00);

								PdfPCell flightcell10 = new PdfPCell(
										new Paragraph(returnFlightList.get(v).getOperatingAirline(), commonBoldFont8));
								flightcell10.setBorder(Rectangle.NO_BORDER);
								flightcell10.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell10.setPadding(3.0f);
								flightcell10.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell10);

								PdfPCell flightcell11 = new PdfPCell(new Paragraph(returnFlightList.get(v).getSource()
										+ " (" + returnFlightList.get(v).getSourceCode() + ")", commonBoldFont8));
								flightcell11.setBorder(Rectangle.NO_BORDER);
								flightcell11.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell11.setPadding(3.0f);
								flightcell11.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell11);

								PdfPCell flightcell21 = new PdfPCell(
										new Paragraph(
												returnFlightList.get(v).getDestination() + " ("
														+ returnFlightList.get(v).getDestinationCode() + ")",
												commonBoldFont8));
								flightcell21.setBorder(Rectangle.NO_BORDER);
								flightcell21.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell21.setPadding(3.0f);
								flightcell21.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell21);

								PdfPCell flightcell31 = new PdfPCell(new Paragraph(
										"Check-in: " + returnFlightList.get(v).getBaggageAllowance() + "  Cabin: 7Kg ",
										commonNormalFont7));
								flightcell31.setBorder(Rectangle.NO_BORDER);
								flightcell31.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell31.setPadding(3.0f);
								flightcell31.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell31);

								PdfPCell flightcell011 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell011.setBorder(Rectangle.NO_BORDER);
								flightcell011.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell011.setPadding(3.0f);
								flightcell011.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell011);

								PdfPCell flightcell12 = new PdfPCell(new Paragraph(returnFlightList.get(v).getAirline()
										+ " - " + returnFlightList.get(v).getFlightNo(), commonNormalFont7));
								flightcell12.setBorder(Rectangle.NO_BORDER);
								flightcell12.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell12.setPadding(3.0f);
								flightcell12.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell12);

								PdfPCell flightcell22 = new PdfPCell(new Paragraph(
										returnFlightList.get(v).getSegmentOriginAirport(), commonNormalFont7));
								flightcell22.setBorder(Rectangle.NO_BORDER);
								flightcell22.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell22.setPadding(3.0f);
								flightcell22.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell22);

								PdfPCell flightcell32 = new PdfPCell(new Paragraph(
										returnFlightList.get(v).getSegmentDestinationAirport(), commonNormalFont7));
								flightcell32.setBorder(Rectangle.NO_BORDER);
								flightcell32.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell32.setPadding(3.0f);
								flightcell32.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell32);

								PdfPCell flightcell42 = new PdfPCell(new Paragraph(
										"Cabin Class: " + returnFlightList.get(v).getCabinClass(), commonNormalFont7));
								flightcell42.setBorder(Rectangle.NO_BORDER);
								flightcell42.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell42.setPadding(3.0f);
								flightcell42.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell42);

								PdfPCell flightcell022 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell022.setBorder(Rectangle.NO_BORDER);
								flightcell022.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell022.setPadding(3.0f);
								flightcell022.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell022);

								PdfPCell flightcell13 = new PdfPCell(new Paragraph("", commonNormalFont7));
								flightcell13.setBorder(Rectangle.NO_BORDER);
								flightcell13.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell13.setPadding(3.0f);
								flightcell13.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell13);

								PdfPCell flightcell23 = new PdfPCell(
										new Paragraph(
												returnFlightList.get(v).getScheduledDepartureDate() + " "
														+ returnFlightList.get(v).getScheduledDeparture(),
												commonNormalFont7));
								flightcell23.setBorder(Rectangle.NO_BORDER);
								flightcell23.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell23.setPadding(3.0f);
								flightcell23.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell23);

								PdfPCell flightcell33 = new PdfPCell(
										new Paragraph(
												returnFlightList.get(v).getScheduledArrivalDate() + " "
														+ returnFlightList.get(v).getScheduledArrival(),
												commonNormalFont7));
								flightcell33.setBorder(Rectangle.NO_BORDER);
								flightcell33.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell33.setPadding(3.0f);
								flightcell33.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell33);

								PdfPCell flightcell43 = new PdfPCell(
										new Paragraph("Booking Class: " + returnFlightList.get(v).getBookingClass(),
												commonNormalFont7));
								flightcell43.setBorder(Rectangle.NO_BORDER);
								flightcell43.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell43.setPadding(3.0f);
								flightcell43.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell43);

								PdfPCell flightcell03 = new PdfPCell(new Paragraph("", commonBoldFont6));
								flightcell03.setBorder(Rectangle.NO_BORDER);
								flightcell03.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell03.setPadding(3.0f);
								flightcell03.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell03);

								PdfPCell flightcell14 = new PdfPCell(new Paragraph("", commonNormalFont7));
								flightcell14.setBorder(Rectangle.NO_BORDER);
								flightcell14.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell14.setPadding(3.0f);
								flightcell14.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell14);

								PdfPCell flightcell24 = new PdfPCell(new Paragraph(
										"Terminal: " + returnFlightList.get(v).getDepartTerminal(), commonNormalFont7));
								flightcell24.setBorder(Rectangle.NO_BORDER);
								flightcell24.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell24.setPadding(3.0f);
								flightcell24.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell24);

								PdfPCell flightcell34 = new PdfPCell(new Paragraph(
										"Terminal: " + returnFlightList.get(v).getArrivTerminal(), commonNormalFont7));
								flightcell34.setBorder(Rectangle.NO_BORDER);
								flightcell34.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell34.setPadding(3.0f);
								flightcell34.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell34);

								PdfPCell flightcell44 = new PdfPCell(new Paragraph(
										"Duration: " + returnFlightList.get(v).getSectorDuration(), commonNormalFont7));
								flightcell44.setBorder(Rectangle.NO_BORDER);
								flightcell44.setHorizontalAlignment(Element.ALIGN_LEFT);
								flightcell44.setPadding(3.0f);
								flightcell44.setBackgroundColor(flightColor);
								flightDetailTable.addCell(flightcell44);
							} // End of returnFlightList loop

						} // End of Return block

					} // End of Return

	        else {
	        	java.util.List<FlightInfo> onwardFlightList = airTicketDetailsXml.getFlightInfo();
						PdfPCell flightcell0 = new PdfPCell(new Paragraph("Flight Information", commonBoldFont10));
						flightcell0.setBorder(Rectangle.NO_BORDER);
						flightcell0.setColspan(3);
						flightcell0.setBackgroundColor(flightColor);
						flightcell0.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell0.setPadding(8.0f);
						flightDetailTable.addCell(flightcell0);

						// Start: Stops display
						PdfPCell flightcell01 = new PdfPCell(
								new Paragraph("Stops : " + (onwardFlightList.size() - 1), commonBoldFont10));
						flightcell01.setBorder(Rectangle.NO_BORDER);
						flightcell01.setBackgroundColor(flightColor);
						flightcell01.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell01.setPadding(8.0f);
						flightDetailTable.addCell(flightcell01);

						PdfPCell flightcell02 = new PdfPCell(new Paragraph("", commonBoldFont6));
						flightcell02.setBorder(Rectangle.NO_BORDER);
						flightcell02.setBackgroundColor(flightColor);
						flightcell02.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell02.setPadding(8.0f);
						flightDetailTable.addCell(flightcell02);
						// End: Stops display

						PdfPCell flightcell = new PdfPCell(new Paragraph("", commonBoldFont6));
			    flightcell.setBorder(Rectangle.NO_BORDER);
			    flightcell.setBackgroundColor(flightColor);
						flightcell.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell.setPadding(3.0f);
			    flightDetailTable.addCell(flightcell);

						PdfPCell flightcell1 = new PdfPCell(new Paragraph("Carrier", commonBoldFont7));
						flightcell1.setBorder(Rectangle.NO_BORDER);
						flightcell1.setBackgroundColor(flightColor);
						flightcell1.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell1.setPadding(3.0f);
						flightDetailTable.addCell(flightcell1);

						PdfPCell flightcell2 = new PdfPCell(new Paragraph("Departure", commonBoldFont7));
						flightcell2.setBorder(Rectangle.NO_BORDER);
						flightcell2.setBackgroundColor(flightColor);
						flightcell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell2.setPadding(3.0f);
						flightDetailTable.addCell(flightcell2);

						PdfPCell flightcell3 = new PdfPCell(new Paragraph("Arrival", commonBoldFont7));
						flightcell3.setBorder(Rectangle.NO_BORDER);
						flightcell3.setBackgroundColor(flightColor);
						flightcell3.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell3.setPadding(3.0f);
						flightDetailTable.addCell(flightcell3);

						PdfPCell flightcell4 = new PdfPCell(new Paragraph("Other Information", commonBoldFont7));
						flightcell4.setBorder(Rectangle.NO_BORDER);
						flightcell4.setBackgroundColor(flightColor);
						flightcell4.setHorizontalAlignment(Element.ALIGN_LEFT);
						flightcell4.setPadding(3.0f);
						flightDetailTable.addCell(flightcell4);

						for (int v = 0; v < onwardFlightList.size(); v++) {

							if (v == 0) {
		    			airLinePNR = onwardFlightList.get(v).getAirlinePnr();
		    			gdsPNR = onwardFlightList.get(v).getGdsPnr();
								isRefundable = onwardFlightList.get(v).getRefundable();
		    		}
							PdfPCell flightcell00 = new PdfPCell(new Paragraph("", commonBoldFont6));
							flightcell00.setBorder(Rectangle.NO_BORDER);
							flightcell00.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell00.setPadding(3.0f);
							flightcell00.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell00);

							PdfPCell flightcell10 = new PdfPCell(
									new Paragraph(onwardFlightList.get(v).getOperatingAirline(), commonBoldFont8));
							flightcell10.setBorder(Rectangle.NO_BORDER);
							flightcell10.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell10.setPadding(3.0f);
							flightcell10.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell10);

							PdfPCell flightcell11 = new PdfPCell(new Paragraph(onwardFlightList.get(v).getSource()
									+ " (" + onwardFlightList.get(v).getSourceCode() + ")", commonBoldFont8));
							flightcell11.setBorder(Rectangle.NO_BORDER);
							flightcell11.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell11.setPadding(3.0f);
							flightcell11.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell11);

							PdfPCell flightcell21 = new PdfPCell(new Paragraph(onwardFlightList.get(v).getDestination()
									+ " (" + onwardFlightList.get(v).getDestinationCode() + ")", commonBoldFont8));
							flightcell21.setBorder(Rectangle.NO_BORDER);
							flightcell21.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell21.setPadding(3.0f);
							flightcell21.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell21);

							PdfPCell flightcell31 = new PdfPCell(new Paragraph(
									"Check-in: " + onwardFlightList.get(v).getBaggageAllowance() + "  Cabin: 7Kg ",
									commonNormalFont7));
							flightcell31.setBorder(Rectangle.NO_BORDER);
							flightcell31.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell31.setPadding(3.0f);
							flightcell31.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell31);

							PdfPCell flightcell011 = new PdfPCell(new Paragraph("", commonBoldFont6));
							flightcell011.setBorder(Rectangle.NO_BORDER);
							flightcell011.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell011.setPadding(3.0f);
							flightcell011.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell011);

							PdfPCell flightcell12 = new PdfPCell(new Paragraph(onwardFlightList.get(v).getAirline()
									+ " - " + onwardFlightList.get(v).getFlightNo(), commonNormalFont7));
							flightcell12.setBorder(Rectangle.NO_BORDER);
							flightcell12.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell12.setPadding(3.0f);
							flightcell12.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell12);

							PdfPCell flightcell22 = new PdfPCell(new Paragraph(
									onwardFlightList.get(v).getSegmentOriginAirport(), commonNormalFont7));
							flightcell22.setBorder(Rectangle.NO_BORDER);
							flightcell22.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell22.setPadding(3.0f);
							flightcell22.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell22);

							PdfPCell flightcell32 = new PdfPCell(new Paragraph(
									onwardFlightList.get(v).getSegmentDestinationAirport(), commonNormalFont7));
							flightcell32.setBorder(Rectangle.NO_BORDER);
							flightcell32.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell32.setPadding(3.0f);
							flightcell32.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell32);

							PdfPCell flightcell42 = new PdfPCell(new Paragraph(
									"Cabin Class: " + onwardFlightList.get(v).getCabinClass(), commonNormalFont7));
							flightcell42.setBorder(Rectangle.NO_BORDER);
							flightcell42.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell42.setPadding(3.0f);
							flightcell42.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell42);

							PdfPCell flightcell023 = new PdfPCell(new Paragraph("", commonBoldFont6));
							flightcell023.setBorder(Rectangle.NO_BORDER);
							flightcell023.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell023.setPadding(3.0f);
							flightcell023.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell023);

							PdfPCell flightcell13 = new PdfPCell(new Paragraph("", commonNormalFont7));
							flightcell13.setBorder(Rectangle.NO_BORDER);
							flightcell13.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell13.setPadding(3.0f);
							flightcell13.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell13);

							PdfPCell flightcell23 = new PdfPCell(
									new Paragraph(
											onwardFlightList.get(v).getScheduledDepartureDate() + " "
													+ onwardFlightList.get(v).getScheduledDeparture(),
											commonNormalFont7));
							flightcell23.setBorder(Rectangle.NO_BORDER);
							flightcell23.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell23.setPadding(3.0f);
							flightcell23.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell23);

							PdfPCell flightcell33 = new PdfPCell(
									new Paragraph(
											onwardFlightList.get(v).getScheduledArrivalDate() + " "
													+ onwardFlightList.get(v).getScheduledArrival(),
											commonNormalFont7));
							flightcell33.setBorder(Rectangle.NO_BORDER);
							flightcell33.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell33.setPadding(3.0f);
							flightcell33.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell33);

							PdfPCell flightcell43 = new PdfPCell(new Paragraph(
									"Booking Class: " + onwardFlightList.get(v).getBookingClass(), commonNormalFont7));
							flightcell43.setBorder(Rectangle.NO_BORDER);
							flightcell43.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell43.setPadding(3.0f);
							flightcell43.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell43);

							PdfPCell flightcell03 = new PdfPCell(new Paragraph("", commonBoldFont6));
							flightcell03.setBorder(Rectangle.NO_BORDER);
							flightcell03.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell03.setPadding(3.0f);
							flightcell03.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell03);

							PdfPCell flightcell14 = new PdfPCell(new Paragraph("", commonNormalFont7));
							flightcell14.setBorder(Rectangle.NO_BORDER);
							flightcell14.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell14.setPadding(3.0f);
							flightcell14.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell14);

							PdfPCell flightcell24 = new PdfPCell(new Paragraph(
									"Terminal: " + onwardFlightList.get(v).getDepartTerminal(), commonNormalFont7));
							flightcell24.setBorder(Rectangle.NO_BORDER);
							flightcell24.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell24.setPadding(3.0f);
							flightcell24.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell24);

							PdfPCell flightcell34 = new PdfPCell(new Paragraph(
									"Terminal: " + onwardFlightList.get(v).getArrivTerminal(), commonNormalFont7));
							flightcell34.setBorder(Rectangle.NO_BORDER);
							flightcell34.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell34.setPadding(3.0f);
							flightcell34.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell34);

							PdfPCell flightcell44 = new PdfPCell(new Paragraph(
									"Duration: " + onwardFlightList.get(v).getSectorDuration(), commonNormalFont7));
							flightcell44.setBorder(Rectangle.NO_BORDER);
							flightcell44.setHorizontalAlignment(Element.ALIGN_LEFT);
							flightcell44.setPadding(3.0f);
							flightcell44.setBackgroundColor(flightColor);
							flightDetailTable.addCell(flightcell44);
						} // End of onwardFlightList loop
		    }

	        maintable.addCell(flightDetailTable);

		    PdfPTable passengerContactDetailTable = new PdfPTable(4);

		    passengerContactDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

					float[] passengerContactColumnWidths = new float[] { 25f, 14f, 25f, 13f };

		    passengerContactDetailTable.setWidths(passengerContactColumnWidths);

		java.util.List<PassengerInfo> passengerContactElementList = airTicketDetailsXml.getPassengerInfo();

					PdfPCell paxCell001 = new PdfPCell(new Paragraph("Passenger Contact Details", commonBoldFont10));
					paxCell001.setBorder(Rectangle.NO_BORDER);
					paxCell001.setColspan(4);
					paxCell001.setBackgroundColor(flightColor);
					paxCell001.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell001.setPadding(8.0f);
					passengerContactDetailTable.addCell(paxCell001);

					PdfPCell paxCell01 = new PdfPCell(new Paragraph("Name", commonBoldFont7));
					paxCell01.setBorder(Rectangle.NO_BORDER);
					paxCell01.setBackgroundColor(flightColor);
					paxCell01.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell01.setPadding(5.0f);
					passengerContactDetailTable.addCell(paxCell01);

					PdfPCell paxCell02 = new PdfPCell(new Paragraph("Mobile Number", commonBoldFont7));
					paxCell02.setBorder(Rectangle.NO_BORDER);
					paxCell02.setBackgroundColor(flightColor);
					paxCell02.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell02.setPadding(5.0f);
					passengerContactDetailTable.addCell(paxCell02);

					PdfPCell paxCell03 = new PdfPCell(new Paragraph("Email", commonBoldFont7));
					paxCell03.setBorder(Rectangle.NO_BORDER);
					paxCell03.setBackgroundColor(flightColor);
					paxCell03.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell03.setPadding(5.0f);
					passengerContactDetailTable.addCell(paxCell03);

					PdfPCell paxCell04 = new PdfPCell(new Paragraph("Endorsements", commonBoldFont7));
					paxCell04.setBorder(Rectangle.NO_BORDER);
					paxCell04.setBackgroundColor(flightColor);
					paxCell04.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell04.setPadding(5.0f);
					passengerContactDetailTable.addCell(paxCell04);

					// Element paxContactElement = null;

					for (int v = 0; v < passengerContactElementList.size(); v++) {

						if (passengerContactElementList.get(v).getLeadPassanger() == 0) {
							PdfPCell paxCell11 = new PdfPCell(
									new Paragraph(
											passengerContactElementList.get(v).getTitle() + ". "
													+ passengerContactElementList.get(v).getFirstName() + " "
													+ passengerContactElementList.get(v).getMiddleName() + " "
													+ passengerContactElementList.get(v).getLastName(),
											commonBoldFont8));
							paxCell11.setBorder(Rectangle.NO_BORDER);
							paxCell11.setHorizontalAlignment(Element.ALIGN_LEFT);
							paxCell11.setPadding(5.0f);
							paxCell11.setBackgroundColor(flightColor);
							passengerContactDetailTable.addCell(paxCell11);

							PdfPCell paxCell12 = new PdfPCell(
									new Paragraph(passengerContactElementList.get(v).getMobileNo(), commonBoldFont8));
							paxCell12.setBorder(Rectangle.NO_BORDER);
							paxCell12.setHorizontalAlignment(Element.ALIGN_LEFT);
							paxCell12.setPadding(5.0f);
							paxCell12.setBackgroundColor(flightColor);
							passengerContactDetailTable.addCell(paxCell12);

							PdfPCell paxCell13 = new PdfPCell(
									new Paragraph(passengerContactElementList.get(v).getEmailId(), commonBoldFont8));
							paxCell13.setBorder(Rectangle.NO_BORDER);
							paxCell13.setHorizontalAlignment(Element.ALIGN_LEFT);
							paxCell13.setPadding(5.0f);
							paxCell13.setBackgroundColor(flightColor);
							passengerContactDetailTable.addCell(paxCell13);

							PdfPCell paxCell14 = new PdfPCell(new Paragraph("-", commonBoldFont8));
							paxCell14.setBorder(Rectangle.NO_BORDER);
							paxCell14.setHorizontalAlignment(Element.ALIGN_LEFT);
							paxCell14.setPadding(5.0f);
							paxCell14.setBackgroundColor(flightColor);
							passengerContactDetailTable.addCell(paxCell14);

		    	}
					} // End of Passenger loop

		    maintable.addCell(passengerContactDetailTable);

					// End: Passenger contact detail

		    PdfPTable passengerDetailTable = new PdfPTable(5);

		    passengerDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

					float[] passengerColumnWidths = new float[] { 18f, 14f, 17f, 10f, 13f };

		    passengerDetailTable.setWidths(passengerColumnWidths);

		    java.util.List<PassengerInfo> passengerElementList = airTicketDetailsXml.getPassengerInfo();

					PdfPCell paxCell0 = new PdfPCell(new Paragraph("Traveller Details", commonBoldFont10));
					paxCell0.setBorder(Rectangle.NO_BORDER);
					paxCell0.setColspan(7);
					paxCell0.setBackgroundColor(flightColor);
					paxCell0.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell0.setPadding(8.0f);
					passengerDetailTable.addCell(paxCell0);

					PdfPCell paxCell = new PdfPCell(new Paragraph("Pax Name", commonBoldFont7));
		    paxCell.setBorder(Rectangle.NO_BORDER);
		    paxCell.setBackgroundColor(flightColor);
					paxCell.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell.setPadding(5.0f);
		    passengerDetailTable.addCell(paxCell);

					PdfPCell paxCell1 = new PdfPCell(new Paragraph("Traveller Type", commonBoldFont7));
					paxCell1.setBorder(Rectangle.NO_BORDER);
					paxCell1.setBackgroundColor(flightColor);
					paxCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell1.setPadding(5.0f);
					passengerDetailTable.addCell(paxCell1);

					PdfPCell paxCell4 = new PdfPCell(new Paragraph("Ticket Number", commonBoldFont7));
					paxCell4.setBorder(Rectangle.NO_BORDER);
					paxCell4.setBackgroundColor(flightColor);
					paxCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell4.setPadding(5.0f);
					passengerDetailTable.addCell(paxCell4);

					PdfPCell paxCell5 = new PdfPCell(new Paragraph("Airline PNR", commonBoldFont7));
					paxCell5.setBorder(Rectangle.NO_BORDER);
					paxCell5.setBackgroundColor(flightColor);
					paxCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell5.setPadding(5.0f);
					passengerDetailTable.addCell(paxCell5);

					PdfPCell paxCell6 = new PdfPCell(new Paragraph("GDS PNR", commonBoldFont7));
					paxCell6.setBorder(Rectangle.NO_BORDER);
					paxCell6.setBackgroundColor(flightColor);
					paxCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
					paxCell6.setPadding(5.0f);
					passengerDetailTable.addCell(paxCell6);

					for (int v = 0; v < passengerElementList.size(); v++) {

						PdfPCell paxCell00 = new PdfPCell(new Paragraph(passengerElementList.get(v).getTitle() + ". "
								+ passengerElementList.get(v).getFirstName() + " "
								+ passengerElementList.get(v).getMiddleName() + " "
								+ passengerElementList.get(v).getLastName(), commonBoldFont8));
						paxCell00.setBorder(Rectangle.NO_BORDER);
						paxCell00.setHorizontalAlignment(Element.ALIGN_LEFT);
						paxCell00.setPadding(5.0f);
						paxCell00.setBackgroundColor(flightColor);
						passengerDetailTable.addCell(paxCell00);

						PdfPCell paxCell10 = new PdfPCell(
								new Paragraph(passengerElementList.get(v).getPassCategory(), commonBoldFont8));
						paxCell10.setBorder(Rectangle.NO_BORDER);
						paxCell10.setHorizontalAlignment(Element.ALIGN_LEFT);
						paxCell10.setPadding(5.0f);
						paxCell10.setBackgroundColor(flightColor);
						passengerDetailTable.addCell(paxCell10);

						PdfPCell paxCell40 = new PdfPCell(
								new Paragraph(passengerElementList.get(v).getTicketNumber(), commonBoldFont8));
						paxCell40.setBorder(Rectangle.NO_BORDER);
						paxCell40.setHorizontalAlignment(Element.ALIGN_LEFT);
						paxCell40.setPadding(5.0f);
						paxCell40.setBackgroundColor(flightColor);
						passengerDetailTable.addCell(paxCell40);

						PdfPCell paxCell50 = new PdfPCell(new Paragraph(airLinePNR, commonBoldFont8));
						paxCell50.setBorder(Rectangle.NO_BORDER);
						paxCell50.setHorizontalAlignment(Element.ALIGN_LEFT);
						paxCell50.setPadding(5.0f);
						paxCell50.setBackgroundColor(flightColor);
						passengerDetailTable.addCell(paxCell50);

						PdfPCell paxCell60 = new PdfPCell(new Paragraph(gdsPNR, commonBoldFont8));
						paxCell60.setBorder(Rectangle.NO_BORDER);
						paxCell60.setHorizontalAlignment(Element.ALIGN_LEFT);
						paxCell60.setPadding(5.0f);
						paxCell60.setBackgroundColor(flightColor);
						passengerDetailTable.addCell(paxCell60);

					} // End of Passenger loop

		    maintable.addCell(passengerDetailTable);

		    PdfPTable fareDetailTable = new PdfPTable(8);

		    fareDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

					float[] fareColumnWidths = new float[] { 10f, 30f, 9f, 8f, 8f, 10f, 10f, 15f };

		    fareDetailTable.setWidths(fareColumnWidths);

					float fuelCharge = 0;
					float totalInvoice = 0;
					float gstTax = 0;
					float otherTax = 0;
		    InvoiceInfoModel invoiceElement = airTicketDetailsXml.getInvoiceInfoModel();

					try {

						fuelCharge = invoiceElement.getFuleCharge();
						gstTax = invoiceElement.getGstTax();
						otherTax = invoiceElement.getOtherTax();
						totalInvoice = invoiceElement.getTotalInvoice();

					} catch (NullPointerException npe) {

						DODLog.printStackTrace(npe, AirEmailTicketService.class, LogConstant.EMAIL_PDF_LOG_FILE);
		    }

					PdfPCell fareCell0 = new PdfPCell(new Paragraph("Billing Details", commonBoldFont10));
					fareCell0.setBorder(Rectangle.NO_BORDER);
					fareCell0.setColspan(8);
					fareCell0.setBackgroundColor(flightColor);
					fareCell0.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell0.setPadding(5.0f);
					fareDetailTable.addCell(fareCell0);

					PdfPCell fareCell1 = new PdfPCell(new Paragraph("Base Fare : ", commonNormalFont7));
					fareCell1.setColspan(7);
					fareCell1.setBorder(Rectangle.NO_BORDER);
					fareCell1.setBackgroundColor(flightColor);
					fareCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell1.setPadding(5.0f);
					fareDetailTable.addCell(fareCell1);

					PdfPCell fareCell2 = new PdfPCell(
							new Paragraph(invoiceElement.getBaseFare() + "", commonNormalFont7));
					fareCell2.setBorder(Rectangle.NO_BORDER);
					fareCell2.setBackgroundColor(flightColor);
					fareCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell2.setPadding(5.0f);
					fareDetailTable.addCell(fareCell2);

					PdfPCell fareCell5 = new PdfPCell(new Paragraph("Fuel Charges : ", commonNormalFont7));
					fareCell5.setColspan(7);
					fareCell5.setBorder(Rectangle.NO_BORDER);
					fareCell5.setBackgroundColor(flightColor);
					fareCell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell5.setPadding(5.0f);
					fareDetailTable.addCell(fareCell5);

					PdfPCell fareCell6 = new PdfPCell(new Paragraph(fuelCharge + "", commonNormalFont7));
					fareCell6.setBorder(Rectangle.NO_BORDER);
					fareCell6.setBackgroundColor(flightColor);
					fareCell6.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell6.setPadding(5.0f);
					fareDetailTable.addCell(fareCell6);

					PdfPCell fareCell13 = new PdfPCell(new Paragraph("GST : ", commonNormalFont7));
					fareCell13.setColspan(7);
					fareCell13.setBorder(Rectangle.NO_BORDER);
					fareCell13.setBackgroundColor(flightColor);
					fareCell13.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell13.setPadding(5.0f);
					fareDetailTable.addCell(fareCell13);

					PdfPCell fareCell14 = new PdfPCell(new Paragraph(gstTax + "", commonNormalFont7));
					fareCell14.setBorder(Rectangle.NO_BORDER);
					fareCell14.setBackgroundColor(flightColor);
					fareCell14.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell14.setPadding(5.0f);
					fareDetailTable.addCell(fareCell14);

					PdfPCell fareCell15 = new PdfPCell(new Paragraph("Tax : ", commonNormalFont7));
					fareCell15.setColspan(7);
					fareCell15.setBorder(Rectangle.NO_BORDER);
					fareCell15.setBackgroundColor(flightColor);
					fareCell15.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell15.setPadding(5.0f);
					fareDetailTable.addCell(fareCell15);

					PdfPCell fareCell16 = new PdfPCell(new Paragraph(invoiceElement.getTax() + "", commonNormalFont7));
					fareCell16.setBorder(Rectangle.NO_BORDER);
					fareCell16.setBackgroundColor(flightColor);
					fareCell16.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell16.setPadding(5.0f);
					fareDetailTable.addCell(fareCell16);

					PdfPCell fareCell17 = new PdfPCell(new Paragraph("Other Tax : ", commonNormalFont7));
					fareCell17.setColspan(7);
					fareCell17.setBorder(Rectangle.NO_BORDER);
					fareCell17.setBackgroundColor(flightColor);
					fareCell17.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell17.setPadding(5.0f);
					fareDetailTable.addCell(fareCell17);

					PdfPCell fareCell18 = new PdfPCell(new Paragraph(otherTax + "", commonNormalFont7));
					fareCell18.setBorder(Rectangle.NO_BORDER);
					fareCell18.setBackgroundColor(flightColor);
					fareCell18.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell18.setPadding(5.0f);
					fareDetailTable.addCell(fareCell18);

					PdfPCell fareCell19 = new PdfPCell(new Paragraph("Grand Total : ", commonBoldFont7Red));
					fareCell19.setColspan(7);
					fareCell19.setBorder(Rectangle.NO_BORDER);
					fareCell19.setBackgroundColor(flightColor);
					fareCell19.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell19.setPadding(5.0f);
					fareDetailTable.addCell(fareCell19);

					PdfPCell fareCell20 = new PdfPCell(new Paragraph(totalInvoice + "", commonBoldFont7Red));
					fareCell20.setBorder(Rectangle.NO_BORDER);
					fareCell20.setBackgroundColor(flightColor);
					fareCell20.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell20.setPadding(5.0f);
					fareDetailTable.addCell(fareCell20);

					PdfPCell fareCell21 = new PdfPCell(new Paragraph("GST No. :", commonNormalFont7));
					fareCell21.setColspan(1);
					fareCell21.setBorder(Rectangle.NO_BORDER);
					fareCell21.setBackgroundColor(flightColor);
					fareCell21.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell21.setPadding(5.0f);
					fareDetailTable.addCell(fareCell21);

					PdfPCell fareCell22 = new PdfPCell(new Paragraph(airLineGST, commonBoldFont7Red));
					fareCell22.setColspan(4);
					fareCell22.setBorder(Rectangle.NO_BORDER);
					fareCell22.setBackgroundColor(flightColor);
					fareCell22.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell22.setPadding(5.0f);
					fareDetailTable.addCell(fareCell22);

					PdfPCell fareCell23 = new PdfPCell(new Paragraph("Fare Type :", commonNormalFont7));
					fareCell23.setColspan(2);
					fareCell23.setBorder(Rectangle.NO_BORDER);
					fareCell23.setBackgroundColor(flightColor);
					fareCell23.setHorizontalAlignment(Element.ALIGN_RIGHT);
					fareCell23.setPadding(5.0f);
					fareDetailTable.addCell(fareCell23);

					PdfPCell fareCell24 = new PdfPCell(new Paragraph(isRefundable + " *", commonBoldFont7Red));
					fareCell24.setColspan(1);
					fareCell24.setBorder(Rectangle.NO_BORDER);
					fareCell24.setBackgroundColor(flightColor);
					fareCell24.setHorizontalAlignment(Element.ALIGN_LEFT);
					fareCell24.setPadding(5.0f);
					fareDetailTable.addCell(fareCell24);

		    maintable.addCell(fareDetailTable);

					// Start: Passenger Refund Details

					java.util.List<PaxCanInvoiceInfo> passengerRefundElementList = airTicketDetailsXml
							.getPaxCanInvoiceInfo();

					if (null != passengerRefundElementList && !passengerRefundElementList.isEmpty()) {

			    PdfPTable passengerRefundDetailTable = new PdfPTable(8);

			    passengerRefundDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
						float[] passengerRefundColumnWidths = new float[] { 13f, 13f, 13f, 13f, 13f, 13f, 13f, 13f };
			    passengerRefundDetailTable.setWidths(passengerRefundColumnWidths);

						PdfPCell refCell001 = new PdfPCell(new Paragraph("Refund Details", commonBoldFont10));
						refCell001.setBorder(Rectangle.NO_BORDER);
						refCell001.setColspan(9);
						refCell001.setBackgroundColor(flightColor);
						refCell001.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell001.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell001);

						PdfPCell refCell01 = new PdfPCell(new Paragraph("Traveler Name", commonBoldFont7));
						refCell01.setBorder(Rectangle.NO_BORDER);
						refCell01.setBackgroundColor(flightColor);
						refCell01.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell01.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell01);

						PdfPCell refCell02 = new PdfPCell(new Paragraph("Pax ID", commonBoldFont7));
						refCell02.setBorder(Rectangle.NO_BORDER);
						refCell02.setBackgroundColor(flightColor);
						refCell02.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell02.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell02);

						PdfPCell refCell03 = new PdfPCell(new Paragraph("Cancelled Txn ID", commonBoldFont7));
						refCell03.setBorder(Rectangle.NO_BORDER);
						refCell03.setBackgroundColor(flightColor);
						refCell03.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell03.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell03);

						PdfPCell refCell04 = new PdfPCell(new Paragraph("Refund Status", commonBoldFont7));
						refCell04.setBorder(Rectangle.NO_BORDER);
						refCell04.setBackgroundColor(flightColor);
						refCell04.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell04.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell04);

						PdfPCell refCell05 = new PdfPCell(new Paragraph("Refund Invoice ID", commonBoldFont7));
						refCell05.setBorder(Rectangle.NO_BORDER);
						refCell05.setBackgroundColor(flightColor);
						refCell05.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell05.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell05);

						PdfPCell refCell06 = new PdfPCell(new Paragraph("Refunded Date", commonBoldFont7));
						refCell06.setBorder(Rectangle.NO_BORDER);
						refCell06.setBackgroundColor(flightColor);
						refCell06.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell06.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell06);

						PdfPCell refCell07 = new PdfPCell(new Paragraph("Refunded Amount", commonBoldFont7));
						refCell07.setBorder(Rectangle.NO_BORDER);
						refCell07.setBackgroundColor(flightColor);
						refCell07.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell07.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell07);

						PdfPCell refCell08 = new PdfPCell(new Paragraph("Cancellation Date", commonBoldFont7));
						refCell08.setBorder(Rectangle.NO_BORDER);
						refCell08.setBackgroundColor(flightColor);
						refCell08.setHorizontalAlignment(Element.ALIGN_LEFT);
						refCell08.setPadding(5.0f);
						passengerRefundDetailTable.addCell(refCell08);

						for (int v = 0; v < passengerRefundElementList.size(); v++) {

							PdfPCell refCell10 = new PdfPCell(new Paragraph(
									passengerRefundElementList.get(v).getPassangerFullName(), commonNormalFont7));
							refCell10.setBorder(Rectangle.NO_BORDER);
							refCell10.setBackgroundColor(flightColor);
							refCell10.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell10.setPadding(5.0f);
							passengerRefundDetailTable.addCell(refCell10);

							PdfPCell refCell101 = new PdfPCell(new Paragraph(
									passengerRefundElementList.get(v).getOnwardPaxId(), commonNormalFont7));
							refCell101.setBorder(Rectangle.NO_BORDER);
							refCell101.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell101.setPadding(5.0f);
							refCell101.setBackgroundColor(flightColor);
							passengerRefundDetailTable.addCell(refCell101);

							PdfPCell refCell102 = new PdfPCell(new Paragraph(
									passengerRefundElementList.get(v).getCancellationTaxId(), commonNormalFont7));
							refCell102.setBorder(Rectangle.NO_BORDER);
							refCell102.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell102.setPadding(5.0f);
							refCell102.setBackgroundColor(flightColor);
							passengerRefundDetailTable.addCell(refCell102);

							PdfPCell refCell103 = new PdfPCell(new Paragraph(
									passengerRefundElementList.get(v).getInvoiceStatus(), "", commonNormalFont7));
							refCell103.setBorder(Rectangle.NO_BORDER);
							refCell103.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell103.setPadding(5.0f);
							refCell103.setBackgroundColor(flightColor);
							passengerRefundDetailTable.addCell(refCell103);

							PdfPCell refCell104 = new PdfPCell(new Paragraph(
									passengerRefundElementList.get(v).getCreditNoteNo(), commonNormalFont7));
							refCell104.setBorder(Rectangle.NO_BORDER);
							refCell104.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell104.setPadding(5.0f);
							refCell104.setBackgroundColor(flightColor);
							passengerRefundDetailTable.addCell(refCell104);

							PdfPCell refCell105 = new PdfPCell(new Paragraph(
									passengerRefundElementList.get(v).getCreditNoteDate(), commonNormalFont7));
							refCell105.setBorder(Rectangle.NO_BORDER);
							refCell105.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell105.setPadding(5.0f);
							refCell105.setBackgroundColor(flightColor);
							passengerRefundDetailTable.addCell(refCell105);

							PdfPCell refCell106 = new PdfPCell(new Paragraph(String.valueOf(
									passengerRefundElementList.get(v).getCalTotalRefund()), commonNormalFont7));
							refCell106.setBorder(Rectangle.NO_BORDER);
							refCell106.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell106.setPadding(5.0f);
							refCell106.setBackgroundColor(flightColor);
							passengerRefundDetailTable.addCell(refCell106);

							PdfPCell refCell107 = new PdfPCell(new Paragraph(
									passengerRefundElementList.get(v).getCancellationDate(), commonNormalFont7));
							refCell107.setBorder(Rectangle.NO_BORDER);
							refCell107.setHorizontalAlignment(Element.ALIGN_LEFT);
							refCell107.setPadding(5.0f);
							refCell107.setBackgroundColor(flightColor);
							passengerRefundDetailTable.addCell(refCell107);

						}
			    maintable.addCell(passengerRefundDetailTable);

			 }
	    }

				// End: Passenger Refund detail

		    PdfPTable blankTable = new PdfPTable(1);
		    blankTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

				PdfPCell blankTableCell0 = new PdfPCell(new Paragraph("DTS Air Helpline", commonBoldFont7));
				blankTableCell0.setBorder(Rectangle.NO_BORDER);
				blankTableCell0.setBackgroundColor(flightColor);
				blankTableCell0.setHorizontalAlignment(Element.ALIGN_LEFT);
				blankTableCell0.setPadding(3.0f);
				blankTable.addCell(blankTableCell0);

				PdfPCell blankTableCell01 = new PdfPCell(new Paragraph(
						"For Queries (Timings 09:30 AM to 06:00 PM) Monday to Saturday", commonNormalFont7));
				blankTableCell01.setBorder(Rectangle.NO_BORDER);
				blankTableCell01.setBackgroundColor(flightColor);
				blankTableCell01.setHorizontalAlignment(Element.ALIGN_LEFT);
				blankTableCell01.setPadding(3.0f);
				blankTable.addCell(blankTableCell01);

				PdfPCell blankTableCell02 = new PdfPCell(new Paragraph(
						"Call us on: 011-26700300 or E-mail: air.helpdesk@hub.nic.in", commonNormalFont7));
				blankTableCell02.setBorder(Rectangle.NO_BORDER);
				blankTableCell02.setBackgroundColor(flightColor);
				blankTableCell02.setHorizontalAlignment(Element.ALIGN_LEFT);
				blankTableCell02.setPadding(3.0f);
				blankTable.addCell(blankTableCell02);

				PdfPCell blankTableCell1 = new PdfPCell(
						new Paragraph("Balmer Lawrie Air Helpline: (Timing: 24x7)", commonBoldFont7));
				blankTableCell1.setBorder(Rectangle.NO_BORDER);
				blankTableCell1.setBackgroundColor(flightColor);
				blankTableCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				blankTableCell1.setPadding(3.0f);
				blankTable.addCell(blankTableCell1);

				PdfPCell blankTableCell11 = new PdfPCell(
						new Paragraph("Call on: 0124-4603500 / 0124-6282500", commonNormalFont7));
				blankTableCell11.setBorder(Rectangle.NO_BORDER);
				blankTableCell11.setBackgroundColor(flightColor);
				blankTableCell11.setHorizontalAlignment(Element.ALIGN_LEFT);
				blankTableCell11.setPadding(3.0f);
				blankTable.addCell(blankTableCell11);
		    maintable.addCell(blankTable);

				PdfPCell blankTableCell12 = new PdfPCell(
						new Paragraph("* (after deduction of applicable cancellation charges)", commonBoldFont7Red));
				blankTableCell12.setBorder(Rectangle.NO_BORDER);
				blankTableCell12.setBackgroundColor(flightColor);
				blankTableCell12.setHorizontalAlignment(Element.ALIGN_LEFT);
				blankTableCell12.setPadding(3.0f);
				maintable.addCell(blankTableCell12);

		    PdfPTable rulesInforTable = new PdfPTable(1);
		    rulesInforTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

				PdfPCell ruleHeaderCell0 = new PdfPCell(new Paragraph("Rules and Conditions", commonBoldFont10));
				ruleHeaderCell0.setBorder(Rectangle.NO_BORDER);
				ruleHeaderCell0.setBackgroundColor(flightColor);
				ruleHeaderCell0.setHorizontalAlignment(Element.ALIGN_LEFT);
				ruleHeaderCell0.setPadding(5.0f);
				rulesInforTable.addCell(ruleHeaderCell0);

		    PdfPCell listCell = new PdfPCell();
		    listCell.setBorder(Rectangle.NO_BORDER);

				List orderedList = new List(List.ORDERED, 20f);
				orderedList.add(new ListItem(
						"DTS Air Helpline number 011-26700300 is available from 9:30 to 6:00 (Mon – Sat). You may also email your concern at air.helpdesk@hub.nic.in",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"Report at the designated Airport terminal as per the recommended reporting time of the Airline. Airline check-in counters closing time varies from airline to airline. Certain Airports have enhanced reporting time due to high security checks.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"Flight Timings are subject to change without prior notice. Please re-check with the Airline prior to departure to avoid any inconvenience.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"For queries such as Departure/Arrival status of Flights, Flight Delays, Terminal Information, Baggage Information, Cancellation Status etc, users may call the respective service provider Customer Care or the Airline’s helpline/counter keeping in view the information printed on the ticket. Users may provide the Ticket Number & Airline/GDS PNR for identification.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"Users should cancel tickets Online through DTS unless it is not feasible to do so. Online cancellation updates the required information in DTS immediately for any further action such as rebooking/refunds.However if the status is not updated within 10 minutes you may kindly call the helpline number to get it updated. Ticket for the same day and sector can only be booked after the status is updated as cancelled.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"In case sufficient time is not remaining for online cancellation of Air Ticket, user may call the Customer Care of Service Provider. Tickets can be cancelled by contacting the Customer Care of Service Providers and not through the Airline Website. Service Provider numbers are as follows:",
						commonNormalFont7));

            List sublist = new List(false, false, 10);
				sublist.setListSymbol(new Chunk("", commonBoldFont6));
				sublist.add(new ListItem("Balmer Lawrie & Co. Ltd.:   0124-4603500/ 0124-6282500", commonBoldFont8));
				sublist.add(new ListItem("Ashoka Travels and Tours:   7304542848 and 7738687401 and 011-26700300",
						commonBoldFont8));
            orderedList.add(sublist);

				orderedList.add(new ListItem(
						"In case user is not able to contact service provider he may cancel the ticket directly through airlines also on the following airlinenumbers:",
						commonNormalFont7));

            List custlist = new List(true, false, 10);
				custlist.setListSymbol(new Chunk("", commonBoldFont6));
				custlist.add(new ListItem("Air India       :   011-69329333",commonBoldFont8));
	            custlist.add(new ListItem("Akasa       :   09606112131",commonBoldFont8));
	            custlist.add(new ListItem("Indigo          :   0124-6173838 / 0124-4973838",commonBoldFont8));
	            custlist.add(new ListItem("Star Air          :   022-50799555",commonBoldFont8));
	            custlist.add(new ListItem("Spice Jet     :   0124-4983410",commonBoldFont8));
	            custlist.add(new ListItem("Alliance Air     : 044 4255 4255/044 3511 3511",commonBoldFont8));
	            custlist.add(new ListItem("Air India Exp     : 080 4666 2222",commonBoldFont8));
            orderedList.add(custlist);

				List custlist1 = new List(false, false, 10);
				custlist1.setListSymbol(new Chunk("", commonBoldFont6));
				custlist1.add(
						new ListItem("*Users may also confirm the above numbers from respective websites of Airlines.",
								commonBoldFont8));
				orderedList.add(custlist1);

				orderedList.add(new ListItem(
						"In case of Offline cancellation DTS Air helpline may be intimated via e-mail to update the status of the cancelled Ticket in DTS for processing of refunds and records kept your end for future references.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"Different Cancellation Timings/Policies are applicable based on the Booking Class (S, T, H, etc) of the Ticket. Users may contact the Airlines or the Service Providers for information regarding cancellation policies. Information regarding Booking Class is available on the final Booking Page and the DTS Air Ticket Printouts.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"If a passenger fails to cancel the travel booking and do not report for travel on time, the Airline will consider such passenger as No Show and refund of fare is not applicable in such cases. The cost is to be borne by the passenger.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"All refunds will be automatically made to Defence Travel System upon cancellation of Air Ticket. Refunds will be processed as per the cancellation policies of the airlines.",
						commonNormalFont7));
				orderedList.add(new ListItem(
						"Seat/Meal requests may/may not be realized depending on the participating airline.",
						commonNormalFont7));

				/* CODE BLOCK TO ADD INTERNATIONAL TRAVEL CONDITION */
				if (domInt.get() == 1) {
					orderedList.add(new ListItem(
							"Passport: Valid passport with minimum expiry date as per the country of travel(06 Months).",
							commonNormalFont7));
					orderedList.add(new ListItem(
							"Vaccine Requirements: All fully vaccinated must carry certificate, partially vaccinated, and non-vaccinated Indians can travel but required RTPCR report as per country requirement. Travelers must check the recently updated policy before traveling (African Nations).Some countries may require proof of vaccination against certain diseases, such as yellow fever or cholera.",
							commonNormalFont7));
					orderedList.add(new ListItem(
							"Visiting Guidelines: Please check all the detailed guidelines for both your chosen destinations/countries and airlines.",
							commonNormalFont7));
					orderedList.add(new ListItem(
							"Fare Rules: Fare will be applicable as per airlines policies, must check the fare refund rule before booking, in case of non-refundable tickets – no refund will be applicable.",
							commonNormalFont7));
					orderedList.add(new ListItem(
							"Visa Requirements: All travellers must present hard copies of their foreign visa (soft copies won’t be accepted) at the immigration counters during departure. Defence Travel System, Balmer Lawrie, Ashok Travels & Tours and Corporate Client hold no liability with respect to visa information. To get further details on visa/transit and passport requirements please contact embassy before booking your travel.",
							commonNormalFont7));
					orderedList.add(new ListItem(
							"Additional Services post booking: Once International flight booking is done on the portal then there will not be any additional services are to be purchased offline or online through Airline portal on direct payment basis. ",
							commonNormalFont7));
					orderedList.add(new ListItem(
							"Name: Please ensure that the spelling of your name and other details match with your travel document/ govt.ID as these cannot be changed later. Incorrect details will lead to cancellation penalties or including reissued charges / Airline can deny boarding.",
							commonNormalFont7));
					orderedList.add(new ListItem(
							"Check the airline's baggage allowance: Each airline has different baggage allowances for checked and carry-on luggage. Make sure you know how much luggage you can bring with you before you book your flight.",
							commonNormalFont7));
					orderedList.add(new ListItem(
							"Check the airline's website for the latest updates: The airline's website is a good source of information about the latest travel rules and regulations. You should check the website before you book your flight to make sure you are up-todate on the latest information.",
							commonNormalFont7));
					orderedList.add(new ListItem(
							"Please note that travellers are solely responsible for ensuring their eligibility to enter the destination or transit countries. We accept no liability in this regard. Please check the travel rules of all regulatory websites before booking and commencing travel. ",
							commonNormalFont7));
				}
				/* END CODE BLOCK TO ADD INTERNATIONAL TRAVEL CONDITION */

            listCell.addElement(orderedList);

            rulesInforTable.addCell(listCell);

				PdfPCell mandatoryCell = new PdfPCell(new Paragraph(
						"Mandatory: All travelers have to ensure that they must carry a copy of this e-ticket accompanied with a photo identification (ID) card as per requirement of security at the airport. ID Cards may include Driving License, Passport,PAN Card,Voter ID Card, Aadhaar Card or any other ID issued by Government of India. Birth Certificate is a mandatory proof forinfant traveler.",
						commonBoldFont8));
            mandatoryCell.setBorder(Rectangle.NO_BORDER);

            rulesInforTable.addCell(mandatoryCell);

		    maintable.addCell(rulesInforTable);

		    document.add(maintable);

		    document.close();

 		    }

	   catch (Exception e) {
			DODLog.printStackTrace(e, AirEmailTicketService.class, LogConstant.EMAIL_PDF_LOG_FILE);
		}

		}
	}

}
