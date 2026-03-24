package com.pcda.mb.travel.journey.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.DODServices;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.services.MasterServices;
import com.pcda.common.services.OfficesService;
import com.pcda.login.model.LoginUser;
import com.pcda.login.model.SessionVisitor;
import com.pcda.mb.travel.journey.model.CombineRequestRoute;
import com.pcda.mb.travel.journey.model.CreateJourneyRequest;
import com.pcda.mb.travel.journey.model.JourneyDetailsDTO;
import com.pcda.mb.travel.journey.model.JourneyMainDTO;
import com.pcda.mb.travel.journey.model.JourneyRequestDTO;
import com.pcda.mb.travel.journey.model.PreventProcess;
import com.pcda.mb.travel.journey.model.TravelRequestViewResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class JourneyRequestService {
	
	@Autowired
	private RestTemplate template;
	
	@Autowired
	private OfficesService officesService;
	
	@Autowired
	private MasterServices masterServices;

	public CreateJourneyRequest createJourneyRequest(LoginUser visitor) {
		
		String loginVisitorServiceId="";
		
		
		if(visitor.getUserServiceId()!=null && visitor.getUserServiceId().length()>2){
			  loginVisitorServiceId=visitor.getUserServiceId();
		}else{
			  loginVisitorServiceId=visitor.getServiceId();
		}
		
		CreateJourneyRequest createJourneyRequest=new CreateJourneyRequest();
		
		
		Optional<OfficeModel> modelOptional=  officesService.getOfficeByUserId(visitor.getUserId());
		if(modelOptional.isPresent()) {
			OfficeModel model=modelOptional.get();
			createJourneyRequest.setGroupId(model.getGroupId()); 
			createJourneyRequest.setUnitServiceId(model.getServiceId());
			createJourneyRequest.setIntTravelAllow(model.getIntTravelAllow());
		}
		
		Optional<DODServices> dodServices= masterServices.getServiceByServiceId(loginVisitorServiceId);
		if(dodServices.isPresent()) {
		createJourneyRequest.setServiceType(dodServices.get().getArmedForces().ordinal()); 
		}
		
		PreventProcess preventProcess=new PreventProcess();
		preventProcess.setLoginUserId(visitor.getUserId());
		preventProcess.setProcess("GET_PERSONAL_INFO");
		preventProcess.setProcessState(1);
		preventProcess.setGroupId(createJourneyRequest.getGroupId()); 
		preventProcess.setActionType("logout"); 
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestService.class, "Request For Prevent Process:"+preventProcess.toString());
		try {
		template.put(PcdaConstant.PREVENT_PROCESS_BASE_URL+"/update", preventProcess);
		}catch(Exception e) {
			DODLog.printStackTrace(e, JourneyRequestService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		return createJourneyRequest;
	}

	public TravelRequestViewResponse postRequestData(HttpServletRequest request, JourneyRequestDTO journeyRequestDTO) {
		TravelRequestViewResponse response = null;
		try {
			JourneyMainDTO journeyMainDTO = new JourneyMainDTO();
			journeyMainDTO.initializeVariable(request);

			SessionVisitor sessionVisitor = SessionVisitor.getInstance(request.getSession());
			journeyRequestDTO.setLoginUserId(sessionVisitor.getLoginUser().getUserId());

			List<JourneyDetailsDTO> journeyDetails = null;
			String[] journeyTypes = request.getParameterValues("journeyType");
			if (null != journeyTypes && journeyTypes.length > 0) {
				journeyDetails = new ArrayList<>();
				for (int index = 0; index < journeyTypes.length; index++) {
					String journeyType = journeyTypes[index];
					if (journeyType.equals("0")) {
						JourneyDetailsDTO onwardsJourneyDetails = new JourneyDetailsDTO();
						onwardsJourneyDetails.initializeOnwardsVariable(request);
						journeyDetails.add(onwardsJourneyDetails);
					} else if (journeyType.equals("1")) {
						JourneyDetailsDTO returnJourneyDetails = new JourneyDetailsDTO();
						returnJourneyDetails.initializeReturnVariable(request);
						journeyDetails.add(returnJourneyDetails);
					}
				}
			}

			journeyRequestDTO.setJourneyMainDTO(journeyMainDTO);
			validateTezpurAirport(journeyDetails);

			if (null != journeyDetails && !journeyDetails.isEmpty()) {
				journeyRequestDTO.setJourneyDetails(journeyDetails);
			}

			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestService.class, "journeyRequestDTO save model" + journeyRequestDTO);
			response = template.postForObject(PcdaConstant.REQUEST_BASE_URL + "/saveJourneyRequest", journeyRequestDTO, TravelRequestViewResponse.class);
			
			if(response!=null  && response.getErrorCode()==200 && response.getResponse() != null) {
			
			response.getResponse().getTravelRequestList().forEach(e->{
				List<CombineRequestRoute> combinedList = new ArrayList<>();
		
				if(Optional.ofNullable(e.getRailRequestRoute()).isPresent()) {
				e.getRailRequestRoute().forEach(item->{
					CombineRequestRoute combinedModel = new CombineRequestRoute();
					combinedModel.setEntitledClass(item.getEntitledClass());
					combinedModel.setFrmStation(item.getFrmStation());
					combinedModel.setJourneyDate(item.getJourneyDate());
					combinedModel.setJourneyMode(item.getJourneyMode());
					combinedModel.setSeqNo(item.getSeqNo());
					combinedModel.setToStation(item.getToStation());
					combinedList.add(combinedModel);
				});
				}
				
				if(Optional.ofNullable(e.getAirRequestRoute()).isPresent()) {
				CombineRequestRoute aircombinedModel = new CombineRequestRoute();
				aircombinedModel.setEntitledClass(e.getAirRequestRoute().getEntitledClass());
				aircombinedModel.setFrmStation(e.getAirRequestRoute().getFrmStation());
				aircombinedModel.setJourneyDate(e.getAirRequestRoute().getJourneyDate());
				aircombinedModel.setJourneyMode(e.getAirRequestRoute().getJourneyMode());
				aircombinedModel.setSeqNo(e.getAirRequestRoute().getSeqNo());
				aircombinedModel.setToStation(e.getAirRequestRoute().getToStation());
				combinedList.add(aircombinedModel);
				}
				
				Collections.sort(combinedList,(s1,s2)->s1.getJourneyDate().compareTo(s2.getJourneyDate()));
				e.setCombinedBean(combinedList);
				
			});

			
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyRequestService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestService.class, "Save Journey Request " + response);
		return response;
	}
	
	
   // To validate Tezpur Airport
	private void validateTezpurAirport(List<JourneyDetailsDTO> journeyDetails) {
		final String airPortName = "Donyi Polo Airport,Itanagar,Assam,India(HGI)";

		for (JourneyDetailsDTO details : journeyDetails) {
			if(!details.getTravelMode().equals("2")) {
		
			String origin = details.getOrigin();
			String dest = details.getDestination();
			String orgCode="";
			String destCode ="";
			
			if(origin!=null && !origin.equals("")) {
			orgCode = origin.substring(origin.lastIndexOf("(") + 1, origin.lastIndexOf(")"));
			}
			if(dest!=null && !dest.equals("")) {
			destCode = dest.substring(dest.lastIndexOf("(") + 1, dest.lastIndexOf(")"));
			}
			if (orgCode.equalsIgnoreCase("TEZ")) {

				details.setOrigin(airPortName);

			} else if (destCode.equalsIgnoreCase("TEZ")){

				details.setDestination(airPortName);
		}
			}
		}

	}

}
