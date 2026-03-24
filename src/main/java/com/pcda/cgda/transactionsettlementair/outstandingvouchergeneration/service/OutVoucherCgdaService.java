package com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.service;



import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model.AccountOfficeCgdaResponse;
import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model.AfterVoucherGenCgdaResponse;
import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model.GenerateVoucherCgdaResponse;
import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model.GetOutVoucherGenCgdaDataModel;
import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model.OutVoucherGenCgdaReqModel;
import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model.PopUpParamCgdaModel;
import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model.PostOutStandVoucherGenCgdaModel;
import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.model.PostOutVouchGenCgdaChildModel;

import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

import jakarta.servlet.http.HttpServletRequest;


@Service

public class OutVoucherCgdaService {

@Autowired

private RestTemplate restTemplate;
	
 public List<GetOutVoucherGenCgdaDataModel>	getGenerateReportData(OutVoucherGenCgdaReqModel genReqModel){
	 
	 List<GetOutVoucherGenCgdaDataModel>  modelList = new ArrayList<>();
	 DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE,OutVoucherCgdaService.class, "######### Cgda Search Model outVouchGen ###### "+genReqModel);
    try {
	
	
	 String url = PcdaConstant.OUTSTANDING_VOUCHER_GENERATION_URL+
	"/getVoucherGenerateData?fromBookingDate={fromBookingDate}&toBookingDate={toBookingDate}&bookingId={bookingId}&txnId={txnId}&serviceType={serviceType}&personalNo={personalNo}&travelType={travelType}&accountOffice={accountOffice}&statusType={statusType}&unitOffice={unitOffice}&serviceProvider={serviceProvider}";
	 
	 Map<String, String> params = new HashMap<>();
	 params.put("fromBookingDate", genReqModel.getFromBookingDate());
	 params.put("toBookingDate", genReqModel.getToBookingDate());
	 params.put("bookingId", genReqModel.getBookingId());
	 params.put("txnId", genReqModel.getTxnId());
	 params.put("serviceType", genReqModel.getServiceType());
	 params.put("personalNo", genReqModel.getPersonalNo());
	 params.put("travelType", genReqModel.getTravelType());
	 params.put("accountOffice", genReqModel.getAccountOffice());
	 params.put("statusType", genReqModel.getStatusType());
	 params.put("unitOffice", genReqModel.getUnitOffice());
	 params.put("serviceProvider", genReqModel.getServiceProvider().toString());
	 
	 GenerateVoucherCgdaResponse response = restTemplate.getForObject(url, GenerateVoucherCgdaResponse.class, params);
	 
	 
	 
	 if(response!=null && response.getErrorCode()==200 && null!=response.getResponseList()) {
		  modelList =  response.getResponseList();
		 
		  
	 }
	 
	 DODLog.info(LogConstant.CGDA_VOUCHER_LOG_FILE,OutVoucherCgdaService.class, "######### modelList ###### "+modelList.size());
	}
	catch(Exception e) {
		DODLog.printStackTrace(e, OutVoucherCgdaService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
	}
   
    return modelList;
 }
	
 
 // create and download csv
 
	
	public void createCSVDownload(PrintWriter printWriter , List<GetOutVoucherGenCgdaDataModel> modelList) {
		
		StringBuilder buffer=new StringBuilder();
		buffer.append("Personal_No");
		buffer.append(",");
		buffer.append("TravelType");
		buffer.append(",");
		buffer.append("First_Name");
		buffer.append(",");
		buffer.append("Last_Name");
		buffer.append(",");
		buffer.append("CDAO_Ac_No");
		buffer.append(",");
		buffer.append("Unit");
		buffer.append(",");
		buffer.append("PAO_Name");
		buffer.append(",");
		buffer.append("Authority_No");
		buffer.append(",");
		buffer.append("Authority_Date");
		buffer.append(",");
		buffer.append("DGCA_Apporval_No");
		buffer.append(",");
		buffer.append("DGCA_Apporval_Date");
		buffer.append(",");
		buffer.append("FB_Number"); 
		buffer.append(",");
		buffer.append("Transaction_Id");
		buffer.append(",");
		buffer.append("Transaction_Date");
		buffer.append(",");
		buffer.append("Base_Fare");
		buffer.append(",");
		buffer.append("YQ");
		buffer.append(",");
		buffer.append("Other_Tax");
		buffer.append(",");
		buffer.append("SSR_Amount");
		buffer.append(",");
		buffer.append("Service_Tax");
		buffer.append(",");
		buffer.append("Edu_cess");
		buffer.append(",");
		buffer.append("Higher_Edu_cess");
		buffer.append(",");
		buffer.append("Cancellation_Charges");
		buffer.append(",");
		buffer.append("Transaction_Amount");
		buffer.append(",");
		buffer.append("Transaction_Nature");
		buffer.append(",");
		buffer.append("Cancellation_Ground");
		buffer.append(",");
		buffer.append("LTC_Fare");
		buffer.append(",");
		buffer.append("Reference_Id");
		buffer.append(",");
		buffer.append("Reference_Id_Date");
		buffer.append(",");
		
		buffer.append("Reference_Id_Amount");
		buffer.append(",");
		buffer.append("Reference_Id_Nature");
		buffer.append(",");
		buffer.append("Net_Outstanding");
		buffer.append(",");
		buffer.append("Nature_Net_Outstanding");
		buffer.append(",");
		buffer.append("Due_Amount");
		buffer.append(",");
		buffer.append("Due_Amount_Nature");
		buffer.append(",");
		buffer.append("Travel_Date");
		buffer.append(",");
		buffer.append("Traveler_Title");
		buffer.append(",");
		buffer.append("Traveler_First_Name");
		buffer.append(",");
		buffer.append("Traveler_Last_Name");
		buffer.append(",");
		buffer.append("Traveler_Gender");
		buffer.append(",");
		buffer.append("Traveler_Age");
		buffer.append(",");
		buffer.append("Traveler_Relation");
		buffer.append(",");
		buffer.append("Ticket_No");
		buffer.append(",");
		buffer.append("GDS_PNR_No");
		buffer.append(",");
		buffer.append("Airline_PNR_No");
		buffer.append(",");
		buffer.append("Airline_Code");
		buffer.append(",");
		buffer.append("Sector_Detail");
		buffer.append(",");
		buffer.append("Flight_No");
		buffer.append(",");
		buffer.append("Status");
		buffer.append(",");
		buffer.append("Journey_Type");
		buffer.append(",");
		buffer.append("CGST");
		buffer.append(",");
		buffer.append("SGST");
		buffer.append(",");
		buffer.append("IGST");
		buffer.append(",");
		buffer.append("UGST");
		buffer.append(",");
		buffer.append("K3");
		
	
		
		
		 printWriter.write(buffer.toString()+"\n");
		 for(GetOutVoucherGenCgdaDataModel model : modelList) {
			 
			 StringBuilder build = new StringBuilder();
			
		  build.append(model.getPersonalNo()); 
			build.append(",");
			build.append(model.getTravelType()); 
			build.append(",");
			build.append(model.getFirstName()); 
			build.append(",");
			build.append(model.getLastName()); 
			build.append(",");
			build.append(model.getCdaoAcNo()); 
			build.append(",");
			build.append(model.getUnit()); 
			build.append(",");
			build.append(model.getPaoName()); 
			build.append(",");
			build.append(model.getAuthorityNo()); 
			build.append(",");
			build.append(model.getAuthorityDate()); 
			build.append(",");
			build.append(model.getDgcaApporvalNo()); 
			build.append(",");
			build.append(model.getDgcaApporvalDate()); 
			build.append(",");
			build.append(model.getFbNo()); 
			build.append(",");
			build.append(model.getTransactionId()); 
			build.append(",");
			build.append(model.getTransactionDate()); 
			build.append(",");
			build.append(model.getBaseFare().toString()); 
			build.append(",");
			build.append(model.getYq().toString()); 
			build.append(",");
			build.append(model.getOtherTax().toString()); 
			build.append(",");
			build.append(model.getSsrAmount().toString()); 
			build.append(",");
			build.append(model.getServiceTax().toString()); 
			build.append(",");
			build.append(model.getEducess().toString()); 
			build.append(",");
			build.append(model.getHigherEducess().toString()); 
			build.append(",");
			build.append(model.getCancellationCharges().toString()); 
			build.append(",");
			build.append(model.getTransactionAmount().toString()); 
			build.append(",");
			build.append(model.getTransactionNature()); 
			build.append(",");
			build.append(model.getCancellationGround()); 
			build.append(",");
			build.append(model.getLtcFare()); 
			build.append(",");
			build.append(model.getReferenceId()); 
			build.append(",");
			build.append(model.getReferenceIdDate()); 
			build.append(",");
			if(model.getAmountDrCr().equalsIgnoreCase("CR")){
				build.append(model.getReferenceIdAmount());
			}else{
				build.append("N/A");
			} 
			build.append(",");
		    build.append(model.getReferenceIdNature()); 
			build.append(",");
			build.append(model.getNetOutstanding().toString()); 
			build.append(",");
			build.append(model.getNatureNetOutstanding()); 
			build.append(",");
			build.append(model.getDueAmount().toString()); 
			build.append(",");
			build.append(model.getDueAmountNature()); 
			build.append(",");
			build.append(model.getTravelDate()); 
			build.append(",");
			build.append(model.getTravelerTitle()); 
			build.append(",");
			build.append(model.getTravelerFirstName()); 
			build.append(",");
			build.append(model.getTravelerLastName()); 
			build.append(",");
			build.append(model.getTravelerGender()); 
			build.append(",");
			build.append(model.getTravelerAge().toString()); 
			build.append(",");
			build.append(model.getTravelerRelation()); 
			build.append(",");
			build.append(model.getTicketNo()); 
			build.append(",");
			build.append(model.getGdsPnrNo()); 
			build.append(",");
			build.append(model.getAirlinePnrNo()); 
			build.append(",");
			build.append(model.getAirlineCode()); 
			build.append(",");
			build.append(model.getSectorDetail()); 
			build.append(",");
			build.append(model.getFlightNo()); 
			build.append(",");
			
			if(model.getValidForVoucher().equals("YES")){
				build.append("Voucher Not Generated");
			}else{
				build.append("Voucher Generated");
			}
			build.append(",");
		    build.append(model.getJourneyType()); 
			build.append(",");
			build.append(model.getCgstTax().toString()); 
			build.append(",");
			build.append(model.getSgstTax().toString()); 
			build.append(",");
			build.append(model.getIgstTax().toString()); 
			build.append(",");
			build.append(model.getUgstTax().toString()); 
			build.append(",");
			build.append(model.getK3Tax().toString()); 
		
			
			
	     	printWriter.write(build.toString()+"\n");
	     	
	   
		 }
		 printWriter.flush();
		 
		 
	}
	
	
//popup
	
	public  List<PopUpParamCgdaModel> voucherGenerationSummaryDataInXML(HttpServletRequest request){
		 
		  
		  String personalNo = request.getParameter("personalNo") ;
		  
		  String transactionId = request.getParameter("transaction_Id") ;
		  
		  String netOutstanding = request.getParameter("net_Outstanding") ;
		  
		  String dueAmount = request.getParameter("due_Amount") ;
		  
		  String[] personalNoValues = personalNo.split(",") ;
		  
		  String[] transactionIdValues = transactionId.split(",") ;
		  
		  String[] netOutstandingValues = netOutstanding.split(",") ;
		  
		  String[] dueAmountValues = dueAmount.split(",") ;
		  
		  double netOutstandingtotal = 0.00 ;
		  
		  double dueAmounttotal = 0.00 ;
		  
		  List<PopUpParamCgdaModel>  paramModelList = new ArrayList<>(); 
		  try {
			 if(null!=personalNoValues && personalNoValues.length>0)
			 {
				 
					for(int index=0;index<personalNoValues.length;index++)
				 	{
						 netOutstandingtotal = netOutstandingtotal+Double.valueOf(netOutstandingValues[index]) ;
						 dueAmounttotal = dueAmounttotal+Double.valueOf(dueAmountValues[index]);
				 	}
				 
					
					
				for(int i=0;i<personalNoValues.length;i++)
				 	{
					PopUpParamCgdaModel model =  new PopUpParamCgdaModel();
					
					model.setSeqNo(i+1);
				 	model.setPersonalNo(personalNoValues[i]);	
				 	model.setTransactionId(transactionIdValues[i]);
				 	model.setNetOutstanding(netOutstandingValues[i]);
				 	model.setDueAmount(dueAmountValues[i]);	
				 		
				 	
				 	model.setNetOutstandingTotal(String.valueOf(netOutstandingtotal));
					model.setDueAmountTotal(String.valueOf(dueAmounttotal));
				 	paramModelList.add(model);
				 		
				 	
				   }
				
				 	
				 	
				 	 
			 }
			 return paramModelList;
		  }catch(Exception e) {
			  DODLog.printStackTrace(e, OutVoucherCgdaService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
		  }
		  return paramModelList;
		 }  
	  
	
//Set Model From PopUp Selected Data
	
public PostOutStandVoucherGenCgdaModel getPopUpModelList(List<String> txnsId,OutVoucherGenCgdaReqModel reqModel){
	
	List<GetOutVoucherGenCgdaDataModel> filteredModelList=new ArrayList<>();
	
	
	
	List<GetOutVoucherGenCgdaDataModel> modelDataList = getGenerateReportData(reqModel);
	
	for(String txn : txnsId ) {
		
		for(GetOutVoucherGenCgdaDataModel model : modelDataList) {
			  if(model.getUniqueTransactionId().equalsIgnoreCase(txn)) {
				  filteredModelList.add(model);
			  }
		}
		
	}

	
	List<PostOutVouchGenCgdaChildModel> childModelList = new ArrayList<>(); 
	
if(filteredModelList !=null) {
 
	for(GetOutVoucherGenCgdaDataModel child : filteredModelList) {
		
		PostOutVouchGenCgdaChildModel postChildModel = new PostOutVouchGenCgdaChildModel();
		
		postChildModel.setAirCompDisplayType(child.getAirCompDisplayType());
		postChildModel.setAirlineCode(child.getAirlineCode());
		postChildModel.setAirlinePnrNo(child.getAirlinePnrNo());
		postChildModel.setAmountDrCr(child.getAmountDrCr());
		postChildModel.setAuthorityDate(child.getAuthorityDate());
		postChildModel.setAuthorityNo(child.getAuthorityNo());
		postChildModel.setBookingClass(child.getBookingClass());
		postChildModel.setBaseFare(child.getBaseFare());
		postChildModel.setBkgVoucherStatus(child.getBkgVoucherStatus());
		postChildModel.setBookingId(child.getBookingId());
		postChildModel.setCancellationCharges(child.getCancellationCharges());
		postChildModel.setCancellationGround(child.getCancellationGround());
		postChildModel.setCdaoAcNo(child.getCdaoAcNo());
		postChildModel.setCgstTax(child.getCgstTax());
		postChildModel.setDgcaApporvalDate(child.getDgcaApporvalDate());
		postChildModel.setDgcaApporvalNo(child.getDgcaApporvalNo());
		postChildModel.setDueAmount(child.getDueAmount());
		postChildModel.setDueAmountNature(child.getDueAmountNature());
		postChildModel.setEducess(child.getEducess());
		postChildModel.setFbNo(child.getFbNo());
		postChildModel.setFirstName(child.getFirstName());
		postChildModel.setFlightNo(child.getFlightNo());
		postChildModel.setGdsPnrNo(child.getGdsPnrNo());
		postChildModel.setGstTax(child.getGstTax());
		postChildModel.setHigherEducess(child.getHigherEducess());
		postChildModel.setIgstTax(child.getIgstTax());
		postChildModel.setJn(child.getJn());
		postChildModel.setJourneyType(child.getJourneyType());
		postChildModel.setK3Tax(child.getK3Tax());
		postChildModel.setLastName(child.getLastName());
		postChildModel.setLtcFare(child.getLastName());
		postChildModel.setNatureNetOutstanding(child.getNatureNetOutstanding());
		postChildModel.setNetOutstanding(child.getNetOutstanding());
		postChildModel.setOtherTax(child.getOtherTax());
		postChildModel.setPaoName(child.getPaoName());
		postChildModel.setPersonalNo(child.getPersonalNo());
		postChildModel.setProcValidForVoucher(child.getProcValidForVoucher());
		postChildModel.setReasonText(child.getReasonText());
		postChildModel.setRefAmount(child.getRefAmount());
		postChildModel.setReferenceId(child.getReferenceId());
		postChildModel.setReferenceIdAmount(child.getReferenceIdAmount());
		postChildModel.setReferenceIdDate(child.getReferenceIdDate());
		postChildModel.setReferenceIdNature(child.getReferenceIdNature());
		postChildModel.setRequestId(child.getRequestId());
		postChildModel.setSectorDetail(child.getSectorDetail());
		postChildModel.setServiceTax(child.getServiceTax());
		postChildModel.setSgstTax(child.getSgstTax());
		postChildModel.setSsrAmount(child.getSsrAmount());
		postChildModel.setStatus(child.getStatus());
		postChildModel.setTicketNo(child.getTicketNo());
		postChildModel.setTransactionAmount(child.getTransactionAmount());
		postChildModel.setTransactionDate(child.getTransactionDate());
		postChildModel.setTransactionId(child.getTransactionId());
		postChildModel.setTransactionNature(child.getTransactionNature());
		postChildModel.setTransactionStatus(child.getTransactionStatus());
		postChildModel.setTravelDate(child.getTravelDate());
		postChildModel.setTravelerAge(child.getTravelerAge());
		postChildModel.setTravelerFirstName(child.getTravelerFirstName());
		postChildModel.setTravelerGender(child.getTravelerGender());
		postChildModel.setTravelerLastName(child.getTravelerLastName());
		postChildModel.setTravelerRelation(child.getTravelerRelation());
		postChildModel.setTravelerTitle(child.getTravelerTitle());
		postChildModel.setTravelerTitleInt(child.getTravelerTitleInt());
		
		postChildModel.setTravelType(child.getTravelType());
		postChildModel.setUgstTax(child.getUgstTax());
		postChildModel.setUniqueTransactionId(child.getUniqueTransactionId());
		
		postChildModel.setUnit(child.getUnit());
		postChildModel.setUserId(child.getUserId());
		postChildModel.setValidForVoucher(child.getValidForVoucher());
		
		postChildModel.setVerifyStatus(child.getVerifyStatus());
		postChildModel.setVoucherTxnType(child.getVoucherTxnType());
		postChildModel.setYq(child.getYq());
		
		
		childModelList.add(postChildModel);
		
	}
}
	
	PostOutStandVoucherGenCgdaModel postParent = new PostOutStandVoucherGenCgdaModel();
	
	
	postParent.setVoucherData(childModelList);
	postParent.setAccountOffice(reqModel.getAccountOffice());
	postParent.setServiceProvider(reqModel.getServiceProvider());
	postParent.setLoginUserId(reqModel.getLoginUserId());
	return postParent;
}

// send Data for save


public AfterVoucherGenCgdaResponse  sendDataForsave(PostOutStandVoucherGenCgdaModel postParent) {
	AfterVoucherGenCgdaResponse  response =null;
	try {
		
       response = restTemplate.postForObject(PcdaConstant.AIR_SERVICE_VOUCHER+"/getGenerateVoucher", postParent, AfterVoucherGenCgdaResponse.class);
    
       return response;
	}
	catch(Exception e) {
		
		DODLog.printStackTrace(e, OutVoucherCgdaService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
	}
	
	return new AfterVoucherGenCgdaResponse();
}


// get a/c office List
public Map<String,String> getAccountOfficeListMap(String officeType,String groupId){
	AccountOfficeCgdaResponse response=null;
	Map<String,String> responseMap=null;
	try {
		 String url = PcdaConstant.OUTSTANDING_VOUCHER_GENERATION_URL+
					"/getMappedAccountOffice/";
		 response = restTemplate.getForObject(url+officeType+"/"+groupId, AccountOfficeCgdaResponse.class);
		 if(response!=null && response.getErrorCode()==200) {
			 responseMap=response.getResponseList();
			 
			 return responseMap;
		 }
	}catch(Exception e) {
		DODLog.printStackTrace(e, OutVoucherCgdaService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
	}
	
	return new HashMap<>();
}

	
}
