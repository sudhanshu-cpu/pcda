package com.pcda.mb.finalclaim.claimrequest.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.pcda.util.CommonUtil;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClaimRequestBean {

	private String dmyFormat = "dd-MM-yyyy";
	private String hmFormat = "dd-MM-yyyy hh:mm";

	private BigInteger loginUserId;	

	private BigInteger userId;
	private String personalNo;

	private String suppClaimId;
	private String levelId;
	private Double totalSpentAmount = 0.0;
	private Double totalAdvanceAmount = 0.0;
	private Double totalRefundAmount = 0.0;
	private Double totalClaimedAmount = 0.0;
	private String unitId;
	private String irlaGrpID;
	private String travelTypeId;
	private String travelID; 
	private String trRuleName;
	@NotBlank(message = "Move Sanction No should not be blank.")
	private String moveSanctionNo;
	@NotNull(message = "Move Sanction Date should not be blank.")
	private Date movSanctionDate;

	@NotBlank(message = "Journey Commenced From should not be blank.")
	private String jrnyStrtdFrom;
	@NotNull(message = "Journey Commenced Date should not be blank.")
	private Date jrnyStrtdTime;

	private String categoryId;
	private String serviceId;
	private Integer approvalstate;

	private ClaimAdvanceDtlsBean claimAdvanceDtls;

	private List<ClaimJourneyDtlsBean> claimJourneyDtls;
	private List<ClaimLeaveDtlsBean> claimLeaveDtls;
	private List<ClaimNonDtsJrnyDtlsBean> claimNonDtsJrnyDtls;
	private List<ClaimHotelDtlsBean> claimHotelDtls;
	private List<ClaimFoodDtlsBean> claimFoodDtls;
	private List<ClaimConyncDtlsBean> claimConyncDtls;

	private String[] claimCertify;

	private List<ClaimDocDtlsBean> claimDocDtls;
    private List<ClaimPersonalEffectsBean> claimPersonalEffectsBean;
	private String designatin;
	private String basicPay;
	private Date leaveFrom;
	private Date leaveTo;
	private String hometown;
	private String leaveStn;
	private String ltcBlockYr;
	private String ltcCalYr;
	private String payAccntNo;	
	private String trnsfrFrom;
	private String trnsfrTO;
	private String jrnyStrtdTo;
	private Date jrnyFnshTime;
	private String moveIssungAuth;		



//----------------------------------------------



	private Integer serviceType;

//	private String journeyStartDate;

//	private String personalPayLevel;
//	private String jrnyStartToDate;
//	private String moveAuthority;

//	private String advFromPAO;



		public ClaimRequestBean(){}

		public ClaimRequestBean(HttpServletRequest request){
			this.setLevelId(Optional.ofNullable(request.getParameter("levelId")).orElse(""));
			this.setMoveSanctionNo(Optional.ofNullable(request.getParameter("moveSanctionNo")).orElse(""));
			this.setMovSanctionDate(CommonUtil.formatString(request.getParameter("moveSanctionDate"), dmyFormat));
			this.setJrnyStrtdFrom(Optional.ofNullable(request.getParameter("jrnyStartedFrom")).orElse(""));
			this.setJrnyStrtdTime(CommonUtil.formatString(request.getParameter("jrnyStartDate"), hmFormat));
			this.setTravelID(Optional.ofNullable(request.getParameter("travelId")).orElse(""));
			this.setClaimJourneyDtls(setDTSJourneyData(request));
			this.setClaimLeaveDtls(setLeaveDetails(request));
			this.setClaimNonDtsJrnyDtls(setNonDTSJourneyData(request));
			this.setClaimHotelDtls(setHotelDetails(request));
			this.setClaimFoodDtls(setFoodDetails(request));
			this.setClaimConyncDtls(setConveyanceData(request));
			this.setClaimDocDtls(setClaimDocumentData(request));
			this.setClaimCertify(request.getParameterValues("claimCertify"));
			this.setTotalSpentAmount(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("totalSpentAmount"), "0")));
			this.setTotalAdvanceAmount(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("totalAdvanceAmount"), "0")));
			this.setTotalRefundAmount(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("totalRefundAmount"), "0")));
			this.setTotalClaimedAmount(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("totalClaimedAmount"), "0")));
			this.setTrRuleName(Optional.ofNullable(request.getParameter("trRuleName")).orElse(""));
			this.setServiceType(Integer.parseInt(CommonUtil.getStringParameter(request.getParameter("userServiceType"), "0"))); 
			this.setClaimAdvanceDtls(setClaimAdvDtl(request));
		}

		private ClaimAdvanceDtlsBean setClaimAdvDtl(HttpServletRequest request) {
			ClaimAdvanceDtlsBean advanceDtlsBean = new ClaimAdvanceDtlsBean();

			advanceDtlsBean.setAdvFromPAODate(Optional.ofNullable(CommonUtil.formatString(request.getParameter("advFromPAODate"), dmyFormat)).orElse(new Date()));
			advanceDtlsBean.setMroNumber(Optional.ofNullable(request.getParameter("mroNumber")).orElse("0"));
			advanceDtlsBean.setMroDate(Optional.ofNullable(CommonUtil.formatString(request.getParameter("mroDate"), dmyFormat)).orElse(new Date()));
			advanceDtlsBean.setCounterPersonalNo(Optional.ofNullable(request.getParameter("counterPersonalNo")).orElse(""));
			advanceDtlsBean.setAdvFromPAO(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("advFromPAO"), "0")));
			advanceDtlsBean.setCounterPersonalName(Optional.ofNullable(request.getParameter("counterPersonalName")).orElse(""));
			advanceDtlsBean.setCounterPersonalLevel(Optional.ofNullable(request.getParameter("counterPersonalLevel")).orElse(""));
			advanceDtlsBean.setAdvFromOther(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("advFromOther"), "0")));
			advanceDtlsBean.setAdvFromOtherVoucherNo(Optional.ofNullable(request.getParameter("advFromOtherVoucherNo")).orElse(""));
			advanceDtlsBean.setAdvFromOtherDate(CommonUtil.formatString(request.getParameter("advFromOtherDate"), dmyFormat));
			advanceDtlsBean.setMroRefund(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("mroRefund"), "0")));

			advanceDtlsBean.setClaimPreferredDate(Optional.ofNullable(CommonUtil.formatString(request.getParameter("claimPreferredDate"), dmyFormat)).orElse(new Date()));
			advanceDtlsBean.setClaimPreferredSanction(Optional.ofNullable(request.getParameter("claimPreferredSanction")).orElse(""));
			advanceDtlsBean.setClaimPreferredSanctionNo(Optional.ofNullable(request.getParameter("claimPreferredSanctionNo")).orElse(""));
			advanceDtlsBean.setClaimPreferredSanctionDate(Optional.ofNullable(CommonUtil.formatString(request.getParameter("claimPreferredSanctionDate"), dmyFormat)).orElse(new Date()));

			return advanceDtlsBean;
		}

		private List<ClaimDocDtlsBean> setClaimDocumentData(HttpServletRequest request) {

			List<ClaimDocDtlsBean> docDtls=new ArrayList<>();
			
			String[] fileTypeName=request.getParameterValues("fileTypeName");
			String[] fileTypePath=request.getParameterValues("fileTypePath");
			
			if(null!=fileTypeName && fileTypeName.length>0){
				for(int index=0;index<fileTypeName.length;index++){
					ClaimDocDtlsBean documentDTO = new ClaimDocDtlsBean();
					documentDTO.setDocName(fileTypeName[index]);
					documentDTO.setFilePath(fileTypePath[index]);
					docDtls.add(documentDTO);
				}
			}	
			
			return docDtls;
		}

		private List<ClaimLeaveDtlsBean> setLeaveDetails(HttpServletRequest request) {

			List<ClaimLeaveDtlsBean> leaveDtls=new ArrayList<>();

			String[] leavePeriodDate=request.getParameterValues("leavePeriodDate");
			String[] leaveFullHalf=request.getParameterValues("leaveFullHalf");

			if(null!=leavePeriodDate && leavePeriodDate.length>0){
				for(int index=0;index<leavePeriodDate.length;index++){
					ClaimLeaveDtlsBean leavePeriodDTO=new ClaimLeaveDtlsBean();
					leavePeriodDTO.setLeaveDate(CommonUtil.formatString(leavePeriodDate[index], dmyFormat));
					leavePeriodDTO.setLeaveFullHalf(Integer.parseInt(CommonUtil.getStringParameter(leaveFullHalf[index], "0")));
					leaveDtls.add(leavePeriodDTO);
				}
			}
			return leaveDtls;
		}

		private List<ClaimConyncDtlsBean> setConveyanceData(HttpServletRequest request) {

			List<ClaimConyncDtlsBean> conveyanceDtls=new ArrayList<>();

			String[] tourVehicleNo=request.getParameterValues("tourVehicleNo");
			String[] tourDate=request.getParameterValues("tourDate");
			String[] tourDistance=request.getParameterValues("tourDistance");
			String[] tourBillNo=request.getParameterValues("tourBillNo");
			String[] tourAmount=request.getParameterValues("tourAmount");
			String[] actualTourAmount=request.getParameterValues("actualTourAmount");
			String[] tourNoOfDays=request.getParameterValues("tourNoOfDays");
			String isConveyanceDtls=request.getParameter("isConveyanceDtls");

			if("0".equals(isConveyanceDtls)){
				if(null!=tourVehicleNo && tourVehicleNo.length>0){
					for(int index=0;index<tourVehicleNo.length;index++){
						ClaimConyncDtlsBean conveyanceDetailsDTO = new ClaimConyncDtlsBean();

						conveyanceDetailsDTO.setTourVehicleNo(tourVehicleNo[index]);
						conveyanceDetailsDTO.setTourDate(CommonUtil.formatString(tourDate[index], dmyFormat));
						conveyanceDetailsDTO.setTourDistance(Integer.parseInt(CommonUtil.getStringParameter(tourDistance[index], "0")));
						conveyanceDetailsDTO.setTourBillNo(tourBillNo[index]);
						conveyanceDetailsDTO.setTourAmount(Double.valueOf(CommonUtil.getStringParameter(tourAmount[index], "0")));
						conveyanceDetailsDTO.setActualTourAmount(Double.valueOf(CommonUtil.getStringParameter(actualTourAmount[index], "0")));
						conveyanceDetailsDTO.setConveyanceDtls(Integer.parseInt(CommonUtil.getStringParameter(isConveyanceDtls, "0")));

						conveyanceDtls.add(conveyanceDetailsDTO);
					}
				}
			}else if("1".equals(isConveyanceDtls)){

				if(null!=tourNoOfDays && tourNoOfDays.length>0){
					for(int index=0;index<tourNoOfDays.length;index++){
						ClaimConyncDtlsBean conveyanceDetailsDTO = new ClaimConyncDtlsBean();

						conveyanceDetailsDTO.setTourNoOfDays(Integer.parseInt(CommonUtil.getStringParameter(tourNoOfDays[index], "0")));
						conveyanceDetailsDTO.setTourAmount(Double.valueOf(CommonUtil.getStringParameter(tourAmount[index], "0")));
						conveyanceDetailsDTO.setActualTourAmount(Double.valueOf(CommonUtil.getStringParameter(actualTourAmount[index], "0")));
						conveyanceDetailsDTO.setConveyanceDtls(Integer.parseInt(CommonUtil.getStringParameter(isConveyanceDtls, "0")));

						conveyanceDtls.add(conveyanceDetailsDTO);
					}
				}

			}
			return conveyanceDtls;
		}

		private List<ClaimFoodDtlsBean> setFoodDetails(HttpServletRequest request) {

			List<ClaimFoodDtlsBean> foodDtls=new ArrayList<>();

			String[] nonDrawlCertificate=request.getParameterValues("nonDrawlCertificate");
			String[] foodNoDay=request.getParameterValues("foodNoDay");
			String[] foodAmount=request.getParameterValues("foodAmount");
			String[] actualFoodAmount=request.getParameterValues("actualFoodAmount");

			if(null!=foodNoDay && foodNoDay.length>0){
				for(int index=0;index<foodNoDay.length;index++){
					ClaimFoodDtlsBean foodDetailsDTO=new ClaimFoodDtlsBean();

					foodDetailsDTO.setRationCertiIssue(Integer.parseInt(CommonUtil.getStringParameter(nonDrawlCertificate[index], "0")));
					foodDetailsDTO.setFoodNoDay(Integer.parseInt(CommonUtil.getStringParameter(foodNoDay[index], "0")));
					foodDetailsDTO.setFoodAmount(Double.valueOf(CommonUtil.getStringParameter(foodAmount[index], "0")));
					foodDetailsDTO.setActualFoodAmount(Double.valueOf(CommonUtil.getStringParameter(actualFoodAmount[index], "0")));

					foodDtls.add(foodDetailsDTO);
				}
			}	
			return foodDtls;
		}

		private List<ClaimHotelDtlsBean> setHotelDetails(HttpServletRequest request) {

			List<ClaimHotelDtlsBean> hotelDtls=new ArrayList<>();

			String[] hotelOrMess=request.getParameterValues("hotelOrMess");
			String[] hotelNACTaken=request.getParameterValues("hotelNACTaken");
			String[] messLevel=request.getParameterValues("messLevel");
			String[] hotelName=request.getParameterValues("hotelName");
			String[] hotelCity=request.getParameterValues("hotelCity");
//			String[] destinationCityGrade=request.getParameterValues("destinationCityGrade");
			String[] hotelGSTNo=request.getParameterValues("hotelGSTNo");
			String[] checkInDate=request.getParameterValues("checkInDate");
			String[] checkOutDate=request.getParameterValues("checkOutDate");
			String[] hotelNoDays=request.getParameterValues("hotelNoDays");
			String[] hotelDailyAmount=request.getParameterValues("hotelDailyAmount");
			String[] hotelAmount=request.getParameterValues("hotelAmount");
			String[] hotelGSTAmount=request.getParameterValues("hotelGSTAmount");
			String[] actualHotelAmount=request.getParameterValues("actualHotelAmount");

			if(null!=hotelName && hotelName.length>0){
				for(int index=0;index<hotelName.length;index++){
					ClaimHotelDtlsBean hotelDetailsDTO = new ClaimHotelDtlsBean();

					hotelDetailsDTO.setHotelOrMess(hotelOrMess[index]);
					hotelDetailsDTO.setHotelNACTaken(hotelNACTaken[index]);
					hotelDetailsDTO.setMessLevel(messLevel[index]);
					hotelDetailsDTO.setHotelName(hotelName[index]);
					hotelDetailsDTO.setHotelCity(hotelCity[index]);
//					hotelDetailsDTO.setDestinationCityGrade(destinationCityGrade[index]);
					hotelDetailsDTO.setHotelGSTNo(hotelGSTNo[index]);
					hotelDetailsDTO.setCheckInDate(CommonUtil.formatString(checkInDate[index], hmFormat));
					hotelDetailsDTO.setCheckOutDate(CommonUtil.formatString(checkOutDate[index], hmFormat));
					hotelDetailsDTO.setHotelNoDays(Integer.parseInt(CommonUtil.getStringParameter(hotelNoDays[index], "0")));
					hotelDetailsDTO.setHotelDailyAmount(Double.valueOf(CommonUtil.getStringParameter(hotelDailyAmount[index], "0")));
					hotelDetailsDTO.setHotelAmount(Double.valueOf(CommonUtil.getStringParameter(hotelAmount[index], "0")));
					hotelDetailsDTO.setHotelGSTAmount(Double.valueOf(CommonUtil.getStringParameter(hotelGSTAmount[index], "0")));
					hotelDetailsDTO.setActualHotelAmount(Double.valueOf(CommonUtil.getStringParameter(actualHotelAmount[index], "0")));

					hotelDtls.add(hotelDetailsDTO);
				}
			}	
			return hotelDtls;
		}

		private List<ClaimNonDtsJrnyDtlsBean> setNonDTSJourneyData(HttpServletRequest request) {

			List<ClaimNonDtsJrnyDtlsBean> nonDTSJrny=new ArrayList<>();

			String[] nonDTSFromPlace=request.getParameterValues("nonDTSFromPlace");
			String[] nonDTSToPlace=request.getParameterValues("nonDTSToPlace");
			String[] nonDTSModeOfTravel=request.getParameterValues("nonDTSModeOfTravel");
//			String[] airlineType=request.getParameterValues("airlineType");
//			String[] otherAirlineType=request.getParameterValues("otherAirlineType");
//			String[] FAA_AuthorityNo=request.getParameterValues("FAA_AuthorityNo");
//			String[] FAA_AuthorityDate=request.getParameterValues("FAA_AuthorityDate");
			String[] nonDTSClassOfTravel=request.getParameterValues("nonDTSClassOfTravel");
			String[] nonDTSJryStartDate=request.getParameterValues("nonDTSJryStartDate");
			String[] nonDTSJryEndDate=request.getParameterValues("nonDTSJryEndDate");
			String[] nonDTSDistanceKM=request.getParameterValues("nonDTSDistanceKM");
			String[] nonDTSTicketNo=request.getParameterValues("nonDTSTicketNo");
			String[] nonDTSJryAmount=request.getParameterValues("nonDTSJryAmount");
			String[] nonDTSJryRefundAmount=request.getParameterValues("nonDTSJryRefundAmount");
			String[] nonDTSJryPerformed=request.getParameterValues("nonDTSJryPerformed");
			String[] nonDTSJrnyCanType=request.getParameterValues("nonDTSJrnyCanType");
			String[] nonDTSJrnyCanSanNo=request.getParameterValues("nonDTSJrnyCanSanNo");
			String[] nonDTSJrnyCanSanDate=request.getParameterValues("nonDTSJrnyCanSanDate");

			if(null!=nonDTSFromPlace && nonDTSFromPlace.length>0){
				for(int index=0;index<nonDTSFromPlace.length;index++){
					ClaimNonDtsJrnyDtlsBean nonDTSJourneyDTO=new ClaimNonDtsJrnyDtlsBean();

					nonDTSJourneyDTO.setNonDTSFromPlace(nonDTSFromPlace[index]);
					nonDTSJourneyDTO.setNonDTSToPlace(nonDTSToPlace[index]);
					nonDTSJourneyDTO.setNonDTSModeOfTravel(nonDTSModeOfTravel[index]);
//					nonDTSJourneyDTO.setAirlineType(airlineType[index]);
//					nonDTSJourneyDTO.setOtherAirlineType(otherAirlineType[index]);
//					nonDTSJourneyDTO.setAuthorityNo(FAA_AuthorityNo[index]);
//					nonDTSJourneyDTO.setAuthorityDate(FAA_AuthorityDate[index]);
					nonDTSJourneyDTO.setNonDTSClassOfTravel(nonDTSClassOfTravel[index]);
					nonDTSJourneyDTO.setNonDTSJryStartDate(CommonUtil.formatString(nonDTSJryStartDate[index], hmFormat));
					nonDTSJourneyDTO.setNonDTSJryEndDate(CommonUtil.formatString(nonDTSJryEndDate[index], hmFormat));
					nonDTSJourneyDTO.setNonDTSDistanceKM(Integer.parseInt(CommonUtil.getStringParameter(nonDTSDistanceKM[index], "0")));
					nonDTSJourneyDTO.setNonDTSTicketNo(nonDTSTicketNo[index]);
					nonDTSJourneyDTO.setNonDTSJryAmount(Double.valueOf(CommonUtil.getStringParameter(nonDTSJryAmount[index], "0")));
					nonDTSJourneyDTO.setNonDTSJryRefundAmount(Double.valueOf(CommonUtil.getStringParameter(nonDTSJryRefundAmount[index], "0")));
					nonDTSJourneyDTO.setNonDTSJryPerformed(Integer.parseInt(CommonUtil.getStringParameter(nonDTSJryPerformed[index], "0")));
					nonDTSJourneyDTO.setNonDTSJrnyCanType(Integer.parseInt(CommonUtil.getStringParameter(nonDTSJrnyCanType[index], "0")));
					nonDTSJourneyDTO.setNonDTSJrnyCanSanNo(nonDTSJrnyCanSanNo[index]);
					nonDTSJourneyDTO.setNonDTSJrnyCanSanDate(CommonUtil.formatString(nonDTSJrnyCanSanDate[index], dmyFormat));

					nonDTSJrny.add(nonDTSJourneyDTO);
				}
			}
			return nonDTSJrny;
		}

		private List<ClaimJourneyDtlsBean> setDTSJourneyData(HttpServletRequest request) {

			List<ClaimJourneyDtlsBean> dtsJrny=new ArrayList<ClaimJourneyDtlsBean>();
			String[] dtsJrnyData=request.getParameterValues("dtsJrnyRequestData");
			String[] dtsJrnyDate=request.getParameterValues("dtsJourneyDate");
			String[] dtsJrnyEndDate=request.getParameterValues("dtsJourneyEndDate");
			String[] dtsJourneyPerformed=request.getParameterValues("dtsJourneyPerformed");
			String[] dtsJrnyCanType=request.getParameterValues("dtsJrnyCanType");
			String[] dtsJrnyCanSanNo=request.getParameterValues("dtsJrnyCanSanNo");
			String[] dtsJrnyCanSanDate=request.getParameterValues("dtsJrnyCanSanDate");

			if(null!=dtsJrnyData && dtsJrnyData.length>0){
				for(int index=0;index<dtsJrnyData.length;index++){
					String[] jrnyData=dtsJrnyData[index].split("::");
					if(jrnyData.length==10){
						ClaimJourneyDtlsBean journeyDTO=new ClaimJourneyDtlsBean();
						journeyDTO.setBookingId(Optional.ofNullable(jrnyData[0]).orElse("0"));
						journeyDTO.setTravelMode(Integer.parseInt(CommonUtil.getStringParameter(jrnyData[1], "0")));
						journeyDTO.setBookingNo(Optional.ofNullable(jrnyData[2]).orElse("0"));
						journeyDTO.setJourneyClass(Optional.ofNullable(jrnyData[3]).orElse("0"));
						journeyDTO.setJourneyType(Integer.parseInt(CommonUtil.getStringParameter(jrnyData[4], "0")));
						journeyDTO.setFromStation(Optional.ofNullable(jrnyData[5]).orElse("0"));
						journeyDTO.setToStation(Optional.ofNullable(jrnyData[6]).orElse("0"));
						journeyDTO.setBookingAmount(Double.valueOf(CommonUtil.getStringParameter(jrnyData[7], "0")));
						journeyDTO.setRefundAmount(Double.valueOf(CommonUtil.getStringParameter(jrnyData[8], "0")));
						journeyDTO.setRequestId(Optional.ofNullable(jrnyData[9]).orElse("0"));
						journeyDTO.setJourneyStartDate(CommonUtil.formatString(dtsJrnyDate[index], hmFormat));
						journeyDTO.setJourneyEndDate(CommonUtil.formatString(dtsJrnyEndDate[index], hmFormat));
						journeyDTO.setJourneyPerformed(Integer.parseInt(CommonUtil.getStringParameter(dtsJourneyPerformed[index], "0")));
						journeyDTO.setJrnyCanType(Integer.parseInt(CommonUtil.getStringParameter(dtsJrnyCanType[index], "0")));
						journeyDTO.setJrnyCanSanNo(Optional.ofNullable(dtsJrnyCanSanNo[index]).orElse("0"));
						journeyDTO.setJrnyCanSanDate(CommonUtil.formatString(dtsJrnyCanSanDate[index], dmyFormat)); 

						dtsJrny.add(journeyDTO);
					}
				}
			}
			return dtsJrny;
		}



		public void setLTCClaimDetails(HttpServletRequest request) {

			this.setLevelId(Optional.ofNullable(request.getParameter("levelId")).orElse(""));

			this.setDesignatin(Optional.ofNullable(request.getParameter("personalDesignation")).orElse(""));
			this.setMoveSanctionNo(Optional.ofNullable(request.getParameter("moveSanctionNo")).orElse(""));
			this.setMovSanctionDate(CommonUtil.formatString(request.getParameter("moveSanctionDate"), dmyFormat));
			this.setJrnyStrtdTime(CommonUtil.formatString(request.getParameter("jrnyStartDate"), hmFormat));
			this.setBasicPay(Optional.ofNullable(request.getParameter("personalBasicPay")).orElse(""));
			this.setLeaveFrom(CommonUtil.formatString(request.getParameter("leaveFrom"), dmyFormat));
			this.setLeaveTo(CommonUtil.formatString(request.getParameter("leaveTo"), dmyFormat));
			this.setHometown(Optional.ofNullable(request.getParameter("personalHomeTown")).orElse(""));
			this.setLeaveStn(Optional.ofNullable(request.getParameter("declaredLeaveStation")).orElse(""));
			this.setLtcBlockYr(Optional.ofNullable(request.getParameter("ltcBlockYear")).orElse(""));
			this.setLtcCalYr(Optional.ofNullable(request.getParameter("ltcCalendarYear")).orElse(""));

			this.setClaimJourneyDtls(setDTSLTCJourneyData(request));
			this.setClaimNonDtsJrnyDtls(setNonDTSLTCJourneyData(request));

			this.setTravelID(Optional.ofNullable(request.getParameter("travelId")).orElse(""));
			this.setClaimCertify(request.getParameterValues("claimCertify"));
			this.setTotalSpentAmount(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("totalSpentAmount"), "0")));
			this.setTotalAdvanceAmount(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("totalAdvanceAmount"), "0")));
			this.setTotalRefundAmount(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("totalRefundAmount"), "0")));
			this.setTotalClaimedAmount(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("totalClaimedAmount"), "0")));
			this.setTrRuleName(Optional.ofNullable(request.getParameter("trRuleName")).orElse(""));

			this.setClaimDocDtls(setClaimDocumentData(request));
			this.setClaimAdvanceDtls(setClaimAdvDtl(request));
			this.setServiceType(Integer.parseInt(CommonUtil.getStringParameter(request.getParameter("userServiceType"), "0"))); 

		}

		private List<ClaimJourneyDtlsBean> setDTSLTCJourneyData(HttpServletRequest request) {
			
			List<ClaimJourneyDtlsBean> dtsJrny=new ArrayList<>();
			String[] dtsJrnyData=request.getParameterValues("dtsJrnyRequestData");
			String[] dtsJrnyDate=request.getParameterValues("dtsJourneyDate");
			String[] dtsJrnyEndDate=request.getParameterValues("dtsJourneyEndDate");
			String[] dtsLtcJrnyTableIdlen=request.getParameterValues("dtsLtcJrnyTableIdlen");
			
			if(null!=dtsJrnyData && dtsJrnyData.length>0){
				for(int index=0;index<dtsJrnyData.length;index++){
					String[] jrnyData=dtsJrnyData[index].split("::");
					if(jrnyData.length==12){
						ClaimJourneyDtlsBean journeyDTO = new ClaimJourneyDtlsBean();
						journeyDTO.setBookingId(Optional.ofNullable(jrnyData[0]).orElse("0"));

						journeyDTO.setTravelMode(Integer.parseInt(CommonUtil.getStringParameter(jrnyData[1], "0")));
						journeyDTO.setBookingNo(Optional.ofNullable(jrnyData[2]).orElse("0"));
						journeyDTO.setJourneyClass(Optional.ofNullable(jrnyData[3]).orElse("0"));
						journeyDTO.setJourneyType(Integer.parseInt(CommonUtil.getStringParameter(jrnyData[4], "0")));
						journeyDTO.setFromStation(Optional.ofNullable(jrnyData[5]).orElse("0"));
						journeyDTO.setToStation(Optional.ofNullable(jrnyData[6]).orElse("0"));
						journeyDTO.setBookingAmount(Double.valueOf(CommonUtil.getStringParameter(jrnyData[7], "0")));
						journeyDTO.setRefundAmount(Double.valueOf(CommonUtil.getStringParameter(jrnyData[8], "0")));
						journeyDTO.setRequestId(Optional.ofNullable(jrnyData[9]).orElse("0"));
						journeyDTO.setJourneyStartDate(CommonUtil.formatString(dtsJrnyDate[index], hmFormat));
						journeyDTO.setJourneyEndDate(CommonUtil.formatString(dtsJrnyEndDate[index], hmFormat));
						journeyDTO.setSector(Optional.ofNullable(jrnyData[10]).orElse("0"));
						journeyDTO.setClaimedAmount(Double.valueOf(CommonUtil.getStringParameter(jrnyData[11], "0")));

						journeyDTO.setClaimPassDtls(setLTCPassengerDetails(dtsLtcJrnyTableIdlen[index],request));

						dtsJrny.add(journeyDTO);
					}
				}
			}
			return dtsJrny;
		}

		private List<ClaimPassDtlsBean> setLTCPassengerDetails(String passSeq, HttpServletRequest request) {

			List<ClaimPassDtlsBean> passengers=new ArrayList<>();
			String[] dtsLtcPassengers=request.getParameterValues("dtsJrnyPassengerData"+passSeq);
			String[] dtsJourneyPerformed=request.getParameterValues("dtsJourneyPerformed"+passSeq);
			String[] dtsJrnyCanType=request.getParameterValues("dtsJrnyCanType"+passSeq);
			String[] dtsJrnyCanSanNo=request.getParameterValues("dtsJrnyCanSanNo"+passSeq);
			String[] dtsJrnyCanSanDate=request.getParameterValues("dtsJrnyCanSanDate"+passSeq);

			
			
			if(null!=dtsLtcPassengers && dtsLtcPassengers.length>0){
				for(int index = 0; index < dtsLtcPassengers.length; index++){
					String[] passData = dtsLtcPassengers[index].split("::");
					if(passData.length==8){
						ClaimPassDtlsBean passengerDTO = new ClaimPassDtlsBean();
						passengerDTO.setTravellerName(Optional.ofNullable(passData[0]).orElse(""));
						passengerDTO.setRelation(Optional.ofNullable(passData[1]).orElse(""));
						passengerDTO.setAge(Integer.parseInt(CommonUtil.getStringParameter(passData[2], "0")));
						passengerDTO.setTicketNo(Optional.ofNullable(passData[3]).orElse(""));
						passengerDTO.setBookingAmount(Double.valueOf(CommonUtil.getStringParameter(passData[4], "0")));
						passengerDTO.setRefundAmount(Double.valueOf(CommonUtil.getStringParameter(passData[5], "0")));
						passengerDTO.setClaimedAmount(Double.valueOf(CommonUtil.getStringParameter(passData[6], "0")));
						passengerDTO.setCurrentStatus(Integer.parseInt(CommonUtil.getStringParameter(passData[7], "3")));
						passengerDTO.setJrnyPerfmd(Integer.parseInt(CommonUtil.getStringParameter(dtsJourneyPerformed[index], "0")));
						passengerDTO.setCancellationGrnd(Integer.parseInt(CommonUtil.getStringParameter(dtsJrnyCanType[index], "0")));
						passengerDTO.setCancellationRmrk(Optional.ofNullable(dtsJrnyCanSanNo[index]).orElse(""));
						passengerDTO.setCancellationDate(CommonUtil.formatString(dtsJrnyCanSanDate[index], dmyFormat)); 

						passengers.add(passengerDTO);
					}
				}
			}
			return passengers;
		}

		private List<ClaimNonDtsJrnyDtlsBean> setNonDTSLTCJourneyData(HttpServletRequest request) {

			List<ClaimNonDtsJrnyDtlsBean> nonDTSJrny=new ArrayList<>();

			String[] nonDTSJourneyType=request.getParameterValues("nonDTSJourneyType");
			String[] nonDTSModeOfTravel=request.getParameterValues("nonDTSModeOfTravel");
			String[] nonDTSFromPlace=request.getParameterValues("nonDTSFromPlace");
			String[] nonDTSToPlace=request.getParameterValues("nonDTSToPlace");
			String[] nonDTSClassOfTravel=request.getParameterValues("nonDTSClassOfTravel");
			String[] nonDTSJryStartDate=request.getParameterValues("nonDTSJryStartDate");
			String[] nonDTSJryEndDate=request.getParameterValues("nonDTSJryEndDate");
			String[] nonDTSSector=request.getParameterValues("nonDTSSector");
			String[] nonDTSTicketNo=request.getParameterValues("nonDTSTicketNo");
			String[] nonDTSDistanceKM=request.getParameterValues("nonDTSDistanceKM");
			String[] nonDTSJryAmount=request.getParameterValues("nonDTSJryAmount");
			String[] nonDTSJryRefundAmount=request.getParameterValues("nonDTSJryRefundAmount");
			String[] nonDTSJryClaimAmount=request.getParameterValues("nonDTSJryClaimAmount");
//			String[] airlineType=request.getParameterValues("airlineType");
//			String[] otherAirlineType=request.getParameterValues("otherAirlineType");
//			String[] FAA_AuthorityNo=request.getParameterValues("FAA_AuthorityNo");
//			String[] FAA_AuthorityDate=request.getParameterValues("FAA_AuthorityDate");
			String[] ltcJrnyTableIdlen=request.getParameterValues("ltcJrnyTableIdlen");

			if(null!=nonDTSFromPlace && nonDTSFromPlace.length>0){
				for(int index=0;index<nonDTSFromPlace.length;index++){
					ClaimNonDtsJrnyDtlsBean nonDTSJourneyDTO = new ClaimNonDtsJrnyDtlsBean();

					nonDTSJourneyDTO.setJourneyType(Integer.parseInt(CommonUtil.getStringParameter(nonDTSJourneyType[index], "0")));
					nonDTSJourneyDTO.setNonDTSModeOfTravel(Optional.ofNullable(nonDTSModeOfTravel[index]).orElse(""));
					nonDTSJourneyDTO.setNonDTSFromPlace(Optional.ofNullable(nonDTSFromPlace[index]).orElse(""));
					nonDTSJourneyDTO.setNonDTSToPlace(Optional.ofNullable(nonDTSToPlace[index]).orElse(""));
					nonDTSJourneyDTO.setNonDTSClassOfTravel(Optional.ofNullable(nonDTSClassOfTravel[index]).orElse(""));
					nonDTSJourneyDTO.setNonDTSJryStartDate(CommonUtil.formatString(nonDTSJryStartDate[index], hmFormat));
					nonDTSJourneyDTO.setNonDTSJryEndDate(CommonUtil.formatString(nonDTSJryEndDate[index], hmFormat));
					nonDTSJourneyDTO.setSector(Optional.ofNullable(nonDTSSector[index]).orElse(""));
					nonDTSJourneyDTO.setNonDTSTicketNo(Optional.ofNullable(nonDTSTicketNo[index]).orElse(""));
					nonDTSJourneyDTO.setNonDTSDistanceKM(Integer.parseInt(CommonUtil.getStringParameter(nonDTSDistanceKM[index], "0")));
					nonDTSJourneyDTO.setNonDTSJryAmount(Double.valueOf(CommonUtil.getStringParameter(nonDTSJryAmount[index], "0")));
					nonDTSJourneyDTO.setNonDTSJryRefundAmount(Double.valueOf(CommonUtil.getStringParameter(nonDTSJryRefundAmount[index], "0")));
					nonDTSJourneyDTO.setClaimedAmount(Double.valueOf(CommonUtil.getStringParameter(nonDTSJryClaimAmount[index], "0")));
					nonDTSJourneyDTO.setClaimPassDtls(setLTCNonDTSPassengerDetails(ltcJrnyTableIdlen[index],request));

					nonDTSJrny.add(nonDTSJourneyDTO);
				}
			}
			return nonDTSJrny;
		}

		private List<ClaimPassDtlsBean> setLTCNonDTSPassengerDetails(String passSeq, HttpServletRequest request) {

			List<ClaimPassDtlsBean> passengers=new ArrayList<>();
			String[] nonDTSPassDtlsTravellerName=request.getParameterValues("nonDTSPassDtlsTravellerName"+passSeq);
			String[] nonDTSPassDtlsRelation=request.getParameterValues("nonDTSPassDtlsRelation"+passSeq);
			String[] nonDTSPassDtlsAge=request.getParameterValues("nonDTSPassDtlsAge"+passSeq);
			String[] nonDTSPassDtlsTicketNo=request.getParameterValues("nonDTSPassDtlsTicketNo"+passSeq);
			String[] nonDTSPassDtlsJourneyPerformed=request.getParameterValues("nonDTSPassDtlsJourneyPerformed"+passSeq);
			String[] nonDTSPassDtlsBookingAmount=request.getParameterValues("nonDTSPassDtlsBookingAmount"+passSeq);
			String[] nonDTSPassDtlsRefundAmount=request.getParameterValues("nonDTSPassDtlsRefundAmount"+passSeq);
			String[] nonDTSPassDtlsClaimedAmount=request.getParameterValues("nonDTSPassDtlsClaimedAmount"+passSeq);
			String[] nonDTSJrnyCanType=request.getParameterValues("nonDTSJrnyCanType"+passSeq);
			String[] nonDTSJrnyCanSanNo=request.getParameterValues("nonDTSJrnyCanSanNo"+passSeq);
			String[] nonDTSJrnyCanSanDate=request.getParameterValues("nonDTSJrnyCanSanDate"+passSeq);

			if(null!=nonDTSPassDtlsTravellerName && nonDTSPassDtlsTravellerName.length>0){
				for(int i=0; i < nonDTSPassDtlsTravellerName.length; i++){
					ClaimPassDtlsBean passengerDTO = new ClaimPassDtlsBean();

					passengerDTO.setTravellerName(Optional.ofNullable(nonDTSPassDtlsTravellerName[i]).orElse(""));
					passengerDTO.setRelation(Optional.ofNullable(nonDTSPassDtlsRelation[i]).orElse(""));
					passengerDTO.setAge(Integer.parseInt(CommonUtil.getStringParameter(nonDTSPassDtlsAge[i], "0")));
					passengerDTO.setTicketNo(Optional.ofNullable(nonDTSPassDtlsTicketNo[i]).orElse(""));
					passengerDTO.setBookingAmount(Double.valueOf(CommonUtil.getStringParameter(nonDTSPassDtlsBookingAmount[i], "0")));
					passengerDTO.setRefundAmount(Double.valueOf(CommonUtil.getStringParameter(nonDTSPassDtlsRefundAmount[i], "0")));
					passengerDTO.setClaimedAmount(Double.valueOf(CommonUtil.getStringParameter(nonDTSPassDtlsClaimedAmount[i], "0")));
					passengerDTO.setJrnyPerfmd(Integer.parseInt(CommonUtil.getStringParameter(nonDTSPassDtlsJourneyPerformed[i], "0")));
					passengerDTO.setCancellationGrnd(Integer.parseInt(CommonUtil.getStringParameter(nonDTSJrnyCanType[i], "0")));
					passengerDTO.setCancellationRmrk(Optional.ofNullable(nonDTSJrnyCanSanNo[i]).orElse(""));
					passengerDTO.setCancellationDate(CommonUtil.formatString(nonDTSJrnyCanSanDate[i], dmyFormat)); 
					
					passengers.add(passengerDTO);
				}
			}	
			return passengers;
		}



		public void setPTClaimDetails(HttpServletRequest request) {

			this.setPayAccntNo(Optional.ofNullable(request.getParameter("personalPayAccNo")).orElse(""));
			this.setLevelId(Optional.ofNullable(request.getParameter("personalPayLevel")).orElse(""));
			this.setBasicPay(Optional.ofNullable(request.getParameter("personalBasicPay")).orElse(""));
			this.setTrnsfrFrom(Optional.ofNullable(request.getParameter("transferredFrom")).orElse(""));
			this.setTrnsfrTO(Optional.ofNullable(request.getParameter("transferredTo")).orElse(""));
			this.setJrnyStrtdFrom(Optional.ofNullable(request.getParameter("jrnyStartedFrom")).orElse(""));
			this.setJrnyStrtdTime(CommonUtil.formatString(request.getParameter("jrnyStartDate"), hmFormat));
			this.setJrnyStrtdTo(Optional.ofNullable(request.getParameter("jrnyStartedTo")).orElse(""));
			this.setJrnyFnshTime(CommonUtil.formatString(request.getParameter("jrnyStartToDate"), hmFormat));
			this.setTrRuleName(Optional.ofNullable(request.getParameter("trRuleName")).orElse(""));
			this.setMoveSanctionNo(Optional.ofNullable(request.getParameter("moveSanctionNo")).orElse(""));
			this.setMovSanctionDate(CommonUtil.formatString(request.getParameter("moveSanctionDate"), dmyFormat));
			this.setMoveIssungAuth(Optional.ofNullable(request.getParameter("moveAuthority")).orElse(""));

			this.setTravelID(Optional.ofNullable(request.getParameter("travelId")).orElse(""));
			this.setClaimCertify(request.getParameterValues("claimCertify"));

			this.setTotalSpentAmount(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("totalSpentAmount"), "0")));
			this.setTotalAdvanceAmount(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("totalAdvanceAmount"), "0")));
			this.setTotalRefundAmount(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("totalRefundAmount"), "0")));
			this.setTotalClaimedAmount(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("totalClaimedAmount"), "0")));

			this.setClaimDocDtls(setClaimDocumentData(request));
			this.setClaimJourneyDtls(setDTSPTJourneyData(request));
			this.setClaimNonDtsJrnyDtls(setNonDTSPTJourneyData(request));
			this.setClaimPersonalEffectsBean(setPersonalEffectsData(request));
			this.setClaimAdvanceDtls(setClaimAdvDtlPT(request));

			this.setServiceType(Integer.parseInt(CommonUtil.getStringParameter(request.getParameter("userServiceType"), "0")));

		}

		private ClaimAdvanceDtlsBean setClaimAdvDtlPT(HttpServletRequest request) {
			ClaimAdvanceDtlsBean advanceDtlsBean = new ClaimAdvanceDtlsBean();

			advanceDtlsBean.setPaoAdvLugg(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("advLuggageFromPAO"), "0")));
			advanceDtlsBean.setPaoAdvConvyance(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("advCoveyanceFromPAO"), "0")));
			advanceDtlsBean.setPaoAdvCtg(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("advCTGFromPAO"), "0")));
			advanceDtlsBean.setCtgAmount(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("ClaimGTCAmt"), "0")));
			advanceDtlsBean.setConvPartOfLugg(Integer.valueOf(CommonUtil.getStringParameter(request.getParameter("conveyancePartOfLuggage"), "0")));
			advanceDtlsBean.setConveyanceName(Optional.ofNullable(request.getParameter("conveyanceType")).orElse(""));
			advanceDtlsBean.setCtgPercentage(Optional.ofNullable(request.getParameter("CTGPercentage")).orElse(""));
			advanceDtlsBean.setAdvFromPAO(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("advFromPAO"), "0")));
			advanceDtlsBean.setAdvFromPAODate(CommonUtil.formatString(request.getParameter("advFromPAODate"), dmyFormat ));
			advanceDtlsBean.setMroRefund(Double.valueOf(CommonUtil.getStringParameter(request.getParameter("mroRefund"), "0")));
			advanceDtlsBean.setMroNumber(Optional.ofNullable(request.getParameter("mroNumber")).orElse("0"));
			advanceDtlsBean.setMroDate(CommonUtil.formatString(request.getParameter("mroDate"), dmyFormat));
			advanceDtlsBean.setCounterPersonalNo(Optional.ofNullable(request.getParameter("counterPersonalNo")).orElse(""));
			advanceDtlsBean.setCounterPersonalName(Optional.ofNullable(request.getParameter("counterPersonalName")).orElse(""));
			advanceDtlsBean.setCounterPersonalLevel(Optional.ofNullable(request.getParameter("counterPersonalLevel")).orElse(""));
			advanceDtlsBean.setClaimPreferredDate(CommonUtil.formatString(request.getParameter("claimPreferredDate"), dmyFormat));
			advanceDtlsBean.setClaimPreferredSanction(Optional.ofNullable(request.getParameter("claimPreferredSanction")).orElse(""));
			advanceDtlsBean.setClaimPreferredSanctionNo(Optional.ofNullable(request.getParameter("claimPreferredSanctionNo")).orElse(""));
			advanceDtlsBean.setClaimPreferredSanctionDate(CommonUtil.formatString(request.getParameter("claimPreferredSanctionDate"), dmyFormat));

			return advanceDtlsBean;
		}

		private List<ClaimPersonalEffectsBean> setPersonalEffectsData(HttpServletRequest request) {

			List<ClaimPersonalEffectsBean> personalEffects=new ArrayList<>();
			String[] particularName0=request.getParameterValues("particularName_0");
			String[] particularWeight0=request.getParameterValues("particularWeight_0");
			String[] particularConveyance0=request.getParameterValues("particularConveyance_0");
			String[] particularDistance0=request.getParameterValues("particularDistance_0");
			String[] particularClaimAmt0=request.getParameterValues("particularClaimAmt_0");

			if(null!=particularName0 && particularName0.length>0){
				for(int index=0;index<particularName0.length;index++){
					ClaimPersonalEffectsBean effectsDTO = new ClaimPersonalEffectsBean();
					effectsDTO.setType(0);
					effectsDTO.setParticularName(Optional.ofNullable(particularName0[index]).orElse(""));
					effectsDTO.setParticularWeight(Integer.valueOf(CommonUtil.getStringParameter(particularWeight0[index], "0")));
					effectsDTO.setParticularConveyance(Optional.ofNullable(particularConveyance0[index]).orElse(""));
					effectsDTO.setParticularDistance(Integer.valueOf(CommonUtil.getStringParameter(particularDistance0[index], "0")));
					effectsDTO.setParticularClaimAmt(Double.valueOf(CommonUtil.getStringParameter(particularClaimAmt0[index], "0")));
					personalEffects.add(effectsDTO);
				}
			}

			String[] particularName1=request.getParameterValues("particularName_1");
			String[] particularConveyance1=request.getParameterValues("particularConveyance_1");
			String[] particularDistance1=request.getParameterValues("particularDistance_1");
			String[] particularClaimAmt1=request.getParameterValues("particularClaimAmt_1");

			if(null!=particularName1 && particularName1.length>0){
				for(int index=0;index<particularName1.length;index++){
					ClaimPersonalEffectsBean effectsDTO=new ClaimPersonalEffectsBean();
					effectsDTO.setType(1);
					effectsDTO.setParticularName(Optional.ofNullable(particularName1[index]).orElse(""));
					effectsDTO.setParticularWeight(0);  
					effectsDTO.setParticularConveyance(Optional.ofNullable(particularConveyance1[index]).orElse(""));
					effectsDTO.setParticularDistance(Integer.valueOf(CommonUtil.getStringParameter(particularDistance1[index], "0")));
					effectsDTO.setParticularClaimAmt(Double.valueOf(CommonUtil.getStringParameter(particularClaimAmt1[index], "0")));
					personalEffects.add(effectsDTO);
				}
			} 
			return personalEffects;
		}

		private List<ClaimJourneyDtlsBean> setDTSPTJourneyData(HttpServletRequest request) {

			List<ClaimJourneyDtlsBean> dtsJrny=new ArrayList<>();
			String[] dtsJrnyData=request.getParameterValues("dtsJrnyRequestData");
			String[] dtsJrnyDate=request.getParameterValues("dtsJourneyDate");
			String[] dtsJrnyEndDate=request.getParameterValues("dtsJourneyEndDate");
			String[] dtsPTJrnyTableIdlen=request.getParameterValues("dtsPTJrnyTableIdlen");

			if(null!=dtsJrnyData && dtsJrnyData.length>0){
				for(int index=0;index<dtsJrnyData.length;index++){
					String[] jrnyData=dtsJrnyData[index].split("::");
					if(jrnyData.length==12){
						ClaimJourneyDtlsBean journeyDTO = new ClaimJourneyDtlsBean();

						journeyDTO.setBookingId(Optional.ofNullable(jrnyData[0]).orElse("0"));
						journeyDTO.setTravelMode(Integer.parseInt(CommonUtil.getStringParameter(jrnyData[1], "0")));
						journeyDTO.setBookingNo(Optional.ofNullable(jrnyData[2]).orElse("0"));
						journeyDTO.setJourneyClass(Optional.ofNullable(jrnyData[3]).orElse("0"));
						journeyDTO.setJourneyType(Integer.parseInt(CommonUtil.getStringParameter(jrnyData[4], "0")));
						journeyDTO.setFromStation(Optional.ofNullable(jrnyData[5]).orElse("0"));
						journeyDTO.setToStation(Optional.ofNullable(jrnyData[6]).orElse("0"));
						journeyDTO.setBookingAmount(Double.valueOf(CommonUtil.getStringParameter(jrnyData[7], "0")));
						journeyDTO.setRefundAmount(Double.valueOf(CommonUtil.getStringParameter(jrnyData[8], "0")));
						journeyDTO.setRequestId(Optional.ofNullable(jrnyData[9]).orElse("0"));
						journeyDTO.setSector(Optional.ofNullable(jrnyData[10]).orElse(""));
						journeyDTO.setClaimedAmount(Double.valueOf(CommonUtil.getStringParameter(jrnyData[11], "0")));
						journeyDTO.setJourneyStartDate(CommonUtil.formatString(dtsJrnyDate[index], hmFormat));
						journeyDTO.setJourneyEndDate(CommonUtil.formatString(dtsJrnyEndDate[index], hmFormat));

						journeyDTO.setClaimPassDtls(setPTPassengerDetails(dtsPTJrnyTableIdlen[index],request));

						dtsJrny.add(journeyDTO);
					}
				}
			}
			return dtsJrny;
		}

		private List<ClaimPassDtlsBean> setPTPassengerDetails(String passSeq, HttpServletRequest request) {

		List<ClaimPassDtlsBean> passengers=new ArrayList<>();
		String[] dtsLtcPassengers=request.getParameterValues("dtsJrnyPassengerData"+passSeq);
		String[] dtsJourneyPerformed=request.getParameterValues("dtsJourneyPerformed"+passSeq);
		String[] dtsJrnyCanType=request.getParameterValues("dtsJrnyCanType"+passSeq);
		String[] dtsJrnyCanSanNo=request.getParameterValues("dtsJrnyCanSanNo"+passSeq);
		String[] dtsJrnyCanSanDate=request.getParameterValues("dtsJrnyCanSanDate"+passSeq);

		if(null!=dtsLtcPassengers && dtsLtcPassengers.length>0){
			for(int index=0;index<dtsLtcPassengers.length;index++){
				String[] passData=dtsLtcPassengers[index].split("::");
				if(passData.length==8){
					ClaimPassDtlsBean passengerDTO = new ClaimPassDtlsBean();

					passengerDTO.setTravellerName(Optional.ofNullable(passData[0]).orElse(""));
					passengerDTO.setRelation(Optional.ofNullable(passData[1]).orElse(""));
					passengerDTO.setAge(Integer.parseInt(CommonUtil.getStringParameter(passData[2], "0")));
					passengerDTO.setTicketNo(Optional.ofNullable(passData[3]).orElse(""));
					passengerDTO.setBookingAmount(Double.valueOf(CommonUtil.getStringParameter(passData[4], "0")));
					passengerDTO.setRefundAmount(Double.valueOf(CommonUtil.getStringParameter(passData[5], "0")));
					passengerDTO.setClaimedAmount(Double.valueOf(CommonUtil.getStringParameter(passData[6], "0")));
					passengerDTO.setCurrentStatus(Integer.parseInt(CommonUtil.getStringParameter(passData[7], "3")));
					passengerDTO.setJrnyPerfmd(Integer.parseInt(CommonUtil.getStringParameter(dtsJourneyPerformed[index], "0")));
					passengerDTO.setCancellationGrnd(Integer.parseInt(CommonUtil.getStringParameter(dtsJrnyCanType[index], "0")));
					passengerDTO.setCancellationRmrk(Optional.ofNullable(dtsJrnyCanSanNo[index]).orElse(""));
					passengerDTO.setCancellationDate(CommonUtil.formatString(dtsJrnyCanSanDate[index], dmyFormat)); 

					passengers.add(passengerDTO);
				}
			}
		}
		return passengers;
	}

	private List<ClaimNonDtsJrnyDtlsBean> setNonDTSPTJourneyData(HttpServletRequest request) {

		List<ClaimNonDtsJrnyDtlsBean> nonDTSJrny = new ArrayList<>();

		String[] nonDTSJourneyType=request.getParameterValues("nonDTSJourneyType");
		String[] nonDTSModeOfTravel=request.getParameterValues("nonDTSModeOfTravel");
		String[] nonDTSFromPlace=request.getParameterValues("nonDTSFromPlace");
		String[] nonDTSToPlace=request.getParameterValues("nonDTSToPlace");
		String[] nonDTSClassOfTravel=request.getParameterValues("nonDTSClassOfTravel");
		String[] nonDTSJryStartDate=request.getParameterValues("nonDTSJryStartDate");
		String[] nonDTSJryEndDate=request.getParameterValues("nonDTSJryEndDate");
		String[] nonDTSSector=request.getParameterValues("nonDTSSector");
		String[] nonDTSTicketNo=request.getParameterValues("nonDTSTicketNo");
		String[] nonDTSDistanceKM=request.getParameterValues("nonDTSDistanceKM");
		String[] nonDTSJryAmount=request.getParameterValues("nonDTSJryAmount");
		String[] nonDTSJryRefundAmount=request.getParameterValues("nonDTSJryRefundAmount");
		String[] nonDTSJryClaimAmount=request.getParameterValues("nonDTSJryClaimAmount");
		String[] ptJrnyTableIdlen=request.getParameterValues("ptJrnyTableIdlen");

		if(null!=nonDTSFromPlace && nonDTSFromPlace.length>0){
			for(int index=0;index<nonDTSFromPlace.length;index++){
				ClaimNonDtsJrnyDtlsBean nonDTSJourneyDTO = new ClaimNonDtsJrnyDtlsBean();

				nonDTSJourneyDTO.setJourneyType(Integer.parseInt(CommonUtil.getStringParameter(nonDTSJourneyType[index], "0"))); 
				nonDTSJourneyDTO.setNonDTSModeOfTravel(Optional.ofNullable(nonDTSModeOfTravel[index]).orElse(""));
				nonDTSJourneyDTO.setNonDTSFromPlace(Optional.ofNullable(nonDTSFromPlace[index]).orElse(""));
				nonDTSJourneyDTO.setNonDTSToPlace(Optional.ofNullable(nonDTSToPlace[index]).orElse(""));
				nonDTSJourneyDTO.setNonDTSClassOfTravel(Optional.ofNullable(nonDTSClassOfTravel[index]).orElse(""));
				nonDTSJourneyDTO.setNonDTSJryStartDate(CommonUtil.formatString(nonDTSJryStartDate[index], hmFormat));
				nonDTSJourneyDTO.setNonDTSJryEndDate(CommonUtil.formatString(nonDTSJryEndDate[index], hmFormat));
				nonDTSJourneyDTO.setSector(Optional.ofNullable(nonDTSSector[index]).orElse(""));
				nonDTSJourneyDTO.setNonDTSTicketNo(Optional.ofNullable(nonDTSTicketNo[index]).orElse(""));
				nonDTSJourneyDTO.setNonDTSDistanceKM(Integer.parseInt(CommonUtil.getStringParameter(nonDTSDistanceKM[index], "0")));
				nonDTSJourneyDTO.setNonDTSJryAmount(Double.valueOf(CommonUtil.getStringParameter(nonDTSJryAmount[index], "0")));
				nonDTSJourneyDTO.setNonDTSJryRefundAmount(Double.valueOf(CommonUtil.getStringParameter(nonDTSJryRefundAmount[index], "0")));
				nonDTSJourneyDTO.setClaimedAmount(Double.valueOf(CommonUtil.getStringParameter(nonDTSJryClaimAmount[index], "0")));
				nonDTSJourneyDTO.setClaimPassDtls(setPTNonDTSPassengerDetails(ptJrnyTableIdlen[index],request));

				nonDTSJrny.add(nonDTSJourneyDTO);
			}
		}
		return nonDTSJrny;
	}

	private List<ClaimPassDtlsBean> setPTNonDTSPassengerDetails(String passSeq, HttpServletRequest request) {

		List<ClaimPassDtlsBean> passengers=new ArrayList<>();
		String[] nonDTSPassDtlsTravellerName=request.getParameterValues("nonDTSPassDtlsTravellerName"+passSeq);
		String[] nonDTSPassDtlsRelation=request.getParameterValues("nonDTSPassDtlsRelation"+passSeq);
		String[] nonDTSPassDtlsAge=request.getParameterValues("nonDTSPassDtlsAge"+passSeq);
		String[] nonDTSPassDtlsTicketNo=request.getParameterValues("nonDTSPassDtlsTicketNo"+passSeq);
		String[] nonDTSPassDtlsJourneyPerformed=request.getParameterValues("nonDTSPassDtlsJourneyPerformed"+passSeq);
		String[] nonDTSPassDtlsBookingAmount=request.getParameterValues("nonDTSPassDtlsBookingAmount"+passSeq);
		String[] nonDTSPassDtlsRefundAmount=request.getParameterValues("nonDTSPassDtlsRefundAmount"+passSeq);
		String[] nonDTSPassDtlsClaimedAmount=request.getParameterValues("nonDTSPassDtlsClaimedAmount"+passSeq);
		String[] nonDTSJrnyCanType=request.getParameterValues("nonDTSJrnyCanType"+passSeq);
		String[] nonDTSJrnyCanSanNo=request.getParameterValues("nonDTSJrnyCanSanNo"+passSeq);
		String[] nonDTSJrnyCanSanDate=request.getParameterValues("nonDTSJrnyCanSanDate"+passSeq);

		if(null!=nonDTSPassDtlsTravellerName && nonDTSPassDtlsTravellerName.length>0){
			for(int index=0;index<nonDTSPassDtlsTravellerName.length;index++){
				ClaimPassDtlsBean passengerDTO = new ClaimPassDtlsBean();

				passengerDTO.setTravellerName(Optional.ofNullable(nonDTSPassDtlsTravellerName[index]).orElse(""));
				passengerDTO.setRelation(Optional.ofNullable(nonDTSPassDtlsRelation[index]).orElse(""));
				passengerDTO.setAge(Integer.parseInt(CommonUtil.getStringParameter(nonDTSPassDtlsAge[index], "0")));
				passengerDTO.setTicketNo(Optional.ofNullable(nonDTSPassDtlsTicketNo[index]).orElse(""));
				passengerDTO.setBookingAmount(Double.valueOf(CommonUtil.getStringParameter(nonDTSPassDtlsBookingAmount[index], "0")));
				passengerDTO.setRefundAmount(Double.valueOf(CommonUtil.getStringParameter(nonDTSPassDtlsRefundAmount[index], "0")));
				passengerDTO.setClaimedAmount(Double.valueOf(CommonUtil.getStringParameter(nonDTSPassDtlsClaimedAmount[index], "0")));
				passengerDTO.setJrnyPerfmd(Integer.parseInt(CommonUtil.getStringParameter(nonDTSPassDtlsJourneyPerformed[index], "0")));
				passengerDTO.setCancellationGrnd(Integer.parseInt(CommonUtil.getStringParameter(nonDTSJrnyCanType[index], "0")));
				passengerDTO.setCancellationRmrk(Optional.ofNullable(nonDTSJrnyCanSanNo[index]).orElse(""));
				passengerDTO.setCancellationDate(CommonUtil.formatString(nonDTSJrnyCanSanDate[index], dmyFormat)); 

				passengers.add(passengerDTO);
			}
		}	
		return passengers;
	}

}
