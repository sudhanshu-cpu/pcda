package com.pcda.mb.travel.emailticket.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.MailProperties;
import com.pcda.mb.travel.emailticket.model.AirTicketAllData;
import com.pcda.mb.travel.emailticket.model.AirTicketDataResponse;
import com.pcda.mb.travel.emailticket.model.TicketFormModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class MailServiceImpl {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Autowired
	private JavaMailSender mailSender;

public void sendMail(String toMail,String body,String subjectMail) {
	
	 SimpleMailMessage mailMessage  = new SimpleMailMessage();
	 MailProperties mail=new MailProperties();
	 try {
	 mailMessage.setFrom(mail.getUsername());
	 mailMessage.setTo(toMail);
	 mailMessage.setText(body);
	 mailMessage.setSubject(subjectMail);
	 
	 mailSender.send(mailMessage);
	 
	 DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, MailServiceImpl.class, "Mail send "+mailMessage);
	 }catch (Exception e) {
		 DODLog.printStackTrace(e, MailServiceImpl.class, LogConstant.EMAIL_PDF_LOG_FILE);
	}
//	 MimeMessagePreparator preparator = new MimeMessagePreparator() 
//	    {
//	        public void prepare(MimeMessage mimeMessage) throws Exception 
//	        {
//	 
//	 mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
//     mimeMessage.setFrom(new InternetAddress("shyamsundar222222@gmail.com"));
//     mimeMessage.setSubject(subjectMail);
//     mimeMessage.setText(body);
//	 
//     FileSystemResource file = new FileSystemResource("C:\\Users\\HP\\Downloads");
//     MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//     helper.addAttachment("mydocument.pdf", file);
//	 
//}
//	        
//	    };
//	    try {
//	        mailSender.send(preparator);
//	    }
//	    catch (MailException ex) {
//	        // simply log it and go on...
//	        System.err.println(ex.getMessage());
//	    }
}


public List<AirTicketAllData> getAllData(TicketFormModel ticketFormModel) {
	DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, MailServiceImpl.class, "[getAllData] ticketFormModel ##  "+ticketFormModel);
	List<AirTicketAllData> airDataList= new ArrayList<>();
	
				 
	 Map<String, String> params = new HashMap<>();
	 
	
	 params.put("groupId", ticketFormModel.getGroupId());
	 params.put("travelType", ticketFormModel.getTravelType());
	 params.put("personalNo", ticketFormModel.getPersonalNo());
	 params.put("fbOrPnr", ticketFormModel.getFbOrPnr());
	 params.put("airlinePnr",ticketFormModel.getAirlinePnr());

	String baseUrl= PcdaConstant.EMAIL_BASE_URL+ "/emailTickets?";

	 String url = params.entrySet().stream()		 
				.filter(entry -> !entry.getValue().isEmpty())
			    .map(entry -> entry.getKey() + "=" + entry.getValue())
			    .collect(Collectors.joining("&", baseUrl, ""));	
	 
	 AirTicketDataResponse response = restTemplate.getForObject(url, AirTicketDataResponse.class, params);
		if(response != null && response.getErrorCode()== 200 && null!=response.getResponseList()) {
			airDataList=response.getResponseList();
		}
		DODLog.info(LogConstant.EMAIL_PDF_LOG_FILE, MailServiceImpl.class, "[getAllData] airDataList ##  "+airDataList.size());
	return airDataList;
	 
}




}