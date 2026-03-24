package com.pcda.mb.travel.emailticket.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.travel.emailticket.model.AirTicketAllData;
import com.pcda.mb.travel.emailticket.model.AirTicketPdfModel;
import com.pcda.mb.travel.emailticket.model.FlightInfo;
import com.pcda.mb.travel.emailticket.model.PassengerInfo;
import com.pcda.mb.travel.emailticket.model.RailTicketPdfModel;
import com.pcda.mb.travel.emailticket.model.TicketFormModel;
import com.pcda.mb.travel.emailticket.service.AirEmailTicketService;
import com.pcda.mb.travel.emailticket.service.MailServiceImpl;
import com.pcda.mb.travel.emailticket.service.RailTicketPDFService;
import com.pcda.mb.travel.emailticket.service.SendMailRailService;
import com.pcda.mb.travel.emailticket.service.SendMailService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/mb")
public class EmailTicketController {
	
	@Autowired
	private AirEmailTicketService emailTicketService;
	
	@Autowired
	private RailTicketPDFService railPdfService;
	
	@Autowired
	private OfficesService officesService;
	
	@Autowired
    private MailServiceImpl mailServiceImpl;
	
	@Autowired
	private SendMailRailService sendMailRailService;
	
	@Autowired
	private SendMailService sendMailService;
	
	String pageUrl="/MB/TravelRequest/emailTicket/emailTicket";
	
	@GetMapping("/emailticket")
	public String getEmailTicket(Model model,HttpServletRequest request) {
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		BigInteger userid = loginUser.getUserId();
		Optional<OfficeModel> officeModel=officesService.getOfficeByUserId(userid);
		
		String groupId="";
		if(officeModel.isPresent()) {
			groupId=officeModel.get().getGroupId();
		}
		model.addAttribute("groupId", groupId);		
		
		model.addAttribute("formData", new TicketFormModel());
		return pageUrl;
	}
	
	@PostMapping("/getData")
	public String getDate(TicketFormModel ticketFormModel,Model model ) {
		
		String prsnlNo = ticketFormModel.getPersonalNo();
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, prsnlNo);
		ticketFormModel.setPersonalNo(decryptPersonalNo);
		
		model.addAttribute("formData", ticketFormModel);
		model.addAttribute("groupId", ticketFormModel.getGroupId());	
		
		if(ticketFormModel.getTravelType().equalsIgnoreCase("air")) {
			List<AirTicketAllData> listAirTicketData = mailServiceImpl.getAllData(ticketFormModel);
			if(listAirTicketData != null && !listAirTicketData.isEmpty()){
				model.addAttribute("airTicket", listAirTicketData);
			}else {
				model.addAttribute("errorMessage", "No Record Found");
			}
		}
		else if(ticketFormModel.getTravelType().equalsIgnoreCase("rail")){
			List<AirTicketAllData> listAirTicketData = mailServiceImpl.getAllData(ticketFormModel);
			if(listAirTicketData != null && !listAirTicketData.isEmpty()){
				model.addAttribute("railTicket", listAirTicketData);
			}else {
				model.addAttribute("errorMessage", "No Record Found");
			}
			
		}
		return pageUrl;
	}
	
	
	
	@RequestMapping(value="/airPdfTicket",method= {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void getAirPdf(String optTxnId,HttpServletRequest request,HttpServletResponse response) {
		 response.setContentType("application/pdf");
		 response.setHeader("Content-Disposition", "attachment; filename=Air_"+optTxnId+".pdf");
		
		 AirTicketPdfModel airTicketPdfModel  = emailTicketService.getAirTicketPdf(optTxnId);
		 
		 List<FlightInfo> flightInfoList = airTicketPdfModel.getFlightInfo();
		 List<PassengerInfo> passengerInfo = airTicketPdfModel.getPassengerInfo();
		 Collections.sort(passengerInfo);
		 Collections.sort(flightInfoList);
		 airTicketPdfModel.setFlightInfo(flightInfoList);
		 airTicketPdfModel.setPassengerInfo(passengerInfo);
		 try {
			 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if(airTicketPdfModel.getServiceProvider() == 0) {
			
			 emailTicketService.airBLTicketPDF(airTicketPdfModel,  baos, "", false);
			
		}
		else if(airTicketPdfModel.getServiceProvider() == 1){
			
			 emailTicketService.createAirATTicketPDF(airTicketPdfModel , baos, "", false);
		}
		
		 ServletOutputStream out  = response.getOutputStream();
			byte[] bytes = baos.toByteArray();
			
			out.write(bytes);
			out.flush();
		        response.flushBuffer();
			
			
		
		 } catch (Exception e) {
				DODLog.printStackTrace(e, EmailTicketController.class, LogConstant.EMAIL_PDF_LOG_FILE);
			}
		
	}

	@PostMapping("/railTicketPDF")
	@ResponseBody
	public void getRailTicketPDFDetails(Model model,String bookingId, HttpServletRequest request,HttpServletResponse response) {
		 
		 response.setHeader("Content-Disposition", "attachment; filename=\"mydoc.pdf\"");
		
		 try {
		 RailTicketPdfModel railTicketPdfModel=railPdfService.getRailTicketPdf(bookingId);
			 
			 if(railTicketPdfModel !=null) {
			 ByteArrayOutputStream baos = new ByteArrayOutputStream();
			 
			 if(railTicketPdfModel.getIrctcTktType()==1) {
				 railPdfService.createITicketPDF(railTicketPdfModel, response, request);
			 }else {
			 railPdfService.createETicketPDF(response, railTicketPdfModel,baos);
			 }
			
			
		 }
		 }
		 catch (Exception e) {
			DODLog.printStackTrace(e, EmailTicketController.class, LogConstant.EMAIL_PDF_LOG_FILE);
		}
		
	}
	
	
	 @PostMapping("/sendEmailRailticket")
	public void sendRailTicketMail(@RequestParam String bkpnr,@RequestParam String personalNo,@RequestParam String mailId,@RequestParam String isUpdateMail, HttpServletRequest request,HttpServletResponse response,String bookingId ){
		

			 boolean flag=sendMailRailService.sendMailRailTicket(bkpnr,bkpnr,mailId,personalNo, request,null, bookingId);
			 String mailResponse="";
				 try
				 {
					 if(flag)
						 {
						 mailResponse="Rail ticket has been sent successfully.";
							 response.getWriter().write(mailResponse);
						 }
					 else 
						 {
						 mailResponse="Kindly check your mail id & try again.";
							 response.getWriter().write(mailResponse);
						 }
				 response.getWriter().flush();
				 response.getWriter().close();
				 
	    		}catch (IOException e) {
	    			
	    			DODLog.printStackTrace(e, EmailTicketController.class, LogConstant.EMAIL_PDF_LOG_FILE);
	    		}  
		
	}
	
	 
	 @PostMapping("/sendAirTicketMail")
	public void sendAirTicketMail(AirTicketPdfModel airTicketDetailsXml,@RequestParam String optTxnId,@RequestParam String personalNo,@RequestParam String mailId, HttpServletRequest request,HttpServletResponse response){

			 boolean flag=sendMailService.sendMailAirTicket(airTicketDetailsXml, mailId, request, personalNo);
			 String mailResponse="";
				 try
				 {
					 if(flag)
						 {
							 mailResponse="Air ticket has been sent successfully.";
							 response.getWriter().write(mailResponse);
						 }
					 else 
						 {
							 mailResponse="Kindly check your mail id & try again.";
							 response.getWriter().write(mailResponse);
						 }
				 response.getWriter().flush();
				 response.getWriter().close();
				 
	    		}catch (IOException e) {
	    			
	    			DODLog.printStackTrace(e, EmailTicketController.class, LogConstant.EMAIL_PDF_LOG_FILE);
	    		}  
		
	}
	 
	
	
}
