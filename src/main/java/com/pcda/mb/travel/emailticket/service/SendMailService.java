package com.pcda.mb.travel.emailticket.service;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcda.mb.travel.emailticket.model.AirTicketPdfModel;

import jakarta.activation.DataHandler;
import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class SendMailService {

	@Autowired
	AirEmailTicketService airEmailTicketService;
	
	@Autowired
	RailTicketPDFService railTicketPDFService;
	
	public boolean sendMailAirTicket(AirTicketPdfModel airTicketDetailsXml	, String mailId,HttpServletRequest request,String personalNo) {
		
		 boolean sendMail=false;
		
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 
		 StringBuffer textfrommailtemplate=new StringBuffer();
		 
		  String mailBody=textfrommailtemplate.toString(); 
		 
			if(airTicketDetailsXml.getServiceProvider()==0)
			{
				
				airEmailTicketService.airBLTicketPDF(airTicketDetailsXml,  baos, personalNo, sendMail);
				 mailBody=mailBody.replaceAll("Service_Provider", " Balmer Lawrie & CO. Ltd. For any assistance you may call Balmer Lawrie helpline at 0124-4603500 / 0124-6282500.");
			}
			else if(airTicketDetailsXml.getServiceProvider()==1)
			{
				
				airEmailTicketService.createAirATTicketPDF(airTicketDetailsXml,  baos, personalNo, sendMail);
				mailBody=mailBody.replaceAll("Service_Provider", " Ashok Travels & Tours For any assistance you may call Ashok Travels and Tours Helpline at: 022-48946701/9372818685/8169668269.");
		        
			}
			
			String requestId="";
			 ByteArrayOutputStream daBAOS =railTicketPDFService.createDAAdvancePDF(requestId,request, personalNo);
			    
			    sendMail=mailAirTicketSend(baos,airTicketDetailsXml.getOperatorTxnId(),mailId,mailBody,daBAOS);

	return sendMail;	
	}

	
	private boolean mailAirTicketSend(ByteArrayOutputStream outputStream, String optTxnId, String mailId, String mailBody, ByteArrayOutputStream daBAOS) {
		
		String fileName = optTxnId+".pdf";
		
        Address[] to;
	    
	    Session session = null;
	      try{
	    	  
		    	MimeMessage message = new MimeMessage(session);
		    	MimeBodyPart textPart = new MimeBodyPart();
		    	Multipart mp = new MimeMultipart();
		    	MimeMultipart mimeMultipart = new MimeMultipart(); 
		    	 
		    	message.setFrom();
		    	message.setSubject("Air Ticket: DTS");
		
		    	
		    	 to = new InternetAddress[] {new InternetAddress(mailId)};
			        MimeBodyPart messageBodyPart1 = new MimeBodyPart(); 
			    	messageBodyPart1.setContent(mailBody, "text/html"); 
			    	
			    	
			    	  if (outputStream != null && outputStream instanceof ByteArrayOutputStream) {

				        	MimeBodyPart attachment= new MimeBodyPart();
				            ByteArrayDataSource ds = new ByteArrayDataSource(outputStream.toByteArray(), "application/pdf"); 
				            attachment.setDataHandler(new DataHandler(ds));
				            attachment.setFileName(fileName);
				            mimeMultipart.addBodyPart(attachment);
				        }
			    	  
			    	  
			    	  
			    	  if (daBAOS != null && daBAOS instanceof ByteArrayOutputStream) {

				        	MimeBodyPart attachment= new MimeBodyPart();
				            ByteArrayDataSource ds = new ByteArrayDataSource(daBAOS.toByteArray(), "application/pdf"); 
				            attachment.setDataHandler(new DataHandler(ds));
				            attachment.setFileName("DAAdvance.pdf");
				            mimeMultipart.addBodyPart(attachment);
				        }
			    	  
			    	  mimeMultipart.addBodyPart(messageBodyPart1);
				        mp.addBodyPart(textPart);
				        message.setRecipients(Message.RecipientType.TO, to);
				        message.setSentDate(new Date());
				        message.setContent(mp);
					    message.setContent(mimeMultipart);
			    	
					    Transport.send(message);
		
		  }catch (MessagingException me) 
			{
			  return false;
	        }
		  catch (Exception e) {
			return false;
		}
		  return true; 
	}

		
	
	
}
