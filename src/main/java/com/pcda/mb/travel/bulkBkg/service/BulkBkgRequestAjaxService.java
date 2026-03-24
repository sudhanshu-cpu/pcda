package com.pcda.mb.travel.bulkBkg.service;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import com.pcda.mb.travel.bulkBkg.model.EligibleMemberBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.model.PartyVisitorBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.model.TRJourneyBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.model.TRQuestionBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.model.TRRuleDetailsBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.model.TRRulesDataBulkBkg;
import com.pcda.mb.travel.bulkBkg.model.TravellerDetailsBulkBkgModel;
import com.pcda.mb.travel.journey.model.IntegerResponse;
import com.pcda.mb.travel.journey.model.JrnyFairRuleResponse;
import com.pcda.mb.travel.journey.model.JrnyFareRuleBLResponse;
import com.pcda.mb.travel.journey.model.StringResponse;
import com.pcda.mb.travel.journey.service.JourneyRequestAjaxService;
import com.pcda.mb.travel.journey.service.TravelIdAndRuleService;
import com.pcda.mb.travel.journey.service.TravelInfoService;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;
import com.pcda.util.UserStatus;

import jakarta.servlet.http.HttpServletRequest;
@Service
public class BulkBkgRequestAjaxService {
	
	@Autowired
	private MasterServices masterServices;
	
	@Autowired
	private GradePayRankServices rankServices;
	
	@Autowired
	private TravellerDetailsBulkBkgService bulkBkgDetailsService;
	@Autowired
	private UserServices userServices;
	@Autowired
	private TRRuleService trRuleService;
	@Autowired
	private TravelTypeServices travelTypeServices;
	
	@Autowired
	private CategoryServices categoryServices; 
	@Autowired
	private LocationServices locationServices;
	
	@Autowired
	private EnumTypeServices enumService;
	@Autowired
	private TravelIdAndRulesServiceBulkBkg rulesBulkBkgService;
	
	@Autowired
	private OfficesService officesService;
	
	@Autowired
	private CityServices cityServices;
	
	@Autowired
	private RestTemplate template;

	public TravellerDetailsBulkBkgModel getTravellerDetailsBulkBkg(HttpServletRequest request) {
		
		TravellerDetailsBulkBkgModel details= new TravellerDetailsBulkBkgModel();
		try {
			
		String personalNo =  request.getParameter("personalNo");
		Optional<String> userAlias=Optional.ofNullable(personalNo);
		
		
		 details = bulkBkgDetailsService.getTravellerDetails(userAlias, request);
		EditUserModel user= userServices.getUserByUserId(details.getUserID());
		if(user!= null&& user.getProfileStatus()!=null && user.getProfileStatus().equalsIgnoreCase("requested") && user.getUserEditType().contentEquals("edit")) {
			details.setMessage("editMode");
		}
		
		
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class, "  getTravellerDetails = "+details);
		return details;
		
	}

	public List<TRRulesDataBulkBkg> getAllTRforTravelGroupBulkBkg(HttpServletRequest request) {
		List<TRRulesDataBulkBkg> trRulesDatas=new ArrayList<>();
		try {
		String travelType= Optional.ofNullable(request.getParameter("travelType")).orElse(""); 
		String enumCode=Optional.ofNullable(request.getParameter("enumCode")).orElse("-1");  
		String serviceType=Optional.ofNullable(request.getParameter("serviceType")).orElse("-1"); 
		String serviceId=Optional.ofNullable(request.getParameter("serviceId")).orElse(""); 
		String categoryId=Optional.ofNullable(request.getParameter("categoryId")).orElse(""); 
		String requestType=Optional.ofNullable(request.getParameter("requestType")).orElse(""); 
		
		
		trRulesDatas=	rulesBulkBkgService.getTRforTravelGroup(travelType, enumCode, serviceType, serviceId, categoryId, requestType);
		}catch (Exception e) {
			DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
		return trRulesDatas;
		
	}

	public TRRuleDetailsBulkBkgModel getTravelRuleDetailsBulkBkg(HttpServletRequest request) {
		
    String trRuleID=Optional.ofNullable(request.getParameter("trRuleID")).orElse("");
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelIdAndRuleService.class,":trRuleID=="+trRuleID);
		
		TravelRule travelRule=trRuleService.getTRRuleDetails(trRuleID);
		
		TRRuleDetailsBulkBkgModel ruleDetailsModel=new TRRuleDetailsBulkBkgModel();
		
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
	
        private void setJourneyAllowed(Integer jrnyCountAllowed,TRRuleDetailsBulkBkgModel ruleDetailsModel) {
		
		if(jrnyCountAllowed.intValue()==-1) {
			ruleDetailsModel.setJourneyCountTR("Unlimited");
		}else {
			ruleDetailsModel.setJourneyCountTR(String.valueOf(jrnyCountAllowed));
		}
	}
        
        private void setEligibleCategories(TravelRule travelRule, TRRuleDetailsBulkBkgModel ruleDetailsModel) {
    		
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
        private void setEligbleFromToStations(TravelRule travelRule, TRRuleDetailsBulkBkgModel ruleDetailsModel) {
    		
    		List<TRJourneyBulkBkgModel> journeys=new ArrayList<>();
    		if(travelRule.getJourneys()!=null && !travelRule.getJourneys().isEmpty()) {
    		
    		travelRule.getJourneys().forEach(obj->{
    			
    			if(obj.getJrnyType().ordinal()==0) {
    				TRJourneyBulkBkgModel journeyModel=new TRJourneyBulkBkgModel();
    				if(obj.getFromToType().ordinal()==0) {
    					journeyModel.setFromStnOnward(obj.getJrnyStation().getDisplayValue());
    				}else {
    					journeyModel.setToStnOnward(obj.getJrnyStation().getDisplayValue());
    				}
    				
    				journeys.add(journeyModel);
    				
    			}else if(obj.getJrnyType().ordinal()==1) {
    				TRJourneyBulkBkgModel journeyModel=new TRJourneyBulkBkgModel();
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

        private void setEligbleMembers(TravelRule travelRule, TRRuleDetailsBulkBkgModel ruleDetailsModel) {
    		
    		List<EligibleMemberBulkBkgModel> eligbleMembers=new ArrayList<>();
    		if(	travelRule.getEligibilityMembers()!=null&& 	!travelRule.getEligibilityMembers().isEmpty()) {
    		
    		travelRule.getEligibilityMembers().forEach(obj->{
    			
    			EligibleMemberBulkBkgModel membersModel=new EligibleMemberBulkBkgModel();
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
	
        private void setQuestionsDtls(TravelRule travelRule, TRRuleDetailsBulkBkgModel ruleDetailsModel) {
    		
    		List<TRQuestionBulkBkgModel> questions=new ArrayList<>();
    		
    		if(travelRule.getQuestions()!=null && !travelRule.getQuestions().isEmpty()) {
    		
    			
    		travelRule.getQuestions().forEach(obj->{
    			
    			TRQuestionBulkBkgModel questionsModel=new TRQuestionBulkBkgModel();
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
        private void setEligbleLocation(TravelRule travelRule, TRRuleDetailsBulkBkgModel ruleDetailsModel) {
    		
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

		public Map<Integer, String> getTravelGroupBulkBkg(HttpServletRequest request) {
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
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class, "  travelGroups  = "+travelGroups);
			return travelGroups;
			
		}
		
		private Map<Integer, String> getTravelGroupByTravelId() {
			
			
			Map<Integer, String> sortedTravelGroupMap = new HashMap<>();
			try {
			List<EnumType> travelGroup=enumService.getEnumType("TRAVEL_GROUP_TYPE");
			sortedTravelGroupMap = travelGroup.stream().filter(obj -> !obj.getValue().startsWith("TA-"))
					.collect(Collectors.toMap(EnumType::getCode, EnumType::getValue));
			
			sortedTravelGroupMap = sortedTravelGroupMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,
					"######## Get Service Type for Travel Group MAP #######"+sortedTravelGroupMap.size());
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
			
			return sortedTravelGroupMap;
		}
		
		private Map<Integer, String> getCivilianTravelGroup() {
			
			
			Map<Integer, String> sortedTravelGroupMap = new HashMap<>();
			try {
			List<EnumType> travelGroup = enumService.getEnumType("TRAVEL_GROUP_TYPE");
			
			sortedTravelGroupMap = travelGroup.stream().filter(obj -> !obj.getValue().startsWith("TA-"))
					.collect(Collectors.toMap(EnumType::getCode, EnumType::getValue));
			
			sortedTravelGroupMap = sortedTravelGroupMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,
					" ########### Get Service Type for Civilian Travel Group##########"+sortedTravelGroupMap.size());
			
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
			return sortedTravelGroupMap;
		}

		public List<TRRulesDataBulkBkg> getAllTRforTravelTypeBulkBkg(HttpServletRequest request) {
			List<TRRulesDataBulkBkg>  trRuleList =new ArrayList<>();
			try {
			String travelType=Optional.ofNullable(request.getParameter("travelType")).orElse("");
			String serviceType=Optional.ofNullable(request.getParameter("serviceType")).orElse("-1");
			String serviceId=Optional.ofNullable(request.getParameter("serviceId")).orElse("");
			String categoryId=Optional.ofNullable(request.getParameter("categoryId")).orElse("");
			String requestType=Optional.ofNullable(request.getParameter("requestType")).orElse("");
			
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,"travel type id=="+travelType+"||serviceType="+serviceType+"||serviceId="+serviceId+"||categoryId="+categoryId+
					"||requestType="+requestType);
			
			trRuleList=	rulesBulkBkgService.getTRforTravelTypeBulkBkg(travelType, serviceType, serviceId, categoryId, requestType);
			} catch (Exception e) {
				DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,
					"######## trRuleList #######"+trRuleList);
			return trRuleList;
			
		}
		
		public PartyVisitorBulkBkgModel getPartyVisitor(HttpServletRequest request) {
			
			String userAlias= Optional.ofNullable(request.getParameter("personalNo")).orElse(""); 
			String isPartyHigherClassAllowed=CommonUtil.getStringParameter(request.getParameter("isPartyHigherClassAllowed"),"No");  
			String selEntitledClass=Optional.ofNullable(request.getParameter("selEntitledClass")).orElse("100"); 
			String travelerUnitName=Optional.ofNullable(request.getParameter("travelerUnitName")).orElse(""); 
			
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,"getPartyVisitor:userAlias="+userAlias+"||isPartyHigherClassAllowed="+isPartyHigherClassAllowed+
					"||selEntitledClass="+selEntitledClass+"||travelerUnitName="+travelerUnitName);
			
			PartyVisitorBulkBkgModel partyVisitorModel=new PartyVisitorBulkBkgModel();
			if(selEntitledClass==null || selEntitledClass.isBlank()||selEntitledClass.isEmpty()||selEntitledClass.equals("")) {
				partyVisitorModel.setMessage("Kindly Select Air Entitlement first.");
				return partyVisitorModel;
			}
			int selEntitledClassInt=Integer.parseInt(selEntitledClass);
			
			
			if (userAlias.equals("")) {
				partyVisitorModel.setMessage("Enter Personal No.");
				 return partyVisitorModel;
			 }
			
			Optional<VisitorModel> user= userServices.getCompleteUser(userAlias);
			if(user.isEmpty()) {
				partyVisitorModel.setMessage("Traveler Not found.");
				return partyVisitorModel;
			}
			
			if(user.isPresent()) {
				VisitorModel travler=user.get();
				
				if(travler.getApprovalState().ordinal()!=1) {
					partyVisitorModel.setMessage("Traveler is Not Approved, First Approve The Traveler");
					return partyVisitorModel;
				}
				Optional<OfficeModel> officeModel= officesService.getOfficeByUserId(travler.getUserId());
				if(officeModel.isPresent()) {
					
					String groupName=officeModel.get().getName();
					if(!groupName.equals(travelerUnitName))
					 {
						 partyVisitorModel.setMessage("Master traveler and party dependent traveler are not a part of same unit");
						 return partyVisitorModel;
						 
					 }
				}else {
					 partyVisitorModel.setMessage("Traveler is not a part of Unit");
					 return partyVisitorModel;
				}
				
				int  travelerEntitledClass=getTravelerEntitiledClass(travler, partyVisitorModel);
				
				
				
				if(travelerEntitledClass==100){
					partyVisitorModel.setMessage("Traveler Does Not Have Entitled Class OR Rank");
					return partyVisitorModel;
				}else if(isPartyHigherClassAllowed.equalsIgnoreCase("No") && travelerEntitledClass>selEntitledClassInt){
						
					partyVisitorModel.setMessage("Traveler Is Not Allowed To Travel On Selected Journey Class As Per TR Rule");
					return partyVisitorModel;
					
				}
				
				
				
				if (travler.getUserStatus().equals(UserStatus.INACTIVE.name())) {
					
					 partyVisitorModel.setMessage("This Personal No. marked as non effective.");
					 return partyVisitorModel;
				}
				
				if(!isPaoVarified(travler)) {
					 partyVisitorModel.setMessage("Traveler is not verified by PAO. Please verify by PAO.");
					 return partyVisitorModel;
				}
				
				
				if(!checkUserCDAOAccount(travler)) {
					 partyVisitorModel.setMessage("CDAO Account Number not updated in your profile. Please update before process your request.");
					 return partyVisitorModel;
				}
				
				if(travler.getRequestBlock().ordinal()==0) {
					 partyVisitorModel.setMessage(travler.getRequestBlockRemark());
					 return partyVisitorModel;
					
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
				 return partyVisitorModel;
			}
			
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,"getPartyVisitor:partyVisitorModel="+partyVisitorModel.toString());
			return partyVisitorModel;
		}
		
		private boolean checkUserCDAOAccount(VisitorModel travler) {
			boolean isCDAOValidate = true;

			try {
				String userService = travler.getUserServiceId();
				String category = travler.getCategoryId();

				if ((userService.equals("100002") && category.equals("100003"))
						|| (userService.equals("100016") && category.equals("100030"))) {

					String accNo = travler.getTravelerProfile().getAccountNumber();

					if (null == accNo || "".equals(accNo) || "null".equals(accNo)) {
						isCDAOValidate = false;
					}

				}
			} catch (Exception e) {

				DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
			return isCDAOValidate;
		}
		
		
		private boolean isPaoVarified(VisitorModel travler) {
			
			boolean isUserVerifiedByPAO=true;
			
			
				try {
					String userService=travler.getUserServiceId();
					String category=travler.getCategoryId();
					Integer userVerifyStatus=travler.getPaoVerificationStatus().ordinal();
					

					
					if((userService.equals("100002") && category.equals("100003")) ||
							(userService.equals("100016") && category.equals("100030"))) {
						
						List<Integer> verifyStatus=new ArrayList<>();
						verifyStatus.add(2);
						verifyStatus.add(3);
						verifyStatus.add(5);
						verifyStatus.add(7);
						verifyStatus.add(8);
						
						if(verifyStatus.contains(userVerifyStatus)) {isUserVerifiedByPAO=false;}
					}
				} catch (Exception e) {
					DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
					
				}
			
			
			
			
			return isUserVerifiedByPAO;
			
		}

		private int getTravelerEntitiledClass(VisitorModel travler, PartyVisitorBulkBkgModel partyVisitorModel) {

			int entitledClass = 100;

			try {

				if (getCPCPermission(travler.getUserServiceId()) == 1) {
					List<Integer> entitleClass = getEntitledClassFromLevelEntitlement(travler.getLevelId(),
							travler.getUserServiceId(), "100002");
					if (entitleClass.size() == 2) {
						partyVisitorModel.setHighestEntitledClass(entitleClass.get(1));
						partyVisitorModel.setRankId(travler.getRankId());
						if(entitleClass.get(1)>-1) {
						entitledClass=entitleClass.get(1);
						}
						
							
					}
				} else {
					Optional<GradePayRankModel> rankData = rankServices.getGradePayWithDODRankId(travler.getRankId());
					if (rankData.isPresent()) {
						entitledClass = rankData.get().getHighestAirEntitledClass().ordinal();
						partyVisitorModel.setRankName(rankData.get().getRankName());
						partyVisitorModel.setRankId(rankData.get().getDodRankId());
						partyVisitorModel
								.setHighestEntitledClass(rankData.get().getHighestAirEntitledClass().ordinal());
					}
				}

			} catch (Exception e) {
				DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
			}

			return entitledClass;
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
		
		private boolean validateDateOfRetirement(VisitorModel visitor) 
		{
			Date retrmntDate=visitor.getDateOfRetirement();
			Date date=Calendar.getInstance().getTime();
			
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,"[validateDateOfRetirement]Retirement Date-"+retrmntDate+"||Today Date-"+date);
			
			return !(retrmntDate!=null && (retrmntDate.after(date)|| date.equals(retrmntDate)));
			
		}
		
		public List<CitySearchModel> getCityList(HttpServletRequest request) {
			
			String cityName=Optional.ofNullable(request.getParameter("city")).orElse("");
			
			
			List<CitySearchModel> city=new ArrayList<>();
			 city=cityServices.getCitySearch(cityName);
			if(city.isEmpty()) {
				CitySearchModel cityModel = new CitySearchModel();
				cityModel.setCityName("City Name Not Exist");
				city.add(cityModel);
				}
			
			
			
			return city;
		}
		
		public String getATTFareRule(String flightKey) {

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
			return rule;
		}
		
		public String getBLFareRule(String flightKey,String domInt) {

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
			return rule;
		}
		
		private int getCPCPermission(String serviceId) {
			
			int permissionType = -1;
			try
			{
				Optional<DODServices> service=masterServices.getServiceByServiceId(serviceId);
				if(service.isPresent()) {
					permissionType=service.get().getPermissionType().ordinal();
				}

			}catch(Exception e){
				DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
			
			return permissionType;
		}
	
		
		public String getTravelerJournayDate(HttpServletRequest request) {
			
			String name = Optional.ofNullable(request.getParameter("nameArr")).orElse("");
			String travelType = Optional.ofNullable(request.getParameter("travelType")).orElse("");
			String personalNo = Optional.ofNullable(request.getParameter("personalNo")).orElse("");
			String familyName = Optional.ofNullable(request.getParameter("familyName")).orElse("");
			String relationCodeArr = Optional.ofNullable(request.getParameter("relationCodeArr")).orElse("");
			String journeyType = Optional.ofNullable(request.getParameter("journeyType")).orElse("");
			String ltcYear = Optional.ofNullable(request.getParameter("ltcYear")).orElse("");
			
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class, "[getTravelerJournayDate]name--" + name +" travelType--" + travelType
					+" personalNo--" + personalNo+" familyName--" + familyName +" relationCodeArr--" + relationCodeArr +" journeyType--" + journeyType +" ltcYear--" + ltcYear);
			
			
			String uri = PcdaConstant.REQUEST_BASE_URL + "getTravelerJournayDate";
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
			
			return value;
		}
	

}
