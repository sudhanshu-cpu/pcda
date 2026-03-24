package com.pcda.mb.adduser.changeservice.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.PAOMappingModel;
import com.pcda.common.model.PersonalNoPrefix;
import com.pcda.common.model.User;
import com.pcda.common.services.CategoryServices;
import com.pcda.common.services.EnumTypeServices;
import com.pcda.common.services.GradePayRankServices;
import com.pcda.common.services.GradePayServices;
import com.pcda.common.services.LevelServices;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.PAOMappingServices;
import com.pcda.common.services.PersonalNoPrefixServices;
import com.pcda.common.services.UserServices;
import com.pcda.login.model.LoginUser;
import com.pcda.mb.adduser.changeservice.model.ChangeServiceRequired;
import com.pcda.mb.adduser.changeservice.model.ChangeServiceResponse;
import com.pcda.mb.adduser.changeservice.model.ChangeServiceViewResponse;
import com.pcda.mb.adduser.changeservice.model.PostChangeServiceModel;
import com.pcda.util.DODDATAConstants;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class ChangeServiceService {

	@Autowired
	private MasterServices masterServices;
	@Autowired
	private OfficesService officesService;

	@Autowired
	private GradePayServices gradePayServices;

	@Autowired
	private LevelServices levelServices;

	@Autowired
	private GradePayRankServices gradePayRankServices;

	@Autowired
	private PersonalNoPrefixServices personalNoPrefixServices;


	@Autowired
	private UserServices userServices;

	@Autowired
	private PAOMappingServices mappingServices;

	@Autowired
	private CategoryServices categoryServices;

	@Autowired
	private EnumTypeServices enumTypeServices;

	@Autowired
	private RestTemplate restTemplate;

	// Get Service Based with Service id
	public DODServices getService(String serviceId) {
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class,
				" ## SERVICE ID :: " +serviceId);
		DODServices dodServices = new DODServices();
		try {
			Optional<DODServices> opt = masterServices.getServiceByServiceId(serviceId);

			if (opt.isPresent()) {
				dodServices = opt.get();
			}
			
			return dodServices;
		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangeServiceService.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
			return dodServices;
		}
	}

	// Get Services Based on Service Type
	public List<DODServices> getArmedTravelerServices(LoginUser loginUser, OfficeModel officeModel,
			ChangeServiceRequired noRequired) {
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class, "[getArmedTravelerServices] Get Traveler Service");
		List<DODServices> serviceData = new ArrayList<>();
		List<GradePayRankModel> rankData = new ArrayList<>();

		List<DODServices> dodServices = new ArrayList<>();

		try {
			rankData = gradePayRankServices.getAllGradePayRankWithApproval("1");
			StringBuilder rankExistService = new StringBuilder();
			if (rankData != null && !rankData.isEmpty()) {
				List<String> serviceRankExist = new ArrayList<>();

				rankData.forEach(ob -> {
					String service = ob.getServiceId();
					if (!serviceRankExist.contains(service)) {
						serviceRankExist.add(service);
						rankExistService.append(rankExistService).append("," + service);
					}
				});

				noRequired.setRankExistService(rankExistService.toString()); // Need to be checked

				serviceData = masterServices.getServicesByApprovalState("1").stream()
						.filter(src -> src.getArmedForces().name().equalsIgnoreCase("YES")).toList();

				serviceData.forEach(ob -> {

					String serviceName = ob.getServiceName();
					String serviceId = ob.getServiceId();

					if (loginUser.getServiceId().equals(serviceId)) {
						noRequired.setRankExistService(Boolean.FALSE.toString());
					}
					if (noRequired.getServiceName().equalsIgnoreCase("DSC")) {
						if (serviceRankExist.contains(ob.getServiceId()) && (serviceName.equalsIgnoreCase("DSC")
								|| serviceName.equalsIgnoreCase("Army") || serviceName.equalsIgnoreCase("MNS"))) {
							dodServices.add(ob);
						}
					} else {
						if (serviceRankExist.contains(ob.getServiceId())) {
							String officeName = officeModel.getName();
							if (officeName.equalsIgnoreCase("APSRECORDS") || !serviceName.equalsIgnoreCase("APS")) {
								dodServices.add(ob);
							}
						}
					}
				});
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangeServiceService.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
		}
		return dodServices;
	}

	// Get Categories Map Based on Service Id
	public Map<String, String> getCategoryBasedOnService(String serviceId) {
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class, "serviceId"+serviceId);
		List<Category> categoryList = categoryServices.getCategories(serviceId);
		Map<String, String> serviceCategoryMap = new LinkedHashMap<>();
		if (!categoryList.isEmpty()) {
			serviceCategoryMap = categoryList.stream()
					.collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
			
		}
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class,
				"[getCategoryBasedOnService] SERVICE CATEGORY MAP  CHANGE SERVICE :: " + serviceCategoryMap.size());
		return serviceCategoryMap;
	}

	// Map of level Id And list of level Name, dod rank and personal number prefix
	// id w.r.t serviceID and CategoryId
	public Map<String, List<String>> gradePayBasedOnServiceCategory(String serviceId, String categoryId) {
		List<GradePayMappingModel> gradepayList = gradePayServices.getGradePayWithServiceAndCategory(serviceId,
				categoryId);
		Map<String, List<String>> gradePayMap = new HashMap<>();

		List<Level> levelList = levelServices.getLevelByApprovalType("1");
		Map<String, String> levelMap = levelList.stream().collect(Collectors.toMap(Level::getLevelId, Level::getLevelName));

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
		if (opt.isPresent()) {
			model = opt.get();
		}
		return model;
	}

	// Map of Personal Number Prefix
	public Map<String, String> getPersonalNoPrefixMap(String serviceId, String categoryId) {
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class,
				"[getPersonalNoPrefixMap] SERVICE ID AND CATEGORY ID TO GET PNO PREFIX " + serviceId + "," + categoryId);
		List<PersonalNoPrefix> personalNoPrefixList = personalNoPrefixServices.getPersonalNoPrefixWithApprovalType("1");
		return personalNoPrefixList.stream()
				.filter(pno -> pno.getServiceId().equals(serviceId) && pno.getCategoryId().equals(categoryId))
				.collect(Collectors.toMap(PersonalNoPrefix::getPrefixId, PersonalNoPrefix::getPrefix));
	}

	// Check Duplicate User Alias
	public Boolean checkDuplicate(String personalNo) {
		Optional<User> mappedUser = userServices.getUserByUserAlias(personalNo);

		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class,
				"[checkDuplicate] PERSONAL NO AND MAPPED USER ::" + personalNo + " ; " + mappedUser);
		return mappedUser.isEmpty();
	}

	// Get PAO Offices List
	public Map<String, List<PAOMappingModel>> getPaoOffice(String serviceId, String categoryId, LoginUser loginUser) {
	
		Map<String, String> paoOfficeMap = getPaoOfficesMap();
		List<PAOMappingModel> railPAOOfficeList = new ArrayList<>();
		List<PAOMappingModel> airPAOOfficeList = new ArrayList<>();
		List<PAOMappingModel> paoModelList = mappingServices.getPaoMappingServiceWithApproval("1");

		if (serviceId.equals(DODDATAConstants.NAVY_SEVICE_ID)
				&& loginUser.getServiceId().equals(DODDATAConstants.NAVY_SEVICE_ID)) {

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
			paoModelList = paoModelList.stream()
					.filter(pao -> pao.getServiceId().equals(serviceId) && pao.getCategoryId().equals(categoryId))
					.toList();

			paoModelList.forEach(e -> e.setName(setGroupName(e.getAcuntoficeId(), paoOfficeMap)));

			airPAOOfficeList = paoModelList.stream().filter(ob -> ob.getTravelType().ordinal()==1).toList();
			railPAOOfficeList = paoModelList.stream().filter(ob -> ob.getTravelType().ordinal()==0).toList();
		}

		return Map.of("0", railPAOOfficeList, "1", airPAOOfficeList);
	}

	// Get Map of PAO Office Id and Name
	public Map<String, String> getPaoOfficesMap() {
		List<OfficeModel> paoOfficesList = officesService.getOffices("PAO", "1");
		Map<String, String> paoOfficeMap = new HashMap<>();
		try {
			paoOfficesList.forEach(e -> paoOfficeMap.put(e.getGroupId(), e.getName()));
			DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class,
					"[getPaoOfficesMap] PAO OFFICE MAP ::" + paoOfficeMap);
			return paoOfficeMap;
		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangeServiceService.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
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
		OfficeModel officeModel = new OfficeModel();
		try {
			Optional<OfficeModel> opt = officesService.getOfficeByUserId(userId);
			if (opt.isPresent()) {
				officeModel = opt.get();
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangeServiceService.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
		}
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class,
				"[getOfficeByUserId] OFFICE MODEL ::" + officeModel.toString());
		return officeModel;
	}

	// Get office by group id
	public OfficeModel getOfficeByGroupId(String groupId) {
		OfficeModel officeModel = new OfficeModel();
		try {
			Optional<OfficeModel> opt = officesService.getOfficeByGroupId(groupId);
			if (opt.isPresent()) {
				officeModel = opt.get();
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangeServiceService.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
		}
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class, officeModel.toString());
		return officeModel;
	}

	// Get Enum Map by Name
	public Map<Integer, String> getEnumList(String enumType) {
		Map<Integer, String> enumMap = null;

		try {
			List<EnumType> enumList = enumTypeServices.getEnumType(enumType);
			DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class,
					"ENUM LIST ::" + enumList);

			if (enumList != null) {
				enumMap = enumList.stream().collect(Collectors.toMap(EnumType::getCode, EnumType::getValue));
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangeServiceService.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
		}

		return enumMap;
	}

	// Get Enum code and value as a String
	public String getEnumAsString(String enumType) {
		String enumString = "";

		try {
			Map<Integer, String> enumMap = getEnumList(enumType);
			if (enumMap != null) {
				enumString = enumMap.entrySet().stream().map(entry -> entry.getValue() + "::" + entry.getKey())
						.collect(Collectors.joining(","));
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, ChangeServiceService.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
		}

		return enumString + ',';
	}

// get data from personalNo
	public ChangeServiceResponse getSearchPnoData(BigInteger loginUser, String personalNo) {
		ChangeServiceResponse changeServiceResponse =null;
		DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class,
				"[getSearchPnoData] USERID ::::: " + loginUser +"PERSONAL NO :::::"+personalNo);
		try {
			String url = PcdaConstant.CHANGE_SERVICE_URL + "/getData/" + loginUser + '/' + personalNo;

			 changeServiceResponse = restTemplate.getForObject(url, ChangeServiceResponse.class);

			
		} catch (Exception e) {
			
			DODLog.printStackTrace(e, ChangeServiceService.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
			
		}
		 DODLog.info(LogConstant.CHANGE_SERVICE_LOG_FILE, ChangeServiceService.class,
					"[getSearchPnoData] ###CHANGE SERVICE DATA RESPONSE ########### " + changeServiceResponse);
		return changeServiceResponse;

	}

// save
	public ChangeServiceViewResponse getSaveChangeService(PostChangeServiceModel model) {
		ChangeServiceViewResponse response = null;
		try {

			response = restTemplate.postForObject(PcdaConstant.CHANGE_SERVICE_URL + "/updateData", model,
					ChangeServiceViewResponse.class);

			return response;
		} catch (Exception e) {
			
			DODLog.printStackTrace(e, ChangeServiceService.class, LogConstant.CHANGE_SERVICE_LOG_FILE);
			
		}
		return response;
	}

}
