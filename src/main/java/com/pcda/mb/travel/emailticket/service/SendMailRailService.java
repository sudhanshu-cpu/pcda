package com.pcda.mb.travel.emailticket.service;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcda.mb.travel.emailticket.model.RailTicketPdfModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

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
import jakarta.servlet.http.HttpServletResponse;

@Service
public class SendMailRailService {

	
	@Autowired
	private RailTicketPDFService railPdfService;
	
	public boolean sendMailRailTicket(String bkpnr,String ticketNo, String mailId, String personalNo, HttpServletRequest request,HttpServletResponse response, String bookingId ) 
	{
		 
		 boolean sendMail=false;
		 
		 RailTicketPdfModel railTicketPdfModel=railPdfService.getRailTicketPdf(bookingId);
	  if(!ticketNo.equals("")&& !bkpnr.equals(""))
	  {
		  	personalNo=personalNo.toUpperCase();
					 
//					  //  String myFile =DODDATAConstants.railBook_mb_mail_template;
//						StringBuffer text_from_mail_template=new StringBuffer();
//						//java.io.FileReader fr = new FileReader(myFile);
//						//java.io.BufferedReader reader = new BufferedReader(fr);
//				        String line = null;
//						 while ((line = reader.readLine()) != null) 
//						 {
//				           text_from_mail_template.append(line);
//				         }
//						 
//				        String mailBody=text_from_mail_template.toString(); 
					    ByteArrayOutputStream baos = new ByteArrayOutputStream();
						{
							if(railTicketPdfModel.getIrctcTktType()==1) {
								 try {
									railPdfService.createITicketPDF(railTicketPdfModel, response, request);
								} catch (Exception e) {
									DODLog.printStackTrace(e, SendMailRailService.class, LogConstant.EMAIL_PDF_LOG_FILE);
								}
							 }else {
							 try {
								railPdfService.createETicketPDF(response, railTicketPdfModel,baos);
							} catch (Exception e) {
								DODLog.printStackTrace(e, SendMailRailService.class, LogConstant.EMAIL_PDF_LOG_FILE);
							}
							 }
						 }
						
						sendMail=mailRailTicketSend(baos,ticketNo,mailId);

					}
					  

		return sendMail;
	}

	
private boolean mailRailTicketSend(ByteArrayOutputStream outputStream, String ticketNo, String mailId) {

	
	String fileName = ticketNo+".pdf";
	
	Address[] to;
    
    Session session = null;
     
      try{
    	  
    	MimeMessage message = new MimeMessage(session);
    	MimeBodyPart textPart = new MimeBodyPart();
    	Multipart mp = new MimeMultipart();
    	MimeMultipart mimeMultipart = new MimeMultipart(); 
    	 
    	message.setFrom();
    	message.setSubject("Rail Ticket: DTS");
    	
	     to = new InternetAddress[] {new InternetAddress(mailId)};
        MimeBodyPart messageBodyPart1 = new MimeBodyPart(); 
    	//messageBodyPart1.setContent(mailBody, "text/html"); 
        
        if (outputStream != null && outputStream instanceof ByteArrayOutputStream) {

        	MimeBodyPart attachment= new MimeBodyPart();
            ByteArrayDataSource ds = new ByteArrayDataSource(outputStream.toByteArray(), "application/pdf"); 
            attachment.setDataHandler(new DataHandler(ds));
            attachment.setFileName(fileName);
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
