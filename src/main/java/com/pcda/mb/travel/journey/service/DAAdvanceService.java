package com.pcda.mb.travel.journey.service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.common.model.TaDaExpense;
import com.pcda.common.model.TravelRule;
import com.pcda.common.model.User;
import com.pcda.common.services.TADAExpenseServices;
import com.pcda.common.services.TRRuleService;
import com.pcda.common.services.UserServices;
import com.pcda.mb.travel.journey.model.BooleanResponse;
import com.pcda.mb.travel.journey.model.CompositeTransferData;
import com.pcda.mb.travel.journey.model.DAAdvanceAmountData;
import com.pcda.mb.travel.journey.model.DAAdvanceDataModel;
import com.pcda.mb.travel.journey.model.MaxDAAdvanceModel;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class DAAdvanceService {

	@Autowired
	private TADAExpenseServices expenseServices;
		
	@Autowired
	private TRRuleService ruleService;
	
	@Autowired
	private RestTemplate template;

	@Autowired
	private UserServices userServices;

	
	public DAAdvanceDataModel getDAAdvanceDetails(HttpServletRequest request) {
		
		DAAdvanceDataModel daAdvanceDataModel=new DAAdvanceDataModel();
		try {
		String categoryId=Optional.ofNullable(request.getParameter("category_id")).orElse("");
		String serviceId=Optional.ofNullable(request.getParameter("service_id")).orElse("");
		String rank=Optional.ofNullable(request.getParameter("rank")).orElse("");
		String trRuleID=Optional.ofNullable(request.getParameter("trRuleID")).orElse("");
		String cityRankId=Optional.ofNullable(request.getParameter("cityRankId")).orElse("");
		String hotelAllowForTR=Optional.ofNullable(request.getParameter("hotelAllowForTR")).orElse("1");
		String conveyanceAllowForTR=Optional.ofNullable(request.getParameter("conveyanceAllowForTR")).orElse("1");
		String foodAllowForTR=Optional.ofNullable(request.getParameter("foodAllowForTR")).orElse("1");
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,DAAdvanceService.class, "[getTotalDAAmount] :::service_id="+serviceId+"|category_id="+categoryId+"|rank-"+rank+"|trRuleID:"+trRuleID
	 			 +"|hotelAllowForTR:"+hotelAllowForTR+"|cityRankId:-"+cityRankId+"|conveyanceAllowForTR:"+conveyanceAllowForTR+"|foodAllowForTR:"+foodAllowForTR);
		
		TaDaExpense daExpense=expenseServices.getTADAExpenseDataByRank(serviceId, categoryId, rank, cityRankId);
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,DAAdvanceService.class, "[getTotalDAAmount] :::taDaMappingContent = "+daExpense);
		
	
		
		if(null!=daExpense) {
		
		TravelRule travelRule=ruleService.getTRRuleDetails(trRuleID);
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,DAAdvanceService.class, "[getTotalDAAmount] :::trRulContent = "+travelRule); 
		

		double hotelAmt = daExpense.getHotelAmount();
		double conveyanceAmt = daExpense.getConvenceAmount();
		double foodAmt = daExpense.getFoodAmount();
		double miscAmt = daExpense.getMiscAmount();
 	
		
	 		 		
 		int hotelAllow=1;
 		int conveyanceAllow=1;
 		int foodAllow=1;
 		int miscAllow=1;
 		
 		if(null!=travelRule && travelRule.getDaAllowed().ordinal()==0){
 			hotelAllow=travelRule.getHotelAllowanceAllow().ordinal();
 			conveyanceAllow=travelRule.getConveyanceAllowanceAllow().ordinal();
 			foodAllow=travelRule.getFoodAllowanceAllow().ordinal();
 			miscAllow=travelRule.getMiscAllowanceAllow().ordinal();
 		}
 		
 		if("1".equals(hotelAllowForTR)){
 			hotelAllow=1;
 		}
 		if("1".equals(conveyanceAllowForTR)){
 			conveyanceAllow=1;
 		}
 		if("1".equals(foodAllowForTR)){
 			foodAllow=1;
 		}
 		
 		
 		
 		double totalAmount=0d;
 		
 		if(hotelAllow==0){
 			totalAmount=totalAmount+hotelAmt;
 		}
 		if(conveyanceAllow==0){
 			totalAmount=totalAmount+conveyanceAmt;
 		}
 		if(foodAllow==0){
 			totalAmount=totalAmount+foodAmt;
 		}
 		if(miscAllow==0){
 			totalAmount=totalAmount+miscAmt;
 		}
 		
 		
 		
 		daAdvanceDataModel.setMaxHotelAmt(hotelAmt);
 		daAdvanceDataModel.setMaxConveyanceAmt(conveyanceAmt); 
 		daAdvanceDataModel.setMaxFoodAmt(foodAmt);
 		daAdvanceDataModel.setMaxMiscAmt(miscAmt);
 		daAdvanceDataModel.setHotelAllowed(hotelAllow);
 		daAdvanceDataModel.setConveyanceAllowed(conveyanceAllow);
 		daAdvanceDataModel.setFoodAllowed(foodAllow);
 		daAdvanceDataModel.setMiscAllowed(miscAllow);
 		daAdvanceDataModel.setTotalAmount(totalAmount); 
 		
 		daAdvanceDataModel.setMaxKilometerRate(daExpense.getKilometerRate());
 		daAdvanceDataModel.setMaxAllowedWeight(daExpense.getLuggageWeight()); 
	
		
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e,DAAdvanceService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,DAAdvanceService.class, " ::: daAdvanceDataModel = "+daAdvanceDataModel);
		return daAdvanceDataModel;
	}

	@Async("pcdaAsyncExecutor")
	public CompletableFuture<Boolean> getTaggedReqAdvanceCount(HttpServletRequest request) {
		
		boolean flag=true;
		try {
		String requestId=Optional.ofNullable(request.getParameter("requestId")).orElse(""); 
		
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,DAAdvanceService.class, " getTaggedReqAdvanceCount ::: requestId = "+requestId);
		
		if(!requestId.isBlank()) {
			ResponseEntity<BooleanResponse> response= template.getForEntity(PcdaConstant.REQUEST_BASE_URL+"/getTaggedReqAdvanceCount/"+requestId, BooleanResponse.class);
			
			BooleanResponse booleanResponse=  response.getBody();
			if(null!=booleanResponse && booleanResponse.getErrorCode()==200) {
				flag=booleanResponse.getResponse();
			}
		
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e,DAAdvanceService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,DAAdvanceService.class, " getTaggedReqAdvanceCount ::: flag "+flag);
		return CompletableFuture.completedFuture(flag);
	}

	@Async("pcdaAsyncExecutor")
	public CompletableFuture<MaxDAAdvanceModel> calMaxDAAmount(HttpServletRequest request) {
		MaxDAAdvanceModel advanceModel=new MaxDAAdvanceModel();
		try {
		 String categoryId=Optional.ofNullable(request.getParameter("category_id")).orElse("0");
	 	 String serviceId=Optional.ofNullable(request.getParameter("service_id")).orElse("0") ;
	 	 String rank=Optional.ofNullable(request.getParameter("rank")).orElse("");
	 	 String noOfDays=Optional.ofNullable(request.getParameter("noOfDays")).orElse("0");
	 	 String trRuleID=Optional.ofNullable(request.getParameter("trRuleID")).orElse("");
	 	 String hotelAllowForTR=Optional.ofNullable(request.getParameter("hotelAllowForTR")).orElse("1");
	 	 String conveyanceAllowForTR=Optional.ofNullable(request.getParameter("conveyanceAllowForTR")).orElse("1");
	 	 String foodAllowForTR=Optional.ofNullable(request.getParameter("foodAllowForTR")).orElse("1");
	 	 String amount=Optional.ofNullable(request.getParameter("amount")).orElse("0");
	 	DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,DAAdvanceService.class, "[calMaxDAAmount] :::serviceId="+serviceId+"|categoryId="+categoryId+"|rank="+rank+"|trRuleID="+trRuleID+" | amount="+amount
	 			+" | noOfDays="+noOfDays+" | hotelAllowForTR="+hotelAllowForTR+"|conveyanceAllowForTR="+conveyanceAllowForTR+"|foodAllowForTR="+foodAllowForTR);
	 	
	 	
	 	TaDaExpense daExpense=expenseServices.getTADAExpenseDataByRank(serviceId, categoryId, rank, "");
	 	
	 	 double oneDayTotalDaAmt=0;
	 	 if(daExpense!=null)
	 	 {
	 		TravelRule travelRule=ruleService.getTRRuleDetails(trRuleID);
			DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,DAAdvanceService.class, "[calMaxDAAmount] :::trRulContent = "+travelRule+"####### daExpense :: "+daExpense); 
			
			double hotelAmt = daExpense.getHotelAmount();
			double conveyanceAmt = daExpense.getConvenceAmount();
			double foodAmt = daExpense.getFoodAmount();
			double miscAmt = daExpense.getMiscAmount();
	 		
	 		int hotelAllow=1;
	 		int conveyanceAllow=1;
	 		int foodAllow=1;
	 		int miscAllow=1;
	 		
	 		if(null!=travelRule && travelRule.getDaAllowed().ordinal()==0){
	 			hotelAllow=travelRule.getHotelAllowanceAllow().ordinal();
	 			conveyanceAllow=travelRule.getConveyanceAllowanceAllow().ordinal();
	 			foodAllow=travelRule.getFoodAllowanceAllow().ordinal();
	 			miscAllow=travelRule.getMiscAllowanceAllow().ordinal();
	 		}
	 		
	 		if("1".equals(hotelAllowForTR)){
	 			hotelAllow=1;
	 		}
	 		if("1".equals(conveyanceAllowForTR)){
	 			conveyanceAllow=1;
	 		}
	 		if("1".equals(foodAllowForTR)){
	 			foodAllow=1;
	 		}
	 		
	 		if(hotelAllow==0){
	 			oneDayTotalDaAmt=oneDayTotalDaAmt+hotelAmt;
	 		}
	 		if(conveyanceAllow==0){
	 			oneDayTotalDaAmt=oneDayTotalDaAmt+conveyanceAmt;
	 		}
	 		if(foodAllow==0){
	 			oneDayTotalDaAmt=oneDayTotalDaAmt+foodAmt;
	 		}
	 		if(miscAllow==0){
	 			oneDayTotalDaAmt=oneDayTotalDaAmt+miscAmt;
	 		}
	 		
	 		
		 		
	 		
		 	long days=Long.parseLong(noOfDays);
		 	long trRuleDays= Optional.ofNullable(travelRule.getDaAdvanceDay()).orElse(0);
		 	   if(days > trRuleDays){
		 		  days = trRuleDays;
	           }
		 	   
		 
		 	
	 		double totalAmt=oneDayTotalDaAmt*days;
	 		
	 		
	 		
	 		if(Double.parseDouble(amount) > totalAmt){
	 			advanceModel.setFlag("false");
	 			if(hotelAllow==0){
	 				advanceModel.setMsg("Hotel DA Advance Amount should not be greater than Rs. "+totalAmt);
		 		}else if(conveyanceAllow==0){
		 			advanceModel.setMsg("Conveyance DA Advance Amount should not be greater than Rs. "+totalAmt);
		 		}else if(foodAllow==0){
		 			advanceModel.setMsg("Food DA Advance Amount should not be greater than Rs. "+totalAmt);
		 		}
	 		}else{
	 			advanceModel.setFlag("true");
	 		}
		}else{
			advanceModel.setFlag("false");
			advanceModel.setMsg("DA Advance Amount Can Not Be Availed ");
			
		}
		}catch(Exception e) {
			DODLog.printStackTrace(e,DAAdvanceService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
	 	 
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,DAAdvanceService.class, "[calMaxDAAmount] ::: advanceModel= "+ advanceModel); 
		return CompletableFuture.completedFuture(advanceModel);
	}

	@Async("pcdaAsyncExecutor")
	public CompletableFuture<CompositeTransferData> getCompositeTransferAmount(HttpServletRequest request) {
		CompositeTransferData transferData=new CompositeTransferData();
		 try {
		 String personalNo=Optional.ofNullable(request.getParameter("personalNo")).orElse("");
		 
		 Optional<User> userOpt=userServices.getUserByUserAlias(personalNo);
		 
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,DAAdvanceService.class, "getCompositeTransferAmount::: userOpt = "+userOpt+" || personalNo = "+personalNo);
		 
		 
		 if(userOpt.isPresent()) {
			 User user= userOpt.get();
			 TaDaExpense daExpense=expenseServices.getTADAExpenseDataByLevel(user.getUserServiceId(), user.getCategoryId(), user.getLevelId(), "");
			 
			
			
			 if(null!=daExpense) {
				 transferData.setVehicleAmt(daExpense.getVehicleTransportRate());
				 transferData.setKmRate(daExpense.getKilometerRate()); 
				 transferData.setWeight(daExpense.getLuggageWeight()); 
			 }else {
				 transferData.setVehicleAmt(0);
				 transferData.setKmRate(0); 
				 transferData.setWeight(0); 
			 }
		 }
		 
		
		 }catch(Exception e) {
				DODLog.printStackTrace(e,DAAdvanceService.class, LogConstant.JOURNEY_REQUEST_LOG);
			}
		 DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,DAAdvanceService.class,"getCompositeTransferAmount::: ->transferData:: "+transferData.toString());
	 	return CompletableFuture.completedFuture(transferData);
	 }

	@Async("pcdaAsyncExecutor")
	public CompletableFuture<DAAdvanceAmountData> calculateDAAmount(HttpServletRequest request) {
		
		DAAdvanceAmountData amountData=new DAAdvanceAmountData();
		try {
		 String categoryId=Optional.ofNullable(request.getParameter("category_id")).orElse("0");
	 	 String serviceId=Optional.ofNullable(request.getParameter("service_id")).orElse("0");
	 	 String rank=Optional.ofNullable(request.getParameter("rank")).orElse("");
	 	 String noOfDays=Optional.ofNullable(request.getParameter("noOfDays")).orElse("");
	 	 String trRuleID=Optional.ofNullable(request.getParameter("trRuleID")).orElse("");
	 	 String hotelAllowForTR=Optional.ofNullable(request.getParameter("hotelAllowForTR")).orElse("1");
	 	 String conveyanceAllowForTR=Optional.ofNullable(request.getParameter("conveyanceAllowForTR")).orElse("1");
	 	 String foodAllowForTR=Optional.ofNullable(request.getParameter("foodAllowForTR")).orElse("1");
	 	 
	 	DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,DAAdvanceService.class, "[calculateDAAmount] :::service_id="+serviceId+"|category_id="+categoryId+"|rank="+rank+"|trRuleID="+trRuleID+" | noOfDays="+noOfDays
	 			+" | hotelAllowForTR="+hotelAllowForTR+"|conveyanceAllowForTR="+conveyanceAllowForTR+"|foodAllowForTR="+foodAllowForTR);
	 	
	 	 
	 	 
	 	TaDaExpense daExpense=expenseServices.getTADAExpenseDataByRank(serviceId, categoryId, rank, "");
	 	
	 	
	 
	 	 double oneDayTotalDaAmt=0;
	 	 if(daExpense!=null)
	 	 {
	 		TravelRule travelRule=ruleService.getTRRuleDetails(trRuleID);
	 		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,DAAdvanceService.class, "[calculateDAAmount] :::trRuleContent= "+travelRule +"### daExpense = "+daExpense);
	 		
	 		double hotelAmt = daExpense.getHotelAmount();
			double conveyanceAmt = daExpense.getConvenceAmount();
			double foodAmt = daExpense.getFoodAmount();
			double miscAmt = daExpense.getMiscAmount();
	 		
	 		int hotelAllow=1;
	 		int conveyanceAllow=1;
	 		int foodAllow=1;
	 		int miscAllow=1;
	 		

	 		if(null!=travelRule && travelRule.getDaAllowed().ordinal()==0){
	 			hotelAllow=travelRule.getHotelAllowanceAllow().ordinal();
	 			conveyanceAllow=travelRule.getConveyanceAllowanceAllow().ordinal();
	 			foodAllow=travelRule.getFoodAllowanceAllow().ordinal();
	 			miscAllow=travelRule.getMiscAllowanceAllow().ordinal();
	 		}
	 		
	 		
	 		if("1".equals(hotelAllowForTR)){
	 			hotelAllow=1;
	 		}
	 		if("1".equals(conveyanceAllowForTR)){
	 			conveyanceAllow=1;
	 		}
	 		if("1".equals(foodAllowForTR)){
	 			foodAllow=1;
	 		}
	 		
	 		if(hotelAllow==0){
	 			oneDayTotalDaAmt=oneDayTotalDaAmt+hotelAmt;
	 		}
	 		if(conveyanceAllow==0){
	 			oneDayTotalDaAmt=oneDayTotalDaAmt+conveyanceAmt;
	 		}
	 		if(foodAllow==0){
	 			oneDayTotalDaAmt=oneDayTotalDaAmt+foodAmt;
	 		}
	 		if(miscAllow==0){
	 			oneDayTotalDaAmt=oneDayTotalDaAmt+miscAmt;
	 		}
	 	   	
		 		
	 		
	 		long days=Long.parseLong(noOfDays);
	 		long trRuleDays = 0L;
	 		if(travelRule!=null) {
	 			trRuleDays= Optional.ofNullable(travelRule.getDaAdvanceDay()).orElse(0);
	 		}
		
		 	
		 	   if(days > trRuleDays){
		 		  days = trRuleDays;
	           }
		 	   
		 	
		 	  
		 	 double totalAmt=oneDayTotalDaAmt*days;
	 		
	 		amountData.setTotalAmt(totalAmt);
	 		
		}else{
			
			amountData.setTotalAmt(-1);
			amountData.setMsg("DA Advance Amount Can Not Be Availed Because Mapping For The Same Not Found . Please Contact Customer Care."); 
		}
	 	 
	 
		}catch(Exception e) {
			DODLog.printStackTrace(e,DAAdvanceService.class, LogConstant.JOURNEY_REQUEST_LOG);
		}
		DODLog.info(LogConstant.JOURNEY_REQUEST_LOG,DAAdvanceService.class,"[calculateDAAmount]->amountData:: "+amountData.toString());
	 	return CompletableFuture.completedFuture(amountData);
	 }

}
