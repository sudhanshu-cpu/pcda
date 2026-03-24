package com.pcda.mb.adduser.changepersonalno.service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.Category;
import com.pcda.common.model.DODServices;
import com.pcda.common.model.EnumType;
import com.pcda.common.model.GradePayMappingModel;
import com.pcda.common.model.GradePayRankModel;
import com.pcda.common.model.Level;
import com.pcda.common.model.LevelEntitlementModel;
import com.pcda.common.model.Location;
import com.pcda.common.model.OfficeModel;
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
import com.pcda.mb.adduser.changepersonalno.model.ChangePNoRequired;
import com.pcda.mb.adduser.changepersonalno.model.ChangePersonalNoPostResponse;
import com.pcda.mb.adduser.changepersonalno.model.ChangePersonalResponse;
import com.pcda.mb.adduser.changepersonalno.model.GetChangePersonalNo;
import com.pcda.mb.adduser.changepersonalno.model.PostChangePersonalNoModel;
import com.pcda.mb.adduser.changepersonalno.model.PostOldPersonalNo;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODDATAConstants;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ChangePersonalNoService {

	@Autowired
	private OfficesService officesService;

	@Autowired
	private MasterServices masterServices;


	@Autowired
	private GradePayServices gradePayServices;

	@Autowired
	private LevelServices levelServices;

	@Autowired
	private GradePayRankServices gradePayRankServices;

	@Autowired
	private LevelEntitlementServices levelEntitlementServices;

	@Autowired
	private PersonalNoPrefixServices  personalNoPrefixServices;

	@Autowired
	private AirportServices airportServices;

	@Autowired
	private StationServices stationServices;

	

	@Autowired
	private UserServices userServices;
	
	@Autowired
	private PAOMappingServices mappingServices;
	
	@Autowired
	private CategoryServices categoryServices;

	@Autowired
	private EnumTypeServices enumTypeServices;

	@Autowired
	private LocationServices locationServices;

	
	
	@Autowired
	private RestTemplate restTemplate;



	// Get Services Based on Service Type
	public List<DODServices> getArmedTravelerServices(LoginUser loginUser, OfficeModel officeModel, ChangePNoRequired changePNoRequired) {

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

			    changePNoRequired.setRankExistService(rankExistService.toString());		

			    serviceData = masterServices.getServicesByApprovalState("1").stream().filter(src -> src.getArmedForces().name().equalsIgnoreCase("YES")).toList();

				serviceData.forEach(ob -> {

					String serviceName = ob.getServiceName();
					  String serviceId = ob.getServiceId();

					  if (loginUser.getServiceId().equals(serviceId)) {
						  changePNoRequired.setRankExistService(Boolean.FALSE.toString());
					  } 
					  if (changePNoRequired.getServiceName()!=null && changePNoRequired.getServiceName().equalsIgnoreCase("DSC")) {
						  if(serviceRankExist.contains(ob.getServiceId()) && 
								  (serviceName.equalsIgnoreCase("DSC")||serviceName.equalsIgnoreCase("Army")||serviceName.equalsIgnoreCase("MNS"))) {
					  				dodServices.add(ob);
						  }
					  } else {
						    if(serviceRankExist.contains(ob.getServiceId())) {
						    	String officeName = officeModel.getName();
						    	if(officeName.equalsIgnoreCase("APSRECORDS") || !serviceName.equalsIgnoreCase("APS")) {
					  				dodServices.add(ob);
						    	}
						    }
					  }
				});
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangePersonalNoService.class, LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
		}
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class," ::[getArmedTravelerServices] CHANGE PNO LOGIN dodServices  :: " + dodServices.size());
		return dodServices;
	}

	// Get Service Based with Service id
	public DODServices getService(String serviceId) {
	
		DODServices dodServices = new DODServices();
		try {
			Optional<DODServices> opt =masterServices.getServiceByServiceId(serviceId);
			
			if(opt.isPresent()) {
				dodServices = opt.get();
			}
			
			return dodServices;
		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangePersonalNoService.class, LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
			return dodServices;
		}
	}

	// Get Categories Map Based on Service Id
	public Map<String, String> getCategoriesBasedOnService(String serviceId) {
		List<Category> categoryList = categoryServices.getCategories(serviceId);
		Map<String, String> serviceCategoryMap = new HashMap<>();
		if (!categoryList.isEmpty()) {

			serviceCategoryMap = categoryList.stream()
					.collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
			DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class,
					"[getCategoriesBasedOnService] SERVICE CATEGORY MAP :: " + serviceCategoryMap.size());
		}
		
		return serviceCategoryMap;
	}

	// Map of level Id And list of level Name, dod rank and personal number prefix
	// id w.r.t serviceID and CategoryId
	public Map<String, List<String>> gradePayBasedOnServiceCategory(String serviceId, String categoryId) {
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
	public GradePayRankModel getGradePayRank(String dodRankId) {
	
		GradePayRankModel model = new GradePayRankModel();
		Optional<GradePayRankModel> opt = gradePayRankServices.getGradePayWithDODRankId(dodRankId);
		 if(opt.isPresent()) {
			 model= opt.get();
		 }
		return model;
	}

	// Get Retirement age with service id and level id
	public Integer getRetirementAge(String serviceId, String levelId) {
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class,
				"[getRetirementAge] serviceId" + serviceId +" levelId "+levelId);
		
		Integer retirementAge = null;
		List<LevelEntitlementModel> levelEntitlementList = levelEntitlementServices.getAllLevelEntWithApproval("1");
		if (levelEntitlementList != null) {
			Optional<LevelEntitlementModel> entOptional = levelEntitlementList.stream().filter(ent ->
				ent.getServiceId().equals(serviceId) && ent.getLevelId().equals(levelId)).findFirst();
			if (!entOptional.isEmpty()) {
				retirementAge = entOptional.get().getRetirementAge();
			}
		}
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class,
				"[getRetirementAge] retirementAge" + retirementAge);
		return retirementAge;
	}

	// Map of Personal Number Prefix
	public Map<String, String> getPersonalNoPrefixMap(String serviceId, String categoryId) {
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class,
				"SERVICE ID AND CATEGORY ID TO GET PNO PREFIX " + serviceId + "," + categoryId);
		List<PersonalNoPrefix> personalNoPrefixList = personalNoPrefixServices.getPersonalNoPrefixWithApprovalType("1");
		return personalNoPrefixList.stream()
				.filter(pno -> pno.getServiceId().equals(serviceId) && pno.getCategoryId().equals(categoryId))
				.collect(Collectors.toMap(PersonalNoPrefix::getPrefixId, PersonalNoPrefix::getPrefix));
	}

	// Get Stations
	public List<String> getStation(String station) {
		List<String> stationList = stationServices.getStation(station);
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class, "STATION AND STATIONLIST :: " + station + "," + stationList);
		return stationList;
	}

	// Get Airport
	public List<String> getAirport(String airPortName) {
		List<String> airPortList = airportServices.getAirport(airPortName);
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class, "AIRPORT NAME AND AIRPORT LIST " + airPortName + " ; " + airPortList);
		return airPortList;
	}

	// Check Duplicate User Alias
	public Boolean checkDuplicate(String personalNo) {
		Optional<User> mappedUser =  userServices.getUserByUserAlias(personalNo);
		
		
		
		return mappedUser.isEmpty();
	}

	// Get PAO Offices List
	public Map<String, List<PAOMappingModel>> getPaoOffice(String serviceId, String categoryId, LoginUser loginUser) {
		
		Map<String, String> paoOfficeMap = getPaoOfficesMap();
		List<PAOMappingModel> railPAOOfficeList = new ArrayList<>();
		List<PAOMappingModel> airPAOOfficeList = new ArrayList<>();
		List<PAOMappingModel> paoModelList = mappingServices.getPaoMappingServiceWithApproval("1");

		if (serviceId.equals(DODDATAConstants.NAVY_SEVICE_ID) && loginUser.getServiceId().equals(DODDATAConstants.NAVY_SEVICE_ID)) {

			OfficeModel officeModel = getOfficeByUserId(loginUser.getUserId());
			if (officeModel.getPaoGroupId() != null) {
				OfficeModel paoOfficeModel = getOfficeByGroupId(officeModel.getPaoGroupId());
				PAOMappingModel mappingModel = new PAOMappingModel();

				mappingModel.setName(paoOfficeModel.getName());
				mappingModel.setAcuntoficeId(paoOfficeModel.getGroupId());

				railPAOOfficeList.add(mappingModel);
			}
			if (officeModel.getPaoAirGroupId() != null) {
				OfficeModel paoAirOfficeModel = getOfficeByGroupId(officeModel.getPaoGroupId());
				PAOMappingModel mappingModel = new PAOMappingModel();

				mappingModel.setName(paoAirOfficeModel.getName());
				mappingModel.setAcuntoficeId(paoAirOfficeModel.getGroupId());

				airPAOOfficeList.add(mappingModel);
			}

		} else {
			paoModelList = paoModelList.stream().filter(pao -> pao.getServiceId().equals(serviceId) && pao.getCategoryId().equals(categoryId)).toList();

			paoModelList.forEach(e -> e.setName(setGroupName(e.getAcuntoficeId(), paoOfficeMap)));

			airPAOOfficeList = paoModelList.stream().filter(ob -> ob.getTravelType().ordinal()==1).toList();
			railPAOOfficeList = paoModelList.stream().filter(ob -> ob.getTravelType().ordinal()==0).toList();
		}

		return Map.of("0", railPAOOfficeList, "1", airPAOOfficeList);
	}

	// Get Map of PAO Office Id and Name
	public Map<String, String> getPaoOfficesMap() {
		List<OfficeModel> paoOfficesList = officesService.getOffices("PAO", "1");
		Map<String, String> paoOfficeMap=new HashMap<>();
		try {
	paoOfficesList.forEach(e -> paoOfficeMap.put(e.getGroupId(), e.getName()));
		
		return paoOfficeMap;
		}catch(Exception e) {
			DODLog.printStackTrace(e, ChangePersonalNoService.class, LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
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
	public OfficeModel getOfficeByUserId(BigInteger userId) {
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class, "[getOfficeByUserId] USERiD ::" +userId);
		OfficeModel officeModel = new OfficeModel();
		try {
			Optional<OfficeModel> opt =officesService.getOfficeByUserId(userId);
			if(opt.isPresent()) {
				officeModel=opt.get();	
			}
			 
		} catch (Exception e) {
			DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class,"[getOfficeByUserId] ## EXCEPTION ##   :: ");
			DODLog.printStackTrace(e, ChangePersonalNoService.class, LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
		}
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class, "[getOfficeByUserId]OFFICE MODEL ::" +officeModel.toString());
		return officeModel;
	}

	// Get office by group id
	public OfficeModel getOfficeByGroupId(String groupId) {
		OfficeModel officeModel = new OfficeModel();
		try {
			Optional<OfficeModel>opt = officesService.getOfficeByGroupId(groupId);
			if(opt.isPresent()) {
				officeModel = opt.get();
			}
			
		} catch (Exception e) {
		
			DODLog.printStackTrace(e, ChangePersonalNoService.class, LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
		}
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class, officeModel.toString());
		return officeModel;
	}

	// Get Enum Map by Name
	public Map<Integer, String> getEnumList(String enumType) {
		Map<Integer, String> enumMap = null;

		try {
			List<EnumType> enumList = enumTypeServices.getEnumType(enumType);
			

			if (enumList != null) {
				enumMap = enumList.stream().collect(Collectors.toMap(EnumType::getCode, EnumType::getValue));
			}
		} catch (Exception e) {
			
			DODLog.printStackTrace(e, ChangePersonalNoService.class, LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
		}
		
		return enumMap;
	}

	// Get Enum code and value as a String
	public String getEnumAsString (String enumType) {
		String enumString = "";

		try {
			Map<Integer, String> enumMap = getEnumList(enumType);
			if (enumMap != null) {
				enumString = enumMap.entrySet().stream()
						.map(entry -> entry.getValue() + "::" + entry.getKey())
						.collect(Collectors.joining(","));
			}
		} catch (Exception e) {
			
			DODLog.printStackTrace(e, ChangePersonalNoService.class, LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
		}

		return enumString + ',';
	}

	public Location getLocationById(String locationId) {
		Optional<Location> opt =  locationServices.getLocationById(locationId);
		Location location = new Location();
		if(opt.isPresent()) {
			location = opt.get();	
		}
		
		
		return location;
	}
	
//	
	public static String formatDate(Date date,String format) {

		String dateString="";
	
		try{
			DateFormat dateFormat=new  SimpleDateFormat(format);
			if(date!=null) {
				dateString=dateFormat.format(date);
			}	
		}catch(Exception e){
			DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class,"[formatDate] ## EXCEPTION ##   :: ");
			DODLog.printStackTrace(e, ChangePersonalNoService.class, LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);	
		}
		return dateString;
	}
	
public List<GetChangePersonalNo> getData(String dob,String enrollmentDate,BigInteger loginUser) {

	PostOldPersonalNo postOldPNo  = new PostOldPersonalNo();
	List<GetChangePersonalNo> changePersonal = new ArrayList<>();
	postOldPNo.setDob(dob);
	postOldPNo.setEnrollmentDate(enrollmentDate);
	postOldPNo.setLoginUserId(loginUser);
	try {
		
	ChangePersonalResponse  responseEntity	=
			restTemplate.postForObject(PcdaConstant.CHANGE_PERSONAL_NO_URL + "/getOldData",
			postOldPNo, ChangePersonalResponse.class);
	

	if(responseEntity!=null && responseEntity.getErrorCode()==200 && responseEntity.getResponseList()!=null) {
	changePersonal =responseEntity.getResponseList();
	
	changePersonal.stream().forEach(e->e.setDobStr(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy")));
	changePersonal.stream().forEach(e->e.setEnrollmentDateStr(CommonUtil.formatDate(e.getEnrollmentDate(), "dd-MM-yyyy")));
	return changePersonal;
	}
	
	
	}catch(Exception e) {
		
		DODLog.printStackTrace(e, ChangePersonalNoService.class, LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
	}
	return changePersonal;
}

public Date getDate(String dateString,String desireFormat){
	Date date=null;
	try{
		SimpleDateFormat dateFormat=new  SimpleDateFormat(desireFormat);
		dateFormat.applyPattern(desireFormat);
		if(dateString!=null && !dateString.equals(""))
			date=dateFormat.parse(dateString);
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class, "[getDate] ## DATE AFTER CHANGE FROM STRING :: "+date);
	
	}catch(Exception e){
		DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class,"[getDate] ## EXCEPTION ##   :: ");
		DODLog.printStackTrace(e, ChangePersonalNoService.class, LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
	}
	DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class, "[getDate] ## DATE BEFORE CHANGE FROM STRING :: "+date);
	return  date;
}

// save

public  ChangePersonalNoPostResponse getSaveChangePno(PostChangePersonalNoModel postChangePNo) {
	ChangePersonalNoPostResponse changePersonalNoPostResponse=null;
	try {
	
		changePersonalNoPostResponse=restTemplate.postForObject(PcdaConstant.CHANGE_PERSONAL_NO_URL + "/sentForApprove",
						postChangePNo, ChangePersonalNoPostResponse.class);
	}catch(Exception e) {
		
		DODLog.printStackTrace(e, ChangePersonalNoService.class, LogConstant.CHANGE_PERSONAL_NO_LOG_FILE);
		
	}
	DODLog.info(LogConstant.CHANGE_PERSONAL_NO_LOG_FILE, ChangePersonalNoService.class,
			"[getSaveChangePno]  CHANGE PERSONAL NO SAVE RESPONSE :: "+changePersonalNoPostResponse);
	return changePersonalNoPostResponse;
}

}
