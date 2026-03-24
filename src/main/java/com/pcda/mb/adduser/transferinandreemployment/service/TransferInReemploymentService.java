package com.pcda.mb.adduser.transferinandreemployment.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.Category;
import com.pcda.common.model.DODServices;
import com.pcda.common.model.GradePayMappingModel;
import com.pcda.common.model.GradePayRankModel;
import com.pcda.common.model.Level;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.OfficesResponse;
import com.pcda.common.model.PAOMapResponse;
import com.pcda.common.model.PAOMappingModel;
import com.pcda.common.model.User;
import com.pcda.common.services.AirportServices;
import com.pcda.common.services.CategoryServices;
import com.pcda.common.services.GradePayRankServices;
import com.pcda.common.services.GradePayServices;
import com.pcda.common.services.LevelServices;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.StationServices;
import com.pcda.common.services.UserServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.transferin.service.TransferInService;
import com.pcda.mb.adduser.transferinandreemployment.model.PostTransferInReemployement;
import com.pcda.mb.adduser.transferinandreemployment.model.TINAndReemployment;
import com.pcda.mb.adduser.transferinandreemployment.model.TINAndReqData;
import com.pcda.mb.adduser.transferinandreemployment.model.TransferInReemployeeResponse;
import com.pcda.mb.adduser.transferout.model.EditUserModel;
import com.pcda.mb.adduser.transferout.service.TransferOutServiceMB;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TransferInReemploymentService {

	@Autowired
	private MasterServices masterServices;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private OfficesService officesService;
	
	@Autowired
	private CategoryServices categoryServices;
	
	@Autowired
	private GradePayServices gradePayServices;
	
	@Autowired
	private LevelServices levelServices;
	
	@Autowired
	private StationServices stationServices;
	
	@Autowired
	private AirportServices airportServices;

	@Autowired
	private GradePayRankServices gradePayRankServices;

	@Autowired
	private RestTemplate template;

	public List<DODServices> getTransferInReEmpServices() {
		
		List<DODServices>	transferInReempServices = masterServices.getServicesByApprovalState("1");
		
		return transferInReempServices.stream().filter(src -> src.getArmedForces().name().equalsIgnoreCase("YES")).toList();
	}

	public List<OfficeModel> getUnitList() {
		return officesService.getOffices("UNIT", "1");
	}

	public List<DODServices> getArmedTravelerServices(LoginUser loginUser, Optional<OfficeModel> office,
			TINAndReqData reqData) {
		
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

				reqData.setRankExistService(rankExistService.toString()); 

				serviceData = masterServices.getServicesByApprovalState("1").stream()
						.filter(src -> src.getArmedForces().name().equalsIgnoreCase("YES")).toList();

				serviceData.forEach(ob -> {

					String serviceName = ob.getServiceName();
					String serviceId = ob.getServiceId();

					if (loginUser.getServiceId().equals(serviceId)) {
						reqData.setRankExistService(Boolean.FALSE.toString());
						reqData.setServiceName(serviceName);
						reqData.setServiceId(serviceId);

					} else {
						if (serviceRankExist.contains(ob.getServiceId())) {
					
							reqData.setRankExistService(Boolean.FALSE.toString());
							reqData.setServiceName(serviceName);
							reqData.setServiceId(serviceId);
						}
					}
				});
			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, TransferInReemploymentService.class, LogConstant.TRANSFER_IN_REEMPLOYEMENT);
		}
		return dodServices;
	}

	public TINAndReemployment getPersonalNumberTransferInRe(String personalNo, HttpServletRequest request) {
		TINAndReemployment reemployment = new TINAndReemployment();
		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferInReemploymentService.class,"[getPersonalNumberTransferInRe] PNO "+personalNo);
		try {
			if (null != personalNo && personalNo.length() < 4) {
				reemployment.setMessage("Not Valid");
				return reemployment;
			} else {

				SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
				LoginUser loginUser = sessionVisitor.getLoginUser();

				Optional<OfficeModel> officeModel = officesService.getOfficeByUserId(loginUser.getUserId());
				String loggedVisitorOfficeId = "";
				String visitorUnitName = "";

				if (officeModel.isPresent()) {
					loggedVisitorOfficeId = officeModel.get().getGroupId();
					visitorUnitName = officeModel.get().getName();
				}
				

				Optional<User> personalNumber = userServices.getUserByUserAlias(personalNo);
				
				BigInteger userId=BigInteger.ZERO;
				

				if(personalNumber.isPresent()) {
					userId=personalNumber.get().getUserId();
				}
			

				if (personalNumber.isEmpty()) {
					reemployment.setMessage("usernotfound");
					return reemployment;
				}
				User visitor = personalNumber.get();
				String categoryId = visitor.getCategoryId();
				String rankId = visitor.getRankId();

				Optional<OfficeModel> personalOffices = officesService.getOfficeByUserId(visitor.getUserId());
				String visitorOfficeId = "";
				String currentOfficeName="";
				if (personalOffices.isPresent()) {
					visitorOfficeId = personalOffices.get().getGroupId();
					currentOfficeName=personalOffices.get().getName();
				}
				DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferInReemploymentService.class, "[getPersonalNumberTransferInRe] loggedVisitorOfficeId :: "+loggedVisitorOfficeId
						+" visitorUnitName: "+visitorUnitName+" visitorOfficeId: "+visitorOfficeId +" currentOfficeName: "+currentOfficeName);
				
				
				if(personalOffices.isEmpty()) {
					reemployment.setMessage("dataexist");
					return reemployment;
				}
				
				if (visitor.getApprovalState().ordinal() != 1) {
					reemployment.setMessage("usernotapproved");
					return reemployment;
				}

				if (loggedVisitorOfficeId.equals(visitorOfficeId)) {
					reemployment.setMessage("sameUnit");
					return reemployment;
				}

				if (categoryId == null || categoryId.equals("") || rankId == null || rankId.equals("")) {
					reemployment.setMessage("VirtualUser");
					return reemployment;
				}
				EditUserModel user = userServices.getUserByUserId(userId);
				if(user!=null && user.getProfileStatus()!=null && user.getProfileStatus().contentEquals("requested")&& user.getUserEditType().contentEquals("edit")) {
					reemployment.setMessage("editMode");
					return reemployment;
				}else
				if(user!=null && user.getProfileStatus()!=null && user.getProfileStatus().contentEquals("requested")&& user.getUserEditType().contentEquals("TransferInAndReemployment")) {
					reemployment.setMessage("transferInReemployment");
					return reemployment;
				}

				String unitName = "";
				if (personalOffices.isPresent()) {
					unitName = personalOffices.get().getName();
				}

				String unitServiceId = "";
				String travelerServiceId;
				String unitServiceName = "";
				String travelerServiceName;
				String categoryName = "";

				if (visitor.getUserServiceId() != null && !visitor.getUserServiceId().equals("")) {
					travelerServiceId = visitor.getUserServiceId();
					unitServiceId = visitor.getServiceId();
					travelerServiceName = getServiceName(travelerServiceId);
					unitServiceName = getServiceName(unitServiceId);
				} else {
					travelerServiceId = visitor.getServiceId();
					travelerServiceName = getServiceName(travelerServiceId);
				}


				

				if (visitor.getCategoryId() != null && !visitor.getCategoryId().equals("")) {
					categoryId = visitor.getCategoryId();
					categoryName = getCategoryName(travelerServiceId, categoryId);
					
				}

				String name = visitor.getName();
				List<OfficeModel> units = officesService.getOffices("UNIT", "1");
				List<OfficeModel> unitList = units.stream().sorted(Comparator.comparing(OfficeModel::getName)).toList();

				reemployment.setServiceName(travelerServiceName);
				reemployment.setUnitServiceName(unitServiceName);
				reemployment.setUnitName(unitName);
				reemployment.setCategoryName(categoryName);
				reemployment.setOfficeId(visitorOfficeId);
				reemployment.setVisitorUnitName(visitorUnitName);
				reemployment.setName(name);
				reemployment.setCurrentUnit(currentOfficeName);
				reemployment.setUserId(visitor.getUserId());
				reemployment.setDob(CommonUtil.formatDate(visitor.getDateOfBirth(), "dd/MM/yyyy"));
				reemployment.setPersonalNo(visitor.getUserAlias());

				String loggedVisitorServiceName = getServiceName(loginUser.getServiceId());
				if (!unitList.isEmpty()) {

					List<OfficeModel> unitData = new ArrayList<>();

					
					unitList.stream().forEach(groupDataHolder -> {

						if (!groupDataHolder.getName().equalsIgnoreCase(personalOffices.get().getName())) {

							if (loginUser.getServiceId().equals(PcdaConstant.CIVILIAN_SEVICE_ID)) {
								if (groupDataHolder.getServiceId().equals(PcdaConstant.CIVILIAN_SEVICE_ID)) {
									unitData.add(groupDataHolder);
								}
							} else if (loggedVisitorServiceName
									.equalsIgnoreCase(PcdaConstant.COAST_GUARD_SEVICE_NAME)) {
								// transfer can only be done in cost gaurd unit
								if (travelerServiceName.equalsIgnoreCase(PcdaConstant.COAST_GUARD_SEVICE_NAME)) {
									if (groupDataHolder.getServiceId().equals(travelerServiceId)) {

										unitData.add(groupDataHolder);
									}

								} else {

									if (!groupDataHolder.getServiceId().equals(PcdaConstant.CIVILIAN_SEVICE_ID)) {

										unitData.add(groupDataHolder);
									}
								}
							} else if (loggedVisitorServiceName.equalsIgnoreCase("DSC")) {
								unitData.add(groupDataHolder);
							} else {

								if (!groupDataHolder.getServiceId().equals(PcdaConstant.CIVILIAN_SEVICE_ID)
										&& !groupDataHolder.getServiceId().equals(PcdaConstant.DSC_SEVICE_ID)) {

									unitData.add(groupDataHolder);
								}

							}
						}

					});

					reemployment.setUnitList(unitData);
				}
				
				return reemployment;

			}

		} catch (Exception e) {
			DODLog.printStackTrace(e, TransferInService.class, LogConstant.TRANSFER_IN_REEMPLOYEMENT);
		}
		
		return reemployment;
	}

	private String getCategoryName(String serviceId, String categoryId) {

		Optional<Category> cateOptional = categoryServices.getCategory(serviceId, categoryId);
		if (cateOptional.isPresent()) {
			return cateOptional.get().getCategoryName();
		}
		return "";
	}

	private String getServiceName(String serviceId) {
		Optional<DODServices> service = masterServices.getServiceByServiceId(serviceId);

		if (service.isPresent()) {
			return service.get().getServiceName();
		}
		return "";
	}

	// save Audit Table
	public TransferInReemployeeResponse saveTransferInReemploye(PostTransferInReemployement postTransferModel) {



			
		Date currentDate = new Date();
		


		postTransferModel.setJoiningDate(currentDate);

		postTransferModel.setSosDate(CommonUtil.formatString(postTransferModel.getSosDateString(), "dd-MM-yyyy"));

		String [] level=postTransferModel.getLevel_Id().split(":");
		postTransferModel.setLevelId(level[0]);
		postTransferModel.setLevelName(level[0]);
		
		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferInReemploymentService.class,
				"[saveTransferInReemploye] ## Save Model::" + postTransferModel);
		
		TransferInReemployeeResponse transferOutResponce =null;
		try {
			ResponseEntity<TransferInReemployeeResponse> response = template.postForEntity(
				PcdaConstant.TRANSFER_SERVICE_URL + "/transferInAndReemployment/save", postTransferModel,
				TransferInReemployeeResponse.class);

			 transferOutResponce = response.getBody();

		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferOutServiceMB.class, "Response::" + transferOutResponce);

			
			
		}catch(Exception e) {
			DODLog.printStackTrace(e, TransferInService.class, LogConstant.TRANSFER_IN_REEMPLOYEMENT);
		}
		return transferOutResponce;
	}

	// STRING TO DATE

	public String setService(String serviceId, Map<String, String> serviceMap) {

		if (serviceMap.containsKey(serviceId)) {
			return serviceMap.get(serviceId);
		} else {
			return "";
		}
	}

	public String setCategory(String categoryId, Map<String, String> categoryMap) {

		if (categoryMap.containsKey(categoryId)) {
			return categoryMap.get(categoryId);
		} else {
			return "";
		}
	}

	public Map<String, String> serviceMap() {
		List<DODServices> service = masterServices.getServicesByApprovalState("1");
		return service.stream().collect(Collectors.toMap(DODServices::getServiceId, DODServices::getServiceName));
	}

	// Get Categories Map Based on Service Id
	public Map<String, String> getCategoriesBasedOnService(String serviceId) {
		List<Category> categoryList = categoryServices.getCategories(serviceId);
		Map<String, String> serviceCategoryMap = new HashMap<>();
		if (!categoryList.isEmpty()) {
		
			serviceCategoryMap = categoryList.stream()
					.collect(Collectors.toMap(Category::getCategoryId, Category::getCategoryName));
			
		}
		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferInReemploymentService.class," serviceCategoryMap: " + serviceCategoryMap.size());
		return serviceCategoryMap;
	}

	// Map of level Id And list of level Name, dod rank and personal number prefix
	// id w.r.t serviceID and CategoryId
	public Map<String, List<String>> gradePayBasedOnServiceCategory(String serviceId, String categoryId) {
		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferInReemploymentService.class,
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
	
	
	private List<PAOMappingModel> getPaoMappingTransfer(String approvalType) {
		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, MasterServices.class, "[getPaoMappingTransfer] PAO MAPPING with approval type " + approvalType);

		List<PAOMappingModel> paoModelList = new ArrayList<>();
		
		try {
		ResponseEntity<PAOMapResponse> responseEntity = template.exchange(
				PcdaConstant.MASTER_BASE_URL + "/paomapping/allPAOmap/" + approvalType, HttpMethod.GET, null,
				new ParameterizedTypeReference<PAOMapResponse>() {});
		PAOMapResponse paoMapResponse=responseEntity.getBody();

		if(null != paoMapResponse && (paoMapResponse.getErrorCode()==200) && null!= paoMapResponse.getResponseList()) {
			paoModelList =  paoMapResponse.getResponseList();
			DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferInReemploymentService.class, "PAO Service :::: " + paoModelList.size());
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e, TransferInService.class, LogConstant.TRANSFER_IN_REEMPLOYEMENT);
		}
		return paoModelList;
	}

	// Get PAO Offices List
	public Map<String, List<PAOMappingModel>> getPaoOfficeTra(String serviceId, String categoryId) {
		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferInReemploymentService.class,
				"[getPaoOfficeTra] PAO List with service id: " + serviceId + " ; category id: " + categoryId);
		List<PAOMappingModel> railPAOOfficeList = new ArrayList<>();
		List<PAOMappingModel> airPAOOfficeList = new ArrayList<>();
		try {
			Map<String, String> paoOfficeMap = getPaoOfficesMap();
		List<PAOMappingModel> paoModelList = getPaoMappingTransfer("1");


			paoModelList = paoModelList.stream().filter(pao -> pao.getServiceId().equals(serviceId) && pao.getCategoryId().equals(categoryId))
					.toList();

			paoModelList.forEach(e -> e.setName(setGroupName(e.getAcuntoficeId(), paoOfficeMap)));

			airPAOOfficeList = paoModelList.stream().filter(ob -> ob.getTravelType().ordinal()==1).toList();
			railPAOOfficeList = paoModelList.stream().filter(ob -> ob.getTravelType().ordinal()==0).toList();
		
		} catch (Exception e) {
			DODLog.printStackTrace(e, TransferInService.class, LogConstant.TRANSFER_IN_REEMPLOYEMENT);
		}
		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferInReemploymentService.class,"[getPaoOfficeTra];;; airPAOOfficeList ;;;;" +
				airPAOOfficeList.size() + ";;;; railPAOOfficeList ;;;;" + railPAOOfficeList.size());

		return Map.of("0", railPAOOfficeList, "1", airPAOOfficeList);
	}

	
	private List<OfficeModel> getOfficesTrans(String officeType, String approvalType) {
		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferInReemploymentService.class, "[getOfficesTrans ] ## officeType " + officeType + " approvalType " + approvalType);

		List<OfficeModel> officesList =new ArrayList<>();
		try {
		StringJoiner joiner = new StringJoiner("/").add(PcdaConstant.OFFICE_BASE_URL).add("allOffices").add(officeType).add(approvalType);

		ResponseEntity<OfficesResponse> responseEntity = template.exchange(joiner.toString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<OfficesResponse>() {});
		OfficesResponse officesResponse = responseEntity.getBody();

		if (null != officesResponse && (officesResponse.getErrorCode() == 200) && null != officesResponse.getResponseList()) {
			officesList = officesResponse.getResponseList();
		} 
		}catch (Exception e) {
			DODLog.printStackTrace(e, TransferInService.class, LogConstant.TRANSFER_IN_REEMPLOYEMENT);
		}
		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferInReemploymentService.class, "[getOfficesTrans] ## officesList " + officesList.size());
		return officesList;
	}
	
	// Get Map of PAO Office Id and Name
	public Map<String, String> getPaoOfficesMap() {
		List<OfficeModel> paoOfficesList = getOfficesTrans("PAO", "1");
		Map<String, String> paoOfficeMap=new HashMap<>();
		
		if(paoOfficesList!=null && !paoOfficesList.isEmpty()) {
		paoOfficesList.forEach(e -> paoOfficeMap.put(e.getGroupId(), e.getName()));
		
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

	// Get Grade Pay/Rank
	public Optional<GradePayRankModel> getGradePayRank(String dodRankId) {
		DODLog.info(LogConstant.TRANSFER_IN_REEMPLOYEMENT, TransferInReemploymentService.class,
				"[getGradePayRank] ## Get grade pay rank with rank id: " + dodRankId);
		return gradePayRankServices.getGradePayWithDODRankId(dodRankId);
	}

	// get Stations
	public List<String> getStations(String station) {
		return stationServices.getStation(station);
	}

	// get Airport
	public List<String> getAirports(String airPortName) {
		return airportServices.getAirport(airPortName);
	}

}
