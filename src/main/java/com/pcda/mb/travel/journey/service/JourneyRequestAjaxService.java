package com.pcda.mb.travel.journey.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcda.common.model.Category;
import com.pcda.common.model.CitySearchModel;
import com.pcda.common.model.DODServices;
import com.pcda.common.model.EnumType;
import com.pcda.common.model.GradePayRankModel;
import com.pcda.common.model.Location;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.TRScheme;
import com.pcda.common.model.TravelRule;
import com.pcda.common.model.TravelType;
import com.pcda.common.model.VisitorModel;
import com.pcda.common.services.CategoryServices;
import com.pcda.common.services.CityServices;
import com.pcda.common.services.EnumTypeServices;
import com.pcda.common.services.GradePayRankServices;
import com.pcda.common.services.LocationServices;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.TRRuleService;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.common.services.UserServices;
import com.pcda.mb.adduser.transferout.model.EditUserModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetSingleTrainRouteModel;
import com.pcda.mb.requestdashboard.normalbookingdashboard.model.GetSingleTrainRouteResponse;
import com.pcda.mb.requestdashboard.normalbookingdashboard.service.NormalBkgAjaxService;
import com.pcda.mb.travel.journey.model.EligbleMembersModel;
import com.pcda.mb.travel.journey.model.JrnyFairRuleResponse;
import com.pcda.mb.travel.journey.model.JrnyFareRuleBLResponse;
import com.pcda.mb.travel.journey.model.PartyVisitorModel;
import com.pcda.mb.travel.journey.model.StringResponse;
import com.pcda.mb.travel.journey.model.TRJourneyModel;
import com.pcda.mb.travel.journey.model.TRQuestionsModel;
import com.pcda.mb.travel.journey.model.TRRuleDetailsModel;
import com.pcda.mb.travel.journey.model.TRRulesData;
import com.pcda.mb.travel.journey.model.TravelIdDetails;
import com.pcda.mb.travel.journey.model.TravelIdsData;
import com.pcda.mb.travel.journey.model.TravellerDetails;
import com.pcda.mb.travel.journey.model.ViaRouteStationsModel;
import com.pcda.mb.travel.journey.model.VisibleYearModel;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class JourneyRequestAjaxService {
	
	@Autowired
	private TravelDetailsService detailsService;
	
	@Autowired
	private TravelIdAndRuleService ruleService;
	
	@Autowired
	private EnumTypeServices enumService;
	
	@Autowired
	private TRRuleService trRuleService;
	
	@Autowired
	private TravelTypeServices travelTypeServices; 
	
	@Autowired
	private CategoryServices categoryServices; 
	
	@Autowired
	private LocationServices locationServices;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private GradePayRankServices rankServices;
	
	@Autowired
	private OfficesService officesService;
	
	@Autowired
	private MasterServices masterServices;
	
	@Autowired
	private CityServices cityServices;
	
	@Autowired
	private RestTemplate template;
	
	@Autowired
	private NormalBkgAjaxService ajaxService;
	

	@Async("pcdaAsyncExecutor")
	public CompletableFuture<TravellerDetails> getTravellerDetails(HttpServletRequest request) {
		TravellerDetails details= new TravellerDetails();
		try {
			
		String personalNo =  request.getParameter("personalNo");
		Optional<String> userAlias=Optional.ofNullable(personalNo);
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class, "  userAlias = "+userAlias);
		 details = detailsService.getTravellerDetails(userAlias, request);
		EditUserModel user= userServices.getUserByUserId(details.getUserID());
		if(user!= null&& user.getProfileStatus()!=null && user.getProfileStatus().equalsIgnoreCase("requested") && user.getUserEditType().contentEquals("edit")) {
			details.setMessage("The Profile is under Edit Process,Kindly get it Approved/Disapproved from CO before making journey request");
		}
		
		
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class, "  getTravellerDetails = "+details);
		return CompletableFuture.completedFuture(details);
	}

	@Async("pcdaAsyncExecutor")
	public CompletableFuture<Map<Integer, String>> getTravelGroup(HttpServletRequest request) {
		Map<Integer, String> travelGroups=new LinkedHashMap<>();
		try {
		String serviceType=Optional.ofNullable(request.getParameter("serviceType")).orElse("0");
		
		if(serviceType.equals("0"))	{
			travelGroups.putAll(getTravelGroupByTravelId());
		}else{
			travelGroups.putAll(getCivilianTravelGroup());
		}
		
	} catch (Exception e) {
		DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
	}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class, "  travelGroups  = "+travelGroups.size());
		return CompletableFuture.completedFuture(travelGroups);
	}



	private Map<Integer, String> getCivilianTravelGroup() {
		
		
		Map<Integer, String> sortedTravelGroupMap = null;
		try {
		List<EnumType> travelGroup = enumService.getEnumType("TRAVEL_GROUP_TYPE");
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,
				" Get travel Group" + travelGroup.size());
		sortedTravelGroupMap = travelGroup.stream().filter(obj -> !obj.getValue().startsWith("TA-"))
				.collect(Collectors.toMap(EnumType::getCode, EnumType::getValue));
		
		sortedTravelGroupMap = sortedTravelGroupMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		
	} catch (Exception e) {
		DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
	}
	
		return sortedTravelGroupMap;
	}



	private Map<Integer, String> getTravelGroupByTravelId() {
		
		Map<Integer, String> sortedTravelGroupMap = null;
		try {
		List<EnumType> travelGroup=enumService.getEnumType("TRAVEL_GROUP_TYPE");
		sortedTravelGroupMap = travelGroup.stream().filter(obj -> !obj.getValue().startsWith("TA-"))
				.collect(Collectors.toMap(EnumType::getCode, EnumType::getValue));
		
		sortedTravelGroupMap = sortedTravelGroupMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
	} catch (Exception e) {
		DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
	}
		
		return sortedTravelGroupMap;
	}



	@Async("pcdaAsyncExecutor")
	public CompletableFuture<List<TRRulesData>> getAllTRforTravelType(HttpServletRequest request) {
		List<TRRulesData>  trRuleList =new ArrayList<>();
		try {
		String travelType=Optional.ofNullable(request.getParameter("travelType")).orElse("");
		String serviceType=Optional.ofNullable(request.getParameter("serviceType")).orElse("-1");
		String serviceId=Optional.ofNullable(request.getParameter("serviceId")).orElse("");
		String categoryId=Optional.ofNullable(request.getParameter("categoryId")).orElse("");
		String requestType=Optional.ofNullable(request.getParameter("requestType")).orElse("");
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,"travel type id=="+travelType+"||serviceType="+serviceType+"||serviceId="+serviceId+"||categoryId="+categoryId+
				"||requestType="+requestType);
		
		trRuleList=	ruleService.getTRforTravelType(travelType, serviceType, serviceId, categoryId, requestType);
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
		return CompletableFuture.completedFuture(trRuleList);
		
	}

	
	@Async("pcdaAsyncExecutor")
	public CompletableFuture<List<TRRulesData>> getAllTRforTravelGroup(HttpServletRequest request) {
		
		List<TRRulesData> trRulesDatas=new ArrayList<>();
		try {
		String travelType= Optional.ofNullable(request.getParameter("travelType")).orElse(""); 
		String enumCode=Optional.ofNullable(request.getParameter("enumCode")).orElse("-1");  
		String serviceType=Optional.ofNullable(request.getParameter("serviceType")).orElse("-1"); 
		String serviceId=Optional.ofNullable(request.getParameter("serviceId")).orElse(""); 
		String categoryId=Optional.ofNullable(request.getParameter("categoryId")).orElse(""); 
		String requestType=Optional.ofNullable(request.getParameter("requestType")).orElse(""); 
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,":travelTypeId=="+travelType+"||TravelGroupId="+enumCode+
				"||serviceType="+serviceType+"||serviceId="+serviceId+"||categoryId="+categoryId+"||requestType="+requestType);
		trRulesDatas=	ruleService.getTRforTravelGroup(travelType, enumCode, serviceType, serviceId, categoryId, requestType);
		}catch (Exception e) {
			DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
		return CompletableFuture.completedFuture(trRulesDatas);
	}


	@Async("pcdaAsyncExecutor")
	public CompletableFuture<List<TravelIdsData>> getTravelIDs(HttpServletRequest request) {

		 String personalNo= Optional.ofNullable(request.getParameter("personalNo")).orElse(""); 
		 String travelTypeId= Optional.ofNullable(request.getParameter("travelTypeId")).orElse(""); 
		 
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class,":personalNo=="+personalNo+"||travelTypeId="+travelTypeId);

		return CompletableFuture.completedFuture(ruleService.getTravelIds(personalNo, travelTypeId));
	}

	@Async("pcdaAsyncExecutor")
	public CompletableFuture<TravelIdDetails> getTravelRequestID(HttpServletRequest request) {
		
		 String travelId= Optional.ofNullable(request.getParameter("travelID")).orElse(""); 
		 
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class,":travelID=="+travelId);

		return CompletableFuture.completedFuture(ruleService.getTravelIdData(travelId));
	}


	
	public TRRuleDetailsModel getTravelRuleDetails(HttpServletRequest request) {
		
		String trRuleID=Optional.ofNullable(request.getParameter("trRuleID")).orElse("");
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class,":trRuleID=="+trRuleID);
		
		TravelRule travelRule=trRuleService.getTRRuleDetails(trRuleID);
		
		TRRuleDetailsModel ruleDetailsModel=new TRRuleDetailsModel();
		
		if(null!=travelRule) {
			ruleDetailsModel.setTrRuleID(travelRule.getTrRuleId());
			ruleDetailsModel.setTrRuleNo(travelRule.getTrRuleNumber());
			if(null==travelRule.getTravelGroup()) {
				ruleDetailsModel.setTravelGrp("NA");
			}else {
				ruleDetailsModel.setTravelGrp(travelRule.getTravelGroup().getDisplayValue());
			}
			
			ruleDetailsModel.setNotes(travelRule.getNotes());
			ruleDetailsModel.setTrRuleNews(travelRule.getRuleRelatedNews());
			ruleDetailsModel.setTravelReqStatus(travelRule.getIsRequestAllowed().ordinal());
			ruleDetailsModel.setTrRuleTitle(travelRule.getTrRuleTitle());
			ruleDetailsModel.setTrRuleDesc(travelRule.getTrRuleDesc());
			ruleDetailsModel.setRemarks(travelRule.getRemarks());
			ruleDetailsModel.setStatus(travelRule.getStatus().getDisplayValue());
			
			Integer jrnyCountAllowed= Optional.ofNullable(travelRule.getJrnyCountAllowedTR()).orElse(0);

			setJourneyAllowed(jrnyCountAllowed,ruleDetailsModel);
			
			Integer jrnyCountCalender= Optional.ofNullable(travelRule.getJrnyCountCalenderYear()).orElse(0);
			
			if(jrnyCountCalender.intValue()==-1) {
				ruleDetailsModel.setJourneyCountYear("Unlimited");
			}else {
				ruleDetailsModel.setJourneyCountYear(String.valueOf(jrnyCountCalender));
			}
			
			Integer attendentJrny= Optional.ofNullable(travelRule.getAttendentJrnyCount()).orElse(0);
			
			if(attendentJrny.intValue()==-1) {
				ruleDetailsModel.setAttendedCount("Unlimited");
			}else {
				ruleDetailsModel.setAttendedCount(String.valueOf(attendentJrny));
			}
			
			TravelType type=travelTypeServices.getTravelType(travelRule.getRulePurpose());
			
			if(null!=type) {
				ruleDetailsModel.setTravelType(type.getTravelName());
			}else {
				ruleDetailsModel.setTravelType("");
			}
			
			setEligibleCategories(travelRule,ruleDetailsModel);
			setEligbleLocation(travelRule,ruleDetailsModel);
			setEligbleFromToStations(travelRule,ruleDetailsModel);
			setEligbleMembers(travelRule,ruleDetailsModel);
			setQuestionsDtls(travelRule,ruleDetailsModel);
			
		}
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class,"### ruleDetailsModel == "+ ruleDetailsModel);
		return ruleDetailsModel;
	}

	
	private void setJourneyAllowed(Integer jrnyCountAllowed,TRRuleDetailsModel ruleDetailsModel) {
		
		if(jrnyCountAllowed.intValue()==-1) {
			ruleDetailsModel.setJourneyCountTR("Unlimited");
		}else {
			ruleDetailsModel.setJourneyCountTR(String.valueOf(jrnyCountAllowed));
		}
	}

	private void setQuestionsDtls(TravelRule travelRule, TRRuleDetailsModel ruleDetailsModel) {
		
		List<TRQuestionsModel> questions=new ArrayList<>();
		
		if(travelRule.getQuestions()!=null && !travelRule.getQuestions().isEmpty()) {
		
			
		travelRule.getQuestions().forEach(obj->{
			
			TRQuestionsModel questionsModel=new TRQuestionsModel();
			questionsModel.setSequanceNo(obj.getSequanceNo());
			questionsModel.setQuestion(obj.getQuestion());
			questionsModel.setQuestionDesc(obj.getDescription());
			questionsModel.setAnswer(obj.getAnswer().getDisplayValue());
			questions.add(questionsModel);
		});
		}
			Collections.sort(questions);	
		ruleDetailsModel.setQuestions(questions);
	}


	private void setEligbleMembers(TravelRule travelRule, TRRuleDetailsModel ruleDetailsModel) {
		
		List<EligbleMembersModel> eligbleMembers=new ArrayList<>();
		if(	travelRule.getEligibilityMembers()!=null&& 	!travelRule.getEligibilityMembers().isEmpty()) {
		
		travelRule.getEligibilityMembers().forEach(obj->{
			
			EligbleMembersModel membersModel=new EligbleMembersModel();
			membersModel.setRelationType(obj.getRelationType().getDisplayValue());
			if(obj.getPerMemberJrnyCount().intValue()==-1) {
				membersModel.setPerMemberCount("Unlimited");
			}else {
				membersModel.setPerMemberCount(String.valueOf(obj.getPerMemberJrnyCount()));
			}
			
			eligbleMembers.add(membersModel);
			
		});
		}
		ruleDetailsModel.setEligbleMembers(eligbleMembers);
	}


	private void setEligbleFromToStations(TravelRule travelRule, TRRuleDetailsModel ruleDetailsModel) {
		
		List<TRJourneyModel> journeys=new ArrayList<>();
		if(travelRule.getJourneys()!=null && !travelRule.getJourneys().isEmpty()) {
		
		travelRule.getJourneys().forEach(obj->{
			
			if(obj.getJrnyType().ordinal()==0) {
				TRJourneyModel journeyModel=new TRJourneyModel();
				if(obj.getFromToType().ordinal()==0) {
					journeyModel.setFromStnOnward(obj.getJrnyStation().getDisplayValue());
				}else {
					journeyModel.setToStnOnward(obj.getJrnyStation().getDisplayValue());
				}
				
				journeys.add(journeyModel);
				
			}else if(obj.getJrnyType().ordinal()==1) {
				TRJourneyModel journeyModel=new TRJourneyModel();
				if(obj.getFromToType().ordinal()==0) {
					journeyModel.setFromStnReturn(obj.getJrnyStation().getDisplayValue());
				}else {
					journeyModel.setToStnReturn(obj.getJrnyStation().getDisplayValue());
				}
				
				journeys.add(journeyModel);
			}
			
		});
		}
		ruleDetailsModel.setJourneys(journeys);
	}


	private void setEligbleLocation(TravelRule travelRule, TRRuleDetailsModel ruleDetailsModel) {
		
		List<String> locations=new ArrayList<>();
		try {
			if(travelRule.getEligibilityLocations()!=null && !travelRule.getEligibilityLocations().isEmpty()) {
		
		travelRule.getEligibilityLocations().forEach(obj->{
			 Optional<Location> loc= locationServices.getLocationById(obj.getLocationId());
			 if(loc.isPresent()) {
				 locations.add(loc.get().getLocationName()); 
			 }
		});
				
				}
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
	
		
		ruleDetailsModel.setLocations(locations); 
	}


	private void setEligibleCategories(TravelRule travelRule, TRRuleDetailsModel ruleDetailsModel) {
		
		Set<String> categories=new LinkedHashSet<>();
		if(travelRule.getEligibilityCategories()!=null && !travelRule.getEligibilityCategories().isEmpty()) {
		  
		travelRule.getEligibilityCategories().forEach(obj->{
			Optional<Category> cat=categoryServices.getCategoryByCatId(obj.getCategoryId());
			if(cat.isPresent()) {
				categories.add(cat.get().getCategoryName());
			}
			
		});
		}
		
		ruleDetailsModel.setCategories(categories);
		
	}


	@Async("pcdaAsyncExecutor")
	public CompletableFuture<PartyVisitorModel> getPartyVisitor(HttpServletRequest request) {
		
		String userAlias= Optional.ofNullable(request.getParameter("personalNo")).orElse(""); 
		String isPartyHigherClassAllowed=Optional.ofNullable(request.getParameter("isPartyHigherClassAllowed")).orElse("No");  
		String selEntitledClass=Optional.ofNullable(request.getParameter("selEntitledClass")).orElse("10"); 
		String travelerUnitName=Optional.ofNullable(request.getParameter("travelerUnitName")).orElse(""); 
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,"getPartyVisitor:userAlias="+userAlias+"||isPartyHigherClassAllowed="+isPartyHigherClassAllowed+
				"||selEntitledClass="+selEntitledClass+"||travelerUnitName="+travelerUnitName);
		
		int selEntitledClassInt=Integer.parseInt(selEntitledClass);
		PartyVisitorModel partyVisitorModel=new PartyVisitorModel();
		
		if (userAlias.equals("")) {
			partyVisitorModel.setMessage("Enter Personal No.");
			 return CompletableFuture.completedFuture(partyVisitorModel);
		 }
		
		Optional<VisitorModel> user= userServices.getCompleteUser(userAlias);
		if(user.isPresent()) {
			VisitorModel travler=user.get();
			
			if(travler.getApprovalState().ordinal()!=1) {
				partyVisitorModel.setMessage("Traveler is Not Approved, First Approve The Traveler");
				return CompletableFuture.completedFuture(partyVisitorModel);
			}
			
			int  travelerEntitledClass=getTravelerEntitiledClass(travler, partyVisitorModel);
			
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,"getPartyVisitor:travelerEntitledClass="+travelerEntitledClass);
			
			if(travelerEntitledClass==10){
				partyVisitorModel.setMessage("Traveler Does Not Have Entitled Class OR Rank");
				return CompletableFuture.completedFuture(partyVisitorModel);
			}else if(isPartyHigherClassAllowed.equalsIgnoreCase("No") && travelerEntitledClass>selEntitledClassInt){
					
				partyVisitorModel.setMessage("Traveler Is Not Allowed To Travel On Selected Journey Class As Per TR Rule");
				return CompletableFuture.completedFuture(partyVisitorModel);
				
			}
			
			Optional<OfficeModel> officeModel= officesService.getOfficeByUserId(travler.getUserId());
			if(officeModel.isPresent()) {
				
				String groupName=officeModel.get().getName();
				if(!groupName.equals(travelerUnitName))
				 {
					 partyVisitorModel.setMessage("Master traveler and party dependent traveler are not a part of same unit");
					 return CompletableFuture.completedFuture(partyVisitorModel);
					 
				 }
			}else {
				 partyVisitorModel.setMessage("Traveler is not a part of Unit");
				 return CompletableFuture.completedFuture(partyVisitorModel);
			}
			
			boolean checkDateOfRetr=validateDateOfRetirement(travler);
			partyVisitorModel.setCheckRtrmntAge(checkDateOfRetr);
			partyVisitorModel.setUserID(travler.getUserId());
			
			if(null!=travler.getUserServiceId() && !travler.getUserServiceId().equals("")) {
				
				Optional<DODServices> service=masterServices.getServiceByServiceId(travler.getUserServiceId());
				if(service.isPresent()) {
					partyVisitorModel.setService(service.get().getServiceName());
				}else {
					partyVisitorModel.setService("");
				}
				
				partyVisitorModel.setServiceId(travler.getUserServiceId());
				
				
			}else {
				Optional<DODServices> service=masterServices.getServiceByServiceId(travler.getServiceId());
				if(service.isPresent()) {
					partyVisitorModel.setService(service.get().getServiceName());
				}else {
					partyVisitorModel.setService("");
				}
				
				partyVisitorModel.setServiceId(travler.getServiceId());
				
			}
			
			Optional<Category> category= categoryServices.getCategoryByCatId(travler.getCategoryId());
			if(category.isPresent()) {
				partyVisitorModel.setCategory(category.get().getCategoryName());
				partyVisitorModel.setCategoryId(travler.getCategoryId());
			}
			
			partyVisitorModel.setName(travler.getName());
			partyVisitorModel.setDateOfBirth(CommonUtil.formatDate(travler.getDateOfBirth(), "dd-MM-yyyy"));
			partyVisitorModel.setErsPrintName(travler.getTravelerProfile().getErsPrintName());
			partyVisitorModel.setGender(travler.getGender().getDisplayValue());
			
			
		}else {
			partyVisitorModel.setMessage("Traveler Does Not Exists");
			 return CompletableFuture.completedFuture(partyVisitorModel);
		}
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,"getPartyVisitor:partyVisitorModel="+partyVisitorModel.toString());
		return CompletableFuture.completedFuture(partyVisitorModel);
	}
	
	private int getTravelerEntitiledClass(VisitorModel travler, PartyVisitorModel partyVisitorModel) {
		 
		 int entitledClass=10;
		 
		 try{
			 
			 Optional<GradePayRankModel> rankData= rankServices.getGradePayWithDODRankId(travler.getRankId());
			 
				if (rankData.isPresent()) {
				    entitledClass=rankData.get().getHighestEntitledClass().ordinal();
				    partyVisitorModel.setRankName(rankData.get().getRankName());
				    partyVisitorModel.setRankId(rankData.get().getDodRankId());
				    partyVisitorModel.setHighestEntitledClass(rankData.get().getHighestEntitledClass().ordinal());
		        }
		 		}catch (Exception e) {
		 			DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
				}
		 		
		  return entitledClass;
	}
	
	private boolean validateDateOfRetirement(VisitorModel visitor) 
	{
		Date retrmntDate=visitor.getDateOfRetirement();
		Date date=Calendar.getInstance().getTime();
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,"[validateDateOfRetirement]Retirement Date-"+retrmntDate+"||Today Date-"+date);
		
		return !(retrmntDate!=null && (retrmntDate.after(date)|| date.equals(retrmntDate)));
		
	}


	@Async("pcdaAsyncExecutor")
	public CompletableFuture<VisibleYearModel> getVisibleRequestYear(HttpServletRequest request) {
		
		String trRuleId=Optional.ofNullable(request.getParameter("trRuleId")).orElse("");
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,"[getVisibleRequestYear]:Parameter-Tr Rule Id="+trRuleId);
		
		Set<Integer> serviceTypeSet=new HashSet<>();
		TravelRule travelRule=null;
		if(!trRuleId.isBlank()) {
			travelRule=trRuleService.getTRRuleDetails(trRuleId);
			serviceTypeSet.addAll(getServiceTypeFromTrRule(travelRule));
		}
		
		 boolean isCivilianServiceExist=serviceTypeSet.contains(1);
		 
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,"[getVisibleRequestYear]isCivilianServiceExist-"+isCivilianServiceExist);
		 
		 VisibleYearModel model=null;
		 
		 try{
				
			 if(isCivilianServiceExist){
				 Map<String,String> schemeDetailsMap=getSchemeDetailsMap(travelRule);
				 model=getVisibleYearForCivilianXml(schemeDetailsMap);   
			 }else{
				 model=getVisibleYearXml();
			 }
			 
		 }catch(Exception e){
			 DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
		 }
		
		return CompletableFuture.completedFuture(model);
	}


	private Set<Integer> getServiceTypeFromTrRule(TravelRule travelRule) {

		  Set<Integer> serviceTypeSet=new HashSet<>();
		  try{
			 
			  travelRule.getRuleServices().forEach(obj->
				  serviceTypeSet.add(obj.getServiceType().ordinal())
			  );
			 
		  }catch(Exception e){
			  DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
		  }
		  
		  
		  
		  return serviceTypeSet;
	  
	}
	
	private Map<String,String> getSchemeDetailsMap(TravelRule travelRule) 
	{
		Map<String,String> schemeDetailsMap=new HashMap<>();
		 try 
		 {
			 Optional<TRScheme> schemes= travelRule.getSchemes().stream().findFirst();
			
		    if (schemes.isPresent()) 
		    {
		    	TRScheme trRuleSchemeDtls=schemes.get();
		    	int blockStartYear=trRuleSchemeDtls.getBlockStartYear();
		    	int blockEndYear=trRuleSchemeDtls.getBlockEndYear();
		    	int maxYearsPerBlock=trRuleSchemeDtls.getMaxYearsPerBlock();
		    	int maxYearsPerSubBlock=trRuleSchemeDtls.getMaxYearsPerSubblock();
		    	
		    	String currentBlockYear=blockStartYear+"-"+blockEndYear;
		    	String previousBlockYear=(blockStartYear-maxYearsPerBlock)+"-"+(blockEndYear-maxYearsPerBlock);
		    	String nextBlockYear=(blockStartYear+ maxYearsPerBlock)+"-"+(blockEndYear+maxYearsPerBlock);
		    	
		    	schemeDetailsMap.put(String.valueOf("previousBlockYear"),previousBlockYear);
		    	schemeDetailsMap.put(String.valueOf("currentBlockYear"),currentBlockYear);
		    	schemeDetailsMap.put(String.valueOf("nextBlockYear"),nextBlockYear);
		    	
		    	String currentSubBlockYear="";
		    	String previousSubBlockYear="";
		    	String nextSubBlockYear="";
		    	
		    	String schemeAppliedDate="";
		    	
		    	int subBlockCount=maxYearsPerSubBlock;
		    	if(subBlockCount==1)schemeDetailsMap.put(String.valueOf("SchemeType"),"Yearly");
		    	else schemeDetailsMap.put(String.valueOf("SchemeType"),"BlockYearly");
		    	
		    	Calendar calendar=Calendar.getInstance();
		    	int currYear=calendar.get(Calendar.YEAR);
		    	
		    	if(trRuleSchemeDtls.getSchemeAppliedDate()!=null)
		    	{
		    			schemeAppliedDate=CommonUtil.formatDate(trRuleSchemeDtls.getSchemeAppliedDate(),"dd-MM-yyyy");
		    	    	currentSubBlockYear=currYear+"";
			    		previousSubBlockYear=(currYear- 1)+"";
				    	nextSubBlockYear=(currYear+ 1)+"";
		    	}else
		    	{	
		    		String[] currentBlockYearArr=currentBlockYear.split("-");
		    		String currentBlockSubBlock1=currentBlockYearArr[0]+"-"+(Integer.parseInt(currentBlockYearArr[0])+1);
		    		String currentBlockSubBlock2=(Integer.parseInt(currentBlockYearArr[1])-1)+"-"+currentBlockYearArr[1];
		    		
		    		if(currentBlockSubBlock1.contains(currYear+""))
		    		{
		    			
		    			previousSubBlockYear=(blockStartYear-2)+"-"+(blockStartYear-1);
		    			currentSubBlockYear=currentBlockSubBlock1;
		    			nextSubBlockYear=currentBlockSubBlock2;
		    			
		    	    }else if(currentBlockSubBlock2.contains(currYear+""))
		    	    {
		    			previousSubBlockYear=currentBlockSubBlock1;
		    			currentSubBlockYear=currentBlockSubBlock2;
		    			
		    			nextSubBlockYear=(blockEndYear+1)+"-"+(blockEndYear+2);
		    		}
		    		if(currentBlockYear.equalsIgnoreCase(currentSubBlockYear)){
			    		 schemeDetailsMap.put(String.valueOf("bothBlockAreSame"),"Yes");
			    	}else
			    	{
			    		 schemeDetailsMap.put(String.valueOf("bothBlockAreSame"),"No");
			 	    }
			    	
		    	}
		    	
		    	schemeDetailsMap.put(String.valueOf("previousSubBlockYear"),previousSubBlockYear);
		    	schemeDetailsMap.put(String.valueOf("currentSubBlockYear"),currentSubBlockYear);
		    	schemeDetailsMap.put(String.valueOf("nextSubBlockYear"),nextSubBlockYear);
		    	schemeDetailsMap.put(String.valueOf("schemeAppliedDate"),schemeAppliedDate);
		    }
		 }catch(Exception e){
			 DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class, ":getSchemeDetailsMap()->"+schemeDetailsMap);
		 return schemeDetailsMap;
	}

	public VisibleYearModel getVisibleYearForCivilianXml(Map<String,String> schemeDetailsMap)
	{
		
		 Calendar calendar=Calendar.getInstance();
	     int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	   
	     calendar.add(Calendar.DAY_OF_MONTH, 125);
	     int nextYear=calendar.get(Calendar.YEAR);
	     
	     int previousYear=currentYear-1;
	     
	     String schemeType=schemeDetailsMap.get("SchemeType");
	    
	    
	     VisibleYearModel model=new VisibleYearModel();
	     
	    	 	
	    	 	model.setPreviousBlockYear(schemeDetailsMap.get("previousBlockYear"));
	    	 	model.setCurrentBlockYear(schemeDetailsMap.get("currentBlockYear"));	
	    	 	model.setNextBlockYear(schemeDetailsMap.get("nextBlockYear")); 
					
				if(schemeType.equals("Yearly"))
				{
					
					if(schemeDetailsMap.get("currentSubBlockYear").equals(String.valueOf(currentYear)))
					{
						model.setCurrentSubBlockYear(schemeDetailsMap.get("currentSubBlockYear"));
					}
					
				}else{
					if(schemeDetailsMap.get("previousSubBlockYear").contains(String.valueOf(previousYear)))
					{
						model.setPreviousSubBlockYear(schemeDetailsMap.get("previousSubBlockYear"));
					}
					if(schemeDetailsMap.get("currentSubBlockYear").contains(String.valueOf(currentYear)))
					{
						model.setCurrentSubBlockYear(schemeDetailsMap.get("currentSubBlockYear"));
						
					}
					if(schemeDetailsMap.get("nextSubBlockYear").contains(String.valueOf(nextYear)))
					{
						model.setNextSubBlockYear(schemeDetailsMap.get("nextSubBlockYear"));
					}
				}
				
				model.setBothBlockAreSame(schemeDetailsMap.get("bothBlockAreSame"));
				model.setSchemeType(schemeType);
			
	    	 
	    return model;
	}
	
	 private VisibleYearModel getVisibleYearXml() {
			
		     Calendar calendar=Calendar.getInstance();
		     int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		   
		     calendar.add(Calendar.DAY_OF_MONTH, 125);
		     int nextYear=calendar.get(Calendar.YEAR);
		     int previousYear= currentYear-1;
		     
		     
		     
		     VisibleYearModel yearModel=new VisibleYearModel();
		     yearModel.setYearCurrent(String.valueOf(currentYear));
		     yearModel.setPreviousYear(String.valueOf(previousYear));
		  
		    		if( nextYear > currentYear )
					{
		    			yearModel.setYearNext(String.valueOf(nextYear));
					
					}else{
						yearModel.setYearNext("");
					}
					
			
			return yearModel;
		}


	 @Async("pcdaAsyncExecutor")
	public CompletableFuture<List<CitySearchModel>> getCityList(HttpServletRequest request) {
		
		String cityName=Optional.ofNullable(request.getParameter("city")).orElse("");
		
		
		List<CitySearchModel> city=new ArrayList<>();
		 city=cityServices.getCitySearch(cityName);
		if(city.isEmpty()) {
			CitySearchModel cityModel = new CitySearchModel();
			cityModel.setCityName("City Name Not Exist");
			city.add(cityModel);
			}
		
		
		
		return CompletableFuture.completedFuture(city);
	}


	 @Async("pcdaAsyncExecutor")
	public CompletableFuture<String> getTravelerJournayDate(HttpServletRequest request) {
		
		String name = Optional.ofNullable(request.getParameter("nameArr")).orElse("");
		String travelType = Optional.ofNullable(request.getParameter("travelType")).orElse("");
		String personalNo = Optional.ofNullable(request.getParameter("personalNo")).orElse("");
		String familyName = Optional.ofNullable(request.getParameter("familyName")).orElse("");
		String relationCodeArr = Optional.ofNullable(request.getParameter("relationCodeArr")).orElse("");
		String journeyType = Optional.ofNullable(request.getParameter("journeyType")).orElse("");
		String ltcYear = Optional.ofNullable(request.getParameter("ltcYear")).orElse("");
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class, "[getTravelerJournayDate]name--" + name+" travelType--" + travelType
				+" personalNo--" + personalNo +" familyName--" + familyName +" relationCodeArr--" + relationCodeArr +" journeyType--" + journeyType +" ltcYear--" + ltcYear);

		
		String uri = PcdaConstant.REQUEST_BASE_URL + "/getTravelerJournayDate";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri)
				.queryParam("name", name)
				.queryParam("travelType", travelType)
				.queryParam("personalNo", personalNo)
				.queryParam("familyName", familyName)
				.queryParam("relationCodeArr", relationCodeArr)
				.queryParam("journeyType", journeyType)
				.queryParam("ltcYear", ltcYear)
				.build();

		ResponseEntity<StringResponse> serviceEntity = template.exchange(
				builder.toString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<StringResponse>() {});

		StringResponse response = serviceEntity.getBody();
		String value="";
		if (null != response && response.getErrorCode() == 200) {
			value=response.getResponse();
		}
		
		return CompletableFuture.completedFuture(value);
	}

	 @Async("pcdaAsyncExecutor")
	public CompletableFuture<String> getATTFareRule(String flightKey) {

		String rule="";
		try {
			String url = PcdaConstant.AIR_BOOK_SERVICE+"/att/fareRule?flightKey="+flightKey;
			JrnyFairRuleResponse	response = template.postForObject(url,null, JrnyFairRuleResponse.class);
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class, "ATT FLIGHT RULE RESPONSE :: "+response);
			if(response!=null) {
			rule = response.getResponse().getApiStatus().getResult().getRule();
			}
		}catch(Exception e) {
			DODLog.printStackTrace(e,  JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		return CompletableFuture.completedFuture(rule);
	}

	 @Async("pcdaAsyncExecutor")
	public CompletableFuture<String> getBLFareRule(String flightKey,String domInt) {

		String rule="";
		try {
			String url = PcdaConstant.AIR_BOOK_SERVICE+"/bl/fareRule?flightKey="+flightKey+"&domint="+domInt;
			JrnyFareRuleBLResponse	response = template.postForObject(url,null, JrnyFareRuleBLResponse.class);
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class, "BL FLIGHT RULE RESPONSE :: "+response);
			if(response!=null) {
			rule = response.getResponse().getResult();
			}
		}catch(Exception e) {
			DODLog.printStackTrace(e,  JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		return CompletableFuture.completedFuture(rule);
	}

	/* Method to check station Available on Via-route */
	public List<String> getViaRouteStationsAvailablity(String fromStation, String toStation) {
		List<String> viaRouteStation = new ArrayList<>();
		try {
			String url = PcdaConstant.MASTER_BASE_URL + "/alternateViaRoute/getViaRouteStns?fromStation=" + fromStation + "&toStation=" + toStation;
			ViaRouteStationsModel response = template.getForObject(url, ViaRouteStationsModel.class);
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class, "Via-Route Stations Response :: " + response);
			if (response.getResponseList() != null && !response.getResponseList().isEmpty()) {
				viaRouteStation = response.getResponseList();
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		return viaRouteStation;
	}
	
	public Map<String, String> getSingleTrainRoute(String trainNumber, String fromStationCode, String toStationCode,
			String journeyDate) {

		Map<String, String> boardingMap = new LinkedHashMap<>();

		GetSingleTrainRouteResponse routeResponse = ajaxService
				.getSingleTrainData(CommonUtil.getChangeFormat(journeyDate, "yyyy-MM-dd", "dd-MM-yyyy"), trainNumber);

		if (routeResponse != null && routeResponse.getErrorCode() == 200 && routeResponse.getResponseList() != null
				&& !routeResponse.getResponseList().isEmpty()) {

			List<GetSingleTrainRouteModel> modelList = routeResponse.getResponseList();
			int fromStationIndex = IntStream.range(0, modelList.size())
					.filter(i -> modelList.get(i).getStationcode().trim().equalsIgnoreCase(fromStationCode.trim()))
					.findFirst().orElse(-1);

			int toStationIndex = IntStream.range(0, modelList.size())
					.filter(i -> modelList.get(i).getStationcode().trim().equalsIgnoreCase(toStationCode.trim()))
					.findFirst().orElse(-1);

			if (fromStationIndex != -1 && toStationIndex != -1) {

				int startIndex = Math.min(fromStationIndex, toStationIndex);
				int endIndex = Math.max(fromStationIndex, toStationIndex);
				modelList.subList(startIndex+1, endIndex).stream().forEach(
						stationModel -> boardingMap.put(stationModel.getStationcode(), stationModel.getStationname()));
			} else {

				boardingMap.put(fromStationCode, fromStationCode);
			}

		}
		return boardingMap;

	}





}
