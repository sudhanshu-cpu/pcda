package com.pcda.mb.travel.bulkBkg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pcda.mb.travel.journey.model.DAAdvanceDTO;
import com.pcda.mb.travel.journey.service.JourneyRequestService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

@Data
public class JourneyDetailsBulkBkgDTO {
	
	private String journeyType;
	private String travelMode;
	private String journeyDate;
	private String[] mixedJrnyJourneyDate;
	private String[] mixedJrnySource;
	private String[] mixedJrnyDestination;
	private String[] mixedJrnyMode;
	private String fromStation;
	private String toStation;
	private String fromStationType;
	private String toStationType;
	private String relation;
	private String depSeqNo;
	private String origin;
	private String destination;
	private String fixedOrigin;
	private String fixedDestination;
	private List<String> removeFrmList;
	private String onwordJourneyDate;
	private String viaRoute;
	private String viaRelxRoute;
	private String isTatkalMixed;
	private String isTatkal;
	private String clusteredRoute;
	private String clusterValidate;
	private String isPartyBooking;
	private String partyMemberCount;
	private String isAttendentBooking;
	private String finalAttendentCount;
	private String[] dependentPersonalNo;
	private String[] validIdCardPass;
	private String[] validIdCardAttCheck;
	private String[] validIdCardPartyGroupCheck;
	private String lastRowIndex;
	private List<String> frmStationList;
	private List<String> toStationList;
	private List<String> journeyDateList;
	private String viaStn;
	private String journeyDate1;
	private String journeyDate2;
	private String viaRuteOne;
	private String viaRuteTwo;
	private String mixedPreference;
	private String[] frmStationOne;
	private String[] toStationOne;
	private String[] journeyDateOne;
	private String[] frmStationTwo;
	private String[] toStationTwo;
	private String[] journeyDateTwo;
	private String daAdvanced;
	private String transferReq;
	private String mixedRailEntitledClass;
	private String shippngAdd;
	private String shippngCity;
	private String shippngStat;
	private String shippngCountry;
	private String shippngPin;
	private String shippngPhone;
	private String shippngFax;
	private String entitledClass;
	private String ersPrntNameVal;
	private String gender;
	private String dob;
	private String otherRel;
	private String[] partyDepName;
	private String[] partyDepErsName;
	private String[] partyDepGender;
	private String[] partyDepDOB;
	private String[] attName;
	private String[] attErsName;
	private String[] attGender;
	private String[] attDob;
	private String clusterTrainNoList;
	private String clusterStation;
	private String tripType;
	private String lrcCheck;
	private String familyName;
	private String returnJourneyDate;
	private String age;
	private String originType;
	private String destinationType;
	private String airEntitledClass;
	private String mixedAirEntitledClass;
	private String reasonCode;
	private String flightCode;
	private String preferAirline;
	private String mealType;
	private String airJourneyType;
	private DAAdvanceDTO daAdvanceDTO;
	private String airlineType;
	private String otherAirlineType;
	private String faaAuthorityNo;
	private String faaAuthorityDate;
	private String railRequestId;
	private String airRequestId;
	private String errorMessage;
	private String airViaLeg;
	private String departureTimeSlot;
	private String[] passportNo;
	private String[] passExpDate;

	public void initializeOnwardsVariable(HttpServletRequest request) {
		this.setJourneyType("0");
		this.setTravelMode(Optional.ofNullable(request.getParameter("travelMode")).orElse("0"));
		this.setJourneyDate(Optional.ofNullable(request.getParameter("journeyDate")).orElse(""));
		this.setMixedJrnyJourneyDate(request.getParameterValues("mixedJrnyJourneyDate"));
		this.setMixedJrnySource(request.getParameterValues("mixedJrnySource"));
		this.setMixedJrnyDestination(request.getParameterValues("mixedJrnyDestination"));
		this.setMixedJrnyMode(request.getParameterValues("mixedJrnyMode"));
		this.setFromStation(Optional.ofNullable(request.getParameter("fromStation")).orElse(""));		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestService.class, " toSation " + request.getParameter("toStation"));
		this.setToStation(Optional.ofNullable(request.getParameter("toStation")).orElse(""));		
		this.setFromStationType(Optional.ofNullable(request.getParameter("fromStationType")).orElse(""));
		this.setToStationType(Optional.ofNullable(request.getParameter("toStationType")).orElse(""));
		this.setRelation(Optional.ofNullable(request.getParameter("relation")).orElse(""));
		this.setDepSeqNo(Optional.ofNullable(request.getParameter("depSeqNo")).orElse(""));
		this.setOrigin(Optional.ofNullable(request.getParameter("origin")).orElse(""));
		this.setDestination(Optional.ofNullable(request.getParameter("destination")).orElse(""));
		this.setFixedOrigin(Optional.ofNullable(request.getParameter("fixedOrigin")).orElse(""));
		this.setFixedDestination(Optional.ofNullable(request.getParameter("fixedDestination")).orElse(""));
		this.setRemoveFrmList(initializeRemoveFrmList(request));
		this.setOnwordJourneyDate(Optional.ofNullable(request.getParameter("onwordJourneyDate")).orElse(""));
		this.setViaRoute(Optional.ofNullable(request.getParameter("viaRoute")).orElse(""));
		this.setViaRelxRoute(Optional.ofNullable(request.getParameter("viaRelxRoute")).orElse(""));
		this.setIsTatkalMixed(Optional.ofNullable(request.getParameter("isTatkalMixed")).orElse(""));
		this.setIsTatkal(Optional.ofNullable(request.getParameter("isTatkal")).orElse(""));
		this.setClusteredRoute(Optional.ofNullable(request.getParameter("clusteredRoute")).orElse("off"));
		this.setClusterValidate(Optional.ofNullable(request.getParameter("clusterValidate")).orElse("false"));
		this.setIsPartyBooking(Optional.ofNullable(request.getParameter("isPartyBooking")).orElse(""));
		this.setPartyMemberCount(Optional.ofNullable(request.getParameter("partyMemberCount")).orElse(""));
		this.setIsAttendentBooking(Optional.ofNullable(request.getParameter("isAttendentBooking")).orElse(""));
		this.setFinalAttendentCount(Optional.ofNullable(request.getParameter("finalAttendentCount")).orElse(""));
		this.setDependentPersonalNo(request.getParameterValues("dependentPersonalNo"));
		this.setValidIdCardPass(request.getParameterValues("validIdCardPass"));
		this.setValidIdCardAttCheck(request.getParameterValues("validIdCardAttCheck"));
		this.setValidIdCardPartyGroupCheck(request.getParameterValues("validIdCardPartyGroupCheck"));
		this.setLastRowIndex(Optional.ofNullable(request.getParameter("lastRowIndex")).orElse(""));
		setViaJourneyInfo(request);
		this.setViaStn(Optional.ofNullable(request.getParameter("viaStn")).orElse(""));
		this.setJourneyDate1(Optional.ofNullable(request.getParameter("journeyDate1")).orElse(""));
		this.setJourneyDate2(Optional.ofNullable(request.getParameter("journeyDate2")).orElse(""));
		this.setViaRuteOne(Optional.ofNullable(request.getParameter("viaRuteOne")).orElse("NO"));
		this.setViaRuteTwo(Optional.ofNullable(request.getParameter("viaRuteTwo")).orElse("NO"));
		this.setMixedPreference(Optional.ofNullable(request.getParameter("mixedPreference")).orElse("0"));
		this.setFrmStationOne(request.getParameterValues("frmStationOne"));
		this.setToStationOne(request.getParameterValues("toStationOne"));
		this.setJourneyDateOne(request.getParameterValues("journeyDateOne"));
		this.setFrmStationTwo(request.getParameterValues("frmStationTwo"));
		this.setToStationTwo(request.getParameterValues("toStationTwo"));
		this.setJourneyDateTwo(request.getParameterValues("journeyDateTwo"));
		this.setDaAdvanced(Optional.ofNullable(request.getParameter("DAAdvanced")).orElse("1"));
		this.setTransferReq(Optional.ofNullable(request.getParameter("transferReq")).orElse("1"));
		this.setMixedRailEntitledClass(
				Optional.ofNullable(request.getParameter("mixedRailEntitledClass")).orElse(""));
		this.setShippngAdd(Optional.ofNullable(request.getParameter("shippngAdd")).orElse(""));
		this.setShippngCity(Optional.ofNullable(request.getParameter("shippngCity")).orElse(""));
		this.setShippngStat(Optional.ofNullable(request.getParameter("shippngStat")).orElse(""));
		this.setShippngCountry(Optional.ofNullable(request.getParameter("shippngCountry")).orElse("India"));
		this.setShippngPin(Optional.ofNullable(request.getParameter("shippngPin")).orElse(""));
		this.setShippngPhone(Optional.ofNullable(request.getParameter("shippngPhone")).orElse("-"));
		this.setShippngFax(Optional.ofNullable(request.getParameter("shippngFax")).orElse("-"));
		this.setEntitledClass(Optional.ofNullable(request.getParameter("entitledClass")).orElse(""));
		this.setErsPrntNameVal(Optional.ofNullable(request.getParameter("ersPrntNameVal")).orElse(""));
		this.setGender(Optional.ofNullable(request.getParameter("gender")).orElse(""));
		this.setDob(Optional.ofNullable(request.getParameter("dob")).orElse(""));
		this.setOtherRel(Optional.ofNullable(request.getParameter("otherRel")).orElse(""));
		this.setPartyDepName(request.getParameterValues("partyDepName"));
		this.setPartyDepErsName(request.getParameterValues("partyDepErsName"));
		this.setPartyDepGender(request.getParameterValues("partyDepGender"));
		this.setPartyDepDOB(request.getParameterValues("partyDepDOB"));
		this.setAttName(request.getParameterValues("attName"));
		this.setAttErsName(request.getParameterValues("attErsName"));
		this.setAttGender(request.getParameterValues("attGender"));
		this.setAttDob(request.getParameterValues("attDob"));
		this.setClusterTrainNoList(Optional.ofNullable(request.getParameter("clusterTrainNoList")).orElse(""));
		this.setClusterStation(Optional.ofNullable(request.getParameter("clusterStation")).orElse("false"));
		this.setTripType(Optional.ofNullable(request.getParameter("tripType")).orElse("0"));
		this.setLrcCheck(Optional.ofNullable(request.getParameter("lrc_check")).orElse(""));
		this.setFamilyName(Optional.ofNullable(request.getParameter("familyName")).orElse(""));
		this.setReturnJourneyDate(Optional.ofNullable(request.getParameter("returnJourneyDate")).orElse(""));
		this.setAge(Optional.ofNullable(request.getParameter("age")).orElse(""));
		this.setOriginType(Optional.ofNullable(request.getParameter("originType")).orElse("0"));
		this.setDestinationType(Optional.ofNullable(request.getParameter("destinationType")).orElse("0"));
		this.setAirEntitledClass(Optional.ofNullable(request.getParameter("airEntitledClass")).orElse("2"));
		this.setMixedAirEntitledClass(
				Optional.ofNullable(request.getParameter("mixedAirEntitledClass")).orElse("2"));
		this.setReasonCode(Optional.ofNullable(request.getParameter("reasonCode")).orElse(""));
		this.setFlightCode(Optional.ofNullable(request.getParameter("flightCode")).orElse(""));
		this.setPreferAirline(Optional.ofNullable(request.getParameter("preferAirline")).orElse(""));
		this.setMealType(Optional.ofNullable(request.getParameter("mealType")).orElse("0"));
		this.setAirJourneyType(Optional.ofNullable(request.getParameter("airJourneyType")).orElse(""));
		this.setDepartureTimeSlot(Optional.ofNullable(request.getParameter("airDepartureTime")).orElse(""));
		this.setDaAdvanceDTO(setDaAdvanceDetails(request));
		this.setPassportNo(request.getParameterValues("passPassportNo"));
		this.setPassExpDate(request.getParameterValues("passExpdate"));

		if (this.getTravelMode().equals("1")) {
			this.setAirlineType(Optional.ofNullable(request.getParameter("airlineType")).orElse(""));
			this.setOtherAirlineType(Optional.ofNullable(request.getParameter("otherAirlineType")).orElse(""));
			this.setFaaAuthorityNo(Optional.ofNullable(request.getParameter("FAA_AuthorityNo")).orElse(""));
			this.setFaaAuthorityDate(Optional.ofNullable(request.getParameter("FAA_AuthorityDate")).orElse(""));
			this.setAirViaLeg(setAirViaLegsDtls(request));
		} else if (this.getTravelMode().equals("2")) {
			this.setAirlineType(Optional.ofNullable(request.getParameter("mixedAirlineType")).orElse(""));
			this.setOtherAirlineType(Optional.ofNullable(request.getParameter("mixedOtherAirlineType")).orElse(""));
			this.setFaaAuthorityNo(Optional.ofNullable(request.getParameter("mixed_FAA_AuthorityNo")).orElse(""));
			this.setFaaAuthorityDate(
					Optional.ofNullable(request.getParameter("mixed_FAA_AuthorityDate")).orElse(""));
			this.setAirViaLeg(setMixedAirViaLegsDtls(request));
		}
	}

	public void initializeReturnVariable(HttpServletRequest request) {
		this.setJourneyType("1");
		this.setTravelMode(Optional.ofNullable(request.getParameter("returnTravelMode")).orElse("0"));
		this.setJourneyDate(Optional.ofNullable(request.getParameter("return_JourneyDate")).orElse(""));
		this.setMixedJrnyJourneyDate(request.getParameterValues("returnMixedJrnyJourneyDate"));
		this.setMixedJrnySource(request.getParameterValues("returnMixedJrnySource"));
		this.setMixedJrnyDestination(request.getParameterValues("returnMixedJrnyDestination"));
		this.setMixedJrnyMode(request.getParameterValues("returnMixedJrnyMode"));
		this.setFromStation(Optional.ofNullable(request.getParameter("returnFromStation")).orElse(""));
		this.setToStation(Optional.ofNullable(request.getParameter("returnToStation")).orElse(""));
		this.setFromStationType(Optional.ofNullable(request.getParameter("returnFromStationType")).orElse(""));
		this.setToStationType(Optional.ofNullable(request.getParameter("returnToStationType")).orElse(""));
		this.setRelation(Optional.ofNullable(request.getParameter("returnRelation")).orElse(""));
		this.setDepSeqNo(Optional.ofNullable(request.getParameter("returnDepSeqNo")).orElse(""));
		this.setOrigin(Optional.ofNullable(request.getParameter("returnOrigin")).orElse(""));
		this.setDestination(Optional.ofNullable(request.getParameter("returnDestination")).orElse(""));
		this.setFixedOrigin(Optional.ofNullable(request.getParameter("returnFixedOrigin")).orElse(""));
		this.setFixedDestination(Optional.ofNullable(request.getParameter("returnFixedDestination")).orElse(""));
		this.setRemoveFrmList(initializeRemoveFrmList(request));
		this.setOnwordJourneyDate(Optional.ofNullable(request.getParameter("returnOnwordJourneyDate")).orElse(""));
		this.setViaRoute(Optional.ofNullable(request.getParameter("returnViaRoute")).orElse(""));
		this.setViaRelxRoute(Optional.ofNullable(request.getParameter("returnViaRelxRoute")).orElse(""));
		this.setIsTatkalMixed(Optional.ofNullable(request.getParameter("returnIsTatkalMixed")).orElse(""));
		this.setIsTatkal(Optional.ofNullable(request.getParameter("returnIsTatkal")).orElse(""));
		this.setClusteredRoute(Optional.ofNullable(request.getParameter("returnClusteredRoute")).orElse("off"));
		this.setClusterValidate(Optional.ofNullable(request.getParameter("returnClusterValidate")).orElse("false"));
		this.setIsPartyBooking(Optional.ofNullable(request.getParameter("isPartyBooking")).orElse(""));
		this.setPartyMemberCount(Optional.ofNullable(request.getParameter("returnPartyMemberCount")).orElse(""));
		this.setIsAttendentBooking(Optional.ofNullable(request.getParameter("isAttendentBooking")).orElse(""));
		this.setFinalAttendentCount(
				Optional.ofNullable(request.getParameter("returnFinalAttendentCount")).orElse(""));
		this.setDependentPersonalNo(request.getParameterValues("returnDependentPersonalNo"));
		this.setValidIdCardPass(request.getParameterValues("returnValidIdCardPass"));
		this.setValidIdCardAttCheck(request.getParameterValues("returnValidIdCardAttCheck"));
		this.setValidIdCardPartyGroupCheck(request.getParameterValues("returnValidIdCardPartyGroupCheck"));
		this.setLastRowIndex(Optional.ofNullable(request.getParameter("returnLastRowIndex")).orElse(""));
		setViaJourneyInfo(request);
		this.setViaStn(Optional.ofNullable(request.getParameter("returnViaStn")).orElse(""));
		this.setJourneyDate1(Optional.ofNullable(request.getParameter("returnJourneyDate1")).orElse(""));
		this.setJourneyDate2(Optional.ofNullable(request.getParameter("returnJourneyDate2")).orElse(""));
		this.setViaRuteOne(Optional.ofNullable(request.getParameter("returnViaRuteOne")).orElse("NO"));
		this.setViaRuteTwo(Optional.ofNullable(request.getParameter("returnViaRuteTwo")).orElse("NO"));
		this.setMixedPreference(Optional.ofNullable(request.getParameter("returnMixedPreference")).orElse("0"));
		this.setFrmStationOne(request.getParameterValues("returnFrmStationOne"));
		this.setToStationOne(request.getParameterValues("returnToStationOne"));
		this.setJourneyDateOne(request.getParameterValues("return_JourneyDateOne"));
		this.setFrmStationTwo(request.getParameterValues("returnFrmStationTwo"));
		this.setToStationTwo(request.getParameterValues("returnToStationTwo"));
		this.setJourneyDateTwo(request.getParameterValues("return_JourneyDateTwo"));
		this.setDaAdvanced("1");
		this.setTransferReq("1");
		this.setMixedRailEntitledClass(
				Optional.ofNullable(request.getParameter("returnMixedRailEntitledClass")).orElse(""));
		this.setShippngAdd(Optional.ofNullable(request.getParameter("returnShippngAdd")).orElse(""));
		this.setShippngCity(Optional.ofNullable(request.getParameter("returnShippngCity")).orElse(""));
		this.setShippngStat(Optional.ofNullable(request.getParameter("returnShippngStat")).orElse(""));
		this.setShippngCountry(Optional.ofNullable(request.getParameter("returnShippngCountry")).orElse("India"));
		this.setShippngPin(Optional.ofNullable(request.getParameter("returnShippngPin")).orElse(""));
		this.setShippngPhone(Optional.ofNullable(request.getParameter("returnShippngPhone")).orElse("-"));
		this.setShippngFax(Optional.ofNullable(request.getParameter("returnShippngFax")).orElse("-"));
		this.setEntitledClass(Optional.ofNullable(request.getParameter("returnEntitledClass")).orElse(""));
		this.setErsPrntNameVal(Optional.ofNullable(request.getParameter("returnErsPrntNameVal")).orElse(""));
		this.setGender(Optional.ofNullable(request.getParameter("returnGender")).orElse(""));
		this.setDob(Optional.ofNullable(request.getParameter("returnDOB")).orElse(""));
		this.setOtherRel(Optional.ofNullable(request.getParameter("returnOtherRel")).orElse(""));
		this.setPartyDepName(request.getParameterValues("returnPartyDepName"));
		this.setPartyDepErsName(request.getParameterValues("returnPartyDepErsName"));
		this.setPartyDepGender(request.getParameterValues("returnPartyDepGender"));
		this.setPartyDepDOB(request.getParameterValues("returnPartyDepDOB"));
		this.setAttName(request.getParameterValues("returnAttName"));
		this.setAttErsName(request.getParameterValues("returnAttErsName"));
		this.setAttGender(request.getParameterValues("returnAttGender"));
		this.setAttDob(request.getParameterValues("returnAttDob"));
		this.setClusterTrainNoList(Optional.ofNullable(
				request.getParameter("returnClusterTrainNoList")).orElse(""));
		this.setClusterStation(Optional.ofNullable(request.getParameter("returnClusterStation")).orElse("false"));
		this.setTripType(Optional.ofNullable(request.getParameter("returnTripType")).orElse("0"));
		this.setLrcCheck("");
		this.setFamilyName(Optional.ofNullable(request.getParameter("returnFamilyName")).orElse(""));
		this.setReturnJourneyDate(Optional.ofNullable(request.getParameter("returnReturnJourneyDate")).orElse(""));
		this.setAge(Optional.ofNullable(request.getParameter("returnAge")).orElse(""));
		this.setOriginType(Optional.ofNullable(request.getParameter("returnOriginType")).orElse("0"));
		this.setDestinationType(Optional.ofNullable(request.getParameter("returnDestinationType")).orElse("0"));
		this.setAirEntitledClass(Optional.ofNullable(request.getParameter("returnAirEntitledClass")).orElse("2"));
		this.setMixedAirEntitledClass(
				Optional.ofNullable(request.getParameter("returnMixedAirEntitledClass")).orElse("2"));
		this.setReasonCode(Optional.ofNullable(request.getParameter("returnReasonCode")).orElse(""));
		this.setFlightCode(Optional.ofNullable(request.getParameter("returnFlightCode")).orElse(""));
		this.setPreferAirline(Optional.ofNullable(request.getParameter("returnPreferAirline")).orElse(""));
		this.setMealType(Optional.ofNullable(request.getParameter("returnMealType")).orElse("0"));
		this.setAirJourneyType(Optional.ofNullable(request.getParameter("returnAirJourneyType")).orElse(""));
		this.setDepartureTimeSlot(Optional.ofNullable(request.getParameter("returnAirDepartureTime")).orElse(""));
		this.setDaAdvanceDTO(null);
		this.setPassportNo(request.getParameterValues("returnPassPassportNo"));
		this.setPassExpDate(request.getParameterValues("returnPassExpdate"));

		if (this.getTravelMode().equals("1")) {
			this.setAirlineType(Optional.ofNullable(request.getParameter("returnAirlineType")).orElse(""));
			this.setOtherAirlineType(
					Optional.ofNullable(request.getParameter("returnOtherAirlineType")).orElse(""));
			this.setFaaAuthorityNo(Optional.ofNullable(request.getParameter("returnFAA_AuthorityNo")).orElse(""));
			this.setFaaAuthorityDate(
					Optional.ofNullable(request.getParameter("returnFAA_AuthorityDate")).orElse(""));
			this.setAirViaLeg(setAirViaLegsDtls(request));
		} else if (this.getTravelMode().equals("2")) {
			this.setAirlineType(Optional.ofNullable(request.getParameter("returnMixedAirlineType")).orElse(""));
			this.setOtherAirlineType(
					Optional.ofNullable(request.getParameter("returnMixedOtherAirlineType")).orElse(""));
			this.setFaaAuthorityNo(
					Optional.ofNullable(request.getParameter("returnMixedFAA_AuthorityNo")).orElse(""));
			this.setFaaAuthorityDate(
					Optional.ofNullable(request.getParameter("returnMixedFAA_AuthorityDate")).orElse(""));
			this.setAirViaLeg(setMixedAirViaLegsDtls(request));
		}

	}

	private List<String> initializeRemoveFrmList(HttpServletRequest request) {
		String[] relationArray = this.getRelation().split(",");
		List<String> removeFrmList = new ArrayList<>();
		int count = 0;
		for (int i = 1; i < relationArray.length; i++) {
			if ("0".equals(this.getJourneyType())) {
				removeFrmList.add(Optional.ofNullable(request.getParameter("removeFrmList" + count)).orElse(""));
			} else if ("1".equals(this.getJourneyType())) {
				removeFrmList
						.add(Optional.ofNullable(request.getParameter("returnRemoveFrmList" + count)).orElse(""));
			} 

			count++;
		}
		return removeFrmList;
	}

	private void setViaJourneyInfo(HttpServletRequest request) {
		String noOfRoutes = this.getLastRowIndex();
		if (noOfRoutes == null || noOfRoutes.length() == 0)
			noOfRoutes = "0";
		int noOfRoutesInt = Integer.parseInt(noOfRoutes);

		if (!(this.getViaRoute().equalsIgnoreCase("on")) && this.getClusterValidate().equalsIgnoreCase("true")
				&& this.getClusteredRoute().equalsIgnoreCase("on")) {
			noOfRoutesInt = 1;
		}

		List<String> frmStationList = new ArrayList<>();
		List<String> toStationList = new ArrayList<>();
		List<String> journeyDateList = new ArrayList<>();

		for (int index = 0; index <= noOfRoutesInt; index++) {
			if ("0".equals(this.getJourneyType())) {
				frmStationList.add(Optional.ofNullable(request.getParameter("frmStation" + index)).orElse(""));
				toStationList.add(Optional.ofNullable(request.getParameter("toStation" + index)).orElse(""));
				journeyDateList.add(Optional.ofNullable(request.getParameter("journeyDate" + index)).orElse(""));
			} else if ("1".equals(this.getJourneyType())) {
				frmStationList
						.add(Optional.ofNullable(request.getParameter("returnFrmStation" + index)).orElse(""));
				toStationList.add(Optional.ofNullable(request.getParameter("returnToStation" + index)).orElse(""));
				journeyDateList
						.add(Optional.ofNullable(request.getParameter("return_JourneyDate" + index)).orElse(""));
			} else {
			}
		}
		this.setFrmStationList(frmStationList);
		this.setToStationList(toStationList);
		this.setJourneyDateList(journeyDateList);
	}

	private DAAdvanceDTO setDaAdvanceDetails(HttpServletRequest request) {
		DAAdvanceDTO daAdvanceDTO = new DAAdvanceDTO();

		daAdvanceDTO.setNoOfDays(Optional.ofNullable(request.getParameter("noOfDays")).orElse("0"));
		daAdvanceDTO.setReturnDate(Optional.ofNullable(request.getParameter("returnDate")).orElse(""));
		daAdvanceDTO.setDestinationCity(Optional.ofNullable(request.getParameter("destinationCity")).orElse(""));
		daAdvanceDTO.setDestinationCityGrade(
				Optional.ofNullable(request.getParameter("destinationCityGrade")).orElse("-1"));
		daAdvanceDTO.setGovtAcc(Optional.ofNullable(request.getParameter("govtAcc")).orElse("1"));
		daAdvanceDTO.setAdvanceAmt(Optional.ofNullable(request.getParameter("advanceAmt")).orElse("0"));
		daAdvanceDTO.setOffice(Optional.ofNullable(request.getParameter("office")).orElse(""));
		daAdvanceDTO.setBasicPay(Optional.ofNullable(request.getParameter("basicPay")).orElse("0"));
		daAdvanceDTO.setLuggageAmt(Optional.ofNullable(request.getParameter("luggageAmt")).orElse("0"));
		daAdvanceDTO.setConveyance(Optional.ofNullable(request.getParameter("conveyance")).orElse("0"));
		daAdvanceDTO.setNewDaOrOldDa(Optional.ofNullable(request.getParameter("newDaOrOldDa")).orElse(""));
		daAdvanceDTO
				.setRequestForHotelDA(Optional.ofNullable(request.getParameter("requestForHotelDA")).orElse("1"));
		daAdvanceDTO.setRequestForConveyanceDA(
				Optional.ofNullable(request.getParameter("requestForConveyanceDA")).orElse("1"));
		daAdvanceDTO.setRequestForFoodDA(Optional.ofNullable(request.getParameter("requestForFoodDA")).orElse("1"));
		daAdvanceDTO.setHotelAmount(Optional.ofNullable(request.getParameter("hotelAmount")).orElse("0"));
		daAdvanceDTO.setConveyanceAmount(Optional.ofNullable(request.getParameter("conveyanceAmount")).orElse("0"));
		daAdvanceDTO.setFoodAmount(Optional.ofNullable(request.getParameter("foodAmount")).orElse("0"));
		daAdvanceDTO.setTotalCtg(Optional.ofNullable(request.getParameter("totalCtg")).orElse("0"));
		daAdvanceDTO.setPtTransferType(
				Integer.parseInt(Optional.ofNullable(request.getParameter("ptTransferType")).orElse("0")));

		return daAdvanceDTO;
	}

	private String setAirViaLegsDtls(HttpServletRequest request) {

		StringBuilder builder = new StringBuilder("");
		if ("0".equals(this.getJourneyType())) {
			String airViaLegCount = Optional.ofNullable(request.getParameter("airViaLegCount")).orElse("0");
			int airViaLegInt = 0;
			try {
				airViaLegInt = Integer.parseInt(airViaLegCount);
			} catch (Exception e) {
			}

			if (airViaLegInt > 0) {
				for (int index = 0; index < airViaLegInt - 1; index++) {
					String viaCode = CommonUtil.getStationCode(
							Optional.ofNullable(request.getParameter("ltcViaDestination" + index)).orElse(""));
					if (index == 0) {
						builder.append(viaCode);
					} else {
						builder.append("#");
						builder.append(viaCode);
					}
				}
			}

		} else if ("1".equals(this.getJourneyType())) {
			String airViaLegCount = Optional.ofNullable(request.getParameter("returnAirViaLegCount")).orElse("0");
			int airViaLegInt = 0;
			try {
				airViaLegInt = Integer.parseInt(airViaLegCount);
			} catch (Exception e) {
			}

			if (airViaLegInt > 0) {
				for (int index = 0; index < airViaLegInt - 1; index++) {
					String viaCode = CommonUtil.getStationCode(
							Optional.ofNullable(request.getParameter("returnLtcViaDestination" + index)).orElse(""));
					if (index == 0) {
						builder.append(viaCode);
					} else {
						builder.append("#");
						builder.append(viaCode);
					}
				}
			}
		} else {
		}
		return builder.toString();
	}

	private String setMixedAirViaLegsDtls(HttpServletRequest request) {

		StringBuilder builder = new StringBuilder("");
		if ("0".equals(this.getJourneyType())) {
			String airViaLegCount = Optional.ofNullable(request.getParameter("airViaLegCount")).orElse("0");
			int airViaLegInt = 0;
			try {
				airViaLegInt = Integer.parseInt(airViaLegCount);
			} catch (Exception e) {
			}

			if (airViaLegInt > 0) {
				for (int index = 0; index < airViaLegInt - 1; index++) {
					String viaCode = CommonUtil.getStationCode(
							Optional.ofNullable(request.getParameter("mixedLTCViaDestination" + index)).orElse(""));
					if (index == 0) {
						builder.append(viaCode);
					} else {
						builder.append("#");
						builder.append(viaCode);
					}
				}
			}

		} else if ("1".equals(this.getJourneyType())) {
			String airViaLegCount = Optional.ofNullable(request.getParameter("returnAirViaLegCount")).orElse("0");
			int airViaLegInt = 0;
			try {
				airViaLegInt = Integer.parseInt(airViaLegCount);
			} catch (Exception e) {
			}

			if (airViaLegInt > 0) {
				for (int index = 0; index < airViaLegInt - 1; index++) {
					String viaCode = CommonUtil.getStationCode(
							Optional.ofNullable(request.getParameter("returnMixedLtcViaDestination" + index)).orElse(""));
					if (index == 0) {
						builder.append(viaCode);
					} else {
						builder.append("#");
						builder.append(viaCode);
					}
				}
			}
		} else {
		}
		return builder.toString();
	}
	

}
