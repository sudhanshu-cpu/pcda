package com.pcda.mb.requestdashboard.changeboardingstationdashboard.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcda.common.model.OfficeModel;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.requestdashboard.changeboardingstationdashboard.model.BoardingStationDetailsModel;
import com.pcda.mb.requestdashboard.changeboardingstationdashboard.model.BoardingStationDetailsResponse;
import com.pcda.mb.requestdashboard.changeboardingstationdashboard.model.ChangeBoardingStationResponseModel;
import com.pcda.mb.requestdashboard.changeboardingstationdashboard.model.PostBoardingStationModel;
import com.pcda.mb.requestdashboard.changeboardingstationdashboard.service.ChangeBoardingStationService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mb")
public class ChangeBoardingStationController {

	private ChangeBoardingStationService changeBoardingStationService;

	public ChangeBoardingStationController(ChangeBoardingStationService changeBoardingStationService) {

		this.changeBoardingStationService = changeBoardingStationService;
	}

	private String url = "/MB/RequestDashbord/changeboardingstationdashboard";

	@GetMapping("/changeBoardingStationReq")
	public String getChangeBoardingStationReqPage(Model model, HttpServletRequest request) {

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		if (loginUser == null) {
			return "redirect:/login";
		}
		return url + "/changeBoardingStationReq";
	}

	@PostMapping("/getBoardingStationDtls")
	public String searchBookingDtls(Model model, @RequestParam(required = false, defaultValue = "") String bookingId,
			@RequestParam(required = false, defaultValue = "") String pnrNo, HttpServletRequest request) {

		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());

			LoginUser loginUser = sessionVisitor.getLoginUser();
			if (loginUser == null) {
				return "redirect:/login";
			}

			model.addAttribute("bookingId", bookingId);
			model.addAttribute("pnrNo", pnrNo);
			Optional<OfficeModel> optionalOffice = changeBoardingStationService.getUnitByUserId(loginUser.getUserId());

			String groupId = "";
			if (optionalOffice.isPresent()) {
				groupId = optionalOffice.get().getGroupId();
			}

			BoardingStationDetailsResponse railCancellationResponse = changeBoardingStationService
					.getRailBookingDtls(bookingId, pnrNo, groupId);
			BoardingStationDetailsModel boardingStationDetailsModel = null;

			if (null != railCancellationResponse && railCancellationResponse.getErrorCode() == 200
					&& railCancellationResponse.getResponse() != null) {

				boardingStationDetailsModel = railCancellationResponse.getResponse();
				boardingStationDetailsModel.setBoardingDate(CommonUtil.getChangeFormat(boardingStationDetailsModel.getJourneyDate(),"yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy HH:mm"));
				

			} else if (null != railCancellationResponse && railCancellationResponse.getErrorMessage() != null
					&& railCancellationResponse.getErrorCode() != 200) {
				model.addAttribute("error", railCancellationResponse.getErrorMessage());
				return url + "/changeBoardingStationReq";
			}                            

			

			if (boardingStationDetailsModel == null) {
				model.addAttribute("error", "No Record Found!!");
			} else {

				changeBoardingStationService.getAllBoardingStation(boardingStationDetailsModel);

				model.addAttribute("pnrData", boardingStationDetailsModel);
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangeBoardingStationController.class,
					LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE);

		}
		return url + "/changeBoardingStationReq";
	}

	@PostMapping("/updateBoardingPoint")
	public String saveBoardingChangeStationReq(Model model, HttpServletRequest request,
			PostBoardingStationModel postBoardingStationModel) {

		try {
			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());

			LoginUser loginUser = sessionVisitor.getLoginUser();
			if (loginUser == null) {
				return "redirect:/login";
			}

			Optional<OfficeModel> optionalOffice = changeBoardingStationService.getUnitByUserId(loginUser.getUserId());

			String groupId = "";
			if (optionalOffice.isPresent()) {
				groupId = optionalOffice.get().getGroupId();
			}
			postBoardingStationModel.setGroupId(groupId);
			postBoardingStationModel.setLoginUserId(loginUser.getUserId());

			if (changeBoardingStationService.validateBean(postBoardingStationModel).equals("OK")) {

				ChangeBoardingStationResponseModel responseModel = changeBoardingStationService.saveBoardingStatioinReq(postBoardingStationModel);
				
				if (responseModel.getFlag().equalsIgnoreCase("true")) {
					responseModel.setNewBoardingDateStr(CommonUtil.formatDate(responseModel.getNewBoardingDate(), "dd-MM-yyyy HH:mm"));
					
					model.addAttribute("error", "Your Boarding Point Has Been Successfully Changed from"
							+ responseModel.getOldBoardingPoint() + " To " + responseModel.getNewBoardingPoint() +" New Boarding Date Is "+responseModel.getNewBoardingDateStr());
				} else {

					model.addAttribute("error", responseModel.getSuccessMsg());
				}
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangeBoardingStationController.class,LogConstant.RAIL_CANCELLATION_DASHBOARD_LOG_FILE);
		}
		return url + "/changeBoardingStationReq";
	}

}
