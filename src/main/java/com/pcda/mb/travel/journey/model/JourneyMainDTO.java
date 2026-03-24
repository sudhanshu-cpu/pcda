package com.pcda.mb.travel.journey.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

@Data
public class JourneyMainDTO {

	private String requestType;
	private String personalNo;
	private String travelType;
	private String travelGroup;
	private String trRule;
	private String travelId;
	private String travelStartDate;
	private String travelEndDate;
	private String authorityNo;
	private String authorityDate;
	private String ltcYear;
	private String warrantIssueDate;
	private String reason;
	private String warrantReason;
	private String oldDutyStn;
	private String newDutyStn;
	private String oldSprStn;
	private String newSprStn;
	private String oldSprNRS;
	private String newSprNRS;
	private String rank;
	private String codeHead;
	private String office;
	private String cancelWaitList;
	private String isAgeRelx;
	private String oldDutyNap;
	private String newDutyNap;
	private String oldSprNap;
	private String newSprNap;
	private String categoryId;
	private String serviceId;
	private String serviceType;
	private String spouseNocNo;
	private String paoChanged;
	private String projectBudgetId;
	private String unitBudgetId;
	private String budgetType;
	private String isBgtAvailed;
	private String civilBgtAvl;
	private String taggedReqId;
	private String[] question;
	private String travellerUnitService;
	private String currentBlockYear;
	private String currentSubBlockYear;
	private String formDAndGYear;
	private List<String> answerCheck;
	private String airCodeHead;
	private String railAccountOffice;
	private String airPaoAcc;
	private String railPAOId;
	private String airPAOId;
	private String[] oldRequestId;

	public void initializeVariable(HttpServletRequest request) {
		this.setRequestType(Optional.ofNullable(request.getParameter("reqType")).orElse(""));
		this.setPersonalNo(Optional.ofNullable(request.getParameter("personalNo")).orElse(""));
		this.setTravelType(Optional.ofNullable(request.getParameter("TravelTypeDD")).orElse(""));
		this.setTravelGroup(Optional.ofNullable(request.getParameter("trGroup")).orElse(""));
		this.setTrRule(Optional.ofNullable(request.getParameter("TRRule")).orElse(""));
		this.setTravelId(Optional.ofNullable(request.getParameter("travelID")).orElse(""));
		this.setTravelStartDate(Optional.ofNullable(request.getParameter("travelStartDate")).orElse(""));
		this.setTravelEndDate(Optional.ofNullable(request.getParameter("travelEndDate")).orElse(""));
		this.setAuthorityNo(Optional.ofNullable(request.getParameter("authorityNo")).orElse(""));
		this.setAuthorityDate(Optional.ofNullable(request.getParameter("authorityDate")).orElse(""));
		this.setLtcYear(Optional.ofNullable(request.getParameter("ltcYear")).orElse(""));
		this.setWarrantIssueDate(Optional.ofNullable(request.getParameter("warrantIssueDate")).orElse(""));
		this.setReason(Optional.ofNullable(request.getParameter("reason")).orElse(""));
		this.setWarrantReason(Optional.ofNullable(request.getParameter("warrantReason")).orElse(""));
		this.setOldDutyStn(Optional.ofNullable(request.getParameter("oldDutyStn")).orElse(""));
		this.setNewDutyStn(Optional.ofNullable(request.getParameter("newDutyStn")).orElse(""));
		this.setOldSprStn(Optional.ofNullable(request.getParameter("oldSprStn")).orElse(""));
		this.setNewSprStn(Optional.ofNullable(request.getParameter("newSprStn")).orElse(""));
		this.setOldSprNRS(Optional.ofNullable(request.getParameter("oldSprNRS")).orElse(""));
		this.setNewSprNRS(Optional.ofNullable(request.getParameter("newSprNRS")).orElse(""));
		this.setRank(Optional.ofNullable(request.getParameter("rank")).orElse(""));
		this.setCodeHead(Optional.ofNullable(request.getParameter("codeHead")).orElse(""));
		this.setOffice(Optional.ofNullable(request.getParameter("office")).orElse(""));
		this.setCancelWaitList(Optional.ofNullable(request.getParameter("cancelWaitList")).orElse("0"));
		this.setIsAgeRelx(Optional.ofNullable(request.getParameter("isAgeRelx")).orElse(""));
		this.setOldDutyNap(Optional.ofNullable(request.getParameter("oldDutyNap")).orElse(""));
		this.setNewDutyNap(Optional.ofNullable(request.getParameter("newDutyNap")).orElse(""));
		this.setOldSprNap(Optional.ofNullable(request.getParameter("oldSprNap")).orElse(""));
		this.setNewSprNap(Optional.ofNullable(request.getParameter("newSprNap")).orElse(""));
		this.setCategoryId(Optional.ofNullable(request.getParameter("category_id")).orElse("0"));
		this.setServiceId(Optional.ofNullable(request.getParameter("service_id")).orElse("0"));
		this.setServiceType(Optional.ofNullable(request.getParameter("serviceType")).orElse("0"));
		this.setSpouseNocNo(Optional.ofNullable(request.getParameter("spouseNocNo")).orElse("0"));
		this.setPaoChanged(Optional.ofNullable(request.getParameter("paoChanged")).orElse("1"));
		this.setProjectBudgetId(Optional.ofNullable(request.getParameter("projectBudgetId")).orElse(""));
		this.setUnitBudgetId(Optional.ofNullable(request.getParameter("unitBudgetId")).orElse(""));
		this.setBudgetType(Optional.ofNullable(request.getParameter("budgetType")).orElse(""));
		this.setIsBgtAvailed(Optional.ofNullable(request.getParameter("isBgtAvailed")).orElse(""));
		this.setCivilBgtAvl(Optional.ofNullable(request.getParameter("civilBgtAvl")).orElse(""));
		this.setTaggedReqId(Optional.ofNullable(request.getParameter("taggedReqId")).orElse(""));
		this.setQuestion(request.getParameterValues("question"));
		this.setTravellerUnitService(Optional.ofNullable(request.getParameter("travellerUnitService")).orElse(""));
		this.setCurrentBlockYear(Optional.ofNullable(request.getParameter("currentBlockYear")).orElse(""));
		this.setCurrentSubBlockYear(Optional.ofNullable(request.getParameter("currentSubBlockYear")).orElse(""));
		this.setFormDAndGYear(Optional.ofNullable(request.getParameter("formDAndGYear")).orElse(""));
		this.setAnswerCheck(setQuestionAns(request));
		this.setAirCodeHead(Optional.ofNullable(request.getParameter("airCodeHead")).orElse(""));
		this.setRailAccountOffice(Optional.ofNullable(request.getParameter("railAccountOffice")).orElse(""));
		this.setAirPaoAcc(Optional.ofNullable(request.getParameter("airPaoAcc")).orElse(""));
		this.setRailPAOId(Optional.ofNullable(request.getParameter("railPAOId")).orElse(""));
		this.setAirPAOId(Optional.ofNullable(request.getParameter("airPAOId")).orElse(""));
		this.setOldRequestId(request.getParameterValues("oldRequestId"));

	}

	private List<String> setQuestionAns(HttpServletRequest request) {
		List<String> answerCheck = new ArrayList<>();
		for (int i = 0; i < getQuestion().length; i++) {
			answerCheck.add(Optional.ofNullable(request.getParameter("answerCheck" + i)).orElse(""));
		}

		return answerCheck;
	}

}
