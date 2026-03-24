package com.pcda.mb.requestdashboard.mastermissingdashboard.controller;

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
import com.pcda.mb.requestdashboard.mastermissingdashboard.model.GetMasterMissingModel;
import com.pcda.mb.requestdashboard.mastermissingdashboard.model.GetMasterMissingProfileModel;
import com.pcda.mb.requestdashboard.mastermissingdashboard.model.PostMasterMissingModel;
import com.pcda.mb.requestdashboard.mastermissingdashboard.model.PostSettleMasterMissEditModel;
import com.pcda.mb.requestdashboard.mastermissingdashboard.service.MasterMissingDashboardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mb")
public class MasterMissingDashBoardController {
	private String pageUrl = "/MB/RequestDashbord/MasterMissingDashBoard/";

	@Autowired
	private MasterMissingDashboardService masterMissingDashboardService;

	// Get master missing data
	@GetMapping("/masterMissingDashboard")

	public String getmasterMissingData(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		Optional<OfficeModel> officeModel = masterMissingDashboardService.getOfficesByGroupId(loginUser.getUserId());

		String groupId = " ";
		if (officeModel.isPresent()) {

			groupId = officeModel.get().getGroupId();

		}

		List<GetMasterMissingModel> lists = masterMissingDashboardService.getMasterMissingDetails(groupId);
		if (lists == null || lists.isEmpty()) {
			model.addAttribute("errorMessage", "No Record Found");
		} else {
			model.addAttribute("userData", lists);
		}

		return pageUrl + "masterMissDasboard";
	}

	// Get User Details For Settle

	@RequestMapping(value = "/masterMissingcommentHistory", method = { RequestMethod.GET, RequestMethod.POST })
	public String getviewUserData(Model model, HttpServletRequest request, @RequestParam String personalNo,
			@RequestParam String showCommentInput) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}

		Optional<OfficeModel> officeModel = masterMissingDashboardService.getOfficesByGroupId(loginUser.getUserId());

		String groupId = "";
		if (officeModel.isPresent()) {

			groupId = officeModel.get().getGroupId();
  
		}
		model.addAttribute("groupId", groupId);
		model.addAttribute("personalNo", personalNo);
		model.addAttribute("showCommentInput", showCommentInput);

		return pageUrl + "masterMissDasboardRemarks";

	}

	// Settle Master Missing DashBoard

	@PostMapping("/settleMasterMissDashboard")
	public String saveMasterMissing(@ModelAttribute @Valid PostMasterMissingModel model, BindingResult result,
			HttpServletRequest request,RedirectAttributes attribute) {
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
			
		model.setLoginUserId(loginUser.getUserId());
	   String message=	masterMissingDashboardService.saveSettleMasterMissingDash(model, result);
	   attribute.addFlashAttribute("errorMessage",message);
		return "redirect:masterMissingDashboard";
	}

	// Get Profile Data

	@RequestMapping(value = "/profileDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public String getviewUserProfileApproval(Model model, @RequestParam String personalNo, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		GetMasterMissingProfileModel user = masterMissingDashboardService.getviewProfileDetails(personalNo);
		model.addAttribute("userData", user);

		model.addAttribute("serviceId", user.getUserServiceId());

		model.addAttribute("categoryId", user.getCategoryId());
		Map<String, List<PAOMappingModel>> officesMap = masterMissingDashboardService
				.getPaoOffice(user.getUserServiceId(), user.getCategoryId(), loginUser);
		model.addAttribute("railOffices", officesMap.get("0"));
		model.addAttribute("airOffices", officesMap.get("1"));

		return pageUrl + "masterMissProfileDetails";
	}

	// Update Pao Edit
	@PostMapping("/updatePaoAfterEditMasterMiss")
	public String updatePaoMasterAfterEdit(
			@ModelAttribute @Valid PostSettleMasterMissEditModel postSettleMasterMissEditModel, BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		postSettleMasterMissEditModel.setLoginUserId(loginUser.getUserId());

		masterMissingDashboardService.updatePaoMasterMissingDashboard(postSettleMasterMissEditModel, result);

		return "redirect:masterMissingDashboard";

	}

	// Ajax To Get PAO
	@PostMapping("/getPAO2")
	public ResponseEntity<Map<String, List<PAOMappingModel>>> getPAOMissing(@RequestParam String serviceId,
			@RequestParam String categoryId, HttpServletRequest request) {
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		return ResponseEntity.ok(masterMissingDashboardService.getPaoOffice(serviceId, categoryId, loginUser));
	}

}
