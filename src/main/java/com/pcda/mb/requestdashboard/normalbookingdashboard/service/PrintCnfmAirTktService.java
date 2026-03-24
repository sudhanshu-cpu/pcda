package com.pcda.mb.requestdashboard.normalbookingdashboard.service;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import org.springframework.stereotype.Service;

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
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetAttBookInfoModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetAttFlightInfoModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetAttInvoiceInfoModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetAttPassengerInfoModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetAttPaxCanInvoiceInfoModel;
import com.pcda.mb.travel.emailticket.service.AirEmailTicketService;
import com.pcda.util.DODDATAConstants;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PrintCnfmAirTktService {

	
	

	public void createATTAirATktPDF(GetAttBookInfoModel airTicketDetailsXml , HttpServletRequest request, ByteArrayOutputStream baos,String personalNumber,boolean flag) {
		
		Document document = null;
	    
		PdfPCell cell0 = new PdfPCell();
		
		String IATACode=DODDATAConstants.DOD_ATT_IATA_CODE_NUMBER;
		if(airTicketDetailsXml != null)
		{ 
		try
			{
				
				document = new Document(PageSize.A4, 10, 10, 10, 10);
			
				  PdfPTable flightDetailTable = new PdfPTable(5);
				  BaseColor flightColor = new BaseColor(255,255,255);
				  PdfPTable maintable = new PdfPTable(1);
			
			Font commonBoldFont_8 = FontFactory.getFont("", 8, Font.BOLD);
		 	Font commonBoldFont_10 = FontFactory.getFont("", 10, Font.BOLD);
			Font commonBoldFont_12 = FontFactory.getFont("", 12, Font.BOLD);
	        Font commonNormalFont_7 = FontFactory.getFont("", 7);
		
	        	
	        BaseColor redColor = new BaseColor(255,0,0);

	        Font commonBoldFont_7_red = FontFactory.getFont("", 7, Font.BOLD, redColor);
	        Font commonBoldFont_7 = FontFactory.getFont("", 7, Font.BOLD);
	        Font commonBoldFont_6 = FontFactory.getFont("", 6, Font.BOLD);
	        
	        if(document !=null)
	        {
	        	PdfWriter writer=PdfWriter.getInstance(document, baos);
	        	if(flag){
	        		writer.setEncryption(personalNumber.getBytes(),personalNumber.getBytes(),PdfWriter.ALLOW_PRINTING,PdfWriter.ALLOW_COPY); 
	        	}
	        	document.open();
	        	
	        	String tktRequestId=airTicketDetailsXml.getRequestId();
	        	
	        	
	        	
	         	boolean isCGTicket=true ;		
				
				
	         	URL classURL = getClass().getProtectionDomain().getCodeSource().getLocation();
	         				
	         				String irctcLogoPath = classURL.getPath().replaceAll("<classname>.class","");
	         	
			 	
			 	request.getRequestDispatcher("irctclogo.jpg");
				irctcLogoPath=irctcLogoPath.replace("./", ""); 
				irctcLogoPath=irctcLogoPath.replace(".\\", ""); 
				irctcLogoPath=irctcLogoPath.replace("irctclogo.jpg", ""); 
				
				 PdfPTable imageTable = new PdfPTable(1);
				    Image addlogo=null;
					try {
						if (isCGTicket) {
							addlogo = Image.getInstance(irctcLogoPath+"/static/images/" + "DTS_Header.png");
						}else {
							addlogo = Image.getInstance(irctcLogoPath+"/static/images/" + "DTS_Header.png");
						}
					} catch (Exception e) {
						DODLog.printStackTrace(e, PrintCnfmAirTktService.class,LogConstant.EMAIL_PDF_LOG_FILE);
					}
					
					addlogo.scaleToFit(455f, 6700f);
					
					cell0 = new PdfPCell(addlogo);
					cell0.setBorder(Rectangle.NO_BORDER);
					cell0.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
					cell0.setVerticalAlignment(Rectangle.ALIGN_CENTER);
					imageTable.addCell(cell0);
				    
				    maintable.addCell(imageTable); 
				 	
				 	PdfPTable claimTable1 = new PdfPTable(1);
				    claimTable1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				    
				    
				    PdfPCell claimCell_1 = new PdfPCell (new Paragraph("ALL TRAVELERS WHO HAVE BOOKED THEIR TICKET THROUGH DEFENCE TRAVEL SYSTEM MUST SUBMIT THEIR FINAL SETTLEMENT CLAIM TO THEIR RESPECTIVE PAO OFFICES WITHIN STIPULATED TIME w.r.t.AIR TRAVEL EVEN IF THE TICKET/JOURNEY HAS BEEN CANCELLED.", commonBoldFont_12));
				    claimCell_1.setBorder(Rectangle.NO_BORDER);
				    claimCell_1.setBackgroundColor(flightColor);
				    claimCell_1.setHorizontalAlignment (Element.ALIGN_JUSTIFIED);
				    claimCell_1.setPadding (3.0f);
				    claimTable1.addCell(claimCell_1);
				    maintable.addCell(claimTable1);
					
				 	PdfPTable headerTable = new PdfPTable(1);
			        
			        Font fontbold = FontFactory.getFont("", 14, Font.BOLD);
			        headerTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			        PdfPCell cell=null;
			        if(airTicketDetailsXml.getShowLTCLabel().equals("YES") ){
			        	cell = new PdfPCell (new Paragraph("LTC Air Ticket", fontbold));
			        }else{
			    	    cell = new PdfPCell (new Paragraph("Air Ticket", fontbold));
			        }
			        
			        
			        cell.setBorder(Rectangle.NO_BORDER);
			        cell.setBackgroundColor(flightColor);
			        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				    cell.setPadding (8.0f);
				    headerTable.addCell(cell);
				    
				    maintable.addCell(headerTable);
				    
				    PdfPTable journeyDetailTable = new PdfPTable(6);
				    journeyDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				    
				    float[] journeyColumnWidths = new float[] {19f, 20f, 19f, 22f, 16f, 12f};
			        
				    PdfPCell journeycell_0 = new PdfPCell (new Paragraph("Booking Id: ", commonBoldFont_8));
				    journeycell_0.setBorder(Rectangle.NO_BORDER);
				    journeycell_0.setHorizontalAlignment (Element.ALIGN_LEFT);
				    journeycell_0.setBackgroundColor(flightColor);
				    journeycell_0.setPadding (8.0f);
				    
				    journeyDetailTable.addCell(journeycell_0);
				    
				    
				    PdfPCell journeycell_0_0 = new PdfPCell (new Paragraph(airTicketDetailsXml.getBookingId(),commonNormalFont_7));
				    journeycell_0_0.setBorder(Rectangle.NO_BORDER);
				    journeycell_0_0.setBackgroundColor(flightColor);
				    journeycell_0_0.setHorizontalAlignment (Element.ALIGN_LEFT);
				    journeycell_0_0.setPadding (8.0f);
				    
				    journeyDetailTable.addCell(journeycell_0_0);
				    
				    
				    PdfPCell journeycell_1 = new PdfPCell (new Paragraph("Booking Date: ", commonBoldFont_8));
				    journeycell_1.setBorder(Rectangle.NO_BORDER);
				    journeycell_1.setBackgroundColor(flightColor);
				    journeycell_1.setHorizontalAlignment (Element.ALIGN_LEFT);
				    journeycell_1.setPadding (8.0f);
				    journeyDetailTable.addCell(journeycell_1);	
				    
				    PdfPCell journeycell_1_0 = new PdfPCell (new Paragraph(airTicketDetailsXml.getBookingDate(),commonNormalFont_7));
				    journeycell_1_0.setBorder(Rectangle.NO_BORDER);
				    journeycell_1_0.setBackgroundColor(flightColor);
				    journeycell_1_0.setHorizontalAlignment (Element.ALIGN_LEFT);
				    journeycell_1_0.setPadding (8.0f);
				    
				    journeyDetailTable.addCell(journeycell_1_0);
				   	
				    
                    PdfPCell journeycell_2 = new PdfPCell (new Paragraph("Status: ", commonBoldFont_8));
				    
				    journeycell_2.setBorder(Rectangle.NO_BORDER);
				    journeycell_2.setBackgroundColor(flightColor);
				    journeycell_2.setHorizontalAlignment (Element.ALIGN_LEFT);
				    journeycell_2.setPadding (8.0f);
				    journeyDetailTable.addCell(journeycell_2);	
				    
				    PdfPCell journeycell_2_0 = null;
					
					
				    if(airTicketDetailsXml.getBookingStatus().equalsIgnoreCase("Booked")){
				    	journeycell_2_0 = new PdfPCell (new Paragraph("Confirmed", commonNormalFont_7));
				    }
				    else{
				    	journeycell_2_0 = new PdfPCell (new Paragraph(airTicketDetailsXml.getBookingStatus(), commonNormalFont_7));
				    }
				    
				    journeycell_2_0.setBorder(Rectangle.NO_BORDER);
				    journeycell_2_0.setBackgroundColor(flightColor);
				    journeycell_2_0.setHorizontalAlignment (Element.ALIGN_LEFT);
				    journeycell_2_0.setPadding (8.0f);
				    journeyDetailTable.addCell(journeycell_2_0);
				    
				    PdfPCell journeycell_021 = new PdfPCell (new Paragraph("Order No.: ", commonBoldFont_8));
				    journeycell_021.setBorder(Rectangle.NO_BORDER);
				    journeycell_021.setHorizontalAlignment (Element.ALIGN_LEFT);
				    journeycell_021.setBackgroundColor(flightColor);
				    journeycell_021.setPadding (8.0f);				    
				    journeyDetailTable.addCell(journeycell_021);
				    
				    
				    
				    PdfPCell journeycell_022 = new PdfPCell (new Paragraph(airTicketDetailsXml.getOperatorTxnId(),commonNormalFont_7));
				    journeycell_022.setBorder(Rectangle.NO_BORDER);
				    journeycell_022.setBackgroundColor(flightColor);
				    journeycell_022.setHorizontalAlignment (Element.ALIGN_LEFT);
				    journeycell_022.setPadding (8.0f);				    
				    journeyDetailTable.addCell(journeycell_022);
				    
				    PdfPCell journeycell_023 = new PdfPCell (new Paragraph("Agent Name: ", commonBoldFont_8));
				    journeycell_023.setBorder(Rectangle.NO_BORDER);
				    journeycell_023.setHorizontalAlignment (Element.ALIGN_LEFT);
				    journeycell_023.setBackgroundColor(flightColor);
				    journeycell_023.setPadding (8.0f);				    
				    journeyDetailTable.addCell(journeycell_023);
				    
				    PdfPCell journeycell_024 = new PdfPCell (new Paragraph("Ashok Travels & Tours",commonNormalFont_7));
				    journeycell_024.setBorder(Rectangle.NO_BORDER);
				    journeycell_024.setBackgroundColor(flightColor);
				    journeycell_024.setHorizontalAlignment (Element.ALIGN_LEFT);
				    journeycell_024.setPadding (8.0f);				    
				    journeyDetailTable.addCell(journeycell_024);
				    
				    
				    
				    PdfPCell journeycell_025 = new PdfPCell (new Paragraph(IATACode, commonBoldFont_8));
				    journeycell_025.setBorder(Rectangle.NO_BORDER);
				    journeycell_025.setHorizontalAlignment (Element.ALIGN_LEFT);
				    journeycell_025.setBackgroundColor(flightColor);
				    journeycell_025.setPadding (8.0f);				    
				    journeyDetailTable.addCell(journeycell_025);
				    
				    PdfPCell journeycell_026 = new PdfPCell (new Paragraph(DODDATAConstants.DOD_ATT_IATA_CODE_NUMBER,commonNormalFont_7));
				    journeycell_026.setBorder(Rectangle.NO_BORDER);
				    journeycell_026.setBackgroundColor(flightColor);
				    journeycell_026.setHorizontalAlignment (Element.ALIGN_LEFT);
				    journeycell_026.setPadding (8.0f);				    
				    journeyDetailTable.addCell(journeycell_026);
				    
				    journeycell_0 = new PdfPCell (new Paragraph("Trip Type: ", commonBoldFont_8));
				    journeycell_0.setBorder(Rectangle.NO_BORDER);
				    journeycell_0.setBackgroundColor(flightColor);
				    journeycell_0.setHorizontalAlignment (Element.ALIGN_LEFT);
				    journeycell_0.setPadding (8.0f);
				    
				    journeyDetailTable.addCell(journeycell_0);
					    
					journeycell_0_0 = null;
					
					if(airTicketDetailsXml.getJournyType().equalsIgnoreCase("Return")){
				    	journeycell_1_0 = new PdfPCell (new Paragraph(airTicketDetailsXml.getSourceSectorCity()
				    			+" ("+airTicketDetailsXml.getSourceSectorCode()
				    			+") - "+airTicketDetailsXml.getDestinationSectorCity()+" ("+airTicketDetailsXml.getDestinationSectorCode()+") - "+airTicketDetailsXml.getSourceSectorCity()
				    			+" ("+airTicketDetailsXml.getSourceSectorCode()
				    			+")",commonNormalFont_7));
				    }else{
				    	journeycell_1_0 = new PdfPCell (new Paragraph(airTicketDetailsXml.getSourceSectorCity()
				    			+" ("+airTicketDetailsXml.getSourceSectorCode()
				    			+") - "+airTicketDetailsXml.getDestinationSectorCity()+" ("+airTicketDetailsXml.getDestinationSectorCity()+")",commonNormalFont_7));
				    }
					
					    journeycell_1_0.setColspan(3);
					    journeycell_1_0.setBackgroundColor(flightColor);
					    journeycell_1_0.setBorder(Rectangle.NO_BORDER);
					    journeycell_1_0.setHorizontalAlignment (Element.ALIGN_LEFT);
					    journeycell_1_0.setPadding (8.0f);
					    
					    journeyDetailTable.addCell(journeycell_1_0);
						    
					    journeyDetailTable.setWidths(journeyColumnWidths);
					    
					    maintable.addCell(journeyDetailTable);
				    
					  
					    
					    flightDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
					    
					    float[] flightColumnWidths = new float[] {2f, 13f, 25f, 30f, 25f};
					    flightDetailTable.setWidths(flightColumnWidths);
					    
					    String airLineGST=airTicketDetailsXml.getAirGSTNo();
					    
					    String airLinePNR = "";
					    String gdsPNR = "";
					    String isRefundable="";
					    
					    if(airTicketDetailsXml.getJournyType().equalsIgnoreCase("Return"))
					    {
					    	java.util.List<GetAttFlightInfoModel> onwardFlightList = airTicketDetailsXml.getFlightInfo();
					    	
					    	java.util.List<GetAttFlightInfoModel> returnFlightList = airTicketDetailsXml.getFlightInfo();
					    
					    	DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, AirEmailTicketService.class, "onwardFlightList == "+onwardFlightList);
					    	DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, AirEmailTicketService.class, "returnFlightList == "+returnFlightList);
					    { 
						    PdfPCell flightcell_0 = new PdfPCell (new Paragraph("Flight Information (Onward)", commonBoldFont_10));
						    flightcell_0.setBorder(Rectangle.NO_BORDER);
						    flightcell_0.setColspan(3);
						    flightcell_0.setBackgroundColor(flightColor);
						    flightcell_0.setHorizontalAlignment (Element.ALIGN_LEFT);
						    flightcell_0.setPadding (8.0f);
						    flightDetailTable.addCell(flightcell_0);
					    
						    
						    PdfPCell flightcell_01 = new PdfPCell (new Paragraph("Stops : "+(onwardFlightList.size()-1), commonBoldFont_10));
						    flightcell_01.setBorder(Rectangle.NO_BORDER);
						    flightcell_01.setBackgroundColor(flightColor);
						    flightcell_01.setHorizontalAlignment (Element.ALIGN_LEFT);
						    flightcell_01.setPadding (8.0f);
						    flightDetailTable.addCell(flightcell_01);
					    
						    PdfPCell flightcell_02 = new PdfPCell (new Paragraph("", commonBoldFont_6));
						    flightcell_02.setBorder(Rectangle.NO_BORDER);
						    flightcell_02.setBackgroundColor(flightColor);
						    flightcell_02.setHorizontalAlignment (Element.ALIGN_LEFT);
						    flightcell_02.setPadding (8.0f);
						    flightDetailTable.addCell(flightcell_02);
						    
						    PdfPCell flightcell = new PdfPCell (new Paragraph("", commonBoldFont_6));
						    flightcell.setBorder(Rectangle.NO_BORDER);
						    flightcell.setBackgroundColor(flightColor);
						    flightcell.setHorizontalAlignment (Element.ALIGN_LEFT);
						    flightcell.setPadding (3.0f);
						    flightDetailTable.addCell(flightcell);
					    
						    PdfPCell flightcell_1 = new PdfPCell (new Paragraph("Carrier", commonBoldFont_7));
						    flightcell_1.setBorder(Rectangle.NO_BORDER);
						    flightcell_1.setBackgroundColor(flightColor);
						    flightcell_1.setHorizontalAlignment (Element.ALIGN_LEFT);
						    flightcell_1.setPadding (3.0f);
						    flightDetailTable.addCell(flightcell_1);
						    
						    PdfPCell flightcell_2 = new PdfPCell (new Paragraph("Departure", commonBoldFont_7));
						    flightcell_2.setBorder(Rectangle.NO_BORDER);
						    flightcell_2.setBackgroundColor(flightColor);
						    flightcell_2.setHorizontalAlignment (Element.ALIGN_LEFT);
						    flightcell_2.setPadding (3.0f);
						    flightDetailTable.addCell(flightcell_2);
					    
						    PdfPCell flightcell_3 = new PdfPCell (new Paragraph("Arrival", commonBoldFont_7));
						    flightcell_3.setBorder(Rectangle.NO_BORDER);
						    flightcell_3.setBackgroundColor(flightColor);
						    flightcell_3.setHorizontalAlignment (Element.ALIGN_LEFT);
						    flightcell_3.setPadding (3.0f);
						    flightDetailTable.addCell(flightcell_3);
						    
						    PdfPCell flightcell_4 = new PdfPCell (new Paragraph("Other Information", commonBoldFont_7));
						    flightcell_4.setBorder(Rectangle.NO_BORDER);
						    flightcell_4.setBackgroundColor(flightColor);
						    flightcell_4.setHorizontalAlignment (Element.ALIGN_LEFT);
						    flightcell_4.setPadding (3.0f);
						    flightDetailTable.addCell(flightcell_4);
						    
						    Element flightElement=null;
						    
						    for(int v = 0; v < onwardFlightList.size(); v++)
						    {
					    	
					    		if(v == 0){
					    			airLinePNR=onwardFlightList.get(v).getAirlinePnr();
					    			gdsPNR=onwardFlightList.get(v).getGdsPnr();
					    			isRefundable=onwardFlightList.get(v).getRefundable();
					    			
					    		}
					    		
					      		PdfPCell flightcell_0_0 = new PdfPCell (new Paragraph("", commonBoldFont_6));
						    	flightcell_0_0.setBorder(Rectangle.NO_BORDER);
						    	flightcell_0_0.setHorizontalAlignment (Element.ALIGN_LEFT);
						    	flightcell_0_0.setPadding (3.0f);
						    	flightcell_0_0.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_0_0);
						    
					    		PdfPCell flightcell_1_0 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getOperatingAirline(), commonBoldFont_8));
					    		flightcell_1_0.setBorder(Rectangle.NO_BORDER);
					    		flightcell_1_0.setHorizontalAlignment(Element.ALIGN_LEFT);
					    		flightcell_1_0.setPadding (3.0f);
					    		flightcell_1_0.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_1_0);
							    
							    
							    
							    PdfPCell flightcell_1_1 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getSource()+" ("+onwardFlightList.get(v).getSourceCode()+")", commonBoldFont_8));
							    flightcell_1_1.setBorder(Rectangle.NO_BORDER);
							    flightcell_1_1.setHorizontalAlignment(Element.ALIGN_LEFT);
							    flightcell_1_1.setPadding (3.0f);
							    flightcell_1_1.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_1_1);
							    
							    PdfPCell flightcell_2_1 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getDestination()+" ("+onwardFlightList.get(v).getDestinationCode()+")", commonBoldFont_8));
							    flightcell_2_1.setBorder(Rectangle.NO_BORDER);
							    flightcell_2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
							    flightcell_2_1.setPadding (3.0f);
							    flightcell_2_1.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_2_1);
							    
							    PdfPCell flightcell_3_1 = new PdfPCell (new Paragraph("Check-in: "+onwardFlightList.get(v).getBaggageAllowance()+"  Cabin: 7Kg ", commonNormalFont_7));
							    flightcell_3_1.setBorder(Rectangle.NO_BORDER);
							    flightcell_3_1.setHorizontalAlignment(Element.ALIGN_LEFT);
							    flightcell_3_1.setPadding (3.0f);
							    flightcell_3_1.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_3_1);
							    
							    PdfPCell flightcell_0_1 = new PdfPCell (new Paragraph("", commonBoldFont_6));
							    flightcell_0_1.setBorder(Rectangle.NO_BORDER);
							    flightcell_0_1.setHorizontalAlignment (Element.ALIGN_LEFT);
							    flightcell_0_1.setPadding (3.0f);
							    flightcell_0_1.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_0_1);
						    
					    		PdfPCell flightcell_1_2 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getAirline()+" - "+onwardFlightList.get(v).getFlightNo(), commonNormalFont_7));
					    		flightcell_1_2.setBorder(Rectangle.NO_BORDER);
					    		flightcell_1_2.setHorizontalAlignment(Element.ALIGN_LEFT);
					    		flightcell_1_2.setPadding (3.0f);
					    		flightcell_1_2.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_1_2);
							    
							    
							    PdfPCell flightcell_2_2 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getSegmentOriginAirport(), commonNormalFont_7));
							    
							    flightcell_2_2.setBorder(Rectangle.NO_BORDER);
							    flightcell_2_2.setHorizontalAlignment(Element.ALIGN_LEFT);
							    flightcell_2_2.setPadding (3.0f);
							    flightcell_2_2.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_2_2);
							    
							    PdfPCell flightcell_3_2 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getSegmentDestinationAirport(), commonNormalFont_7));
							    flightcell_3_2.setBorder(Rectangle.NO_BORDER);
							    flightcell_3_2.setHorizontalAlignment(Element.ALIGN_LEFT);
							    flightcell_3_2.setPadding (3.0f);
							    flightcell_3_2.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_3_2);
							    
							    PdfPCell flightcell_4_2 = new PdfPCell (new Paragraph("Cabin Class: "+onwardFlightList.get(v).getCabinClass(), commonNormalFont_7));
							    flightcell_4_2.setBorder(Rectangle.NO_BORDER);
							    flightcell_4_2.setHorizontalAlignment(Element.ALIGN_LEFT);
							    flightcell_4_2.setPadding (3.0f);
							    flightcell_4_2.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_4_2);
							    
							    
							    PdfPCell flightcell_0_2 = new PdfPCell (new Paragraph("", commonBoldFont_6));
							    flightcell_0_2.setBorder(Rectangle.NO_BORDER);
							    flightcell_0_2.setHorizontalAlignment (Element.ALIGN_LEFT);
							    flightcell_0_2.setPadding (3.0f);
							    flightcell_0_2.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_0_2);
						    
					    		PdfPCell flightcell_1_3 = new PdfPCell (new Paragraph("", commonNormalFont_7));
					    		flightcell_1_3.setBorder(Rectangle.NO_BORDER);
					    		flightcell_1_3.setHorizontalAlignment(Element.ALIGN_LEFT);
					    		flightcell_1_3.setPadding (3.0f);
					    		flightcell_1_3.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_1_3);
							    
							   PdfPCell flightcell_2_3 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getScheduledDepartureDate()+""+onwardFlightList.get(v).getScheduledDeparture(), commonNormalFont_7));
							    flightcell_2_3.setBorder(Rectangle.NO_BORDER);
							    flightcell_2_3.setHorizontalAlignment(Element.ALIGN_LEFT);
							    flightcell_2_3.setPadding (3.0f);
							    flightcell_2_3.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_2_3);
							    
							    PdfPCell flightcell_3_3 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getScheduledArrivalDate()+" "+onwardFlightList.get(v).getScheduledArrival(), commonNormalFont_7));
							    flightcell_3_3.setBorder(Rectangle.NO_BORDER);
							    flightcell_3_3.setHorizontalAlignment(Element.ALIGN_LEFT);
							    flightcell_3_3.setPadding (3.0f);
							    flightcell_3_3.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_3_3);
							    
							    PdfPCell flightcell_4_3 = new PdfPCell (new Paragraph("Booking Class: "+onwardFlightList.get(v).getBookingClass(), commonNormalFont_7));
							    flightcell_4_3.setBorder(Rectangle.NO_BORDER);
							    flightcell_4_3.setHorizontalAlignment(Element.ALIGN_LEFT);
							    flightcell_4_3.setPadding (3.0f);
							    flightcell_4_3.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_4_3);
							    
							    
							    PdfPCell flightcell_0_3 = new PdfPCell (new Paragraph("", commonBoldFont_6));
							    flightcell_0_3.setBorder(Rectangle.NO_BORDER);
							    flightcell_0_3.setHorizontalAlignment (Element.ALIGN_LEFT);
							    flightcell_0_3.setPadding (3.0f);
							    flightcell_0_3.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_0_3);
						    
					    		PdfPCell flightcell_1_4 = new PdfPCell (new Paragraph("", commonNormalFont_7));
					    		flightcell_1_4.setBorder(Rectangle.NO_BORDER);
					    		flightcell_1_4.setHorizontalAlignment(Element.ALIGN_LEFT);
					    		flightcell_1_4.setPadding (3.0f);
					    		flightcell_1_4.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_1_4);
							    
							    PdfPCell flightcell_2_4 = new PdfPCell (new Paragraph("Terminal: "+onwardFlightList.get(v).getDepartTerminal(), commonNormalFont_7));
							    flightcell_2_4.setBorder(Rectangle.NO_BORDER);
							    flightcell_2_4.setHorizontalAlignment(Element.ALIGN_LEFT);
							    flightcell_2_4.setPadding (3.0f);
							    flightcell_2_4.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_2_4);
							    
							   PdfPCell flightcell_3_4 = new PdfPCell (new Paragraph("Terminal: "+onwardFlightList.get(v).getArrivTerminal(), commonNormalFont_7));
							    flightcell_3_4.setBorder(Rectangle.NO_BORDER);
							    flightcell_3_4.setHorizontalAlignment(Element.ALIGN_LEFT);
							    flightcell_3_4.setPadding (3.0f);
							    flightcell_3_4.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_3_4);
							    
							    PdfPCell flightcell_4_4 = new PdfPCell (new Paragraph("Duration: "+onwardFlightList.get(v).getSectorDuration(), commonNormalFont_7));
							    flightcell_4_4.setBorder(Rectangle.NO_BORDER);
							    flightcell_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
							    flightcell_4_4.setPadding (3.0f);
							    flightcell_4_4.setBackgroundColor(flightColor);
							    flightDetailTable.addCell(flightcell_4_4);
					    
				    
						    } //End of onwardFlightList loop
	
			}//End of onward block
						    
						    
						    { //Starts Return Block
						    	
						    	 PdfPCell flightcell_0 = new PdfPCell (new Paragraph("Flight Information (Return)", commonBoldFont_10));
								    flightcell_0.setBorder(Rectangle.NO_BORDER);
								    flightcell_0.setColspan(3);
								    flightcell_0.setBackgroundColor(flightColor);
								    flightcell_0.setHorizontalAlignment (Element.ALIGN_LEFT);
								    flightcell_0.setPadding (8.0f);
								    flightDetailTable.addCell(flightcell_0);
								    
								    //Start: Stops display
								    PdfPCell flightcell_01 = new PdfPCell (new Paragraph("Stops : "+(returnFlightList.size()-1), commonBoldFont_10));
								    flightcell_01.setBorder(Rectangle.NO_BORDER);
								    flightcell_01.setBackgroundColor(flightColor);
								    flightcell_01.setHorizontalAlignment (Element.ALIGN_LEFT);
								    flightcell_01.setPadding (8.0f);
								    flightDetailTable.addCell(flightcell_01);
								    
								    PdfPCell flightcell_02 = new PdfPCell (new Paragraph("", commonBoldFont_6));
								    flightcell_02.setBorder(Rectangle.NO_BORDER);
								    flightcell_02.setBackgroundColor(flightColor);
								    flightcell_02.setHorizontalAlignment (Element.ALIGN_LEFT);
								    flightcell_02.setPadding (8.0f);
								    flightDetailTable.addCell(flightcell_02);
								    //End: Stops display
								    
								    PdfPCell flightcell = new PdfPCell (new Paragraph("", commonBoldFont_6));
								    flightcell.setBorder(Rectangle.NO_BORDER);
								    flightcell.setBackgroundColor(flightColor);
								    flightcell.setHorizontalAlignment (Element.ALIGN_LEFT);
								    flightcell.setPadding (3.0f);
								    flightDetailTable.addCell(flightcell);
								    
								    PdfPCell flightcell_1 = new PdfPCell (new Paragraph("Carrier", commonBoldFont_7));
								    flightcell_1.setBorder(Rectangle.NO_BORDER);
								    flightcell_1.setBackgroundColor(flightColor);
								    flightcell_1.setHorizontalAlignment (Element.ALIGN_LEFT);
								    flightcell_1.setPadding (3.0f);
								    flightDetailTable.addCell(flightcell_1);
								    
								    PdfPCell flightcell_2 = new PdfPCell (new Paragraph("Departure", commonBoldFont_7));
								    flightcell_2.setBorder(Rectangle.NO_BORDER);
								    flightcell_2.setBackgroundColor(flightColor);
								    flightcell_2.setHorizontalAlignment (Element.ALIGN_LEFT);
								    flightcell_2.setPadding (3.0f);
								    flightDetailTable.addCell(flightcell_2);
								    
								    PdfPCell flightcell_3 = new PdfPCell (new Paragraph("Arrival", commonBoldFont_7));
								    flightcell_3.setBorder(Rectangle.NO_BORDER);
								    flightcell_3.setBackgroundColor(flightColor);
								    flightcell_3.setHorizontalAlignment (Element.ALIGN_LEFT);
								    flightcell_3.setPadding (3.0f);
								    flightDetailTable.addCell(flightcell_3);
								    
								    PdfPCell flightcell_4 = new PdfPCell (new Paragraph("Other Information", commonBoldFont_7));
								    flightcell_4.setBorder(Rectangle.NO_BORDER);
								    flightcell_4.setBackgroundColor(flightColor);
								    flightcell_4.setHorizontalAlignment (Element.ALIGN_LEFT);
								    flightcell_4.setPadding (3.0f);
								    flightDetailTable.addCell(flightcell_4);
							    
								   
						    
						    	
								    for(int v = 0; v < returnFlightList.size(); v++)
								    {
								    	
								    	
							    	
							      		PdfPCell flightcell_0_0 = new PdfPCell (new Paragraph("", commonBoldFont_6));
								    	flightcell_0_0.setBorder(Rectangle.NO_BORDER);
								    	flightcell_0_0.setHorizontalAlignment (Element.ALIGN_LEFT);
								    	flightcell_0_0.setPadding (3.0f);
								    	flightcell_0_0.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_0_0);
								    
							    		PdfPCell flightcell_1_0 = new PdfPCell (new Paragraph(returnFlightList.get(v).getOperatingAirline(), commonBoldFont_8));
							    		flightcell_1_0.setBorder(Rectangle.NO_BORDER);
							    		flightcell_1_0.setHorizontalAlignment(Element.ALIGN_LEFT);
							    		flightcell_1_0.setPadding (3.0f);
							    		flightcell_1_0.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_1_0);
									    
									    PdfPCell flightcell_1_1 = new PdfPCell (new Paragraph(returnFlightList.get(v).getSource()+" ("+returnFlightList.get(v).getSourceCode()+")", commonBoldFont_8));
									    flightcell_1_1.setBorder(Rectangle.NO_BORDER);
									    flightcell_1_1.setHorizontalAlignment(Element.ALIGN_LEFT);
									    flightcell_1_1.setPadding (3.0f);
									    flightcell_1_1.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_1_1);
									    
									    PdfPCell flightcell_2_1 = new PdfPCell (new Paragraph(returnFlightList.get(v).getDestination()+" ("+returnFlightList.get(v).getDestinationCode()+")", commonBoldFont_8));
									    flightcell_2_1.setBorder(Rectangle.NO_BORDER);
									    flightcell_2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
									    flightcell_2_1.setPadding (3.0f);
									    flightcell_2_1.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_2_1);
									    
									    PdfPCell flightcell_3_1 = new PdfPCell (new Paragraph("Check-in: "+returnFlightList.get(v).getBaggageAllowance()+"  Cabin: 7Kg ", commonNormalFont_7));
									    flightcell_3_1.setBorder(Rectangle.NO_BORDER);
									    flightcell_3_1.setHorizontalAlignment(Element.ALIGN_LEFT);
									    flightcell_3_1.setPadding (3.0f);
									    flightcell_3_1.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_3_1);
									    
									    
									    PdfPCell flightcell_0_1 = new PdfPCell (new Paragraph("", commonBoldFont_6));
									    flightcell_0_1.setBorder(Rectangle.NO_BORDER);
									    flightcell_0_1.setHorizontalAlignment (Element.ALIGN_LEFT);
									    flightcell_0_1.setPadding (3.0f);
									    flightcell_0_1.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_0_1);
								    
							    		PdfPCell flightcell_1_2 = new PdfPCell (new Paragraph(returnFlightList.get(v).getAirline()+" - "+returnFlightList.get(v).getFlightNo(), commonNormalFont_7));
							    		flightcell_1_2.setBorder(Rectangle.NO_BORDER);
							    		flightcell_1_2.setHorizontalAlignment(Element.ALIGN_LEFT);
							    		flightcell_1_2.setPadding (3.0f);
							    		flightcell_1_2.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_1_2);
									    
									   PdfPCell flightcell_2_2 = new PdfPCell (new Paragraph(returnFlightList.get(v).getSegmentOriginAirport(), commonNormalFont_7));
									    flightcell_2_2.setBorder(Rectangle.NO_BORDER);
									    flightcell_2_2.setHorizontalAlignment(Element.ALIGN_LEFT);
									    flightcell_2_2.setPadding (3.0f);
									    flightcell_2_2.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_2_2);
									    
									    PdfPCell flightcell_3_2 = new PdfPCell (new Paragraph(returnFlightList.get(v).getSegmentDestinationAirport(), commonNormalFont_7));
									    flightcell_3_2.setBorder(Rectangle.NO_BORDER);
									    flightcell_3_2.setHorizontalAlignment(Element.ALIGN_LEFT);
									    flightcell_3_2.setPadding (3.0f);
									    flightcell_3_2.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_3_2);
									    
									    PdfPCell flightcell_4_2 = new PdfPCell (new Paragraph("Cabin Class: "+returnFlightList.get(v).getCabinClass(), commonNormalFont_7));
									    flightcell_4_2.setBorder(Rectangle.NO_BORDER);
									    flightcell_4_2.setHorizontalAlignment(Element.ALIGN_LEFT);
									    flightcell_4_2.setPadding (3.0f);
									    flightcell_4_2.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_4_2);
									    
									    
									    PdfPCell flightcell_0_2 = new PdfPCell (new Paragraph("", commonBoldFont_6));
									    flightcell_0_2.setBorder(Rectangle.NO_BORDER);
									    flightcell_0_2.setHorizontalAlignment (Element.ALIGN_LEFT);
									    flightcell_0_2.setPadding (3.0f);
									    flightcell_0_2.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_0_2);
								    
							    		PdfPCell flightcell_1_3 = new PdfPCell (new Paragraph("", commonNormalFont_7));
							    		flightcell_1_3.setBorder(Rectangle.NO_BORDER);
							    		flightcell_1_3.setHorizontalAlignment(Element.ALIGN_LEFT);
							    		flightcell_1_3.setPadding (3.0f);
							    		flightcell_1_3.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_1_3);
									    
									   PdfPCell flightcell_2_3 = new PdfPCell (new Paragraph(returnFlightList.get(v).getScheduledDepartureDate()+" "+returnFlightList.get(v).getScheduledDeparture(), commonNormalFont_7));
									    flightcell_2_3.setBorder(Rectangle.NO_BORDER);
									    flightcell_2_3.setHorizontalAlignment(Element.ALIGN_LEFT);
									    flightcell_2_3.setPadding (3.0f);
									    flightcell_2_3.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_2_3);
									    
									   PdfPCell flightcell_3_3 = new PdfPCell (new Paragraph(returnFlightList.get(v).getScheduledArrivalDate()+" "+returnFlightList.get(v).getScheduledArrival(), commonNormalFont_7));
									    flightcell_3_3.setBorder(Rectangle.NO_BORDER);
									    flightcell_3_3.setHorizontalAlignment(Element.ALIGN_LEFT);
									    flightcell_3_3.setPadding (3.0f);
									    flightcell_3_3.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_3_3);
									    
									    PdfPCell flightcell_4_3 = new PdfPCell (new Paragraph("Booking Class: "+returnFlightList.get(v).getBookingClass(), commonNormalFont_7));
									    flightcell_4_3.setBorder(Rectangle.NO_BORDER);
									    flightcell_4_3.setHorizontalAlignment(Element.ALIGN_LEFT);
									    flightcell_4_3.setPadding (3.0f);
									    flightcell_4_3.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_4_3);
									    
									    
									    PdfPCell flightcell_0_3 = new PdfPCell (new Paragraph("", commonBoldFont_6));
									    flightcell_0_3.setBorder(Rectangle.NO_BORDER);
									    flightcell_0_3.setHorizontalAlignment (Element.ALIGN_LEFT);
									    flightcell_0_3.setPadding (3.0f);
									    flightcell_0_3.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_0_3);
								    
							    		PdfPCell flightcell_1_4 = new PdfPCell (new Paragraph("", commonNormalFont_7));
							    		flightcell_1_4.setBorder(Rectangle.NO_BORDER);
							    		flightcell_1_4.setHorizontalAlignment(Element.ALIGN_LEFT);
							    		flightcell_1_4.setPadding (3.0f);
							    		flightcell_1_4.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_1_4);
									    
									    PdfPCell flightcell_2_4 = new PdfPCell (new Paragraph("Terminal: "+returnFlightList.get(v).getDepartTerminal(), commonNormalFont_7));
									    flightcell_2_4.setBorder(Rectangle.NO_BORDER);
									    flightcell_2_4.setHorizontalAlignment(Element.ALIGN_LEFT);
									    flightcell_2_4.setPadding (3.0f);
									    flightcell_2_4.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_2_4);
									    
									   PdfPCell flightcell_3_4 = new PdfPCell (new Paragraph("Terminal: "+returnFlightList.get(v).getArrivTerminal(), commonNormalFont_7));
									    flightcell_3_4.setBorder(Rectangle.NO_BORDER);
									    flightcell_3_4.setHorizontalAlignment(Element.ALIGN_LEFT);
									    flightcell_3_4.setPadding (3.0f);
									    flightcell_3_4.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_3_4);
									    
									   PdfPCell flightcell_4_4 = new PdfPCell (new Paragraph("Duration: "+returnFlightList.get(v).getSectorDuration(), commonNormalFont_7));
									    flightcell_4_4.setBorder(Rectangle.NO_BORDER);
									    flightcell_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
									    flightcell_4_4.setPadding (3.0f);
									    flightcell_4_4.setBackgroundColor(flightColor);
									    flightDetailTable.addCell(flightcell_4_4);
								    }  //End of returnFlightList loop
							    
							    } //End of Return block
						   
	        }  //End of Return
	        
			
	        else {
	        	java.util.List<GetAttFlightInfoModel> onwardFlightList = airTicketDetailsXml.getFlightInfo();
			    PdfPCell flightcell_0 = new PdfPCell (new Paragraph("Flight Information", commonBoldFont_10));
			    flightcell_0.setBorder(Rectangle.NO_BORDER);
			    flightcell_0.setColspan(3);
			    flightcell_0.setBackgroundColor(flightColor);
			    flightcell_0.setHorizontalAlignment (Element.ALIGN_LEFT);
			    flightcell_0.setPadding (8.0f);
			    flightDetailTable.addCell(flightcell_0);
			    
			    //Start: Stops display
			    PdfPCell flightcell_01 = new PdfPCell (new Paragraph("Stops : "+(onwardFlightList.size()-1), commonBoldFont_10));
			    flightcell_01.setBorder(Rectangle.NO_BORDER);
			    flightcell_01.setBackgroundColor(flightColor);
			    flightcell_01.setHorizontalAlignment (Element.ALIGN_LEFT);
			    flightcell_01.setPadding (8.0f);
			    flightDetailTable.addCell(flightcell_01);
			    
			    PdfPCell flightcell_02 = new PdfPCell (new Paragraph("", commonBoldFont_6));
			    flightcell_02.setBorder(Rectangle.NO_BORDER);
			    flightcell_02.setBackgroundColor(flightColor);
			    flightcell_02.setHorizontalAlignment (Element.ALIGN_LEFT);
			    flightcell_02.setPadding (8.0f);
			    flightDetailTable.addCell(flightcell_02);
			    //End: Stops display
			    
			    PdfPCell flightcell = new PdfPCell (new Paragraph("", commonBoldFont_6));
			    flightcell.setBorder(Rectangle.NO_BORDER);
			    flightcell.setBackgroundColor(flightColor);
			    flightcell.setHorizontalAlignment (Element.ALIGN_LEFT);
			    flightcell.setPadding (3.0f);
			    flightDetailTable.addCell(flightcell);
			    
			    PdfPCell flightcell_1 = new PdfPCell (new Paragraph("Carrier", commonBoldFont_7));
			    flightcell_1.setBorder(Rectangle.NO_BORDER);
			    flightcell_1.setBackgroundColor(flightColor);
			    flightcell_1.setHorizontalAlignment (Element.ALIGN_LEFT);
			    flightcell_1.setPadding (3.0f);
			    flightDetailTable.addCell(flightcell_1);
			    
			    PdfPCell flightcell_2 = new PdfPCell (new Paragraph("Departure", commonBoldFont_7));
			    flightcell_2.setBorder(Rectangle.NO_BORDER);
			    flightcell_2.setBackgroundColor(flightColor);
			    flightcell_2.setHorizontalAlignment (Element.ALIGN_LEFT);
			    flightcell_2.setPadding (3.0f);
			    flightDetailTable.addCell(flightcell_2);
			    
			    PdfPCell flightcell_3 = new PdfPCell (new Paragraph("Arrival", commonBoldFont_7));
			    flightcell_3.setBorder(Rectangle.NO_BORDER);
			    flightcell_3.setBackgroundColor(flightColor);
			    flightcell_3.setHorizontalAlignment (Element.ALIGN_LEFT);
			    flightcell_3.setPadding (3.0f);
			    flightDetailTable.addCell(flightcell_3);
			    
			    PdfPCell flightcell_4 = new PdfPCell (new Paragraph("Other Information", commonBoldFont_7));
			    flightcell_4.setBorder(Rectangle.NO_BORDER);
			    flightcell_4.setBackgroundColor(flightColor);
			    flightcell_4.setHorizontalAlignment (Element.ALIGN_LEFT);
			    flightcell_4.setPadding (3.0f);
			    flightDetailTable.addCell(flightcell_4);
			    
			   
			    
			    
			    
			    for(int v = 0; v < onwardFlightList.size(); v++)
			    {
			    	
			   
			    	
			    	if(v == 0){
		    			airLinePNR = onwardFlightList.get(v).getAirlinePnr();
		    			gdsPNR = onwardFlightList.get(v).getGdsPnr();
		    			isRefundable=onwardFlightList.get(v).getRefundable();
		    		}
		      		PdfPCell flightcell_0_0 = new PdfPCell (new Paragraph("", commonBoldFont_6));
			    	flightcell_0_0.setBorder(Rectangle.NO_BORDER);
			    	flightcell_0_0.setHorizontalAlignment (Element.ALIGN_LEFT);
			    	flightcell_0_0.setPadding (3.0f);
			    	flightcell_0_0.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_0_0);
			    
		    		PdfPCell flightcell_1_0 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getOperatingAirline(), commonBoldFont_8));
		    		flightcell_1_0.setBorder(Rectangle.NO_BORDER);
		    		flightcell_1_0.setHorizontalAlignment(Element.ALIGN_LEFT);
		    		flightcell_1_0.setPadding (3.0f);
		    		flightcell_1_0.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_1_0);
				    
				    PdfPCell flightcell_1_1 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getSource()+" ("+onwardFlightList.get(v).getSourceCode()+")", commonBoldFont_8));
				    flightcell_1_1.setBorder(Rectangle.NO_BORDER);
				    flightcell_1_1.setHorizontalAlignment(Element.ALIGN_LEFT);
				    flightcell_1_1.setPadding (3.0f);
				    flightcell_1_1.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_1_1);
					    
				    PdfPCell flightcell_2_1 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getDestination()+" ("+onwardFlightList.get(v).getDestinationCode()+")", commonBoldFont_8));
				    flightcell_2_1.setBorder(Rectangle.NO_BORDER);
				    flightcell_2_1.setHorizontalAlignment(Element.ALIGN_LEFT);
				    flightcell_2_1.setPadding (3.0f);
				    flightcell_2_1.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_2_1);
				    
				    PdfPCell flightcell_3_1 = new PdfPCell (new Paragraph("Check-in: "+onwardFlightList.get(v).getBaggageAllowance()+"  Cabin: 7Kg ", commonNormalFont_7));
				    flightcell_3_1.setBorder(Rectangle.NO_BORDER);
				    flightcell_3_1.setHorizontalAlignment(Element.ALIGN_LEFT);
				    flightcell_3_1.setPadding (3.0f);
				    flightcell_3_1.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_3_1);
				    
				    
				    PdfPCell flightcell_0_1 = new PdfPCell (new Paragraph("", commonBoldFont_6));
				    flightcell_0_1.setBorder(Rectangle.NO_BORDER);
				    flightcell_0_1.setHorizontalAlignment (Element.ALIGN_LEFT);
				    flightcell_0_1.setPadding (3.0f);
				    flightcell_0_1.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_0_1);
			    
		    		PdfPCell flightcell_1_2 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getAirline()+" - "+onwardFlightList.get(v).getFlightNo(), commonNormalFont_7));
		    		flightcell_1_2.setBorder(Rectangle.NO_BORDER);
		    		flightcell_1_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		    		flightcell_1_2.setPadding (3.0f);
		    		flightcell_1_2.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_1_2);
					    
				   PdfPCell flightcell_2_2 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getSegmentOriginAirport(), commonNormalFont_7));
				    flightcell_2_2.setBorder(Rectangle.NO_BORDER);
				    flightcell_2_2.setHorizontalAlignment(Element.ALIGN_LEFT);
				    flightcell_2_2.setPadding (3.0f);
				    flightcell_2_2.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_2_2);
				    
				    PdfPCell flightcell_3_2 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getSegmentDestinationAirport(), commonNormalFont_7));
				    flightcell_3_2.setBorder(Rectangle.NO_BORDER);
				    flightcell_3_2.setHorizontalAlignment(Element.ALIGN_LEFT);
				    flightcell_3_2.setPadding (3.0f);
				    flightcell_3_2.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_3_2);
				    
				    PdfPCell flightcell_4_2 = new PdfPCell (new Paragraph("Cabin Class: "+onwardFlightList.get(v).getCabinClass(), commonNormalFont_7));
				    flightcell_4_2.setBorder(Rectangle.NO_BORDER);
				    flightcell_4_2.setHorizontalAlignment(Element.ALIGN_LEFT);
				    flightcell_4_2.setPadding (3.0f);
				    flightcell_4_2.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_4_2);
				    
				    
				    PdfPCell flightcell_0_2 = new PdfPCell (new Paragraph("", commonBoldFont_6));
				    flightcell_0_2.setBorder(Rectangle.NO_BORDER);
				    flightcell_0_2.setHorizontalAlignment (Element.ALIGN_LEFT);
				    flightcell_0_2.setPadding (3.0f);
				    flightcell_0_2.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_0_2);
				    
		    		PdfPCell flightcell_1_3 = new PdfPCell (new Paragraph("", commonNormalFont_7));
		    		flightcell_1_3.setBorder(Rectangle.NO_BORDER);
		    		flightcell_1_3.setHorizontalAlignment(Element.ALIGN_LEFT);
		    		flightcell_1_3.setPadding (3.0f);
		    		flightcell_1_3.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_1_3);
				    
				   PdfPCell flightcell_2_3 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getScheduledDepartureDate()+" "+onwardFlightList.get(v).getScheduledDeparture(), commonNormalFont_7));
				    flightcell_2_3.setBorder(Rectangle.NO_BORDER);
				    flightcell_2_3.setHorizontalAlignment(Element.ALIGN_LEFT);
				    flightcell_2_3.setPadding (3.0f);
				    flightcell_2_3.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_2_3);
				    
				   PdfPCell flightcell_3_3 = new PdfPCell (new Paragraph(onwardFlightList.get(v).getScheduledArrivalDate()+" "+onwardFlightList.get(v).getScheduledArrival(), commonNormalFont_7));
				    flightcell_3_3.setBorder(Rectangle.NO_BORDER);
				    flightcell_3_3.setHorizontalAlignment(Element.ALIGN_LEFT);
				    flightcell_3_3.setPadding (3.0f);
				    flightcell_3_3.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_3_3);
					    
				    PdfPCell flightcell_4_3 = new PdfPCell (new Paragraph("Booking Class: "+onwardFlightList.get(v).getBookingClass(), commonNormalFont_7));
				    flightcell_4_3.setBorder(Rectangle.NO_BORDER);
				    flightcell_4_3.setHorizontalAlignment(Element.ALIGN_LEFT);
				    flightcell_4_3.setPadding (3.0f);
				    flightcell_4_3.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_4_3);
				    
				    
				    PdfPCell flightcell_0_3 = new PdfPCell (new Paragraph("", commonBoldFont_6));
				    flightcell_0_3.setBorder(Rectangle.NO_BORDER);
				    flightcell_0_3.setHorizontalAlignment (Element.ALIGN_LEFT);
				    flightcell_0_3.setPadding (3.0f);
				    flightcell_0_3.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_0_3);
			    
		    		PdfPCell flightcell_1_4 = new PdfPCell (new Paragraph("", commonNormalFont_7));
		    		flightcell_1_4.setBorder(Rectangle.NO_BORDER);
		    		flightcell_1_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		    		flightcell_1_4.setPadding (3.0f);
		    		flightcell_1_4.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_1_4);
				    
				   PdfPCell flightcell_2_4 = new PdfPCell (new Paragraph("Terminal: "+onwardFlightList.get(v).getDepartTerminal(), commonNormalFont_7));
				    flightcell_2_4.setBorder(Rectangle.NO_BORDER);
				    flightcell_2_4.setHorizontalAlignment(Element.ALIGN_LEFT);
				    flightcell_2_4.setPadding (3.0f);
				    flightcell_2_4.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_2_4);
				    
				    PdfPCell flightcell_3_4 = new PdfPCell (new Paragraph("Terminal: "+onwardFlightList.get(v).getArrivTerminal(), commonNormalFont_7));
				    flightcell_3_4.setBorder(Rectangle.NO_BORDER);
				    flightcell_3_4.setHorizontalAlignment(Element.ALIGN_LEFT);
				    flightcell_3_4.setPadding (3.0f);
				    flightcell_3_4.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_3_4);
					    
				    PdfPCell flightcell_4_4 = new PdfPCell (new Paragraph("Duration: "+onwardFlightList.get(v).getSectorDuration(), commonNormalFont_7));
				    flightcell_4_4.setBorder(Rectangle.NO_BORDER);
				    flightcell_4_4.setHorizontalAlignment(Element.ALIGN_LEFT);
				    flightcell_4_4.setPadding (3.0f);
				    flightcell_4_4.setBackgroundColor(flightColor);
				    flightDetailTable.addCell(flightcell_4_4);
			    } //End of onwardFlightList loop
		    }
				    
	        
	        maintable.addCell(flightDetailTable);
		    
		    PdfPTable passengerContactDetailTable = new PdfPTable(4);
		    
		    passengerContactDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		    
		    float[] passengerContactColumnWidths = new float[] {25f, 14f, 25f, 13f};
		    
		    passengerContactDetailTable.setWidths(passengerContactColumnWidths);
		    
	
		java.util.List<GetAttPassengerInfoModel> passengerContactElementList = airTicketDetailsXml.getPassengerInfo();
		
		    
		    PdfPCell paxCell_0_01 = new PdfPCell (new Paragraph("Passenger Contact Details", commonBoldFont_10));
		    paxCell_0_01.setBorder(Rectangle.NO_BORDER);
		    paxCell_0_01.setColspan(4);
		    paxCell_0_01.setBackgroundColor(flightColor);
		    paxCell_0_01.setHorizontalAlignment (Element.ALIGN_LEFT);
		    paxCell_0_01.setPadding (8.0f);
		    passengerContactDetailTable.addCell(paxCell_0_01);
		    
		    PdfPCell paxCell_0_1 = new PdfPCell (new Paragraph("Name", commonBoldFont_7));
		    paxCell_0_1.setBorder(Rectangle.NO_BORDER);
		    paxCell_0_1.setBackgroundColor(flightColor);
		    paxCell_0_1.setHorizontalAlignment (Element.ALIGN_LEFT);
		    paxCell_0_1.setPadding (5.0f);
		    passengerContactDetailTable.addCell(paxCell_0_1);
		    
		    PdfPCell paxCell_0_2 = new PdfPCell (new Paragraph("Mobile Number", commonBoldFont_7));
		    paxCell_0_2.setBorder(Rectangle.NO_BORDER);
		    paxCell_0_2.setBackgroundColor(flightColor);
		    paxCell_0_2.setHorizontalAlignment (Element.ALIGN_LEFT);
		    paxCell_0_2.setPadding (5.0f);
		    passengerContactDetailTable.addCell(paxCell_0_2);
		    
		    PdfPCell paxCell_0_3 = new PdfPCell (new Paragraph("Email", commonBoldFont_7));
		    paxCell_0_3.setBorder(Rectangle.NO_BORDER);
		    paxCell_0_3.setBackgroundColor(flightColor);
		    paxCell_0_3.setHorizontalAlignment (Element.ALIGN_LEFT);
		    paxCell_0_3.setPadding (5.0f);
		    passengerContactDetailTable.addCell(paxCell_0_3);
		    
		    PdfPCell paxCell_0_4 = new PdfPCell (new Paragraph("Endorsements", commonBoldFont_7));
		    paxCell_0_4.setBorder(Rectangle.NO_BORDER);
		    paxCell_0_4.setBackgroundColor(flightColor);
		    paxCell_0_4.setHorizontalAlignment (Element.ALIGN_LEFT);
		    paxCell_0_4.setPadding (5.0f);
		    passengerContactDetailTable.addCell(paxCell_0_4);
		    
		    
		  
	        
		    for(int v = 0; v < passengerContactElementList.size(); v++)
		    {
		    	
		    	
		    	if(passengerContactElementList.get(v).getLeadPassanger()==0)
		    	{
		      		PdfPCell paxCell_1_1 = new PdfPCell (new Paragraph(passengerContactElementList.get(v).getTitle()
		      				+". "+passengerContactElementList.get(v).getFirstName()+" "+passengerContactElementList.get(v).getMiddleName()+" "+passengerContactElementList.get(v).getLastName(), commonBoldFont_8));
		      		paxCell_1_1.setBorder(Rectangle.NO_BORDER);
		      		paxCell_1_1.setHorizontalAlignment (Element.ALIGN_LEFT);
		      		paxCell_1_1.setPadding (5.0f);
		      		paxCell_1_1.setBackgroundColor(flightColor);
		      		passengerContactDetailTable.addCell(paxCell_1_1);
			    
		    		PdfPCell paxCell_1_2 = new PdfPCell (new Paragraph(passengerContactElementList.get(v).getMobileNo(), commonBoldFont_8));
		    		paxCell_1_2.setBorder(Rectangle.NO_BORDER);
		    		paxCell_1_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		    		paxCell_1_2.setPadding (5.0f);
		    		paxCell_1_2.setBackgroundColor(flightColor);
		    		passengerContactDetailTable.addCell(paxCell_1_2);				    		
		    		
		    		PdfPCell paxCell_1_3 = new PdfPCell (new Paragraph(passengerContactElementList.get(v).getEmailId(), commonBoldFont_8));
		    		paxCell_1_3.setBorder(Rectangle.NO_BORDER);
		    		paxCell_1_3.setHorizontalAlignment(Element.ALIGN_LEFT);
		    		paxCell_1_3.setPadding (5.0f);
		    		paxCell_1_3.setBackgroundColor(flightColor);
		    		passengerContactDetailTable.addCell(paxCell_1_3);
		    		
		    		PdfPCell paxCell_1_4 = new PdfPCell (new Paragraph("-", commonBoldFont_8));
		    		paxCell_1_4.setBorder(Rectangle.NO_BORDER);
		    		paxCell_1_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		    		paxCell_1_4.setPadding (5.0f);
		    		paxCell_1_4.setBackgroundColor(flightColor);
		    		passengerContactDetailTable.addCell(paxCell_1_4);
		    		
		    	}
		    } //End of Passenger loop
			    
			    
		    maintable.addCell(passengerContactDetailTable);
		    
		    //End: Passenger contact detail
		    
		    PdfPTable passengerDetailTable = new PdfPTable(5);
		    
		    passengerDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		    
		    float[] passengerColumnWidths = new float[] {18f, 14f, 17f, 10f, 13f};
		    
		    passengerDetailTable.setWidths(passengerColumnWidths);
	
		    java.util.List<GetAttPassengerInfoModel> passengerElementList = airTicketDetailsXml.getPassengerInfo();
		    
		    PdfPCell paxCell_0 = new PdfPCell (new Paragraph("Traveller Details", commonBoldFont_10));
		    paxCell_0.setBorder(Rectangle.NO_BORDER);
		    paxCell_0.setColspan(7);
		    paxCell_0.setBackgroundColor(flightColor);
		    paxCell_0.setHorizontalAlignment (Element.ALIGN_LEFT);
		    paxCell_0.setPadding (8.0f);
		    passengerDetailTable.addCell(paxCell_0);
		    
		    PdfPCell paxCell = new PdfPCell (new Paragraph("Pax Name", commonBoldFont_7));
		    paxCell.setBorder(Rectangle.NO_BORDER);
		    paxCell.setBackgroundColor(flightColor);
		    paxCell.setHorizontalAlignment (Element.ALIGN_LEFT);
		    paxCell.setPadding (5.0f);
		    passengerDetailTable.addCell(paxCell);
		    
		    PdfPCell paxCell_1 = new PdfPCell (new Paragraph("Traveller Type", commonBoldFont_7));
		    paxCell_1.setBorder(Rectangle.NO_BORDER);
		    paxCell_1.setBackgroundColor(flightColor);
		    paxCell_1.setHorizontalAlignment (Element.ALIGN_LEFT);
		    paxCell_1.setPadding (5.0f);
		    passengerDetailTable.addCell(paxCell_1);
		    
		    
		    
		    PdfPCell paxCell_4 = new PdfPCell (new Paragraph("Ticket Number", commonBoldFont_7));
		    paxCell_4.setBorder(Rectangle.NO_BORDER);
		    paxCell_4.setBackgroundColor(flightColor);
		    paxCell_4.setHorizontalAlignment (Element.ALIGN_LEFT);
		    paxCell_4.setPadding (5.0f);
		    passengerDetailTable.addCell(paxCell_4);
		    
		    PdfPCell paxCell_5 = new PdfPCell (new Paragraph("Airline PNR", commonBoldFont_7));
		    paxCell_5.setBorder(Rectangle.NO_BORDER);
		    paxCell_5.setBackgroundColor(flightColor);
		    paxCell_5.setHorizontalAlignment (Element.ALIGN_LEFT);
		    paxCell_5.setPadding (5.0f);
		    passengerDetailTable.addCell(paxCell_5);
		    
		    PdfPCell paxCell_6 = new PdfPCell (new Paragraph("GDS PNR", commonBoldFont_7));
		    paxCell_6.setBorder(Rectangle.NO_BORDER);
		    paxCell_6.setBackgroundColor(flightColor);
		    paxCell_6.setHorizontalAlignment (Element.ALIGN_LEFT);
		    paxCell_6.setPadding (5.0f);
		    passengerDetailTable.addCell(paxCell_6);
		    
		    
		 
		    
		    
		    
		    for(int v = 0; v < passengerElementList.size(); v++)
		    {
		    	
	    	
	      		PdfPCell paxCell_0_0 = new PdfPCell (new Paragraph(passengerElementList.get(v).getTitle()+". "+
	      				passengerElementList.get(v).getFirstName()+" "+passengerElementList.get(v).getMiddleName()+" "+passengerElementList.get(v).getLastName(), commonBoldFont_8));
	    		paxCell_0_0.setBorder(Rectangle.NO_BORDER);
	      		paxCell_0_0.setHorizontalAlignment (Element.ALIGN_LEFT);
	      		paxCell_0_0.setPadding (5.0f);
	      		paxCell_0_0.setBackgroundColor(flightColor);
	      		passengerDetailTable.addCell(paxCell_0_0);
		    
	    		PdfPCell paxCell_1_0 = new PdfPCell (new Paragraph(passengerElementList.get(v).getPassCategory(), commonBoldFont_8));
	    		paxCell_1_0.setBorder(Rectangle.NO_BORDER);
	    		paxCell_1_0.setHorizontalAlignment(Element.ALIGN_LEFT);
	    		paxCell_1_0.setPadding (5.0f);
	    		paxCell_1_0.setBackgroundColor(flightColor);
	    		passengerDetailTable.addCell(paxCell_1_0);
	    		
	    		
	    	    PdfPCell paxCell_4_0 = new PdfPCell (new Paragraph(passengerElementList.get(v).getTicketNumber(), commonBoldFont_8));
	    		paxCell_4_0.setBorder(Rectangle.NO_BORDER);
	    		paxCell_4_0.setHorizontalAlignment(Element.ALIGN_LEFT);
	    		paxCell_4_0.setPadding (5.0f);
	    		paxCell_4_0.setBackgroundColor(flightColor);
	    		passengerDetailTable.addCell(paxCell_4_0);
	    		
	    		PdfPCell paxCell_5_0 = new PdfPCell (new Paragraph(airLinePNR, commonBoldFont_8));
	    		paxCell_5_0.setBorder(Rectangle.NO_BORDER);
	    		paxCell_5_0.setHorizontalAlignment(Element.ALIGN_LEFT);
	    		paxCell_5_0.setPadding (5.0f);
	    		paxCell_5_0.setBackgroundColor(flightColor);
	    		passengerDetailTable.addCell(paxCell_5_0);
			   
	    		PdfPCell paxCell_6_0 = new PdfPCell (new Paragraph(gdsPNR, commonBoldFont_8));
	    		paxCell_6_0.setBorder(Rectangle.NO_BORDER);
	    		paxCell_6_0.setHorizontalAlignment(Element.ALIGN_LEFT);
	    		paxCell_6_0.setPadding (5.0f);
	    		paxCell_6_0.setBackgroundColor(flightColor);
	    		passengerDetailTable.addCell(paxCell_6_0);
		    

		    } //End of Passenger loop
		    
		    
		    maintable.addCell(passengerDetailTable);
		    
		    PdfPTable fareDetailTable = new PdfPTable(8);
		    
		    fareDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		    
		    float[] fareColumnWidths = new float[] {10f, 30f, 9f, 8f, 8f, 10f, 10f, 15f};
		    
		    fareDetailTable.setWidths(fareColumnWidths);

		  
		    float fuelCharge=0;float totalInvoice=0;
		    float gstTax=0;float otherTax=0;
		    GetAttInvoiceInfoModel invoiceElement = airTicketDetailsXml.getInvoiceInfoModel();
       
            try{
          
		   
		    	fuelCharge=(float) invoiceElement.getFuleCharge();
		    	gstTax=(float) invoiceElement.getGstTax();
		    	otherTax=(float) invoiceElement.getOtherTax();
		        totalInvoice=(float) invoiceElement.getTotalInvoice();
		        
		    }catch(NullPointerException npe){
		    	
		    	DODLog.printStackTrace(npe, AirEmailTicketService.class,LogConstant.EMAIL_PDF_LOG_FILE);
		    }
	    		
            PdfPCell fareCell_0 = new PdfPCell (new Paragraph("Billing Details", commonBoldFont_10));
		    fareCell_0.setBorder(Rectangle.NO_BORDER);
		    fareCell_0.setColspan(8);
		    fareCell_0.setBackgroundColor(flightColor);
		    fareCell_0.setHorizontalAlignment (Element.ALIGN_LEFT);
		    fareCell_0.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_0);
		    
		    PdfPCell fareCell_1 = new PdfPCell (new Paragraph("Base Fare :", commonNormalFont_7));
		    fareCell_1.setColspan(7);
		    fareCell_1.setBorder(Rectangle.NO_BORDER);
		    fareCell_1.setBackgroundColor(flightColor);
		    fareCell_1.setHorizontalAlignment (Element.ALIGN_RIGHT);
		    fareCell_1.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_1);
		    
		    PdfPCell fareCell_2 = new PdfPCell (new Paragraph((float) invoiceElement.getBaseFare(),"", commonNormalFont_7));
		    fareCell_2.setBorder(Rectangle.NO_BORDER);
		    fareCell_2.setBackgroundColor(flightColor);
		    fareCell_2.setHorizontalAlignment (Element.ALIGN_LEFT);
		    fareCell_2.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_2);
		    
		    
		    PdfPCell fareCell_5 = new PdfPCell (new Paragraph("Fuel Charges :", commonNormalFont_7));
		    fareCell_5.setColspan(7);
		    fareCell_5.setBorder(Rectangle.NO_BORDER);
		    fareCell_5.setBackgroundColor(flightColor);
		    fareCell_5.setHorizontalAlignment (Element.ALIGN_RIGHT);
		    fareCell_5.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_5);
		    
		    PdfPCell fareCell_6 = new PdfPCell (new Paragraph(fuelCharge,"", commonNormalFont_7));
		    fareCell_6.setBorder(Rectangle.NO_BORDER);
		    fareCell_6.setBackgroundColor(flightColor);
		    fareCell_6.setHorizontalAlignment (Element.ALIGN_LEFT);
		    fareCell_6.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_6);
		    
		    PdfPCell fareCell_13 = new PdfPCell (new Paragraph("GST :", commonNormalFont_7));
		    fareCell_13.setColspan(7);
		    fareCell_13.setBorder(Rectangle.NO_BORDER);
		    fareCell_13.setBackgroundColor(flightColor);
		    fareCell_13.setHorizontalAlignment (Element.ALIGN_RIGHT);
		    fareCell_13.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_13);
		    
		    PdfPCell fareCell_14 = new PdfPCell (new Paragraph(gstTax,"", commonNormalFont_7));
		    fareCell_14.setBorder(Rectangle.NO_BORDER);
		    fareCell_14.setBackgroundColor(flightColor);
		    fareCell_14.setHorizontalAlignment (Element.ALIGN_LEFT);
		    fareCell_14.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_14); 
		    
		    PdfPCell fareCell_15 = new PdfPCell (new Paragraph("Other Tax :", commonNormalFont_7));
		    fareCell_15.setColspan(7);
		    fareCell_15.setBorder(Rectangle.NO_BORDER);
		    fareCell_15.setBackgroundColor(flightColor);
		    fareCell_15.setHorizontalAlignment (Element.ALIGN_RIGHT);
		    fareCell_15.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_15);
		    
		    PdfPCell fareCell_16 = new PdfPCell (new Paragraph(otherTax,"", commonNormalFont_7));
		    fareCell_16.setBorder(Rectangle.NO_BORDER);
		    fareCell_16.setBackgroundColor(flightColor);
		    fareCell_16.setHorizontalAlignment (Element.ALIGN_LEFT);
		    fareCell_16.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_16);
		    
		    
		    PdfPCell fareCell_19 = new PdfPCell (new Paragraph("Grand Total :", commonBoldFont_7_red));
		    fareCell_19.setColspan(7);
		    fareCell_19.setBorder(Rectangle.NO_BORDER);
		    fareCell_19.setBackgroundColor(flightColor);
		    fareCell_19.setHorizontalAlignment (Element.ALIGN_RIGHT);
		    fareCell_19.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_19);
		    
		    PdfPCell fareCell_20 = new PdfPCell (new Paragraph(totalInvoice,"", commonBoldFont_7_red));
		    fareCell_20.setBorder(Rectangle.NO_BORDER);
		    fareCell_20.setBackgroundColor(flightColor);
		    fareCell_20.setHorizontalAlignment (Element.ALIGN_LEFT);
		    fareCell_20.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_20);
		    
		    
		    PdfPCell fareCell_21 = new PdfPCell (new Paragraph("GST No. :", commonNormalFont_7));
		    fareCell_21.setColspan(1);
		    fareCell_21.setBorder(Rectangle.NO_BORDER);
		    fareCell_21.setBackgroundColor(flightColor);
		    fareCell_21.setHorizontalAlignment (Element.ALIGN_RIGHT);
		    fareCell_21.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_21);
		    
		    PdfPCell fareCell_22 = new PdfPCell (new Paragraph(airLineGST, commonBoldFont_7_red));
		    fareCell_22.setColspan(4);
		    fareCell_22.setBorder(Rectangle.NO_BORDER);
		    fareCell_22.setBackgroundColor(flightColor);
		    fareCell_22.setHorizontalAlignment (Element.ALIGN_LEFT);
		    fareCell_22.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_22);
		    
		    
		    PdfPCell fareCell_23 = new PdfPCell (new Paragraph("Fare Type :", commonNormalFont_7));
		    fareCell_23.setColspan(2);
		    fareCell_23.setBorder(Rectangle.NO_BORDER);
		    fareCell_23.setBackgroundColor(flightColor);
		    fareCell_23.setHorizontalAlignment (Element.ALIGN_RIGHT);
		    fareCell_23.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_23);
		    
		    PdfPCell fareCell_24 = new PdfPCell (new Paragraph("isRefundable" +" *", commonBoldFont_7_red));
		    fareCell_24.setColspan(1);
		    fareCell_24.setBorder(Rectangle.NO_BORDER);
		    fareCell_24.setBackgroundColor(flightColor);
		    fareCell_24.setHorizontalAlignment (Element.ALIGN_LEFT);
		    fareCell_24.setPadding (5.0f);
		    fareDetailTable.addCell(fareCell_24);
		    
		    maintable.addCell(fareDetailTable);
            
		    //Start: Passenger Refund Details 
			    
 		     java.util.List<GetAttPaxCanInvoiceInfoModel> passengerRefundElementList = airTicketDetailsXml.getPaxCanInvoiceInfo();
 		      
 		      
			    if(null != passengerRefundElementList)
			    {
			    
			    PdfPTable passengerRefundDetailTable = new PdfPTable(8);
			    
			    passengerRefundDetailTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			    float[] passengerRefundColumnWidths = new float[] {13f, 13f, 13f, 13f, 13f, 13f, 13f, 13f};
			    passengerRefundDetailTable.setWidths(passengerRefundColumnWidths);
			    
			    
			    
			    PdfPCell refCell_0_01 = new PdfPCell (new Paragraph("Refund Details", commonBoldFont_10));
			    refCell_0_01.setBorder(Rectangle.NO_BORDER);
			    refCell_0_01.setColspan(9);
			    refCell_0_01.setBackgroundColor(flightColor);
			    refCell_0_01.setHorizontalAlignment (Element.ALIGN_LEFT);
			    refCell_0_01.setPadding (5.0f);
			    passengerRefundDetailTable.addCell(refCell_0_01);
			    
			    PdfPCell refCell_0_1 = new PdfPCell (new Paragraph("Traveler Name", commonBoldFont_7));
			    refCell_0_1.setBorder(Rectangle.NO_BORDER);
			    refCell_0_1.setBackgroundColor(flightColor);
			    refCell_0_1.setHorizontalAlignment (Element.ALIGN_LEFT);
			    refCell_0_1.setPadding (5.0f);
			    passengerRefundDetailTable.addCell(refCell_0_1);
			    
			    PdfPCell refCell_0_2 = new PdfPCell (new Paragraph("Pax ID", commonBoldFont_7));
			    refCell_0_2.setBorder(Rectangle.NO_BORDER);
			    refCell_0_2.setBackgroundColor(flightColor);
			    refCell_0_2.setHorizontalAlignment (Element.ALIGN_LEFT);
			    refCell_0_2.setPadding (5.0f);
			    passengerRefundDetailTable.addCell(refCell_0_2);
			    
			    PdfPCell refCell_0_3 = new PdfPCell (new Paragraph("Cancelled Txn ID", commonBoldFont_7));
			    refCell_0_3.setBorder(Rectangle.NO_BORDER);
			    refCell_0_3.setBackgroundColor(flightColor);
			    refCell_0_3.setHorizontalAlignment (Element.ALIGN_LEFT);
			    refCell_0_3.setPadding (5.0f);
			    passengerRefundDetailTable.addCell(refCell_0_3);
			    
			    PdfPCell refCell_0_4 = new PdfPCell (new Paragraph("Refund Status", commonBoldFont_7));
			    refCell_0_4.setBorder(Rectangle.NO_BORDER);
			    refCell_0_4.setBackgroundColor(flightColor);
			    refCell_0_4.setHorizontalAlignment (Element.ALIGN_LEFT);
			    refCell_0_4.setPadding (5.0f);
			    passengerRefundDetailTable.addCell(refCell_0_4);
			    
			    PdfPCell refCell_0_5 = new PdfPCell (new Paragraph("Refund Invoice ID", commonBoldFont_7));
			    refCell_0_5.setBorder(Rectangle.NO_BORDER);
			    refCell_0_5.setBackgroundColor(flightColor);
			    refCell_0_5.setHorizontalAlignment (Element.ALIGN_LEFT);
			    refCell_0_5.setPadding (5.0f);
			    passengerRefundDetailTable.addCell(refCell_0_5);
			    
			    PdfPCell refCell_0_6 = new PdfPCell (new Paragraph("Refunded Date", commonBoldFont_7));
			    refCell_0_6.setBorder(Rectangle.NO_BORDER);
			    refCell_0_6.setBackgroundColor(flightColor);
			    refCell_0_6.setHorizontalAlignment (Element.ALIGN_LEFT);
			    refCell_0_6.setPadding (5.0f);
			    passengerRefundDetailTable.addCell(refCell_0_6);
			    
			    PdfPCell refCell_0_7 = new PdfPCell (new Paragraph("Refunded Amount", commonBoldFont_7));
			    refCell_0_7.setBorder(Rectangle.NO_BORDER);
			    refCell_0_7.setBackgroundColor(flightColor);
			    refCell_0_7.setHorizontalAlignment (Element.ALIGN_LEFT);
			    refCell_0_7.setPadding (5.0f);
			    passengerRefundDetailTable.addCell(refCell_0_7);
			    
			    PdfPCell refCell_0_8 = new PdfPCell (new Paragraph("Cancellation Date", commonBoldFont_7));
			    refCell_0_8.setBorder(Rectangle.NO_BORDER);
			    refCell_0_8.setBackgroundColor(flightColor);
			    refCell_0_8.setHorizontalAlignment (Element.ALIGN_LEFT);
			    refCell_0_8.setPadding (5.0f);
			    passengerRefundDetailTable.addCell(refCell_0_8);
			    
			    
			   
			    
			    
			    
			    for(int v = 0; v < passengerRefundElementList.size(); v++){
			    	
			    	
			    	    PdfPCell refCell_1_0 = new PdfPCell (new Paragraph(passengerRefundElementList.get(v).getPassangerFullName(),commonNormalFont_7));
			    	    refCell_1_0.setBorder(Rectangle.NO_BORDER);
			    	    refCell_1_0.setBackgroundColor(flightColor);
			    	    refCell_1_0.setHorizontalAlignment (Element.ALIGN_LEFT);
			    	    refCell_1_0.setPadding (5.0f);
					    passengerRefundDetailTable.addCell(refCell_1_0);
					    
				    	PdfPCell refCell_1_01 = new PdfPCell (new Paragraph(passengerRefundElementList.get(v).getOnwardPaxId(),commonNormalFont_7));
				    	refCell_1_01.setBorder(Rectangle.NO_BORDER);
				    	refCell_1_01.setHorizontalAlignment (Element.ALIGN_LEFT);
				    	refCell_1_01.setPadding (5.0f);
				    	refCell_1_01.setBackgroundColor(flightColor);
			      		passengerRefundDetailTable.addCell(refCell_1_01);
			      		
			      		PdfPCell refCell_1_02 = new PdfPCell (new Paragraph(passengerRefundElementList.get(v).getCancellationTaxId(),commonNormalFont_7));
			      		refCell_1_02.setBorder(Rectangle.NO_BORDER);
			      		refCell_1_02.setHorizontalAlignment (Element.ALIGN_LEFT);
			      		refCell_1_02.setPadding (5.0f);
			      		refCell_1_02.setBackgroundColor(flightColor);
			      		passengerRefundDetailTable.addCell(refCell_1_02);
			      		
			      		PdfPCell refCell_1_03 = new PdfPCell (new Paragraph(passengerRefundElementList.get(v).getInvoiceStatus(),"",commonNormalFont_7));
			      		refCell_1_03.setBorder(Rectangle.NO_BORDER);
			      		refCell_1_03.setHorizontalAlignment (Element.ALIGN_LEFT);
			      		refCell_1_03.setPadding (5.0f);
			      		refCell_1_03.setBackgroundColor(flightColor);
			      		passengerRefundDetailTable.addCell(refCell_1_03);
			      		
			      		
			      		PdfPCell refCell_1_04 = new PdfPCell (new Paragraph(passengerRefundElementList.get(v).getCreditNoteNo(),commonNormalFont_7));
			      		refCell_1_04.setBorder(Rectangle.NO_BORDER);
			      		refCell_1_04.setHorizontalAlignment (Element.ALIGN_LEFT);
			      		refCell_1_04.setPadding (5.0f);
			      		refCell_1_04.setBackgroundColor(flightColor);
			      		passengerRefundDetailTable.addCell(refCell_1_04);
			      		
			      		
			      		PdfPCell refCell_1_05 = new PdfPCell (new Paragraph(passengerRefundElementList.get(v).getCreditNoteDate(),commonNormalFont_7));
			      		refCell_1_05.setBorder(Rectangle.NO_BORDER);
			      		refCell_1_05.setHorizontalAlignment (Element.ALIGN_LEFT);
			      		refCell_1_05.setPadding (5.0f);
			      		refCell_1_05.setBackgroundColor(flightColor);
			      		passengerRefundDetailTable.addCell(refCell_1_05);
			      		
			      		
			      		PdfPCell refCell_1_06 = new PdfPCell (new Paragraph((float) passengerRefundElementList.get(v).getCalTotalRefund(),"",commonNormalFont_7));
			      		refCell_1_06.setBorder(Rectangle.NO_BORDER);
			      		refCell_1_06.setHorizontalAlignment (Element.ALIGN_LEFT);
			      		refCell_1_06.setPadding (5.0f);
			      		refCell_1_06.setBackgroundColor(flightColor);
			      		passengerRefundDetailTable.addCell(refCell_1_06);
			      		
			        	PdfPCell refCell_1_07 = new PdfPCell (new Paragraph(passengerRefundElementList.get(v).getCancellationDate(),commonNormalFont_7));
			      		refCell_1_07.setBorder(Rectangle.NO_BORDER);
			      		refCell_1_07.setHorizontalAlignment (Element.ALIGN_LEFT);
			      		refCell_1_07.setPadding (5.0f);
			      		refCell_1_07.setBackgroundColor(flightColor);
			      		passengerRefundDetailTable.addCell(refCell_1_07);
			    	
			    } 
			    maintable.addCell(passengerRefundDetailTable);
			    
			 }
	    }
 		    
 		    
 		   //End: Passenger Refund detail
		    
		    
		    PdfPTable blankTable = new PdfPTable(1);
		    blankTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		    
		    PdfPCell blankTableCell_0 = new PdfPCell (new Paragraph("DTS Air Helpline", commonBoldFont_7));
		    blankTableCell_0.setBorder(Rectangle.NO_BORDER);
		    blankTableCell_0.setBackgroundColor(flightColor);
		    blankTableCell_0.setHorizontalAlignment (Element.ALIGN_LEFT);
		    blankTableCell_0.setPadding (3.0f);
		    blankTable.addCell(blankTableCell_0);
		    
		    PdfPCell blankTableCell_01 = new PdfPCell (new Paragraph("For Queries (Timings 09:30 AM to 06:00 PM) Monday to Saturday", commonNormalFont_7));
		    blankTableCell_01.setBorder(Rectangle.NO_BORDER);
		    blankTableCell_01.setBackgroundColor(flightColor);
		    blankTableCell_01.setHorizontalAlignment (Element.ALIGN_LEFT);
		    blankTableCell_01.setPadding (3.0f);
		    blankTable.addCell(blankTableCell_01);
		    
		    PdfPCell blankTableCell_02 = new PdfPCell (new Paragraph("Call us on: 011-26700300 or E-mail: air.helpdesk@hub.nic.in", commonNormalFont_7));
		    blankTableCell_02.setBorder(Rectangle.NO_BORDER);
		    blankTableCell_02.setBackgroundColor(flightColor);
		    blankTableCell_02.setHorizontalAlignment (Element.ALIGN_LEFT);
		    blankTableCell_02.setPadding (3.0f);
		    blankTable.addCell(blankTableCell_02);
		    
		    PdfPCell blankTableCell_1 = new PdfPCell (new Paragraph("Ashok Travels & Tours Helpline: (Timing: 24x7)", commonBoldFont_7));
		    blankTableCell_1.setBorder(Rectangle.NO_BORDER);
		    blankTableCell_1.setBackgroundColor(flightColor);
		    blankTableCell_1.setHorizontalAlignment (Element.ALIGN_LEFT);
		    blankTableCell_1.setPadding (3.0f);
		    blankTable.addCell(blankTableCell_1);
		    
		    PdfPCell blankTableCell_11 = new PdfPCell (new Paragraph("Call on: 022 48946701/9372818685/8169668269", commonNormalFont_7));
		    blankTableCell_11.setBorder(Rectangle.NO_BORDER);
		    blankTableCell_11.setBackgroundColor(flightColor);
		    blankTableCell_11.setHorizontalAlignment (Element.ALIGN_LEFT);
		    blankTableCell_11.setPadding (3.0f);
		    blankTable.addCell(blankTableCell_11);
		    maintable.addCell(blankTable);
		    
		    PdfPCell blankTableCell_12 = new PdfPCell (new Paragraph("* (after deduction of applicable cancellation charges)", commonBoldFont_7_red));
		    blankTableCell_12.setBorder(Rectangle.NO_BORDER);
		    blankTableCell_12.setBackgroundColor(flightColor);
		    blankTableCell_12.setHorizontalAlignment (Element.ALIGN_LEFT);
		    blankTableCell_12.setPadding (3.0f);
		    maintable.addCell(blankTableCell_12);
		    
		    PdfPTable rulesInforTable = new PdfPTable(1);
		    rulesInforTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		    
		    PdfPCell ruleHeaderCell_0 = new PdfPCell (new Paragraph("Rules and Conditions", commonBoldFont_10));
		    ruleHeaderCell_0.setBorder(Rectangle.NO_BORDER);
		    ruleHeaderCell_0.setBackgroundColor(flightColor);
		    ruleHeaderCell_0.setHorizontalAlignment (Element.ALIGN_LEFT);
		    ruleHeaderCell_0.setPadding (5.0f);
		    rulesInforTable.addCell(ruleHeaderCell_0);
		    
		    PdfPCell listCell = new PdfPCell();
		    listCell.setBorder(Rectangle.NO_BORDER);
		    
		    
	       List orderedList = new List(List.ORDERED,20f);
            orderedList.add(new ListItem("DTS Air Helpline number 011-26700300 is available from 9:30 to 6:00 (Mon – Sat). You may also email your concern at air.helpdesk@hub.nic.in",commonNormalFont_7));
 		    
            orderedList.add(new ListItem("DTS Air Helpline number 011-26700300 is available from 9:30 to 6:00 (Mon – Sat). You may also email your concern at air.helpdesk@hub.nic.in",commonNormalFont_7));
 		    
			    
            
            orderedList.add(new ListItem("Report at the designated Airport terminal as per the recommended reporting time of the Airline. Airline check-in counters closing time varies from airline to airline. Certain Airports have enhanced reporting time due to high security checks.",commonNormalFont_7));
            orderedList.add(new ListItem("Flight Timings are subject to change without prior notice. Please re-check with the Airline prior to departure to avoid any inconvenience.",commonNormalFont_7));
            orderedList.add(new ListItem("For queries such as Departure/Arrival status of Flights, Flight Delays, Terminal Information, Baggage Information, Cancellation Status etc, users may call the respective service provider Customer Care or the Airline’s helpline/counter keeping in view the information printed on the ticket. Users may provide the Ticket Number & Airline/GDS PNR for identification.",commonNormalFont_7));
            orderedList.add(new ListItem("Users should cancel tickets Online through DTS unless it is not feasible to do so. Online cancellation updates the required information in DTS immediately for any further action such as rebooking/refunds.However if the status is not updated within 10 minutes you may kindly call the helpline number to get it updated. Ticket for the same day and sector can only be booked after the status is updated as cancelled.",commonNormalFont_7));
            orderedList.add(new ListItem("In case sufficient time is not remaining for online cancellation of Air Ticket, user may call the Customer Care of Service Provider. Tickets can be cancelled by contacting the Customer Care of Service Providers and not through the Airline Website. Service Provider numbers are as follows:",commonNormalFont_7));
		    
            List sublist = new List(false, false, 10);
            sublist.setListSymbol(new Chunk("", commonBoldFont_6));
            sublist.add(new ListItem("Balmer Lawrie & Co. Ltd.:   0124-4603500/ 0124-6282500",commonBoldFont_8));
            sublist.add(new ListItem("Ashoka Travels and Tours:   022 48946701/9372818685/8169668269",commonBoldFont_8));
            orderedList.add(sublist);
            
            orderedList.add(new ListItem("In case user is not able to contact service provider he may cancel the ticket directly through airlines also on the following airlinenumbers:",commonNormalFont_7));
		    
            List custlist = new List(true, false, 10);
            custlist.setListSymbol(new Chunk("", commonBoldFont_6));
            custlist.add(new ListItem("Air India       :   011-69329333",commonBoldFont_8));
            custlist.add(new ListItem("Akasa       :   09606112131",commonBoldFont_8));
            custlist.add(new ListItem("Indigo          :   0124-6173838 / 0124-4973838",commonBoldFont_8));
            custlist.add(new ListItem("Star Air          :   022-50799555",commonBoldFont_8));
            custlist.add(new ListItem("Spice Jet     :   0124-4983410",commonBoldFont_8));
            custlist.add(new ListItem("Alliance Air     : 044 4255 4255/044 3511 3511",commonBoldFont_8));
            custlist.add(new ListItem("Air India Exp     : 080 4666 2222",commonBoldFont_8));
            orderedList.add(custlist);
            
            
            List custlist_1 = new List(false, false, 10);
            custlist_1.setListSymbol(new Chunk("", commonBoldFont_6));
            custlist_1.add(new ListItem("*Users may also confirm the above numbers from respective websites of Airlines.",commonBoldFont_8));
            orderedList.add(custlist_1);
		    
            orderedList.add(new ListItem("In case of Offline cancellation DTS Air helpline may be intimated via e-mail to update the status of the cancelled Ticket in DTS for processing of refunds and records kept your end for future references.",commonNormalFont_7));
	        orderedList.add(new ListItem("Different Cancellation Timings/Policies are applicable based on the Booking Class (S, T, H, etc) of the Ticket. Users may contact the Airlines or the Service Providers for information regarding cancellation policies. Information regarding Booking Class is available on the final Booking Page and the DTS Air Ticket Printouts.",commonNormalFont_7));
            orderedList.add(new ListItem("If a passenger fails to cancel the travel booking and do not report for travel on time, the Airline will consider such passenger as No Show and refund of fare is not applicable in such cases. The cost is to be borne by the passenger.",commonNormalFont_7));
            orderedList.add(new ListItem("All refunds will be automatically made to Defence Travel System upon cancellation of Air Ticket. Refunds will be processed as per the cancellation policies of the airlines.",commonNormalFont_7));
            orderedList.add(new ListItem("Seat/Meal requests may/may not be realized depending on the participating airline.",commonNormalFont_7));
		    
            listCell.addElement(orderedList);
		    
            rulesInforTable.addCell(listCell);
            
			    
            PdfPCell mandatoryCell = new PdfPCell(new Paragraph("Mandatory: All travelers have to ensure that they must carry a copy of this e-ticket accompanied with a photo identification (ID) card as per requirement of security at the airport. ID Cards may include Driving License, Passport,PAN Card,Voter ID Card, Aadhaar Card or any other ID issued by Government of India. Birth Certificate is a mandatory proof forinfant traveler.", commonBoldFont_8));
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
