package com.pcda.mb.reports.airrequestreport.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.CodeHeadServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.reports.airrequestreport.model.GetAirReqIdResponse;
import com.pcda.mb.reports.airrequestreport.model.GetAirReqRepResponse;
import com.pcda.mb.reports.airrequestreport.model.GetAirRqIdParentModel;
import com.pcda.mb.reports.airrequestreport.model.PostAirReqRepModel;
import com.pcda.mb.reports.airrequestreport.service.AirReqReportService;
import com.pcda.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
@Controller
@RequestMapping("/reports")
public class AirReqReportController {

	
	@Autowired
	private TravelTypeServices travelService;
	
	@Autowired
	private OfficesService offService;
	
	@Autowired
	private CodeHeadServices codeService;
	@Autowired
	private AirReqReportService repService;

	@GetMapping("/getAirReqReport")
	public String getRailReqReport(Model model, HttpServletRequest request) {
		SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = visitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		Optional<OfficeModel> office =  offService.getOfficeByUserId(loginUser.getUserId());
		if(office.isPresent()) {
			model.addAttribute("gropuId",office.get().getGroupId() );
		}
		model.addAttribute("airReqModel",new PostAirReqRepModel());
		model.addAttribute("travelTypeList", travelService.getAllTravelType(1));
		model.addAttribute("accountOfficeList", offService.getOffices("PAO", "1"));
		model.addAttribute("codeHeadList", codeService.getCodeHeadByApproval("1"));
	
		return "/MB/Reports/AirRequestReport/airrequestreport";
	}
	
	@PostMapping("/getAirReqReportData")
	public String getRailReqReportData(PostAirReqRepModel airReqModel , HttpServletRequest request,Model model) {
		SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = visitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}

		String encryptPno = airReqModel.getPersonalNo();
		String secret = "Hidden Pass";
		String decryptPersonalNo = CommonUtil.getDecryptText(secret, encryptPno);
		airReqModel.setPersonalNo(decryptPersonalNo);

		Optional<OfficeModel> office =  offService.getOfficeByUserId(loginUser.getUserId());
		if(office.isPresent()) {
			airReqModel.setGroupID(office.get().getGroupId());
		}
		airReqModel.setFromDate(CommonUtil.formatString(airReqModel.getFromXdate(), "dd-MM-yyyy"));
		airReqModel.setToDate(CommonUtil.formatString(airReqModel.getToXdate(), "dd-MM-yyyy"));
		
	
		GetAirReqRepResponse response =  repService.getAirReqReportData(airReqModel);
		if(response!=null && !response.getResponseList().isEmpty() && response.getErrorCode()==200) {
			model.addAttribute("reportDataList", response.getResponseList());
		}else {
			  model.addAttribute("noRecord", "No Record Found !!");
		}
        model.addAttribute("airReqModel", airReqModel);
		model.addAttribute("travelTypeList", travelService.getAllTravelType(1));
		model.addAttribute("accountOfficeList", offService.getOffices("PAO", "1"));
		model.addAttribute("codeHeadList", codeService.getCodeHeadByApproval("1"));
		
		return "/MB/Reports/AirRequestReport/airrequestreport";
	}
	
@PostMapping("/getAirReqIdData")
public String getReqIdAirData(@RequestParam String requestId,Model model) {
	GetAirReqIdResponse response = repService.getAirReIdData(requestId);
if(response!=null && response.getErrorCode()==200&& response.getResponse()!=null) {
	GetAirRqIdParentModel parentModel = response.getResponse();
	model.addAttribute("DataModel", parentModel);
}else {
	model.addAttribute("noData", "No Data Available!!");
}
	return "/MB/Reports/AirRequestReport/airrequestidview";
}
}
