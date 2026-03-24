package com.pcda.mb.travel.journey.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.Category;
import com.pcda.common.model.DODServices;
import com.pcda.common.model.Level;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.TravelType;
import com.pcda.common.model.User;
import com.pcda.common.model.VisitorModel;
import com.pcda.common.services.CategoryServices;
import com.pcda.common.services.LevelServices;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.common.services.UserServices;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.travel.journey.model.PreventProcess;
import com.pcda.mb.travel.journey.model.PreventProcessDetails;
import com.pcda.mb.travel.journey.model.PreventProcessResponse;
import com.pcda.mb.travel.journey.model.TravellerDetails;
import com.pcda.mb.travel.journey.util.JourneyRequestUtils;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;
import com.pcda.util.UserStatus;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TravelDetailsService {
	
	@Autowired
	private TravelTypeServices travelTypeServices;
	
	@Autowired
	private UserServices userServices; 
	
	@Autowired
	private OfficesService officesService;
	
	@Autowired
	private MasterServices masterServices;
	
	@Autowired
	private CategoryServices categoryServices;
	
	@Autowired
	private LevelServices levelServices;
	
	@Autowired
	private RestTemplate template;
	

	public TravellerDetails getTravellerDetails(Optional<String> userAlias, HttpServletRequest request) {
		
		String logStr="getTravellerDetails("+userAlias+")";
		
		SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
		LoginUser portalVisitor = sessionVisitor.getLoginUser();
		VisitorModel  travler= new VisitorModel();
		TravellerDetails  travellerDetails=new TravellerDetails();
		try {
		String reqType= Optional.ofNullable(request.getParameter("reqType")).orElse("");
		String unitId= Optional.ofNullable(request.getParameter("groupId")).orElse(""); 
		String serviceType=Optional.ofNullable(request.getParameter("serviceType")).orElse("0");  
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class, logStr+" :: UnitId = "+unitId+"||reqType="+reqType+"||serviceType="+serviceType);
		
		 if (userAlias.isEmpty()){
			 travellerDetails.setMessage("Enter Traveler Personal No.");
			 return travellerDetails;
		 }
		 
			 try {
		
		 Optional<VisitorModel> visitorOptional  =userServices.getCompleteUser(userAlias.get());
		 
		 if(visitorOptional.isEmpty()) {
			 travellerDetails.setMessage("Unable to get Traveler Personal Information");
			 return travellerDetails;
		 }
		 
				 travler = visitorOptional.get();
				 travellerDetails.setUserID(travler.getUserId());
			 } catch(Exception e) {
					DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
					 travellerDetails.setMessage("Unable to get Traveler Personal Information");
					 return travellerDetails;
			 }
		 
		Optional<OfficeModel> portalVisitorOffice= officesService.getOfficeByUserId(portalVisitor.getUserId());
		Optional<OfficeModel> travelerOffice= officesService.getOfficeByUserId(travler.getUserId());
		 
		Optional<DODServices> dodServices= masterServices.getServiceByServiceId(travler.getUserServiceId());
		Optional<Category> category= categoryServices.getCategory(travler.getUserServiceId(), travler.getCategoryId());
		Optional<Level> level=  levelServices.getLevel(travler.getLevelId());
		 
		 
		 if(UserStatus.valueOf(travler.getUserStatus()).ordinal()==1) {
			 travellerDetails.setMessage("This Personal No. marked as non effective.");
			 return travellerDetails;
		 }
		 
		 if(travler.getRequestBlock().ordinal()==0) {
			 travellerDetails.setMessage(travler.getRequestBlockRemark());
			 return travellerDetails;
		 }
		 
		 if(travler.getApprovalState().ordinal()!=1) {
			 if(travler.getApprovalState().ordinal()==3){
				 travellerDetails.setMessage("Grade Pay for the traveller has been increased to 5400. Kindly contact DTS Helpline through call or email.");
			 }else {
				 travellerDetails.setMessage("Please approve the traveller first. ");
				
			 }
			 
			 
			 return travellerDetails;
		 }
		 
		 
		 if (!reqType.equalsIgnoreCase("exceptionalBooking"))
			{
				if (!reqType.equalsIgnoreCase("international")){
					String unitCheck=JourneyRequestUtils.validateVisitorUnit(portalVisitorOffice,travelerOffice);
					if(!(unitCheck.equals("")))
					 {
						travellerDetails.setMessage(unitCheck);
						 return travellerDetails;
					 }
				}
				
			}else{
				
				if(JourneyRequestUtils.validateVisitorUnit(portalVisitorOffice,travelerOffice).equals(""))
				{
					travellerDetails.setMessage("You Cannot Book Exceptional Booking For Same Unit");
					 return travellerDetails;
					 
				}
				
			}
		 
		 boolean checkCDAOAccountNo=JourneyRequestUtils.validateCDAOAccountNo(travler,category);
		 if (!checkCDAOAccountNo) 
		 {
			 travellerDetails.setMessage("Kindly update CDA(O) A/c number before request creation. ");
			 return travellerDetails;
		 }
		 
		 
		 if (reqType.equalsIgnoreCase("international")){
			 String intTravelAllow="No";
			 
			 if(portalVisitorOffice.isPresent()){intTravelAllow=portalVisitorOffice.get().getIntTravelAllow();}
			 if("No".equalsIgnoreCase(intTravelAllow)){
				 
				 travellerDetails.setMessage("This unit is not allowed to create international journey request. ");
				 return travellerDetails;
			 }
			 
		 }
		 
		 /*  Block For Coast Guard Service */
		 boolean isArmedPersonalInsideCoastGuard=false;
		 String travlerUnitService=travler.getServiceId(); 
		 String travlerSelfService=travler.getUserServiceId();
		 
		 if(travlerUnitService!=null && travlerUnitService.equals(PcdaConstant.COAST_GUARD_SEVICE_ID) && 
				 travlerSelfService!=null && !travlerSelfService.equals(PcdaConstant.COAST_GUARD_SEVICE_ID))
		 {
				 isArmedPersonalInsideCoastGuard=true;
			
		 }
		
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class, logStr+"isArmedPersonalInsideCoastGuard-"+isArmedPersonalInsideCoastGuard+"|travlerUnitService-"+travlerUnitService+"|travlerSelfService-"+travlerSelfService);
		 
		 // Validating Level Update
		  
		   int permissionType = -1;
		   
		   if(dodServices.isPresent()){
			 permissionType=dodServices.get().getPermissionType().ordinal();             // 0 -- Grade Pay , 1-- 7CPC 
		    }
			
			
			if(permissionType == 1){
			 String levelId=travler.getLevelId();
			 
			 
			 if(null==levelId || "".equals(levelId.trim()) || "null".equals(levelId.trim())){
				 
				 travellerDetails.setMessage("Please update your level.");
				 return travellerDetails;
			 }
			 
			}
			
		
			synchronized (this)
			{
				
				 BigInteger personalUserId=travler.getUserId();
				
				 String processName="GET_PERSONAL_INFO";
				 String groupId=portalVisitorOffice.get().getGroupId();
				 
				 List<PreventProcessDetails> processDetails=getProcessData(personalUserId,processName);
				 
			 
				 if(processDetails==null||processDetails.isEmpty())
				 {
					
					 PreventProcessDetails preventProcessDetails=new PreventProcessDetails();
					 preventProcessDetails.setPersonalId(personalUserId);
					 preventProcessDetails.setPersonalNumber(travler.getUserAlias());
					 preventProcessDetails.setProcessName(processName);
					 preventProcessDetails.setProcessStatus(0);
					 preventProcessDetails.setUnitLoginId(portalVisitor.getUserId());
					 preventProcessDetails.setGroupId(groupId);
					 
					 ResponseEntity<PreventProcessResponse> processResponse= template.postForEntity(PcdaConstant.PREVENT_PROCESS_BASE_URL+"/save", preventProcessDetails, PreventProcessResponse.class);
					
					
					
				 }
				 else
				 {
					 //-----------For Existing User
					
					 PreventProcessDetails preventProcessDetails=processDetails.get(0);
				 	
					
					
					if(preventProcessDetails.getProcessStatus().equals(0) )
					{	 
						Optional<User> unitVisitor= userServices.getUser(preventProcessDetails.getUnitLoginId());
						Optional<OfficeModel> unit=officesService.getOfficeByGroupId(preventProcessDetails.getGroupId());
						 
						 String unitName="";
						 String unitUser="";
						 if(unit.isPresent()) {unitName=unit.get().getName();}
						 if(unitVisitor.isPresent()) {unitUser=unitVisitor.get().getUserAlias();}
						 
						 travellerDetails.setMessage("As another user["+unitUser+"] Of "+unitName+" is working for personal number["+preventProcessDetails.getPersonalNumber()+"].Kindly wait...");
						 return travellerDetails;
					}else
					{
						PreventProcess preventProcess=new PreventProcess();
						preventProcess.setLoginUserId(portalVisitor.getUserId());
						preventProcess.setGroupId(groupId);
						preventProcess.setProcessState(0);
						preventProcess.setPersonalId(preventProcessDetails.getPersonalId());
						preventProcess.setProcess("GET_PERSONAL_INFO");
						preventProcess.setActionType("update"); 
						
						template.put(PcdaConstant.PREVENT_PROCESS_BASE_URL+"/update", preventProcess);
					}
				 }
			 
			 }
			
			
			// end of syncronization block
			
						travellerDetails.setUserID(travler.getUserId());
						travellerDetails.setTravlerName(travler.getName());
						
						if(dodServices.isPresent()) {
							travellerDetails.setServiceName(dodServices.get().getServiceName());
							travellerDetails.setServiceId(dodServices.get().getServiceId());
						}else {
							travellerDetails.setServiceName("");
							travellerDetails.setServiceId("");
						}
						
						if(category.isPresent()) {
							travellerDetails.setCategoryName(category.get().getCategoryName());
							travellerDetails.setCategoryId(category.get().getCategoryId());
						}else {
							travellerDetails.setCategoryName("");
							travellerDetails.setCategoryId("");
						}
						
						if(level.isPresent()) {
							travellerDetails.setLevelId(level.get().getLevelId());
							travellerDetails.setLevelName(level.get().getLevelName());
						}else {
							travellerDetails.setLevelId("");
							travellerDetails.setLevelName("");
						}
						
						
						/*  ----------------   Getting and Validation User PAO and UnitOffice Start-------------*/
						  
							  
							  String paoGroupId=travler.getTravelerProfile().getAccountOffice();
							  Optional<OfficeModel> officeModel= officesService.getOfficeByGroupId(paoGroupId);
							  String paoGroupName= "";
							  if(officeModel.isPresent())
							  {
								 paoGroupName= officeModel.get().getName();
								
							  }
							 
							  String airPaoGroupId=travler.getTravelerProfile().getAirAccountOffice();
							  Optional<OfficeModel> airOfficeModel= officesService.getOfficeByGroupId(airPaoGroupId);
							  String airPaoGroupName= "";
							  if(airOfficeModel.isPresent())
							  {
								  airPaoGroupName=airOfficeModel.get().getName();  
							  }
							 
							  String cdaoAccNo=travler.getTravelerProfile().getAccountNumber();
							  if(null==cdaoAccNo)cdaoAccNo="";
							  
							  travellerDetails.setRailPAOId(paoGroupId);
							  travellerDetails.setAirPAOId(airPaoGroupId);
							  travellerDetails.setOffice(paoGroupName);
							  travellerDetails.setAirOffice(airPaoGroupName);
							  travellerDetails.setCdaoAccNo(cdaoAccNo);
							 
							 
						 
						
						/*  ----------------   Getting and Validation User PAO and UnitOffice End -------------*/
						  
							  
						  if(PcdaConstant.CIVILIAN_SEVICE_ID.equals(portalVisitor.getServiceId()) 
									|| PcdaConstant.CIVILIAN_SEVICE_ID.equals(portalVisitor.getUserServiceId()))
							{
							  
							  setAllCivilianTravelTypeXml(travellerDetails);
							}else{
								setAllTravelTypeXml(travellerDetails);
							}
						  
						
					} catch (Exception e) {
						DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
						travellerDetails.setMessage("Select mandatory fields");
					}
		 
					DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class, logStr+"Finally Traveler :"+travellerDetails.toString());
					
		 return travellerDetails;
	}
	
	
private List<PreventProcessDetails> getProcessData(BigInteger personalUserId, String processName) {
	DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestAjaxService.class,"personalUserId :: "+personalUserId+" | processName::"+processName);
		List<PreventProcessDetails> processDetails=new ArrayList<>();
		try {
		ResponseEntity<PreventProcessResponse> responseEntity = template.exchange(
				PcdaConstant.PREVENT_PROCESS_BASE_URL + "/getProcess/"+personalUserId+"/"+processName, HttpMethod.GET, null,
				new ParameterizedTypeReference<PreventProcessResponse>() {});
		
		PreventProcessResponse processResponse = responseEntity.getBody();

		if (null != processResponse && (processResponse.getErrorCode() == 200) && null != processResponse.getResponseList()) {
			processDetails.addAll(processResponse.getResponseList());
		}
		}catch (Exception e) {
			
			DODLog.printStackTrace(e, JourneyRequestAjaxService.class, LogConstant.JOURNEY_REQUEST_LOG);
					}
			
		return processDetails;
	}



	private void setAllTravelTypeXml(TravellerDetails travellerDetails) {
		
		List<TravelType> travelTypes=travelTypeServices.getAllTravelType(1);
		
		travellerDetails.setTravelType(travelTypes.stream().
				collect(Collectors.toMap(TravelType::getTravelTypeId, TravelType::getTravelName)));
		
	}



	private void setAllCivilianTravelTypeXml(TravellerDetails travellerDetails) {
		
		List<TravelType> travelTypes=travelTypeServices.getAllTravelType(1);
		List<String> travelType=Arrays.asList("100001","100002","100005","100006");
		
		travellerDetails.setTravelType(travelTypes.stream().filter(obj->travelType.contains(obj.getTravelTypeId())).
				collect(Collectors.toMap(TravelType::getTravelTypeId, TravelType::getTravelName)));
	}

}
