package com.pcda.mb.travel.journey.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcda.common.model.TravelType;
import com.pcda.common.services.TravelTypeServices;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;

@Service
public class JourneyRequestCommonUtil {
	
	@Autowired
	private TravelTypeServices travelTypeServices;

	public String getTravelTypeNameByID(String travelTypeID){
		 
			String travelTypeName="";
			try 
			{
				TravelType type=travelTypeServices.getTravelType(travelTypeID);
				if(null!=type) {
					travelTypeName=type.getTravelName();
				}
			} catch (Exception e) {
				DODLog.printStackTrace(e, JourneyRequestCommonUtil.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
			 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyRequestCommonUtil.class," travelTypeName --"+travelTypeName);
			
			return travelTypeName;
			
		}
	
}
