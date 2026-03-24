package com.pcda.mb.adduser.usermastermissdasboard.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.PAOMappingModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.usermastermissdasboard.model.GetUserMasterMissProfileModel;
import com.pcda.mb.adduser.usermastermissdasboard.model.PostSettleUserMasterMissStatusModel;
import com.pcda.mb.adduser.usermastermissdasboard.service.UserMasterMissDasboardService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mb")
public class UserMasterMissDasboardController {

	private String path = "MB/AddUsers/UserMasterMissDasboard/";
	@Autowired
	private UserMasterMissDasboardService userMasterMissDasboardService;
	

//Get User MM Dashboard
	@GetMapping("/userMasterMissDasboard")
	public String getuserMasterMissDasboard(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		Optional<OfficeModel> officeModel = userMasterMissDasboardService.getOfficesByGroupId(loginUser.getUserId());

		String groupId = "";
		if (officeModel.isPresent()) {
			groupId = officeModel.get().getGroupId();
		}

		List<GetUserMasterMissProfileModel> list = userMasterMissDasboardService.getMasterMissProfile(groupId);
		if(list == null || list.isEmpty()) {
			model.addAttribute("errorMessage", "No Record Found");
		}else {
			model.addAttribute("profileData", list);
		}

		return path + "userMasterMissDasboard";
	}

	// Get View User Details
	@RequestMapping(value = "/viewUserDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public String getviewUserDtls(Model model, @RequestParam String personalNo) {
		
		model.addAttribute("userDtls", userMasterMissDasboardService.getviewUserDetails(personalNo));
		return path + "userMasterMissViewUserDetails";
	}

	// Get Master Missing Commnent History

	@RequestMapping(value = "/masterMissingCommnentHistory", method = { RequestMethod.GET, RequestMethod.POST })
	public String getViewMmCommnentHistry(Model model, @RequestParam String personalNo) {

		DODLog.info(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardController.class, "getViewMmCommnentHistry::" + personalNo);
		model.addAttribute("personalNo", personalNo);
		model.addAttribute("userMmHistory", userMasterMissDasboardService.getViewMMCommentHistory(personalNo));
		return path + "masterMissCommnentHistory";
	}

//Settle Master Miss User
	@RequestMapping(value = "/settleMasterMissUser", method = { RequestMethod.GET, RequestMethod.POST })
	public String getViewMmCommnentHistrySettel(Model model, @RequestParam String personalNo) {
		model.addAttribute("personalNo", personalNo);
		return path + "masterMissCommnentHistoryRemarks";
	}
	
	@PostMapping("/saveSettelMasterMissUser")
	public String saveSettleMasterMissUser(@ModelAttribute @Valid PostSettleUserMasterMissStatusModel model,
			BindingResult result, HttpServletRequest request, RedirectAttributes attributes) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		model.setLoginUserId(loginUser.getUserId());

	        model.setCommentBy(loginUser.getUserId());
	        DODLog.info(LogConstant.MASTER_MISS_PROFILE_LOG, UserMasterMissDasboardController.class, "saveSettleMasterMissUser::" + model);
		String message=userMasterMissDasboardService.saveSettleMasterMissUser(model, result);
		attributes.addFlashAttribute("errorMessage",message);
		return  "redirect:userMasterMissDasboard";
	}

	@RequestMapping(value ="/profileApproval",method = {RequestMethod.GET,RequestMethod.POST})
	public String getviewUserProfileApproval(Model model,  @RequestParam String personalNo, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		
		
		GetUserMasterMissProfileModel user = userMasterMissDasboardService.getviewUserDetails(personalNo);
		String serviceId=user.getUserServiceId();
		model.addAttribute("userDtls", user);
		model.addAttribute("serviceId",serviceId);
		model.addAttribute("categoryId",user.getCategoryId());
		Map<String, List<PAOMappingModel>> officesMap = userMasterMissDasboardService.getPaoOffice(user.getUserServiceId(), user.getCategoryId(), loginUser);
		
		
		model.addAttribute("railOffices", officesMap.get("0"));
		model.addAttribute("airOffices", officesMap.get("1"));
		return path + "userMasterMissProfileApprove";
	}

	//Save Pao After Edit 
	
	@PostMapping("/updatePaoAfterEdit")
	
	public String updatePaoAftrEdit(
			@ModelAttribute @Valid PostSettleUserMasterMissStatusModel postSettleUserMasterMissStatusModel,
			BindingResult result, RedirectAttributes redirectAttributes, HttpServletRequest request) {

			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();

			postSettleUserMasterMissStatusModel.setLoginUserId(loginUser.getUserId());
		userMasterMissDasboardService.updatePao(postSettleUserMasterMissStatusModel, result);
			
		return  "redirect:userMasterMissDasboard";

}
	
	 //Ajax To Get PAO
		@PostMapping("/getPAO1")
	public ResponseEntity<Map<String, List<PAOMappingModel>>> getPAO(@RequestParam String serviceId,
			@RequestParam String categoryId, HttpServletRequest request) {
			
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			LoginUser loginUser = sessionVisitor.getLoginUser();

			return ResponseEntity.ok(userMasterMissDasboardService.getPaoOffice(serviceId, categoryId, loginUser));
		}

}
