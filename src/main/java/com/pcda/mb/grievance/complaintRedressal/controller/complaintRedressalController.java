package com.pcda.mb.grievance.complaintRedressal.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.GrievanceCategoryBean;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.grievance.complaintRedressal.model.ComplaintRedressalModel;
import com.pcda.mb.grievance.complaintRedressal.model.ComplaintRedressalResponse;
import com.pcda.mb.grievance.complaintRedressal.model.FileDetails;
import com.pcda.mb.grievance.complaintRedressal.model.GrievanceFAQViewBean;
import com.pcda.mb.grievance.complaintRedressal.service.GrievanceService;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")

public class complaintRedressalController {

	private String pageURL = "/MB/Grievance/complaintRedressal/";

	@Autowired
	private GrievanceService grievanceService;

	@Autowired
	private OfficesService officesService;

	@GetMapping("/complaintRedressal")
	public String getComplaintRedressal(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		return pageURL + "complaintRedressal";
	}

	@GetMapping("/faqCheckboxYes")
	public String GetfaqCheckboxYes(Model model, HttpServletRequest request) {
		return pageURL + "FAQResolved";
	}

	// to get the grievance type

	@GetMapping("/getGrievanceCategory")
	public ResponseEntity<List<GrievanceCategoryBean>> getGrievanceCategoryData() {
		List<GrievanceCategoryBean> grievanceCategory = grievanceService.getGrievanceCategory();
		return ResponseEntity.ok(grievanceCategory);
	}

	// To save the grievance
	@PostMapping("/saveGrievance")
	public String saveGrievanceRedressal(ComplaintRedressalModel complaintRedressalModel,@RequestParam List<MultipartFile> file, BindingResult result,
			Model model, RedirectAttributes attributes, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if(loginUser==null) {
			return "redirect:/login";
		}
		complaintRedressalModel.setLoginUserId(loginUser.getUserId());

		Optional<OfficeModel> officeModel = officesService.getOfficeByUserId(complaintRedressalModel.getLoginUserId());
	    complaintRedressalModel.setGroupId(officeModel.map(OfficeModel::getGroupId).orElse(""));
		DODLog.info(LogConstant.COMMON_LOG, complaintRedressalController.class,
				"complaintRedressalModel ::: " + complaintRedressalModel);
	    try {
			if (result.hasErrors()) {
				DODLog.error(LogConstant.COMMON_LOG, complaintRedressalController.class,
						"Error::::: " + result.getAllErrors());
	            return "redirect:complaintRedressal";
			} else {
				List<FileDetails> fileDetails = new ArrayList<>();
				if(!file.isEmpty()){
				
				file.forEach(uploadFile->
				{					
					FileDetails details= new FileDetails();
				String filePath = grievanceService.uploadFile(uploadFile);
					details.setFilePath(filePath);
					details.setFileName(uploadFile.getOriginalFilename());
					fileDetails.add(details);
				});
				}
				complaintRedressalModel.setFileDetails(fileDetails);
				ComplaintRedressalResponse response = grievanceService.saveGrievance(complaintRedressalModel);

	            DODLog.info(LogConstant.COMMON_LOG, complaintRedressalController.class, "response ::: " + response);

				if (response != null && response.getErrorCode() == 200 && response.getResponse() != null) {
					String complaintNo = response.getResponse();
					model.addAttribute("complaintNo", complaintNo);
	            } else {
	                attributes.addFlashAttribute("message", "Complaint not saved");
	                return "redirect:complaintRedressal";
	            }
	        }
	    } catch (Exception e) {
	        DODLog.printStackTrace(e, complaintRedressalController.class, LogConstant.COMMON_LOG);
	        attributes.addFlashAttribute("message", "Error occurred!!!");
	    }

	    return pageURL + "nextIndex";
	}

	@ResponseBody
	@GetMapping("/getFaqByCategoryId")
	public ResponseEntity<List<GrievanceFAQViewBean>> faqByCategoryId(@RequestParam BigInteger grievanceCatId,
			Model model) {
		DODLog.info(LogConstant.COMMON_LOG, complaintRedressalController.class, "grievanceCatId ::: " + grievanceCatId);
		return ResponseEntity.ok(grievanceService.getfaqByCategoryId(grievanceCatId));
	}

	// validation Personal No
	@ResponseBody
	@GetMapping("/validatePersonal")
	public ResponseEntity<String> validatePersonalNo(@RequestParam String personalNo, HttpServletRequest request,
			Model model) {
		String groupId = "";
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		Optional<OfficeModel> office = officesService.getOfficeByUserId(loginUser.getUserId());
		if (office.isPresent()) {
			groupId = office.get().getGroupId();

		}
		DODLog.info(LogConstant.COMMON_LOG, complaintRedressalController.class, "personalNo ::: " + personalNo);
		return ResponseEntity.ok(grievanceService.validatePersonalNo(personalNo, groupId));
	}

}
