package com.pcda.pao.reports.userverification.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.pao.reports.userverification.model.DownloadFileModel;
import com.pcda.pao.reports.userverification.model.FileUploadDataModel;
import com.pcda.pao.reports.userverification.model.FileUploadModel;
import com.pcda.pao.reports.userverification.model.MissDesVirifiedResponse;
import com.pcda.pao.reports.userverification.model.PaoReportBean;
import com.pcda.pao.reports.userverification.model.PostParentModel;
import com.pcda.pao.reports.userverification.model.UserVarificationFormDataModel;
import com.pcda.pao.reports.userverification.model.UserVerificationConfirmModel;
import com.pcda.pao.reports.userverification.model.UserVerificationHistoryModel;
import com.pcda.pao.reports.userverification.service.UserVerificationService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pao")
public class UserVerificationController {

	private String userVeriUrl = "/PAO/reports/userVerification";

	@Autowired
	private UserVerificationService userVarificationService;
	
	@Autowired
	private OfficesService officesService; 

	@GetMapping("/userVarification")
	public String userVerification(HttpServletRequest request, Model model) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}

		model.addAttribute("formData", new UserVarificationFormDataModel());
		return userVeriUrl + "/userVarification";

	}

	@PostMapping("/getPaoUserReportData")
	public String getDataUserVarification(UserVarificationFormDataModel userVarificationFormDataModel, Model model,
			HttpServletRequest request) {
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		
		model.addAttribute("formData", userVarificationFormDataModel);
		try {
			
			Optional<OfficeModel> optionalOffice =officesService.getOfficeByUserId(loginUser.getUserId());
			if (optionalOffice.isPresent()) {
				OfficeModel officeModel = optionalOffice.get();
				userVarificationFormDataModel.setAccountOffice(officeModel.getGroupId());
			}
			List<PaoReportBean> paoDataList = userVarificationService
					.getUserVarificationReport(userVarificationFormDataModel);

			if (paoDataList == null || paoDataList.isEmpty()) {

				model.addAttribute("listEmpty", "No Record's Found");
			} else {

				model.addAttribute("reportData", paoDataList);
				DownloadFileModel userVarification = new DownloadFileModel();
				userVarification
						.setDataList(userVarificationService.getUserVarificationReport(userVarificationFormDataModel));
				HttpSession session = request.getSession();
				session.setAttribute("voucherTxnData", userVarification);

			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, UserVerificationController.class,
					LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE);
		}

		return userVeriUrl + "/userVarification";
	}

	@PostMapping("/downloadpaouserreport")
	public void downloadPaoUserReport(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			Object data = session.getAttribute("voucherTxnData");
			
			ServletOutputStream out = response.getOutputStream();
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=PAOUserVerificationReport_"
					+ CommonUtil.formatDate(new Date(), "dd-MMM-yyyy") + ".csv");
			userVarificationService.addPAOUserReportCSVHeader(out);
			DODLog.info(LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE, UserVerificationController.class,
					"Response= " + response);
			if (null != data && data instanceof DownloadFileModel txnModel) {
				List<PaoReportBean> paoDataList = txnModel.getDataList();
				userVarificationService.writePAOUserReportCSVFile(out, paoDataList);
				
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			DODLog.printStackTrace(e, UserVerificationController.class, LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE);
		}

	}

	@PostMapping("/verifiMissDes")
	public String verifiedMassterMiss(PostParentModel parentModel, HttpServletRequest request, Model model) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		parentModel.setLoginUser(loginUser.getUserId());
		MissDesVirifiedResponse response = userVarificationService.updateMissVerified(parentModel, request);

		List<UserVerificationConfirmModel> responseList = response.getResponseList();

		responseList.stream()
				.forEach(e -> e.setFormatCreationDate(CommonUtil.formatDate(e.getCreationDate(), "dd-MM-yyyy")));
		model.addAttribute("data", responseList);
		model.addAttribute("message", "Selected user's status updated successfully..!");
		return userVeriUrl + "/userVerificationConfirm";
	}

	// Get Master Missing Commnent History
	@PostMapping("/userVerificationHistory")
	public String getuserVerificationHistory(Model model, @RequestParam String personalNo) {

		List<UserVerificationHistoryModel> userVerificationHistory = userVarificationService
				.getUserVerificationHistory(personalNo);

		model.addAttribute("personalNo", personalNo);
		if (userVerificationHistory.isEmpty()) {

			model.addAttribute("noDataFound", "No Data Found");
		} else {
		model.addAttribute("userMmHistory", userVerificationHistory);
		}
		return userVeriUrl + "/userMasterMissingHistory";
	}

	@PostMapping("/fileUpload")
	public String fileUpload() {

		return userVeriUrl + "/userVarificationFileUpload";
	}

	@PostMapping("/verifiedFile")
	public String verifiedFile(HttpServletRequest request,Model model) {

		String[] personalNoList=request.getParameterValues("personalNo");
	
		String msg=userVarificationService.verfyUplaodPersonalNoData(personalNoList);
		model.addAttribute("message", msg);
		return userVeriUrl + "/userVerificationFileConfirmation";
	}

	@PostMapping("/fileRead")
	public String fileRead(FileUploadModel postIndianRailReconCreate, HttpServletRequest request, Model model) {
		try {
			InputStream inputStream = postIndianRailReconCreate.getUserVerificationFileToUpload().getInputStream();

			List<List<String>> sumList = userVarificationService.getBookingData(inputStream);
			DODLog.info(LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE, UserVerificationController.class,
					"PAO_USER_VERIFICATION_REPORT_FILE_DATA" + sumList);
			List<FileUploadDataModel> modelList = new ArrayList<>();

			for (int i = 1; i < sumList.size(); i++) {
				FileUploadDataModel fileModel = new FileUploadDataModel();
				if (sumList.get(i).size() == 9) {
				fileModel.setPersonalNo(sumList.get(i).get(0));
					String date = sumList.get(i).get(1);
					fileModel.setCreationDate(date);
				fileModel.setFullName(sumList.get(i).get(2));
				fileModel.setUnitName(sumList.get(i).get(3));
				fileModel.setService(sumList.get(i).get(4));
				fileModel.setLevelName(sumList.get(i).get(5));
				fileModel.setCategory(sumList.get(i).get(6));
				fileModel.setStatus(sumList.get(i).get(7));
				modelList.add(fileModel);
				} else {
					model.addAttribute("error",
							"Wrong csv format.CSV column[1] is not matched with required csv Column[9]");
					return userVeriUrl + "/userVarificationFileUpload";
					}
			}

			for (FileUploadDataModel data : modelList) {
				try {
					String created = data.getCreationDate();
					DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
					Date date = (Date) formatter.parse(created);
					data.setCreationDate(CommonUtil.formatDate(date, "dd-MMM-yyyy"));
				} catch (Exception e) {
					DODLog.printStackTrace(e, UserVerificationController.class,
							LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE);
			}
			}

			if (modelList.isEmpty()) {
				model.addAttribute("error", "No Records found in csv file for user verification");

			} else {
				model.addAttribute("fileData", modelList);

			}

		} catch (IOException e) {
			DODLog.printStackTrace(e, UserVerificationController.class, LogConstant.PAO_USER_VERIFICATION_REPORT_LOG_FILE);
		}
		return userVeriUrl + "/userVarificationFileUpload";
	}

}
