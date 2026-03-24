package com.pcda.mb.reports.railrequestreport.controller;

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
import com.pcda.mb.reports.railrequestreport.model.GetRailReqIdParentModel;
import com.pcda.mb.reports.railrequestreport.model.GetRailReqRepoResponse;
import com.pcda.mb.reports.railrequestreport.model.GetReqIdResponse;
import com.pcda.mb.reports.railrequestreport.model.PostRailReqModel;
import com.pcda.mb.reports.railrequestreport.service.RailReqReportService;
import com.pcda.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/reports")
public class RailReqReportController {
	
	@Autowired
	private TravelTypeServices travelService;
	
	@Autowired
	private OfficesService offService;
	
	@Autowired
	private CodeHeadServices codeService;
	@Autowired
	private RailReqReportService repService;

	@GetMapping("/getRailReqReport")
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
		model.addAttribute("railReqModel",new PostRailReqModel());
		model.addAttribute("travelTypeList", travelService.getAllTravelType(1));
		model.addAttribute("accountOfficeList", offService.getOffices("PAO", "1"));
		model.addAttribute("codeHeadList", codeService.getCodeHeadByApproval("1"));
	
		return "/MB/Reports/RailRequestReport/railrequestreport";
	}
	
	@PostMapping("/getRailReqReportData")
	public String getRailReqReportData(PostRailReqModel railReqModel , HttpServletRequest request,Model model) {
		SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = visitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		String encryptPno =railReqModel.getPersonalNo();
		String secret = "Hidden Pass";
		String decryptPersonalNo =  CommonUtil.getDecryptText(secret, encryptPno);
		railReqModel.setPersonalNo(decryptPersonalNo);
		
		Optional<OfficeModel> office =  offService.getOfficeByUserId(loginUser.getUserId());
		if(office.isPresent()) {
			railReqModel.setGroupID(office.get().getGroupId());
		}

		railReqModel.setFromDate(CommonUtil.formatString(railReqModel.getFromXdate(), "dd-MM-yyyy"));
		railReqModel.setToDate(CommonUtil.formatString(railReqModel.getToXdate(), "dd-MM-yyyy"));
		
	
		GetRailReqRepoResponse response =  repService.getRailReqReportData(railReqModel);
		if(response!=null && response.getErrorCode()==200 && response.getResponseList() != null && !response.getResponseList().isEmpty() ) {
			model.addAttribute("reportDataList", response.getResponseList());
		}else {
			  model.addAttribute("noRecord", "No Record Found !!");
		}
        model.addAttribute("railReqModel", railReqModel);
		model.addAttribute("travelTypeList", travelService.getAllTravelType(1));
		model.addAttribute("accountOfficeList", offService.getOffices("PAO", "1"));
		model.addAttribute("codeHeadList", codeService.getCodeHeadByApproval("1"));
		
		return "/MB/Reports/RailRequestReport/railrequestreport";
	}
	
	@PostMapping("/getRailReqIdData") 
	public String getRailReqView(@RequestParam String requestId,HttpServletRequest request,Model model) {
		GetReqIdResponse response = repService.getRailReIdData(requestId);
		if(response!=null && response.getErrorCode()==200 && response.getResponse()!=null) {
			GetRailReqIdParentModel idParentModel = response.getResponse(); 
			model.addAttribute("reqIdModel", idParentModel);
		}else {
			model.addAttribute("noData", "NO Data Available!!");
		}
		
		return "/MB/Reports/RailRequestReport/railrequestidview";
	}
}
