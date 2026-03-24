package com.pcda.co.requestdashboard.approvetdr.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.co.requestdashboard.approvetdr.model.GetChildDataFrmGrpIdModel;
import com.pcda.co.requestdashboard.approvetdr.model.GetParentDataGrpIdModel;
import com.pcda.co.requestdashboard.approvetdr.model.GroupIdDataListRespose;
import com.pcda.co.requestdashboard.approvetdr.model.PostTdrAppDisAppModel;
import com.pcda.co.requestdashboard.approvetdr.service.TdrApproveService;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.TdrStatus;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/co")
public class TdrApproveController {
	
@Autowired
private OfficesService officesService;
@Autowired
private TdrApproveService approveService;


@RequestMapping(value="/approveTdr",method = {RequestMethod.GET,RequestMethod.POST})
public String dataForApproal(Model model,HttpServletRequest request) {
	List<GetParentDataGrpIdModel> parentModelList = new ArrayList<>();
	SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = visitor.getLoginUser();
	if (loginUser == null) {
		return "redirect:/login";
	}
     
	
	BigInteger loginUserId = loginUser.getUserId();
	Optional<OfficeModel> officeModel = officesService.getOfficeByUserId(loginUserId);
	String groupId ="";
	if(officeModel.isPresent()) {
		groupId = officeModel.get().getGroupId();
	
    }
	GroupIdDataListRespose response = approveService.getDataListFrmGrpId(groupId);
	if(response!=null && response.getErrorCode()==200) {
		 parentModelList = response.getResponseList();
	}
	if(!parentModelList.isEmpty()) {
		model.addAttribute("dataList", parentModelList);
	}else {
		model.addAttribute("noRecord", "No Record Found !!");
	}
	return "/CO/RequestDashBoard/TDRApprove/tdrapprovedetail";
}

@PostMapping("/getAppDataFrmBkgId")
public String getAppDataFrmBkgId(Model model,@RequestParam String bookingId,HttpServletRequest request) {
	try {
		
	GetParentDataGrpIdModel parentModel = approveService.getAppDataTktBkg( bookingId);
	
	List<GetChildDataFrmGrpIdModel> filterPsngerList = parentModel.getPassengerList().stream()
    .filter(pssnger -> pssnger.getTdrAprrovalState() != null) 
    .filter(pssnger -> pssnger.getTdrAprrovalState().equals(TdrStatus.REQUESTED)) 
    .toList();

	parentModel.setPassengerList(filterPsngerList);
	
	model.addAttribute("dataModel", parentModel);
	}catch (Exception e) {
		DODLog.printStackTrace(e, TdrApproveController.class, LogConstant.TDR_LOG_FILE);

	}
	return "/CO/RequestDashBoard/TDRApprove/tdrapprove";
}

@PostMapping("/approveDisapproveTdr")
public String getReqForAppDisApp(PostTdrAppDisAppModel appDisAppModel,HttpServletRequest request) {
	SessionVisitor visitor = SessionVisitor.getInstance(request.getSession());
	LoginUser loginUser = visitor.getLoginUser();
	if (loginUser == null) {
		return "redirect:/login";
	}
   
	BigInteger loginUserId = loginUser.getUserId();
	appDisAppModel.setLoginUserId(loginUserId);
	
	if(appDisAppModel.getEvent().equalsIgnoreCase("approve")) {
		GroupIdDataListRespose response =approveService.sendForApproval(appDisAppModel);
		if(response!=null && response.getErrorCode()==200) {
			return "redirect:approveTdr";
		}
	}
if(appDisAppModel.getEvent().equalsIgnoreCase("disapprove")) {
	GroupIdDataListRespose response =approveService.sendForDisApproval(appDisAppModel);
	if(response!=null && response.getErrorCode()==200) {
		return "redirect:approveTdr";
	}
	}
return "/common/errorPage";
}

}
