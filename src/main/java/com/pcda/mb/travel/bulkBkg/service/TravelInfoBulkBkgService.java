package com.pcda.mb.travel.bulkBkg.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.AirPort;
import com.pcda.common.model.Category;
import com.pcda.common.model.CodeHead;
import com.pcda.common.model.DODServices;
import com.pcda.common.model.GradePayRankModel;
import com.pcda.common.model.Level;
import com.pcda.common.model.LtcSpecialSector;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.RailStation;
import com.pcda.common.model.TRJourney;
import com.pcda.common.model.TRQuestion;
import com.pcda.common.model.TRRequisite;
import com.pcda.common.model.TRScheme;
import com.pcda.common.model.TRTravelMode;
import com.pcda.common.model.TravelRule;
import com.pcda.common.model.VisitorModel;
import com.pcda.common.services.AirportServices;
import com.pcda.common.services.CategoryServices;
import com.pcda.common.services.CodeHeadServices;
import com.pcda.common.services.GradePayRankServices;
import com.pcda.common.services.LevelServices;
import com.pcda.common.services.LtcSpecialSectorServices;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.StationServices;
import com.pcda.common.services.TRRuleService;
import com.pcda.common.services.UserServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.travel.bulkBkg.model.AirStationFromBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.model.AirStationToBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.model.StationListFromBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.model.StationListToBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.model.TRQuestionBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.model.TravellerInfoBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.util.JourneyCountBulkBkgUtil;
import com.pcda.mb.travel.bulkBkg.util.JourneyRequestBulkBkgCommonUtil;
import com.pcda.mb.travel.bulkBkg.util.TravelInfoBulkBkgUtil;
import com.pcda.mb.travel.journey.model.BooleanResponse;
import com.pcda.mb.travel.journey.model.IntegerResponse;
import com.pcda.mb.travel.journey.model.StringResponse;
import com.pcda.mb.travel.journey.service.TravelInfoService;
import com.pcda.mb.travel.journey.util.JourneyRequestUtils;
import com.pcda.util.AirDestinationType;
import com.pcda.util.AllowedJourneyType;
import com.pcda.util.CabinClass;
import com.pcda.util.ClassType;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.FromToType;
import com.pcda.util.Gender;
import com.pcda.util.JourneyDestinationType;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;
import com.pcda.util.RelationType;
import com.pcda.util.Status;
import com.pcda.util.TravelMode;
import com.pcda.util.YesOrNo;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TravelInfoBulkBkgService {
	
	@Autowired
	private TravelInfoBulkBkgUtil travelInfoUtil;
	
	
	@Autowired
	private RestTemplate template;
	@Autowired
	private JourneyRequestBulkBkgCommonUtil trCommonUtil;
	@Autowired
	private JourneyCountBulkBkgUtil journeyCountBizlogic;
	
	@Autowired
	private UserServices userServices;
	@Autowired
	private MasterServices services;
	
	@Autowired
	private OfficesService officesService;
	
	@Autowired
	private CategoryServices categoryServices;
	
	@Autowired
	private TRRuleService ruleService;
	
	@Autowired
	private StationServices stationServices;
	
	@Autowired
	private AirportServices airportServices;
	
	@Autowired
	private LtcSpecialSectorServices ltcSpecialSectorServices;
	
	@Autowired
	private LevelServices levelServices; 
	
	@Autowired
	private CodeHeadServices headServices;
	
	@Autowired
	private GradePayRankServices gradePayRankServices; 

	public TravellerInfoBulkBkgModel getTravelInfoBulkBkg(HttpServletRequest request) {
		String userAlias = Optional.ofNullable(request.getParameter("personalNo")).orElse("");

		String logStr = "getTravelInfo(" + userAlias + ")";

	

		String travelType = Optional.ofNullable(request.getParameter("travelTypeDD")).orElse("");
		String trRuleID = Optional.ofNullable(request.getParameter("tRRule")).orElse("");
		String reqType = Optional.ofNullable(request.getParameter("reqType")).orElse("");
		String isAttAll = Optional.ofNullable(request.getParameter("isAttAll")).orElse("");
		String unitId = Optional.ofNullable(request.getParameter("groupId")).orElse("");
		String isPartyBooking = Optional.ofNullable(request.getParameter("isPartyBookingParameter")).orElse("");
		String travelMode = "0";
		String serviceType = Optional.ofNullable(request.getParameter("serviceType")).orElse("0");
		String journeyDate = Optional.ofNullable(request.getParameter("journeyDate")).orElse("0");

		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,
				logStr + " :: UnitId = " + unitId + " || travelType=" + travelType + "||travelMode=" + travelMode
						+ "||trRuleId=" + trRuleID + "||reqType=" + reqType + "||isAttAll=" + isAttAll
						+ "||isPartyBooking=" + isPartyBooking + "||serviceType=" + serviceType + "||journeyDate="
						+ journeyDate);

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser portalVisitor = sessionVisitor.getLoginUser();

		TravellerInfoBulkBkgModel infoModel = new TravellerInfoBulkBkgModel();

		if (userAlias.isBlank()) {
			infoModel.setMessage("Enter Traveler Personal No.");
			return infoModel;
		}

		Optional<VisitorModel> travlerOpt = userServices.getCompleteUser(userAlias);
		VisitorModel travler = null;
		if (travlerOpt.isPresent()) {
			travler = travlerOpt.get();
		}

		if (null == travler) {
			infoModel.setMessage("Enter Traveler Personal No.");
			return infoModel;
		}

		TravelRule travelRule = ruleService.getTRRuleDetails(trRuleID);

		// Validating TR Max Journey Date

		int isTrMaxJourney = travelRule.getJrnyBasedTR().ordinal();
		if (isTrMaxJourney == 0) {
			Date journeyEndDate = CommonUtil.formatString(journeyDate, "dd/MM/yyyy");
			Date trJourneyDate = travelRule.getMaxJrnyDate();
			if (journeyEndDate.after(trJourneyDate)) {
				
				infoModel.setMessage("This TR Rule Is Not Appicable For journeyEndDate check TR Rule");
				return infoModel;
			}

		}

		// Validating Traveler Eligible Category
		boolean checkCategory = validateEligibleCategoryID(travelRule, travler);
		if (!checkCategory) {
			infoModel.setMessage(
					"This TR Rule Is Not Appicable For Category " + getCategoryDetails(travler.getCategoryId()));
			return infoModel;
		}

		/* Block For Coast Guard Service */
		boolean isArmedPersonalInsideCoastGuard = false;
		String travlerUnitService = travler.getServiceId();
		String travlerSelfService = travler.getUserServiceId();
		if (travlerUnitService != null && travlerUnitService.equals(PcdaConstant.COAST_GUARD_SEVICE_ID)
				&& travlerSelfService != null && !travlerSelfService.equals(PcdaConstant.COAST_GUARD_SEVICE_ID)) {

			isArmedPersonalInsideCoastGuard = true;
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,
				logStr + "isArmedPersonalInsideCoastGuard-" + isArmedPersonalInsideCoastGuard + "|travlerUnitService-"
						+ travlerUnitService + "|travlerSelfService-" + travlerSelfService);
		/* Block For Coast Guard Service */

		// Validating Traveler Service
		String serviceId = "";
		if (travler.getUserServiceId() != null && !travler.getUserServiceId().trim().equals("")) {
			serviceId = travler.getUserServiceId();
		}else {
			serviceId = travler.getServiceId();
		}
		boolean checkRuleService = validateEligibleService(travelRule, serviceId);
		
		if (!checkRuleService) {
			infoModel.setMessage("This TR Rule Is Not Appicable For Service " + getServiceNameByServiceId(serviceId));
			return infoModel;
		}

		// Validating Traveler Unit Location
		try {
			boolean checkUnitLocation = validateUnitAndLocation(travler,infoModel );
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,
					logStr + "check for location And Unit Match--" + checkUnitLocation);
			if (checkUnitLocation) {
				
				return infoModel;
			}
		} catch (Exception e) {
			
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}

		// Validating no Of Elligble Passanger List
		try {
			if (!validateNoOfEligblePassenger(travler, travelRule)) {
				infoModel.setMessage("No Passenger From Traveler Profile Is Allowed To travel On this TR Rule");
				return infoModel;
			}

		} catch (Exception e) {
			
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}


		String travelTypeName = trCommonUtil.getTravelTypeNameByID(travelRule.getRulePurpose()).toUpperCase();

		boolean isOnSuspension = false;
		if (serviceType.equals("1") && !isArmedPersonalInsideCoastGuard && Optional
				.ofNullable(travler.getTravelerProfile().getOnSuspension()).orElse(YesOrNo.NO).ordinal() == 0) {
			isOnSuspension = true;
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,
				logStr + "||isOnSuspension=" + isOnSuspension);

		if(!travelTypeName.contains("LTC") && !travelTypeName.contains("PT"))
		 {
			 /*-------------------------------------------------------------*/ 
			 if(isOnSuspension)
			 {
				 infoModel.setMessage("Personal has already on suspension so not allowed to travel.");
				 return infoModel;
			 }
			/*-------------------------------------------------------------*/
			 
			boolean checkJouneyCount=journeyCountBizlogic.validateJourneyCount(travler,travelRule,travelTypeName,reqType);
		 
			if (!checkJouneyCount) 
			{
				infoModel.setMessage("Personal has already availed all Journey's allowed for particular TR Rule");
				 return infoModel;
			}
		 }
		 else if(travelTypeName.contains("LTC"))
		 {
			 if(serviceType.equals("1") && !isArmedPersonalInsideCoastGuard)
			 {
				 /*-------------------------------------------------------------*/ 
				 //Case for defaulter
				
				 
				 YesOrNo isAbamdoned= Optional.ofNullable(travler.getTravelerProfile().getAbandoned()).orElse(YesOrNo.YES);
				 if (isAbamdoned.ordinal()==0 && isAbamdoned.getDisplayValue().equalsIgnoreCase("Yes")) 
				 {
					 infoModel.setMessage("Personal has already abandoned by auditor so not allowed to avail LTC.");
					 return infoModel;
				 }
				 
				/*-------------------------------------------------------------*/
				 
				 try
				 {
				
					 	Optional<TRScheme> schemes=travelRule.getSchemes().stream().findFirst();
					 	
					    if (schemes.isPresent()) 
					    {
					    	TRScheme trRuleSchemeDtls=  schemes.get();
					    	Map<String, String> schemeDetailsMap=JourneyRequestUtils.getSchemeDetailsMap(trRuleSchemeDtls);
					    	
					    	Date schemeAppliedDate=trRuleSchemeDtls.getSchemeAppliedDate();
					    
					    	
					    	infoModel.setAllowedRequestCountInBlock(Optional.ofNullable(trRuleSchemeDtls.getMaxCountAllowedPerBlock()).orElse(0));
					    	infoModel.setAllowedRequestCountInSubBlock(Optional.ofNullable(trRuleSchemeDtls.getMaxCountAllowedPerSubblock()).orElse(0));
					    	infoModel.setBlockExtendableYear(Optional.ofNullable(trRuleSchemeDtls.getBlockExtendableYears()).orElse(0));
					    	infoModel.setSubBlockExtendableYear(Optional.ofNullable(trRuleSchemeDtls.getSubblockExtendableYears()).orElse(0));
					    
					    	String currentBlockYear=schemeDetailsMap.get("currentBlockYear");
					    	String nextBlockYear=schemeDetailsMap.get("nextSubBlockYear");
					    	
					    	
						    
					    	infoModel.setCurrentBlockYear(currentBlockYear);
					    	infoModel.setNextBlockYear(nextBlockYear);
					    
					    	List<Integer> jrCountCurrentBlockList	= journeyCountBizlogic.getCivilianUserJourneyCountInBlockYear(travler.getUserId(),travelRule.getRulePurpose(),travelRule.getTrRuleId(),currentBlockYear);
					    	List<Integer> jrCountNextBlockList	= journeyCountBizlogic.getCivilianUserJourneyCountInBlockYear(travler.getUserId(),travelRule.getRulePurpose(),travelRule.getTrRuleId(),nextBlockYear);
							 
							infoModel.setOnwardRequestCountInCurrentBlock(jrCountCurrentBlockList.get(0));
							infoModel.setReturnRequestCountInCurrentBlock(jrCountCurrentBlockList.get(1));
							infoModel.setOnwardRequestCountInNextBlock(jrCountNextBlockList.get(0));
							infoModel.setReturnRequestCountInNextBlock(jrCountNextBlockList.get(1));
						
							
					     
					    	int maxYearSubBlock=trRuleSchemeDtls.getMaxYearsPerSubblock();
					    	
					    	if(maxYearSubBlock==1 && schemeAppliedDate!=null )
					    	{
				    			infoModel.setIsSchemeApplied("Yes");
				    			
						    	String previousSubBlockYear=schemeDetailsMap.get("previousSubBlockYear");
						    	String currentSubBlockYear=schemeDetailsMap.get("currentSubBlockYear");
						    	String nextSubBlockYear=schemeDetailsMap.get("nextSubBlockYear");
						    	String currentYearLTCAvailed=travler.getTravelerProfile().getLtcCurrentYear();
						    	
						    	
						    	
						    	infoModel.setCurrentSubBlockYear(currentSubBlockYear);
						    	infoModel.setNextSubBlockYear(nextSubBlockYear);
						    
						    	if(currentYearLTCAvailed!=null && currentYearLTCAvailed.equals(currentSubBlockYear))
						    	{
						    		
						    		List<Integer> jrCountPrevSubBlockList	= journeyCountBizlogic.getCivilianUserJourneyCountInSubblockYear(travler.getUserId(),travelRule.getRulePurpose(),travelRule.getTrRuleId(),previousSubBlockYear);
						    		List<Integer> jrCountNextSubBlockList	= journeyCountBizlogic.getCivilianUserJourneyCountInSubblockYear(travler.getUserId(),travelRule.getRulePurpose(),travelRule.getTrRuleId(),nextSubBlockYear);
								
									infoModel.setOnwardRequestCountInPreviousSubBlock(jrCountPrevSubBlockList.get(0));
									infoModel.setReturnRequestCountInPreviousSubBlock(jrCountPrevSubBlockList.get(1));
									infoModel.setOnwardRequestCountInCurrentSubBlock(1);
									infoModel.setReturnRequestCountInCurrentSubBlock(1);
									infoModel.setOnwardRequestCountInNextSubBlock(jrCountNextSubBlockList.get(0));
									infoModel.setReturnRequestCountInNextSubBlock(jrCountNextSubBlockList.get(1));
									
								
									
								
						    	}else
						    	{
						    		
						    		List<Integer> jrCountPrevSubBlockList	= journeyCountBizlogic.getCivilianUserJourneyCountInSubblockYear(travler.getUserId(),travelRule.getRulePurpose(),travelRule.getTrRuleId(),previousSubBlockYear);
						    		List<Integer> jrCountCurrentSubBlockList= journeyCountBizlogic.getCivilianUserJourneyCountInSubblockYear(travler.getUserId(),travelRule.getRulePurpose(),travelRule.getTrRuleId(),currentSubBlockYear);
						    		List<Integer> jrCountNextSubBlockList	= journeyCountBizlogic.getCivilianUserJourneyCountInSubblockYear(travler.getUserId(),travelRule.getRulePurpose(),travelRule.getTrRuleId(),nextSubBlockYear);
								
									infoModel.setOnwardRequestCountInPreviousSubBlock(jrCountPrevSubBlockList.get(0));
									infoModel.setReturnRequestCountInPreviousSubBlock(jrCountPrevSubBlockList.get(1));
									infoModel.setOnwardRequestCountInCurrentSubBlock(jrCountCurrentSubBlockList.get(0));
									infoModel.setReturnRequestCountInCurrentSubBlock(jrCountCurrentSubBlockList.get(1));
									infoModel.setOnwardRequestCountInNextSubBlock(jrCountNextSubBlockList.get(0));
									infoModel.setReturnRequestCountInNextSubBlock(jrCountNextSubBlockList.get(1));
									
								
						    	}
						    	
				    			
					    	}else
					    	{
					    			infoModel.setIsSchemeApplied("No");
					    			
					    			String currentSubBlockLTCAvailed=travler.getTravelerProfile().getLtcCurrentSubBlock();
					    			
					    			String previousSubBlockYear=schemeDetailsMap.get("previousSubBlockYear");
					    			String currentSubBlockYear=schemeDetailsMap.get("currentSubBlockYear");
							    	String nextSubBlockYear=schemeDetailsMap.get("nextSubBlockYear");
							    	String bothBlockAreSame=schemeDetailsMap.get("bothBlockAreSame");
							    	
							    	infoModel.setBothBlockAreSame(bothBlockAreSame);
					    			
							    	
							    	if(currentSubBlockLTCAvailed!=null && currentSubBlockLTCAvailed.equals(currentSubBlockYear))
							    	{
							    		List<Integer> jrCountPrevSubBlockList	= journeyCountBizlogic.getCivilianUserJourneyCountInSubblockYear(travler.getUserId(),travelRule.getRulePurpose(),travelRule.getTrRuleId(),previousSubBlockYear);
							    		List<Integer> jrCountNextSubBlockList	= journeyCountBizlogic.getCivilianUserJourneyCountInSubblockYear(travler.getUserId(),travelRule.getRulePurpose(),travelRule.getTrRuleId(),nextSubBlockYear);
									
							    		infoModel.setOnwardRequestCountInPreviousSubBlock(jrCountPrevSubBlockList.get(0));
										infoModel.setReturnRequestCountInPreviousSubBlock(jrCountPrevSubBlockList.get(1));
										infoModel.setOnwardRequestCountInCurrentSubBlock(1);
										infoModel.setReturnRequestCountInCurrentSubBlock(1);
										infoModel.setOnwardRequestCountInNextSubBlock(jrCountNextSubBlockList.get(0));
										infoModel.setReturnRequestCountInNextSubBlock(jrCountNextSubBlockList.get(1));
									
										
									
							    	}else
							    	{
							    		List<Integer> jrCountPrevSubBlockList	= journeyCountBizlogic.getCivilianUserJourneyCountInSubblockYear(travler.getUserId(),travelRule.getRulePurpose(),travelRule.getTrRuleId(),previousSubBlockYear);
							    		List<Integer> jrCountCurrentSubBlockList	= journeyCountBizlogic.getCivilianUserJourneyCountInSubblockYear(travler.getUserId(),travelRule.getRulePurpose(),travelRule.getTrRuleId(),currentSubBlockYear);
							    		List<Integer> jrCountNextSubBlockList	= journeyCountBizlogic.getCivilianUserJourneyCountInSubblockYear(travler.getUserId(),travelRule.getRulePurpose(),travelRule.getTrRuleId(),nextSubBlockYear);
									
										infoModel.setOnwardRequestCountInPreviousSubBlock(jrCountPrevSubBlockList.get(0));
										infoModel.setReturnRequestCountInPreviousSubBlock(jrCountPrevSubBlockList.get(1));
										infoModel.setOnwardRequestCountInCurrentSubBlock(jrCountCurrentSubBlockList.get(0));
										infoModel.setReturnRequestCountInCurrentSubBlock(jrCountCurrentSubBlockList.get(1));
										infoModel.setOnwardRequestCountInNextSubBlock(jrCountNextSubBlockList.get(0));
										infoModel.setReturnRequestCountInNextSubBlock(jrCountNextSubBlockList.get(1));
										
									
										
									
							    	} 
							  		
					    	}
				    	
					    	String spousePanNumber = Optional.ofNullable(travler.getTravelerProfile().getSpousePanNo()).orElse("");
							
							infoModel.setAllowedTravels(Optional.ofNullable(travelRule.getJrnyCountCalenderYear()).orElse(0));
							infoModel.setSpousePanNumber(spousePanNumber);
							infoModel.setIsOnSuspension(Optional.ofNullable(travler.getTravelerProfile().getOnSuspension()).orElse(YesOrNo.YES).ordinal());
							infoModel.setIsOnSuspensionStr(Optional.ofNullable(travler.getTravelerProfile().getOnSuspension()).orElse(YesOrNo.YES).getDisplayValue());
							
							DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class, logStr+"infoModel :: "+infoModel);
					    	
						}
				 }catch(Exception e){
					 
					 DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
				 }
				 
					 
			 }else
			 {
				 List<Integer> jrCountCurrentList	= journeyCountBizlogic.noOfJrnyCountForLTC(travler.getUserId(),travelRule,"current");
				 List<Integer> jrCountNextList	= journeyCountBizlogic.noOfJrnyCountForLTC(travler.getUserId(),travelRule,"next");
				 
				 infoModel.setNoOFRequestsOnward(jrCountCurrentList.get(0));
				 infoModel.setNoOFRequestsReturn(jrCountCurrentList.get(1));
				 infoModel.setAllowedTravels(Optional.ofNullable(travelRule.getJrnyCountCalenderYear()).orElse(0));
				 infoModel.setNoOFRequestsNextOnward(jrCountNextList.get(0));
				 infoModel.setNoOFRequestsNextReturn(jrCountNextList.get(1));
				 
			 }
			
		 } // end of LTC block
		 else if(travelTypeName.contains("PT"))
		 {
			 /*-------------------------------------------------------------*/ 
			 if(isOnSuspension)
			 {
				 infoModel.setMessage("Personal has already on suspension so not allowed to travel.");
				 return infoModel;
			 }
			/*-------------------------------------------------------------*/
			 
			 infoModel.setAllowedTravels(Optional.ofNullable(travelRule.getJrnyCountAllowedTR()).orElse(0));
			 infoModel.setRequestCreated(journeyCountBizlogic.getPtJourneyCountForCurrentYear(travler.getUserId(),travelRule));
		 }

         getTRRuleTravelMode(travelRule,infoModel);
			
		  getFrmToStationByTRRule(travelRule, travler,infoModel);
		  getAirFromToStationByTRRule(travelRule, travler, infoModel);
		  infoModel.setTrQuestion(getQuestionAskedForTRRule(travelRule));
		  infoModel.setRequisiteDtls(getRequisiteAllowedXmlForTRRule(travelRule));
		 
		  boolean checkDateOfRetr=validateDateOfRetirement(travler);
		  
		  if(travelRule.getTrRuleId().equals("TR100081") || travelRule.getTrRuleId().equals("TR100213")){
			  infoModel.setCheckRtrmntAge("true");
			} else if (travelRule.getTrRuleId().equals("TR100204")) {
				
				 infoModel.setCheckRtrmntAge(validateDateOfBooking(travler));
			} else {
		      infoModel.setCheckRtrmntAge(Boolean.toString(checkDateOfRetr));
		  }
		  
		  if(isAttAll.equalsIgnoreCase("true"))
		  {
			  infoModel.setAttendentCount(isAttendedAllowed(travelRule));
			  infoModel.setRetationType(Arrays.stream(RelationType.values()).collect(Collectors.toMap(RelationType::ordinal, RelationType::getDisplayValue)));
			  infoModel.setGender(Arrays.stream(Gender.values()).collect(Collectors.toMap(Gender::ordinal, Gender::getDisplayValue)));
		  }
		
		  if(isPartyBooking.equalsIgnoreCase("true"))
		  {
			 infoModel.setDepHigherClassAllowed(isPartyDependentHigherClassAllowed(travelRule));
			 infoModel.setIsPartyBooking("Yes");
		  }
		  
		  /*  ----------------   Getting and Validation User PAO and UnitOffice Start-------------*/
		  
		  Optional<OfficeModel> portalOfficeModel=officesService.getOfficeByUserId(portalVisitor.getUserId());
		  try
		  {
			  String paoGroupId=travler.getTravelerProfile().getAccountOffice();
			  Optional<OfficeModel> officeModel= officesService.getOfficeByGroupId(paoGroupId);
			  String paoGroupName= "";
			  if(officeModel.isPresent())
			  {
				 paoGroupName=officeModel.get().getName();  
				 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,logStr+"Traveler has Assigned No PAO Group");
			  }
			 
			  String airPaoGroupId=travler.getTravelerProfile().getAirAccountOffice();
			  Optional<OfficeModel> airOfficeModel= officesService.getOfficeByGroupId(airPaoGroupId);
			  String airPaoGroupName="";
			  if(airOfficeModel.isPresent())
			  {
				  airPaoGroupName=airOfficeModel.get().getName();  
				  DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,logStr+"Traveler has Assigned No AIR PAO Group");
			  }
			 
			  String unitGroupId="";
			  String unitGroupName="";
			  Optional<OfficeModel> travlerOfficeModel=officesService.getOfficeByUserId(travler.getUserId());
			  if(travlerOfficeModel.isPresent()) {
				  unitGroupId=travlerOfficeModel.get().getGroupId();
				  unitGroupName=travlerOfficeModel.get().getName();
			  }
			 
			  if (portalOfficeModel.isPresent() && reqType.equalsIgnoreCase("international")){
				  
				  unitGroupId=portalOfficeModel.get().getGroupId();
			  }
			 
			  DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,logStr+"PaoGroupId["+paoGroupId+"]PaoGroupName["+paoGroupName+"]UnitGroupId["+unitGroupId+"]UnitGroupName["+unitGroupName+"]");
			 
			  if (reqType.equalsIgnoreCase("exceptionalBooking")){
				 infoModel.setUnitName(unitGroupName);
			  }
			  
			  infoModel.setMasterTravelerUnitName(unitGroupName);
			  infoModel.setRailPAOId(paoGroupId);
			  infoModel.setAirPAOId(airPaoGroupId);
			  infoModel.setOffice(paoGroupName);
			  infoModel.setAirOffice(airPaoGroupName);
			  infoModel.setOfficeID(unitGroupId);
			  
			  if(portalOfficeModel.isPresent()) {
				  infoModel.setTravellerOfficeId(portalOfficeModel.get().getGroupId());
			  }
			  
		  
		  }catch(Exception e)
		  {
			  DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		  }
		
		/*  ----------------   Getting and Validation User PAO and UnitOffice End -------------*/
		  
		  //	================= Block for civillian service ======================== START ========================
		 
		  boolean isCivilianService= isCivilianService(serviceId);
		  DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,logStr+"USER ALIAS = "+travler.getUserAlias()+"|| SERVICE_ID="+serviceId+" Is Part Of Civilain Service="+isCivilianService);
		  
		  infoModel.setCivilianService(String.valueOf(isCivilianService));
		  
		 // ================= Block for civillian service ======================== END ==========================
		  
		infoModel.setUserID(travler.getUserId());
		
		if (travler.getFirstName()==null || travler.getFirstName().equals("")) {
			
			String[] fullName = travler.getName().split(" ");
			infoModel.setFirstName(fullName[0]);
			
			if (fullName.length > 0) {
				infoModel.setMiddleName(fullName[1]);
			}
			if (fullName.length > 1) {
				infoModel.setLastName(fullName[2]);
			}
		}else{
			infoModel.setFirstName(travler.getFirstName());
			infoModel.setMiddleName(travler.getMiddleName());
			infoModel.setLastName(travler.getLastName());
		}
		
		if(travler.getUserServiceId()!=null && !travler.getUserServiceId().equals(""))
		{
		
			infoModel.setService(getServiceNameByServiceId(travler.getUserServiceId()));
			infoModel.setUnitService(getServiceNameByServiceId(travler.getServiceId()));
			infoModel.setServiceId(travler.getUserServiceId());
			
		}
		else
		{ 
			infoModel.setService(getServiceNameByServiceId(travler.getServiceId()));
			infoModel.setUnitService("");
			infoModel.setServiceId(travler.getServiceId());
			
		}
		
		Map<String,String> codeHeadMap=getCodeHead(travler, travelType, reqType);
		infoModel.setCategory(getCategoryDetails(travler.getCategoryId()));
		infoModel.setCategoryId(travler.getCategoryId());
		infoModel.setCodeHead(codeHeadMap.get("railCodeHead"));
		infoModel.setAirCodeHead(codeHeadMap.get("airCodeHead"));
		infoModel.setLevelId(travler.getLevelId());
		infoModel.setLevelName(getLevelNameByLevelId(travler.getLevelId()));
		
		
		int permissionType = getCPCPermission(serviceId);
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,logStr+"Permission Type:"+permissionType+" personalNumber:"+userAlias);
		
		if(permissionType == 0){   //Grade Pay
			   getRank(travler,travelRule,infoModel);
			if(travelTypeName.contains("LTC"))
		    {
				getRankAirLTC(travler,travelRule,infoModel);
		    }else
		    {
		    	if(travelTypeName.contains("TD") && serviceType.equals("0")){
		    		getRankAirTD(travler,travelRule,infoModel);
		    	}else{
		    		getRankAir(travler,travelRule,infoModel);	
		    	}
		    }
		}else if(permissionType == 1){  // CPC
			 getCPCRailEntitlement(travler, travelRule, serviceId, travelType,infoModel);
			if(travelTypeName.contains("LTC"))
		    {
				getCPCAirLTCEntitlement(travler, travelRule, serviceId, travelType,infoModel);
		    }else
		    {
		    	if(travelTypeName.contains("TD") && serviceType.equals("0")){
		    		getCPCAirTDEntitlement(travler, travelRule, serviceId, travelType,infoModel);
		    	}else{
		    		getCPCAirEntitlement(travler, travelRule, serviceId, travelType,infoModel);	
		    	}
		    }
		}
		
		if(travelTypeName.contains("TD") || travelTypeName.contains("PT")){
			String travellerServiceId=travler.getUserServiceId();
			boolean flag =getTADAPermission(travler.getCategoryId(),portalOfficeModel.get().getGroupId(),travelTypeName,travellerServiceId);
			boolean trRuleTADAFlag=getTRRuleTADAPermission(travelRule, travelTypeName);
			boolean trRuleTADAHotelFlag=getTADAHotelAllow(travelRule, travelTypeName);
			boolean trRuleTADAConveyanceFlag=getTADAConveyanceAllow(travelRule, travelTypeName);
			boolean trRuleTADAFoodFlag=getTADAFoodAllow(travelRule, travelTypeName);
			boolean accountUpdateFlag=isAccountDtlsUpdated(travler);
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,logStr+"TADA_PERMISSION:"+flag+"| trRuleTADAFlag:"+trRuleTADAFlag+"| trRuleTADAHotelFlag:"+trRuleTADAHotelFlag+
		            "| accountUpdateFlag"+accountUpdateFlag);
			
			infoModel.setTadaPermission(flag);
			infoModel.setRuleTadaPermission(trRuleTADAFlag);
			infoModel.setRuleTadaHotelPermission(trRuleTADAHotelFlag);
			infoModel.setRuleTadaConveyancePermission(trRuleTADAConveyanceFlag);
			infoModel.setRuleTadaFoodPermission(trRuleTADAFoodFlag);
			infoModel.setRuleDaAdvanceDay(travelRule.getDaAdvanceDay().intValue() > 0?travelRule.getDaAdvanceDay().intValue():0);
			infoModel.setAccountDtlsUpdate(accountUpdateFlag);
			infoModel.setTravlerBankAccountNumber(travler.getBankAccountNumber());
			infoModel.setTravlerIfscCode(travler.getIfscCode());
			
		}else{
			infoModel.setTadaPermission(false);
			infoModel.setRuleTadaPermission(false);
			infoModel.setRuleTadaHotelPermission(false);
			infoModel.setRuleTadaConveyancePermission(false);
			infoModel.setRuleTadaFoodPermission(false);
			infoModel.setRuleDaAdvanceDay(0);
			infoModel.setAccountDtlsUpdate(false);
			infoModel.setTravlerBankAccountNumber("");
			infoModel.setTravlerIfscCode("");
			
		}
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,logStr+" ::INFO MODEL ::::: "+ infoModel);
		infoModel.setDateOfBirth(CommonUtil.formatDate(travler.getDateOfBirth(), "dd-MM-yyyy"));
		infoModel.setDateOfRetire(CommonUtil.formatDate(travler.getDateOfRetirement(), "dd-MM-yyyy"));
	   
	    String travelTypeId=travelRule.getRulePurpose();
	    DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,logStr+"DateOfBirth="+travler.getDateOfBirth()+"||TravelTypeId="+travelTypeId+"||travelTypeName="+travelTypeName);
	   
	    if(travelTypeName.contains("LTC"))
	    {
	    	if(serviceType.equals("1") && !isArmedPersonalInsideCoastGuard){
	    		travelInfoUtil.getCivilianTravellorFamilyDetailsForLtc(travler,getEligibleMemberList(travelRule),travelTypeId,travelRule,infoModel);
	    	}else{
	    		travelInfoUtil.getTravellorFamilyDetailsForLtc(travler,getEligibleMemberList(travelRule),travelTypeId,travelRule,infoModel);
	    	}
	    	
	    }
	    
	    else if(travelTypeName.equalsIgnoreCase("FORMD") || travelTypeName.equalsIgnoreCase("FORMG"))
	    {
	    	List<Map<String, String>> formMaps= journeyCountBizlogic.getFormDAndGYears(travler,travelRule,travelTypeName,reqType);
	    	List<String> yearList=new ArrayList<>();
	    	
	    	infoModel.setFormDAndGYear(formMaps);
	    	formMaps.stream().forEach(e->yearList.add(e.get("Year")));
	    	 
	    	travelInfoUtil.getTravellorFamilyDetailsForFormDAndGYearWise(travler,getEligibleMemberList(travelRule),travelTypeId,travelRule,yearList, infoModel);
	    }
	    else
	    {
	    	travelInfoUtil.getTravellorFamilyDetails(travler,getEligibleMemberList(travelRule),travelTypeId,travelRule, infoModel);
	    }
		
	    DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,logStr+" ::INFO MODEL ::::: "+ infoModel);
		return infoModel;
		
	}
	
	
	private void getAirFromToStationByTRRule(TravelRule travelRule, VisitorModel visitor,
			TravellerInfoBulkBkgModel infoModel) {

		  
	  	List<AirStationFromBulkBkgModel> fromStationList=new ArrayList<>();
	  	List<AirStationToBulkBkgModel> toStationList=new ArrayList<>();
	  	try 
	  	{
	  		Set<TRJourney> trJounry=travelRule.getJourneys();
	  		
	  		if(!trJounry.isEmpty())
	  		{	
	  			String groupId=getGroupIdForUserId(visitor.getUserId());
	  			trJounry.forEach(obj->{
	  			
	  				int jrnyType= Optional.ofNullable(obj.getJrnyType()).orElse(AllowedJourneyType.ONWARD).ordinal();
	  				int jrnyStn= Optional.ofNullable(obj.getJrnyStation()).orElse(JourneyDestinationType.DUTY_STATION).ordinal();
	  				int frmToTye= Optional.ofNullable(obj.getFromToType()).orElse(FromToType.FROM).ordinal();
	  				String jrnyStnType= Optional.ofNullable(obj.getJrnyStation()).orElse(JourneyDestinationType.DUTY_STATION).getDisplayValue();
	  			
	  				StringBuilder frmStn=new StringBuilder("");
	  				StringBuilder toStn=new StringBuilder("");
	  				
	  				if((travelRule.getTrRuleId().equals("TR100233")||travelRule.getTrRuleId().equals("TR100234") ||travelRule.getTrRuleId().equals("TR100118")) && jrnyStn!=10){
	  						return;
	  				}
	  				
	  				if(frmToTye==0)
	  				{
	  					AirStationFromBulkBkgModel stationFromModel=new AirStationFromBulkBkgModel();
	  					stationFromModel.setJrnyType(jrnyType);
	  					
	  					if(jrnyStn==0)
	  					{
	  					  
	  					  frmStn.append(getFullAirportNameByCode(visitor.getTravelerProfile().getDutyStationNA()));
	  					  String existingStationCode=Optional.ofNullable(visitor.getTravelerProfile().getDutyStationNA()).orElse("");
	  					 
	  					  try
	  					  {
	  						
	  						List<String> dutyStationList=getAllDutyStation(groupId,existingStationCode,1);
	  						
	  						dutyStationList.forEach(e->{
	  							if(frmStn.toString().isBlank()) {
	  								frmStn.append(getFullStationNameByStnCode(e));
	  							}else {
	  								frmStn.append("::"+getFullStationNameByStnCode(e));
	  							} 
	  						});
	  						
	  						
	  						DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getAirFromToStationByTRRule]finally the value of frmStn for NRS Duty Station is "+frmStn);
	  					  
	  					  }catch(Exception e){
	  						DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	  					  }
	  					}
	  					else if(jrnyStn==1)
	  						frmStn.append(getFullAirportNameByCode(visitor.getTravelerProfile().getOdsNA()));
	  					else if(jrnyStn==3)
	  						frmStn.append(getFullAirportNameByCode(visitor.getTravelerProfile().getHomeTownNA()));
	  					else if(jrnyStn==4)
	  						frmStn.append(getFullAirportNameByCode(visitor.getTravelerProfile().getSprNA()));
	  					else if(jrnyStn==5)
	  						frmStn.append(getFullAirportNameByCode(visitor.getTravelerProfile().getDutyStationNA()));
	  					else if(jrnyStn==6)
	  						frmStn.append(getFullAirportNameByCode(visitor.getTravelerProfile().getOdsSPRNa()));
	  					else if(jrnyStn==7)
	  						frmStn.append(getFullAirportNameByCode(visitor.getTravelerProfile().getSprNA()));
	  					
	  					else if(jrnyStn==8)
	  					{
	  						/*LTC Special Sector 8=North East,11-Jammu & Kashmir,12-Andaman & Nicobar */
	  						Set<String> ltcSpclSecAirportList=getSpecialLtcSectorAirports("North East",1);
	  						ltcSpclSecAirportList.forEach(e->{
	  							if(frmStn.toString().length()==0)
	  								frmStn.append(e);
	  							else	
	  								frmStn.append("::"+e);
	  						});
	  						
	  					}else if(jrnyStn==11)
	  					{
	  						/*LTC Special Sector 8=North East,11-Jammu & Kashmir,12-Andaman & Nicobar */
	  						Set<String> ltcSpclSecAirportList=getSpecialLtcSectorAirports("Jammu & Kashmir",1);
	  						ltcSpclSecAirportList.forEach(e->{
	  							if(frmStn.toString().length()==0)
	  								frmStn.append(e);
	  							else	
	  								frmStn.append("::"+e);
	  						});
	  						
	  					}else if(jrnyStn==12)
	  					{
	  						/*LTC Special Sector 8=North East,11-Jammu & Kashmir,12-Andaman & Nicobar */
	  						Set<String> ltcSpclSecAirportList=getSpecialLtcSectorAirports("Andaman & Nicobar",1);
	  						ltcSpclSecAirportList.forEach(e->{
	  							if(frmStn.toString().length()==0)
	  								frmStn.append(e);
	  							else	
	  								frmStn.append("::"+e);
	  						});
	  						
	  						
	  					}else if(jrnyStn==10)
	  					{
	  						if(travelRule.getTrRuleId().equals("TR100233")||travelRule.getTrRuleId().equals("TR100234") ||travelRule.getTrRuleId().equals("TR100118")){
	  							List<String> fixedStations=getFromStationListForTR100208(travelRule,jrnyType,visitor.getTravelerProfile().getDutyStationNA());
	  							fixedStations.forEach(e->{
		  							if(frmStn.toString().length()==0)
		  								frmStn.append(e);
		  							else	
		  								frmStn.append("::"+e);
		  						});
	  							
	  							
	  						}else{
	  							if(travelRule.getFixedStations()!=null && !travelRule.getFixedStations().isEmpty() ){
	  							travelRule.getFixedStations().stream().forEach(el->{
	  								int stationType=Optional.ofNullable(el.getStationType()).orElse(0); 
		  							int stationStatus=Optional.ofNullable(el.getStatus()).orElse(Status.OFF_LINE).ordinal();
		  							
		  							if(jrnyType==0 && stationType==0 && stationStatus==1)
		  							{
	  	  								 if(frmStn.toString().length()==0) {
	  	  									frmStn.append(el.getStationName()+"("+el.getStationCode()+")");
	  	  								 }else {	
	  	  									frmStn.append("::"+el.getStationName()+"("+el.getStationCode()+")");
	  	  								 }
		  							}
		  							if(jrnyType==1 && stationType==1 && stationStatus==1)
		  							{
		  								 if(frmStn.toString().length()==0) {
		  									frmStn.append(el.getStationName()+"("+el.getStationCode()+")");
	  	  								 }else {	
	  	  									frmStn.append("::"+el.getStationName()+"("+el.getStationCode()+")");
	  	  								 }
	  	  							 
		  							}
		  							
	  							});
	  							
	  							}
	  							
	  					  }
	  					}else if(jrnyStn==13){
	  						String airAccountOffice = visitor.getTravelerProfile().getAirAccountOffice();
	  						String railAccountOffice = visitor.getTravelerProfile().getAccountOffice();
	  						Map<String,String> recordOfficeInfo=null;
	  						DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getFrmToStationByTRRule] ("+visitor.getUserId()+")airAccountOffice:"+
	  								                                airAccountOffice+" railAccountOffice:"+railAccountOffice);
	  						if(null!=airAccountOffice && null!=railAccountOffice){
	  							if(airAccountOffice.trim().equals(railAccountOffice.trim())){
	  								recordOfficeInfo=getRecordOfficeInfoForSamePAO(railAccountOffice.trim());
	  							}else if(!airAccountOffice.trim().equals(railAccountOffice.trim())){
	  								recordOfficeInfo=getRecordOfficeInfoForDiffPAO(railAccountOffice, airAccountOffice);
	  							}
	  						}
	  						
	  						 String recordOfficeNAP="";
	  						  if(null!=recordOfficeInfo && recordOfficeInfo.containsKey("RECORD_OFFICE_NAP")){
	  							  recordOfficeNAP=recordOfficeInfo.get("RECORD_OFFICE_NAP");
	  						  }
	  						  
	  						frmStn.append(getFullAirportNameByCode(recordOfficeNAP));
	  					}else if(jrnyStn==14){
	  						frmStn.append(getAirHostelLocation(visitor));
	  					}	
	  					else
	  						frmStn.append("AnyStation");
	  					
	  					
	  					stationFromModel.setAirFromStation(frmStn.toString());
	  					stationFromModel.setAirFromStationType(jrnyStnType);
	  					stationFromModel.setAirFromStationTypeInt(jrnyStn);
	  					
	  					fromStationList.add(stationFromModel);
	  					
	  				}
	  				
	  				if(frmToTye==1)
	  				{
	  					AirStationToBulkBkgModel stationToModel=new AirStationToBulkBkgModel();
	  					stationToModel.setJrnyType(jrnyType);
	  					
	  					
	  					if(jrnyStn==0)
	  					{
	  						
	  						toStn.append(getFullAirportNameByCode(visitor.getTravelerProfile().getDutyStationNA()));
	  						String existingStationCode=Optional.ofNullable(visitor.getTravelerProfile().getDutyStationNA()).orElse("");
	  						
	  						try
	  						{
	  						
	  							List<String> dutyStationList=getAllDutyStation(groupId,existingStationCode,1);
	  							
	  							dutyStationList.forEach(e->{
	  								if(toStn.toString().length()==0)
	  									toStn.append(getFullAirportNameByCode(e));
	  	  							else	
	  	  								toStn.append("::"+getFullAirportNameByCode(e));
	  							});
	  							
	  							
	  							DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getAirFromToStationByTRRule]finally the value of toStn for NRS Duty Station is "+toStn);
	  						}
	  						catch(Exception e)
	  						{
	  							DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	  						}
	  						
	  					}
	  					else if(jrnyStn==1)
	  						toStn.append(getFullAirportNameByCode(visitor.getTravelerProfile().getOdsNA()));
	  					else if(jrnyStn==3)
	  						toStn.append(getFullAirportNameByCode(visitor.getTravelerProfile().getHomeTownNA()));
	  					else if(jrnyStn==4)
	  						toStn.append(getFullAirportNameByCode(visitor.getTravelerProfile().getSprNA()));
	  					else if(jrnyStn==5)
	  						toStn.append(getFullAirportNameByCode(visitor.getTravelerProfile().getDutyStationNA()));
	  					else if(jrnyStn==6)
	  						toStn.append(getFullAirportNameByCode(visitor.getTravelerProfile().getOdsSPRNa()));
	  					else if(jrnyStn==7)
	  						toStn.append(getFullAirportNameByCode(visitor.getTravelerProfile().getSprNA()));
	  					else if(jrnyStn==8)
	  					{
	  						/*LTC Special Sector 8=North East,11-Jammu & Kashmir,12-Andaman & Nicobar */
	  						Set<String> ltcSpclSecAirportList=getSpecialLtcSectorAirports("North East",1);
	  						ltcSpclSecAirportList.forEach(stn->{
	  							if(toStn.toString().length()==0)
	  								toStn.append(stn);
	  							else	
	  								toStn.append("::"+stn);
	  						});
	  						
	  					}else if(jrnyStn==11)
	  					{
	  						/*LTC Special Sector 8=North East,11-Jammu & Kashmir,12-Andaman & Nicobar */
	  						Set<String> ltcSpclSecAirportList=getSpecialLtcSectorAirports("Jammu & Kashmir",1);
	  						ltcSpclSecAirportList.forEach(stn->{
	  							if(toStn.toString().length()==0)
	  								toStn.append(stn);
	  							else	
	  								toStn.append("::"+stn);
	  						});
	  						
	  					}else if(jrnyStn==12)
	  					{
	  						/*LTC Special Sector 8=North East,11-Jammu & Kashmir,12-Andaman & Nicobar */
	  						Set<String> ltcSpclSecAirportList=getSpecialLtcSectorAirports("Andaman & Nicobar",1);
	  						ltcSpclSecAirportList.forEach(stn->{
	  							if(toStn.toString().length()==0)
	  								toStn.append(stn);
	  							else	
	  								toStn.append("::"+stn);
	  						});
	  						
	  					}else if(jrnyStn==10)
	  					{
	  						if(travelRule.getTrRuleId().equals("TR100233")||travelRule.getTrRuleId().equals("TR100234")||travelRule.getTrRuleId().equals("TR100118")){
	  							List<String> fixedStations=getToStationListForTR100208(travelRule,jrnyType,visitor.getTravelerProfile().getDutyStationNA());
	  							fixedStations.forEach(stn->{
		  							if(toStn.toString().length()==0)
		  								toStn.append(stn);
		  							else	
		  								toStn.append("::"+stn);
		  						});
	  							
	  							
	  						}else{
	  							
	  							travelRule.getFixedStations().stream().forEach(el->{
	  								int stationType=Optional.ofNullable(el.getStationType()).orElse(0); 
		  							int stationStatus=Optional.ofNullable(el.getStatus()).orElse(Status.OFF_LINE).ordinal();
		  							
		  							if(jrnyType==0 && stationType==1 && stationStatus==1)
		  							{
	  	  								 if(toStn.toString().length()==0) {
	  	  									toStn.append(el.getStationName()+"("+el.getStationCode()+")");
	  	  								 }else {	
	  	  	  								 toStn.append("::"+el.getStationName()+"("+el.getStationCode()+")");
	  	  								 }
		  							}
		  							if(jrnyType==1 && stationType==0 && stationStatus==1)
		  							{
		  								 if(toStn.toString().length()==0) {
	  	  									toStn.append(el.getStationName()+"("+el.getStationCode()+")");
	  	  								 }else {	
	  	  	  								 toStn.append("::"+el.getStationName()+"("+el.getStationCode()+")");
	  	  								 }
	  	  							 
		  							}
		  							
	  							});
								
	  						}
	  					}else if(jrnyStn==13){
	  						String airAccountOffice = visitor.getTravelerProfile().getAirAccountOffice();
	  						String railAccountOffice = visitor.getTravelerProfile().getAccountOffice();
	  						Map<String,String> recordOfficeInfo=null;
	  						DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getFrmToStationByTRRule] ("+visitor.getUserId()+")airAccountOffice:"+
	  								                                airAccountOffice+" railAccountOffice:"+railAccountOffice);
	  						if(null!=airAccountOffice && null!=railAccountOffice){
	  							if(airAccountOffice.trim().equals(railAccountOffice.trim())){
	  								recordOfficeInfo=getRecordOfficeInfoForSamePAO(railAccountOffice.trim());
	  							}else if(!airAccountOffice.trim().equals(railAccountOffice.trim())){
	  								recordOfficeInfo=getRecordOfficeInfoForDiffPAO(railAccountOffice, airAccountOffice);
	  							}
	  						}
	  						
	  						 String recordOfficeNAP="";
	  						  if(null!=recordOfficeInfo && recordOfficeInfo.containsKey("RECORD_OFFICE_NAP")){
	  							  recordOfficeNAP=recordOfficeInfo.get("RECORD_OFFICE_NAP");
	  						  }
	  						  
	  						toStn.append(getFullAirportNameByCode(recordOfficeNAP));
	  					}else if(jrnyStn==14){
	  						toStn.append(getAirHostelLocation(visitor));
	  					}else{
	  						toStn.append("AnyStation");
	  					}
	  					
	  					stationToModel.setAirToStation(toStn.toString());
	  					stationToModel.setAirToStationType(jrnyStnType);
	  					stationToModel.setAirToStationTypeInt(jrnyStn);
	  					
	  					toStationList.add(stationToModel);
	  					
	  				   	}
	  				});
	  			}
	  		
	  	} catch (Exception e) {
	  		DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	  	}
	  	
	  	
	  	DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getAirFromToStationByTRRule]getAirFromToStationByTRRule fromStationList:"+fromStationList+" | toStationList:"+toStationList);
	  	
		infoModel.setAirStationListFrom(fromStationList);
	  	infoModel.setAirStationListTo(toStationList);
	  	
	  
		
	}


	private void getFrmToStationByTRRule(TravelRule travelRule, VisitorModel visitor,
			TravellerInfoBulkBkgModel infoModel) {

	  	
	  	List<StationListFromBulkBkgModel> fromStationList=new ArrayList<>();
	  	List<StationListToBulkBkgModel> toStationList=new ArrayList<>();
	  	try 
	  	{
	  		Set<TRJourney> journeys=travelRule.getJourneys();
	  		AirDestinationType[] airEnumCode= AirDestinationType.values();
	  		
	  		if(null!=journeys && !journeys.isEmpty())
	  		{
	  			journeys.forEach(obj->{
	  				
	  				int jrnyType= Optional.ofNullable(obj.getJrnyType()).orElse(AllowedJourneyType.ONWARD).ordinal();
	  				int jrnyStn= Optional.ofNullable(obj.getJrnyStation()).orElse(JourneyDestinationType.DUTY_STATION).ordinal();
	  				int frmToTye= Optional.ofNullable(obj.getFromToType()).orElse(FromToType.FROM).ordinal();
	  				String jrnyStnType= Optional.ofNullable(obj.getJrnyStation()).orElse(JourneyDestinationType.DUTY_STATION).getDisplayValue();
	  					
	  				StringBuilder frmStn=new StringBuilder("");
	  				StringBuilder toStn=new StringBuilder("");
	  				
	  				AtomicBoolean isTypeForAir= new AtomicBoolean(false);
	  				
	  				if((airEnumCode!=null && airEnumCode.length>0 )&& !(travelRule.getTrRuleId().equals("TR100233") || travelRule.getTrRuleId().equals("TR100234") ||travelRule.getTrRuleId().equals("TR100118"))){
	  					
	  					Arrays.stream(airEnumCode).forEach(e->{
	  						if(jrnyStn==e.getDisplayValue())isTypeForAir.set(true);
	  					}); 
	  					
	  				}
	  				
	  				if(frmToTye==0 && !isTypeForAir.get())
	  				{
	  					if(travelRule.getTrRuleId().equals("TR100233")||travelRule.getTrRuleId().equals("TR100234") ||travelRule.getTrRuleId().equals("TR100118")){
	  						if((jrnyStn==10 && jrnyType==0)||(jrnyStn!=10 && jrnyType==1)){/*No Action*/}else{return;}
		  				}
	  					
	  					StationListFromBulkBkgModel fromModel=new StationListFromBulkBkgModel();
	  					fromModel.setJrnyType(jrnyType);
	  					
	  					if(jrnyStn==0)
	  					{
	  					  
	  					  frmStn.append(getFullStationNameByStnCode(visitor.getTravelerProfile().getNrsDutyStation()));
	  					  String existingStationCode=visitor.getTravelerProfile().getNrsDutyStation();
	  					  if(existingStationCode==null)existingStationCode="";
	  					  try
	  					  {
	  						String groupId=getGroupIdForUserId(visitor.getUserId());	
	  						List<String> dutyStationList=getAllDutyStation(groupId,existingStationCode,0);
	  						
	  						dutyStationList.forEach(e->{frmStn.append("::"+getFullStationNameByStnCode(e));});
	  						
	  						DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getFrmToStationByTRRule]finally the value of frmStn for NRS Duty Station is "+frmStn);
	  					  
	  					  }catch(Exception e)
	  					  {
	  						DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	  					  }
	  					}
	  					else if(jrnyStn==1)
	  						frmStn.append(getFullStationNameByStnCode(visitor.getTravelerProfile().getOldNRSDutyStation()));
	  					else if(jrnyStn==3)
	  						frmStn.append(getFullStationNameByStnCode(visitor.getTravelerProfile().getNrsHomeTown()));
	  					else if(jrnyStn==4)
	  						frmStn.append(getFullStationNameByStnCode(visitor.getTravelerProfile().getSprNRS()));
	  					else if(jrnyStn==5)
	  						frmStn.append(getFullStationNameByStnCode(visitor.getTravelerProfile().getNrsDutyStation()));
	  					else if(jrnyStn==7)
	  						frmStn.append(getFullStationNameByStnCode(visitor.getTravelerProfile().getSprNRS()));
	  					else if(jrnyStn==6)
	  						frmStn.append(getFullStationNameByStnCode(visitor.getTravelerProfile().getOdsSPRNrs()));
	  					else if(jrnyStn==13){
	  						String airAccountOffice = visitor.getTravelerProfile().getAirAccountOffice();
	  						String railAccountOffice = visitor.getTravelerProfile().getAccountOffice();
	  						Map<String,String> recordOfficeInfo=null;
	  						DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getFrmToStationByTRRule] ("+visitor.getUserId()+") airAccountOffice:"+
	  								                                airAccountOffice+" railAccountOffice:"+railAccountOffice);
	  						if(null!=airAccountOffice && null!=railAccountOffice){
	  							if(airAccountOffice.trim().equals(railAccountOffice.trim())){
	  								recordOfficeInfo=getRecordOfficeInfoForSamePAO(railAccountOffice.trim());
	  							}else if(!airAccountOffice.trim().equals(railAccountOffice.trim())){
	  								recordOfficeInfo=getRecordOfficeInfoForDiffPAO(railAccountOffice, airAccountOffice);
	  							}
	  						}
	  						String recordOfficeNRS="";
		  					  if(null!=recordOfficeInfo && recordOfficeInfo.containsKey("RECORD_OFFICE_NRS")){
		  						  recordOfficeNRS=recordOfficeInfo.get("RECORD_OFFICE_NRS");
		  					  }
		  					  
		  					frmStn.append(getFullStationNameByStnCode(recordOfficeNRS));
	  					}
	  					else if(jrnyStn==14){
	  						frmStn.append(getRailHostelLocation(visitor));
	  					}
	  					else if(jrnyStn==10 &&(travelRule.getTrRuleId().equals("TR100233")||travelRule.getTrRuleId().equals("TR100234") ||travelRule.getTrRuleId().equals("TR100118"))){
	  						if(travelRule.getTrRuleId().equals("TR100118")){
	  							String[] fix ={"CDG","NDLS","JAT","SINA"};
	  							
	  							Arrays.stream(fix).forEach(e->{
	  								if(frmStn.length()==0){
	  									frmStn.append(getFullStationNameByStnCode(e));
	  								}else{
	  									frmStn.append("::"+getFullStationNameByStnCode(e));
	  								}
	  								
	  							});
	  							
	  						}else{
	  							frmStn.append(getFullStationNameByStnCode("HWH"));
	  					}
	  					}
	  					else{
	  						frmStn.append("AnyStation");
	  					}
	  					
	  					fromModel.setFromStation(frmStn.toString());
	  					fromModel.setFromStationType(jrnyStnType);
	  					
	  					fromStationList.add(fromModel);
	  					
	  				}
	  				
	  				if(frmToTye==1 && !isTypeForAir.get())
	  				{
	  					if(travelRule.getTrRuleId().equals("TR100233")||travelRule.getTrRuleId().equals("TR100234")||travelRule.getTrRuleId().equals("TR100118")){
	  						if((jrnyStn==10 && jrnyType==1)|| (jrnyStn!=10 && jrnyType==0)){/*No Action*/}else{return;}
		  				}
	  					
	  					StationListToBulkBkgModel toModel=new StationListToBulkBkgModel();
	  					toModel.setJrnyType(jrnyType);
	  					
	  					if(jrnyStn==0)
	  					{
	  						toStn.append(getFullStationNameByStnCode(visitor.getTravelerProfile().getNrsDutyStation()));
	  						String existingStationCode=visitor.getTravelerProfile().getNrsDutyStation();
	  						if(existingStationCode==null)existingStationCode="";
	  						try
	  						{
	  							String groupId=getGroupIdForUserId(visitor.getUserId());	
	  							List<String> dutyStationList=getAllDutyStation(groupId,existingStationCode,0);
	  							
	  							dutyStationList.forEach(e->toStn.append("::"+getFullStationNameByStnCode(e)));
	  							
	  							DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getFrmToStationByTRRule]finally the value of toStn for NRS Duty Station is "+toStn);
	  						}
	  						catch(Exception e)
	  						{
	  							DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	  						}
	  						
	  					}
	  					else if(jrnyStn==1)
	  						toStn.append(getFullStationNameByStnCode(visitor.getTravelerProfile().getOldNRSDutyStation()));
	  					else if(jrnyStn==3)
	  						toStn.append(getFullStationNameByStnCode(visitor.getTravelerProfile().getNrsHomeTown()));
	  					else if(jrnyStn==4)
	  						toStn.append(getFullStationNameByStnCode(visitor.getTravelerProfile().getSprNRS()));
	  					else if(jrnyStn==5)
	  						toStn.append(getFullStationNameByStnCode(visitor.getTravelerProfile().getNrsDutyStation()));
	  					else if(jrnyStn==6)
	  						toStn.append(getFullStationNameByStnCode(visitor.getTravelerProfile().getOdsSPRNrs()));
	  					else if(jrnyStn==7)
	  						toStn.append(getFullStationNameByStnCode(visitor.getTravelerProfile().getSprNRS()));
	  					
	  					else if(jrnyStn==13){
	  						String airAccountOffice = visitor.getTravelerProfile().getAirAccountOffice();
	  						String railAccountOffice = visitor.getTravelerProfile().getAccountOffice();
	  						Map<String,String> recordOfficeInfo=null;
	  						DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getFrmToStationByTRRule] ("+visitor.getUserId()+")airAccountOffice:"+
	  								                                airAccountOffice+" railAccountOffice:"+railAccountOffice);
	  						if(null!=airAccountOffice && null!=railAccountOffice){
	  							if(airAccountOffice.trim().equals(railAccountOffice.trim())){
	  								recordOfficeInfo=getRecordOfficeInfoForSamePAO(railAccountOffice.trim());
	  							}else if(!airAccountOffice.trim().equals(railAccountOffice.trim())){
	  								recordOfficeInfo=getRecordOfficeInfoForDiffPAO(railAccountOffice, airAccountOffice);
	  							}
	  						}
	  						String recordOfficeNRS="";
		  					  if(null!=recordOfficeInfo && recordOfficeInfo.containsKey("RECORD_OFFICE_NRS")){
		  						  recordOfficeNRS=recordOfficeInfo.get("RECORD_OFFICE_NRS");
		  					  }
		  					toStn.append(getFullStationNameByStnCode(recordOfficeNRS));
	  					}else if(jrnyStn==14){
	  						toStn.append(getRailHostelLocation(visitor));
	  					}else if(jrnyStn==10 && (travelRule.getTrRuleId().equals("TR100233")||travelRule.getTrRuleId().equals("TR100234")|| travelRule.getTrRuleId().equals("TR100118"))){
	  						if(travelRule.getTrRuleId().equals("TR100118")){
	  							String[] fix ={"CDG","NDLS","JAT","SINA"};
	  						  							
	  							
	  							Arrays.stream(fix).forEach(e->{
	  								if(toStn.length()==0){
	  									toStn.append(getFullStationNameByStnCode(e));
	  								}else{
	  									toStn.append("::"+getFullStationNameByStnCode(e));
	  								}
	  								
	  							});
	  							
	  						}else{
	  							toStn.append(getFullStationNameByStnCode("HWH"));
	  					}
	  					}
	  					else{
	  						toStn.append("AnyStation");
	  					}
	  					
	  					toModel.setToStation(toStn.toString());
	  					toModel.setToStationType(jrnyStnType);
	  					
	  					toStationList.add(toModel);
	  				 }
	  			   });
	  		}
	  		
	  	} catch (Exception e) {
	  		DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	  	}
	  	
	  	DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getFrmToStationByTRRule]getFrmToStationByTRRule fromStationList:"+fromStationList+" | toStationList:"+toStationList);
	  	
	  	infoModel.setFromStationList(fromStationList);
	  	infoModel.setToStationList(toStationList);
	  	
	  
		
	}
	private boolean validateEligibleCategoryID(TravelRule travelRule, VisitorModel visitor) {
		try {
			return travelRule.getEligibilityCategories().stream()
					.anyMatch(e -> e.getCategoryId().equals(visitor.getCategoryId()));

		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}

		return false;
	}
	
	
	private String getCategoryDetails(String catId) {
		String catDetails = "";

		try {
			Optional<Category> category = categoryServices.getCategoryByCatId(catId);

			if (category.isPresent()) {
				catDetails = category.get().getCategoryName();
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		return catDetails;
	}
	
	private boolean validateEligibleService(TravelRule travelRule, String serviceId) {
		try {
			return travelRule.getRuleServices().stream().anyMatch(e -> e.getServiceId().equals(serviceId));

		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		return false;
	}

	private String getServiceNameByServiceId(String serviceId) {
		String serName = "";
		try {
			Optional<DODServices> service = services.getServiceByServiceId(serviceId);

			if (service.isPresent()) {
				serName = service.get().getServiceName();
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		return serName;
	}
	
	
	private boolean validateUnitAndLocation(VisitorModel travler, TravellerInfoBulkBkgModel infoModel) {

		String groupID = null;
		try {
			Optional<OfficeModel> groupOptional = officesService.getOfficeByUserId(travler.getUserId());
			if (groupOptional.isPresent()) {
				groupID = groupOptional.get().getGroupId();
			}
		} catch (Exception e) {
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,
					"error in getting visitor group-->" + e.getMessage());
		}
		
		if (groupID == null || groupID.isEmpty()) {
			infoModel.setMessage("Traveller does not Belongs To Any Office");
			return true;
		}

		
		return false;

	}
	
	private boolean validateNoOfEligblePassenger(VisitorModel travler, TravelRule travelRule) {
		if(!(Arrays.asList("TR100275","TR100276").contains(travelRule.getTrRuleId()))){ 
		List<Integer> memberList = getEligibleMemberList(travelRule);
		long count =0;
		if(null!=travler.getFamilyDetails()) {
			count = travler.getFamilyDetails().stream().filter(e -> memberList.contains(e.getRelation().ordinal()))
				.count();
		}

		if (memberList.contains(0))
			count++;

		return count != 0;
	}
		return true;
	}
	
	private List<Integer> getEligibleMemberList(TravelRule travelRule) {
		List<Integer> memberList = new ArrayList<>();
		try {
			memberList.addAll(
					travelRule.getEligibilityMembers().stream().map(e -> e.getRelationType().ordinal()).toList());

		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		return memberList;
	}

	private void getTRRuleTravelMode(TravelRule travelRule,TravellerInfoBulkBkgModel infoModel) {
		
		 try 
		 {
			Set<TRTravelMode> modes=travelRule.getModes();
			
			AtomicBoolean isMixedApplicable=new AtomicBoolean(false);
			Map<Integer, String> travelMode=new LinkedHashMap<>();
			if (modes!=null && !modes.isEmpty()) 
			{
				infoModel.setIsExist("YES");
				
				modes.forEach(obj->{
					TravelMode modeData= Optional.ofNullable(obj.getTravelMode()).orElse(TravelMode.RAIL);
					if(modeData.ordinal()==2) {isMixedApplicable.set(true);}
					travelMode.put(modeData.ordinal(), modeData.getDisplayValue());
				});
				
				if(isMixedApplicable.get()) {infoModel.setIsMixApplicable("YES");}
				else {infoModel.setIsMixApplicable("NO");}
			}else
			{
			
				infoModel.setIsExist("YES");
				travelMode.put(TravelMode.RAIL.ordinal(), TravelMode.RAIL.getDisplayValue());
				infoModel.setIsMixApplicable("NO");
			}
			
			infoModel.setTravelMode(travelMode);
			
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
	}
	
	private String getFullStationNameByStnCode(String stnCode) 
	 {
		String fullString="";
		try
		 {
			 
			 RailStation station= stationServices.getStationByCode(stnCode);
			
		     if(null!=station)
		     {
		    	 String stnName= station.getRailStationName();
		    	 fullString=stnName+"("+stnCode+")";
		     }
		 }
		 catch (Exception e) 
		 {
			 DODLog.error(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"Error In getting Station Name  from Station Code--"+e.getMessage());
			
		 }
		 
	     return fullString;
	} 
	
	private String getGroupIdForUserId(BigInteger userId) 
	{
		String groupId="";
		try{
			
			Optional<OfficeModel> office=officesService.getOfficeByUserId(userId);
			if(office.isPresent()) {
				groupId=office.get().getGroupId();
			}
			
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class, "getGroupIdForUserId("+userId+")-groupId="+groupId);
		
		}catch(Exception e){
			DODLog.error(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class, "getGroupIdForUserId("+userId+")-Exception is:: "+e);
		}
		return groupId;
	}
	
	private List<String> getAllDutyStation(String unitOfficeId, String existingStationCode, int type) 
	{
		
		List<String> records=new ArrayList<>();
		try
		{	
			if(unitOfficeId!=null && !unitOfficeId.equals(""))
			{
				ResponseEntity<StringResponse> response= template.getForEntity(PcdaConstant.REQUEST_BASE_URL+"/getAllDutyStation?unitOfficeId={unitOfficeId}&&type={type}", 
						StringResponse.class, unitOfficeId,type);
				
				StringResponse dutyStationResponse=response.getBody();
				
				if (null != dutyStationResponse && dutyStationResponse.getErrorCode() == 200 && null != dutyStationResponse.getResponseList()) {
					records=dutyStationResponse.getResponseList().stream().filter(e->!existingStationCode.equals(e)).toList();
				}
				
			}
		}catch(Exception e){
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		return records;
		
	}
	
private Map<String, String> getRecordOfficeInfoForSamePAO(String accountOffice) {
		
		Optional<OfficeModel> office= officesService.getOfficeByGroupId(accountOffice);
	
		Map<String,String> recordOfficeInfo=new HashMap<>();
		
		if(office.isPresent()){
			recordOfficeInfo.put("RECORD_OFFICE_NRS", office.get().getRecordOfficeNRS());
			recordOfficeInfo.put("RECORD_OFFICE_NAP", office.get().getRecordOfficeNAP());
		}
		return recordOfficeInfo;
	}

private Map<String, String> getRecordOfficeInfoForDiffPAO(String railAccountOffice, String airAccountOffice) {
	
	  
	Map<String,String> recordOfficeInfo=new HashMap<>();
	Optional<OfficeModel> railOffice= officesService.getOfficeByGroupId(railAccountOffice);
	Optional<OfficeModel> airOffice= officesService.getOfficeByGroupId(airAccountOffice);
	
	if(railOffice.isPresent()) {
		recordOfficeInfo.put("RECORD_OFFICE_NRS", railOffice.get().getRecordOfficeNRS());
	}
	
	if(airOffice.isPresent()) {
		recordOfficeInfo.put("RECORD_OFFICE_NAP", airOffice.get().getRecordOfficeNAP());
	}
	

	return recordOfficeInfo;
}

private String getRailHostelLocation(VisitorModel visitor) {
	
	StringBuilder railStation= new StringBuilder("");
	Set<String> stationCode=new HashSet<>();
	
	if(null!=visitor.getFamilyDetails() && !visitor.getFamilyDetails().isEmpty()) {
	
	visitor.getFamilyDetails().forEach(obj->{
		int relation=Optional.ofNullable(obj.getRelation()).orElse(RelationType.SELF).ordinal();
		String hostelNRS=Optional.ofNullable(obj.getHostelNRS()).orElse("");
		if((relation==2 || relation==3 || relation==4 || relation==5) && !hostelNRS.isBlank() && stationCode.add(hostelNRS)){
			
			if("".equals(railStation.toString())){
				railStation.append(getFullStationNameByStnCode(hostelNRS));
			}else{
				railStation.append("::"+getFullStationNameByStnCode(hostelNRS));
			}
			
		}
	});
	
	}
	
	DODLog.error(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getRailHostelLocation("+visitor.getName()+"): railStation:: "+railStation);
	return railStation.toString();
}


public String getFullAirportNameByCode(String airCode) 
{
	String fullString="";
	 try
	 {
		 AirPort airport=airportServices.getAirportByCode(airCode);
		 
		 if(null!=airport)
	     {
	    	 String stnName= airport.getAirPortName();
	    	 fullString=stnName+"("+airCode+")";
	     }
	 }
	 catch (Exception e) 
	 {
		 DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	 }
	 
    return fullString;
}

private String getAirHostelLocation(VisitorModel visitor) {
	
	Set<String> stationCode=new HashSet<>();
	StringBuilder airStation=new StringBuilder("");
	
	if(null!=visitor.getFamilyDetails() && !visitor.getFamilyDetails().isEmpty()) {
	
	visitor.getFamilyDetails().forEach(e->{
		int relation=Optional.ofNullable(e.getRelation()).orElse(RelationType.SELF).ordinal();
		String hostelNAP=Optional.ofNullable(e.getHostelNAP()).orElse("");
		
		if((relation==2 || relation==3 || relation==4 || relation==5) && !hostelNAP.isBlank() && stationCode.add(hostelNAP)){
				if(airStation.toString().isBlank()){
					airStation.append(getFullAirportNameByCode(hostelNAP));
				}else{
					airStation.append("::"+getFullAirportNameByCode(hostelNAP));
				}
			
		}
	});
		
	}
		
		DODLog.error(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getAirHostelLocation("+visitor.getUserAlias()+"): airStation :: "+airStation.toString());
		return airStation.toString();
	}


private Set<String> getSpecialLtcSectorAirports( String sectorName, int stationType) 
{
	 //stationType=1 means airports , 0 means railways stations 
	//LTC Special Sector 8=North East,11-Jammu & Kashmir,12-Andaman & Nicobar 
	 
	 Set<String> ltcSpclSecAirport=new HashSet<>();
	 try
	 {
		 ResponseEntity<StringResponse> response= template.getForEntity(PcdaConstant.REQUEST_BASE_URL+"/getLtcSpecialSectorId?sectorName={sectorName}", 
				 StringResponse.class, sectorName);
		 
		 String sectorId=""; 
		 
		 StringResponse ltcSectorIdResponse= response.getBody();
		 if(null!=ltcSectorIdResponse) {
		    sectorId= ltcSectorIdResponse.getResponse();
		 }
		 
		 Optional<LtcSpecialSector> specialSector= ltcSpecialSectorServices.getLtcSpecialSectorData(sectorId);
		 
		
		 
		 if(specialSector.isPresent()) {
			 
			 LtcSpecialSector ltcSpecialSector=specialSector.get();
			 
			 DODLog.error(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"get Special Ltc Sector Airports:For ApprovalState"+ltcSpecialSector.getApprovalState()+" |"
					 +" TravelType:"+ltcSpecialSector.getTravelType());
			 
			 if(ltcSpecialSector.getApprovalState().ordinal()==1 && ltcSpecialSector.getTravelType().equals("LTC")) {
				 
				 ltcSpecialSector.getLtcSpecialSectorDtls().forEach(obj->{
					 if(obj.getStationType().ordinal()==stationType) {
						 ltcSpclSecAirport.add(obj.getStationName()+"("+obj.getStationCode()+")");
					 }
					 
				 });
				 
			 }
			 
		 }
		 
	 }
	 catch (Exception e) 
	 {
		 DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	 }
	 
	 DODLog.error(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getSpecialLtcSectorAirports:ltcSpclSecAirportList Size-"+ltcSpclSecAirport.size());
	 
	 return ltcSpclSecAirport;
}

private List<String> getFromStationListForTR100208(TravelRule travelRule, int jrnyType, String dutyAirport) {
	
	List<String> stations=new ArrayList<>();
	try {
		if(jrnyType==0){
			
			travelRule.getFixedStations().stream().filter(obj->obj.getStationType().equals(0) 
					                         && Optional.ofNullable(obj.getStatus()).orElse(Status.OFF_LINE).ordinal()==1
					                         && obj.getStationCode().equals(dutyAirport))
			                                .forEach(e->stations.add(e.getStationName()+"("+e.getStationCode()+")"));
		}
		if(jrnyType==1){
			
			travelRule.getFixedStations().stream().filter(obj->obj.getStationType().equals(1) 
                    && Optional.ofNullable(obj.getStatus()).orElse(Status.OFF_LINE).ordinal()==1)
                   .forEach(e->stations.add(e.getStationName()+"("+e.getStationCode()+")"));
		}
	} catch (Exception e) {
		DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	}
	return stations;
}
private List<String> getToStationListForTR100208(TravelRule travelRule, int jrnyType, String dutyAirport) {
	
	List<String> stations=new ArrayList<>();
	try {
		if(jrnyType==0){
			
			travelRule.getFixedStations().stream().filter(obj->obj.getStationType().equals(1) 
                    && Optional.ofNullable(obj.getStatus()).orElse(Status.OFF_LINE).ordinal()==1)
                   .forEach(e->stations.add(e.getStationName()+"("+e.getStationCode()+")"));
		
		}
		if(jrnyType==1){
			
			travelRule.getFixedStations().stream().filter(obj->obj.getStationType().equals(0) 
                    && Optional.ofNullable(obj.getStatus()).orElse(Status.OFF_LINE).ordinal()==1 
                    && obj.getStationCode().equals(dutyAirport))
                   .forEach(obj->stations.add(obj.getStationName()+"("+obj.getStationCode()+")"));
			
		}
	} catch (Exception e) {
		DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	}
	return stations;
}

private List<TRQuestionBulkBkgModel> getQuestionAskedForTRRule(TravelRule travelRule) {
	
	List<TRQuestionBulkBkgModel> trQuestions=new ArrayList<>();
	 try {
		 List<TRQuestion> questions=new ArrayList<>(travelRule.getQuestions());
		 Collections.sort(questions);
		 
		 questions.forEach(obj->{
			 TRQuestionBulkBkgModel questionsModel=new TRQuestionBulkBkgModel();
			 questionsModel.setQuestion(obj.getQuestion());
			 questionsModel.setQuestionDesc(obj.getDescription());
			 questionsModel.setAnswer(String.valueOf(Optional.ofNullable(obj.getAnswer()).orElse(YesOrNo.YES).ordinal()));
			 trQuestions.add(questionsModel);
			 
		 });
		
		 questions.clear();
		
	} catch (Exception e) {
		DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	}
	
	return trQuestions;
}


private List<String> getRequisiteAllowedXmlForTRRule(TravelRule travelRule)
{
	 List<String> requisiteDtls=new ArrayList<>();
	 
	 try 
	 {
		 if(null!=travelRule.getRequisites()) {
		 requisiteDtls.addAll(travelRule.getRequisites().stream().map(TRRequisite::getTravelRequisite).toList());
		 }
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getRequisiteAllowedXmlForTRRule]:Requisite ->"+travelRule.getRequisites());
	
	} catch (Exception e) {
		DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	}
	 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getRequisiteAllowedXmlForTRRule]:Requisite ->"+requisiteDtls);
	 
    return requisiteDtls;	 
}

private boolean validateDateOfRetirement(VisitorModel visitor) 
{
	Date retrmntDate=visitor.getDateOfRetirement();
	Date date=new Date();
	boolean flag=true;
	
	DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[validateDateOfRetirement]Retirement Date-"+retrmntDate+"||Today Date-"+date);
	
	if(retrmntDate!=null && (date.after(retrmntDate)|| date.equals(retrmntDate))){
		flag= false;
	}
	
	return flag;
}

private String validateDateOfBooking(VisitorModel visitor) 
{
	Date retrmntDate=visitor.getDateOfRetirement();
	Date date=new Date();
	String isValid="NOTALLOW";
	
	DODLog.error(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[validateDateOfRetirement]Retirement Date-"+retrmntDate+"||Today Date-"+date);
	if(retrmntDate!=null && (date.before(retrmntDate) || date.equals(retrmntDate)) ){
	
		return "true";
	}
	return isValid;
}


private long isAttendedAllowed(TravelRule travelRule) {
	   int isAttAll=Optional.ofNullable(travelRule.getIsAttendendAllowed()).orElse(YesOrNo.YES).ordinal();
	   long noOfAttAll=0;
	   if(isAttAll==0)
		   noOfAttAll=Optional.ofNullable(travelRule.getAttendentJrnyCount()).orElse(0).intValue();
	   
	    return noOfAttAll;
}

private String isPartyDependentHigherClassAllowed(TravelRule travelRule){
	
	 String depHigherClassAllowed="";
	
	 if(travelRule.getPartyDepHigherClassAllowed()!=null && !travelRule.getPartyDepHigherClassAllowed().getDisplayValue().equals(""))
	 {
		depHigherClassAllowed= travelRule.getPartyDepHigherClassAllowed().getDisplayValue();
	 }
	 else
	 {
		  depHigherClassAllowed="No";  
	 }
	  
	 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"isPartyDependentHigherClassAllowed ->"+depHigherClassAllowed);
	 return depHigherClassAllowed;
	  
}


private boolean isCivilianService(String serviceId){
	
	boolean isCivilianService=false;
	try
	{
		Optional<DODServices> service=services.getServiceByServiceId(serviceId);
		if(service.isPresent() && 
				Optional.ofNullable(service.get().getArmedForces()).orElse(YesOrNo.YES).ordinal()==1) {
			isCivilianService=true;
		}

	}catch(Exception e){
		DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	}
	
	return isCivilianService;
}

private Map<String,String> getCodeHead(VisitorModel travler,String travelType, String reqType) 
{
	    String railCodeHead="";
	    String airCodeHead="";
	    try
	    {
	    	
	    	Optional<CodeHead> codeHead= headServices.getCodeHeadByService(travler.getUserServiceId(), travler.getCategoryId(), travelType);
	    	
	    	if(codeHead.isPresent()) {
	    		railCodeHead=codeHead.get().getRailCodehead();
	    		if (reqType.equalsIgnoreCase("international")){
					airCodeHead=codeHead.get().getIntlcodehead();
				}else{
					airCodeHead=codeHead.get().getAirCodehead();
				}
	    	}
	    
		
	    }
	    catch (Exception e) {
	    	DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
	    
	    DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getCodeHead | railCodeHead:"+railCodeHead+" | airCodeHead:"+airCodeHead);
	    
	    Map<String, String> codeHeadMap=new HashMap<>();
	    codeHeadMap.put("railCodeHead", railCodeHead);
	    codeHeadMap.put("airCodeHead", airCodeHead);
	    
	    return codeHeadMap;
	
}


private String getLevelNameByLevelId(String levelID) {
	
	String value="";
	try {
		
		Optional<Level> level=  levelServices.getLevel(levelID);
		if(level.isPresent()) {
			value=level.get().getLevelName();
		}
		
	} catch (Exception e) {
		DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	}
   
	return  value;
	
}

private int getCPCPermission(String serviceId) {
	
	int permissionType = -1;
	try
	{
		Optional<DODServices> service=services.getServiceByServiceId(serviceId);
		if(service.isPresent()) {
			permissionType=service.get().getPermissionType().ordinal();
		}

	}catch(Exception e){
		DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	}
	
	return permissionType;
}
	


private void getRank(VisitorModel travler,TravelRule travelRule, TravellerInfoBulkBkgModel infoModel)
{
	
	 try{
		 Optional<GradePayRankModel> rankData= gradePayRankServices.getGradePayWithDODRankId(travler.getRankId());
		 
		 if (rankData.isPresent()) {
			 
				infoModel.setRankName(rankData.get().getRankName());
				infoModel.setRankID(rankData.get().getDodRankId());
				
				
				String isHghrClsAllwd=travelRule.getHigherClassAllowed().getDisplayValue();
				DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getRank]is higher class allwd--"+isHghrClsAllwd);
				
				infoModel.setEntiltedClass(getEntitledClass(rankData.get().getHighestEntitledClass().ordinal(),isHghrClsAllwd).
						                                      stream().collect(Collectors.toMap(ClassType::ordinal, ClassType::getDisplayValue)));
				
	        }
		}catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
	    
}

private List<ClassType> getEntitledClass(int highestClass,String isAllwdHghrAll){
	
	 List<ClassType> arrayList=new ArrayList<>();
	 
	 if(!isAllwdHghrAll.equalsIgnoreCase("yes")){
		 arrayList.addAll(Arrays.stream(ClassType.values()).filter(obj->obj.ordinal() >= highestClass).toList());
	 }else {
		 arrayList.addAll(Arrays.asList(ClassType.values()));
	 } 
	 
	
	return arrayList;
	
}


private void getRankAirLTC(VisitorModel travler,TravelRule travelRule, TravellerInfoBulkBkgModel infoModel)
{
	
	 try{
		    Optional<GradePayRankModel> rankData= gradePayRankServices.getGradePayWithDODRankId(travler.getRankId());
			if (rankData.isPresent()) 
	        {
		       
				infoModel.setAirRankName(rankData.get().getRankName());
				infoModel.setAirRankID(rankData.get().getDodRankId());
				
				String isHghrClsAllwd=travelRule.getHigherClassAllowedAir().getDisplayValue();
				DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getRankAirLTC:RankName-"+rankData.get().getRankName()+",RankID-"+rankData.get().getDodRankId()+",isHghrClsAllwd-"+isHghrClsAllwd);
				
			    
				if(Optional.ofNullable(rankData.get().getHighestAirEntitledClass()).isPresent())
				{
					Map<Integer, String> map=new HashMap<>();
					map.put(2, "Economy Class");
					infoModel.setAirEntiltedClass(map);
				}else
				{
					
					int isFixedStationAllowed=Optional.ofNullable(travelRule.getIsFixedStnAllowed()).orElse(YesOrNo.NO).ordinal();
					String isFixedStationAllowedStr=Optional.ofNullable(travelRule.getIsFixedStnAllowed()).orElse(YesOrNo.NO).getDisplayValue();
					
					DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getRankXMLAirLTC:isFixedStationAllowed-"+isFixedStationAllowed+",isFixedStationAllowedStr-"+isFixedStationAllowedStr);
					
					if(isFixedStationAllowed==0 && isFixedStationAllowedStr.equalsIgnoreCase("YES")){
						Map<Integer, String> map=new HashMap<>();
						map.put(2, "Economy Class");
						infoModel.setAirEntiltedClass(map);
					}else{
						
						Map<Integer, String> map=new HashMap<>();
						map.put(-1, "NA");
						infoModel.setAirEntiltedClass(map);
					}
				}
		        
	        }
	        
		}catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
	    
}


private void getRankAirTD(VisitorModel travler,TravelRule travelRule, TravellerInfoBulkBkgModel infoModel)
{
	
	 try{
		 
		 Optional<GradePayRankModel> rankData= gradePayRankServices.getGradePayWithDODRankId(travler.getRankId());
			if (rankData.isPresent()) 
	        {
				infoModel.setAirRankName(rankData.get().getRankName());
				infoModel.setAirRankID(rankData.get().getDodRankId());
				
		        String isHghrClsAllwd=travelRule.getHigherClassAllowedAir().getDisplayValue();
		        
		        DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getRankAir:RankName-"+rankData.get().getRankName()+",RankID-"+rankData.get().getDodRankId());
				
				if(Optional.ofNullable(rankData.get().getHighestAirEntitledClass()).isPresent())
				{
					
					infoModel.setAirEntiltedClass(getAirEntitledClass(rankData.get().getHighestAirEntitledClass().ordinal(),isHghrClsAllwd).
                           stream().collect(Collectors.toMap(CabinClass::ordinal, CabinClass::getDisplayValue)));
				
				}else
				{
					

					int isFixedStationAllowed=Optional.ofNullable(travelRule.getIsFixedStnAllowed()).orElse(YesOrNo.NO).ordinal();
					String isFixedStationAllowedStr=Optional.ofNullable(travelRule.getIsFixedStnAllowed()).orElse(YesOrNo.NO).getDisplayValue();
					
					
					DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getRankAirTD:isFixedStationAllowed-"+isFixedStationAllowed+",isFixedStationAllowedStr-"+isFixedStationAllowedStr);
					
					if((isFixedStationAllowed==0 && isFixedStationAllowedStr.equalsIgnoreCase("YES")) || (travelRule.getTrRuleId().equals("TR100034") || travelRule.getTrRuleId().equals("TR100180"))){
						Map<Integer, String> map=new HashMap<>();
						map.put(2, "Economy Class");
						infoModel.setAirEntiltedClass(map);
						
					}else{
						Map<Integer, String> map=new HashMap<>();
						map.put(-1, "NA");
						infoModel.setAirEntiltedClass(map);
						
					}
					
				}
				
		        
	        }
	        
		}catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
	    
}

private List<CabinClass> getAirEntitledClass(int highestClass,String isAllwdHghrAll){
	
	 List<CabinClass> arrayList=new ArrayList<>();
	 
	 if(!isAllwdHghrAll.equalsIgnoreCase("yes")){
		 arrayList.addAll(Arrays.stream(CabinClass.values()).filter(obj->obj.ordinal() >= highestClass).toList());
	 }else {
		 arrayList.addAll(Arrays.asList(CabinClass.values()));
	 } 
	 
	
	return arrayList;
	
}

private void getRankAir(VisitorModel travler,TravelRule travelRule, TravellerInfoBulkBkgModel infoModel)
{
	
	 try{
		 Optional<GradePayRankModel> rankData= gradePayRankServices.getGradePayWithDODRankId(travler.getRankId());
		    if (rankData.isPresent()) 
	        {
		    	infoModel.setAirRankName(rankData.get().getRankName());
				infoModel.setAirRankID(rankData.get().getDodRankId());
				
				String isHghrClsAllwd=travelRule.getHigherClassAllowedAir().getDisplayValue();
		       
				DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getRankAir:RankName-"+rankData.get().getRankName()+",RankID-"+rankData.get().getDodRankId()+",isHghrClsAllwd-"+isHghrClsAllwd);
			    
				if(Optional.ofNullable(rankData.get().getHighestAirEntitledClass()).isPresent())
				{
					infoModel.setAirEntiltedClass(getAirEntitledClass(rankData.get().getHighestAirEntitledClass().ordinal(),isHghrClsAllwd).
                           stream().collect(Collectors.toMap(CabinClass::ordinal, CabinClass::getDisplayValue)));
				
				}else
				{
					
					Map<Integer, String> map=new HashMap<>();
					map.put(-1, "NA");
					infoModel.setAirEntiltedClass(map);
					
				}
				
		        
	        }
	        
		}catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
}

private void getCPCRailEntitlement(VisitorModel travler, TravelRule travelRule, String serviceId, String travelTypeId, TravellerInfoBulkBkgModel infoModel) {
	
	 try{
		 Optional<GradePayRankModel> rankData= gradePayRankServices.getGradePayWithDODRankId(travler.getRankId());
		 
			if (rankData.isPresent()) 
	        {
				
				infoModel.setRankName(rankData.get().getRankName());
				infoModel.setRankID(rankData.get().getDodRankId());
				
				String isHghrClsAllwd=travelRule.getHigherClassAllowed().getDisplayValue();
				DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getCPCRailEntitlement]is higher class allwd--"+isHghrClsAllwd);
				String levelId=travler.getLevelId();
				int railEntitledClass=getRailEntitledClass(levelId, serviceId, travelTypeId);
				
				DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getCPCRailEntitlement] rail Entitled Class--"+railEntitledClass+", levelId-"+levelId);
				if(railEntitledClass >= 0){
					
					infoModel.setEntiltedClass(getEntitledClass(railEntitledClass,isHghrClsAllwd).
                           stream().collect(Collectors.toMap(ClassType::ordinal, ClassType::getDisplayValue)));
					
			      
				}else{
					Map<Integer, String> map=new HashMap<>();
					map.put(-1, "Not Entitled");
					
					infoModel.setEntiltedClass(map);
					
				
				}
	        }
		}catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
	    
}


private int getRailEntitledClass(String levelId, String serviceId, String travelTypeId) {
		
		int railEntitledClass=-1;
		
		List<Integer> entitlements=getEntitledClassFromLevelEntitlement(levelId, serviceId, travelTypeId);
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getRailEntitledClass] entitlements--"+entitlements);
		if(entitlements.size() ==2){
			railEntitledClass=entitlements.get(0);
		}
		
		return railEntitledClass;
	}


private void getCPCAirLTCEntitlement(VisitorModel travler, TravelRule travelRule, String serviceId, String travelTypeId, TravellerInfoBulkBkgModel infoModel) {
	
	 try{
		 Optional<GradePayRankModel> rankData= gradePayRankServices.getGradePayWithDODRankId(travler.getRankId());
		 
			 if (rankData.isPresent()) {
				
				infoModel.setAirRankName(rankData.get().getRankName());
				infoModel.setAirRankID(rankData.get().getDodRankId());
				
				String isHghrClsAllwd=travelRule.getHigherClassAllowedAir().getDisplayValue();
				DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getCPCAirLTCEntitlement:RankName-"+rankData.get().getRankName()+",RankID-"+rankData.get().getDodRankId());
				
				String levelId=travler.getLevelId();
				int airEntitledClass=getAirEntitledClass(levelId, serviceId, travelTypeId);
				
				
				DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getCPCAirLTCEntitlement:HIGHEST_AIR_ENTITLED::"+airEntitledClass+",isHghrClsAllwd-"+isHghrClsAllwd+",levelId-"+levelId);
			    
				if(airEntitledClass >= 0)
				{
					infoModel.setAirEntiltedClass(getAirEntitledClass(airEntitledClass,isHghrClsAllwd).
                           stream().collect(Collectors.toMap(CabinClass::ordinal, CabinClass::getDisplayValue)));
					
				}else
				{
					
					int isFixedStationAllowed=Optional.ofNullable(travelRule.getIsFixedStnAllowed()).orElse(YesOrNo.NO).ordinal();
					String isFixedStationAllowedStr=Optional.ofNullable(travelRule.getIsFixedStnAllowed()).orElse(YesOrNo.NO).getDisplayValue();
					
					int isAirAllowed=Optional.ofNullable(travelRule.getAirlineAllowedAll()).orElse(YesOrNo.NO).ordinal();
					String isAirAllowedStr=Optional.ofNullable(travelRule.getAirlineAllowedAll()).orElse(YesOrNo.NO).getDisplayValue();
					
					DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getCPCAirLTCEntitlement:isFixedStationAllowed:"+isFixedStationAllowed+
							                    "|isFixedStationAllowedStr:"+isFixedStationAllowedStr+
							                    "|isAirAllowed:"+isAirAllowed+
							                    "|isAirAllowedStr:"+isAirAllowedStr);
					
					if((isFixedStationAllowed==0 && isFixedStationAllowedStr.equalsIgnoreCase("YES"))||(isAirAllowed==0 && isAirAllowedStr.equalsIgnoreCase("YES"))){
						Map<Integer, String> map=new HashMap<>();
						map.put(2, "Economy Class");
						infoModel.setAirEntiltedClass(map);
						
					}else{
						Map<Integer, String> map=new HashMap<>();
						map.put(-1, "Not Entitled");
						infoModel.setAirEntiltedClass(map);
					}
				}
				
		        
	        }
	        
		}catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
	   
}


private List<Integer> getEntitledClassFromLevelEntitlement(String levelId, String serviceId, String travelTypeId){
	
	List<Integer> entitlements=new ArrayList<>();
	
	try {
		ResponseEntity<IntegerResponse> response= template.getForEntity(PcdaConstant.REQUEST_BASE_URL+"/getEntitledClassFromLevelEntitlement/"+levelId+"/"+serviceId+"/"+travelTypeId, 
				IntegerResponse.class);
		
		IntegerResponse entitledResponse= response.getBody();
		if(null!=entitledResponse && entitledResponse.getErrorCode()==200) {
			entitlements.addAll(entitledResponse.getResponseList());
		}
		
	} catch (Exception e) {
		DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	}
	return entitlements;
}

private int getAirEntitledClass(String levelId, String serviceId, String travelTypeId) {
    int airEntitledClass=-1;
		
    List<Integer> entitlements=getEntitledClassFromLevelEntitlement(levelId, serviceId, travelTypeId);
    DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getAirEntitledClass] entitlements--"+entitlements.size());
		if(entitlements.size() ==2){
			airEntitledClass=entitlements.get(1);
		}
		
		return airEntitledClass;
	}





private void getCPCAirTDEntitlement(VisitorModel travler, TravelRule travelRule, String serviceId, String travelTypeId, TravellerInfoBulkBkgModel infoModel) {
	
	 try{
		 Optional<GradePayRankModel> rankData= gradePayRankServices.getGradePayWithDODRankId(travler.getRankId());
	        if (rankData.isPresent()) 
	        {
				
				infoModel.setAirRankName(rankData.get().getRankName());
				infoModel.setAirRankID(rankData.get().getDodRankId());
				
				String isHghrClsAllwd=travelRule.getHigherClassAllowedAir().getDisplayValue();
				DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getCPCAirTDEntitlement:RankName-"+rankData.get().getRankName()+",RankID-"+rankData.get().getDodRankId());
				
				String levelId=travler.getLevelId();
				int airEntitledClass=getAirEntitledClass(levelId, serviceId, travelTypeId);
				
				
				DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getCPCAirTDEntitlement:HIGHEST_AIR_ENTITLED::"+airEntitledClass+",isHghrClsAllwd-"+isHghrClsAllwd+",levelId-"+levelId);
			    
				if(airEntitledClass >= 0)
				{
					
					infoModel.setAirEntiltedClass(getAirEntitledClass(airEntitledClass,isHghrClsAllwd).
                           stream().collect(Collectors.toMap(CabinClass::ordinal, CabinClass::getDisplayValue)));
				}else
				{ 
					
					int isFixedStationAllowed=Optional.ofNullable(travelRule.getIsFixedStnAllowed()).orElse(YesOrNo.NO).ordinal();
					String isFixedStationAllowedStr=Optional.ofNullable(travelRule.getIsFixedStnAllowed()).orElse(YesOrNo.NO).getDisplayValue();
					
					int isAirAllowed=Optional.ofNullable(travelRule.getAirlineAllowedAll()).orElse(YesOrNo.NO).ordinal();
					String isAirAllowedStr=Optional.ofNullable(travelRule.getAirlineAllowedAll()).orElse(YesOrNo.NO).getDisplayValue();
					
					DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getCPCAirTDEntitlement:isFixedStationAllowed:"+isFixedStationAllowed+
							                    "|isFixedStationAllowedStr:"+isFixedStationAllowedStr+
							                    "|isAirAllowed:"+isAirAllowed+
							                    "|isAirAllowedStr:"+isAirAllowedStr);
					
					if((isFixedStationAllowed==0 && isFixedStationAllowedStr.equalsIgnoreCase("YES"))||(isAirAllowed==0 && isAirAllowedStr.equalsIgnoreCase("YES"))){
						Map<Integer, String> map=new HashMap<>();
						map.put(2, "Economy Class");
						infoModel.setAirEntiltedClass(map);
						
					}else{
						Map<Integer, String> map=new HashMap<>();
						map.put(-1, "Not Entitled");
						infoModel.setAirEntiltedClass(map);
					}
					
				}
		        
	        }
	        
		}catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
	
	 
}

private void getCPCAirEntitlement(VisitorModel travler, TravelRule travelRule, String serviceId, String travelTypeId,TravellerInfoBulkBkgModel infoModel) {
	
	 try{
		 
		 Optional<GradePayRankModel> rankData= gradePayRankServices.getGradePayWithDODRankId(travler.getRankId());
			
	        if (rankData.isPresent()) 
	        {
		       infoModel.setAirRankName(rankData.get().getRankName());
				infoModel.setAirRankID(rankData.get().getDodRankId());
				
				String isHghrClsAllwd=travelRule.getHigherClassAllowedAir().getDisplayValue();
				
				DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getCPCAirEntitlement:getCPCAirEntitlement-"+rankData.get().getRankName()+",RankID-"+rankData.get().getDodRankId());
				
				String levelId=travler.getLevelId();
				int airEntitledClass=getAirEntitledClass(levelId, serviceId, travelTypeId);
				
				DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getCPCAirEntitlement:HIGHEST_AIR_ENTITLED-"+airEntitledClass+",isHghrClsAllwd-"+isHghrClsAllwd+",levelId-"+levelId);
			    
				if(airEntitledClass >= 0)
				{
					infoModel.setAirEntiltedClass(getAirEntitledClass(airEntitledClass,isHghrClsAllwd).
                           stream().collect(Collectors.toMap(CabinClass::ordinal, CabinClass::getDisplayValue)));
				
				}else{
					
					int isAirAllowed=Optional.ofNullable(travelRule.getAirlineAllowedAll()).orElse(YesOrNo.NO).ordinal();
					String isAirAllowedStr=Optional.ofNullable(travelRule.getAirlineAllowedAll()).orElse(YesOrNo.NO).getDisplayValue();
					
					DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"getCPCAirEntitlement"+
		                    "|isAirAllowed:"+isAirAllowed+
		                    "|isAirAllowedStr:"+isAirAllowedStr);
					
					if(isAirAllowed==0 && isAirAllowedStr.equalsIgnoreCase("YES")){
						
						Map<Integer, String> map=new HashMap<>();
						map.put(2, "Economy Class");
						infoModel.setAirEntiltedClass(map);
						
					}else
					{
						Map<Integer, String> map=new HashMap<>();
						map.put(-1, "Not Entitled");
						infoModel.setAirEntiltedClass(map);
					}
				
				}
		        
	        }
	        
		}catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
}

private boolean getTADAPermission(String categoryId,String unitId,String travelTypeName,String serviceId)
{
	
	DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getTADAPermission]categoryId = "+categoryId+" || unitId ="+unitId+" || travelTypeName ="+travelTypeName+" || serviceId="+serviceId);
	boolean flag=false;
	try 
	{
		ResponseEntity<BooleanResponse> response= template.getForEntity(PcdaConstant.REQUEST_BASE_URL+"/getTADAPermission/"+serviceId+"/"+unitId+"/"+categoryId+"/"+travelTypeName, 
				BooleanResponse.class);
		
		BooleanResponse permissionResponse= response.getBody();
		if(null!=permissionResponse && permissionResponse.getErrorCode()==200 && null!=permissionResponse.getResponse()) {
			flag=permissionResponse.getResponse().booleanValue();
		}
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class,"[getTADAPermission] flag= "+flag); 
	 	
	    
	} catch (Exception e) {
		DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
	}
	return flag;
}

private boolean getTRRuleTADAPermission(TravelRule travelRule, String travelTypeName){
	
	boolean flag=false;
	
	if(("TD".equals(travelTypeName) && Optional.ofNullable(travelRule.getDaAllowed()).orElse(YesOrNo.NO).ordinal() == 0 
			&& Optional.ofNullable(travelRule.getDaAdvanceDay()).orElse(0).intValue() > 0) 
			|| ("PT".equals(travelTypeName) && Optional.ofNullable(travelRule.getCtgAllowed()).orElse(YesOrNo.NO).ordinal() == 0)){
		
		flag=true;
		
	}
	
	return flag;
}

private boolean getTADAHotelAllow(TravelRule travelRule, String travelTypeName){
    boolean flag=false;
		
		if("TD".equals(travelTypeName) && Optional.ofNullable(travelRule.getDaAllowed()).orElse(YesOrNo.NO).ordinal() == 0 && 
				Optional.ofNullable(travelRule.getHotelAllowanceAllow()).orElse(YesOrNo.NO).ordinal()== 0){
			flag=true;
		}
		
		return flag;
	}

	private boolean isAccountDtlsUpdated(VisitorModel travler){
		boolean flag=false;
		String accountNumber=travler.getBankAccountNumber();
		String ifscCode=travler.getIfscCode();
		
		if(null!=accountNumber && !"".equals(accountNumber) && null!=ifscCode && !"".equals(ifscCode)){
			flag=true;
		}
		
		return flag;
	}
	
	private boolean getTADAConveyanceAllow(TravelRule travelRule, String travelTypeName){
	       boolean flag=false;
			
			if("TD".equals(travelTypeName) && Optional.ofNullable(travelRule.getDaAllowed()).orElse(YesOrNo.NO).ordinal()== 0 && 
					Optional.ofNullable(travelRule.getConveyanceAllowanceAllow()).orElse(YesOrNo.NO).ordinal()== 0){
				flag=true;
			}
			
			return flag;
		}
	
	private boolean getTADAFoodAllow(TravelRule travelRule, String travelTypeName){
	       boolean flag=false;
			
			if("TD".equals(travelTypeName) && Optional.ofNullable(travelRule.getDaAllowed()).orElse(YesOrNo.NO).ordinal()== 0 && 
					Optional.ofNullable(travelRule.getFoodAllowanceAllow()).orElse(YesOrNo.NO).ordinal()== 0){
				flag=true;
			}
			
			return flag;
		}
	

}
