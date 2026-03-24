package com.pcda.mb.grievance.extraInformation.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.grievance.complaintHistory.model.GrievancePostBean;
import com.pcda.mb.grievance.complaintRedressal.model.FileDetails;
import com.pcda.mb.grievance.complaintRedressal.service.GrievanceService;
import com.pcda.mb.grievance.extraInformation.model.ExtraInfoModel;
import com.pcda.mb.grievance.extraInformation.model.PostUpdateGrievanceModel;
import com.pcda.mb.grievance.extraInformation.service.ExtraInfoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")

public class ExtraInformationController {

	@Autowired
	private ExtraInfoService extraInfoService;
	
	@Autowired
	private GrievanceService grievanceService;
	
	@Autowired
	private OfficesService officesService;

	private String pageURL = "/MB/Grievance/extraInformation/";

	@GetMapping("/extraInformation")
	public String GetExtraInformation(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		if (loginUser == null) {
			return "redirect:/login";
		}
		String groupId = "";
		Optional<OfficeModel> office = officesService.getOfficeByUserId(loginUser.getUserId());
		if (office.isPresent()) {
			groupId = office.get().getGroupId();

		}
		List<ExtraInfoModel> browseList = extraInfoService.getAllInformation(groupId);
		if(browseList!=null && !browseList.isEmpty()) {	
			browseList.sort(Comparator.comparing(ExtraInfoModel::getCreationTime).reversed());
			model.addAttribute("browseList", browseList);
		}
		else {
			model.addAttribute("error", "No Record Found!!!");
		}

		return pageURL + "viewInformation";
	}
	
	//get the notification count	
	@GetMapping("/getCount")
	@ResponseBody
    public int getCountNotification(Model model, HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		String groupId = "";
		Optional<OfficeModel> office = officesService.getOfficeByUserId(loginUser.getUserId());
		if (office.isPresent()) {
			groupId = office.get().getGroupId();

		}
        int count = extraInfoService.getAllCountNotify(groupId);
        model.addAttribute("notificationCount", count);
        return count;
		
    }
	
	
	
	@PostMapping("/editComplaintRedressal")
	public String editComplaintRedressal(Model model,GrievancePostBean bean,HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		String groupId = "";
		Optional<OfficeModel> office = officesService.getOfficeByUserId(loginUser.getUserId());
		if (office.isPresent()) {
			groupId = office.get().getGroupId();

		}
		bean.setGroupId(groupId);
		model.addAttribute("editComplaint", extraInfoService.getEditComplaintRedressal(bean));
		
		return pageURL+"editComplaint";
	}
	
	//update grievance
	
	@PostMapping("/updateGrievance")
	public String updateGrievance(PostUpdateGrievanceModel updateGrievanvceBean,List<MultipartFile> file, BindingResult result,RedirectAttributes redirectAttributes, HttpServletRequest request) {
		updateGrievanvceBean.setLoginUserId(SessionVisitor.getInstance(request.getSession()).getLoginUser().getUserId());
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
		updateGrievanvceBean.setFileDetails(fileDetails);
		redirectAttributes.addFlashAttribute("message",extraInfoService.updateGrievance(updateGrievanvceBean));
		
		return "redirect:extraInformation";
	}
	
	
	

	

}
