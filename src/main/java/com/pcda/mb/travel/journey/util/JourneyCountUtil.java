package com.pcda.mb.travel.journey.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.TravelRule;
import com.pcda.common.model.VisitorModel;
import com.pcda.mb.travel.journey.model.IntegerResponse;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class JourneyCountUtil {

	@Autowired
	private RestTemplate template;
	

	public boolean validateJourneyCount(VisitorModel travler,TravelRule travelRule,String traveltype, String requestType) 
	{
	
		String logStr="validateJourneyCount("+travler.getUserAlias()+"):";
		String travelTypeId=travelRule.getRulePurpose();
		String trRuleId= travelRule.getTrRuleId();
		
		
		long reqeustNo=0;
		boolean check=true;
		long allwdNoJrny=0;
		long trJrnyCount=Optional.ofNullable(travelRule.getJrnyCountAllowedTR()).orElse(0);
		long trJrnyCountYear=Optional.ofNullable(travelRule.getJrnyCountCalenderYear()).orElse(0);
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyCountUtil.class,logStr+"allwdNoJrny="+allwdNoJrny+"||trJrnyCount="+trJrnyCount+"||trJrnyCountYear="+trJrnyCountYear
				+"||requestType="+requestType+"||traveltype="+traveltype+"travelTypeId :>>"+travelTypeId+" trRuleId "+trRuleId);
		
		
		int totalRequestCount=getJourneyCountInCurrentYear(travler.getUserId(),travelTypeId);
		
		if(trJrnyCountYear==0 || trJrnyCountYear==-1)
		{
			
			 
			 if(trJrnyCount!=0 && trJrnyCount!=-1)
			 {
				 
				 allwdNoJrny=trJrnyCount;
				 reqeustNo=totalRequestCount;
				 
				
			 
				 if(reqeustNo>=allwdNoJrny)
					 check=false;
			 }
		 }
		 else
		 {
			
			 
			 if(traveltype.contains("FORM") || traveltype.contains("CV"))
			 {
				 reqeustNo=totalRequestCount;
				 Date creationDate= travler.getCreationDate();
				 Calendar calender=Calendar.getInstance();
				 calender.setTime(creationDate);
				 int creationDateYear=calender.get(Calendar.YEAR);
				 calender.setTime(new Date());
				 int currentDateYear=calender.get(Calendar.YEAR);
				 
				 
				 if(creationDateYear==currentDateYear)
					 reqeustNo=reqeustNo+travler.getTravelerProfile().getCvFormDUsed();
			 }
			 else
			 {
				 reqeustNo=totalRequestCount;
			 }
			 
		 	allwdNoJrny=trJrnyCountYear;
		 	
			if(reqeustNo>=allwdNoJrny)
				 check= false;
		}
		
		// add for FormD Next Year
		 if(travelTypeId.equalsIgnoreCase("100004")&& reqeustNo == 6){
			 
			int totalNextYearRequestCount=getJourneyCountInNextYear(travler.getUserId(),travelTypeId);
			
			 reqeustNo=totalNextYearRequestCount;
			 if(reqeustNo<=allwdNoJrny)
				 check= true;
		}
		
		
		return check;
	}
	
	public int getJourneyCountInNextYear(BigInteger userId,String travelTypeId)
	{
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyCountUtil.class,"getJourneyCountInNextYear()Param:userId="+userId+"||travelTypeId="+travelTypeId);

		int count=0;
		Calendar cal=Calendar.getInstance();
	    int currentYear=cal.get(Calendar.YEAR)+1;
		
		try {
			ResponseEntity<IntegerResponse> response= template.getForEntity(PcdaConstant.REQUEST_BASE_URL+"/getJourneyCountInYear?userId={userId}&travelTypeId={travelTypeId}&currentYear={currentYear}", 
					IntegerResponse.class, userId,travelTypeId,currentYear);
			
			IntegerResponse journeyResponse= response.getBody();
			
			if(null!=journeyResponse) {
				count=journeyResponse.getResponse().intValue();
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyCountUtil.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyCountUtil.class," getJourneyCountInNextYear --"+count);
		return count;
	}
	
	public int getJourneyCountInCurrentYear(BigInteger userId,String travelTypeId)
	{
		
		
		int count=0;
		Calendar cal=Calendar.getInstance();
	    int currentYear=cal.get(Calendar.YEAR);
	    
		try {
			ResponseEntity<IntegerResponse> response= template.getForEntity(PcdaConstant.REQUEST_BASE_URL+"/getJourneyCountInYear?userId={userId}&travelTypeId={travelTypeId}&currentYear={currentYear}", 
					IntegerResponse.class, userId,travelTypeId,currentYear);
			
			IntegerResponse journeyResponse= response.getBody();
			
			if(null!=journeyResponse && null!=journeyResponse.getResponse()) {
				count=journeyResponse.getResponse().intValue();
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyCountUtil.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyCountUtil.class," getJourneyCountInCurrentYear --"+count);
		return count;
	}
	
	public List<Integer> getCivilianUserJourneyCountInBlockYear(BigInteger userId,String travelTypeId,String trRuleId,String blockYear)
	  {
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyCountUtil.class,"getCivilianUserJourneyCountForLtc()Param:userId="+userId+"||travelTypeId="+travelTypeId+"||trRuleId="+trRuleId+"||blockYear="+blockYear);
			List<Integer> countList=new ArrayList<>();
			
			try {
				ResponseEntity<IntegerResponse> response= template.getForEntity(
						PcdaConstant.REQUEST_BASE_URL+"/getCivilianUserJourneyCountInBlockYear"
								+ "?userId={userId}&travelTypeId={travelTypeId}&trRuleId={trRuleId}&blockYear={blockYear}", 
						IntegerResponse.class, userId,travelTypeId,trRuleId,blockYear);
				
				IntegerResponse journeyResponse= response.getBody();
				
				if(null!=journeyResponse && null!=journeyResponse.getResponseList()) {
					countList.addAll(journeyResponse.getResponseList());
				}
			} catch (Exception e) {
				DODLog.printStackTrace(e, JourneyCountUtil.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
			
			
			
			return countList;
		}
	
	public List<Integer> getCivilianUserJourneyCountInSubblockYear(BigInteger userId,String travelTypeId,String trRuleId,String blockYear)
	{
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyCountUtil.class,"getCivilianUserJourneyCountForLtc()Param:userId="+userId+"||travelTypeId="+travelTypeId+"||trRuleId="+trRuleId+"||blockYear="+blockYear);
		List<Integer> countList=new ArrayList<>();
		
		try {
			ResponseEntity<IntegerResponse> response= template.getForEntity(
					PcdaConstant.REQUEST_BASE_URL+"/getCivilianUserJourneyCountInSubblockYear"
							+ "?userId={userId}&travelTypeId={travelTypeId}&trRuleId={trRuleId}&blockYear={blockYear}", 
					IntegerResponse.class, userId,travelTypeId,trRuleId,blockYear);
			
			IntegerResponse journeyResponse= response.getBody();
			
			if(null!=journeyResponse && null!=journeyResponse.getResponseList()) {
				countList.addAll(journeyResponse.getResponseList());
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyCountUtil.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
		
	 
		return countList;
	}
	
	public List<Integer> noOfJrnyCountForLTC(BigInteger userId,TravelRule travelRule,String year){
		
		 String travelTypeId=travelRule.getRulePurpose();
		 String trRuleId=travelRule.getTrRuleId();
	 	 
		 Calendar cal = Calendar.getInstance();
		 int currentYear = cal.get(Calendar.YEAR);
		 if(year.equals("current")){/*Nothing to do*/}
		 if(year.equals("next")){currentYear=currentYear+1;}
		 if(year.equals("previous")){currentYear=currentYear-1;}
	
		return getUserJourneyCountForLtc(userId,travelTypeId,trRuleId,currentYear);
	}
	
	private List<Integer> getUserJourneyCountForLtc(BigInteger userId,String travelTypeId,String trRuleId,int year)
	{
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyCountUtil.class,"getUserJourneyCountForLtc()Param:userId="+userId+"||travelTypeId="+travelTypeId+"||trRuleId="+trRuleId+"||year="+year);
		List<Integer> countList=new ArrayList<>();
		
		try {
			ResponseEntity<IntegerResponse> response= template.getForEntity(
					PcdaConstant.REQUEST_BASE_URL+"/getUserJourneyCountForLtc"
							+ "?userId={userId}&travelTypeId={travelTypeId}&trRuleId={trRuleId}&year={year}", 
					IntegerResponse.class, userId,travelTypeId,trRuleId,year);
			
			IntegerResponse journeyResponse= response.getBody();
			
			if(null!=journeyResponse && null!=journeyResponse.getResponseList()) {
				countList.addAll(journeyResponse.getResponseList());
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyCountUtil.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
		
	 
		return countList;
	}
	
	public int getPtJourneyCountForCurrentYear(BigInteger userId,TravelRule travelRule) 
	{
		String travelTypeId=travelRule.getRulePurpose();
		String trRuleId=travelRule.getTrRuleId();
		
		List<Integer> jrCountList=getUserJourneyCountInCurrentYear(userId,travelTypeId,trRuleId);
		int reqCount= jrCountList.get(0)+ jrCountList.get(1);
		
		return reqCount;
	}
  
  private List<Integer> getUserJourneyCountInCurrentYear(BigInteger userId,String travelTypeId,String trRuleId)
	{
	  DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyCountUtil.class,"getUserJourneyCountInCurrentYear()Param:userId="+userId+"||travelTypeId="+travelTypeId+"||trRuleId="+trRuleId);
		List<Integer> countList=new ArrayList<>();
		
		try {
			ResponseEntity<IntegerResponse> response= template.getForEntity(
					PcdaConstant.REQUEST_BASE_URL+"/getUserJourneyCountInCurrentYear"
							+ "?userId={userId}&travelTypeId={travelTypeId}&trRuleId={trRuleId}", 
					IntegerResponse.class, userId,travelTypeId,trRuleId);
			
			IntegerResponse journeyResponse= response.getBody();
			
			if(null!=journeyResponse && null!=journeyResponse.getResponseList()) {
				countList.addAll(journeyResponse.getResponseList());
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyCountUtil.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
	
	 
		return countList;
	}
  
  public List<Integer> getCivilianUserJourneyCountBySeqNoForLtc(BigInteger userId,int seqNo,String travelTypeId,String trRuleId,String year)
  {
	  DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyCountUtil.class,"getCivilianUserJourneyCountBySeqNoForLtc()Param:userId="+userId+"||seqNo="+seqNo+"||travelTypeId="+travelTypeId+"||trRuleId="+trRuleId+"||year="+year);
		List<Integer> countList=new ArrayList<>();
		
		try {
			ResponseEntity<IntegerResponse> response= template.getForEntity(
					PcdaConstant.REQUEST_BASE_URL+"/getCivilianUserJourneyCountBySeqNoForLtc"
							+ "?userId={userId}&seqNo={seqNo}&travelTypeId={travelTypeId}&trRuleId={trRuleId}&year={year}", 
					IntegerResponse.class, userId,seqNo,travelTypeId,trRuleId,year);
			
			IntegerResponse journeyResponse= response.getBody();
			
			if(null!=journeyResponse && null!=journeyResponse.getResponseList()) {
				countList.addAll(journeyResponse.getResponseList());
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyCountUtil.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
		
		
		return countList;
  }
  
  public List<Integer> getUserJourneyCountBySeqNoForLtc(BigInteger userId,int seqNo,String travelTypeId,String trRuleId,String year)
  {
	  DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyCountUtil.class,"getUserJourneyCountBySeqNoForLtc()Param:userId="+userId+"||seqNo="+seqNo+"||travelTypeId="+travelTypeId+"||trRuleId="+trRuleId+"||year="+year);
		List<Integer> countList=new ArrayList<>();
		
		try {
			ResponseEntity<IntegerResponse> response= template.getForEntity(
					PcdaConstant.REQUEST_BASE_URL+"/getUserJourneyCountBySeqNoForLtc"
							+ "?userId={userId}&seqNo={seqNo}&travelTypeId={travelTypeId}&trRuleId={trRuleId}&year={year}", 
					IntegerResponse.class, userId,seqNo,travelTypeId,trRuleId,year);
			
			IntegerResponse journeyResponse= response.getBody();
			
			if(null!=journeyResponse && null!=journeyResponse.getResponseList()) {
				countList.addAll(journeyResponse.getResponseList());
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyCountUtil.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyCountUtil.class,"getUserJourneyCountBySeqNoForLtc()countList="+countList);
		
		return countList;
  }
  
  public List<Integer> getJourneyCountBySeqNo(BigInteger userId,int seqNo,String travelTypeId,String trRuleId,String year)
	{
	  DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyCountUtil.class,"getJourneyCountBySeqNo()Param:userId="+userId+"||travelTypeId="+travelTypeId+"||trRuleId="+trRuleId+"||year="+year);
		List<Integer> countList=new ArrayList<>();

		try {
			ResponseEntity<IntegerResponse> response= template.getForEntity(
					PcdaConstant.REQUEST_BASE_URL+"/getJourneyCountBySeqNo"
							+ "?userId={userId}&seqNo={seqNo}&travelTypeId={travelTypeId}&trRuleId={trRuleId}&year={year}", 
					IntegerResponse.class, userId,seqNo,travelTypeId,trRuleId,year);
			
			IntegerResponse journeyResponse= response.getBody();
			
			if(null!=journeyResponse && null!=journeyResponse.getResponseList()) {
				countList.addAll(journeyResponse.getResponseList());
			}
		} catch (Exception e) {
			DODLog.printStackTrace(e, JourneyCountUtil.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		
		
	 
		return countList;
	}
  
  
  public List<Map<String, String>> getFormDAndGYears(VisitorModel visitor,TravelRule travelRule,String traveltype, String requestType) {

		String logStr="getFormDOrGYears("+visitor.getUserAlias()+"):";
		
		List<Map<String, String>> yearList= new ArrayList<>();
		Calendar calendar=Calendar.getInstance();
		String currentYr=String.valueOf(calendar.get(Calendar.YEAR));
		 
		String travelTypeId=travelRule.getRulePurpose();
		String trRuleId=travelRule.getTrRuleId();
		BigInteger userId=visitor.getUserId();
		
		
		
		long reqeustNo=0;
		boolean check=true;
		long allwdNoJrny=0;
		long trJrnyCount=travelRule.getJrnyCountAllowedTR().intValue();
		long trJrnyCountYear=Optional.ofNullable(travelRule.getJrnyCountCalenderYear()).orElse(0);
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyCountUtil.class,logStr+"allwdNoJrny="+allwdNoJrny+"||trJrnyCount="+trJrnyCount+"||trJrnyCountYear="+trJrnyCountYear+"||requestType="+requestType+"||traveltype="+traveltype
				+"by travelTypeId "+travelTypeId+" trRuleId "+trRuleId+" userId "+userId);
		
		int totalRequestCount=getJourneyCountInCurrentYear(userId,travelTypeId);
		
		if(trJrnyCountYear==0 || trJrnyCountYear==-1)
		{
			
			 
			 if(trJrnyCount!=0 && trJrnyCount!=-1)
			 {
				 
				 
				 allwdNoJrny=trJrnyCount;

				 reqeustNo=totalRequestCount;
				 
				 
			 
				 if(reqeustNo<allwdNoJrny){
					 Map<String, String> map=new HashMap<>();
					 map.put("Year", currentYr);
					 map.put("AvailReq", String.valueOf(allwdNoJrny-reqeustNo));
					 yearList.add(map);
				 }
					 
			 }
		 }
		 else
		 {
			
			 
			 if(traveltype.contains("FORM") || traveltype.contains("CV"))
			 {
				 reqeustNo=totalRequestCount;
				 Date creationDate=visitor.getCreationDate();
				 Calendar calender=Calendar.getInstance();
				 calender.setTime(creationDate);
				 int creationDateYear=calender.get(Calendar.YEAR);
				 calender.setTime(new Date());
				 int currentDateYear=calender.get(Calendar.YEAR);
				 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,JourneyCountUtil.class,logStr+"creationDate-"+creationDate+"||creationDateYear-"+creationDateYear+"||currentDateYear="+currentDateYear+"||CV_FD_USED-"+visitor.getTravelerProfile().getCvFormDUsed());
				 
				 
				 if(creationDateYear==currentDateYear) {
					 reqeustNo=reqeustNo+visitor.getTravelerProfile().getCvFormDUsed();
			 }
				 
			 }
			 else
			 {
				 reqeustNo=totalRequestCount;
			 }
			
		 	allwdNoJrny=trJrnyCountYear;
		 	
			if(reqeustNo<allwdNoJrny){
				 Map<String, String> map=new HashMap<>();
				 map.put("Year", currentYr);
				 map.put("AvailReq", String.valueOf(allwdNoJrny-reqeustNo));
				yearList.add(map);
		}
		
		}
		
		// add for FormD Next Year
		 if(travelTypeId.equalsIgnoreCase("100004") || travelTypeId.equalsIgnoreCase("100012")){
			Calendar cal=Calendar.getInstance();
			int currentYear=cal.get(Calendar.YEAR)+1;
			int totalNextYearRequestCount=getJourneyCountInNextYear(userId,travelTypeId);
			reqeustNo=totalNextYearRequestCount;
			if(reqeustNo<allwdNoJrny){
				Map<String, String> map=new HashMap<>();
				map.put("Year", String.valueOf(currentYear));
				map.put("AvailReq", String.valueOf(allwdNoJrny-reqeustNo));
				yearList.add(map);
				
			}
		}
		
			return yearList;
		}
  
}
