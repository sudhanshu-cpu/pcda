package com.pcda.cgda.transactionsettlementair.vouchersettlement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pcda.cgda.transactionsettlementair.outstandingvouchergeneration.service.OutVoucherCgdaService;
import com.pcda.cgda.transactionsettlementair.voucheracknowledgement.model.AccountOfficeAckCgdaResponse;
import com.pcda.cgda.transactionsettlementair.vouchersettlement.model.AfterSaveCgdaResponse;
import com.pcda.cgda.transactionsettlementair.vouchersettlement.model.GetDataListCgdaResponse;
import com.pcda.cgda.transactionsettlementair.vouchersettlement.model.GetDataVoucherNoCgdaParentModel;
import com.pcda.cgda.transactionsettlementair.vouchersettlement.model.GetVoucherNoCgdaResponse;
import com.pcda.cgda.transactionsettlementair.vouchersettlement.model.GetVoucherSetListDataCgdaModel;
import com.pcda.cgda.transactionsettlementair.vouchersettlement.model.PostFormFieldCgdaModel;
import com.pcda.cgda.transactionsettlementair.vouchersettlement.model.PostVoucherCgdaSaveModel;
import com.pcda.util.CommonUtil;
import com.pcda.util.DODLog;
import com.pcda.util.LogConstant;
import com.pcda.util.PcdaConstant;

@Service
public class VoucherSettlementCgdaService {

@Autowired
 private RestTemplate restTemplate;
	
	public List<GetVoucherSetListDataCgdaModel> getDataList(PostFormFieldCgdaModel  fieldModel) {
		 DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementCgdaService.class," fieldModel :::"+fieldModel);	
		
		 List<GetVoucherSetListDataCgdaModel> dataModelList = new ArrayList<>();
		try {

		 String url = PcdaConstant.OUTSTANDING_VOUCHER_GENERATION_URL+
					"/getVoucherSettlementData?fromBookingDate={fromBookingDate}&toBookingDate={toBookingDate}&voucherNo={voucherNo}&invoiceNo={invoiceNo}&statusType={statusType}&accountOffice={accountOffice}&serviceProvider={serviceProvider}";
					 
					 Map<String, String> params = new HashMap<>();
					 
					 
					 params.put("fromBookingDate", fieldModel.getFromBookingDate());
					 params.put("toBookingDate", fieldModel.getToBookingDate());
					 params.put("voucherNo", fieldModel.getVoucherNo());
					 params.put("invoiceNo", fieldModel.getInvoiceNo());
					 params.put("statusType", fieldModel.getStatusType().toString());
				     params.put("accountOffice", fieldModel.getAccountOffice());
					 params.put("serviceProvider", fieldModel.getServiceProvider().toString());
					 
					 GetDataListCgdaResponse response = restTemplate.getForObject(url, GetDataListCgdaResponse.class, params);
		if(response != null && response.getErrorCode()== 200 && null!=response.getResponseList()) {
			
			dataModelList=response.getResponseList();
				
		  
		}
		 DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementCgdaService.class," dataModelList :::"+dataModelList.size());
		}catch(Exception e) {
			 DODLog.printStackTrace(e, VoucherSettlementCgdaService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
					 }
		
		 return dataModelList;
	}
	
	
	
	
	public GetDataVoucherNoCgdaParentModel getVocherNodata(String voucherNo) {
		DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementCgdaService.class," voucherNo :: "+voucherNo);	
		GetDataVoucherNoCgdaParentModel noModel = null;
		
		 String url = PcdaConstant.OUTSTANDING_VOUCHER_GENERATION_URL+
					"/getVoucherDetailsForSettlement/"+voucherNo;
		 try {
		 GetVoucherNoCgdaResponse response = restTemplate.getForObject(url, GetVoucherNoCgdaResponse.class); 
		if(response!=null && response.getErrorCode()==200) {
		 noModel = response.getResponse(); 
		 DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementCgdaService.class," noModel :: "+noModel);	
		 return noModel;
		
		}
		
		 }catch(Exception e) {
			 DODLog.printStackTrace(e, VoucherSettlementCgdaService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
		 }
		 
	return new GetDataVoucherNoCgdaParentModel();
	}
	
	
	public AfterSaveCgdaResponse saveSettlement(PostVoucherCgdaSaveModel saveModel) {
		
		AfterSaveCgdaResponse response;
		 String url = PcdaConstant.OUTSTANDING_VOUCHER_GENERATION_URL+
					"/saveVoucherSettlementDetails";
		 Date date=CommonUtil.formatString(saveModel.getTrDate(), "dd-MM-yyyy");
		 saveModel.setUtrDate(date);
		 DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementCgdaService.class,
					" ## VOUCHER SAVE MODEL IN SERVICE :::"+saveModel);
		 try {
			
			ResponseEntity<AfterSaveCgdaResponse> entity = restTemplate.postForEntity(url, saveModel, AfterSaveCgdaResponse.class);
			
			response = entity.getBody();
			if(response !=null && response.getErrorCode()==200) {
				DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementCgdaService.class," noModel :: "+response);	
				return response;
			}
			
		}catch(Exception e) {
			 DODLog.printStackTrace(e, VoucherSettlementCgdaService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
		}
		
		return new AfterSaveCgdaResponse();
	}
	
	// get a/c office List
	public Map<String,String> getAccountOfficeListMap(String officeType,String groupId){
		DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementCgdaService.class," officeType :: "+officeType +" groupId "+groupId);	
		
		AccountOfficeAckCgdaResponse response=null;
		Map<String,String> responseMap=null;
		try {
			 String url = PcdaConstant.OUTSTANDING_VOUCHER_GENERATION_URL+
						"/getMappedAccountOffice/";
			 response = restTemplate.getForObject(url+officeType+"/"+groupId, AccountOfficeAckCgdaResponse.class);
			 if(response!=null && response.getErrorCode()==200) {
				 responseMap=response.getResponseList();
				 DODLog.info(LogConstant.VOUCHER_SETTLEMENT_LOG_FILE, VoucherSettlementCgdaService.class," responseMap :: "+responseMap.size());	 
				 return responseMap;
			 }
		}catch(Exception e) {
			DODLog.printStackTrace(e, OutVoucherCgdaService.class, LogConstant.CGDA_VOUCHER_LOG_FILE);
		}
					
		return new HashMap<>();
	}
	
}
