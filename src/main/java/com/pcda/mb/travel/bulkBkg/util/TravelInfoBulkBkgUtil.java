package com.pcda.mb.travel.bulkBkg.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcda.common.model.FamilyDetailsModel;
import com.pcda.common.model.TRScheme;
import com.pcda.common.model.TravelRule;
import com.pcda.common.model.VisitorModel;
import com.pcda.mb.travel.bulkBkg.model.FamilyDetailBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.model.TravellerInfoBulkBkgModel;
import com.pcda.mb.travel.bulkBkg.model.YearWiseBulkBkgModel;
import com.pcda.mb.travel.journey.service.TravelInfoService;
import com.pcda.mb.travel.journey.util.TravelInfoUtil;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.Gender;
import com.pcda.util.LogConstant;
import com.pcda.util.YesOrNo;

@Service
public class TravelInfoBulkBkgUtil {
	
	
	@Autowired
	private JourneyCountBulkBkgUtil countBizlogic;

	@Autowired
	private JourneyRequestBulkBkgCommonUtil trReqCommonUtil;
	

	public void  getCivilianTravellorFamilyDetailsForLtc(VisitorModel visitor,List<Integer> eligibleMember,String travelTypeId,TravelRule travelRule, TravellerInfoBulkBkgModel infoModel) 
	{
		
		 String logStr=":getCivilianTravellorFamilyDetailsForLtc("+visitor.getName()+"):";
		
		 boolean isOnSuspension=Optional.ofNullable(visitor.getTravelerProfile().getOnSuspension()).orElse(YesOrNo.NO).ordinal()==0;
		 
		 Set<FamilyDetailsModel> familyTD=visitor.getFamilyDetails();
	     
		 Map<String, Integer> eligblMap=getEligibleMemberCountListForCivilian(travelRule);
		 Map<String,String> schemeDetailsMap=getSchemeDetailsMap(travelRule);
		
		 String travelTypeName=trReqCommonUtil.getTravelTypeNameByID(travelTypeId);
	 
		 String currentYearLTCAvailed=visitor.getTravelerProfile().getLtcCurrentYear();
		 String currentSubBlockLTCAvailed=visitor.getTravelerProfile().getLtcCurrentSubBlock();
		 String previousSubBlockLTCAvailed=visitor.getTravelerProfile().getLtcPreviousSubBlock();
			
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"::schemeDetailsMap->"+schemeDetailsMap);
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"::EligibleMember->"+eligibleMember);
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"::eligblMap->"+eligblMap);
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"::travelTypeId="+travelTypeId+"||travelTypeName="+travelTypeName);
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"::currentYearLTCAvailed="+currentYearLTCAvailed+"||currentSubBlockLTCAvailed="+currentSubBlockLTCAvailed+"||previousSubBlockLTCAvailed="+previousSubBlockLTCAvailed);
		 
		 List<String> ltcAvailYear=new ArrayList<>();
		 ltcAvailYear.add(currentYearLTCAvailed);
		 ltcAvailYear.add(currentSubBlockLTCAvailed);
		 ltcAvailYear.add(previousSubBlockLTCAvailed);
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"::ltcAvailYear->"+ltcAvailYear);
			
		 List<FamilyDetailsModel> spouseDependent=new ArrayList<>();
		 List<FamilyDetailsModel> sonDependent=null;
		 List<FamilyDetailsModel> daughterDependent=null;
		 List<FamilyDetailsModel> otherDependent=null;
	      
		 Map<String,FamilyDetailsModel> sonMap = new HashMap<>();
		 Map<String,FamilyDetailsModel> daughterMap = new HashMap<>(); 
		 Map<String,FamilyDetailsModel> otherMap = new HashMap<>();
		
		 List<String> yearWiseList=new ArrayList<>();
		
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"::schemeAppliedDate->"+schemeDetailsMap.get("schemeAppliedDate")+"::SchemeType="+schemeDetailsMap.get("SchemeType"));
		 if(schemeDetailsMap.get("SchemeType")!=null && (schemeDetailsMap.get("SchemeType")).equals("Yearly")){
			 // USing Yearly Scheme
			 Calendar calendar=Calendar.getInstance();
			 String previousYr=String.valueOf(calendar.get(Calendar.YEAR)-1);
			 String currentYr=String.valueOf(calendar.get(Calendar.YEAR));
		     String nextYr=String.valueOf(calendar.get(Calendar.YEAR)+1);
		     yearWiseList.add(previousYr);
		     yearWiseList.add(currentYr);
		     yearWiseList.add(nextYr);
		 
		 }else
		 { 
		     String previousSubBlockYear= schemeDetailsMap.get("previousSubBlockYear");
		     String currentSubBlockYear=schemeDetailsMap.get("currentSubBlockYear");
		     String nextSubBlockYear =schemeDetailsMap.get("nextSubBlockYear");
		     yearWiseList.add(previousSubBlockYear);
		     yearWiseList.add(currentSubBlockYear);
		     yearWiseList.add(nextSubBlockYear);
		  }
		
		  
	    
		 List<FamilyDetailBulkBkgModel> familyDetail=new ArrayList<>();
		 
		 // Case For Self
		 if(eligibleMember.contains(Integer.valueOf(0)))
		 {
			 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
			 
			 String name=visitor.getName();
			 List<YearWiseBulkBkgModel> yearWise=new ArrayList<>();
		    	
		    if(travelTypeName.contains("LTC"))
		    {
		    	yearWiseList.forEach(e->{
		    		if(isOnSuspension)
		    		{
		    			if(eligblMap.get("selfCount").intValue()!=-1){
		    				YearWiseBulkBkgModel model=new YearWiseBulkBkgModel();
		    				model.setYear(e);
					 	 	model.setJourneyCheck("false");
					 	 	model.setJourneyCheckRet("false");
					 	 	model.setReason("You Are On Suspension");
					 	 	yearWise.add(model);
				 	 	
		    			}
		    			
			 	 	}
		    		else
		    		{
		    			
		    			if(ltcAvailYear.contains(e)){
		    				YearWiseBulkBkgModel model=new YearWiseBulkBkgModel();
		    				model.setYear(e);
					 	 	model.setJourneyCheck("false");
					 	 	model.setJourneyCheckRet("false");
					 	 	model.setReason("Already Availed Journey On This TR Rule");
					 	 	yearWise.add(model);
		    			}else
		    			{
		    				List<Integer> reqCountList=countBizlogic.getCivilianUserJourneyCountBySeqNoForLtc(visitor.getUserId(), -1, travelTypeId, travelRule.getTrRuleId(), e);
					    	 
		    				YearWiseBulkBkgModel model=new YearWiseBulkBkgModel();
		    				model.setYear(e);
				 	 	    
				 	 	    if(eligblMap.get("selfCount").intValue()!=-1 && (eligblMap.get("selfCount").intValue()<=reqCountList.get(0).intValue())){
				 	 	    	model.setJourneyCheck("false");
				 	 	    	model.setReason("Already Availed Journey On This TR Rule");
				 	 	    }else{
				 	 	    	model.setJourneyCheck("true");
				 	 	    }
				 	 	    if(eligblMap.get("selfCount").intValue()!=-1 && (eligblMap.get("selfCount").intValue()<=reqCountList.get(1).intValue())){
				 	 	    	model.setJourneyCheckRet("false");
				 	 	    	model.setReason("Already Availed Journey On This TR Rule");
				 	 	    	
				 	 	    }else{
				 	 	    	
				 	 	    	model.setJourneyCheckRet("true");
				 	 	    }
				 	 	  yearWise.add(model);
		    			}
		    			
		    		}
		    		
		 	 	    
		    	});
		    	
		    }
		    
		    familyDetailModel.setYearWise(yearWise);
		       
	         familyDetailModel.setName(name);
		     familyDetailModel.setRelationShip("Self");
		     familyDetailModel.setRelationCode(0);
		     familyDetailModel.setDob(CommonUtil.formatDate(visitor.getDateOfBirth(), "dd-MM-yyyy"));
		     familyDetailModel.setGender(visitor.getGender().getDisplayValue());
		     familyDetailModel.setGenderCode(visitor.getGender().ordinal());
		     familyDetailModel.setDepSeqNo(-1);
		     familyDetailModel.setErsPrintFamilyName(visitor.getTravelerProfile().getErsPrintName());
		     if(!yearWise.isEmpty()) {
		    	 familyDetailModel.setReason(yearWise.get(0).getReason());
		     }else {
		    	 familyDetailModel.setReason("");
		     }
		     
		    
		     familyDetail.add(familyDetailModel);
		 }
		 
		 // end self
		 
		 if (familyTD!= null && !familyTD.isEmpty())  
		 {
			 familyTD.forEach(e->{
			
				 String relationType=e.getRelation().getDisplayValue();
				 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"relationType->"+relationType+"||Status="+e.getStatus()+"||Is In EligibleMember="+eligibleMember.contains(e.getRelation().ordinal()));
				 
				 if(relationType.equals("Spouse") && eligibleMember.contains(e.getRelation().ordinal()) && e.getStatus().ordinal()==1){
					  spouseDependent.add(e);
				  }
				  else if(relationType.equals("Son") && eligibleMember.contains(e.getRelation().ordinal()) && e.getStatus().ordinal()==1){
					  sonMap.put( CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")),e);
				  }
				  else if(relationType.equals("Daughter") && eligibleMember.contains(e.getRelation().ordinal()) && e.getStatus().ordinal()==1){
					  daughterMap.put(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")),e);
				  }
				  else if(eligibleMember.contains(e.getRelation().ordinal()) &&  e.getStatus().ordinal()==1){
				      otherMap.put(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")),e);
				  }
			 });
		 }
			 
		 sonDependent=new ArrayList<>(sonMap.values()); 
		 daughterDependent=new ArrayList<>(daughterMap.values()); 
		 otherDependent=new ArrayList<>(otherMap.values());
		 
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"sonDependent->"+sonDependent+"||daughterDependent="+daughterDependent+"||otherDependent="+otherDependent);
			
		 Collections.sort(sonDependent,byValue); 
		 Collections.sort(daughterDependent,byValue); 
		 Collections.sort(otherDependent,byValue); 
		 
		 if (familyTD!= null && !familyTD.isEmpty()) 
		 {
			
			 spouseDependent.forEach(e->{

		 		 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
		 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
		 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
		 		familyDetailModel.setGender(e.getGender().getDisplayValue());
		 		familyDetailModel.setGenderCode(e.getGender().ordinal());
		 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
		 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
		 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
		 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
		 		familyDetailModel.setDOIIDate(CommonUtil.formatDate(e.getPartDate(), "dd/MM/yyyy"));
		 		familyDetailModel.setDOIIPartNo(e.getPartNo());
		 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
		 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
				
				  if(travelTypeName.contains("LTC"))
				  {
					  List<YearWiseBulkBkgModel> yearWise=new ArrayList<>();
					  yearWiseList.forEach(obj->{
						  

						  	if(ltcAvailYear.contains(obj))
						  	{
						  		YearWiseBulkBkgModel yearWiseModel=new YearWiseBulkBkgModel();
						  		yearWiseModel.setYear(obj);
						  		yearWiseModel.setJourneyCheck("false");
						  		yearWiseModel.setJourneyCheckRet("false");
						  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
						  		yearWise.add(yearWiseModel);
			    			}else
			    			{
			    				List<Integer> reqCountList=countBizlogic.getCivilianUserJourneyCountBySeqNoForLtc(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), obj);
						    	
			    				YearWiseBulkBkgModel yearWiseModel=new YearWiseBulkBkgModel();
			    				yearWiseModel.setYear(obj);
					 	 	   
					 	 	   if(eligblMap.get("spouseCount")!=-1 &&  eligblMap.get("spouseCount")<=reqCountList.get(0)){
						 	    	yearWiseModel.setJourneyCheck("false");
							  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
							  		
						 	    }else{
						 	    	yearWiseModel.setJourneyCheck("true");
						 	    }
						 	   if(eligblMap.get("spouseCount")!=-1 &&  eligblMap.get("spouseCount")<=reqCountList.get(1)){
						 	    	yearWiseModel.setJourneyCheckRet("false");
							  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
						 	    }else{
						 	    	yearWiseModel.setJourneyCheckRet("true");
						 	    }
						 	  yearWise.add(yearWiseModel);
			    			}
						  
					  });
					  
					  familyDetailModel.setYearWise(yearWise);
					  
				} // End of LTC Case
				    	
				  if(!familyDetailModel.getYearWise().isEmpty()) {
				    	 familyDetailModel.setReason(familyDetailModel.getYearWise().get(0).getReason());
				     }else {
				    	 familyDetailModel.setReason("");
				     }
				  
				  familyDetail.add(familyDetailModel);
		
				 
			 });
			 
			 sonDependent.forEach(e->{

		 		 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
		 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
		 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
		 		familyDetailModel.setGender(e.getGender().getDisplayValue());
		 		familyDetailModel.setGenderCode(e.getGender().ordinal());
		 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
		 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
		 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
		 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
		 		familyDetailModel.setDOIIDate(CommonUtil.formatDate(e.getPartDate(), "dd/MM/yyyy"));
		 		familyDetailModel.setDOIIPartNo(e.getPartNo());
		 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
		 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
				
				  if(travelTypeName.contains("LTC"))
				  {
					  List<YearWiseBulkBkgModel> yearWise=new ArrayList<>();
					  yearWiseList.forEach(obj->{
						  

						  	if(ltcAvailYear.contains(obj))
						  	{
						  		YearWiseBulkBkgModel yearWiseModel=new YearWiseBulkBkgModel();
						  		yearWiseModel.setYear(obj);
						  		yearWiseModel.setJourneyCheck("false");
						  		yearWiseModel.setJourneyCheckRet("false");
						  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
						  		yearWise.add(yearWiseModel);
			    			}else
			    			{
			    				List<Integer> reqCountList=countBizlogic.getCivilianUserJourneyCountBySeqNoForLtc(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), obj);
						    	
			    				YearWiseBulkBkgModel yearWiseModel=new YearWiseBulkBkgModel();
			    				yearWiseModel.setYear(obj);
					 	 	   
					 	 	   if(eligblMap.get("sonCount")!=-1 &&  eligblMap.get("sonCount")<=reqCountList.get(0)){
						 	    	yearWiseModel.setJourneyCheck("false");
							  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
							  		
						 	    }else{
						 	    	yearWiseModel.setJourneyCheck("true");
						 	    }
						 	   if(eligblMap.get("sonCount")!=-1 &&  eligblMap.get("sonCount")<=reqCountList.get(1)){
						 	    	yearWiseModel.setJourneyCheckRet("false");
							  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
						 	    }else{
						 	    	yearWiseModel.setJourneyCheckRet("true");
						 	    }
						 	  yearWise.add(yearWiseModel);
			    			}
						  
					  });
					  
					  familyDetailModel.setYearWise(yearWise);
					  
				} // End of LTC Case
				    	
				  if(!familyDetailModel.getYearWise().isEmpty()) {
				    	 familyDetailModel.setReason(familyDetailModel.getYearWise().get(0).getReason());
				     }else {
				    	 familyDetailModel.setReason("");
				     }
				  familyDetail.add(familyDetailModel);
		
				 
			 });
			
			 daughterDependent.forEach(e->{

		 		 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
		 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
		 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
		 		familyDetailModel.setGender(e.getGender().getDisplayValue());
		 		familyDetailModel.setGenderCode(e.getGender().ordinal());
		 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
		 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
		 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
		 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
		 		familyDetailModel.setDOIIDate(CommonUtil.formatDate(e.getPartDate(), "dd/MM/yyyy"));
		 		familyDetailModel.setDOIIPartNo(e.getPartNo());
		 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
		 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
				
				  if(travelTypeName.contains("LTC"))
				  {
					  List<YearWiseBulkBkgModel> yearWise=new ArrayList<>();
					  yearWiseList.forEach(obj->{
						  

						  	if(ltcAvailYear.contains(obj))
						  	{
						  		YearWiseBulkBkgModel yearWiseModel=new YearWiseBulkBkgModel();
						  		yearWiseModel.setYear(obj);
						  		yearWiseModel.setJourneyCheck("false");
						  		yearWiseModel.setJourneyCheckRet("false");
						  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
						  		yearWise.add(yearWiseModel);
			    			}else
			    			{
			    				List<Integer> reqCountList=countBizlogic.getCivilianUserJourneyCountBySeqNoForLtc(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), obj);
						    	
			    				YearWiseBulkBkgModel yearWiseModel=new YearWiseBulkBkgModel();
			    				yearWiseModel.setYear(obj);
					 	 	   
					 	 	   if(eligblMap.get("daughterCount")!=-1 &&  eligblMap.get("daughterCount")<=reqCountList.get(0)){
						 	    	yearWiseModel.setJourneyCheck("false");
							  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
							  		
						 	    }else{
						 	    	yearWiseModel.setJourneyCheck("true");
						 	    }
						 	   if(eligblMap.get("daughterCount")!=-1 &&  eligblMap.get("daughterCount")<=reqCountList.get(1)){
						 	    	yearWiseModel.setJourneyCheckRet("false");
							  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
						 	    }else{
						 	    	yearWiseModel.setJourneyCheckRet("true");
						 	    }
						 	  yearWise.add(yearWiseModel);
			    			}
						  
					  });
					  
					  familyDetailModel.setYearWise(yearWise);
					  
				} // End of LTC Case
				    	
				  if(!familyDetailModel.getYearWise().isEmpty()) {
			    	 familyDetailModel.setReason(familyDetailModel.getYearWise().get(0).getReason());
			     }else {
			    	 familyDetailModel.setReason("");
			     }
				  familyDetail.add(familyDetailModel);
				 
			 });
						
		 		
		 		otherDependent.forEach(e->{
		 			
		 			int relation=e.getRelation().ordinal();

			 		FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
			 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
			 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
			 		familyDetailModel.setGender(e.getGender().getDisplayValue());
			 		familyDetailModel.setGenderCode(e.getGender().ordinal());
			 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
			 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
			 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
			 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
			 		familyDetailModel.setDOIIDate(CommonUtil.formatDate(e.getPartDate(), "dd/MM/yyyy"));
			 		familyDetailModel.setDOIIPartNo(e.getPartNo());
			 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
			 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
					
					  if(travelTypeName.contains("LTC"))
					  {
						  List<YearWiseBulkBkgModel> yearWise=new ArrayList<>();
						  yearWiseList.forEach(obj->{
							  

							  	if(ltcAvailYear.contains(obj))
							  	{
							  		YearWiseBulkBkgModel yearWiseModel=new YearWiseBulkBkgModel();
							  		yearWiseModel.setYear(obj);
							  		yearWiseModel.setJourneyCheck("false");
							  		yearWiseModel.setJourneyCheckRet("false");
							  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
							  		yearWise.add(yearWiseModel);
				    			}else
				    			{
				    				AtomicBoolean jrnyCountCheckForOther=new AtomicBoolean(true);
				    				AtomicBoolean jrnyCountCheckRetForOther=new AtomicBoolean(true);
							  		
				    				List<Integer> reqCountList=countBizlogic.getCivilianUserJourneyCountBySeqNoForLtc(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), obj);
				    				
							    	
				    				DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"Other Dependent::  reqCountList[0]"+reqCountList.get(0)+"||relation="+relation);
									
				    				YearWiseBulkBkgModel yearWiseModel=new YearWiseBulkBkgModel();
				    				yearWiseModel.setYear(obj);
						 	 	    
						 	 	     if(eligblMap.containsKey("stepSonCount") && eligblMap.get("stepSonCount")!=-1 &&  (relation==3)  && eligblMap.get("stepSonCount")<=reqCountList.get(0)){
										jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("stepDaughterCount") && eligblMap.get("stepDaughterCount")!=-1 && (relation==5)  && eligblMap.get("stepDaughterCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("brotherCount") && eligblMap.get("brotherCount")!=-1 && relation==6 && eligblMap.get("brotherCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("sisterCount") && eligblMap.get("sisterCount")!=-1 && relation==7  && eligblMap.get("sisterCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("fatherCount") && eligblMap.get("fatherCount")!=-1 && (relation==10) &&  eligblMap.get("fatherCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("motherCount") && eligblMap.get("motherCount")!=-1 && relation==11 &&  eligblMap.get("motherCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("stepMotherCount") && eligblMap.get("stepMotherCount")!=-1 && (relation==8) && eligblMap.get("stepMotherCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("stepFatherCount") && eligblMap.get("stepFatherCount")!=-1 && relation==9  && eligblMap.get("stepFatherCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
						 	 	    
									 if(eligblMap.containsKey("stepBrotherCount") && eligblMap.get("stepBrotherCount")!=-1 && relation==13  && eligblMap.get("stepBrotherCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("stepSisterCount") && eligblMap.get("stepSisterCount")!=-1 && relation==14  && eligblMap.get("stepSisterCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
									 
									 if(!jrnyCountCheckForOther.get()){
										 yearWiseModel.setJourneyCheck("false");
										 yearWiseModel.setReason("Already Availed Journey On This TR Rule");
									 }else{
										 yearWiseModel.setJourneyCheck("true");
									 }
						 	 	    
						 	 	    
						 	 	    
								    if(eligblMap.containsKey("stepSonCount") && eligblMap.get("stepSonCount")!=-1 &&  (relation==3)  && eligblMap.get("stepSonCount")<=reqCountList.get(1)){
										jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("stepDaughterCount") && eligblMap.get("stepDaughterCount")!=-1 && (relation==5)  && eligblMap.get("stepDaughterCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("brotherCount") && eligblMap.get("brotherCount")!=-1 && relation==6 && eligblMap.get("brotherCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("sisterCount") && eligblMap.get("sisterCount")!=-1 && relation==7  && eligblMap.get("sisterCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("fatherCount") && eligblMap.get("fatherCount")!=-1 && (relation==10) &&  eligblMap.get("fatherCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("motherCount") && eligblMap.get("motherCount")!=-1 && relation==11 &&  eligblMap.get("motherCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("stepMotherCount") && eligblMap.get("stepMotherCount")!=-1 && (relation==8) && eligblMap.get("stepMotherCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("stepFatherCount") && eligblMap.get("stepFatherCount")!=-1 && relation==9  && eligblMap.get("stepFatherCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 
									 if(eligblMap.containsKey("stepBrotherCount") && eligblMap.get("stepBrotherCount")!=-1 && relation==13  && eligblMap.get("stepBrotherCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("stepSisterCount") && eligblMap.get("stepSisterCount")!=-1 && relation==14  && eligblMap.get("stepSisterCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 
									 if(!jrnyCountCheckRetForOther.get()){
										  yearWiseModel.setJourneyCheckRet("false");
										  yearWiseModel.setReason("Already Availed Journey On This TR Rule");
									 }else{
										 yearWiseModel.setJourneyCheckRet("true");
									 }
						 	 	    
									 yearWise.add(yearWiseModel);
							 	 
				    			}
							  
						  });
						  
						  familyDetailModel.setYearWise(yearWise);
						  
					} // End of LTC Case
					    	
					  if(!familyDetailModel.getYearWise().isEmpty()) {
				    	 familyDetailModel.setReason(familyDetailModel.getYearWise().get(0).getReason());
				     }else {
				    	 familyDetailModel.setReason("");
				     }
					  familyDetail.add(familyDetailModel);
					 
				 });
		 		
		 		
		    }
		
		 infoModel.setFamilyDetail(familyDetail);
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"familyDetail At the end-->"+familyDetail.toString());
		 
	}
	
	
	public void  getTravellorFamilyDetailsForLtc(VisitorModel visitor,List<Integer> eligibleMember,String travelTypeId,TravelRule travelRule, TravellerInfoBulkBkgModel infoModel) 
	{
		
		 String logStr=":getTravellorFamilyDetailsForLtc("+visitor.getName()+"):";
		
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"EligibleMember-"+eligibleMember);
		 
		 Set<FamilyDetailsModel> familyTD=visitor.getFamilyDetails();
	     
		 Map<String, Integer> eligblMap=getEligibleMemberCountList(travelRule);

		 String travelTypeName=trReqCommonUtil.getTravelTypeNameByID(travelTypeId);
	 
		 List<FamilyDetailsModel> spouseDependent=new ArrayList<>();
		 List<FamilyDetailsModel> sonDependent=null;
		 List<FamilyDetailsModel> daughterDependent=null;
		 List<FamilyDetailsModel> otherDependent=null;
	      
		 Map<String,FamilyDetailsModel> sonMap = new HashMap<>();
		 Map<String,FamilyDetailsModel> daughterMap = new HashMap<>(); 
		 Map<String,FamilyDetailsModel> otherMap = new HashMap<>();
			 
		 Calendar calendar=Calendar.getInstance();
		 String previousYr=String.valueOf(calendar.get(Calendar.YEAR)-1);
		 String currentYr=String.valueOf(calendar.get(Calendar.YEAR));
	     String nextYr=String.valueOf(calendar.get(Calendar.YEAR)+1);
		
	     List<String> yearWiseList=new ArrayList<>();
	     yearWiseList.add(previousYr);
	     yearWiseList.add(currentYr);
	     yearWiseList.add(nextYr);
	     
	     List<FamilyDetailBulkBkgModel> familyDetail=new ArrayList<>();
		 
		 
		 // Case For Self
		 if(eligibleMember.contains(Integer.valueOf(0)))
		 {
			 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
			 String name=visitor.getName();
			 List<YearWiseBulkBkgModel> yearWise=new ArrayList<>();
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class, "Year Wise " + travelTypeName);
		    if(travelTypeName.contains("LTC"))
		    {
		    	yearWiseList.forEach(e->{
		    		List<Integer> reqCountList=countBizlogic.getUserJourneyCountBySeqNoForLtc(visitor.getUserId(), -1, travelTypeId, travelRule.getTrRuleId(), e);
		    		YearWiseBulkBkgModel model=new YearWiseBulkBkgModel();
    				model.setYear(e);
    				if(eligblMap.get("selfCount").intValue()!=-1 && (eligblMap.get("selfCount").intValue()<=reqCountList.get(0).intValue())){
		 	 	    	model.setJourneyCheck("false");
		 	 	    	model.setReason("Already Availed Journey On This TR Rule");
		 	 	    }else{
		 	 	    	model.setJourneyCheck("true");
		 	 	    }
		 	 	    if(eligblMap.get("selfCount").intValue()!=-1 && (eligblMap.get("selfCount").intValue()<=reqCountList.get(1).intValue())){
		 	 	    	model.setJourneyCheckRet("false");
		 	 	    	model.setReason("Already Availed Journey On This TR Rule");
		 	 	    	
		 	 	    }else{
		 	 	    	
		 	 	    	model.setJourneyCheckRet("true");
		 	 	    }
		 	 	  yearWise.add(model);
		    	});
		    	
		    }
		    
		    familyDetailModel.setYearWise(yearWise);
		       
	         familyDetailModel.setName(name);
		     familyDetailModel.setRelationShip("Self");
		     familyDetailModel.setRelationCode(0);
		     familyDetailModel.setDob(CommonUtil.formatDate(visitor.getDateOfBirth(), "dd-MM-yyyy"));
		     familyDetailModel.setGender(visitor.getGender().getDisplayValue());
		     familyDetailModel.setGenderCode(visitor.getGender().ordinal());
		     familyDetailModel.setDepSeqNo(-1);
		     familyDetailModel.setErsPrintFamilyName(visitor.getTravelerProfile().getErsPrintName());
		    
		     if(!familyDetailModel.getYearWise().isEmpty()) {
		    	 familyDetailModel.setReason(familyDetailModel.getYearWise().get(0).getReason());
		     }else {
		    	 familyDetailModel.setReason("");
		     }
		     
		     familyDetail.add(familyDetailModel);
		       
		 }
		 
		 // end self
		 
		 
		 if (familyTD!= null && !familyTD.isEmpty())
		 {
			 familyTD.forEach(e->{
			
				 String relationType=e.getRelation().getDisplayValue();
				 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"relationType->"+relationType+"||Status="+e.getStatus()+"||Is In EligibleMember="+eligibleMember.contains(e.getRelation().ordinal()));
				 
				 if(relationType.equals("Spouse") && eligibleMember.contains(Integer.valueOf(e.getRelation().ordinal())) && e.getStatus().ordinal()==1){
					  spouseDependent.add(e);
				  }
				  else if(relationType.equals("Son") && eligibleMember.contains(Integer.valueOf(e.getRelation().ordinal())) && e.getStatus().ordinal()==1){
					  sonMap.put( CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")),e);
				  }
				  else if(relationType.equals("Daughter") && eligibleMember.contains(Integer.valueOf(e.getRelation().ordinal())) && e.getStatus().ordinal()==1){
					  daughterMap.put(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")),e);
				  }
				  else if(eligibleMember.contains(Integer.valueOf(e.getRelation().ordinal())) &&  e.getStatus().ordinal()==1){
				      otherMap.put(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")),e);
				  }
			 });
		 }
			 
		 sonDependent=new ArrayList<>(sonMap.values()); 
		 daughterDependent=new ArrayList<>(daughterMap.values()); 
		 otherDependent=new ArrayList<>(otherMap.values());
		 
		 Collections.sort(sonDependent,byValue); 
		 Collections.sort(daughterDependent,byValue); 
		 Collections.sort(otherDependent,byValue); 
		 
		 if (familyTD!= null && !familyTD.isEmpty())
		 {
			 
			 spouseDependent.forEach(e->{

		 		 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
		 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
		 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
		 		familyDetailModel.setGender(e.getGender().getDisplayValue());
		 		familyDetailModel.setGenderCode(e.getGender().ordinal());
		 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
		 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
		 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
		 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
		 		familyDetailModel.setDOIIDate(CommonUtil.formatDate(e.getPartDate(), "dd/MM/yyyy"));
		 		familyDetailModel.setDOIIPartNo(e.getPartNo());
		 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
		 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
				
				  if(travelTypeName.contains("LTC"))
				  {
					  List<YearWiseBulkBkgModel> yearWise=new ArrayList<>();
					  yearWiseList.forEach(obj->{
						  
			    				List<Integer> reqCountList=countBizlogic.getUserJourneyCountBySeqNoForLtc(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), obj);
						    	
			    				YearWiseBulkBkgModel yearWiseModel=new YearWiseBulkBkgModel();
			    				yearWiseModel.setYear(obj);
					 	 	   
					 	 	   if(eligblMap.get("spouseCount")!=-1 &&  eligblMap.get("spouseCount")<=reqCountList.get(0)){
						 	    	yearWiseModel.setJourneyCheck("false");
							  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
							  		
						 	    }else{
						 	    	yearWiseModel.setJourneyCheck("true");
						 	    }
						 	   if(eligblMap.get("spouseCount")!=-1 &&  eligblMap.get("spouseCount")<=reqCountList.get(1)){
						 	    	yearWiseModel.setJourneyCheckRet("false");
							  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
						 	    }else{
						 	    	yearWiseModel.setJourneyCheckRet("true");
						 	    }
						 	  yearWise.add(yearWiseModel);
						  
					  });
					  
					  familyDetailModel.setYearWise(yearWise);
					  
				} // End of LTC Case
				    	
				  if(!familyDetailModel.getYearWise().isEmpty()) {
				    	 familyDetailModel.setReason(familyDetailModel.getYearWise().get(0).getReason());
				     }else {
				    	 familyDetailModel.setReason("");
				     }
				  familyDetail.add(familyDetailModel);
		
				 
			 });
			 
			 sonDependent.forEach(e->{

		 		 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
		 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
		 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
		 		familyDetailModel.setGender(e.getGender().getDisplayValue());
		 		familyDetailModel.setGenderCode(e.getGender().ordinal());
		 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
		 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
		 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
		 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
		 		familyDetailModel.setDOIIDate(CommonUtil.formatDate(e.getPartDate(), "dd/MM/yyyy"));
		 		familyDetailModel.setDOIIPartNo(e.getPartNo());
		 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
		 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
				
				  if(travelTypeName.contains("LTC"))
				  {
					  List<YearWiseBulkBkgModel> yearWise=new ArrayList<>();
					  yearWiseList.forEach(obj->{
						  
			    				List<Integer> reqCountList=countBizlogic.getUserJourneyCountBySeqNoForLtc(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), obj);
						    	
			    				YearWiseBulkBkgModel yearWiseModel=new YearWiseBulkBkgModel();
			    				yearWiseModel.setYear(obj);
					 	 	   
					 	 	   if(eligblMap.get("sonCount")!=-1 &&  eligblMap.get("sonCount")<=reqCountList.get(0)){
						 	    	yearWiseModel.setJourneyCheck("false");
							  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
							  		
						 	    }else{
						 	    	yearWiseModel.setJourneyCheck("true");
						 	    }
						 	   if(eligblMap.get("sonCount")!=-1 &&  eligblMap.get("sonCount")<=reqCountList.get(1)){
						 	    	yearWiseModel.setJourneyCheckRet("false");
							  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
						 	    }else{
						 	    	yearWiseModel.setJourneyCheckRet("true");
						 	    }
						 	  yearWise.add(yearWiseModel);
						  
					  });
					  
					  familyDetailModel.setYearWise(yearWise);
					  
				} // End of LTC Case
				    	
				  if(!familyDetailModel.getYearWise().isEmpty()) {
				    	 familyDetailModel.setReason(familyDetailModel.getYearWise().get(0).getReason());
				     }else {
				    	 familyDetailModel.setReason("");
				     }
				  familyDetail.add(familyDetailModel);
		
				 
			 });			

			 
			 daughterDependent.forEach(e->{

		 		 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
		 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
		 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
		 		familyDetailModel.setGender(e.getGender().getDisplayValue());
		 		familyDetailModel.setGenderCode(e.getGender().ordinal());
		 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
		 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
		 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
		 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
		 		familyDetailModel.setDOIIDate(CommonUtil.formatDate(e.getPartDate(), "dd/MM/yyyy"));
		 		familyDetailModel.setDOIIPartNo(e.getPartNo());
		 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
		 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
				
				  if(travelTypeName.contains("LTC"))
				  {
					  List<YearWiseBulkBkgModel> yearWise=new ArrayList<>();
					  yearWiseList.forEach(obj->{
						  						  
			    				List<Integer> reqCountList=countBizlogic.getUserJourneyCountBySeqNoForLtc(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), obj);
						    	
			    				YearWiseBulkBkgModel yearWiseModel=new YearWiseBulkBkgModel();
			    				yearWiseModel.setYear(obj);
					 	 	   
					 	 	   if(eligblMap.get("daughterCount")!=-1 &&  eligblMap.get("daughterCount")<=reqCountList.get(0)){
						 	    	yearWiseModel.setJourneyCheck("false");
							  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
							  		
						 	    }else{
						 	    	yearWiseModel.setJourneyCheck("true");
						 	    }
						 	   if(eligblMap.get("daughterCount")!=-1 &&  eligblMap.get("daughterCount")<=reqCountList.get(1)){
						 	    	yearWiseModel.setJourneyCheckRet("false");
							  		yearWiseModel.setReason("Already Availed Journey On This TR Rule");
						 	    }else{
						 	    	yearWiseModel.setJourneyCheckRet("true");
						 	    }
						 	  yearWise.add(yearWiseModel);
						  
					  });
					  
					  familyDetailModel.setYearWise(yearWise);
					  
				} // End of LTC Case
				    	
				  if(!familyDetailModel.getYearWise().isEmpty()) {
				    	 familyDetailModel.setReason(familyDetailModel.getYearWise().get(0).getReason());
				     }else {
				    	 familyDetailModel.setReason("");
				     }
				  familyDetail.add(familyDetailModel);
				 
			 });
			
			 otherDependent.forEach(e->{
		 			
		 			int relation=e.getRelation().ordinal();

			 		FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
			 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
			 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
			 		familyDetailModel.setGender(e.getGender().getDisplayValue());
			 		familyDetailModel.setGenderCode(e.getGender().ordinal());
			 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
			 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
			 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
			 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
			 		familyDetailModel.setDOIIDate(CommonUtil.formatDate(e.getPartDate(), "dd/MM/yyyy"));
			 		familyDetailModel.setDOIIPartNo(e.getPartNo());
			 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
			 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
					
					  if(travelTypeName.contains("LTC"))
					  {
						  List<YearWiseBulkBkgModel> yearWise=new ArrayList<>();
						  yearWiseList.forEach(obj->{
							  
				    				AtomicBoolean jrnyCountCheckForOther=new AtomicBoolean(true);
				    				AtomicBoolean jrnyCountCheckRetForOther=new AtomicBoolean(true);
							  		
				    				List<Integer> reqCountList=countBizlogic.getUserJourneyCountBySeqNoForLtc(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), obj);
				    				
							    	
				    				DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"Other Dependent::  reqCountList[0]"+reqCountList.get(0)+"||relation="+relation);
									
				    				YearWiseBulkBkgModel yearWiseModel=new YearWiseBulkBkgModel();
				    				yearWiseModel.setYear(obj);
						 	 	    
						 	 	     if(eligblMap.containsKey("stepSonCount") && eligblMap.get("stepSonCount")!=-1 &&  (relation==3)  && eligblMap.get("stepSonCount")<=reqCountList.get(0)){
										jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("stepDaughterCount") && eligblMap.get("stepDaughterCount")!=-1 && (relation==5)  && eligblMap.get("stepDaughterCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("brotherCount") && eligblMap.get("brotherCount")!=-1 && relation==6 && eligblMap.get("brotherCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("sisterCount") && eligblMap.get("sisterCount")!=-1 && relation==7  && eligblMap.get("sisterCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("fatherCount") && eligblMap.get("fatherCount")!=-1 && (relation==10) &&  eligblMap.get("fatherCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("motherCount") && eligblMap.get("motherCount")!=-1 && relation==11 &&  eligblMap.get("motherCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("stepMotherCount") && eligblMap.get("stepMotherCount")!=-1 && (relation==8) && eligblMap.get("stepMotherCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
									 if(eligblMap.containsKey("stepFatherCount") && eligblMap.get("stepFatherCount")!=-1 && relation==9  && eligblMap.get("stepFatherCount")<=reqCountList.get(0)){
										 jrnyCountCheckForOther.set(false);
									 }
						 	 	    
									 
									 if(!jrnyCountCheckForOther.get()){
										 yearWiseModel.setJourneyCheck("false");
										 yearWiseModel.setReason("Already Availed Journey On This TR Rule");
									 }else{
										 yearWiseModel.setJourneyCheck("true");
									 }
						 	 	    
						 	 	    
						 	 	    
								    if(eligblMap.containsKey("stepSonCount") && eligblMap.get("stepSonCount")!=-1 &&  (relation==3)  && eligblMap.get("stepSonCount")<=reqCountList.get(1)){
										jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("stepDaughterCount") && eligblMap.get("stepDaughterCount")!=-1 && (relation==5)  && eligblMap.get("stepDaughterCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("brotherCount") && eligblMap.get("brotherCount")!=-1 && relation==6 && eligblMap.get("brotherCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("sisterCount") && eligblMap.get("sisterCount")!=-1 && relation==7  && eligblMap.get("sisterCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("fatherCount") && eligblMap.get("fatherCount")!=-1 && (relation==10) &&  eligblMap.get("fatherCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("motherCount") && eligblMap.get("motherCount")!=-1 && relation==11 &&  eligblMap.get("motherCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("stepMotherCount") && eligblMap.get("stepMotherCount")!=-1 && (relation==8) && eligblMap.get("stepMotherCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 if(eligblMap.containsKey("stepFatherCount") && eligblMap.get("stepFatherCount")!=-1 && relation==9  && eligblMap.get("stepFatherCount")<=reqCountList.get(1)){
										 jrnyCountCheckRetForOther.set(false);
									 }
									 
									  
									 if(!jrnyCountCheckRetForOther.get()){
										  yearWiseModel.setJourneyCheckRet("false");
										  yearWiseModel.setReason("Already Availed Journey On This TR Rule");
									 }else{
										 yearWiseModel.setJourneyCheckRet("true");
									 }
						 	 	    
									 yearWise.add(yearWiseModel);
							 	 
							  
						  });
						  
						  familyDetailModel.setYearWise(yearWise);
						  
					} // End of LTC Case
					    	
					  if(!familyDetailModel.getYearWise().isEmpty()) {
					    	 familyDetailModel.setReason(familyDetailModel.getYearWise().get(0).getReason());
					     }else {
					    	 familyDetailModel.setReason("");
					     }
					  familyDetail.add(familyDetailModel);
					 
				 });
								 
		 		
		    }
		
		 infoModel.setFamilyDetail(familyDetail);
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"familyDetail At the end-->"+familyDetail.toString());
		 
	}
	
	
	public void getTravellorFamilyDetailsForFormDAndGYearWise(VisitorModel visitor, List<Integer> eligibleMember, String travelTypeId, TravelRule travelRule, List<String> yearList, TravellerInfoBulkBkgModel infoModel) {
		
		 String logStr=":getTravellorFamilyDetailsXMLForFormDAndGYearWise("+visitor.getName()+"):";
		 
		 boolean isOnSuspension=Optional.ofNullable(visitor.getTravelerProfile().getOnSuspension()).orElse(YesOrNo.NO).ordinal()==0;
		 

		 Set<FamilyDetailsModel> familyTD=visitor.getFamilyDetails();
	     
		 Map<String, Integer> eligblMap=getEligibleMemberCountList(travelRule);

		 String travelTypeName=trReqCommonUtil.getTravelTypeNameByID(travelTypeId);
		 
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"::EligibleMember->"+eligibleMember);
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"::eligblMap->"+eligblMap);
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"::travelTypeId="+travelTypeId+"||travelTypeName="+travelTypeName+"||isOnSuspension="+isOnSuspension);
		
		 List<FamilyDetailBulkBkgModel> familyDetail=new ArrayList<>();
		 
		 
		 
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"yearList.size"+yearList.size()); 
		 
		 yearList.forEach(currentYr->{
			 
			 List<FamilyDetailsModel> spouseDependent=new ArrayList<>();
			 List<FamilyDetailsModel> sonDependent=null;
			 List<FamilyDetailsModel> daughterDependent=null;
			 List<FamilyDetailsModel> otherDependent=null;
		     
			 Map<String,FamilyDetailsModel> sonMap = new HashMap<>();
			 Map<String,FamilyDetailsModel> daughterMap = new HashMap<>(); 
			 Map<String,FamilyDetailsModel> otherMap = new HashMap<>();
			 
			 
			 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"yearList.size"+currentYr); 
		
		 if(eligibleMember.contains(Integer.valueOf(0)))
		 {
			 
			 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
			 String name=visitor.getName();
		
		     List<Integer> reqCountList=countBizlogic.getJourneyCountBySeqNo(visitor.getUserId(), -1, travelTypeId,  travelRule.getTrRuleId(), currentYr);
		     
			 int reqNo=reqCountList.get(0)+reqCountList.get(1);
			 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"reqNo"+reqNo); 
			 if(isOnSuspension)
	 		{
				 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"if"); 
	 			if(eligblMap.get("selfCount")!=-1){
	 		    	familyDetailModel.setJourneyCheck("false");
	 		    	familyDetailModel.setReason("You Are On Suspension.");
	 			}
	 			
		 	}else
	 		{
		 		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"else"); 
				if(eligblMap.get("selfCount")!=-1 && (eligblMap.get("selfCount")<=reqNo))
				{
					 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"else if"); 
			    	familyDetailModel.setJourneyCheck("false");
	 		    	familyDetailModel.setReason("Already Availed Journey On This TR Rule");
				}else {
					 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"else else"); 
				 familyDetailModel.setJourneyCheck("true");
			    }
	 		}
			 
			 familyDetailModel.setName(name);
		     familyDetailModel.setRelationShip("Self");
		     familyDetailModel.setRelationCode(0);
		     familyDetailModel.setDob(CommonUtil.formatDate(visitor.getDateOfBirth(), "dd-MM-yyyy"));
		     familyDetailModel.setGender(Optional.ofNullable(visitor.getGender()).orElse(Gender.MALE).getDisplayValue());
		     familyDetailModel.setGenderCode(Optional.ofNullable(visitor.getGender()).orElse(Gender.MALE).ordinal());
		     familyDetailModel.setDepSeqNo(-1);
		     familyDetailModel.setErsPrintFamilyName(Optional.ofNullable(visitor.getTravelerProfile().getErsPrintName()).orElse(""));
		     familyDetailModel.setYearWiseFormDAndG(currentYr);
		    
		     familyDetail.add(familyDetailModel);
		    
		 }
		
		 if (!travelTypeName.contains("LTC")) 
		 {
			 if (familyTD!= null && !familyTD.isEmpty())
			 {
				 familyTD.forEach(e->{
						
					 String relationType=e.getRelation().getDisplayValue();
					 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"relationType->"+relationType+"||Status="+e.getStatus()+"||Is In EligibleMember="+eligibleMember.contains(e.getRelation().ordinal()));
					 
					 if(relationType.equals("Spouse") && eligibleMember.contains(e.getRelation().ordinal()) && e.getStatus().ordinal()==1){
						  spouseDependent.add(e);
					  }
					  else if(relationType.equals("Son") && eligibleMember.contains(e.getRelation().ordinal()) && e.getStatus().ordinal()==1){
						  sonMap.put( CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")),e);
					  }
					  else if(relationType.equals("Daughter") && eligibleMember.contains(e.getRelation().ordinal()) && e.getStatus().ordinal()==1){
						  daughterMap.put(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")),e);
					  }
					  else if(eligibleMember.contains(e.getRelation().ordinal()) &&  e.getStatus().ordinal()==1){
					      otherMap.put(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")),e);
					  }
				 });
				 
			 }
			 
			 sonDependent=new ArrayList<>(sonMap.values()); 
			 daughterDependent=new ArrayList<>(daughterMap.values()); 
			 otherDependent=new ArrayList<>(otherMap.values());
			 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"spouseDependent->"+spouseDependent);
			 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"sonDependent->"+sonDependent);
			 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"daughterDependent="+daughterDependent);
			 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"otherDependent="+otherDependent);
				
			 Collections.sort(sonDependent,byValue); 
			 Collections.sort(daughterDependent,byValue); 
			 Collections.sort(otherDependent,byValue); 
			 
			 if (familyTD!= null && !familyTD.isEmpty())
			 {
				 
				 spouseDependent.forEach(e->{
					 
					 boolean jrnyCountCheck=true;

					 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
			 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
			 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
			 		familyDetailModel.setGender(e.getGender().getDisplayValue());
			 		familyDetailModel.setGenderCode(e.getGender().ordinal());
			 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
			 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
			 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
			 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
			 		familyDetailModel.setDOIIDate(Optional.ofNullable(CommonUtil.formatDate(e.getPartDate(),"dd/MM/yyyy")).orElse(""));
			 		familyDetailModel.setDOIIPartNo(e.getPartNo());
			 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
			 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
			 		
			 		List<Integer> reqCountList=countBizlogic.getJourneyCountBySeqNo(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), currentYr);
					  int reqNo=reqCountList.get(0)+reqCountList.get(1);
				     	
					  if(eligblMap.get("spouseCount")!=-1 &&  (eligblMap.get("spouseCount")<=reqNo)){
						 jrnyCountCheck=false;
						 
					  }
				  
					  if(!jrnyCountCheck){
					  	familyDetailModel.setReason("Already Availed Journey On This TR Rule");
					   }
					  
					  familyDetailModel.setJourneyCheck(Boolean.toString(jrnyCountCheck));
					  familyDetailModel.setYearWiseFormDAndG(currentYr);
					    	
					  familyDetail.add(familyDetailModel);
					 
				 });
				 
				 sonDependent.forEach(e->{
					 
					 boolean jrnyCountCheck=true;

					 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
			 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
			 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
			 		familyDetailModel.setGender(e.getGender().getDisplayValue());
			 		familyDetailModel.setGenderCode(e.getGender().ordinal());
			 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
			 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
			 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
			 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
			 		familyDetailModel.setDOIIDate(Optional.ofNullable(CommonUtil.formatDate(e.getPartDate(),"dd/MM/yyyy")).orElse(""));
			 		familyDetailModel.setDOIIPartNo(e.getPartNo());
			 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
			 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
			 		
			 		List<Integer> reqCountList=countBizlogic.getJourneyCountBySeqNo(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), currentYr);
					  int reqNo=reqCountList.get(0)+reqCountList.get(1);
				     	
					  if(eligblMap.get("sonCount")!=-1 &&  (eligblMap.get("sonCount")<=reqNo)){
						 jrnyCountCheck=false;
						 
					  }
				  
					  if(!jrnyCountCheck){
					  	familyDetailModel.setReason("Already Availed Journey On This TR Rule");
					   }
					  
					  familyDetailModel.setJourneyCheck(Boolean.toString(jrnyCountCheck));
					  familyDetailModel.setYearWiseFormDAndG(currentYr);
					    	
					  familyDetail.add(familyDetailModel);
					 
				 });
				 
				 daughterDependent.forEach(e->{
					 
					 boolean jrnyCountCheck=true;

					 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
			 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
			 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
			 		familyDetailModel.setGender(e.getGender().getDisplayValue());
			 		familyDetailModel.setGenderCode(e.getGender().ordinal());
			 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
			 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
			 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
			 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
			 		familyDetailModel.setDOIIDate(Optional.ofNullable(CommonUtil.formatDate(e.getPartDate(),"dd/MM/yyyy")).orElse(""));
			 		familyDetailModel.setDOIIPartNo(e.getPartNo());
			 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
			 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
			 		
			 		List<Integer> reqCountList=countBizlogic.getJourneyCountBySeqNo(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), currentYr);
					  int reqNo=reqCountList.get(0)+reqCountList.get(1);
				     	
					  if(eligblMap.get("daughterCount")!=-1 &&  (eligblMap.get("daughterCount")<=reqNo)){
						 jrnyCountCheck=false;
						 
					  }
				  
					  if(!jrnyCountCheck){
					  	familyDetailModel.setReason("Already Availed Journey On This TR Rule");
					   }
					  
					  familyDetailModel.setJourneyCheck(Boolean.toString(jrnyCountCheck));
					  familyDetailModel.setYearWiseFormDAndG(currentYr);
					    	
					  familyDetail.add(familyDetailModel);
					 
				 });
			    
				  
				  otherDependent.forEach(e->{
						 
						 boolean jrnyCountCheckForOther=true;
						 
						 int relation=e.getRelation().ordinal();

						 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
				 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
				 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
				 		familyDetailModel.setGender(e.getGender().getDisplayValue());
				 		familyDetailModel.setGenderCode(e.getGender().ordinal());
				 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
				 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
				 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
				 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
				 		
				 		familyDetailModel.setDOIIDate(Optional.ofNullable(CommonUtil.formatDate(e.getPartDate(),"dd/MM/yyyy")).orElse(""));
				 		familyDetailModel.setDOIIPartNo(e.getPartNo());
				 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
				 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
				 		
				 		List<Integer> reqCountList=countBizlogic.getJourneyCountBySeqNo(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), currentYr);
						  int reqNo=reqCountList.get(0)+reqCountList.get(1);
					     	
						  if(eligblMap.containsKey("stepSonCount") && eligblMap.get("stepSonCount") !=-1 &&  (relation==3)  && (eligblMap.get("stepSonCount")<=reqNo)){
							  jrnyCountCheckForOther=false;
					      }
					      if(eligblMap.containsKey("stepDaughterCount") && eligblMap.get("stepDaughterCount") !=-1 && (relation==5)  && (eligblMap.get("stepDaughterCount")<=reqNo)){
								 jrnyCountCheckForOther=false;
						  }
						 if(eligblMap.containsKey("brotherCount") && eligblMap.get("brotherCount") !=-1 && relation==6 && (eligblMap.get("brotherCount")<=reqNo)){
							 jrnyCountCheckForOther=false;
						 }
						 if(eligblMap.containsKey("sisterCount") && eligblMap.get("sisterCount") !=-1 && relation==7  && (eligblMap.get("sisterCount")<=reqNo)){
							 jrnyCountCheckForOther=false;
						 }
						 if(eligblMap.containsKey("fatherCount") && eligblMap.get("fatherCount") !=-1 && (relation==10) &&  (eligblMap.get("fatherCount")<=reqNo)){
							 jrnyCountCheckForOther=false;
						 }
						 if(eligblMap.containsKey("motherCount") && eligblMap.get("motherCount") !=-1 && relation==11 &&  (eligblMap.get("motherCount")<=reqNo)){
							 jrnyCountCheckForOther=false;
						 }
						 if(eligblMap.containsKey("stepMotherCount") && eligblMap.get("stepMotherCount") !=-1 && (relation==8) && (eligblMap.get("stepMotherCount")<=reqNo)){
							 jrnyCountCheckForOther=false;
						 }
						 if(eligblMap.containsKey("stepFatherCount") && eligblMap.get("stepFatherCount") !=-1 && relation==9  && (eligblMap.get("stepFatherCount")<=reqNo)){
							 jrnyCountCheckForOther=false;
						 }
						 if(eligblMap.containsKey("stepBrotherCount") && eligblMap.get("stepBrotherCount") !=-1&& relation==13  && (eligblMap.get("stepBrotherCount")<=reqNo)){
							 jrnyCountCheckForOther=false;
						 }
						 if(eligblMap.containsKey("stepSisterCount") && eligblMap.get("stepSisterCount") !=-1&& relation==14  && (eligblMap.get("stepSisterCount")<=reqNo)){
							 jrnyCountCheckForOther=false;
						 }
					  
						  if(!jrnyCountCheckForOther){
						  	familyDetailModel.setReason("Already Availed Journey On This TR Rule");
						   }
						  
						  familyDetailModel.setJourneyCheck(Boolean.toString(jrnyCountCheckForOther));
						  familyDetailModel.setYearWiseFormDAndG(currentYr);
						    	
						  familyDetail.add(familyDetailModel);
						 
					 });
				  
		   }
		 }
		 
		 });
		 
		 infoModel.setFamilyDetail(familyDetail);
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"familyDetail At the end-->"+familyDetail.toString());
		 
	}
	
	
	public void getTravellorFamilyDetails(VisitorModel visitor,List<Integer> eligibleMember,String travelTypeId,TravelRule travelRule,TravellerInfoBulkBkgModel infoModel) 
	{
		
		 String logStr=":getTravellorFamilyDetails:>>("+visitor.getName()+"):";
		
		 boolean isOnSuspension=Optional.ofNullable(visitor.getTravelerProfile().getOnSuspension()).orElse(YesOrNo.NO).ordinal()==0;
		 
		 Set<FamilyDetailsModel> familyTD=visitor.getFamilyDetails();
	     
		 Map<String, Integer> eligblMap=getEligibleMemberCountList(travelRule);

		 String travelTypeName=trReqCommonUtil.getTravelTypeNameByID(travelTypeId);
		 
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"::EligibleMember->"+eligibleMember);
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"::eligblMap->"+eligblMap);
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"::travelTypeId="+travelTypeId+"||travelTypeName="+travelTypeName+"||isOnSuspension="+isOnSuspension);
		
		 List<FamilyDetailBulkBkgModel> familyDetail=new ArrayList<>();
		 
		 List<FamilyDetailsModel> spouseDependent=new ArrayList<>();
		 List<FamilyDetailsModel> sonDependent=null;
		 List<FamilyDetailsModel> daughterDependent=null;
		 List<FamilyDetailsModel> otherDependent=null;
		 
		 Map<String,FamilyDetailsModel> sonMap = new HashMap<>();
		 Map<String,FamilyDetailsModel> daughterMap = new HashMap<>(); 
		 Map<String,FamilyDetailsModel> otherMap = new HashMap<>();
		
		 Calendar calendar=Calendar.getInstance();
		 String currentYr=String.valueOf(calendar.get(Calendar.YEAR));
	    
		 
		 // Case For Self
		 if(eligibleMember.contains(Integer.valueOf(0)))
		 {
			 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
			 String name=visitor.getName();
		  
		
		     List<Integer> reqCountList=countBizlogic.getJourneyCountBySeqNo(visitor.getUserId(), -1, travelTypeId,travelRule.getTrRuleId(), currentYr);
			 int reqNo=reqCountList.get(0)+reqCountList.get(1);
			 
			 if(isOnSuspension)
		 		{
		 			if(eligblMap.get("selfCount")!=-1){
		 		    	familyDetailModel.setJourneyCheck("false");
		 		    	familyDetailModel.setReason("You Are On Suspension.");
		 			}
		 			
			 	}else
		 		{
					if(eligblMap.get("selfCount")!=-1 && (eligblMap.get("selfCount")<=reqNo))
					{
				    	familyDetailModel.setJourneyCheck("false");
		 		    	familyDetailModel.setReason("Already Availed Journey On This TR Rule");
					}else {
					 familyDetailModel.setJourneyCheck("true");
				    }
		 		}
			
			 familyDetailModel.setName(name);
		     familyDetailModel.setRelationShip("Self");
		     familyDetailModel.setRelationCode(0);
		     familyDetailModel.setDob(CommonUtil.formatDate(visitor.getDateOfBirth(), "dd-MM-yyyy"));
		     familyDetailModel.setGender(visitor.getGender().getDisplayValue());
		     familyDetailModel.setGenderCode(visitor.getGender().ordinal());
		     familyDetailModel.setDepSeqNo(-1);
		     familyDetailModel.setErsPrintFamilyName(visitor.getTravelerProfile().getErsPrintName());
		    
		     familyDetail.add(familyDetailModel);
		    
		 }
		 /* Case Of Self End */
		 
		 if (!travelTypeName.contains("LTC")) 
		 {
			 
			 
			 
			 if (familyTD!= null && !familyTD.isEmpty()) 
			 {
				 familyTD.forEach(e->{
						
					 String relationType=e.getRelation().getDisplayValue();
					 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"relationType->"+relationType+"||Status="+e.getStatus()+"||Is In EligibleMember="+eligibleMember.contains(e.getRelation().ordinal()));
					 
					 if(relationType.equals("Spouse") && eligibleMember.contains(e.getRelation().ordinal()) && e.getStatus().ordinal()==1){
						  spouseDependent.add(e);
					  }
					  else if(relationType.equals("Son") && eligibleMember.contains(e.getRelation().ordinal()) && e.getStatus().ordinal()==1){
						  sonMap.put( CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")),e);
					  }
					  else if(relationType.equals("Daughter") && eligibleMember.contains(e.getRelation().ordinal()) && e.getStatus().ordinal()==1){
						  daughterMap.put(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")),e);
					  }
					  else if(eligibleMember.contains(e.getRelation().ordinal()) &&  e.getStatus().ordinal()==1){
					      otherMap.put(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")),e);
					  }
				 });
				 
			 }
				 
			
		
			 sonDependent=new ArrayList<>(sonMap.values()); 
			 daughterDependent=new ArrayList<>(daughterMap.values()); 
			 otherDependent=new ArrayList<>(otherMap.values());
			 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"spouseDependent->"+spouseDependent);
			 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"sonDependent->"+sonDependent);
			 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"daughterDependent="+daughterDependent);
			 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"otherDependent="+otherDependent);
				
			 Collections.sort(sonDependent,byValue); 
			 Collections.sort(daughterDependent,byValue); 
			 Collections.sort(otherDependent,byValue); 
			
				 
			 if (familyTD!= null && !familyTD.isEmpty()) 
			 {
			    
				 spouseDependent.forEach(e->{
					 
					 boolean jrnyCountCheck=true;

					 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
			 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
			 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
			 		familyDetailModel.setGender(e.getGender().getDisplayValue());
			 		familyDetailModel.setGenderCode(e.getGender().ordinal());
			 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
			 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
			 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
			 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
			 		familyDetailModel.setDOIIDate(CommonUtil.formatDate(e.getPartDate(), "dd/MM/yyyy"));
			 		familyDetailModel.setDOIIPartNo(e.getPartNo());
			 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
			 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
			 		
			 		List<Integer> reqCountList=countBizlogic.getJourneyCountBySeqNo(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), currentYr);
					  int reqNo=reqCountList.get(0)+reqCountList.get(1);
				     	
					  if(eligblMap.get("spouseCount")!=-1 &&  (eligblMap.get("spouseCount")<=reqNo)){
						 jrnyCountCheck=false;
						 
					  }
				  
					  if(!jrnyCountCheck){
					  	familyDetailModel.setReason("Already Availed Journey On This TR Rule");
					   }
					  
					  familyDetailModel.setJourneyCheck(Boolean.toString(jrnyCountCheck));
					    	
					  familyDetail.add(familyDetailModel);
					 
				 });
				 

				 sonDependent.forEach(e->{
					 
					 boolean jrnyCountCheck=true;

					 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
			 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
			 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
			 		familyDetailModel.setGender(e.getGender().getDisplayValue());
			 		familyDetailModel.setGenderCode(e.getGender().ordinal());
			 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
			 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
			 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
			 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
			 		familyDetailModel.setDOIIDate(CommonUtil.formatDate(e.getPartDate(), "dd/MM/yyyy"));
			 		familyDetailModel.setDOIIPartNo(e.getPartNo());
			 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
			 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
			 		
			 		List<Integer> reqCountList=countBizlogic.getJourneyCountBySeqNo(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), currentYr);
					  int reqNo=reqCountList.get(0)+reqCountList.get(1);
				     	
					  if(eligblMap.get("sonCount")!=-1 &&  (eligblMap.get("sonCount")<=reqNo)){
						 jrnyCountCheck=false;
						 
					  }
				  
					  if(!jrnyCountCheck){
					  	familyDetailModel.setReason("Already Availed Journey On This TR Rule");
					   }
					  
					  familyDetailModel.setJourneyCheck(Boolean.toString(jrnyCountCheck));
					  familyDetailModel.setYearWiseFormDAndG(currentYr);
					    	
					  familyDetail.add(familyDetailModel);
					 
				 });
				 
				 daughterDependent.forEach(e->{
					 
					 boolean jrnyCountCheck=true;

					 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
			 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
			 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
			 		familyDetailModel.setGender(e.getGender().getDisplayValue());
			 		familyDetailModel.setGenderCode(e.getGender().ordinal());
			 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
			 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
			 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
			 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
			 		familyDetailModel.setDOIIDate(CommonUtil.formatDate(e.getPartDate(), "dd/MM/yyyy"));
			 		familyDetailModel.setDOIIPartNo(e.getPartNo());
			 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
			 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
			 		
			 		List<Integer> reqCountList=countBizlogic.getJourneyCountBySeqNo(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), currentYr);
					  int reqNo=reqCountList.get(0)+reqCountList.get(1);
				     	
					  if(eligblMap.get("daughterCount")!=-1 &&  (eligblMap.get("daughterCount")<=reqNo)){
						 jrnyCountCheck=false;
						 
					  }
				  
					  if(!jrnyCountCheck){
					  	familyDetailModel.setReason("Already Availed Journey On This TR Rule");
					   }
					  
					  familyDetailModel.setJourneyCheck(Boolean.toString(jrnyCountCheck));
					  familyDetailModel.setYearWiseFormDAndG(currentYr);
					    	
					  familyDetail.add(familyDetailModel);
					 
				 });
						
				 
				 otherDependent.forEach(e->{
					 
					 boolean jrnyCountCheckForOther=true;
					 
					 int relation=e.getRelation().ordinal();

					 FamilyDetailBulkBkgModel familyDetailModel=new FamilyDetailBulkBkgModel();
			 		familyDetailModel.setName(CommonUtil.removeSpace(Optional.ofNullable(e.getFirstName()).orElse("")+" "+Optional.ofNullable(e.getMiddleName()).orElse("")+" "+Optional.ofNullable(e.getLastName()).orElse("")));
			 		familyDetailModel.setDob(CommonUtil.formatDate(e.getDob(), "dd-MM-yyyy"));
			 		familyDetailModel.setGender(e.getGender().getDisplayValue());
			 		familyDetailModel.setGenderCode(e.getGender().ordinal());
			 		familyDetailModel.setRelationShip(e.getRelation().getDisplayValue());
			 		familyDetailModel.setRelationCode(e.getRelation().ordinal());
			 		familyDetailModel.setMaritalStatus(e.getMaritalStatus().getDisplayValue());
			 		familyDetailModel.setMaritalStatusCode(e.getMaritalStatus().ordinal());
			 		familyDetailModel.setDOIIDate(CommonUtil.formatDate(e.getPartDate(), "dd/MM/yyyy"));
			 		familyDetailModel.setDOIIPartNo(e.getPartNo());
			 		familyDetailModel.setErsPrintFamilyName(e.getErsPrintName());
			 		familyDetailModel.setDepSeqNo(e.getSeqNumber().intValue());
			 		
			 		List<Integer> reqCountList=countBizlogic.getJourneyCountBySeqNo(visitor.getUserId(), e.getSeqNumber(), travelTypeId, travelRule.getTrRuleId(), currentYr);
					  int reqNo=reqCountList.get(0)+reqCountList.get(1);
				     	
					  if(eligblMap.containsKey("stepSonCount") && eligblMap.get("stepSonCount") !=-1 &&  (relation==3)  && (eligblMap.get("stepSonCount")<=reqNo)){
						  jrnyCountCheckForOther=false;
				      }
				      if(eligblMap.containsKey("stepDaughterCount") && eligblMap.get("stepDaughterCount") !=-1 && (relation==5)  && (eligblMap.get("stepDaughterCount")<=reqNo)){
							 jrnyCountCheckForOther=false;
					  }
					 if(eligblMap.containsKey("brotherCount") && eligblMap.get("brotherCount") !=-1 && relation==6 && (eligblMap.get("brotherCount")<=reqNo)){
						 jrnyCountCheckForOther=false;
					 }
					 if(eligblMap.containsKey("sisterCount") && eligblMap.get("sisterCount") !=-1 && relation==7  && (eligblMap.get("sisterCount")<=reqNo)){
						 jrnyCountCheckForOther=false;
					 }
					 if(eligblMap.containsKey("fatherCount") && eligblMap.get("fatherCount") !=-1 && (relation==10) &&  (eligblMap.get("fatherCount")<=reqNo)){
						 jrnyCountCheckForOther=false;
					 }
					 if(eligblMap.containsKey("motherCount") && eligblMap.get("motherCount") !=-1 && relation==11 &&  (eligblMap.get("motherCount")<=reqNo)){
						 jrnyCountCheckForOther=false;
					 }
					 if(eligblMap.containsKey("stepMotherCount") && eligblMap.get("stepMotherCount") !=-1 && (relation==8) && (eligblMap.get("stepMotherCount")<=reqNo)){
						 jrnyCountCheckForOther=false;
					 }
					 if(eligblMap.containsKey("stepFatherCount") && eligblMap.get("stepFatherCount") !=-1 && relation==9  && (eligblMap.get("stepFatherCount")<=reqNo)){
						 jrnyCountCheckForOther=false;
					 }
					 if(eligblMap.containsKey("stepBrotherCount") && eligblMap.get("stepBrotherCount") !=-1&& relation==13  && (eligblMap.get("stepBrotherCount")<=reqNo)){
						 jrnyCountCheckForOther=false;
					 }
					 if(eligblMap.containsKey("stepSisterCount") && eligblMap.get("stepSisterCount") !=-1&& relation==14  && (eligblMap.get("stepSisterCount")<=reqNo)){
						 jrnyCountCheckForOther=false;
					 }
				  
					  if(!jrnyCountCheckForOther){
					  	familyDetailModel.setReason("Already Availed Journey On This TR Rule");
					   }
					  
					  familyDetailModel.setJourneyCheck(Boolean.toString(jrnyCountCheckForOther));
					    	
					  familyDetail.add(familyDetailModel);
					 
				 });
				  
		   }
		
		 }
		 
		 infoModel.setFamilyDetail(familyDetail);
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoUtil.class,logStr+"familyDetail At the end-->"+familyDetail.toString());
		 
		 
	}
	
	private Map<String, Integer> getEligibleMemberCountList(TravelRule travelRule) 
	{
		Map<String, Integer> hashMap=new HashMap<>();
		
		 try 
		 {
				 travelRule.getEligibilityMembers().forEach(obj->{
					 
					 int count=Optional.ofNullable(obj.getPerMemberJrnyCount()).orElse(0).intValue();
						
							 if(obj.getRelationType().ordinal()==0)hashMap.put("selfCount",count);
						else if(obj.getRelationType().ordinal()==1)hashMap.put("spouseCount",count);
						else if(obj.getRelationType().ordinal()==2)hashMap.put("sonCount",count);
						else if(obj.getRelationType().ordinal()==3)hashMap.put("stepSonCount",count);
						else if(obj.getRelationType().ordinal()==4)hashMap.put("daughterCount",count);
						else if(obj.getRelationType().ordinal()==5)hashMap.put("stepDaughterCount",count);
						else if(obj.getRelationType().ordinal()==6)hashMap.put("brotherCount",count);
						else if(obj.getRelationType().ordinal()==7)hashMap.put("sisterCount",count);
						else if(obj.getRelationType().ordinal()==8)hashMap.put("stepMotherCount",count);
						else if(obj.getRelationType().ordinal()==9)hashMap.put("stepFatherCount",count);
						else if (obj.getRelationType().ordinal()==10)hashMap.put("fatherCount",count);
						else if(obj.getRelationType().ordinal()==11)hashMap.put("motherCount",count);
						else if(obj.getRelationType().ordinal()==13)hashMap.put("stepBrotherCount",count);
						else if (obj.getRelationType().ordinal()==14)hashMap.put("stepSisterCount",count);
					 
				 });
				 
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		return hashMap;
	}
	
	private Map<String, Integer> getEligibleMemberCountListForCivilian(TravelRule travelRule) 
	{
		 Map<String, Integer> hashMap=new HashMap<>();
		 
		   try {
			   
			 travelRule.getEligibilityMembers().forEach(obj->{
				 
				 int count=Optional.ofNullable(obj.getPerMemberJrnyCount()).orElse(0).intValue();
					
						 if(obj.getRelationType().ordinal()==0)hashMap.put("selfCount",count);
					else if(obj.getRelationType().ordinal()==1)hashMap.put("spouseCount",count);
					else if(obj.getRelationType().ordinal()==2)hashMap.put("sonCount",count);
					else if(obj.getRelationType().ordinal()==3)hashMap.put("stepSonCount",count);
					else if(obj.getRelationType().ordinal()==4)hashMap.put("daughterCount",count);
					else if(obj.getRelationType().ordinal()==5)hashMap.put("stepDaughterCount",count);
					else if(obj.getRelationType().ordinal()==7)hashMap.put("sisterCount",count);
					else if(obj.getRelationType().ordinal()==6)hashMap.put("brotherCount",count);
					else if(obj.getRelationType().ordinal()==8)hashMap.put("stepMotherCount",count);
					else if(obj.getRelationType().ordinal()==9)hashMap.put("stepFatherCount",count);
					else if(obj.getRelationType().ordinal()==11)hashMap.put("motherCount",count);
					else if (obj.getRelationType().ordinal()==10)hashMap.put("fatherCount",count);
					else if(obj.getRelationType().ordinal()==13)hashMap.put("stepBrotherCount",count);
					else if (obj.getRelationType().ordinal()==14)hashMap.put("stepSisterCount",count);
				 
			 });
			 
		} catch (Exception e) {
			DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		 
		return hashMap;
	}
	
	public Map<String,String> getSchemeDetailsMap(TravelRule travelRule) 
	{
		Map<String,String> schemeDetailsMap=new HashMap<>();
		 try 
		 {
			
			 Optional<TRScheme> trRuleScheme=travelRule.getSchemes().stream().findFirst();
			
		    if (trRuleScheme.isPresent()) 
		    {
		    	TRScheme trRuleSchemeDtls=trRuleScheme.get();
		    	
		    	int blockStartYear=trRuleSchemeDtls.getBlockStartYear().intValue();
		    	int blockEndYear=trRuleSchemeDtls.getBlockEndYear().intValue();
		    	int maxYearsPerBlock=trRuleSchemeDtls.getMaxYearsPerBlock().intValue();
		    	int maxYearsPerSubBlock=trRuleSchemeDtls.getMaxYearsPerSubblock().intValue();
		    	
		    	
		    	String currentBlockYear=blockStartYear+"-"+blockEndYear;
		    	String previousBlockYear=(blockStartYear-maxYearsPerBlock)+"-"+(blockEndYear-maxYearsPerBlock);
		    	String nextBlockYear=(blockStartYear+ maxYearsPerBlock)+"-"+(blockEndYear+maxYearsPerBlock);
		    	
		    	schemeDetailsMap.put("previousBlockYear",previousBlockYear);
		    	schemeDetailsMap.put("currentBlockYear",currentBlockYear);
		    	schemeDetailsMap.put("nextBlockYear",nextBlockYear);
		    	
		    	String currentSubBlockYear="";
		    	String previousSubBlockYear="";
		    	String nextSubBlockYear="";
		    	
		    	int subBlockCount=maxYearsPerSubBlock;
		    	if(subBlockCount==1)schemeDetailsMap.put(String.valueOf("SchemeType"),"Yearly");
		    	else schemeDetailsMap.put(String.valueOf("SchemeType"),"BlockYearly");
		    	
		    	Calendar calendar=Calendar.getInstance();
		    	int currYear=calendar.get(Calendar.YEAR);
		    	
		    	String schemeAppliedDate="";
		    	
		    	if(trRuleSchemeDtls.getSchemeAppliedDate()!=null )
		    	{
		    			schemeAppliedDate=CommonUtil.formatDate(trRuleSchemeDtls.getSchemeAppliedDate(),"dd-MM-yyyy");
		    	    	currentSubBlockYear=currYear+"";
			    		previousSubBlockYear=(currYear- 1)+"";
				    	nextSubBlockYear=(currYear+ 1)+"";
				    	
				    	
				    	
		    	}else
		    	{	
		    		String[] currentBlockYearArr=currentBlockYear.split("-");
		    		String currentBlockSubBlock1=currentBlockYearArr[0]+"-"+(Integer.parseInt(currentBlockYearArr[0])+1);
		    		String currentBlockSubBlock2=(Integer.parseInt(currentBlockYearArr[1])-1)+"-"+currentBlockYearArr[1];
		    		
		    		if(currentBlockSubBlock1.contains(currYear+""))
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
		    	
		    	schemeDetailsMap.put("previousSubBlockYear",previousSubBlockYear);
		    	schemeDetailsMap.put("currentSubBlockYear",currentSubBlockYear);
		    	schemeDetailsMap.put("nextSubBlockYear",nextSubBlockYear);
		    	schemeDetailsMap.put("schemeAppliedDate",schemeAppliedDate);
		    }
		 }catch(Exception e){
			 DODLog.printStackTrace(e, TravelInfoService.class, LogConstant.JOURNEY_REQUEST_LOG);
		 }
	 
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG, TravelInfoService.class, "getSchemeDetailsMap()->"+schemeDetailsMap);
		 return schemeDetailsMap;
	}
	
	private Comparator<FamilyDetailsModel> byValue= new Comparator<FamilyDetailsModel>() {

		@Override
		public int compare(FamilyDetailsModel o1, FamilyDetailsModel o2) {
			
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(Calendar.getInstance().getTime());
			
			Calendar dobOne=Calendar.getInstance();
			dobOne.setTime(o1.getDob());
			
			Calendar dobTwo=Calendar.getInstance();
			dobTwo.setTime(o2.getDob());
		
			int ageOne=calendar.get(Calendar.YEAR)-dobOne.get(Calendar.YEAR);
			int ageTwo=calendar.get(Calendar.YEAR)-dobTwo.get(Calendar.YEAR);
			
			
			return ageOne-ageTwo;
	}

	};

}
