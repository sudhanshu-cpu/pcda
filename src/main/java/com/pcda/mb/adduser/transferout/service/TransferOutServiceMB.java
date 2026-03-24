package com.pcda.mb.adduser.transferout.service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.Category;
import com.pcda.common.model.DODServices;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.User;
import com.pcda.common.services.AirportServices;
import com.pcda.common.services.CategoryServices;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.StationServices;
import com.pcda.common.services.UserServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.adduser.transferin.service.TransferInService;
import com.pcda.mb.adduser.transferout.model.EditUserModel;
import com.pcda.mb.adduser.transferout.model.PostTransferOutModel;
import com.pcda.mb.adduser.transferout.model.TransferOut;
import com.pcda.mb.adduser.transferout.model.TransferOutResponse;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TransferOutServiceMB {

	@Autowired
	private RestTemplate template;

	@Autowired
	private MasterServices masterServices;
	@Autowired
	private UserServices userServices;

	@Autowired
	private StationServices stationServices;

	@Autowired
	private AirportServices airportServices;

	@Autowired
	private CategoryServices categoryServices;

	@Autowired
	private OfficesService officesService;

	// save Audit Table
	public TransferOutResponse saveTransferOut(PostTransferOutModel postTransferOutModel) {


		TransferOutResponse transferOutResponce=null;
		
		try {
			
	postTransferOutModel.setSosDate(CommonUtil.formatString(postTransferOutModel.getSosDateString(),"dd-MM-yyyy"));
			ResponseEntity<TransferOutResponse> response = template.postForEntity(
					PcdaConstant.TRANSFER_SERVICE_URL + "/transferout/save", postTransferOutModel,
					TransferOutResponse.class);

			 transferOutResponce = response.getBody();

		} catch (Exception e) {
			
			DODLog.printStackTrace(e, TransferOutServiceMB.class, LogConstant.TRANSFER_OUT_LOG_FILE);
	
		}

		
		return transferOutResponce;
	}


	public TransferOut getPersonalDetails(String userAlias, HttpServletRequest request) {
		DODLog.info(LogConstant.TRANSFER_OUT_LOG_FILE, TransferOutServiceMB.class,"[getPersonalDetails]  Transfer Out personalNo::"+userAlias);
		TransferOut transferOut = new TransferOut();

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();
		try {
			Optional<OfficeModel> officeModel = officesService.getOfficeByUserId(loginUser.getUserId());
			String loggedVisitorOfficeId = "";
			String visitorUnitName = "";

			if (officeModel.isPresent()) {
				loggedVisitorOfficeId = officeModel.get().getGroupId();
				visitorUnitName = officeModel.get().getName();
			}

			String personalNo = userAlias;
			
			String functionality = "TOUT";
			Optional<User> userPersonal = userServices.getUserByUserAlias(personalNo);
			
			BigInteger userId=BigInteger.ZERO;
			String categoryId="";
			String rankId = "";
				

			if(userPersonal.isPresent()) {
	
	userId=userPersonal.get().getUserId();
	categoryId=userPersonal.get().getCategoryId();
	rankId=userPersonal.get().getRankId();
}
		
			
			if (userPersonal.isEmpty()) {
	transferOut.setMessage("NoRecordFound");
				return transferOut;
			}

			User visitor = userPersonal.get();
			Optional<OfficeModel> personalOffices = officesService.getOfficeByUserId(userId);
			String visitorOfficeId = "";
			String currentOfficeName="";
			if (personalOffices.isPresent()) {
				visitorOfficeId = personalOffices.get().getGroupId();
				currentOfficeName=personalOffices.get().getName();
			}
			
			DODLog.info(LogConstant.TRANSFER_OUT_LOG_FILE, TransferOutServiceMB.class,"[getPersonalDetails] visitorOfficeId ::" + visitorOfficeId
					+" currentOfficeName ::" + currentOfficeName +" loggedVisitorOfficeId::" + loggedVisitorOfficeId + " visitorUnitName :: "+visitorUnitName);	
			
			if(personalOffices.isEmpty()) {
				transferOut.setMessage("duplicate");
				return transferOut;
			}
			
			
			if (personalNo.length() < 4) {
				transferOut.setMessage("Not Valid");

				return transferOut;
			} else {

				if (personalNo.isEmpty()) {
					transferOut.setMessage("usernotfound");
					return transferOut;
				}

				int userStatus = visitor.getUserStatus().ordinal();
				if (functionality.equals("TOUT") && userStatus == 1) {

					transferOut.setMessage("userNotActive");
					return transferOut;
				}
			
				int userApprovalSts = visitor.getApprovalState().ordinal();
				
				if (userApprovalSts != 1) {
					transferOut.setMessage("usernotapproved");
					return transferOut;
				}
				if (functionality.equals("TOUT")) {
					if (!loggedVisitorOfficeId.equals(visitorOfficeId)) {
						transferOut.setMessage("NoData");
						return transferOut;
					}
				} else if (loggedVisitorOfficeId.equals(visitorOfficeId)) {
					transferOut.setMessage("sameUnit");
					return transferOut;
				}

				if (categoryId == null ||categoryId.equals("") || rankId== null
						|| rankId.equals("")) {
					transferOut.setMessage("VirtualUser");
					return transferOut;
				}
				
				EditUserModel user = userServices.getUserByUserId(userId);
				
					

				if(user!=null && user.getProfileStatus()!=null && user.getProfileStatus().contentEquals("requested")&& user.getUserEditType().contentEquals("edit")) {
					transferOut.setMessage("editMode");
					return transferOut;
				}else
				if(user!=null && user.getProfileStatus()!=null && user.getProfileStatus().contentEquals("requested")&& user.getUserEditType().contentEquals("TransferOut")) {
					transferOut.setMessage("transferOut");
					return transferOut;
				}
				

		
				String unitName = personalOffices.get().getName();

				String unitServiceId = "";
				String travelerServiceId = "";
				String unitServiceName = "";
				String travelerServiceName = "";
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

				transferOut.setServiceName(travelerServiceName);
				transferOut.setUnitServiceName(unitServiceName);
				transferOut.setUnitName(unitName);
				transferOut.setCategoryName(categoryName);
				transferOut.setOfficeId(visitorOfficeId);
				transferOut.setVisitorUnitName(visitorUnitName);
				transferOut.setName(name);
				transferOut.setUserId(userId);
				transferOut.setCurrentUnit(currentOfficeName);
				transferOut.setDob(CommonUtil.formatDate(visitor.getDateOfBirth(), "dd/MM/yyyy"));

				String loggedVisitorServiceName = getServiceName(loginUser.getServiceId());
				String loggedVistorServiceId =loginUser.getServiceId();

				AtomicBoolean isPartOfDSC = new AtomicBoolean(false);
				if (loggedVisitorServiceName.equalsIgnoreCase("DSC")) {
					isPartOfDSC.set(true);
				}

				final String trName =travelerServiceName;
		    	 final	String trServiceId=travelerServiceId;
				
				
				 
		
				 
		    	 	

				if (!unitList.isEmpty()) {

					List<OfficeModel> unitData = new ArrayList<>();

					unitList.stream().forEach(groupDataHolder -> {

						if (!groupDataHolder.getName().equalsIgnoreCase(personalOffices.get().getName())) {

							if (loggedVistorServiceId.equals(PcdaConstant.CIVILIAN_SEVICE_ID)) {
								if (groupDataHolder.getServiceId().equals(PcdaConstant.CIVILIAN_SEVICE_ID)) {
									unitData.add(groupDataHolder);
								}
							} else if (loggedVisitorServiceName.equalsIgnoreCase(PcdaConstant.COAST_GUARD_SEVICE_NAME)) {
							
							if (trName.equalsIgnoreCase(PcdaConstant.COAST_GUARD_SEVICE_NAME)) {
								if (groupDataHolder.getServiceId().equals(trServiceId)) {

									unitData.add(groupDataHolder);
								}

							} else {

								if (!groupDataHolder.getServiceId().equals(PcdaConstant.CIVILIAN_SEVICE_ID)) {

									unitData.add(groupDataHolder);
								}
							}
						} else if (isPartOfDSC.get()) {
							unitData.add(groupDataHolder);
						} else {

							if (!groupDataHolder.getServiceId().equals(PcdaConstant.CIVILIAN_SEVICE_ID)
									&& !groupDataHolder.getServiceId().equals(PcdaConstant.DSC_SEVICE_ID)) {

								unitData.add(groupDataHolder);
							}

						}
						}
					});

					transferOut.setUnitList(unitData);
				}
				
				return transferOut;
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, TransferInService.class, LogConstant.TRANSFER_OUT_LOG_FILE);
		}
		
		return transferOut;
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

	// get Stations
	public List<String> getStations(String station) {
		return stationServices.getStation(station);
	}

	// get Airport
	public List<String> getAirports(String airPortName) {
		return airportServices.getAirport(airPortName);
	}

	public Optional<OfficeModel> getOffices(BigInteger userId) {

		return officesService.getOfficeByUserId(userId);
	}

	public String setService(String serviceId, Map<String, String> serviceMap) {

		if (serviceMap.containsKey(serviceId)) {
			return serviceMap.get(serviceId);
		} else {
			return "";
		}
	}

	public Map<String, String> serviceMap() {

		List<DODServices> service = masterServices.getServicesByApprovalState("1");

		return service.stream().collect(Collectors.toMap(DODServices::getServiceId, DODServices::getServiceName));
	}

	// Get All Units
	public List<OfficeModel> getUnitList() {

		List<OfficeModel> officeList = officesService.getOffices("UNIT", "1");
		
		return officeList;
	}

	// STRING TO DATE
	public Date formatString(String date) {
		Date date2;
		try {
			date2 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
		} catch (ParseException e) {
			DODLog.printStackTrace(e, TransferOutServiceMB.class, LogConstant.TRANSFER_OUT_LOG_FILE);
			return null;
		}
		return date2;
	}

	// DATE TO STRING
	public String formatDate(Date dateFormat) {
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String date = "";
		if (dateFormat != null) {
			date = formatter.format(dateFormat);
		}
		return date;
	}

}
