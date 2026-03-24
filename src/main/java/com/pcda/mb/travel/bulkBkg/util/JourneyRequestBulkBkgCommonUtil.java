package com.pcda.mb.travel.bulkBkg.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcda.common.model.TravelType;
import com.pcda.mb.travel.journey.util.JourneyRequestCommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
@Service
public class JourneyRequestBulkBkgCommonUtil {
	
	@Autowired
	private TravelTypeBulkBkgService travelTypeBulkBkgService;
	
	
	public String getTravelTypeNameByID(String travelTypeID){
		
			String travelTypeName="";
			try 
			{
				TravelType type=travelTypeBulkBkgService.getTravelType(travelTypeID);
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
