package com.pcda.mb.adduser.travelerprofile.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.pcda.common.model.BigIntegerResponse;
import com.pcda.common.model.Category;
import com.pcda.common.model.DODServices;
import com.pcda.common.model.EnumType;
import com.pcda.common.model.GradePayMappingModel;
import com.pcda.common.model.GradePayRankModel;
import com.pcda.common.model.Level;
import com.pcda.common.model.LevelEntitlementModel;
import com.pcda.common.model.Location;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.OfficesResponse;
import com.pcda.common.model.PAOMapResponse;
import com.pcda.common.model.PAOMappingModel;
import com.pcda.common.model.PersonalNoPrefix;
import com.pcda.common.model.User;
import com.pcda.common.services.AirportServices;
import com.pcda.common.services.CategoryServices;
import com.pcda.common.services.EnumTypeServices;
import com.pcda.common.services.GradePayRankServices;
import com.pcda.common.services.GradePayServices;
import com.pcda.common.services.LevelEntitlementServices;
import com.pcda.common.services.LevelServices;
import com.pcda.common.services.LocationServices;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.PAOMappingServices;
import com.pcda.common.services.PersonalNoPrefixServices;
import com.pcda.common.services.StationServices;
import com.pcda.common.services.UserServices;
import com.pcda.login.model.LoginUser;
import com.pcda.mb.adduser.edittravelerprofile.model.TravelerReqResponse;
import com.pcda.mb.adduser.travelerprofile.model.CDAOAccNoCheckResponse;
import com.pcda.mb.adduser.travelerprofile.model.FamilyDetails;
import com.pcda.mb.adduser.travelerprofile.model.TravelerProfileDTO;
import com.pcda.mb.adduser.travelerprofile.model.TravelerProfileModel;
import com.pcda.mb.adduser.travelerprofile.model.TravelerProfileReqData;
import com.pcda.mb.adduser.travelerprofile.model.TravelerUser;
import com.pcda.mb.travel.journey.model.StringResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODDATAConstants;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;
import com.pcda.util.ServiceType;
import com.pcda.util.Status;

@Service
public class TravelerProfileService {

	@Autowired
	private GradePayServices gradePayServices;

	@Autowired
	private LevelServices levelServices;

	@Autowired
	private GradePayRankServices gradePayRankServices;

	@Autowired
	private LevelEntitlementServices levelEntitlementServices;

	@Autowired
	private PersonalNoPrefixServices personalNoPrefixServices;

	@Autowired
	private AirportServices airportServices;

	@Autowired
	private StationServices stationServices;

	@Autowired
	private OfficesService officesService;

	@Autowired
	private MasterServices masterServices;

	@Autowired
	private UserServices userServices;
	
	@Autowired
	private CategoryServices categoryServices;

	@Autowired
	private EnumTypeServices enumTypeServices;

	@Autowired
	private LocationServices locationServices;

	@Autowired
	private RestTemplate template;

	// Get Services Based on Service Type
	public List<DODServices> getArmedTravelerServices(LoginUser loginUser, OfficeModel officeModel, TravelerProfileReqData reqData) {
		
		List<DODServices> serviceData = new ArrayList<>();
		List<GradePayRankModel> rankData = new ArrayList<>();

		List<DODServices> dodServices = new ArrayList<>();

		try {
			rankData = gradePayRankServices.getAllGradePayRankWithApproval("1");
			StringBuilder rankExistService = new StringBuilder();		
			if(rankData != null && !rankData.isEmpty()) {
				List<String> serviceRankExist = new ArrayList<>();

			    rankData.forEach(ob -> {
			    	String service = ob.getServiceId();
			    	if (!serviceRankExist.contains(service)) {
			    		serviceRankExist.add(service);
			    		rankExistService.append(rankExistService).append("," + service);
			    	}
			    });

			    reqData.setRankExistService(rankExistService.toString());		//Need to be checked

			    serviceData = masterServices.getServicesByApprovalState("1").stream().filter(src -> src.getArmedForces().name().equalsIgnoreCase("YES")).toList();

				serviceData.forEach(ob -> {

					String serviceName = ob.getServiceName();
					  String serviceId = ob.getServiceId();

					  if (loginUser.getServiceId().equals(serviceId)) {
						  reqData.setRankExistService(Boolean.FALSE.toString());
					  } 
					  if (reqData.getServiceName().equalsIgnoreCase("DSC")) {
						  if(serviceRankExist.contains(ob.getServiceId()) && 
								  (serviceName.equalsIgnoreCase("DSC")||serviceName.equalsIgnoreCase("Army")||serviceName.equalsIgnoreCase("MNS"))) {
					  				dodServices.add(ob);
						  }
					  } else {
						    if(serviceRankExist.contains(ob.getServiceId())) {
						    	String officeName = officeModel.getName();
						    	if(officeName.equalsIgnoreCase("APSRECORDS") || officeName.equalsIgnoreCase("APSDTE") || !serviceName.equalsIgnoreCase("APS")) {
					  				dodServices.add(ob);
						    	}
						    }
					  }
				});
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelerProfileService.class, LogConstant.TRAVELER_PROFILE_LOG_FILE);
		}
		return dodServices;
	}

	// Get category by id
	public Optional<Category> getCategoryById(String categoryId, String serviceId) {
		
		return categoryServices.getCategory(categoryId, serviceId);
		
	}

	// Get Service Based with Service id
	public Optional<DODServices> getService(String serviceId) {
		return masterServices.getServiceByServiceId(serviceId);
	}

	// Get Categories Map Based on Service Id
	public Map<String, String> getCategoriesBasedOnService(String serviceId) {
		List<Category> categoryList = categoryServices.getCategories(serviceId);
		Map<String, String> serviceCategoryMap = new HashMap<>();
		if (!categoryList.isEmpty()) {
	
			serviceCategoryMap = categoryList.stream()
					.collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
			
		}
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class,
				"Categories Map:::::: " + serviceCategoryMap.size());
		return serviceCategoryMap;
	}

	// Map of level Id And list of level Name, dod rank and personal number prefix
	// id w.r.t serviceID and CategoryId
	public Map<String, List<String>> gradePayBasedOnServiceCategory(String serviceId, String categoryId) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class,
				"Get Level Map with service id: " + serviceId + " category id: " + categoryId);
		List<GradePayMappingModel> gradepayList = gradePayServices.getGradePayWithServiceAndCategory(serviceId,
				categoryId);
		Map<String, List<String>> gradePayMap = new HashMap<>();

		List<Level> levelList = levelServices.getLevelByApprovalType("1");
		Map<String, String> levelMap = levelList.stream()
				.collect(Collectors.toMap(Level::getLevelId, Level::getLevelName));

		gradepayList.forEach(e -> e.setLevelName(setLevel(e.getLevelId(), levelMap)));
		gradepayList.forEach(e -> gradePayMap.put(e.getLevelId(), List.of(e.getLevelName(), e.getDodRankId())));
		
		return gradePayMap;
	}

	private String setLevel(String levelId, Map<String, String> levelMap) {
		if (levelMap.containsKey(levelId)) {
			return levelMap.get(levelId);
		} else {
			return "";
		}
	}

	// Get Grade Pay/Rank
	public Optional<GradePayRankModel> getGradePayRank(String dodRankId) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class,
				"Get grade pay rank with rank id: " + dodRankId);
		return gradePayRankServices.getGradePayWithDODRankId(dodRankId);
	}

	// Get Retirement age with service id and level id
	public Integer getRetirementAge(String serviceId, String levelId, String rankId) {
		Integer retirementAge = 0;
		Optional<DODServices> service= masterServices.getServiceByServiceId(serviceId);
		if(service.isPresent()) {
			DODServices dodServices=service.get();
			if(dodServices.getPermissionType().ordinal()==0) {
				
				Optional<GradePayRankModel> rank=gradePayRankServices.getGradePayWithDODRankId(rankId);
				if(rank.isPresent()) {
					retirementAge=rank.get().getRetirementAge();
				}
				
			}else {
				
		List<LevelEntitlementModel> levelEntitlementList = levelEntitlementServices.getAllLevelEntWithApproval("1");
		if (levelEntitlementList != null) {
			Optional<LevelEntitlementModel> entOptional = levelEntitlementList.stream().filter(ent ->
				ent.getServiceId().equals(serviceId) && ent.getLevelId().equals(levelId)).findFirst();
			if (!entOptional.isEmpty()) {
				retirementAge = entOptional.get().getRetirementAge();
			}
		}
			}
		}
		
		
		return retirementAge;
	}

	// Map of Personal Number Prefix
	public Map<String, String> getPersonalNoPrefixMap(String serviceId, String categoryId) {
		
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class,
				"Get Personal Number Prefix with service id: " + serviceId + " and category id: " + categoryId);
		
		List<PersonalNoPrefix> personalNoPrefixList = personalNoPrefixServices.getPersonalNoPrefixWithApprovalType("1");
		
		
		
		return personalNoPrefixList.stream()
				.filter(pno -> pno.getServiceId().equals(serviceId) && pno.getCategoryId().equals(categoryId))
				.collect(Collectors.toMap(PersonalNoPrefix::getPrefixId, PersonalNoPrefix::getPrefix));
	}

	// Get Stations
	public List<String> getStation(String station) {
		List<String> stationList = stationServices.getStation(station);
		
		return stationList;
	}

	// Get Airport
	public List<String> getAirport(String airPortName) {
		return airportServices.getAirport(airPortName);
	}

	// Check Duplicate User Alias
	public Boolean checkDuplicate(String personalNo) {
		Optional<User> mappedUser = userServices.getUserByUserAlias(personalNo);
		
		return mappedUser.isEmpty();
	}

	
	private List<PAOMappingModel> getPaoMappingApproval(String approvalType) {
		DODLog.info(LogConstant.MASTER_SERVICES_LOG_FILE, MasterServices.class, " PAO MAPPING with approval type " + approvalType);

		List<PAOMappingModel> paoModelList = new ArrayList<>();
		ResponseEntity<PAOMapResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/paomapping/allPAOmap/" + approvalType, HttpMethod.GET, null,
				new ParameterizedTypeReference<PAOMapResponse>() {});
		PAOMapResponse paoMapResponse=responseEntity.getBody();

		if(null != paoMapResponse && (paoMapResponse.getErrorCode()==200) && null!= paoMapResponse.getResponseList()) {
			paoModelList =  paoMapResponse.getResponseList();
			paoModelList =paoModelList.stream().filter(e->e.getStatus().equals(Status.ON_LINE)).toList();
			DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, PAOMappingServices.class, "PAO Service :::: " + paoModelList);
		}
		return paoModelList;
	}
	
	// Get PAO Offices List
	public Map<String, List<PAOMappingModel>> getPaoOffice(String serviceId, String categoryId, LoginUser loginUser) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class, "PAO List with service id: " + serviceId + " ; category id: " + categoryId);
		Map<String, String> paoOfficeMap = getPaoOfficesMap();
		List<PAOMappingModel> railPAOOfficeList = new ArrayList<>();
		List<PAOMappingModel> airPAOOfficeList = new ArrayList<>();
		List<PAOMappingModel> paoModelList = getPaoMappingApproval("1");

		if (serviceId.equals(DODDATAConstants.NAVY_SEVICE_ID) && loginUser.getServiceId().equals(DODDATAConstants.NAVY_SEVICE_ID)) {

			Optional<OfficeModel> officeModel = getOfficeByUserId(loginUser.getUserId());
			if (officeModel.isPresent()) {
				if (officeModel.get().getPaoGroupId() != null) {
					Optional<OfficeModel> paoOfficeModel = getOfficeByGroupId(officeModel.get().getPaoGroupId());
					PAOMappingModel mappingModel = new PAOMappingModel();
	               
					if(paoOfficeModel.isPresent()) {
					mappingModel.setName(paoOfficeModel.get().getName());
					mappingModel.setAcuntoficeId(paoOfficeModel.get().getGroupId());
					}

					railPAOOfficeList.add(mappingModel);
				}
				if (officeModel.get().getPaoAirGroupId() != null) {
					Optional<OfficeModel> paoAirOfficeModel = getOfficeByGroupId(officeModel.get().getPaoAirGroupId());
					PAOMappingModel mappingModel = new PAOMappingModel();

					if (paoAirOfficeModel.isPresent()) {
						mappingModel.setName(paoAirOfficeModel.get().getName());
						mappingModel.setAcuntoficeId(paoAirOfficeModel.get().getGroupId());
					}

					airPAOOfficeList.add(mappingModel);
				}
			}

		} else {
			paoModelList=	paoModelList.stream().filter(pao -> pao.getServiceId().equals(serviceId) && pao.getCategoryId().equals(categoryId)).toList();
			
			paoModelList.forEach(e -> e.setName(setGroupName(e.getAcuntoficeId(), paoOfficeMap)));
			
			
			airPAOOfficeList = paoModelList.stream().filter(ob -> ob.getTravelType().ordinal()==1).toList();
			
			railPAOOfficeList = paoModelList.stream().filter(ob -> ob.getTravelType().ordinal()==0).toList();
			
			DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class,"PAO AIR LIST SIZE FOR SERVICE AND CATEGORY ID:::: " + airPAOOfficeList.size()
					+" RAIL SIZE :::: " + railPAOOfficeList.size());
		}

		return Map.of("0", railPAOOfficeList, "1", airPAOOfficeList);
	}

	
	private List<OfficeModel> getOfficesTravel(String officeType, String approvalType) {
		DODLog.info(LogConstant.OFFICES_SERVICES_LOG_FILE, MasterServices.class, "Get " + officeType + " with approval type " + approvalType);

		List<OfficeModel> officesList = null;
		StringJoiner joiner = new StringJoiner("/").add(PcdaConstant.OFFICE_BASE_URL).add("allOffices").add(officeType).add(approvalType);

		ResponseEntity<OfficesResponse> responseEntity = template.exchange(joiner.toString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<OfficesResponse>() {});
		OfficesResponse officesResponse = responseEntity.getBody();

		if (null != officesResponse && (officesResponse.getErrorCode() == 200) && null != officesResponse.getResponseList()) {
			officesList = officesResponse.getResponseList();
		}

		return officesList;
	}
	
	// Get Map of PAO Office Id and Name
	public Map<String, String> getPaoOfficesMap() {
		Map<String, String> paoOfficeMap = new HashMap<>();
		try {
		List<OfficeModel> paoOfficesList = getOfficesTravel("PAO", "1");
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class, "pao Offices List: " + paoOfficesList);
		if(null!=paoOfficesList) {
		paoOfficesList.forEach(e -> paoOfficeMap.put(e.getGroupId(), e.getName()));
		}
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class, "Pao Map: " + paoOfficeMap);
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelerProfileService.class, LogConstant.TRAVELER_PROFILE_LOG_FILE);
		}
		return paoOfficeMap;
	}

	private String setGroupName(String acuntoficeId, Map<String, String> paoMap) {
		if (paoMap.containsKey(acuntoficeId)) {
			return paoMap.get(acuntoficeId);
		} else {
			return "";
		}
	}

	// Get office by user id
	public Optional<OfficeModel> getOfficeByUserId(BigInteger userId) {
			return officesService.getOfficeByUserId(userId);
	}

	// Get office by group id
	public Optional<OfficeModel> getOfficeByGroupId(String groupId) {
			return officesService.getOfficeByGroupId(groupId);
	}

	// Get Enum Map by Name
	public Map<Integer, String> getEnumMap(String enumType) {
		Map<Integer, String> enumMap = null;
		Map<Integer, String> sortedClassTypeMap = null;

		try {
			List<EnumType> enumList = enumTypeServices.getEnumType(enumType);
			DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class, "Enum List::::::::::: " + enumList.size());

			if (enumList != null) {
				enumMap = enumList.stream().collect(Collectors.toMap(EnumType::getCode, EnumType::getValue));
				
				// Sort the map based on values
				sortedClassTypeMap = enumMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (prev, next) -> next,
								LinkedHashMap::new));
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelerProfileService.class, LogConstant.TRAVELER_PROFILE_LOG_FILE);
		}
		
		return sortedClassTypeMap;
	}

	// Get Enum code and value as a String
	public String getEnumAsString (String enumType) {
		String enumString = "";

		try {
			Map<Integer, String> enumMap = getEnumMap(enumType);
			if (enumMap != null) {
			enumMap.remove(0, "Self");
				enumString = enumMap.entrySet().stream()
						.map(entry -> entry.getValue() + "::" + entry.getKey())
						.collect(Collectors.joining(","));
				
				
				
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelerProfileService.class, LogConstant.TRAVELER_PROFILE_LOG_FILE);
		}

		return enumString + ',';
	}

	public Optional<Location> getLocationById(String locationId) {
		return locationServices.getLocationById(locationId);
	}

	// Get Sub Block Of Year
	public String getSubBlockYearOfYear(int year) {
		String subBlockYearOfYear = "";

		if (year % 2 == 0) {
			subBlockYearOfYear = year + "-" + (year + 1);
		} else {
			subBlockYearOfYear = (year - 1) + "-" + year;
		}

		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class, "Get sub block year of: " + year + ": " + subBlockYearOfYear);
		return subBlockYearOfYear;
	}

	public TravelerUser initVariable(TravelerProfileDTO travelerProfileDTO) {
		TravelerUser travelerUser = null;

		try {
			

			travelerUser = new TravelerUser();
			TravelerProfileModel travelerProfile = new TravelerProfileModel();

			travelerUser.setFirstName(CommonUtil.getStringParameter(travelerProfileDTO.getFName(),""));
			travelerUser.setMiddleName(CommonUtil.getStringParameter(travelerProfileDTO.getMName(),""));
			travelerUser.setLastName(CommonUtil.getStringParameter(travelerProfileDTO.getLName(),""));

			StringBuilder travellerId = new StringBuilder();

			travellerId.append(travelerProfileDTO.getAlphaNo());
			travellerId.append(travelerProfileDTO.getPersonalNo());
			travellerId.append(travelerProfileDTO.getChkAlpha());

			travellerId.append(travelerProfileDTO.getAirForceCadetNo());
			travellerId.append(travelerProfileDTO.getCadetNo());
			travellerId.append(travelerProfileDTO.getCadetChkAlpha());
			travellerId.append(travelerProfileDTO.getCourseSerialNo());

			travelerUser.setUserAlias((travellerId.toString()));
			travelerUser.setPersonalNumber(travellerId.toString());
			travelerProfileDTO.setTravllerId(travellerId.toString());

			travelerUser.setDateOfBirth(stringToDate(travelerProfileDTO.getDob()));

		

			String retirementAge="";
			String dateOfRetirement = "";
			String actualDateOfRetirement="";

			retirementAge = travelerProfileDTO.getRetireAge();

			dateOfRetirement = travelerProfileDTO.getDateOfRetirement();

			actualDateOfRetirement = getActualRetirementDate(travelerProfileDTO.getDob(),retirementAge);
			if(null==dateOfRetirement || dateOfRetirement.length() == 0 ||
					(stringToDate(dateOfRetirement) != null && stringToDate(actualDateOfRetirement) != null &&
						stringToDate(dateOfRetirement).after(stringToDate(actualDateOfRetirement)))) {
				dateOfRetirement=actualDateOfRetirement;
			}


			travelerUser.setDateOfRetirement(stringToDate(dateOfRetirement));

			travelerUser.setCategoryId(travelerProfileDTO.getCategoryId());
			travelerUser.setServiceId(travelerProfileDTO.getService());
			travelerUser.setUserServiceId(travelerProfileDTO.getAlternateService());
			travelerUser.setRankId(travelerProfileDTO.getRankId());
			travelerUser.setLevelId(travelerProfileDTO.getLevel());

			travelerUser.setGender(travelerProfileDTO.getGender());
			travelerUser.setEmail(travelerProfileDTO.getEmail());
			travelerUser.setMobileNo(travelerProfileDTO.getMobNo());

			travelerUser.setMaritalStatus(travelerProfileDTO.getMaritalStatus());

			travelerUser.setUnitId(travelerProfileDTO.getLoginVisitorUnitId());

			if(travelerProfileDTO.getAcNo().isBlank() &&
		    		 ((travelerProfileDTO.getAlternateService().equals("100002") && travelerProfileDTO.getCategoryId().equals("100003")) ||
		    				 (travelerProfileDTO.getAlternateService().equals("100016") && travelerProfileDTO.getCategoryId().equals("100030")) )) {
				DODLog.error(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class, "CDAO Account Number Can not be empty for Army And MNS Officer");
				return null;
		    }
			travelerProfile.setAccountNumber(travelerProfileDTO.getAcNo());

			travelerProfile.setCommisioningDate(stringToDate(travelerProfileDTO.getDateOfCom_join()));

			travelerProfile.setPersonalNoBlock(travellerId.toString());
			travelerProfile.setErsPrintName(travelerProfileDTO.getErsPrntName());

			if (travelerProfileDTO.getServiceType().equals(ServiceType.CIVILIAN)) {
				travelerProfile.setAccountOffice(travelerProfileDTO.getLoginVisitorPaoOfficeId());
				travelerProfile.setAirAccountOffice(travelerProfileDTO.getLoginVisitorPaoOfficeId());
			} else if (travelerProfileDTO.getServiceType().equals(ServiceType.ARMED_FORCES)) {
				travelerProfile.setAccountOffice(travelerProfileDTO.getPayAcOff());
				travelerProfile.setAirAccountOffice(travelerProfileDTO.getAirAcOff());

				travelerUser.setPaoVerificationStatus(0);
			}

			if (!CommonUtil.isEmpty(travelerProfileDTO.getCv_fd_used())) {
			travelerProfile.setCvFormDUsed(Integer.parseInt(travelerProfileDTO.getCv_fd_used()));
			}


			travelerProfile.setNrsDutyStation(travelerProfileDTO.getDutyStnNrs());
			travelerProfile.setHomeTown(travelerProfileDTO.getHmeTwnStnPlace());
			travelerProfile.setNrsHomeTown(travelerProfileDTO.getHmeTwnStnNrs());
			travelerProfile.setSprSFA(travelerProfileDTO.getSprPlace());
			travelerProfile.setSprNRS(travelerProfileDTO.getSprNrs());
			travelerProfile.setDutyStationNA(travelerProfileDTO.getDutyStnNa());
			travelerProfile.setHomeTownNA(travelerProfileDTO.getHmeTwnNa());
			travelerProfile.setSprNA(travelerProfileDTO.getSprNa());
			if (travelerProfileDTO.getLoginVisitorServiceId().equalsIgnoreCase("1000019")) {
				travelerProfile.setServiceNo(travelerProfileDTO.getCivilianPersonalNo());
			}

			if (travelerProfileDTO.getChkForSpouseService().equalsIgnoreCase("Y")) {
				travelerProfile.setSpouseGovEmployee(0);
			}

			travelerProfile.setSpousePanNo(travelerProfileDTO.getSpousePanNumber());

			if (travelerProfileDTO.getChkForSuspension().equalsIgnoreCase("Y")) {
				travelerProfile.setOnSuspension(0);
			}

			if (travelerProfileDTO.getChkForAbandoned().equalsIgnoreCase("Y")) {
				travelerProfile.setAbandoned(0);
			}

			if (travelerProfileDTO.getIsLtcAvailCurrentYear().equalsIgnoreCase("Y")) {
				travelerProfile.setLtcCurrentYear(travelerProfileDTO.getCurrentYear());
			}
			
			if (travelerProfileDTO.getIsLtcAvailPreviousYear().equalsIgnoreCase("Y")) {
				travelerProfile.setLtcPreviousYear(travelerProfileDTO.getLtcAvailPreviousYear());
			}

			if (travelerProfileDTO.getIsLtcAvailCurrentSubBlock().equalsIgnoreCase("Y")) {
				travelerProfile.setLtcCurrentSubBlock(travelerProfileDTO.getCurrentSubBlockYear());
			}

			if (travelerProfileDTO.getIsLtcAvailPreviousSubBlock().equalsIgnoreCase("Y")) {
				travelerProfile.setLtcPreviousSubBlock(travelerProfileDTO.getPreviousSubBlockYear());
			}

			travelerUser.setBankAccountNumber("");
			travelerUser.setIfscCode("");
			travelerUser.setLoginUserId(travelerProfileDTO.getLoginUserId());

			travelerProfile.setModifiedBy(travelerProfileDTO.getLoginUserId());

			List<FamilyDetails> familyDetails = new ArrayList<>();
			
			IntStream.range(0, travelerProfileDTO.getLastRowIndex()).forEach(index -> {
				FamilyDetails familyDtls = new FamilyDetails();

				String relation = "";
				if (travelerProfileDTO.getMemRelation() != null && travelerProfileDTO.getMemRelation().size() > index && 
						! travelerProfileDTO.getMemRelation().get(index).isBlank()) {
						 relation = travelerProfileDTO.getMemRelation().get(index);
				}

				Boolean checkStatus = true;
				String childrenDateOfBirth = "";
				String isDependant = "";
				
				
				if (travelerProfileDTO.getIsDepen() != null && travelerProfileDTO.getIsDepen().size() > index) {
					isDependant = Optional.ofNullable(travelerProfileDTO.getIsDepen().get(index)).orElse("");
				}

				Date memDob = stringToDate(travelerProfileDTO.getMemdob().get(index));
				Date compDate = stringToDate(DODDATAConstants.CHILD_DEPENDENT_DATE);
				
				
				if(relation.equals("2") || relation.equals("3") || relation.equals("4") ||  (relation.equals("5") && !isDependant.equals("0")) && memDob.after(compDate)){ 
					  checkStatus=false;
					  childrenDateOfBirth = childrenDateOfBirth+travelerProfileDTO.getMemdob().get(index)+"::"+index+",";
					
					  if (travelerProfileDTO.getChildHostelNRS() != null && travelerProfileDTO.getChildHostelNRS().size() > index ) {
							familyDtls.setHostelNRS(travelerProfileDTO.getChildHostelNRS().get(index));
						}
					  
						if (travelerProfileDTO.getChildHostelNAP() != null && travelerProfileDTO.getChildHostelNAP().size() > index ) {
							familyDtls.setHostelNAP(travelerProfileDTO.getChildHostelNAP().get(index));
						}
				}

				
				String[] dobStrArr = childrenDateOfBirth.split(",");
				List<Date> dateArray = new ArrayList<>();
				 String stringFormat = "dd/MM/yyyy";
				 SimpleDateFormat dateFormat = new SimpleDateFormat();
				 dateFormat.applyPattern(stringFormat);
				if(childrenDateOfBirth.trim().length()>1) {
					for (int i = 0; i < dobStrArr.length; i++) 
					{
						try {
							dateArray.add(dateFormat.parse(dobStrArr[i].split("::")[0]));
						} catch (ParseException e) {
							DODLog.printStackTrace(e, TravelerProfileService.class, LogConstant.TRAVELER_PROFILE_LOG_FILE);
						}
					}
				}

				Collections.sort(dateArray);

				if(!isDependant.equals("1") && !checkStatus.booleanValue() ) {
					
					if (relation.equals("2") || relation.equals("3") || relation.equals("4") || relation.equals("5")) {
						if(dateArray.size()==1 && (dateArray.get(0).equals(memDob))){
							familyDtls.setStatus(1);
						}
						else if (dateArray.size()>1 && (dateArray.get(0).equals(memDob) || dateArray.get(1).equals(memDob))){
							familyDtls.setStatus(1);
						}
						else {
							familyDtls.setStatus(0);
						}
					} else {
						familyDtls.setStatus(1);
					}
				}

				
				if (travelerProfileDTO.getFirstmemberName() != null && travelerProfileDTO.getFirstmemberName().size() > index) {
					familyDtls.setFirstName(CommonUtil.getStringParameter(travelerProfileDTO.getFirstmemberName().get(index),""));
				}
				if (travelerProfileDTO.getMiddlememberName() != null && travelerProfileDTO.getMiddlememberName().size() > index) {
					familyDtls.setMiddleName(CommonUtil.getStringParameter(travelerProfileDTO.getMiddlememberName().get(index),""));
				}
				if (travelerProfileDTO.getLastmemberName() != null && travelerProfileDTO.getLastmemberName().size() > index) {
					familyDtls.setLastName(CommonUtil.getStringParameter(travelerProfileDTO.getLastmemberName().get(index),""));
				}


				if (!CommonUtil.isEmpty(travelerProfileDTO.getMemGender().get(index))) {
				familyDtls.setGender(Integer.parseInt(travelerProfileDTO.getMemGender().get(index)));
				}

				if (!relation.isBlank()) {
					familyDtls.setRelation(Integer.parseInt(relation));
				}

				if (!CommonUtil.isEmpty(travelerProfileDTO.getMemMaritalStatus().get(index))) {
				familyDtls.setMaritalStatus(Integer.parseInt(travelerProfileDTO.getMemMaritalStatus().get(index)));
				}

				if (stringToDate(travelerProfileDTO.getMemdob().get(index)) != null) {
					familyDtls.setDob(memDob);
				}
				if (travelerProfileDTO.getDoIIDate().size() > index && !travelerProfileDTO.getDoIIDate().get(index).isBlank() && stringToDate(travelerProfileDTO.getDoIIDate().get(index)) != null) {
					familyDtls.setPartDate(stringToDate(travelerProfileDTO.getDoIIDate().get(index)));
				}
				
				if (travelerProfileDTO.getDoIIPartNo() !=null && travelerProfileDTO.getDoIIPartNo().size() > index) {
				familyDtls.setPartNo(travelerProfileDTO.getDoIIPartNo().get(index));
				}
				

				if (travelerProfileDTO.getMemReson() != null && travelerProfileDTO.getMemReson().size() > index) {
					familyDtls.setReason(CommonUtil.getStringParameter(travelerProfileDTO.getMemReson().get(index),""));
				}

				familyDtls.setErsPrintName(travelerProfileDTO.getErsPrintName().get(index));



				familyDtls.setModifiedBy(travelerProfileDTO.getLoginUserId());
				familyDtls.setCreatedBy(travelerProfileDTO.getLoginUserId());

				familyDetails.add(familyDtls);

			});



			travelerUser.setFamilyDetails(familyDetails);
			travelerUser.setTravelerProfile(travelerProfile);

		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelerProfileService.class, LogConstant.TRAVELER_PROFILE_LOG_FILE);
			return null;
		}
		
		return travelerUser;
	}

	public String getActualRetirementDate(String dateOfBirth,String retirementAge) {
		String actualRetirementDate="";
		
		if(dateOfBirth!=null && dateOfBirth.length()>0 && retirementAge!=null && retirementAge.length()>0)
		{
			
			int  rAge=Integer.parseInt(retirementAge);
			String[] dobArr=dateOfBirth.split("/");
	       	int dobYear=Integer.parseInt(dobArr[2]);
	    	int dobMonth=Integer.parseInt(dobArr[1]);
	    	int retirementYear=rAge+dobYear; 
	    	int birthMonth=dobMonth;
	    	  
	    	int birthDay=Integer.parseInt(dobArr[0]);
	    	
	    	if(birthMonth==1 && birthDay==1)
	    	{
	    		retirementYear=retirementYear-1;
		    	birthMonth=12;
		    	birthDay=31;
	    	}
	    	
	    	if(birthMonth!=1 && birthDay==1)
	    	{
	    		if(birthMonth==5||birthMonth==7||birthMonth==10||birthMonth==12)
	    			birthDay=30;
	    	
		    	if(birthMonth==3)
		    	{
			    	if(retirementYear%4==0)
			    		birthDay=29;
			    	else
			    		birthDay=28;
		    	}
	    	
	    	
	    		if(birthMonth==2||birthMonth==4||birthMonth==8||birthMonth==6||birthMonth==9||birthMonth==11)
	    			birthDay=31;
	    	
	    		birthMonth=birthMonth-1;
	    	}
	    	
	    	
	    	if(birthDay!=1)
	    	{
		    	if(birthMonth==1||birthMonth==3||birthMonth==5||birthMonth==7||birthMonth==8||birthMonth==10||birthMonth==12)
		    		birthDay=31;
		   	
		   		if(birthMonth==2)
		    	{
			    	if(retirementYear%4==0)
			    		birthDay=29;
			    	else
			    		birthDay=28;
		    	}
		    	
		    	if(birthMonth==4||birthMonth==6||birthMonth==9||birthMonth==11)
		    		birthDay=30;
		    
	    	}
	    	actualRetirementDate=birthDay+"/"+birthMonth+"/"+retirementYear;
	    	
	      }
	    	
	  
		return actualRetirementDate;
	}

	private Date stringToDate(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			if (!dateString.isBlank()) {
				date = formatter.parse(dateString);
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelerProfileService.class, LogConstant.TRAVELER_PROFILE_LOG_FILE);
			return null;
		}
		return date;
	}

	public Map<Boolean, String> saveTraveler(TravelerUser travelerUser) {
		Map<Boolean, String> resultMap = new HashMap<>();
		try {
			checkedDateOfEnrollement(travelerUser.getTravelerProfile());

			ResponseEntity<BigIntegerResponse> response = template.postForEntity(PcdaConstant.USER_BASE_URL + "/save", travelerUser, BigIntegerResponse.class);
			BigIntegerResponse travelerUserResponse = response.getBody();
			DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class, "Traveler User Response::::: " + travelerUserResponse);
			if (travelerUserResponse != null) {
				
				if (travelerUserResponse.getErrorCode() == 200) {
					resultMap.put(true, "Traveler saved");
					return resultMap;
				} else {
					resultMap.put(false, "Failed to save Traveler");
					return resultMap;
				}
			} else {
				
				resultMap.put(false, "Unable To Save Traveler");
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelerProfileService.class, LogConstant.TRAVELER_PROFILE_LOG_FILE);
			resultMap.clear();
			resultMap.put(false, "Unable To Save Traveler");
		}
		
		
		return resultMap;
	}

	private void checkedDateOfEnrollement(TravelerProfileModel travelerProfileModel) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(travelerProfileModel.getCommisioningDate());
		int enrollementYear = cal.get(Calendar.YEAR);
		
	     int currYear = Calendar.getInstance().get(Calendar.YEAR);
	     DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class, "currYear "+currYear + " enrollementYear" +enrollementYear);
		if (enrollementYear >= currYear) {
			travelerProfileModel.setLtcPreviousYear(String.valueOf(currYear - 1));

		}

	}

	public TravelerReqResponse getTravelerProfile(String personalNo, String officeId) {
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class, "[getTravelerProfile] ## Get user with personal no: " + personalNo + "; officeId:" + officeId);

		TravelerReqResponse response=null;
		
		try {
		String uri = PcdaConstant.EDIT_TRAVELLER_BASE_URL + "/getProfileViewData";
		UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("personalNo", personalNo).queryParam("officeId", officeId).build();

		ResponseEntity<TravelerReqResponse> responseEntity = template.exchange(
				builder.toString(), HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

		
		response=responseEntity.getBody();
} catch (Exception e) {
	DODLog.printStackTrace(e, TravelerProfileService.class, LogConstant.TRAVELER_PROFILE_LOG_FILE);
	
}
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class, "[getTravelerProfile] ## Traveler Req Response " + response);
		return response;
	}

	public String checkDuplicateCDAOAccountNo(String cdaoAccountNo) {
	  	String validateResponse="NOT";
	  	try {
		ResponseEntity<CDAOAccNoCheckResponse> response = template.exchange(PcdaConstant.TRAVELLER_AJAX_URL + "/checkDuplicateCDAOAccountNo/" + cdaoAccountNo, HttpMethod.GET
				, null, CDAOAccNoCheckResponse.class);
		CDAOAccNoCheckResponse checkResponse = response.getBody();
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class, "Duplicate CDAO Account No Response: " + checkResponse);
		if (checkResponse != null && checkResponse.getErrorCode() == 200 && checkResponse.getResponse() != null) {
			validateResponse = checkResponse.getResponse();
		}
		
	  	} catch (Exception e) {
	  		DODLog.printStackTrace(e, TravelerProfileService.class, LogConstant.TRAVELER_PROFILE_LOG_FILE);
	  		
	  	}
		return validateResponse;
	}

	

	public String chkDupNavyCivilianServiceNo(String navyCivilianServiceNo) {
		String validateResponse="NOT";
	  	try {
		ResponseEntity<StringResponse> response = template.exchange(PcdaConstant.TRAVELLER_AJAX_URL + "/chkDupNavyCivilianServiceNo/" + navyCivilianServiceNo, HttpMethod.GET
				, null, StringResponse.class);
		StringResponse checkResponse = response.getBody();
		if (checkResponse != null && checkResponse.getErrorCode() == 200 && checkResponse.getResponse() != null) {
			validateResponse = checkResponse.getResponse();
		}
		DODLog.info(LogConstant.TRAVELER_PROFILE_LOG_FILE, TravelerProfileService.class, "validateResponse: " + validateResponse);
	  	} catch (Exception e) {
	  		DODLog.printStackTrace(e, TravelerProfileService.class, LogConstant.TRAVELER_PROFILE_LOG_FILE);
	  		
	  	}
		return validateResponse;
	}
	}


