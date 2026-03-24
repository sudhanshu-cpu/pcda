package com.pcda.mb.requestdashboard.normalbookingdashboard.controller;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetConfirmIRSearchResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetConfirmResponseParent;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetFinalBookRailTktResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetFinalTicketBookDtls;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetPreConfirmParentModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetPreConfirmResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetRailPreSearchResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetRailPreSrchModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetRailSearchParentModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetTrainSearchResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostFinalBookModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostIRPreConfirmModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostIRSearchConfirmModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostRailPreSearchModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.PostTrainSearchModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.service.NormalBkgDashService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class NormalBkgRailController {


	@Autowired
	private NormalBkgDashService normalService;

	// first time search
	@PostMapping("/getRailTktForm")
	public String getRailTktForm(Model model, HttpServletRequest request, PostRailPreSearchModel preSearchModel) {

		preSearchModel.setRequestId(Base64Coder.decodeString(preSearchModel.getRailRequestId()));
		GetRailPreSearchResponse response = normalService.getPreSearchData(preSearchModel);
		if (response != null && response.getErrorCode() == 200) {
			GetRailPreSrchModel preModel = response.getResponse();
			
			model.addAttribute("preModel", preModel);
			return "/MB/RequestDashbord/normalbookingdashboard/railBookSearchPage";
		}
		if (response != null && response.getErrorCode() != 200) {
			model.addAttribute("errorMessage", response.getErrorMessage());
			 return "/MB/RequestDashbord/normalbookingdashboard/RailBookErrorPage";
		} else {
		return "/common/errorPage";
		}
	}

	// after clicking on search button -again
	@PostMapping("/getRailTrainsData")
	public String getRailTrainsData(Model model, HttpServletRequest request, PostTrainSearchModel trainSearchModel) {
		GetTrainSearchResponse response = normalService.getTrainsInfo(trainSearchModel);
		if (response != null && response.getErrorCode() == 200) {
			GetRailSearchParentModel parentModel = response.getResponse();
			if(parentModel.getIsTatkal().equals("off") || parentModel.getIsTatkal().isBlank()) {
				parentModel.setJrnyQuota("GN");
			}
			if(parentModel.getIsTatkal().equals("on")){
				parentModel.setJrnyQuota("TQ");
			}
			
			if (parentModel.getErrorCode() != null) {
				model.addAttribute("errorMessage", parentModel.getErrorMessage());
				 return "/MB/RequestDashbord/normalbookingdashboard/RailBookErrorPage";
			}

			model.addAttribute("preModel", parentModel);
			model.addAttribute("firstsrchModel", trainSearchModel);
			model.addAttribute("trainInfoList", parentModel.getResponseBean());
			if( parentModel.getResponseBean()==null|| parentModel.getResponseBean().isEmpty()) {
				model.addAttribute("errorMessage", parentModel.getErrorMessage());
			}
			return "/MB/RequestDashbord/normalbookingdashboard/RailTrainsInfoSearchPage";
		} else {
			return "/common/errorPage";
		}

	}

	// pre confirm page data
	@PostMapping("/getIRPreConfirmForm")
	public String getIRPreConfirmForm(PostIRPreConfirmModel preConfirmModel, Model model, HttpServletRequest request) {
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgRailController.class,
				"POST RAIL PRE_CONFIRM MODEL " + preConfirmModel);
		GetPreConfirmResponse response = normalService.getPreConfirmData(preConfirmModel);
		if (response != null && response.getErrorMessage() == null && response.getErrorCode() == 200) {
			GetPreConfirmParentModel parentModel = response.getResponse();
			Collections.sort(parentModel.getSearchReqDtls().getConfirmPaxBeans());
			model.addAttribute("preConfirmData", parentModel);
		} else {
			model.addAttribute("errors",response==null ? "" : response.getErrorMessage());
			return "/common/errorPage";
		}

		 return "/MB/RequestDashbord/normalbookingdashboard/RailPreConfirmPage";
	}

	// After Proceed for Booking confirm rail ticket(step-1) 
	@PostMapping("/confirmIRRail")
	public String getConfirmIRRail(Model model, PostIRSearchConfirmModel irPostModel, HttpServletRequest request) {
		
		SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = visitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		irPostModel.setLoginUserId(loginUser.getUserId());
		irPostModel.setSessionId(request.getSession().getId());
		String ipAddress = request.getRemoteAddr();
		irPostModel.setIpAddress(ipAddress);
			
			
		GetConfirmIRSearchResponse response = normalService.confirmIRSearch(irPostModel, request);
		if (response != null && response.getErrorCode() == 200) {
			GetConfirmResponseParent confrmParent = response.getResponse();

			Map<String, String> requestParam = normalService.modifyHost(confrmParent.getRequestParam(), request);
			
			
			
			Enumeration<String> headerNameList=request.getHeaderNames();
			
			 while (headerNameList.hasMoreElements()) {
				    String headerName = headerNameList.nextElement();
			        String value = request.getHeader(headerName);
			        DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgRailController.class,
							"######### HEAREDS ###### ::" +headerName+"|"+value); 
			    }
			 

			model.addAttribute("urlValue", confrmParent.getIrctcBookingUrl());
			model.addAttribute("paramMap", requestParam);

			return "/MB/RequestDashbord/normalbookingdashboard/crisBookingRequest";
		} else {
			model.addAttribute("errors", response==null ? "" : response.getErrorMessage());
			return "/common/errorPage";
		}

	}
	// After Proceed for Booking confirm rail ticket(step-2(CRIS RESPONSE)) 
	@RequestMapping(value="/crisBookingResponse/{txnId}/{bookingId}", method= {RequestMethod.GET,RequestMethod.POST})
	public String getCrisBkgRespose(@PathVariable String txnId,@PathVariable String bookingId,Model model, HttpServletRequest request) {
		
		String data = request.getParameter("data");
		String clientTxnId = txnId+"/"+bookingId;
		String cancelButton=Optional.ofNullable(request.getParameter("CancelButton")).orElse("N"); 
		DODLog.info(LogConstant.NORMAL_BOOKING_DASHBOARD_LOG_FILE, NormalBkgRailController.class,
				"DATA ::" + data + "CLIENT TRANSACTION-ID ::" + clientTxnId+"| cancelButton:"+cancelButton);
		if (cancelButton.equalsIgnoreCase("Y")) {
			model.addAttribute("errors", "Your booking transaction has been cancelled.");
			return "/common/errorPage";
		}
		clientTxnId=Base64Coder.encodeString(clientTxnId);
		String sessionId=	normalService.getSessionDtls(clientTxnId);
		model.addAttribute("data", data);
		model.addAttribute("clientTxnId", clientTxnId);
		model.addAttribute("sessionId", sessionId);
		return "/MB/RequestDashbord/normalbookingdashboard/crisBookingResponse";
	}

	// After Proceed for Booking confirm rail ticket(step-3) 
	@PostMapping("/getFinalTrainDtls")
	public String getFinalTrainDtls(PostFinalBookModel finalBookModel,Model model,HttpServletRequest request) {
             
		GetFinalBookRailTktResponse  response= normalService.getFinalTrainDtls(finalBookModel);
		if(response!=null && response.getErrorCode()==200) {
			GetFinalTicketBookDtls  parentModel=response.getResponse();
			parentModel.setBoardingDateStr(CommonUtil.formatDate(parentModel.getBoardingDate(), "dd-MM-yyyy"));
			parentModel.setBookingDateStr(CommonUtil.formatDate(parentModel.getBookingDate(),"dd-MM-yyyy"));
			model.addAttribute("ModelData",parentModel);
		}else {
			model.addAttribute("errorMessage",response==null ? "" : response.getErrorMessage());
		}
		return "/MB/RequestDashbord/normalbookingdashboard/NormalBookAfterRailTktBook";
	}

}
