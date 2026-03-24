package com.pcda.mb.adduser.transferin.service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.pcda.mb.adduser.transferin.model.PostTransferInModel;
import com.pcda.mb.adduser.transferin.model.TransferIN;
import com.pcda.mb.adduser.transferin.model.TransferInResponse;
import com.pcda.mb.adduser.transferout.model.EditUserModel;
import com.pcda.mb.adduser.transferout.service.TransferOutServiceMB;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODDATAConstants;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TransferInService {

	@Autowired
	private MasterServices masterServices;
	@Autowired
	private UserServices userServices;

	@Autowired
	private OfficesService officesService;

	@Autowired
	private RestTemplate template;

	
	
	@Autowired
	private StationServices stationServices;

	@Autowired
	private AirportServices airportServices;

	@Autowired
	private CategoryServices  categoryServices;

	// Save
	public TransferInResponse saveTransferIn(PostTransferInModel postTransferInModel) {
		TransferInResponse transferInResponse =null;
		try {
			
			
			postTransferInModel.setSosDate(CommonUtil.formatString(postTransferInModel.getSosDateString(),"dd-MM-yyyy"));
			ResponseEntity<TransferInResponse> response = template.postForEntity(
					PcdaConstant.TRANSFER_SERVICE_URL+"/transferin/saveTransferInAudit", postTransferInModel,
					TransferInResponse.class);
			transferInResponse = response.getBody();
		
			
		}catch (Exception e) {
			
			DODLog.printStackTrace(e, TransferInService.class, LogConstant.TRANSFER_IN_LOG_FILE);
		}
		DODLog.info(LogConstant.TRANSFER_IN_LOG_FILE, TransferInService.class, "[saveTransferIn] ## Response::" +transferInResponse);
			return transferInResponse;
	}

	public String setCategory(String categoryId, Map<String, String> categoryMap) {

		if (categoryMap.containsKey(categoryId)) {
			return categoryMap.get(categoryId);
		} else {
			return "";
		}
	}

	public TransferIN getDetailBypersonalNo(String userAlias, HttpServletRequest request) {

		DODLog.info(LogConstant.TRANSFER_IN_LOG_FILE, TransferInService.class, "[getDetailBypersonalNo] ## userAlias" +userAlias);
		
		TransferIN transferIN = new TransferIN();
		if(userAlias.length()<4 || userAlias.length()>11) {
			transferIN.setMessage("minMaxLength");
			
			return transferIN;
		}

		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser loginUser = sessionVisitor.getLoginUser();

		String categoryId;
		String categoryName = "";
		try {

			Optional<OfficeModel> officeModel = officesService.getOfficeByUserId(loginUser.getUserId());
			
			String loggedUserUnitName = "";
			String loginVisitorServiceId = "";
			String loginServiceName = "";
			if (officeModel.isPresent()) {
	
				loggedUserUnitName = officeModel.get().getName();
				loginVisitorServiceId = officeModel.get().getServiceId();
				loginServiceName = getServiceName(loginVisitorServiceId);
			}
			
			String personalNo = userAlias;

			Optional<User> personalNumber = userServices.getUserByUserAlias(personalNo);
			
			

			BigInteger persUserId=BigInteger.ZERO;

			if(personalNumber.isPresent()) {
				persUserId=personalNumber.get().getUserId();
			}
			if (personalNumber.isEmpty()) {
				transferIN.setMessage("noRecordFound");
				return transferIN;
			}

			Optional<OfficeModel> persOffice = officesService.getOfficeByUserId(persUserId);
			
			String visitorOfficeName="";
			
			if(persOffice.isPresent()) {
				visitorOfficeName=persOffice.get().getName();
			}
				
			if(persOffice.isEmpty()) {
				transferIN.setMessage("duplicate");
				return transferIN;
			}

			

			User visitor = personalNumber.get();
			String rankId = visitor.getRankId();
			if (personalNo.length() < 2) {

				transferIN.setMessage("Not Valid");
				return transferIN;
			}

			else {

				int userApprovalSts = visitor.getApprovalState().ordinal();
				int userStatus = visitor.getUserStatus().ordinal();
				categoryId = visitor.getCategoryId();

				if (userStatus == 1) {
					transferIN.setMessage("userNotActive");
					return transferIN;
				}

				if (userApprovalSts != 1) {

					transferIN.setMessage("usernotapproved");
					return transferIN;
				}

				String personalServiceId = visitor.getServiceId();
				
				if (personalServiceId != null && personalServiceId.trim().length() == 0) {
					personalServiceId = visitor.getUserServiceId();
				}
				String personalServiceName = getServiceName(personalServiceId);

				String unitServiceId = "";
				String travelerServiceId = "";
				String unitServiceName = "";
				String travelerServiceName = "";

				if (visitor.getUserServiceId() != null && !visitor.getUserServiceId().trim().equals("")) {
					travelerServiceId = visitor.getUserServiceId();
					unitServiceId = visitor.getServiceId();
					travelerServiceName = getServiceName(travelerServiceId);
					unitServiceName = getServiceName(unitServiceId);
				} else {

					travelerServiceId = visitor.getServiceId();
					travelerServiceName = getServiceName(travelerServiceId);
				}
				
				if (categoryId != null && !categoryId.equals("")) {
					
				categoryName = getCategoryName(travelerServiceId, categoryId);
				}
				
				DODLog.info(LogConstant.TRANSFER_IN_LOG_FILE, TransferInService.class, "[getDetailBypersonalNo] ## personal user Id  Model "
				+userAlias+"-->" +loginServiceName+"<-- personalServiceName --> "+personalServiceName+"<-- travelerServiceName --> "+travelerServiceName);
				
				if (loginServiceName != null && loginServiceName.equalsIgnoreCase("DSC")) {
					if (personalServiceName != null && !personalServiceName.equalsIgnoreCase("DSC")) {
						if ((travelerServiceName.equalsIgnoreCase("Army")
								|| travelerServiceName.equalsIgnoreCase("MNS"))
								&& categoryName.equalsIgnoreCase("Officer")) {
							
						}else {
							transferIN.setMessage("tinNotAllowedFromAnyOtherToDSC");
							return transferIN;
						}

					}
				}

		
				String personalUnitName = persOffice.get().getName();

				if (personalUnitName.equals(loggedUserUnitName)) {

					transferIN.setMessage("notPartOfSameUnit");
					return transferIN;
				}



				if (categoryId == null || categoryId.equals("") || rankId == null || rankId.equals("")) {
					transferIN.setMessage("VirtualUser");
					return transferIN;
				}
				
				EditUserModel user = userServices.getUserByUserId(persUserId);
				if(user!=null && user.getProfileStatus()!=null && user.getProfileStatus().contentEquals("requested")&& user.getUserEditType().contentEquals("edit")) {
					transferIN.setMessage("editMode");
					return transferIN;
				}else
				if(user!=null && user.getProfileStatus()!=null && user.getProfileStatus().contentEquals("requested")&& user.getUserEditType().contentEquals("TransferIn")) {
					transferIN.setMessage("transferIn");
					return transferIN;
				}



				if (travelerServiceId.equals(DODDATAConstants.COAST_GUARD_SEVICE_ID)
						&& !loginVisitorServiceId.equals(DODDATAConstants.COAST_GUARD_SEVICE_ID)) {
					transferIN.setMessage("personalIsPartOfCostGaurd");
					return transferIN;
				}

				String officeId = officeModel.get().getGroupId();
				String name = visitor.getName();

				transferIN.setServiceName(travelerServiceName);
				transferIN.setUnitServiceName(unitServiceName);
				transferIN.setUnitName(visitorOfficeName);
				transferIN.setCategoryName(categoryName);
				transferIN.setOfficeId(officeId);
				transferIN.setVisitorUnitName(loggedUserUnitName);
				transferIN.setUserId(visitor.getUserId());
				transferIN.setCurrentUnit(visitorOfficeName);
				
				transferIN.setName(name);
				transferIN.setDob(CommonUtil.formatDate(visitor.getDateOfBirth(), "dd/MM/yyyy"));
			

				transferIN.setLoggedUserUnitName(loggedUserUnitName);

				transferIN.setLoginServiceName(loginServiceName);
				transferIN.setPersonalNo(personalNo);

			}


		} catch (Exception e) {
			
			DODLog.printStackTrace(e, TransferInService.class, LogConstant.TRANSFER_IN_LOG_FILE);
		}

		return transferIN;
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
		DODLog.info(LogConstant.TRANSFER_IN_LOG_FILE, TransferInService.class, "Unit Offices::" + officeList);

		return officeList;
	}

	public Optional<OfficeModel> getOffices(BigInteger userId) {

		return officesService.getOfficeByUserId(userId);
	}

	// get Stations
	public List<String> getStations(String station) {
		return stationServices.getStation(station);
	}

	// get Airport
	public List<String> getAirports(String airPortName) {
		return airportServices.getAirport(airPortName);
	}

	// STRING TO DATE
	public Date formatString(String date) {
		Date date2;
		try {
			date2 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
		} catch (ParseException e) {
			DODLog.printStackTrace(e, TransferOutServiceMB.class, LogConstant.TRANSFER_IN_LOG_FILE);
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


	private String getServiceName(String serviceId) {
		Optional<DODServices> service = masterServices.getServiceByServiceId(serviceId);

		if (service.isPresent()) {
			return service.get().getServiceName();
		}
		return "";
	}
	private String getCategoryName(String serviceId, String categoryId) {

		Optional<Category> cateOptional = categoryServices.getCategory(serviceId, categoryId);
		if (cateOptional.isPresent()) {
			return cateOptional.get().getCategoryName();
		}
		return "";
	}

}
