package com.pcda.mb.travel.journey.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.pcda.common.model.Category;
import com.pcda.common.model.OfficeModel;
import com.pcda.common.model.TRScheme;
import com.pcda.common.model.VisitorModel;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JourneyRequestUtils {

	public static String validateVisitorUnit(Optional<OfficeModel> portalVisitorOffice, Optional<OfficeModel> travelerOffice) {
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestUtils.class, "[validateVisitorUnit] portalVisitorOffice: "+portalVisitorOffice +" | travelerOffice: "+travelerOffice);
		
		 String check="";
		try
		{
			 String sessionVisitorGroupId="";	
			 String travelerGroupId="";
			 if(portalVisitorOffice.isPresent()) {
				 sessionVisitorGroupId=portalVisitorOffice.get().getGroupId();
			 }
			 if(travelerOffice.isPresent()) {
				 travelerGroupId=travelerOffice.get().getGroupId();
			 }
				
			 if (sessionVisitorGroupId==null || sessionVisitorGroupId.length()==0) {
				 check="Traveler must belongs to a group";
				 return check;
			 }
			 if (travelerGroupId==null || travelerGroupId.length()==0) {
				 check="Transfer Request for this profile is pending. Kindly get it approved from CO token before creation of travel request";
				 return check;
			 }
		
			 if (!sessionVisitorGroupId.equals(travelerGroupId)) {
				check="You cannot Book Ticket for the Traveler who belongs to different Unit";
				return check;
			 }
		 }
		 catch (Exception e) {
			DODLog.printStackTrace(e, JourneyRequestUtils.class, LogConstant.JOURNEY_REQUEST_LOG);
			check=e.toString();
		}
		
		 return check;
	}

	public static boolean validateCDAOAccountNo(VisitorModel travler, Optional<Category> category) {
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestUtils.class, "[validateCDAOAccountNo] travler: "+travler +" | category: "+category);
		
		boolean flag=true;
		try
		{
			String travlerSelfService= travler.getUserServiceId();
			String categoryName="";
			if(category.isPresent()) {categoryName=category.get().getCategoryName();}
			
			if((travlerSelfService.equalsIgnoreCase(PcdaConstant.ARMY_SEVICE_ID) || travlerSelfService.equalsIgnoreCase(PcdaConstant.MNS_SEVICE_ID)) 
					&& categoryName.equalsIgnoreCase("OFFICER")){
				
				String accountNumber= travler.getTravelerProfile().getAccountNumber();
				Date creationDate=travler.getCreationDate();
				
				
				 
				if(null==accountNumber || "".equals(accountNumber.trim()) || "null".equals(accountNumber.trim())){
			        Date nowDate = Calendar.getInstance().getTime();
			        long difference = Math.abs(creationDate.getTime() - nowDate.getTime());
			        long differenceDates = difference /86400000;
			        long dayDifference = differenceDates+1;
			        
			       
			        
			        if(dayDifference>30){
			        	flag=false;
			        }
				 }
			}
			
	     }catch (Exception e){
	    	 DODLog.printStackTrace(e, JourneyRequestUtils.class, LogConstant.JOURNEY_REQUEST_LOG); 
	     }
	
	     return flag;
	 }

	public static Map<String, String> getSchemeDetailsMap(TRScheme trRuleSchemeDtls) 
	{
		Map<String, String> schemeDetailsMap=new HashMap<>();
		 try 
		 {
		    	int blockStartYear =trRuleSchemeDtls.getBlockStartYear().intValue();
		    	int blockEndYear=trRuleSchemeDtls.getBlockEndYear().intValue();
		    	int maxYearsPerBlock=trRuleSchemeDtls.getMaxYearsPerBlock().intValue();
		    	int maxYearsPerSubBlock=trRuleSchemeDtls.getMaxYearsPerSubblock().intValue();
		    	
		    	
		    	String currentBlockYear=blockStartYear+"-"+blockEndYear;
		    	String previousBlockYear=(blockStartYear-maxYearsPerBlock)+"-"+(blockEndYear-maxYearsPerBlock);
		    	String nextBlockYear=(blockStartYear+ maxYearsPerBlock)+"-"+(blockEndYear+maxYearsPerBlock);
		    	
		    	schemeDetailsMap.put(String.valueOf("previousBlockYear"),previousBlockYear);
		    	schemeDetailsMap.put(String.valueOf("currentBlockYear"),currentBlockYear);
		    	schemeDetailsMap.put(String.valueOf("nextBlockYear"),nextBlockYear);
		    	
		    	String currentSubBlockYear="";
		    	String previousSubBlockYear="";
		    	String nextSubBlockYear="";
		    	
		    	String schemeAppliedDate="";
		    	
		    	int subBlockCount=maxYearsPerSubBlock;
		    	if(subBlockCount==1)schemeDetailsMap.put(String.valueOf("SchemeType"),"Yearly");
		    	else schemeDetailsMap.put(String.valueOf("SchemeType"),"BlockYearly");
		    	
		    	Calendar calendar=Calendar.getInstance();
		    	int currYear=calendar.get(Calendar.YEAR);
		    	
		    	if(trRuleSchemeDtls.getSchemeAppliedDate()!=null)
		    	{
		    			schemeAppliedDate= CommonUtil.formatDate(trRuleSchemeDtls.getSchemeAppliedDate(),"dd-MM-yyyy");
		    	    	currentSubBlockYear=currYear+"";
			    		previousSubBlockYear=(currYear- 1)+"";
				    	nextSubBlockYear=(currYear+ 1)+"";
				    	
				    	
				    	
		    	}else
		    	{	
		    		String[] currentBlockYearArr=currentBlockYear.split("-");
		    		String currentBlockSubBlock1=currentBlockYearArr[0]+"-"+(Integer.parseInt(currentBlockYearArr[0])+1);
		    		String currentBlockSubBlock2=(Integer.parseInt(currentBlockYearArr[1])-1)+"-"+currentBlockYearArr[1];
		    		
		    		if(currentBlockSubBlock1.contains(String.valueOf(currYear)))
		    		{
		    			
		    			previousSubBlockYear=(blockStartYear-2)+"-"+(blockStartYear-1);
		    			currentSubBlockYear=currentBlockSubBlock1;
		    			nextSubBlockYear=currentBlockSubBlock2;
		    			
		    	    }else if(currentBlockSubBlock2.contains(currYear+""))
		    	    {
		    			previousSubBlockYear=currentBlockSubBlock1;
		    			currentSubBlockYear=currentBlockSubBlock2;
		    			
		    			nextSubBlockYear=(blockEndYear+1)+"-"+(blockEndYear+2);
		    		}
		    		if(currentBlockYear.equalsIgnoreCase(currentSubBlockYear)){
			    		 schemeDetailsMap.put(String.valueOf("bothBlockAreSame"),"Yes");
			    	}else
			    	{
			    		 schemeDetailsMap.put(String.valueOf("bothBlockAreSame"),"No");
			 	    }
			    	
		    		
			    	
		    	}
		    	
		    	schemeDetailsMap.put(String.valueOf("previousSubBlockYear"),previousSubBlockYear);
		    	schemeDetailsMap.put(String.valueOf("currentSubBlockYear"),currentSubBlockYear);
		    	schemeDetailsMap.put(String.valueOf("nextSubBlockYear"),nextSubBlockYear);
		    	schemeDetailsMap.put(String.valueOf("schemeAppliedDate"),schemeAppliedDate);
		    
		 }catch(Exception e){
			 DODLog.printStackTrace(e, JourneyRequestUtils.class, LogConstant.JOURNEY_REQUEST_LOG); 
		 }
	 
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, JourneyRequestUtils.class, "TravelRequestCommonUtil:getSchemeDetailsMap()->"+schemeDetailsMap);
		 return schemeDetailsMap;
	}

}
